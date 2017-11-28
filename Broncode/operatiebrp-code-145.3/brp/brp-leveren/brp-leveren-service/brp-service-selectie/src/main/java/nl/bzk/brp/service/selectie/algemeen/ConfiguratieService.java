/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.algemeen;

/**
 * ConfiguratieService.
 */
public interface ConfiguratieService {
    /**
     * @return poolsize
     */
    int getPoolSizeBlobBatchProducer();

    /**
     * @return batchsize
     */
    int getBatchSizeBatchProducer();

    /**
     * @return blobs pers selectie taak
     */
    int getBlobsPerSelectieTaak();

    /**
     * @return max selectie taak
     */
    int getMaxSelectieTaak();

    /**
     * @return max poolsize
     */
    int getVerwerkerPoolSize();

    /**
     * @return bericht resultaat folder
     */
    String getBerichtResultaatFolder();

    /**
     * @return selectielijst folder
     */
    String getSelectiebestandFolder();

    /**
     * @return max filesize
     */
    int getConcatPartsCount();

    /**
     * @return max selectie schrijftaak
     */
    int getMaxSelectieSchrijfTaak();

    /**
     * @return max autorisaties per selectie taak
     */
    int getAutorisatiesPerSelectieTaak();

    /**
     * @return max poolsize
     */
    int getSchrijverPoolSize();

    /**
     * @return max wacht tijd voor verwerken fragmenten
     */
    long getMaximaleWachttijdFragmentVerwerkerMin();

    /**
     * @return max wachttijd maak persoonsbeelden.
     */
    long getMaximaleWachttijdMaakPersoonsBeeldMin();

    /**
     * @return max wachttijd maak persoonslijsten fragment.
     */
    long getMaximaleWachttijdPersoonslijstFragmentMin();

    /**
     * @return maximale looptijd selectie run
     */
    long getMaximaleLooptijdSelectierun();

    /**
     * @return maximale wacht tijd op schrijftaken in job
     */
    long getMaximaleWachttijdWachttaak();
}
