/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.levering.mutatiebericht;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.util.xml.annotation.Attribute;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementList;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.common.resultaat.TestStatus;

/**
 * Test resultaat: levering mutatiebericht.
 */
public final class LeveringMutatieberichtTestResultaat extends TestResultaat {

    @Element(name = "initieren", required = false)
    private TestStap initieren;

    @ElementList(inline = true, entry = "levering", type = TestLeveringResultaat.class, required = false)
    private List<TestLeveringResultaat> leveringen = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param thema
     *            thema
     * @param naam
     *            naam
     */
    protected LeveringMutatieberichtTestResultaat(
        @Attribute(name = "thema", required = false) final String thema,
        @Attribute(name = "naam", required = false) final String naam)
    {
        super(thema, naam);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.test.common.resultaat.TestResultaat#isSucces()
     */
    @Override
    public boolean isSucces() {
        boolean result = true;
        if (!isSucces(initieren)) {
            result = false;
        }

        for (final TestLeveringResultaat levering : leveringen) {
            if (!levering.isSucces()) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Geef de waarde van initieren.
     *
     * @return initieren
     */
    public TestStap getInitieren() {
        return initieren;
    }

    /**
     * Zet de waarde van initieren.
     *
     * @param initieren
     *            initieren
     */
    public void setInitieren(final TestStap initieren) {
        this.initieren = initieren;
    }

    /**
     * Geef de waarde van leveringen.
     *
     * @return leveringen
     */
    public List<TestLeveringResultaat> getLeveringen() {
        return leveringen;
    }

    /**
     * Zet de waarde van leveringen.
     *
     * @param leveringen
     *            leveringen
     */
    public void setLeveringen(final List<TestLeveringResultaat> leveringen) {
        this.leveringen = leveringen;
    }

    /**
     * Resultaat per levering.
     */
    public static final class TestLeveringResultaat {

        @Attribute(name = "idAdministratieveHandeling", required = false)
        private Long idAdministratieveHandeling;

        @Attribute(name = "soortAdministratieveHandeling", required = false)
        private String soortAdministratieveHandeling;

        @Attribute(name = "soortAdministratieveHandelingStatus", required = false)
        private TestStatus soortAdministratieveHandelingStatus;

        @Element(name = "mapping", required = false)
        private TestStap mapping;

        @ElementList(inline = true, entry = "bericht", type = TestLeveringBerichtResultaat.class, required = false)
        private List<TestLeveringBerichtResultaat> berichten = new ArrayList<>();

        /**
         * Geef de succes.
         *
         * @return succes
         */
        public boolean isSucces() {
            boolean result = true;
            if (!TestResultaat.isSucces(mapping)) {
                result = false;
            }

            for (final TestLeveringBerichtResultaat bericht : berichten) {
                if (!bericht.isSucces()) {
                    result = false;
                    break;
                }
            }
            return result;
        }

        /**
         * Geef de waarde van id administratieve handeling.
         *
         * @return id administratieve handeling
         */
        public Long getIdAdministratieveHandeling() {
            return idAdministratieveHandeling;
        }

        /**
         * Zet de waarde van id administratieve handeling.
         *
         * @param idAdministratieveHandeling
         *            id administratieve handeling
         */
        public void setIdAdministratieveHandeling(final Long idAdministratieveHandeling) {
            this.idAdministratieveHandeling = idAdministratieveHandeling;
        }

        /**
         * Geef de waarde van soort administratieve handeling.
         *
         * @return soort administratieve handeling
         */
        public String getSoortAdministratieveHandeling() {
            return soortAdministratieveHandeling;
        }

        /**
         * Zet de waarde van soort administratieve handeling.
         *
         * @param soortAdministratieveHandeling
         *            soort administratieve handeling
         */
        public void setSoortAdministratieveHandeling(final String soortAdministratieveHandeling) {
            this.soortAdministratieveHandeling = soortAdministratieveHandeling;
        }

        /**
         * Geef de waarde van soort administratieve handeling status.
         *
         * @return soort administratieve handeling status
         */
        public TestStatus getSoortAdministratieveHandelingStatus() {
            return soortAdministratieveHandelingStatus;
        }

        /**
         * Zet de waarde van soort administratieve handeling status.
         *
         * @param soortAdministratieveHandelingStatus
         *            soort administratieve handeling status
         */
        public void setSoortAdministratieveHandelingStatus(final TestStatus soortAdministratieveHandelingStatus) {
            this.soortAdministratieveHandelingStatus = soortAdministratieveHandelingStatus;
        }

        /**
         * Geef de waarde van mapping.
         *
         * @return mapping
         */
        public TestStap getMapping() {
            return mapping;
        }

        /**
         * Zet de waarde van mapping.
         *
         * @param mapping
         *            mapping
         */
        public void setMapping(final TestStap mapping) {
            this.mapping = mapping;
        }

        /**
         * Geef de waarde van berichten.
         *
         * @return berichten
         */
        public List<TestLeveringBerichtResultaat> getBerichten() {
            return berichten;
        }

        /**
         * Zet de waarde van leveringen.
         *
         * @param berichten
         *            leveringen
         */
        public void setLeveringen(final List<TestLeveringBerichtResultaat> berichten) {
            this.berichten = berichten;
        }

    }

    /**
     * Resultaat per levering.
     */
    public static final class TestLeveringBerichtResultaat {

        @Attribute(name = "soortBericht", required = false)
        private String soortBericht;

        @Attribute(name = "soortBerichtStatus", required = false)
        private TestStatus soortBerichtStatus;

        @Element(name = "conversie", required = false)
        private TestStap conversie;

        @Element(name = "filteren", required = false)
        private TestStap filteren;

        @Element(name = "bericht", required = false)
        private TestStap bericht;

        /**
         * Geef de succes.
         *
         * @return succes
         */
        public boolean isSucces() {
            return TestResultaat.isSucces(conversie, filteren, bericht);
        }

        /**
         * Geef de waarde van soort bericht.
         *
         * @return soort bericht
         */
        public String getSoortBericht() {
            return soortBericht;
        }

        /**
         * Zet de waarde van soort bericht.
         *
         * @param soortBericht
         *            soort bericht
         */
        public void setSoortBericht(final String soortBericht) {
            this.soortBericht = soortBericht;
        }

        /**
         * Geef de waarde van soort bericht status.
         *
         * @return soort bericht status
         */
        public TestStatus getSoortBerichtStatus() {
            return soortBerichtStatus;
        }

        /**
         * Zet de waarde van soort bericht status.
         *
         * @param soortBerichtStatus
         *            soort bericht status
         */
        public void setSoortBerichtStatus(final TestStatus soortBerichtStatus) {
            this.soortBerichtStatus = soortBerichtStatus;
        }

        /**
         * Geef de waarde van conversie.
         *
         * @return conversie
         */
        public TestStap getConversie() {
            return conversie;
        }

        /**
         * Zet de waarde van conversie.
         *
         * @param conversie
         *            conversie
         */
        public void setConversie(final TestStap conversie) {
            this.conversie = conversie;
        }

        /**
         * Geef de waarde van filteren.
         *
         * @return filteren
         */
        public TestStap getFilteren() {
            return filteren;
        }

        /**
         * Zet de waarde van filteren.
         *
         * @param filteren
         *            filteren
         */
        public void setFilteren(final TestStap filteren) {
            this.filteren = filteren;
        }

        /**
         * Geef de waarde van bericht.
         *
         * @return bericht
         */
        public TestStap getBericht() {
            return bericht;
        }

        /**
         * Zet de waarde van bericht.
         *
         * @param bericht
         *            bericht
         */
        public void setBericht(final TestStap bericht) {
            this.bericht = bericht;
        }

    }
}
