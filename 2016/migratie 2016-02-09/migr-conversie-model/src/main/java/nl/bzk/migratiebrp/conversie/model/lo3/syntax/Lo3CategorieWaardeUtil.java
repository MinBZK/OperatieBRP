/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.syntax;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;

/**
 * Helper methode voor operaties op Lo3CategorieWaarde.
 */
public final class Lo3CategorieWaardeUtil {

    private Lo3CategorieWaardeUtil() {
        throw new UnsupportedOperationException();
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Deep-copy van Lo3CategorieWaarde lijst.
     * 
     * @param categorieen
     *            categorieen
     * @return categorieen copy
     */
    public static List<Lo3CategorieWaarde> deepCopy(final List<Lo3CategorieWaarde> categorieen) {
        final List<Lo3CategorieWaarde> result = new ArrayList<>(categorieen.size());
        for (final Lo3CategorieWaarde categorie : categorieen) {
            result.add(Lo3CategorieWaardeUtil.deepCopy(categorie));
        }

        return result;
    }

    /**
     * Deep-copy van Lo3CategorieWaarde.
     * 
     * @param categorie
     *            categorie
     * @return categorie
     */
    public static Lo3CategorieWaarde deepCopy(final Lo3CategorieWaarde categorie) {
        final Lo3CategorieWaarde result = new Lo3CategorieWaarde(categorie.getCategorie(), categorie.getStapel(), categorie.getVoorkomen());

        result.getElementen().putAll(categorie.getElementen());

        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Geef een lijst van lo3categoriewaarde lijsten voor de gegeven categorie. Een lo3cateogirewaarde lijst bevat dan
     * alle lo3cateogirewaarden voor 1 voorkomen van 'categorie'.
     * 
     * @param categorieWaarden
     *            totale lisjt van lo3categoriewaarde (alle categorieen door elkaar)
     * @param categorie
     *            categorie
     * @return lijst van lo3cateogirewaarde lijsten
     */
    public static List<List<Lo3CategorieWaarde>> getCategorieen(final List<Lo3CategorieWaarde> categorieWaarden, final Lo3CategorieEnum categorie) {
        final Lo3CategorieEnum actueleCategorie = Lo3CategorieEnum.bepaalActueleCategorie(categorie);
        final Lo3CategorieEnum historischeCategorie = Lo3CategorieEnum.bepaalHistorischeCategorie(categorie);

        final List<List<Lo3CategorieWaarde>> result = new ArrayList<>();
        List<Lo3CategorieWaarde> latest = null;
        if (categorieWaarden != null) {
            for (final Lo3CategorieWaarde categorieWaarde : categorieWaarden) {
                if (categorieWaarde.getCategorie().equals(actueleCategorie)
                    || categorieWaarde.getCategorie().equals(historischeCategorie)
                    && result.isEmpty())
                {
                    latest = new ArrayList<>();
                    result.add(latest);
                }

                if (categorieWaarde.getCategorie().equals(actueleCategorie) || categorieWaarde.getCategorie().equals(historischeCategorie)) {
                    latest.add(categorieWaarde);
                }
            }
        }

        return result;
    }

    /**
     * Geef het stapel voorkomen van een categorie in een lo3categoriewaarde lijst.
     * 
     * @param categorieWaarden
     *            totale lisjt van lo3categoriewaarde (alle categorieen door elkaar)
     * @param categorie
     *            categorie
     * @param stapel
     *            gewenste stapel voorkomen
     * @return lisjt van lo3categoriewaarde die de gevraagde stapel voorkomen representeren; of null als niet gevonden
     */
    public static List<Lo3CategorieWaarde> getStapelVoorkomen(
        final List<Lo3CategorieWaarde> categorieWaarden,
        final Lo3CategorieEnum categorie,
        final int stapel)
    {
        final List<List<Lo3CategorieWaarde>> stapels = Lo3CategorieWaardeUtil.getCategorieen(categorieWaarden, categorie);

        if (stapels.size() < stapel + 1) {
            return null;
        }

        return stapels.get(stapel);

    }

    /**
     * Geef het gevraagde categorie voorkomen uit het stapel voorkomen van een categorie in een lo3categoriewaarde
     * lijst.
     * 
     * @param categorieWaarden
     *            totale lisjt van lo3categoriewaarde (alle categorieen door elkaar)
     * @param categorie
     *            categorie
     * @param stapel
     *            stapel voorkomen
     * @param voorkomen
     *            categorie voorkomen
     * @return lo3categoriewaarde die het gevraagde voorkomen representeerd; of null als niet gevonden
     */
    public static Lo3CategorieWaarde getCategorieVoorkomen(
        final List<Lo3CategorieWaarde> categorieWaarden,
        final Lo3CategorieEnum categorie,
        final int stapel,
        final int voorkomen)
    {

        final List<Lo3CategorieWaarde> categorieen = Lo3CategorieWaardeUtil.getStapelVoorkomen(categorieWaarden, categorie, stapel);

        if (categorieen == null || categorieen.size() < voorkomen + 1) {
            return null;
        }

        return categorieen.get(voorkomen);
    }

    /**
     * Geef de gevraagde element waarde uit een categorie voorkomen uit het stapel voorkomen van een categorie in een
     * lo3categoriewaarde lijst.
     * 
     * @param categorieWaarden
     *            totale lisjt van lo3categoriewaarde (alle categorieen door elkaar)
     * @param categorie
     *            categorie
     * @param stapel
     *            stapel voorkomen
     * @param voorkomen
     *            categorie voorkomen
     * @param element
     *            element
     * @return de gevraagde element waarde; of null als niet gevonden
     */
    public static String getElementWaarde(
        final List<Lo3CategorieWaarde> categorieWaarden,
        final Lo3CategorieEnum categorie,
        final int stapel,
        final int voorkomen,
        final Lo3ElementEnum element)
    {
        final Lo3CategorieWaarde categorieWaarde = Lo3CategorieWaardeUtil.getCategorieVoorkomen(categorieWaarden, categorie, stapel, voorkomen);

        if (categorieWaarde == null) {
            return null;
        } else {
            return categorieWaarde.getElement(element);
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static int bepaalEindIndexVanStapel(final List<Lo3CategorieWaarde> categorieWaarden, final Lo3CategorieEnum actueleCategorie, final int stapel)
    {

        final Lo3CategorieEnum historischeCategorie = Lo3CategorieEnum.bepaalHistorischeCategorie(actueleCategorie);

        int dezeStapel = -1;
        int laatsteIndexInDezeStapel = -1;

        for (int index = 0; index < categorieWaarden.size(); index++) {
            final Lo3CategorieWaarde dezeCategorieWaarde = categorieWaarden.get(index);

            if (dezeCategorieWaarde.getCategorie().equals(actueleCategorie)) {
                if (dezeStapel == stapel) {
                    return laatsteIndexInDezeStapel;
                } else {
                    dezeStapel++;
                }
            }

            if ((dezeCategorieWaarde.getCategorie().equals(actueleCategorie) || dezeCategorieWaarde.getCategorie().equals(historischeCategorie))
                && dezeStapel == stapel)
            {
                laatsteIndexInDezeStapel = index;
            }

        }

        return laatsteIndexInDezeStapel;
    }

    /**
     * Zet de element waarde in een categorie voorkomen in een stapel voorkomen van een categorie in een
     * lo3categoriewaarde lijst.
     * 
     * @param categorieWaarden
     *            totale lisjt van lo3categoriewaarde (alle categorieen door elkaar)
     * @param categorie
     *            categorie
     * @param stapel
     *            stapel voorkomen
     * @param voorkomen
     *            categorie voorkomen
     * @param element
     *            element
     * @param waarde
     *            te zetten waarde
     */
    public static void setElementWaarde(
        final List<Lo3CategorieWaarde> categorieWaarden,
        final Lo3CategorieEnum categorie,
        final int stapel,
        final int voorkomen,
        final Lo3ElementEnum element,
        final String waarde)
    {

        final Lo3CategorieEnum actueleCategorie = Lo3CategorieEnum.bepaalActueleCategorie(categorie);
        final Lo3CategorieEnum historischeCategorie = Lo3CategorieEnum.bepaalHistorischeCategorie(actueleCategorie);

        if (historischeCategorie == null && voorkomen > 0) {
            throw new IllegalArgumentException("//TODO");
        }

        // 'Genoeg' stapels 'aanmaken'
        while (Lo3CategorieWaardeUtil.getCategorieen(categorieWaarden, actueleCategorie).size() < stapel + 1) {
            categorieWaarden.add(new Lo3CategorieWaarde(actueleCategorie, -1, -1));
        }

        // 'Genoeg' categorieen 'aanmaken'
        while (Lo3CategorieWaardeUtil.getStapelVoorkomen(categorieWaarden, categorie, stapel).size() < voorkomen + 1) {
            categorieWaarden.add(Lo3CategorieWaardeUtil.bepaalEindIndexVanStapel(categorieWaarden, categorie, stapel) + 1, new Lo3CategorieWaarde(
                historischeCategorie,
                -1,
                -1));
        }

        // Bepaal categorie
        final Lo3CategorieWaarde categorieWaarde = Lo3CategorieWaardeUtil.getCategorieVoorkomen(categorieWaarden, actueleCategorie, stapel, voorkomen);

        // Zet waarde
        categorieWaarde.addElement(element, waarde);
    }

}
