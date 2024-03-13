package br.com.agls.expensescontrolapi.api.filter;

import br.com.agls.expensescontrolapi.domain.exceptions.ExpiredTokenException;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.TimeZone;

@Component
public class TokenDecompositionFilter extends OncePerRequestFilter {

    @Value("${application.default-timezone}")
    private String defaultTimeZone;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        MutableHttpServletRequest mutableHttpServletRequest = new MutableHttpServletRequest(request);
        mutableHttpServletRequest.putHeader("x-user-id", getUserId(request));
        filterChain.doFilter(mutableHttpServletRequest, response);
    }

    private String getUserId(HttpServletRequest request) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String jwt = request.getHeader("x-token");
        String payload = getPayload(jwt);
        AuthenticatedUser authenticatedUser = getAuthenticatedUser(payload);

        verifyExpiration(authenticatedUser);

        return authenticatedUser.getUserId();
    }

    private String getPayload(String jwt) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        return new String(decoder.decode(jwt.split("\\.")[1]));
    }

    private AuthenticatedUser getAuthenticatedUser(String payload) {
        Gson gson = new Gson();
        return gson.fromJson(payload, AuthenticatedUser.class);
    }

    private void verifyExpiration(AuthenticatedUser authenticatedUser) {
        if (isExpired(authenticatedUser.getExp()))
                throw new ExpiredTokenException("Session expired. Please, login again.");
    }

    private boolean isExpired(Double exp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(exp.longValue()), TimeZone.getTimeZone(defaultTimeZone)
                .toZoneId()).isBefore(LocalDateTime.ofInstant(Instant.now(), TimeZone.getTimeZone(defaultTimeZone)
                .toZoneId()));
    }
}
