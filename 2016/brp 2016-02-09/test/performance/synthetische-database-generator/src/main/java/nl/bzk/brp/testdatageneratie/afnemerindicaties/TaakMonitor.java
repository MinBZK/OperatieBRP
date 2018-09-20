/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.afnemerindicaties;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

public class TaakMonitor {

    private static Logger LOGGER = Logger.getLogger(TaakMonitor.class);

    private int totaalAantalTaken;
    private int takenVoltooid = 0;
    private Class<? extends Callable> taakKlasse;
    private long startTijd = System.currentTimeMillis();

    public TaakMonitor(final Class<? extends Callable> taakKlasse, final int totaalAantalTaken) {
        this.totaalAantalTaken = totaalAantalTaken;
        this.taakKlasse = taakKlasse;
    }

    public void taakVoltooid(final long tijdInMilliSeconde) {
        takenVoltooid++;
        logStatus(tijdInMilliSeconde);
    }

    private void logStatus(final long tijdInMilliSeconde) {
        double percentageVoltooid = ((double) takenVoltooid / (double) totaalAantalTaken) * 100D;
        long tijdVerstreken = System.currentTimeMillis() - startTijd;
        double prognoseTotaleTijd = ((double) tijdVerstreken / (double) takenVoltooid) * (double) totaalAantalTaken;
        double prognoseTijdTeGaanInMinuten = (prognoseTotaleTijd - tijdVerstreken) / 1000D / 60D;
        int percentageVoltooidAfgerond = Double.valueOf(percentageVoltooid).intValue();
        int prognoseTijdTeGaanInMinutenAfgerond = Double.valueOf(prognoseTijdTeGaanInMinuten).intValue();
        LOGGER.info(
                taakKlasse.getSimpleName() + ": taak " + takenVoltooid + " / " + totaalAantalTaken
                        + " (" + percentageVoltooidAfgerond + "%) voltooid in " + tijdInMilliSeconde + " ms. "
                        + "Prognose tijd te gaan in minuten: " + prognoseTijdTeGaanInMinutenAfgerond);
    }

}
