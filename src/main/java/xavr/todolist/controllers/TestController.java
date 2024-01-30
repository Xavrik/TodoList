package xavr.todolist.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	
	  @GetMapping(path = "/test")
	    public ResponseEntity<String> getTest() {
		  System.out.println("start mvc test");
	        return new ResponseEntity<>("This is first Test MVC", HttpStatus.OK);
	       
	    }
	  @GetMapping(path = "/test1")
	    public ResponseEntity<String> getTest1() {
		  
		  System.out.println("start mvc test1");
	        return new ResponseEntity<>("This is second Test MVC", HttpStatus.OK);
	       
	    }
	  


}