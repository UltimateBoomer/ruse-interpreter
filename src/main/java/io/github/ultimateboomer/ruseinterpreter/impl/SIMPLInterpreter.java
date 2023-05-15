package io.github.ultimateboomer.ruseinterpreter.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.AbstractSyntax;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Bool;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Exp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Num;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Var;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;
import io.github.ultimateboomer.ruseinterpreter.model.simpl.IifStmt;
import io.github.ultimateboomer.ruseinterpreter.model.simpl.PrintExpStmt;
import io.github.ultimateboomer.ruseinterpreter.model.simpl.PrintStrStmt;
import io.github.ultimateboomer.ruseinterpreter.model.simpl.SeqStmt;
import io.github.ultimateboomer.ruseinterpreter.model.simpl.SetStmt;
import io.github.ultimateboomer.ruseinterpreter.model.simpl.SkipStmt;
import io.github.ultimateboomer.ruseinterpreter.model.simpl.Stmt;
import io.github.ultimateboomer.ruseinterpreter.model.simpl.VarDef;

public class SIMPLInterpreter {

    public static AbstractSyntax parse(SExp exp) {
        if (exp instanceof Atom) {
            String value = ((Atom) exp).value();
            if (RuseCommon.numPattern.matcher(value).matches()) {
                return new Num(Integer.parseInt(value));
            } else if (RuseCommon.boolPattern.matcher(value).matches()) {
                return new Bool(Boolean.parseBoolean(value));
            } else {
                return new Var(value);
            }
        } else {
            return parseSList((SList) exp);
        }
    }

    private static AbstractSyntax parseSList(SList list) {
        if (list.exps().isEmpty()) {
            throw new InterpException("SList is empty");
        }

        SExp first = list.exps().get(0);

        if (first instanceof Atom && ((Atom) first).value().equals("vars")) {
            Map<String, Exp> vars = new HashMap<>();
            ((SList) list.exps().get(1)).exps().stream().forEach(e -> vars.put(((Atom) ((SList) e).exps().get(0)).value(),
                (Exp) parse(((SList) e).exps().get(1))));
            List<Stmt> stmts = list.exps().stream().skip(2).map(s -> (Stmt) parse(s)).toList();
            return new VarDef(vars, stmts);
        } else if (first instanceof Atom && ((Atom) first).value().equals("skip")) {
            return new SkipStmt();
        } else if (first instanceof Atom && ((Atom) first).value().equals("print")) {
            SExp second = list.exps().get(1);
            Matcher matcher = RuseCommon.strPattern.matcher(((Atom) second).value());
            if (second instanceof Atom && matcher.matches()) {
                String str = matcher.group(1).translateEscapes();
                return new PrintStrStmt(str);
            } else {
                return new PrintExpStmt((Exp) parse(second));
            }
        }

        throw new InterpException("Invalid Faux Racket syntax");
    }

    public static void interp(VarDef stmt, StringBuilder out) {
        Map<String, Exp> env = stmt.vars();
        stmt.stmts().forEach(s -> interpStmt(s, env, out));
    }

    private static void interpStmt(Stmt stmt, Map<String, Exp> env, StringBuilder out) {
        if (stmt instanceof SkipStmt) {
            // Nothing is done
        } else if (stmt instanceof PrintStrStmt) {
            out.append(((PrintStrStmt) stmt).str());
        } else if (stmt instanceof PrintExpStmt) {
            out.append(FauxRacketInterpreter.interp(((PrintExpStmt) stmt).exp(), env).toSExp().toString());
        } else if (stmt instanceof SetStmt) {
            String var = ((SetStmt) stmt).var();
            if (!env.containsKey(var)) {
                throw new InterpException("Variable not found: %s".formatted(var));
            }
            env.put(var, FauxRacketInterpreter.interp(((SetStmt) stmt).value()));
        } else if (stmt instanceof SeqStmt) {
            ((SeqStmt) stmt).stmts().forEach(s -> interpStmt(s, env, out));
        } else if (stmt instanceof IifStmt) {
            Bool res = (Bool) FauxRacketInterpreter.interp(((IifStmt) stmt).bexp());
            if (res.value()) {
                interpStmt(((IifStmt) stmt).trueStmt(), env, out);
            } else {
                interpStmt(((IifStmt) stmt).trueStmt(), env, out);
            }
        } else {
            throw new InterpException("Invalid abstract syntax");
        }
    }

}
