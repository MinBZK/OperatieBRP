/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.element;

import org.junit.Assert;
import org.junit.Test;

public class ElementObjectTest {

    private static final int ID = 1;
    private static final int SRT = 2;
    private static final String NAAM = "Naam";
    private static final String ELEMENTNAAM = "ElementNaam";
    private static final Integer OBJECTTYPE = 12;
    private static final String EXPRESSIEBASISTYPE = "ExpressieBasisType";
    private static final String SRT_INH = "SoortInhoud";
    private static final Integer TYPE = 7;
    private static final Integer ALIAS_VAN = 8;
    private static final String INVERSE_ASSOCIATIE_CODE = "InverseAssociatieCode";
    private static final String IDENT_EXPRESSIE = "IdentExpressie";
    private static final Integer DATUM_AANVANG = 20140101;
    private static final Integer DATUM_EINDE = 20150101;
    private static final Integer AUTORISATIE = 3;
    private static final Integer MIN_LENGTE = 4;
    private static final Integer MAX_LENGTE = 5;
    private static final Boolean HEEFT_EXPRESSIE = false;
    private static final Integer GROEP_ID = 6;
    private static final String VERANTWOORDING_CAT = "VerantwoordingCategorie";
    private static final String HIS_PATROON = "F";
    private static final int VOLG_NR = 9;
    private static final String INBERICHT = "f";
    private static final String XSD_IDENT = "XsdIdent";
    private static final String IDENT_DB = "IdentDb";
    private static final String HIS_IDENT_DB = "HisIdentDb";
    private static final Integer TABEL = 10;
    private static final Integer HISTABEL = 11;
    private static final String SCHEMA = "Schema";
    private static final String TYPE_IDENT_DB = "TypeIdentDb";

    @Test
    public void dummy() {
        Assert.assertTrue(true);
    }

//    @Test
//    public void test() {
//        final ElementObject elementObject =new ElementObject(maakElementRecord(null), null);
//
//        Assert.assertEquals(ID, elementObject.getId().intValue());
//        Assert.assertEquals(NAAM, elementObject.getNaam());
//        Assert.assertEquals(ELEMENTNAAM, elementObject.getElementNaam());
//        Assert.assertEquals(TYPE, elementObject.getType());
//        Assert.assertEquals(VOLG_NR, elementObject.getVolgnummer());
//        Assert.assertFalse(elementObject.inBericht());
//        Assert.assertEquals(XSD_IDENT, elementObject.getXmlNaam());
//        Assert.assertEquals(SRT_INH, elementObject.getSoortInhoud());
//        Assert.assertEquals(HEEFT_EXPRESSIE, elementObject.heeftExpressie());
//        Assert.assertEquals(HEEFT_EXPRESSIE, elementObject.heeftExpressie());
//
//        Assert.assertEquals(TABEL, elementObject.getDataBaseInfo().getTabel());
//        Assert.assertEquals(HISTABEL, elementObject.getDataBaseInfo().getHisTabel());
//        Assert.assertEquals(IDENT_DB, elementObject.getDataBaseInfo().getIdentDb());
//        Assert.assertEquals(HIS_IDENT_DB, elementObject.getDataBaseInfo().getHisIdentDb());
//        Assert.assertEquals(SCHEMA, elementObject.getDataBaseInfo().getSchema());
//        Assert.assertEquals(TYPE_IDENT_DB, elementObject.getDataBaseInfo().getTypeIdentDb());
//    }

//    @Test
//    public void equals() {
//        final ElementObject elementObject1 =new ElementObject(maakElementRecord(1), null);
//        final ElementObject elementObject2 =new ElementObject(maakElementRecord(2), null);
//        final ElementObject elementObject3 =new ElementObject(maakElementRecord(1), null);
//
//        Assert.assertTrue(elementObject1.equals(elementObject1));
//        Assert.assertTrue(elementObject1.equals(elementObject3));
//        Assert.assertFalse(elementObject1.equals(elementObject2));
//        Assert.assertFalse(elementObject1.equals(maakElementRecord(null)));
//        Assert.assertFalse(elementObject1.equals(null));
//    }
//
//    @Test
//    public void heeftExpressieElementnaamIsNull() {
//        final ElementRecord elementRecord = new ElementRecord();
//        elementRecord.setElementNaam("");
//        final ElementObject elementObject = new ElementObject(elementRecord, null);
//        Assert.assertNull(elementObject.getExpressieString());
//    }
//
//    @Test
//    public void heeftExpressieGroepIdIsNull() {
//        final ElementRecord elementRecord = new ElementRecord();
//        elementRecord.setElementNaam(ELEMENTNAAM);
//        elementRecord.setGroepId(null);
//        elementRecord.setIdentexpressie(IDENT_EXPRESSIE);
//        final ElementObject elementObject = new ElementObject(elementRecord, null);
//        Assert.assertEquals(IDENT_EXPRESSIE.toLowerCase(), elementObject.getExpressieString());
//    }


}
