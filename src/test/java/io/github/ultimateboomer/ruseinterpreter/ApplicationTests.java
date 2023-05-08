package io.github.ultimateboomer.ruseinterpreter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.ultimateboomer.ruseinterpreter.model.InterpRequest;
import io.github.ultimateboomer.ruseinterpreter.model.InterpResponse;
import io.github.ultimateboomer.ruseinterpreter.model.RuseLanguage;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void testGetLanguages(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/api/ruse/langs"))
            .andExpect(content().json(objectMapper.writeValueAsString(List.of(RuseLanguage.values()))));
    }

    @Test
    void testInterpRuseApi(@Autowired MockMvc mvc) throws Exception {
        String t, tr;

        t = objectMapper.writeValueAsString(new InterpRequest(RuseLanguage.FAUX_RACKET, "5"));
        tr = objectMapper.writeValueAsString(new InterpResponse("5"));
        mvc.perform(post("/api/ruse/interp")
            .content(t)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(tr));

        t = objectMapper.writeValueAsString(new InterpRequest(RuseLanguage.FAUX_RACKET, "(+ (* 2 3) 4)"));
        tr = objectMapper.writeValueAsString(new InterpResponse("10"));
        mvc.perform(post("/api/ruse/interp")
            .content(t)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(tr));
    }

}
