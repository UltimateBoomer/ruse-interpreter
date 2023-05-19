package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import java.util.function.BiFunction;

public enum BoolBinOp implements BiFunction<Boolean, Boolean, Boolean> {
    
    AND("and") {
        @Override
        public Boolean apply(Boolean t, Boolean u) {
            return t && u;
        }
    },
    OR("or") {
        @Override
        public Boolean apply(Boolean t, Boolean u) {
            return t || u;
        }
    };

    private final String name;

    private BoolBinOp(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
