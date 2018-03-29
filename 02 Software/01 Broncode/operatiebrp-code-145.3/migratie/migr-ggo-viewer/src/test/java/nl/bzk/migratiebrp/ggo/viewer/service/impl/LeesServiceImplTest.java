/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;

import org.junit.Test;

/**
 * Unit tests voor {@link LeesServiceImpl}.
 */
public class LeesServiceImplTest {

    private LeesServiceImpl service = new LeesServiceImpl();

    @Test
    public void testLezenNicBestandZonderKop() throws ReflectiveOperationException {
        final Method method = service.getClass().getDeclaredMethod("convertToLg01Bestand", byte[].class, FoutMelder.class);
        method.setAccessible(true);

        final String bericht = getMooiBerichtZonderKop();
        final FoutMelder foutMelder = new FoutMelder();
        byte[] resultaat = (byte[]) method.invoke(service, bericht.getBytes(), foutMelder);
        assertNotNull(resultaat);
        assertTrue(foutMelder.getFoutRegels().isEmpty());
        assertEquals(getLelijkBericht(), new String(resultaat));
    }

    @Test
    public void testLezenNicBestandMetKop() throws ReflectiveOperationException {
        final Method method = service.getClass().getDeclaredMethod("convertToLg01Bestand", byte[].class, FoutMelder.class);
        method.setAccessible(true);

        final String bericht = getMooiBerichtMetKop();
        final FoutMelder foutMelder = new FoutMelder();
        byte[] resultaat = (byte[]) method.invoke(service, bericht.getBytes(), foutMelder);
        assertNotNull(resultaat);
        assertTrue(foutMelder.getFoutRegels().isEmpty());
        assertEquals(getLelijkBericht().toLowerCase(), new String(resultaat).toLowerCase());
    }

    private String getMooiBerichtMetKop() {
        return new StringBuilder("00000000Lg01\n").append("0000000000000000000000000000000000000\n")
                .append("_01977\n")
                .append(getMooiBerichtZonderKop())
                .toString();
    }

