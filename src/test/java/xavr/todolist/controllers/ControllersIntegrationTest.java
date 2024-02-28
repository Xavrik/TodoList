package xavr.todolist.controllers;

import jakarta.servlet.ServletContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

@ContextConfiguration("classpath:/main.xml")
@WebAppConfiguration(value = "/WEB-INF/web.xml")
@RunWith(SpringJUnit4ClassRunner.class)

public class ControllersIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
    @Test
    public void checkServletContext(){
    ServletContext servletContext = webApplicationContext.getServletContext();
    assertNotNull(servletContext);
    assertTrue(servletContext instanceof MockServletContext);
    assertNotNull(webApplicationContext.getBean("userController"));
    }

    @Test
    public void createUser() throws Exception {
        this.mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("{\"email\": \"email\" , \"password\": \"password\"}")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("email").value("email"))
                .andExpect(jsonPath("password").value("password"))
                .andExpect(jsonPath("id").isNotEmpty());

    }

    @Test
    public void getUser() throws Exception {
        this.mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content("{\"email\": \"email\" , \"password\": \"password\"}")
        ).andDo(print());

        this.mockMvc.perform(get("/users/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value("email"))
                .andExpect(jsonPath("password").value("password"))
                .andExpect(jsonPath("id").value(1));
    }

}
