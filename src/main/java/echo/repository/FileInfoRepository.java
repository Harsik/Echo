package echo.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import echo.model.Account;
import echo.model.FileInfo;;

public interface FileInfoRepository extends JpaRepository<FileInfo, String> {
    Optional<FileInfo> findByAccountId(Long AccountId);
    //  Set<FileInfo>  findByAccountId(Long AccountId);

    Boolean existsByAccountId(Long AccountId);
}