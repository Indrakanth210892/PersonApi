package com.mars.person.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mars.person.model.Person;
import com.mars.person.person.repository.IPersonRepository;
import com.mars.person.service.IPersonService;

@Service
public class PersonServiceImpl implements IPersonService{
	
	 private static Logger logger=LogManager.getLogger(PersonServiceImpl.class);

	@Autowired
	private IPersonRepository repository;

	@Override
	public Boolean addPerson(Integer id, String firstName, String surname) {
		Optional<Person> fetchedRecord;
		if(StringUtils.isBlank(firstName)||StringUtils.isBlank(surname)||id==null)
			return false;
		else{
			fetchedRecord=repository.findById(id);
			if(fetchedRecord.isPresent()) //record already exists
				return false;

			repository.save(new Person(id,firstName,surname));}
		return true;
	}

	@Override
	public Person editPerson(String firstName, String surname) {

		List<Person> list=repository.findByFirstNameOrSurname(firstName, surname);
		if(list.isEmpty())
			return null;

		Person person=list.get(0);
		person.setFirstName(firstName);
		person.setSurname(surname);
		return repository.save(person);
	}

	@Override
	public Boolean deletePerson(Integer id) {
		if(id==null)
			return false;
		try {
		repository.deleteById(id);
		return true;
		}catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Integer countPersons() {

		return allPeople().size();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Person> allPeople() {

		return repository.findAll();
	}


}
