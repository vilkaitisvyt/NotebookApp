package lt.vilkaitisvyt.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lt.vilkaitisvyt.Model.MyUser;
import lt.vilkaitisvyt.Model.RegistrationRequest;
import lt.vilkaitisvyt.Repository.MyUserRepository;
import lt.vilkaitisvyt.Util.MyUserDecorator;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private MyUserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUser user = userRepo.findByUsername(username);		
		return new MyUserDecorator(user);
	}
	
	public MyUser createUser(RegistrationRequest registrationRequest) throws Exception {
		String username = registrationRequest.getUsername();
		
		if (!usernameAlreadyExists(registrationRequest.getUsername())) {
			MyUser user = new MyUser(registrationRequest.getUsername(), registrationRequest.getPassword());
			userRepo.save(user);
			return user;
		}
		return null;
	}
	
	public boolean usernameAlreadyExists(String username) {
		List<MyUser> allUsers = userRepo.findAll();
		
		for (MyUser user: allUsers) {
			if(user.getUsername().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}
}
