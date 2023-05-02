package io.github.ultimateboomer.ruseinterpreter.impl;

import io.github.ultimateboomer.ruseinterpreter.model.ruse.ArithBin;
import io.github.ultimateboomer.ruseinterpreter.model.ruse.Num;
import io.github.ultimateboomer.ruseinterpreter.model.ruse.RuseAbstractSyntax;

public class RuseInterpreter {
    
    public RuseAbstractSyntax interp(RuseAbstractSyntax exp) {
        if (exp instanceof Num) {
            return exp;
        } else if (exp instanceof ArithBin) {
            Num leftRes = (Num) interp(((ArithBin) exp).left());
            Num rightRes = (Num) interp(((ArithBin) exp).right());
            return ((ArithBin) exp).op().apply(leftRes, rightRes);
        } else {
            throw new IllegalArgumentException("Invalid syntax");
        }
    }

}
