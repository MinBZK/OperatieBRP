/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.AttributenFilterService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.basis.Attribuut;
import org.perf4j.aop.Profiled;


/**
 * Stap die er voor zorgt dat de magGeleverdWorden vlaggen gezet worden op de attributen. De bindings zorgen er uit- eindelijk voor dat alleen de elementen
 * die deze vlag op true hebben staan in de uitgaande xml terecht komen.
 * <p/>
 * Mooie situatie zou zijn dat de voerNabewerkingStap niet meer de vlaggen reset maar dat dit gebeurt in de voerStapUit methode zodat elke persoon altijd
 * de juiste begin situatie heeft. Echter dat betekent dat voor elke uitvoering de hele persoon view tree doorlopen dient te worden. Dit zal altijd meer
 * performance impact hebben dan alleen de aangepaste attributen resetten. Daarom wordt hier niet voor gekozen (zie TEAMBRP-2409)
 */
public class AttributenFilterStap extends AbstractAfnemerVerwerkingStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private AttributenFilterService attributenFilterService;

    /**
     * @param onderwerp Het onderwerp van deze stap, dit kan een bericht zijn of een Persoon of elk ander ObjectType.
     * @param context   De context waarbinnen de stap wordt uitgevoerd (bevat informatie en kan informatie meekrijgen).
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven aan de client.
     * @return
     * @brp.bedrijfsregel R2002
     */
    @Regels(Regel.R2002)
    @Override
    @Profiled(tag = "AttributenFilterStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp,
        final LeveringsautorisatieVerwerkingContext context, final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        final Dienst dienst = onderwerp.getLeveringinformatie().getDienst();
        LOGGER.info("Mag geleverd worden vlaggen worden gezet voor leveringsautorisatie met id {}.", dienst.getDienstbundel().getLeveringsautorisatie()
            .getID());

        final List<Attribuut> geraakteAttributen;
        try {
            geraakteAttributen =
                attributenFilterService.zetMagGeleverdWordenVlaggen(context.getBijgehoudenPersoonViews(), dienst,
                    onderwerp.getLeveringinformatie().getToegangLeveringsautorisatie().getGeautoriseerde().getRol(),
                    context.getPersoonAttributenMap());
            context.setAttributenDieGeleverdMogenWorden(geraakteAttributen);
            return DOORGAAN;
        } catch (final ExpressieExceptie expressieExceptie) {
            LOGGER.error(FunctioneleMelding.LEVERING_UITVOER_ATTRIBUTEN_FILTER_MISLUKT,
                "Er is een fout opgetreden tijdens de uitvoer van het attributenfilter. ", expressieExceptie);
            return STOPPEN;
        }
    }

    @Override
    public final void voerNabewerkingStapUit(final LeveringautorisatieStappenOnderwerp onderwerp,
        final LeveringsautorisatieVerwerkingContext context, final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        super.voerNabewerkingStapUit(onderwerp, context, resultaat);

        LOGGER.info("Attributen worden ge-reset voor leveringsautorisatienaam {} en ah {}", onderwerp.getLeveringinformatie()
                .getToegangLeveringsautorisatie().getLeveringsautorisatie().getID(),
            onderwerp.getAdministratieveHandelingId());

        if (context.getAttributenDieGeleverdMogenWorden() != null) {
            attributenFilterService.resetMagGeleverdWordenVlaggen(context.getAttributenDieGeleverdMogenWorden());
        } else {
            LOGGER.error("Geen attributen gevonden op de context, waardoor deze niet ge-reset konden worden. Dit kan "
                    + "alleen gebeuren als het zetten van de mag-geleverd-worden vlaggen mislukt is. Hier zou een "
                    + "aparte logregel van moeten zijn. Leveringsautorisatie id: {}" + " ah: {}", onderwerp.getLeveringinformatie()
                    .getToegangLeveringsautorisatie().getLeveringsautorisatie().getID(),
                onderwerp.getAdministratieveHandelingId());
        }
    }

}
