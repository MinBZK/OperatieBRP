/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.service.impl;

import java.util.List;
import org.springframework.jmx.export.annotation.ManagedMetric;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.support.MetricType;
import org.springframework.stereotype.Component;

/**
 * Bean welke JMX exposed wordt om informatie te verschaffen over het aantal
 * successvol en mislukte leveringen.
 */
@Component
@ManagedResource(
    objectName = "nl.bzk.brp:name=VezondenBerichten",
    description = "Metriek voor bijhouden geleverde / niet geleverde berichten van de verzend module")
public final class VerzendTellerJMXBean {

    private List<VerwerkContext> verwerkContextList;

    /**
     * @return het aantal mislukte leveringen
     */
    @ManagedMetric(category = "metrieken", displayName = "Aantal mislukte leveringen",
        description = "Aantal mislukte leveringen",
        metricType = MetricType.COUNTER, unit = "berichten")
    public int getAantalErrorGeleverdeBerichten() {
        int aantal = 0;
        for (final VerwerkContext verwerkContext : verwerkContextList) {
            aantal += verwerkContext.getError();
        }
        return aantal;
    }

    /**
     * @return het aantal gelukte leveringen
     */
    @ManagedMetric(category = "metrieken", displayName = "Aantal geleverde berichten",
        description = "Aantal geleverde berichten",
        metricType = MetricType.COUNTER, unit = "berichten")
    public int getAantalGeleverdeBerichten() {

        int aantalGeleverdeBerichten = 0;
        for (final VerwerkContext verwerkContext : verwerkContextList) {
            aantalGeleverdeBerichten += verwerkContext.getSucces();
        }
        return aantalGeleverdeBerichten;
    }

    /**
     * @return het aantal gelukte leveringen
     */
    @ManagedMetric(category = "metrieken", displayName = "Doorlooptijd protocolleerstap",
        description = "Doorlooptijd protocolleerstap",
        metricType = MetricType.GAUGE, unit = "msec")
    public double getGemiddeldeTijdProtocollering() {

        int totaalProtocolleerTijd = 0;
        int totaalProtocolleringen = 0;
        for (final VerwerkContext verwerkContext : verwerkContextList) {
            totaalProtocolleerTijd += verwerkContext.getProtocolleerTijdTotaal();
            totaalProtocolleringen += verwerkContext.getAantalProtocolleringen();
        }
        return ((double) totaalProtocolleerTijd) / totaalProtocolleringen;
    }

    /**
     * @return het aantal gelukte leveringen
     */
    @ManagedMetric(category = "metrieken", displayName = "Doorlooptijd archiveerstap",
        description = "Doorlooptijd archiveerstap",
        metricType = MetricType.GAUGE, unit = "msec")
    public double getGemiddeldeTijdArchivering() {

        int totaalArchiveerTijd = 0;
        int totaalArchviveringen = 0;
        for (final VerwerkContext verwerkContext : verwerkContextList) {
            totaalArchiveerTijd += verwerkContext.getArchiveerTijdTotaal();
            totaalArchviveringen += verwerkContext.getAantalArchiveringen();
        }
        return ((double) totaalArchiveerTijd) / totaalArchviveringen;
    }

    /**
     * @return het aantal gelukte leveringen
     */
    @ManagedMetric(category = "metrieken", displayName = "Doorlooptijd verzendstap",
        description = "Doorlooptijd verzenden geleverde berichten",
        metricType = MetricType.GAUGE, unit = "msec")
    public double getGemiddeldeTijdVerzending() {

        int totaalVerzendTijd = 0;
        int totaalBerichten = 0;
        for (final VerwerkContext verwerkContext : verwerkContextList) {
            totaalVerzendTijd += verwerkContext.getVerzendTijdTotaal();
            totaalBerichten += verwerkContext.getAantalVerzendingen();
        }
        return ((double) totaalVerzendTijd) / totaalBerichten;
    }


    /**
     * @return het aantal gelukte leveringen
     */
    @ManagedMetric(category = "metrieken", displayName = "Totale doorlooptijd",
        description = "Totale doorlooptijd",
        metricType = MetricType.GAUGE, unit = "msec")
    public double getGemiddeldeTijdVerwerking() {
        int totaalTijd = 0;
        int totaalBerichten = 0;
        for (final VerwerkContext verwerkContext : verwerkContextList) {
            totaalTijd += verwerkContext.getTotaalTijd();
            totaalBerichten += verwerkContext.getSucces();
        }
        return ((double) totaalTijd) / totaalBerichten;
    }

    public void setVerwerkContextList(final List<VerwerkContext> verwerkContextList) {
        this.verwerkContextList = verwerkContextList;
    }

}
