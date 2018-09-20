/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.test.controller.form;


public class ZoekTimingResult {

    private long tokenizing = 0;
    private long parsing = 0;
    
    /**
     * @return the tokenizing
     */
    public long getTokenizing() {
        return tokenizing;
    }
    
    /**
     * @param tokenizing the tokenizing to set
     */
    public void setTokenizing(long tokenizing) {
        this.tokenizing = tokenizing;
    }
    
    /**
     * @return the parsing
     */
    public long getParsing() {
        return parsing;
    }
    
    /**
     * @param parsing the parsing to set
     */
    public void setParsing(long parsing) {
        this.parsing = parsing;
    }
    
    /**
     * @return the total
     */
    public long getTotal() {
        return tokenizing + parsing;
    }
    
}
