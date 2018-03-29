/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.sleutelrubrieken;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Dummy implementatie van de expressie service factory.
 */
public final class DummyExpressieServiceFactory implements FactoryBean<ExpressieService>, InitializingBean {

    private ExpressieService expressieService;

    @Override
    public void afterPropertiesSet() throws Exception {
        final InvocationHandler handler = new InvocationHandler() {

            @Override
            public Object invoke(final Object arg0, final Method arg1, final Object[] arg2) {
                throw new UnsupportedOperationException();
            }
        };
        expressieService =
                (ExpressieService) Proxy.newProxyInstance(ExpressieService.class.getClassLoader(), new Class<?>[] {ExpressieService.class }, handler);

    }

    @Override
    public ExpressieService getObject() throws Exception {
        return expressieService;
    }

    @Override
    public Class<?> getObjectType() {
        return ExpressieService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
