package com.albierygs.hrvacancies.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(example = "João da Silva")
    private String name;

    @Schema(example = "joao")
    private String username;

    @Schema(example = "Desenvolvedor Java")
    private String description;

    @Schema(example = "joao@gmail.com")
    private String email;
}
