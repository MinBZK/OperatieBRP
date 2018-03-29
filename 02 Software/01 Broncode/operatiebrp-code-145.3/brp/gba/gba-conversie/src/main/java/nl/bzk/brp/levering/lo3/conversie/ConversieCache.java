/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Conversie cache.
 */
public final class ConversieCache {

    private List<Lo3CategorieWaarde> volledigCategorien;
    private List<Lo3CategorieWaarde> mutatieCategorien;
    private List<Lo3CategorieWaarde> anummerwijzigingCategorien;

    /**
     * @return volledige persoon categorieen
     */
    public List<Lo3CategorieWaarde> getVolledigCategorien() {
        return copy(volledigCategorien);
    }

    /**
     * @param volledigCategorien volledige persoon categorieen
     */
    public void setVolledigCategorien(final List<Lo3CategorieWaarde> volledigCategorien) {
        this.volledigCategorien = copy(volledigCategorien);
    }

    /**
     * @return mutatie categorieen
     */
    public List<Lo3CategorieWaarde> getMutatieCategorien() {
        return copy(mutatieCategorien);
    }

    /**
     * @param mutatieCategorien mutatie categorieen
     */
    public void setMutatieCategorien(final List<Lo3CategorieWaarde> mutatieCategorien) {
        this.mutatieCategorien = copy(mutatieCategorien);
    }

    /**
     * @return a-nummer wijziging categorieen
     */
    public List<Lo3CategorieWaarde> getAnummerwijzigingCategorien() {
        return copy(anummerwijzigingCategorien);
    }

    /**
     * @param anummerwijzigingCategorien a-nummer wijziging categorieen
     */
    public void setAnummerwijzigingCategorien(final List<Lo3CategorieWaarde> anummerwijzigingCategorien) {
        this.anummerwijzigingCategorien = copy(anummerwijzigingCategorien);
    }

    /**
     * Deep-copy categorieen lijst.
     * @param categorieen categorieen
     * @return mutable copy van de lijst van categorieen
     */
    private List<Lo3CategorieWaarde> copy(final List<Lo3CategorieWaarde> categorieen) {
        if (categorieen == null) {
            return null;
        }

        final List<Lo3CategorieWaarde> resultaat = new ArrayList<>(categorieen.size());

        for (final Lo3CategorieWaarde categorie : categorieen) {
            resultaat.add(
                    new Lo3CategorieWaarde(
                            categorie.getCategorie(),
                            categorie.getStapel(),
                            categorie.getVoorkomen(),
                            new HashMap<>(categorie.getElementen())));
        }

        return resultaat;
    }

}
