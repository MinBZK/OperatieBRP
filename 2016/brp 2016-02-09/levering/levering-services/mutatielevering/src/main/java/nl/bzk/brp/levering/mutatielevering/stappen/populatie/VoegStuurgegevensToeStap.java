/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import javax.inject.Inject;
import nl.bzk.brp.levering.business.bericht.BerichtFactory;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;


/**
 * Deze klasse creeert het uitgaande (max) bericht dat naar de afnemers gaat.
 */
public class VoegStuurgegevensToeStap extends AbstractAfnemerVerwerkingStap {

    /**
     * De Constante LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();
    @Inject
    private BerichtFactory berichtFactory;

    @Override
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp, final LeveringsautorisatieVerwerkingContext context,
        final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        voegStuurgegevensEnParametersToeAanLeveringBerichten(onderwerp.getLeveringinformatie(), context);

        LOGGER.debug("Stuurgegevens toegevoegd.");

        return DOORGAAN;
    }

    /**
     * Deze methode voegt de stuurgegevens toe aan het kennisgevingbericht. Wanneer al stuurgegevens aanwezig zijn vanuit een vorige iteratie, zullen deze
     * overschreven worden.
     *
     * @param context             De context.
     * @param leveringAutorisatie Het leveringAutorisatie.
     */
    private void voegStuurgegevensEnParametersToeAanLeveringBerichten(final Leveringinformatie leveringAutorisatie,
        final LeveringsautorisatieVerwerkingContext context)
    {
        for (final SynchronisatieBericht leveringBericht : context.getLeveringBerichten()) {
            voegStuurgegevensToeAanLeveringBericht(leveringAutorisatie, leveringBericht);
            voegParametersToeAanLeveringBericht(leveringAutorisatie, leveringBericht);
        }
    }

    /**
     * Voegt stuurgegevens toe aan levering bericht. Wanneer al stuurgegevens aanwezig zijn vanuit een vorige iteratie, zullen deze overschreven worden.
     *
     * @param leveringAutorisatie de leveringAutorisatie
     * @param leveringBericht     levering bericht
     */
    private void voegStuurgegevensToeAanLeveringBericht(final Leveringinformatie leveringAutorisatie,
        final SynchronisatieBericht leveringBericht)
    {
        final BerichtStuurgegevensGroepBericht stuurgegevens =
            maakBerichtStuurgegevensGroepBericht(leveringAutorisatie.getToegangLeveringsautorisatie().getGeautoriseerde().getPartij());

        MDC.put(MDCVeld.MDC_REFERENTIE_NUMMER, String.valueOf(stuurgegevens.getReferentienummer().getWaarde()));

        leveringBericht.setStuurgegevens(stuurgegevens);
    }

    /**
     * Voegt parameters toe aan levering bericht. Wanneer al parameters aanwezig zijn vanuit een vorige iteratie, zullen deze overschreven worden.
     *
     * @param leveringAutorisatie leveringAutorisatie
     * @param leveringBericht     levering bericht
     */
    private void voegParametersToeAanLeveringBericht(final Leveringinformatie leveringAutorisatie,
        final SynchronisatieBericht leveringBericht)
    {

        final BerichtParametersGroepBericht parametersGroepBericht =
            berichtFactory.maakParameters(leveringAutorisatie, leveringBericht.geefSoortSynchronisatie());

        leveringBericht.setBerichtParameters(parametersGroepBericht);
    }

    @Override
    public final void voerNabewerkingStapUit(final LeveringautorisatieStappenOnderwerp onderwerp,
        final LeveringsautorisatieVerwerkingContext context, final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        // reset stuurgegevens, zodat andere afnemers deze niet zien.
        for (final SynchronisatieBericht leveringBericht : context.getLeveringBerichten()) {
            leveringBericht.setStuurgegevens(null);
            leveringBericht.setBerichtParameters(null);
        }
    }

    /**
     * Maakt een BerichtStuurgegevensGroepModel aan voor een afnemer naam.
     *
     * @param partij De afnemer.
     * @return Het BerichtStuurgegevensGroepModel object.
     */
    private BerichtStuurgegevensGroepBericht maakBerichtStuurgegevensGroepBericht(final Partij partij) {
        return berichtFactory.maakStuurgegevens(partij);
    }

}
