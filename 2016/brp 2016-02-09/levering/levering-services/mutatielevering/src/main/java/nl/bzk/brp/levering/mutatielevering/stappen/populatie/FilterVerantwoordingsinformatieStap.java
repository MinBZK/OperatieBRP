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
import nl.bzk.brp.levering.business.toegang.gegevensfilter.MutatieLeveringVerantwoordingsinformatieFilter;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.VerantwoordingsinformatieFilter;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import org.perf4j.aop.Profiled;


/**
 * Stap die de consistentie in de verantwoordingsinformatie onderaan de persoonhisvolledigview filtert, zodat deze alleen de relevante
 * verantwoordingsinformatie toont.
 * <p/>
 * VR00086: In het resultaat van een levering mogen geen verantwoordingsgroepen ‘Actie’ worden opgenomen waarnaar geen enkele verwijzing bestaat vanuit een
 * inhoudelijke groep in hetzelfde resultaat. Dit betekent dat wanneer een Actie alleen groepen heeft geraakt die door autorisatie of een ander
 * filtermechanisme niet in het bericht komen, de Actie dus niets meer 'verantwoordt' en zelf ook uit het bericht verwijderd moet worden.
 * <p/>
 * VR00087: In het resultaat van een levering mogen geen verantwoordingsgroepen ‘Administratieve handeling’ en onderliggende groepen ‘Bron’ en ‘Document’
 * worden opgenomen als er binnen die Administratieve handeling geen enkele Actie voorkomt waarvoor een verwijzing bestaat vanuit een inhoudelijke groep
 * uit hetzelfde resultaat.
 */
@Regels({ Regel.VR00086, Regel.VR00087 })
public class FilterVerantwoordingsinformatieStap extends AbstractAfnemerVerwerkingStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VerantwoordingsinformatieFilter verantwoordingsinformatieFilter;

    @Inject
    private MutatieLeveringVerantwoordingsinformatieFilter mutatieLeveringVerantwoordingsinformatieFilter;

    @Override
    @Profiled(tag = "FilterVerantwoordingsinformatieStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp,
        final LeveringsautorisatieVerwerkingContext context, final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        for (final SynchronisatieBericht leveringBericht : context.getLeveringBerichten()) {
            final List<PersoonHisVolledigView> bijgehoudenPersonen =
                leveringBericht.getAdministratieveHandeling().getBijgehoudenPersonen();
            if (SoortSynchronisatie.MUTATIEBERICHT.equals(leveringBericht.geefSoortSynchronisatie())) {
                filterPersoonHisVolledigViewsVoorMutatieLevering(bijgehoudenPersonen,
                    leveringBericht.getAdministratieveHandeling(), onderwerp.getLeveringinformatie());
            } else if (SoortSynchronisatie.VOLLEDIGBERICHT.equals(leveringBericht.geefSoortSynchronisatie())) {
                filterPersoonHisVolledigViews(bijgehoudenPersonen, onderwerp.getLeveringinformatie());
            }
        }

        return DOORGAAN;
    }

    private void filterPersoonHisVolledigViewsVoorMutatieLevering(
        final List<PersoonHisVolledigView> bijgehoudenPersonen,
        final AdministratieveHandelingSynchronisatie administratieveHandeling, final Leveringinformatie leveringAutorisatie)
    {
        for (final PersoonHisVolledigView persoonHisVolledigView : bijgehoudenPersonen) {
            LOGGER.debug("Filteren van verantwoordingsinformatie voor persoon {} voor mutatie met handeling id: {}.",
                persoonHisVolledigView.getID(), administratieveHandeling.getID());
            this.mutatieLeveringVerantwoordingsinformatieFilter.filter(persoonHisVolledigView,
                administratieveHandeling.getID(), leveringAutorisatie);
        }
    }

    /**
     * Filtert een lijst met persoon his volledig views.
     *
     * @param persoonHisVolledigViews de persoon his volledig views
     */
    private void filterPersoonHisVolledigViews(final List<PersoonHisVolledigView> persoonHisVolledigViews, final Leveringinformatie leveringAutorisatie) {
        for (final PersoonHisVolledigView persoonHisVolledigView : persoonHisVolledigViews) {
            LOGGER.debug("Filteren van verantwoordingsinformatie voor persoon {}", persoonHisVolledigView.getID());
            this.verantwoordingsinformatieFilter.filter(persoonHisVolledigView, leveringAutorisatie);
        }
    }

}
