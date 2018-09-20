/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bijhouding;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManager;
import nl.bzk.brp.business.bedrijfsregels.BerichtContextBedrijfsRegel;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.stappen.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.model.validatie.Melding;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * In deze stap wordt de resultaat uit de context na het uit voeren van het bericht gevalideerd.
 *
 */
public class BerichtContextValidatieStap
        extends AbstractBerichtVerwerkingsStap<AbstractBijhoudingsBericht, BerichtVerwerkingsResultaat>
{

    private static final Logger  LOGGER = LoggerFactory.getLogger(BerichtContextValidatieStap.class);

    @Inject
    private BedrijfsRegelManager bedrijfsRegelManager;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht,
                                                     final BerichtContext context,
                                                     final BerichtVerwerkingsResultaat resultaat)
    {
        controleerBedrijfsregelsOpBerichtContext(bericht, context, resultaat);
        return DOORGAAN_MET_VERWERKING;
    }

    /**
     * Controleert of er bericht specifieke bedrijfsregels zijn en zo ja, controleert deze tegen het bericht. Eventuele
     * gevonden fouten/waarschuwingen worden aan het resultaat toegevoegd als meldingen.
     *
     * @param bericht het bericht waarover de context gecontrolleerd dient te worden.
     * @param context de bericht context dat gecontrolleerd dient te worden.
     * @param resultaat het resultaat van de bericht verwerking.
     */
    private void controleerBedrijfsregelsOpBerichtContext(final AbstractBijhoudingsBericht bericht,
            final BerichtContext context, final BerichtVerwerkingsResultaat resultaat)
    {
        if (bedrijfsRegelManager == null) {
            LOGGER.error("Geen bedrijfsregelmanager aanwezig; bericht wordt niet gevalideerd op bericht regels!");
        } else if (null != bedrijfsRegelManager.getUitTeVoerenBerichtContextBedrijfsRegels(bericht.getClass())) {
            List<? extends BerichtContextBedrijfsRegel> regels =  bedrijfsRegelManager
                    .getUitTeVoerenBerichtContextBedrijfsRegels(bericht.getClass());
            if (CollectionUtils.isNotEmpty(regels)) {
                for (BerichtContextBedrijfsRegel regel : regels) {
                    final List<Melding> meldingen = regel.executeer(context);
                    if (meldingen.size() > 0) {
                        resultaat.voegMeldingenToe(meldingen);
                    }
                }
            } else {
                LOGGER.info("Geen regels voor bericht " + bericht.getClass());
            }
        }
    }

    @Override
    public boolean voerStapUit(final AbstractBijhoudingsBericht onderwerp, final BerichtContext context,
                               final BerichtVerwerkingsResultaat resultaat)
    {
        return voerVerwerkingsStapUitVoorBericht(onderwerp, context,resultaat);
    }

}
