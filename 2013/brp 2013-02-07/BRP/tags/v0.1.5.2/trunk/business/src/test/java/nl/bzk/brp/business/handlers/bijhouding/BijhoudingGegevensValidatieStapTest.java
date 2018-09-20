/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.FunctieAdres;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.RedenWijzigingAdres;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


public class BijhoudingGegevensValidatieStapTest extends AbstractStapTest {

    private final BijhoudingGegevensValidatieStap bijhoudingGegevensValidatieStap = new BijhoudingGegevensValidatieStap();

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
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    /**
     * Instantieert een bericht met ongeldige gegevens.
     *
     * @return een nieuw bericht
     */
    private AbstractBijhoudingsBericht maakNieuwOngeldigBericht() {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() { };

        Land land = new Land();
        land.setLandcode("6030");

        PersoonIdentificatienummers pin = new PersoonIdentificatienummers();
        pin.setVerzendendId("" + (new Date().getTime()));
        pin.setBurgerservicenummer("123456789");

        PersoonAdres persoonAdres = new PersoonAdres();
        persoonAdres.setDatumAanvangAdreshouding(10);
        persoonAdres.setLand(land);
        persoonAdres.setVerzendendId("" + (new Date().getTime()));
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
    private AbstractBijhoudingsBericht maakNieuwGeldigBericht() {
        AbstractBijhoudingsBericht bericht = Mockito.mock(AbstractBijhoudingsBericht.class);

        Land land = new Land();
        land.setLandcode("6030");

        PersoonIdentificatienummers pin = new PersoonIdentificatienummers();
        pin.setBurgerservicenummer("123456782");

        PersoonAdres persoonAdres = new PersoonAdres();
        persoonAdres.setDatumAanvangAdreshouding(20121231);
        persoonAdres.setLand(land);
        persoonAdres.setSoort(FunctieAdres.WOONADRES);
        persoonAdres.setRedenWijziging(RedenWijzigingAdres.PERSOON);
        persoonAdres.setDatumAanvangAdreshouding(20120101);
        persoonAdres.setGemeente(new Partij());
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
