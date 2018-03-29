/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import java.util.UUID;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonMapper;
import nl.bzk.brp.gba.domain.bevraging.Persoonsantwoord;
import nl.bzk.brp.gba.domain.bevraging.PersoonsvraagQueue;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortDienstType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonVerzoekBericht;
import nl.bzk.migratiebrp.test.common.vergelijk.SorteerJson;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkJson;
import org.junit.Assert;
import org.junit.Test;

/**
 * AdHoc zoeken integratie test.
 */
public class AdHocZoekenServiceIT extends AbstractIT {

    private static final Logger LOG = LoggerFactory.getLogger();

    public AdHocZoekenServiceIT() {
        super(Main.Modus.SYNCHRONISATIE);
    }

    @Test
    public void testVanBrp() throws Exception {
        final Persoonsantwoord persoonsantwoord = new Persoonsantwoord();
        persoonsantwoord.setFoutreden("X");
        putBrpMessage(PersoonsvraagQueue.ANTWOORD.getQueueNaam(), JsonMapper.writer().writeValueAsString(persoonsantwoord), UUID.randomUUID().toString());

        final String antwoord = getContent(expectMessage(SYNC_ANTWOORD_QUEUE));
        System.out.println(antwoord);
        Assert.assertTrue(antwoord.contains("<?xml version=\"1.0\" encoding=\"UTF-8\" "
                + "standalone=\"yes\"?><adHocZoekPersoonAntwoord xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\"><foutreden>X</foutreden"
                + "></adHocZoekPersoonAntwoord>"));
    }

    @Test
    public void testNaarBrp() throws Exception {
        naarBrp("00069010460110010123456789001200091234567890240006Jansen0801311600062994HA",
                "{\"gevraagdeRubrieken\":[\"01.01.10\"],\"partijCode\":\"000014\","
                        + "\"zoekCriteria\":[{\"naam\":\"Persoon.Identificatienummers"
                        + ".Administratienummer\",\"waarde\":\"1234567890\"},{\"naam\":\"Persoon.Identificatienummers.Burgerservicenummer\","
                        + "\"waarde\":\"123456789\"},{\"naam\":\"Persoon.SamengesteldeNaam.Geslachtsnaamstam\",\"waarde\":\"Jansen\"},{\"naam\":\"Persoon.Adres"
                        + ".Postcode\",\"waarde\":\"2994HA\"}],\"zoekRubrieken\":[\"01.01.10\",\"01.01.20\",\"01.02.40\",\"08.11.60\"], \"soortDienst"
                        + "\":\"ZOEK_PERSOON\"}");
    }

    @Test
    public void testNaarBrpAlleLegeCategorieen() throws Exception {
        naarBrp("000260102102300000240007Enenaam", "{\"gevraagdeRubrieken\":[\"01.01.10\"],\"partijCode\":\"000014\","
                + "\"zoekCriteria\":[{\"naam\":\"Persoon.SamengesteldeNaam.Voorvoegsel\"},{\"naam\":\"Persoon.SamengesteldeNaam.Scheidingsteken\"},"
                + "{\"naam\":\"Persoon.SamengesteldeNaam.Geslachtsnaamstam\",\"waarde\":\"Enenaam\"}],\"zoekRubrieken\":[\"01.02.30\",\"01.02.40\"], "
                + "\"soortDienst\":\"ZOEK_PERSOON\"}");
    }

    @Test
    public void testNaarBrpIndicatie() throws Exception {
        naarBrp("00032010140240007Enenaam130083810001A",
                "{\"gevraagdeRubrieken\":[\"01.01.10\"],\"partijCode\":\"000014\",\"zoekCriteria\":[{\"naam\":\"Persoon"
                        + ".SamengesteldeNaam.Geslachtsnaamstam\",\"waarde\":\"Enenaam\"},{\"naam\":\"Persoon.UitsluitingKiesrecht.Indicatie\","
                        + "\"waarde\":\"J\"}],"
                        + "\"zoekRubrieken\":[\"01.02.40\",\"13.38.10\"], \"soortDienst\":\"ZOEK_PERSOON\"}");
    }

