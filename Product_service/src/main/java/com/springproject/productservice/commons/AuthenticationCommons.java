package com.springproject.productservice.commons;

import com.springproject.productservice.Exception.TokenAuthenticationFailedException;
import com.springproject.productservice.dtos.Role;
import com.springproject.productservice.dtos.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
@Component

public class AuthenticationCommons {
    RestTemplate restTemplate;
    public AuthenticationCommons(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    UserDto validateToken(String token) throws TokenAuthenticationFailedException {
        try {
            return restTemplate.postForObject("http://localhost:8080/users/validate/" + token,null,
                     UserDto.class);
        }
        catch(HttpClientErrorException ex){
            throw new TokenAuthenticationFailedException("Wrong token");
        }
    }

    public boolean isTokenValid(String token) {
        try {
            validateToken(token);
            return true;
        } catch (TokenAuthenticationFailedException ex) {
            return false;
        }
    }

    public boolean checkAutorization(String role, String token) throws TokenAuthenticationFailedException {
        UserDto userDto = this.validateToken(token);
        for(Role roles: userDto.getRoles()){
            if (roles.getName().equals(role)){
                return true;
            }
        }

        return false;
    }
}
