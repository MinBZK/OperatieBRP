/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc808;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;
import nl.bzk.migratiebrp.isc.jbpm.uc808.PersoonslijstOverzicht.Categorie;
import nl.bzk.migratiebrp.isc.jbpm.uc808.PersoonslijstOverzicht.Element;

/**
 * Builer voor een persoonslijstoverzicht. Op het overzicht komen de 'gesorteerde' 'in-sync' categorien 01/51 (Persoon),
 * 06/56 (Overlijden), 07/57 (Inschrijving), 08/58 (Verblijfplaats).
 */
public final class PersoonslijstOverzichtBuilder {

    private static final String LEGE_DATUM = "00000000";
    private static final int LENGTE_DATUM = 8;

    private static final String LEGE_ONJUIST = " ";
    private static final int LENGTE_ONJUIST = 1;

    private final List<List<Lo3CategorieWaarde>> plen = new ArrayList<>();

    /**
     * Voeg PL toe.
     * 
     * @param categorieen
     *            pl
     */
    public void voegPersoonslijstToe(final List<Lo3CategorieWaarde> categorieen) {
        plen.add(categorieen);
    }

    /**
     * Bouw overzicht.
     * 
     * @return overzicht
     */
    public PersoonslijstOverzicht build() {
        final List<String> identificaties = new ArrayList<>();
        for (final List<Lo3CategorieWaarde> pl : plen) {
            identificaties.add(Lo3CategorieWaardeUtil.getElementWaarde(pl, Lo3CategorieEnum.PERSOON, 0, 0, Lo3ElementEnum.ANUMMER));
        }

        final List<Categorie> categorieen = new ArrayList<>();

        categorieen.addAll(maakCategorieen(Lo3CategorieEnum.CATEGORIE_01));
        categorieen.addAll(maakCategorieen(Lo3CategorieEnum.CATEGORIE_02));
        categorieen.addAll(maakCategorieen(Lo3CategorieEnum.CATEGORIE_03));
        categorieen.addAll(maakCategorieen(Lo3CategorieEnum.CATEGORIE_04));
        categorieen.addAll(maakCategorieen(Lo3CategorieEnum.CATEGORIE_05));
        categorieen.addAll(maakCategorieen(Lo3CategorieEnum.CATEGORIE_06));
        categorieen.addAll(maakCategorieen(Lo3CategorieEnum.CATEGORIE_07));
        categorieen.addAll(maakCategorieen(Lo3CategorieEnum.CATEGORIE_08));
        categorieen.addAll(maakCategorieen(Lo3CategorieEnum.CATEGORIE_09));
        categorieen.addAll(maakCategorieen(Lo3CategorieEnum.CATEGORIE_10));
        categorieen.addAll(maakCategorieen(Lo3CategorieEnum.CATEGORIE_11));
        categorieen.addAll(maakCategorieen(Lo3CategorieEnum.CATEGORIE_12));
        categorieen.addAll(maakCategorieen(Lo3CategorieEnum.CATEGORIE_13));

        return new PersoonslijstOverzicht(identificaties, categorieen);
    }

    private List<Categorie> maakCategorieen(final Lo3CategorieEnum categorie) {
        final int aantalStapels = PersoonslijstUtil.getMaxAantalStapels(plen, categorie);

        final List<Categorie> result = new ArrayList<>();
        for (int stapel = 0; stapel < aantalStapels; stapel++) {
            result.addAll(maakCategorieen(categorie, stapel));
        }

        return result;
    }

    private List<Categorie> maakCategorieen(final Lo3CategorieEnum categorie, final int stapelIndex) {
        final List<List<Lo3CategorieWaarde>> stapels = new ArrayList<>();
        for (final List<Lo3CategorieWaarde> pl : plen) {
            stapels.add(PersoonslijstUtil.getStapel(pl, categorie, stapelIndex));
        }

        // Actuele categorie
        final List<Lo3CategorieWaarde> actueleCategorieen = new ArrayList<>();
        for (final List<Lo3CategorieWaarde> stapel : stapels) {
            actueleCategorieen.add(stapel.isEmpty() ? null : stapel.remove(0));
        }

        final List<Categorie> result = new ArrayList<>();
        result.add(maakCategorie(actueleCategorieen, categorie));

        // Historische categorieen
        final Set<String> histories = new TreeSet<>();
        for (final List<Lo3CategorieWaarde> stapel : stapels) {
            for (final Lo3CategorieWaarde categorieWaarde : stapel) {
                histories.add(getHistorie(categorieWaarde));
            }
        }

        final Lo3CategorieEnum historieCategorie = Lo3CategorieEnum.bepaalHistorischeCategorie(categorie);
        for (final String historie : histories) {
            final List<Lo3CategorieWaarde> historieCategorieen = new ArrayList<>();
            for (final List<Lo3CategorieWaarde> stapel : stapels) {
                historieCategorieen.add(getHistorie(stapel, historie));
            }
            result.add(maakCategorie(historieCategorieen, historieCategorie));
        }

        return result;
    }

    private String getHistorie(final Lo3CategorieWaarde categorie) {
        final String ingang = formatDatum(categorie.getElement(Lo3ElementEnum.ELEMENT_8510));
        final String opneming = formatDatum(categorie.getElement(Lo3ElementEnum.ELEMENT_8610));
        final String onjuist = formatOnjuist(categorie.getElement(Lo3ElementEnum.ELEMENT_8410));

        return ingang + opneming + onjuist;
    }

    private String formatDatum(final String element) {
        if (element == null || "".equals(element)) {
            return LEGE_DATUM;
        } else {
            return element.substring(0, LENGTE_DATUM);
        }
    }

    private String formatOnjuist(final String element) {
        if (element == null || "".equals(element)) {
            return LEGE_ONJUIST;
        } else {
            return element.substring(0, LENGTE_ONJUIST);
        }
    }

    private Lo3CategorieWaarde getHistorie(final List<Lo3CategorieWaarde> stapel, final String historie) {
        for (final Lo3CategorieWaarde categorie : stapel) {
            if (historie.equals(getHistorie(categorie))) {
                return categorie;
            }
        }

        return null;
    }

    private Categorie maakCategorie(final List<Lo3CategorieWaarde> categorieen, final Lo3CategorieEnum categorie) {
        final String titel = "Categorie " + categorie.getCategorie() + (categorie.getLabel() == null ? "" : " (" + categorie.getLabel() + ")");

        final List<Element> elementen = new ArrayList<>();
        for (final Lo3ElementEnum elementEnum : getElementen(categorie)) {
            elementen.add(maakElement(categorieen, elementEnum));
        }

        return new Categorie(titel, !categorie.isActueel(), elementen);
    }

    private Element maakElement(final List<Lo3CategorieWaarde> categorieen, final Lo3ElementEnum element) {
        final String titel = element.getElementNummer(true) + LEGE_ONJUIST + element.getLabel();

        final List<String> waarden = new ArrayList<>();
        for (final Lo3CategorieWaarde categorie : categorieen) {
            waarden.add(categorie == null ? null : categorie.getElement(element));
        }

        return new Element(titel, waarden);
    }

    private List<Lo3ElementEnum> getElementen(final Lo3CategorieEnum categorie) {
        final List<Lo3ElementEnum> result = new ArrayList<>();
        for (final Lo3ElementEnum element : Lo3ElementEnum.values()) {
            if (categorie.getGroepen().contains(element.getGroep())) {
                result.add(element);
            }
        }
        return result;
    }
}
