grammar MiniJaja;

classe
    : 'class' ident '{' decls methmain '}' EOF
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
    | methode
    ;

vars
    : var ';' vars
    | // empty
    ;

var
    : typemeth ident vexp
    | typemeth ident '[' exp ']'
    | 'final' type ident vexp
    ;

vexp
    : '=' exp
    | // empty
    ;

methode
    : typemeth ident '(' entetes ')' '{' vars instrs '}'
    ;

methmain
    : 'main' '{' vars instrs '}'
    ;

entetes
    : entete ',' entetes
    | entete
    | // empty
    ;

entete
    : type ident
    ;

instrs
    : instr ';' instrs
    | // empty
    ;

instr
    : ident1 '=' exp
    | ident1 '+=' exp
    | ident1 '++'
    | ident '(' listexp ')'
    | 'return' exp
    | 'if' exp '{' instrs '}' ('else' '{' instrs '}')?
    | 'while' '(' exp ')' '{' instrs '}'
    ;

listexp
    : exp ',' listexp
    | exp
    | // empty
    ;

exp
    : '!' exp1
    | exp '&&' exp1
    | exp '||' exp1
    | exp1
    ;

exp1
    : exp1 '==' exp2
    | exp1 '>' exp2
    | exp2
    ;

exp2
    : exp2 '+' terme
    | exp2 '-' terme
    | '-' terme
    | terme
    ;

terme
    : terme '*' fact
    | terme '/' fact
    | fact
    ;

fact
    : ident1
    | ident '(' listexp ')'
    | 'true'
    | 'false'
    | NUMBER
    | '(' exp ')'
    ;

ident1
    : ident
    | ident '[' exp ']'
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
