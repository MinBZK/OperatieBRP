package groovy.runtime.metaclass.nl.bzk.brp.util.hisvolledig.kern

import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder

/**
 * {@link DelegatingMetaClass} voor {@link PersoonVoornaamHisVolledigImplBuilder}
 * die het mogelijk maakt om een andere constructor dan de gecompileerde aan te roepen
 * in de DSL. Dit maakt het mogelijk om aan een bestaande groep nieuwe historie toe te
 * voegen door middel van de builder.
 */
class PersoonVoornaamHisVolledigImplBuilderMetaClass extends DelegatingMetaClass {
    PersoonVoornaamHisVolledigImplBuilderMetaClass(final MetaClass delegate) {
        super(delegate)
    }

    @Override
    Object invokeConstructor(final Object[] arguments) {
        if (arguments[0] instanceof PersoonVoornaamHisVolledigImpl) {
            def builder = new PersoonVoornaamHisVolledigImplBuilder(null)
            builder.hisVolledigImpl = (PersoonVoornaamHisVolledigImpl) arguments[0]

            return builder
        } else {
            return super.invokeConstructor(arguments)
        }
    }
}
