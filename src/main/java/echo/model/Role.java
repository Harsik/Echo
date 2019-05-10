package echo.model;

import org.hibernate.annotations.NaturalId;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 데이터베이스 테이블의 기본 키Primary key를 선정할 때 크게 2가지 선택지가 있는데 하나는 자연 키Natural key이고 또 하나는 대체 키Surrogate key이다.
    // 자연 키는 전화번호, 이메일처럼 비즈니스적으로 의미 있는 키를 말하며, 대체 키는 비즈니스와 상관없이 임의로 만들어진 키를 말한다.
    @Enumerated(EnumType.STRING) // 자바 enum 타입을 엔티티 클래스의 속성으로 사용할 수 있다.
    @NaturalId
    @Column(length = 60)
    private RoleName name;
    
}