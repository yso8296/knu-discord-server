# 🌟2023 대구를 빛내는 해커톤
![제목 없음-1](https://github.com/bayy1216/Beside-App/assets/80496838/63db6f3c-a6d6-4583-b8d0-1af1c24163e2)

<br>

## 팀명

비사이드(Be:SIDE)

<br>

## 제출 타입 및 주제

[E타입: 청년을 위한 SW개발] - 사회초년생들이 사회를 살아가며 꼭 필요하지만 학교에서는 알려주지 않는 지식을 쉽게 전달해주는 서비스를 만들어 보자

<br>

## 프로젝트 한 줄 설명

사회 초년생들의 사회지식 부족 문제를 카드 뉴스와 퀴즈 시스템으로 해결

<br>

## 프로젝트에 활용된 기술



### Android
- jetpack compose + xml 혼용하여 구글 권장 앱 아키텍처 적용
  
|     Layer     | Description |
| ------------- | ------------- |
| di | dagger-hilt를 사용하여 의존성 주입 |
| repository | persentaion-layer에서 필요한 모든 데이터는 repository를 통하여 전달 |
| remoteDatasource | okhttp3-retrofit2을 사용하여 api 연결 |
| views | single-activity를 사용, navigation을 통해 fragment를 교체하도록 구성 |
| viewModel | repository에서 가져온 데이터를 livedata, flow, state로 데이터 상태 구성 |

<br>

**페이지 구조도**

![스크린샷 2023-11-13 오후 5 50 49](https://github.com/bayy1216/Beside-App/assets/80496838/99e3c8ea-0a5b-48d0-9b91-cded64554cd3)

<br>

### Server

- 스프링부트 프레임워크 사용, JPA + JWT
- 데이터베이스 : mysql 사용
  
**ERD-Diagram**
![Untitled](https://github.com/bayy1216/Beside-App/assets/78216059/2335c551-578d-486f-8025-2195e8acdae6)

JPA(Java Persistence API)를 활용한 데이터베이스 설계에서, 사용자(Users)와 퀴즈(Quiz), 필기노트(summary), 카드뉴스(news)를 중심으로 하는 네 개의 주요 테이블로 이루어져 있다. 각 테이블은 다양한 정보를 담고 있으며, 사용자는 학교 정보와 관심 주제에 대한 정보를 가지고 있다. 퀴즈 테이블은 문제 번호를 중심으로 다양한 퀴즈 정보를 저장하고 있다. 뿐만 아니라 필기노트와 카드뉴스도 별도의 내용 테이블에 저장되어 있다. 

테이블 간의 다대다(N:M) 관계를 표현하기 위해 각각의 테이블에 매핑 테이블을 도입했다.  매핑 테이블은 각 테이블 간의 관계를 정의하고, 복잡한 관계를 쉽게 처리할 수 있도록 도와준다.

이러한 설계는 데이터의 일관성을 유지하면서 다양한 관계를 효과적으로 처리할 수 있는 구조를 제공한다. 또한 매핑 테이블은 데이터베이스의 성능을 향상 시키고 쿼리를 간편하게 작성할 수 있도록 도와주며, 데이터베이스와 애플리케이션 간의 일관성을 유지하면서 유연하고 확장 가능한 시스템을 만들 수 있도록 도와준다.

<br>

**인증 로직 Flow Chart**
![jwt](https://github.com/bayy1216/Beside-App/assets/78216059/3cbae289-30c0-4c1c-972a-3b36038063c9)

위 다이어 그램은 JWT가 유효한 토큰 인지를 검사하는 로직을 간략히 나타낸 플로우 차트이다. 

로그인이 필요한 요청은 HTTP 헤더에 AccessToken을 담은 채로 서버에 전송된다. 이후 이것이 유효한 요청이 맞는 지를 검사하기 위해 아래 3가지 로직을 거친다.

1. JWT Token 형식에 맞는지 검사:
    
    받은 JwtToken이 Bearer 시작하는 token이 맞는지, null이거나 비어있는 값은 아닌 지 등 Jwt Token형식이 맞는 지를 검사한다.
    
2. Token의 용도에 맞는지 검사:
    
    서버가 발급하는 토큰은 Access Token과 Refresh Token이 있다. Refresh Token의 경우, Access token의 유효 기간이 다되었을 때, 토큰을 재발급 받기 위해 사용되는데, Refresh Token을 재발급이 아닌 다른 용도로 사용하거나, Access Token을 토큰 재발급을 위해 사용하는 등, 토큰 용도에 맞지 않는 용도를 검사하고 걸러낸다. 
    
3. 로그아웃 처리된 Token인지 검사:
    
    JWT Token은 Cookie-Session식의 로그인 인증과는 달리 유저의 인증 정보를 클라이언트가 보관한다. JWT는 일종의 서버에 대한 입장권으로 유저의 정보와 권한 정보가 저장되어 있다. Cookie-Session방식에서는 유저가 로그 아웃 할 경우,  서버의 Session정보를 삭제하면 되지만, JWT는 유저가 보관하기 때문에 서버에서 삭제가 불가능하다. 유저는 서비스 사용이 끝났지만 토큰을 탈취한 해커가 해당 토큰을 사용하여 서비스를 이용하는 것을 막기 위해 블랙리스트를 도입하였다. 유저가 로그 아웃을 진행할 때, Redis에 해당 정보를 남은 유효 기간 만큼 저장하고 요청이 들어올 때마다 이것이 블랙리스트에 등록된 토큰이 아닌지 검사한다.
<br>

## 시연영상
https://youtu.be/rqKCiqaVCAE

<br>

## 백엔드 Git Repository
https://github.com/J-MU/Beside-server
