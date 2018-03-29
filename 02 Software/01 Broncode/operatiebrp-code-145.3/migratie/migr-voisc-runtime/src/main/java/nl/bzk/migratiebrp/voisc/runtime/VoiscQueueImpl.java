/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RichtingType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ArchiveringVerzoekBericht;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.util.common.logging.MDCVeld;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscQueueException;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Queue interactie.
 */
@Service
public final class VoiscQueueImpl implements VoiscQueue {
    private static final int INDEX_BEGIN_BERICHTNUMMER = 8;
    private static final int INDEX_EINDE_BERICHTNUMMER = 12;
    private static final int MINIMALE_BERICHT_LENGTE = 11;
    private static final String VOISC_TRANSACTION_MANAGER = "voiscTransactionManager";
    private static final List<String> BEKENDE_BERICHTNUMMERS =
            Arrays.asList(
                    "Af01",
                    "Af11",
                    "Ag01",
                    "Ag11",
                    "Ag21",
                    "Ag31",
                    "Ap01",
                    "Av01",
                    "Gv01",
                    "Gv02",
                    "Ha01",
                    "Hf01",
                    "Hq01",
                    "Ib01",
                    "If01",
                    "If21",
                    "If31",
                    "If41",
                    "Ii01",
                    "Iv01",
                    "Iv11",
                    "Iv21",
                    "La01",
                    "Lf01",
                    "Lg01",
                    "Lq01",
                    "Ng01",
                    "Of11",
                    "Og11",
                    "Pf01",
                    "Pf02",
                    "Pf03",
                    "Qf01",
                    "Qf11",
                    "Qs01",
                    "Qv01",
                    "Sf01",
                    "Sv01",
                    "Sv11",
                    "Tb01",
                    "Tb02",
                    "Tf01",
                    "Tf11",
                    "Tf21",
                    "Tv01",
                    "Vb01",
                    "Wa01",
                    "Wa11",
                    "Wf01",
                    "Xa01",
                    "Xf01",
                    "Xq01");

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private JmsTemplate jmsTemplate;
    private Destination voiscOntvangst;
    private Destination archivering;
    private MailboxRepository mailboxRepo;

    /**
     * Constructor.
     * @param jmsTemplate jmsTemplate
     * @param voiscOntvangst voiscOntvangst queue
     * @param archivering archivering queue
     * @param mailboxRepo mailbox repository
     */
    @Inject
    public VoiscQueueImpl(@Named("voiscJmsTemplate") final JmsTemplate jmsTemplate, @Named("voiscOntvangstQueue") final Destination voiscOntvangst,
                          @Named("archiveringQueue") final Destination archivering, final MailboxRepository mailboxRepo) {
        this.jmsTemplate = jmsTemplate;
        this.voiscOntvangst = voiscOntvangst;
        this.archivering = archivering;
        this.mailboxRepo = mailboxRepo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, value = "voiscTransactionManager")
    public void verstuurBerichtNaarIsc(final Bericht bericht) throws VoiscQueueException {
        LOGGER.info("[Bericht {}]: Bericht versturen naar ISC.", bericht.getId());
        try {
            jmsTemplate.send(voiscOntvangst, session -> {
                final Message message = session.createTextMessage(bericht.getBerichtInhoud());
                MDCProcessor.registreerVerwerkingsCode(message);
                fillMessage(bericht, message);
                return message;
            });
            LOGGER.info("[Bericht {}]: Bericht succesvol verstuurd naar ISC.", bericht.getId());
            MDCProcessor.extra(MDCVeld.VOISC_BERICHT_ID, bericht.getId()).run(() -> LOGGER.info(FunctioneleMelding.VOISC_ISC_VERSTUURD));
        } catch (final JmsException e) {
            LOGGER.error("[Bericht {}]: Onverwachte fout bij versturen van bericht naar ISC.", bericht.getId(), e);
            throw new VoiscQueueException("Onverwachte fout bij het versturen van een bericht naar de ISC", e);
        }
    }

    private void fillMessage(final Bericht bericht, final Message message) throws JMSException {
        if (bericht.getRecipient() != null) {
            final Mailbox recipientMailbox = mailboxRepo.getMailboxByNummer(bericht.getRecipient());
            if (recipientMailbox != null) {
                message.setStringProperty(JMSConstants.BERICHT_RECIPIENT, recipientMailbox.getPartijcode());
            }
        }
        if (bericht.getOriginator() != null) {
            final Mailbox originatorMailbox = mailboxRepo.getMailboxByNummer(bericht.getOriginator());
            if (originatorMailbox != null) {
                message.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, originatorMailbox.getPartijcode());
            }
        }

