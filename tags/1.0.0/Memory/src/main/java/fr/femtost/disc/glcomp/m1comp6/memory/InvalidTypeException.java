package fr.femtost.disc.glcomp.m1comp6.memory;

public class InvalidTypeException extends Exception {
   public <T> InvalidTypeException(T value, SymbolNode parent) {
       super("The provided value \"" + String.valueOf(value) + "\" is not compatible with " + parent.getType() + " type.");
   }
}