    private String getMooiBerichtZonderKop() {
        return new StringBuilder("01_179_\n").append("       0110_010_1868196961\n")
                .append("       0120_009_422531881\n")
                .append("       0210_005_Libby\n")
                .append("       0240_008_Thatcher\n")
                .append("       0310_008_19660821\n")
                .append("       0320_004_0014\n")
                .append("       0330_004_6030\n")
                .append("       0410_001_V\n")
                .append("       6110_001_V\n")
                .append("       8210_004_0518\n")
                .append("       8220_008_19940930\n")
                .append("       8230_003_PKA\n")
                .append("       8510_008_19920808\n")
                .append("       8610_008_19940930\n")
                .append("02_166_\n")
                .append("       0210_009_Margareth\n")
                .append("       0230_003_van\n")
                .append("       0240_008_Gemerden\n")
                .append("       0310_008_19420831\n")
                .append("       0320_004_0013\n")
                .append("       0330_004_6030\n")
                .append("       0410_001_V\n")
                .append("       6210_008_19660821\n")
                .append("       8210_004_0518\n")
                .append("       8220_008_19940930\n")
                .append("       8230_002_PK\n")
                .append("       8510_008_19660821\n")
                .append("       8610_008_19940930\n")
                .append("03_152_\n")
                .append("       0210_005_Willy\n")
                .append("       0240_008_Thatcher\n")
                .append("       0310_008_19381118\n")
                .append("       0320_004_0017\n")
                .append("       0330_004_6030\n")
                .append("       0410_001_M\n")
                .append("       6210_008_19660821\n")
                .append("       8210_004_0518\n")
                .append("       8220_008_19940930\n")
                .append("       8230_002_PK\n")
                .append("       8510_008_19660821\n")
                .append("       8610_008_19940930\n")
                .append("04_086_\n")
                .append("       0510_004_0001\n")
                .append("       6310_003_001\n")
                .append("       8210_004_0518\n")
                .append("       8220_008_19940930\n")
                .append("       8230_002_PK\n")
                .append("       8510_008_19660821\n")
                .append("       8610_008_19940930\n")
                .append("05_227_\n")
                .append("       0110_010_7582961953\n")
                .append("       0120_009_390144265\n")
                .append("       0210_006_Philip\n")
                .append("       0240_006_Jansen\n")
                .append("       0310_008_19650217\n")
                .append("       0320_004_0026\n")
                .append("       0330_004_6030\n")
                .append("       0410_001_M\n")
                .append("       0610_008_19920808\n")
                .append("       0620_004_0014\n")
                .append("       0630_004_6030\n")
                .append("       1510_001_H\n")
                .append("       8210_004_0626\n")
                .append("       8220_008_19980624\n")
                .append("       8230_015_PL gerelateerde\n")
                .append("       8510_008_19980624\n")
                .append("       8610_008_19980624\n")
                .append("55_214_\n")
                .append("       0110_010_7582961953\n")
                .append("       0120_009_390144265\n")
                .append("       0210_006_Philip\n")
                .append("       0240_006_Jansen\n")
                .append("       0310_008_19650217\n")
                .append("       0320_004_0026\n")
                .append("       0330_004_6030\n")
                .append("       0410_001_M\n")
                .append("       0610_008_19920808\n")
                .append("       0620_004_0014\n")
                .append("       0630_004_6030\n")
                .append("       1510_001_H\n")
                .append("       8210_004_0518\n")
                .append("       8220_008_19940930\n")
                .append("       8230_002_PK\n")
                .append("       8510_008_19920808\n")
                .append("       8610_008_19940930\n")
                .append("07_077_\n")
                .append("       6810_008_19940930\n")
                .append("       6910_004_0518\n")
                .append("       7010_001_0\n")
                .append("       8010_004_0003\n")
                .append("       8020_017_20120701143501000\n")
                .append("       8710_001_P\n")
                .append("08_235_\n")
                .append("       0910_004_0626\n")
                .append("       0920_008_19980622\n")
                .append("       1010_001_W\n")
                .append("       1030_008_19980622\n")
                .append("       1110_010_S vd Oyeln\n")
                .append("       1115_038_Baron Schimmelpenninck van der Oyelaan\n")
                .append("       1120_002_16\n")
                .append("       1160_006_2252EB\n")
                .append("       1170_011_Voorschoten\n")
                .append("       1180_016_0626010010016001\n")
                .append("       1190_016_0626200010016001\n")
                .append("       7210_001_T\n")
                .append("       8510_008_20110316\n")
                .append("       8610_008_20110316\n")
                .append("58_126_\n")
                .append("       0910_004_0626\n")
                .append("       0920_008_19980622\n")
                .append("       1010_001_W\n")
                .append("       1030_008_19980622\n")
                .append("       1110_010_S vd Oyeln\n")
                .append("       1120_002_16\n")
                .append("       1160_006_2252EB\n")
                .append("       7210_001_I\n")
                .append("       8510_008_19980622\n")
                .append("       8610_008_19980624\n")
                .append("58_131_\n")
                .append("       0910_004_0518\n")
                .append("       0920_008_19901002\n")
                .append("       1010_001_W\n")
                .append("       1030_008_19951213\n")
                .append("       1110_014_Waldorpsstraat\n")
                .append("       1120_003_304\n")
                .append("       1160_006_2521CG\n")
                .append("       7210_001_I\n")
                .append("       8510_008_19951213\n")
                .append("       8610_008_19951214\n")
                .append("58_133_\n")
                .append("       0910_004_0518\n")
                .append("       0920_008_19901002\n")
                .append("       1010_001_W\n")
                .append("       1030_008_19901002\n")
                .append("       1110_017_Van Swietenstraat\n")
                .append("       1120_002_20\n")
                .append("       1160_006_2518EH\n")
                .append("       7210_001_A\n")
                .append("       8510_008_19901002\n")
                .append("       8610_008_19940930\n")
                .append("09_117_\n")
                .append("       0210_005_James\n")
                .append("       0240_006_Jansen\n")
                .append("       0310_008_20040912\n")
                .append("       0320_004_0626\n")
                .append("       0330_004_6030\n")
                .append("       8110_004_0518\n")
                .append("       8120_007_10A0520\n")
                .append("       8510_008_20040912\n")
                .append("       8610_008_20040912\n")
                .append("10_069_\n")
                .append("       3910_002_09\n")
                .append("       3920_008_20000110\n")
                .append("       3930_008_19900110\n")
                .append("       8510_008_19900110\n")
                .append("       8610_008_19900112\n")
                .toString();
    }

    private String getLelijkBericht() {
        return "00000000lg01000000000000000000000000000000000000001977011790110010186819696101200094225318810210005Libby0240008Thatcher03100081966082103200040014033000460300410001V6110001V821000405188220008199409308230003PKA851000819920808861000819940930021660210009Margareth0230003van0240008Gemerden03100081942083103200040013033000460300410001V621000819660821821000405188220008199409308230002PK851000819660821861000819940930031520210005Willy0240008Thatcher03100081938111803200040017033000460300410001M621000819660821821000405188220008199409308230002PK85100081966082186100081994093004086051000400016310003001821000405188220008199409308230002PK851000819660821861000819940930052270110010758296195301200093901442650210006Philip0240006Jansen03100081965021703200040026033000460300410001M06100081992080806200040014063000460301510001H821000406268220008199806248230015PL gerelateerde851000819980624861000819980624552140110010758296195301200093901442650210006Philip0240006Jansen03100081965021703200040026033000460300410001M06100081992080806200040014063000460301510001H821000405188220008199409308230002PK851000819920808861000819940930070776810008199409306910004051870100010801000400038020017201207011435010008710001P08235091000406260920008199806221010001W1030008199806221110010S vd Oyeln1115038Baron Schimmelpenninck van der Oyelaan11200021611600062252EB1170011Voorschoten11800160626010010016001119001606262000100160017210001T85100082011031686100082011031658126091000406260920008199806221010001W1030008199806221110010S vd Oyeln11200021611600062252EB7210001I85100081998062286100081998062458131091000405180920008199010021010001W1030008199512131110014Waldorpsstraat112000330411600062521CG7210001I85100081995121386100081995121458133091000405180920008199010021010001W1030008199010021110017Van Swietenstraat11200022011600062518EH7210001A851000819901002861000819940930091170210005James0240006Jansen031000820040912032000406260330004603081100040518812000710A052085100082004091286100082004091210069391000209392000820000110393000819900110851000819900110861000819900112";
    }
}
