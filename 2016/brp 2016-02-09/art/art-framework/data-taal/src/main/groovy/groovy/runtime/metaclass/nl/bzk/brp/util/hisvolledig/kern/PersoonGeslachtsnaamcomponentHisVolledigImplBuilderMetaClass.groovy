package groovy.runtime.metaclass.nl.bzk.brp.util.hisvolledig.kern

import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl
import nl.bzk.brp.util.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigImplBuilder

/**
 * {@link DelegatingMetaClass} voor {@link PersoonGeslachtsnaamcomponentHisVolledigImplBuilder}
 * die het mogelijk maakt om een andere constructor dan de gecompileerde aan te roepen
 * in de DSL. Dit maakt het mogelijk om aan een bestaande groep nieuwe historie toe te
 * voegen door middel van de builder.
 */
class PersoonGeslachtsnaamcomponentHisVolledigImplBuilderMetaClass extends DelegatingMetaClass {
    PersoonGeslachtsnaamcomponentHisVolledigImplBuilderMetaClass(final MetaClass delegate) {
        super(delegate)
    }

    @Override
    Object invokeConstructor(final Object[] arguments) {
        if (arguments[0] instanceof PersoonGeslachtsnaamcomponentHisVolledigImpl) {
            def builder = new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(null)
            builder.hisVolledigImpl = (PersoonGeslachtsnaamcomponentHisVolledigImpl) arguments[0]

            return builder
        } else {
            return super.invokeConstructor(arguments)
        }
    }

}
