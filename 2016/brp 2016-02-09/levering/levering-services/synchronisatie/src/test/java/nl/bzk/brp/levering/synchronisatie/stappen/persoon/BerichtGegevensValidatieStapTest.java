/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.persoon;

import nl.bzk.brp.business.regels.BedrijfsregelManager;
import nl.bzk.brp.levering.synchronisatie.stappen.AbstractStappenTest;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.SoortFout;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class BerichtGegevensValidatieStapTest extends AbstractStappenTest {

    private BerichtGegevensValidatieStap stap = new BerichtGegevensValidatieStap();

    @Mock
    private BedrijfsregelManager bedrijfsregelManager;

    @Before
    public final void init() {
        ReflectionTestUtils.setField(stap, "bedrijfsregelManager", bedrijfsregelManager);
    }

    @Test
    public final void testVoerStapUit() {
        getOnderwerp().setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        getOnderwerp().getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut("302533928"));
        stap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertThat(getResultaat().isSuccesvol(), CoreMatchers.is(true));
    }

    @Test
    public final void testVoerStapUitInvalideBSN() {
        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(Regel.BRAL0012)).thenReturn(
            new RegelParameters(new MeldingtekstAttribuut("test"), SoortMelding.FOUT, Regel.BRAL0012, null,
                SoortFout.VERWERKING_KAN_DOORGAAN)
        );

        getOnderwerp().setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        getOnderwerp().getZoekcriteriaPersoon().setBurgerservicenummer(new BurgerservicenummerAttribuut("111111111"));
        stap.voerStapUit(getOnderwerp(), getBerichtContext(), getResultaat());

        Assert.assertThat(getResultaat().isSuccesvol(), CoreMatchers.is(false));
        Assert.assertEquals(Regel.BRAL0012, getResultaat().getMeldingen().iterator().next().getRegel());
    }
}
