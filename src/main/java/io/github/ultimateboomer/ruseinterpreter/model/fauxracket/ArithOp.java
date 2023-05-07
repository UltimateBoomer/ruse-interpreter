package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import java.util.function.BiFunction;

public enum ArithOp implements BiFunction<Num, Num, Num> {

    ADD("+") {
        @Override
        public Num apply(Num arg0, Num arg1) {
            return new Num(arg0.n() + arg1.n());
        }
    },
    SUB("-") {
        @Override
        public Num apply(Num arg0, Num arg1) {
            return new Num(arg0.n() - arg1.n());
        }
    },
    MUL("*") {
        @Override
        public Num apply(Num arg0, Num arg1) {
            return new Num(arg0.n() * arg1.n());
        }
    },
    DIV("/") {
        @Override
        public Num apply(Num arg0, Num arg1) {
            return new Num(arg0.n() / arg1.n());
        }
    };

    private final String string;

    private ArithOp(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

}
