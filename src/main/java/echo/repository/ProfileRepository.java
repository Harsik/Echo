package echo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import echo.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile,Long>{

}