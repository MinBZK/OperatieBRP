/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import java.util.Map;

import nl.moderniseringgba.isc.esb.message.sync.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3AntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Converteert de BRP persoonslijst naar een Lo3 Persoonslijst.
 */
@Component("uc306ControleerConverteerResponseBerichtDecision")
public final class ControleerConverteerResponseBerichtDecision implements SpringDecision {

    private static final String RESULT_FOUT = "4b-2. Fout";
    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final ConverteerNaarLo3AntwoordBericht converteerNaarLo3Antwoord =
                (ConverteerNaarLo3AntwoordBericht) parameters.get("converteerNaarLo3Antwoord");

        final String result;
        if (StatusType.OK.equals(converteerNaarLo3Antwoord.getStatus())) {
            final Lo3Persoonslijst lo3Persoonslijst = converteerNaarLo3Antwoord.getLo3Persoonslijst();
            if (lo3Persoonslijst == null) {
                result = RESULT_FOUT;
            } else {
                result = null;
            }
        } else {
            result = RESULT_FOUT;
        }

        return result;
    }
}
