/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Interface voor een Mapper om op algemene wijze BRP Onderzoek entiteiten te vertalen naar onderzoeken op individuele
 * attributen in het migratie model.
 */
public interface BrpOnderzoekMapper {

    /**
     * Bepaal of een attribuut in een onderzoek betrokken is en levert in dat geval een {@link Lo3Onderzoek} op.
     * @param entiteit De (database) entiteit instantie waar het attribuut onderdeel van uitmaakt.
     * @param element De identificatie van het specifieke attribuut.
     * @param elementBehoortBijGroepsOnderzoek Geeft aan of dit attribuut in onderzoek moet staan als de BRP groep in zijn geheel in onderzoek staat.
     * @return Het {@link Lo3Onderzoek} als dit attribuut in onderzoek staat, of anders <code>null</code>.
     */
    Lo3Onderzoek bepaalOnderzoek(Object entiteit, Element element, boolean elementBehoortBijGroepsOnderzoek);

    /**
     * Bepaal of een attribuut in een of meerdere onderzoeken betrokken is en levert in dat geval een set van {@link Lo3Onderzoek} op.
     * @param entiteit De (database) entiteit instantie waar het attribuut onderdeel van uitmaakt.
     * @param element De identificatie van het specifieke attribuut.
     * @param elementBehoortBijGroepsOnderzoek Geeft aan of dit attribuut in onderzoek moet staan als de BRP groep in zijn geheel in onderzoek staat.
     * @return Een set van {@link Lo3Onderzoek}. De set is leeg als er geen onderzoeken zijn.
     */
    Set<Lo3Onderzoek> bepaalOnderzoeken(Object entiteit, Element element, boolean elementBehoortBijGroepsOnderzoek);
}
