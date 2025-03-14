package com.wagdynavas.sushi2go.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = {AboutController.class ,ContactController.class})
@AutoConfigureMockMvc(addFilters = false)
class ViewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void aboutViewTest() throws Exception {

        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about/about"));

    }

    @Test
    void contactViewTest() throws Exception {

        mockMvc.perform(get("/contact"))
                .andExpect(status().isOk())
                .andExpect(view().name("contact/contact"));

    }
}
