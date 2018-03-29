/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.gba.domain.bevraging.Persoonsvraag;
import nl.bzk.brp.gba.domain.bevraging.PersoonsvraagQueue;
import nl.bzk.brp.gba.domain.bevraging.ZoekCriterium;
import org.junit.Test;

public class PersoonsvraagIT extends AbstractIT {

    @Test
    public void statusA() {
        assertEquals("{\"inhoud\":\"00000000Ha01A00000000000220101701100101234567890\"}", zoekOpANummer("1234567890"));
    }

    @Test
    public void statusR() {
        assertEquals("{\"inhoud\":\"00000000Ha01R19760103000220101701100101234567891\"}", zoekOpANummer("1234567891"));
    }

    @Test
    public void statusE() {
        assertEquals("{\"inhoud\":\"00000000Ha01E19760103000220101701100101234567892\"}", zoekOpANummer("1234567892"));
    }

    @Test
    public void statusO() {
        assertEquals("{\"inhoud\":\"00000000Ha01O19760103000220101701100101234567893\"}", zoekOpANummer("1234567893"));
    }

    @Test
    public void statusM() {
        assertEquals("{\"inhoud\":\"00000000Ha01M19760103000220101701100101234567894\"}", zoekOpANummer("1234567894"));
    }

    @Test
    public void historischANummer() {
        Persoonsvraag vraag = maakZoekOpANummerVraag("1234567890");
        vraag.setGevraagdeRubrieken(Collections.singletonList("51.01.10"));
        assertEquals("{\"inhoud\":\"00000000Ha01A00000000000225101701100102234567890\"}", plaatsPersoonsvraag(vraag));
    }

