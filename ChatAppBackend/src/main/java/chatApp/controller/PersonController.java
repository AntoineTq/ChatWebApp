package chatApp.controller;

import chatApp.service.DiscussionService;
import chatApp.model.Person;
import chatApp.repository.PersonRepository;
import chatApp.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {


    private final PersonService personService;
    private final DiscussionService discussionService;
    private final PersonRepository personRepository;

    public PersonController(PersonService personService, DiscussionService discussionService, PersonRepository personRepository) {
        this.personService = personService;
        this.discussionService = discussionService;
        this.personRepository = personRepository;
    }



    @GetMapping("/protected")
    public ResponseEntity<Person> getProtected(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Return a Person instead
        if (principal instanceof Person) {
            return ResponseEntity.ok((Person) principal);
        }
        return (ResponseEntity<Person>) ResponseEntity.status(HttpStatus.UNAUTHORIZED);
    }
}
