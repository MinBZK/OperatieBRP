/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc311;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.NoSignal;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.isc.migratie.service.GemeenteService;
import nl.moderniseringgba.isc.migratie.service.Stelsel;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.springframework.stereotype.Component;

/**
 * Deze actionhandler bepaalt voor welke gemeenten een wa01 bericht verstuurd moet worden en werkt als een Fork. De
 * corresponderende {@link ControleerGemeenteAction} controleert dat alle berichten verstuurd zijn en werkt als een
 * Join.
 */
@Component("uc311BepaalGemeentenAction")
public final class BepaalGemeentenAction implements SpringAction, NoSignal {

    /** Lock voor het token als de 'fork' wordt gedaan. */
    public static final String LOCK = "uc311BepaalGemeentenAction";

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private GemeenteService gemeenteService;

    public void setGemeenteService(final GemeenteService gemeenteService) {
        this.gemeenteService = gemeenteService;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                parameters == null ? null : (LeesUitBrpAntwoordBericht) parameters.get("leesUitBrpAntwoordBericht");
        final Lo3Persoonslijst persoonslijst = leesUitBrpAntwoordBericht.getLo3Persoonslijst();

        final Set<String> gemeenten = bepaalGemeenten(persoonslijst);

        // Lock parent token
        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        final Token token = executionContext.getToken();
        token.lock(LOCK);

        // Maak child tokens en schiet deze over de gemeente transition
        for (final String gemeente : gemeenten) {
            final Token childToken = new Token(token, "gemeente-" + gemeente);
            final ExecutionContext childContext = new ExecutionContext(childToken);
            // Create variabele on token-scope
            childContext.getContextInstance().createVariable("doelGemeente", gemeente, childToken);
            childContext.getContextInstance().createVariable("wa01Bericht", null, childToken);
            childContext.getContextInstance().createVariable("wa01Herhaling", null, childToken);
            childContext.leaveNode("gemeente");
        }

        // Schiet de parent token over de controle transition
        executionContext.leaveNode("controle");
        return null;
    }

    private Set<String> bepaalGemeenten(final Lo3Persoonslijst persoonslijst) {
        final Set<String> result = new TreeSet<String>();
        final Lo3PersoonInhoud persoon = persoonslijst.getPersoonStapel().getMeestRecenteElement().getInhoud();

        if (isGbaGemeente(persoon.getGeboorteGemeenteCode())) {
            result.add(persoon.getGeboorteGemeenteCode().getCode());
        }

        for (final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats : persoonslijst.getVerblijfplaatsStapel()) {
            if (!verblijfplaats.getHistorie().isOnjuist()) {
                if (isGbaGemeente(verblijfplaats.getInhoud().getGemeenteInschrijving())) {
                    result.add(verblijfplaats.getInhoud().getGemeenteInschrijving().getCode());
                }
            }
        }

        return result;
    }

    private boolean isGbaGemeente(final Lo3GemeenteCode gemeente) {
        if (gemeente == null) {
            return false;
        }

        return Stelsel.GBA == gemeenteService.geefStelselVoorGemeente(Integer.valueOf(gemeente.getCode()));
    }
}
