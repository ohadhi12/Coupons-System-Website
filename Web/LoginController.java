package com.Project3.Web;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Project3.Exceptions.invalidClientTypeException;
import com.Project3.Exceptions.loginFaildException;
import com.Project3.Facade.ClientFacade;
import com.Project3.LoginManager.ClientType;
import com.Project3.LoginManager.LoginManager;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

	@Autowired
	private LoginManager loginManager;
	@Autowired
	private Map<String, Session> sessionsMap;

	@PostMapping("/login/{email}/{password}/{type}")
	public String login(@PathVariable String email, @PathVariable String password, @PathVariable ClientType type) {
		String token = UUID.randomUUID().toString();

		try {
			ClientFacade facade = loginManager.login(email, password, type);
			Session session = new Session(facade, System.currentTimeMillis());
			sessionsMap.put(token, session);
			return token;
		} catch (loginFaildException e) {
			return "Error: " + e.getMessage();
		} catch (invalidClientTypeException e) {
			return "Error: " + e.getMessage();
		}

	}

	@PostMapping("/logout/{token}")
	public void logOut(@PathVariable String token) {
		sessionsMap.remove(token);
	}
}