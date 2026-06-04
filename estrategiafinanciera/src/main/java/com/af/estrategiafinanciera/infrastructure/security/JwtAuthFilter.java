package com.af.estrategiafinanciera.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain fIlterChain
    ) throws ServletException, IOException {

        //extraer el header Authorization
        final String authHeader = request.getHeader("Authorization");

        //Si no hay token o no empieza con "Bearer ", dejar pasar
        if (authHeader == null ||!authHeader.startsWith("Bearer ")){
            fIlterChain.doFilter(request,response);
            return;
        }

        //Extraer el token (quitar "Bearer ")
        final String jwt = authHeader.substring(7);

        //Extraer el email del token
        final String userEmail = jwtService.extractUsername(jwt);

        // Si hay email y el usuario NO está autenticado aún
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){


            // 6. Cargar el usuario desde la base de datos
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            //Validar que el token sea válido
            if (jwtService.isTokenValid(jwt, userDetails)){

                //Crear objeto de autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                //Agregar detalles de la petición
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                //Registrar autenticación en el contexto de Spring Security
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        //Continuar con la cadena de filtros
        fIlterChain.doFilter(request, response);
    }
}
