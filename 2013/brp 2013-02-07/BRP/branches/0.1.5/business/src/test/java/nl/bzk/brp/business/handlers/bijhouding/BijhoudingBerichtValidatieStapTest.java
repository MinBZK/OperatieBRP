/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.OverruleMelding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class BijhoudingBerichtValidatieStapTest extends AbstractStapTest {

    private BijhoudingBerichtValidatieStap bijhoudingBerichtValidatieStap;


    @Before
    public void init() {

        bijhoudingBerichtValidatieStap = new BijhoudingBerichtValidatieStap();
    }

    @Test
    public void testBerichtGeldig() {
        // geen enkel overrule.
        AbstractBijhoudingsBericht bericht = maakBericht();
        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            bijhoudingBerichtValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtGeldigWhitelist() {
        // geen enkel overrule.
        AbstractBijhoudingsBericht bericht = maakBericht("AGDRT12");
        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            bijhoudingBerichtValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtBijgehoudenPersonenNull() {
        // geen enkel overrule.
        AbstractBijhoudingsBericht bericht = maakBericht();
        BerichtResultaat resultaat = new BijhoudingResultaat(null);
        bijhoudingBerichtValidatieStap.naVerwerkingsStapVoorBericht(bericht,
                  bouwBerichtContext(), resultaat);

        BijhoudingResultaat bresultaat = (BijhoudingResultaat) resultaat;
        Assert.assertEquals(0, bresultaat.getMeldingen().size());
        Assert.assertTrue(bresultaat.getBijgehoudenPersonen().isEmpty());
    }

    @Test
    public void testBerichtBijgehoudenPersonenMetBijPersonen() {
        // geen enkel overrule.
        AbstractBijhoudingsBericht bericht = maakBericht();
        BerichtResultaat resultaat = new BijhoudingResultaat(null);
        BerichtContext bc = bouwBerichtContext("154853653", "133343443");
        bc.voegBijPersoonToe(bouwSimplePersoon("154853653"));
        bc.voegBijPersoonToe(bouwSimplePersoon("216522547"));
        bijhoudingBerichtValidatieStap.naVerwerkingsStapVoorBericht(bericht,
                  bc, resultaat);
        BijhoudingResultaat bresultaat = (BijhoudingResultaat) resultaat;
        Assert.assertEquals(0, bresultaat.getMeldingen().size());
        // nog steeds 3 personen (1 dubbele)
        Assert.assertEquals(3, bresultaat.getBijgehoudenPersonen().size());
    }

    /**
     * Bouwt en retourneert een standaard {@link nl.bzk.brp.business.dto.BerichtContext} instantie, met ingevulde in-
     * en uitgaande bericht ids, een authenticatiemiddel id en een partij.
     *
     * @return een geldig bericht context.
     */
    @Override
    protected BerichtContext bouwBerichtContext(final String ... bsns) {
        BerichtContext retval = super.bouwBerichtContext();
        if (null != bsns) {
            for (String bsn: bsns) {
                retval.voegHoofdPersoonToe(bouwSimplePersoon(bsn));
            }
        }
        return retval;
    }









    private AbstractBijhoudingsBericht maakBericht(final String... meldingen) {
        BRPActie actie = new BRPActie();
        Persoon persoon = new Persoon();
        PersoonAdres nieuwAdres = new PersoonAdres();
        persoon.setAdressen(new HashSet<PersoonAdres>());
        persoon.getAdressen().add(nieuwAdres);
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("123456782");
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        actie.setSoort(SoortActie.VERHUIZING);


        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() { };
        bericht.setBrpActies(Arrays.asList(actie));
        if (null != meldingen) {
            List<OverruleMelding> list = new ArrayList<OverruleMelding>();
            for (String code : meldingen) {
                list.add(new OverruleMelding(code));
            }
            bericht.setOverruledMeldingen(list);
        }
        return bericht;
    }

    private static Persoon bouwSimplePersoon(final String bsn) {
        Land land = new Land();
        land.setLandcode("6030");

        PersoonIdentificatienummers pin = new PersoonIdentificatienummers();
        pin.setBurgerservicenummer(bsn);

        PersoonAdres persoonAdres = new PersoonAdres();
        persoonAdres.setDatumAanvangAdreshouding(10);
        persoonAdres.setLand(land);
        Set<PersoonAdres> adressen = new HashSet<PersoonAdres>();
        adressen.add(persoonAdres);

        Persoon persoon = new Persoon();
        persoon.setIdentificatienummers(pin);
        persoon.setAdressen(adressen);
        return persoon;

    }

    private static Persoon bouwSimplePersoon() {
        return bouwSimplePersoon("154853653");
    }

}
