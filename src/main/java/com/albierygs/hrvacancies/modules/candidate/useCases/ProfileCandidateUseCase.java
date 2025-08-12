package com.albierygs.hrvacancies.modules.candidate.useCases;

import com.albierygs.hrvacancies.exceptions.UserNotFoundException;
import com.albierygs.hrvacancies.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.albierygs.hrvacancies.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID id) {
        var profile = this.candidateRepository.findById(id).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        return ProfileCandidateResponseDTO.builder()
                .username(profile.getUsername())
                .name(profile.getName())
                .email(profile.getEmail())
                .description(profile.getDescription())
                .build();
    }
}
