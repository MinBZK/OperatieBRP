package groovy.runtime.metaclass.nl.bzk.brp.util.hisvolledig.autaut

import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl
import nl.bzk.brp.util.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledigImplBuilder

/**
 * {@link DelegatingMetaClass} voor {@link PersoonAfnemerindicatieHisVolledigImplBuilder}
 * die het mogelijk maakt om een andere constructor dan de gecompileerde aan te roepen
 * in de DSL. Dit maakt het mogelijk om aan een bestaande groep nieuwe historie toe te
 * voegen door middel van de builder.
 */
class PersoonAfnemerindicatieHisVolledigImplBuilderMetaClass extends DelegatingMetaClass {

    PersoonAfnemerindicatieHisVolledigImplBuilderMetaClass(final MetaClass delegate) {
        super(delegate)
    }

    @Override
    Object invokeConstructor(final Object[] arguments) {
        if (arguments[0] instanceof PersoonAfnemerindicatieHisVolledigImpl) {
            def builder = new PersoonAfnemerindicatieHisVolledigImplBuilder(null, null)
            builder.hisVolledigImpl = (PersoonAfnemerindicatieHisVolledigImpl) arguments[0]

            return builder
        } else {
            return super.invokeConstructor(arguments)
        }
    }

}
