package com.albierygs.hrvacancies.modules.candidate.repositories;

import com.albierygs.hrvacancies.modules.candidate.entities.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {
}
