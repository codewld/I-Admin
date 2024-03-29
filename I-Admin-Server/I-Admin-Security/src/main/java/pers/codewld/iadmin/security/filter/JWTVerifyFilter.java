package pers.codewld.iadmin.security.filter;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pers.codewld.iadmin.common.controller.ErrController;
import pers.codewld.iadmin.common.exception.CustomException;
import pers.codewld.iadmin.security.model.entity.IUserDetails;
import pers.codewld.iadmin.security.util.JWTUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT校验 过滤器
 */
@Component
public class JWTVerifyFilter extends OncePerRequestFilter {

    @Value("${security.jwt.headerField}")
    String headerField;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader(headerField);
        if (jwtToken != null) {
            try {
                IUserDetails iUserDetails = JWTUtils.decode(jwtToken);
                Authentication authentication = new UsernamePasswordAuthenticationToken(iUserDetails.getId(), null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (CustomException e) {
                ErrController.forward2Err(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}
