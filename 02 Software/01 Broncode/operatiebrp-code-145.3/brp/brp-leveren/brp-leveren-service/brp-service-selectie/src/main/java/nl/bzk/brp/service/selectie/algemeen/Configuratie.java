/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.algemeen;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Configuratie.
 */
@Component
public final class Configuratie {

    /**
     * The constant QUEUE_POLLING_TIMEOUT_MS.
     */
    public static final int QUEUE_POLLING_TIMEOUT_MS = 10;
    private static final int DEFAULT_MAX_WACHTIJD_WACHTTAAK = 30;

    @Value("${brp.selectie.lezer.selectietaak.autorisaties:50}")
    private int autorisatiesPerSelectieTaak;
    @Value("${brp.selectie.lezer.poolsize:3}")
    private int poolSizeBlobBatchProducer;
    @Value("${brp.selectie.lezer.batchsize:250}")
    private int batchSizeBatchProducer;
    //het aantal blobs per selectie taak moet kleiner of gelijk zijn aan de lezer batchsize
    @Value("${brp.selectie.verwerker.selectietaak.blobs:250}")
    private int blobsPerSelectieTaak;
    @Value("${brp.selectie.verwerker.selectietaak.max:100}")
    private int maxSelectieTaak;
    @Value("${brp.selectie.verwerker.selectiebestandenfolder}")
    private String selectiebestandFolder;
    @Value("${brp.selectie.verwerker.poolsize:3}")
    private int verwerkerPoolSize;
    @Value("${brp.selectie.schrijver.selectieschrijftaak.max:100}")
    private int maxSelectieSchrijfTaak;
    @Value("${brp.selectie.schrijver.resultaatfolder}")
    private String berichtResultaatFolder;
    @Value("${brp.selectie.schrijver.concatpartscount:1000}")
    private int concatPartsCount;
    @Value("${brp.selectie.schrijver.poolsize:3}")
    private int schrijverPoolSize;
    @Value("${brp.selectie.max.looptijd:12}")
    private int maxLooptijdSelectierun;

    private long maximaleWachtTijdFragmentVerwerkerMin = TimeUnit.HOURS.toMinutes(1);
    private long wachttijdMaakPersoonsBeeldMin = TimeUnit.MINUTES.toMinutes(1);
    private long maximaleWachttijdPersoonslijstFragmentMin = TimeUnit.MINUTES.toMinutes(1);
    private long maximaleWachttijdWachttaak = TimeUnit.MINUTES.toMinutes(DEFAULT_MAX_WACHTIJD_WACHTTAAK);

    /**
     * Gets pool size blob batch producer.
     * @return the pool size blob batch producer
     */
    public int getPoolSizeBlobBatchProducer() {
        return poolSizeBlobBatchProducer;
    }

    /**
     * Sets pool size blob batch producer.
     * @param poolSizeBlobBatchProducer the pool size blob batch producer
     */
    public void setPoolSizeBlobBatchProducer(int poolSizeBlobBatchProducer) {
        this.poolSizeBlobBatchProducer = poolSizeBlobBatchProducer;
    }

    /**
     * Gets batch size batch producer.
     * @return the batch size batch producer
     */
    public int getBatchSizeBatchProducer() {
        return batchSizeBatchProducer;
    }

    /**
     * Sets batch size batch producer.
     * @param batchSizeBatchProducer the batch size batch producer
     */
    public void setBatchSizeBatchProducer(int batchSizeBatchProducer) {
        this.batchSizeBatchProducer = batchSizeBatchProducer;
    }

    /**
     * Gets blobs per selectie taak.
     * @return the blobs per selectie taak
     */
    public int getBlobsPerSelectieTaak() {
        return blobsPerSelectieTaak;
    }

    /**
     * Sets blobs per selectie taak.
     * @param blobsPerSelectieTaak the blobs per selectie taak
     */
    public void setBlobsPerSelectieTaak(int blobsPerSelectieTaak) {
        this.blobsPerSelectieTaak = blobsPerSelectieTaak;
    }

    /**
     * Gets max selectie taak.
     * @return the max selectie taak
     */
    public int getMaxSelectieTaak() {
        return maxSelectieTaak;
    }

    /**
     * Sets max selectie taak.
     * @param maxSelectieTaak the max selectie taak
     */
    public void setMaxSelectieTaak(int maxSelectieTaak) {
        this.maxSelectieTaak = maxSelectieTaak;
    }

