/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.services.dsl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import nl.bzk.brp.datataal.dataaccess.SpringBeanProvider;
import nl.bzk.brp.testrunner.component.services.datatoegang.DatabaseVerzoek;
import nl.bzk.brp.testrunner.component.services.datatoegang.VerzoekMetEntityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class DslVerzoekFactoryImpl implements DslVerzoekFactory {

    @Inject
    private ApplicationContext applicationContext;

    @Override
    public DatabaseVerzoek maakDSLVerzoek(final String dsl) {
        return new VerzoekMetEntityManager() {
            @Override
            public void voerUitMet(final EntityManager entityManager) {
                try {
                    SpringBeanProvider.setContext(applicationContext);
                    new PersoonDSLExecutor().execute(dsl);
                } finally {
                    SpringBeanProvider.setContext(null);
                }
            }
        };
    }

    @Override
    public DatabaseVerzoek maakDSLVerzoek(final Resource dsl) {
        return new VerzoekMetEntityManager() {
            @Override
            public void voerUitMet(final EntityManager entityManager) {
                try {
                    SpringBeanProvider.setContext(applicationContext);
                    new PersoonDSLExecutor().execute(dsl);
                } finally {
                    SpringBeanProvider.setContext(null);
                }
            }
        };
    }
}
