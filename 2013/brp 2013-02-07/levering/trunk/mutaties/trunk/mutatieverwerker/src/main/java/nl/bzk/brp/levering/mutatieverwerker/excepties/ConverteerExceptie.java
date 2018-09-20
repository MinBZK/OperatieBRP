/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.excepties;


/**
 * Exceptie die aangeeft dat een (in een bepaalde situatie) data niet juist kon worden geconverteerd.
 * Het gaat hierbij om bijvoorbeeld over het converteren van PersoonHisVolledig naar Persoon via een peilmoment.
 */
public class ConverteerExceptie extends RuntimeException {

    /**
     * Standaard constructor die het bericht zet van de exceptie.
     * @param message het fout bericht.
     */
    public ConverteerExceptie(final String message) {
        super(message);
    }
}

