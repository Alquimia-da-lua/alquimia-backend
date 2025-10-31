package com.alquimia.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configure(httpSecurity))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        // endpoints sem token
                        .requestMatchers(HttpMethod.POST,
                                "/auth/login",
                                "/auth/register",
                                "/auth/refresh-token")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET,
                                "/api/produto/listar",
                                "/api/produto/listar/ativos",
                                "/api/produto/buscar/**")
                        .permitAll()

                        // endpoints com token
                        // usuario
                        .requestMatchers(HttpMethod.GET, "/api/usuario/buscar/{cdUsuario}").hasAnyRole("CLIENTE", "FUNCIONARIO")
                        .requestMatchers(HttpMethod.GET, "/api/usuario/listar").hasRole("FUNCIONARIO")
                        .requestMatchers(HttpMethod.GET, "/api/usuario/listar/ativos").hasRole("FUNCIONARIO")
                        .requestMatchers(HttpMethod.PUT, "/api/usuario/deletar/{cdUsuario}").hasRole("FUNCIONARIO")
                        .requestMatchers(HttpMethod.POST, "/api/usuario/{cdUsuario}/endereco").hasAnyRole("CLIENTE", "FUNCIONARIO")
                        .requestMatchers(HttpMethod.PUT, "/api/usuario/atualizar/{cdUsuario}").hasAnyRole("CLIENTE", "FUNCIONARIO")

                        // endereco
                        .requestMatchers(HttpMethod.GET, "/api/endereco/{cep}").hasAnyRole("CLIENTE", "FUNCIONARIO")
                        .requestMatchers(HttpMethod.GET, "/api/endereco/buscar/{cdEndereco}").hasAnyRole("CLIENTE", "FUNCIONARIO")
                        .requestMatchers(HttpMethod.POST, "/api/endereco").hasAnyRole("CLIENTE", "FUNCIONARIO")
                        .requestMatchers(HttpMethod.PUT, "/api/endereco/alterar/{cdEndereco}").hasAnyRole("CLIENTE", "FUNCIONARIO")
                        .requestMatchers(HttpMethod.DELETE, "/api/endereco/{cdEndereco}").hasAnyRole("CLIENTE", "FUNCIONARIO")

                        // estoque
                        .requestMatchers("/api/estoque/**").hasRole("FUNCIONARIO")

                        // itemEstoqueController
                        .requestMatchers("/api/itemestoque/**").hasRole("FUNCIONARIO")

                        // itemPedidoController
                        .requestMatchers("/api/itempedido/**").hasRole("CLIENTE")

                        // pedido
                        .requestMatchers(HttpMethod.POST, "/api/pedido").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.GET, "/api/pedido/buscar/{cdPedido}").hasAnyRole("CLIENTE", "FUNCIONARIO")
                        .requestMatchers(HttpMethod.GET, "/api/pedido/listar").hasRole("FUNCIONARIO")
                        .requestMatchers(HttpMethod.GET, "/api/pedido/listar/cliente/{cdUsuario}").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/api/pedido/status/{cdPedido}").hasRole("FUNCIONARIO")
                        .requestMatchers(HttpMethod.PUT, "/api/pedido/pagamento/{cdPedido}").hasRole("CLIENTE")

                        // produto
                        .requestMatchers(HttpMethod.POST, "/api/produto").hasRole("FUNCIONARIO")
                        .requestMatchers(HttpMethod.PUT, "/api/produto/alterar/{cdProduto}").hasRole("FUNCIONARIO")
                        .requestMatchers(HttpMethod.DELETE, "/api/produto/delete/{cdProduto}").hasRole("FUNCIONARIO")
                        .requestMatchers(HttpMethod.GET, "/api/produto/**").hasAnyRole("CLIENTE", "FUNCIONARIO")

                        // qualquer outro endpoint precisa de autenticação
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
