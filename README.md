# 요구 사항
## 필수 요구 사항
<details>
<summary> CRUD, Directory Structure,  AWS 배포 </summary>
  
    
    1. 아래의 요구사항을 기반으로 Use Case 그려보기
        - 손으로 그려도 됩니다.
        - cf. https://narup.tistory.com/70
    2. 전체 게시글 목록 조회 API
        - 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
        - 작성 날짜 기준 내림차순으로 정렬하기
    3. 게시글 작성 API 
        - 제목, 작성자명, 비밀번호, 작성 내용을 저장하고
        - 저장된 게시글을 Client 로 반환하기
    4. 선택한 게시글 조회 API 
        - 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기 
        (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
    5. 선택한 게시글 수정 API
        - 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
        - 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
    6. 선택한 게시글 삭제 API
        - 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
        - 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기
    
    - Postman을 이용해 HTTP 메서드 요청을 시도해 보세요!
    
    ❓ **Why: 아래 질문을 고민해보세요.**
    
    1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)
    2. 어떤 상황에 어떤 방식의 request를 써야하나요?
    3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
    4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
    5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!
</details>

<details>
  <summary>회원가입 / 로그인</summary>
  
    1. 회원 가입 API
        - username, password를 Client에서 전달받기
        - username은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성되어야 한다.
        - password는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성되어야 한다.
        - DB에 중복된 username이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환하기
        - 참고자료
            1. https://mangkyu.tistory.com/174
            2. [https://ko.wikipedia.org/wiki/정규_표현식](https://ko.wikipedia.org/wiki/%EC%A0%95%EA%B7%9C_%ED%91%9C%ED%98%84%EC%8B%9D)
            3. https://bamdule.tistory.com/35
                
    2. 로그인 API
        - username, password를 Client에서 전달받기
        - DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기
        - 로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급하고, 
        발급한 토큰을 Header에 추가하고 성공했다는 메시지, 상태코드 와 함께 Client에 반환하기
    
    - Postman을 이용해 HTTP 메서드 요청을 시도해 보세요!
    - API 명세서와 ERD 설계 TIP
        - **ERD 설계 →** https://www.erdcloud.com/
        - **API 명세서 작성 툴 →** [https://learnote-dev.com/java/Spring-A-문서-작성하기/](https://learnote-dev.com/java/Spring-A-%EB%AC%B8%EC%84%9C-%EC%9E%91%EC%84%B1%ED%95%98%EA%B8%B0/)
    
    🔥 꼭 직접 API 명세서를  수정해 본 다음에 확인하세요!!
    - API 명세서 예시
        [API 명세서](https://www.notion.so/8b3d59cad1a24f8fbdce5e9f027e0967?pvs=21)
      
    ❓ **Why: 과제 제출시에는 아래 질문을 고민해보고 답변을 함께 제출해주세요.**
    
    1. 처음 설계한 API 명세서에 변경사항이 있었나요? 
    변경 되었다면 어떤 점 때문 일까요? 첫 설계의 중요성에 대해 작성해 주세요!
    2. ERD를 먼저 설계한 후 Entity를 개발했을 때 어떤 점이 도움이 되셨나요?
    3. JWT를 사용하여 인증/인가를 구현 했을 때의 장점은 무엇일까요?
    4. 반대로 JWT를 사용한 인증/인가의 한계점은 무엇일까요?
    5. 만약 댓글 기능이 있는 블로그에서 댓글이 달려있는 게시글을 삭제하려고 한다면 무슨 문제가 발생할까요? Database 테이블 관점에서 해결방법이 무엇일까요?
    6. IoC / DI 에 대해 간략하게 설명해 주세요!
</details>

## 추가 요구 사항
<details>
  <summary>API 수정</summary>
  
    1. 회원 가입 API
      - username, password를 Client에서 전달받기
      - username은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성되어야 한다.
      - password는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자`로 구성되어야 한다.
      - DB에 중복된 username이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환하기
      - 회원 권한 부여하기 (ADMIN, USER) - ADMIN 회원은 모든 게시글, 댓글 수정 / 삭제 가능
      - 참고자료
          1. https://mangkyu.tistory.com/174
          2. [https://ko.wikipedia.org/wiki/정규_표현식](https://ko.wikipedia.org/wiki/%EC%A0%95%EA%B7%9C_%ED%91%9C%ED%98%84%EC%8B%9D)
          3. https://bamdule.tistory.com/35
          
    2. 로그인 API
      - username, password를 Client에서 전달받기
      - DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기
      - 로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급하고, 
      발급한 토큰을 Header에 추가하고 성공했다는 메시지, 상태코드 와 함께 Client에 반환하기
    3. 댓글 작성 API
      - 토큰을 검사하여, 유효한 토큰일 경우에만 댓글 작성 가능
      - 선택한 게시글의 DB 저장 유무를 확인하기
      - 선택한 게시글이 있다면 댓글을 등록하고 등록된 댓글 반환하기
    4. 댓글 수정 API
      - 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 댓글만 수정 가능
      - 선택한 댓글의 DB 저장 유무를 확인하기
      - 선택한 댓글이 있다면 댓글 수정하고 수정된 댓글 반환하기
    5. 댓글 삭제 API
      - 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 댓글만 삭제 가능
      - 선택한 댓글의 DB 저장 유무를 확인하기
      - 선택한 댓글이 있다면 댓글 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기
    6. 예외 처리
      - 토큰이 필요한 API 요청에서 토큰을 전달하지 않았거나 정상 토큰이 아닐 때는 "토큰이 유효하지 않습니다." 라는 에러메시지와 statusCode: 400을 Client에 반환하기
      - 토큰이 있고, 유효한 토큰이지만 해당 사용자가 작성한 게시글/댓글이 아닌 경우에는 “작성자만 삭제/수정할 수 있습니다.”라는 에러메시지와 statusCode: 400을 Client에 반환하기
      - DB에 이미 존재하는 username으로 회원가입을 요청한 경우 "중복된 username 입니다." 라는 에러메시지와 statusCode: 400을 Client에 반환하기
      - 로그인 시, 전달된 username과 password 중 맞지 않는 정보가 있다면 "회원을 찾을 수 없습니다."라는 에러메시지와 statusCode: 400을 Client에 반환하기

    ✌️ **요구사항에 맞게 수정해 보세요!**
    
    1. 전체 게시글 목록 조회 API
        - 제목, 작성자명(username), 작성 내용, 작성 날짜를 조회하기
        - 작성 날짜 기준 내림차순으로 정렬하기
        - 각각의 게시글에 등록된 모든 댓글을 게시글과 같이 Client에 반환하기
        - 댓글은 작성 날짜 기준 내림차순으로 정렬하기
    2. 게시글 작성 API
        - 토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능
        - 제목, 작성자명(username), 작성 내용을 저장하고
        - 저장된 게시글을 Client 로 반환하기
    3. 선택한 게시글 조회 API
        - 선택한 게시글의 제목, 작성자명(username), 작성 날짜, 작성 내용을 조회하기 
        (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
        - 선택한 게시글에 등록된 모든 댓글을 선택한 게시글과 같이 Client에 반환하기
        - 댓글은 작성 날짜 기준 내림차순으로 정렬하기
    4. 선택한 게시글 수정 API
        - ~~수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후~~
        - 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
        - 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
    5. 선택한 게시글 삭제 API
        - ~~삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후~~
        - 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 삭제 가능
        - 선택한 게시글을 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기
</details>
