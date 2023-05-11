package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import java.util.function.BiFunction;

public enum BoolBinOp implements BiFunction<Bool, Bool, Bool> {
    
    AND("and") {
        @Override
        public Bool apply(Bool t, Bool u) {
            return new Bool(t.value() && u.value());
        }
    },
    OR("or") {
        @Override
        public Bool apply(Bool t, Bool u) {
            return new Bool(t.value() || u.value());
        }
    };

    private final String name;

    private BoolBinOp(String string) {
        this.name = string;
    }

    @Override
    public String toString() {
        return name;
    }

}
