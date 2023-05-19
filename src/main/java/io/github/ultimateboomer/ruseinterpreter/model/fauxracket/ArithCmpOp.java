package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import java.util.function.BiFunction;

public enum ArithCmpOp implements BiFunction<Integer, Integer, Boolean> {

    EQ("=") {
        @Override
        public Boolean apply(Integer t, Integer u) {
            return t == u;
        }
    },
    LT("<") {
        @Override
        public Boolean apply(Integer t, Integer u) {
            return t < u;
        }
    },
    LE("<=") {
        @Override
        public Boolean apply(Integer t, Integer u) {
            return t <= u;
        }
    },
    GT(">") {
        @Override
        public Boolean apply(Integer t, Integer u) {
            return t > u;
        }
    },
    GE(">=") {
        @Override
        public Boolean apply(Integer t, Integer u) {
            return t >= u;
        }
    };
    
    private final String name;

    private ArithCmpOp(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
