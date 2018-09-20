/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.levering;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.business.stappen.bevraging.AbstractVerwerkingRegelsStap;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaatImpl;


/**
 * Stap waarin bedrijfsregels worden uitgevoerd die na de verwerking van het bericht moeten worden gecontrolleerd.
 */
public class NaVerwerkingRegelsStap extends AbstractVerwerkingRegelsStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public final boolean voerStapUit(final BevragingsBericht onderwerp, final BevragingBerichtContextBasis context,
            final BevragingResultaat resultaat)
    {
        controleerNaVerwerkingRegels(onderwerp, context, resultaat);

        // Controleer BRLV0032: Dit doen we hier apart omdat de regel alleen een waarschuwing mag toevoegen als er
        // verder geen andere fouten zijn opgetreden.
        voegMeldingenToeVoorVerstrekkingsBeperkingen(resultaat, context);

        LOGGER.debug("NaVerwerkingRegelsStap.voerStapUit");

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
    private void controleerNaVerwerkingRegels(final BevragingsBericht onderwerp,
            final BevragingBerichtContextBasis context, final BerichtVerwerkingsResultaatImpl resultaat)
    {
        final SoortBericht soortBericht = onderwerp.getSoort().getWaarde();

        // Verzamel alle regels die voor de uitvoer van dit groepje acties gedraaid moeten worden
        // en voer deze allemaal uit.
        final List<RegelInterface> regels =
            this.getBedrijfsregelManagerImpl().getUitTeVoerenRegelsNaVerwerking(soortBericht);

        final Map<BerichtIdentificeerbaar, Map<RegelInterface, List<BerichtIdentificeerbaar>>> falendeRegelsMetEntiteiten =
            new HashMap<>();

        for (final RegelInterface regel : regels) {
            verwerkRegel(onderwerp, context, regel, falendeRegelsMetEntiteiten);
        }

        voegMeldingenToeAanResultaat(falendeRegelsMetEntiteiten, resultaat);
    }

    /**
     * BRLV0032:
     * Indien de persoon op dit moment een volledige- of partiele verstrekkingsbeperking heeft, dan moet er een
     * waarschuwing worden getoond in de meldingen van het bericht. Maar niet als er andere fouten zijn opgetreden
     * en de persoon zoiezo niet in het resultaat zal zitten.
     *
     * @param resultaat resultaat van de bevraging
     * @param context de bericht context.
     */
    @Regels(Regel.BRLV0032)
    private void voegMeldingenToeVoorVerstrekkingsBeperkingen(final BevragingResultaat resultaat,
            final BevragingBerichtContextBasis context)
    {
        // Als er stoppende fouten zijn opgetreden, dan moet de waarschuwing niet worden getoond.
        // De persoon zal niet getoond worden, laat staan of deze uberhaupt verstrekkingsbeperkingen heeft.
        if (!resultaat.bevatStoppendeFouten()) {
            // Let op we moeten kijken of de persoon ACTUEEL verstrekkingsbeperkingen heeft, dus we kijken naar de
            // gevonden volledige persoon uit de context, en niet naar de view die in het resultaat zit.
            final PersoonHisVolledigImpl persoonHisVolledig = context.getPersoonHisVolledigImpl();
            final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl indicatieVolledigeVerstrekkingsbeperking =
                persoonHisVolledig.getIndicatieVolledigeVerstrekkingsbeperking();

            final boolean heeftVolledigeVerstrekkingsBeperking =
                indicatieVolledigeVerstrekkingsbeperking != null
                    && indicatieVolledigeVerstrekkingsbeperking.getPersoonIndicatieHistorie().getActueleRecord() != null;

            boolean heeftVerstrekkingsBeperking = false;
            final Set<PersoonVerstrekkingsbeperkingHisVolledigImpl> verstrekkingsbeperkingen =
                persoonHisVolledig.getVerstrekkingsbeperkingen();

            for (final PersoonVerstrekkingsbeperkingHisVolledigImpl verstrekkingsbeperking : verstrekkingsbeperkingen) {
                if (verstrekkingsbeperking.getPersoonVerstrekkingsbeperkingHistorie().getActueleRecord() != null) {
                    heeftVerstrekkingsBeperking = true;
                    break;
                }
            }

            if (heeftVolledigeVerstrekkingsBeperking || heeftVerstrekkingsBeperking) {
                final Melding melding = new Melding(SoortMelding.WAARSCHUWING, Regel.BRLV0032);
                resultaat.voegMeldingToe(melding);
            }
        }
    }
}
