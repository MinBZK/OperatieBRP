/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.autconv.lo3naarbrp;

import java.io.IOException;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
final class ConverteerderImpl implements Converteerder {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private LeveringsautorisatieConversie leveringsautorisatieConversie;
    @Inject
    private ToegangLeveringsAutorisatieConversie toegangLeveringsAutorisatieConversie;
    @Inject
    private AfnemerindicatieConversie afnemerindicatieConversie;

    private ConverteerderImpl() {}

    @Override
    @Transactional(transactionManager = "masterTransactionManager", propagation = Propagation.REQUIRES_NEW )
    public void converteer() throws IOException {
        LOGGER.info("Start conversie");
        leveringsautorisatieConversie.converteerLeveringsautorisaties();
        toegangLeveringsAutorisatieConversie.converteerToegangLeveringsautorisaties();
        afnemerindicatieConversie.converteerAfnemerindicaties();
        LOGGER.info("Einde conversie");
    }
}
