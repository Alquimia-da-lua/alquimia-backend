package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.LoginRequestDTO;
import com.alquimia.backend.dto.response.LoginResponseDTO;
import com.alquimia.backend.dto.response.RefreshTokenResponseDTO;
import com.alquimia.backend.exception.UsuarioNaoEncontradoException;
import com.alquimia.backend.model.Usuario;
import com.alquimia.backend.repository.UsuarioRepository;
import com.alquimia.backend.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public LoginResponseDTO login (LoginRequestDTO loginDto) {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(
                loginDto.emailUsuario(),
                loginDto.senhaUsuario());

        Authentication authentication = authenticationManager.authenticate(userAndPass);

        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenService.generateToken(usuario);
        String refreshToken = tokenService.generateRefreshToken(usuario);

        return (new LoginResponseDTO(usuario.getCdUsuario(),
                usuario.getNmUsuario(),
                usuario.getEmailUsuario(),
                usuario.getNuTelefone(),
                usuario.getRoleUsuario(),
                token,
                refreshToken));
    }

    public RefreshTokenResponseDTO refreshToken(String refreshToken) {
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }

        String emailUsuario = tokenService.validateToken(refreshToken);

        Usuario usuario = usuarioRepository.findByEmailUsuario(emailUsuario)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        String novoToken = tokenService.generateToken(usuario);
        String novoRefreshToken = tokenService.generateRefreshToken(usuario);

        return (new RefreshTokenResponseDTO(novoToken, novoRefreshToken));
    }
}
