package echo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import echo.model.FileInfo;;

public interface FileInfoRepository extends JpaRepository<FileInfo, String> {
    Optional<FileInfo> findByName(String name);
    Boolean existsByName(String name);
}