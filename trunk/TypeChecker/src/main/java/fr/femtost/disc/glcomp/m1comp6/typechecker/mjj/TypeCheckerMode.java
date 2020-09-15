package fr.femtost.disc.glcomp.m1comp6.typechecker.mjj;

public enum TypeCheckerMode {
    ANY,        // Any kind
    VALUE,      // Variable and constant
    VARIABLE,   // Variable only
    CONSTANT,   // Constant only
    VCONSTANT,  // VConstant only
    ARRAY,      // Array only
    METHOD      // Method only
}
