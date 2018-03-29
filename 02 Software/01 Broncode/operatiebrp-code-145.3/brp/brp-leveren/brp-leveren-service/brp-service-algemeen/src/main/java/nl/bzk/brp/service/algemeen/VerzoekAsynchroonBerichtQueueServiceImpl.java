/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import com.google.common.collect.Lists;
import javax.inject.Inject;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.AfnemerBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import org.springframework.stereotype.Component;

/**
 * Zet afnemerberichten op de queue voor de diensten 'Synchroniseer Persoon' en 'Plaats Afnemerindicatie'.
 */
@Component
final class VerzoekAsynchroonBerichtQueueServiceImpl implements VerzoekAsynchroonBerichtQueueService {

    @Inject
    private PlaatsAfnemerBerichtService plaatsAfnemerBerichtService;

    @Inject
    private SynchronisatieBerichtGegevensFactory synchronisatieBerichtGegevensFactory;

    private VerzoekAsynchroonBerichtQueueServiceImpl() {

    }

    @Override
    public void plaatsQueueberichtVoorVerzoek(final VerwerkPersoonBericht bericht, final Autorisatiebundel autorisatiebundel,
                                              final Integer datumAanvangMaterielePeriode) throws StapException {
        final SynchronisatieBerichtGegevens
                synchronisatieBerichtGegevens =
                synchronisatieBerichtGegevensFactory.maak(bericht, autorisatiebundel, datumAanvangMaterielePeriode);
        plaatsAfnemerBerichtService.plaatsAfnemerberichten(
                Lists.newArrayList(new AfnemerBericht(synchronisatieBerichtGegevens, autorisatiebundel.getToegangLeveringsautorisatie())));
    }
}
