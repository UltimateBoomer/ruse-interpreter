package io.github.ultimateboomer.ruseinterpreter.model;

public record InterpRequest(
    RuseLanguage lang,
    String exp
) {
    
}
