package groovy.runtime.metaclass.nl.bzk.brp.util.hisvolledig.kern

import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder

/**
 * {@link DelegatingMetaClass} voor {@link PersoonHisVolledigImplBuilder}
 * die het mogelijk maakt om een andere constructor dan de gecompileerde aan te roepen
 * in de DSL. Dit maakt het mogelijk om aan een bestaande persoon nieuwe historie toe te
 * voegen door middel van de builder.
 */
class PersoonHisVolledigImplBuilderMetaClass extends DelegatingMetaClass {

    PersoonHisVolledigImplBuilderMetaClass(final MetaClass delegate) {
        super(delegate)
    }

    @Override
    Object invokeConstructor(final Object[] arguments) {
        if (arguments[0] instanceof PersoonHisVolledigImpl) {
            def builder = new PersoonHisVolledigImplBuilder(null)
            builder.hisVolledigImpl = (PersoonHisVolledigImpl) arguments[0]

            return builder
        } else {
            return super.invokeConstructor(arguments)
        }
    }
}
