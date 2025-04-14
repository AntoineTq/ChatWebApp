package chatApp.service;

import chatApp.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }





//    public PersonDTO createPerson(PersonDTO personDTO) {
//        Person newPerson = new Person(personDTO.getFirstName(), personDTO.getLastName());
//        personRepository.save(newPerson);
//        return PersonMapper.toDto(newPerson);
//    }
//
//    public void createDiscussion(Long personId, Long friendId){
//        Discussion discussion = new Discussion();
//        Optional<Person> person = personRepository.findById(personId);
//        Optional<Person> friend = personRepository.findById(friendId);
//        if (person.isPresent() && friend.isPresent()){
//            person.get().getDiscussions().add(discussion);
//            friend.get().getDiscussions().add(discussion);
//            personRepository.save(person.get());
//            personRepository.save(friend.get());
//        }
//    }


}
