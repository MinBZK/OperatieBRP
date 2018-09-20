/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Controleert per akte of een tb02 bericht correct is.
 */
public enum AkteEnum {
// @formatter:off
    /**
     * Akte 1C.
     */
    AKTE_1C(Arrays.asList(
            new AanwezigCategorie(Lo3CategorieEnum.PERSOON, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8120, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8510, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(Lo3CategorieEnum.OUDER_1, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_6210, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8120, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8510, Aanwezig.VERPLICHT)))),
    /**
     * Akte 1E.
     */
    AKTE_1E(Collections.<AanwezigCategorie>emptyList()),
    /**
     * Akte 1J.
     */
    AKTE_1J(Collections.<AanwezigCategorie>emptyList()),
    /**
     * Akte 1N.
     */
    AKTE_1N(Collections.<AanwezigCategorie>emptyList()),
    /**
     * Akte 1Q.
     */
    AKTE_1Q(Collections.<AanwezigCategorie>emptyList()),
    /**
     * Akte 1U.
     */
    AKTE_1U(Collections.<AanwezigCategorie>emptyList()),
    /**
     * Akte 1V.
     */
    AKTE_1V(Collections.<AanwezigCategorie>emptyList()),
    /**
     * Akte 1H.
     */
    AKTE_1H(Arrays.asList(
            new AanwezigCategorie(Lo3CategorieEnum.PERSOON, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8120, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8510, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(Lo3CategorieEnum.CATEGORIE_51, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT)))),
    /**
     * Akte 1M.
     */
    AKTE_1M(Arrays.asList(
            new AanwezigCategorie(Lo3CategorieEnum.PERSOON, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8120, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8510, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(Lo3CategorieEnum.CATEGORIE_51, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT)))),
    /**
     * Akte 1S.
     */
    AKTE_1S(Arrays.asList(
            new AanwezigCategorie(Lo3CategorieEnum.PERSOON, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8120, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8510, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(Lo3CategorieEnum.CATEGORIE_51, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT)))),
    /**
     * Akte 2A.
     */
    AKTE_2A(Arrays.asList(
            new AanwezigCategorie(Lo3CategorieEnum.PERSOON, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(Lo3CategorieEnum.CATEGORIE_06, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0810, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0820, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0830, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8120, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8510, Aanwezig.VERPLICHT)))),
    /**
     * Akte 2G.
     */
    AKTE_2G(Arrays.asList(
            new AanwezigCategorie(Lo3CategorieEnum.PERSOON, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(Lo3CategorieEnum.CATEGORIE_06, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0810, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0820, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0830, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8120, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8510, Aanwezig.VERPLICHT)))),
    /**
     * Akte 3A.
     */
    AKTE_3A(Arrays.asList(
            new AanwezigCategorie(Lo3CategorieEnum.PERSOON, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(Lo3CategorieEnum.HUWELIJK, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0610, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0620, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0630, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_1510, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8120, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8510, Aanwezig.VERPLICHT)))),
    /**
     * Akte 5A.
     */
    AKTE_5A(Arrays.asList(
            new AanwezigCategorie(Lo3CategorieEnum.PERSOON, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(Lo3CategorieEnum.HUWELIJK, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0610, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0620, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0630, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_1510, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8120, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8510, Aanwezig.VERPLICHT)))),
    /**
     * Akte 3B.
     */
    AKTE_3B(Arrays.asList(
            new AanwezigCategorie(Lo3CategorieEnum.PERSOON, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(Lo3CategorieEnum.HUWELIJK, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0710, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0720, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0730, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0740, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_1510, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8120, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8510, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(Lo3CategorieEnum.CATEGORIE_55, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0610, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0620, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0630, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_1510, Aanwezig.VERPLICHT)))),
    /**
     * Akte 5B.
     */
    AKTE_5B(Arrays.asList(
            new AanwezigCategorie(Lo3CategorieEnum.PERSOON, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.OPTIONEEL)),
            new AanwezigCategorie(Lo3CategorieEnum.HUWELIJK, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0710, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0720, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0730, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0740, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_1510, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8120, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8510, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(Lo3CategorieEnum.CATEGORIE_55, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0610, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0620, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0630, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_1510, Aanwezig.VERPLICHT)))),
    /**
     * Akte 3H.
     */
    AKTE_3H(Arrays.asList(
            new AanwezigCategorie(Lo3CategorieEnum.PERSOON, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(Lo3CategorieEnum.HUWELIJK, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0610, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0620, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0630, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_1510, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8120, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8510, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(Lo3CategorieEnum.CATEGORIE_55, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0610, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0620, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0630, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_1510, Aanwezig.VERPLICHT)))),
    /**
     * Akte 5H.
     */
    AKTE_5H(Arrays.asList(
            new AanwezigCategorie(Lo3CategorieEnum.PERSOON, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(Lo3CategorieEnum.HUWELIJK, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0110, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0120, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0610, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0620, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0630, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_1510, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8120, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8510, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(Lo3CategorieEnum.CATEGORIE_55, Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0610, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0620, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0630, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_1510, Aanwezig.VERPLICHT))));

// @formatter:on
    private final List<AanwezigCategorie> categorieen;

    /**
     * Constructor voor de AkteEnum.
     *
     * @param categorieen
     *            Lijst van categorie&euml;n voor deze enum
     */
    AkteEnum(final List<AanwezigCategorie> categorieen) {
        this.categorieen = categorieen;
    }

    /**
     * Geeft AkteEnum voor type akte.
     *
     * @param cijfer
     *            cijfer van de akte (eerste karakter van aktenummer)
     * @param letter
     *            letter van de akte (derde karakter van aktenummer)
     * @return De correcte AkteEnum
     */
    public static AkteEnum valueOf(final char cijfer, final char letter) {
        return AkteEnum.valueOf(String.format("AKTE_%s%s", cijfer, String.valueOf(letter).toUpperCase()));
    }

    /**
     * Controleer bericht op juistheid.
     *
     * @param aktenummer
     *            Aktenummer van te controleren bericht
     * @param waarden
     *            categorieen en waarden uit het bericht
     * @throws nl.bzk.migratiebrp.bericht.model.lo3.impl.AkteEnum.AkteEnumException
     *             fout bij controleren
     */
    public void zijnJuisteCategorieenAanwezig(final String aktenummer, final List<Lo3CategorieWaarde> waarden) throws AkteEnumException {
        final List<String> opgetredenFoutenInBericht = new ArrayList<>();

        final Map<Lo3CategorieEnum, Lo3CategorieWaarde> waardeMap = new HashMap<>();
        vulWaardeMap(aktenummer, waarden, opgetredenFoutenInBericht, waardeMap);
        controleerOfBenodigdeCategorieenAanwezigZijn(aktenummer, opgetredenFoutenInBericht, waardeMap);
        controleerOfErNietToegestaneCategorieenInBerichtZitten(aktenummer, opgetredenFoutenInBericht, waardeMap);

        if (!opgetredenFoutenInBericht.isEmpty()) {
            throw new AkteEnumException(opgetredenFoutenInBericht);
        }
    }

    private void vulWaardeMap(
        final String aktenummer,
        final List<Lo3CategorieWaarde> waarden,
        final List<String> opgetredenFoutenInBericht,
        final Map<Lo3CategorieEnum, Lo3CategorieWaarde> waardeMap)
    {
        for (final Lo3CategorieWaarde waarde : waarden) {
            if (waardeMap.containsKey(waarde.getCategorie())) {
                opgetredenFoutenInBericht.add(String.format(
                    "Voor aktenummer %s is een dubbele categorie %s niet toegestaan",
                    aktenummer,
                    waarde.getCategorie().getCategorie()));
            } else {
                waardeMap.put(waarde.getCategorie(), waarde);
            }
        }
    }

    private void controleerOfBenodigdeCategorieenAanwezigZijn(
        final String aktenummer,
        final List<String> opgetredenFoutenInBericht,
        final Map<Lo3CategorieEnum, Lo3CategorieWaarde> waardeMap)
    {
        for (final AanwezigCategorie categorie : categorieen) {
            if (waardeMap.containsKey(categorie.categorie)) {
                final Lo3CategorieWaarde waarde = waardeMap.remove(categorie.categorie);

                final List<Lo3ElementEnum> elementen = new ArrayList<>();
                for (final Lo3ElementEnum element : waarde.getElementen().keySet()) {
                    elementen.add(element);
                }

                for (final AanwezigElement aanwezigElement : categorie.elementen) {
                    if (elementen.contains(aanwezigElement.element)) {
                        elementen.remove(aanwezigElement.element);
                    } else {
                        if (Aanwezig.VERPLICHT.equals(aanwezigElement.aanwezig)) {
                            opgetredenFoutenInBericht.add(String.format(
                                "Voor aktenummer %s is in categorie %s element %s verplicht",
                                aktenummer,
                                waarde.getCategorie().getCategorie(),
                                aanwezigElement.element.getElementNummer(true)));
                        }
                    }
                }
                for (final Lo3ElementEnum element : elementen) {
                    opgetredenFoutenInBericht.add(String.format(
                        "Voor aktenummer %s is in categorie %s element %s niet toegestaan",
                        aktenummer,
                        waarde.getCategorie().getCategorie(),
                        element.getElementNummer(true)));
                }
            } else {
                if (Aanwezig.VERPLICHT.equals(categorie.aanwezig)) {
                    opgetredenFoutenInBericht.add(String.format(
                        "Voor aktenummer %s is categorie %s verplicht",
                        aktenummer,
                        categorie.categorie.getCategorie()));
                }
            }
        }
    }

    private void controleerOfErNietToegestaneCategorieenInBerichtZitten(
        final String aktenummer,
        final List<String> opgetredenFoutenInBericht,
        final Map<Lo3CategorieEnum, Lo3CategorieWaarde> waardeMap)
    {
        for (final Lo3CategorieEnum categorie : waardeMap.keySet()) {
            opgetredenFoutenInBericht.add(String.format("Voor aktenummer %s is categorie %s niet toegestaan", aktenummer, categorie.getCategorie()));
        }
    }

    /**
     * enum status aanwezig.
     */
    public enum Aanwezig {

        /**
         * verplicht.
         */
        VERPLICHT,
        /**
         * optioneel.
         */
        OPTIONEEL
    }

    /**
     * aanwezige categorie benodigd voor akte.
     */
    public static class AanwezigCategorie implements Serializable {

        private static final long serialVersionUID = 1L;
        private final Lo3CategorieEnum categorie;
        private final Aanwezig aanwezig;
        private final List<AanwezigElement> elementen;

        /**
         * constuctor voor aanwezige categorie.
         *
         * @param categorie
         *            De categorie
         * @param aanwezig
         *            indicatie optioneel of verplicht
         * @param elementen
         *            lijst met elementen
         */
        public AanwezigCategorie(final Lo3CategorieEnum categorie, final Aanwezig aanwezig, final AanwezigElement... elementen) {
            this.categorie = categorie;
            this.aanwezig = aanwezig;
            this.elementen = Arrays.asList(elementen);
        }

        /**
         * Geef categorie terug.
         *
         * @return de categorie
         */
        public final Lo3CategorieEnum getCategorie() {
            return categorie;
        }

        /**
         * Geef Aanwezigheid terug.
         *
         * @return aanwezigheid
         */
        public final Aanwezig getAanwezig() {
            return aanwezig;
        }

        /**
         * Geef lijst met elementen in deze categorie.
         *
         * @return elementen
         */
        public final List<AanwezigElement> getElementen() {
            return elementen;
        }
    }

    /**
     * aanwezig element benodigd voor akte.
     */
    public static class AanwezigElement implements Serializable {

        private static final long serialVersionUID = 1L;
        private final Lo3ElementEnum element;
        private final Aanwezig aanwezig;

        /**
         * Constructor voor aanwezig element.
         *
         * @param element
         *            het element
         * @param aanwezig
         *            indicatie optioneel of verplicht
         */
        public AanwezigElement(final Lo3ElementEnum element, final Aanwezig aanwezig) {
            this.element = element;
            this.aanwezig = aanwezig;
        }

        /**
         * Geef element terug.
         *
         * @return de element
         */
        public final Lo3ElementEnum getElement() {
            return element;
        }

        /**
         * Geef Aanwezigheid terug.
         *
         * @return aanwezigheid
         */
        public final Aanwezig getAanwezig() {
            return aanwezig;
        }
    }

    /**
     * Fout in akte verwerking.
     */
    public static class AkteEnumException extends Exception {

        private static final long serialVersionUID = 1L;

        private final List<String> foutMeldingen;

        /**
         * Nieuwe foutmelding.
         *
         * @param foutmeldingen
         *            lijst met fouten
         */
        public AkteEnumException(final List<String> foutmeldingen) {
            foutMeldingen = foutmeldingen;
        }

        /**
         * Geef de foutmeldingen.
         *
         * @return de foutmeldingen
         */
        public final List<String> getFoutMeldingen() {
            return foutMeldingen;
        }
    }

}
