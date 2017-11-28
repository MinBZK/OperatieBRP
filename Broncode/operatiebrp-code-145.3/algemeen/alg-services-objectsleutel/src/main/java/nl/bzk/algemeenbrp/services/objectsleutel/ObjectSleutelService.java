/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.objectsleutel;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;

/**
 * Interface voor het maskeren en ontmaskeren van een database ID.
 */
@Bedrijfsregel(Regel.R1834)
public interface ObjectSleutelService {

    /**
     * Maakt een objectsleutel op basis van een persoon en versienummer.
     * @param persoonId id van persoon.
     * @param versienummer versienummer van de persoon waar deze objectsleutel voor wordt gegenereerd.
     * @return een objectsleutel.
     */
    ObjectSleutel maakPersoonObjectSleutel(long persoonId, long versienummer);

    /**
     * Maakt een objectsleutel op basis van de gemaskeerde objectsleutel.
     * @param gemaskeerdeObjectSleutel de gemaskeerde objectsleutel
     * @return een objectsleutel
     * @throws OngeldigeObjectSleutelException wanneer de gemaskeerde object sleutel niet kan worden vertaald in het gewenste objectsleutel object
     */
    ObjectSleutel maakPersoonObjectSleutel(String gemaskeerdeObjectSleutel) throws OngeldigeObjectSleutelException;

    /**
     * Maakt een objectsleutel op basis van een relatie id.
     * @param relatieId id van relatie.
     * @return een objectsleutel.
     */
    ObjectSleutel maakRelatieObjectSleutel(long relatieId);

    /**
     * Maakt een objectsleutel op basis van de gemaskeerde objectsleutel.
     * @param gemaskeerdeObjectSleutel de gemaskeerde objectsleutel
     * @return een objectsleutel
     * @throws OngeldigeObjectSleutelException wanneer de gemaskeerde object sleutel niet kan worden vertaald in het gewenste objectsleutel object
     */
    ObjectSleutel maakRelatieObjectSleutel(String gemaskeerdeObjectSleutel) throws OngeldigeObjectSleutelException;

    /**
     * Maakt een objectsleutel op basis van een betrokkenheid id.
     * @param betrokkenheidId id van betrokkenheid
     * @return een objectsleutel
     */
    ObjectSleutel maakBetrokkenheidObjectSleutel(long betrokkenheidId);

    /**
     * Maakt een objectsleutel op basis van de gemaskeerde objectsleutel.
     * @param gemaskeerdeObjectSleutel de gemaskeerde objectsleutel
     * @return een objectsleutel
     * @throws OngeldigeObjectSleutelException wanneer de gemaskeerde object sleutel niet kan worden vertaald in het gewenste objectsleutel object
     */
    ObjectSleutel maakBetrokkenheidObjectSleutel(String gemaskeerdeObjectSleutel) throws OngeldigeObjectSleutelException;

    /**
     * Maakt een objectsleutel op basis van een onderzoek id.
     * @param onderzoekId id van onderzoek
     * @return een objectsleutel
     */
    ObjectSleutel maakOnderzoekObjectSleutel(long onderzoekId);

    /**
     * Maakt een objectsleutel op basis van de gemaskeerde objectsleutel.
     * @param gemaskeerdeObjectSleutel de gemaskeerde objectsleutel
     * @return een objectsleutel
     * @throws OngeldigeObjectSleutelException wanneer de gemaskeerde object sleutel niet kan worden vertaald in het gewenste objectsleutel object
     */
    ObjectSleutel maakOnderzoekObjectSleutel(String gemaskeerdeObjectSleutel) throws OngeldigeObjectSleutelException;
}
