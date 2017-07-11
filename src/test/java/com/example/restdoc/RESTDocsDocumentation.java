package com.example.restdoc;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by phpbae on 2017-07-11.
 */


@RunWith(SpringRunner.class)
@SpringBootTest
public class RESTDocsDocumentation {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    /**
     * 문서 코드조각을 생성
     * @Rule = 기본적으로 각각의 테스트에 부가적인 기능을 추가 또는 확장 시켜줌
     * 기본적인 룰 : TemporaryFolder / ExternalResource / ErrorCollector / ExpectedException / Timeout
     */
    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");
    private RestDocumentationResultHandler document;

    @Ignore
    @Test
    public void simpleTest() {
        System.out.println("Call Test Method..");
    }

    /**
     * MockMvc 컨텍스트를 설정하여  문서를 생성하도록 구성.
     */
    @Before
    public void setUp() {
        this.document = document("{method-name}/", preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.document)
                .build();
    }

    @Test
    public void makeIndex() throws Exception {
        this.mockMvc.perform(get("/index_rest/index").accept(MediaType.TEXT_HTML_VALUE))
                .andExpect(status().isOk())
                .andDo(this.document);
    }

    @Test
    public void makeMember() throws Exception {
        this.mockMvc.perform(get("/index_rest/member").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(this.document);
    }

}
