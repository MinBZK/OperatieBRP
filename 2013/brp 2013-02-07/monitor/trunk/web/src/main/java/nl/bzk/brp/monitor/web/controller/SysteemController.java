/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.inject.Inject;

import nl.bzk.brp.monitor.schedular.Data;
import nl.bzk.brp.monitor.schedular.DataBuffer;
import nl.bzk.brp.monitor.schedular.DataVerzamelaar;
import nl.bzk.brp.monitor.schedular.ResponseTijd;
import nl.bzk.brp.monitor.schedular.model.Row;
import nl.bzk.brp.monitor.schedular.model.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;


/**
 * Controller voor de systeem monitor scherm.
 */
@Controller
@RequestMapping("/monitor/")
@SessionAttributes({ "berichtenVerkeer", "actieveThreads", "dbConnecties", "geheugenGebruik", "responseTijden" })
public class SysteemController {

    private static final long                serialVersionUID   = -7279642573425018330L;

    private static final Logger              LOG                = LoggerFactory.getLogger(SysteemController.class);

    /** Waarde om van bytes om te zetten naar Megabytes. */
    private static final int                 MB                 = 1048576;

    /** 1000 ms is 1 seconden. **/
    private static final int                 SECONDEN           = 1000;

    private static final String              TIJD_FORMAAT       = "HH:mm:ss";

    @Inject
    private DataVerzamelaar                  dataVerzamelaar;

    private static final int                 MAX_BUFFER_GROOTTE = 14400;

    private final Queue<Data<Long, Integer>> berichtenPerSec    = new DataBuffer<Data<Long, Integer>>(
                                                                        MAX_BUFFER_GROOTTE);

    /**
     * Open de systeem monitor scherm.
     *
     * @param modelMap
     *            ModelMap
     * @return monitor scherm
     * @throws Exception exception
     */
    @RequestMapping(value = "systeem.html", method = RequestMethod.GET)
    public String openSysteemDashboard(final ModelMap modelMap) throws Exception {
        return "monitor/systeem";
    }

    /**
     * Reset de tellers.
     *
     * @return true als het gelukt is.
     */
    @RequestMapping(value = "reset.html", method = RequestMethod.GET)
    @ResponseBody
    public Boolean resetTellers() {
        return dataVerzamelaar.resetTellers();
    }

    /**
     * Geeft data terug voor systeem dashboard.
     *
     * @param interval de gebruikte refresh interval van de pagina
     * @param modelMap modelMap wordt gebruikt om de timestamp op te slaan van de laatste item
     * @return data in json formaat
     */
    @RequestMapping(value = "updatesysteem.json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSysteemDashboardData(@RequestParam(value = "interval") final String interval,
            final ModelMap modelMap)

    {
        // TODO soms is er een foutmelding dat de sessie niet aangemaakt kan worden, de exceptie moet op een of andere
        // manier weergegeven worden aan op het scherm zodat de gebruiker de browser kan verversen

        int intervalInt = Integer.parseInt(interval);

        Map<String, Object> data = new HashMap<String, Object>();

        data.put("aantalUitgaandeBerichten", getUitgaandeBerichten());
        data.put("aantalBerichtenPerSeconden", getAantalBerichtenPerSeconden());
        data.put(
                "berichtenVerkeer",
                maakRijen(dataVerzamelaar.getBerichtenInVerwerking().toArray(), intervalInt, modelMap,
                        "berichtenVerkeer"));
        data.put("responseTijdLaatsteBericht", getBerichtResponseTijd());
        data.put("responseTijden", getResponseTijden(intervalInt, modelMap, "responseTijden"));
        // data.put("dbConnecties",
        // maakRijen(dataVerzamelaar.getDatabaseConnecties().toArray(), intervalInt, modelMap, "dbConnecties"));
        // data.put("geheugenGebruik", getGeheugenGebruik(intervalInt, modelMap, "geheugenGebruik"));

        return data;
    }

