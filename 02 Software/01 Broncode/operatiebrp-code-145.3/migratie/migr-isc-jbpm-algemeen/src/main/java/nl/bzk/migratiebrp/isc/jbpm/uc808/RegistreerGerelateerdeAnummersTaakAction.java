/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc808;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.dao.TaakGerelateerdeInformatieDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.NoSignal;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.jbpm.graph.exe.ExecutionContext;
import org.springframework.stereotype.Component;

/**
 * Registreer gerelateerde a-nummers bij taak.
 */
@Component("registreerGerelateerdeAnummersTaak")
public final class RegistreerGerelateerdeAnummersTaakAction implements SpringAction, NoSignal {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtDao;
    private final TaakGerelateerdeInformatieDao taakGerelateerdeInformatieDao;

    /**
     * Constructor.
     * @param berichtDao berichten dao
     * @param taakGerelateerdeInformatieDao taak gerelateerde informatie dao
     */
    @Inject
    public RegistreerGerelateerdeAnummersTaakAction(final BerichtenDao berichtDao, final TaakGerelateerdeInformatieDao taakGerelateerdeInformatieDao) {
        this.berichtDao = berichtDao;
        this.taakGerelateerdeInformatieDao = taakGerelateerdeInformatieDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        if (executionContext == null) {
            throw new IllegalStateException("RegistreerGerelateerdeAnummersTaakAction moet binnen een geldige execution context worden gebruikt.");
        }
        if (executionContext.getTaskInstance() == null) {
            throw new IllegalStateException("RegistreerGerelateerdeAnummersTaakAction moet binnen een taak worden gebruikt.");
        }
        if (executionContext.getTaskInstance().getProcessInstance().getSuperProcessToken() == null) {
            throw new IllegalStateException("RegistreerGerelateerdeAnummersTaakAction moet binnen een proces binnen een proces worden gebruikt.");
        }

        final Long syncVerzoekId =
                (Long) executionContext.getTaskInstance()
                        .getProcessInstance()
                        .getSuperProcessToken()
                        .getProcessInstance()
                        .getContextInstance()
                        .getVariable("synchroniseerNaarBrpVerzoekBericht");
        if (syncVerzoekId == null) {
            throw new IllegalStateException(
                    "RegistreerGerelateerdeAnummersTaakAction moet de beschikking hebben over een SynchroniseerNaarBrpVerzoek bericht.");
        }

        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoek = (SynchroniseerNaarBrpVerzoekBericht) berichtDao.leesBericht(syncVerzoekId);
        final List<String> gerelateerdeAnummers = bepaalGerelateerdeAnummers(synchroniseerNaarBrpVerzoek.getLo3Persoonslijst());
        taakGerelateerdeInformatieDao.registreerAdministratienummers(executionContext.getTaskInstance(), gerelateerdeAnummers.toArray(new String[]{}));

        return null;
    }

    /**
     * Bepaal gerelateerde a-nummers voor taken.
     * @param lo3Persoonslijst lo3 persoonslijst
     * @return lijst van a-nummers
     */
    public static List<String> bepaalGerelateerdeAnummers(final Lo3Persoonslijst lo3Persoonslijst) {
        final List<String> result = new ArrayList<>();
        lo3Persoonslijst.getPersoonStapel().forEach(categorie -> {
            final Lo3String anummer = categorie.getInhoud().getANummer();
            if (anummer != null) {
                result.add(anummer.getWaarde());
            }
        });

        return result;
    }
}
