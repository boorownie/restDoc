package com.example.restdoc;

import com.example.restdoc.presentation.controller.rest.IndexRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by phpbae on 2017-07-11.
 * boot 기반 Mvc Test설정
 */


@RunWith(SpringRunner.class)
@WebMvcTest(IndexRestController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class RESTDocs2Documentation {

    @Autowired
    private MockMvc mockMvc;

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
        this.mockMvc.perform(get("/index_rest/member")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("member")); //식별자 Dir에 스니펫이 생성.
    }

    @Test
    public void getRestIndex() throws Exception {
        this.mockMvc.perform(get("/index_rest/index?testNum=1")
                .accept(MediaType.TEXT_HTML_VALUE)) //서비스의 URL 루트 표시 및 어떠한 응답이 필요함을 나타냄
                .andExpect(status().isOk())
                .andDo(print());
    }

}
