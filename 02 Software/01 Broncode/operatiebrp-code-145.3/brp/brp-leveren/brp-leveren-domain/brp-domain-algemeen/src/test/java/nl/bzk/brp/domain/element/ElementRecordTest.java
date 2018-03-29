/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.element;

import org.junit.Assert;
import org.junit.Test;

public class ElementRecordTest {

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
    private static final String INBERICHT = "InBer";
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
//        final ElementRecord elementRecord = new ElementRecord();
//        elementRecord.setId(ID);
//        elementRecord.setSrt(SRT);
//        elementRecord.setNaam(NAAM);
//        elementRecord.setElementNaam(ELEMENTNAAM);
//        elementRecord.setObjectType(OBJECTTYPE);
//        elementRecord.setExpressiebasistype(EXPRESSIEBASISTYPE);
//        elementRecord.setSrtinh(SRT_INH);
//        elementRecord.setType(TYPE);
//        elementRecord.setAliasVan(ALIAS_VAN);
//        elementRecord.setInverseassociatieidentcode(INVERSE_ASSOCIATIE_CODE);
//        elementRecord.setIdentexpressie(IDENT_EXPRESSIE);
//        elementRecord.setDatumAanvang(DATUM_AANVANG);
//        elementRecord.setDatumEinde(DATUM_EINDE);
//        elementRecord.setAutorisatie(AUTORISATIE);
//        elementRecord.setMininumLengte(MIN_LENGTE);
//        elementRecord.setHeeftExpressie(HEEFT_EXPRESSIE);
//        elementRecord.setGroepId(GROEP_ID);
//        elementRecord.setVerantwoordingCategorie(VERANTWOORDING_CAT);
//        elementRecord.setHistoriePatroon(HIS_PATROON);
//        elementRecord.setVolgnummer(VOLG_NR);
//        elementRecord.setInBericht(INBERICHT);
//        elementRecord.setXsdIdent(XSD_IDENT);
//        elementRecord.setIdentDb(IDENT_DB);
//        elementRecord.setHisIdentDb(HIS_IDENT_DB);
//        elementRecord.setTabel(TABEL);
//        elementRecord.setHisTabel(HISTABEL);
//        elementRecord.setSchema(SCHEMA);
//        elementRecord.setTypeIdentDb(TYPE_IDENT_DB);
//        elementRecord.setMaximumLengte(MAX_LENGTE);
//
//        Assert.assertEquals(ID, elementRecord.getId());
//        Assert.assertEquals(SRT, elementRecord.getSrt());
//        Assert.assertEquals(NAAM, elementRecord.getNaam());
//        Assert.assertEquals(ELEMENTNAAM, elementRecord.getElementNaam());
//        Assert.assertEquals(OBJECTTYPE, elementRecord.getObjectType());
//        Assert.assertEquals(EXPRESSIEBASISTYPE, elementRecord.getExpressiebasistype());
//        Assert.assertEquals(SRT_INH, elementRecord.getSrtinh());
//        Assert.assertEquals(TYPE, elementRecord.getType());
//        Assert.assertEquals(ALIAS_VAN, elementRecord.getAliasVan());
//        Assert.assertEquals(INVERSE_ASSOCIATIE_CODE, elementRecord.getInverseassociatieidentcode());
//        Assert.assertEquals(IDENT_EXPRESSIE, elementRecord.getIdentexpressie());
//        Assert.assertEquals(DATUM_AANVANG, elementRecord.getDatumAanvang());
//        Assert.assertEquals(DATUM_EINDE, elementRecord.getDatumEinde());
//        Assert.assertEquals(AUTORISATIE, elementRecord.getAutorisatie());
//        Assert.assertEquals(MIN_LENGTE, elementRecord.getMininumLengte());
//        Assert.assertEquals(MAX_LENGTE, elementRecord.getMaximumLengte());
//        Assert.assertEquals(HEEFT_EXPRESSIE, elementRecord.getHeeftExpressie());
//        Assert.assertEquals(GROEP_ID, elementRecord.getGroepId());
//        Assert.assertEquals(VERANTWOORDING_CAT, elementRecord.getVerantwoordingCategorie());
//        Assert.assertEquals(HIS_PATROON, elementRecord.getHistoriePatroon());
//        Assert.assertEquals(VOLG_NR, elementRecord.getVolgnummer());
//        Assert.assertEquals(INBERICHT, elementRecord.getInBericht());
//        Assert.assertEquals(XSD_IDENT, elementRecord.getXsdIdent());
//        Assert.assertEquals(IDENT_DB, elementRecord.getIdentDb());
//        Assert.assertEquals(HIS_IDENT_DB, elementRecord.getHisIdentDb());
//        Assert.assertEquals(TABEL, elementRecord.getTabel());
//        Assert.assertEquals(HISTABEL, elementRecord.getHisTabel());
//        Assert.assertEquals(SCHEMA, elementRecord.getSchema());
//        Assert.assertEquals(TYPE_IDENT_DB, elementRecord.getTypeIdentDb());
//
//        Assert.assertEquals("ElementRecord{id=1, naam='Naam', elementNaam='ElementNaam'}", elementRecord.toString());
//    }
}
