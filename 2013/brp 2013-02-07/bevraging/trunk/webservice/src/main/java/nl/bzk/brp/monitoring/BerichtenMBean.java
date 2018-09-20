/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitoring;

import org.apache.cxf.management.counters.Counter;
import org.apache.cxf.management.counters.CounterRepository;
import org.apache.cxf.management.counters.ResponseTimeCounter;


/**
 * Class voor het bijhouden van monitoring data.
 *
 */
public class BerichtenMBean {

    private static final int  MS_INTERVAL = 1000;

    private CounterRepository counterRepository;

    private volatile int      inkomendeBerichten;
    private volatile int      uitgaandeBerichten;
    private volatile int      soapfault;
    private volatile int      foutenInfo;
    private volatile int      foutenWaarschuwing;
    private volatile int      foutenFout;
    private volatile int      foutenSysteem;
    private volatile int      laatsteResponseTijd;
    private volatile long     totaalVerwerkingstijd;

    private volatile long     totaalVerwerkingsTijdPerTijdsEenHeid;
    private volatile long     uitgaandeBerichtenPerTijdsEenheid;
    private volatile long     timestampStartTijdsEenheid;

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
     * Telt de verwerkingstijd op.
     *
     * @param verwerkingstijd de verwerkingstijd
     */
    public synchronized void telOpVerwerktijd(final int verwerkingstijd) {
        laatsteResponseTijd = verwerkingstijd;
        totaalVerwerkingstijd = totaalVerwerkingstijd + verwerkingstijd;
    }

    /**
     * Telt de berichten met een interval van 1000ms, na 1000ms worden de waarden weer opnieuw geteld.
     *
     * @param verwerkingstijd de verwerkingstijd
     */
    public synchronized void telOpVerwerktijdPerTijdsEenheid(final int verwerkingstijd) {
        long timestamp = System.currentTimeMillis();

        if (timestamp - timestampStartTijdsEenheid > MS_INTERVAL) {
            uitgaandeBerichtenPerTijdsEenheid = 0;
            totaalVerwerkingsTijdPerTijdsEenHeid = 0;
            timestampStartTijdsEenheid = timestamp;
        }

        uitgaandeBerichtenPerTijdsEenheid++;
        totaalVerwerkingsTijdPerTijdsEenHeid = totaalVerwerkingsTijdPerTijdsEenHeid + verwerkingstijd;
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
        return (int) (totaalVerwerkingstijd / uitgaandeBerichten);
    }

    public int getGemiddeldeResponseTijdPerTijdsEenheid() {
        return (int) (totaalVerwerkingsTijdPerTijdsEenHeid / uitgaandeBerichtenPerTijdsEenheid);
    }

    public int getLaatsteReponseTijd() {
        return laatsteResponseTijd;
    }

    public long getTotaalVerwerkingstijd() {
        return totaalVerwerkingstijd;
    }

    public long getUitgaandeBerichtenPerTijdsEenheid() {
        return uitgaandeBerichtenPerTijdsEenheid;
    }

    public long getTotaalVerwerkingsTijdPerTijdsEenHeid() {
        return totaalVerwerkingsTijdPerTijdsEenHeid;
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
        laatsteResponseTijd = 0;
        totaalVerwerkingstijd = 0;
        totaalVerwerkingsTijdPerTijdsEenHeid = 0;
        uitgaandeBerichtenPerTijdsEenheid = 0;

        // Reset tellers in de cxf managed beans
        for (Counter counter : counterRepository.getCounters().values()) {
            if (counter instanceof ResponseTimeCounter) {
                ((ResponseTimeCounter) counter).reset();
            }
        }
    }

    public void setCounterRepository(final CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }
}
