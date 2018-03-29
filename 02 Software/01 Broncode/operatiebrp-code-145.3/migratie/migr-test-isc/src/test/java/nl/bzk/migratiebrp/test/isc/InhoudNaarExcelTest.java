/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc;

import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class InhoudNaarExcelTest {

    @Test
    public void generate103() {
        int year = 2016;
        int month = 12;
        int day = 31;

        for (int i = 0; i < 1200; i++) {
            StringBuilder line = new StringBuilder();
            line.append("<afnemersindicatie>");
            line.append("<stapelNummer>0</stapelNummer>");
            line.append("<volgNummer>").append(i).append("</volgNummer>");
            if (i % 2 == 0) {
                line.append("<afnemerCode>900050</afnemerCode>");
            }

            day = day - 3;
            if (day < 1) {
                day = day + 28;
                month = month - 1;
            }
            if (month < 1) {
                month = month + 12;
                year = year - 1;
            }

            String datum = String.format("%04d%02d%02d", year, month, day);

            line.append("<geldigheidStartDatum>").append(datum).append("</geldigheidStartDatum>");
            line.append("</afnemersindicatie>");
            System.out.println(line.toString());
        }
    }

    @Test
    public void inhoudNaarExcel() throws BerichtSyntaxException {
        outputInhoud("00071010460110010123456789001200091234567890240006Jansen08015092000820160101");
    }


    @Test
    public void berichtNaarExcel() throws BerichtSyntaxException {
        outputBericht("00000000Xa0100701011650110010817238743501200092995889450210004Mart0240006Jansen03100081990010103200040599033000460300410001M6110001E811"
                + "0004059981200071 A91028510008199001018610008199001020217201100101928293895012000999"
                + "11223340210006Jannie0240004Smit031000819690101032"
                + "00041901033000460300410001M6210008199001018110004059981200071 A91028510008199001018"
                + "61000819900102031750110010172625463201200093827261340210008Mitchell0240005Vries0310"
                + "0081970010103200041900033000460300410001M6210008199001018110004059981200071 A910285"
                + "10008199001018610008199001020705568100081990010170100010801000400018020017000000000000"
                + "0000008106091000405990920008199001011010001W102000405991030008199001011110001.72100"
                + "01G851000819900101861000819900102");
        //outputBericht
        // ("00000000Ha01A0000000000126010130240006Heuvel1200836100011120903510002PD3520009NA20101113530008199001103540005B19013550008200001103560008200501103570001I");
    }

    private void outputBericht(String lo3Bericht) throws BerichtSyntaxException {
        final Lo3BerichtFactory berichtFactory = new Lo3BerichtFactory();
        final Lo3Bericht bericht = berichtFactory.getBericht(lo3Bericht);
        final Lo3Header header = bericht.getHeader();
        final String[] headers = header.parseHeaders(lo3Bericht);
        outputHeaders(header, headers);

        final String lo3Inhoud = lo3Bericht.substring(getTotalHeaderSize(headers));
        outputInhoud(lo3Inhoud);
    }

    private void outputHeaders(final Lo3Header header, final String[] headers) throws BerichtSyntaxException {
        for (int i = 0; i < headers.length; i++) {
            final Lo3HeaderVeld headerVeld = header.getHeaderVelden()[i];
            System.out.format("%s,,%s%n", headerVeld.getOmschrijving(), headers[i]);
        }
    }

    private int getTotalHeaderSize(final String[] headers) throws BerichtSyntaxException {
        int totalHeaderSize = 0;
        for (final String header : headers) {
            totalHeaderSize += header.length();
        }
        return totalHeaderSize;
    }

    private void outputInhoud(final String lo3Inhoud) throws BerichtSyntaxException {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(lo3Inhoud);

        for (final Lo3CategorieWaarde categorie : categorieen) {
            System.out.println(categorie.getCategorie().getLabel() + "," + categorie.getCategorie().getCategorieAsInt());

            for (final Map.Entry<Lo3ElementEnum, String> element : categorie.getElementen().entrySet()) {
                System.out.println(element.getKey().getLabel() + "," + element.getKey().getElementNummer() + "," + element.getValue());
            }
        }
    }
}
