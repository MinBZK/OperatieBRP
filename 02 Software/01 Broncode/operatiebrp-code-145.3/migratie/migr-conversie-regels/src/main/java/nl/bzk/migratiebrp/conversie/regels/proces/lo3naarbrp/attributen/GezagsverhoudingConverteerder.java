/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;

/**
 * Deze class bevat de logica om een LO3 gezagsverhouding te converteren naar BRP. Deze converteerder converteert de
 * 'helft' van de gegevens, te weten de indicatoren.
 */
@Requirement(Requirements.CCA11)
public class GezagsverhoudingConverteerder {

    private final Lo3AttribuutConverteerder converteerder;

    /**
     * Constructor.
     * @param lo3AttribuutConverteerder Lo3AttribuutConverteerder
     */
    public GezagsverhoudingConverteerder(final Lo3AttribuutConverteerder lo3AttribuutConverteerder) {
        this.converteerder = lo3AttribuutConverteerder;
    }

    /**
     * Converteert de LO3 gezagsverhouding stapel naar de corresponderende BRP groepen en vult hiermee de migratie
     * builder aan.
     * @param gezagsverhoudingStapel de gezagsverhoudingstapel
     * @param tussenPersoonslijstBuilder de migratie persoonslijst builder
     */
    public final void converteer(final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel,
                                 final TussenPersoonslijstBuilder tussenPersoonslijstBuilder) {
        if (gezagsverhoudingStapel == null) {
            return;
        }
        if (tussenPersoonslijstBuilder == null) {
            throw new NullPointerException("tussenPersoonslijstBuilder mag niet null zijn voor GezagsverhoudingConverteerder.converteer");
        }

        converteerDerdeHeeftGezag(gezagsverhoudingStapel, tussenPersoonslijstBuilder);
        converteerOnderCuratele(gezagsverhoudingStapel, tussenPersoonslijstBuilder);
    }

    private void converteerDerdeHeeftGezag(
            final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel,
            final TussenPersoonslijstBuilder tussenPersoonslijstBuilder) {
        final List<TussenGroep<BrpDerdeHeeftGezagIndicatieInhoud>> migratieDerdeHeeftGezagIndicatieList = new ArrayList<>();

        for (final Lo3Categorie<Lo3GezagsverhoudingInhoud> lo3GezagsverhoudingCategorie : gezagsverhoudingStapel) {
            final Lo3GezagsverhoudingInhoud lo3Gezagsverhouding = lo3GezagsverhoudingCategorie.getInhoud();
            final BrpBoolean derdeHeeftGezag = converteerder.converteerDerdeHeeftGezag(lo3Gezagsverhouding.getIndicatieGezagMinderjarige());

            final BrpDerdeHeeftGezagIndicatieInhoud inhoud = new BrpDerdeHeeftGezagIndicatieInhoud(derdeHeeftGezag, null, null);

            migratieDerdeHeeftGezagIndicatieList.add(new TussenGroep<>(
                    inhoud,
                    lo3GezagsverhoudingCategorie.getHistorie(),
                    lo3GezagsverhoudingCategorie.getDocumentatie(),
                    lo3GezagsverhoudingCategorie.getLo3Herkomst()));
        }

        if (!migratieDerdeHeeftGezagIndicatieList.isEmpty()) {
            final TussenStapel<BrpDerdeHeeftGezagIndicatieInhoud> stapel = new TussenStapel<>(migratieDerdeHeeftGezagIndicatieList);
            tussenPersoonslijstBuilder.derdeHeeftGezagIndicatieStapel(stapel);
        }
    }

    private void converteerOnderCuratele(
            final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel,
            final TussenPersoonslijstBuilder tussenPersoonslijstBuilder) {
        final List<TussenGroep<BrpOnderCurateleIndicatieInhoud>> migratieOnderCurateleIndicatieList = new ArrayList<>();

        for (final Lo3Categorie<Lo3GezagsverhoudingInhoud> lo3GezagsverhoudingCategorie : gezagsverhoudingStapel) {
            final Lo3GezagsverhoudingInhoud lo3Gezagsverhouding = lo3GezagsverhoudingCategorie.getInhoud();
            final BrpBoolean onderCuratele = converteerder.converteerOnderCuratele(lo3Gezagsverhouding.getIndicatieCurateleregister());

            final BrpOnderCurateleIndicatieInhoud inhoud = new BrpOnderCurateleIndicatieInhoud(onderCuratele, null, null);

            migratieOnderCurateleIndicatieList.add(new TussenGroep<>(
                    inhoud,
                    lo3GezagsverhoudingCategorie.getHistorie(),
                    lo3GezagsverhoudingCategorie.getDocumentatie(),
                    lo3GezagsverhoudingCategorie.getLo3Herkomst()));
        }

        if (!migratieOnderCurateleIndicatieList.isEmpty()) {
            final TussenStapel<BrpOnderCurateleIndicatieInhoud> stapel = new TussenStapel<>(migratieOnderCurateleIndicatieList);
            tussenPersoonslijstBuilder.onderCurateleIndicatieStapel(stapel);
        }
    }
}
