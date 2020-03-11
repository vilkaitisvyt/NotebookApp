package lt.vilkaitisvyt.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.vilkaitisvyt.Model.Entry;
import lt.vilkaitisvyt.Model.MyUser;
import lt.vilkaitisvyt.Repository.EntryRepository;
import lt.vilkaitisvyt.Repository.MyUserRepository;
import lt.vilkaitisvyt.Util.JwtUtil;

@Service
public class EntryService {
	
	@Autowired
	private EntryRepository entryRepository;
	
	@Autowired
	private MyUserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	public List<Entry> findAllUserEntries(HttpServletRequest request) {
		String username = extractUsernameFromRequest(request);
		return	entryRepository.findAll().stream()
				.filter(entry -> entry.getUser().getUsername().equalsIgnoreCase(username))
				.collect(Collectors.toList());
	}
	
	public void saveUserEntry(String newEntry, HttpServletRequest request) {
		String username = extractUsernameFromRequest(request);
		MyUser user = userRepository.findByUsername(username);
		
		if (user != null ) {
			Entry entry = new Entry(newEntry, user);
			entryRepository.save(entry);
		}		
	}
	
	public void deleteEntryById(Long id) {
		entryRepository.deleteById(id);
	}
	
	public void updateEntryById(Long id, String updatedEntry) {
		Entry entry = entryRepository.findById(id).get();
		entry.setEntryItem(updatedEntry);
		entryRepository.save(entry);
	}
	
	public String extractUsernameFromRequest(HttpServletRequest request) {
		final String header = request.getHeader("Authorization");
		
		String username = null;
		String jwt = null;
		
		if (header != null && header.startsWith("Bearer ")) {
			jwt = header.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}
		return username;
	}

}
