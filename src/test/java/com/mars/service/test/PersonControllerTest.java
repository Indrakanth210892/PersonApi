package com.mars.service.test;



import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mars.person.controller.PersonController;
import com.mars.person.model.Person;
import com.mars.person.service.impl.PersonServiceImpl;




@RunWith(SpringRunner.class)
@WebMvcTest(value = PersonController.class)
public class PersonControllerTest{
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PersonServiceImpl personService;

	Person mockPerson = new Person(122, "Spring", "Boot");

	String examplePersonJson = "{\r\n" + 
			"        \"id\": 143,\r\n" + 
			"        \"firstName\": \"Indra\",\r\n" + 
			"        \"surname\": \"Kanth\"\r\n" + 
			"    }";

	@Test
	public void addPersonTestSuccess() throws Exception {
		
		String samplePerson=" {\r\n" + 
				"        \"id\": 190,\r\n" + 
				"        \"firstName\": \"Mars\",\r\n" + 
				"        \"surname\": \"India\"\r\n" + 
				"    }";

		Mockito.when(
				personService.addPerson(190, "Mars", "India")).thenReturn(true);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/person/add")
				.accept(MediaType.APPLICATION_JSON)
				.content(samplePerson)
				.contentType(MediaType.APPLICATION_JSON);;

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "Person record added successfully.";
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

		assertEquals(expected,result.getResponse()
				.getContentAsString());
		
	}
	
	@Test
	public void addPersonTestFailure() throws Exception {
		
		String samplePerson=" {\r\n" + 
				"        \"id\": 255,\r\n" + 
				"        \"firstName\": \"Dummy\",\r\n" + 
				"        \"surname\": \"Person\"\r\n" + 
				"    }";

		Mockito.when(
				personService.addPerson(any(), any(), any())).thenReturn(false);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/person/add")
				.accept(MediaType.APPLICATION_JSON)
				.content(samplePerson)
				.contentType(MediaType.APPLICATION_JSON);;

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "Error: Person recordcould not be added because of missing required parameters or a person with this record already exists. Please make sure you have entered all the inputs correctly.";
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

		assertEquals(expected,result.getResponse()
				.getContentAsString());
		
	}
	
	@Test
	public void countTest() throws Exception {

		Mockito.when(
				personService.countPersons()).thenReturn(2);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/person/count").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "Number of Person records are: 2";
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		assertEquals(expected,result.getResponse()
				.getContentAsString());
	}
	
	@Test
	public void listAllTest() throws Exception {
		
		Person person1=new Person(133, "Priya", "Darshini");
		Person person2=new Person(244, "Rohini", "Madepalli");
		Person person3=new Person(256, "Srikanth", "S");
		List<Person> list=List.of(person1,person2,person3);

		Mockito.when(
				personService.allPeople()).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/person/list").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "[{\"id\":133,\"firstName\":\"Priya\",\"surname\":\"Darshini\"},{\"id\":244,\"firstName\":\"Rohini\",\"surname\":\"Madepalli\"},{\"id\":256,\"firstName\":\"Srikanth\",\"surname\":\"S\"}]";

		assertEquals(expected,result.getResponse()
				.getContentAsString());
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	

	
	@Test
	public void editPersonTestFailure() throws Exception {
		
		String samplePerson=" {\r\n" + 
				"        \"firstName\": \"DOESNT\",\r\n" + 
				"        \"surname\": \"EXIST\"\r\n" + 
				"    }";

		Mockito.when(
				personService.editPerson("DOESNT", "EXIST")).thenReturn(null);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
				"/person/edit")
				.accept(MediaType.APPLICATION_JSON)
				.content(samplePerson)
				.contentType(MediaType.APPLICATION_JSON);;

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "Person record could not be updated because of missing required parameters. Please make sure you have entered all the inputs correctly.";
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

		assertEquals(expected,result.getResponse()
				.getContentAsString());
		
	}
	
	@Test
	public void editPersonTestSuccess() throws Exception {
		
		String samplePerson=" {\r\n" + 
				"        \"firstName\": \"Priyaa\",\r\n" + 
				"        \"surname\": \"Darshini\"\r\n" + 
				"    }";

		Mockito.when(
				personService.editPerson("Priyaa", "Darshini")).thenReturn(new Person(133,"Priyaa", "Darshini"));
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
				"/person/edit")
				.accept(MediaType.APPLICATION_JSON)
				.content(samplePerson)
				.contentType(MediaType.APPLICATION_JSON);;

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "Person record updated successfully.";
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

		assertEquals(expected,result.getResponse()
				.getContentAsString());
		
	}
	
	@Test
	public void deletePersonTestSuccess() throws Exception {
		
		

		Mockito.when(
				personService.deletePerson(255)).thenReturn(true);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
				"/person/delete/255")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);;

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "Person record with id: 255 was deleted successfully.";
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

		assertEquals(expected,result.getResponse()
				.getContentAsString());
		
	}
	
	@Test
	public void deletePersonTestFailure() throws Exception {
		
		

		Mockito.when(
				personService.deletePerson(99999)).thenReturn(false);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
				"/person/delete/99999")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);;

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "No Person record with id: 99999 was found for deletion.";
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());

		assertEquals(expected,result.getResponse()
				.getContentAsString());
		
	}
	
	@Test
	public void PersonControllerStringTest() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/person")
				.accept(MediaType.APPLICATION_JSON)
				;

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "Person Controller Home";
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

		assertEquals(expected,result.getResponse()
				.getContentAsString());
		
	}
}
