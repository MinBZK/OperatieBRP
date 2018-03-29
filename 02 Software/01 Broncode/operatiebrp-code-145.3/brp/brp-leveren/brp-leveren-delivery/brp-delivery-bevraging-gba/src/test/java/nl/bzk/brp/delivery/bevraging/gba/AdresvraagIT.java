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
import nl.bzk.brp.gba.domain.bevraging.Adresvraag;
import nl.bzk.brp.gba.domain.bevraging.AdresvraagQueue;
import nl.bzk.brp.gba.domain.bevraging.ZoekCriterium;
import org.junit.Test;

public class AdresvraagIT extends AbstractIT {

    @Test
    public void meerderePersonenOpPostcode() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium postcodeCriterium = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        postcodeCriterium.setWaarde("1234AA");
        ZoekCriterium huisletterCriterium = new ZoekCriterium(Element.PERSOON_ADRES_HUISLETTER.getNaam());
        huisletterCriterium.setWaarde("a");
        vraag.setZoekCriteria(Arrays.asList(postcodeCriterium, huisletterCriterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("08.11.60"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.ADRES);

        assertEquals(
                "{\"inhoud\":\"00000000Xa01000960101701100105555555556080211120001211600061234AA0101701100105555555557080211120001211600061234AA\"}",
                plaatsAdresvraag(vraag));
    }

    @Test
    public void enkelePersoonOpPostcodeEnHuisnummer() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium postcodeCriterium = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        postcodeCriterium.setWaarde("1234AA");
        ZoekCriterium huisnummerCriterium = new ZoekCriterium(Element.PERSOON_ADRES_HUISNUMMER.getNaam());
        huisnummerCriterium.setWaarde("1");
        vraag.setZoekCriteria(Arrays.asList(postcodeCriterium, huisnummerCriterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("08.11.60"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.ADRES);

        assertEquals("{\"inhoud\":\"00000000Xa01000480101701100105555555555080211120001111600061234AA\"}",
                plaatsAdresvraag(vraag));
    }

    @Test
    public void enkelePersoonOpIdentificatiecodeVerblijfsplaats() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getNaam());
        criterium.setWaarde("0626200010016003");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("08.11.60"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.ADRES);

        assertEquals("{\"inhoud\":\"00000000Xa01000480101701100105555555558080211120001211600061234AA\"}",
                plaatsAdresvraag(vraag));
    }

