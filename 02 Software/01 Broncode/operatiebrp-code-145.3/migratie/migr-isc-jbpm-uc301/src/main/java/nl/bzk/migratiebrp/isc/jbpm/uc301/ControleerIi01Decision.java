/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ii01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.Stelsel;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringDecision;
import nl.bzk.migratiebrp.register.client.PartijService;
import org.springframework.stereotype.Component;

/**
 * Controleer het Ii01-bericht.
 */
@Component("uc301ControleerIi01Decision")
public final class ControleerIi01Decision implements SpringDecision {
    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String OK = null;
    private static final String FOUT_BIJHOUDER_NIET_CORRECT = "2b. fout (nieuwe bijhouder is BRP)";
    private static final String FOUT_VERVOLG_PAD_ZOEKCRITERIA_FOUT = "2c. fout (zoekcriteria voldoen niet aan eisen)";

    private final BerichtenDao berichtenDao;
    private final PartijService partijRegisterService;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     * @param partijRegisterService partij register service
     */
    @Inject
    public ControleerIi01Decision(final BerichtenDao berichtenDao, final PartijService partijRegisterService) {
        this.berichtenDao = berichtenDao;
        this.partijRegisterService = partijRegisterService;
    }

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Ii01Bericht ii01Bericht = (Ii01Bericht) berichtenDao.leesBericht((Long) parameters.get("input"));

        final PartijRegister partijRegister = partijRegisterService.geefRegister();
        final Partij bron = partijRegister.zoekPartijOpPartijCode(ii01Bericht.getBronPartijCode());
        final Partij doel = partijRegister.zoekPartijOpPartijCode(ii01Bericht.getDoelPartijCode());

        final String result;
        if (bron == null || !bron.isBijhouder() || bron.getStelsel() != Stelsel.GBA) {
            LOG.info("Bron gemeente valt niet in het GBA stelsel.");
            result = FOUT_BIJHOUDER_NIET_CORRECT;
        } else if (doel == null || !doel.isBijhouder() || doel.getStelsel() != Stelsel.BRP) {
            LOG.info("Doel gemeente valt niet in het BRP stelsel.");
            result = FOUT_BIJHOUDER_NIET_CORRECT;
        } else if (!zoekCriteriaOk(ii01Bericht)) {
            LOG.info("Zoek criteria niet geschikt.");
            result = FOUT_VERVOLG_PAD_ZOEKCRITERIA_FOUT;
        } else {
            result = OK;
        }

        return result;
    }

    private boolean zoekCriteriaOk(final Ii01Bericht ii01Bericht) {
        final String bsn = ii01Bericht.get(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER);

        final boolean result;

        if (bsn == null || "".equals(bsn)) {
            result = false;
        } else {
            result = true;
        }

        return result;
    }

}
