/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;

/**
 * Helper class voor Lo3Stapels.
 */
public final class Lo3Stapels {
    private static final Comparator<Lo3Categorie<?>> LG01_CATEGORIE_COMPARATOR = new Lg01CategorieComparator();
    private static final Comparator<Lo3Stapel<?>> LG01_STAPEL_COMPARATOR = new Lg01StapelComparator();

    private Lo3Stapels() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /**
     * Maak een stapel van categorieen.
     * 
     * @param <T>
     *            inhoud type
     * @param categorieen
     *            categorieen
     * @return stapel
     */
    public static <T extends Lo3CategorieInhoud> Lo3Stapel<T> of(final List<Lo3Categorie<T>> categorieen) {
        return new Lo3Stapel<>(categorieen);
    }

    /**
     * Sorteer lo3 categorieen zoals dat zou moeten voor een lg01 bericht. Let op dat de laatste categorie (actueel)
     * niet meegesorteerd wordt.
     * 
     * @param <T>
     *            inhoud type
     * @param categorieen
     *            te sorteren lijst categorieen
     */
    public static <T extends Lo3CategorieInhoud> void sorteerCategorieenLg01(final List<Lo3Categorie<T>> categorieen) {
        if (categorieen == null || categorieen.isEmpty()) {
            return;
        }

        if (categorieen.size() > 1) {
            final Lo3Categorie<T> actueel = categorieen.remove(categorieen.size() - 1);

            Collections.sort(categorieen, LG01_CATEGORIE_COMPARATOR);

            categorieen.add(actueel);

            verplaatsLaatsteOnjuisteRij(categorieen);
        }
    }

    /*
     * Als de laatste rij of rijen onjuist zijn dan wordt de eerste juist rij die voor deze onjuiste rij of rijen staat
     * verplaats naar het einde.
     */
    private static <T extends Lo3CategorieInhoud> void verplaatsLaatsteOnjuisteRij(final List<Lo3Categorie<T>> categorieen) {
        if (categorieen.get(categorieen.size() - 1).getHistorie().isOnjuist()) {
            int index = categorieen.size() - 1;
            while (index >= 0 && categorieen.get(index).getHistorie().isOnjuist()) {
                index--;
            }
            if (index >= 0) {
                categorieen.add(categorieen.remove(index));
            }
        }
    }

    /**
     * Sorteer een lo3 stapel zoals dat zou moeten voor een lg01 bericht.
     * 
     * @param <T>
     *            inhoud type
     * @param stapel
     *            te sorteren stapel
     * @return gesorteerde stapel
     */
    public static <T extends Lo3CategorieInhoud> Lo3Stapel<T> sorteerStapelLg01(final Lo3Stapel<T> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return stapel;
        }

        final List<Lo3Categorie<T>> categorieen = stapel.getCategorieen();
        sorteerCategorieenLg01(categorieen);

        return new Lo3Stapel<>(categorieen);
    }

    /**
     * Sorteer lo3 stapels zoals dat zou moeten voor een lg01 bericht.
     * 
     * @param <T>
     *            inhoud type
     * @param stapels
     *            te sorteren stapels
     * @return gesorteerde stapels in de juiste volgorde
     */
    public static <T extends Lo3CategorieInhoud> List<Lo3Stapel<T>> sorteerStapelsLg01(final List<Lo3Stapel<T>> stapels) {
        final List<Lo3Stapel<T>> result = new ArrayList<>(stapels.size());
        for (final Lo3Stapel<T> stapel : stapels) {
            result.add(sorteerStapelLg01(stapel));
        }

        Collections.sort(result, LG01_STAPEL_COMPARATOR);
        return result;

    }

    /**
     * Sorteer oplopend op ingangsdatum geldigheid en daarbinnen op datum opneming.
     */
    private static final class Lg01CategorieComparator implements Comparator<Lo3Categorie<?>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Lo3Categorie<?> arg0, final Lo3Categorie<?> arg1) {
            final Lo3Historie his0 = arg0.getHistorie();
            final Lo3Historie his1 = arg1.getHistorie();

            int result = his0.getIngangsdatumGeldigheid().compareTo(his1.getIngangsdatumGeldigheid());

            if (result == 0) {
                result = his0.getDatumVanOpneming().compareTo(his1.getDatumVanOpneming());
            }

            if (result == 0 && arg0.getLo3Herkomst() != null && arg1.getLo3Herkomst() != null) {
                result = arg1.getLo3Herkomst().getVoorkomen() - arg0.getLo3Herkomst().getVoorkomen();
            }

            if (result == 0) {
                result = arg1.hashCode() - arg0.hashCode();
            }

            return result;
        }
    }

    /**
     * Sorteer aflopend op ingangsdatum geldigheid en daarbinnen op datum opneming van de actuele categorie
     */
    private static final class Lg01StapelComparator implements Comparator<Lo3Stapel<?>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Lo3Stapel<?> arg0, final Lo3Stapel<?> arg1) {
            final Lo3Historie his0 = arg0.getLaatsteElement().getHistorie();
            final Lo3Historie his1 = arg1.getLaatsteElement().getHistorie();

            int result = 0;

            if (arg0.getLaatsteElement().getLo3Herkomst() != null && arg1.getLaatsteElement().getLo3Herkomst() != null) {
                result = arg0.getLaatsteElement().getLo3Herkomst().getStapel() - arg1.getLaatsteElement().getLo3Herkomst().getStapel();

                if (result == 0) {
                    result = arg1.getLaatsteElement().getLo3Herkomst().getVoorkomen() - arg0.getLaatsteElement().getLo3Herkomst().getVoorkomen();
                }
            }

            if (result == 0) {
                result = his1.getIngangsdatumGeldigheid().compareTo(his0.getIngangsdatumGeldigheid());
            }

            if (result == 0) {
                result = his1.getDatumVanOpneming().compareTo(his0.getDatumVanOpneming());
            }

            if (result == 0) {
                result = arg1.hashCode() - arg0.hashCode();
            }

            return result;
        }
    }
}
