/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import nl.bzk.migratiebrp.tools.proefsynchronisatie.domein.ProefSynchronisatieBericht;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.repository.ProefSynchronisatieRepository;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.BerichtVerwerker;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * ProefSynchronisatie bericht verwerker.
 */
public abstract class AbstractProefSynchronisatieBerichtVerwerker implements BerichtVerwerker<ProefSynchronisatieBericht> {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int VERSTUUR_LOG_INTERVAL = 500;

    private List<ProefSynchronisatieBericht> proefSynchronisatieBerichten;
    private final AtomicInteger verwerkTeller = new AtomicInteger(0);
    private final AtomicInteger verwerktTotaal = new AtomicInteger(0);
    private final ProefSynchronisatieRepository proefSynchronisatieRepository;
    private final ThreadPoolExecutor executor;

    /**
     * Constructor.
     *
     * @param executor
     *            {@link ThreadPoolExecutor}
     * @param proefSynchronisatieRepository
     *            De repository waaruit de proefsynchronisatie berichten moeten worden gelezen
     */
    public AbstractProefSynchronisatieBerichtVerwerker(final ThreadPoolExecutor executor, final ProefSynchronisatieRepository proefSynchronisatieRepository)
    {
        this.executor = executor;
        proefSynchronisatieBerichten = new ArrayList<>();
        this.proefSynchronisatieRepository = proefSynchronisatieRepository;
    }

    @Override
    public final void voegBerichtToe(final ProefSynchronisatieBericht proefSynchronisatieBericht) {
        proefSynchronisatieBerichten.add(proefSynchronisatieBericht);
    }

    @Override
    public final void verwerkBerichten() {
        final List<ProefSynchronisatieBericht> teVersturenBerichten = proefSynchronisatieBerichten;
        proefSynchronisatieBerichten = new ArrayList<>();

        if (!teVersturenBerichten.isEmpty()) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    final List<ProefSynchronisatieBericht> verstuurdeBerichten = verstuurProefSynchronisatieBerichten(teVersturenBerichten);
                    final int verwerktCount = verwerkTeller.addAndGet(verstuurdeBerichten.size());

                    if (verwerktCount >= VERSTUUR_LOG_INTERVAL) {
                        LOG.info("Verwerkte ProefSynchronisatieBerichten: {}", verwerktTotaal.addAndGet(VERSTUUR_LOG_INTERVAL));
                        verwerkTeller.addAndGet(-VERSTUUR_LOG_INTERVAL);
                    }
                    if (!verstuurdeBerichten.isEmpty()) {
                        LOG.info("Aantal verstuurde berichten: " + verstuurdeBerichten.size());
                        final List<Long> proefSynchronisatieBerichtIds = new ArrayList<>(teVersturenBerichten.size());
                        for (final ProefSynchronisatieBericht proefSynchronisatieBericht : verstuurdeBerichten) {
                            proefSynchronisatieBerichtIds.add(proefSynchronisatieBericht.getId());
                        }

                        if (!proefSynchronisatieRepository.updateProefSynchronisatieBerichtStatus(proefSynchronisatieBerichtIds)) {
                            LOG.warn("Niet alle berichten zijn gemarkeerd als verwerkt!");
                        }
                    } else {
                        LOG.warn("Geen verstuurde berichten!");
                    }
                }
            });
        }

    }

    /**
     * Geef de waarde van verwerk teller.
     *
     * @return aantal verwerkte berichten
     */
    @Override
    public final int getVerwerkTeller() {
        return verwerkTeller.intValue() + verwerktTotaal.intValue();
    }

    /**
     * Verstuur berichten.
     *
     * @param teVersturenBerichten
     *            te versturen berichten
     * @return verstuurde berichten
     */
    protected abstract List<ProefSynchronisatieBericht> verstuurProefSynchronisatieBerichten(final List<ProefSynchronisatieBericht> teVersturenBerichten);

}
