/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Lo3 Rubriek waarde object bedoelt om te vertalen naar een expressie.
 */
public final class RubriekWaarde {

    private final String lo3Expressie;
    private final boolean voorVragen;

    /**
     * Maakt een expressie string obv een Lo3CategorieWaarde en een element.
     * Als element waarde heeft wordt expressie xx.xx.xx.vragen GA1 waarde element.
     * Indien element waarde null is wordt expressie KNV xx.xx.xx.vragen.
     *
     * @param waarde Volledige Lo3CategorieWaarde
     * @param element Het element waarvoor de Rubriek waarde moet worden gemaakt
     */
    public RubriekWaarde(final Lo3CategorieWaarde waarde, final Lo3ElementEnum element) {
        voorVragen = true;
        final StringBuilder builder = new StringBuilder();
        final String waardeVanElement = waarde.getElementen().get(element);
        if (waardeVanElement == null || waardeVanElement.length() == 0) {
            builder.append("KNV ");
        }
        builder.append(waarde.getCategorie().getCategorie());
        builder.append(".");
        builder.append(element.getElementNummer(true));
        builder.append(".vragen");
        if (waardeVanElement != null && waardeVanElement.length() > 0) {
            builder.append(" GA1 ");
            builder.append(waardeVanElement);
        }
        lo3Expressie = builder.toString();
    }

    /**
     * Expressie welke vertaald moet worden.
     * @param expressie te vertalen expressie
     */
    public RubriekWaarde(final String expressie) {
        voorVragen = false;
        lo3Expressie = expressie;
    }

    /**
     * Geef de lo3 expressie string.
     * @return lo3 expressie string
     */
    public String getLo3Expressie() {
        return lo3Expressie;
    }

    /**
     * Geef terug of vertaling voor vragen of voor expressie moet worden gedaan.
     * @return indicatie of RubriekWaarde voor vragen moet worden vertaald
     */
    public boolean isVoorVragen() {
        return voorVragen;
    }
}
