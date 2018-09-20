/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Factory class voor comparatoren.
 */
public class ComparatorFactory {

    /**
     * Constructor mag niet aangeroepen wroden, dus private.
     */
    protected ComparatorFactory() {
    }

    /**
     * Geeft de comparator voor PersoonVoornaam.
     *
     * @return De comparator voor PersoonVoornaam.
     */
    public static VolgnummerComparator getComparatorVoorPersoonVoornaam() {
        return new VolgnummerComparator();
    }

    /**
     * Geeft de comparator voor PersoonGeslachtsnaamcomponent.
     *
     * @return De comparator voor PersoonGeslachtsnaamcomponent.
     */
    public static VolgnummerComparator getComparatorVoorPersoonGeslachtsnaamcomponent() {
        return new VolgnummerComparator();
    }

    /**
     * Geeft de standaard-comparator.
     *
     * @return De standaard-comparator.
     */
    public static IdComparator getStandaardComparator() {
        return new IdComparator();
    }

    /**
     * Geeft de comparator voor ActieBron.
     *
     * @return De comparator voor ActieBron.
     */
    public static IdComparator getComparatorVoorActieBron() {
        return getStandaardComparator();
    }

    /**
     * Geeft de comparator voor AdministratieveHandelingGedeblokkeerdeMelding.
     *
     * @return De comparator voor AdministratieveHandelingGedeblokkeerdeMelding.
     */
    public static IdComparator getComparatorVoorAdministratieveHandelingGedeblokkeerdeMelding() {
        return getStandaardComparator();
    }

    /**
     * Geeft de comparator voor Actie.
     *
     * @return De comparator voor Actie.
     */
    public static IdComparator getComparatorVoorActie() {
        return getStandaardComparator();
    }

    /**
     * Geeft de comparator voor BerichtPersoon.
     *
     * @return De comparator voor BerichtPersoon.
     */
    public static IdComparator getComparatorVoorBerichtPersoon() {
        return getStandaardComparator();
    }

    /**
     * Geeft de comparator voor PersoonReisdocument.
     *
     * @return De comparator voor PersoonReisdocument.
     */
    public static IdComparator getComparatorVoorPersoonReisdocument() {
        return getStandaardComparator();
    }

    /**
     * Geeft de comparator voor PersoonAdres.
     *
     * @return De comparator voor PersoonAdres.
     */
    public static IdComparator getComparatorVoorPersoonAdres() {
        return getStandaardComparator();
    }

    /**
     * Geeft de comparator voor MultirealiteitRegel.
     *
     * @return De comparator voor MultirealiteitRegel.
     */
    public static IdComparator getComparatorVoorMultirealiteitRegel() {
        return getStandaardComparator();
    }

    /**
     * Geeft de comparator voor PersoonAfnemerindicatie.
     *
     * @return De comparator voor PersoonAfnemerindicatie.
     */
    public static IdComparator getComparatorVoorPersoonAfnemerindicatie() {
        return getStandaardComparator();
    }

    /**
     * Geeft de comparator voor PersoonVerstrekkingsbeperking.
     *
     * @return De comparator voor PersoonVerstrekkingsbeperking.
     */
    public static IdComparator getComparatorVoorPersoonVerstrekkingsbeperking() {
        return getStandaardComparator();
    }

    /**
     * Geeft de comparator voor GegevenInOnderzoek.
     *
     * @return De comparator voor GegevenInOnderzoek.
     */
    public static IdComparator getComparatorVoorGegevenInOnderzoek() {
        return getStandaardComparator();
    }

    /**
     * Geeft de comparator voor PartijOnderzoek.
     *
     * @return De comparator voor PartijOnderzoek.
     */
    public static IdComparator getComparatorVoorPartijOnderzoek() {
        return getStandaardComparator();
    }

    /**
     * Geeft de comparator voor StapelRelatie.
     *
     * @return De comparator voor StapelRelatie.
     */
    public static IdComparator getComparatorVoorStapelRelatie() {
        return getStandaardComparator();
    }

    /**
     * Geeft de comparator voor StapelVoorkomen.
     *
     * @return De comparator voor StapelVoorkomen.
     */
    public static VolgnummerComparator getComparatorVoorStapelVoorkomen() {
        return new VolgnummerComparator();
    }

    /**
     * Geeft de comparator voor GegevenInTerugmelding.
     *
     * @return De comparator voor GegevenInTerugmelding.
     */
    public static IdComparator getComparatorVoorGegevenInTerugmelding() {
        return getStandaardComparator();
    }

    /**
     * Geeft de comparator voor Afleverwijze.
     *
     * @return De comparator voor Afleverwijze.
     */
    public static IdComparator getComparatorVoorAfleverwijze() {
        return getStandaardComparator();
    }
}
