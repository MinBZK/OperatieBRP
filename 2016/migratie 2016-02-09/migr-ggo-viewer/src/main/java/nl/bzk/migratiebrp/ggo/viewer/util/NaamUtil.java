/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.util;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;

/**
 * Util voor het opbouwen namen en labels.
 */
public final class NaamUtil {
    private static final int HISTORISCH = 50;
    private static final String WHITE_SPACE = " ";

    private NaamUtil() {
    }

    /**
     * Bouwt een volledige naam op basis van de lo3Persoonslijst.
     *
     * @param lo3Persoonslijst
     *            De persoonslijst.
     * @return Volledigenaam.
     */
    public static String createVolledigeNaam(final Lo3Persoonslijst lo3Persoonslijst) {
        String volledigeNaam = null;
        if (lo3Persoonslijst != null) {
            final Lo3PersoonInhoud inhoud = lo3Persoonslijst.getPersoonStapel().getLaatsteElement().getInhoud();
            final StringBuffer volledigeNaamDelen = new StringBuffer();
            addNaamDeel(volledigeNaamDelen, Lo3String.unwrap(inhoud.getVoornamen()));
            addNaamDeel(volledigeNaamDelen, Lo3String.unwrap(inhoud.getVoorvoegselGeslachtsnaam()));
            addNaamDeel(volledigeNaamDelen, Lo3String.unwrap(inhoud.getGeslachtsnaam()));
            volledigeNaam = volledigeNaamDelen.toString().trim();
        }
        return volledigeNaam;
    }

    private static void addNaamDeel(final StringBuffer volledigeNaam, final String naamDeel) {
        if (naamDeel != null) {
            volledigeNaam.append(WHITE_SPACE).append(naamDeel);
        }
    }

    /**
     * Bouwt een lo3 categorie label op bestaande uit de categorie nummer en categorie naam. Voor weergave op stapel
     * niveau.
     *
     * @param catNr
     *            lo3 categorie nummer.
     * @return label van de lo3 categorie
     */
    public static String createLo3CategorieStapelLabel(final int catNr) {
        final String categorie = VerwerkerUtil.zeroPad(String.valueOf(catNr), 2);
        return categorie + WHITE_SPACE + createLo3CategorieLabel(catNr);
    }

    /**
     * Bouwt een lo3 categorie label op bestaande uit de categorie naam. Voor weergave op voorkomen niveau.
     *
     * @param catNr
     *            lo3 categorie nummer.
     * @return label van de lo3 categorie
     */
    public static String createLo3CategorieLabel(final int catNr) {
        String label;
        try {
            label = Lo3CategorieEnum.getLO3Categorie(getActueelCatNr(catNr)).getLabel();
        } catch (final IllegalArgumentException iae) {
            label = "Onbekend";
        }
        return label;
    }

    private static int getActueelCatNr(final int catNr) {
        return catNr > HISTORISCH ? catNr - HISTORISCH : catNr;
    }
}
