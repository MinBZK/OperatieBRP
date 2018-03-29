/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal;

import java.util.Collections;
import java.util.List;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.common.vergelijk.Vergelijk;

/**
 * Basis kanaal implementatie.
 */
public abstract class AbstractKanaal implements Kanaal {

    private static final String SKIP_CONTROLEER_PARTIJ_PROPERTY = "skip.controleer.partij";
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
        throw new KanaalException(getKanaal() + " niet ondersteund op uitgaand kanaal.");
    }

    @Override
    public Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {
        throw new KanaalException(getKanaal() + " niet ondersteund op inkomend kanaal.");
    }

    @Override
    public final boolean controleerInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht, final Bericht ontvangenBericht) {
        final boolean result =
                controleerOntvangendePartij(verwachtBericht, ontvangenBericht)
                        && controleerHeaderInformatie(verwachtBericht, ontvangenBericht)
                        && controleerBasisValidatie(verwachtBericht, ontvangenBericht)
                        && controleerInhoud(verwachtBericht, ontvangenBericht);
        LOGGER.info("controleerInkomend() -> {}", result);
        return result;
    }

    private boolean controleerHeaderInformatie(final Bericht verwachtBericht, final Bericht ontvangenBericht) {
        return controleerVerzendendePartij(verwachtBericht, ontvangenBericht)
                && controleerCorrelatie(verwachtBericht, ontvangenBericht)
                && controleerRequestNonReceiptNotification(verwachtBericht, ontvangenBericht);
    }

    private boolean controleerOntvangendePartij(final Bericht verwachtBericht, final Bericht ontvangenBericht) {
        final String skipPartij = verwachtBericht.getTestBericht().getTestBerichtProperty(SKIP_CONTROLEER_PARTIJ_PROPERTY);
        final String skip = verwachtBericht.getTestBericht().getTestBerichtProperty("skip.controleer.ontvangendepartij");

        if (skipPartij != null || skip != null || negeerPartijen()) {
            LOGGER.info("Controle ontvangende partij wordt niet uitgevoerd.");
            return true;
        } else {
            final boolean result = isEqual(verwachtBericht.getOntvangendePartij(), ontvangenBericht.getOntvangendePartij());
            if (!result) {
                LOGGER.info(
                        "Ontvangende partij niet correct; verwacht={}, ontvangen={}",
                        verwachtBericht.getOntvangendePartij(),
                        ontvangenBericht.getOntvangendePartij());
            }
            return result;
        }
    }

    private boolean controleerVerzendendePartij(final Bericht verwachtBericht, final Bericht ontvangenBericht) {
        final String skipPartij = verwachtBericht.getTestBericht().getTestBerichtProperty(SKIP_CONTROLEER_PARTIJ_PROPERTY);
        final String skip = verwachtBericht.getTestBericht().getTestBerichtProperty("skip.controleer.verzendendepartij");

        if (skipPartij != null || skip != null || negeerPartijen()) {
            LOGGER.info("Controle verzendende partij wordt niet uitgevoerd.");
            return true;
        } else {
            final boolean result = isEqual(verwachtBericht.getVerzendendePartij(), ontvangenBericht.getVerzendendePartij());
            if (!result) {
                LOGGER.info(
                        "Verzendende partij niet correct; verwacht={}, ontvangen={}",
                        verwachtBericht.getVerzendendePartij(),
                        ontvangenBericht.getVerzendendePartij());
            }
            return result;
        }
    }

    private boolean controleerCorrelatie(final Bericht verwachtBericht, final Bericht ontvangenBericht) {
        final String skip = verwachtBericht.getTestBericht().getTestBerichtProperty("skip.controleer.correlatie");

        if (skip != null || negeerCorrelatie()) {
            LOGGER.info("Controle correlatie wordt niet uitgevoerd.");
            return true;
        } else {
            final boolean result = isEqual(verwachtBericht.getCorrelatieReferentie(), ontvangenBericht.getCorrelatieReferentie());
            if (!result) {
                LOGGER.info(
                        "Correlatie niet correct; verwacht={}, ontvangen={}",
                        verwachtBericht.getCorrelatieReferentie(),
                        ontvangenBericht.getCorrelatieReferentie());
            }
            return result;
        }
    }

    private boolean controleerRequestNonReceiptNotification(final Bericht verwachtBericht, final Bericht ontvangenBericht) {
        final String skip = verwachtBericht.getTestBericht().getTestBerichtProperty("skip.controleer.requestnonreceiptnotification");

        if (skip != null || negeerRequestNonReceiptNotification()) {
            LOGGER.info("Controle request non receipt notification wordt niet uitgevoerd.");
            return true;
        } else {
            final boolean result =
                    isEqual(
                            Boolean.TRUE.equals(verwachtBericht.getRequestNonReceiptNotification()),
                            Boolean.TRUE.equals(ontvangenBericht.getRequestNonReceiptNotification()));
            if (!result) {
                LOGGER.info(
                        "request non receipt notification niet correct; verwacht={}, ontvangen={}",
                        Boolean.TRUE.equals(verwachtBericht.getRequestNonReceiptNotification()),
                        Boolean.TRUE.equals(ontvangenBericht.getRequestNonReceiptNotification()));
            }
            return result;
        }
    }

    private boolean controleerBasisValidatie(final Bericht verwachtBericht, final Bericht ontvangenBericht) {
        final String skip = verwachtBericht.getTestBericht().getTestBerichtProperty("skip.controleer.basisvalidatie");

        if (skip != null) {
            LOGGER.info("Basis validatie wordt niet uitgevoerd.");
            return true;
        } else {
            final boolean result = basisValidatie(ontvangenBericht.getInhoud());
            if (!result) {
                LOGGER.info("Basis validatie niet geslaagd.");
            }
            return result;
        }
    }

    /**
     * Hook voor basis validatie (zoals controle obv XSD, oid).
     * @param inhoud te controleren ontvangen bericht inhoud
     * @return true, als basis validatie is geslaagd
     */
    protected boolean basisValidatie(final String inhoud) {
        return true;
    }

    private boolean controleerInhoud(final Bericht verwachtBericht, final Bericht ontvangenBericht) {
        final String skip = verwachtBericht.getTestBericht().getTestBerichtProperty("skip.controleer.inhoud");
        if (skip != null) {
            LOGGER.info("Controle inhoud wordt niet uitgevoerd.");
            return true;
        } else {
            final boolean result = vergelijkInhoud(verwachtBericht.getInhoud(), ontvangenBericht.getInhoud());
            if (!result) {
                LOGGER.info("Inhoud niet correct");
            }
            return result;
        }
    }

    /**
     * Hook om te bepalen of verzendende en ontvangende partij genegeerd moeten worden.
     * @return true, als de velden genegeerd moeten worden
     */
    protected boolean negeerPartijen() {
        return true;
    }

    /**
     * Hook om te bepalen of correlatie genegeerd moet worden.
     * @return true, als de velden genegeerd moeten worden
     */
    protected boolean negeerCorrelatie() {
        return true;
    }

    /**
     * Hook om te bepalen of request non receipt notification genegeerd moet worden.
     * @return true, als request non receipt notification genegeerd moeten worden
     */
    protected boolean negeerRequestNonReceiptNotification() {
        return true;
    }

    /**
     * Vergelijk de inhoud van berichten {@link Vergelijk#vergelijk(String, String)}.
     * @param verwachteInhoud verwachte inhoud
     * @param ontvangenInhoud ontvangen inhoud
     * @return true, als de ontvangen inhoud gelijk is aan de verwachte inhoud
     */
    protected boolean vergelijkInhoud(final String verwachteInhoud, final String ontvangenInhoud) {
        return Vergelijk.vergelijk(verwachteInhoud, ontvangenInhoud);
    }

    /**
     * Null-safe equals vergelijking (null is gelijk aan null).
     * @param actueel actuele waarde
     * @param verwacht verwachte waarde
     * @return true, als de waarden gelijk zijn (null is gelijk aan null), anders false
     */
    protected static boolean isEqual(final Object actueel, final Object verwacht) {
        return verwacht == null ? actueel == null : verwacht.equals(actueel);
    }

    @Override
    public void voorTestcase(final TestCasusContext testCasus) {
        // Hook
    }

    @Override
    public List<Bericht> naTestcase(final TestCasusContext testcasus) {
        return Collections.emptyList();
    }

    @Override
    public String getStandaardVerzendendePartij() {
        return null;
    }

    @Override
    public String getStandaardOntvangendePartij() {
        return null;
    }
}
