/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenAfnemersindicatie;

/**
 * Inhoudelijke conversie van afnemersindicaties.
 */
public final class AfnemersindicatieConverteerder {

    /**
     * Converteer.
     * @param afnemersindicatieStapels LO3 afnemersindicatie stapel
     * @return Migratie afnemersindicatie stapel
     */
    public List<TussenAfnemersindicatie> converteer(final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> afnemersindicatieStapels) {
        if (afnemersindicatieStapels == null) {
            return Collections.emptyList();
        }
        final List<TussenAfnemersindicatie> result = new ArrayList<>();
        for (final Lo3Stapel<Lo3AfnemersindicatieInhoud> afnemersindicatieStapel : afnemersindicatieStapels) {
            result.add(converteerStapel(afnemersindicatieStapel));
        }
        return result;
    }

    private TussenAfnemersindicatie converteerStapel(final Lo3Stapel<Lo3AfnemersindicatieInhoud> stapel) {
        final String afnemersIndicatie = getAfnemersindicatie(stapel);
        final BrpPartijCode partijCode = afnemersIndicatie == null ? null : new BrpPartijCode(afnemersIndicatie);
        final List<TussenGroep<BrpAfnemersindicatieInhoud>> groepen = new ArrayList<>();

        for (final Lo3Categorie<Lo3AfnemersindicatieInhoud> categorie : stapel) {
            // Datum onbekend lijkt problemen te geven bij de protocollering van een leveringsbericht.
            final BrpAfnemersindicatieInhoud inhoud = new BrpAfnemersindicatieInhoud(null, null, categorie.getInhoud().isLeeg());

            groepen.add(new TussenGroep<>(inhoud, categorie.getHistorie(), categorie.getDocumentatie(), categorie.getLo3Herkomst()));
        }

        return new TussenAfnemersindicatie(partijCode, new TussenStapel<>(groepen));
    }

    private String getAfnemersindicatie(final Lo3Stapel<Lo3AfnemersindicatieInhoud> stapel) {
        for (final Lo3Categorie<Lo3AfnemersindicatieInhoud> categorie : stapel) {
            final String afnemersIndicatie = categorie.getInhoud().getAfnemersindicatie();
            if (afnemersIndicatie != null) {
                return afnemersIndicatie;
            }
        }

        return null;
    }
}
