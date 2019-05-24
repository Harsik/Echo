package echo.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import echo.model.Account;
import echo.model.AvatarFileInfo;
import echo.model.FileInfo;;

public interface AvatarFileInfoRepository extends JpaRepository<AvatarFileInfo, String> {
    Optional<AvatarFileInfo> findByProfileId(Long ProfileId);
    //  Set<FileInfo>  findByAccountId(Long AccountId);

    Boolean existsByProfileId(Long ProfileId);
}