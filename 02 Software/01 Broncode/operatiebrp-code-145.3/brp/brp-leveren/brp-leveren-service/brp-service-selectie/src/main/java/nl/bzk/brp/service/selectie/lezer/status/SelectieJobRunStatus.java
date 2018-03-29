/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.status;

import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * SelectieJobRunStatus.
 */
public final class SelectieJobRunStatus {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private Integer selectieRunId;
    private AtomicInteger verwerkTaken = new AtomicInteger();
    private AtomicInteger verwerkKlaarTaken = new AtomicInteger();
    private AtomicInteger schrijfTaken = new AtomicInteger();
    private AtomicInteger schrijfKlaarTaken = new AtomicInteger();
    private AtomicInteger selectieResultaatSchrijfTaken = new AtomicInteger();
    private AtomicInteger selectieResultaatSchrijfResultaatTaken = new AtomicInteger();
    private AtomicInteger aantalPlaatsAfnemerindicatieTaken = new AtomicInteger();
    private AtomicInteger aantalVerwerktePersonenNetwerk = new AtomicInteger();
    private Map<Integer, AtomicInteger> taakNaarAantalPersonen = new ConcurrentHashMap<>();
    private Date startDatum;
    private Date eindeDatum;
    private int totaalAantalSelectieTaken;
    private boolean moetStoppen = false;
    private boolean error = false;
    private Set<Integer> ongeldigeSelectietaken = Sets.newHashSet();

    public boolean isStopped() {
        return getEindeDatum() != null;
    }

    /**
     * @param datum datum
     */
    public void setStartDatum(final Date datum) {
        this.startDatum = datum;
    }

    /**
     * @return selectietaken
     */
    public int incrementEnGetVerwerkTaken() {
        return verwerkTaken.incrementAndGet();
    }

    /**
     * Hoog selectieresultaat taken op.
     * @return selectieresultaat taken
     */
    int incrementEnGetVerwerkKlaarTaken() {
        return verwerkKlaarTaken.incrementAndGet();
    }

    /**
     * @return selectie resultaat counten.
     */
    public int getVerwerkTakenKlaarCount() {
        return verwerkKlaarTaken.get();
    }

    /**
     * @return selectietaken
     */
    public int getSelectieTaakCount() {
        return verwerkTaken.get();
    }

    /**
     * Zet schrijftaken count
     * @param count count
     * @return selectieschrijftaken
     */
    int incrementEnGetSchrijfTaken(final int count) {
        return schrijfTaken.addAndGet(count);
    }

    /**
     * Hoog selectieresultaat schrijf taken op.
     * @return selectieschrijfresultaat taken
     */
    public int incrementEnGetSchrijfKlaarTaken() {
        return schrijfKlaarTaken.incrementAndGet();
    }

    /**
     * @return selectieschrijftaken count.
     */
    public int getAantalSchrijfTaken() {
        return schrijfTaken.get();
    }

    /**
     * @return selectieresultaat schrijf taken count.
     */
    public int getSchrijfTakenKlaarCount() {
        return schrijfKlaarTaken.get();
    }

    /**
     * @return startDatum
     */
    public Date getStartDatum() {
        return startDatum;
    }

    /**
     * @param totaalAantalSelectieTaken totaalAantalSelectieTaken
     */
    public void setTotaalAantalSelectieTaken(int totaalAantalSelectieTaken) {
        this.totaalAantalSelectieTaken = totaalAantalSelectieTaken;
    }

    /**
     * @return totaalAantalSelectieTaken
     */
    public int getTotaalAantalSelectieTaken() {
        return totaalAantalSelectieTaken;
    }

    /**
     * @return eindeDatum
     */
    public Date getEindeDatum() {
        return eindeDatum;
    }

    /**
     * @param eindeDatum eindeDatum
     */
    public void setEindeDatum(Date eindeDatum) {
        this.eindeDatum = eindeDatum;
    }

