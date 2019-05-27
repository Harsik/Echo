package echo.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import echo.model.Account;
import echo.model.FileInfo;;

public interface FileInfoRepository extends JpaRepository<FileInfo, String> {
    Optional<FileInfo> findByName(String name);
    //  Set<FileInfo>  findByAccountId(Long AccountId);

    Boolean existsByName(String name);
}