/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;

/**
 * Exceptie die aangeeft dat er meer categorieën dan toegestaan werden gevonden tijdens het parsen.
 */
public class CategorieKomtVakerVoorDanToegestaanExceptie extends ParseException {

    private static final long serialVersionUID = 1150761581693134138L;

    /**
     * Maakt een CategorieKomtVakerVoorDanToegestaanExceptie, die aangeeft dat er meer categorieën dan toegestaan werden
     * gevonden tijdens het parsen.
     *
     * @param categorie
     *            De categorie waarin het onverwachte element werd gevonden
     * @param aantalToegestaan
     *            het aantal toegestane categorieën.
     * @param aantalVoorkomens
     *            het aantal gevonden categorieën
     */
    public CategorieKomtVakerVoorDanToegestaanExceptie(final Lo3CategorieEnum categorie, final int aantalToegestaan, final int aantalVoorkomens) {
        super(String.format(
            "Categorie %d (%s) komt vaker voor dan toegestaan (Toegestaan = %d, Aanwezig = %d).",
            categorie.getCategorieAsInt(),
            categorie.getLabel(),
            aantalToegestaan,
            aantalVoorkomens));
    }
}
