/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.berichten;

import java.util.List;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.BerichtMetaData;

/**
 * Berichten interface voor JBPM processen.
 */
public interface BerichtenDao {

    /**
     * Lees een bericht.
     *
     * @param id
     *            id
     * @return bericht
     */
    Bericht leesBericht(Long id);

    /**
     * Sla een bericht op in de database. Dit bericht krijgt een nieuw message id (op basis van het database id).
     *
     * @param bericht
     *            bericht
     * @return id
     */
    Long bewaarBericht(Bericht bericht);

    /**
     * Haalt de meta data van een bericht op.
     *
     * @param id
     *            Het id van het bericht waarvoor de meta data wordt opgehaald.
     * @return De meta data van het bericht.
     */
    BerichtMetaData leesBerichtMetaData(Long id);

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** ESB ************************************************************************************ */
    /* ************************************************************************************************************* */

    /**
     * Bewaar een bericht.
     *
     * @param kanaal
     *            kanaal
     * @param direction
     *            richting
     * @param messageId
     *            message id
     * @param correlatieId
     *            correlatie id
     * @param bericht
     *            bericht inhoud
     * @param originator
     *            originator
     * @param recipient
     *            recipient
     * @param msSequenceNumber
     *            mssequencenumber
     * @return bericht id
     */
    Long bewaar(
        final String kanaal,
        final Direction direction,
        final String messageId,
        final String correlatieId,
        final String bericht,
        final String originator,
        final String recipient,
        final Long msSequenceNumber);

    /* ************************************************************************************************************* */
    /* *** Inkomend ************************************************************************************************ */
    /* ************************************************************************************************************* */

    /**
     * Associeer (update) een bericht naam aan een bericht (gebruikt bij inkomend).
     *
     * @param berichtId
     *            bericht id
     * @param naam
     *            bericht naam
     */
    void updateNaam(final Long berichtId, final String naam);

    /**
     * Associeer (update) een proces instantie aan een bericht (gebruikt bij inkomend).
     *
     * @param berichtId
     *            bericht id
     * @param processInstanceId
     *            proces instantie id
     */
    void updateProcessInstance(final Long berichtId, final Long processInstanceId);

    /**
     * Leg vast welke actie er wordt uitgevoerd op dit bericht (gebruikt bij inkomend).
     *
     * @param berichtId
     *            bericht id
     * @param actie
     *            actie
     */
    void updateActie(Long berichtId, String actie);

    /* ************************************************************************************************************* */
    /* *** Inkomend (herhaling) ************************************************************************************ */
    /* ************************************************************************************************************* */

    /**
     * Zoek het vraag bericht waar de gegeven correlatie gegevens op zouden kunnen slaan.
     *
     * @param correlatieId
     *            correlatie id (van het antwoord bericht)
     * @param kanaal
     *            kanaal (van het antwoord bericht)
     * @param originator
     *            originator (van het antwoord bericht)
     * @param recipient
     *            recipient (van het antwoord bericht)
     * @return vraag bericht
     */
    nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht zoekVraagBericht(String correlatieId, String kanaal, String originator, String recipient);

    /**
     * Zoek berichten op MsSequenceNumber (negeer een gegeven id).
     *
     * @param msSequenceNumber
     *            MsSequenceNumber
     * @param berichtId
     *            te negeren bericht
     * @return lijst van gebonven berichten (eventueel lege lijst)
     */
    List<nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht> zoekOpMsSequenceNumberBehalveId(Long msSequenceNumber, Long berichtId);

    /**
     * Tel berichten obv messag id, originator, recipient, kanaal, richting (negeer een gegeven id).
     *
     * @param messageId
     *            message id
     * @param originator
     *            originator
     * @param recipient
     *            recipient
     * @param kanaal
     *            kanaal
     * @param direction
     *            richting
     * @param berichtId
     *            te negeren bericht
     * @return aantal berichten
     */
    int telBerichtenBehalveId(
        final String messageId,
        final String originator,
        final String recipient,
        final String kanaal,
        final Direction direction,
        final Long berichtId);

    /**
     * Zoek het meeste recent verstuurde antwoord op een vraag bericht.
     *
     * @param messageId
     *            message id (van het vraag bericht)
     * @param originator
     *            originator (van het vraag bericht)
     * @param recipient
     *            recipient (van het vraag bericht)
     * @param kanaal
     *            kanaal (van het vraag bericht)
     * @return het meest recent verstuurde antwoord, of null
     */
    nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht zoekMeestRecenteAntwoord(String messageId, String originator, String recipient, String kanaal);

    /* ************************************************************************************************************* */
    /* *** Levering ************************************************************************************************ */
    /* ************************************************************************************************************* */

    /**
     * Update het message id bij een bericht (alleen gebruikt bij levering).
     *
     * @param berichtId
     *            bericht id
     * @param messageId
     *            message id
     */
    void updateMessageId(final Long berichtId, final String messageId);

    /**
     * Update het correlatie id bij een bericht (alleen gebruikt bij levering).
     *
     * @param berichtId
     *            bericht id
     * @param correlatieId
     *            correlatie id
     */
    void updateCorrelatieId(final Long berichtId, final String correlatieId);

    /* ************************************************************************************************************* */
    /* *** Virtueel proces ***************************************************************************************** */
    /* ************************************************************************************************************* */

    /**
     * Associeer (update) een virtuele proces instantie aan een bericht (gebruikt bij verwerken levering).
     *
     * @param berichtId
     *            bericht id
     * @param virtueelProcesId
     *            virtueel proces id
     */
    void updateVirtueelProcesId(final Long berichtId, final Long virtueelProcesId);

    /**
     * Omzetten van koppelingen naar een virtueel proces naar koppelingen naar een echt proces (gebruikt bij inkomend).
     *
     * @param virtueelProcesId
     *            virtueel proces id
     * @param processInstanceId
     *            jpbm proces instance id
     */
    void omzettenVirtueelProcesNaarActueelProces(final long virtueelProcesId, final long processInstanceId);

    /* ************************************************************************************************************* */
    /* *** Uitgaand ************************************************************************************************ */
    /* ************************************************************************************************************* */

    /**
     * Geef een specifiek bericht terug (gebruikt bij uitgaand).
     *
     * @param berichtId
     *            bericht id
     * @return bericht inhoud
     */
    nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht getBericht(final Long berichtId);

    /**
     * Zet het kanaal en de richting van een bericht (gebruikt bij uitgaand).
     *
     * @param berichtId
     *            bericht id
     * @param kanaal
     *            kanaal
     * @param direction
     *            richting
     */
    void updateKanaalEnRichting(final Long berichtId, final String kanaal, final Direction direction);

    /* ************************************************************************************************************* */
    /* *** DTO ***************************************************************************************************** */
    /* ************************************************************************************************************* */

    /**
     * De richting van een bericht (vanuit het zichtpunt van ISC).
     */
    enum Direction {
        /** Inkomend. */
        INKOMEND('I'),
        /** Uitgaand. */
        UITGAAND('U');

        private final Character code;

        /**
         * Constructor.
         *
         * @param code
         *            code
         */
        Direction(final Character code) {
            this.code = code;
        }

        /**
         * Geef de waarde van code.
         *
         * @return code
         */
        public Character getCode() {
            return code;
        }

        /**
         * Zoek de richting obv de code.
         *
         * @param code
         *            code
         * @return richting, of null als niet gevonden
         */
        public static Direction valueOfCode(final Character code) {
            for (final Direction direction : values()) {
                if (code.equals(direction.getCode())) {
                    return direction;
                }
            }

            return null;
        }
    }
}
