/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.stappen.AbstractStapTest;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingAdres;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAanschrijvingBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningOngeborenVruchtBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Test;


public class BijhoudingGegevensValidatieStapTest extends AbstractStapTest {

    private final BijhoudingGegevensValidatieStap bijhoudingGegevensValidatieStap =
        new BijhoudingGegevensValidatieStap();

    /** Tot nu toe gevalideerde velden: BSN, SoortAdres, Datum */
    @Test
    public void testBerichtMetOngeldigeGegevens() {
        AbstractBijhoudingsBericht bericht = maakNieuwOngeldigBericht();
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        boolean stapResultaat =
            bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(5, resultaat.getMeldingen().size());

        Assert.assertNotNull(zoekMelding(MeldingCode.BRAL0012, resultaat.getMeldingen()));
        Assert.assertNotNull(zoekMelding(MeldingCode.BRAL2032, resultaat.getMeldingen()));
        Assert.assertNotNull(zoekMelding(MeldingCode.BRAL2033, resultaat.getMeldingen()));
        Assert.assertNotNull(zoekMelding(MeldingCode.BRAL0102, resultaat.getMeldingen()));
        Assert.assertNotNull(zoekMelding(MeldingCode.BRAL1118, resultaat.getMeldingen()));
    }

    @Test
    public void testBerichtMetGeldigeGegevens() {
        AbstractBijhoudingsBericht bericht = maakNieuwGeldigBericht();

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        boolean stapResultaat =
            bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtMetActieZonderRootObject() {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht(null) {
        };

        ActieBericht actie = new ActieRegistratieAanschrijvingBericht();
        actie.setRootObjecten(new ArrayList<RootObject>());
        bericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        bericht.getAdministratieveHandeling().setActies(Arrays.asList(actie));

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        boolean stapResultaat =
            bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtFamrechtBetrekking() {
        // test de validatie dat een FamRecht.Betr geen aanvangsdatum mag hebben.
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht(null) {
        };
        RelatieBericht relatie = maaktFamilieBericht(
            bouwPersoon("kind"),
            bouwPersoon("ouder"));

        ActieBericht actie = new ActieRegistratieAanschrijvingBericht();
        actie.setRootObjecten(Arrays.asList((RootObject) relatie));
        bericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        bericht.getAdministratieveHandeling().setActies(Arrays.asList(actie));

        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        boolean stapResultaat =
            bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());

        // gaat ook nog goed ...
        // Test waarbij datums in betr is ingevuld had nooit gekund door de xsd, maar kan nu ook niet meer in java,
        // omdat we subtyperingen nu kennen.
//        relatie = maaktFamilieBericht(
//            bouwPersoon("kind"),
//            bouwPersoon("ouder"),
//            DatumUtil.vandaag(), null, null);
//        actie.setRootObjecten(Arrays.asList((RootObject) relatie));
//        resultaat = new BerichtVerwerkingsResultaat(null);
//        stapResultaat =
//            bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);
//
//        Assert.assertTrue(stapResultaat);
//        Assert.assertEquals(0, resultaat.getMeldingen().size());
    }

    // Test waarbij datums in betr is ingevuld had nooit gekund door de xsd, maar kan nu ook niet meer in java,
    // omdat we subtyperingen nu kennen.
//    @Test
//    public void testBerichtKindGeenAanvangDatum() {
//        // test de validatie dat een FamRecht.Betr op het kinds rol geen aanvangsdatum mag hebben.
//        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {
//        };
//        RelatieBericht relatie = maaktFamilieBericht(
//            bouwPersoon("kind"),
//            bouwPersoon("ouder"),
//            null, null, DatumUtil.vandaag());
//
//        ActieBericht actie = new ActieBericht();
//        actie.setRootObjecten(Arrays.asList((RootObject) relatie));
//        bericht.setAdministratieveHandeling(new AdministratieveHandelingBericht());
//        bericht.getAdministratieveHandeling().setActies(Arrays.asList(actie));
//
//        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
//        boolean stapResultaat =
//            bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);
//
//        Assert.assertTrue(stapResultaat);
//        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
//
//        // nu zet een datum op de kind betrokkenheid en verifieer dat het fpout gaat.
//        relatie = maaktFamilieBericht(
//            bouwPersoon("kind"),
//            bouwPersoon("ouder"),
//            null, DatumUtil.vandaag(), null);
//        actie.setRootObjecten(Arrays.asList((RootObject) relatie));
//        resultaat = new BerichtVerwerkingsResultaat(null);
//        stapResultaat =
//            bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);
//
//        Assert.assertTrue(stapResultaat);
//        Assert.assertEquals(1, resultaat.getMeldingen().size());
//        Assert.assertEquals(MeldingCode.BRAL0203, resultaat.getMeldingen().get(0).getOmschrijving());
//    }

    /**
     * Instantieert een bericht met ongeldige gegevens.
     *
     * @return een nieuw bericht
     */
    private AbstractBijhoudingsBericht maakNieuwOngeldigBericht() {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht(null) {
        };

        Land land = StatischeObjecttypeBuilder.LAND_NEDERLAND;

        RedenWijzigingAdres redenwijziging = new RedenWijzigingAdres(
                BrpConstanten.PERSOON_REDEN_WIJZIGING_ADRES_CODE, null);

        PersoonIdentificatienummersGroepBericht pin = new PersoonIdentificatienummersGroepBericht();
        pin.setCommunicatieID("" + (new Date().getTime()));
        pin.setBurgerservicenummer(new Burgerservicenummer("123456789"));

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        gegevens.setDatumAanvangAdreshouding(new Datum(10));
        gegevens.setLand(land);
        gegevens.setRedenWijziging(redenwijziging);

        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setStandaard(gegevens);

        persoonAdres.setCommunicatieID("" + (new Date().getTime()));
        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(pin);
        persoon.setAdressen(adressen);

        ActieBericht actie = new ActieRegistratieAanschrijvingBericht();
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        bericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        bericht.getAdministratieveHandeling().setActies(Arrays.asList(actie));

        return bericht;
    }

    /**
     * Instantieert een bericht met geldige gegevens.
     *
     * @return een nieuw bericht
     */
    private AbstractBijhoudingsBericht maakNieuwGeldigBericht() {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht(null) {
        };

        RedenWijzigingAdres redenWijzigingAdres = StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_PERSOON;
        Land land = StatischeObjecttypeBuilder.LAND_NEDERLAND;

        PersoonIdentificatienummersGroepBericht pin = new PersoonIdentificatienummersGroepBericht();
        pin.setBurgerservicenummer(new Burgerservicenummer("123456782"));

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        gegevens.setDatumAanvangAdreshouding(new Datum(20121231));
        gegevens.setLand(land);
        gegevens.setSoort(FunctieAdres.WOONADRES);
        gegevens.setRedenWijziging(redenWijzigingAdres);
        gegevens.setGemeente(StatischeObjecttypeBuilder.GEMEENTE_BREDA);
        gegevens.setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_ADRESHOUDING_PARTNER);

        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setStandaard(gegevens);

        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(pin);
        persoon.setAdressen(adressen);

        ActieBericht actie = new ActieRegistratieAanschrijvingBericht();
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        bericht.setAdministratieveHandeling(new HandelingErkenningOngeborenVruchtBericht());
        bericht.getAdministratieveHandeling().setActies(Arrays.asList(actie));

        return bericht;
    }


