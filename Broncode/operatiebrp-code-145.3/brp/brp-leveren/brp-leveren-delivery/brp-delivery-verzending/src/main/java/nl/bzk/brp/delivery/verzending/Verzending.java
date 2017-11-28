/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.internbericht.bijhoudingsnotificatie.BijhoudingsplanNotificatieBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevens;

/**
 * Container interface voor specifieke stappen van verzending.
 */
interface Verzending {


    /**
     * Stap t.b.v. archivering van een verzendingsbericht.
     */
    interface ArchiveerBerichtStap {

        /**
         * Archiveert synchronisatiebericht.
         * @param archiveringOpdracht archiveringOpdracht
         */
        void archiveerSynchronisatieBericht(ArchiveringOpdracht archiveringOpdracht);

        /**
         * Archiveert bijhoudingsnotificatiebericht.
         * @param berichtgegevens bijhoudingsNotificatie berichtgegevens
         */
        void archiveerBijhoudingsNotificatieBericht(BijhoudingsplanNotificatieBericht berichtgegevens);

        /**
         * Archiveert vrij bericht.
         * @param archiveringOpdracht archiveringOpdracht
         */
        void archiveerVrijBericht(ArchiveringOpdracht archiveringOpdracht);
    }

    /**
     * Stap t.b.v. verzending van BRP (levering/notificatie) bericht.
     */
    interface BrpStelselService {

        /**
         * Verzendt het leveringsbericht.
         * @param berichtGegevens JMS bericht t.b.v. verzending
         */
        void verzendSynchronisatieBericht(SynchronisatieBerichtGegevens berichtGegevens);

        /**
         * Verzendt het bijhoudingsnotificatiebericht.         *
         * @param berichtgegevens bijhoudingsnotificatie berichtgegevens
         */
        void verzendBijhoudingsNotificatieBericht(BijhoudingsplanNotificatieBericht berichtgegevens);

        /**
         * Verzendt het vrije bericht.
         * @param berichtGegevens JMS bericht t.b.v. verzending
         */
        void verzendVrijBericht(VrijBerichtGegevens berichtGegevens);
    }

    /**
     * Stap t.b.v. verzending van een Lo3-bericht.
     */
    interface GBAStelselService {

        /**
         * Verzendt het lo3-bericht.
         * @param berichtGegevens jms bericht t.b.v. verzending
         */
        void verzendLo3Bericht(SynchronisatieBerichtGegevens berichtGegevens);

        /**
         * Verzendt het vrije bericht.
         * @param berichtGegevens JMS bericht t.b.v. verzending
         */
        void verzendVrijBericht(VrijBerichtGegevens berichtGegevens);
    }


    /**
     * Webservice-client marker interface voor verzending protocolleer persoon.
     */
    interface VerwerkPersoonWebServiceClient extends VerzendingWebserviceClient {
    }

    /**
     * Webservice-client marker interface voor verzending protocolleer bijhoudingsnotificatie.
     */

    interface VerwerkBijhoudingsNotificatieWebServiceClient extends VerzendingWebserviceClient {
    }

    /**
     * Webservice-client marker interface voor verzending vrijbericht.
     */
    interface VerwerkVrijBerichtWebServiceClient extends VerzendingWebserviceClient {
    }

    /**
     * Interface voor het uitvoeren van de verzend stappen.
     */
    interface VerzendingService {

        /**
         * Verwerk en verzendt bericht.
         * @param berichtGegevens verwerkContext
         */
        void verwerkSynchronisatieBericht(final SynchronisatieBerichtGegevens berichtGegevens);

        /**
         * Verwerk en verzendt bijhoudingsnotificatie bericht.
         * @param berichtgegevens bijhoudingsnotificatie berichtgegevens
         */
        void verwerkBijhoudingsNotificatieBericht(final BijhoudingsplanNotificatieBericht berichtgegevens);

        /**
         * Verwerk en verzendt vrij bericht.
         * @param vrijBerichtGegevens verwerkContext
         */
        void verwerkVrijBericht(final VrijBerichtGegevens vrijBerichtGegevens);
    }

    /**
     * De protocolleringservice.
     */
    @FunctionalInterface
    interface ProtocolleringService {

        /**
         * Verwerk een protocollering.
         * @param verzendingDTO protocollering metadata
         */
        void verwerkProtocollering(final SynchronisatieBerichtGegevens verzendingDTO);
    }

}
