/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.isc;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.test.resultaat.TestResultaat;
import nl.moderniseringgba.migratie.test.resultaat.TestStap;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Test resultaat: processen.
 */
public final class ProcessenTestResultaat extends TestResultaat {

    @Element(name = "procesInstanceId", required = false)
    private Long procesInstanceId;

    @Element(name = "resultaat", required = false)
    private TestStap resultaat;

    @ElementList(name = "bestanden", entry = "bestand", type = Bestand.class, required = false)
    private List<Bestand> bestanden;

    /**
     * Constructor.
     * 
     * @param thema
     *            thema
     * @param naam
     *            naam
     */
    protected ProcessenTestResultaat(final String thema, final String naam) {
        super(thema, naam);
    }

    public Long getProcesInstanceId() {
        return procesInstanceId;
    }

    public void setProcesInstanceId(final Long procesInstanceId) {
        this.procesInstanceId = procesInstanceId;
    }

    public TestStap getResultaat() {
        return resultaat;
    }

    public void setResultaat(final TestStap resultaat) {
        this.resultaat = resultaat;
    }

    public List<Bestand> getBestanden() {
        return bestanden;
    }

    public void setBestanden(final List<Bestand> bestanden) {
        this.bestanden = bestanden;
    }

    /**
     * Bestand toevoegen.
     * 
     * @param bestandsnaam
     *            bestand
     */
    public void addBestand(final String bestandsnaam) {
        if (bestanden == null) {
            bestanden = new ArrayList<Bestand>();
        }

        bestanden.add(new Bestand(bestandsnaam.substring(0, bestandsnaam.indexOf("-")), bestandsnaam));
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
         * 
         * @param volgnummer
         *            volgnummer
         * @param bestand
         *            bestand
         */
        private Bestand(final String volgnummer, final String bestand) {
            super();
            this.volgnummer = volgnummer;
            this.bestand = bestand;
        }

        public String getVolgnummer() {
            return volgnummer;
        }

        public void setVolgnummer(final String volgnummer) {
            this.volgnummer = volgnummer;
        }

        public String getBestand() {
            return bestand;
        }

        public void setBestand(final String bestand) {
            this.bestand = bestand;
        }

    }

}
