/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.locking.BrpLocker;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 * In deze stap wordt een nieuwe transactie gecreeerd voor de bijhouding.
 */
@Component
public class BijhoudingTransactieStap {

    private static final Logger        LOGGER              = LoggerFactory.getLogger();
    private static final String        TRANSACTIE_DEF_NAAM = "Transactie tbv verwerking van bijhouding met berichtid: ";

    @Inject
    @Named("lezenSchrijvenTransactionManager")
    private PlatformTransactionManager transactionManager;

    @Inject
    private BrpLocker brpLocker;

    /**
     * Start de transactie.
     * @param context de bijhoudingberichtcontext
     */
    public void startTransactie(final BijhoudingBerichtContext context) {
        final DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(TRANSACTIE_DEF_NAAM + context.getIngaandBerichtId());
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        context.setTransactionStatus(transactionManager.getTransaction(def));
    }

    /**
     * Stopt de transactie door een commit of een rollback te doen.
     * @param bericht het bijhoudingsbericht
     * @param context de bijhoudingberichtcontext
     * @param rollbackVerzoek of er stoppende fouten zijn opgetreden sinds startTransactie
     * @return resultaat
     */
    public Resultaat stopTransactie(final BijhoudingsBericht bericht, final BijhoudingBerichtContext context, final boolean rollbackVerzoek) {
        Resultaat resultaat = Resultaat.LEEG;

        final TransactionStatus transactionStatus = context.getTransactionStatus();

        if (transactieSituatieValide(transactionStatus) && opslaanIsGewenst(bericht, context) && !rollbackVerzoek) {
            transactionManager.commit(transactionStatus);
            // tot deze stap zou de administratieve handeling in het resultaat altijd leeg moeten zijn
            resultaat = resultaat.voegToe(Resultaat.builder().withAdministratieveHandeling(context.getAdministratieveHandeling()).build());
        } else {
            LOGGER.info("Transactie voor bericht met id '" + context.getIngaandBerichtId() + "' krijgt een rollback.");
            // reset de link naar administratieve handeling bij rollback.
            context.setAdministratieveHandeling(null);
            resultaat = resultaat.voegToe(Resultaat.builder().withAdministratieveHandeling(null).build());
            transactionManager.rollback(transactionStatus);
        }
        context.clearBusinessTransactionStatus();

        return resultaat;
    }

    private boolean transactieSituatieValide(final TransactionStatus transactionStatus) {
        return !transactionStatus.isRollbackOnly() && brpLocker.isLockNogAanwezig();
    }

    private boolean opslaanIsGewenst(final BijhoudingsBericht bericht, final BijhoudingBerichtContext context) {
        return !BerichtUtils.isBerichtPrevalidatieAan(bericht)
            && !BerichtUtils.isHuwelijksberichtMetHandmatigFiatterendePartners(bericht, context);
    }
}
