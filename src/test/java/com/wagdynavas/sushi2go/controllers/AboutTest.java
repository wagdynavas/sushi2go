package com.wagdynavas.sushi2go.controllers;

import org.springframework.web.servlet.ModelAndView;

public class AboutTest {

    public void testViewName() {
        ModelAndView md = new ModelAndView();
        md.setViewName("about/about");


       // assertEquals(md.getViewName(), );
    }
}
