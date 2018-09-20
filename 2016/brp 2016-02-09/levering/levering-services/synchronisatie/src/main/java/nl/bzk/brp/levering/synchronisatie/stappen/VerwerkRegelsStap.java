/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.business.regels.Bedrijfsregel;
import nl.bzk.brp.business.regels.BedrijfsregelManager;
import nl.bzk.brp.business.regels.BerichtBedrijfsregel;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.business.regels.context.AutorisatieRegelContext;
import nl.bzk.brp.business.regels.context.BerichtRegelContext;
import nl.bzk.brp.business.regels.context.HuidigeSituatieRegelContext;
import nl.bzk.brp.business.regels.context.RegelContext;
import nl.bzk.brp.business.regels.context.VerstrekkingsbeperkingRegelContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.levering.synchronisatie.regels.SynchronisatieBedrijfsregelManager;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.synchronisatie.AbstractSynchronisatieBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtValidatieStap;

/**
 * De stap waarin het synchronisatie verzoek gevalideerd wordt.
 */
public class VerwerkRegelsStap extends AbstractBerichtValidatieStap<BerichtBericht,
    AbstractSynchronisatieBericht, SynchronisatieBerichtContext, SynchronisatieResultaat>
{
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String REGELCONTEXT_NIET_ONDERSTEUND = "Dit type regelcontext wordt niet ondersteund: ";

    private static final String SOORT_BERICHT_NIET_ONDERSTEUND = "Soort bericht wordt niet ondersteund door synchronisatie service: ";

    @Inject
    private BedrijfsregelManager bedrijfsregelManager;

    @Override
    public final boolean voerStapUit(final AbstractSynchronisatieBericht onderwerp,
        final SynchronisatieBerichtContext context,
        final SynchronisatieResultaat resultaat)
    {
        controleerVoorBerichtRegels(onderwerp, context, resultaat);
        controleerVoorVerwerkingRegels(onderwerp, context, resultaat);

        LOGGER.debug("VerwerkRegelsStap.voerStapUit");

        return DOORGAAN;
    }

    @Override
    public final void voerNabewerkingStapUit(final AbstractSynchronisatieBericht onderwerp,
        final SynchronisatieBerichtContext context,
        final SynchronisatieResultaat resultaat)
    {
        controleerNaVerwerkingRegels(onderwerp, context, resultaat);

        LOGGER.debug("VerwerkRegelsStap.voerNabewerkingStapUit");
    }

    /**
     * Controleert de voor bericht regels.
     *
     * @param bericht   het bericht dat verwerkt wordt
     * @param context   de context
     * @param resultaat verwerking resultaat
     */
    private void controleerVoorBerichtRegels(final AbstractSynchronisatieBericht bericht,
        final SynchronisatieBerichtContext context,
        final SynchronisatieResultaat resultaat)
    {
        final SoortBericht soortBericht = bericht.getSoort().getWaarde();

        final List<RegelInterface> bedrijfsregels =
            getBedrijfsregelManagerImpl().getUitTeVoerenRegelsVoorBericht(soortBericht);

        controleerBedrijfsregels(bedrijfsregels, bericht, context, resultaat);
    }

    /**
     * Controleert alle bij de opgegeven acties behorende voor verwerking regels, waarbij eventuele meldingen worden toegevoegd aan de lijst van opgetreden
     * meldingen in het {@link nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat}.
     *
     * @param bericht   het bericht
     * @param context   de BijhoudingBerichtContext waarbinnen de verwerking plaatsvindt
     * @param resultaat het resultaat van de stappen verwerking
     */
    private void controleerVoorVerwerkingRegels(final AbstractSynchronisatieBericht bericht,
        final SynchronisatieBerichtContext context,
        final SynchronisatieResultaat resultaat)
    {
        final SoortBericht soortBericht = bericht.getSoort().getWaarde();

        // Verzamel alle regels die voor de uitvoer van dit groepje acties gedraaid moeten worden
        // en voer deze allemaal uit.
        final List<RegelInterface> bedrijfsregels =
            getBedrijfsregelManagerImpl().getUitTeVoerenRegelsVoorVerwerking(soortBericht);

        controleerBedrijfsregels(bedrijfsregels, bericht, context, resultaat);
    }

    /**
     * Controleert alle bij de opgegeven acties behorende na verwerking regels, waarbij eventuele meldingen worden toegevoegd aan de lijst van opgetreden
     * meldingen in het {@link nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat}.
     *
     * @param bericht   het bericht
     * @param context   de BijhoudingBerichtContext waarbinnen de verwerking plaatsvindt
     * @param resultaat het resultaat van de stappen verwerking
     */
    private void controleerNaVerwerkingRegels(final AbstractSynchronisatieBericht bericht,
        final SynchronisatieBerichtContext context,
        final SynchronisatieResultaat resultaat)
    {
        final SoortBericht soortBericht = bericht.getSoort().getWaarde();

        final List<RegelInterface> bedrijfsregels =
            getBedrijfsregelManagerImpl().getUitTeVoerenRegelsVoorBericht(soortBericht);

        controleerBedrijfsregels(bedrijfsregels, bericht, context, resultaat);
    }

    /**
     * Controleert de bedrijfsregels.
     *
     * @param bedrijfsregels de bedrijfsregels.
     * @param bericht        het bericht.
     * @param context        de context.
     * @param resultaat      het resultaat.
     */
    private void controleerBedrijfsregels(final List<? extends RegelInterface> bedrijfsregels,
        final AbstractSynchronisatieBericht bericht,
        final SynchronisatieBerichtContext context,
        final SynchronisatieResultaat resultaat)
    {
        final Map<RegelInterface, List<BerichtIdentificeerbaar>> falendeRegelsMetEntiteiten = new HashMap<>();
        final SoortAdministratieveHandeling soortAdministratieveHandeling =
            bepaalSoortAdministratieveHandelingVoorBericht(bericht);
        final BerichtIdentificeerbaar berichtIdentificeerbaar = bepaalBerichtIdentificeerbaarVoorBericht(bericht);

        for (final RegelInterface regel : bedrijfsregels) {
            if (regel instanceof Bedrijfsregel) {
                final Bedrijfsregel<RegelContext> bedrijfsregel = (Bedrijfsregel<RegelContext>) regel;
                final RegelContext regelContext = bouwRegelContextVoorBedrijfsregel(bedrijfsregel, context, bericht);

                if (regelContext != null && Bedrijfsregel.INVALIDE == bedrijfsregel.valideer(regelContext)) {
                    falendeRegelsMetEntiteiten.put(bedrijfsregel, null);
                }
            } else if (regel instanceof BerichtBedrijfsregel) {
                final BerichtBedrijfsregel berichtBedrijfsregel = (BerichtBedrijfsregel) regel;

                if (berichtBedrijfsregel.getContextType() == BerichtRegelContext.class) {
                    final BerichtRegelContext regelContext =
                        new BerichtRegelContext(berichtIdentificeerbaar, soortAdministratieveHandeling, bericht);
                    final List<BerichtIdentificeerbaar> objectenDieDeregelOvertreden =
                        berichtBedrijfsregel.valideer(regelContext);

                    if (!objectenDieDeregelOvertreden.isEmpty()) {
                        falendeRegelsMetEntiteiten.put(berichtBedrijfsregel, objectenDieDeregelOvertreden);
                    }
                } else {
                    LOGGER.error(REGELCONTEXT_NIET_ONDERSTEUND
                        + berichtBedrijfsregel.getContextType().getClass().getSimpleName());
                }
            } else {
                LOGGER.error(REGELCONTEXT_NIET_ONDERSTEUND + regel.getClass().getSimpleName());
            }
        }

        if (!falendeRegelsMetEntiteiten.isEmpty()) {
            final Map<AdministratieveHandelingBericht, Map<RegelInterface,
                List<BerichtIdentificeerbaar>>> falendeRegelsMap = new HashMap<>();
            falendeRegelsMap.put(bericht.getAdministratieveHandeling(), falendeRegelsMetEntiteiten);
            voegMeldingenToeAanResultaat(falendeRegelsMap, resultaat);
        }
    }

    /**
     * Bouw een RegelContext object voor een bedrijfsregel. Dit wordt gedaan op basis van het contexttype van de bedrijfsregel.
     *
     * @param bedrijfsregel de bedrijfsregel waarvoor een regel context gebouwd moet worden.
     * @param context       de bericht context
     * @param bericht       het bericht dat verwerkt wordt
     * @return Een implementatie van een RegelContext die nodig is om de bedrijfsregel uit te kunnen voeren.
     */
    private RegelContext bouwRegelContextVoorBedrijfsregel(final Bedrijfsregel bedrijfsregel,
        final SynchronisatieBerichtContext context,
        final AbstractSynchronisatieBericht bericht)
    {
        RegelContext regelContext = null;
        PersoonView persoonView = null;
        if (context.getPersoonHisVolledig() != null) {
            persoonView = new PersoonView(context.getPersoonHisVolledig());
        }
        final SoortAdministratieveHandeling soortAdministratieveHandeling =
            bepaalSoortAdministratieveHandelingVoorBericht(bericht);

        if (bedrijfsregel.getContextType() == AutorisatieRegelContext.class) {
            regelContext = new AutorisatieRegelContext(context.getLeveringinformatie().getToegangLeveringsautorisatie(),
                context.getRelevanteDienst(), persoonView, soortAdministratieveHandeling);
        } else if (bedrijfsregel.getContextType() == VerstrekkingsbeperkingRegelContext.class) {
            regelContext = new VerstrekkingsbeperkingRegelContext(persoonView, context.getPartij().getWaarde());
        } else if (bedrijfsregel.getContextType() == HuidigeSituatieRegelContext.class) {
            regelContext = new HuidigeSituatieRegelContext(persoonView);
        } else {
            LOGGER.error(REGELCONTEXT_NIET_ONDERSTEUND
                + bedrijfsregel.getContextType().getClass().getSimpleName());
        }
        return regelContext;
    }

    /**
     * Bepaalt voor een synchronisatie bericht de bijbehorende administratieve handeling, zodat deze kan meegegeven worden aan de VoorVerwerkingRegels.
     *
     * @param bericht het synchronisatie bericht
     * @return de soort administratieve handeling
     */
    private SoortAdministratieveHandeling bepaalSoortAdministratieveHandelingVoorBericht(
        final AbstractSynchronisatieBericht bericht)
    {
        if (SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON == bericht.getSoort().getWaarde()) {
            return SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON;
        } else if (SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN == bericht.getSoort().getWaarde()) {
            return SoortAdministratieveHandeling.SYNCHRONISATIE_STAMGEGEVEN;
        } else {
            throw new IllegalArgumentException(SOORT_BERICHT_NIET_ONDERSTEUND
                + bericht.getSoort().getWaarde());
        }
    }

    /**
     * Bepaalt voor een synchronisatie bericht de te identificeren groep voor de VoorVerwerkingRegels. Deze groep wordt vervolgens meegegeven aan de Regel
     * als parameter.
     *
     * @param bericht het synchronisatie bericht.
     * @return De te identificeren groep voor de VoorVerwerkingRegels.
     */
    private BerichtIdentificeerbaar bepaalBerichtIdentificeerbaarVoorBericht(
        final AbstractSynchronisatieBericht bericht)
    {
        if (SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON == bericht.getSoort().getWaarde()) {
            return bericht.getZoekcriteriaPersoon();
        } else if (SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN == bericht.getSoort().getWaarde()) {
            return bericht.getParameters();
        } else {
            throw new IllegalArgumentException(SOORT_BERICHT_NIET_ONDERSTEUND
                + bericht.getSoort().getWaarde());
        }
    }

    /**
     * Geeft de implementatie van de bedrijfsregel manager, zodat alle methoden van de manager voor dit project beschikbaar zijn.
     *
     * @return de bedrijfsregel manager implementatie
     */
    private SynchronisatieBedrijfsregelManager getBedrijfsregelManagerImpl() {
        return (SynchronisatieBedrijfsregelManager) bedrijfsregelManager;
    }
}
