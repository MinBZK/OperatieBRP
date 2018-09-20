/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaatImpl;


/**
 * Stap waarin bedrijfsregels worden uitgevoerd die voor de verwerking van het bericht moeten worden gecontroleerd.
 */
public class VoorBerichtRegelsStap extends AbstractVerwerkingRegelsStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final boolean voerStapUit(final BevragingsBericht onderwerp, final BevragingBerichtContextBasis context,
            final BevragingResultaat resultaat)
    {
        controleerVoorBerichtRegels(onderwerp, context, resultaat);

        LOGGER.debug("VoorBerichtRegelsStap.voerStapUit");

        return DOORGAAN;
    }

    /**
     * Controleert alle regels voor deze fase, waarbij eventuele meldingen
     * worden toegevoegd aan de lijst van opgetreden meldingen in het
     * {@link nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat}.
     *
     * @param onderwerp het onderwerp
     * @param context de BijhoudingBerichtContext waarbinnen de verwerking plaatsvindt
     * @param resultaat het resultaat van de stappen verwerking
     */
    private void controleerVoorBerichtRegels(final BevragingsBericht onderwerp,
            final BevragingBerichtContextBasis context, final BerichtVerwerkingsResultaatImpl resultaat)
    {
        final SoortBericht soortBericht = onderwerp.getSoort().getWaarde();

        // Verzamel alle regels die voor de uitvoer van dit groepje acties gedraaid moeten worden
        // en voer deze allemaal uit.
        final List<RegelInterface> regels =
            this.getBedrijfsregelManagerImpl().getUitTeVoerenRegelsVoorBericht(soortBericht);

        final Map<BerichtIdentificeerbaar, Map<RegelInterface, List<BerichtIdentificeerbaar>>> falendeRegelsMetEntiteiten =
            new HashMap<>();

        for (final RegelInterface regel : regels) {
            verwerkRegel(onderwerp, context, regel, falendeRegelsMetEntiteiten);
        }

        voegMeldingenToeAanResultaat(falendeRegelsMetEntiteiten, resultaat);
    }
}
