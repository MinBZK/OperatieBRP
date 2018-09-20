package groovy.runtime.metaclass.nl.bzk.brp.util.hisvolledig.kern

import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder

/**
 * {@link DelegatingMetaClass} voor {@link HuwelijkHisVolledigImplBuilder}
 * die het mogelijk maakt om een andere constructor dan de gecompileerde aan te roepen
 * in de DSL. Dit maakt het mogelijk om aan een bestaande groep nieuwe historie toe te
 * voegen door middel van de builder.
 */
class HuwelijkHisVolledigImplBuilderMetaClass extends DelegatingMetaClass {
    HuwelijkHisVolledigImplBuilderMetaClass(final MetaClass delegate) {
        super(delegate)
    }

    @Override
    Object invokeConstructor(final Object[] arguments) {
        if (arguments && arguments[0] instanceof HuwelijkHisVolledigImpl) {
            def builder = new HuwelijkHisVolledigImplBuilder()
            builder.hisVolledigImpl = (HuwelijkHisVolledigImpl) arguments[0]

            return builder
        } else {
            return super.invokeConstructor(arguments)
        }
    }

}
