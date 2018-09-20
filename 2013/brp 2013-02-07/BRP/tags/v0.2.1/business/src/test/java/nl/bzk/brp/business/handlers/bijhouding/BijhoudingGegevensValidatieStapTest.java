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
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderschapGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshoudingIdentiteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortbericht;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.DatumUtil;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import org.junit.Assert;
import org.junit.Test;


public class BijhoudingGegevensValidatieStapTest extends AbstractStapTest {

    private final BijhoudingGegevensValidatieStap bijhoudingGegevensValidatieStap =
                                                                                      new BijhoudingGegevensValidatieStap();

    /** Tot nu toe gevalideerde velden: BSN, SoortAdres, Datum */
    @Test
    public void testBerichtMetOngeldigeGegevens() {
        BijhoudingsBericht bericht = maakNieuwOngeldigBericht();
        BerichtResultaat resultaat = new BerichtResultaat(null);
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
        BijhoudingsBericht bericht = maakNieuwGeldigBericht();

        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtMetActieZonderRootObject() {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {
            @Override
            public Soortbericht getSoortBericht() {
                return null;
            }
        };

        ActieBericht actie = new ActieBericht();
        actie.setRootObjecten(new ArrayList<RootObject>());
        bericht.setBrpActies(Arrays.asList((Actie) actie));

        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertFalse(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtFamrechtBetrekking() {
        // test de validatie dat een FamRecht.Betr geen aanvangsdatum mag hebben.
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {
            @Override
            public Soortbericht getSoortBericht() {
                return null;
            }
        };
        RelatieBericht relatie = maaktFamilieBericht(
                bouwPersoon("kind"),
                bouwPersoon("ouder"),
                null, null, null);

        ActieBericht actie = new ActieBericht();
        actie.setRootObjecten(Arrays.asList((RootObject) relatie));
        bericht.setBrpActies(Arrays.asList((Actie) actie));

        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
                bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());

        // gaat ook nog goed ...
        // TODO: verifieren dat de unittest wel correct opgezet is.
        relatie = maaktFamilieBericht(
                bouwPersoon("kind"),
                bouwPersoon("ouder"),
                DatumUtil.vandaag(), null, null);
        actie.setRootObjecten(Arrays.asList((RootObject) relatie));
        resultaat = new BerichtResultaat(null);
        stapResultaat =
                bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(0, resultaat.getMeldingen().size());
    }

    @Test
    public void testBerichtKindGeenAanvangDatum() {
        // test de validatie dat een FamRecht.Betr op het kinds rol geen aanvangsdatum mag hebben.
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {
            @Override
            public Soortbericht getSoortBericht() {
                return null;
            }
        };
        RelatieBericht relatie = maaktFamilieBericht(
                bouwPersoon("kind"),
                bouwPersoon("ouder"),
                null, null, DatumUtil.vandaag());

        ActieBericht actie = new ActieBericht();
        actie.setRootObjecten(Arrays.asList((RootObject) relatie));
        bericht.setBrpActies(Arrays.asList((Actie) actie));

        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
                bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());

        // nu zet een datum op de kind betrokkenheid en verifieer dat het fpout gaat.
        relatie = maaktFamilieBericht(
                bouwPersoon("kind"),
                bouwPersoon("ouder"),
                null, DatumUtil.vandaag(), null);
        actie.setRootObjecten(Arrays.asList((RootObject) relatie));
        resultaat = new BerichtResultaat(null);
        stapResultaat =
                bijhoudingGegevensValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(MeldingCode.BRAL0203, resultaat.getMeldingen().get(0).getCode());
    }

    /**
     * Instantieert een bericht met ongeldige gegevens.
     *
     * @return een nieuw bericht
     */
    private BijhoudingsBericht maakNieuwOngeldigBericht() {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {
            @Override
            public Soortbericht getSoortBericht() {
                return null;
            }
        };

        Land land = new Land();
        land.setCode(BrpConstanten.NL_LAND_CODE);

        RedenWijzigingAdres redenwijziging = new RedenWijzigingAdres();
        redenwijziging.setRedenWijzigingAdresCode(BrpConstanten.PERSOON_REDEN_WIJZIGING_ADRES_CODE);

        PersoonIdentificatienummersGroepBericht pin = new PersoonIdentificatienummersGroepBericht();
        pin.setVerzendendId("" + (new Date().getTime()));
        pin.setBurgerservicenummer(new Burgerservicenummer("123456789"));

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        gegevens.setDatumAanvangAdreshouding(new Datum(10));
        gegevens.setLand(land);
        gegevens.setRedenwijziging(redenwijziging);

        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setGegevens(gegevens);

        persoonAdres.setVerzendendId("" + (new Date().getTime()));
        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(pin);
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
    private BijhoudingsBericht maakNieuwGeldigBericht() {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {
            @Override
            public Soortbericht getSoortBericht() {
                return null;
            }
        };

        RedenWijzigingAdres redenWijzigingAdres = new RedenWijzigingAdres();
        redenWijzigingAdres.setRedenWijzigingAdresCode(BrpConstanten.PERSOON_REDEN_WIJZIGING_ADRES_CODE);

        Land land = new Land();
        land.setCode(BrpConstanten.NL_LAND_CODE);

        PersoonIdentificatienummersGroepBericht pin = new PersoonIdentificatienummersGroepBericht();
        pin.setBurgerservicenummer(new Burgerservicenummer("123456782"));

        PersoonAdresStandaardGroepBericht gegevens = new PersoonAdresStandaardGroepBericht();
        gegevens.setDatumAanvangAdreshouding(new Datum(20121231));
        gegevens.setLand(land);
        gegevens.setSoort(FunctieAdres.WOONADRES);
        gegevens.setRedenwijziging(redenWijzigingAdres);
        gegevens.setGemeente(new Partij());
        gegevens.getGemeente().setGemeentecode(new Gemeentecode((short) 1234));
        gegevens.setAangeverAdreshouding(AangeverAdreshoudingIdentiteit.PARTNER);

        PersoonAdresBericht persoonAdres = new PersoonAdresBericht();
        persoonAdres.setGegevens(gegevens);

        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();
        adressen.add(persoonAdres);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(pin);
        persoon.setAdressen(adressen);

        ActieBericht actie = new ActieBericht();
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));

        bericht.setBrpActies(Arrays.asList((Actie) actie));

        return bericht;
    }


