/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Voegt extra documentatie toe aan sommige categorieen (ouders en huwelijk).
 */
@Component
public class Lo3ToevoegenExtraDocumentatie {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final Comparator<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> HUWELIJK_COMPARATOR = new HuwelijkComparator();

    /**
     * Voeg extra documentatie toe.
     * 
     * @param persoonslijst
     *            persoonlijst
     * @return persoonslijst
     */
    public final Lo3Persoonslijst converteer(final Lo3Persoonslijst persoonslijst) {
        LOG.debug("converteer(pl={})", persoonslijst);
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(persoonslijst);

        // Ouder 1
        builder.ouder1Stapels(Collections.<Lo3Stapel<Lo3OuderInhoud>>emptyList());
        for (final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel : persoonslijst.getOuder1Stapels()) {
            builder.ouder1Stapel(voegDocumentatieToe(ouder1Stapel, Lo3Documentatie.ExtraDocument.OUDER, 1));
        }

        // Ouder 2
        builder.ouder2Stapels(Collections.<Lo3Stapel<Lo3OuderInhoud>>emptyList());
        for (final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel : persoonslijst.getOuder2Stapels()) {
            builder.ouder2Stapel(voegDocumentatieToe(ouder2Stapel, Lo3Documentatie.ExtraDocument.OUDER, 2));
        }

        // Huwelijk
        builder.huwelijkOfGpStapels(Collections.<Lo3Stapel<Lo3HuwelijkOfGpInhoud>>emptyList());
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> verbintenissen = persoonslijst.getHuwelijkOfGpStapels();
        Collections.sort(verbintenissen, HUWELIJK_COMPARATOR);
        for (int index = 0; index < verbintenissen.size(); index++) {
            builder.huwelijkOfGpStapel(voegDocumentatieToe(verbintenissen.get(index),
                    Lo3Documentatie.ExtraDocument.HUWELIJK, index + 1));
        }

        return builder.build();
    }

    private static <T extends Lo3CategorieInhoud> Lo3Stapel<T> voegDocumentatieToe(
            final Lo3Stapel<T> stapel,
            final Lo3Documentatie.ExtraDocument extraDocument,
            final Integer extraDocumentInformatie) {
        final List<Lo3Categorie<T>> categorieen = new ArrayList<Lo3Categorie<T>>();

        for (final Lo3Categorie<T> categorie : stapel) {
            categorieen.add(new Lo3Categorie<T>(categorie.getInhoud(), categorie.getDocumentatie()
                    .extraDocumentToevoegen(extraDocument, extraDocumentInformatie), categorie.getHistorie(),
                    categorie.getLo3Herkomst()));
        }

        return new Lo3Stapel<T>(categorieen);
    }

    private static Lo3Datum getOudsteDatumOpneming(final Lo3Stapel<?> stapel) {
        Lo3Datum result = null;

        for (final Lo3Categorie<?> categorie : stapel) {
            final Lo3Datum datum = categorie.getHistorie().getDatumVanOpneming();
            if (result == null || datum.compareTo(result) < 0) {
                result = datum;
            }
        }

        return result;
    }

    /**
     * Volgorde bepalen voor huwelijk stapels.
     */
    private static final class HuwelijkComparator implements Comparator<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> {

        @Override
        public int compare(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> arg0, final Lo3Stapel<Lo3HuwelijkOfGpInhoud> arg1) {
            return getOudsteDatumOpneming(arg0).compareTo(getOudsteDatumOpneming(arg1));
        }

    }
}
