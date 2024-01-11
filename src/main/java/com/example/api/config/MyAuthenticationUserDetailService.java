package com.example.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class MyAuthenticationUserDetailService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private String keyAccessSecret;

    @Autowired
    public MyAuthenticationUserDetailService(String key) {
        this.keyAccessSecret = key;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {

        var credential = token.getCredentials().toString();

        if (credential.isEmpty()) {
            throw new UsernameNotFoundException("Authorization header must not be empty.");
        }

        if(credential.equals("dayvison")){
            return new User("user", "", AuthorityUtils.createAuthorityList("GetItem"));
        }else{
            throw new UsernameNotFoundException("Invalid authorization header.");
        }
    }
}
