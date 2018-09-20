/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc202;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.mvi.impl.PlSyncBericht;
import nl.moderniseringgba.isc.migratie.service.GemeenteService;
import nl.moderniseringgba.isc.migratie.service.Stelsel;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ControleerBijhoudingsverantwoordelijkheidDecisionTest {

    private final ControleerBijhoudingsverantwoordelijkheidDecision subject =
            new ControleerBijhoudingsverantwoordelijkheidDecision();
    private GemeenteService gemeenteServiceMock;

    @Before
    public void setUp() {
        gemeenteServiceMock = Mockito.mock(GemeenteService.class);
        subject.setGemeenteService(gemeenteServiceMock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testOk() {
        final PlSyncBericht plSync = new PlSyncBericht();
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper
                .lo3Verblijfplaats("1234", 20040101, 20040101, "Straat", 14, "1111AA", "K"), Lo3StapelHelper
                .lo3His(20040101), Lo3StapelHelper.lo3Akt(1), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0))));
        plSync.setLo3Persoonslijst(builder.build());

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", plSync);

        Mockito.when(gemeenteServiceMock.geefStelselVoorGemeente(1234)).thenReturn(Stelsel.GBA);

        Assert.assertEquals(null, subject.execute(parameters));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNok() {
        final PlSyncBericht plSync = new PlSyncBericht();
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.verblijfplaatsStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(Lo3StapelHelper
                .lo3Verblijfplaats("1234", 20040101, 20040101, "Straat", 14, "1111AA", "K"), Lo3StapelHelper
                .lo3His(20040101), Lo3StapelHelper.lo3Akt(1), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0))));
        plSync.setLo3Persoonslijst(builder.build());

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("input", plSync);

        Mockito.when(gemeenteServiceMock.geefStelselVoorGemeente(1234)).thenReturn(Stelsel.BRP);

        Assert.assertEquals("5a. Bijhoudingsverantwoordelijkheid niet bij LO3", subject.execute(parameters));
    }
}
