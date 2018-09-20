/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.bijhouding.ActualiseerAfstammingBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Test;

public class BevragingBerichtVerwerkerImplTest {

    private BevragingResultaat berichtResultaat = new BevragingResultaat(new ArrayList<Melding>());

    private BevragingBerichtVerwerkerImpl berichtVerwerker = new BevragingBerichtVerwerkerImpl();

    @Test
    public final void testValideerBerichtOpVerplichteObjectenGeenStuurgegevens() {
        berichtVerwerker.valideerBerichtOpVerplichteObjecten(new GeefDetailsPersoonBericht(),
            berichtResultaat);

        assertEquals(1, berichtResultaat.getMeldingen().size());
        assertEquals(SoortMelding.FOUT, berichtResultaat.getMeldingen().get(0).getSoort());
    }

    @Test
    public final void testValideerBerichtOpVerplichteObjectenFoutType() {
        final ActualiseerAfstammingBericht foutBericht = new ActualiseerAfstammingBericht();
        foutBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        berichtVerwerker.valideerBerichtOpVerplichteObjecten(foutBericht, berichtResultaat);

        assertEquals(1, berichtResultaat.getMeldingen().size());
        assertEquals(SoortMelding.FOUT, berichtResultaat.getMeldingen().get(0).getSoort());
    }

    @Test
    public final void testValideerBerichtOpVerplichteObjectenGeenZoekcriteriaPersoon() {
        final GeefDetailsPersoonBericht geefDetailsPersoonBericht = new GeefDetailsPersoonBericht();
        geefDetailsPersoonBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        berichtVerwerker.valideerBerichtOpVerplichteObjecten(geefDetailsPersoonBericht, berichtResultaat);

        assertEquals(1, berichtResultaat.getMeldingen().size());
        assertEquals(SoortMelding.FOUT, berichtResultaat.getMeldingen().get(0).getSoort());
    }

    @Test
    public final void testValideerBerichtOpVerplichteObjectenBerichtValide() {
        final GeefDetailsPersoonBericht geefDetailsPersoonBericht = new GeefDetailsPersoonBericht();
        geefDetailsPersoonBericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        geefDetailsPersoonBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        berichtVerwerker.valideerBerichtOpVerplichteObjecten(geefDetailsPersoonBericht, berichtResultaat);

        assertEquals(0, berichtResultaat.getMeldingen().size());
    }

}
