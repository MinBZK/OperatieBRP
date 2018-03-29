/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.dao.TaakGerelateerdeInformatieDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.uc808.RegistreerGerelateerdeAnummersTaakAction;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.job.SignalTokenJob;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.stereotype.Component;

/**
 * Automatisch verwerken gerelateerde taken
 */
@Component("uc202AutomatischVerwerkenGerelateerdeTakenAction")
public final class AutomatischVerwerkenGerelateerdeTakenAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;
    private final TaakGerelateerdeInformatieDao taakGerelateerdeInformatieDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     * @param taakGerelateerdeInformatieDao taak gerelateerde informatie dao
     */
    @Inject
    public AutomatischVerwerkenGerelateerdeTakenAction(final BerichtenDao berichtenDao, final TaakGerelateerdeInformatieDao taakGerelateerdeInformatieDao) {
        this.berichtenDao = berichtenDao;
        this.taakGerelateerdeInformatieDao = taakGerelateerdeInformatieDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long berichtId = (Long) parameters.get("synchroniseerNaarBrpVerzoekBericht");
        final SynchroniseerNaarBrpVerzoekBericht input = (SynchroniseerNaarBrpVerzoekBericht) berichtenDao.leesBericht(berichtId);

        final List<String> gerelateerdeAnummers = RegistreerGerelateerdeAnummersTaakAction.bepaalGerelateerdeAnummers(input.getLo3Persoonslijst());

        final Collection<TaskInstance> gerelateerdeTaken =
                taakGerelateerdeInformatieDao.zoekOpAdministratienummers(gerelateerdeAnummers.toArray(new String[]{}));

        for (final TaskInstance taskInstance : gerelateerdeTaken) {
            verwerkTaak(taskInstance);
        }

        return null;
    }

    private void verwerkTaak(final TaskInstance taskInstance) {
        if (taskInstance.hasEnded() || !taskInstance.isOpen() || taskInstance.isSuspended()) {
            return;
        }

        LOG.info("Automatisch verwerken taak: {} - {}", taskInstance.getId(), taskInstance);
        taskInstance.start("automatische verwerking");
        taskInstance.setVariable("restart", MaakBeheerderskeuzesAction.KEUZE_AUTOMATISCH_OPNIEUW);
        taskInstance.setSignalling(false);
        taskInstance.end();

        // Schedule async execution of token
        final JbpmContext jbpmContext = JbpmConfiguration.getInstance().getCurrentJbpmContext();
        jbpmContext.getServices().getMessageService().send(new SignalTokenJob(taskInstance.getToken()));

        LOG.info("Automatisch verwerken taak: {} - gereed", taskInstance.getId());
    }
}
