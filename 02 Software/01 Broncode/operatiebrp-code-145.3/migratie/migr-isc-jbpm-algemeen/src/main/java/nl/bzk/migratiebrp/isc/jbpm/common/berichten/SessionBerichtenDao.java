/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.berichten;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtMetaData;
import nl.bzk.migratiebrp.bericht.model.MessageId;
import nl.bzk.migratiebrp.bericht.model.brp.BrpBericht;
import nl.bzk.migratiebrp.bericht.model.brp.factory.BrpBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.isc.IscBericht;
import nl.bzk.migratiebrp.bericht.model.isc.factory.IscBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3EindBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.notificatie.factory.NotificatieBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.VirtueelProces;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * Berichten dao implementatie obv hibernate session factory.
 */
@Component
public final class SessionBerichtenDao implements BerichtenDao {

    /**
     * Constante voor het kanaal SYNC.
     */
    public static final String KANAAL_SYNC = "SYNC";
    /**
     * Constante voor het kanaal SYNC.
     */
    public static final String KANAAL_BRP = "BRP";

    /**
     * Constante voor het kanaal VOISC.
     */
    public static final String KANAAL_ISC = "ISC";

    /**
     * Constante voor het kanaal VOISC.
     */
    public static final String KANAAL_VOISC = "VOISC";

    /**
     * Constante voor het kanaal NOTIFICATIE.
     */
    public static final String KANAAL_NOTIFICATIE = "NOTIFICATIE";

    private static final String CORRELATION_ID = "correlationId";
    private static final String ID = "id";
    private static final String KANAAL = "kanaal";
    private static final String MESSAGE_ID = "messageId";
    private static final String MS_SEQUENCE_NUMBER = "msSequenceNumber";
    private static final String ONTVANGENDE_PARTIJ = "ontvangendePartij";
    private static final String PROCESS_INSTANCE = "processInstance";
    private static final String RICHTING = "richting";
    private static final String VERZENDENDE_PARTIJ = "verzendendePartij";
    private static final String VIRTUEEL_PROCES = "virtueelProces";

    private static final Map<String, BerichtFactory> FACTORIES = new HashMap<>();

    static {
        FACTORIES.put(KANAAL_VOISC, new Lo3BerichtFactory());
        FACTORIES.put(KANAAL_SYNC, SyncBerichtFactory.SINGLETON);
        FACTORIES.put(KANAAL_BRP, BrpBerichtFactory.SINGLETON);
        FACTORIES.put(KANAAL_ISC, IscBerichtFactory.SINGLETON);
        FACTORIES.put(KANAAL_NOTIFICATIE, NotificatieBerichtFactory.SINGLETON);
    }


    private final SessionFactory sessionFactory;

