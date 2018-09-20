/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

import org.junit.Test;

public class Lo3OnderzoekTest {

    @Test
    public void testBuild() throws Exception {
        assertNotNull(Lo3Onderzoek.build(new Lo3Integer(1), new Lo3Datum(20), new Lo3Datum(30), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1)));
        assertNotNull(Lo3Onderzoek.build(new Lo3Integer(1), new Lo3Datum(20), new Lo3Datum(30), null));
        assertNotNull(Lo3Onderzoek.build(new Lo3Integer(1), new Lo3Datum(20), null, null));
        assertNotNull(Lo3Onderzoek.build(new Lo3Integer(1), null, null, null));
        assertNotNull(Lo3Onderzoek.build(null, new Lo3Datum(20), new Lo3Datum(30), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1)));
        assertNotNull(Lo3Onderzoek.build(null, new Lo3Datum(20), null, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1)));
        assertNotNull(Lo3Onderzoek.build(null, new Lo3Datum(20), null, null));
        assertNotNull(Lo3Onderzoek.build(null, null, new Lo3Datum(30), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1)));
        assertNotNull(Lo3Onderzoek.build(null, null, new Lo3Datum(30), null));
        assertNull(Lo3Onderzoek.build(null, null, null, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1)));

    }

    @Test
    public void testGetAanduidingGegevensInOnderzoek() throws Exception {
        Lo3Onderzoek lo3 = Lo3Onderzoek.build(new Lo3Integer(1), new Lo3Datum(20), new Lo3Datum(30), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1));
        assertNotNull(lo3);
        assertEquals(new Lo3Integer(1), lo3.getAanduidingGegevensInOnderzoek());
        lo3 = Lo3Onderzoek.build(null, new Lo3Datum(20), new Lo3Datum(30), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1));
        assertNotNull(lo3);
        assertNull(lo3.getAanduidingGegevensInOnderzoek());
    }

    @Test
    public void testGetAanduidingGegevensInOnderzoekCode() throws Exception {
        Lo3Onderzoek lo3 = Lo3Onderzoek.build(new Lo3Integer(1), new Lo3Datum(20), new Lo3Datum(30), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1));
        assertNotNull(lo3);
        assertEquals("000001", lo3.getAanduidingGegevensInOnderzoekCode());
        lo3 = Lo3Onderzoek.build(null, new Lo3Datum(20), new Lo3Datum(30), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1));
        assertNotNull(lo3);
        assertNull(lo3.getAanduidingGegevensInOnderzoekCode());

    }

    @Test
    public void testGetLo3Herkomst() throws Exception {
        Lo3Onderzoek lo3 = Lo3Onderzoek.build(new Lo3Integer(1), new Lo3Datum(20), new Lo3Datum(30), new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 1, 1));
        assertNotNull(lo3);
        assertEquals(Lo3CategorieEnum.CATEGORIE_01, lo3.getLo3Herkomst().getCategorie());
    }

    @Test
    public void testBepaalRelevantOnderzoekAllemaalNull() throws Exception {
        Collection<Lo3Onderzoek> onderzoeken = new ArrayList<>();
        assertNull(Lo3Onderzoek.bepaalRelevantOnderzoek(onderzoeken));

    }

    @Test
    public void testBepaalRelevantOnderzoekLopendOnderzoek() throws Exception {
        Collection<Lo3Onderzoek> onderzoeken = new ArrayList<>();
        onderzoeken.add(createLo3Onderzoek(1, 99999910, 99999941));
        onderzoeken.add(createLo3Onderzoek(2, 99999941, 99999951));
        onderzoeken.add(null);
        onderzoeken.add(createLo3Onderzoek(3, 99999950, null));
        Lo3Onderzoek relevant = Lo3Onderzoek.bepaalRelevantOnderzoek(onderzoeken);
        assertNotNull(relevant);
        assertEquals(3, relevant.getAanduidingGegevensInOnderzoek().getIntegerWaarde().intValue());
    }

    @Test
    public void testBepaalRelevantOnderzoekLopendOnderzoek2() throws Exception {
        Collection<Lo3Onderzoek> onderzoeken = new ArrayList<>();
        onderzoeken.add(createLo3Onderzoek(1, 99999910, 99999940));
        onderzoeken.add(createLo3Onderzoek(2, 99999941, 99999950));
        onderzoeken.add(createLo3Onderzoek(3, 99999960, null));
        onderzoeken.add(createLo3Onderzoek(4, 99999970, null));
        onderzoeken.add(createLo3Onderzoek(5, 99999960, null));
        Lo3Onderzoek relevant = Lo3Onderzoek.bepaalRelevantOnderzoek(onderzoeken);
        assertNotNull(relevant);
        assertEquals(4, relevant.getAanduidingGegevensInOnderzoek().getIntegerWaarde().intValue());
    }

    @Test
    public void testBepaalRelevantOnderzoekGeen() throws Exception {
        Collection<Lo3Onderzoek> onderzoeken = new ArrayList<>();
        onderzoeken.add(createLo3Onderzoek(1, 99999910, 99999941));
        onderzoeken.add(createLo3Onderzoek(2, 99999941, 99999952));
        onderzoeken.add(createLo3Onderzoek(3, 99999950, 99999975));
        onderzoeken.add(createLo3Onderzoek(4, 99999960, 99999971));
        Lo3Onderzoek relevant = Lo3Onderzoek.bepaalRelevantOnderzoek(onderzoeken);
        assertNotNull(relevant);
        assertEquals(3, relevant.getAanduidingGegevensInOnderzoek().getIntegerWaarde().intValue());
    }

    private Lo3Onderzoek createLo3Onderzoek(Integer onderzoek, Integer ingang, Integer einde) {
        Lo3Onderzoek result;
        if (einde != null) {
            result = new Lo3Onderzoek(new Lo3Integer(onderzoek), new Lo3Datum(ingang), new Lo3Datum(einde));
        } else {
            result = new Lo3Onderzoek(new Lo3Integer(onderzoek), new Lo3Datum(ingang), null);
        }

        return result;

    }

    @Test
    public void testConsolideerOnderzoekenEmptyList() throws Exception {
        Collection<Lo3Onderzoek> onderzoeken = new ArrayList<>();
        assertNull(Lo3Onderzoek.consolideerOnderzoeken(onderzoeken, Lo3CategorieEnum.CATEGORIE_01));
        assertNull(Lo3Onderzoek.consolideerOnderzoeken(null, Lo3CategorieEnum.CATEGORIE_01));
    }

    @Test
    public void testConsolideerOnderzoekenListMet1Onderzoek() throws Exception {
        Collection<Lo3Onderzoek> onderzoeken = new ArrayList<>();
        onderzoeken.add(createLo3Onderzoek(1, 99999910, 99999941));
        Lo3Onderzoek onderzoek = Lo3Onderzoek.consolideerOnderzoeken(onderzoeken, Lo3CategorieEnum.CATEGORIE_01);
        assertEquals(1, onderzoek.getAanduidingGegevensInOnderzoek().getIntegerWaarde().intValue());
        assertEquals(99999910, onderzoek.getDatumIngangOnderzoek().getIntegerWaarde().intValue());
    }

    @Test
    public void testConsolideerOnderzoeken() throws Exception {
        Collection<Lo3Onderzoek> onderzoeken = new ArrayList<>();
        onderzoeken.add(createLo3Onderzoek(1, 99999910, 99999941));
        onderzoeken.add(createLo3Onderzoek(2, 99999941, 99999952));
        Lo3Onderzoek onderzoek = Lo3Onderzoek.consolideerOnderzoeken(onderzoeken, Lo3CategorieEnum.CATEGORIE_01);
        assertEquals(10000, onderzoek.getAanduidingGegevensInOnderzoek().getIntegerWaarde().intValue());
        assertEquals(99999941, onderzoek.getDatumIngangOnderzoek().getIntegerWaarde().intValue());
        assertEquals(99999952, onderzoek.getDatumEindeOnderzoek().getIntegerWaarde().intValue());
    }

    @Test
    public void testConsolideerOnderzoekenMet1Open() throws Exception {
        Collection<Lo3Onderzoek> onderzoeken = new ArrayList<>();
        onderzoeken.add(createLo3Onderzoek(1, 99999910, 99999941));
        onderzoeken.add(createLo3Onderzoek(2, 99999941, 99999952));
        onderzoeken.add(createLo3Onderzoek(3, 99999960, null));
        Lo3Onderzoek onderzoek = Lo3Onderzoek.consolideerOnderzoeken(onderzoeken, Lo3CategorieEnum.CATEGORIE_01);
        assertEquals(10003, onderzoek.getAanduidingGegevensInOnderzoek().getIntegerWaarde().intValue());
        assertEquals(99999960, onderzoek.getDatumIngangOnderzoek().getIntegerWaarde().intValue());
        assertEquals(null, onderzoek.getDatumEindeOnderzoek());

    }

    @Test
    public void testConsolideerOnderzoekenMet3Open() throws Exception {
        Collection<Lo3Onderzoek> onderzoeken = new ArrayList<>();
        onderzoeken.add(createLo3Onderzoek(2, 99999910, 99999941));
        onderzoeken.add(createLo3Onderzoek(2, 99999941, 99999952));
        onderzoeken.add(createLo3Onderzoek(38989898, 99999960, null));
        onderzoeken.add(createLo3Onderzoek(38989898, 99999970, null));
        onderzoeken.add(createLo3Onderzoek(38989898, 99999950, null));
        Lo3Onderzoek onderzoek = Lo3Onderzoek.consolideerOnderzoeken(onderzoeken, Lo3CategorieEnum.CATEGORIE_01);
        assertEquals(19898, onderzoek.getAanduidingGegevensInOnderzoek().getIntegerWaarde().intValue());
        assertEquals(99999970, onderzoek.getDatumIngangOnderzoek().getIntegerWaarde().intValue());
        assertEquals(null, onderzoek.getDatumEindeOnderzoek());

    }

    @Test
    public void testConsolideerOnderzoekenMet3OpenVerschillendeElementNummers() throws Exception {
        Collection<Lo3Onderzoek> onderzoeken = new ArrayList<>();
        onderzoeken.add(createLo3Onderzoek(1, 99999910, 99999941));
        onderzoeken.add(createLo3Onderzoek(2, 99999941, 99999952));
        onderzoeken.add(createLo3Onderzoek(3555555, 99999960, null));
        onderzoeken.add(createLo3Onderzoek(38989898, 99999970, null));
        onderzoeken.add(createLo3Onderzoek(32222222, 99999950, null));
        Lo3Onderzoek onderzoek = Lo3Onderzoek.consolideerOnderzoeken(onderzoeken, Lo3CategorieEnum.CATEGORIE_01);
        assertEquals(10000, onderzoek.getAanduidingGegevensInOnderzoek().getIntegerWaarde().intValue());
        assertEquals(99999970, onderzoek.getDatumIngangOnderzoek().getIntegerWaarde().intValue());
        assertEquals(null, onderzoek.getDatumEindeOnderzoek());

    }

    @Test
    public void testEquals() {
        Lo3Onderzoek lo3a = createLo3Onderzoek(1, 99999910, 99999941);
        Lo3Onderzoek lo3b = createLo3Onderzoek(1, 99999910, 99999941);
        Lo3Onderzoek lo3c = createLo3Onderzoek(1, 99999910, 99999942);
        Lo3Onderzoek lo3d = createLo3Onderzoek(1, 99999911, 99999941);
        Lo3Onderzoek lo3e = createLo3Onderzoek(2, 99999910, 99999941);
        Lo3Onderzoek lo3f = null;
        assertTrue(lo3a.equals(lo3a));
        assertTrue(lo3a.equals(lo3b));
        assertFalse(lo3a.equals(lo3c));
        assertFalse(lo3a.equals(lo3d));
        assertFalse(lo3a.equals(lo3e));
        assertFalse(lo3a.equals(lo3f));
    }

}
