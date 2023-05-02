package io.github.ultimateboomer.ruseinterpreter.model.ruse;

public record ArithBin(
    AOp op,
    ArithExp left,
    ArithExp right
) implements ArithExp {
    
}
