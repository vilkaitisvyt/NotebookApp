package lt.vilkaitisvyt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.vilkaitisvyt.Model.Entry;

@Repository
public interface EntryRepository extends JpaRepository <Entry, Long> {

}
