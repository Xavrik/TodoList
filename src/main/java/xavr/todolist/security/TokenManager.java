package xavr.todolist.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class TokenManager {

    private static final Logger log = LoggerFactory.getLogger(TokenManager.class);
    private final String secretKey;

    public TokenManager(String secretKey) {
        this.secretKey = secretKey;
    }

    public String createToken(TokenPayload tokenPayload) {
        String mixedPayload = createMixedTokenPayload(tokenPayload);
        String sigbature = createSignature(mixedPayload);
        String token = String.format("%s#%s", mixedPayload, sigbature);
        return token;
    }

    public String createMixedTokenPayload(TokenPayload tokenPayload) {
        String timeofCrearion = String.valueOf(tokenPayload.getTimeOfCreation().getTime());
        String id = String.valueOf(tokenPayload.getUserID());
        String email = String.valueOf(tokenPayload.getEmail());

        return String.format("%s#%s#%s", id, email, timeofCrearion);
    }

    public String createSignature(String mixedPayload) {
        return " " + mixedPayload.charAt(0)
                + mixedPayload.charAt(2)
                + mixedPayload.charAt(5)
                + mixedPayload.charAt(mixedPayload.length() - 1);
    }

    public boolean verifyToken(String token) {
        TokenPayload payload = extractToken(token);
        String trustedToken = createToken(payload);
        return token.equals(trustedToken);
    }

    public TokenPayload extractToken(String token) {
        String[] tokenParts = token.split("#");
        long id = Long.parseLong(tokenParts[0]);
        String email = tokenParts[1];
        Date timeOfCreation = new Date(Long.parseLong(tokenParts[2]));

        TokenPayload payload = new TokenPayload(id, email, timeOfCreation);
        return payload;
    }

}
