/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import java.util.ArrayList;
import java.util.Date;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.bericht.ber.AntwoordBericht;
import nl.bzk.brp.model.bericht.ber.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingCorrectieAdresBericht;
import nl.bzk.brp.model.bericht.kern.HandelingPlaatsingAfnemerindicatieBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerwijderingAfnemerindicatieBericht;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieAntwoordBericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Assert;
import org.junit.Test;

public class AntwoordBerichtFactoryImplTest {

    private AntwoordBerichtFactoryImpl antwoordBerichtFactory = new AntwoordBerichtFactoryImpl();

    @Test
    public final void testMaakInitieelBerichtResultaatGroepBericht() {
        final BerichtResultaatGroepBericht resultaatGroepBericht =
            antwoordBerichtFactory.maakInitieelBerichtResultaatGroepBericht(null, null);

        Assert.assertNotNull(resultaatGroepBericht);
    }

    @Test
    public final void testMaakInitieelAntwoordBerichtVoorInkomendBericht() {
        final Bericht bericht = new RegistreerAfnemerindicatieBericht();
        final AntwoordBericht antwoordBericht = antwoordBerichtFactory.maakInitieelAntwoordBerichtVoorInkomendBericht(bericht);

        Assert.assertTrue(antwoordBericht instanceof RegistreerAfnemerindicatieAntwoordBericht);
    }

    @Test(expected = IllegalStateException.class)
    public final void testMaakInitieelAntwoordBerichtVoorInkomendOnbekendBericht() {
        final Bericht bericht = new GeefDetailsPersoonBericht();
        antwoordBerichtFactory.maakInitieelAntwoordBerichtVoorInkomendBericht(bericht);
    }

    @Test
    public final void testVulAntwoordBerichtAanPlaatsing() {
        final PartijAttribuut partijAttribuut = new PartijAttribuut(TestPartijBuilder.maker().maak());

        final RegistreerAfnemerindicatieBericht inkomendBericht = new RegistreerAfnemerindicatieBericht();
        final BerichtStandaardGroepBericht standaardGroep = new BerichtStandaardGroepBericht();
        final AdministratieveHandelingBericht administratieveHandeling =
            new HandelingPlaatsingAfnemerindicatieBericht();
        administratieveHandeling.setPartij(partijAttribuut);
        administratieveHandeling.setPartijCode("123");
        administratieveHandeling.setToelichtingOntlening(new OntleningstoelichtingAttribuut("toelichting a"));
        standaardGroep.setAdministratieveHandeling(administratieveHandeling);
        inkomendBericht.setStandaard(standaardGroep);

        final AntwoordBericht antwoordBericht = new RegistreerAfnemerindicatieAntwoordBericht();

        // Coverage
        final OnderhoudAfnemerindicatiesResultaat berichtVerwerkingsResultaat = new OnderhoudAfnemerindicatiesResultaat(new ArrayList<Melding>());
        berichtVerwerkingsResultaat.setTijdstipRegistratie(new Date());
        antwoordBerichtFactory.vulAntwoordBerichtAan(berichtVerwerkingsResultaat, inkomendBericht, antwoordBericht);

        Assert.assertNotNull(antwoordBericht.getStandaard());
        Assert.assertNotNull(antwoordBericht.getStandaard().getAdministratieveHandeling());
        Assert.assertTrue(antwoordBericht.getStandaard().getAdministratieveHandeling()
            instanceof HandelingPlaatsingAfnemerindicatieBericht);
        Assert.assertEquals(inkomendBericht.getStandaard().getAdministratieveHandeling().getPartij(),
            antwoordBericht.getStandaard().getAdministratieveHandeling().getPartij());
        Assert.assertEquals(inkomendBericht.getStandaard().getAdministratieveHandeling().getPartijCode(),
            antwoordBericht.getStandaard().getAdministratieveHandeling().getPartijCode());
        Assert.assertEquals(inkomendBericht.getStandaard().getAdministratieveHandeling().getToelichtingOntlening(),
            antwoordBericht.getStandaard().getAdministratieveHandeling().getToelichtingOntlening());
    }

    @Test
    public final void testVulAntwoordBerichtAanVerwijdering() {
        final PartijAttribuut partijAttribuut = new PartijAttribuut(TestPartijBuilder.maker().maak());

        final RegistreerAfnemerindicatieBericht inkomendBericht = new RegistreerAfnemerindicatieBericht();
        final BerichtStandaardGroepBericht standaardGroep = new BerichtStandaardGroepBericht();
        final AdministratieveHandelingBericht administratieveHandeling =
            new HandelingVerwijderingAfnemerindicatieBericht();
        administratieveHandeling.setPartij(partijAttribuut);
        administratieveHandeling.setPartijCode("12345");
        administratieveHandeling.setToelichtingOntlening(new OntleningstoelichtingAttribuut("toelichting b"));
        standaardGroep.setAdministratieveHandeling(administratieveHandeling);
        inkomendBericht.setStandaard(standaardGroep);

        final AntwoordBericht antwoordBericht = new RegistreerAfnemerindicatieAntwoordBericht();

        // Coverage
        final OnderhoudAfnemerindicatiesResultaat berichtVerwerkingsResultaat = new OnderhoudAfnemerindicatiesResultaat(new ArrayList<Melding>());
        berichtVerwerkingsResultaat.setTijdstipRegistratie(new Date());
        antwoordBerichtFactory.vulAntwoordBerichtAan(berichtVerwerkingsResultaat, inkomendBericht, antwoordBericht);

        Assert.assertNotNull(antwoordBericht.getStandaard());
        Assert.assertNotNull(antwoordBericht.getStandaard().getAdministratieveHandeling());
        Assert.assertTrue(antwoordBericht.getStandaard().getAdministratieveHandeling()
            instanceof HandelingVerwijderingAfnemerindicatieBericht);
    }

    @Test(expected = IllegalStateException.class)
    public final void testVulAntwoordBerichtAanOnbekendeAdministratieveHandeling() {
        final PartijAttribuut partijAttribuut = new PartijAttribuut(TestPartijBuilder.maker().maak());

        final RegistreerAfnemerindicatieBericht inkomendBericht = new RegistreerAfnemerindicatieBericht();
        final BerichtStandaardGroepBericht standaardGroep = new BerichtStandaardGroepBericht();
        final AdministratieveHandelingBericht administratieveHandeling =
            new HandelingCorrectieAdresBericht();
        administratieveHandeling.setPartij(partijAttribuut);
        administratieveHandeling.setPartijCode("235");
        administratieveHandeling.setToelichtingOntlening(new OntleningstoelichtingAttribuut("toelichting c"));
        standaardGroep.setAdministratieveHandeling(administratieveHandeling);
        inkomendBericht.setStandaard(standaardGroep);

        final AntwoordBericht antwoordBericht = new RegistreerAfnemerindicatieAntwoordBericht();

        // Coverage
        antwoordBerichtFactory.vulAntwoordBerichtAan(null, inkomendBericht, antwoordBericht);
    }
}