    private RelatieBericht maaktFamilieBericht(final PersoonBericht kind, final PersoonBericht ouder,
            final Datum aanvangDatumRelatie, final Datum aanvangDatumBetrekkingKind,
            final Datum aanvangDatumBetrekkingOuder)
    {
        RelatieBericht relatie = new RelatieBuilder()
            .bouwFamilieRechtelijkeBetrekkingRelatie()
            .voegOuderToe(ouder)
            .voegKindToe(kind)
            .getRelatie();

        if (aanvangDatumRelatie != null) {
            // dit is ee test geval, deze levert altijd een fout op. Fam.recht. mag nooit aanvangDaum hebben.
            relatie.setGegevens(new RelatieStandaardGroepBericht());
            relatie.getGegevens().setDatumAanvang(aanvangDatumRelatie);
        }
        if (aanvangDatumBetrekkingKind != null) {
            // dit is ee test geval, deze levert altijd een fout op. kind. mag nooit aanvangDaum hebben.
            BetrokkenheidBericht betr = relatie.getKindBetrokkenheid();
            betr.setBetrokkenheidOuderschap(new BetrokkenheidOuderschapGroepBericht());
            betr.getBetrokkenheidOuderschap().setDatumAanvang(aanvangDatumBetrekkingKind);
        }
        if (aanvangDatumBetrekkingOuder != null) {
            BetrokkenheidBericht betr = relatie.getOuderBetrokkenheden().iterator().next();
            betr.setBetrokkenheidOuderschap(new BetrokkenheidOuderschapGroepBericht());
            betr.getBetrokkenheidOuderschap().setDatumAanvang(aanvangDatumBetrekkingKind);
        }
        return relatie;
    }

    private PersoonBericht bouwPersoon(final String naam) {
        return PersoonBuilder.bouwPersoon("123456782", Geslachtsaanduiding.MAN,
                                20121212, null, "vn", "", naam);

    }
}
