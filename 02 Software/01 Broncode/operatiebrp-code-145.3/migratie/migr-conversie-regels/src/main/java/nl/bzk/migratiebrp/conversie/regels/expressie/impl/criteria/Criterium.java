/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria;

/**
 * Een criterium waaraan het gezocht moet voldoen.
 */
public class Criterium {

    private final String element;
    private final Operator operator;
    private final String waarde;
    private final boolean datumWaarde;

    /**
     * Constructor.
     * @param element element waarvoor criterium geldt
     * @param operator hoe waarde moet worden vergeleken
     * @param waarde waarde waartegen element bekeken wordt
     */
    public Criterium(final String element, final Operator operator, final String waarde) {
        this(element, operator, waarde, false);
    }

    /**
     * Constructor.
     * @param element element waarvoor criterium geldt
     * @param operator hoe waarde moet worden vergeleken
     * @param waarde waarde waartegen element bekeken wordt
     * @param datumWaarde indicatie dat de waarde een datum is. Nodig vanwege verschillende separator waarden in expressie en zoeken
     */
    public Criterium(final String element, final Operator operator, final String waarde, final boolean datumWaarde) {
        this.element = element;
        this.operator = operator;
        this.waarde = waarde;
        this.datumWaarde = datumWaarde;
    }


    /**
     * @return criterium als brp expressie string
     */
    public final String getExpressieString() {
        return operator.apply(element, waarde);
    }

    /**
     * @return element van criterium
     */
    public final String getElement() {
        return this.element;
    }

    /**
     * @return operator van criterium
     */
    public final Operator getOperator() {
        return this.operator;
    }

    /**
     * Geeft waarde terug. Van de waarde worden openings en sluitings " verwijderd.
     * Dit is speciaal voor scheidingsteken omdat deze later wordt bijgevoegd.
     * @return waarde van criterium
     */
    public final String getWaarde() {
        if (waarde == null) {
            return waarde;
        } else {
            return this.waarde.replaceAll("^\"", "").replaceAll("\"$", "");
        }
    }

    /**
     * @return indicatie of waarde een datum betreft
     */
    public boolean isDatumWaarde() {
        return datumWaarde;
    }
}
