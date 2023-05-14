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
import org.springframework.test.web.servlet.ResultActions;

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

    ResultActions expectInterp(String input, String expectedResult, RuseLanguage lang, MockMvc mvc) throws Exception {
        String t = objectMapper.writeValueAsString(new InterpRequest(RuseLanguage.FAUX_RACKET, input));
        String tr = objectMapper.writeValueAsString(new InterpResponse(expectedResult));

        return mvc.perform(post("/api/ruse/interp")
            .content(t)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(tr)); 
    }

    @Test
    void testFauxRacket(@Autowired MockMvc mvc) throws Exception {
        expectInterp("5", "5", RuseLanguage.FAUX_RACKET, mvc);

        expectInterp("(+ (* 2 3) 4)", "10", RuseLanguage.FAUX_RACKET, mvc);

        expectInterp("(if (and true (not false)) 1 2)", "1", RuseLanguage.FAUX_RACKET, mvc);

        expectInterp("((fun (x) (+ x 1)) 2)", "3", RuseLanguage.FAUX_RACKET, mvc);

        expectInterp("(with ((x 2)) (+ x 3))", "5", RuseLanguage.FAUX_RACKET, mvc);
    }

    @Test
    void testSIMPL(@Autowired MockMvc mvc) throws Exception {
        expectInterp("(vars ((x 1)) (print x))", "1", RuseLanguage.SIMPL, mvc);
    }

}
