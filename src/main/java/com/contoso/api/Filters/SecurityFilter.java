package com.contoso.api.Filters;

import com.contoso.api.model.TokenDetails;
import com.contoso.api.utils.JwtTokenUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecurityFilter extends OncePerRequestFilter {
    private static final Logger logger = LogManager.getLogger(SecurityFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            //Extract JWT token from Http Request
            String jwtToken = this.jwtTokenUtil.extractJwtTokenFromRequest(request);
            if(StringUtils.isNotEmpty(jwtToken) &&
                    ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication())){
                //Parse JWT token and collect required details
                TokenDetails tokenDetails = this.jwtTokenUtil.parseJwtToken(jwtToken);
                //Load the user details from database
                UserDetails userDetails = userDetailsService.loadUserByUsername(tokenDetails.getUserName());
                //Check if token is valid or not
                Boolean isJwtTokenValid = userDetails.getUsername().equals(tokenDetails.getUserName())
                                            && !tokenDetails.getIsTokenExpired();
                if(isJwtTokenValid){
                    //If valid set authentication in context
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    List<Object> additionDetail = new ArrayList<>();
                    additionDetail.add(jwtToken);
                    additionDetail.add(new WebAuthenticationDetailsSource().buildDetails(request));
                    authentication.setDetails(additionDetail);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception exception) {
            logger.error("Cannot set user authentication: {}", exception.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
