/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import java.util.List;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Command dat de geselecteerde BSN lijst modificeert.
 */
@Component
public class ModificeerBsnLijstStap implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModificeerBsnLijstStap.class);

    @Override
    public boolean execute(final Context context) throws Exception {
        List<Integer> bsns = (List<Integer>) context.get(ContextParameterNames.BSNLIJST);
        int count = (Integer) context.get(ContextParameterNames.AANTAL_PERSOONSLIJSTEN);
        int repeat = context.containsKey(ContextParameterNames.DUBBELE_PER) ?
                (Integer) context.get(ContextParameterNames.DUBBELE_PER) : -1;

        if (repeat > 2) {
            for (int i = 1; i < bsns.size(); i++) {
                if (i % repeat == 0) {
                    Integer bsn = bsns.get(i - 1);
                    bsns.set(i, bsn);
                }
            }
        }

        if (bsns.size() > count) {
            bsns = bsns.subList(0, count);
        }
        context.put(ContextParameterNames.BSNLIJST, bsns);

        return false;
    }
}
