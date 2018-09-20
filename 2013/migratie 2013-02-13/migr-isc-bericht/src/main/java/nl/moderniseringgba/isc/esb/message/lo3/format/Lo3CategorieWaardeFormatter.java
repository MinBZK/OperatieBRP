/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.format;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Format lo3 als een categoriewaarde lijst.
 * 
 * Deze implementatie is statefull en moet voor elke format opnieuw worden geinstantieerd.
 */
public final class Lo3CategorieWaardeFormatter implements Lo3Formatter {

    private final List<Lo3CategorieWaarde> list = new ArrayList<Lo3CategorieWaarde>();
    private Lo3CategorieWaarde huidige;

    @Override
    public void categorie(final Lo3CategorieEnum categorie) {
        endCategorie();

        // Geen herkomst
        huidige = new Lo3CategorieWaarde(categorie, -1, -1);
    }

    private void endCategorie() {
        if (huidige != null && huidige.isGevuld()) {
            list.add(huidige);
        }
        huidige = null;
    }

    @Override
    public void element(final Lo3ElementEnum element, final String inhoud) {
        if (huidige == null) {
            throw new IllegalStateException("Geen actieve categorie om een element aan toe te voegen.");
        }

        if (inhoud != null) {
            huidige.addElement(element, inhoud);
        }
    }

    /**
     * Geef de categorie waarde lijst.
     * 
     * @return categorie waarde lijst
     */
    public List<Lo3CategorieWaarde> getList() {
        endCategorie();
        return list;
    }

}
