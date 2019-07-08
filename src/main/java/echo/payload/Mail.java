package echo.payload;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// @Data // @ToString, @EqualsAndHashCode, @Getter / @Setter and @RequiredArgsConstructor
@Getter
@Setter
@Builder
// @NoArgsConstructor매개 변수없이 생성자를 생성합니다. 
// 하지 않는 한이 (때문에 최종 필드로) 할 수없는 경우, 컴파일러 오류 대신 발생합니다, 
// @NoArgsConstructor(force = true)사용, 모든 최종 필드는 초기화됩니다
// @RequiredArgsConstructor특수 처리가 필요한 각 필드에 대해 1 개의 매개 변수로 생성자를 생성합니다
// @AllArgsConstructor클래스의 각 필드에 대해 1 개의 매개 변수로 생성자를 생성합니다. 
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mail {

    @NotBlank
    private String sender;

    @NotBlank
    private String receiver;

    @NotBlank
    private String subject;
    
    private String text;

}