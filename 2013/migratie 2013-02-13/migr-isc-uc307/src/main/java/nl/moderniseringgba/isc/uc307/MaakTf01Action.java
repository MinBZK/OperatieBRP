/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf01Bericht.Foutreden;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maakt een Tf01Bericht op basis van {@link Tb01Bericht} en {@link Foutreden}.
 * 
 */
@Component("uc307MaakTf01Action")
public final class MaakTf01Action implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Tb01Bericht tb01Bericht = (Tb01Bericht) parameters.get("input");
        final String fout = (String) parameters.get("foutreden");

        final Foutreden foutreden = Foutreden.valueOf(fout);
        final Tf01Bericht tf01Bericht = new Tf01Bericht(tb01Bericht, foutreden);

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(UC307Constants.TF01_BERICHT, tf01Bericht);

        return result;
    }

}
