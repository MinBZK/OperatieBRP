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
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Controleert per akte of een tb02 bericht correct is.
 */
public enum Tb02SoortAkte {

    /**
     * Akte 1C.
     */
    AKTE_1C(Arrays.asList(
            new AanwezigCategorie(
                    Lo3CategorieEnum.PERSOON,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.CATEGORIE_51,
                    Aanwezig.VERPLICHT,
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0210, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0220, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0230, Aanwezig.OPTIONEEL),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0240, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0310, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0320, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0330, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_0410, Aanwezig.VERPLICHT)),
            new AanwezigCategorie(
                    Lo3CategorieEnum.OUDER_1,
                    Aanwezig.VERPLICHT,
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
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_6210, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8110, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8120, Aanwezig.VERPLICHT),
                    new AanwezigElement(Lo3ElementEnum.ELEMENT_8510, Aanwezig.VERPLICHT)))),

    /**
     * Akte 1E.
     */
    AKTE_1E(Collections.emptyList()),

    /**
     * Akte 1J.
     */
    AKTE_1J(Collections.emptyList()),

    /**
     * Akte 1N.
     */
    AKTE_1N(Collections.emptyList()),

    /**
     * Akte 1Q.
     */
    AKTE_1Q(Collections.emptyList()),

    /**
     * Akte 1U.
     */
    AKTE_1U(Collections.emptyList()),

    /**
     * Akte 1V.
     */
    AKTE_1V(Collections.emptyList()),

    /**
     * Akte 1H.
     */
    AKTE_1H(Arrays.asList(
            new AanwezigCategorie(
                    Lo3CategorieEnum.PERSOON,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.CATEGORIE_51,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.PERSOON,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.CATEGORIE_51,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.PERSOON,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.CATEGORIE_51,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.PERSOON,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.CATEGORIE_06,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.PERSOON,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.CATEGORIE_06,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.PERSOON,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.HUWELIJK,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.PERSOON,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.HUWELIJK,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.PERSOON,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.HUWELIJK,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.CATEGORIE_55,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.PERSOON,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.HUWELIJK,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.CATEGORIE_55,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.PERSOON,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.HUWELIJK,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.CATEGORIE_55,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.PERSOON,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.HUWELIJK,
                    Aanwezig.VERPLICHT,
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
            new AanwezigCategorie(
                    Lo3CategorieEnum.CATEGORIE_55,
                    Aanwezig.VERPLICHT,
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

    private static final int LENGTE_AKTENUMMER = 7;
    private final List<AanwezigCategorie> categorieen;

    /**
     * Constructor voor de AkteEnum.
     * @param categorieen Lijst van categorie&euml;n voor deze enum
     */
    Tb02SoortAkte(final List<AanwezigCategorie> categorieen) {
        this.categorieen = categorieen;
    }

    /**
     * Bepaal het soort akte voor een aktenummer.
     * @param aktenummer aktenummer
     * @return Soort akte, of null als het aktenummer ongeldig is, of de akte niet bekend is voor een Tb02
     */
    public static Tb02SoortAkte bepaalAkteObvAktenummer(final String aktenummer) {
        try {
            final boolean ongeldigeAkte = aktenummer == null || aktenummer.length() != LENGTE_AKTENUMMER;
            final int charIndex = 2;
            return ongeldigeAkte ? null : valueOf(String.format("AKTE_%s%s", Character.toUpperCase(aktenummer.charAt(0)), aktenummer.charAt(charIndex)));
        } catch (final IllegalArgumentException e) {
            LoggerFactory.getLogger().debug("Fout tijdens bepalen soort akte", e);
            return null;
        }
    }

    /**
     * Controleer bericht op juistheid.
     * @param aktenummer Aktenummer van te controleren bericht
     * @param waarden categorieen en waarden uit het bericht
     * @param overtredingen lijst waaraan opgetreden overtredingen toegevoegd dienen te worden
     */
    public void controleerCategorieenEnElementen(final String aktenummer, final List<Lo3CategorieWaarde> waarden, final List<String> overtredingen) {
        final Map<Lo3CategorieEnum, Lo3CategorieWaarde> waardeMap = new EnumMap<>(Lo3CategorieEnum.class);
        vulWaardeMap(aktenummer, waarden, overtredingen, waardeMap);
        controleerOfBenodigdeCategorieenAanwezigZijn(aktenummer, overtredingen, waardeMap);
        controleerOfErNietToegestaneCategorieenInBerichtZitten(aktenummer, overtredingen, waardeMap);
    }

    private void vulWaardeMap(
            final String aktenummer,
            final List<Lo3CategorieWaarde> waarden,
            final List<String> overtredingen,
            final Map<Lo3CategorieEnum, Lo3CategorieWaarde> waardeMap) {
        for (final Lo3CategorieWaarde waarde : waarden) {
            if (waardeMap.containsKey(waarde.getCategorie())) {
                overtredingen.add(
                        String.format(
                                "Voor aktenummer %s zijn meerdere voorkomens van een categorie %s niet toegestaan.",
                                aktenummer,
                                waarde.getCategorie().getCategorie()));
            } else {
                waardeMap.put(waarde.getCategorie(), waarde);
            }
        }
    }

    private void controleerOfBenodigdeCategorieenAanwezigZijn(
            final String aktenummer,
            final List<String> overtredingen,
            final Map<Lo3CategorieEnum, Lo3CategorieWaarde> waardeMap) {
        for (final AanwezigCategorie categorie : categorieen) {
            if (waardeMap.containsKey(categorie.categorie)) {
                final Lo3CategorieWaarde waarde = waardeMap.remove(categorie.categorie);

                final List<Lo3ElementEnum> elementen = new ArrayList<>();
                for (final Lo3ElementEnum element : waarde.getElementen().keySet()) {
                    elementen.add(element);
                }

                for (final AanwezigElement aanwezigElement : categorie.elementen) {
                    controleerAanwezigheidElement(aktenummer, overtredingen, waarde, elementen, aanwezigElement);
                }
                for (final Lo3ElementEnum element : elementen) {
                    overtredingen.add(
                            String.format(
                                    "Voor aktenummer %s is in categorie %s element %s niet toegestaan.",
                                    aktenummer,
                                    waarde.getCategorie().getCategorie(),
                                    element.getElementNummer(true)));
                }
            } else {
                if (Aanwezig.VERPLICHT.equals(categorie.aanwezig)) {
                    overtredingen.add(String.format("Voor aktenummer %s is categorie %s verplicht.", aktenummer, categorie.categorie.getCategorie()));
                }
            }
        }
    }

    private void controleerAanwezigheidElement(String aktenummer, List<String> overtredingen, Lo3CategorieWaarde waarde, List<Lo3ElementEnum> elementen,
                                               AanwezigElement aanwezigElement) {
        if (elementen.contains(aanwezigElement.element)) {
            elementen.remove(aanwezigElement.element);
        } else {
            if (Aanwezig.VERPLICHT.equals(aanwezigElement.aanwezig)) {
                overtredingen.add(
                        String.format(
                                "Voor aktenummer %s is in categorie %s element %s verplicht.",
                                aktenummer,
                                waarde.getCategorie().getCategorie(),
                                aanwezigElement.element.getElementNummer(true)));
            }
        }
    }

    private void controleerOfErNietToegestaneCategorieenInBerichtZitten(
            final String aktenummer,
            final List<String> overtredingen,
            final Map<Lo3CategorieEnum, Lo3CategorieWaarde> waardeMap) {
        for (final Lo3CategorieEnum categorie : waardeMap.keySet()) {
            overtredingen.add(String.format("Voor aktenummer %s is categorie %s niet toegestaan.", aktenummer, categorie.getCategorie()));
        }
    }

    /**
     * enum status aanwezig.
     */
    public enum Aanwezig {

        /**
         * Verplicht.
         */
        VERPLICHT,

        /**
         * Optioneel.
         */
        OPTIONEEL
    }

    /**
     * aanwezige categorie benodigd voor akte.
     */
    private static class AanwezigCategorie implements Serializable {

        private static final long serialVersionUID = 1L;
        private final Lo3CategorieEnum categorie;
        private final Aanwezig aanwezig;
        private final List<AanwezigElement> elementen;

        /**
         * constuctor voor aanwezige categorie.
         * @param categorie De categorie
         * @param aanwezig indicatie optioneel of verplicht
         * @param elementen lijst met elementen
         */
        AanwezigCategorie(final Lo3CategorieEnum categorie, final Aanwezig aanwezig, final AanwezigElement... elementen) {
            this.categorie = categorie;
            this.aanwezig = aanwezig;
            this.elementen = Arrays.asList(elementen);
        }
    }

    /**
     * Aanwezig element benodigd voor akte.
     */
    private static class AanwezigElement implements Serializable {

        private static final long serialVersionUID = 1L;
        private final Lo3ElementEnum element;
        private final Aanwezig aanwezig;

        /**
         * Constructor voor aanwezig element.
         * @param element het element
         * @param aanwezig indicatie optioneel of verplicht
         */
        AanwezigElement(final Lo3ElementEnum element, final Aanwezig aanwezig) {
            this.element = element;
            this.aanwezig = aanwezig;
        }

    }
}
