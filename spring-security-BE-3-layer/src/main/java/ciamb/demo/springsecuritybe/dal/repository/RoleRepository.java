package ciamb.demo.springsecuritybe.dal.repository;

import ciamb.demo.springsecuritybe.dal.entity.Role;
import ciamb.demo.springsecuritybe.dal.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
