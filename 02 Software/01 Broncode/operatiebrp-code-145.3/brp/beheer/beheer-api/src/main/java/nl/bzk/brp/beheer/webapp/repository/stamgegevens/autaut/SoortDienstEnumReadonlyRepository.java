/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Enumeratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.EnumReadonlyRepository;

/**
 * Enumeration based repository.
 * @param <T> enum type
 */
public class SoortDienstEnumReadonlyRepository<T extends Enumeratie> extends EnumReadonlyRepository<T> {

    private static final List<Integer> GBA_SOORT_DIENSTEN =
            Arrays.asList(
                    SoortDienst.ATTENDERING.getId(),
                    SoortDienst.GEEF_DETAILS_PERSOON.getId(),
                    SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON.getId(),
                    SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE.getId(),
                    SoortDienst.PLAATSING_AFNEMERINDICATIE.getId(),
                    SoortDienst.SELECTIE.getId(),
                    SoortDienst.VERWIJDERING_AFNEMERINDICATIE.getId(),
                    SoortDienst.ZOEK_PERSOON.getId(),
                    SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS.getId());

    /**
     * Default constructor.
     * @param enumClazz De enumeratie klasse
     */
    public SoortDienstEnumReadonlyRepository(final Class<T> enumClazz) {
        super(enumClazz);
    }

    /**
     * Filtert de enumeratie op basis van soort.
     * @param filterVoorGba of voor GBA gefilterd moet worden
     * @return De lijst met enumeratiewaarden die hieraan voldoen
     */
    public final List<T> filterEnumOpStelsel(final Boolean filterVoorGba) {
        if (!SoortDienst.class.equals(getEnumClazz())) {
            throw new UnsupportedOperationException("Filteren op stelsel is alleen voor de enum SoortDienst toegestaan.");
        }

        final List<T> result;

        if (!filterVoorGba) {
            result = getValues().stream()
                                .sorted(new SoortDienstComparator<>())
                                .collect(Collectors.toList());
        } else {
            result = getValues().stream()
                                .filter(value -> GBA_SOORT_DIENSTEN.contains(value.getId()))
                                .sorted(new SoortDienstComparator<>())
                                .collect(Collectors.toList());
        }
        return result;
    }

    /**
     * Comparator voor het sorteren van Elementen op basis van volgnummer.
     */
    private static class SoortDienstComparator<U extends Enumeratie> implements Comparator<U>, Serializable {

        private static final long serialVersionUID = 1;

        @Override
        public int compare(final U o1, final U o2) {

            if (o1 == null || o2 == null) {
                // Deze situatie zou niet mogen voorkomen.
                return 0;
            }

            return ((SoortDienst) o1).getNaam().compareTo(((SoortDienst) o2).getNaam());
        }

    }
}
