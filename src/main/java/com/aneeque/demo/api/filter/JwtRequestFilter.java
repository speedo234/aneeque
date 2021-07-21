package com.aneeque.demo.api.filter;



import com.aneeque.demo.api.util.JwtUtil;
import com.aneeque.demo.commons.security.UserDetailsImpl;
import com.aneeque.demo.exception.ApplicationException;
import com.aneeque.demo.user.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Isidienu Chudi
 */
public class JwtRequestFilter extends OncePerRequestFilter {

    static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);


    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ApplicationException, ServletException, IOException {
        String username = null;
        String jwt = null;
        UserDetailsImpl userDetailsImpl = null;
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null) {
            response.sendError((HttpServletResponse.SC_UNAUTHORIZED), "Missing Authorization Header...");
            return;
        }
        if (!authorizationHeader.startsWith("Bearer ")) {
            response.sendError((HttpServletResponse.SC_UNAUTHORIZED), "Malformed Authorization Header. Should start with Bearer ");
            return;
        }
        jwt = authorizationHeader.substring(7);
        try {
            username = new JwtUtil().extractUsername(jwt);
            if(username == null){
                response.sendError((HttpServletResponse.SC_UNAUTHORIZED), "username not found...");
                return;
            }
            Claims claims = new JwtUtil().extractAllClaims(jwt);
            if(!claims.toString().contains("ROLE_USER"))
                response.sendError((HttpServletResponse.SC_FORBIDDEN), "Unauthorized Role Access");
        } catch (SignatureException se) {
            se.printStackTrace();
            response.sendError((HttpServletResponse.SC_UNAUTHORIZED), "Invalid Authorization Token");
            return;
        }catch (ExpiredJwtException eje){
            eje.printStackTrace();
            response.sendError((HttpServletResponse.SC_UNAUTHORIZED), "Token is Expired");
            return;
        }
        catch (MalformedJwtException mje){
            mje.printStackTrace();
            response.sendError((HttpServletResponse.SC_UNAUTHORIZED), "Malformed JSON value");
            return;
        }
        if (username != null)
            chain.doFilter(request, response);
    }

}