    @Test
    public void legeGevraagdeRubriek() {
        Persoonsvraag vraag = maakZoekOpANummerVraag("1234567890");
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "01.02.30", "04.05.10"));
        assertEquals("{\"inhoud\":\"00000000Ha01A00000000000220101701100101234567890\"}", plaatsPersoonsvraag(vraag));
    }

    @Test
    public void geenGevraagdeRubrieken() {
        Persoonsvraag vraag = maakZoekOpANummerVraag("1234567890");
        vraag.setGevraagdeRubrieken(Collections.emptyList());
        assertEquals("{\"inhoud\":\"00000000Ha01A0000000000000\"}", plaatsPersoonsvraag(vraag));
    }

    @Test
    public void populatieBeperking() {
        Persoonsvraag vraag = maakZoekOpANummerVraag("1234567890");
        vraag.setPartijCode("002101");
        assertEquals("{\"foutreden\":\"G\"}", plaatsPersoonsvraag(vraag));
    }

    @Test
    public void geheimhouding() {
        Persoonsvraag vraag = maakZoekOpANummerVraag("1234567895");
        vraag.setPartijCode("000701");
        assertEquals("{\"foutreden\":\"G\"}", plaatsPersoonsvraag(vraag));
    }

    @Test
    public void leegVerzoek() {
        assertEquals("{\"foutreden\":\"U\"}", plaatsPersoonsvraag(maakPersoonvraag()));
    }

    @Test
    public void geenResultaat() {
        assertEquals("{\"foutreden\":\"G\"}", zoekOpANummer("9999999999"));
    }

    @Test
    public void nullAutorisatie() {
        Persoonsvraag vraag = maakPersoonvraag();
        vraag.setPartijCode(null);
        vraag.setSoortDienst(SoortDienst.ZOEK_PERSOON);
        assertEquals("{\"foutreden\":\"X\"}", plaatsPersoonsvraag(vraag));
    }

    @Test
    public void nietBestaandeAutorisatie() {
        Persoonsvraag vraag = maakPersoonvraag();
        vraag.setPartijCode("001701");
        vraag.setSoortDienst(SoortDienst.ZOEK_PERSOON);
        assertEquals("{\"foutreden\":\"X\"}", plaatsPersoonsvraag(vraag));
    }

    @Test
    public void verkeerdeDienst() {
        Persoonsvraag vraag = maakPersoonvraag();
        vraag.setPartijCode("001901");
        vraag.setSoortDienst(SoortDienst.ZOEK_PERSOON);
        assertEquals("{\"foutreden\":\"X\"}", plaatsPersoonsvraag(vraag));
    }

    @Test
    public void nietGeautoriseerdVoorGevraagdeRubriek() {
        Persoonsvraag vraag = maakZoekOpANummerVraag("1234567890");
        vraag.setPartijCode("002001");
        assertEquals("{\"foutreden\":\"X\"}", plaatsPersoonsvraag(vraag));
    }

    @Test
    public void nietGeautoriseerdVoorZoekRubriek() {
        Persoonsvraag vraag = maakZoekOpANummerVraag("1234567890");
        vraag.setPartijCode("001801");
        vraag.setZoekRubrieken(Collections.singletonList("01.01.30"));
        assertEquals("{\"foutreden\":\"X\"}", plaatsPersoonsvraag(vraag));
    }

    @Test
    public void testcaseUc1004_01C10T120() {
        Persoonsvraag vraag = maakPersoonvraag();
        ZoekCriterium criteriumPostcode = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        criteriumPostcode.setWaarde("1234AA");
        ZoekCriterium criteriumNadereBijhoudingsaard = new ZoekCriterium(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getNaam());
        criteriumNadereBijhoudingsaard.setWaarde("M");
        ZoekCriterium criteriumInschrijvingDatum = new ZoekCriterium(Element.PERSOON_INSCHRIJVING_DATUM.getNaam());
        criteriumInschrijvingDatum.setWaarde("1976-10-30");
        ZoekCriterium criteriumPersoonskaartGemeente = new ZoekCriterium(Element.PERSOON_PERSOONSKAART_PARTIJCODE.getNaam());
        criteriumPersoonskaartGemeente.setWaarde("190101");

        vraag.setZoekCriteria(Arrays.asList(criteriumPostcode, criteriumNadereBijhoudingsaard, criteriumInschrijvingDatum, criteriumPersoonskaartGemeente));
        vraag.setGevraagdeRubrieken(Collections.singletonList("01.01.10"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));

        assertEquals("{\"foutreden\":\"G\"}", plaatsPersoonsvraag(vraag));
    }

    @Test
    public void testcaseUc1004_01C10T130() {
        Persoonsvraag vraag = maakPersoonvraag();

        vraag.setZoekCriteria(Arrays.asList(
                criterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER, "3760342305", null),
                criterium(Element.PERSOON_BIJHOUDING_PARTIJCODE, "190101", null),
                criterium(Element.PERSOON_ADRES_SOORTCODE, "W", null),
                criterium(Element.PERSOON_ADRES_GEMEENTEDEEL, null, null),
                criterium(Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING, "1976-10-30", null),
                criterium(Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE, "Karel Doormanstraat", null),
                criterium(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE, "Karel Doormanstraat", null),
                criterium(Element.PERSOON_ADRES_HUISNUMMER, "23", null),
                criterium(Element.PERSOON_ADRES_HUISLETTER, "B", null),
                criterium(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING, null, null),
                criterium(Element.PERSOON_ADRES_LOCATIETENOPZICHTEVANADRES, null, null),
                criterium(Element.PERSOON_ADRES_POSTCODE, "2811EW", null),
                criterium(Element.PERSOON_ADRES_WOONPLAATSNAAM, "Reeuwijk", null),
                criterium(Element.PERSOON_ADRES_IDENTIFICATIECODEADRESSEERBAAROBJECT, "0000000000000000", null),
                criterium(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING, "0000000000000000", null),
                criterium(Element.PERSOON_ADRES_LOCATIEOMSCHRIJVING, null, null),
                criterium(Element.PERSOON_ADRES_REDENWIJZIGINGCODE, "I", criterium(Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE, "I", null)),
                criterium(Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_WAARDE, null, null)));
        vraag.setGevraagdeRubrieken(Collections.singletonList("01.01.10"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));

        assertEquals("{\"foutreden\":\"G\"}", plaatsPersoonsvraag(vraag));
    }

    @Test
    public void testcaseUc1004_01C10T170() {
        Persoonsvraag vraag = maakPersoonvraag();

        vraag.setZoekCriteria(Collections.singletonList(
                criterium(Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_WAARDE, null, null)
        ));

        vraag.setGevraagdeRubrieken(Collections.singletonList("01.01.10"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));
        vraag.setSoortDienst(SoortDienst.ZOEK_PERSOON);

        assertEquals("{\"foutreden\":\"U\"}", plaatsPersoonsvraag(vraag));
    }

    @Test
    public void testcaseUc1004_02C40T070() {
        Persoonsvraag vraag = maakPersoonvraag();
        vraag.setZoekCriteria(Collections.singletonList(
                criterium(Element.PERSOON_ADRES_GEMEENTECODE, "0599", null)
        ));
        vraag.setGevraagdeRubrieken(Collections.singletonList("01.01.10"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));
        assertEquals("{\"foutreden\":\"U\"}", plaatsPersoonsvraag(vraag));
    }

    private ZoekCriterium criterium(Element element, String waarde, ZoekCriterium of) {
        ZoekCriterium criterium = new ZoekCriterium(element.getNaam());
        criterium.setWaarde(waarde);
        criterium.setOf(of);
        return criterium;
    }

    private Persoonsvraag maakPersoonvraag() {
        Persoonsvraag persoonvraag = new Persoonsvraag();
        persoonvraag.setPartijCode("001801");
        persoonvraag.setSoortDienst(SoortDienst.ZOEK_PERSOON);

        return persoonvraag;
    }

    private Persoonsvraag maakZoekOpANummerVraag(final String anr) {
        Persoonsvraag vraag = maakPersoonvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        criterium.setWaarde(anr);
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Collections.singletonList("01.01.10"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));
        vraag.setSoortDienst(SoortDienst.ZOEK_PERSOON);
        return vraag;
    }

    private String plaatsPersoonsvraag(final Persoonsvraag vraag) {
        String verzoek = new JsonStringSerializer().serialiseerNaarString(vraag);
        putMessage(PersoonsvraagQueue.VERZOEK.getQueueNaam(), verzoek, "ref-1");
        return expectMessage(PersoonsvraagQueue.ANTWOORD.getQueueNaam());
    }

    private String zoekOpANummer(final String anr) {
        return plaatsPersoonsvraag(maakZoekOpANummerVraag(anr));
    }
}
