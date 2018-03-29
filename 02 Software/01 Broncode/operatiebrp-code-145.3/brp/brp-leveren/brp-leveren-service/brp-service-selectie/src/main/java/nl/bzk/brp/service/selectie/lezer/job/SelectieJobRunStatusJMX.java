/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.job;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import nl.bzk.brp.service.selectie.algemeen.ConfiguratieService;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatus;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatusService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * SelectieJobRunStatusServiceJMX.
 */
@ManagedResource(objectName = "selectie:name=SelectieRunStatus",
        description = "De status van de selectieJob.")
@Component
public final class SelectieJobRunStatusJMX {

    private static final int PERC_100 = 100;
    private static final double FACTOR_VOORTGANG_VERWERKERS = .7;
    private static final double FACTOR_VOORTGANG_SCHRIJVEN_EN_AFNEMERINDICATIE = .1;
    private static final double FACTOR_VOORTGANG_RESULTAATBESTANDEN = .2;

    @Inject
    private SelectieJobRunStatusService selectieJobRunStatusService;

    @Inject
    private ConfiguratieService configuratieService;

    private SelectieJobRunStatusJMX() {
    }

    /**
     * @return moestStoppen.
     */
    @ManagedAttribute(description = "moestStoppen")
    public boolean getMoestStoppen() {
        return selectieJobRunStatusService.getStatus().moetStoppen();
    }

    /**
     * @return isError.
     */
    @ManagedAttribute(description = "isError")
    public boolean isError() {
        return selectieJobRunStatusService.getStatus().isError();
    }

    /**
     * @return isError.
     */
    @ManagedAttribute(description = "selectieRunId")
    public Integer getSelectieRunId() {
        return selectieJobRunStatusService.getStatus().getSelectieRunId();
    }


    /**
     * Bepaalt het voortgangspercentage van de selectierun.
     *
     * @return voortgang.
     */
    @ManagedOperation(description = "voortgang (%)")
    public double voortgang() {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();

        //blok 1; 70% aantal verwerk taken / totaal aantal verwerktaken
        final int totaal1 = status.getTotaalAantalSelectieTaken();
        final int afgerond1 = status.getVerwerkTakenKlaarCount();
        final boolean blok1Klaar = totaal1 == afgerond1;
        double factorBlok1 = ((double) afgerond1 / totaal1) * FACTOR_VOORTGANG_VERWERKERS;

        //blok 2: 10% aantal schrijf+afnemerindicatie taken / totaal aantal schrijf+afnemerindicatie taken
        double factorBlok2 = 0;
        boolean blok2Klaar = false;
        if (blok1Klaar) {
            final int totaal2 = status.getAantalSchrijfTaken() + status.getAantalPlaatsAfnemerindicatieTaken();
            final int afgerond2 = status.getSchrijfTakenKlaarCount() + status.getAantalPlaatsAfnemerindicatieKlaarTaken();
            blok2Klaar = totaal2 == afgerond2;
            factorBlok2 = blok2Klaar
                    ? FACTOR_VOORTGANG_SCHRIJVEN_EN_AFNEMERINDICATIE
                            : ((double) afgerond2 / totaal2) * FACTOR_VOORTGANG_SCHRIJVEN_EN_AFNEMERINDICATIE;
        }

        //blok 3: 20% resultaattaken / totaal aantal resultaattaken klaar
        double factorBlok3 = 0;
        if (blok2Klaar) {
            final int totaal3 = status.getSelectieResultaatSchrijfTaakCount();
            final int afgerond3 = status.getSelectieResultaatSchrijfTaakResultaatCount();
            factorBlok3 = totaal3 == afgerond3
                    ? FACTOR_VOORTGANG_RESULTAATBESTANDEN
                    : ((double) afgerond3 / totaal3) * FACTOR_VOORTGANG_RESULTAATBESTANDEN;
        }

        return (factorBlok1 + factorBlok2 + factorBlok3) * PERC_100;
    }

