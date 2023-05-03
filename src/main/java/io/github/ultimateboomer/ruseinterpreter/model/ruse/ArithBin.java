package io.github.ultimateboomer.ruseinterpreter.model.ruse;

public record ArithBin(
    ArithOp op,
    ArithExp left,
    ArithExp right
) implements ArithExp {
    
}
