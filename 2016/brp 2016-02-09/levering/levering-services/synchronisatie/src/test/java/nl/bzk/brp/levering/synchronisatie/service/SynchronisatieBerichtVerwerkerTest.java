/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.service;

import java.util.ArrayList;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.StamgegevenAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatieStamgegevenBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Assert;
import org.junit.Test;

public class SynchronisatieBerichtVerwerkerTest {

    private SynchronisatieResultaat berichtResultaat = new SynchronisatieResultaat(new ArrayList<Melding>());

    private SynchronisatieBerichtVerwerker synchronisatieBerichtVerwerker = new SynchronisatieBerichtVerwerker();

    @Test
    public final void testValideerBerichtOpVerplichteObjectenGeenStuurgegevens() {
        synchronisatieBerichtVerwerker.valideerBerichtOpVerplichteObjecten(new GeefSynchronisatieStamgegevenBericht(), berichtResultaat);

        Assert.assertEquals(1, berichtResultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT, berichtResultaat.getMeldingen().get(0).getSoort());
    }

    @Test
    public final void testValideerBerichtOpVerplichteObjectenGeenZoekcriteriaPersoonVoorSynhroniseerPersoon() {
        final GeefSynchronisatiePersoonBericht geefSynchronisatiePersoonBericht = new GeefSynchronisatiePersoonBericht();
        geefSynchronisatiePersoonBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());

        synchronisatieBerichtVerwerker.valideerBerichtOpVerplichteObjecten(geefSynchronisatiePersoonBericht, berichtResultaat);

        Assert.assertEquals(1, berichtResultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT, berichtResultaat.getMeldingen().get(0).getSoort());
    }

    @Test
    public final void testValideerBerichtOpVerplichteObjectenGeenParametersSynchroniseerStamgegeven() {
        final GeefSynchronisatieStamgegevenBericht geefSynchronisatieStamgegevenBericht = new GeefSynchronisatieStamgegevenBericht();
        geefSynchronisatieStamgegevenBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());

        synchronisatieBerichtVerwerker.valideerBerichtOpVerplichteObjecten(geefSynchronisatieStamgegevenBericht, berichtResultaat);

        Assert.assertEquals(1, berichtResultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT, berichtResultaat.getMeldingen().get(0).getSoort());
    }

    @Test
    public final void testValideerBerichtOpVerplichteObjectenGeenParameterWaardeSynchroniseerStamgegeven() {
        final GeefSynchronisatieStamgegevenBericht geefSynchronisatieStamgegevenBericht = new GeefSynchronisatieStamgegevenBericht();
        geefSynchronisatieStamgegevenBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        geefSynchronisatieStamgegevenBericht.setParameters(new BerichtParametersGroepBericht());

        synchronisatieBerichtVerwerker.valideerBerichtOpVerplichteObjecten(geefSynchronisatieStamgegevenBericht, berichtResultaat);

        Assert.assertEquals(1, berichtResultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT, berichtResultaat.getMeldingen().get(0).getSoort());
    }

    @Test
    public final void testValideerBerichtOpVerplichteObjectenStamgegevenBerichtValide() {
        final GeefSynchronisatieStamgegevenBericht geefSynchronisatieStamgegevenBericht = new GeefSynchronisatieStamgegevenBericht();
        geefSynchronisatieStamgegevenBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        geefSynchronisatieStamgegevenBericht.setParameters(new BerichtParametersGroepBericht());
        geefSynchronisatieStamgegevenBericht.getParameters().setStamgegeven(new StamgegevenAttribuut("test"));

        synchronisatieBerichtVerwerker.valideerBerichtOpVerplichteObjecten(geefSynchronisatieStamgegevenBericht, berichtResultaat);

        Assert.assertEquals(0, berichtResultaat.getMeldingen().size());
    }

    @Test
    public final void testValideerBerichtOpVerplichteObjectenPersoonBerichtValide() {
        final GeefSynchronisatiePersoonBericht geefSynchronisatiePersoonBericht = new GeefSynchronisatiePersoonBericht();
        geefSynchronisatiePersoonBericht.setZoekcriteriaPersoon(new BerichtZoekcriteriaPersoonGroepBericht());
        geefSynchronisatiePersoonBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());

        synchronisatieBerichtVerwerker.valideerBerichtOpVerplichteObjecten(geefSynchronisatiePersoonBericht, berichtResultaat);

        Assert.assertEquals(0, berichtResultaat.getMeldingen().size());
    }

}
