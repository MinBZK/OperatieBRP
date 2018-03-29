/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.jbehave;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.AbstractZoekPersoonVerzoek;
import nl.bzk.brp.test.common.jbehave.ConversieUtil;
import nl.bzk.brp.tooling.apitest.dto.XmlVerzoek;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.model.ExamplesTable;

/**
 * Stappen voor het doen van een service verzoek.
 */
public final class VerzoekSteps {

    private static final String ZOEKCRITERIA = "zoekcriteria";

    /**
     * Voert het technisch verzoek 'Synchroniseer Persoon' uit.
     * @param table tabel met params
     * @throws IOException als het fout gaat
     */
    @Given("verzoek synchroniseer persoon: $table")
    public void givenVerzoekSynchroniseerPersoon(final ExamplesTable table) throws IOException {
        StoryController.getOmgeving().getApiService().getSynchronisatieApiService().synchroniseerPersoon(ConversieUtil.alsKeyValueMap(table));
    }

    /**
     * Voert het technisch verzoek 'Synchroniseer Stamgegeven' uit.
     * @param table tabel met params
     */
    @Given("verzoek synchroniseer stamgegeven: $table")
    public void givenVerzoekSynchroniseerStamgegeven(final ExamplesTable table) {
        StoryController.getOmgeving().getApiService().getSynchronisatieApiService().synchroniseerStamgegeven(ConversieUtil.alsKeyValueMap(table));
    }

    /**
     * Voert het technisch verzoek 'Stuur vrij bericht' uit.
     * @param table tabel met params
     */
    @Given("verzoek verstuur vrij bericht: $table")
    public void givenVerzoekStuurVrijBericht(final ExamplesTable table) {
        StoryController.getOmgeving().getApiService().getVerstuurVrijBerichtApiService().verzoekVerstuurVrijBericht(ConversieUtil.alsKeyValueMap(table));
    }

    /**
     * Voert het technisch verzoek 'Stuur stuf bericht' uit.
     * @param table tabel met params
     */
    @Given("verzoek verstuur stuf bericht: $table")
    public void givenVerzoekStuurStufBericht(final ExamplesTable table) {
        StoryController.getOmgeving().getApiService().getVerstuurStufBerichtApiService().verzoekVerstuurStufBericht(ConversieUtil.alsKeyValueMap(table));
    }

    /**
     * Voert het technisch verzoek 'Plaats Afnemerindicatie' uit.
     * @param table tabel met params
     * @throws IOException als het fout gaat
     */
    @Given("verzoek plaats afnemerindicatie: $table")
    public void givenVerzoekPlaatsAfnemerindicatie(final ExamplesTable table) throws IOException {
        StoryController.getOmgeving().getApiService().getOnderhoudAfnemerindicatieService().plaatsAfnemerindicatie(ConversieUtil.alsKeyValueMap(table));
    }

    /**
     * Voert het technisch verzoek 'Verwijder Afnemerindicatie' uit.
     * @param table tabel met params
     * @throws IOException als het fout gaat
     */
    @Given("verzoek verwijder afnemerindicatie: $table")
    public void givenVerzoekVerwijderAfnemerindicatie(final ExamplesTable table) throws IOException {
        StoryController.getOmgeving().getApiService().getOnderhoudAfnemerindicatieService().verwijderAfnemerindicatie(ConversieUtil.alsKeyValueMap(table));
    }

    /**
     * Voert het technisch verzoek 'Geef Details Persoon' uit.
     * @param table tabel met params
     */
    @Given("verzoek geef details persoon: $table")
    public void givenVerzoekGeefDetailsPersoon(final ExamplesTable table) {
        StoryController.getOmgeving().getApiService().getBevragingApiService().getGeefDetailsPersoonApiService()
                .verzoek(ConversieUtil.alsKeyValueMap(table));
    }

    /**
     * Voert het technisch verzoek 'Zoek Persoon' uit.
     * @param table tabel met params
     */
    @Given("verzoek zoek persoon: $table")
    public void givenVerzoekZoekPersoon(final ExamplesTable table) {
        final Map<String, String> verzoekMap = ConversieUtil.unquoteMapValues(ConversieUtil.alsKeyValueMap(table));
        verzoekMap.remove(ZOEKCRITERIA);
        final LinkedList<AbstractZoekPersoonVerzoek.ZoekCriteria> zoekCriteriaList = maakZoekCriteriaBasis(table);
        StoryController.getOmgeving().getApiService().getBevragingApiService().getZoekPersoonApiService().verzoekZoekPersoon(verzoekMap, zoekCriteriaList);
    }

