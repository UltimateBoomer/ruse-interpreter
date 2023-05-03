package io.github.ultimateboomer.ruseinterpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import io.github.ultimateboomer.ruseinterpreter.impl.SExpParser;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public class SexpParserTests {
    
    @Test
    public void testParse() {
        assertEquals(new Atom("5"), SExpParser.parse("5"));

        assertEquals(new SList(Arrays.asList(
            new Atom("a"), 
            new SList(Arrays.asList(
                new Atom("b"), 
                new Atom("c"))),
            new Atom("d"))),
            SExpParser.parse("(a (b c) d)"));
    }

    @Test
    public void testToString() {
        assertEquals("5", new Atom("5").toString());

        assertEquals("(a (b c) d)",
            new SList(Arrays.asList(
                new Atom("a"), 
                new SList(Arrays.asList(
                    new Atom("b"),
                    new Atom("c"))),
                new Atom("d"))).toString());
    }

}
