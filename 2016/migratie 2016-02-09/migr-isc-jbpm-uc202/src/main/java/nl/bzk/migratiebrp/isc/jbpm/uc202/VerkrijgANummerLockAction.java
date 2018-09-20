/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.FoutUtil;
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
@Component("uc202VerkrijgANummerLockAction")
public final class VerkrijgANummerLockAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String GELOCKED = "3b. Fout: Lock niet verkregen (automatisch herhalen na pauze)";
    private static final String FOUT = "3c. Technische fout";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";

    @Inject
    private LockService lockingService;

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long berichtId = (Long) parameters.get("input");

        final Lg01Bericht lg01Bericht = (Lg01Bericht) berichtenDao.leesBericht(berichtId);
        final Set<Long> teLockenANummers = getTeLockenANummers(lg01Bericht);

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
            result.put(FOUTMELDING_VARIABELE, FoutUtil.beperkFoutmelding(e.getMessage()));
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);
        } catch (final LockedException e) {
            result.put(FOUTMELDING_VARIABELE, FoutUtil.beperkFoutmelding(e.getMessage()));
            result.put(SpringActionHandler.TRANSITION_RESULT, GELOCKED);
        }

        LOG.info("result: {}", result);
        return result;
    }

    private Set<Long> getTeLockenANummers(final Lg01Bericht lg01Bericht) {
        final Set<Long> teLockenANummers = new HashSet<>();

        for (final Lo3CategorieWaarde huidigeCategorie : lg01Bericht.getCategorieen()) {
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
