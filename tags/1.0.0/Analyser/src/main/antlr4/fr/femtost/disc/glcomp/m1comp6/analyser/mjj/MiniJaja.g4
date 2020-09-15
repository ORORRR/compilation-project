grammar MiniJaja;

classe
    : 'class' ident '{' decls methmain '}'
    ;

ident
    : ID
    ;

decls
    : decl ';' decls
    | // empty
    ;

decl
    : var
    ;

vars
    : var ';' vars
    | // empty
    ;

var
    : typemeth ident vexp
    ;

vexp
    : '=' exp
    | // empty
    ;

methmain
    : 'main' '{' vars instrs '}'
    ;

instrs
    : instr ';' instrs
    | //empty
    ;

instr
    : // empty
    ;

exp
    : exp1
    ;

exp1
    : exp2
    ;

exp2
    : terme
    ;

terme
    : fact
    ;

fact
    : 'true'
    | 'false'
    | NUMBER
    ;

typemeth
    : 'void'
    | type
    ;

type
    : 'int'
    | 'boolean'
    ;

ID
    : [a-zA-Z$_][a-zA-Z0-9$_]*
    ;

NUMBER
    : [0-9]+
    ;

INLINE_COMMENTS
    : '//' ~[\n\r]* -> skip
    ;

BLOCK_COMMENTS
    : '/*' .*? '*/' -> skip
    ;

WS
    : [ \t\n\r]+ -> skip
    ;