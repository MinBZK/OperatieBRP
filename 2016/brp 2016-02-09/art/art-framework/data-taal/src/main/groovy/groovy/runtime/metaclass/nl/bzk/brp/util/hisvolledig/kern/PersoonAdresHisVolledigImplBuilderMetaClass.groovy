package groovy.runtime.metaclass.nl.bzk.brp.util.hisvolledig.kern

import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder

/**
 * {@link DelegatingMetaClass} voor {@link PersoonAdresHisVolledigImplBuilder}
 * die het mogelijk maakt om een andere constructor dan de gecompileerde aan te roepen
 * in de DSL. Dit maakt het mogelijk om aan een bestaande groep nieuwe historie toe te
 * voegen door middel van de builder.
 */
class PersoonAdresHisVolledigImplBuilderMetaClass extends DelegatingMetaClass {
    PersoonAdresHisVolledigImplBuilderMetaClass(final MetaClass delegate) {
        super(delegate)
    }

    @Override
    Object invokeConstructor(final Object[] arguments) {
        if (arguments[0] instanceof PersoonAdresHisVolledigImpl) {
            def builder = new PersoonAdresHisVolledigImplBuilder(null)
            builder.hisVolledigImpl = (PersoonAdresHisVolledigImpl) arguments[0]

            return builder
        } else {
            return super.invokeConstructor(arguments)
        }
    }

}
