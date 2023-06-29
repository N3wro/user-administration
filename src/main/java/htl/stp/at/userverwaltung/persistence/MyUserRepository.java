package htl.stp.at.userverwaltung.persistence;

import htl.stp.at.userverwaltung.domain.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser,String> {

    Optional<MyUser> findByUsername(String username);

}
