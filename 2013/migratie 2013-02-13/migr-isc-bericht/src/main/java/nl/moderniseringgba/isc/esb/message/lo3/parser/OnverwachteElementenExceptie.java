/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.parser;

import java.util.Map;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;

/**
 * Exceptie die aangeeft dat er onverwachte elementen werden gevonden tijdens het parsen.
 */
public class OnverwachteElementenExceptie extends ParseException {

    private static final long serialVersionUID = 1150761581693134138L;

    /**
     * Maakt een OnverwachteElementenExceptie, die aangeeft dat er een onverwacht element werd gevonden tijdens het
     * parsen.
     * 
     * @param categorie
     *            De categorie waarin het onverwachte element werd gevonden
     * @param elementen
     *            de onverwachte elementen
     */
    public OnverwachteElementenExceptie(final Lo3CategorieEnum categorie, final Map<Lo3ElementEnum, String> elementen) {
        super(String.format("Onverwachte elementen voor categorie %s: %s", categorie, elementen));
    }
}
