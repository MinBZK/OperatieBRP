/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.util;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * Utility methodes gebruikt tijdens het verwerk proces van een PL.
 */
public final class VerwerkerUtil {
    private static final int HISTORISCH = 50;

    private VerwerkerUtil() {
    }

    /**
     * Voegt zeros(nullen) toe aan de voorkant van de String tot de opgegeven lengte.
     *
     * "4" wordt "004" als de aangegeven lengte 3 is.
     * @param string String
     * @param size int
     * @return aangevulde String
     */
    public static String zeroPad(final String string, final int size) {
        return StringUtils.leftPad(string, size, "0");
    }

    /**
     * Maakt de catNr historisch indien nodig (als volgNr groter is dan 0).
     * @param catNr int
     * @param volgNr int
     * @return historischCatNr (of actueel)
     */
    public static int maakCatNrHistorisch(final int catNr, final int volgNr) {
        int historischCatNr = catNr;
        if (catNr < HISTORISCH && volgNr > 0) {
            historischCatNr += HISTORISCH;
        }
        return historischCatNr;
    }

    /**
     * Maakt de catNr historisch indien nodig (als volgNr groter is dan 0).
     * @param catNr Lo3CategorieEnum
     * @param volgNr int
     * @return historischCatNr (of actueel)
     */
    public static Lo3CategorieEnum maakCatNrHistorisch(final Lo3CategorieEnum catNr, final int volgNr) {
        final int historischCatNr = maakCatNrHistorisch(catNr.getCategorieAsInt(), volgNr);
        return Lo3CategorieEnum.getLO3Categorie(historischCatNr);
    }

    /**
     * Kopieert de meegegeven Lo3Persoonslijst naar een nieuwe Lo3Persoonslijst. Stapels en Categorien worden nieuw
     * aangemaakt en gevuld met dezelfde inhoud.
     * @param lo3Persoonslijst origineel
     * @return gekopieerde lo3Persoonslijst
     */
    public static Lo3Persoonslijst kopieerLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst) {
        if (lo3Persoonslijst == null) {
            return null;
        }
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(kopieerStapel(lo3Persoonslijst.getPersoonStapel()));
        builder.ouder1Stapel(kopieerStapel(lo3Persoonslijst.getOuder1Stapel()));
        builder.ouder2Stapel(kopieerStapel(lo3Persoonslijst.getOuder2Stapel()));
        builder.nationaliteitStapels(kopieerStapels(lo3Persoonslijst.getNationaliteitStapels()));
        builder.huwelijkOfGpStapels(kopieerStapels(lo3Persoonslijst.getHuwelijkOfGpStapels()));
        builder.overlijdenStapel(kopieerStapel(lo3Persoonslijst.getOverlijdenStapel()));
        builder.inschrijvingStapel(kopieerStapel(lo3Persoonslijst.getInschrijvingStapel()));
        builder.verblijfplaatsStapel(kopieerStapel(lo3Persoonslijst.getVerblijfplaatsStapel()));
        builder.kindStapels(kopieerStapels(lo3Persoonslijst.getKindStapels()));
        builder.verblijfstitelStapel(kopieerStapel(lo3Persoonslijst.getVerblijfstitelStapel()));
        builder.gezagsverhoudingStapel(kopieerStapel(lo3Persoonslijst.getGezagsverhoudingStapel()));
        builder.reisdocumentStapels(kopieerStapels(lo3Persoonslijst.getReisdocumentStapels()));
        builder.kiesrechtStapel(kopieerStapel(lo3Persoonslijst.getKiesrechtStapel()));
        return builder.build();
    }

    /**
     * Kopieert de gegevens in de meegegeven lo3Stapels naar een nieuwe List<Lo3Stapel>.
     * @param lo3Stapels List<Lo3Stapel>
     * @return gekopieerde lo3Stapels
     */
    private static <T extends Lo3CategorieInhoud> List<Lo3Stapel<T>> kopieerStapels(final List<Lo3Stapel<T>> lo3Stapels) {
        final List<Lo3Stapel<T>> stapels = new ArrayList<>();
        for (final Lo3Stapel<T> lo3Stapel : lo3Stapels) {
            stapels.add(kopieerStapel(lo3Stapel));
        }
        return stapels;
    }

    /**
     * Kopieert de gegevens in de meegegeven lo3Stapel naar een nieuwe Lo3Stapel.
     * @param lo3Stapel Lo3Stapel
     * @return gekopieerde lo3Stapel
     */
    private static <T extends Lo3CategorieInhoud> Lo3Stapel<T> kopieerStapel(final Lo3Stapel<T> lo3Stapel) {
        if (lo3Stapel == null) {
            return null;
        }

        final List<Lo3Categorie<T>> categorieen = new ArrayList<>();
        for (final Lo3Categorie<T> categorie : lo3Stapel.getCategorieen()) {
            categorieen.add(new Lo3Categorie<>(
                    categorie.getInhoud(),
                    categorie.getDocumentatie(),
                    categorie.getOnderzoek(),
                    categorie.getHistorie(),
                    categorie.getLo3Herkomst()));
        }
        return new Lo3Stapel<>(categorieen);
    }
}
