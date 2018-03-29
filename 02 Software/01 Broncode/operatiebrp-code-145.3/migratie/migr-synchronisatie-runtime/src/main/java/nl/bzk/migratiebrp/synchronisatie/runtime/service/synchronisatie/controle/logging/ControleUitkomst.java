/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging;

/**
 * Mogelijke uitkomsten van de beslisboom uc202.
 */
public enum ControleUitkomst {

    /**
     * resultaat toevoegen.
     */
    TOEVOEGEN("Toevoegen"),

    /**
     * resultaat vervangen.
     */
    VERVANGEN("Vervangen"),

    /**
     * resultaat negeren.
     */
    NEGEREN("Negeren"),

    /**
     * resultaat afkeuren.
     */
    AFKEUREN("Afkeuren"),

    /**
     * resultaat onduidelijk.
     */
    ONDUIDELIJK("Onduidelijk");

    private final String resultaat;

    /**
     * Default constructor.
     * @param resultaat Het resultaat.
     */
    ControleUitkomst(final String resultaat) {
        this.resultaat = resultaat;
    }

    public String getResultaat() {
        return resultaat;
    }
}
