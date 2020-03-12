package lt.vilkaitisvyt.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lt.vilkaitisvyt.Service.EntryService;

@Controller
public class ViewResources {
	
	@Autowired
	private EntryService entryService;

	
	@GetMapping("")
	public String home() {
		return "index";
	}
	
	@GetMapping("/entries")
	public String entries(HttpServletRequest request, Model model) {
		model.addAttribute("entries", entryService.findAllUserEntries(request));
		return "entries";
	}	

	@PostMapping("/entryNew")
	public String add(@RequestParam String newEntry, HttpServletRequest request, Model model) {
		entryService.saveUserEntry(newEntry, request);		
		model.addAttribute("entries", entryService.findAllUserEntries(request));
		return "entries";
	}	

	@DeleteMapping("/entryDelete")
	public String delete(Long id, HttpServletRequest request, Model model) {
		entryService.deleteEntryById(id);
		model.addAttribute("entries", entryService.findAllUserEntries(request));
		return "entries";
	}

	@PutMapping("/entryUpdate")
	public String update(Long id, String updatedEntry, HttpServletRequest request, Model model) {
		entryService.updateEntryById(id, updatedEntry);
		model.addAttribute("entries", entryService.findAllUserEntries(request));
		return "entries";
	}
}
