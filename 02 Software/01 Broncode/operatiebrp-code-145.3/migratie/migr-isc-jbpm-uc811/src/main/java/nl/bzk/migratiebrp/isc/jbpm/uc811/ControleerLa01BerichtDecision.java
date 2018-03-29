/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lq01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringDecision;
import nl.bzk.migratiebrp.register.client.PartijService;
import org.springframework.stereotype.Component;

/**
 * Controleer het ontvangen La01 bericht.
 */
@Component("uc811ControleerLa01BerichtDecision")
public final class ControleerLa01BerichtDecision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String GELDIG = "La01";
    private static final String ONGELDIG = "3f. Fout";
    private static final String FOUT_MSG_ORIGINATOR_KOP = "Originator kop incorrect.";
    private static final String FOUT_MSG_ANUMMER = "Anummer incorrect";

    private final BerichtenDao berichtenDao;
    private final PartijService partijService;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     * @param partijService de partijService
     */
    @Inject
    public ControleerLa01BerichtDecision(final BerichtenDao berichtenDao, final PartijService partijService) {
        this.berichtenDao = berichtenDao;
        this.partijService = partijService;
    }

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        final Long lq01BerichtId = (Long) parameters.get("lq01Bericht");
        final Long la01BerichtId = (Long) parameters.get("la01Bericht");
        final Lq01Bericht lq01Bericht = (Lq01Bericht) berichtenDao.leesBericht(lq01BerichtId);
        final La01Bericht la01Bericht = (La01Bericht) berichtenDao.leesBericht(la01BerichtId);

        if (controleerLa01Bericht(la01Bericht, lq01Bericht == null ? null : lq01Bericht.getANummer())) {
            return GELDIG;
        } else {
            return ONGELDIG;
        }
    }

    private boolean controleerLa01Bericht(final La01Bericht la01Bericht, final String aNummerLq01Bericht) {

        if (la01Bericht == null || la01Bericht.getLo3Persoonslijst() == null) {
            return false;
        }

        if (aNummerLq01Bericht == null || "".equals(aNummerLq01Bericht)
                || !aNummerLq01Bericht.equals(la01Bericht.getLo3Persoonslijst().getActueelAdministratienummer())) {
            LOG.debug(FOUT_MSG_ANUMMER);
            return false;
        }

        final String originator = la01Bericht.getBronPartijCode();
        final Partij partijOriginator = partijService.geefRegister().zoekPartijOpPartijCode(originator);

        final List<Lo3CategorieWaarde> inhoud = la01Bericht.getCategorieen();
        final String gemeenteInhoud =
                Lo3CategorieWaardeUtil.getElementWaarde(inhoud, Lo3CategorieEnum.VERBLIJFPLAATS, 0, 0, Lo3ElementEnum.GEMEENTE_VAN_INSCHRIJVING);

        boolean result = true;

        if (partijOriginator == null || !partijOriginator.isBijhouder() || !partijOriginator.getGemeenteCode().equals(gemeenteInhoud)) {
            LOG.debug(FOUT_MSG_ORIGINATOR_KOP);
            result = false;
        }

        return result;
    }

}
