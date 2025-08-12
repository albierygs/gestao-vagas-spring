package com.albierygs.hrvacancies.modules.candidate.useCases;

import com.albierygs.hrvacancies.modules.company.entities.JobEntity;
import com.albierygs.hrvacancies.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsByfilterUseCase {

    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(String filter) {
        return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }
}
