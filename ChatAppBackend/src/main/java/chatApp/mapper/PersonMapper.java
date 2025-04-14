package chatApp.mapper;

import chatApp.dto.PersonDTO;
import chatApp.model.Person;

public class PersonMapper {

    public static PersonDTO toDto(Person person){
        return new PersonDTO(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getEmail()
        );
    }
}
