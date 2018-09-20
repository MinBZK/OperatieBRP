/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
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
        bijhoudingBerichtValidatieStap.naVerwerkingsStapVoorBericht(bericht, bouwBerichtContext(), resultaat);

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
        bijhoudingBerichtValidatieStap.naVerwerkingsStapVoorBericht(bericht, bc, resultaat);
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
    protected BerichtContext bouwBerichtContext(final String... bsns) {
        BerichtContext retval = super.bouwBerichtContext();
        if (null != bsns) {
            for (String bsn : bsns) {
                retval.voegHoofdPersoonToe(bouwSimplePersoon(bsn));
            }
        }
        return retval;
    }

    private AbstractBijhoudingsBericht maakBericht(final String... meldingen) {
        ActieBericht actie = new ActieBericht();

        PersoonAdresBericht nieuwAdres = new PersoonAdresBericht();

        PersoonBericht persoon = new PersoonBericht();
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(nieuwAdres);

        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("123456782"));


        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        actie.setSoort(SoortActie.VERHUIZING);

        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {
        };
        bericht.setBrpActies(Arrays.asList((Actie) actie));
        if (null != meldingen) {
            List<OverruleMelding> list = new ArrayList<OverruleMelding>();
            for (String code : meldingen) {
                list.add(new OverruleMelding(code));
            }
            bericht.setOverruledMeldingen(list);
        }
        return bericht;
    }

    private PersoonBericht bouwSimplePersoon(final String bsn) {
        Land land = new Land();
        land.setCode(BrpConstanten.NL_LAND_CODE);

        PersoonIdentificatienummersGroepBericht pin = new PersoonIdentificatienummersGroepBericht();
        pin.setBurgerservicenummer(new Burgerservicenummer(bsn));

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        gegevens.setDatumAanvangAdreshouding(new Datum(10));
        gegevens.setLand(land);

        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setGegevens(gegevens);

        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(pin);
        persoon.setAdressen(adressen);
        return persoon;

    }
}
