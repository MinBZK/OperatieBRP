/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.xsd;


/** Java model representatie van een XSD element. */
public class XsdElement {

    /** Constante die aangeeft wat het getal is dat gebruikt wordt voor 'unbounded'. */
    public static final int UNBOUNDED = Integer.MAX_VALUE;

    private String  naam;
    private String  type;
    private boolean nillable;
    private int     minOccurs;
    private int     maxOccurs;

    /**
     * Basale constructor voor een XSD element dat gelijk de naam en het (basis) type zet.
     *
     * @param naam de naam van het element.
     * @param type het type van het element.
     */
    public XsdElement(final String naam, final String type) {
        this(naam, type, false);
    }

    /**
     * Constructor voor een XSD element dat gelijk de naam en het (basis) type zet en op basis van het al dan niet
     * verplicht zijn ook de andere properties zet.
     *
     * @param naam de naam van het element.
     * @param type het type van het element.
     * @param verplicht of het element verplicht is of niet.
     */
    public XsdElement(final String naam, final String type, final boolean verplicht) {
        this(naam, type, verplicht, false);
    }

    /**
     * Constructor voor een XSD element dat gelijk de naam en het (basis) type zet en op basis van het al dan niet
     * verplicht zijn en het al dan niet 'unbounded' zijn ook de andere properties zet.
     *
     * @param naam de naam van het element.
     * @param type het type van het element.
     * @param verplicht of het element verplicht is of niet.
     * @param unbounded of het element 'unbounded' is of niet.
     */
    public XsdElement(final String naam, final String type, final boolean verplicht, final boolean unbounded) {
        this(naam, type, !verplicht, getMinOccurs(verplicht), getMaxOccurs(unbounded));
    }

    /**
     * Constructor voor een XSD element dat gelijk alle properties zet.
     *
     * @param naam de naam van het element.
     * @param type het type van het element.
     * @param nillable of het element op nil gezet kan worden of niet.
     * @param minOccurs het minimaal aantal keer dat het element voor dient te komen.
     * @param maxOccurs het maximaal aantal keer dat het element mag voorkomen.
     */
    public XsdElement(final String naam, final String type,
        final boolean nillable, final int minOccurs, final int maxOccurs)
    {
        this.naam = naam;
        this.type = type;
        this.nillable = nillable;
        this.minOccurs = minOccurs;
        this.maxOccurs = maxOccurs;
    }

    /**
     * Retourneert het minimaal aantal keer dat dit element moet voorkomen op basis van het al dan niet verplicht zijn
     * van een element. Indien een element verplicht is dient het dus minimaal 1 keer voor te komen en anders 0 keer.
     *
     * @param verplicht of het element verplicht is of niet.
     * @return het minimaal aantal keer dat het element moet voorkomen.
     */
    private static int getMinOccurs(final boolean verplicht) {
        int minOccurs = 0;
        if (verplicht) {
            minOccurs = 1;
        }
        return minOccurs;
    }

    /**
     * Retourneert het maximaal aantal keer dat dit element mag voorkomen op basis van het al dan niet 'unbounded'
     * zijn van een element. Indien een element 'unbounded' is kan het dus een onbeperkt aantal keer voor komen en
     * anders 1 keer.
     *
     * @param unbounded of het element onbeperkt is of niet.
     * @return het maximaal aantal keer dat het element mag voorkomen.
     */
    private static int getMaxOccurs(final boolean unbounded) {
        int maxOccurs = 1;
        if (unbounded) {
            maxOccurs = Integer.MAX_VALUE;
        }
        return maxOccurs;
    }

    /**
     * Retourneert de naam van het XSD element.
     *
     * @return de naam van het XSD element.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert het (basis) type van het XSD element.
     *
     * @return het type van het XSD element.
     */
    public String getType() {
        return type;
    }

    /**
     * Retourneert of het XSD element 'nillable' is, dus of het de waarde 'null' kan hebben.
     *
     * @return of het XSD element 'nillable' is.
     */
    public boolean isNillable() {
        return nillable;
    }

    /**
     * Retourneert het minimaal aantal keer dat het XSD element moet voorkomen.
     *
     * @return het minimaal aantal keer dat het XSD element moet voorkomen.
     */
    public int getMinOccurs() {
        return minOccurs;
    }

    /**
     * Retourneert het maximaal aantal keer dat het XSD element mag voorkomen.
     *
     * @return het maximaal aantal keer dat het XSD element mag voorkomen.
     */
    public int getMaxOccurs() {
        return maxOccurs;
    }

}
