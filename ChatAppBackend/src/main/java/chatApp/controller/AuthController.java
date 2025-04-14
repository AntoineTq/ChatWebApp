package chatApp.controller;


import chatApp.service.JwtService;
import chatApp.model.Person;
import chatApp.repository.PersonRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public static class TokenRequest {
        private String token;
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }
    @Autowired
    private GoogleIdTokenVerifier googleIdTokenVerifier;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<TokenRequest> userLogin(@RequestBody TokenRequest tokenRequest) throws GeneralSecurityException, IOException {
        GoogleIdToken googleIdToken = googleIdTokenVerifier.verify(tokenRequest.getToken());
        GoogleIdToken.Payload googleUser = googleIdToken.getPayload();


        Optional<Person> existingUser = personRepository.findByEmail(googleUser.getEmail());
        if (existingUser.isEmpty()) {
            Person p = new Person(
                    googleUser.getSubject(),
                    googleUser.get("given_name").toString(),
                    googleUser.get("family_name").toString(),
                    googleUser.getEmail()
            );
            personRepository.save(p);
        }

        String token = jwtService.generateToken(googleUser.getEmail());
        TokenRequest request = new TokenRequest();
        request.setToken(token);
        return ResponseEntity.ok(request);
    }


}
