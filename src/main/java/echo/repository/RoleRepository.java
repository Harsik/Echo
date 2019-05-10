package echo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import echo.model.Role;
import echo.model.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(RoleName roleName);
}