    /**
     * Voert het technisch verzoek 'Zoek Persoon Op Adres' uit.
     * @param table tabel met params
     */
    @Given("verzoek zoek persoon op adres: $table")
    public void givenVerzoekZoekPersoonOpAdres(final ExamplesTable table) {
        final Map<String, String> verzoekMap = ConversieUtil.unquoteMapValues(ConversieUtil.alsKeyValueMap(table));
        verzoekMap.remove(ZOEKCRITERIA);
        final LinkedList<AbstractZoekPersoonVerzoek.ZoekCriteria> zoekCriteriaList = maakZoekCriteriaBasis(table);
        StoryController.getOmgeving().getApiService().getBevragingApiService().getZoekPersoonApiService()
                .verzoekZoekPersoonOpAdres(verzoekMap, zoekCriteriaList);
    }

    /**
     * Voert het technisch verzoek 'Geef Medebewoners Van Persoon' uit.
     * @param table tabel met params
     */
    @Given("verzoek geef medebewoners van persoon: $table")
    public void givenVerzoekGeefMedebewonersVanPersoon(final ExamplesTable table) {
        final Map<String, String> verzoekMap = ConversieUtil.unquoteMapValues(ConversieUtil.alsKeyValueMap(table));
        verzoekMap.remove(ZOEKCRITERIA);
        StoryController.getOmgeving().getApiService().getBevragingApiService().getGeefMedebewonersApiService().verzoek(verzoekMap);
    }

    private LinkedList<AbstractZoekPersoonVerzoek.ZoekCriteria> maakZoekCriteriaBasis(final ExamplesTable table) {
        final LinkedList<AbstractZoekPersoonVerzoek.ZoekCriteria> zoekCriteriaList = Lists.newLinkedList();
        for (final Map<String, String> rowMap : table.getRows()) {
            if (!ZOEKCRITERIA.equals(rowMap.get(ConversieUtil.KEY))) {
                continue;
            }
            final String value = rowMap.get(ConversieUtil.VALUE);
            final String[] splitValues = value.split(",");
            final AbstractZoekPersoonVerzoek.ZoekCriteria criteria = new AbstractZoekPersoonVerzoek.ZoekCriteria();
            for (final String splitValue : splitValues) {
                final String[] splitCriteria = splitValue.split("=");
                switch (splitCriteria[0]) {
                    case "ZoekOptie":
                        criteria.setZoekOptie(Zoekoptie.getByNaam(splitCriteria[1]));
                        break;
                    case "ElementNaam":
                        criteria.setElementNaam(splitCriteria[1]);
                        break;
                    case "Waarde":
                        criteria.setWaarde(splitCriteria[1]);
                        break;
                    default:
                        throw new IllegalArgumentException("Onbekend criteria opgeven: " + splitCriteria[0]);
                }
            }
            zoekCriteriaList.add(criteria);
        }
        return zoekCriteriaList;
    }

    /**
     * Voert het XML verzoek 'Synchroniseer Persoon' uit.
     * @param verzoekFile file met XML verzoek
     * @param transporteur de transporteur
     * @param ondertekenaar de ondertekenaar
     */
    @Given("verzoek synchroniseer persoon met xml $verzoekFile transporteur $transporteur ondertekenaar $ondertekenaar")
    public void givenXmlVerzoekSynchroniseerPersoon(final String verzoekFile, final String transporteur, final String ondertekenaar) {
        StoryController.getOmgeving().getApiService().getSynchronisatieApiService()
                .synchroniseerPersoonMetXml(new XmlVerzoek(verzoekFile, transporteur, ondertekenaar));
    }

    /**
     * Voert het XML verzoek 'Synchroniseer Stamgegeven' uit.
     * @param verzoekFile file met XML verzoek
     * @param transporteur de transporteur
     * @param ondertekenaar de ondertekenaar
     */
    @Given("verzoek synchroniseer stamgegeven met xml $verzoekFile transporteur $transporteur ondertekenaar $ondertekenaar")
    public void givenVerzoekSynchroniseerStamgegevenMetXml(final String verzoekFile, final String transporteur, final String ondertekenaar) {
        StoryController.getOmgeving().getApiService().getSynchronisatieApiService().
                synchroniseerStamgegevenMetXml(new XmlVerzoek(verzoekFile, transporteur, ondertekenaar));
    }

    /**
     * Voert het XML verzoek 'Plaats Afnemerindicatie' uit.
     * @param verzoekFile file met XML verzoek
     * @param transporteur de transporteur
     * @param ondertekenaar de ondertekenaar
     */
    @Given("verzoek plaats afnemerindicatie met xml $verzoekFile transporteur $transporteur ondertekenaar $ondertekenaar")
    public void givenVerzoekPlaatsAfnemerindicatieMetXml(final String verzoekFile, final String transporteur, final String ondertekenaar) {
        StoryController.getOmgeving().getApiService().getOnderhoudAfnemerindicatieService()
                .plaatsAfnemerindicatieMetXml(new XmlVerzoek(verzoekFile, transporteur, ondertekenaar));
    }

