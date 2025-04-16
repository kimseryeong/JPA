# @Entity

JPA 사용해서 테이블과 매핑할 클래스는 필수 
<br>
@Entity 가 붙은 클래스는 JPA 가 관리하는 것

### 📌 Entity 속성 정리
|속성|기능|기본값|
|--|--|--|
|`name`|JPA에서 사용할 엔티티 이름을 지정 <br> 보통 기본값인 클래스 이름을 사용 <br> 이름을 지정해서 충돌 방지|설정하지 않으면 클래스 이름 그대로 사용|

### 📌 주의 사항
- 기본 생성자는 필수 (파라미터가 없는 public, protected)
- final 클래스, enum, interface, inner 클래스는 사용 불가
- 저장할 필드에 final 사용 금지
