/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.springframework.stereotype.Component;

/**
 * Filter voor NG01 berichten.
 *
 * Iedere afnemer krijgt de rubrieken 01.10.10 (a-nummer), 07.67.10 (datum opschorting) en 07.67.20 (reden opschorting).
 */
@Component("lo3_ng01BerichtFilter")
public final class Ng01BerichtFilter implements Filter {

    private static final List<String> RUBRIEKEN = Arrays.asList("01.01.10", "07.67.10", "07.67.20");

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
        return vulBerichtFilter.filter(categorieen, RUBRIEKEN, true, false);
    }

    /**
     * {@inheritDoc}
     *
     * @return true, Ng01 moet altijd geleverd worden.
     */
    @Override
    public boolean leveringUitvoeren(final PersoonHisVolledig persoon, final List<Lo3CategorieWaarde> gefilterd) {
        return true;
    }
}
