/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.model.groep.logisch.BerichtStuurgegevensGroep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * In deze stap wordt een nieuwe transactie gecreeerd voor de bijhouding.
 */
public class BijhoudingTransactieStap
        extends AbstractBerichtVerwerkingsStap<AbstractBijhoudingsBericht, BerichtVerwerkingsResultaat>
{

    private static final Logger LOGGER                  = LoggerFactory.getLogger(BijhoudingTransactieStap.class);
    private static final String TRANSACTIE_DEF_NAAM     = "Transactie tbv verwerking van bijhouding met berichtid: ";

    @Inject
    private PlatformTransactionManager transactionManager;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht,
                                                     final BerichtContext context,
                                                     final BerichtVerwerkingsResultaat resultaat)
    {
        final DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(TRANSACTIE_DEF_NAAM + context.getIngaandBerichtId());
        def.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
        context.setTransactionStatus(transactionManager.getTransaction(def));
        return DOORGAAN_MET_VERWERKING;
    }

    @Override
    public void naVerwerkingsStapVoorBericht(final AbstractBijhoudingsBericht bericht, final BerichtContext context,
                                             final BerichtVerwerkingsResultaat resultaat)
    {
        final TransactionStatus transactionStatus = context.getTransactionStatus();
        final BerichtStuurgegevensGroep stuurgegevens = bericht.getBerichtStuurgegevensGroep();
        if (transactionStatus != null
            && resultaat.getVerwerkingsResultaat()
            && !transactionStatus.isRollbackOnly()
            && null == stuurgegevens.getIndPrevalidatie())
        {
            transactionManager.commit(transactionStatus);
        } else {
            LOGGER.info("Transactie voor bericht met id '" + context.getIngaandBerichtId() + "' "
                    + "krijgt een rollback.");
            transactionManager.rollback(transactionStatus);
        }
        context.clearBusinessTransactionStatus();
    }
}
