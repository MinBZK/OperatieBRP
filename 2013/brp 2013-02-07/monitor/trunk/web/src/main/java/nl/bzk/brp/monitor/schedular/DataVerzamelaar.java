/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.schedular;

import java.net.ConnectException;
import java.util.Calendar;
import java.util.Queue;

import javax.inject.Inject;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


/**
 * Verzamelt gegevens van de services.
 *
 */
@Service
public class DataVerzamelaar {

    private static final Logger                   LOGGER                = LoggerFactory
                                                                                .getLogger(DataVerzamelaar.class);

    // 60*60*24*10
    private static final int                      MAX_BUFFER_GROOTTE    = 14400;

    private final Queue<Data<Long, Integer>>      inkomendeBerichten    = new DataBuffer<Data<Long, Integer>>(
                                                                                MAX_BUFFER_GROOTTE);

    private final Queue<Data<Long, Integer>>      uitgaandeBerichten    = new DataBuffer<Data<Long, Integer>>(
                                                                                MAX_BUFFER_GROOTTE);

    private final Queue<Data<Long, Integer>>      berichtenInVerwerking = new DataBuffer<Data<Long, Integer>>(
                                                                                MAX_BUFFER_GROOTTE);

    private final Queue<Data<Long, Integer>>      actieveThreads        = new DataBuffer<Data<Long, Integer>>(
                                                                                MAX_BUFFER_GROOTTE);

    private final Queue<Data<Long, Integer>>      databaseConnecties    = new DataBuffer<Data<Long, Integer>>(
                                                                                MAX_BUFFER_GROOTTE);

    private final Queue<Data<Long, Long>>         geheugenGebruik       = new DataBuffer<Data<Long, Long>>(
                                                                                MAX_BUFFER_GROOTTE);

    private final Queue<Data<Long, ResponseTijd>> responseTijd          = new DataBuffer<Data<Long, ResponseTijd>>(
                                                                                MAX_BUFFER_GROOTTE);

    private int aantalBerichtenBekend = 0;

    private int                                   maxThreads            = -1;

    @Inject
    private JmxClient                             jmxClient;

    /** Interval in ms van data units. */
    public static final int                       DATA_INTERVAL         = 1000;

    /**
     * Verzamel data elke 10 miliseconden.
     *
     */
    @Scheduled(fixedDelay = 1000)
    public void verzamelBerichten() throws MalformedObjectNameException, InstanceNotFoundException,
            IntrospectionException, NullPointerException, ReflectionException, AttributeNotFoundException,
            MBeanException
    {
        // if (jmxClient.isConnected()) {
        // Long timestamp = Calendar.getInstance().getTimeInMillis();
        // try {
        // LOGGER.debug("jmxClient: " + jmxClient.getActiveThreads());
        // } catch (Exception e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // try {
        // int inkb = jmxClient.getInkomendeBerichten();
        // inkomendeBerichten.add(new Data<Long, Integer>(timestamp, inkb));
        //
        // int uitgb = jmxClient.getUitgaandeBerichten();
        // uitgaandeBerichten.add(new Data<Long, Integer>(timestamp, uitgb));
        //
        // berichtenInVerwerking.add(new Data<Long, Integer>(timestamp, inkb - uitgb));
        // } catch (ConnectException e) {
        // //Als verbinding halverwege verbroken is probeer opnieuw te verbinden.
        // jmxClient.connect();
        // } catch (Exception e) {
        // LOGGER.error("Er is iets fout gegaan bij het ophalen van data.", e);
        // }
        // } else {
        // jmxClient.connect();
        // }
        if (jmxClient.isConnected()) {
            Long timestamp = Calendar.getInstance().getTimeInMillis();
            int uitgb;
            try {
                uitgb = jmxClient.getUitgaandeBerichten();
                uitgaandeBerichten.add(new Data<Long, Integer>(timestamp, uitgb));
                berichtenInVerwerking.add(new Data<Long, Integer>(timestamp, uitgb - aantalBerichtenBekend));

                aantalBerichtenBekend = jmxClient.getUitgaandeBerichten();
            } catch (Exception e) {
                LOGGER.error("FOUT bij ophalen uitgaande berichten:", e);
            }

        } else {
            jmxClient.connect();
        }
    }

    // @Scheduled(fixedDelay = 10)
    // public void verzamelActieveThreads() throws MalformedObjectNameException, InstanceNotFoundException,
    // IntrospectionException, NullPointerException, ReflectionException, AttributeNotFoundException, MBeanException {
    // if (jmxClient.isConnected()) {
    // Long timestamp = Calendar.getInstance().getTimeInMillis();
    // try {
    // actieveThreads.add(new Data<Long, Integer>(timestamp, jmxClient.getActiveThreads()));
    // } catch (ConnectException e) {
    // //Als verbinding halverwege verbroken is probeer opnieuw te verbinden.
    // jmxClient.connect();
    // } catch (Exception e) {
    // LOGGER.error("Er is iets fout gegaan bij het ophalen van data.", e);
    // }
    //
    // } else {
    // jmxClient.connect();
    // }
    // }

