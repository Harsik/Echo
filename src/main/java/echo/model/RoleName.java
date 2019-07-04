package echo.model;

// enum에 대해서 잊어먹었으니 다시 적음 
// 간략하게 적으면 class 보다 확장된 기능을 가지고 있는 class라고 볼 수 있다.
// 클래스처럼 보이게 하는 상수
// 서로 관련 있는 상수들을 모아 심볼릭한 명칭의 집합으로 정의한 것
// Enum 클래스형을 기반으로 한 클래스형 선언
// 새로운 열거형을 선언하면, 내부적으로 Enum 클래스형 기반의 새로운 클래스형이 만들어짐.
public enum RoleName {
    ROLE_ADMIN, ROLE_MANAGER, ROLE_USER
}