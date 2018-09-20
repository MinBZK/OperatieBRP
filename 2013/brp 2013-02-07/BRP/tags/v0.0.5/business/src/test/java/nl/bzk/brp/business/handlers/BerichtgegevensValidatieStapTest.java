/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.binding.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.handlers.bijhouding.BerichtgegevensValidatieStap;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.FunctieAdres;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import org.junit.Assert;
import org.junit.Test;


public class BerichtgegevensValidatieStapTest extends AbstractStapTest {

    private final BerichtgegevensValidatieStap berichtgegevensValidatieStap = new BerichtgegevensValidatieStap();

    /** Tot nu toe gevalideerde velden: BSN, SoortAdres, Datum */
    @Test
    public void testBerichtMetOngeldigeGegevens() {
        BijhoudingsBericht bericht = maakNieuwOngeldigBericht();
        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            berichtgegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertFalse(stapResultaat);
        Assert.assertEquals(3, resultaat.getMeldingen().size());

        Assert.assertNotNull(zoekMelding(MeldingCode.BRAL0012, resultaat.getMeldingen()));
        Assert.assertNotNull(zoekMelding(MeldingCode.BRAL2032, resultaat.getMeldingen()));
        Assert.assertNotNull(zoekMelding(MeldingCode.BRAL0102, resultaat.getMeldingen()));
    }

    @Test
    public void testBerichtMetGeldigeGegevens() {
        BijhoudingsBericht bericht = maakNieuwGeldigBericht();

        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            berichtgegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertNull(resultaat.getMeldingen());
    }

    /**
     * Instantieert een bericht met ongeldige gegevens.
     *
     * @return een nieuw bericht
     */
    private BijhoudingsBericht maakNieuwOngeldigBericht() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();

        Land land = new Land();
        land.setLandcode("6030");

        PersoonIdentificatienummers pin = new PersoonIdentificatienummers();
        pin.setBurgerservicenummer("123456789");

        PersoonAdres persoonAdres = new PersoonAdres();
        persoonAdres.setDatumAanvangAdreshouding(10);
        persoonAdres.setLand(land);
        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
        adressen.add(persoonAdres);

        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(pin);
        persoon.setAdressen(adressen);

        BRPActie actie = new BRPActie();
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        bericht.setBrpActies(Arrays.asList(actie));

        return bericht;
    }

    /**
     * Instantieert een bericht met geldige gegevens.
     *
     * @return een nieuw bericht
     */
    private BijhoudingsBericht maakNieuwGeldigBericht() {
        BijhoudingsBericht bericht = new BijhoudingsBericht();

        Land land = new Land();
        land.setLandcode("6030");

        PersoonIdentificatienummers pin = new PersoonIdentificatienummers();
        pin.setBurgerservicenummer("123456782");

        PersoonAdres persoonAdres = new PersoonAdres();
        persoonAdres.setDatumAanvangAdreshouding(20121231);
        persoonAdres.setLand(land);
        persoonAdres.setSoort(FunctieAdres.WOONADRES);
        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
        adressen.add(persoonAdres);

        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(pin);
        persoon.setAdressen(adressen);

        BRPActie actie = new BRPActie();
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        bericht.setBrpActies(Arrays.asList(actie));

        return bericht;
    }

}
