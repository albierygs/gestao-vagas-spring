package com.albierygs.hrvacancies.modules.candidate.useCases;

import com.albierygs.hrvacancies.exceptions.JobNotFoundException;
import com.albierygs.hrvacancies.exceptions.UserNotFoundException;
import com.albierygs.hrvacancies.modules.candidate.entities.ApplyJobEntity;
import com.albierygs.hrvacancies.modules.candidate.entities.CandidateEntity;
import com.albierygs.hrvacancies.modules.candidate.repositories.ApplyJobRepository;
import com.albierygs.hrvacancies.modules.candidate.repositories.CandidateRepository;
import com.albierygs.hrvacancies.modules.company.entities.JobEntity;
import com.albierygs.hrvacancies.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    public void should_not_be_able_to_apply_job_with_candidate_not_found() {
        try {
            this.applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to apply job with job not found")
    public void should_not_be_able_to_apply_job_with_job_not_found() {
        var idCandidate = UUID.randomUUID();

        var candidate = CandidateEntity.builder().id(idCandidate).build();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        try {
            this.applyJobCandidateUseCase.execute(idCandidate, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should be able to create a new apply job")
    public void should_be_able_to_create_a_new_apply_job() {
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();

        var candidate = CandidateEntity.builder().id(idCandidate).build();
        var job = JobEntity.builder().id(idJob).build();

        var applyJob = ApplyJobEntity.builder().jobId(idJob).candidateId(idCandidate).build();
        var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(job));
        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = this.applyJobCandidateUseCase.execute(idCandidate, idJob);
        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
    }
}
