/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitoring;


/**
 * Class om de telling van fouten bij te houden.
 *
 */
public class Fouten {

    private volatile int soapfault;
    private volatile int foutenInfo;
    private volatile int foutenWaarschuwing;
    private volatile int foutenFout;
    private volatile int foutenSysteem;

    /**
     * Verhoog de teller voor soap fault.
     */
    public synchronized void telOpSoapFault() {
        soapfault++;
    }

    /**
     * Verhoog de teller voor fouten niveau Info.
     */
    public synchronized void telOpFoutenInfo() {
        foutenInfo++;
    }

    /**
     * Verhoog de teller voor fouten niveau Waarschuwing.
     */
    public synchronized void telOpFoutenWaarschuwing() {
        foutenWaarschuwing++;
    }

    /**
     * Verhoog de teller voor fouten niveau Fout.
     */
    public synchronized void telOpFoutenFout() {
        foutenFout++;
    }

    /**
     * Verhoog de teller voor fouten niveau Systeem.
     */
    public synchronized void telOpFoutenSysteem() {
        foutenSysteem++;
    }

    public int getSoapfault() {
        return soapfault;
    }

    public int getFoutenInfo() {
        return foutenInfo;
    }

    public int getFoutenWaarschuwing() {
        return foutenWaarschuwing;
    }

    public int getFoutenFout() {
        return foutenFout;
    }

    public int getFoutenSysteem() {
        return foutenSysteem;
    }

    /**
     * Telling op null zetten.
     */
    public void reset() {
        soapfault = 0;
        foutenInfo = 0;
        foutenWaarschuwing = 0;
        foutenFout = 0;
        foutenSysteem = 0;
    }
}
