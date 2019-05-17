package echo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
// import lombok.Builder;
// import lombok.AccessLevel;
// import lombok.NoArgsConstructor;
// import lombok.AllArgsConstructor;

@Getter
@Setter
// @Builder
// @AllArgsConstructor
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;
}