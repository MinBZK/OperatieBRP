/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ib01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class MaakIv01ActionTest {

    private static final String LO3_PL_STRING =
            "00697011640110010817238743501200092995889450210004Mart0240005Vries03100081990010103200040599033000460300410001M6110001E8110004059981200071 A9102851000819900101861000819900102021720110010192829389501200099911223340210006Jannie0240004Smit03100081969010103200041901033000460300410001M6210008199001018110004059981200071 A9102851000819900101861000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries03100081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285100081990010186100081990010207055681000819900101701000108010001180200170000000000000000008106091000405990920008199001011010001W102000405991030008199001011110001.7210001G851000819900101861000819900102";

    private static Lo3Persoonslijst maakLo3Persoonslijst() throws Exception {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(LO3_PL_STRING);
        final Lo3Persoonslijst result = new Lo3PersoonslijstParser().parse(categorieen);
        return result;
    }

    @Test
    public void test() throws Exception {
        final Ib01Bericht ib01Bericht = new Ib01Bericht();
        ib01Bericht.setLo3Persoonslijst(maakLo3Persoonslijst());
        // final String ib01String = IOUtils.toString(VerwerkIb01TaskTest.class.getResourceAsStream("ib01.txt"));
        // ib01Bericht.parse(ib01String);

        final BrpBerichtFactory berichtFactory = BrpBerichtFactory.SINGLETON;
        final VerhuizingVerzoekBericht verhuisBericht =
                (VerhuizingVerzoekBericht) berichtFactory.getBericht(IOUtils.toString(MaakIv01ActionTest.class
                        .getResourceAsStream("uc302.xml")));

        verhuisBericht.setLo3Gemeente(verhuisBericht.getLo3Gemeente());
        verhuisBericht.setBrpGemeente(verhuisBericht.getBrpGemeente());

        final Map<String, Object> input = new HashMap<String, Object>();
        input.put("input", verhuisBericht); // verhuisbericht
        input.put("ib01Bericht", ib01Bericht); // ib01Bericht

        final MaakIv01Action task = new MaakIv01Action();

        final Map<String, Object> output = task.execute(input);

        Assert.assertEquals(1, output.size());
        Assert.assertTrue(output.containsKey("iv01Bericht"));

        // final Iv01Bericht iv01Bericht = (Iv01Bericht) output.get("iv01Bericht");
        //
        // final String kop = "00000000" + "Iv01" + "0";
        // // cat01
        // final String cat01nr = "01";
        // final String anr = "0110" + "010" + "8172387435";
        // final String bsn = "0120" + "009" + "299588945";
        // final String voornaam = "0210" + "004" + "Mart";
        // final String geslachtsnaam = "0240" + "005" + "Vries";
        // final String geboorteDatum = "0310" + "008" + "19900101";
        // final String geboortePlaats = "0320" + "004" + "0599";
        // final String geboorteLand = "0330" + "004" + "6030";
        // final String cat01Lengte =
        // parseInteger(
        // anr.length() + bsn.length() + voornaam.length() + geslachtsnaam.length()
        // + geboorteDatum.length() + geboortePlaats.length() + geboorteLand.length(), 3);
        // final String categorie01 =
        // cat01nr + cat01Lengte + anr + bsn + voornaam + geslachtsnaam + geboorteDatum + geboortePlaats
        // + geboorteLand;
        // // cat08
        // final String gemeenteInschrijving = "0910" + "004" + "0599";
        // final String datumInschrijving = "0920" + "008" + "20120102";
        // final String straatnaam = "1110" + "001" + ".";
        // final String cat08Lengte =
        // parseInteger(gemeenteInschrijving.length() + datumInschrijving.length() + straatnaam.length(), 3);
        // final String categorie08 = "08" + cat08Lengte + gemeenteInschrijving + datumInschrijving + straatnaam;
        //
        // // TODO: andere categorieen/groepen moeten nog worden toegevoegd
        // final int berichtLengte = categorie01.length() + categorie08.length();
        // final String bl = parseInteger(berichtLengte, 5); //
        //
        // final String verwacht = kop + bl + categorie01 + categorie08;
        // // TODO: nog niet alle velden worden goed gevuld, zie overige TODO's in MaakIv01Action.java
        // // Assert.assertEquals(verwacht, iv01Bericht.format());
        //
        // // TODO brp notificatie testen

    }
    //
    // private String parseInteger(final int berichtLengte, final int benodigdeLengte) {
    // String waarde = "" + berichtLengte;
    // while (waarde.length() < benodigdeLengte) {
    // waarde = "0" + waarde;
    // }
    // return waarde;
    // }
}
