/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapels;
import org.springframework.stereotype.Component;

/**
 * Sorteer de LO3 stapels.
 */
@Component
public class BrpSorterenLo3 {

    /**
     * Sorteer de LO3 stapels.
     * 
     * @param persoonslijst
     *            persoonslijst
     * @return persoonslijst (met gesorteerde stapels)
     */
    public final Lo3Persoonslijst converteer(final Lo3Persoonslijst persoonslijst) {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(persoonslijst);

        builder.persoonStapel(Lo3Stapels.sorteerStapelLg01(persoonslijst.getPersoonStapel()));
        builder.ouder1Stapel(Lo3Stapels.sorteerStapelLg01(persoonslijst.getOuder1Stapel()));
        builder.ouder2Stapel(Lo3Stapels.sorteerStapelLg01(persoonslijst.getOuder2Stapel()));
        builder.nationaliteitStapels(Lo3Stapels.sorteerStapelsLg01(persoonslijst.getNationaliteitStapels()));
        builder.huwelijkOfGpStapels(Lo3Stapels.sorteerStapelsLg01(persoonslijst.getHuwelijkOfGpStapels()));
        builder.overlijdenStapel(Lo3Stapels.sorteerStapelLg01(persoonslijst.getOverlijdenStapel()));
        builder.inschrijvingStapel(Lo3Stapels.sorteerStapelLg01(persoonslijst.getInschrijvingStapel()));
        builder.verblijfplaatsStapel(Lo3Stapels.sorteerStapelLg01(persoonslijst.getVerblijfplaatsStapel()));
        builder.kindStapels(Lo3Stapels.sorteerStapelsLg01(persoonslijst.getKindStapels()));
        builder.verblijfstitelStapel(Lo3Stapels.sorteerStapelLg01(persoonslijst.getVerblijfstitelStapel()));
        builder.gezagsverhoudingStapel(Lo3Stapels.sorteerStapelLg01(persoonslijst.getGezagsverhoudingStapel()));
        builder.reisdocumentStapels(Lo3Stapels.sorteerStapelsLg01(persoonslijst.getReisdocumentStapels()));
        builder.kiesrechtStapel(Lo3Stapels.sorteerStapelLg01(persoonslijst.getKiesrechtStapel()));

        return builder.build();
    }
}
