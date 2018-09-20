/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.actions;

import java.sql.Timestamp;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.BerichtMetaData;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.rapportage.RapportageDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.foutafhandeling.FoutafhandelingConstants;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * Voegt de gestarte proces instantie toe aan de extratie tabel ten behoeve van rapportage.
 */
@Component("startProcesInstantieAction")
public final class StartProcesInstantieAction implements SpringAction {

    @Inject
    private RapportageDao rapportageDao;

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        if (executionContext == null) {
            throw new IllegalStateException(FoutafhandelingConstants.FOUTMELDING_GEEN_EXECUTION_CONTEXT);
        }

        final ProcessInstance procesInstantie = executionContext.getProcessInstance();
        if (procesInstantie == null) {
            throw new IllegalStateException(FoutafhandelingConstants.FOUTMELDING_GEEN_GESTARTE_PROCES_INSTANTIE);
        }

        // Maak rapportage extract regel aan als dit een 'root proces' is.
        if (procesInstantie.getSuperProcessToken() == null) {

            final BerichtMetaData berichtMetaData = getStartBerichtMetaData(parameters);
            if (berichtMetaData == null) {
                throw new IllegalStateException("Kan bericht metadata niet ophalen?!?");
            }

            rapportageDao.voegStartProcesInstantieToe(
                procesInstantie.getProcessDefinition().getName(),
                berichtMetaData.getBerichtType(),
                berichtMetaData.getKanaal(),
                procesInstantie.getId(),
                new Timestamp(procesInstantie.getStart().getTime()));
        }

        return null;
    }

    private BerichtMetaData getStartBerichtMetaData(final Map<String, Object> parameters) {

        // Omdat uc811 een proces kan starten ZONDER bericht, maar als output een Lq01 heeft, kan 'input' een
        // HashMap zijn i.p.v. een Long. Daarom wordt het zetten van berichtId uit elkaar getrokken.
        final Object parameter = parameters.get("input");
        Long berichtId = null;

        if (parameter instanceof Long) {
            berichtId = (Long) parameter;
        }

        if (berichtId == null) {
            // Dan is dit vast een foutmelding proces; probeer de volgende
            // parameters 'lo3Bericht', 'brpBericht', 'blokkeringBericht', 'overigBericht'.

            // Controleer op lo3Bericht.
            berichtId = (Long) parameters.get("lo3Bericht");

            if (berichtId == null) {
                // Geen lo3Bericht, controleer op brpBericht.
                berichtId = (Long) parameters.get("brpBericht");
            }

            if (berichtId == null) {
                // Geen brpBerichtBericht, controleer op blokkeringBericht.
                berichtId = (Long) parameters.get("blokkeringBericht");
            }

            if (berichtId == null) {
                // Geen blokkeringBericht, controleer op overigBericht.
                berichtId = (Long) parameters.get("overigBericht");
            }
        }

        if (berichtId != null) {
            return berichtenDao.leesBerichtMetaData(berichtId);
        } else {
            // Indien berichtId nog steeds null is, dan is het proces door een beheerder gestart vanuit de console.
            final BerichtMetaData berichtMetaData = new BerichtMetaData();
            berichtMetaData.setBerichtType("BEHEERDER");
            berichtMetaData.setKanaal("CONSOLE");
            return berichtMetaData;
        }
    }

}
