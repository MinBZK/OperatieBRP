/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import java.util.List;

/**
 * Interface voor objecten die voorzien kunnen worden van JavaDoc-commentaar.
 */
public interface JavaDocable {

    /**
     * Haalt de Javadoc op.
     *
     * @return de javadoc als lijst van Strings
     */
    List<String> getJavaDoc();

    /**
     * Set de javadoc voor dit element.
     *
     * @param javaDocVoorObject de javadoc als String
     */
    void setJavaDoc(final String javaDocVoorObject);

}
