/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.resultaat;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Test;

public class ResultaatTest {
    @Test
    public void testBuild() {
        final Resultaat resultaat = Resultaat.builder().build();
        assertNotNull(resultaat);
    }

    @Test
    public void testDeblokkeerMeldingen() {
        final ResultaatMelding meldingVoor = ResultaatMelding.builder()
            .withReferentieID("A")
            .withRegel(Regel.BRAL0001)
            .withSoort(SoortMelding.DEBLOKKEERBAAR).build();

        final Resultaat resultaatVoor = Resultaat.builder().withMeldingen(singleton(meldingVoor)).build();

        final Resultaat resultaatNa = resultaatVoor.deblokkeer(singletonList(meldingVoor));

        assertEquals(1, resultaatNa.getMeldingen().size());
        assertEquals(SoortMelding.WAARSCHUWING, resultaatNa.getMeldingen().iterator().next().getSoort());
    }

    @Test
    public void testVoegResultaatToe() {
        final AdministratieveHandelingModel administratieveHandelingModelMock1 = mock(AdministratieveHandelingModel.class);
        final AdministratieveHandelingGedeblokkeerdeMeldingBericht administratieveHandelingGedeblokkeerdeMeldingBerichtMock1
            = new AdministratieveHandelingGedeblokkeerdeMeldingBericht();
        final Resultaat eneResultaat =
            Resultaat.builder()
                .withMeldingen(singleton(ResultaatMelding.builder().withRegel(Regel.AUTH0001).build()))
                .withAdministratieveHandeling(administratieveHandelingModelMock1)
                .withTeArchiverenPersoonIdsIngaandBericht(asList(1, 2, 3))
                .withToegepasteDeblokkeermeldingen(singletonList(administratieveHandelingGedeblokkeerdeMeldingBerichtMock1))
                .build();
        final AdministratieveHandelingModel administratieveHandelingModelMock2 = mock(AdministratieveHandelingModel.class);
        final AdministratieveHandelingGedeblokkeerdeMeldingBericht administratieveHandelingGedeblokkeerdeMeldingBerichtMock2
            = new AdministratieveHandelingGedeblokkeerdeMeldingBericht();
        final Resultaat andereResultaat =
            Resultaat.builder()
                .withMeldingen(singleton(ResultaatMelding.builder().withRegel(Regel.ALG0001).build()))
                .withAdministratieveHandeling(administratieveHandelingModelMock2)
                .withTeArchiverenPersoonIdsIngaandBericht(asList(4, 5, 6))
                .withToegepasteDeblokkeermeldingen(singletonList(administratieveHandelingGedeblokkeerdeMeldingBerichtMock2))
                .build();

        final Resultaat resultaat = eneResultaat.voegToe(andereResultaat);

        assertThat(resultaat, is(not(eneResultaat)));
        assertThat(resultaat, is(not(andereResultaat)));
        assertThat(resultaat.getMeldingen().size(), is(2));
        assertThat(resultaat.getAdministratieveHandeling(), is(administratieveHandelingModelMock2));
        assertThat(resultaat.getTeArchiverenPersoonIdsIngaandBericht(), is(asList(1, 2, 3, 4, 5, 6)));
        assertThat(resultaat.getToegepasteDeblokkeerverzoeken(), is(asList(administratieveHandelingGedeblokkeerdeMeldingBerichtMock1,
            administratieveHandelingGedeblokkeerdeMeldingBerichtMock2)));
    }

    @Test
    public void testVoegResultaatMeldingToe() {
        final ResultaatMelding eneResultaatMelding = ResultaatMelding.builder().withRegel(Regel.AUTH0001).build();
        final Resultaat eneResultaat =
            Resultaat.builder()
                .withMeldingen(singleton(eneResultaatMelding))
                .build();

        final ResultaatMelding andereResultaatMelding = ResultaatMelding.builder().withRegel(Regel.ALG0001).build();
        final Resultaat andereResultaat = eneResultaat.voegToe(andereResultaatMelding);

        assertThat(eneResultaat, is(not(andereResultaat)));
        assertThat(andereResultaat.getMeldingen().size(), is(2));
        assertThat(andereResultaat.getMeldingen(), containsInAnyOrder(eneResultaatMelding, andereResultaatMelding));
    }

    @Test
    public void testBevatVerwerkingStoppendeMelding() {
        final Resultaat resultaat =
            Resultaat.builder()
                .withMeldingen(singleton(ResultaatMelding.builder().withRegel(Regel.AUTH0001).build()))
                .build();

        assertThat(resultaat.bevatVerwerkingStoppendeMelding(), is(true));
    }

    @Test
    public void testBevatGeenVerwerkingStoppendeMelding() {
        final Resultaat resultaat =
            Resultaat.builder()
                .withMeldingen(singleton(ResultaatMelding.builder().withSoort(SoortMelding.WAARSCHUWING).withRegel(Regel.VR00001).build()))
                .build();

        assertThat(resultaat.bevatVerwerkingStoppendeMelding(), is(false));
    }
}
