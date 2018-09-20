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
    ATTRIBUUT_TYPEN("binding/basis", "attribuuttypen"),
    /** Binding voor basis typen. */
    BASIS_TYPEN("binding/basis", "basistypen"),
    /** Binding voor groepen in bericht. */
    GROEPEN_BERICHT("binding/bijhouden", "groepen-bericht"),
    /** Binding voor object typen in bericht. */
    OBJECT_TYPEN_BERICHT("binding/bijhouden", "objecttypen-bericht"),
    /** Extra handmatige binding voor niet BMR delen. */
    EXTRA_HANDMATIGE_BINDING("binding/basis", "handmatige-extras"),
    /** Binding voor objecttypen t.b.v. leveringen. */
    LEVERING_OBJECTTYPEN("binding/leveren", "levering-objecttypen"),
    /** Binding voor objecttypen t.b.v. Bevraging t.b.v. bijhouding. */
    BIJHOUDING_BEVRAGING_OBJECTTYPEN("binding/bevragen/bijhouding", "bevraging-objecttypen"),
    /** Binding voor historie objecttypen t.b.v. bevraging t.b.v. bijhouding. */
    BIJHOUDING_BEVRAGING_HIS_OBJECTTYPEN("binding/bevragen/bijhouding", "bevraging-objecttypen-historie"),
    /** Binding voor objecttypen t.b.v. Bevraging t.b.v. leveren. */
    LEVERING_BEVRAGING_OBJECTTYPEN("binding/bevragen/levering", "bevraging-objecttypen"),
    /** Binding voor historie objecttypen t.b.v. bevraging t.b.v. leveren. */
    LEVERING_BEVRAGING_HIS_OBJECTTYPEN("binding/bevragen/levering", "bevraging-objecttypen-historie"),
    /** Binding voor historie objecttypen t.b.v. leveringen. */
    LEVERING_HIS_OBJECTTYPEN("binding/leveren", "levering-objecttypen-historie"),
    /** Nu nog met de hand gemaakte binding voor stamgegevens. */
    OBJECTTYPEN_STATISCH("binding/basis", "objecttypen-statisch-model"),
    /** Met de hand gemaakte binding voor Materiele en Formele historie entiteit. */
    HISTORIE("binding/basis", "historie"),
    /** Met de hand gemaakte binding voor Materiele en Formele historie entiteit. */
    HISTORIE_MET_VERANTWOORDING("binding/basis", "historie-verantwoording"),
    /** Binding voor synchronisatie stamgegevens. */
    SYNCHRONISATIE_STAMGEGEVENS("binding/leveren", "levering-synchronisatie-stamgegevens");

    private String map;
    private String naam;

    /**
     * Nieuwe jibx binding.
     * Private, zodat er geen compleet nieuwe namen aangemaakt kunnen worden.
     * @param map map naar de binding file
     * @param naam de binding naam
     */
    private JibxBinding(final String map, final String naam) {
        this.map = map;
        this.naam = naam;
    }

    public String getMap() {
        return map;
    }

    public String getNaam() {
        return this.naam;
    }

}
