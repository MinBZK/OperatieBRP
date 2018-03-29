/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.model;

import java.io.Serializable;

/**
 * Verzender.
 */

public class Verzender implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long instantiecode;
    private Long verzendendeInstantiecode;

    /**
     * Geeft de instantie code.
     * @return de instantie code
     */
    public Long getInstantiecode() {
        return instantiecode;
    }

    /**
     * Zet de instantie code.
     * @param instantiecode De te zetten instantie code
     */
    public void setInstantiecode(final Long instantiecode) {
        this.instantiecode = instantiecode;
    }

    /**
     * Geeft de verzendende instantie code.
     * @return de verzendende instantie code
     */
    public Long getVerzendendeInstantiecode() {
        return verzendendeInstantiecode;
    }

    /**
     * Zet de verzenden instantie code.
     * @param verzendendeInstantiecode De te zetten verzendende instantie code
     */
    public void setVerzendendeInstantiecode(final Long verzendendeInstantiecode) {
        this.verzendendeInstantiecode = verzendendeInstantiecode;
    }

}
