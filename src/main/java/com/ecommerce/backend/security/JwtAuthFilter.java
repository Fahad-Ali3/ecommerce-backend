package com.ecommerce.backend.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/**/me")
public class JwtAuthFilter extends OncePerRequestFilter {

    private Logger logger= LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String reqHeader=request.getHeader("Authorization");
        logger.info("header: {}",reqHeader);

        String username=null;
        String token=null;

        if(reqHeader!=null&&reqHeader.startsWith("Bearer")){
            token=reqHeader.substring(7);

            try {
                username=this.jwtHelper.getUsernameFromToken(token);
            }catch (IllegalArgumentException e){
                logger.info("Illegal Argument while fetching username!!!");
            }catch (ExpiredJwtException e){
                logger.info("Given jwt token is expired");
            }catch (MalformedJwtException e){
                logger.info("Some changes has been made to token!, Invalid Token!!!");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            logger.info("Invalid header info!!!");
        }
        if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
            Boolean validateToken=jwtHelper.validateToken(token,userDetails);

            if(validateToken){

                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.setAttribute("username",username);

            }else{
                logger.info("Validation Failed!!!");
            }
        }

        filterChain.doFilter(request,response);
    }
}
