# Database Schema 자동생성 DDL

JPA 는 데이터베이스 스키마 자동 생성 기능을 제공
<br>
클래스의 매핑 정보와 데이터베이스 방언을 사용해서 스키마 생성

### 📌 Persistence.xml 에 속성 추가

애플리케이션 실행 시점에 데이터베이스에 테이블 자동 생성
```
<property name="hibernate.hbm2ddl.auto" value="create"/>

```

### 📌 hibernate.hbm2ddl.auto 속성
|옵션|설명||
|---|---|---|
|`create`|기존 테이블을 삭제하고 새로 생성. |`DROP + CREATE`|
|`create-drop`|create 속성에 추가로 애플리케이션 종료 시 생성한 DDL 제거|`DROP + CREATE + DROP`|
|`update`|데이터베이스 테이블과 엔티티 매핑정보를 비교해서 변경사항만 수정|
|`validate`|데이터베이스 테이블과 엔티티 매핑정보를 비교해서 차이가 있으면 경고 남기고 애플리케이션을 실행하지 않음 (DDL 수정하지 않음)|
|`none`|자동 생성 기능 사용하지 않기 위해 작성하는 유효하지 않은 옵션 값|

### ⚠️ HBM2DDL 주의
- 스키마 자동 생성 기능은 운영 환경에서 사용할 만큼 완벽하지 않으므로 개발환경에서 사용하거나 매핑 참고용으로만 사용하기
- 특히 create, create-drop, update 와 같은 DDL 수정 옵션은 절대 사용 X
- 추천 전략
    - 개발 초기 단계 -> `create` or `update`
    - 초기화 상태로 자동화된 테스트를 진행하는 개발자 환경과 CI 서버 -> `create` or `create-drop`
    - 테스트 서버 -> `update` or `validate`
    - 스테이징 및 운영 서버 -> `validate` or `none`


    