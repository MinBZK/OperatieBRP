/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.schedular.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


/**
 * Waarde van een cell.
 *
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Value {

    private Object v;
    private String f;

    /**
     * Standaard constructor van een cell waarde.
     *
     * @param waarde kan een string zijn of een getal
     */
    public Value(final Object waarde) {
        v = waarde;
    }

    /**
     * Constructor voor een waarde met string respesentatie.
     *
     * @param waarde waarde
     * @param representatie de representatie van de waarde
     */
    public Value(final Object waarde, final String representatie) {
        v = waarde;
        f = representatie;
    }

    public Object getV() {
        return v;
    }

    public void setV(final Object v) {
        this.v = v;
    }

    public String getF() {
        return f;
    }

    public void setF(final String f) {
        this.f = f;
    }
}
