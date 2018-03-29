/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoon;

import static org.mockito.Mockito.when;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.ExceptionRegelMatcher;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * ZoekPersoonOphalenZoekPersoonServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class ZoekPersoonOphalenPersoonServiceImplTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private ZoekPersoonOphalenPersoonServiceImpl ophalenZoekPersoonPersoonService = new ZoekPersoonOphalenPersoonServiceImpl();
    @Mock
    private List<Persoonslijst> persoonslijstList;
    @Mock
    private Dienst dienst;

    @Test
    public void geeftRegelR2289BijTeveelResultaten() throws StapMeldingException {
        when(persoonslijstList.size()).thenReturn(10);
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(5);
        exception.expect(new ExceptionRegelMatcher(Regel.R2289));

        ophalenZoekPersoonPersoonService.valideerAantalZoekResultaten(persoonslijstList, new Autorisatiebundel(null, dienst), null);
    }

    @Test
    public void geeftRegelR2289BijTeveelResultatenDefaultMax() throws StapMeldingException {
        when(persoonslijstList.size()).thenReturn(11);
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(null);
        exception.expect(new ExceptionRegelMatcher(Regel.R2289));

        ophalenZoekPersoonPersoonService.valideerAantalZoekResultaten(persoonslijstList, new Autorisatiebundel(null, dienst), null);
    }

    @Test
    public void geenExceptionBijMinderDanMaxResultaten() throws StapMeldingException {
        when(persoonslijstList.size()).thenReturn(8);
        when(dienst.getMaximumAantalZoekresultaten()).thenReturn(null);

        ophalenZoekPersoonPersoonService.valideerAantalZoekResultaten(persoonslijstList, new Autorisatiebundel(null, dienst), null);
    }
}
