package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenAValidUrl_whenShortenUrl_thenReturnAHashWith8Characters() throws Exception {
        String url = "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json";

        mockMvc.perform(post("/short").param("url", url))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string(Matchers.hasLength(8)));
    }

    @Test
    public void givenAValidUrl_whenShortenUrl_thenItsPossibleToRetriveTheUrlByHash() throws Exception {
        String url = "https://avalid.url/path/content.json";

        String generatedHash = mockMvc.perform(post("/short").param("url", url))
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/long").param("tiny", generatedHash))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl(url));
    }
    
    @Test
    public void givenAnInvalidUrl_whenShortenUrl_thenReturnHTTP400() throws Exception {
        String url = "unknownProtocol://url.fake";
        mockMvc.perform(post("/short").param("url", url))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenAnInvalidHash_whenRetrievingURL_thenReturnHTTP404() throws Exception {
        String invalidHash = "1234ABCD";

        mockMvc.perform(get("/long").param("tiny", invalidHash))
                .andExpect(status().isNotFound());
    }

}
