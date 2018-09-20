/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;

import org.springframework.stereotype.Component;

/**
 * Deze class bevat de logica om een LO3 gezagsverhouding te converteren naar BRP. Deze converteerder converteert de
 * 'helft' van de gegevens, te weten de indicatoren.
 * 
 * 
 * 
 */
@Component
@Requirement(Requirements.CCA11)
public class GezagsverhoudingConverteerder {

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Converteert de LO3 gezagsverhouding stapel naar de corresponderende BRP groepen en vult hiermee de migratie
     * builder aan.
     * 
     * @param gezagsverhoudingStapel
     *            de gezagsverhoudingstapel
     * @param migratiePersoonslijstBuilder
     *            de migratie persoonslijst builder
     */
    public final void converteer(
            final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel,
            final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder) {
        if (gezagsverhoudingStapel == null) {
            return;
        }
        if (migratiePersoonslijstBuilder == null) {
            throw new NullPointerException(
                    "migratiePersoonslijstBuilder mag niet null zijn voor GezagsverhoudingConverteerder.converteer");
        }

        final List<MigratieGroep<BrpDerdeHeeftGezagIndicatieInhoud>> migratieDerdeHeeftGezagIndicatieList =
                new ArrayList<MigratieGroep<BrpDerdeHeeftGezagIndicatieInhoud>>();

        final List<MigratieGroep<BrpOnderCurateleIndicatieInhoud>> migratieOnderCurateleIndicatieList =
                new ArrayList<MigratieGroep<BrpOnderCurateleIndicatieInhoud>>();

        for (final Lo3Categorie<Lo3GezagsverhoudingInhoud> lo3GezagsverhoudingCategorie : gezagsverhoudingStapel) {
            final Lo3GezagsverhoudingInhoud lo3Gezagsverhouding = lo3GezagsverhoudingCategorie.getInhoud();
            final Boolean derdeHeeftGezag =
                    converteerder.converteerDerdeHeeftGezag(lo3Gezagsverhouding.getIndicatieGezagMinderjarige());
            final Boolean onderCuratele =
                    converteerder.converteerOnderCuratele(lo3Gezagsverhouding.getIndicatieCurateleregister());

            migratieDerdeHeeftGezagIndicatieList.add(new MigratieGroep<BrpDerdeHeeftGezagIndicatieInhoud>(
                    new BrpDerdeHeeftGezagIndicatieInhoud(derdeHeeftGezag), lo3GezagsverhoudingCategorie
                            .getHistorie(), lo3GezagsverhoudingCategorie.getDocumentatie(),
                    lo3GezagsverhoudingCategorie.getLo3Herkomst()));
            migratieOnderCurateleIndicatieList.add(new MigratieGroep<BrpOnderCurateleIndicatieInhoud>(
                    new BrpOnderCurateleIndicatieInhoud(onderCuratele), lo3GezagsverhoudingCategorie.getHistorie(),
                    lo3GezagsverhoudingCategorie.getDocumentatie(), lo3GezagsverhoudingCategorie.getLo3Herkomst()));
        }

        if (!migratieDerdeHeeftGezagIndicatieList.isEmpty()) {
            final MigratieStapel<BrpDerdeHeeftGezagIndicatieInhoud> stapel =
                    new MigratieStapel<BrpDerdeHeeftGezagIndicatieInhoud>(migratieDerdeHeeftGezagIndicatieList);
            migratiePersoonslijstBuilder.derdeHeeftGezagIndicatieStapel(stapel);
        }

        if (!migratieOnderCurateleIndicatieList.isEmpty()) {
            final MigratieStapel<BrpOnderCurateleIndicatieInhoud> stapel =
                    new MigratieStapel<BrpOnderCurateleIndicatieInhoud>(migratieOnderCurateleIndicatieList);
            migratiePersoonslijstBuilder.onderCurateleIndicatieStapel(stapel);
        }
    }

}