    // @Scheduled(fixedDelay = 10)
    // public void verzamelDatabaseConnecties() throws MalformedObjectNameException, InstanceNotFoundException,
    // IntrospectionException, NullPointerException, ReflectionException, AttributeNotFoundException, MBeanException {
    // if (jmxClient.isConnected()) {
    // Long timestamp = Calendar.getInstance().getTimeInMillis();
    //
    // try {
    // databaseConnecties
    // .add(new Data<Long, Integer>(timestamp, jmxClient.getSqlDataSourceActiveConnections()));
    // } catch (ConnectException e) {
    // //Als verbinding halverwege verbroken is probeer opnieuw te verbinden.
    // jmxClient.connect();
    // } catch (Exception e) {
    // LOGGER.error("Er is iets fout gegaan bij het ophalen van data.", e);
    // }
    //
    // } else {
    // jmxClient.connect();
    // }
    // }

    // @Scheduled(fixedDelay = 10)
    // public void verzamelGeheugenGebruik() throws MalformedObjectNameException, InstanceNotFoundException,
    // IntrospectionException, NullPointerException, ReflectionException, AttributeNotFoundException, MBeanException {
    // if (jmxClient.isConnected()) {
    // Long timestamp = Calendar.getInstance().getTimeInMillis();
    //
    // try {
    // geheugenGebruik.add(new Data<Long, Long>(timestamp, jmxClient.getHeapMemoryUsage(), jmxClient
    // .getNonHeapMemoryUsage()));
    // } catch (ConnectException e) {
    // //Als verbinding halverwege verbroken is probeer opnieuw te verbinden.
    // jmxClient.connect();
    // } catch (Exception e) {
    // LOGGER.error("Er is iets fout gegaan bij het ophalen van data.", e);
    // }
    //
    // } else {
    // jmxClient.connect();
    // }
    // }

    @Scheduled(fixedDelay = 1000)
    public void verzamelResponseTijden() throws MalformedObjectNameException, InstanceNotFoundException,
            IntrospectionException, NullPointerException, ReflectionException, AttributeNotFoundException,
            MBeanException
    {
        if (jmxClient.isConnected()) {
            Long timestamp = Calendar.getInstance().getTimeInMillis();

            try {
                responseTijd.add(new Data<Long, ResponseTijd>(timestamp, jmxClient.getResponseTijden()));
            } catch (ConnectException e) {
                // Als verbinding halverwege verbroken is probeer opnieuw te verbinden.
                jmxClient.connect();
            } catch (Exception e) {
                LOGGER.error("Er is iets fout gegaan bij het ophalen van data.", e);
            }

        } else {
            jmxClient.connect();
        }
    }

    public Queue<Data<Long, Integer>> getInkomendeBerichten() {
        return inkomendeBerichten;
    }

    public Queue<Data<Long, Integer>> getUitgaandeBerichten() {
        return uitgaandeBerichten;
    }

    public Queue<Data<Long, Integer>> getBerichtenInVerwerking() {
        return berichtenInVerwerking;
    }

    public Queue<Data<Long, Integer>> getActieveThreads() {
        return actieveThreads;
    }

    public Queue<Data<Long, Integer>> getDatabaseConnecties() {
        return databaseConnecties;
    }

    public Queue<Data<Long, Long>> getGeheugenGebruik() {
        return geheugenGebruik;
    }

    public Queue<Data<Long, ResponseTijd>> getResponseTijden() {
        return responseTijd;
    }

    /**
     * Geeft the maximale aantal threads terug. Initieel staat de waarde op -1 als er geen waarde opgehaald kan worden.
     *
     * @return maxThreads
     */
    public int getMaxThreads() {
        if (maxThreads == -1) {
            try {
                maxThreads = jmxClient.getMaxThreads();
            } catch (Exception e) {
                LOGGER.error("Er is iets fout gegaan bij het ophalen van data.", e);
            }
        }

        return maxThreads;
    }

    public int getAantalBerichtenBekend() {
        return aantalBerichtenBekend;
    }

    /**
     * Zet alle tellers op 0.
     *
     * @return true als het gelukt
     */
     public boolean resetTellers() {
     try {
     jmxClient.resetTellers();
     return true;
     } catch (Exception e) {
     LOGGER.error("Reset is mislukt, reden: " + e.getMessage());
     return false;
     }
     }
}
