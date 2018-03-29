/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.jms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.management.MBeanServerConnection;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.test.common.util.SelectorBuilder;
import nl.bzk.migratiebrp.test.isc.environment.correlatie.Correlator;
import nl.bzk.migratiebrp.test.isc.environment.id.IdGenerator;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;

/**
 * ISC Queuer kanaal.
 */
public abstract class AbstractIscQueueKanaal extends AbstractKanaal {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private Correlator correlator;
    @Inject
    private MBeanServerConnection routeringServerConnection;
    @Inject
    private IscQueuer iscQueuer;
    @Inject
    private IdGenerator idGenerator;
    private boolean registreerUitgaandeCorrelatie = true;
    private boolean registreerInkomendeCorrelatie = true;

    private final List<Bericht> berichten = new ArrayList<>();

    /**
     * Zet de uitgaande correlatie.
     * @param registreerUitgaandeCorrelatie de te zetten uitgaande correlatie
     */
    public final void setRegistreerUitgaandeCorrelatie(final boolean registreerUitgaandeCorrelatie) {
        this.registreerUitgaandeCorrelatie = registreerUitgaandeCorrelatie;
    }

    /**
     * Zet de inkomende correlatie.
     * @param registreerInkomendeCorrelatie de te zetten inkomende correlatie
     */
    public final void setRegistreerInkomendeCorrelatie(final boolean registreerInkomendeCorrelatie) {
        this.registreerInkomendeCorrelatie = registreerInkomendeCorrelatie;
    }

    /**
     * Geef de waarde van uitgaand destination.
     * @return uitgaand destination
     */
    public abstract Destination getUitgaandDestination();

    /**
     * Geef de waarde van inkomend destination.
     * @return inkomend destination
     */
    public abstract Destination getInkomendDestination();

    @Override
    public final void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
        // Controleer correlatie
        if (bericht.getCorrelatieReferentie() != null) {
            correlator.controleerInkomend(bericht.getCorrelatieReferentie(), getKanaal());

            // Hack?
            bericht.setCorrelatieReferentie(correlator.getBerichtReferentie(bericht.getCorrelatieReferentie()));
        }

        // Genereer ID
        final String berichtReferentie;
        if (correlator.getBerichtReferentie(bericht.getBerichtReferentie()) != null) {
            berichtReferentie = correlator.getBerichtReferentie(bericht.getBerichtReferentie());
        } else {
            berichtReferentie = idGenerator.generateId();
        }

        if (registreerUitgaandeCorrelatie) {
            correlator.registreerUitgaand(bericht.getBerichtReferentie(), getKanaal(), berichtReferentie);
        }

        // Hack(?)
        bericht.setBerichtReferentie(berichtReferentie);

        final Bericht queueBericht = new Bericht();
        queueBericht.setBerichtReferentie(berichtReferentie);
        queueBericht.setMsSequenceNumber(bericht.getMsSequenceNumber());
        queueBericht.setCorrelatieReferentie(bericht.getCorrelatieReferentie());
        queueBericht.setInhoud(bericht.getInhoud());
        queueBericht.setVerzendendePartij(bericht.getVerzendendePartij());
        queueBericht.setOntvangendePartij(bericht.getOntvangendePartij());
        queueBericht.setRequestNonReceiptNotification(bericht.getRequestNonReceiptNotification());

        postProcessQueueBericht(queueBericht);

        iscQueuer.sendMessage(getUitgaandDestination(), queueBericht);
    }

    /**
     * Post process hook na het ontvangen van een bericht van de queue.
     * @param queueBericht het ontvangen bericht
     */
    protected void postProcessQueueBericht(final Bericht queueBericht) {
        // Hook
    }

    @Override
    public final Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {
        // Controleer correlatie
        if (verwachtBericht.getCorrelatieReferentie() != null) {
            correlator.controleerUitgaand(verwachtBericht.getCorrelatieReferentie(), getKanaal());

            // Hack(?)
            verwachtBericht.setCorrelatieReferentie(correlator.getBerichtReferentie(verwachtBericht.getCorrelatieReferentie()));
        }

        // Opbouwen specifieke selector
        final SelectorBuilder selector = new SelectorBuilder();
        if (verwachtBericht.getCorrelatieReferentie() != null) {
            selector.addEqualsCriteria(JMSConstants.CORRELATIE_REFERENTIE, verwachtBericht.getCorrelatieReferentie());
        }

        Bericht ontvangenBericht = zoekBericht(testCasus, berichten, verwachtBericht);
        while (ontvangenBericht == null) {
            final Bericht berichtVanQueue = iscQueuer.ontvangBericht(getInkomendDestination(), selector.toString());
            if (berichtVanQueue == null) {
                break;
            }

            berichten.add(berichtVanQueue);
            ontvangenBericht = zoekBericht(testCasus, berichten, verwachtBericht);
        }

        if (ontvangenBericht != null) {
            if (registreerInkomendeCorrelatie) {
                // Registreer bericht referentie
                correlator.registreerInkomend(verwachtBericht.getBerichtReferentie(), getKanaal(), ontvangenBericht.getBerichtReferentie());
            }
        } else {
            if (verwachtBericht.getInhoud() != null && !"".equals(verwachtBericht.getInhoud())) {
                throw new KanaalException("Bericht verwacht, maar niet ontvangen.");
            }
        }

        LOG.info("verwerkInkomend: ontvangenBericht={}", ontvangenBericht);
        return ontvangenBericht;
    }

    private Bericht zoekBericht(final TestCasusContext testCasus, final List<Bericht> ontvangenBerichten, final Bericht verwachtBericht) {
        final Iterator<Bericht> berichtenIterator = ontvangenBerichten.iterator();

        while (berichtenIterator.hasNext()) {
            final Bericht ontvangenBericht = berichtenIterator.next();
            if (controleerInkomend(testCasus, verwachtBericht, ontvangenBericht)) {
                berichtenIterator.remove();
                return ontvangenBericht;
            }
        }

        return null;
    }

    /**
     * Correlatie referenties ook vergelijken bij inkomend bericht.
     * @return false
     */
    @Override
    protected final boolean negeerCorrelatie() {
        return false;
    }

    @Override
    public final void voorTestcase(final TestCasusContext testCasus) {
        LOG.info("Schonen queue voor test case voor kanaal {}", getKanaal());
        naTestcase(testCasus);
    }

    @Override
    public final List<Bericht> naTestcase(final TestCasusContext testCasus) {
        final List<Bericht> result = new ArrayList<>();
        result.addAll(berichten);
        berichten.clear();
        result.addAll(iscQueuer.cleanUp(getUitgaandDestination(), routeringServerConnection));
        result.addAll(iscQueuer.cleanUp(getInkomendDestination(), routeringServerConnection));
        if ("false".equalsIgnoreCase(testCasus.getTestcaseConfiguratie().getProperty(getKanaal() + ".na.controle"))) {
            LOG.info("Controle op bericht(en) na testcase is uitgezet. " + result.size() + " bericht(en) worden genegeerd.");
            result.clear();
        }

        return result;
    }
}
