# PrimaryKey Mapping (기본 키 할당)

JPA가 제공하는 데이터베이스 기본 키 생성 전략
<br>
영속성 컨텍스트는 엔티티를 식별자 값으로 구분하므로 엔티티를 영속 상태로 만들기 위해 식별자 값은 필수



## 📌 직접 할당
기본 키를 애플리케이션에서 직접 할당
```
Board board = new Board();
board.setId("id1"); //기본 키 직접 할당
em.persist(board);
```

## 📌 자동 할당 `@GeneratedValue`
대리 키 사용 방식 (데이터베이스 벤더마다 지원 방식 다르기 때문) 

|||
|--|--|
|IDENTITY| 기본 키 생성을 데이터베이스에 위임|
|SEQUENCE| 데이터 베이스 시퀀스를 사용하여 기본 키 할당|
|TABLE| 키 생성 테이블 사용|
|AUTO||

<br>

**사용 코드** <br>

```
private static void logic(EntityManager em){
    Board board = new Board();
    em.persist(board);
    System.out.println("board.id = " + board.getId()); //출력: board.id = 1
}
```

### IDENTITY 전략
> MySQL, PostgreSQL, SQL Server, DB2 
- MySQL의 AUTO_INCREMENT 와 같은 기능 
- 데이터베이스에 INSERT 한 이후 기본 키 값을 조회 
    - -> 엔티티에 식별자 값을 할당하려면 JPA 는 추가적으로 데이터베이스를 조회해야 함

**매핑 코드**
```
@Entity
public class Board{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    ...
}
```

**사용 코드**

```
private static void logic(EntityManager em){
    Board board = new Board();
    em.persist(board);
    System.out.println("board.id = " + board.getId()); //출력: board.id = 1
}
```
출력된 값(1)은 저장 시점에 데이터베이스가 생성한 값을 JPA가 식별자를 조회해서 엔티티의 식별자에 할당

<br>

### SEQUENCE 전략
> Oracle, PostgreSQL, DB2, H2 (시퀀스 지원하는 DB)
- 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트

**매핑 코드**
```
@Entity
public class Board{

    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "BOARD_SEQ_GENERATOR")
    @SequenceGenerator(
        name = "BOARD_SEQ_GENERATOR",
        sequenceName = "BOARD_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 1)
    private Long id;
    ...
}
```

**출력된 값(1) 순서**
1. em.persist(); 호출할 때 먼저 데이터베이스 시퀀스 사용해서 식별자 조회
2. 조회한 식별자를 엔티티에 할당한 후에 엔티티 영속성 컨텍스트에 저장
3. 트랜잭션을 커밋해서 플러시가 일어나면 엔티티 데이터베이스에 저장

<br>

**✔️ `@SequenceGenerator` 속성**

|속성|기능|기본값|
|--|--|--|
|`name`|식별자 생성기 이름|필수|
|`sequenceName`|데이터베이스에 등록되어 있는 시퀀스 이름|hibernate_sequence|
|`initialValue`|DDL 생성 시에만 사용됨. 시퀀스 DDL 생성 시 초기값|1|
|`allocationSize`|시퀀스 한 번 호출에 증가하는 수 (성능 최적화에 사용됨)|50|
|`catalog.schema`|데이터베이스 catalog, schema 이름||


<br>

### TABLE 전략

- 키 생성 전용 테이블을 하나 만들고 여기에 이름과 값으로 사용할 컬럼을 만들어 데이터베이스 시퀀스를 흉내내는 전략


**매핑 코드**
```
@Entity
@TableGenerator(
    name = "BOARD_SEQ_GENERATOR",
    table = "My_SEQ",
    pkColumnValue = "BOARD_SEQ", allocationSize = 1)
public class Board{

    @GeneratedValue(strategy = GenerationType.TABLE,
                        generator = "BOARD_SEQ_GENERATOR")
    private Long id;
    ...
}
```
<br>

**✔️ `@TableGenerator` 속성**

|속성|기능|기본값|
|--|--|--|
|`name`|식별자 생성기 이름|필수|
|`table`|키 생성 테이블명|hibernate_sequence|
|`pkColumnName`|시퀀스 컬럼명|sequence_name|
|`valueColumnName`|시퀀스 값 컬럼명|next_val|
|`pkColumnValue`|키로 사용할 값 이름|엔티티 이름|
|`initialValue`|초기값, 마지막으로 생성된 값이 기준|0|
|`allocationSize`|시퀀스 한 번 호출에 증가하는 수 (성능최적화에 사용)|50|
|`catalog.schema`|데이터베이스 catalog.schema 이름||
|`uniqueConstraints(DDL)`|유니크 제약 조건 지정 가능||


<br>

### AUTO 전략
- 데이터베이스 방언에 따라 IDENTITY, SEQUENCE, TABLE 전략 중 하나를 자동으로 선택

```
@GeneratedValue(strategy = GeneratorType.AUTO)
==
@GeneratedValue
```