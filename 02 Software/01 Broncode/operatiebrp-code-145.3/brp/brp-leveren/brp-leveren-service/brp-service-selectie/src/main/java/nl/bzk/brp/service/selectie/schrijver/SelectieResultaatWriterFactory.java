/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import nl.bzk.brp.domain.berichtmodel.SelectieResultaatBericht;

/**
 * SelectieResultaatWriter.
 */
public interface SelectieResultaatWriterFactory {


    /**
     * Geeft een brp writer voor een persoon bestand.
     * @param info meta info
     * @param bericht meta bericht
     * @return een {@link PersoonBestandWriter}
     * @throws SelectieResultaatVerwerkException als het schrijven faalt.
     */
    PersoonBestandWriter persoonWriterBrp(final SelectieResultaatSchrijfInfo info,
                                          final SelectieResultaatBericht bericht) throws SelectieResultaatVerwerkException;

    /**
     * Geeft een gba writer voor een persoon bestand.
     * @param info info
     * @param bericht bericht
     * @return en {@link PersoonBestandWriter}
     * @throws SelectieResultaatVerwerkException als het schrijven faalt.
     */
    PersoonBestandWriter persoonWriterGba(SelectieResultaatSchrijfInfo info, SelectieResultaatBericht bericht)
            throws SelectieResultaatVerwerkException;

    /**
     * Maakt een brp writer voor een totalen bestand.
     * @param info meta info
     * @param bericht meta bericht
     * @return een {@link TotalenBestandWriter}
     * @throws SelectieResultaatVerwerkException als het schrijven faalt.
     */
    TotalenBestandWriter totalenWriterBrp(final SelectieResultaatSchrijfInfo info,
                                          final SelectieResultaatBericht bericht) throws SelectieResultaatVerwerkException;

    /**
     * Maakt een gba writer voor een totalen bestand.
     * @param info meta info
     * @param bericht meta bericht
     * @return een {@link TotalenBestandWriter}
     * @throws SelectieResultaatVerwerkException als het schrijven faalt.
     */
    TotalenBestandWriter totalenWriterGba(final SelectieResultaatSchrijfInfo info,
                                          final SelectieResultaatBericht bericht) throws SelectieResultaatVerwerkException;


    /**
     * Interface voor een bestand.
     */
    interface BerichtBestand {

        /**
         * @throws SelectieResultaatVerwerkException xml schrijf fout
         */
        void eindeBericht() throws SelectieResultaatVerwerkException;

    }

    /**
     * Interface voor een Persoon bestand.
     */
    interface PersoonBestandWriter extends BerichtBestand {

        /**
         * @param persoon persoon
         * @throws SelectieResultaatVerwerkException io fout
         */
        void voegPersoonToe(String persoon) throws SelectieResultaatVerwerkException;
    }

    /**
     * Interface voor een writer van een totalen bestand.
     */
    interface TotalenBestandWriter extends BerichtBestand {

        /**
         * Schrijf het totalenbericht.
         * @param totaalPersonen het aantal personen dat is geselecteerd (en terug te vinden is in de berichten)
         * @param aantalSelectieresultaatSets het aantal selectieresultaatsets waaruit het resultaat bestaat (inclusief de Resultaatset totalen)
         * @throws SelectieResultaatVerwerkException io fout
         */
        void schrijfTotalen(int totaalPersonen, int aantalSelectieresultaatSets) throws SelectieResultaatVerwerkException;
    }
}
