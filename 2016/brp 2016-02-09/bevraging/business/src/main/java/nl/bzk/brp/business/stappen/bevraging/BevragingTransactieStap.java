/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * In deze stap wordt een nieuwe read-only transactie gecreeerd voor de bevraging.
 */
public class BevragingTransactieStap
        extends AbstractBerichtVerwerkingStap<BevragingsBericht, BevragingBerichtContext, BevragingResultaat>
{

    private static final String TRANSACTIE_DEF_NAAM = "Transactie tbv verwerking van bevraging met berichtid: ";

    @Inject
    @Named("alleenLezenTransactionManager")
    private PlatformTransactionManager transactionManager;

    @Override
    public final boolean voerStapUit(final BevragingsBericht onderwerp, final BevragingBerichtContext context,
                               final BevragingResultaat resultaat)
    {
        final DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(TRANSACTIE_DEF_NAAM + context.getIngaandBerichtId());
        def.setReadOnly(true);
        context.setTransactionStatus(transactionManager.getTransaction(def));
        return DOORGAAN;
    }

    @Override
    public final void voerNabewerkingStapUit(final BevragingsBericht onderwerp, final BevragingBerichtContext context,
                                       final BevragingResultaat resultaat)
    {
        //Voor de zekerheid doen we hier een rollback!
        if (context.getTransactionStatus() != null) {
            transactionManager.rollback(context.getTransactionStatus());
        }
        context.clearBusinessTransactionStatus();
    }
}
