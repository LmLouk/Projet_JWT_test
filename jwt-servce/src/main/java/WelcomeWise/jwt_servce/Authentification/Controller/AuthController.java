package WelcomeWise.jwt_servce.Authentification.Controller;

import WelcomeWise.jwt_servce.Authentification.Model.AuthenticationRequest;
import WelcomeWise.jwt_servce.Authentification.Model.AuthenticationResponse;
import WelcomeWise.jwt_servce.Authentification.Model.User;
import WelcomeWise.jwt_servce.Authentification.Repository.UserRepository;
import WelcomeWise.jwt_servce.Authentification.Service.JwtUtil;
import WelcomeWise.jwt_servce.Authentification.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Endpoint pour l'authentification
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        // Extraire les rôles
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new AuthenticationResponse(jwt, roles));
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthenticationRequest registrationRequest) throws Exception {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByUsername(registrationRequest.getUsername()) != null) {
            return ResponseEntity.badRequest().body("User already exists!");
        }

        // Créer un nouvel utilisateur
        User newUser = new User();
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        newUser.setEmail(registrationRequest.getEmail());

        // Assigner les rôles spécifiés ou un rôle par défaut
        Set<String> roles = registrationRequest.getRoles();
        if (roles == null || roles.isEmpty()) {
            roles = new HashSet<>();
            roles.add("ROLE_USER");  // Rôle par défaut si aucun rôle n'est spécifié
        }
        newUser.setRoles(roles);

        // Sauvegarder l'utilisateur dans la base de données
        userRepository.save(newUser);

        // Créer un token JWT pour le nouvel utilisateur
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(registrationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        // Convertir le Set de rôles en List pour la réponse
        List<String> rolesList = roles.stream().collect(Collectors.toList());

        return ResponseEntity.ok(new AuthenticationResponse(jwt, rolesList));
    }

    @GetMapping("/validateToken")
    public ResponseEntity<AuthResponse> validateToken(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7); // Remove "Bearer " prefix
            String username = jwtUtil.extractUsername(jwtToken);

            // Charger les détails de l'utilisateur à partir du nom d'utilisateur
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);

            // Valider le token en utilisant le token et les détails de l'utilisateur
            boolean isValid = jwtUtil.validateToken(jwtToken, userDetails);

            AuthResponse response = new AuthResponse(isValid);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            AuthResponse response = new AuthResponse(false);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    // Define AuthResponse class as an inner class or in a separate file
    public static class AuthResponse {
        private boolean valid;

        public AuthResponse(boolean valid) {
            this.valid = valid;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }
    }
}
