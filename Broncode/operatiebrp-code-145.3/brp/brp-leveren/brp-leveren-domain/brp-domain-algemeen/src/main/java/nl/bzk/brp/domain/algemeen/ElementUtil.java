/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.AttribuutElement;

/**
 * ElementUtil.
 */
public final class ElementUtil {

    private ElementUtil() {
    }

    /**
     * Bepaalt of een element geldig is voor attibuut autorisatie.
     * @param element element
     * @param datumTijd datumTijd
     * @return is element geldig voor attibuut autorisatie
     */
    public static boolean isElementGeldigVoorAttribuutAutorisatie(final AttribuutElement element, final Integer datumTijd) {

        final boolean datumAanvangEnDatumEindeZijnGeldig = valideerDatums(element, datumTijd);

        final boolean soortAutorisatieIsGeldig =
                element.getAutorisatie() != null
                        && element.getAutorisatie() != SoortElementAutorisatie.NIET_VERSTREKKEN
                        && element.getAutorisatie() != SoortElementAutorisatie.VIA_GROEPSAUTORISATIE
                        && element.getAutorisatie() != SoortElementAutorisatie.STRUCTUUR;

        return datumAanvangEnDatumEindeZijnGeldig && soortAutorisatieIsGeldig;
    }

    /**
     * Bepaalt of een element geldig is voor groep autorisatie.
     * @param element element
     * @param datumTijd datumTijd
     * @return is element geldig voor groep autorisatie
     */
    public static boolean isElementGeldigVoorGroepAutorisatie(final AttribuutElement element, final Integer datumTijd) {

        final boolean datumAanvangEnDatumEindeZijnGeldig = valideerDatums(element, datumTijd);

        final boolean soortAutorisatieIsGeldig =
                element.getAutorisatie() != null
                        && element.getAutorisatie() == SoortElementAutorisatie.VIA_GROEPSAUTORISATIE;

        return datumAanvangEnDatumEindeZijnGeldig && soortAutorisatieIsGeldig;
    }

    private static boolean valideerDatums(final AttribuutElement element, final Integer datumTijd) {
        final boolean datumAanvangIsGeldig = element.getDatumAanvangGeldigheid() == null
                || DatumUtil.valtDatumBinnenPeriodeInclusief(element.getDatumAanvangGeldigheid(), null, datumTijd);
        final boolean datumEindeIsGeldig = element.getDatumEindeGeldigheid() == null
                || DatumUtil.valtDatumBinnenPeriode(element.getDatumEindeGeldigheid(), datumTijd, null)
                && !element.getDatumEindeGeldigheid().equals(datumTijd);
        return datumAanvangIsGeldig && datumEindeIsGeldig;
    }
}
