package io.github.ultimateboomer.ruseinterpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.github.ultimateboomer.ruseinterpreter.impl.SExpParser;
import io.github.ultimateboomer.ruseinterpreter.impl.SIMPLInterpreter;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Num;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Var;
import io.github.ultimateboomer.ruseinterpreter.model.simpl.PrintExpStmt;
import io.github.ultimateboomer.ruseinterpreter.model.simpl.PrintStrStmt;
import io.github.ultimateboomer.ruseinterpreter.model.simpl.SkipStmt;
import io.github.ultimateboomer.ruseinterpreter.model.simpl.VarDef;

class SIMPLInterpreterTests {
    
    @Test
    void testParse() {
        assertEquals(new VarDef(Map.of("x", new Num(2)), List.of(new SkipStmt())),
            SIMPLInterpreter.parse(SExpParser.parse("(vars ((x 2)) (skip))")));

        assertEquals(new VarDef(Map.of("x", new Num(2)), List.of(new PrintExpStmt(new Var("x")))),
            SIMPLInterpreter.parse(SExpParser.parse("(vars ((x 2)) (print x))")));

        assertEquals(new VarDef(Map.of("x", new Num(2)), List.of(new PrintStrStmt("\n"))),
            SIMPLInterpreter.parse(SExpParser.parse("(vars ((x 2)) (print \"\\n\"))")));
    }

    @Test
    void testCombined() {
        StringBuilder out = new StringBuilder();

        out.setLength(0);
        SIMPLInterpreter.interp((VarDef) SIMPLInterpreter.parse(
            SExpParser.parse("(vars ((x 2)) (print x))")), out);
        assertEquals("2", out.toString());

        out.setLength(0);
        SIMPLInterpreter.interp((VarDef) SIMPLInterpreter.parse(
            SExpParser.parse("(vars ((x 2)) (print (* x 2)))")), out);
        assertEquals("4", out.toString());

        out.setLength(0);
        SIMPLInterpreter.interp((VarDef) SIMPLInterpreter.parse(
            SExpParser.parse("(vars ((x 5)) (iif (> x 3) (print \"t\") (print \"f\")))")), out);
        assertEquals("t", out.toString());

        out.setLength(0);
        SIMPLInterpreter.interp((VarDef) SIMPLInterpreter.parse(
            SExpParser.parse("(vars ((x 5)) (iif (< x 3) (print \"t\") (print \"f\")))")), out);
        assertEquals("f", out.toString());

        out.setLength(0);
        SIMPLInterpreter.interp((VarDef) SIMPLInterpreter.parse(
            SExpParser.parse("(vars ((x 5)) (while (> x 0) (print x) (set x (- x 1))))")), out);
        assertEquals("54321", out.toString());
        
    }

}
