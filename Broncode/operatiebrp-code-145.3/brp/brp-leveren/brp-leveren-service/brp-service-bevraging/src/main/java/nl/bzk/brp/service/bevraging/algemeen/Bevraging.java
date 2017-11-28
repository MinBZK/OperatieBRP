/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;


/**
 * Container interface voor generieke bevraging stappen.
 */
public interface Bevraging {

    /**
     * Maak bericht service.
     * @param <V> de specialisatie van {@link BevragingVerzoek}
     * @param <R> de specialisatie van {@link BevragingResultaat}
     */
    @FunctionalInterface
    interface MaakBerichtService<V extends BevragingVerzoek, R extends BevragingResultaat> {

        /**
         * Maak berichtresultaat.
         * @param bevragingVerzoek bevragingverzoek
         * @return berichtresultaat
         */
        R voerStappenUit(V bevragingVerzoek);

    }

    /**
     * Archiveer bericht.
     * @param <V> de specialisatie van {@link BevragingVerzoek}
     * @param <R> de specialisatie van {@link BevragingResultaat}
     */
    @FunctionalInterface
    interface ArchiveerBerichtService<V extends BevragingVerzoek, R extends BevragingResultaat> {

        /**
         * Archiveer in- en uitgaand bericht.
         * @param verzoek verzoek
         * @param berichtResultaat geefDetailsPersoonBerichtResultaat
         * @param antwoordBerichtResultaat antwoordBerichtResultaat
         */
        void archiveer(V verzoek, R berichtResultaat, AntwoordBerichtResultaat antwoordBerichtResultaat);
    }

    /**
     * Protocolleer bericht.
     * @param <V> de specialisatie van {@link BevragingVerzoek}
     * @param <R> de specialisatie van {@link BevragingResultaat}
     */
    @FunctionalInterface
    interface ProtocolleerBerichtService<V extends BevragingVerzoek, R extends BevragingResultaat> {

        /**
         * Protocolleer het bericht.
         * @param verzoek het specifieke {@link BevragingVerzoek}
         * @param berichtResultaat het specifieke {@link BevragingResultaat}
         * @param antwoordBerichtResultaat het {@link AntwoordBerichtResultaat}
         */
        void protocolleer(V verzoek, R berichtResultaat, AntwoordBerichtResultaat antwoordBerichtResultaat);
    }
}
