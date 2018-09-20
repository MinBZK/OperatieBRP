/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

/**
 * Het 'element' is het kern begrip in het meta model.
 * Hier toegespitst op de elementen die we onderkennen in het operationele model.
 *
 * Element valt uiteen in verschillende soorten elementen zoals objecttype, groepen, attributen, bedrijfsregels etc...
 *
 * De constructie is gebruikt om een volledige catalogus voor meta-data-gedreven ontwikkeling mogelijk te maken voor de
 * BRP.
 *
 * Voor de constructie van 'gegevens in onderzoek' en 'verantwoording' zijn de elementen uit het operationele model
 * nodig. Vooralsnog is element beperkt tot die groep.
 * Uitbreiden tot meer is mogelijk:
 * Er komt dan wellicht een attribuut bij ( "Laag" ) die verwijst naar een stamtabel met waarden S (set), L(Logisch
 * model), B(Set �n Logisch model) of O(Operationeel model).
 * Deze uitbreiding is echter nog niet gemodelleerd; onduidelijk hoe dit precies zou moeten gelden.
 * RvdP 14 november 2011.
 *
 *
 *
 */
public enum Element {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", null, null);

    private final String       naam;
    private final SoortElement soort;
    private final Element      ouder;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor Element
     * @param soort Soort voor Element
     * @param ouder Ouder voor Element
     */
    private Element(final String naam, final SoortElement soort, final Element ouder) {
        this.naam = naam;
        this.soort = soort;
        this.ouder = ouder;
    }

    /**
     * Retourneert Naam van Element.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Soort van Element.
     *
     * @return Soort.
     */
    public SoortElement getSoort() {
        return soort;
    }

    /**
     * Retourneert Ouder van Element.
     *
     * @return Ouder.
     */
    public Element getOuder() {
        return ouder;
    }

}
