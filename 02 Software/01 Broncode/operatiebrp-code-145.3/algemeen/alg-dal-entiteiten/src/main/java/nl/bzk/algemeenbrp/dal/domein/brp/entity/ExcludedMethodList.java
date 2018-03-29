/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class voor het testen van delegate classes.
 */
final class ExcludedMethodList {

    /**
     * Lijst met methodes die niet moeten worden getest.
     */
    static final List<String> EXCLUDED_METHODS = Collections.unmodifiableList(Arrays.asList(
            "convertLongNaarInteger",
            "convertIntegerNaarLong",
            "wait",
            "equals",
            "hashCode",
            "getClass",
            "notify",
            "notifyAll",
            "bestaatVerwantschap",
            "isVoortgekomenUitNietActueelVoorkomen",
            "setVoortgekomenUitNietActueelVoorkomen",
            "kopieerGegevenInOnderzoekVoorNieuwGegeven"
    ));

    /**
     * Constructor.
     */
    private ExcludedMethodList() {
        throw new IllegalStateException("Class mag niet worden geïnstantieerd");
    }

}
