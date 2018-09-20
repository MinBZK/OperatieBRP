/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.GemeenteCode;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatieNummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class BijhoudingGegevensValidatieStapTest extends AbstractStapTest {

    private final BijhoudingGegevensValidatieStap bijhoudingGegevensValidatieStap =
                                                                                      new BijhoudingGegevensValidatieStap();

    /** Tot nu toe gevalideerde velden: BSN, SoortAdres, Datum */
    @Test
    public void testBerichtMetOngeldigeGegevens() {
        AbstractBijhoudingsBericht bericht = maakNieuwOngeldigBericht();
        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(4, resultaat.getMeldingen().size());

        Assert.assertNotNull(zoekMelding(MeldingCode.BRAL0012, resultaat.getMeldingen()));
        Assert.assertNotNull(zoekMelding(MeldingCode.BRAL2032, resultaat.getMeldingen()));
        Assert.assertNotNull(zoekMelding(MeldingCode.BRAL2033, resultaat.getMeldingen()));
        Assert.assertNotNull(zoekMelding(MeldingCode.BRAL0102, resultaat.getMeldingen()));
    }

    @Test
    public void testBerichtMetGeldigeGegevens() {
        AbstractBijhoudingsBericht bericht = maakNieuwGeldigBericht();

        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        for (Melding melding : resultaat.getMeldingen()) {
            System.out.println("Melding: " + melding.getCode() + " " + melding.getOmschrijving());
        }
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtMetActieZonderRootObject() {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {
        };

        ActieBericht actie = new ActieBericht();
        actie.setRootObjecten(new ArrayList<RootObject>());
        bericht.setBrpActies(Arrays.asList((Actie) actie));

        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertFalse(resultaat.getMeldingen().isEmpty());

        for (Melding melding : resultaat.getMeldingen()) {
            System.out.println("Melding: " + melding.getCode() + " " + melding.getOmschrijving());
        }
    }

    /**
     * Instantieert een bericht met ongeldige gegevens.
     *
     * @return een nieuw bericht
     */
    private AbstractBijhoudingsBericht maakNieuwOngeldigBericht() {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {
        };

        Land land = new Land();
        ReflectionTestUtils.setField(land, "landCode", new LandCode("6030"));

        PersoonIdentificatieNummersGroepBericht pin = new PersoonIdentificatieNummersGroepBericht();
        pin.setVerzendendId("" + (new Date().getTime()));
        pin.setBurgerServiceNummer(new Burgerservicenummer("123456789"));

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        gegevens.setDatumAanvangAdreshouding(new Datum(10));
        gegevens.setLand(land);

        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setGegevens(gegevens);

        persoonAdres.setVerzendendId("" + (new Date().getTime()));
        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatieNummers(pin);
        persoon.setAdressen(adressen);

        ActieBericht actie = new ActieBericht();
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        bericht.setBrpActies(Arrays.asList((Actie) actie));

        return bericht;
    }

    /**
     * Instantieert een bericht met geldige gegevens.
     *
     * @return een nieuw bericht
     */
    private AbstractBijhoudingsBericht maakNieuwGeldigBericht() {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {
        };

        RedenWijzigingAdres redenWijzigingAdres = new RedenWijzigingAdres();
        ReflectionTestUtils.setField(redenWijzigingAdres, "redenWijzigingAdresCode", new RedenWijzigingAdresCode(
                "PERSOON"));

        Land land = new Land();
        ReflectionTestUtils.setField(land, "landCode", new LandCode("6030"));

        PersoonIdentificatieNummersGroepBericht pin = new PersoonIdentificatieNummersGroepBericht();
        pin.setBurgerServiceNummer(new Burgerservicenummer("123456782"));

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        gegevens.setDatumAanvangAdreshouding(new Datum(20121231));
        gegevens.setLand(land);
        gegevens.setSoort(FunctieAdres.WOONADRES);
        gegevens.setRedenwijziging(redenWijzigingAdres);
        gegevens.setGemeente(new Partij());
        gegevens.getGemeente().setGemeenteCode(new GemeenteCode("1234"));

        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setGegevens(gegevens);

        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatieNummers(pin);
        persoon.setAdressen(adressen);

        ActieBericht actie = new ActieBericht();
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        bericht.setBrpActies(Arrays.asList((Actie) actie));

        return bericht;
    }

}
