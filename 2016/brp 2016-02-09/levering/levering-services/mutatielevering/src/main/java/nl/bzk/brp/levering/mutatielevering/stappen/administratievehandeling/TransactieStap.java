/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatielevering.stappen.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * In deze stap wordt een nieuwe transactie gecreeerd voor zowel de (master) database als de jms queue. Op dit moment zijn dit 2 verschillende transacties.
 * Bij gebruik van JTA zou dit terug kunnen naar 1 transactie.
 */
public class TransactieStap extends AbstractAdministratieveHandelingVerwerkingStap {

    private static final Logger LOGGER                  = LoggerFactory.getLogger();
    private static final String TRANSACTIE_DEF_NAAM     =
        "Transactie tbv verwerking van administratieve handeling met id: ";
    private static final String JMS_TRANSACTIE_DEF_NAAM =
        "JMS transactie tbv verwerking van administratieve handeling met id: ";

    @Inject
    @Named("lezenSchrijvenTransactionManager")
    private PlatformTransactionManager transactionManagerMaster;

    @Inject
    @Named("jmsTransactionManagerAfnemers")
    private PlatformTransactionManager jmsTransactionManagerAfnemers;

    @Override
    public final boolean voerStapUit(final AdministratieveHandelingMutatie onderwerp,
        final AdministratieveHandelingVerwerkingContext context,
        final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        context.setJmsTransactionStatus(maakJMSTransactieStatus(onderwerp));
        context.setTransactionStatus(maakDatabaseTransactieStatus(onderwerp));

        return DOORGAAN;
    }

    /**
     * Maakt een transactie voor de database.
     *
     * @param onderwerp Het huidige onderwerp.
     * @return De transactie.
     */
    private TransactionStatus maakDatabaseTransactieStatus(final AdministratieveHandelingMutatie onderwerp) {
        final DefaultTransactionDefinition def =
            maakTransactieDefinitie(TRANSACTIE_DEF_NAAM + onderwerp.getAdministratieveHandelingId());
        return transactionManagerMaster.getTransaction(def);
    }

    /**
     * Maakt een transactie voor JMS.
     *
     * @param onderwerp Het huidige onderwerp.
     * @return De transactie.
     */
    private TransactionStatus maakJMSTransactieStatus(final AdministratieveHandelingMutatie onderwerp) {
        final DefaultTransactionDefinition def =
            maakTransactieDefinitie(JMS_TRANSACTIE_DEF_NAAM + onderwerp.getAdministratieveHandelingId());
        return jmsTransactionManagerAfnemers.getTransaction(def);
    }

    /**
     * Maakt een transactie definitie voor een transactienaam.
     *
     * @param transactieNaam De naam van de transactie.
     * @return De transactie definitie.
     */
    private DefaultTransactionDefinition maakTransactieDefinitie(final String transactieNaam) {
        final DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(transactieNaam);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return def;
    }

    @Override
    public final void voerNabewerkingStapUit(final AdministratieveHandelingMutatie onderwerp,
        final AdministratieveHandelingVerwerkingContext context,
        final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        final TransactionStatus transactionStatus = context.getTransactionStatus();
        final TransactionStatus jmsTransactionStatus = context.getJmsTransactionStatus();
        if (voldoetAanVoorwaardenVoorCommit(resultaat, transactionStatus, jmsTransactionStatus)) {
            transactionManagerMaster.commit(transactionStatus);
            jmsTransactionManagerAfnemers.commit(jmsTransactionStatus);
        } else {
            LOGGER.info(
                "Transactie voor administratieve handeling met id '" + onderwerp.getAdministratieveHandelingId()
                    + "' "
                    + "krijgt een rollback.");
            transactionManagerMaster.rollback(transactionStatus);
            jmsTransactionManagerAfnemers.rollback(jmsTransactionStatus);
        }
        context.clearBusinessTransactionStatus();
    }

    /**
     * Checkt of aan voorwaarden wordt voldaan voor commit van transacties.
     *
     * @param resultaat            resultaat van verwerking
     * @param transactionStatus    transactie status
     * @param jmsTransactionStatus jms transactie status
     * @return true als wordt voldaan, anders false
     */
    private boolean voldoetAanVoorwaardenVoorCommit(final AdministratieveHandelingVerwerkingResultaat resultaat,
        final TransactionStatus transactionStatus,
        final TransactionStatus jmsTransactionStatus)
    {
        final boolean databaseTransactieIsNietRollbackOnly = transactionStatus != null && !transactionStatus.isRollbackOnly();
        final boolean jmsTransactieIsNietRollbackOnly = jmsTransactionStatus != null && !jmsTransactionStatus.isRollbackOnly();

        return resultaat.isSuccesvol()
            && databaseTransactieIsNietRollbackOnly
            && jmsTransactieIsNietRollbackOnly;
    }
}
