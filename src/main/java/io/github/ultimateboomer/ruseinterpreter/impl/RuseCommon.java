package io.github.ultimateboomer.ruseinterpreter.impl;

import java.util.regex.Pattern;

class RuseCommon {

    // Patterns for parsing
    static final Pattern numPattern = Pattern.compile("-?[0-9]+");
    static final Pattern boolPattern = Pattern.compile("true|false");
    static final Pattern strPattern = Pattern.compile("\"(.+)\"");
    
}
