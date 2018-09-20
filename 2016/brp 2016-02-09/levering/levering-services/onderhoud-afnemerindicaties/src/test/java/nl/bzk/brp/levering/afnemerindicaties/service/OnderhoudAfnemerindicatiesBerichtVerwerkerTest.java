/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAfnemerindicatieBericht;
import nl.bzk.brp.model.bericht.kern.HandelingPlaatsingAfnemerindicatieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Assert;
import org.junit.Test;

public class OnderhoudAfnemerindicatiesBerichtVerwerkerTest {

    private RegistreerAfnemerindicatieBericht registreerAfnemerindicatieBericht = new RegistreerAfnemerindicatieBericht();

    private OnderhoudAfnemerindicatiesResultaat onderhoudAfnemerindicatiesResultaat = new OnderhoudAfnemerindicatiesResultaat(new ArrayList<Melding>());

    private OnderhoudAfnemerindicatiesBerichtVerwerker onderhoudAfnemerindicatiesBerichtVerwerker = new OnderhoudAfnemerindicatiesBerichtVerwerker();

    @Test
    public final void testValideerBerichtOpVerplichteObjectenGeenStuurgegevens() {
        onderhoudAfnemerindicatiesBerichtVerwerker
            .valideerBerichtOpVerplichteObjecten(registreerAfnemerindicatieBericht, onderhoudAfnemerindicatiesResultaat);

        Assert.assertEquals(1, onderhoudAfnemerindicatiesResultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT, onderhoudAfnemerindicatiesResultaat.getMeldingen().get(0).getSoort());
    }

    @Test
    public final void testValideerBerichtOpVerplichteObjectenGeenAdministratieveHandeling() {
        registreerAfnemerindicatieBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());

        onderhoudAfnemerindicatiesBerichtVerwerker
            .valideerBerichtOpVerplichteObjecten(registreerAfnemerindicatieBericht, onderhoudAfnemerindicatiesResultaat);

        Assert.assertEquals(1, onderhoudAfnemerindicatiesResultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT, onderhoudAfnemerindicatiesResultaat.getMeldingen().get(0).getSoort());
    }

    @Test
    public final void testValideerBerichtOpVerplichteObjectenGeenActies() {
        registreerAfnemerindicatieBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        registreerAfnemerindicatieBericht.getStandaard().setAdministratieveHandeling(new HandelingPlaatsingAfnemerindicatieBericht());

        onderhoudAfnemerindicatiesBerichtVerwerker
            .valideerBerichtOpVerplichteObjecten(registreerAfnemerindicatieBericht, onderhoudAfnemerindicatiesResultaat);

        Assert.assertEquals(1, onderhoudAfnemerindicatiesResultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT, onderhoudAfnemerindicatiesResultaat.getMeldingen().get(0).getSoort());
    }

    @Test
    public final void testValideerBerichtOpVerplichteObjectenGeenRootObject() {
        registreerAfnemerindicatieBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        registreerAfnemerindicatieBericht.getStandaard().setAdministratieveHandeling(new HandelingPlaatsingAfnemerindicatieBericht());
        final List<ActieBericht> acties = new ArrayList<>();
        acties.add(new ActieRegistratieAfnemerindicatieBericht());
        registreerAfnemerindicatieBericht.getStandaard().getAdministratieveHandeling().setActies(acties);

        onderhoudAfnemerindicatiesBerichtVerwerker
            .valideerBerichtOpVerplichteObjecten(registreerAfnemerindicatieBericht, onderhoudAfnemerindicatiesResultaat);

        Assert.assertEquals(1, onderhoudAfnemerindicatiesResultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT, onderhoudAfnemerindicatiesResultaat.getMeldingen().get(0).getSoort());
    }

    @Test
    public final void testValideerBerichtOpVerplichteObjectenIsValide() {
        registreerAfnemerindicatieBericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        registreerAfnemerindicatieBericht.getStandaard().setAdministratieveHandeling(new HandelingPlaatsingAfnemerindicatieBericht());
        final List<ActieBericht> acties = new ArrayList<>();
        acties.add(new ActieRegistratieAfnemerindicatieBericht());
        registreerAfnemerindicatieBericht.getStandaard().getAdministratieveHandeling().setActies(acties);
        registreerAfnemerindicatieBericht.getStandaard().getAdministratieveHandeling().getHoofdActie().setRootObject(new PersoonBericht());

        onderhoudAfnemerindicatiesBerichtVerwerker
            .valideerBerichtOpVerplichteObjecten(registreerAfnemerindicatieBericht, onderhoudAfnemerindicatiesResultaat);

        Assert.assertEquals(0, onderhoudAfnemerindicatiesResultaat.getMeldingen().size());
    }

}
