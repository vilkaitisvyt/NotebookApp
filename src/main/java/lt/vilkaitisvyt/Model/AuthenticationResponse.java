package lt.vilkaitisvyt.Model;

public class AuthenticationResponse {
	
	private String jwt;
	private String message;
	

	public AuthenticationResponse(String jwt, String message) {
		this.jwt = jwt;
		this.message = message;
	}

	public String getJwt() {
		return jwt;
	}

	public String getMessage() {
		return message;
	}	
}
