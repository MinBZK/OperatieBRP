/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.impl.MvGeboorteVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Voert de actie uit waarbij een MvGeboorteVerzoekBericht wordt opgesteld om de geboorte in BRP door te voeren.
 * 
 */
@Component("uc307MaakMVGeboorteVerzoekBerichtAction")
public final class MaakMVGeboorteVerzoekBerichtAction implements SpringAction {

    /**
     * Constante voor de persoonslijst van het kind in de parameter-map.
     */
    public static final String PL_KIND = "plKind";

    /**
     * Constante voor de persoonslijst van de moeder in de parameter-map.
     */
    public static final String PL_MOEDER = "plMoeder";

    /**
     * Constante voor het geboorteverzoek bericht in de parameter-map.
     */
    public static final String MV_GEBOORTE_VERZOEK_BERICHT = "mvGeboorteVerzoekBericht";

    /**
     * Variabele voor logging.
     */
    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Tb01Bericht input = (Tb01Bericht) parameters.get("input");

        final ConverteerNaarBrpAntwoordBericht converteerNaarBrpAntwoordKind =
                (ConverteerNaarBrpAntwoordBericht) parameters.get(PL_KIND);
        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordMoeder =
                (LeesUitBrpAntwoordBericht) parameters.get(PL_MOEDER);

        final BrpPersoonslijst plKind = converteerNaarBrpAntwoordKind.getBrpPersoonslijst();
        final BrpPersoonslijst plMoeder = leesUitBrpAntwoordMoeder.getBrpPersoonslijst();

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = new MvGeboorteVerzoekBericht(plKind, plMoeder);
        mvGeboorteVerzoekBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal(input.getBronGemeente())));
        mvGeboorteVerzoekBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal(input.getDoelGemeente())));
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(MV_GEBOORTE_VERZOEK_BERICHT, mvGeboorteVerzoekBericht);

        return result;
    }
}