    /**
     * @param count count
     * @return count na increment
     */
    public int getSelectieResultaatSchrijfTaakIncrement(int count) {
        return selectieResultaatSchrijfTaken.addAndGet(count);
    }

    /**
     * @return schrijfcount
     */
    public int getSelectieResultaatSchrijfTaakCount() {
        return selectieResultaatSchrijfTaken.get();
    }

    /**
     * @return increment schrijftaken resultaat
     */
    int incrementEnGetSelectieResultaatSchrijfTakenIncrement() {
        return selectieResultaatSchrijfResultaatTaken.incrementAndGet();
    }

    /**
     * @return schrijf taak count
     */
    public int getSelectieResultaatSchrijfTaakResultaatCount() {
        return selectieResultaatSchrijfResultaatTaken.get();
    }

    /**
     * Increment en get het totaal aantal plaatsafnemerindicatie taken.
     * @param aantalAfnemerindicatieTaken aantal
     * @return nieuw aantalAfnemerindicatieTaken
     */
    public int addEnGetAantalPlaatsAfnemerindicatieTaken(final int aantalAfnemerindicatieTaken) {
        return aantalPlaatsAfnemerindicatieTaken.addAndGet(aantalAfnemerindicatieTaken);
    }

    /**
     * @return het totaal aantal afnemerindicatie taken
     */
    public int getAantalPlaatsAfnemerindicatieTaken() {
        return aantalPlaatsAfnemerindicatieTaken.get();
    }

    /**
     * Increment en get het totaal aantal verwerkte personen netwerk.
     * @param aantalVerwerktePersonen aantal
     * @return nieuw aantalVerwerktePersonenNetwerk
     */
    public int addEnGetAantalVerwerktePesonenNetwerk(final int aantalVerwerktePersonen) {
        return aantalVerwerktePersonenNetwerk.addAndGet(aantalVerwerktePersonen);
    }

    /**
     * Verhoogt het totaal aantal verwerkte personen voor netwerk.
     * @return het totaal aantal verwerkte personen voor netwerk
     */
    public int incrementAndGetAantalVerwerktePersonenNetwerk() {
        return aantalVerwerktePersonenNetwerk.incrementAndGet();
    }

    /**
     * @return het totaal aantal verwerkte personen voor netwerk
     */
    public int getAantalVerwerktePersonenNetwerk() {
        return aantalVerwerktePersonenNetwerk.get();
    }

    /**
     * @return moetStoppen
     */
    public boolean moetStoppen() {
        return moetStoppen;
    }

    /**
     * @param moetStoppen moetStoppen
     */
    void setMoetStoppen(boolean moetStoppen) {
        if (eindeDatum == null) {
            this.moetStoppen = moetStoppen;
        }
    }

    /**
     * @return selectieRunId
     */
    public Integer getSelectieRunId() {
        return selectieRunId;
    }

    /**
     * @param selectieRunId selectieRunId
     */
    public void setSelectieRunId(Integer selectieRunId) {
        this.selectieRunId = selectieRunId;
    }

    /**
     * @return error
     */
    public boolean isError() {
        return error;
    }

    /**
     * @param error error
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * Hoog het aantal verwerkte personen op voor een bepaalde afnemerindicatietaak.
     * @param taakId de taak ID
     */
    void incrementAfnemerindicatieTaakVerwerkt(final Integer taakId) {
        if (!taakNaarAantalPersonen.containsKey(taakId)) {
            taakNaarAantalPersonen.put(taakId, new AtomicInteger());
        }
        final AtomicInteger aantalPersonen = taakNaarAantalPersonen.get(taakId);
        aantalPersonen.incrementAndGet();
    }

    /**
     * @return ongeldige selectietaken
     */
    public Set<Integer> getOngeldigeSelectietaken() {
        return Collections.unmodifiableSet(ongeldigeSelectietaken);
    }

    /**
     * @param ongeldigeSelectietaken ongeldigeSelectietaken
     */
    public void voegOngeldigeSelectietakenToe(final Set<Integer> ongeldigeSelectietaken) {
        if (ongeldigeSelectietaken != null) {
            this.ongeldigeSelectietaken.addAll(ongeldigeSelectietaken);
        }
    }

