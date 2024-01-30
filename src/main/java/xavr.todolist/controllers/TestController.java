
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	
	  @GetMapping(path = "/test")
	    public ResponseEntity<String> getTest() {
		  System.out.println("start mvc test");
	        return new ResponseEntity<>("This is first Test MVC", HttpStatus.OK);
	    }
	  


}