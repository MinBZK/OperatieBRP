/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManager;
import nl.bzk.brp.business.bedrijfsregels.BerichtBedrijfsRegel;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.validatie.Melding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * In deze stap wordt het bericht gevalideerd.
 */
public class BerichtValidatieStap extends AbstractBerichtVerwerkingsStap<BerichtBericht, BerichtVerwerkingsResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger(BerichtValidatieStap.class);

    @Inject
    private BedrijfsRegelManager bedrijfsRegelManager;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final BerichtBericht bericht,
            final BerichtContext context, final BerichtVerwerkingsResultaat resultaat)
    {
        return voerStapUit(bericht, context, resultaat);
    }

    @Override
    public boolean voerStapUit(final BerichtBericht bericht, final BerichtContext context,
       final BerichtVerwerkingsResultaat resultaat)
    {
        List<? extends BerichtBedrijfsRegel> bedrijfsregels = bedrijfsRegelManager
                .getUitTeVoerenBerichtBedrijfsRegels();

        if (bedrijfsRegelManager == null) {
            LOGGER.error("Geen bedrijfsregelmanager aanwezig; bericht wordt niet gevalideerd op bericht regels!");
        } else if (null != bedrijfsregels) {
            for (BerichtBedrijfsRegel regel : bedrijfsregels)
            {
                final List<Melding> meldingen = regel.executeer(bericht);
                if (meldingen.size() > 0) {
                    resultaat.voegMeldingenToe(meldingen);
                }
            }
        }

        return DOORGAAN;
    }
}
