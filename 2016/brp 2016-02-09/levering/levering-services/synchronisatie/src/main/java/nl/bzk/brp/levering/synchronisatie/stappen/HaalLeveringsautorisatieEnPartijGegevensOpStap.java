/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen;

import javax.inject.Inject;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.levering.business.toegang.populatie.LeveringinformatieService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.synchronisatie.AbstractSynchronisatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtValidatieStap;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 * De stap waarin extra gegevens opgehaald worden.
 */
public class HaalLeveringsautorisatieEnPartijGegevensOpStap
    extends AbstractBerichtValidatieStap<BerichtBericht, AbstractSynchronisatieBericht,
    SynchronisatieBerichtContext, SynchronisatieResultaat>
{
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private LeveringinformatieService leveringinformatieService;

    @Inject
    private PartijService partijService;

    @Override
    public final boolean voerStapUit(final AbstractSynchronisatieBericht bericht,
        final SynchronisatieBerichtContext context,
        final SynchronisatieResultaat resultaat)
    {
        Boolean stapResultaat = DOORGAAN;
        final BerichtStuurgegevensGroepBericht stuurgegevens = bericht.getStuurgegevens();
        final Partij zendendePartij = bepaalPartij(stuurgegevens.getZendendePartijCode(), resultaat, stuurgegevens,
            "zendendePartij");

        if (zendendePartij != null) {
            bericht.getStuurgegevens().setZendendePartij(new PartijAttribuut(zendendePartij));
        } else {
            return STOPPEN;
        }

        final int leveringsautorisatieID = Integer.parseInt(bericht.getParameters().getLeveringsautorisatieID());
        final SoortDienst soortDienst = getSoortDienst(bericht);

        try {
            final Leveringinformatie leveringinformatie = leveringinformatieService
                .geefLeveringinformatie(leveringsautorisatieID,
                    zendendePartij.getCode().getWaarde(), soortDienst);
            context.setLeveringinformatie(leveringinformatie);

            if (leveringinformatie.getToegangLeveringsautorisatie() == null) {
                LOGGER.warn("Geen leveringautorisatie gevonden voor leveringsautorisatieIDs {}", leveringsautorisatieID);
                resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRLV0029));
                stapResultaat = STOPPEN;
            } else {
                final Leveringsautorisatie la = leveringinformatie.getToegangLeveringsautorisatie().getLeveringsautorisatie();
                context.setLeveringsautorisatie(la);
                context.setRelevanteDienst(leveringinformatie.getDienst());
                // TBV berichtarchivering wordt de gehele leveringsautorisatie aan het bericht meegegeven
                bericht.getParameters().setLeveringsautorisatie(new LeveringsautorisatieAttribuut(la));
            }
        } catch (final OnbekendeReferentieExceptie e) {
            // Als de leveringsautorisatie niet gevonden kan worden, heeft doorgaan geen nut.
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRLV0007));
            stapResultaat = STOPPEN;
        }

        LOGGER.debug("HaalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit");

        return stapResultaat;
    }

    /**
     * Kijkt aan de hand van het bericht, welke dienst er wordt gebruikt.
     *
     * @param bericht het bericht dat wordt verwerkt
     * @return de bijbehorende catalogus optie
     */
    private SoortDienst getSoortDienst(final AbstractSynchronisatieBericht bericht) {
        // bepaal voor welke dienst we leveringautorisatie ophalen.
        final SoortBericht soortBericht = bericht.getSoort().getWaarde();
        SoortDienst soortDienst = null;
        if (SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON == soortBericht) {
            soortDienst = SoortDienst.SYNCHRONISATIE_PERSOON;
        } else if (SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN == soortBericht) {
            soortDienst = SoortDienst.SYNCHRONISATIE_STAMGEGEVEN;
        } else {
            throw new IllegalStateException();
        }
        return soortDienst;
    }

    /**
     * Bepaalt de partij op basis van de partijcode.
     *
     * @param partijCodeAlsString     de partij code
     * @param resultaat               het resultaat van het stappenproces
     * @param berichtIdentificeerbaar het element uit het bericht
     * @param attribuutNaam           de naam van het attribuut uit het bericht
     * @return de partij of null als deze niet gevonden kan worden
     */
    private Partij bepaalPartij(final String partijCodeAlsString,
        final SynchronisatieResultaat resultaat,
        final BerichtIdentificeerbaar berichtIdentificeerbaar,
        final String attribuutNaam)
    {
        Partij partij = null;
        try {
            final Integer partijCode = Integer.parseInt(partijCodeAlsString);
            partij = partijService.vindPartijOpCode(partijCode);
        } catch (final NumberFormatException e) {
            // Als de partij niet vertaald kan worden naar een Integer, heeft doorgaan geen nut.
            LOGGER.error("De partijcode kon niet vertaald worden naar een Integer voor partijCode: {}.",
                partijCodeAlsString);
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRAL0220, berichtIdentificeerbaar,
                attribuutNaam));
        } catch (final EmptyResultDataAccessException e) {
            // Als de partij niet gevonden kan worden, heeft doorgaan geen nut.
            LOGGER.error("De partij kon niet gevonden worden voor partijCode: {}.", partijCodeAlsString);
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRAL0220, berichtIdentificeerbaar,
                attribuutNaam));
        }
        return partij;
    }

}
