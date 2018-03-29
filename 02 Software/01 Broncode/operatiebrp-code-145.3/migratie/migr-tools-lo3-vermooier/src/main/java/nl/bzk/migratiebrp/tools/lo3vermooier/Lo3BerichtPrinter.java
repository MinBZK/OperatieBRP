/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.lo3vermooier;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

public class Lo3BerichtPrinter {

    private final String lo3;

    public Lo3BerichtPrinter(final String lo3) {
        this.lo3 = lo3;
    }

    public void print() throws BerichtSyntaxException {
        final Lo3BerichtFactory berichtFactory = new Lo3BerichtFactory();
        final Lo3Bericht bericht = berichtFactory.getBericht(lo3);
        final Lo3Header header = bericht.getHeader();
        final String[] headers = header.parseHeaders(lo3);

        printHeader(System.out, header, headers);

        final String inhoud = lo3 == null ? null : lo3.substring(getTotalHeaderSize(headers));

        printInhoud(System.out, inhoud);
    }

    /**
     * Geef de waarde van total headers.
     * @param lo3Header bevat de informatie over welke headers in het bericht zitten.
     * @param lo3 het bericht als string waaruit de headers worden geparsed.
     * @return total header size
     * @throws BerichtSyntaxException In het geval dat de headers niet geparsed kunnen worden
     */
    private int getTotalHeaderSize(final String[] headers) throws BerichtSyntaxException {
        int totalHeaderSize = 0;
        for (final String header : headers) {
            totalHeaderSize += header.length();
        }
        return totalHeaderSize;
    }

    /**
     * Pretty print LO3 header.
     * @param out output
     * @param lo3Header lo3 header
     * @param lo3 Het LO3 bericht als string
     * @throws BerichtSyntaxException In het geval dat de headers niet correct geparsed kunnen worden.
     */
    void printHeader(final PrintStream out, final Lo3Header header, final String[] headers) throws BerichtSyntaxException {
        for (int i = 0; i < headers.length; i++) {
            final Lo3HeaderVeld headerVeld = header.getHeaderVelden()[i];
            if (Lo3HeaderVeld.LengteType.VARIABEL_GEDEFINIEERDE_LENGTE == headerVeld.getLengteType()) {
                for (int part = 0; part < headers[i].length(); part = part + headerVeld.getElementLengte()) {
                    out.format(
                            "%03d %s %s%n",
                            headerVeld.getElementLengte(),
                            headerVeld.getOmschrijving(),
                            headers[i].substring(part, part + headerVeld.getElementLengte()));
                }
            } else {
                out.format("%03d %s %s%n", headerVeld.getElementLengte(), headerVeld.getOmschrijving(), headers[i]);
            }
        }
        out.println();
    }

    /**
     * Pretty print LO3 inhoud.
     * @param sb output
     * @param lo3Inhoud lo3 inhoud
     * @throws BerichtSyntaxException In het geval het parsen van de inhoud mislukt.
     */
    void printInhoud(final PrintStream out, final String lo3Inhoud) throws BerichtSyntaxException {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(lo3Inhoud);

        for (final Lo3CategorieWaarde categorie : categorieen) {
            final SortedMap<Lo3ElementEnum, String> elementen = new TreeMap<>(categorie.getElementen());

            for (final Map.Entry<Lo3ElementEnum, String> element : elementen.entrySet()) {
                out.format(
                        "%03d %02d.%s %s%n",
                        element.getValue().length(),
                        categorie.getCategorie().getCategorieAsInt(),
                        element.getKey().getElementNummer(true),
                        element.getValue());
            }
            out.println();
        }
    }

}
