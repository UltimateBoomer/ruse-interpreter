package io.github.ultimateboomer.ruseinterpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.ultimateboomer.ruseinterpreter.impl.FauxRacketInterpreter;
import io.github.ultimateboomer.ruseinterpreter.impl.SExpParser;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.App;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.ArithBinExp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.ArithBinOp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Fun;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Num;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Var;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

class FauxRacketInterpreterTests {
    
    @Test
    void testParse() {
        assertEquals(new Num(3), FauxRacketInterpreter.parse(new Atom("3")));

        assertEquals(new ArithBinExp(ArithBinOp.ADD, new Num(1), new Num(2)), FauxRacketInterpreter.parse(new SList(
            List.of(new Atom("+"), new Atom("1"), new Atom("2")))));

        assertEquals(new ArithBinExp(
                ArithBinOp.ADD,
                new ArithBinExp(ArithBinOp.MUL, new Num(2), new Num(3)),
                new Num(4)),
            FauxRacketInterpreter.parse(new SList(
                List.of(new Atom("+"),
                    new SList(
                        List.of(new Atom("*"), new Atom("2"), new Atom("3"))),
                    new Atom("4")))));
    }

    @Test
    void testInterp() {
        assertEquals(new Num(3), FauxRacketInterpreter.interp(new Num(3)));

        assertEquals(new Num(3), FauxRacketInterpreter.interp(new ArithBinExp(ArithBinOp.ADD, new Num(1), new Num(2))));

        assertEquals(new Num(10), FauxRacketInterpreter.interp(new ArithBinExp(
            ArithBinOp.ADD,
            new ArithBinExp(ArithBinOp.MUL, new Num(2), new Num(3)),
            new Num(4))));

        assertEquals(new Num(3), FauxRacketInterpreter.interp(new App(
            new Fun("x", new ArithBinExp(ArithBinOp.ADD, new Var("x"), new Num(1))),
            new Num(2))));
    }

    @Test
    void testCombined() {
        assertEquals(new Num(1),
            FauxRacketInterpreter.interp(FauxRacketInterpreter.parse(
                SExpParser.parse("(if (and true (not false)) 1 2)"))));
    }

}
