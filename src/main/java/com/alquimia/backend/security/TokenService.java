package com.alquimia.backend.security;

import com.alquimia.backend.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("alquimia-api")
                    .withClaim("cdUsuario", usuario.getCdUsuario())
                    .withClaim("role", usuario.getRoleUsuario().name())
                    .withSubject(usuario.getEmailUsuario())
                    .withExpiresAt(Instant.now().plusSeconds(3600))
                    .sign(algorithm);

            return token;

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token: ", exception);
        }
    }

    public String generateRefreshToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("alquimia-api")
                    .withClaim("cdUsuario", usuario.getCdUsuario())
                    .withClaim("role", usuario.getRoleUsuario().name())
                    .withSubject(usuario.getEmailUsuario())
                    .withExpiresAt(Instant.now().plusSeconds(86400))
                    .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar refresh token: ", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("alquimia-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return null;
        }
    }
}
