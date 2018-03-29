/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Test;


public class VerblijfsrechtElementTest extends AbstractElementTest {
    private ElementBuilder builder = new ElementBuilder();

    @Test
    public void R2379_98_datumvoorzienEindeGevuld() {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("98");
        params.datumAanvang(20010101);
        params.datumMededeling(20010101);
        params.datumVoorzienEinde(20100101);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2379, meldingen.get(0).getRegel());
    }

    @Test
    public void R2379_0_datumvoorzienEindeGevuld() {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("0");
        params.datumAanvang(20010101);
        params.datumMededeling(20010101);
        params.datumVoorzienEinde(20100101);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R2379_98_datumvoorzienEindeNull() {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("98");
        params.datumAanvang(20010101);
        params.datumMededeling(20010101);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R2379_98_datumvoorzienEindeLeeg() {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("98");
        params.datumAanvang(20010101);
        params.datumMededeling(20010101);
        params.datumVoorzienEinde(null);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R1900() {
        when(getDynamischeStamtabelRepository().getVerblijfsrechtByCode(any(String.class))).thenReturn(null);
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("98");
        params.datumAanvang(20010101);
        params.datumMededeling(20010101);
        params.datumVoorzienEinde(null);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1900, meldingen.get(0).getRegel());
    }

    @Test
    @Bedrijfsregel(Regel.R2750)
    public void testAanduidingVerblijfsrechtNietGeldigOpDatumAanvang() {
        Verblijfsrecht verblijfsrecht = new Verblijfsrecht("98", "omschrijving");
        verblijfsrecht.setDatumAanvangGeldigheid(2001_01_01);
        verblijfsrecht.setDatumEindeGeldigheid(2001_01_02);
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("98");
        params.datumAanvang(20100101);
        params.datumMededeling(20100101);
        when(getDynamischeStamtabelRepository().getVerblijfsrechtByCode(any(String.class))).thenReturn(verblijfsrecht);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2750, meldingen.get(0).getRegel());
    }

    @Test
    public void testDatumMededelingVoorVandaag () {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("98");
        params.datumAanvang(20160101);
        params.datumMededeling(DatumUtil.gisteren());
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testDatumMededelingOpVandaag () {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("98");
        params.datumAanvang(20160101);
        params.datumMededeling(DatumUtil.vandaag());
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testDatumMededelingNaVandaag () {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("98");
        params.datumAanvang(20160101);
        params.datumMededeling(DatumUtil.morgen());
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2330, meldingen.get(0).getRegel());
    }

    @Test
    public void testDatumAamvangOnbekend() {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("98");
        params.datumMededeling(20160101);
        params.datumAanvang(0);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2349, meldingen.get(0).getRegel());
    }

    @Test
    public void testDatumMededelingOnbekend() {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("98");
        params.datumMededeling(0);
        params.datumAanvang(20160101);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2350, meldingen.get(0).getRegel());
    }

    @Test
    public void testDatumVoorzienEindeOnbekend() {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode(Verblijfsrecht.GEGEVEN_IN_ONDERZOEK);
        params.datumVoorzienEinde(20160100);
        params.datumMededeling(20160101);
        params.datumAanvang(20160101);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(2, meldingen.size());
        assertEquals(Regel.R2331, meldingen.get(0).getRegel());
        assertEquals(Regel.R2351, meldingen.get(1).getRegel());
    }

    @Test
    public void testDatumAanvangVoorVandaag() {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("98");
        params.datumAanvang(DatumUtil.gisteren());
        params.datumMededeling(20160101);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testDatumAanvangOpVandaag () {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("98");
        params.datumAanvang(DatumUtil.vandaag());
        params.datumMededeling(20160101);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testDatumAanvangNaVandaag () {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("98");
        params.datumAanvang(DatumUtil.morgen());
        params.datumMededeling(20160101);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2332, meldingen.get(0).getRegel());
    }

    @Test
    public void testDatumVoozienEindeNaDatumAanvang() {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("0");
        params.datumAanvang(20160101);
        params.datumMededeling(20160101);
        params.datumVoorzienEinde(20170101);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testDatumVoozienEindeNietGevuld() {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("0");
        params.datumAanvang(20160101);
        params.datumMededeling(20160101);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testDatumVoozienEindeOpDatumAanvang() {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("0");
        params.datumAanvang(20160101);
        params.datumMededeling(20160101);
        params.datumVoorzienEinde(20160101);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2331, meldingen.get(0).getRegel());
    }

    @Test
    public void testDatumVoozienEindeVoorDatumAanvang() {
        final ElementBuilder.VerblijfsrechtParameters params = new ElementBuilder.VerblijfsrechtParameters();
        params.aanduidingCode("0");
        params.datumAanvang(20160101);
        params.datumMededeling(20160101);
        params.datumVoorzienEinde(20150101);
        final VerblijfsrechtElement element = builder.maakVerblijfsrechtElement("comm_id", params);
        final List<MeldingElement> meldingen = element.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2331, meldingen.get(0).getRegel());
    }
}
