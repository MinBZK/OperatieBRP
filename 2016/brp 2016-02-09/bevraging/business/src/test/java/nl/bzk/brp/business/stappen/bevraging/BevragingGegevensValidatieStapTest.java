/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging;

import java.util.ArrayList;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.regels.BedrijfsregelManager;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.SoortFout;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class BevragingGegevensValidatieStapTest {

    private final BevragingGegevensValidatieStap bevragingGegevensValidatieStap = new BevragingGegevensValidatieStap();

    @Mock
    private BedrijfsregelManager bedrijfsregelManager;

    @Before
    public final void init() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(bevragingGegevensValidatieStap, "bedrijfsregelManager", bedrijfsregelManager);
        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(Regel.BRAL0012))
                .thenReturn(new RegelParameters(null, SoortMelding.FOUT, Regel.BRAL0012, null, SoortFout.VERWERKING_KAN_DOORGAAN));
    }

    @Test
    public final void testOngeldigeBsn() {
        final GeefDetailsPersoonBericht bericht = new GeefDetailsPersoonBericht();
        bericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        bericht.getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut("123456789"));
        final BevragingBerichtContext context = new BevragingBerichtContextBasis(new BerichtenIds(1L, 1L),
                                                                           Mockito.mock(Partij.class), "ref", null);
        final BevragingResultaat resultaat = new BevragingResultaat(new ArrayList<Melding>());
        bevragingGegevensValidatieStap.voerStapUit(bericht, context, resultaat);
        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
        final Melding melding = resultaat.getMeldingen().get(0);
        Assert.assertEquals(Regel.BRAL0012, melding.getRegel());
        Assert.assertEquals(SoortMelding.FOUT, melding.getSoort());
    }
}


