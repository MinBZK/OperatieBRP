/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.impl.GeboorteVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3VerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Converteert de BRP persoonslijst naar een Lo3 Persoonslijst.
 */
@Component("uc306ConverteerBrpGeboorteberichtAction")
public final class ConverteerBrpGeboorteberichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final GeboorteVerzoekBericht bericht = (GeboorteVerzoekBericht) parameters.get("input");
        final BrpPersoonslijst brpPersoonslijst = bericht.getBrpPersoonslijst();

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("converteerBericht", new ConverteerNaarLo3VerzoekBericht(brpPersoonslijst));

        return result;
    }
}
