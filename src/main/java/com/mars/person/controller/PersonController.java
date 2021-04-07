package com.mars.person.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mars.person.model.Person;
import com.mars.person.service.IPersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Person Rest Controller",value = "Swagger2PersonRestController", description = "REST APIs related to Person Records!!!!")
@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private IPersonService personService;

	
	@ApiOperation(value = "Default message when no operation is selected", response = String.class, tags = "Read Only Queries")
	@GetMapping("")
	public String home() {
		return "Person Controller Home";
	}

	@ApiOperation(value = "Inserts a person record into database", response = String.class)
	@PostMapping("/add")
	public ResponseEntity<String> addPerson(@RequestBody Person person) {


		if(person!=null&&personService.addPerson(person.getId(),person.getFirstName(),person.getSurname()))
			return new ResponseEntity<>("Person record added successfully.",HttpStatus.CREATED);
		else
			return new ResponseEntity<>("Error: Person recordcould not be added because of missing required parameters or a person with this record already exists. "
					+ "Please make sure you have entered all the inputs correctly.",HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "Updates a person record in database if exists", response = String.class)
	@PutMapping("/edit")
	public ResponseEntity<String> editPerson(@RequestBody Person person) {


		if(personService.editPerson(person.getFirstName(), person.getSurname())!=null)
			return new ResponseEntity<>("Person record updated successfully.",HttpStatus.CREATED);
		else
			return new ResponseEntity<>("Person record could not be updated because of missing required parameters. "
					+ "Please make sure you have entered all the inputs correctly.",HttpStatus.BAD_REQUEST);
	}

	//Please note in Real time we will not use delete operations using pathvariables due to security concerns
	//instead we shall embed the id field in the body with proper encryption
	@ApiOperation(value = "Deletes a person record with given id if exists", response = String.class)
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deletePerson(@PathVariable Integer id) {

		if(personService.deletePerson(id))
			return new ResponseEntity<>("Person record with id: "+id+" was deleted successfully.",HttpStatus.OK);
		else
			return new ResponseEntity<>("No Person record with id: "+id+" was found for deletion.",HttpStatus.BAD_REQUEST);
	}

	
	@ApiOperation(value = "Returns the count of number of person records in db", response = Integer.class, tags = "Read Only Queries")
	@GetMapping("/count")
	public ResponseEntity<String> countPersons() {
		return new ResponseEntity<>("Number of Person records are: "+personService.countPersons(),HttpStatus.OK);

	}

	
	@ApiOperation(value = "Lists out all the available person records in db", response = List.class, tags = "Read Only Queries")
	@GetMapping("/list")
	public ResponseEntity<List<Person>> allPeople() {
		return new ResponseEntity<>(personService.allPeople(),HttpStatus.OK);

	}
}