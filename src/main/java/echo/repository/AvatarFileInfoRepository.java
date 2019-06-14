package echo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import echo.model.AvatarFileInfo;

public interface AvatarFileInfoRepository extends JpaRepository<AvatarFileInfo, String> {
    Optional<AvatarFileInfo> findByProfileId(Long ProfileId);
    //  Set<FileInfo>  findByAccountId(Long AccountId);

    Boolean existsByProfileId(Long ProfileId);
}