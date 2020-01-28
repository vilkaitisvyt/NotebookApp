package lt.vilkaitisvyt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.vilkaitisvyt.Model.MyUser;

@Repository
public interface MyUserRepository extends JpaRepository <MyUser, Long> {
	
	MyUser findByUsername(String username);

}
