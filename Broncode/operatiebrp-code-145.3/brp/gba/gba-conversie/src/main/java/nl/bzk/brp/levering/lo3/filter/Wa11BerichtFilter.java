/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.springframework.stereotype.Component;

/**
 * Filter voor Wa11 berichten.
 * <p>
 * Een afnemer krijgt de gevens uit categorie 01, groep 02 en groep 03, mits daarvoor geautoriseerd.
 */
@Component("lo3_wa11BerichtFilter")
public final class Wa11BerichtFilter implements Filter {

    private final VulBerichtFilter vulBerichtFilter;

    /**
     * Constructor.
     * @param vulBerichtFilter vul bericht filter
     */
    @Inject
    public Wa11BerichtFilter(final VulBerichtFilter vulBerichtFilter) {
        this.vulBerichtFilter = vulBerichtFilter;
    }

    @Override
    public List<Lo3CategorieWaarde> filter(
            final Persoonslijst persoon,
            final List<Stapel> istStapels,
            final AdministratieveHandeling administratieveHandeling,
            final IdentificatienummerMutatie identificatienummerMutatieResultaat,
            final List<Lo3CategorieWaarde> categorieen,
            final List<String> lo3Filterrubrieken) {
        final List<String> wa11Filterrubrieken = aanpassenVoorWa11Filter(lo3Filterrubrieken);
        return vulBerichtFilter.filter(categorieen, wa11Filterrubrieken, true);
    }

    /**
     * Filter rubrieken aanpassen voor Wa11 bericht (enkel 01.02.xx en 01.03.xx behouden).
     * @param lo3Filterrubrieken alle filter rubrieken
     * @return wa11 filter rubrieken
     */
    private List<String> aanpassenVoorWa11Filter(final List<String> lo3Filterrubrieken) {
        final List<String> wa11Filterrubrieken = new ArrayList<>();

        for (final String lo3Filterrubriek : lo3Filterrubrieken) {
            if (lo3Filterrubriek.startsWith("01.02") || lo3Filterrubriek.startsWith("01.03")) {
                wa11Filterrubrieken.add(lo3Filterrubriek);
            }
        }
        return wa11Filterrubrieken;
    }

    /**
     * {@inheritDoc}
     * @return true, Wa11 moet altijd geleverd worden.
     */
    @Override
    public boolean leveringUitvoeren(final Persoonslijst persoon, final List<Lo3CategorieWaarde> gefilterd) {
        return true;
    }

}
