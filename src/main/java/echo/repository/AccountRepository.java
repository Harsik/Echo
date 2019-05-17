package echo.repository;

import java.util.List;
import java.util.Optional;

import org.jboss.logging.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import echo.model.Account;

public interface AccountRepository extends JpaRepository<Account,Long>{

    Optional<Account> findByEmail(String email);

    Boolean existsByEmail(String email);
}