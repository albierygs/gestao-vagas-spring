package com.albierygs.hrvacancies.modules.company.useCases;

import com.albierygs.hrvacancies.exceptions.CompanyNotFoundException;
import com.albierygs.hrvacancies.modules.company.entities.JobEntity;
import com.albierygs.hrvacancies.modules.company.repositories.CompanyRepository;
import com.albierygs.hrvacancies.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public JobEntity execute(JobEntity jobEntity) {
        this.companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(CompanyNotFoundException::new);
        return this.jobRepository.save(jobEntity);
    }
}
