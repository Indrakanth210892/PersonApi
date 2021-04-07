package com.mars.person.commandline;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.mars.person.model.Person;
import com.mars.person.service.IPersonService;

@Service
public class PersonCommandLineRunner implements CommandLineRunner {

	final Scanner sc=new Scanner(System.in);
	private Integer id;
	private String firstName;
	private String surname;
	private final static String INVALID="Invalid input please try again.";
	@Autowired
	private IPersonService personService;

	@Override
	public void run(String... args) throws Exception {

		int option;
		do {
			Thread.sleep(2000); //Intentional delay added for convenience of user to view results 
			option=0;
			System.out.println("\r\nPERSON API available operations. Please choose any of the numbers from 1-6:");
			System.out.println("1. Add Person (id, firstName, surname)");
			System.out.println("2. Edit Person (firstName, surname)");
			System.out.println("3. Delete Person (id)");
			System.out.println("4. Count Number of People");
			System.out.println("5. List all Person records");
			System.out.println("6. Exit");
			System.out.println("7. Exit Command Line Window (Use this to run tests or to use only REST services)");
			System.out.print("\r\nPlease Enter your option (1-7): ");
			try{
				option=Integer.parseInt(sc.next());
				switch(option) {

				case 1: addPerson();break;
				case 2: editPerson();break;
				case 3: deletePerson();break;
				case 4: countPeople();break;
				case 5: listPeople();break;
				case 6: System.out.println("Thank you for using our API");System.exit(0);
				case 7: System.out.println("\r\nExiting Command Line Window");break;
				default: System.out.println(INVALID);
				}
			}catch (Exception e) {
				option=0;
				System.out.println(INVALID);
			}

		}while(option!=6&&option!=7);

	}



	//OPERATION 1 ADD
	private void addPerson() {

		System.out.println("Welcome to 1. Add operation:\r\n");
		System.out.print("Please enter person Id: ");

		try{
			id=Integer.parseInt(sc.next());
			System.out.print("Please enter person's firstName: ");
			firstName=sc.next();
			System.out.print("Please enter person's surname: ");
			surname=sc.next();
			boolean addStatus=personService.addPerson(id, firstName, surname);
			if(addStatus)
				System.out.println("Person record added successfully.");
			else
				System.out.println("Error: Person record could not be added because of either missing required parameters or a person with this record already exists. \r\nPlease make sure you have entered all the inputs correctly.");

		}catch (Exception e) {
			System.out.println(INVALID);
		}
		
	}


	//OPERATION 2 EDIT
	private void editPerson() {
		System.out.println("Welcome to 2. Edit operation:\r\n");

		System.out.print("Please enter person's firstName: ");
		firstName=sc.next();
		System.out.print("Please enter person's surname: ");
		surname=sc.next();
		Person editedPerson=personService.editPerson(firstName, surname);
		if(editedPerson!=null)
			System.out.println("Person record updated successfully.");
		else
			System.out.println("Person record could not be updated because of missing required parameters. "
					+ "Please make sure you have entered all the inputs correctly.");
	}


	//OPERATION 3 DELETE
	private void deletePerson() {
		System.out.println("Welcome to 3. Delete operation:\r\n");
		System.out.print("Please enter person Id: ");

		try{
			id=Integer.parseInt(sc.next());
			boolean deleteStatus=personService.deletePerson(id);
			if(deleteStatus)
				System.out.println("Person record with id: "+id+" was deleted successfully");
			else
				System.out.println("No Person record with id: "+id+" was found for deletion.");

		}catch (Exception e) {
			System.out.println(INVALID);
		}
	}

	//OPERATION 4 COUNT PERSON RECORDS
	private void countPeople() {
		System.out.println("Welcome to 4. Count Number of People:\r\n");
		System.out.println("Number of Person records are: "+personService.countPersons());
	}

	//OPERATION 5 LIST ALL
	private void listPeople() {
		System.out.println("Welcome to 5. List all Person records:\r\n");

		for(Person person:personService.allPeople())
			System.out.println(person);

	}

}
