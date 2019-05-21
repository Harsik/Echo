package echo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import echo.model.Profile;
import echo.model.DBFile;

public interface DBFileRepository extends JpaRepository<DBFile, String> {

}