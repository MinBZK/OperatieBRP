/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command.impl;

import java.util.UUID;
import nl.bzk.migratiebrp.isc.jbpm.command.Command;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao.Direction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringService;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringServiceFactory;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.job.SignalTokenJob;

/**
 * Klasse voor JBPM Start commando's.
 */
public final class JbpmSynchronisatievraagCommand implements Command<Long> {

    private static final long serialVersionUID = 1L;

    private final String processDefinitionName;
    private final String inhoud;
    private final String doelGemeente;
    private final String naam;

    /**
     * Default constructor.
     *
     * @param processDefinitionNaam
     *            De naam van de proces definitie.
     * @param inhoud
     *            De inhoud van het bericht.
     * @param doelGemeente
     *            De doelgemeente.
     * @param naam
     *            De naam van het bericht.
     */
    public JbpmSynchronisatievraagCommand(final String processDefinitionNaam, final String inhoud, final String doelGemeente, final String naam) {
        processDefinitionName = processDefinitionNaam;
        this.inhoud = inhoud;
        this.doelGemeente = doelGemeente;
        this.naam = naam;
    }

    @Override
    public Long doInContext(final JbpmContext jbpmContext) {
        // Creer nieuwe process instantie
        final ProcessInstance processInstance = jbpmContext.newProcessInstance(processDefinitionName);
        jbpmContext.save(processInstance);
        final Long processInstanceId = processInstance.getId();

        // Sla bericht op
        final SpringService springService = (SpringService) jbpmContext.getServices().getService(SpringServiceFactory.SERVICE_NAME);
        final BerichtenDao berichtenDao = springService.getBean(BerichtenDao.class);
        final Long berichtId = berichtenDao.bewaar("ISC", Direction.INKOMEND, UUID.randomUUID().toString(), null, inhoud, null, doelGemeente, null);
        berichtenDao.updateNaam(berichtId, naam);
        berichtenDao.updateProcessInstance(berichtId, processInstanceId);

        // Zet het bericht als input voor de proces instantie
        processInstance.getContextInstance().setVariable("input", berichtId);
        jbpmContext.save(processInstance);

        // Start het proces (asynchroon)
        jbpmContext.getServices().getMessageService().send(new SignalTokenJob(processInstance.getRootToken()));

        // Gereed
        return processInstanceId;
    }
}
