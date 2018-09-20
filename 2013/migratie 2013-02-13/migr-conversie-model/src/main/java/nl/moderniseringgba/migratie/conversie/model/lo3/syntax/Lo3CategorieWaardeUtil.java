/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.syntax;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;

public final class Lo3CategorieWaardeUtil {

    private Lo3CategorieWaardeUtil() {
        // Niet instantieerbaar
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static List<Lo3CategorieWaarde> deepCopy(final List<Lo3CategorieWaarde> categorieen) {
        final List<Lo3CategorieWaarde> result = new ArrayList<Lo3CategorieWaarde>(categorieen.size());
        for (final Lo3CategorieWaarde categorie : categorieen) {
            result.add(deepCopy(categorie));
        }

        return result;
    }

    public static Lo3CategorieWaarde deepCopy(final Lo3CategorieWaarde categorie) {
        final Lo3CategorieWaarde result =
                new Lo3CategorieWaarde(categorie.getCategorie(), categorie.getStapel(), categorie.getVoorkomen());

        result.getElementen().putAll(categorie.getElementen());

        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    public static List<List<Lo3CategorieWaarde>> getCategorieen(
            final List<Lo3CategorieWaarde> categorieWaarden,
            final Lo3CategorieEnum categorie) {
        final Lo3CategorieEnum actueleCategorie = Lo3CategorieEnum.bepaalActueleCategorie(categorie);
        final Lo3CategorieEnum historischeCategorie = Lo3CategorieEnum.bepaalHistorischeCategorie(categorie);

        final List<List<Lo3CategorieWaarde>> result = new ArrayList<List<Lo3CategorieWaarde>>();
        List<Lo3CategorieWaarde> latest = null;
        if (categorieWaarden != null) {
            for (final Lo3CategorieWaarde categorieWaarde : categorieWaarden) {
                if (categorieWaarde.getCategorie().equals(actueleCategorie)
                        || (categorieWaarde.getCategorie().equals(historischeCategorie) && result.isEmpty())) {
                    latest = new ArrayList<Lo3CategorieWaarde>();
                    result.add(latest);
                }

                if (categorieWaarde.getCategorie().equals(actueleCategorie)
                        || categorieWaarde.getCategorie().equals(historischeCategorie)) {
                    latest.add(categorieWaarde);
                }
            }
        }

        return result;
    }

    public static List<Lo3CategorieWaarde> getStapelVoorkomen(
            final List<Lo3CategorieWaarde> categorieWaarden,
            final Lo3CategorieEnum categorie,
            final int stapel) {
        final List<List<Lo3CategorieWaarde>> stapels = getCategorieen(categorieWaarden, categorie);

        if (stapels.size() < stapel + 1) {
            return null;
        }

        return stapels.get(stapel);

    }

    public static Lo3CategorieWaarde getCategorieVoorkomen(
            final List<Lo3CategorieWaarde> categorieWaarden,
            final Lo3CategorieEnum categorie,
            final int stapel,
            final int voorkomen) {

        final List<Lo3CategorieWaarde> categorieen = getStapelVoorkomen(categorieWaarden, categorie, stapel);

        if (categorieen == null || categorieen.size() < voorkomen + 1) {
            return null;
        }

        return categorieen.get(voorkomen);
    }

    public static String getElementWaarde(
            final List<Lo3CategorieWaarde> categorieWaarden,
            final Lo3CategorieEnum categorie,
            final int stapel,
            final int voorkomen,
            final Lo3ElementEnum element) {
        final Lo3CategorieWaarde categorieWaarde =
                getCategorieVoorkomen(categorieWaarden, categorie, stapel, voorkomen);

        if (categorieWaarde == null) {
            return null;
        } else {
            return categorieWaarde.getElement(element);
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static int bepaalEindIndexVanStapel(
            final List<Lo3CategorieWaarde> categorieWaarden,
            final Lo3CategorieEnum actueleCategorie,
            final int stapel) {

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

            if ((dezeCategorieWaarde.getCategorie().equals(actueleCategorie) || dezeCategorieWaarde.getCategorie()
                    .equals(historischeCategorie)) && dezeStapel == stapel) {
                laatsteIndexInDezeStapel = index;
            }

        }

        return laatsteIndexInDezeStapel;
    }

    public static void setElementWaarde(
            final List<Lo3CategorieWaarde> categorieWaarden,
            final Lo3CategorieEnum categorie,
            final int stapel,
            final int voorkomen,
            final Lo3ElementEnum element,
            final String waarde) {

        final Lo3CategorieEnum actueleCategorie = Lo3CategorieEnum.bepaalActueleCategorie(categorie);
        final Lo3CategorieEnum historischeCategorie = Lo3CategorieEnum.bepaalHistorischeCategorie(actueleCategorie);

        if (historischeCategorie == null && voorkomen > 0) {
            throw new IllegalArgumentException("//TODO");
        }

        // 'Genoeg' stapels 'aanmaken'
        while (getCategorieen(categorieWaarden, actueleCategorie).size() < stapel + 1) {
            categorieWaarden.add(new Lo3CategorieWaarde(actueleCategorie, -1, -1));
        }

        // 'Genoeg' categorieen 'aanmaken'
        while (getStapelVoorkomen(categorieWaarden, categorie, stapel).size() < voorkomen + 1) {
            categorieWaarden.add(bepaalEindIndexVanStapel(categorieWaarden, categorie, stapel) + 1,
                    new Lo3CategorieWaarde(historischeCategorie, -1, -1));
        }

        // Bepaal categorie
        final Lo3CategorieWaarde categorieWaarde =
                getCategorieVoorkomen(categorieWaarden, actueleCategorie, stapel, voorkomen);

        // Zet waarde
        categorieWaarde.addElement(element, waarde);
    }

}
