package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import java.util.function.BiFunction;

public enum ArithBinOp implements BiFunction<Integer, Integer, Integer> {

    ADD("+") {
        @Override
        public Integer apply(Integer arg0, Integer arg1) {
            return arg0 + arg1;
        }
    },
    SUB("-") {
        @Override
        public Integer apply(Integer arg0, Integer arg1) {
            return arg0 - arg1;
        }
    },
    MUL("*") {
        @Override
        public Integer apply(Integer arg0, Integer arg1) {
            return arg0 * arg1;
        }
    },
    DIV("/") {
        @Override
        public Integer apply(Integer arg0, Integer arg1) {
            return arg0 / arg1;
        }
    },
    MOD("%") {
        @Override
        public Integer apply(Integer arg0, Integer arg1) {
            return arg0 % arg1;
        }
    };;

    private final String name;

    private ArithBinOp(String string) {
        this.name = string;
    }

    @Override
    public String toString() {
        return name;
    }

}
