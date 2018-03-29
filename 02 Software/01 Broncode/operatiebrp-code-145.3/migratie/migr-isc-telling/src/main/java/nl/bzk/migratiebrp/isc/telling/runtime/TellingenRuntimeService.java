/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.runtime;

import java.sql.Timestamp;

/**
 * Service welke de verwerking met ISC berichten afhandeld.
 */
public interface TellingenRuntimeService {

    /**
     * Werkt de lopende tellingen bij.
     * @param datumTot De datum tot wanneer de tellingen bijgewerkt dienen te worden.
     */
    void werkLopendeTellingenBij(Timestamp datumTot);

    /**
     * Verwerkt berichten in de tellingen.
     * @param datumTot De datum tot wanneer de berichten worden meegenomen in de telling.
     * @return True indien het bijwerken is gelukt, false in andere gevallen.
     */
    boolean werkLopendeTellingenBerichtenBij(Timestamp datumTot);

    /**
     * Verwerkt beëindigde proces instantie in de tellingen.
     * @param datumTot De datum tot wanneer de processen worden meegenomen in de telling. *
     * @return True indien het bijwerken is gelukt, false in andere gevallen.
     */
    boolean werkLopendeTellingenProcessenBij(Timestamp datumTot);

    /**
     * Verwerkt nieuwe berichten in de tellingen.
     * @return True indien het bijwerken is gelukt, false in andere gevallen.
     */
    boolean werkAantallenLopendeTellingenBerichtenBij();

    /**
     * Verwerkt nieuwe berichten in de tellingen.
     * @return True indien het bijwerken is gelukt, false in andere gevallen.
     */
    boolean werkAantallenLopendeTellingenGestarteProcessenBij();

    /**
     * Verwerkt nieuwe berichten in de tellingen.
     * @return True indien het bijwerken is gelukt, false in andere gevallen.
     */
    boolean werkAantallenLopendeTellingenBeeindigdeProcessenBij();

}