    private FamilierechtelijkeBetrekkingBericht maaktFamilieBericht(final PersoonBericht kind, final PersoonBericht ouder
        /*, final Datum aanvangDatumRelatie,*/ /*final Datum aanvangDatumBetrekkingKind,*/
        /*final Datum aanvangDatumBetrekkingOuder*/)
    {
        FamilierechtelijkeBetrekkingBericht relatie = new RelatieBuilder<FamilierechtelijkeBetrekkingBericht>()
            .bouwFamilieRechtelijkeBetrekkingRelatie()
            .voegOuderToe(ouder)
            .voegKindToe(kind)
            .getRelatie();

//        if (aanvangDatumRelatie != null) {
//            // dit is een test geval, deze levert altijd een fout op. Fam.recht. mag nooit aanvangDaum hebben.
//            relatie.setStandaard(new RelatieStandaardGroepBericht());
//            relatie.getStandaard().setDatumAanvang(aanvangDatumRelatie);
//        }
//        if (aanvangDatumBetrekkingKind != null) {
//            // dit is ee test geval, deze levert altijd een fout op. kind. mag nooit aanvangDaum hebben.
//            KindBericht betr = (KindBericht)relatie.getKindBetrokkenheid();
//            betr.setOuderschap(new OuderOuderschapGroepBericht());
//            betr.getOuderschap().setDatumAanvang(aanvangDatumBetrekkingKind);
//        }
//        if (aanvangDatumBetrekkingOuder != null) {
//            OuderBericht betr = relatie.getOuderBetrokkenheden().iterator().next();
//            betr.setOuderschap(new OuderOuderschapGroepBericht());
//            betr.getOuderschap().setDatumAanvang(aanvangDatumBetrekkingKind);
//        }
        return relatie;
    }

    private PersoonBericht bouwPersoon(final String naam) {
        return PersoonBuilder.bouwPersoon(123456782, Geslachtsaanduiding.MAN,
            20121212, null, "vn", "", naam);

    }
}
