/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service;

import java.util.Collection;
import java.util.Map;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;

/**
 * StamTabelService. Service voor stam tabel interactie.
 */
public interface StamTabelService {

    /**
     * Geef een collectie van alle elementen.
     *
     * @return collectie van alle elementen
     */
    Collection<Element> geefAlleElementen();

    /**
     * Geef alle expressie parser resultaten van attribuut soort elementen.
     *
     * @return map met expressie parser resultaten.
     */
    Map<String, ParserResultaat> geefAlleExpressieParserResultatenVanAttribuutElementenPerExpressie();

    /**
     * Geen een Element terug uit de lijst van alle elementen op basis van element ID.
     * @param id het element ID.
     * @return het Element.
     */
    Element geefElementById(int id);
}
