/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;

public class BrpIstStandaardGroepInhoudTestUtil {

    public static BrpIstStandaardGroepInhoud createInhoud() {
        return createInhoud(Lo3CategorieEnum.CATEGORIE_01, 1, 2);
    }

    public static BrpIstStandaardGroepInhoud createInhoud(Lo3CategorieEnum cat, int stapelNummer, int voorkomenNummer) {
        return new BrpIstStandaardGroepInhoud(
                cat,
                stapelNummer,
                voorkomenNummer,
                null,
                null,
                BrpPartijCode.ONBEKEND,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }


}
