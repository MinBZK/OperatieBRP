/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.actions;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import nl.bzk.migratiebrp.isc.jbpm.common.ProcessInstanceUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.rapportage.RapportageDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.foutafhandeling.FoutafhandelingConstants;
import nl.bzk.migratiebrp.isc.jbpm.foutafhandeling.FoutenDao;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Voegt de gestarte proces instantie toe aan de extratie tabel ten behoeve van rapportage.
 */
@Component("startWachtBeheerderKeuzeAction")
public final class StartWachtBeheerdersKeuzeAction implements SpringAction {

    @Autowired
    private RapportageDao rapportageDao;

    @Autowired
    private FoutenDao foutenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {

        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        if (executionContext == null) {
            throw new IllegalStateException(FoutafhandelingConstants.FOUTMELDING_GEEN_EXECUTION_CONTEXT);
        }

        final ProcessInstance procesInstantie = ProcessInstanceUtil.getRootProcessInstance(executionContext.getProcessInstance());
        if (procesInstantie == null) {
            throw new IllegalStateException(FoutafhandelingConstants.FOUTMELDING_GEEN_GESTARTE_PROCES_INSTANTIE);
        }

        final Long foutId = (Long) parameters.get(FoutafhandelingConstants.REGISTRATIE_ID);

        // De startdatum en de foutcode zetten (en einddatum leegmaken)
        rapportageDao.updateFoutreden(procesInstantie.getId(), new Timestamp(new Date().getTime()), foutenDao.haalFoutcodeOp(foutId));

        return null;
    }
}
