package com.mars.person.person.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mars.person.model.Person;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Integer>{

	public List<Person> findByFirstNameOrSurname(String firstName,String surname);
}
