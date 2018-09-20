/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.levering;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.bericht.ber.MeldingBericht;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;


public class AbstractSynchronisatieBerichtTest {

    @Test
    public void testMaximaalEenMeldingInLijst() {
        // arrange
        final AbstractSynchronisatieBericht bericht = new VolledigBericht(null);

        final MeldingBericht melding1 = new MeldingBericht();
        melding1.setRegel(new RegelAttribuut(Regel.ALG0001));
        melding1.setSoort(new SoortMeldingAttribuut(SoortMelding.INFORMATIE));
        melding1.setMelding(new MeldingtekstAttribuut("Test Melding"));

        final MeldingBericht melding2 = new MeldingBericht();
        melding2.setRegel(new RegelAttribuut(Regel.ALG0002));
        melding2.setSoort(new SoortMeldingAttribuut(SoortMelding.WAARSCHUWING));
        melding2.setMelding(new MeldingtekstAttribuut("Test Melding"));

        // assert
        assertTrue(bericht.getMeldingen().isEmpty());
        assertFalse(bericht.heeftMeldingenVoorLeveren());

        // act
        bericht.addMelding(melding1);
        bericht.addMelding(melding2);

        // assert
        assertThat(bericht.getMeldingen().size(), is(2));
        assertThat(bericht.getMeldingen().get(0), equalTo(melding1));
        assertThat(bericht.heeftMeldingenVoorLeveren(), is(true));
    }

    @Test
    public void kanPersonenToevoegen() {
        final AdministratieveHandelingModel administratieveHandeling =
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON),
                        StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA, null, DatumTijdAttribuut.nu());

        final AbstractSynchronisatieBericht bericht =
                new VolledigBericht(new AdministratieveHandelingSynchronisatie(administratieveHandeling));

        assertThat(bericht.heeftPersonen(), is(false));

        final int index = bericht.addPersoon(new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null));

        assertThat(index, is(1));
        assertThat(bericht.heeftPersonen(), is(true));
    }
}
