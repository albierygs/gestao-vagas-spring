package com.albierygs.hrvacancies.modules.company.entities;

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
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "company")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O campo (username) é obrigatório.")
    @Pattern(regexp = "\\S+", message = "O campo (username) não pode conter espaços.")
    private String username;

    @Length(min = 1, max = 50, message = "O campo nome não pode ultrapassar 50 caracteres.")
    private String name;

    @Email(message = "O campo (email) deve conter um email válido.")
    private String email;

    @Length(max = 200, message = "O campo (description) deve conter no máximo 200 caracteres.")
    private String description;

    @Length(min = 6, max = 100, message = "O campo (password) deve conter entre 6 e 100 caracteres.")
    private String password;

    @URL(message = "O campo (website) precisa ser uma url válida.")
    private String website;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
