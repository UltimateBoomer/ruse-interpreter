package io.github.ultimateboomer.ruseinterpreter.impl;

import java.util.regex.Pattern;

public class RuseCommon {

    // Patterns for parsing
    public static final Pattern numPattern = Pattern.compile("-?[0-9]+");
    public static final Pattern boolPattern = Pattern.compile("^(true|false)$");
    public static final Pattern strPattern = Pattern.compile("\"(.*)\"");
    
}
