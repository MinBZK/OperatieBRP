/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConverterResultaat {

    private int aantalBestandenGeconverteerd;
    private Map<ConverterCase, List<ResultaatRegel>> casesOccured;

    public ConverterResultaat() {
        this.aantalBestandenGeconverteerd = 0;
        this.casesOccured = new HashMap<ConverterCase, List<ResultaatRegel>>();
        for (ConverterCase converterCase : ConverterCase.values()) {
            this.casesOccured.put(converterCase, new ArrayList<ResultaatRegel>());
        }
    }
    
    public void bestandGeconverteerd() {
        this.aantalBestandenGeconverteerd++;
    }

    public void caseOccurred(final ConverterCase converterCase,
            final String xpath) {
        caseOccurred(converterCase, xpath, null);
    }

    public void caseOccurred(final ConverterCase converterCase,
            final String xpath, final String waarde) {
        this.casesOccured.get(converterCase).add(new ResultaatRegel(converterCase, xpath, waarde));
    }

    public int getAantalBestandenGeconverteerd() {
        return this.aantalBestandenGeconverteerd;
    }

    public List<ResultaatRegel> getResultaten(final ConverterCase converterCase) {
        return this.casesOccured.get(converterCase);
    }

    public int getAantalResultaten(final ConverterCase converterCase) {
        return this.getResultaten(converterCase).size();
    }

}
