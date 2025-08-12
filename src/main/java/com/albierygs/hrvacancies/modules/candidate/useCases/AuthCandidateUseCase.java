package com.albierygs.hrvacancies.modules.candidate.useCases;

import com.albierygs.hrvacancies.modules.candidate.dto.AuthCandidateDTO;
import com.albierygs.hrvacancies.modules.candidate.dto.AuthCandidateResponseDTO;
import com.albierygs.hrvacancies.modules.candidate.repositories.CandidateRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    public AuthCandidateResponseDTO execute(AuthCandidateDTO authCandidateDTO) throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(authCandidateDTO.username()).orElseThrow(() -> {
            throw new UsernameNotFoundException("Username/password incorrect");
        });

        var correctPassword = this.passwordEncoder.matches(authCandidateDTO.password(), candidate.getPassword());

        if (!correctPassword) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));

        var token = JWT.create()
                .withIssuer("spring")
                .withExpiresAt(expiresIn)
                .withClaim("roles", Arrays.asList("CANDIDATE"))
                .withSubject(candidate.getId().toString())
                .sign(algorithm);

        return AuthCandidateResponseDTO.builder().acess_token(token).expires_in(expiresIn.toEpochMilli()).build();
    }
}
