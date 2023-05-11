package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import java.util.function.BiFunction;

public enum ArithBinOp implements BiFunction<Num, Num, Num> {

    ADD("+") {
        @Override
        public Num apply(Num arg0, Num arg1) {
            return new Num(arg0.value() + arg1.value());
        }
    },
    SUB("-") {
        @Override
        public Num apply(Num arg0, Num arg1) {
            return new Num(arg0.value() - arg1.value());
        }
    },
    MUL("*") {
        @Override
        public Num apply(Num arg0, Num arg1) {
            return new Num(arg0.value() * arg1.value());
        }
    },
    DIV("/") {
        @Override
        public Num apply(Num arg0, Num arg1) {
            return new Num(arg0.value() / arg1.value());
        }
    };

    private final String name;

    private ArithBinOp(String string) {
        this.name = string;
    }

    @Override
    public String toString() {
        return name;
    }

}
