/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Util klasse voor het parsen van LO3 categoriewaarden.
 */
public interface Lo3ParserUtil {

    /**
     * Geeft per stapel de gesorteerde categorieen terug. De sortering is van oud naar nieuw.
     * @param categorieenLijst categorieen lijst
     * @param categorie categorie
     * @return de lijst met categoriewaarden
     * @see #getCategorieWaarden(List, Lo3CategorieEnum)
     */
    static List<List<Lo3CategorieWaarde>> getGesorteerdeCategorieenPerStapel(
            final List<Lo3CategorieWaarde> categorieenLijst,
            final Lo3CategorieEnum categorie) {
        final List<List<Lo3CategorieWaarde>> stapels = getGesorteerdeCategorieenPerStapelNieuwNaarOud(categorieenLijst, categorie);
        // sorteer de stapels
        for (final List<Lo3CategorieWaarde> teSorterenStapel : stapels) {
            Collections.reverse(teSorterenStapel);
        }
        return stapels;
    }

    /**
     * Geeft per stapel de gesorteerde categorieen terug. De sortering is van nieuw naar oud.
     * @param categorieenLijst categorieen lijst
     * @param categorie categorie
     * @return de lijst met categoriewaarden
     * @see #getCategorieWaarden(List, Lo3CategorieEnum)
     */
    static List<List<Lo3CategorieWaarde>> getGesorteerdeCategorieenPerStapelNieuwNaarOud(
            final List<Lo3CategorieWaarde> categorieenLijst,
            final Lo3CategorieEnum categorie) {
        final List<Lo3CategorieWaarde> categorieWaarden = getCategorieWaarden(categorieenLijst, categorie);
        final List<List<Lo3CategorieWaarde>> stapels = new ArrayList<>();
        List<Lo3CategorieWaarde> stapel = null;
        boolean isActueelAfwezig = true;
        for (final Lo3CategorieWaarde cat : categorieWaarden) {
            if (cat.getCategorie().isActueel()) {
                stapel = new ArrayList<>();
                stapels.add(stapel);
                isActueelAfwezig = false;
            } else if (isActueelAfwezig) {
                // We vinden een historische groep voor een actuele, dit is niet juist aangezien het twee stapels zijn
                // en de nieuwste stapel dan geen actuele
                // bevat.
                throw new GeenActueleCategorieExceptie(Lo3CategorieEnum.bepaalActueleCategorie(cat.getCategorie()));
            }

            // Voeg de categorie toe aan de stapel.
            stapel.add(cat);
        }

        return stapels;
    }

    /**
     * Geeft op basis van de complete lijst met categorieen, een lijst met die categorieen die voldoen aan de
     * opgevraagde categorie enum. Deze methode geeft zowel de actuele als historische categorieen terug. Verwacht wordt
     * dat in de excel sheet de stapels van nieuw naar oud worden opgesomd.
     * @param categorieenLijst de complete lijst met categorieen
     * @param categorie de gevraagde categorie
     * @return lijst van categorieen
     */
    static List<Lo3CategorieWaarde> getCategorieWaarden(final List<Lo3CategorieWaarde> categorieenLijst, final Lo3CategorieEnum categorie) {
        final List<Lo3CategorieWaarde> result = new ArrayList<>();
        final Lo3CategorieEnum historisch = Lo3CategorieEnum.bepaalHistorischeCategorie(categorie, true);
        for (final Lo3CategorieWaarde categorieWaarde : categorieenLijst) {
            if (categorieWaarde.getCategorie().equals(categorie) || categorieWaarde.getCategorie().equals(historisch)) {
                result.add(categorieWaarde);
            }
        }
        return result;
    }
}
