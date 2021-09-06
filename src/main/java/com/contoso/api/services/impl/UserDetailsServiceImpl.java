package com.contoso.api.services.impl;

import com.contoso.api.entities.UserAuth;
import com.contoso.api.repositories.UserAuthRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth userAuth = this.userAuthRepository.findUserAuthByUserName(username);
        if(ObjectUtils.isEmpty(userAuth)){
            throw new UsernameNotFoundException("Username : "+username+" does not exist");
        }

        return new org.springframework.security.core.userdetails.User(userAuth.getUserName(),userAuth.getPassword(),
                userAuth.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList()));
    }
}
