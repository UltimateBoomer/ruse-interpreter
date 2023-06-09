package io.github.ultimateboomer.ruseinterpreter.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.AbstractSyntax;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Bool;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.BoolExp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Exp;
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
import io.github.ultimateboomer.ruseinterpreter.model.simpl.WhileStmt;

public class SIMPLInterpreter {

    public static AbstractSyntax parse(SExp exp) {
        if (exp instanceof SList) {
            return parseSList((SList) exp);
        }

        throw new InterpException(String.format("Invalid SIMPL syntax: %s", exp));
    }

    private static AbstractSyntax parseSList(SList list) {
        if (list.exps().isEmpty()) {
            throw new InterpException("SList is empty");
        }

        SExp first = list.exps().get(0);

        if (first instanceof Atom) {
            if (((Atom) first).value().equals("vars")) {
                Map<String, Exp> vars = new HashMap<>();
                ((SList) list.exps().get(1)).exps().stream().forEach(e -> vars.put(((Atom) ((SList) e).exps().get(0)).value(),
                    (Exp) FauxRacketInterpreter.parse(((SList) e).exps().get(1))));
                List<Stmt> stmts = list.exps().stream().skip(2).map(s -> (Stmt) parse(s)).toList();
                return new VarDef(vars, stmts);
            } else if (((Atom) first).value().equals("skip")) {
                return new SkipStmt();
            } else if (((Atom) first).value().equals("print")) {
                SExp second = list.exps().get(1);
                
                if (second instanceof Atom) {
                    Matcher matcher = RuseCommon.strPattern.matcher(((Atom) second).value());
                    if (matcher.matches()) {
                        String str = matcher.group(1).translateEscapes();
                        return new PrintStrStmt(str);
                    }
                }

                return new PrintExpStmt((Exp) FauxRacketInterpreter.parse(second));
            } else if (((Atom) first).value().equals("iif")) {
                BoolExp bexp = (BoolExp) FauxRacketInterpreter.parse(list.exps().get(1));
                Stmt trueStmt = (Stmt) parse(list.exps().get(2));
                Stmt falseStmt = (Stmt) parse(list.exps().get(3));
                return new IifStmt(bexp, trueStmt, falseStmt);
            } else if (((Atom) first).value().equals("while")) {
                BoolExp bexp = (BoolExp) FauxRacketInterpreter.parse(list.exps().get(1));
                List<Stmt> stmts = list.exps().stream().skip(2).map(s -> (Stmt) parse(s)).toList();
                return new WhileStmt(bexp, stmts);
            } else if (((Atom) first).value().equals("set")) {
                String var = ((Atom) list.exps().get(1)).value();
                Exp value = (Exp) FauxRacketInterpreter.parse(list.exps().get(2));
                return new SetStmt(var, value);
            }
        }
        

        throw new InterpException(String.format("Invalid SIMPL syntax: %s", list));
    }

    public static void interp(VarDef stmt, StringBuilder out) {
        Map<String, Exp> env = stmt.vars();
        stmt.stmts().forEach(s -> interpStmt(s, env, out));
    }

    private static void interpStmt(Stmt stmt, Map<String, Exp> env, StringBuilder out) {
        if (stmt instanceof SkipStmt) {
            // Nothing is done
            return;
        } else if (stmt instanceof PrintStrStmt) {
            out.append(((PrintStrStmt) stmt).str());
            return;
        } else if (stmt instanceof PrintExpStmt) {
            out.append(FauxRacketInterpreter.interp(((PrintExpStmt) stmt).exp(), env).toSExp().toString());
            return;
        } else if (stmt instanceof SetStmt) {
            String var = ((SetStmt) stmt).var();
            if (!env.containsKey(var)) {
                throw new InterpException("Variable not found: %s".formatted(var));
            }
            env.put(var, FauxRacketInterpreter.interp(((SetStmt) stmt).value(), env));
            return;
        } else if (stmt instanceof SeqStmt) {
            ((SeqStmt) stmt).stmts().forEach(s -> interpStmt(s, env, out));
            return;
        } else if (stmt instanceof IifStmt) {
            Bool res = (Bool) FauxRacketInterpreter.interp(((IifStmt) stmt).bexp(), env);
            if (res.value()) {
                interpStmt(((IifStmt) stmt).trueStmt(), env, out);
            } else {
                interpStmt(((IifStmt) stmt).falseStmt(), env, out);
            }
            return;
        } else if (stmt instanceof WhileStmt) {
            while (((Bool) FauxRacketInterpreter.interp(((WhileStmt) stmt).bexp(), env)).value()) {
                ((WhileStmt) stmt).stmts().forEach(s -> interpStmt(s, env, out));
            }
            return;
        }

        throw new InterpException(String.format("Invalid SIMPL abstract syntax: %s", stmt));
    }

}
