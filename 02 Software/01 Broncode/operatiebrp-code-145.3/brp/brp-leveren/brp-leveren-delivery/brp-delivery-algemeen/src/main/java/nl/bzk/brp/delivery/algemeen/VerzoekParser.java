/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.algemeen;

import java.text.ParseException;
import javax.xml.xpath.XPath;
import nl.bzk.brp.service.algemeen.request.Verzoek;
import org.w3c.dom.Node;

/**
 * De interface voor BRP Levering verzoekparsing.
 * @param <T> type verzoek
 */
public interface VerzoekParser<T extends Verzoek> {

    /**
     * @return de dienstspecifieke prefix
     */
    String getPrefix();

    /**
     * Abstracte methode voor het vullen van de dienst specifieke gegevens.
     * @param verzoek het verzoek
     * @param node de basis node
     * @param xPath een XPath instantie
     */
    void vulDienstSpecifiekeGegevens(T verzoek, Node node, XPath xPath);

    /**
     * Abstracte methode voor het vullen van de parameters.
     * @param verzoek het verzoek
     * @param node de basis node
     * @param xPath een XPath instantie
     */
    void vulParameters(T verzoek, Node node, XPath xPath);

    /**
     * Methode voor het vullen van de stuurgegevens.
     * @param verzoek het verzoek
     * @param node de basis node
     * @param xPath een XPath instantie
     * @throws ParseException bij een fout bij het parsen van het tijdstip van verzending
     */
    void vulStuurgegevens(T verzoek, Node node, XPath xPath) throws ParseException;
}