    /**
     * Geeft totaal aantal uitgaande berichten.
     *
     * @return getal
     */
    @SuppressWarnings("unchecked")
    private Integer getUitgaandeBerichten() {
        float uitgaand = 0;
        // Haal alleen de laatste item op
        Object[] uitgaandeBerichten = dataVerzamelaar.getUitgaandeBerichten().toArray();
        uitgaand = ((Data<Long, Integer>) uitgaandeBerichten[uitgaandeBerichten.length - 1]).getWaarde()[0];
        return (int) uitgaand;
    }

    /**
     * Geeft de huidige aantal active threads.
     *
     * @return getal
     */
    @SuppressWarnings("unchecked")
    private Integer getThreadLoad() {
        // Haal alleen de laatste item op
        Object[] actieveThreads = dataVerzamelaar.getActieveThreads().toArray();

        return ((Data<Long, Integer>) actieveThreads[actieveThreads.length - 1]).getWaarde()[0];
    }

    /**
     * Geeft data terug van geheugen gebruik.
     *
     * @param interval gebruikte interval om de aantal units terug te geven
     * @param modelMap modelMap wordt gebruikt om de timestamp op te slaan van de laatste item
     * @param timestampId de id waarin de timestamp opgeslagen moet worden
     * @return Lijst van Row
     */
    @SuppressWarnings("unchecked")
    private List<Row> getGeheugenGebruik(final int interval, final ModelMap modelMap, final String timestampId) {
        Object[] geheugenGebruik = dataVerzamelaar.getGeheugenGebruik().toArray();

        long timestamp = 0;
        if (modelMap.get(timestampId) != null) {
            timestamp = (Long) modelMap.get(timestampId);
        }

        List<Row> rijen = new ArrayList<Row>();

        int aantalUnits = interval / DataVerzamelaar.DATA_INTERVAL;

        for (int i = geheugenGebruik.length - aantalUnits; i < geheugenGebruik.length; i++) {
            Row row = new Row();
            Data<Long, Long> data = (Data<Long, Long>) geheugenGebruik[i];

            if (data.getUnit() > timestamp) {
                row.addWaarden(new SimpleDateFormat(TIJD_FORMAAT).format(data.getUnit()));
                row.addWaarden(new Value(data.getWaarde()[0] / MB, Long.toString(data.getWaarde()[0]) + " bytes"));
                row.addWaarden(new Value(data.getWaarde()[1] / MB, Long.toString(data.getWaarde()[1]) + " bytes"));
                rijen.add(row);
                timestamp = data.getUnit();
            }
        }

        // Sla de timestamp op van de laatste item, dit is nodig om te voorkomen dat er bij de volgende data set
        // aanvraag er overlappende items voorkomen
        modelMap.put(timestampId, timestamp);

        return rijen;
    }

    /**
     * Geeft de response tijd data terug van een servlet aanroep en de ingebouwde cxf avgResponseTijd.
     *
     * @param interval gebruikte interval om de aantal units terug te geven
     * @param modelMap modelMap wordt gebruikt om de timestamp op te slaan van de laatste item
     * @param timestampId de id waarin de timestamp opgeslagen moet worden
     * @return Lijst van Row
     */
    @SuppressWarnings("unchecked")
    private List<Row> getResponseTijden(final int interval, final ModelMap modelMap, final String timestampId) {
        Object[] responseTijden = dataVerzamelaar.getResponseTijden().toArray();

        long timestamp = 0;
        if (modelMap.get(timestampId) != null) {
            timestamp = (Long) modelMap.get(timestampId);
        }

        List<Row> rijen = new ArrayList<Row>();

        int aantalUnits = interval / DataVerzamelaar.DATA_INTERVAL;

        if ((responseTijden.length - aantalUnits) >= 0) {
            for (int i = responseTijden.length - aantalUnits; i < responseTijden.length; i++) {
                Row row = new Row();
                Data<Long, ResponseTijd> data = (Data<Long, ResponseTijd>) responseTijden[i];

                if (data.getUnit() > timestamp) {
                    row.addWaarden(new SimpleDateFormat(TIJD_FORMAAT).format(data.getUnit()));
                    // row.addWaarden(new Value(data.getWaarde()[0].getGemiddeldeResponseTijd()));
                    // row.addWaarden(new Value(data.getWaarde()[0].getCxfGemiddeldeResponseTijd()));
                    row.addWaarden(new Value(data.getWaarde()[0].getGemiddeldeResponseTijdPerTijdsEenheid()));
                    rijen.add(row);
                    timestamp = data.getUnit();
                }
            }
        }
        // Sla de timestamp op van de laatste item, dit is nodig om te voorkomen dat er bij de volgende data set
        // aanvraag er overlappende items voorkomen
        modelMap.put(timestampId, timestamp);

        return rijen;
    }

