/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc501.brp;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.bericht.model.notificatie.impl.VrijBerichtNotificatieBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.isc.jbpm.uc501.VrijBerichtConstanten;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

/**
 * Maakt een Vb01 bericht aan.
 */
@Component("uc501MaakVb01BerichtAction")
public final class MaakVb01BerichtAction implements SpringAction {

    /**
     * Maximale berichtgrootte is 18000 bytes.
     */
    private static final int MAXIMALE_BERICHT_GROOTTE = 18_000;
    /**
     * Melding voor te grote berichten.
     */
    private static final String MELDING_BERICHT_TE_GROOT = "Het oorspronkelijke vrij bericht dat gestuurd werd is groter dan wat er in dit Vb01-bericht past. "
            + "neem contact op met de afzender.";
    private static final String TRANSITIE_FUNCTIONELE_FOUT = "3a. Technische fout";
    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten repo.
     */
    @Inject
    public MaakVb01BerichtAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final VrijBerichtNotificatieBericht input = haalBerichtOp(parameters);
        Map<String, Object> resultaat;
        try {
            final Vb01Bericht vb01Bericht = maakVb01Bericht(input);
            resultaat = slaVb01BerichtOp(vb01Bericht);
            LOG.debug("resultaat: {}", resultaat);
        } catch (BerichtInhoudException bie) {
            LOG.error("Bericht inhoud niet volledig: {}", ExceptionUtils.getStackTrace(bie));
            resultaat = new HashMap<>(1);
            resultaat.put(SpringActionHandler.TRANSITION_RESULT, TRANSITIE_FUNCTIONELE_FOUT);
        }
        return resultaat;
    }

    private VrijBerichtNotificatieBericht haalBerichtOp(final Map<String, Object> parameters) {
        final Long berichtId = (Long) parameters.get("input");
        return (VrijBerichtNotificatieBericht) berichtenDao.leesBericht(berichtId);
    }

    private Vb01Bericht maakVb01Bericht(final VrijBerichtNotificatieBericht input) throws BerichtInhoudException {
        final Vb01Bericht vb01Bericht = new Vb01Bericht(bepaalBerichtInhoud(input.getInhoud().getVrijBericht().getValue().getInhoud().getValue().getValue()));
        if (StringUtils.isEmpty(input.getOntvangendePartij()) || StringUtils.isEmpty(input.getVerzendendePartij())) {
            throw new BerichtInhoudException("Ontvangende of verzendende partij is niet gevuld.");
        }
        vb01Bericht.setDoelPartijCode(input.getOntvangerVrijBericht());
        vb01Bericht.setBronPartijCode(input.getZenderVrijBericht());
        return vb01Bericht;
    }

    private String bepaalBerichtInhoud(final String notificatieBerichtInhoud) {
        final StringBuilder berichtInhoud = new StringBuilder(notificatieBerichtInhoud);
        if (berichtInhoud.length() > MAXIMALE_BERICHT_GROOTTE) {
            berichtInhoud.insert(0, MELDING_BERICHT_TE_GROOT);
            return berichtInhoud.toString().substring(0, MAXIMALE_BERICHT_GROOTTE);
        }
        return berichtInhoud.toString();
    }

    private Map<String, Object> slaVb01BerichtOp(final Vb01Bericht vb01Bericht) {
        final Long vb01BerichtId = berichtenDao.bewaarBericht(vb01Bericht);
        final Map<String, Object> result = new HashMap<>();
        result.put(VrijBerichtConstanten.VB01_BERICHT, vb01BerichtId);
        return result;
    }

}