    @Test
    public void testNaarBrpLegeNumeriekCategorieMetExacteLengte() throws Exception {
        naarBrp("0002901024011001012345678900120000",
                "{\"gevraagdeRubrieken\":[\"01.01.10\"],\"partijCode\":\"000014\",\"zoekCriteria\":[{\"naam\":\"Persoon"
                        + ".Identificatienummers.Administratienummer\",\"waarde\":\"1234567890\"},{\"naam\":\"Persoon.Identificatienummers"
                        + ".Burgerservicenummer\"}],"
                        + "\"zoekRubrieken\":[\"01.01.10\",\"01.01.20\"], \"soortDienst\":\"ZOEK_PERSOON\"}");
    }

    @Test
    public void testIndicatieDerdeHeeftGezagD() throws Exception {
        naarBrpFout("00034010160120009123456789110083210001D", "<?xml version=\"1.0\" encoding=\"UTF-8\" "
                + "standalone=\"yes\"?><adHocZoekPersoonAntwoord xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\"><foutreden>X</foutreden"
                + "></adHocZoekPersoonAntwoord>");
    }

    @Test
    public void testIndicatieDerdeHeeftGezag12() throws Exception {
        naarBrpFout("0003501016012000912345678911009321000212", "<?xml version=\"1.0\" encoding=\"UTF-8\" "
                + "standalone=\"yes\"?><adHocZoekPersoonAntwoord xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\"><foutreden>X</foutreden"
                + "></adHocZoekPersoonAntwoord>");
    }

    @Test
    public void testIndicatieDerdeHeeftGezag1() throws Exception {
        naarBrpFout("000340101601200091234567891100832100011", "<?xml version=\"1.0\" encoding=\"UTF-8\" "
                + "standalone=\"yes\"?><adHocZoekPersoonAntwoord xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\"><foutreden>X</foutreden"
                + "></adHocZoekPersoonAntwoord>");
    }

    @Test
    public void testLege046510() throws Exception {
        naarBrp("000340101701100101234567890040076510000", "{\"gevraagdeRubrieken\":[\"01.01.10\"],\"partijCode\":\"000014\","
                + "\"zoekCriteria\":[{\"naam\":\"Persoon.Identificatienummers.Administratienummer\",\"waarde\":\"1234567890\"},{\"naam\":\"Persoon.Indicatie"
                + ".BehandeldAlsNederlander.Waarde\"},{\"naam\":\"Persoon.Indicatie.VastgesteldNietNederlander.Waarde\"}],\"zoekRubrieken\":[\"01.01.10\","
                + "\"04.65.10\"], \"soortDienst\":\"ZOEK_PERSOON\"}");
    }

    @Test
    public void testVoorvoegsel() throws Exception {
        naarBrp("00030010250230007van der0240004Dijk", "{\"gevraagdeRubrieken\":[\"01.01.10\"],\"partijCode\":\"000014\","
                + "\"zoekCriteria\":[{\"naam\":\"Persoon.SamengesteldeNaam.Voorvoegsel\",\"waarde\":\"van der\"},{\"naam\":\"Persoon.SamengesteldeNaam"
                + ".Scheidingsteken\",\"waarde\":\" \"},{\"naam\":\"Persoon.SamengesteldeNaam.Geslachtsnaamstam\",\"waarde\":\"Dijk\"}],"
                + "\"zoekRubrieken\":[\"01.02.30\",\"01.02.40\"], \"soortDienst\":\"ZOEK_PERSOON\"}");
    }

