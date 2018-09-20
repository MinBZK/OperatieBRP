/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc311;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.Stelsel;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.NoSignal;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.register.client.GemeenteService;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.springframework.stereotype.Component;

/**
 * Deze actionhandler bepaalt voor welke gemeenten een wa01 bericht verstuurd moet worden en werkt als een Fork. De
 * corresponderende {@link nl.bzk.migratiebrp.isc.jbpm.uc311.ControleerGemeentenAction} controleert dat alle berichten
 * verstuurd zijn en werkt als een Join.
 */
@Component("uc311BepaalGemeentenAction")
public final class BepaalGemeentenAction implements SpringAction, NoSignal {

    /** Child context variabele voor: doel gemeente. */
    public static final String DOEL_GEMEENTE = "doelGemeente";
    /** Child context variabele voor: wa01 bericht. */
    public static final String WA01_BERICHT = "wa01Bericht";
    /** Child context variabele voor: wa01 herhaling counter. */
    public static final String WA01_HERHALING = "wa01Herhaling";
    /** Child context variabele voor: vospg antwoord bericht. */
    public static final String VOSPG_BERICHT = "vospgBericht";
    /** Child context variabele voor: vospg antwoord bericht type. */
    public static final String VOSPG_BERICHT_TYPE = "vospgBerichtType";
    /** Child context variabele voor: null bericht. */
    public static final String NULL_BERICHT = "nullBericht";

    /** Lock voor het token als de 'fork' wordt gedaan. */
    public static final String LOCK = "uc311BepaalGemeentenAction";
    /** Prefix voor de child tokens. */
    public static final String TOKEN_PREFIX = "gemeente-";

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BerichtenDao berichtenDao;

    @Inject
    private GemeenteService gemeenteRegisterService;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final GemeenteRegister gemeenteRegister = gemeenteRegisterService.geefRegister();

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht;
        leesUitBrpAntwoordBericht = (LeesUitBrpAntwoordBericht) berichtenDao.leesBericht((Long) parameters.get("leesUitBrpAntwoordBericht"));
        final Lo3Persoonslijst persoonslijst = leesUitBrpAntwoordBericht.getLo3Persoonslijst();

        final Set<String> gemeenten = bepaalGemeenten(gemeenteRegister, persoonslijst);

        // Lock parent token
        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        final Token token = executionContext.getToken();
        token.lock(LOCK);

        // Maak child tokens en schiet deze over de gemeente transition
        for (final String gemeente : gemeenten) {
            final Token childToken = new Token(token, TOKEN_PREFIX + gemeente);
            final ExecutionContext childContext = new ExecutionContext(childToken);

            // Create variabele on token-scope
            childContext.getContextInstance().createVariable(DOEL_GEMEENTE, gemeente, childToken);
            childContext.getContextInstance().createVariable(WA01_BERICHT, null, childToken);
            childContext.getContextInstance().createVariable(WA01_HERHALING, null, childToken);
            childContext.getContextInstance().createVariable("wa01HerhalingTimeout", null, childToken);
            childContext.getContextInstance().createVariable("wa01DueDate", null, childToken);
            childContext.getContextInstance().createVariable("wa01HerhalingMaxHerhalingen", null, childToken);
            childContext.getContextInstance().createVariable(VOSPG_BERICHT, null, childToken);
            childContext.getContextInstance().createVariable(VOSPG_BERICHT_TYPE, null, childToken);
            childContext.getContextInstance().createVariable(NULL_BERICHT, null, childToken);
            childContext.leaveNode("gemeente");
        }

        // Schiet de parent token over de controle transition
        executionContext.leaveNode("controle");
        return null;
    }

    private Set<String> bepaalGemeenten(final GemeenteRegister gemeenteRegister, final Lo3Persoonslijst persoonslijst) {
        final Set<String> result = new TreeSet<>();
        final Lo3PersoonInhoud persoon = persoonslijst.getPersoonStapel().getLaatsteElement().getInhoud();

        if (Stelsel.GBA == gemeenteRegister.zoekGemeenteOpGemeenteCode(persoon.getGeboorteGemeenteCode().getWaarde()).getStelsel()) {
            result.add(persoon.getGeboorteGemeenteCode().getWaarde());
        }

        for (final Lo3Categorie<Lo3VerblijfplaatsInhoud> verblijfplaats : persoonslijst.getVerblijfplaatsStapel()) {
            if (!verblijfplaats.getHistorie().isOnjuist()
                && Stelsel.GBA == gemeenteRegister.zoekGemeenteOpGemeenteCode(verblijfplaats.getInhoud().getGemeenteInschrijving().getWaarde())
                                                  .getStelsel())
            {
                result.add(verblijfplaats.getInhoud().getGemeenteInschrijving().getWaarde());
            }
        }

        return result;
    }
}
