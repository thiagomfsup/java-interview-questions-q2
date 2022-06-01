package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest()
@AutoConfigureMockMvc
public class DemoApplicationTestIT {
    public static final String SECRET_VALUE = "BIG-SECRET";

    static {
        System.setProperty("MY_SECRET", SECRET_VALUE);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetGreeting() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/greeting?name=Daniel"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content()
                                .string("greeting Daniel"));

    }

//    @Test
//    void testGetSecret() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/getSecret"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(
//                        MockMvcResultMatchers.content()
//                                .string("Your secret is: " + SECRET_VALUE));
//
//    }
}