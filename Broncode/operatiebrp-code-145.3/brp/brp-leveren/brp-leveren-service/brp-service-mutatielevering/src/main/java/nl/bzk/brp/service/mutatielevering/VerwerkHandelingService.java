/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering;

import java.util.List;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.brp.domain.internbericht.admhndpublicatie.HandelingVoorPublicatie;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiebericht;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;

/**
 * Interface die gebruikt wordt voor de stappen voor de verwerking van administratieve handelingen.
 */
public interface VerwerkHandelingService {

    /**
     * Verwerkt een administratieve handeling met het opgegeven id. Dit is het startpunt voor de verwerking van een mutatie.
     * @param handelingVoorPublicatie de handeling voor Publicatie
     */
    void verwerkAdministratieveHandeling(HandelingVoorPublicatie handelingVoorPublicatie) throws VerwerkHandelingException;

    /**
     * Markeer handeling als fout.
     * @param handelingVoorPublicatie handelingVoorPublicatie
     */
    void markeerHandelingAlsFout(HandelingVoorPublicatie handelingVoorPublicatie);


    /**
     * Service voor het ophalen van het administratievehandeling object uit de database obv het id.
     */
    @FunctionalInterface
    interface MaakMutatiehandelingService {

        /**
         * Haalt het administratievehandeling object obv het id.
         * @param handelingVoorPublicatie de handeling voor publicatie
         * @return het adminstratieve handeling object
         */
        Mutatiehandeling maakHandeling(HandelingVoorPublicatie handelingVoorPublicatie);
    }

    /**
     * Service interface voor het plaatsen van berichten op de queue.
     */
    @FunctionalInterface
    interface VerstuurAfnemerBerichtService {

        /**
         * Plaatst de berichten op de queue.
         * @param berichten lijst met berichten
         */
        void verstuurBerichten(final List<Mutatiebericht> berichten);
    }

    /**
     * Service interface om afnemerindicaties te plaatsen voor de personen met de relevante autorisaties.
     */
    @FunctionalInterface
    interface AttenderingPlaatsAfnemerindicatieService {

        /**
         * Plaatst afnemerindicaties te plaatsen voor de personen met de relevante autorisaties.
         * @param handeling de administratieveahandeling
         * @param berichten de te versturen berichten.
         * @throws BlobException als de blob niet gemaakt kan worden
         */
        void plaatsAfnemerindicaties(Long handeling, List<Mutatiebericht> berichten) throws BlobException;
    }

    /**
     * Exceptie die gegooid wordt als de administratievehandeling niet gevonden kan worden.
     */
    final class AdministratievehandelingNietGevondenException extends StapException {

        private static final long serialVersionUID = 3579413166467375397L;

        /**
         * Constructor.
         * @param admhdnId id van de administratievehandeling
         */
        AdministratievehandelingNietGevondenException(final long admhdnId) {
            super(String.valueOf(admhdnId));
        }
    }
}
