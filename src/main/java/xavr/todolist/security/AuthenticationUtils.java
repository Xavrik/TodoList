package xavr.todolist.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AuthenticationUtils {

    private final TokenManager tokenManager;

    @Autowired
    public AuthenticationUtils(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public <R> ResponseEntity<R> performAfterAuthentication(HttpServletRequest request, Function<Long, ResponseEntity<R>> function) {
        String token = request.getHeader("token");

        if (!tokenManager.verifyToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        TokenPayload tokenPayload = tokenManager.extractToken(token);

        return function.apply(tokenPayload.getUserID());
    }


}
