/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc311;

import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.format.BrpFormat;
import nl.moderniseringgba.isc.esb.message.brp.impl.WijzigingANummerSignaalBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Wa01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Parser;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.jbpm.graph.exe.ExecutionContext;
import org.springframework.stereotype.Component;

/**
 * Maak een Wa01 bericht obv input, gelezen pl en doel gemeente.
 */
@Component("uc311MaakWa01BerichtAction")
public final class MaakWa01BerichtAction implements SpringAction {

    private static final String WA01_BERICHT = "wa01Bericht";
    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final WijzigingANummerSignaalBericht input = (WijzigingANummerSignaalBericht) parameters.get("input");
        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                (LeesUitBrpAntwoordBericht) parameters.get("leesUitBrpAntwoordBericht");
        final Lo3Persoonslijst persoonslijst = leesUitBrpAntwoordBericht.getLo3Persoonslijst();
        final Lo3PersoonInhoud persoon = persoonslijst.getPersoonStapel().getMeestRecenteElement().getInhoud();

        // Maak wa01 Bericht
        final Wa01Bericht wa01Bericht = new Wa01Bericht();
        wa01Bericht.setBronGemeente(input.getBrpGemeente().getFormattedStringCode());

        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        final String doelGemeente = (String) executionContext.getVariable("doelGemeente");

        wa01Bericht.setDoelGemeente(doelGemeente);

        wa01Bericht.setNieuwANummer(input.getNieuwANummer());
        wa01Bericht.setOudANummer(input.getOudANummer());
        wa01Bericht.setDatumGeldigheid(Parser.parseLo3Datum(BrpFormat.toString(BrpFormat.format(input
                .getDatumGeldigheid()))));

        wa01Bericht.setVoornamen(persoon.getVoornamen());
        wa01Bericht.setAdellijkeTitelPredikaatCode(persoon.getAdellijkeTitelPredikaatCode());
        wa01Bericht.setVoorvoegselGeslachtsnaam(persoon.getVoorvoegselGeslachtsnaam());
        wa01Bericht.setGeslachtsnaam(persoon.getGeslachtsnaam());

        wa01Bericht.setGeboortedatum(persoon.getGeboortedatum());
        wa01Bericht.setGeboorteGemeenteCode(persoon.getGeboorteGemeenteCode());
        wa01Bericht.setGeboorteLandCode(persoon.getGeboorteLandCode());

        // Herhaling
        final Object herhaling = executionContext.getVariable("wa01Herhaling");
        if (herhaling != null) {
            wa01Bericht.setHeader(Lo3HeaderVeld.HERHALING, String.valueOf(herhaling));

            // Herhaal bericht moet zelfde message id hebben
            final Lo3Bericht orgineel = (Lo3Bericht) executionContext.getVariable(WA01_BERICHT);
            wa01Bericht.setMessageId(orgineel.getMessageId());
        }

        executionContext.setVariable(WA01_BERICHT, wa01Bericht);
        return null;
    }
}
