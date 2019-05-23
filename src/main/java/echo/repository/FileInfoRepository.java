package echo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import echo.model.FileInfo;;

public interface FileInfoRepository extends JpaRepository<FileInfo, String> {

}