/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.levering.lo3.util.PersoonUtil;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.springframework.stereotype.Component;

/**
 * Utility class om te werken met ResyncGroep-en.
 */
@Component("lo3_resyncBerichtFilter")
public final class ResyncBerichtFilter implements Filter {

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private VulBerichtFilter vulBerichtFilter;

    @Override
    public List<Lo3CategorieWaarde> filter(
        final PersoonHisVolledig persoon,
        final List<Stapel> istStapels,
        final AdministratieveHandelingModel administratieveHandeling,
        final List<Lo3CategorieWaarde> categorieen,
        final List<String> lo3Filterrubrieken)
    {
        LOGGER.debug("Filter bericht op basis van abonnement filter rubrieken: {}", lo3Filterrubrieken);

        if (persoon != null && administratieveHandeling != null && administratieveHandeling.getID() != null) {
            // Bepalen welke rubrieken geraakt zijn bij deze administratieve handeling
            final List<String> resyncRubrieken = bepaalRubrieken(persoon, istStapels, administratieveHandeling);
            LOGGER.debug("Resync rubrieken: {}", resyncRubrieken);
            if (!bevatRubrieken(lo3Filterrubrieken, resyncRubrieken)) {
                LOGGER.debug("Bericht wordt volledig genegeerd op basis van resync rubrieken");
                // Geen vulbericht sturen. Geen resync rubrieken in filter rubrieken
                return new ArrayList<>();
            }
        }

        return vulBerichtFilter.filter(categorieen, lo3Filterrubrieken, true);
    }

    @Override
    public boolean leveringUitvoeren(final PersoonHisVolledig persoon, final List<Lo3CategorieWaarde> gefilterd) {
        return gefilterd != null && !gefilterd.isEmpty() && !PersoonUtil.isAfgevoerd(persoon);
    }

    /**
     * Bepaal de geraakte rubrieken voor een bepaalde administratieve handeling.
     *
     * @param persoon persoon
     * @param opgehaaldeIstStapels opgehaalde ist stapels
     * @param administratieveHandeling administratieve handeling.
     * @return lijst van ReysncGroep-en
     */
    public static List<String> bepaalRubrieken(
        final PersoonHisVolledig persoon,
        final List<Stapel> opgehaaldeIstStapels,
        final AdministratieveHandelingModel administratieveHandeling)
    {
        final RubriekenVisitor rubriekenVisitor = new RubriekenVisitor(administratieveHandeling);
        rubriekenVisitor.visit(persoon);
        rubriekenVisitor.visit(opgehaaldeIstStapels);

        return rubriekenVisitor.getRubrieken();
    }

    /**
     * Controleert of de gegeven filterRubrieken een rubriek bevat die wordt aangegeven door de gegevens
     * resyncRubrieken.
     *
     * @param filterRubrieken filter rubrieken (uit abonnement)
     * @param resyncRubrieken resync rubrieken (uit {@link #bepaalRubrieken})
     * @return true, als er een filterRubriek is die wordt aangegeven
     */
    public static boolean bevatRubrieken(final List<String> filterRubrieken, final List<String> resyncRubrieken) {
        boolean result = false;
        if (resyncRubrieken == null) {
            result = true;
        } else {
            rubriekenLoop:
            for (final String resyncRubriek : resyncRubrieken) {
                for (final String filterRubriek : filterRubrieken) {
                    if (filterRubriek.startsWith(resyncRubriek)) {
                        result = true;
                        break rubriekenLoop;
                    }
                }
            }
        }
        return result;
    }

}
