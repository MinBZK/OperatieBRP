/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx.common;

/**
 * Een jibx binding die gegenereerd wordt met zijn binding naam.
 */
public enum JibxBinding {

    /** Binding voor attribuut typen. */
    ATTRIBUUT_TYPEN("attribuuttypen"),
    /** Binding voor basis typen. */
    BASIS_TYPEN("basistypen"),
    /** Binding voor groepen in bericht. */
    GROEPEN_BERICHT("groepen-bericht"),
    /** Binding voor object typen in bericht. */
    OBJECT_TYPEN_BERICHT("objecttypen-bericht"),
    /** Extra handmatige binding voor niet BMR delen. */
    EXTRA_HANDMATIGE_BINDING("handmatige-extras");

    private String naam;

    /**
     * Nieuwe jibx binding.
     * Private, zodat er geen compleet nieuwe namen aangemaakt kunnen worden.
     *
     * @param naam de binding naam
     */
    private JibxBinding(final String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return this.naam;
    }

}
