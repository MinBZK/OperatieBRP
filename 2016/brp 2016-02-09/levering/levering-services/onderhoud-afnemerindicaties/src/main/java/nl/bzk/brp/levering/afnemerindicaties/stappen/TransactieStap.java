/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 * De stap waarin de database transactie wordt opgezet en in de nabewerking wordt gecommit of gerollbacked. De rest van
 * de stappen verloopt binnen deze transactie.
 */
public class TransactieStap
        extends
        AbstractBerichtVerwerkingStap<RegistreerAfnemerindicatieBericht, OnderhoudAfnemerindicatiesBerichtContext, OnderhoudAfnemerindicatiesResultaat>
{

    private static final Logger        LOGGER              = LoggerFactory.getLogger();

    private static final String        TRANSACTIE_DEF_NAAM = "Transactie tbv verwerking van bericht: ";

    @Inject
    @Named("lezenSchrijvenTransactionManager")
    private PlatformTransactionManager transactionManager;

    @Override
    public final boolean voerStapUit(final RegistreerAfnemerindicatieBericht onderwerp,
            final OnderhoudAfnemerindicatiesBerichtContext context,
            final OnderhoudAfnemerindicatiesResultaat resultaat)
    {
        context.setTransactionStatus(maakDatabaseTransactieStatus(onderwerp));

        return DOORGAAN;
    }

    /**
     * Maakt een transactie voor de database.
     *
     * @param onderwerp Het huidige onderwerp.
     * @return De transactie.
     */
    private TransactionStatus maakDatabaseTransactieStatus(final RegistreerAfnemerindicatieBericht onderwerp) {
        final DefaultTransactionDefinition def =
            maakTransactieDefinitie(TRANSACTIE_DEF_NAAM
                + onderwerp.getStuurgegevens().getReferentienummer().getWaarde());
        return transactionManager.getTransaction(def);
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
    public final void voerNabewerkingStapUit(final RegistreerAfnemerindicatieBericht bericht,
            final OnderhoudAfnemerindicatiesBerichtContext context,
            final OnderhoudAfnemerindicatiesResultaat resultaat)
    {
        super.voerNabewerkingStapUit(bericht, context, resultaat);

        final TransactionStatus transactionStatus = context.getTransactionStatus();

        if (voldoetAanVoorwaardenVoorCommit(resultaat, transactionStatus)) {
            transactionManager.commit(transactionStatus);
        } else {
            LOGGER.info("Transactie voor bericht met referentieNummer '{}' krijgt een rollback.", bericht
                    .getStuurgegevens().getReferentienummer().getWaarde());
            transactionManager.rollback(transactionStatus);
        }
    }

    /**
     * Checkt of aan voorwaarden wordt voldaan voor commit van transacties.
     *
     * @param resultaat resultaat van verwerking
     * @param transactionStatus transactie status
     * @return true als wordt voldaan, anders false
     */
    private boolean voldoetAanVoorwaardenVoorCommit(final OnderhoudAfnemerindicatiesResultaat resultaat,
            final TransactionStatus transactionStatus)
    {
        return resultaat.isSuccesvol() && transactionStatus != null && !transactionStatus.isRollbackOnly();
    }
}
