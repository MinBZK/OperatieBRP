/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Calendar;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Bijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Geslachtsaanduiding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Naamgebruik;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.NadereBijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Nationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Verblijfsrecht;

import org.junit.Test;

/**
 */
public class BrpMapperUtilTest {

    @Test
    public void mapBrpAutoriteitVanAfgifte(){
        assertNull(BrpMapperUtil.mapBrpAutoriteitVanAfgifte(null,null));
        assertEquals("M",BrpMapperUtil.mapBrpAutoriteitVanAfgifte("M",null).getWaarde());
        Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(Integer.parseInt("100000")),new Lo3Datum(Integer.valueOf(20010101)),null);
        assertNull(BrpMapperUtil.mapBrpAutoriteitVanAfgifte(null, lo3Onderzoek).getWaarde());
    }

    @Test
    public void mapBrpBijhoudingsaard(){
        assertNull(BrpMapperUtil.mapBrpBijhoudingsaard(null,null));
        assertEquals("I",BrpMapperUtil.mapBrpBijhoudingsaard(Bijhoudingsaard.INGEZETENE,null).getWaarde());
        Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(Integer.parseInt("100000")),new Lo3Datum(Integer.valueOf(20010101)),null);
        assertNull(BrpMapperUtil.mapBrpBijhoudingsaard(null, lo3Onderzoek).getWaarde());
    }

    @Test
    public void mapBrpDatumTijd(){
        Calendar now = Calendar.getInstance();
        Timestamp timestamp = new Timestamp(now.getTime().getTime());
        BrpDatumTijd mapped = BrpMapperUtil.mapBrpDatumTijd(timestamp);
        final StringBuilder b = new StringBuilder("");
        b.append(now.get(Calendar.YEAR));
        b.append(String.format("%02d", now.get(Calendar.MONTH)+1));
        b.append(String.format("%02d", now.get(Calendar.DATE)));
        assertTrue(mapped.getWaarde().toString().startsWith(b.toString()));
    }

    @Test
    public void mapBrpSoortAdresCode(){
        assertNull(BrpMapperUtil.mapBrpSoortAdresCode(null,null));
        assertEquals("I",BrpMapperUtil.mapBrpBijhoudingsaard(Bijhoudingsaard.INGEZETENE,null).getWaarde());
        Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(Integer.parseInt("100000")),new Lo3Datum(Integer.valueOf(20010101)),null);
        assertNull(BrpMapperUtil.mapBrpSoortAdresCode(null, lo3Onderzoek).getWaarde());

    }

    @Test
    public void mapBrpGeslachtsaanduidingCode(){
        assertNull(BrpMapperUtil.mapBrpGeslachtsaanduidingCode(null,null));
        assertEquals("M",BrpMapperUtil.mapBrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN,null).getWaarde());
        Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(Integer.parseInt("100000")),new Lo3Datum(Integer.valueOf(20010101)),null);
        assertNull(BrpMapperUtil.mapBrpGeslachtsaanduidingCode(null, lo3Onderzoek).getWaarde());

    }

    @Test
    public void mapBrpNationaliteitCode(){
        assertNull(BrpMapperUtil.mapBrpNationaliteitCode(null,null));
        Nationaliteit nat = new Nationaliteit("NL",Short.parseShort("31"));
        assertEquals("31",BrpMapperUtil.mapBrpNationaliteitCode(nat,null).getWaarde().toString());
        Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(Integer.parseInt("100000")),new Lo3Datum(Integer.valueOf(20010101)),null);
        assertNull(BrpMapperUtil.mapBrpNationaliteitCode(null, lo3Onderzoek).getWaarde());

    }

    @Test
    public void mapBrpRedenEindeRelatieCode(){
        assertNull(BrpMapperUtil.mapBrpRedenEindeRelatieCode(null,null));
        assertEquals("A",BrpMapperUtil.mapBrpRedenEindeRelatieCode(new RedenBeeindigingRelatie('A',"anders"),null).getWaarde().toString());
        Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(Integer.parseInt("100000")),new Lo3Datum(Integer.valueOf(20010101)),null);
        assertNull(BrpMapperUtil.mapBrpRedenEindeRelatieCode(null, lo3Onderzoek).getWaarde());
    }

    @Test
    public void mapBrpNadereBijhoudingsaard(){
        assertNull(BrpMapperUtil.mapBrpNadereBijhoudingsaard(null,null));
        assertEquals("A",BrpMapperUtil.mapBrpNadereBijhoudingsaard(NadereBijhoudingsaard.ACTUEEL,null).getWaarde().toString());
        Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(Integer.parseInt("100000")),new Lo3Datum(Integer.valueOf(20010101)),null);
        assertNull(BrpMapperUtil.mapBrpNadereBijhoudingsaard(null, lo3Onderzoek).getWaarde());
    }

    @Test
    public void mapBrpAanduidingInhoudingOfVermissingReisdocument(){
        assertNull(BrpMapperUtil.mapBrpAanduidingInhoudingOfVermissingReisdocument(null,null));
        AanduidingInhoudingOfVermissingReisdocument aanduiding = new AanduidingInhoudingOfVermissingReisdocument('B',"Bijzonder");
        assertEquals("B",BrpMapperUtil.mapBrpAanduidingInhoudingOfVermissingReisdocument(aanduiding,null).getWaarde().toString());
        Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(Integer.parseInt("100000")),new Lo3Datum(Integer.valueOf(20010101)),null);
        assertNull(BrpMapperUtil.mapBrpAanduidingInhoudingOfVermissingReisdocument(null, lo3Onderzoek).getWaarde());
    }

    @Test
    public void mapBrpReisdocumentSoort(){
        assertNull(BrpMapperUtil.mapBrpReisdocumentSoort(null,null));
        SoortNederlandsReisdocument soort = new SoortNederlandsReisdocument("P","Paspoort");
        assertEquals("P",BrpMapperUtil.mapBrpReisdocumentSoort(soort,null).getWaarde().toString());
        Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(Integer.parseInt("100000")),new Lo3Datum(Integer.valueOf(20010101)),null);
        assertNull(BrpMapperUtil.mapBrpReisdocumentSoort(null, lo3Onderzoek).getWaarde());
    }

    @Test
    public void mapBrpSoortDocumentCode(){
        assertNull(BrpMapperUtil.mapBrpSoortDocumentCode(null,null));
        SoortDocument soort = new SoortDocument("P","Paspoort");
        assertEquals("P",BrpMapperUtil.mapBrpSoortDocumentCode(soort,null).getWaarde().toString());
        Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(Integer.parseInt("100000")),new Lo3Datum(Integer.valueOf(20010101)),null);
        assertNull(BrpMapperUtil.mapBrpSoortDocumentCode(null, lo3Onderzoek).getWaarde());
    }

    @Test
    public void mapBrpVerblijfsrechtCode(){
        assertNull(BrpMapperUtil.mapBrpVerblijfsrechtCode(null,null));
        Verblijfsrecht soort = new Verblijfsrecht(Short.parseShort("2"),"Paspoort");
        assertEquals("2",BrpMapperUtil.mapBrpVerblijfsrechtCode(soort,null).getWaarde().toString());
        Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(Integer.parseInt("100000")),new Lo3Datum(Integer.valueOf(20010101)),null);
        assertNull(BrpMapperUtil.mapBrpVerblijfsrechtCode(null, lo3Onderzoek).getWaarde());
    }

    @Test
    public void mapBrpNaamgebruikCode(){
        assertNull(BrpMapperUtil.mapBrpNaamgebruikCode(null,null));
        assertEquals("P",BrpMapperUtil.mapBrpNaamgebruikCode(Naamgebruik.PARTNER,null).getWaarde().toString());
        Lo3Onderzoek lo3Onderzoek = new Lo3Onderzoek(new Lo3Integer(Integer.parseInt("100000")),new Lo3Datum(Integer.valueOf(20010101)),null);
        assertNull(BrpMapperUtil.mapBrpNaamgebruikCode(null, lo3Onderzoek).getWaarde());
    }

    @Test
    public void mapCharacter(){
        assertNull(BrpMapperUtil.mapCharacter(null));
        assertNull(BrpMapperUtil.mapCharacter(""));
        assertEquals("S",BrpMapperUtil.mapCharacter("String").toString());
    }


}
