/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sync;

import java.sql.Timestamp;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ib01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.La01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.test.isc.environment.id.IdGenerator;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Persoon service implementatie.
 */
public final class PersoonServiceImpl implements PersoonService {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BrpPersoonslijstService persoonslijstService;

    @Inject
    private ConverteerLo3NaarBrpService conversieService;

    @Inject
    private IdGenerator idGenerator;

    private final Lo3BerichtFactory berichtFactory = new Lo3BerichtFactory();

    @Override
    @Transactional(value = "syncDalTransactionManager", propagation = Propagation.REQUIRED)
    public void persisteerPersoon(final String inhoud) throws KanaalException {
        LOG.info("Verwerk bericht: {}", inhoud);
        final Lo3Bericht lo3Bericht = berichtFactory.getBericht(inhoud);
        lo3Bericht.setMessageId(idGenerator.generateId());

        LOG.info("Ophalen GBA persoonslijst");
        final Lo3Persoonslijst lo3Pl = getLo3Persoonslijst(lo3Bericht);
        LOG.info("Converteren GBA persoonslijst");
        Logging.initContext();
        SynchronisatieLogging.init();
        final BrpPersoonslijst brpPersoonslijst = conversieService.converteerLo3Persoonslijst(lo3Pl);
        LOG.info("Opslaan BRP persoonslijst");
        final nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht lo3BerichtEntity =
                new nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht(
                        lo3Bericht.getMessageId(),
                        Lo3BerichtenBron.INITIELE_VULLING,
                        new Timestamp(System.currentTimeMillis()),
                        inhoud,
                        true);
        persoonslijstService.persisteerPersoonslijst(brpPersoonslijst, lo3BerichtEntity);
        Logging.destroyContext();
        LOG.info("Bericht verwerkt");
    }

    private Lo3Persoonslijst getLo3Persoonslijst(final Lo3Bericht lo3Bericht) throws KanaalException {
        final Lo3Persoonslijst result;
        if (lo3Bericht instanceof Lg01Bericht) {
            result = ((Lg01Bericht) lo3Bericht).getLo3Persoonslijst();
        } else if (lo3Bericht instanceof La01Bericht) {
            result = ((La01Bericht) lo3Bericht).getLo3Persoonslijst();
        } else if (lo3Bericht instanceof Ib01Bericht) {
            result = ((Ib01Bericht) lo3Bericht).getLo3Persoonslijst();
        } else {
            throw new KanaalException("Berichttype '" + lo3Bericht.getBerichtType() + "' niet ondersteund in PersoonService.");
        }
        return result;
    }
}
