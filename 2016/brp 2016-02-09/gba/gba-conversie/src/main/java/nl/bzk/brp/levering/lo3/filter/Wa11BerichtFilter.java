/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.springframework.stereotype.Component;

/**
 * Filter voor Wa11 berichten.
 *
 * Een afnemer krijgt de gevens uit categorie 01, groep 02 en groep 03, mits daarvoor geautoriseerd.
 */
@Component("lo3_wa11BerichtFilter")
public final class Wa11BerichtFilter implements Filter {

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
        final List<String> wa11Filterrubrieken = aanpassenVoorWa11Filter(lo3Filterrubrieken);
        return vulBerichtFilter.filter(categorieen, wa11Filterrubrieken, true);
    }

    /**
     * Filter rubrieken aanpassen voor Wa11 bericht (enkel 01.02.xx en 01.03.xx behouden).
     *
     * @param lo3Filterrubrieken alle filter rubrieken
     * @return wa11 filter rubrieken
     */
    private List<String> aanpassenVoorWa11Filter(final List<String> lo3Filterrubrieken) {
        final List<String> wa11Filterrubrieken = new ArrayList<String>();

        for (final String lo3Filterrubriek : lo3Filterrubrieken) {
            if (lo3Filterrubriek.startsWith("01.02") || lo3Filterrubriek.startsWith("01.03")) {
                wa11Filterrubrieken.add(lo3Filterrubriek);
            }
        }
        return wa11Filterrubrieken;
    }

    /**
     * {@inheritDoc}
     *
     * @return true, Wa11 moet altijd geleverd worden.
     */
    @Override
    public boolean leveringUitvoeren(final PersoonHisVolledig persoon, final List<Lo3CategorieWaarde> gefilterd) {
        return true;
    }

}