        if (bericht.getDispatchSequenceNumber() != null) {
            message.setStringProperty(JMSConstants.BERICHT_MS_SEQUENCE_NUMBER, bericht.getDispatchSequenceNumber().toString());
        }

        if (bericht.getMessageId() != null) {
            message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, bericht.getMessageId());
        }
        if (bericht.getCorrelationId() != null) {
            message.setStringProperty(JMSConstants.CORRELATIE_REFERENTIE, bericht.getCorrelationId());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = VOISC_TRANSACTION_MANAGER)
    public void archiveerBericht(final Bericht bericht, final RichtingType richting) throws VoiscQueueException {
        LOGGER.info("[Bericht {}]: Bericht versturen naar archivering.", bericht.getId());
        try {
            jmsTemplate.send(archivering, session -> {
                final ArchiveringVerzoekBericht verzoekBericht = maakArchiveringVerzoek(bericht, richting);
                final String verzoek = verzoekBericht.format();
                final Message message = session.createTextMessage(verzoek);
                MDCProcessor.registreerVerwerkingsCode(message);
                return message;
            });
            LOGGER.info("[Bericht {}]: Bericht succesvol verstuurd naar archivering.", bericht.getId());
        } catch (final JmsException e) {
            LOGGER.error("[Bericht {}]: Onverwachte fout bij versturen van bericht naar archivering.", bericht.getId(), e);
            throw new VoiscQueueException("Onverwachte fout bij het versturen van een bericht naar de archivering", e);
        }
    }

    /**
     * Maak archivering verzoek.
     * @param bericht bericht
     * @param richting ingaand/uitgaand
     * @return archivering verzoek
     */
    ArchiveringVerzoekBericht maakArchiveringVerzoek(final Bericht bericht, final RichtingType richting) {
        final ArchiveringVerzoekBericht resultaat = new ArchiveringVerzoekBericht();
        resultaat.setRichting(richting);
        resultaat.setReferentienummer(bericht.getMessageId());
        resultaat.setCrossReferentienummer(bericht.getCorrelationId());

        if (bericht.getNonDeliveryReason() != null) {
            resultaat.setSoortBericht("DeliveryReport");
            resultaat.setData(bericht.getNonDeliveryReason());
        } else if (bericht.getNotificationType() != null) {
            resultaat.setSoortBericht("StatusReport");
            resultaat.setData(bericht.getNotificationType());
        } else {
            resultaat.setSoortBericht(bepaalSoortBericht(bericht.getBerichtInhoud()));
            resultaat.setData(bericht.getBerichtInhoud());
        }

        if (richting == RichtingType.INGAAND) {
            resultaat.setTijdstipOntvangst(bericht.getTijdstipOntvangst());
        } else {
            resultaat.setTijdstipVerzending(bericht.getTijdstipVerzonden());
        }

        final String originator = bericht.getOriginator();
        if (originator != null && !"".equals(originator)) {
            final Mailbox zendendeMailbox = mailboxRepo.getMailboxByNummer(originator);
            if (zendendeMailbox != null) {
                resultaat.setZendendePartij(zendendeMailbox.getPartijcode());
            } else {
                LOGGER.warn("Mailbox {} niet gevonden. Kan geen verzendende instantie bepalen", originator);
            }
        }

        final String recipient = bericht.getRecipient();
        if (recipient != null && !"".equals(recipient)) {
            final Mailbox ontvangendeMailbox = mailboxRepo.getMailboxByNummer(recipient);
            if (ontvangendeMailbox != null) {
                resultaat.setOntvangendePartij(ontvangendeMailbox.getPartijcode());
            } else {
                LOGGER.warn("Mailbox {} niet gevonden. Kan geen ontvangende instantie bepalen", recipient);
            }
        }

        return resultaat;
    }

    private String bepaalSoortBericht(final String berichtInhoud) {
        if (berichtInhoud == null || "".equals(berichtInhoud)) {
            return "Null";
        }

        final String berichtNummer =
                berichtInhoud.length() >= MINIMALE_BERICHT_LENGTE ? berichtInhoud.substring(INDEX_BEGIN_BERICHTNUMMER, INDEX_EINDE_BERICHTNUMMER) : "????";
        return BEKENDE_BERICHTNUMMERS.contains(berichtNummer) ? berichtNummer : "Onbekend";
    }
}
