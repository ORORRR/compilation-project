package fr.femtost.disc.glcomp.m1comp6.ast.common;

public interface MiniJajaASTVisitable extends Visitable {
    <T, E extends Throwable> T accept(MiniJajaASTVisitor<T, E> visitor, Object... args) throws E;
}
