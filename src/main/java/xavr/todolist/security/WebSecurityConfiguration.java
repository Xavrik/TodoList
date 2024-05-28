package xavr.todolist.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration {

    private final AuthenticationProviderImplementation authenticationProviderImplementation;

    @Autowired
    public WebSecurityConfiguration(AuthenticationProviderImplementation authenticationProviderImplementation) {
        this.authenticationProviderImplementation = authenticationProviderImplementation;
    }


    @Override   
    protected void configure(AuthenticationManagerBuilder auth)  {
        auth.authenticationProvider(authenticationProviderImplementation);

    }

    @Override
    protected void configure(WebSecurity web)  {
        web.debug()

    }

}
