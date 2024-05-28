package xavr.todolist.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProviderImplementation implements AuthenticationProvider {

    private final UserDetailServiceImplementation userDetailServiceImplementation;

    public AuthenticationProviderImplementation(UserDetailServiceImplementation userDetailServiceImplementation) {
        this.userDetailServiceImplementation = userDetailServiceImplementation;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if(userDetailServiceImplementation == null){
            throw new AuthenticationServiceException("User Service is null");
        }
        UserDetails userDetails = userDetailServiceImplementation.loadUserByUsername(username);
        if(userDetails == null){
            throw new AuthenticationServiceException("No such user was found");
        }
        if(userDetails.getPassword().equals(password)){
            return  new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
        }else {
            throw  new AuthenticationServiceException("USer information is incorrect ");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