    /**
     * Voert het XML verzoek 'Verwijder Afnemerindicatie' uit.
     * @param verzoekFile file met XML verzoek
     * @param transporteur de transporteur
     * @param ondertekenaar de ondertekenaar
     */
    @Given("verzoek verwijder afnemerindicatie met xml $verzoekFile transporteur $transporteur ondertekenaar $ondertekenaar")
    public void givenVerzoekVerwijderAfnemerindicatieMetXml(final String verzoekFile, final String transporteur, final String ondertekenaar) {
        StoryController.getOmgeving().getApiService().getOnderhoudAfnemerindicatieService().
                verwijderAfnemerindicatieMetXml(new XmlVerzoek(verzoekFile, transporteur, ondertekenaar));
    }

    /**
     * Voert het XML verzoek 'Geef Details Persoon' uit.
     * @param verzoekFile file met XML verzoek
     * @param transporteur de transporteur
     * @param ondertekenaar de ondertekenaar
     */
    @Given("verzoek geef details persoon met xml $verzoekFile transporteur $transporteur ondertekenaar $ondertekenaar")
    public void givenVerzoekGeefDetailsPersoonMetXml(final String verzoekFile, final String transporteur, final String ondertekenaar) {
        StoryController.getOmgeving().getApiService().getBevragingApiService().getGeefDetailsPersoonApiService().
                verzoekMetXml(new XmlVerzoek(verzoekFile, transporteur, ondertekenaar));
    }

    /**
     * Voert het XML verzoek 'Zoek Persoon' uit.
     * @param verzoekFile file met XML verzoek
     * @param transporteur de transporteur
     * @param ondertekenaar de ondertekenaar
     */
    @Given("verzoek zoek persoon met xml $verzoekFile transporteur $transporteur ondertekenaar $ondertekenaar")
    public void givenVerzoekZoekPersoonMetXml(final String verzoekFile, final String transporteur, final String ondertekenaar) {
        StoryController.getOmgeving().getApiService().getBevragingApiService().getZoekPersoonApiService()
                .zoekPersoonMetXml(new XmlVerzoek(verzoekFile, transporteur, ondertekenaar));
    }
    /**
     * Voert het XML verzoek 'Zoek Persoon' uit.
     * @param verzoekFile file met XML verzoek
     * @param transporteur de transporteur
     * @param ondertekenaar de ondertekenaar
     */
    @Given("verzoek zoek persoon op adres met xml $verzoekFile transporteur $transporteur ondertekenaar $ondertekenaar")
    public void givenVerzoekZoekPersoonOpAdresMetXml(final String verzoekFile, final String transporteur, final String ondertekenaar) {
        StoryController.getOmgeving().getApiService().getBevragingApiService().getZoekPersoonApiService()
                .zoekPersoonOpAdresMetXml(new XmlVerzoek(verzoekFile, transporteur, ondertekenaar));
    }

    /**
     * Voert het XML verzoek 'Geef medebewoners' uit.
     * @param verzoekFile file met XML verzoek
     * @param transporteur de transporteur
     * @param ondertekenaar de ondertekenaar
     */
    @Given("verzoek geef medebewoners met xml $verzoekFile transporteur $transporteur ondertekenaar $ondertekenaar")
    public void givenVerzoekGeefMedebewonersMetXml(final String verzoekFile, final String transporteur, final String ondertekenaar) {
        StoryController.getOmgeving().getApiService().getBevragingApiService().getGeefMedebewonersApiService().
                verzoekMetXml(new XmlVerzoek(verzoekFile, transporteur, ondertekenaar));
    }

    /**
     * Voert het XML verzoek 'Stuur Vrij Bericht' uit.
     * @param verzoekFile file met XML verzoek
     * @param transporteur de transporteur
     * @param ondertekenaar de ondertekenaar
     */
    @Given("verzoek vrijbericht met xml $verzoekFile transporteur $transporteur ondertekenaar $ondertekenaar")
    public void givenVerzoekVrijBerichtMetXml(final String verzoekFile, final String transporteur, final String ondertekenaar) {
        StoryController.getOmgeving().getApiService().
                getVerstuurVrijBerichtApiService().verstuurVrijBericht(new XmlVerzoek(verzoekFile, transporteur, ondertekenaar));

    }

    /**
     * Voert het XML verzoek 'Stuur Stuf¶ Bericht' uit.
     * @param verzoekFile file met XML verzoek
     * @param transporteur de transporteur
     * @param ondertekenaar de ondertekenaar
     */
    @Given("verzoek stufbericht met xml $verzoekFile transporteur $transporteur ondertekenaar $ondertekenaar")
    public void givenVerzoekstufBerichtMetXml(final String verzoekFile, final String transporteur, final String ondertekenaar) {
        StoryController.getOmgeving().getApiService().
                getVerstuurStufBerichtApiService().verstuurStufBericht(new XmlVerzoek(verzoekFile, transporteur, ondertekenaar));

    }

}
