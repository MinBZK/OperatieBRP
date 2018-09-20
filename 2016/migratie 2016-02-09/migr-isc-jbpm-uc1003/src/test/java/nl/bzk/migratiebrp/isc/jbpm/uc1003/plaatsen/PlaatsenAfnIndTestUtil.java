/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.plaatsen;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ap01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatieFoutcodeType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkAfnemersindicatieAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkAfnemersindicatieAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Utils voor tests voor uc1003.
 */
public final class PlaatsenAfnIndTestUtil {

    private PlaatsenAfnIndTestUtil() {
        throw new AssertionError("Niet instantieerbaar");
    }

    public static Ap01Bericht maakAp01Bericht(final String afnemerCode) {
        return maakAp01Bericht(afnemerCode, null, null, null, null);
    }

    public static Ap01Bericht maakAp01Bericht(
        final String afnemerCode,
        final Long aNummer,
        final Integer bsn,
        final String geslachtsnaam,
        final String postcode)
    {
        final Ap01Bericht ap01Bericht = new Ap01Bericht();
        ap01Bericht.setBronGemeente(afnemerCode);
        ap01Bericht.setDoelGemeente("3000200");
        if (aNummer != null || bsn != null || geslachtsnaam != null || postcode != null) {
            ap01Bericht.setCategorieen(maakCategorieen(aNummer, bsn, geslachtsnaam, postcode));
        }
        return ap01Bericht;
    }

    public static VerwerkAfnemersindicatieAntwoordBericht maakVerwerkAfnemersindicatieAntwoordBericht(final String foutCode) {
        final VerwerkAfnemersindicatieAntwoordType antwoordType = new VerwerkAfnemersindicatieAntwoordType();
        antwoordType.setStatus(StatusType.OK);

        if (foutCode != null) {
            antwoordType.setFoutcode(AfnemersindicatieFoutcodeType.fromValue(foutCode));
        }

        return new VerwerkAfnemersindicatieAntwoordBericht(antwoordType);
    }

    private static List<Lo3CategorieWaarde> maakCategorieen(final Long aNummer, final Integer bsn, final String geslachtsnaam, final String postcode) {
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        if (aNummer != null || bsn != null || geslachtsnaam != null) {
            final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
            if (aNummer != null) {
                categorieWaarde.addElement(Lo3ElementEnum.ANUMMER, String.valueOf(aNummer));
            }
            if (bsn != null) {
                categorieWaarde.addElement(Lo3ElementEnum.BURGERSERVICENUMMER, String.valueOf(bsn));
            }
            if (geslachtsnaam != null) {
                categorieWaarde.addElement(Lo3ElementEnum.GESLACHTSNAAM, geslachtsnaam);
            }
            categorieen.add(categorieWaarde);
        }

        if (postcode != null) {
            final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_08, 0, 0);
            categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1160, postcode);
            categorieen.add(categorieWaarde);
        }

        return categorieen;
    }

}