    @Test
    public void medebewoners() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        criterium.setWaarde("5555555556");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.PERSOON);

        assertEquals("{\"inhoud\":\"00000000Xa01000960101701100105555555556080211120001211600061234AA0101701100105555555557080211120001211600061234AA\"}",
                plaatsAdresvraag(vraag));
    }

    @Test
    public void geenMedebewoners() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        criterium.setWaarde("5555555558");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.PERSOON);

        assertEquals("{\"inhoud\":\"00000000Xa01000480101701100105555555558080211120001211600061234AA\"}",
                plaatsAdresvraag(vraag));
    }

    @Test
    public void persoonZonderIndentificatieCodeNrAanduiding() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        criterium.setWaarde("5555555560");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.PERSOON);

        assertEquals("{\"foutreden\":\"G\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void nietBestaandeToegangLeveringsautorisatie() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        criterium.setWaarde("1234AA");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.ADRES);
        vraag.setPartijCode("000099");

        assertEquals("{\"foutreden\":\"X\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void nietGeautoriseerdVoorGevraagdeRubriek() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        criterium.setWaarde("1234AA");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.30"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.ADRES);

        assertEquals("{\"foutreden\":\"X\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void nietGeautoriseerdVoorZoekRubriek() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        criterium.setWaarde("1234AA");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("08.11.30"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.ADRES);

        assertEquals("{\"foutreden\":\"X\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void nietUniekePersoonsidentificatie() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        criterium.setWaarde("1234AA");
        ZoekCriterium geboortePlaatsCriterium = new ZoekCriterium(Element.PERSOON_GEBOORTE_GEMEENTECODE.getNaam());
        geboortePlaatsCriterium.setWaarde("0622");
        vraag.setZoekCriteria(Arrays.asList(criterium, geboortePlaatsCriterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Arrays.asList("01.01.10", "01.01.20"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.PERSOON);

        assertEquals("{\"foutreden\":\"U\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void ontbrekendZoekCriteriumGeenAdresGegevenR2288() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        criterium.setWaarde("1234AA");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.PERSOON);

        assertEquals("{\"foutreden\":\"U\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void nietUniekeAdresidentificatie() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        criterium.setWaarde("1234AA");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.ADRES);

        assertEquals("{\"foutreden\":\"U\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void alleGeenAdresidentificatie() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        criterium.setWaarde("1234EE");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.ADRES);

        assertEquals("{\"foutreden\":\"U\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void slechtsEenGeenAdresidentificatie() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        criterium.setWaarde("1234FF");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.ADRES);

        assertEquals("{\"foutreden\":\"U\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void eenPersoonGeenAdresidentificatieMetPersoonsidentificatie() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        criterium.setWaarde("1234GG");
        ZoekCriterium geslachtsnaamCriterium = new ZoekCriterium(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getNaam());
        geslachtsnaamCriterium.setWaarde("Guirten");
        vraag.setZoekCriteria(Arrays.asList(criterium, geslachtsnaamCriterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.PERSOON);

        assertEquals("{\"foutreden\":\"G\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void geenPersoonGevonden() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        criterium.setWaarde("999999999");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("01.01.10"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.PERSOON);

        assertEquals("{\"foutreden\":\"G\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void geenAdresGevonden() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        criterium.setWaarde("9999ZZ");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("08.11.60"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.ADRES);

        assertEquals("{\"foutreden\":\"G\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void geenActuelePersoonslijstOpAdres() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        criterium.setWaarde("1234BB");
        ZoekCriterium nrCriterium = new ZoekCriterium(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getNaam());
        nrCriterium.setWaarde("0626200010016004");
        vraag.setZoekCriteria(Arrays.asList(criterium, nrCriterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("08.11.60"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.ADRES);

        assertEquals("{\"foutreden\":\"Z\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void geenActuelePersoonslijstOpPersoon() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        criterium.setWaarde("5555555559");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("08.11.60"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.PERSOON);

        assertEquals("{\"foutreden\":\"Z\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void volgordeANummersXa01BerichtAdresvraag() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        criterium.setWaarde("1234CC");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("08.11.60"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.ADRES);

        assertEquals("{\"inhoud\":\"00000000Xa010009801017011001055555555610802211200022011600061234CC01017011001095555555550802211200022011600061234CC\"}",
                plaatsAdresvraag(vraag));
    }

    @Test
    public void volgordeANummersXa01BerichtPersoonsvraag() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        criterium.setWaarde("9555555555");
        vraag.setZoekCriteria(Collections.singletonList(criterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("08.11.60"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.PERSOON);

        assertEquals("{\"inhoud\":\"00000000Xa010009801017011001055555555610802211200022011600061234CC01017011001095555555550802211200022011600061234CC\"}",
                plaatsAdresvraag(vraag));
    }

    @Test
    public void adresFunctieFilteringPersoonsvraag() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        criterium.setWaarde("5555555562");
        ZoekCriterium functieCriterium = new ZoekCriterium(Element.PERSOON_ADRES_SOORTCODE.getNaam());
        functieCriterium.setWaarde("W");
        vraag.setZoekCriteria(Arrays.asList(criterium, functieCriterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Arrays.asList("01.01.10", "08.10.10"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.PERSOON);

        assertEquals("{\"inhoud\":\"00000000Xa010004901017011001055555555620802211200023011600061234DD\"}",
                plaatsAdresvraag(vraag));
    }

    @Test
    public void legeVraagOpPersoon() {
        Adresvraag vraag = maakAdresvraag();
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.PERSOON);
        assertEquals("{\"foutreden\":\"U\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void vraagOpAdresOpNaamOpenbareRuimte() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium norCriterium = new ZoekCriterium(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getNaam());
        norCriterium.setWaarde("HogeHuys");
        ZoekCriterium huisnummerCriterium = new ZoekCriterium(Element.PERSOON_ADRES_HUISNUMMER.getNaam());
        huisnummerCriterium.setWaarde("13");
        vraag.setZoekCriteria(Arrays.asList(norCriterium, huisnummerCriterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Arrays.asList("08.11.15", "08.11.20"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.ADRES);
        assertEquals("{\"foutreden\":\"P\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void testR2375() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium afgekorteNaamOpenbareRuimete = new ZoekCriterium(Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE.getNaam());
        afgekorteNaamOpenbareRuimete.setWaarde("S vd Oyeln");
        vraag.setZoekCriteria(Collections.singletonList(afgekorteNaamOpenbareRuimete));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.10"));
        vraag.setZoekRubrieken(Collections.singletonList("08.11.10"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.ADRES);
        assertEquals("{\"foutreden\":\"P\"}", plaatsAdresvraag(vraag));
    }

    @Test
    public void testFoutieveDatumLeidtTotR2308() {
        Adresvraag vraag = maakAdresvraag();
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_ADRES_POSTCODE.getNaam());
        criterium.setWaarde("1234BB");
        ZoekCriterium geboorteDatumCriterium = new ZoekCriterium(Element.PERSOON_GEBOORTE_DATUM.getNaam());
        geboorteDatumCriterium.setWaarde("2017-02-29");
        vraag.setZoekCriteria(Arrays.asList(criterium, geboorteDatumCriterium));
        vraag.setGevraagdeRubrieken(Arrays.asList("01.01.10", "08.11.20", "08.11.60"));
        vraag.setZoekRubrieken(Collections.singletonList("08.11.60"));
        vraag.setSoortIdentificatie(Adresvraag.SoortIdentificatie.PERSOON);

        assertEquals(
                "{\"gevraagdeRubrieken\":[\"01.01.10\",\"08.11.20\",\"08.11.60\"],\"partijCode\":\"001801\",\"soortDienst\":\"ZOEK_PERSOON_OP_ADRESGEGEVENS\","
                        + "\"soortIdentificatie\":\"PERSOON\","
                        + "\"zoekCriteria\":[{\"naam\":\"Persoon.Adres.Postcode\",\"waarde\":\"1234BB\"},{\"naam\":\"Persoon.Geboorte.Datum\","
                        + "\"waarde\":\"2017-02-29\"}],\"zoekRubrieken\":[\"08.11.60\"]}", plaatsFouteAdresvraag(vraag));
    }

    private Adresvraag maakAdresvraag() {
        Adresvraag vraag = new Adresvraag();
        vraag.setPartijCode("001801");
        vraag.setSoortDienst(SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS);
        return vraag;
    }

    private String plaatsAdresvraag(final Adresvraag vraag) {
        String verzoek = new JsonStringSerializer().serialiseerNaarString(vraag);
        putMessage(AdresvraagQueue.VERZOEK.getQueueNaam(), verzoek, "ref-1");
        return expectMessage(AdresvraagQueue.ANTWOORD.getQueueNaam());
    }

    private String plaatsFouteAdresvraag(final Adresvraag vraag) {
        String verzoek = new JsonStringSerializer().serialiseerNaarString(vraag);
        putMessage(AdresvraagQueue.VERZOEK.getQueueNaam(), verzoek, "stuk-1");
        return expectMessage("DLQ." + AdresvraagQueue.VERZOEK.getQueueNaam());
    }
}
