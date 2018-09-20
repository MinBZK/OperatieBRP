/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import nl.bzk.brp.bevraging.app.support.PersoonIdentificatie;
import nl.bzk.brp.bevraging.app.support.PersoonsLijst;
import nl.bzk.brp.bevraging.dataaccess.PersoonsLijstService;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

/**
 * Stap die een aantal verzoeken naar de BD stuurt om deze in een "opgewarmde" toestande te krijgen.
 */
@Service
public class WarmDatabaseOpStap implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarmDatabaseOpStap.class);
    private static final int WARMUP_COUNT = 3;

    private StopWatch timer = new StopWatch("WarmDatabaseOp");

    @Inject
    private PersoonsLijstService persoonsLijstService;

    @Override
    public boolean execute(final Context context) throws Exception {
        Iterator<PersoonIdentificatie> iter = ((List) context.get(ContextParameterNames.BSNLIJST)).iterator();
        int count = 0;

        while (count++ < WARMUP_COUNT && iter.hasNext()) {
            Integer bsn = iter.next().getBsn();

            timer.start(bsn.toString());
            PersoonsLijst pl = persoonsLijstService.findPersoonVolledigLijst(bsn);

            if (pl != null) {
                LOGGER.debug("Opwarmen met persoonslijst: {}", pl.toString());
            }

            timer.stop();
            LOGGER.info("Gevonden persoon {} in {} ms", bsn, timer.getLastTaskTimeMillis());

        }

        return CONTINUE_PROCESSING;
    }
}
