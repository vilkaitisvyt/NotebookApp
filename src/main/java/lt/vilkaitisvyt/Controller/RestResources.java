package lt.vilkaitisvyt.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lt.vilkaitisvyt.Model.AuthenticationRequest;
import lt.vilkaitisvyt.Model.AuthenticationResponse;
import lt.vilkaitisvyt.Model.MyUser;
import lt.vilkaitisvyt.Model.RegistrationRequest;
import lt.vilkaitisvyt.Service.MyUserDetailsService;
import lt.vilkaitisvyt.Util.JwtUtil;

@RestController
public class RestResources {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	
	@GetMapping({"/hello"})
	public String hello() {
		return "Hello World";
	}
	
	@PostMapping({"/register"})
	public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) throws Exception {
		MyUser user = userDetailsService.createUser(registrationRequest);
		if (user != null) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return ResponseEntity.badRequest().body("Duplicate username");
		}
	}
	
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}catch(BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
			}
		
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
