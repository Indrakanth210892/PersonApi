package com.mars.person.service;

import java.util.List;

import com.mars.person.model.Person;

public interface IPersonService {

	 Boolean addPerson(Integer id,String firstName,String surname);
	 Person editPerson(String firstName,String surname);
	 Boolean deletePerson(Integer id);
	 Integer countPersons();
	 List<Person> allPeople();
}
