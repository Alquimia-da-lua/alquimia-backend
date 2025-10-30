package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.LoginRequestDTO;
import com.alquimia.backend.dto.response.LoginResponseDTO;
import com.alquimia.backend.model.Usuario;
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

    public LoginResponseDTO login (LoginRequestDTO loginDto) {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(
                loginDto.emailUsuario(),
                loginDto.senhaUsuario());

        Authentication authentication = authenticationManager.authenticate(userAndPass);

        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenService.generateToken(usuario);

        return (new LoginResponseDTO(usuario.getNmUsuario(),
                usuario.getEmailUsuario(),
                usuario.getNuTelefone(),
                usuario.getRoleUsuario(),
                token));
    }
}