    @Test
    public void testCategorie01Leeg() throws Exception {
        naarBrp("001060110101100101234567890012000002100000220000023000002400000310000032000003300000410000201000020200006110000", "{"
                + "\"gevraagdeRubrieken\":[\"01.01.10\"],\"partijCode\":\"000014\",\"soortDienst\":\"ZOEK_PERSOON\",\"zoekCriteria\":[{\"naam\":\"Persoon"
                + ".Identificatienummers.Administratienummer\",\"waarde\":\"1234567890\"},{\"naam\":\"Persoon.Identificatienummers.Burgerservicenummer\"},"
                + "{\"naam\":\"Persoon.SamengesteldeNaam.Voornamen\"},{\"naam\":\"Persoon.SamengesteldeNaam.AdellijkeTitelCode\"},"
                + "{\"naam\":\"Persoon.SamengesteldeNaam.PredicaatCode\"},{\"naam\":\"Persoon.SamengesteldeNaam.Voorvoegsel\"},"
                + "{\"naam\":\"Persoon.SamengesteldeNaam.Scheidingsteken\"},{\"naam\":\"Persoon.SamengesteldeNaam.Geslachtsnaamstam\"},"
                + "{\"naam\":\"Persoon.Geboorte.Datum\"},{\"naam\":\"Persoon.Geboorte.GemeenteCode\"},{\"naam\":\"Persoon.Geboorte.BuitenlandsePlaats\"},"
                + "{\"naam\":\"Persoon.Geboorte.OmschrijvingLocatie\"},{\"naam\":\"Persoon.Geboorte.Woonplaatsnaam\"},"
                + "{\"naam\":\"Persoon.Geboorte.LandGebiedCode\"},{\"naam\":\"Persoon.Geslachtsaanduiding.Code\"},"
                + "{\"naam\":\"Persoon.Nummerverwijzing.VorigeAdministratienummer\"},{\"naam\":\"Persoon.Nummerverwijzing.VolgendeAdministratienummer\"},"
                + "{\"naam\":\"Persoon.Naamgebruik.Code\"}],\"zoekRubrieken\":[\"01.01.10\",\"01.01.20\",\"01.02.10\",\"01.02.20\",\"01.02.30\",\"01.02.40\","
                + "\"01.03.10\",\"01.03.20\",\"01.03.30\",\"01.04.10\",\"01.20.10\",\"01.20.20\",\"01.61.10\"]}");
    }

    @Test
    public void testCategorie04Leeg() throws Exception {
        naarBrp("00048010170110010123456789004021051000065100007310000", "{\"gevraagdeRubrieken\":[\"01.01.10\"],\"partijCode\":\"000014\","
                + "\"zoekCriteria\":[{\"naam\":\"Persoon.Identificatienummers.Administratienummer\","
                + "\"waarde\":\"1234567890\"},{\"naam\":\"Persoon.Nationaliteit.NationaliteitCode\"},{\"naam\":\"Persoon.Indicatie.BehandeldAlsNederlander"
                + ".Waarde\"},{\"naam\":\"Persoon.Indicatie.VastgesteldNietNederlander.Waarde\"},{\"naam\":\"Persoon.BuitenlandsPersoonsnummer.Nummer\"}],"
                + "\"zoekRubrieken\":[\"01.01.10\",\"04.05.10\",\"04.65.10\",\"04.73.10\"], \"soortDienst\":\"ZOEK_PERSOON\"}");
    }

    @Test
    public void testCategorie06Leeg() throws Exception {
        naarBrp("00048010170110010123456789006021081000008200000830000", "{\"gevraagdeRubrieken\":[\"01.01.10\"],\"partijCode\":\"000014\","
                + "\"zoekCriteria\":[{\"naam\":\"Persoon.Identificatienummers.Administratienummer\","
                + "\"waarde\":\"1234567890\"},{\"naam\":\"Persoon.Overlijden.Datum\"},{\"naam\":\"Persoon.Overlijden.GemeenteCode\"},{\"naam\":\"Persoon"
                + ".Overlijden.LandGebiedCode\"}],\"zoekRubrieken\":[\"01.01.10\",\"06.08.10\",\"06.08.20\",\"06.08.30\"], \"soortDienst\":\"ZOEK_PERSOON\"}");
    }

    @Test
    public void testCategorie07Leeg() throws Exception {
        naarBrp("00048010170110010123456789007021672000068100006910000", "{\"gevraagdeRubrieken\":[\"01.01.10\"],\"partijCode\":\"000014\","
                + "\"zoekCriteria\":[{\"naam\":\"Persoon.Identificatienummers.Administratienummer\","
                + "\"waarde\":\"1234567890\"},{\"naam\":\"Persoon.Bijhouding.NadereBijhoudingsaardCode\", \"waarde\":\"A\"},{\"naam\":\"Persoon.Inschrijving.Datum\"},"
                + "{\"naam\":\"Persoon.Persoonskaart.PartijCode\"}],\"zoekRubrieken\":[\"01.01.10\",\"07.67.20\",\"07.68.10\",\"07.69.10\"], \"soortDienst"
                + "\":\"ZOEK_PERSOON\"}");
    }

