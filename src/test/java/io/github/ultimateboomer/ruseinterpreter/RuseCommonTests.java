package io.github.ultimateboomer.ruseinterpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Matcher;

import org.junit.jupiter.api.Test;

import io.github.ultimateboomer.ruseinterpreter.impl.RuseCommon;

class RuseCommonTests {
    
    @Test
    void testNumPattern() {
        assertEquals(true, RuseCommon.numPattern.matcher("123456").matches());
        assertEquals(false, RuseCommon.numPattern.matcher("dd56").matches());
    }

    @Test
    void testBoolPattern() {
        assertEquals(false, RuseCommon.boolPattern.matcher("yes").matches());

        // Capture tests
        Matcher m;

        m = RuseCommon.boolPattern.matcher("true");
        assertEquals(true, m.matches());
        assertEquals("true", m.group(1));

        m = RuseCommon.boolPattern.matcher("false");
        assertEquals(true, m.matches());
        assertEquals("false", m.group(1));
    }

    @Test
    void testStrPattern() {
        assertEquals(true, RuseCommon.strPattern.matcher("\"abc\"").matches());
        assertEquals(false, RuseCommon.strPattern.matcher("abc").matches());

        // Capture tests
        Matcher m;

        m = RuseCommon.strPattern.matcher("\"wasd\"");
        assertEquals(true, m.matches());
        assertEquals("wasd", m.group(1));
    }

}
