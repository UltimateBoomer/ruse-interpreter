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

import io.github.ultimateboomer.ruseinterpreter.model.ruse.AOp;
import io.github.ultimateboomer.ruseinterpreter.model.ruse.ArithBin;
import io.github.ultimateboomer.ruseinterpreter.model.ruse.Num;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void contextLoads() {

    }

    @Test
    void interpRuseApi(@Autowired MockMvc mvc) throws Exception {
        String t0 = objectMapper.writeValueAsString(new Num(5));
        
        mvc.perform(post("/api/interp/ruse")
            .content(t0)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(t0));

        String t1 = objectMapper.writeValueAsString(
            new ArithBin(AOp.ADD, 
                new ArithBin(AOp.MUL, 
                    new Num(3), 
                    new Num(4)), 
                new Num(3)));
        String t1r = objectMapper.writeValueAsString(new Num(15));
        
        mvc.perform(post("/api/interp/ruse")
            .content(t1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(t1r));
    }

}
