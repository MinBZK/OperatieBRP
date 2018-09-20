/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitoring;

/**
 * Class voor het bijhouden van monitoring data.
 *
 */
public class BerichtenMBean {

    private volatile int inkomendeBerichten;
    private volatile int uitgaandeBerichten;
    private volatile int soapfault;
    private volatile int foutenInfo;
    private volatile int foutenWaarschuwing;
    private volatile int foutenFout;
    private volatile int foutenSysteem;
    private volatile int gemiddeldeResponseTijd;
    private volatile int laatsteResponseTijd;

    /**
     * Verhoog de teller voor inkomenende berichten.
     */
    public synchronized void telOpInkomendeBericht() {
        inkomendeBerichten++;
    }

    /**
     * Verhoog de teller voor uitgaande berichten.
     */
    public synchronized void telOpUitgaandeBericht() {
        uitgaandeBerichten++;
    }

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

    /**
     * Sla de gemiddelde response tijd op.
     *
     * @param responseTijd de response tijd
     */
    public synchronized void telOpBerekenGemiddeldeResponseTijd(final int responseTijd) {
        laatsteResponseTijd = responseTijd;
        if (gemiddeldeResponseTijd == 0) {
            gemiddeldeResponseTijd = laatsteResponseTijd;
        } else {
            gemiddeldeResponseTijd = (gemiddeldeResponseTijd + laatsteResponseTijd) / 2;
        }
    }

    public int getInkomendeBerichten() {
        return inkomendeBerichten;
    }

    public int getUitgaandeBerichten() {
        return uitgaandeBerichten;
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

    public int getGemiddeldeResponseTijd() {
        return gemiddeldeResponseTijd;
    }

    public int getLaatsteReponseTijd() {
        return laatsteResponseTijd;
    }

    /**
     * Alle tellers op 0 zetten.
     */
    public void reset() {
        inkomendeBerichten = 0;
        uitgaandeBerichten = 0;
        soapfault = 0;
        foutenInfo = 0;
        foutenWaarschuwing = 0;
        foutenFout = 0;
        foutenSysteem = 0;
        gemiddeldeResponseTijd = 0;
        laatsteResponseTijd = 0;
    }
}
