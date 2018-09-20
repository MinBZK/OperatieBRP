/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.util.Collection;
import java.util.Map;

/**
 * Interface voor de entiteiten van BRP.
 */
public interface DeltaEntiteit {

    /**
     * Zet het gegeven in onderzoek voor deze entiteit.
     *
     * @param gegevenInOnderzoek
     *            het gegeven in onderzoek
     */
    void setGegevenInOnderzoek(final GegevenInOnderzoek gegevenInOnderzoek);

    /**
     * @return geeft de elementen die in onderzoek staan terug.
     */
    Collection<Element> getElementenInOnderzoek();

    /**
     * @return een map met per {@link Element} het {@link GegevenInOnderzoek}.
     */
    Map<Element, GegevenInOnderzoek> getGegevenInOnderzoekPerElementMap();

    /**
     * Geef de Id waarde voor de entiteit. Intern is de Id een Long voor integratie met GegevenInOnderzoek. De waarde
     * wordt geconverteerd naar een Integer.
     *
     * @return de Id waarde.
     */
    Number getId();

    /**
     * Verwijdert de koppeling tussen de {@link DeltaEntiteit} en {@link GegevenInOnderzoek}.
     *
     * @param gegevenInOnderzoek
     *            het element waaronder de koppeling is vastgelegd.
     */
    void removeGegevenInOnderzoek(Element gegevenInOnderzoek);
}
