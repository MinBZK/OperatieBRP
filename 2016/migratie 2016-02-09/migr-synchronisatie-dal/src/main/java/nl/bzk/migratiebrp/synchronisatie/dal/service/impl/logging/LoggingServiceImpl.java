/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.logging;

import java.util.Date;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.Lo3BerichtRepository;

/**
 * Implementatie voor de service die alles mbt logging afhandeld.
 */
public final class LoggingServiceImpl implements LoggingService {

    private final Lo3BerichtRepository berichtLogRepository;

    /**
     * Constructor waar een repository voor de berichten en logging wordt meegegeven.
     *
     * @param berichtLogRepository
     *            een repository voor berichten en logging
     */
    public LoggingServiceImpl(final Lo3BerichtRepository berichtLogRepository) {
        this.berichtLogRepository = berichtLogRepository;
    }

    @Override
    public Lo3Bericht persisteerLo3Bericht(final Lo3Bericht bericht) {
        if (bericht == null) {
            throw new NullPointerException("bericht mag niet null zijn");
        }
        return berichtLogRepository.save(bericht);
    }

    @Override
    public Lo3Bericht zoekLo3PersoonslijstBerichtOpAnummer(final long administratienummer) {
        return berichtLogRepository.findLaatsteLo3PersoonslijstBerichtVoorANummer(administratienummer);
    }

    @Override
    public Set<Long> zoekBerichtLogAnrs(final Date vanaf, final Date tot) {
        return berichtLogRepository.findLaatsteBerichtLogAnrs(vanaf, tot);
    }
}