    @Test
    public void testCategorie08Leeg() throws Exception {
        naarBrp("0014601017011001012345678900811909100001010000102000010300001110000111500011200001130000114000011500001160000117000011800001190000121000072100007510000",
                "{\"gevraagdeRubrieken\":[\"01.01.10\"],\"partijCode\":\"000014\",\"zoekCriteria\":[{\"naam\":\"Persoon.Identificatienummers.Administratienummer\","
                        + "\"waarde\":\"1234567890\"},{\"naam\":\"Persoon.Adres.GemeenteCode\"},{\"naam\":\"Persoon.Adres.SoortCode\"},{\"naam\":\"Persoon"
                        + ".Adres.Gemeentedeel\"},{\"naam\":\"Persoon.Adres.DatumAanvangAdreshouding\"},{\"naam\":\"Persoon.Adres"
                        + ".AfgekorteNaamOpenbareRuimte\"},{\"naam\":\"Persoon.Adres.NaamOpenbareRuimte\"},{\"naam\":\"Persoon.Adres.Huisnummer\"},"
                        + "{\"naam\":\"Persoon.Adres.Huisletter\"},{\"naam\":\"Persoon.Adres.Huisnummertoevoeging\"},{\"naam\":\"Persoon.Adres"
                        + ".LocatieTenOpzichteVanAdres\"},{\"naam\":\"Persoon.Adres.Postcode\"},{\"naam\":\"Persoon.Adres.Woonplaatsnaam\"},"
                        + "{\"naam\":\"Persoon.Adres.IdentificatiecodeAdresseerbaarObject\"},{\"naam\":\"Persoon.Adres.IdentificatiecodeNummeraanduiding\"},"
                        + "{\"naam\":\"Persoon.Adres.Locatieomschrijving\"},{\"naam\":\"Persoon.Adres.RedenWijzigingCode\"},{\"naam\":\"Persoon.Adres"
                        + ".AangeverAdreshoudingCode\"},{\"naam\":\"Persoon.Indicatie.OnverwerktDocumentAanwezig.Waarde\"}],\"zoekRubrieken\":[\"01.01.10\","
                        + "\"08.09.10\",\"08.10.10\",\"08.10.20\",\"08.10.30\",\"08.11.10\",\"08.11.15\",\"08.11.20\",\"08.11.30\",\"08.11.40\",\"08.11.50\","
                        + "\"08.11.60\",\"08.11.70\",\"08.11.80\",\"08.11.90\",\"08.12.10\",\"08.72.10\",\"08.75.10\"], \"soortDienst\":\"ZOEK_PERSOON\"}");
    }

    @Test
    public void testCategorie10Leeg() throws Exception {
        naarBrp("00048010170110010123456789010021391000039200003930000", "{\"gevraagdeRubrieken\":[\"01.01.10\"],\"partijCode\":\"000014\","
                + "\"zoekCriteria\":[{\"naam\":\"Persoon.Identificatienummers.Administratienummer\","
                + "\"waarde\":\"1234567890\"},{\"naam\":\"Persoon.Verblijfsrecht.AanduidingCode\"},{\"naam\":\"Persoon.Verblijfsrecht"
                + ".DatumVoorzienEinde\"},{\"naam\":\"Persoon.Verblijfsrecht.DatumAanvang\"}],\"zoekRubrieken\":[\"01.01.10\",\"10.39.10\",\"10.39.20\","
                + "\"10.39.30\"], \"soortDienst\":\"ZOEK_PERSOON\"}");
    }

