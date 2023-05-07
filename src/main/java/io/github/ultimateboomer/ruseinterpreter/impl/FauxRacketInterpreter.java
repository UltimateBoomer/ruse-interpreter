package io.github.ultimateboomer.ruseinterpreter.impl;

import java.util.Map;

import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.ArithBin;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.ArithExp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.ArithOp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Num;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.FauxRacketAbstractSyntax;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public class FauxRacketInterpreter {

    private static final Map<String, ArithOp> arithOpMap = Map.of(
        "+", ArithOp.ADD,
        "-", ArithOp.SUB,
        "*", ArithOp.MUL,
        "/", ArithOp.DIV);

    public static FauxRacketAbstractSyntax parse(SExp exp) {
        if (exp instanceof Atom) {
            return new Num(Integer.parseInt(((Atom) exp).value()));
        } else if (exp instanceof SList) {
            return parseSList((SList) exp);
        } else {
            throw new IllegalArgumentException("Invalid syntax");
        }
    }

    private static FauxRacketAbstractSyntax parseSList(SList list) {
        if (list.exps().isEmpty()) {
            throw new IllegalArgumentException("SList is empty");
        }

        SExp first = list.exps().get(0);
        if (first instanceof Atom && list.exps().size() == 3 && arithOpMap.containsKey(((Atom) first).value())) {
            ArithExp left = (ArithExp) parse(list.exps().get(1));
            ArithExp right = (ArithExp) parse(list.exps().get(2));
            return new ArithBin(arithOpMap.get(((Atom) first).value()), left, right);
        } else {
            throw new IllegalArgumentException("Invalid SList syntax");
        }
    }

    public static FauxRacketAbstractSyntax interp(FauxRacketAbstractSyntax exp) {
        if (exp instanceof Num) {
            return exp;
        } else if (exp instanceof ArithBin) {
            Num leftRes = (Num) interp(((ArithBin) exp).left());
            Num rightRes = (Num) interp(((ArithBin) exp).right());
            return ((ArithBin) exp).op().apply(leftRes, rightRes);
        } else {
            throw new IllegalArgumentException("Invalid abstract syntax");
        }
    }

}
