package com.albierygs.hrvacancies.modules.candidate.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Length(min = 1, max = 100, message = "O campo nome não pode ultrapassar 100 caracteres.")
    @Schema(example = "joão da silva", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "O campo (username) é obrigatório.")
    @Pattern(regexp = "\\S+", message = "O campo (username) não pode conter espaços.")
    @Schema(example = "joao", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Email(message = "O campo (email) deve conter um email válido.")
    @Schema(example = "joao@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Length(min = 6, max = 100, message = "O campo (password) deve conter entre 6 e 100 caracteres.")
    @Schema(example = "******", minLength = 6, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Length(max = 100, message = "O campo (description) deve conter no máximo 100 caracteres.")
    @Schema(example = "Desenvolvedor Java", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
