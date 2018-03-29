/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc812;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc811Bericht;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc812Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.NoSignal;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.springframework.stereotype.Component;

/**
 * Deze actionhandler bepaalt voor welke gemeenten een uc811 proces gestart moet worden en werkt als een Fork. De
 * corresponderende {@link ControleerGemeenteAction} controleert dat alle berichten verstuurd zijn en werkt als een
 * Join.
 */
@Component("uc812BepaalVoorkomensAction")
public final class BepaalVoorkomensAction implements SpringAction, NoSignal {

    /**
     * Lock voor het token als de 'fork' wordt gedaan.
     */
    public static final String LOCK = "uc812BepaalGemeentenAction";
    /**
     * Prefix voor de child tokens.
     */
    public static final String TOKEN_PREFIX = "gemeente-";

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String KOLOM_SPLITTER = ",";
    private static final String INPUT_PARAMETER_KEY = "input";
    private static final String NIEUWE_REGEL_MET_ENTER = "\r\n";
    private static final String NIEUWE_REGEL = "\n";

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public BepaalVoorkomensAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Uc812Bericht uc812Bericht = (Uc812Bericht) berichtenDao.leesBericht((Long) parameters.get(INPUT_PARAMETER_KEY));

        final Map<String, List<Long>> anummersPerGemeenteMap = bepaalAnummersPerGemeenteMapuitBulkBestand(uc812Bericht);

        // Lock parent token
        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        final Token token = executionContext.getToken();
        token.lock(LOCK);

        // Maak child tokens en schiet deze over de voorkomen transition
        for (final Map.Entry<String, List<Long>> entry : anummersPerGemeenteMap.entrySet()) {
            final String gemeente = entry.getKey();
            final List<Long> aNummers = entry.getValue();

            // Per gemeente zijn meerdere anummers mogelijk, maak voor elk anummer een apart childproces.
            for (final Long aNummer : aNummers) {

                final Token childToken = new Token(token, TOKEN_PREFIX + gemeente);
                final ExecutionContext childContext = new ExecutionContext(childToken);

                final Uc811Bericht uc811Bericht = new Uc811Bericht();
                uc811Bericht.setANummer(aNummer);
                uc811Bericht.setGemeenteCode(gemeente);
                final Long uc811BerichtId = berichtenDao.bewaarBericht(uc811Bericht);

                // Create variabele on token-scope
                childContext.getContextInstance().createVariable(INPUT_PARAMETER_KEY, uc811BerichtId, childToken);
                childContext.leaveNode("voorkomen");
            }
        }

        // Schiet de parent token over de controle transition
        executionContext.leaveNode("controle");
        return null;
    }

    private Map<String, List<Long>> bepaalAnummersPerGemeenteMapuitBulkBestand(final Uc812Bericht uc812Bericht) {

        final Map<String, List<Long>> aNummersPerGemeenteMap = new TreeMap<>();

        final String[] bulkBestandRegels = uc812Bericht.getBulkSynchronisatievraag().replaceAll(NIEUWE_REGEL_MET_ENTER, NIEUWE_REGEL).split(NIEUWE_REGEL);

        for (final String huidigeRegel : bulkBestandRegels) {
            final String[] huidigeRegelGesplitst = huidigeRegel.split(KOLOM_SPLITTER);
            final String gemeente = huidigeRegelGesplitst[0];
            final Long aNummer = Long.valueOf(huidigeRegelGesplitst[1]);
            if (aNummersPerGemeenteMap.containsKey(gemeente)) {
                aNummersPerGemeenteMap.get(gemeente).add(aNummer);
            } else {
                aNummersPerGemeenteMap.put(gemeente, new ArrayList<>(Arrays.asList(aNummer)));
            }
        }

        return aNummersPerGemeenteMap;
    }
}
