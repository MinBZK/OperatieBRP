/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

/**
 * De Class BerichtKenmerken.
 */
public class BerichtKenmerken {

    /** De verzendende partij. */
    private Gemeente verzendendePartij;

    /** De burger zaken module naam. */
    private String burgerZakenModuleNaam;

    /** De is prevalidatie. */
    private boolean isPrevalidatie;

    /**
     * Instantieert een nieuwe bericht kenmerken.
     */
    public BerichtKenmerken() {
    }

    /**
     * Instantieert een nieuwe bericht kenmerken.
     *
     * @param verzendendePartij de verzendende partij
     * @param burgerZakenModuleNaam de burger zaken module naam
     */
    public BerichtKenmerken(final Gemeente verzendendePartij, final String burgerZakenModuleNaam) {
        this.verzendendePartij = verzendendePartij;
        this.burgerZakenModuleNaam = burgerZakenModuleNaam;
    }

    /**
     * Haalt een verzendende partij op.
     *
     * @return verzendende partij
     */
    public Gemeente getVerzendendePartij() {
        return verzendendePartij;
    }

    /**
     * Instellen van verzendende partij.
     *
     * @param verzendendePartij de nieuwe verzendende partij
     */
    public void setVerzendendePartij(final Gemeente verzendendePartij) {
        this.verzendendePartij = verzendendePartij;
    }

    /**
     * Haalt een burger zaken module naam op.
     *
     * @return burger zaken module naam
     */
    public String getBurgerZakenModuleNaam() {
        return burgerZakenModuleNaam;
    }

    /**
     * Instellen van burger zaken module naam.
     *
     * @param burgerZakenModuleNaam de nieuwe burger zaken module naam
     */
    public void setBurgerZakenModuleNaam(final String burgerZakenModuleNaam) {
        this.burgerZakenModuleNaam = burgerZakenModuleNaam;
    }

    /**
     * Controleert of de waarde gelijk is aan prevalidatie.
     *
     * @return true, als waarde gelijk is aan prevalidatie
     */
    public boolean isPrevalidatie() {
        return isPrevalidatie;
    }

    /**
     * Instellen van prevalidatie.
     *
     * @param isPrevalidatieParam de nieuwe prevalidatie
     */
    public void setPrevalidatie(final boolean isPrevalidatieParam) {
        isPrevalidatie = isPrevalidatieParam;
    }

}
