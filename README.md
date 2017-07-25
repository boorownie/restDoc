# This Project Environment
Spring boot 1.5.4, Java 1.8, embedded tomcat <br>

spring boot - Selected Dependencies

Core
- AOP
- DevTools
- Lombok

Web
- Web
- REST Docs

# RESTDoc
Spring REST Docs Project <br>

- 개요 <br>
스프링 Rest Docs를 사용 및 예제코드 작성. <br>
RESTful 서비스를 문서화 해보자.(스프링 REST Docs의 목표) <br>
Spring REST Docs는 RESTful 서비스를 문서화 하는데 도움이 된다. <br>

- 작업 <br>
pom.xml 에 <br>
 ```
     <dependency>
     <groupId>org.springframework.restdocs</groupId>
     <artifactId>spring-restdocs-mockmvc</artifactId>
     <version>1.2.1.RELEASE</version>
     </dependency>
 ```
위 라이브러리를 추가. <br>

pom.xml 에 빌드 설정<br>
Spring REST Docs는 Asciidoctor를 사용. 아이스닥터는 평문을 처리하여, HTML문서를 생성해 준다. <br>

```
<properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <snippetsDirectory>${project.build.directory}/generated-snippets</snippetsDirectory>
</properties>
```

위와 같이, 프로퍼티스에 스니펫디렉토리를 추가해 준다. (생성된 코드조각을 output 경로를 설정)
```
<snippetsDirectory>${project.build.directory}/generated-snippets</snippetsDirectory>
```

```
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-surefire-plugin</artifactId>
	<configuration>
   		<includes>
		    <include>**/*Tests.java</include>
		    <include>**/*Test.java</include>
    	</includes>
	    <excludes>
		    <exclude>**/Abstract*.java</exclude>
	    </excludes>
	</configuration>
</plugin>
```
부트로 프로젝트를 구성하면, 위와 같이 starter dependency에 플러그인이 있겠지만, 따로 붙여넣기를 하겠다.

```
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-surefire-plugin</artifactId>
  <version>2.20</version>
  <configuration>
     <includes>
       <include>**/*Tests.java</include>
       <include>**/*Test.java</include>
       <include>**/*Documentation.java</include>
     </includes>
     <excludes>
       <exclude>**/Abstract*.java</exclude>
     </excludes>
  </configuration>
</plugin>
```
plugins 태그 안에 해당 플러그인 태그를 추가해 주자.(플러그인에, Documentation.java로 끝나는 이름을 가진 파일들을 포함하도록 설정) <br>
* Surefire Plugin 은 unit test들을 실행하기 위해 build lifecycle의 test phase동안 사용되어 진다. 
* 그리고 txt, xml 포멧의 리포트파일을 제공. <br>

나머지 플러그인을 추가해주자.(asciidoctor, maven-resources-plugin)

```
<!-- Add asciidoctor-maven-plugin -->
            <plugin>
                <groupId>org.asciidoctor</groupId>
                <artifactId>asciidoctor-maven-plugin</artifactId>
                <version>1.5.5</version>
                <executions>
                    <execution>
                        <id>generate-docs</id>
                        <!-- Using prepare-package allows the documentation to be included in the package. -->
                        <phase>prepare-package</phase>
                        <goals>
                            <goal>process-asciidoc</goal>
                        </goals>
                        <configuration>
                            <backend>html</backend>
                            <doctype>book</doctype>
                            <attributes>
                                <!--  생성 된 snippets 스니펫을 문서에 포함시킬 때 사용할 수있는 명명 된 속성을 정의-->
                                <snippets>${project.build.directory}/generated-snippets</snippets>
                            </attributes>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            
            <!-- Add maven-resources-plugin -->
            <plugin>
                <artifactId>maven-resources-plugin</artifactId>
                <version>2.7</version>
                <configuration>
                    <encoding>UTF-8</encoding>
                </configuration>
                <executions>
                    <execution>
                        <id>copy-resources</id>
                        <phase>package</phase>
                        <goals>
                            <goal>copy-resources</goal>
                        </goals>
                        <configuration>
                            <overwrite>true</overwrite>
                            <outputDirectory>${project.build.outputDirectory}/static/rest_docs</outputDirectory>
                            <resources>
                                <resource>
                                    <directory>${project.build.directory}/generated-docs</directory>
                                </resource>
                            </resources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```
- Asciidoctor는 AsciiDoc 콘텐츠를 HTML5, DocBook 5 (또는 4.5) 및 기타 형식으로 변환하기위한 빠른 텍스트 프로세서 및 게시 툴체인입니다.
- 주의) maven-resources-plugin 이 asciidoctor-maven-plugin 이 실행이 된 이후에 실행이 되도록 해줘야함. 그래서 asciidoctor-maven-plugin 을 보면, prepare-package가 지정이 된것이 보인다.
- 주의) boot에서 spring-restdocs-mockmvc 추가하면, 메소드를 찾지못해서 정상작동이 되지않는 경우가 있는데, spring-restdocs-core를 같이 추가해주면 정상작동.(또는 부트 구성 시, 체크해서 포함시켰으면 해당 라이브러리 추가 안해도 됨)
```
<!-- Add spring-restdocs-mockmvc -->
        <dependency>
            <groupId>org.springframework.restdocs</groupId>
            <artifactId>spring-restdocs-mockmvc</artifactId>
            <version>1.2.1.RELEASE</version>
            <scope>test</scope>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework.restdocs/spring-restdocs-core -->
        <dependency>
            <groupId>org.springframework.restdocs</groupId>
            <artifactId>spring-restdocs-core</artifactId>
            <version>1.2.1.RELEASE</version>
            <scope>test</scope>
        </dependency>
```
<br>
- 생성된 snippet을 사용하기 위해서는, 사용하기 전에 .adoc 소스 파일을 만들어야 함. <br>
- .adoc 파일 경로는 src/main/asciidoc -> default

```
== 사용자 리스트 조회 [get]
  
  사용자를조회
  
  include::{snippets}/index/curl-request.adoc[]
  
  === 요청 구조
  
  ==== 요청 파라미터들
  
  include::{snippets}/index/http-request.adoc[]
  
  === 응답 구조
  
  ==== 응답 파라미터들
  
  include::{snippets}/index/http-response.adoc[]
```
코드조각(snippets)을 사용할려면, include 매크로를 사용하면 된다.<br>
참고 : http://asciidoctor.org/docs/asciidoc-syntax-quick-reference/#include-files
<br>

위 과정이 끝났으면, RESTDocsDocumentation Test 클래스를 통해서, Spring MVC 테스트가 어떻게 설정이 되어있는지 확인.<br>
execute maven goal = clean install <br>
만약, Test를 skip 하고 싶다면, clean install _-Dmaven.test.skip=true_ <br>
참고 : 
https://spring.io/guides/gs/testing-restdocs/

<br>
--------------------------------------------------------
<br>
좀 더 다양하게 쓰려면, asciidoc 이라는 문법을 공부해야함.
asciidoc은 밑에와 같이 정의되어있다. <br>
- 메모, 기사, 문서, 서적, 전자 책, 웹 페이지, 슬라이드 데크, 블로그 게시물, 맨 페이지 등을 작성하기위한 성숙한 일반 텍스트 형식입니다.<br>
- AsciiDoc 문서를 HTML, DocBook, PDF 및 ePub [2]를 포함한 다양한 형식 (백엔드라고 함)으로 변환하기위한 텍스트 프로세서 및 툴체인.<br>