    /**
     * @return tijd te gaan.
     */
    @ManagedOperation(description = "Verwachte looptijd te doen (minuten)")
    public long tijdTeGaan() {
        final Date startDatum = selectieJobRunStatusService.getStatus().getStartDatum();
        if (startDatum == null) {
            return 0;
        }
        if (selectieJobRunStatusService.getStatus().getEindeDatum() != null) {
            return 0;
        }
        final long nu = new Date().getTime();
        final long duur = nu - startDatum.getTime();
        final double voortgang = voortgang();
        final double percentageTodo = PERC_100 - voortgang;

        final double doorloop = (duur / voortgang) * percentageTodo;
        return TimeUnit.MILLISECONDS.toMinutes((int) doorloop);
    }

    /**
     * @return verwachte doorlooptijd.
     */
    @ManagedOperation(description = "Verwachte doorlooptijd (minuten)")
    public long doorloopTijd() {
        final Date startDatum = selectieJobRunStatusService.getStatus().getStartDatum();
        if (startDatum == null) {
            return 0;
        }
        final Date eindDatum = selectieJobRunStatusService.getStatus().getEindeDatum();
        final long nu = eindDatum != null ? eindDatum.getTime() : new Date().getTime();
        final long duur = nu - startDatum.getTime();
        final double voortgang = voortgang();
        final double doorloop = (duur / voortgang) * PERC_100;
        return TimeUnit.MILLISECONDS.toMinutes((int) doorloop);
    }

    /**
     * @return verwerksnelheid selectietaken.
     */
    @ManagedOperation(description = "selectie taken per minuut")
    public long verwerkSnelheidSelectieTaken() {
        final Date startDatum = selectieJobRunStatusService.getStatus().getStartDatum();
        if (startDatum == null) {
            return 0;
        }
        final Date eindDatum = selectieJobRunStatusService.getStatus().getEindeDatum();
        final long nu = eindDatum != null ? eindDatum.getTime() : new Date().getTime();
        final int selectieTaken = selectieJobRunStatusService.getStatus().getSelectieTaakCount();
        final long tijd = TimeUnit.MILLISECONDS.toMinutes(nu - startDatum.getTime());
        if (tijd == 0) {
            return 0;
        }
        return selectieTaken / tijd;
    }

    /**
     * blobs pers minuut.
     * @return aantal blobs.
     */
    @ManagedOperation(description = "blobs per minuut")
    public long verwerkSnelheidBlobs() {
        final Date startDatum = selectieJobRunStatusService.getStatus().getStartDatum();
        if (startDatum == null) {
            return 0;
        }
        final Date eindDatum = selectieJobRunStatusService.getStatus().getEindeDatum();
        final long nu = eindDatum != null ? eindDatum.getTime() : new Date().getTime();
        final int selectieTaken = selectieJobRunStatusService.getStatus().getSelectieTaakCount();
        final int blobsPerBericht = configuratieService.getBlobsPerSelectieTaak();
        final long tijd = TimeUnit.MILLISECONDS.toMinutes(nu - startDatum.getTime());
        if (tijd == 0) {
            return 0;
        }
        return (selectieTaken * blobsPerBericht) / tijd;
    }

    /**
     * verwerksnelheid selectie schrijftaken.
     * @return aantal schrijftaken.
     */
    @ManagedOperation(description = "selectie schrijf taken per minuut")
    public long verwerkSnelheidSelectieSchrijfTaken() {
        final Date startDatum = selectieJobRunStatusService.getStatus().getStartDatum();
        if (startDatum == null) {
            return 0;
        }
        final Date eindDatum = selectieJobRunStatusService.getStatus().getEindeDatum();
        final long nu = eindDatum != null ? eindDatum.getTime() : new Date().getTime();
        final int selectieSchrijfTaken = selectieJobRunStatusService.getStatus().getAantalSchrijfTaken();
        final long tijd = TimeUnit.MILLISECONDS.toMinutes(nu - startDatum.getTime());
        if (tijd == 0) {
            return 0;
        }
        return selectieSchrijfTaken / tijd;
    }
}
