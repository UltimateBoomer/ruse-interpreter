package io.github.ultimateboomer.ruseinterpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.ultimateboomer.ruseinterpreter.impl.RuseInterpreter;
import io.github.ultimateboomer.ruseinterpreter.model.ruse.ArithBin;
import io.github.ultimateboomer.ruseinterpreter.model.ruse.ArithOp;
import io.github.ultimateboomer.ruseinterpreter.model.ruse.Num;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public class RuseInterpreterTests {
    
    @Test
    public void testParse() {
        assertEquals(new Num(3), RuseInterpreter.parse(new Atom("3")));

        assertEquals(new ArithBin(ArithOp.ADD, new Num(1), new Num(2)), RuseInterpreter.parse(new SList(
            List.of(new Atom("+"), new Atom("1"), new Atom("2")))));

        assertEquals(new ArithBin(
                ArithOp.ADD,
                new ArithBin(ArithOp.MUL, new Num(2), new Num(3)),
                new Num(4)),
            RuseInterpreter.parse(new SList(
                List.of(new Atom("+"),
                    new SList(
                        List.of(new Atom("*"), new Atom("2"), new Atom("3"))),
                    new Atom("4")))));

    }

    @Test
    public void testInterp() {
        assertEquals(new Num(3), RuseInterpreter.interp(new Num(3)));

        assertEquals(new Num(3), RuseInterpreter.interp(new ArithBin(ArithOp.ADD, new Num(1), new Num(2))));

        assertEquals(new Num(10), RuseInterpreter.interp(new ArithBin(
            ArithOp.ADD,
            new ArithBin(ArithOp.MUL, new Num(2), new Num(3)),
            new Num(4))));
    }

}
