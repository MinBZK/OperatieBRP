/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

/**
 * StappenlijstFactoryBeanTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class StappenlijstFactoryBeanTest {


    @InjectMocks
    private StappenlijstFactoryBean stappenlijstFactoryBean;

    @Mock
    private ApplicationContext applicationContext;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void test() {
        final Stappenlijst stappenlijst = stappenlijstFactoryBean.maakStappen();
        stappenlijst.getStappen();
        final InOrder inOrder = Mockito.inOrder(applicationContext);
        final Class[] classes = {
                KandidaatRecordBepaling.class,
                AutoriseerAttributenObvAttribuutAutorisatieServiceImpl.class,
                AutoriseerHistorieEnVerantwoordingAttribuutServiceImpl.class,
                AutoriseerObjectenWaarvoorOnderliggendeAutorisatieBestaatServiceImpl.class,
                AutoriseerAttributenObvAboPartijServiceImpl.class,
                VerwijderPreRelatieGegevensServiceImpl.class,
                VerwijderGerelateerdeOuderServiceImpl.class,
                BerichtRestrictieStap.class,
                VerwijderMigratieGroepServiceImpl.class,
                GeefDetailsPersoonCorrigeerVoorScoping.class,
                VerwijderAfnemerindicatieServiceImpl.class,
                GeefDetailsPersoonOpschonenObjecten.class,
                AutoriseerOnderzoekServiceImpl.class,
                MutatieleveringBehoudIdentificerendeGegevensServiceImpl.class,
                VerantwoordingAutorisatieServiceImpl.class,
                AutoriseerGedeblokkeerdeMeldingenServiceImpl.class,
                ObjectsleutelVersleutelaarServiceImpl.class,
                LeegBerichtBepalerServiceImpl.class,
        };

        for (Class aClass : classes) {
            inOrder.verify(applicationContext).getBean(aClass);
        }
        inOrder.verifyNoMoreInteractions();
    }
}
