/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import nl.bzk.brp.model.basis.ComparatorFactory;

/**
 * Factory class voor comparatoren mbt hisvolledigs.
 */
public final class HisVolledigComparatorFactory extends ComparatorFactory {

    /**
     * Constructor mag niet aangeroepen wroden, dus private.
     */
    private HisVolledigComparatorFactory()
    {
    }

    /**
     * Geeft de comparator voor PersoonNationaliteit.
     *
     * @return De comparator voor PersoonNationaliteit.
     */
    public static PersoonNationaliteitComparator getComparatorVoorPersoonNationaliteit() {
        return new PersoonNationaliteitComparator();
    }

    /**
     * Geeft de comparator voor PersoonIndicatie.
     *
     * @return De comparator voor PersoonIndicatie.
     */
    public static PersoonIndicatieComparator getComparatorVoorPersoonIndicatie() {
        return new PersoonIndicatieComparator();
    }

    /**
     * Geeft de comparator voor PersoonOnderzoek.
     *
     * @return De comparator voor PersoonOnderzoek.
     */
    public static PersoonOnderzoekComparator getComparatorVoorPersoonOnderzoek() {
        return new PersoonOnderzoekComparator();
    }

    /**
     * Geeft de comparator voor PersoonVerificatie.
     *
     * @return De comparator voor PersoonVerificatie.
     */
    public static PersoonVerificatieComparator getComparatorVoorPersoonVerificatie() {
        return new PersoonVerificatieComparator();
    }

    /**
     * Geeft de comparator voor Betrokkenheid.
     *
     * @return De comparator voor Betrokkenheid.
     */
    public static BetrokkenheidComparator getComparatorVoorBetrokkenheid() {
        return new BetrokkenheidComparator();
    }

}
