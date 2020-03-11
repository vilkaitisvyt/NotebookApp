package lt.vilkaitisvyt.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Entry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String entryItem;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(referencedColumnName = "id")
	private MyUser user;
	
	
	public Entry() {
		
	}

	public Entry(String entryItem, MyUser user) {
		super();
		this.entryItem = entryItem;
		this.user = user;
	}

	public String getEntryItem() {
		return entryItem;
	}

	public void setEntryItem(String entryItem) {
		this.entryItem = entryItem;
	}

	public Long getId() {
		return id;
	}

	public MyUser getUser() {
		return user;
	}

	public void setUser(MyUser user) {
		this.user = user;
	}
}
