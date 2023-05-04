package io.github.ultimateboomer.ruseinterpreter.model.ruse;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Num.class, name = "num"),
    @JsonSubTypes.Type(value = ArithBin.class, name = "abin")
})
public interface RuseAbstractSyntax {
    
    SExp toSExp();

}