    /**
     * Gets verwerker pool size.
     * @return the verwerker pool size
     */
    public int getVerwerkerPoolSize() {
        return verwerkerPoolSize;
    }

    /**
     * Sets verwerker pool size.
     * @param verwerkerPoolSize the verwerker pool size
     */
    public void setVerwerkerPoolSize(int verwerkerPoolSize) {
        this.verwerkerPoolSize = verwerkerPoolSize;
    }

    /**
     * Gets bericht resultaat folder.
     * @return the bericht resultaat folder
     */
    public String getBerichtResultaatFolder() {
        return berichtResultaatFolder;
    }

    /**
     * Sets bericht resultaat folder.
     * @param berichtResultaatFolder the bericht resultaat folder
     */
    public void setBerichtResultaatFolder(String berichtResultaatFolder) {
        this.berichtResultaatFolder = berichtResultaatFolder;
    }

    /**
     * Gets concat parts count.
     * @return the concat parts count
     */
    public int getConcatPartsCount() {
        return concatPartsCount;
    }

    /**
     * Sets concat parts count.
     * @param concatPartsCount the concat parts count
     */
    public void setConcatPartsCount(int concatPartsCount) {
        this.concatPartsCount = concatPartsCount;
    }

    /**
     * Gets max selectie schrijf taak.
     * @return the max selectie schrijf taak
     */
    public int getMaxSelectieSchrijfTaak() {
        return maxSelectieSchrijfTaak;
    }

    /**
     * Sets max selectie schrijf taak.
     * @param maxSelectieSchrijfTaak the max selectie schrijf taak
     */
    public void setMaxSelectieSchrijfTaak(int maxSelectieSchrijfTaak) {
        this.maxSelectieSchrijfTaak = maxSelectieSchrijfTaak;
    }

    /**
     * Gets autorisaties per selectie taak.
     * @return the autorisaties per selectie taak
     */
    public int getAutorisatiesPerSelectieTaak() {
        return autorisatiesPerSelectieTaak;
    }

    /**
     * Sets autorisaties per selectie taak.
     * @param autorisatiesPerSelectieTaak the autorisaties per selectie taak
     */
    public void setAutorisatiesPerSelectieTaak(int autorisatiesPerSelectieTaak) {
        this.autorisatiesPerSelectieTaak = autorisatiesPerSelectieTaak;
    }

    /**
     * Gets schrijver pool size.
     * @return the schrijver pool size
     */
    public int getSchrijverPoolSize() {
        return schrijverPoolSize;
    }

    /**
     * Sets schrijver pool size.
     * @param schrijverPoolSize the schrijver pool size
     */
    public void setSchrijverPoolSize(int schrijverPoolSize) {
        this.schrijverPoolSize = schrijverPoolSize;
    }

    /**
     * Gets selectielijst folder.
     * @return selectielijst folder.
     */
    public String getSelectiebestandFolder() {
        return selectiebestandFolder;
    }

    /**
     * Sets selectielijst folder.
     * @param selectielijstFolder the selectielijst folder
     */
    public void setSelectiebestandFolder(String selectielijstFolder) {
        this.selectiebestandFolder = selectielijstFolder;
    }

    /**
     * Gets maximale wacht tijd fragment verwerker min.
     * @return the maximale wacht tijd fragment verwerker min
     */
    public long getMaximaleWachtTijdFragmentVerwerkerMin() {
        return maximaleWachtTijdFragmentVerwerkerMin;
    }

    /**
     * Gets wachttijd maak persoons beeld min.
     * @return the wachttijd maak persoons beeld min
     */
    public long getWachttijdMaakPersoonsBeeldMin() {
        return wachttijdMaakPersoonsBeeldMin;
    }

    /**
     * Gets maximale wachttijd persoonslijst fragment min.
     * @return the maximale wachttijd persoonslijst fragment min
     */
    public long getMaximaleWachttijdPersoonslijstFragmentMin() {
        return maximaleWachttijdPersoonslijstFragmentMin;
    }

    /**
     * Gets maximale looptijd selectie run.
     * @return the maximale looptijd selectie run
     */
    public long getMaximaleLooptijdSelectierun() {
        return maxLooptijdSelectierun;
    }

    /**
     * Gets maximale wachttijd wachttaak.
     * @return the maximale wachttijd wachttaak
     */
    public long getMaximaleWachttijdWachttaak() {
        return maximaleWachttijdWachttaak;
    }
}
