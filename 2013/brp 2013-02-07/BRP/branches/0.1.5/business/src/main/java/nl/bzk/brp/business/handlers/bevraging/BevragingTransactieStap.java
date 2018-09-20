/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bevraging;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * In deze stap wordt een nieuwe read-only transactie gecreeerd voor de bevraging.
 */
public class BevragingTransactieStap
        extends AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat>
{

    private static final String TRANSACTIE_DEF_NAAM     = "Transactie tbv verwerking van bevraging met berichtid: ";

    @Inject
    private PlatformTransactionManager transactionManager;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final BRPBericht bericht,
                                                     final BerichtContext context,
                                                     final BerichtResultaat resultaat)
    {
        final DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(TRANSACTIE_DEF_NAAM + context.getIngaandBerichtId());
        def.setReadOnly(true);
        context.setTransactionStatus(transactionManager.getTransaction(def));
        return DOORGAAN_MET_VERWERKING;
    }

    @Override
    public void naVerwerkingsStapVoorBericht(final BRPBericht bericht, final BerichtContext context,
                                             final BerichtResultaat resultaat)
    {
        //Voor de zekerheid doen we hier een rollback!
        if (context.getTransactionStatus() != null) {
            transactionManager.rollback(context.getTransactionStatus());
        }
        context.clearBusinessTransactionStatus();
    }
}
