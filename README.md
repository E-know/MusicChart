# MusicChart
2020학년 문제해결기법 '실력만은 20학번' 팀의 리팩토링 프로젝트입니다.  
[기존 프로젝트](https://github.com/E-know/MusicChartWithCommunity)

# 프로젝트 목표
여러 음원사이트에 나와있는 TOP100차트를 가져와서 한번에 비교해주고 각 사이트별 앨범 댓글들을 한눈에 보여주는 프로그램입니다.


# 리팩토링 내용
+ MVC 패턴 분리
기존 MVC 패턴이 미 적용된 자바 프로젝트였습니다. 코드 리펙토링 전에 MVC패턴으로 분리했습니다.
+ switch문 제거
switch문이 꼭 나쁜 것은 아니지만, 에러가 발생할 여지가 있기에 제거하는 방향으로 진행했습니다.
+ 데이터 클래스 생성
공통적인 불변의 데이터가 여럿 있어서 데이터 클래스를 생성하여서 처리하였습니다.
+ 미완성 부분 완성
지난 프로젝트에서 시간에 쫓겨 하지 못했던 부분들을 완성시켰습니다.
  - 각 사이트 댓글 크롤링
  - 데이터 베이스 구축
자세한 리팩토링 내용은 [최종보고서](https://me2.do/GWFZGvud)내용 참고바람

# 팀 구성
## 팀장
최인호 ( 각 사이트 댓글 크롤링, View 리팩토링 및 서류&발표자료 준비 )    
## 팀원
안건우 ( 차트 크롤링 리팩토링 및 Model 리팩토링 )  
박기춘 ( 데이터 베이스 구축 및 최근 본 목록 생성)   
김성수 ( 컨트롤러 리팩토링 및 유튜브 스트리밍 연결)

# 사용된 라이브러리, 프레임워크, 데이터베이스
## 라이브러리
<img src="https://bit.ly/3jZxrBD" width="30%">

## 프레임워크  
<img src="https://me2.do/FYu8pEeM" width="20%">

## 데이터베이스
<img src="https://me2.do/F36rBkib" width="20%"><img src="https://me2.do/FNmEbLkn" width="20%">

# 프로그램 시연
<img src="https://ifh.cc/g/8uaE7U.jpg" width="40%"> <img src="https://ifh.cc/g/3C3yaf.jpg" width="40">