    /**
     * Geeft de gemiddelde responsetijd terug.
     *
     * @return gemiddelde responsetijd
     */
    @SuppressWarnings("unchecked")
    private Integer getBerichtResponseTijd() {
        Object[] responseTijden = dataVerzamelaar.getResponseTijden().toArray();

        return ((Data<Long, ResponseTijd>) responseTijden[responseTijden.length - 1]).getWaarde()[0]
                .getGemiddeldeResponseTijdPerTijdsEenheid();
    }

    /**
     * Geeft het aantal berichten per seconden terug, gebasseed op gemiddelde response tijd.
     *
     * @return aantal berichten per seconden in gehele getallen
     */
    @SuppressWarnings("unchecked")
    private Integer getAantalBerichtenPerSeconden() {
          Object[] uitgaandeBerichten = dataVerzamelaar.getUitgaandeBerichten().toArray();
        float berPerSec = 0;
        int meetMomenten = 10000 / DataVerzamelaar.DATA_INTERVAL;
        Object[] berichtenInVerwerking = dataVerzamelaar.getBerichtenInVerwerking().toArray();
        if (berichtenInVerwerking.length > meetMomenten) {
            float berichtVerschillen = 0;
            for (int i = 0; i < meetMomenten; i++) {
                Data<Long, Integer> biv =
                    (Data<Long, Integer>) berichtenInVerwerking[berichtenInVerwerking.length - (i + 1)];
                berichtVerschillen += biv.getWaarde()[0];
            }
            berPerSec = berichtVerschillen / 10;
        } else {
            int uitgb = ((Data<Long, Integer>) uitgaandeBerichten[uitgaandeBerichten.length - 1]).getWaarde()[0];
            berPerSec = uitgb - dataVerzamelaar.getAantalBerichtenBekend();
        }
        Long timestamp = Calendar.getInstance().getTimeInMillis();
        berichtenPerSec.add(new Data<Long, Integer>(timestamp, (int) berPerSec));

        return (int) berPerSec;
    }

    /**
     * Maak rijen aan.
     *
     * @param datas data
     * @param interval interval
     * @param modelMap modelMap wordt gebruikt om de timestamp op te slaan van de laatste item
     * @param timestampId de id waarin de timestamp opgeslagen moet worden
     * @return Map met de data en de timstamp van de laatste item, data is omslagen in de key "data" en timestamp is
     *         ogelagen in de key "tijdEinde"
     */
    @SuppressWarnings("unchecked")
    private List<Row> maakRijen(final Object[] datas, final int interval, final ModelMap modelMap,
            final String timestampId)
    {
        long timestamp = 0;
        if (modelMap.get(timestampId) != null) {
            timestamp = (Long) modelMap.get(timestampId);
        }

        List<Row> rijen = new ArrayList<Row>();
        int aantalUnits = interval / DataVerzamelaar.DATA_INTERVAL;

        if ((datas.length - aantalUnits) >= 0) {
            for (int i = datas.length - aantalUnits; i < datas.length; i++) {
                Row row = new Row();
                Data<Long, Integer> data = (Data<Long, Integer>) datas[i];

                if (data.getUnit() > timestamp) {
                    row.addWaarden(new SimpleDateFormat(TIJD_FORMAAT).format(data.getUnit()));
                    row.addWaarden(data.getWaarde());
                    rijen.add(row);
                    timestamp = data.getUnit();
                }

                // Sla de timestamp op van de laatste item, dit is nodig om te voorkomen dat er bij de volgende data set
                // aanvraag er overlappende items voorkomen
                modelMap.put(timestampId, timestamp);
            }
        }

        return rijen;
    }

    public Queue<Data<Long, Integer>> getBerichtenPerSec() {
        return berichtenPerSec;
    }
}
