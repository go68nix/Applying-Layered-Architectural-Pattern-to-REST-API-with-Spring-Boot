package de.tum.in.ase.eist.service;

import de.tum.in.ase.eist.model.Person;
import de.tum.in.ase.eist.util.PersonSortingOptions;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Collections.*;

@Service
public class PersonService {
  	// do not change this
    private final List<Person> persons;

    public PersonService() {
        this.persons = new ArrayList<>();
    }

    public Person savePerson(Person person) {
        var optionalPerson = persons.stream().filter(existingPerson -> existingPerson.getId().equals(person.getId())).findFirst();
        if (optionalPerson.isEmpty()) {
            person.setId(UUID.randomUUID());
            persons.add(person);
            return person;
        } else {
            var existingPerson = optionalPerson.get();
            existingPerson.setFirstName(person.getFirstName());
            existingPerson.setLastName(person.getLastName());
            existingPerson.setBirthday(person.getBirthday());
            return existingPerson;
        }
    }

    public void deletePerson(UUID personId) {
        this.persons.removeIf(person -> person.getId().equals(personId));
    }

    public List<Person> getAllPersons(PersonSortingOptions sortingOptions) {
        // TODO Part 3: Add sorting here
        if (sortingOptions == null) {
            return new ArrayList<>(this.persons);
        }
        var sortedList = new ArrayList<>(this.persons);
        sortedList.sort((p1, p2) -> {
            Person customer1;
            Person customer2;
            if (sortingOptions.getSortingOrder() == PersonSortingOptions.SortingOrder.ASCENDING) {
                customer1 = p1;
                customer2 = p2;
            } else {
                customer1 = p2;
                customer2 = p1;
            }

            return switch (sortingOptions.getSortField()) {
                case ID -> customer1.getId().compareTo(customer2.getId());
                case FIRST_NAME -> customer1.getFirstName().compareTo(customer2.getFirstName());
                case LAST_NAME -> customer1.getLastName().compareTo(customer2.getLastName());
                case BIRTHDAY -> customer1.getBirthday().compareTo(customer2.getBirthday());
            };
        });
        return sortedList;
    }
}