    /**
     * Geef voor een afnemerindicatie selectietaak het aantal verwerkte personen.
     * @param selectietaakId de selectietaak ID
     * @return het aantal verwerkte personen
     */
    public Integer getVerwerktePersonenPerAfnemerindicatieTaak(final Integer selectietaakId) {
        final Map<Integer, AtomicInteger> taakNaarAantalPersonenMap = getAfnemerindicatieTaakNaarAantalVerwerktePersonen();
        final AtomicInteger aantalPersonen = taakNaarAantalPersonenMap.get(selectietaakId);
        return aantalPersonen == null ? 0 : aantalPersonen.get();
    }

    /**
     * @return taakNaarAantalPersonen map
     */
    Map<Integer, AtomicInteger> getAfnemerindicatieTaakNaarAantalVerwerktePersonen() {
        return taakNaarAantalPersonen;
    }

    /**
     * @return het total aantal plaatsafnemerindicatie resultaatberichten.
     */
    public int getAantalPlaatsAfnemerindicatieKlaarTaken() {
        return taakNaarAantalPersonen.values().stream().mapToInt(AtomicInteger::get).sum();
    }

    /**
     * is schrijven klaar exclusief schrijven totalen bestand en steekproef
     * @return klaar
     */
    public boolean schrijvenKlaar() {
        final int aantalSelectieTaken = getSelectieTaakCount();
        final int aantalSelectieResulaatTaken = getVerwerkTakenKlaarCount();
        final int aantalSelectieSchrijfTaken = getAantalSchrijfTaken();
        final int aantalSelectieSchrijfResulaatTaken = getSchrijfTakenKlaarCount();
        final int aantalSelectieTakenTeDoen = aantalSelectieTaken - aantalSelectieResulaatTaken;
        final int aantalSelectieSchrijfTakenTeDoen = aantalSelectieSchrijfTaken - aantalSelectieSchrijfResulaatTaken;
        //afnemerindicaties teller
        final int aantalAfnemerindicaties = getAantalPlaatsAfnemerindicatieTaken();
        final int aantalAfemerindicatiesKlaar = getAantalPlaatsAfnemerindicatieKlaarTaken();
        final int aantalAfnemerindicatieTakenTeDoen = aantalAfnemerindicaties - aantalAfemerindicatiesKlaar;
        boolean klaar = aantalSelectieTakenTeDoen == 0 && aantalSelectieSchrijfTakenTeDoen == 0 && aantalAfnemerindicatieTakenTeDoen == 0;
        if (!klaar) {
            LOGGER.info(
                    String.format(
                            "nog niet klaar met wachten, aantal selectie taken te doen [%d], aantal schrijftaken te doen [%d], aantal afnemerindicaties te "
                                    + "doen "

                                    + "[%d]",
                            aantalSelectieTakenTeDoen,
                            aantalSelectieSchrijfTakenTeDoen, aantalAfnemerindicatieTakenTeDoen));
        }
        return klaar;
    }

    /**
     * is run klaar.
     * @return klaar
     */
    public boolean klaar() {
        final int aantalSelectieResultaatSchrijfTaken = getSelectieResultaatSchrijfTaakCount();
        final int aantalSelectieResultaatSchrijfResultaatTaken = getSelectieResultaatSchrijfTaakResultaatCount();
        final int aantalSelectieResultaatSchrijfTakenTeDoen = aantalSelectieResultaatSchrijfTaken - aantalSelectieResultaatSchrijfResultaatTaken;
        boolean klaar = aantalSelectieResultaatSchrijfTakenTeDoen == 0;
        if (!klaar) {
            LOGGER.info(
                    String.format(
                            "nog niet klaar met wachten, aantal schrijf resultaat taken [%d]", aantalSelectieResultaatSchrijfTakenTeDoen));
        }
        return klaar;
    }
}
