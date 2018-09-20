/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.locking.LockException;
import nl.bzk.migratiebrp.isc.jbpm.common.locking.LockService;
import nl.bzk.migratiebrp.isc.jbpm.common.locking.LockedException;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * Maak een lock bericht voor een verzameling A-nummers aan.
 */
@Component("uc811VerkrijgANummerLockAction")
public final class VerkrijgANummerLockAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String GELOCKED = "6b. Fout: Lock niet verkregen (automatisch herhalen na pauze)";
    private static final String FOUT = "6c. Technische fout";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";

    @Inject
    private LockService lockingService;

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Long la01BerichtId = (Long) parameters.get("la01Bericht");
        final La01Bericht la01Bericht = (La01Bericht) berichtenDao.leesBericht(la01BerichtId);
        final Set<Long> teLockenANummers = getTeLockenANummers(la01Bericht);

        final Map<String, Object> result = new HashMap<>();

        try {

            final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
            final ProcessInstance processInstance = executionContext.getProcessInstance();
            final Long processInstanceId = processInstance.getId();

            final Long lockId = lockingService.verkrijgLockVoorAnummers(teLockenANummers, processInstanceId);
            if (lockId == null) {
                result.put(SpringActionHandler.TRANSITION_RESULT, GELOCKED);
            }

            result.put("lockId", lockId);
        } catch (final LockException e) {
            result.put(FOUTMELDING_VARIABELE, e.getMessage());
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);
        } catch (final LockedException e) {
            result.put(FOUTMELDING_VARIABELE, e.getMessage());
            result.put(SpringActionHandler.TRANSITION_RESULT, GELOCKED);
        }

        LOG.info("result: {}", result);
        return result;
    }

    private Set<Long> getTeLockenANummers(final La01Bericht la01Bericht) {
        final Set<Long> teLockenANummers = new HashSet<>();
        final List<Lo3CategorieWaarde> categorieen = la01Bericht.getCategorieen();
        for (final Lo3CategorieWaarde huidigeCategorie : categorieen) {
            if (isPersoonsCategorie(huidigeCategorie) && isJuisteCategorie(huidigeCategorie)) {
                teLockenANummers.add(VerkrijgANummerLockAction.asLong(huidigeCategorie.getElement(Lo3ElementEnum.ELEMENT_0110)));

                final String vorigAnummer = huidigeCategorie.getElement(Lo3ElementEnum.ELEMENT_2010);
                if (vorigAnummer != null) {
                    teLockenANummers.add(VerkrijgANummerLockAction.asLong(vorigAnummer));
                }
            }
        }

        return teLockenANummers;
    }

    private boolean isJuisteCategorie(final Lo3CategorieWaarde huidigeCategorie) {
        final String indicatieOnjuist = huidigeCategorie.getElement(Lo3ElementEnum.ELEMENT_8410);
        return indicatieOnjuist == null || "".equals(indicatieOnjuist);
    }

    private boolean isPersoonsCategorie(final Lo3CategorieWaarde huidigeCategorie) {
        return Lo3CategorieEnum.CATEGORIE_01.equals(huidigeCategorie.getCategorie())
               || Lo3CategorieEnum.CATEGORIE_51.equals(huidigeCategorie.getCategorie());
    }

    private static Long asLong(final String value) {
        if (value == null || "".equals(value)) {
            return null;
        } else {
            return Long.parseLong(value);
        }
    }
}