    @Test
    public void testCategorie12Leeg() throws Exception {
        naarBrp("0008301017011001012345678901205635100003520000353000035400003550000356000035700003610000",
                "{\"gevraagdeRubrieken\":[\"01.01.10\"],\"partijCode\":\"000014\",\"zoekCriteria\":[{\"naam\":\"Persoon.Identificatienummers.Administratienummer\","
                        + "\"waarde\":\"1234567890\"},{\"naam\":\"Persoon.Reisdocument.SoortCode\"},{\"naam\":\"Persoon.Reisdocument.Nummer\"},"
                        + "{\"naam\":\"Persoon"
                        + ".Reisdocument.DatumUitgifte\"},{\"naam\":\"Persoon.Reisdocument.AutoriteitVanAfgifte\"},{\"naam\":\"Persoon.Reisdocument"
                        + ".DatumEindeDocument\"},{\"naam\":\"Persoon.Reisdocument.DatumInhoudingVermissing\"},{\"naam\":\"Persoon.Reisdocument"
                        + ".AanduidingInhoudingVermissingCode\"},{\"naam\":\"Persoon.Indicatie.SignaleringMetBetrekkingTotVerstrekkenReisdocument.Waarde\"}],"
                        + "\"zoekRubrieken\":[\"01.01.10\",\"12.35.10\",\"12.35.20\",\"12.35.30\",\"12.35.40\",\"12.35.50\",\"12.35.60\",\"12.35.70\","
                        + "\"12.36.10\"], \"soortDienst\":\"ZOEK_PERSOON\"}");
    }

    @Test
    public void testCategorie13Leeg() throws Exception {
        naarBrp("0006201017011001012345678901303531100003120000313000038100003820000", "{\"gevraagdeRubrieken\":[\"01.01.10\"],\"partijCode\":\"000014\","
                + "\"zoekCriteria\":[{\"naam\":\"Persoon.Identificatienummers.Administratienummer\","
                + "\"waarde\":\"1234567890\"},{\"naam\":\"Persoon.DeelnameEUVerkiezingen.IndicatieDeelname\"},{\"naam\":\"Persoon.DeelnameEUVerkiezingen"
                + ".DatumAanleidingAanpassing\"},{\"naam\":\"Persoon.DeelnameEUVerkiezingen.DatumVoorzienEindeUitsluiting\"},{\"naam\":\"Persoon"
                + ".UitsluitingKiesrecht.Indicatie\"},{\"naam\":\"Persoon.UitsluitingKiesrecht.DatumVoorzienEinde\"}],"
                + "\"zoekRubrieken\":[\"01.01.10\",\"13.31.10\",\"13.31.20\",\"13.31.30\",\"13.38.10\",\"13.38.20\"], \"soortDienst\":\"ZOEK_PERSOON\"}");
    }

    private void naarBrp(final String pig, final String expected) throws Exception {
        final AdHocZoekPersoonVerzoekBericht persoonVerzoekBericht = new AdHocZoekPersoonVerzoekBericht();
        persoonVerzoekBericht.getGevraagdeRubrieken().add("01.01.10");
        persoonVerzoekBericht.setPartijCode("000014");
        persoonVerzoekBericht.setPersoonIdentificerendeGegevens(pig);
        persoonVerzoekBericht.setSoortDienst(SoortDienstType.ZOEK_PERSOON);
        System.out.println(persoonVerzoekBericht.format());
        putMessage(SYNC_VERZOEK_QUEUE, persoonVerzoekBericht.format(), UUID.randomUUID().toString());

        final String vraag = getContent(expectBrpMessage(PersoonsvraagQueue.VERZOEK.getQueueNaam()));

        LOG.info("expected: " + expected);
        LOG.info("actual: " + vraag);

        Assert.assertTrue("Persoonsvraag niet zoals verwacht", VergelijkJson.vergelijkJson(SorteerJson.sorteer(expected), SorteerJson.sorteer(vraag)));
    }

    private void naarBrpFout(final String pig, final String expect) throws Exception {
        final AdHocZoekPersoonVerzoekBericht persoonVerzoekBericht = new AdHocZoekPersoonVerzoekBericht();
        persoonVerzoekBericht.getGevraagdeRubrieken().add("01.01.10");
        persoonVerzoekBericht.setPartijCode("000014");
        persoonVerzoekBericht.setPersoonIdentificerendeGegevens(pig);
        persoonVerzoekBericht.setSoortDienst(SoortDienstType.ZOEK_PERSOON);
        System.out.println(persoonVerzoekBericht.format());
        putMessage(SYNC_VERZOEK_QUEUE, persoonVerzoekBericht.format(), UUID.randomUUID().toString());

        final String antwoord = getContent(expectMessage(SYNC_ANTWOORD_QUEUE));
        System.out.println(antwoord);
        Assert.assertEquals(expect, antwoord);
    }
}
