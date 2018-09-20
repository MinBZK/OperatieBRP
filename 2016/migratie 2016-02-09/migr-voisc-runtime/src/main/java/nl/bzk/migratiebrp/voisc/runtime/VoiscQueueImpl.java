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
import javax.jms.Session;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RichtingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SysteemType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ArchiveringVerzoekBericht;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.MDC;
import nl.bzk.migratiebrp.util.common.logging.MDC.MDCCloser;
import nl.bzk.migratiebrp.util.common.logging.MDCVeld;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscQueueException;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Queue interactie.
 */
@Service
public final class VoiscQueueImpl implements VoiscQueue {
    private static final String VOISC_TRANSACTION_MANAGER = "voiscTransactionManager";
    private static final String PARTIJ_CODE_MIGRATIEVOORZIENINGEN = "199902";
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

    @Inject
    @Named("voiscJmsTemplate")
    private JmsTemplate jmsTemplate;

    @Inject
    @Named("vospgOntvangstQueue")
    private Destination vospgOntvangst;

    @Inject
    @Named("archiveringQueue")
    private Destination archivering;

    @Inject
    private MailboxRepository mailboxRepo;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, value = "voiscTransactionManager")
    public void verstuurBerichtNaarIsc(final Bericht bericht) throws VoiscQueueException {
        LOGGER.info("[Bericht {}]: Bericht versturen naar ISC.", bericht.getId());
        try {
            jmsTemplate.send(vospgOntvangst, new MessageCreator() {
                @Override
                public Message createMessage(final Session session) throws JMSException {
                    final Message message = session.createTextMessage(bericht.getBerichtInhoud());
                    fillMessage(bericht, message);
                    return message;
                }
            });
            LOGGER.info("[Bericht {}]: Bericht succesvol verstuurd naar ISC.", bericht.getId());
            try (MDCCloser berichtIdCloser = MDC.put(MDCVeld.VOISC_BERICHT_ID, bericht.getId())) {
                LOGGER.info(FunctioneleMelding.VOISC_ISC_VERSTUURD);
            }
        } catch (final JmsException e) {
            LOGGER.error("[Bericht {}]: Onverwachte fout bij versturen van bericht naar ISC.", bericht.getId(), e);
            throw new VoiscQueueException("Onverwachte fout bij het versturen van een bericht naar de ISC", e);
        }
    }

    private void fillMessage(final Bericht bericht, final Message message) throws JMSException {
        if (bericht.getRecipient() != null) {
            final Mailbox recipientMailbox = mailboxRepo.getMailboxByNummer(bericht.getRecipient());
            message.setStringProperty(JMSConstants.BERICHT_RECIPIENT, recipientMailbox.getFormattedInstantiecode());
        }
        if (bericht.getOriginator() != null) {
            final Mailbox originatorMailbox = mailboxRepo.getMailboxByNummer(bericht.getOriginator());
            message.setStringProperty(JMSConstants.BERICHT_ORIGINATOR, originatorMailbox.getFormattedInstantiecode());
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
            jmsTemplate.send(archivering, new MessageCreator() {
                @Override
                public Message createMessage(final Session session) throws JMSException {
                    final ArchiveringVerzoekBericht verzoekBericht = maakArchiveringVerzoek(bericht, richting);
                    final String verzoek = verzoekBericht.format();
                    LOGGER.info("Verzoek: {}", verzoek);
                    final Message message = session.createTextMessage(verzoek);
                    return message;
                }
            });
            LOGGER.info("[Bericht {}]: Bericht succesvol verstuurd naar archivering.", bericht.getId());
        } catch (final JmsException e) {
            LOGGER.error("[Bericht {}]: Onverwachte fout bij versturen van bericht naar archivering.", bericht.getId(), e);
            throw new VoiscQueueException("Onverwachte fout bij het versturen van een bericht naar de archivering", e);
        }
    }

    /**
     * Maak archivering verzoek.
     *
     * @param bericht
     *            bericht
     * @param richting
     *            ingaand/uitgaand
     * @return archivering verzoek
     */
    protected ArchiveringVerzoekBericht maakArchiveringVerzoek(final Bericht bericht, final RichtingType richting) {
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
            resultaat.setOntvangendeSysteem(SysteemType.MIGRATIEVOORZIENINGEN);
            resultaat.setOntvangendeAfnemerCode(PARTIJ_CODE_MIGRATIEVOORZIENINGEN);
            resultaat.setZendendeSysteem(SysteemType.GBA_NETWERK);

            final String originator = bericht.getOriginator();
            if (originator != null && !"".equals(originator)) {
                final Mailbox zendendeMailbox = mailboxRepo.getMailboxByNummer(originator);
                if (zendendeMailbox != null) {
                    if ("G".equals(zendendeMailbox.getInstantietype())) {
                        resultaat.setZendendeGemeenteCode(zendendeMailbox.getFormattedInstantiecode());
                    } else if ("A".equals(zendendeMailbox.getInstantietype())) {
                        resultaat.setZendendeAfnemerCode(zendendeMailbox.getFormattedInstantiecode());
                    } else {
                        LOGGER.warn("Mailbox {} was van onverwacht type: {}", originator, zendendeMailbox.getInstantietype());
                    }
                } else {
                    LOGGER.warn("Mailbox {} niet gevonden. Kan geen verzendende instantie bepalen", originator);
                }
            }
        } else {
            resultaat.setZendendeSysteem(SysteemType.MIGRATIEVOORZIENINGEN);
            resultaat.setZendendeAfnemerCode(PARTIJ_CODE_MIGRATIEVOORZIENINGEN);
            resultaat.setOntvangendeSysteem(SysteemType.GBA_NETWERK);

            final String recipient = bericht.getRecipient();
            if (recipient != null && !"".equals(recipient)) {
                final Mailbox ontvangendeMailbox = mailboxRepo.getMailboxByNummer(recipient);
                if (ontvangendeMailbox != null) {
                    if ("G".equals(ontvangendeMailbox.getInstantietype())) {
                        resultaat.setOntvangendeGemeenteCode(ontvangendeMailbox.getFormattedInstantiecode());
                    } else if ("A".equals(ontvangendeMailbox.getInstantietype())) {
                        resultaat.setOntvangendeAfnemerCode(ontvangendeMailbox.getFormattedInstantiecode());
                    } else {
                        LOGGER.warn("Mailbox {} was van onverwacht type: {}", recipient, ontvangendeMailbox.getInstantietype());
                    }
                } else {
                    LOGGER.warn("Mailbox {} niet gevonden. Kan geen ontvangende instantie bepalen", recipient);
                }
            }
        }

        return resultaat;
    }

    private String bepaalSoortBericht(final String berichtInhoud) {
        if (berichtInhoud == null || "".equals(berichtInhoud)) {
            return "Null";
        }

        final String berichtNummer = berichtInhoud.length() >= 11 ? berichtInhoud.substring(8, 12) : "????";
        if (BEKENDE_BERICHTNUMMERS.contains(berichtNummer)) {
            return berichtNummer;
        } else {
            return "Onbekend";
        }
    }
}
