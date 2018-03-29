/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Comparator specifiek voor dienst ZoekPersoon ; sorteer personen a.h.v. BSN.
 */
final class ZoekPersoonComparator implements Comparator<BijgehoudenPersoon>, Serializable {

    /**
     * Globale instantie van de {@link ZoekPersoonComparator}.
     */
    static final Comparator<BijgehoudenPersoon> INSTANCE = new ZoekPersoonComparator();

    private static final long serialVersionUID = 1L;

    private ZoekPersoonComparator() {

    }

    @Override
    public int compare(final BijgehoudenPersoon bijgehoudenPersoon1, final BijgehoudenPersoon bijgehoudenPersoon2) {
        final String bsn1 = geefBurgerServiceNummer(bijgehoudenPersoon1);
        final String bsn2 = geefBurgerServiceNummer(bijgehoudenPersoon2);
        return bsn1.compareTo(bsn2);
    }

    private String geefBurgerServiceNummer(final BijgehoudenPersoon persoon) {
        final Persoonslijst persoonslijst = persoon.getPersoonslijst();
        final Set<MetaAttribuut> bsnAttrs = persoonslijst.getModelIndex().geefAttributen(
                ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER));
        for (MetaAttribuut bsnAttr : bsnAttrs) {
            if (bsnAttr.getWaarde() != null && persoonslijst.isActueel(bsnAttr.getParentRecord())) {
                return bsnAttr.getWaarde();
            }
        }
        return "";
    }
}
