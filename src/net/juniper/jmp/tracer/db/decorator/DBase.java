package net.juniper.jmp.tracer.db.decorator;
/**
 * From p6spy
 */
public abstract class DBase {
    private DecoratorFactory factory;

    public DBase(DecoratorFactory factory) {
        this.factory = factory;
    }

    public DecoratorFactory getDecoratorFactory() {
        return factory;
    }
}

