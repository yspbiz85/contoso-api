package com.contoso.api.services.impl;

import com.contoso.api.entities.Role;
import com.contoso.api.entities.UserAuth;
import com.contoso.api.model.ERole;
import com.contoso.api.model.LoginRequest;
import com.contoso.api.model.SignupRequest;
import com.contoso.api.model.UserResponse;
import com.contoso.api.repositories.RoleRepository;
import com.contoso.api.repositories.UserAuthRepository;
import com.contoso.api.services.UserAuthService;
import com.contoso.api.utils.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;


    @Override
    public Boolean signup(SignupRequest signupRequest) {
        UserAuth userAuth = new UserAuth();
        userAuth.setEmail(signupRequest.getEmail());
        userAuth.setUserName(signupRequest.getUsername());
        userAuth.setPassword(this.encoder.encode(signupRequest.getPassword()));
        List<Role> roles = new ArrayList<>();
        signupRequest.getRoles().stream().forEach(sRole -> {
            if(StringUtils.containsAnyIgnoreCase(sRole, ERole.USER.name(),ERole.ADMIN.name())){
                Role role = this.roleRepository.findRoleByName(sRole);
                roles.add(role);
            }
        });
        if(CollectionUtils.isEmpty(roles)){
            Role role = this.roleRepository.findRoleByName("USER");
            roles.add(role);
        }
        userAuth.setRoles(roles);
        this.userAuthRepository.save(userAuth);
        return true;
    }

    @Override
    public UserResponse login(LoginRequest loginRequest) {
        Authentication authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserAuth userAuth = this.userAuthRepository.findUserAuthByUserName(authentication.getName());
        return UserResponse.builder()
                .token(this.jwtTokenUtil.generateToken(userAuth))
                .build();
    }
}
