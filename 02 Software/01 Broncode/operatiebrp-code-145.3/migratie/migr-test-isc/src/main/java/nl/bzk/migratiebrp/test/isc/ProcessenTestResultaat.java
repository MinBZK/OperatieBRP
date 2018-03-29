/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementList;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;

/**
 * Test resultaat: processen.
 */
public final class ProcessenTestResultaat extends TestResultaat {

    @Element(name = "resultaat", required = false)
    private TestStap resultaat;

    @ElementList(name = "bestanden", entry = "bestand", type = Bestand.class, required = false)
    private List<Bestand> bestanden;

    @ElementList(name = "processInstanceIds", entry = "processInstanceId", type = Long.class, required = false)
    private List<Long> processen;

    /**
     * Constructor.
     * @param thema thema
     * @param naam naam
     */
    protected ProcessenTestResultaat(final String thema, final String naam) {
        super(thema, naam);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.test.common.resultaat.TestResultaat#isSucces()
     */
    @Override
    public boolean isSucces() {
        return isSucces(resultaat);
    }

    /**
     * Geef de waarde van resultaat.
     * @return resultaat
     */
    public TestStap getResultaat() {
        return resultaat;
    }

    /**
     * Zet de waarde van resultaat.
     * @param resultaat resultaat
     */
    public void setResultaat(final TestStap resultaat) {
        this.resultaat = resultaat;
    }

    /**
     * Geef de waarde van bestanden.
     * @return bestanden
     */
    public List<Bestand> getBestanden() {
        return bestanden;
    }

    /**
     * Zet de waarde van bestanden.
     * @param bestanden bestanden
     */
    public void setBestanden(final List<Bestand> bestanden) {
        this.bestanden = bestanden;
    }

    /**
     * Bestand toevoegen.
     * @param bestandsnaam bestand
     */
    public void addBestand(final String bestandsnaam) {
        if (bestanden == null) {
            bestanden = new ArrayList<>();
        }

        bestanden.add(new Bestand(bestandsnaam.substring(0, bestandsnaam.indexOf('-')), bestandsnaam));
    }

    /**
     * Geef de waarde van processen.
     * @return processen
     */
    public List<Long> getProcessen() {
        return processen;
    }

    /**
     * Zet de waarde van processen.
     * @param processen processen
     */
    public void setProcessen(final List<Long> processen) {
        this.processen = processen;
    }

    /**
     * Voeg 1 proces toe aan de processen.
     * @param proces proces
     */
    public void addProcess(final Long proces) {
        if (processen == null) {
            processen = new ArrayList<>();
        }

        processen.add(proces);
    }

    /**
     * Bestand.
     */
    public static final class Bestand {
        @Element(name = "volgnummer", required = false)
        private String volgnummer;
        @Element(name = "bestand", required = false)
        private String bestand;

        /**
         * Default constructor.
         */
        public Bestand() {
        }

        /**
         * Constructor.
         * @param volgnummer volgnummer
         * @param bestand bestand
         */
        private Bestand(final String volgnummer, final String bestand) {
            super();
            this.volgnummer = volgnummer;
            this.bestand = bestand;
        }

        /**
         * Geef de waarde van volgnummer.
         * @return volgnummer
         */
        public String getVolgnummer() {
            return volgnummer;
        }

        /**
         * Zet de waarde van volgnummer.
         * @param volgnummer volgnummer
         */
        public void setVolgnummer(final String volgnummer) {
            this.volgnummer = volgnummer;
        }

        /**
         * Geef de waarde van bestand.
         * @return bestand
         */
        public String getBestand() {
            return bestand;
        }

        /**
         * Zet de waarde van bestand.
         * @param bestand bestand
         */
        public void setBestand(final String bestand) {
            this.bestand = bestand;
        }

    }

}
