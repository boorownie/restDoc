package com.example.restdoc.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by phpbae on 2017-07-11.
 */

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(value = "/index_rest_doc", method = RequestMethod.GET)
    public ModelAndView getIndexRestDoc(HttpSession session) {
        ModelAndView mav = new ModelAndView("rest_docs/index.html");
        return mav;
    }

    @RequestMapping(value = "/member_rest_doc", method = RequestMethod.GET)
    public ModelAndView getMemberRestDoc(HttpSession session) {
        ModelAndView mav = new ModelAndView("rest_docs/member.html");
        return mav;
    }

}
