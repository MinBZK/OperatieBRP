package groovy.runtime.metaclass.nl.bzk.brp.util.hisvolledig.kern

import nl.bzk.brp.model.hisvolledig.impl.kern.GeregistreerdPartnerschapHisVolledigImpl
import nl.bzk.brp.util.hisvolledig.kern.GeregistreerdPartnerschapHisVolledigImplBuilder

/**
 * {@link DelegatingMetaClass} voor {@ GeregistreerdPartnerschapHisVolledigImplBuilder}
 * die het mogelijk maakt om een andere constructor dan de gecompileerde aan te roepen
 * in de DSL. Dit maakt het mogelijk om aan een bestaande groep nieuwe historie toe te
 * voegen door middel van de builder.
 */
class GeregistreerdPartnerschapHisVolledigImplBuilderMetaClass extends DelegatingMetaClass {
    GeregistreerdPartnerschapHisVolledigImplBuilderMetaClass(final MetaClass delegate) {
        super(delegate)
    }

    @Override
    Object invokeConstructor(final Object[] arguments) {
        if (arguments && arguments[0] instanceof GeregistreerdPartnerschapHisVolledigImpl) {
            def builder = new GeregistreerdPartnerschapHisVolledigImplBuilder()
            builder.hisVolledigImpl = (GeregistreerdPartnerschapHisVolledigImpl) arguments[0]

            return builder
        } else {
            return super.invokeConstructor(arguments)
        }
    }

}
