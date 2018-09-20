/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.toegangsbewaking.ToegangsBewakingService;
import nl.bzk.brp.bevraging.domein.SoortPersoon;
import nl.bzk.brp.bevraging.domein.kern.Persoon;
import nl.bzk.brp.bevraging.domein.lev.Abonnement;
import nl.bzk.brp.toegangsbewaking.parser.ParserException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link HorizontaleAutorisatieAchterafStap} class.
 */
public class HorizontaleAutorisatieAchterafStapTest {

    @Mock
    private ToegangsBewakingService          toegangsBewakingService;
    @Mock
    private BerichtAntwoord                  antwoord;
    @Mock
    private BerichtContext                   context;

    private HorizontaleAutorisatieAchterafStap stap = null;

    @Test
    public void testHorizontaleAutorisatieMetAllemaalToegang() throws ParserException {
        Map<Long, Boolean> result = new HashMap<Long, Boolean>();
        result.put(1L, Boolean.TRUE);
        result.put(2L, Boolean.TRUE);
        result.put(3L, Boolean.TRUE);

        Mockito.when(
                toegangsBewakingService.controleerLijstVanPersonenVoorAbonnement(Matchers.any(Abonnement.class),
                        Matchers.anyListOf(Long.class))).thenReturn(result);

        assertEquals(3, antwoord.getPersonen().size());
        stap.voerVerwerkingsStapUitVoorBericht(null, context, antwoord);
        assertEquals(3, antwoord.getPersonen().size());
    }

    @Test
    public void testHorizontaleAutorisatieMetNiemandToegang() throws ParserException {
        Map<Long, Boolean> result = new HashMap<Long, Boolean>();
        result.put(1L, Boolean.FALSE);
        result.put(2L, Boolean.FALSE);
        result.put(3L, Boolean.FALSE);

        Mockito.when(
                toegangsBewakingService.controleerLijstVanPersonenVoorAbonnement(Matchers.any(Abonnement.class),
                        Matchers.anyListOf(Long.class))).thenReturn(result);

        assertEquals(3, antwoord.getPersonen().size());
        stap.voerVerwerkingsStapUitVoorBericht(null, context, antwoord);
        assertEquals(0, antwoord.getPersonen().size());
    }

    @Test
    public void testHorizontaleAutorisatieMetEenIemandToegang() throws ParserException {
        Map<Long, Boolean> result = new HashMap<Long, Boolean>();
        result.put(1L, Boolean.FALSE);
        result.put(2L, Boolean.TRUE);
        result.put(3L, Boolean.FALSE);

        Mockito.when(
                toegangsBewakingService.controleerLijstVanPersonenVoorAbonnement(Matchers.any(Abonnement.class),
                        Matchers.anyListOf(Long.class))).thenReturn(result);

        assertEquals(3, antwoord.getPersonen().size());
        stap.voerVerwerkingsStapUitVoorBericht(null, context, antwoord);
        assertEquals(1, antwoord.getPersonen().size());
        assertEquals(new Long(2), antwoord.getPersonen().iterator().next().getId());
    }

    @Test
    public void testHorizontaleAutorisatieMetParseerFoutInCriteria() throws ParserException {
        Mockito.when(
                toegangsBewakingService.controleerLijstVanPersonenVoorAbonnement(Matchers.any(Abonnement.class),
                        Matchers.anyListOf(Long.class))).thenThrow(new ParserException("Test"));
        ArgumentCaptor<BerichtVerwerkingsFout> fout = ArgumentCaptor.forClass(BerichtVerwerkingsFout.class);

        stap.voerVerwerkingsStapUitVoorBericht(null, context, antwoord);
        Mockito.verify(antwoord).voegFoutToe(fout.capture());

        assertEquals(BerichtVerwerkingsFoutCode.BRAU0046_01_POPULATIE_CRITERIA_PARSEER_FOUT.getCode(), fout.getValue()
                .getCode());
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        stap = new HorizontaleAutorisatieAchterafStap();
        ReflectionTestUtils.setField(stap, "toegangsBewakingService", toegangsBewakingService);

        Mockito.when(antwoord.getPersonen()).thenReturn(getTestPersonenLijst(1L, 2L, 3L));
    }

    private Collection<Persoon> getTestPersonenLijst(final Long... ids) {
        List<Persoon> personen = new ArrayList<Persoon>();
        for (Long id : ids) {
            Persoon persoon = new nl.bzk.brp.bevraging.domein.Persoon(SoortPersoon.INGESCHREVENE);
            ReflectionTestUtils.setField(persoon, "id", id);
            personen.add(persoon);
        }
        return personen;
    }

}
