/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.sleutelrubrieken;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Test resultaat: conversie voorwaarderegel lo3 naar brp.
 */
public final class ConversieTestResultaat extends TestResultaat {

    @Element(name = "initieren", required = false)
    private TestStap initieren;

    @Element(name = "lo3NaarBrp", required = false)
    private TestStap lo3NaarBrp;

    @Element(name = "bijhouding", required = false)
    private TestStap bijhouding;

    @ElementList(inline = true, entry = "bijhoudingen", type = TestBijhoudingResultaat.class, required = false)
    private List<TestBijhoudingResultaat> bijhoudingen = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param thema
     *            thema
     * @param naam
     *            naam
     */
    protected ConversieTestResultaat(
        @Attribute(name = "thema", required = false) final String thema,
        @Attribute(name = "naam", required = false) final String naam)
    {
        super(thema, naam);
    }

    @Override
    public boolean isSucces() {
        boolean result = true;
        if (!isSucces(initieren, lo3NaarBrp, bijhouding)) {
            result = false;
        }

        for (final TestBijhoudingResultaat bijhouding : bijhoudingen) {
            if (!bijhouding.isSucces()) {
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
     * Geef de waarde van lo3 naar brp.
     *
     * @return lo3 naar brp
     */
    public TestStap getLo3NaarBrp() {
        return lo3NaarBrp;
    }

    /**
     * Zet de waarde van lo3 naar brp.
     *
     * @param lo3NaarBrp
     *            lo3 naar brp
     */
    public void setLo3NaarBrp(final TestStap lo3NaarBrp) {
        this.lo3NaarBrp = lo3NaarBrp;
    }

    /**
     * Geef de waarde van bijhouding.
     *
     * @return bijhouding
     */
    public TestStap getBijhouding() {
        return bijhouding;
    }

    /**
     * Zet de waarde van bijhouding.
     *
     * @param bijhouding
     *            bijhouding
     */
    public void setBijhouding(final TestStap bijhouding) {
        this.bijhouding = bijhouding;
    }

    /**
     * Geef de waarde van bijhoudingen.
     *
     * @return bijhoudingen
     */
    public List<TestBijhoudingResultaat> getBijhoudingen() {
        return bijhoudingen;
    }

    /**
     * Zet de waarde van bijhoudingen.
     *
     * @param bijhoudingen
     *            bijhoudingen
     */
    public void setBijhoudingen(final List<TestBijhoudingResultaat> bijhoudingen) {
        this.bijhoudingen = bijhoudingen;
    }

    /**
     * Resultaat per levering.
     */
    public static final class TestBijhoudingResultaat {

        @Attribute(name = "idAdministratieveHandeling", required = false)
        private Long idAdministratieveHandeling;

        @Attribute(name = "soortAdministratieveHandeling", required = false)
        private String soortAdministratieveHandeling;

        @Element(name = "uitvoeren", required = false)
        private TestStap uitvoeren;

        /**
         * Geef de succes.
         *
         * @return succes
         */
        public boolean isSucces() {
            boolean result = true;
            if (!TestResultaat.isSucces(uitvoeren)) {
                result = false;
            }

            return result;
        }

        /**
         * Geeft het id van de administratieve handeling.
         *
         * @return Het id van de administratieve handeling.
         */
        public Long getIdAdministratieveHandeling() {
            return idAdministratieveHandeling;
        }

        /**
         * Zet het id van de administratieve handeling.
         *
         * @param idAdministratieveHandeling
         *            Het te zetten id.
         */
        public void setIdAdministratieveHandeling(final Long idAdministratieveHandeling) {
            this.idAdministratieveHandeling = idAdministratieveHandeling;
        }

        /**
         * Geeft het soort administratieve handeling.
         *
         * @return Het soort administratieve handeling.
         */
        public String getSoortAdministratieveHandeling() {
            return soortAdministratieveHandeling;
        }

        /**
         * Zet het soort administratieve handeling.
         *
         * @param soortAdministratieveHandeling
         *            De te zetten soort administratieve handeling.
         */
        public void setSoortAdministratieveHandeling(final String soortAdministratieveHandeling) {
            this.soortAdministratieveHandeling = soortAdministratieveHandeling;
        }

        /**
         * Geeft de 'uitvoeren' teststap.
         *
         * @return De 'uitvoeren' teststap.
         */
        public TestStap getUitvoeren() {
            return uitvoeren;
        }

        /**
         * Zet de 'uitvoeren' teststap. 'uitvoeren' De te zetten uitvoeren teststap.
         *
         * @param uitvoeren
         *            de te zetten waarde
         */
        public void setUitvoeren(final TestStap uitvoeren) {
            this.uitvoeren = uitvoeren;
        }

    }

}
