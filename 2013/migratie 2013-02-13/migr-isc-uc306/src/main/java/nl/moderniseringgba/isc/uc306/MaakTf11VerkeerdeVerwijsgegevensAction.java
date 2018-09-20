/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf11Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tv01Bericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * 
 * Logt op basis van de foutreden in het Tf01-bericht een melding.
 * 
 */
@Component("uc306MaakTf11VerkeerdeVerwijsgegevensAction")
public final class MaakTf11VerkeerdeVerwijsgegevensAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String PARAMETER_TF11_BERICHT = "tf11Bericht";

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);
        final Tv01Bericht tv01Bericht = (Tv01Bericht) parameters.get("tv01Bericht");

        final Tf11Bericht tf11Bericht = new Tf11Bericht();

        final String verwijzingANummer = tv01Bericht.getCategorieen().get(0).getElement(Lo3ElementEnum.ELEMENT_0110);
        tf11Bericht.setANummer(verwijzingANummer);
        tf11Bericht.setCorrelationId(tv01Bericht.getMessageId());
        tf11Bericht.setBronGemeente(tv01Bericht.getDoelGemeente());
        tf11Bericht.setDoelGemeente(tv01Bericht.getBronGemeente());

        // Output
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(PARAMETER_TF11_BERICHT, tf11Bericht);
        return result;
    }
}
