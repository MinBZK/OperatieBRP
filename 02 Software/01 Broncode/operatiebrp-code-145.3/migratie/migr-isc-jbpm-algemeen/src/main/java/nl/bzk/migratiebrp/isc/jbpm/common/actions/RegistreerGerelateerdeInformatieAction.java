/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.actions;

import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.gerelateerd.GerelateerdeInformatieDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.NoSignal;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * Registreer gerelateerde informatie bij een proces.
 *
 * Deze action is bedoeld als action die in een transition wordt aangeroepen (in andere woorden: roept geen SIGNAL aan
 * op het proces).
 */
@Component("registreerGerelateerdeInformatieAction")
public final class RegistreerGerelateerdeInformatieAction implements SpringAction, NoSignal {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    private final GerelateerdeInformatieDao gerelateerdeInformatieDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     * @param gerelateerdeInformatieDao gerelateerde informatie dao
     */
    @Inject
    public RegistreerGerelateerdeInformatieAction(BerichtenDao berichtenDao, GerelateerdeInformatieDao gerelateerdeInformatieDao) {
        this.berichtenDao = berichtenDao;
        this.gerelateerdeInformatieDao = gerelateerdeInformatieDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final String berichtIdVariabele = (String) parameters.get("berichtIdVariabele");
        if (berichtIdVariabele == null) {
            throw new IllegalArgumentException("berichtIdVariabele niet gezet!");
        }
        final Long berichtId = (Long) parameters.get(berichtIdVariabele);
        if (berichtId == null) {
            throw new IllegalArgumentException("Bericht id niet gevonden in variabele '" + berichtIdVariabele + "'");
        }

        final Bericht bericht = berichtenDao.leesBericht(berichtId);
        if (bericht == null) {
            throw new IllegalArgumentException("Bericht met id " + berichtId + " niet gevonden (variabele '" + berichtIdVariabele + "').");
        }

        final GerelateerdeInformatie gerelateerdeInformatie = bericht.getGerelateerdeInformatie();

        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        final ProcessInstance processInstance = executionContext.getProcessInstance();
        final Long processInstanceId = processInstance.getId();

        gerelateerdeInformatieDao.toevoegenGerelateerdeGegevens(processInstanceId, gerelateerdeInformatie);

        return null;
    }
}
