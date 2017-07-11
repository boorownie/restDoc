package com.example.restdoc.presentation.controller.rest;

import com.example.restdoc.business.domain.MemberDomain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phpbae on 2017-07-11.
 */
@RestController
@RequestMapping("/index_rest")
public class IndexRestController {

    @RequestMapping(value = "index", method = RequestMethod.GET, produces = "text/html")
    public ResponseEntity getIndexRest() {

        return new ResponseEntity("REST!!", HttpStatus.OK);
        //result : REST!!
    }

    @RequestMapping(value = "member", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getMemberRest() {
        List<MemberDomain> memberDomainList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            memberDomainList.add(new MemberDomain("TestName-" + i, i));
        }

        return new ResponseEntity(memberDomainList, HttpStatus.OK);
        //result : [{"name":"TestName-0","age":0},{"name":"TestName-1","age":1},{"name":"TestName-2","age":2}]
    }

}
