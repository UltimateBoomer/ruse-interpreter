package io.github.ultimateboomer.ruseinterpreter.model.ruse;

import java.util.function.BiFunction;

public enum AOp implements BiFunction<Num, Num, Num> {

    ADD {
        @Override
        public Num apply(Num arg0, Num arg1) {
            return new Num(arg0.n() + arg1.n());
        }
    },
    SUB {
        @Override
        public Num apply(Num arg0, Num arg1) {
            return new Num(arg0.n() - arg1.n());
        }
    },
    MUL {
        @Override
        public Num apply(Num arg0, Num arg1) {
            return new Num(arg0.n() * arg1.n());
        }
    },
    DIV {
        @Override
        public Num apply(Num arg0, Num arg1) {
            return new Num(arg0.n() / arg1.n());
        }
    }

}
