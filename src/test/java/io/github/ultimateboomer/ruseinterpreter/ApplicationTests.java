package io.github.ultimateboomer.ruseinterpreter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.ultimateboomer.ruseinterpreter.model.InterpRequest;
import io.github.ultimateboomer.ruseinterpreter.model.ruse.Num;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void contextLoads() {

    }

    @Test
    void testInterpRuseApi(@Autowired MockMvc mvc) throws Exception {
        String t, tr;

        t = objectMapper.writeValueAsString(new InterpRequest("5"));
        tr = objectMapper.writeValueAsString(new Num(5));
        mvc.perform(post("/api/interp/ruse")
            .content(t)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(tr));

        t = objectMapper.writeValueAsString(new InterpRequest("(+ 1 2)"));
        tr = objectMapper.writeValueAsString(new Num(3));
        mvc.perform(post("/api/interp/ruse")
            .content(t)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(tr));

        t = objectMapper.writeValueAsString(new InterpRequest("(+ (* 2 3) 4)"));
        tr = objectMapper.writeValueAsString(new Num(10));
        mvc.perform(post("/api/interp/ruse")
            .content(t)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(tr));
    }

}
