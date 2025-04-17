package chatApp.controller;


import chatApp.dto.UserTokenDTO;
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


    @Autowired
    private GoogleIdTokenVerifier googleIdTokenVerifier;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<UserTokenDTO> userLogin(@RequestBody UserTokenDTO userTokenDTO) throws GeneralSecurityException, IOException {
        GoogleIdToken googleIdToken = googleIdTokenVerifier.verify(userTokenDTO.getToken());
        GoogleIdToken.Payload googleUser = googleIdToken.getPayload();


        Optional<Person> existingUser = personRepository.findByEmail(googleUser.getEmail());
        Person person;
        if (existingUser.isEmpty()) {
            person = new Person(
                    googleUser.getSubject(),
                    googleUser.get("given_name").toString(),
                    googleUser.get("family_name").toString(),
                    googleUser.getEmail()
            );
            personRepository.save(person);
        }else {
            person = existingUser.get();
        }

        String token = jwtService.generateToken(googleUser.getEmail());
        UserTokenDTO request = new UserTokenDTO(token, person.getId());
        return ResponseEntity.ok(request);
    }


}
