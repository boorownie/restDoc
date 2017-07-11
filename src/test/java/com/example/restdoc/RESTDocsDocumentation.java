package com.example.restdoc;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by phpbae on 2017-07-11.
 */


@RunWith(SpringRunner.class)
@SpringBootTest
public class RESTDocsDocumentation {

    //Spring Web MVC 는 "Front Controller" 디자인 패턴
    //Front Controller 가 spring 에서 DispatcherServlet 의 역할을함.( DispatcherServlet 도 하나의 서블릿(HttpServlet 을 상속받아 만들어졌음)
    @Autowired
    private WebApplicationContext webApplicationContext;

    //클라이언트에서 요청 내용을 컨트롤러에서 받아 처리하는것과 같은 테스트를 진행하기 위한 인스턴스.
    // 결과값을 status 코드로 테스트 통과 여부 결정
    private MockMvc mockMvc;

    /**
     * 문서 코드조각을 생성(snippets)
     *
     * @Rule = 기본적으로 각각의 테스트에 부가적인 기능을 추가 또는 확장 시켜줌
     * 기본적인 룰 : TemporaryFolder / ExternalResource / ErrorCollector / ExpectedException / Timeout
     */
    @Rule
    public final JUnitRestDocumentation restDocumentation =
            new JUnitRestDocumentation("target/generated-snippets"); //JUnitRestDocumentation 인스턴스를 생성 할 때 출력 디렉토리를 제공하여 기본값을 재정의 할 수 있습니다.

    @Test
    public void simpleTest() {
        System.out.println("Call Test Method..");
    }

    /**
     * MockMvc 컨텍스트를 설정하여 문서를 생성하도록 구성.
     */
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    //mockMvc 인스턴스를 생성했으니, RESTful 서비스를 호출하고, 요청과 응답을 문서화
    @Test
    public void makeIndex() throws Exception {
        this.mockMvc.perform(get("/index_rest/index")
                .accept(MediaType.TEXT_HTML_VALUE)) //서비스의 URL 루트 표시 및 어떠한 응답이 필요함을 나타냄
                .andExpect(status().isOk()) //서비스가 만들어내는 원하는 응답을 결정
                .andDo(document("index")); //RestDocumentationResultHandler
    }

    //mockMvc 인스턴스를 생성했으니, RESTful 서비스를 호출하고, 요청과 응답을 문서화
    @Test
    public void makeMember() throws Exception {
        this.mockMvc.perform(get("/index_rest/member").accept(MediaType.APPLICATION_JSON)) ////MockMvc .perform(@NotNull org.springframework.test.web.servlet.RequestBuilder requestBuilder) .accept() -> MockHttpServletRequestBuilder
                .andExpect(status().isOk()) //ResultActions
                .andDo(document("member")); //ResultActions
    }

}
