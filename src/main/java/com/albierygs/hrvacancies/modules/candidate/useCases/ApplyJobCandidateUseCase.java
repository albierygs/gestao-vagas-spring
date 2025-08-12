package com.albierygs.hrvacancies.modules.candidate.useCases;

import com.albierygs.hrvacancies.exceptions.JobNotFoundException;
import com.albierygs.hrvacancies.exceptions.UserNotFoundException;
import com.albierygs.hrvacancies.modules.candidate.entities.ApplyJobEntity;
import com.albierygs.hrvacancies.modules.candidate.repositories.ApplyJobRepository;
import com.albierygs.hrvacancies.modules.candidate.repositories.CandidateRepository;
import com.albierygs.hrvacancies.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
        var candidate = this.candidateRepository.findById(idCandidate).orElseThrow(UserNotFoundException::new);

        var job = this.jobRepository.findById(idJob).orElseThrow(JobNotFoundException::new);

        var applyJob = ApplyJobEntity.builder().jobId(job.getId()).candidateId(candidate.getId()).build();

        return this.applyJobRepository.save(applyJob);
    }
}
