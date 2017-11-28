/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Enumeratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElement;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;

/**
 * Enumeration based repository.
 *
 * @param <T>
 *            enum type
 */
public class ElementEnumReadonlyRepository<T extends Enumeratie> extends EnumReadonlyRepository<T> {

    /**
     * Default constructor.
     *
     * @param enumClazz
     *            De enumeratie klasse
     */
    public ElementEnumReadonlyRepository(final Class<T> enumClazz) {
        super(enumClazz);
    }

    /**
     * Filtert de enumeratie op basis van soort.
     *
     * @param soort
     *            Id van het soort element
     * @return De lijst met enumeratiewaarden die hieraan voldoen
     */
    public final List<T> filterEnumOpSoort(final Integer soort) {
        if (!Element.class.equals(getEnumClazz())) {
            throw new UnsupportedOperationException("Filteren op soort is alleen voor de enum Element toegestaan.");
        }
        final List<T> result = new ArrayList<>();
        getValues().stream().filter(value -> ((Element) value).getSoort().getId() == soort).forEach(result::add);

        return result;
    }

    /**
     * Filtert de enumeratie op basis van groep (soort is dan attribuut) en sorteert op basis van volgnummer
     * (BMR-volgorde).
     * @param groep Id van de groep
     * @return De lijst met enumeratiewaarden die hieraan voldoen
     */
    public final List<T> filterEnumOpGroepEnAutorisatie(final Integer groep) {
        if (!Element.class.equals(getEnumClazz())) {
            throw new UnsupportedOperationException("Filteren op soort en groep is alleen voor de enum Element toegestaan.");
        }

        final List<T> result = new ArrayList<>();
        getValues().stream()
                .filter(value -> SoortElement.ATTRIBUUT.equals(((Element) value).getSoort()) && ((Element) value).getGroep().getId() == groep)
                .filter(value -> ((Element) value).getSoortAutorisatie() != null)
                .filter(value -> !SoortElementAutorisatie.STRUCTUUR.equals(((Element) value).getSoortAutorisatie()))
                .filter(value -> !SoortElementAutorisatie.VIA_GROEPSAUTORISATIE.equals(((Element) value).getSoortAutorisatie()))
                .sorted(new ElementComparator<>())
                .forEach(result::add);

        return result;
    }

    /**
     * Comparator voor het sorteren van Elementen op basis van volgnummer.
     */
    private static class ElementComparator<U extends Enumeratie> implements Comparator<U>, Serializable {

        private static final long serialVersionUID = 1;

        @Override
        public int compare(final U o1, final U o2) {

            if (o1 == null || o2 == null) {
                // Deze situatie zou niet mogen voorkomen.
                return 0;
            }

            return ((Element) o1).getVolgnummer().compareTo(((Element) o2).getVolgnummer());
        }

    }
}
