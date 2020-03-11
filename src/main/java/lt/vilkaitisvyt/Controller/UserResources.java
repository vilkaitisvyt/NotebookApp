package lt.vilkaitisvyt.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import lt.vilkaitisvyt.Model.AuthenticationRequest;
import lt.vilkaitisvyt.Model.AuthenticationResponse;
import lt.vilkaitisvyt.Model.MyUser;
import lt.vilkaitisvyt.Model.RegistrationRequest;
import lt.vilkaitisvyt.Model.RegistrationResponse;
import lt.vilkaitisvyt.Service.MyUserDetailsService;
import lt.vilkaitisvyt.Util.JwtUtil;

@Controller
public class UserResources {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	
	@GetMapping("/login")
	public String loginPage() {
		return "LoginForm";
	}
	
	@GetMapping("/registrationPage")
	public String registrationPage() {
		return "RegistrationForm";
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(RegistrationRequest registrationRequest) throws Exception {
		MyUser user = userDetailsService.createUser(registrationRequest);
		if (user != null) {
			return ResponseEntity.ok(new RegistrationResponse(""));
		} else {
			return ResponseEntity.ok(new RegistrationResponse("Username already exists"));
		}
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
			
		} catch(BadCredentialsException e) {
			System.out.println("Bad credentials");
			final String jwt = "";
			String message = "Incorrect username or password";
			
			return ResponseEntity.ok(new AuthenticationResponse(jwt, message));
		}
		
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		String message = "Authentication successful";
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt, message));
	}
}
