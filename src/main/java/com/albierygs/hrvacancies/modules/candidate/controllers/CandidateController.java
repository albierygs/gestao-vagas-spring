package com.albierygs.hrvacancies.modules.candidate.controllers;


import com.albierygs.hrvacancies.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.albierygs.hrvacancies.modules.candidate.entities.CandidateEntity;
import com.albierygs.hrvacancies.modules.candidate.useCases.ApplyJobCandidateUseCase;
import com.albierygs.hrvacancies.modules.candidate.useCases.CreateCandidateUseCase;
import com.albierygs.hrvacancies.modules.candidate.useCases.ListAllJobsByfilterUseCase;
import com.albierygs.hrvacancies.modules.candidate.useCases.ProfileCandidateUseCase;
import com.albierygs.hrvacancies.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByfilterUseCase listAllJobsByfilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping("/create")
    @Operation(summary = "Cadastro de candidato", description = "Essa função é responsével por cadastrar os candidatos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CandidateEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "Usuário já existe")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate) {
        try {
            var result = this.createCandidateUseCase.execute(candidate);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Essa função é responsével por as informações do perfil do candidato")
    @SecurityRequirement(name = "jwt_auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "No token provided"),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    public ResponseEntity<Object> getProfile(HttpServletRequest request) {
        var candidateId = request.getAttribute("candidate_id");

        if (candidateId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No token provided");
        }

        try {
            var result = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas disponíveis para o candidato", description = "Essa função é responsével por listar todas as vagas disponíveis baseada no filtro")
    @ApiResponses(
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
            })
    )
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> listJobs(@RequestParam String filter) {
        return this.listAllJobsByfilterUseCase.execute(filter);
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Inscrição do candidato em uma vaga", description = "Essa função é responsével por realizar a inscrição do candidate em uma vaga.")
    public ResponseEntity<Object> applyJob(@RequestBody UUID idJob, HttpServletRequest request) {
        var candidateId = request.getAttribute("candidate_id");

        try {
            var result = this.applyJobCandidateUseCase.execute(UUID.fromString(candidateId.toString()), idJob);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
