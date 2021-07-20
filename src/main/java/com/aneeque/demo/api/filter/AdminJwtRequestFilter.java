package com.aneeque.demo.api.filter;


import com.aneeque.demo.api.util.JwtUtil;
import com.aneeque.demo.commons.security.UserDetailsImpl;
import com.aneeque.demo.user.UserRepository;
import com.aneeque.demo.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Isidienu Chudi
 */
//@Component
//@Order(1)
public class AdminJwtRequestFilter extends OncePerRequestFilter {

    static final Logger logger = LoggerFactory.getLogger(AdminJwtRequestFilter.class);

    public static int counter = 0;
    /*@Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;*/
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    /*@Autowired
    UserDetailsService userDetailsService;*/
//    HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String username = null;
        String jwt = null;
        UserDetailsImpl userDetailsImpl = null;
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null) {
            response.sendError((HttpServletResponse.SC_BAD_REQUEST), "Missing Authorization Header...");
            return;
        }
        if (!authorizationHeader.startsWith("Bearer ")) {
            response.sendError((HttpServletResponse.SC_BAD_REQUEST), "Malformed Authorization Header. Should start with Bearer ");
            return;
        }
        jwt = authorizationHeader.substring(7);
        try {
            username = new JwtUtil().extractUsername(jwt);
            logger.info("jwt ===>> {} ", jwt);
            logger.info("username ===>> {} ", username);

            Claims claims = new JwtUtil().extractAllClaims(jwt);
            logger.info("claims ===>> {} ", claims);
            if(!claims.toString().contains("ROLE_ADMIN")){
                logger.info("===>> Unauthorized Role Access in Secure Filter for this mapping /api/admin/secure/*");
                response.sendError((HttpServletResponse.SC_FORBIDDEN), "Unauthorized Role Access");
            }else
                logger.info("===>> Authorized Role Access in Secure Filter for this mapping /api/admin/secure/*");



        } catch (SignatureException se) {
            se.printStackTrace();
            response.sendError((HttpServletResponse.SC_UNAUTHORIZED), "Invalid Authorization Token");
            return;
        }catch (ExpiredJwtException eje){
            logger.info("token is expired... so return status 401");
            response.sendError((HttpServletResponse.SC_UNAUTHORIZED), "Token is Expired");
            return;
        }

        /*
        user.getRolez().forEach((role) -> {
            this.authorityList.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        */


        /*UserDetails userDetails = new UserDetailsServiceImpl().loadUserByUsername(username);
        logger.info("getUsername <<>> {} ", userDetails.getUsername());*/

/*        logger.info("user rolez to string {} ", user.getRolez().toString());
        for (Rolez role : user.getRolez()) {
            if (!role.getPrivilege().equalsIgnoreCase("admin")) {
                try {
                    response.sendError((HttpServletResponse.SC_UNAUTHORIZED), "Unauthorized Role");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                continue;
            }
        }*/


//        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
//            System.out.println("jwt retrieval attempt...");
//            jwt = authorizationHeader.substring(7);
//            try{
//                username = jwtUtil.extractUsername(jwt);
//            }catch(NullPointerException npe){
//                npe.printStackTrace();
//            }
//        }
//
//        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
//            System.out.println("userDetails retrieval attempt...");
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//            if(jwtUtil.validateToken(jwt, userDetails)){
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken
//        (userDetails, null, userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken
//                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//
//            }
//        }

        if (username != null)
            chain.doFilter(request, response);
    }

}