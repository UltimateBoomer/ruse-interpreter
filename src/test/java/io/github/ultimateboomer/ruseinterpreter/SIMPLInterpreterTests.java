package io.github.ultimateboomer.ruseinterpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.ultimateboomer.ruseinterpreter.impl.SExpParser;
import io.github.ultimateboomer.ruseinterpreter.impl.SIMPLInterpreter;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Num;
import io.github.ultimateboomer.ruseinterpreter.model.simpl.SkipStmt;
import io.github.ultimateboomer.ruseinterpreter.model.simpl.VarDef;

class SIMPLInterpreterTests {
    
    @Test
    void testParse() {
        assertEquals(new VarDef(Map.of("x", new Num(2)), List.of(new SkipStmt())),
            SIMPLInterpreter.parse(SExpParser.parse("(vars ((x 2)) (skip))")));
    }

}
