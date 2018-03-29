/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Default persoon comparator specifiek.
 */
public final class DefaultPersoonComparator implements Comparator<BijgehoudenPersoon>, Serializable {

    /**
     * Globale instantie van de {@link DefaultPersoonComparator}.
     */
    public static final Comparator<BijgehoudenPersoon> INSTANCE = new DefaultPersoonComparator();

    private static final long serialVersionUID = 1L;

    private DefaultPersoonComparator() {

    }

    @Override
    public int compare(final BijgehoudenPersoon bijgehoudenPersoon1, final BijgehoudenPersoon bijgehoudenPersoon2) {
        final MetaObject persoon1 = bijgehoudenPersoon1.getPersoonslijst().getMetaObject();
        final MetaObject persoon2 = bijgehoudenPersoon2.getPersoonslijst().getMetaObject();
        final Comparable<Number> sorteerVolgorde1 = bepaalSorteerVolgorde(bijgehoudenPersoon1);
        final Comparable<Number> sorteerVolgorde2 = bepaalSorteerVolgorde(bijgehoudenPersoon2);
        final int sorteerVolgorde = ObjectUtils.compare( (Comparable) sorteerVolgorde1, (Comparable) sorteerVolgorde2);
        if (sorteerVolgorde != 0) {
            return sorteerVolgorde;
        }
        return Long.compare(persoon1.getObjectsleutel(), persoon2.getObjectsleutel());
    }

    private <T extends Number> Comparable<T> bepaalSorteerVolgorde(final BijgehoudenPersoon bijgehoudenPersoon) {
        final MetaGroep afgeleidAdministratiefGroep = bijgehoudenPersoon.getPersoonslijst().getMetaObject()
                .getGroep(Element.PERSOON_AFGELEIDADMINISTRATIEF);
        return bijgehoudenPersoon.getPersoonslijst().getActueleRecord(afgeleidAdministratiefGroep)
                .map(actueleRecord -> actueleRecord.getAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_SORTEERVOLGORDE).<Comparable<T>>getWaarde())
                .orElse(null);
    }
}
