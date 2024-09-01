package WelcomeWise.jwt_servce.Authentification.Model;

import java.util.List;

public class AuthenticationResponse {
    private final String jwt;
    private final List<String> roles;

    public AuthenticationResponse(String jwt, List<String> roles) {
        this.jwt = jwt;
        this.roles = roles;
    }

    public String getJwt() {
        return jwt;
    }

    public List<String> getRoles() {
        return roles;
    }
}
