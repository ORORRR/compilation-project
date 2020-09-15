package fr.femtost.disc.glcomp.m1comp6.ast.common;

public interface JajaCodeASTVisitable extends Visitable {
    <T, E extends Throwable> T accept(JajaCodeASTVisitor<T, E> visitor, Object... args) throws E;
}
