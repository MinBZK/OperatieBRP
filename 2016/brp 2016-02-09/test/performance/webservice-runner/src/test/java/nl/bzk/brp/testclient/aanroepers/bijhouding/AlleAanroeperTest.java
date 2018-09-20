/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.aanroepers.bijhouding;

import java.util.HashMap;

import nl.bzk.brp.testclient.aanroepers.AbstractAanroeper;
import nl.bzk.brp.testclient.misc.Eigenschappen;
import nl.bzk.brp.testclient.util.DatumUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class AlleAanroeperTest {
    public static final String      PARTIJ_GEMEENTE_ALMERE_CODE     = "3401";
    public static final String      PARTIJ_GEMEENTE_AMSTERDAM_CODE  = "36301";
    public static final String      PARTIJ_GEMEENTE_BREDA_CODE      = "75801";

    public static final String    GEMEENTE_ALMERE_CODE            = "0034";
    public static final String    GEMEENTE_AMSTERDAM_CODE         = "0363";
    public static final String    GEMEENTE_UTRECHT_CODE           = "0344";
    public static final String    GEMEENTE_DEN_HAAG_CODE          = "0518";
    public static final String    GEMEENTE_BREDA_CODE             = "0758";

    public static final String      WOONPLAATS_ALMERE_CODE          = "1270";
    public static final String      WOONPLAATS_AMSTERDAM_CODE       = "1024";
    public static final String      WOONPLAATS_UTRECHT_CODE         = "3295";
    public static final String      WOONPLAATS_DEN_HAAG_CODE        = "1245";
    public static final String      WOONPLAATS_BREDA_CODE           = "1702";
    public static final String      WOONPLAATS_SCHIPHOL_CODE        = "1618";

    public static final String        LAND_NEDERLAND_CODE             = "6030";
    public static final String        LAND_BELGIE_CODE                = "5010";
    public static final String        LAND_FRANKRIJK_CODE             = "5002";
    public static final String        LAND_AFGANISTAN_CODE            = "6023";

    @Mock
    private Eigenschappen eigenschappen;
    private final HashMap<String, String> parameterMap4 = new HashMap<String, String>();
    private final String datumAanvang = DatumUtil.vandaagXmlString();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(eigenschappen.getFlowPrevalidatie()).thenReturn(Boolean.TRUE);

        String bsn = "bsnbsnbsn";

        parameterMap4.put(AbstractAanroeper.PARAMETER_PREVALIDATIE, "J");

        parameterMap4.put(OverlijdenAanroeper.PARAMETER_REFERENTIENUMMER, "referentienummer");
        parameterMap4.put(OverlijdenAanroeper.PARAMETER_BSN, bsn);
        parameterMap4.put(OverlijdenAanroeper.PARAMETER_DATUM_AANVANG_GELDIGHEID, datumAanvang);
        parameterMap4.put(OverlijdenAanroeper.PARAMETER_DATUM_OVERLIJDEN, datumAanvang);
        parameterMap4.put(OverlijdenAanroeper.PARAMETER_GEMEENTECODE, GEMEENTE_ALMERE_CODE);
        parameterMap4.put(OverlijdenAanroeper.PARAMETER_LANDCODE, LAND_NEDERLAND_CODE);
        parameterMap4.put(OverlijdenAanroeper.PARAMETER_WOONPLAATSNAAM, WOONPLAATS_ALMERE_CODE);
    }

//    @Test
//    public void testOverlijden() throws Exception {
//
//        String referentienummer = "" + new Random().nextInt(1000);
//
//        OverlijdenRegistreerOverlijdenBijhouding bijhouding =
//                new OverlijdenAanroeper(eigenschappen).creeerOverlijdenRegistratieOverlijdenBijhouding();
//        String xml = new TestDummyClass(bijhouding).convertToXml();
//
////        System.out.println(xml);
//    }
//
//    @Test
//    public void testHuwelijk() throws Exception {
//        HuwelijkPartnerschapRegistreerHuwelijkPartnerschapBijhouding bijhouding =
//                new RegistreerHuwelijkEnPartnerschapAanroeper(eigenschappen)
//                    .creeerHuwelijkPartnerschapRegistratieHuwelijkBijhouding();
////        System.out.println(new TestDummyClass(bijhouding).convertToXml());
//    }
//
//    @Test
//    public void testGeboorte() throws Exception {
//        AfstammingRegistreerGeboorteBijhouding bijhouding =
//                new InschrijvingGeboorteAanroeper(eigenschappen)
//                    .creeerAfstammingInschrijvingAangifteGeboorteBijhouding();
////        System.out.println(new TestDummyClass(bijhouding).convertToXml());
//    }
//
//    @Test
//    public void testGeboorteMetErkenning() throws Exception {
//        AfstammingRegistreerGeboorteBijhouding bijhouding =
//                new InschrijvingGeboorteMetErkenningAanroeper(eigenschappen)
//                    .creeerAfstammingInschrijvingAangifteGeboorteMetErkenningBijhouding();
////        System.out.println(new TestDummyClass(bijhouding).convertToXml());
//    }
//
//    @Test
//    public void testAdoptie() throws Exception {
//        AfstammingRegistreerAdoptieBijhouding bijhouding =
//                new AdoptieAanroeper(eigenschappen)
//                    .creeerAfstammingRegistreerAdoptieBijhouding();
////        System.out.println(new TestDummyClass(bijhouding).convertToXml());
//    }
//
//    @Test
//    public void testVerhuizing() throws Exception {
//        MigratieRegistreerVerhuizingBijhouding bijhouding =
//                new VerhuizingAanroeper(eigenschappen)
//                    .creeerMigratieVerhuizingBijhouding();
////        System.out.println(new TestDummyClass(bijhouding).convertToXml());
//    }
//
//    @Test
//    public void testCorrectieAdres() throws Exception {
//        MigratieCorrigeerAdresNederlandBijhouding bijhouding =
//                new CorrectieAdresAanroeper(eigenschappen)
//                    .creeerMigratieCorrectieAdresBinnenNLBijhouding();
////        System.out.println(new TestDummyClass(bijhouding).convertToXml());
//    }
//
    @Test
    public void k() {
        String value = "nn,nnn";
        String delimiter = "\"";
        StringBuffer sb = new  StringBuffer();

        if (StringUtils.indexOfAny(value, new char[] {',', ';', '\'',  '\"'}) >= 0) {
            sb.append(delimiter).append(value).append(delimiter);
            // TODO, escapen van \' en \" ?? of dubbele waarden?
        } else {
            sb.append(value);
        }
        System.out.println(sb.toString());
    }
}