    /**
     * Constructor.
     * @param sessionFactory session factory
     */
    @Inject
    public SessionBerichtenDao(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Bericht leesBericht(final Long id) {
        final Session session = sessionFactory.getCurrentSession();

        final nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht dbBericht =
                (nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht) session.get(nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht.class, id);

        final BerichtFactory factory = FACTORIES.get(dbBericht.getKanaal());
        if (factory == null) {
            throw new IllegalArgumentException("Onbekend kanaal ('" + dbBericht.getKanaal() + "').");
        }

        final Bericht bericht = factory.getBericht(dbBericht.getBericht());
        bericht.setMessageId(dbBericht.getMessageId());
        bericht.setCorrelationId(dbBericht.getCorrelationId());

        if (bericht instanceof Lo3Bericht) {
            ((Lo3Bericht) bericht).setBronPartijCode(dbBericht.getVerzendendePartij());
            ((Lo3Bericht) bericht).setDoelPartijCode(dbBericht.getOntvangendePartij());
        }

        return bericht;
    }

    @Override
    public Long bewaarBericht(final Bericht bericht) {
        try {
            final nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht dbBericht = new nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht();

            final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
            if (executionContext == null) {
                throw new IllegalStateException("BerichtenDao moet binnen een geldige execution context worden gebruikt.");
            }
            dbBericht.setProcessInstance(executionContext.getProcessInstance());
            dbBericht.setMessageId(bericht.getMessageId());
            dbBericht.setCorrelationId(bericht.getCorrelationId());
            if (bericht instanceof Lo3Bericht) {
                dbBericht.setVerzendendePartij(((Lo3Bericht) bericht).getBronPartijCode());
                dbBericht.setOntvangendePartij(((Lo3Bericht) bericht).getDoelPartijCode());
            }
            dbBericht.setBericht(bericht.format());
            dbBericht.setNaam(bericht.getBerichtType());
            dbBericht.setKanaal(bepaalKanaal(bericht));
            dbBericht.setTijdstip(new Date());
            if (bericht instanceof Lo3Bericht) {
                dbBericht.setRequestNonReceipt(bericht instanceof Lo3EindBericht);
            }

            final nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht persistentBericht =
                    (nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht) sessionFactory.getCurrentSession().merge(dbBericht);

            if (persistentBericht.getMessageId() == null) {
                persistentBericht.setMessageId(MessageId.bepaalMessageId(persistentBericht.getId()));
            }
            return persistentBericht.getId();

        } catch (final BerichtInhoudException e) {
            throw new IllegalArgumentException("Ongeldig bericht om op te slaan", e);
        }
    }

    private String bepaalKanaal(final Bericht bericht) {
        final String kanaal;
        if (bericht instanceof SyncBericht) {
            kanaal = KANAAL_SYNC;
        } else if (bericht instanceof Lo3Bericht) {
            kanaal = KANAAL_VOISC;
        } else if (bericht instanceof BrpBericht) {
            kanaal = KANAAL_BRP;
        } else if (bericht instanceof IscBericht) {
            kanaal = KANAAL_ISC;
        } else {
            throw new IllegalArgumentException("Onbekend type bericht");
        }
        return kanaal;
    }

    @Override
    public BerichtMetaData leesBerichtMetaData(final Long id) {
        final Session session = sessionFactory.getCurrentSession();

        final nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht bericht =
                (nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht) session.get(nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht.class, id);

        final BerichtMetaData berichtMetaData = new BerichtMetaData();
        berichtMetaData.setId(bericht.getId());
        if (bericht.getTijdstip() != null) {
            berichtMetaData.setTijdstip(new Timestamp(bericht.getTijdstip().getTime()));
        }
        berichtMetaData.setKanaal(bericht.getKanaal());
        if (bericht.getRichting() != null) {
            berichtMetaData.setRichting(bericht.getRichting().toString());
        }
        berichtMetaData.setMessageId(bericht.getMessageId());
        berichtMetaData.setCorrelationId(bericht.getCorrelationId());
        berichtMetaData.setBerichtType(bericht.getNaam());

        return berichtMetaData;
    }

    @Override
    public Long bewaar(
            final String kanaal,
            final Direction direction,
            final String messageId,
            final String correlatieId,
            final String bericht,
            final String originator,
            final String recipient,
            final Long msSequenceNumber,
            final Boolean requestNonReceipt) {
        final nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht dbBericht = new nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht();

        dbBericht.setKanaal(kanaal);
        dbBericht.setRichting(direction.getCode());
        dbBericht.setMessageId(messageId);
        dbBericht.setCorrelationId(correlatieId);
        dbBericht.setBericht(bericht);
        dbBericht.setVerzendendePartij(originator);
        dbBericht.setOntvangendePartij(recipient);
        dbBericht.setMsSequenceNumber(msSequenceNumber);
        dbBericht.setRequestNonReceipt(requestNonReceipt);
        dbBericht.setTijdstip(new Date());

        final nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht persistentBericht =
                (nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht) sessionFactory.getCurrentSession().merge(dbBericht);

        return persistentBericht.getId();
    }

    @Override
    public void updateNaam(final Long berichtId, final String naam) {
        getBericht(berichtId).setNaam(naam);
    }

    @Override
    public void updateProcessInstance(final Long berichtId, final Long processInstanceId) {
        final ProcessInstance processInstance = (ProcessInstance) sessionFactory.getCurrentSession().get(ProcessInstance.class, processInstanceId);
        if (processInstance == null) {
            throw new IllegalArgumentException("Process instance " + processInstanceId + " niet gevonden.");
        }
        getBericht(berichtId).setProcessInstance(processInstance);
    }

    @Override
    public void updateActie(final Long berichtId, final String actie) {
        getBericht(berichtId).setActie(actie);

    }

    @Override
    public nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht zoekVraagBericht(
            final String correlatieId,
            final String kanaal,
            final String originator,
            final String recipient) {
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht.class);
        criteria.add(Restrictions.eq(MESSAGE_ID, correlatieId));
        criteria.add(Restrictions.eq(KANAAL, kanaal));
        criteria.add(Restrictions.eq(VERZENDENDE_PARTIJ, recipient));
        criteria.add(Restrictions.eq(ONTVANGENDE_PARTIJ, originator));
        // Voeg een criteria toe waarbij het process instance id niet null mag zijn. De reden voor deze controle is dat
        // er tussen het Ap01-bericht (plaatsen afnemersindicatie) en het bijbehorende Ag01-bericht (leveringsbericht na
        // plaatsen) herhaalde Ap01's gestuurd kunnen zijn.
        // In het geval dat er herhaalde Ap01's zijn gestuurd, dan is voor die Ap01's, die worden genegeerd indien het
        // oorspronkelijke niet langer dan twee dagen geleden is verstuurd, het process instance id null. Dit zou kunnen
        // resulteren in een NPE in de bovenliggende ESB action en het is inhoudelijk ook niet correct; het Ag01 dient
        // namelijk gerelateerd te worden aan het laatste Ap01 bericht dat het proces heeft gestart (en waarop het dus
        // ook echt het antwoord is).
        criteria.add(Restrictions.isNotNull(PROCESS_INSTANCE));

        final List<?> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return (nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht) list.get(0);
        }
    }

    @Override
    public List<nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht> zoekOpMsSequenceNumberBehalveId(final Long msSequenceNumber, final Long berichtId) {
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht.class);
        criteria.add(Restrictions.ne(ID, berichtId));
        criteria.add(Restrictions.eq(MS_SEQUENCE_NUMBER, msSequenceNumber));
        return criteria.list();
    }

    @Override
    public int telBerichtenBehalveId(
            final String messageId,
            final String originator,
            final String recipient,
            final String kanaal,
            final Direction direction,
            final Long berichtId) {
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht.class);
        criteria.add(Restrictions.ne(ID, berichtId));
        criteria.add(Restrictions.eq(MESSAGE_ID, messageId));
        criteria.add(Restrictions.eq(VERZENDENDE_PARTIJ, originator));
        criteria.add(Restrictions.eq(ONTVANGENDE_PARTIJ, recipient));
        criteria.add(Restrictions.eq(KANAAL, kanaal));
        criteria.add(Restrictions.eq(RICHTING, direction.getCode()));

        return criteria.list().size();
    }

    @Override
    public nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht zoekMeestRecenteAntwoord(
            final String messageId,
            final String originator,
            final String recipient,
            final String kanaal) {
        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht.class);
        criteria.add(Restrictions.eq(CORRELATION_ID, messageId));
        criteria.add(Restrictions.eq(VERZENDENDE_PARTIJ, recipient));
        criteria.add(Restrictions.eq(ONTVANGENDE_PARTIJ, originator));
        criteria.add(Restrictions.eq(KANAAL, kanaal));
        criteria.addOrder(Order.desc(ID));
        criteria.setMaxResults(1);

        final List<?> list = criteria.list();
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return (nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht) list.get(0);
        }
    }

    @Override
    public void updateMessageId(final Long berichtId, final String messageId) {
        getBericht(berichtId).setMessageId(messageId);
    }

    @Override
    public void updateCorrelatieId(final Long berichtId, final String correlatieId) {
        getBericht(berichtId).setCorrelationId(correlatieId);
    }

    @Override
    public void updateVirtueelProcesId(final Long berichtId, final Long virtueelProcesId) {
        final VirtueelProces virtueelProces = (VirtueelProces) sessionFactory.getCurrentSession().get(VirtueelProces.class, virtueelProcesId);
        if (virtueelProces == null) {
            throw new IllegalArgumentException(String.format("Virtueel proces %s onbekend.", virtueelProcesId));
        }
        getBericht(berichtId).setVirtueelProces(virtueelProces);
    }

    @Override
    public void omzettenVirtueelProcesNaarActueelProces(final long virtueelProcesId, final long processInstanceId) {
        final VirtueelProces virtueelProces = (VirtueelProces) sessionFactory.getCurrentSession().get(VirtueelProces.class, virtueelProcesId);
        if (virtueelProces == null) {
            throw new IllegalArgumentException(String.format("Virtueel proces %s onbekend.", virtueelProcesId));
        }

        final ProcessInstance processInstance = (ProcessInstance) sessionFactory.getCurrentSession().get(ProcessInstance.class, processInstanceId);
        if (processInstance == null) {
            throw new IllegalArgumentException(String.format("Proces instance %s onbekend.", virtueelProcesId));
        }

        final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht.class);
        criteria.add(Restrictions.eq(VIRTUEEL_PROCES, virtueelProces));

        for (final nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht bericht : (List<nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht>) criteria.list()) {
            bericht.setVirtueelProces(null);
            bericht.setProcessInstance(processInstance);
        }

    }

    @Override
    public nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht getBericht(final Long berichtId) {
        return (nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht) sessionFactory.getCurrentSession()
                .get(nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht.class, berichtId);
    }

    @Override
    public void updateKanaalEnRichting(final Long berichtId, final String kanaal, final Direction direction) {
        final nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht bericht = getBericht(berichtId);
        bericht.setKanaal(kanaal);
        bericht.setRichting(direction.getCode());

    }
}
