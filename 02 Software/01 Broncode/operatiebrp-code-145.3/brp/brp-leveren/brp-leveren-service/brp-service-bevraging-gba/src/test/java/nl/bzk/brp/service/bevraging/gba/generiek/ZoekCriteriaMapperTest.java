/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.generiek;

import static org.junit.Assert.assertEquals;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.gba.domain.bevraging.ZoekCriterium;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonGeneriekVerzoek;
import org.junit.Test;

public class ZoekCriteriaMapperTest {

    @Test
    public void converteer() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.map(criterium("waarde"));

        assertEquals(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam(), criteria.getElementNaam());
        assertEquals("waarde", criteria.getWaarde());
        assertEquals(null, criteria.getOf());
        assertEquals(Zoekoptie.EXACT, criteria.getZoekOptie());
    }

    @Test
    public void converteerInSlimZoekenContext() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.mapInSlimZoekenContext(criterium("waarde"));

        assertEquals(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam(), criteria.getElementNaam());
        assertEquals("waarde", criteria.getWaarde());
        assertEquals(null, criteria.getOf());
        assertEquals(Zoekoptie.KLEIN, criteria.getZoekOptie());
    }

    @Test
    public void converteerMetHoofdletter() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.map(criterium("Waarde"));
        assertEquals("Waarde", criteria.getWaarde());
        assertEquals(Zoekoptie.EXACT, criteria.getZoekOptie());
    }

    @Test
    public void converteerMetHoofdletterInSlimZoekenContext() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.mapInSlimZoekenContext(criterium("Waarde"));
        assertEquals("Waarde", criteria.getWaarde());
        assertEquals(Zoekoptie.EXACT, criteria.getZoekOptie());
    }

    @Test
    public void converteerMetCijfers() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.map(criterium("waarde01"));
        assertEquals("waarde01", criteria.getWaarde());
        assertEquals(Zoekoptie.EXACT, criteria.getZoekOptie());
    }

    @Test
    public void converteerMetCijfersInSlimZoekenContext() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.mapInSlimZoekenContext(criterium("waarde01"));
        assertEquals("waarde01", criteria.getWaarde());
        assertEquals(Zoekoptie.KLEIN, criteria.getZoekOptie());
    }

    @Test
    public void converteerMetHoofdletterEnWildcardInSlimZoekenContext() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.mapInSlimZoekenContext(criterium("Waarde*"));
        assertEquals("Waarde", criteria.getWaarde());
        assertEquals(Zoekoptie.VANAF_EXACT, criteria.getZoekOptie());
    }

    @Test
    public void converteerMetDiakrietEnWildcardInSlimZoekenContext() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.mapInSlimZoekenContext(criterium("Wäärde*"));
        assertEquals("Wäärde", criteria.getWaarde());
        assertEquals(Zoekoptie.VANAF_EXACT, criteria.getZoekOptie());
    }

    @Test
    public void converteerMetDiakrietEnLowercase() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.map(criterium("wá"));
        assertEquals("wá", criteria.getWaarde());
        assertEquals(Zoekoptie.EXACT, criteria.getZoekOptie());
    }

    @Test
    public void converteerMetDiakrietEnUppercase() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.map(criterium("Wá"));
        assertEquals("Wá", criteria.getWaarde());
        assertEquals(Zoekoptie.EXACT, criteria.getZoekOptie());
    }

    @Test
    public void converteerMetDiakrietEnLowercaseInSlimZoekenContext() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.mapInSlimZoekenContext(criterium("wá"));
        assertEquals("wá", criteria.getWaarde());
        assertEquals(Zoekoptie.EXACT, criteria.getZoekOptie());
    }

    @Test
    public void converteerMetDiakrietEnUppercaseInSlimZoekenContext() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.mapInSlimZoekenContext(criterium("Wá"));
        assertEquals("Wá", criteria.getWaarde());
        assertEquals(Zoekoptie.EXACT, criteria.getZoekOptie());
    }

    @Test
    public void converteerGeenWaarde() {
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.map(criterium);
        assertEquals(null, criteria.getWaarde());
        assertEquals(Zoekoptie.LEEG, criteria.getZoekOptie());
    }

    @Test
    public void converteerGeenWaardeInSlimZoekenContext() {
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.mapInSlimZoekenContext(criterium);
        assertEquals(null, criteria.getWaarde());
        assertEquals(Zoekoptie.LEEG, criteria.getZoekOptie());
    }

    @Test
    public void converteerNullWaarde() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.map(criterium(null));
        assertEquals(null, criteria.getWaarde());
        assertEquals(Zoekoptie.LEEG, criteria.getZoekOptie());
    }

    @Test
    public void converteerNullWaardeInSlimZoekenContext() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.mapInSlimZoekenContext(criterium(null));
        assertEquals(null, criteria.getWaarde());
        assertEquals(Zoekoptie.LEEG, criteria.getZoekOptie());
    }

    @Test
    public void converteerLegeWaarde() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.map(criterium(""));
        assertEquals("", criteria.getWaarde());
        assertEquals(Zoekoptie.EXACT, criteria.getZoekOptie());
    }

    @Test
    public void converteerLegeWaardeInSlimZoekenContext() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.mapInSlimZoekenContext(criterium(""));
        assertEquals("", criteria.getWaarde());
        assertEquals(Zoekoptie.KLEIN, criteria.getZoekOptie());
    }

    @Test
    public void converteerWildcardWaarde() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.map(criterium("wildcard*"));
        assertEquals("wildcard*", criteria.getWaarde());
        assertEquals(Zoekoptie.EXACT, criteria.getZoekOptie());
    }

    @Test
    public void converteerWildcardWaardeInSlimZoekenContext() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.mapInSlimZoekenContext(criterium("wildcard*"));
        assertEquals("wildcard", criteria.getWaarde());
        assertEquals(Zoekoptie.VANAF_KLEIN, criteria.getZoekOptie());
    }

    @Test
    public void converteerExacteWaarde() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.map(criterium("\\exact"));
        assertEquals("\\exact", criteria.getWaarde());
        assertEquals(Zoekoptie.EXACT, criteria.getZoekOptie());
    }

    @Test
    public void converteerExacteWaardeInSlimZoekenContext() {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.mapInSlimZoekenContext(criterium("\\exact"));
        assertEquals("exact", criteria.getWaarde());
        assertEquals(Zoekoptie.EXACT, criteria.getZoekOptie());
    }

    @Test
    public void converteerMetOf() {
        ZoekCriterium OfCriterium = new ZoekCriterium(Element.PERSOON_VOORNAAM_NAAM.getNaam());
        OfCriterium.setWaarde("ofWaarde");

        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        criterium.setWaarde("waarde");
        criterium.setOf(OfCriterium);

        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.map(criterium);

        assertEquals(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam(), criteria.getElementNaam());
        assertEquals("waarde", criteria.getWaarde());
        assertEquals(Zoekoptie.EXACT, criteria.getZoekOptie());
        assertEquals(Element.PERSOON_VOORNAAM_NAAM.getNaam(), criteria.getOf().getElementNaam());
        assertEquals("ofWaarde", criteria.getOf().getWaarde());
        assertEquals(Zoekoptie.EXACT, criteria.getOf().getZoekOptie());
    }

    @Test
    public void converteerMetOfInSlimZoekenContext() {
        ZoekCriterium OfCriterium = new ZoekCriterium(Element.PERSOON_VOORNAAM_NAAM.getNaam());
        OfCriterium.setWaarde("ofWaarde");

        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        criterium.setWaarde("waarde");
        criterium.setOf(OfCriterium);

        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = ZoekCriteriaMapper.mapInSlimZoekenContext(criterium);

        assertEquals(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam(), criteria.getElementNaam());
        assertEquals("waarde", criteria.getWaarde());
        assertEquals(Zoekoptie.KLEIN, criteria.getZoekOptie());
        assertEquals(Element.PERSOON_VOORNAAM_NAAM.getNaam(), criteria.getOf().getElementNaam());
        assertEquals("ofWaarde", criteria.getOf().getWaarde());
        assertEquals(Zoekoptie.EXACT, criteria.getOf().getZoekOptie());
    }

    private ZoekCriterium criterium(final String waarde) {
        ZoekCriterium criterium = new ZoekCriterium(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam());
        criterium.setWaarde(waarde);
        return criterium;
    }
}
