/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.expressie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.common.resultaat.TestStatus;
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

    @ElementList(inline = true, entry = "persoon", type = PersoonControleResultaat.class, required = false)
    private List<PersoonControleResultaat> personen = new ArrayList<>();

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
        for (final PersoonControleResultaat persoon : personen) {
            if (!persoon.isSucces()) {
                return false;
            }
        }
        return isSucces(initieren, lo3NaarBrp);
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
     * Geeft de lijst met de resultaten van de personen terug.
     *
     * @return De lijst met de resultaten van de personen.
     */
    public List<PersoonControleResultaat> getPersonen() {
        return personen;
    }

    /**
     * Zet de lijst met de resultaten van de personen.
     *
     * @param personen
     *            De te zetten lijst met de resultaten van de personen.
     */
    public void setPersonen(final List<PersoonControleResultaat> personen) {
        this.personen = personen;
    }

    /**
     * Resultaat per persoon.
     */
    public static final class PersoonControleResultaat {

        @Attribute(name = "id", required = false)
        private final String id;

        @Attribute(name = "status", required = false)
        private TestStatus status;

        @Element(name = "gbav", required = false)
        private TestStap gbav;

        @Element(name = "brp", required = false)
        private TestStap brp;

        /**
         * Constructor.
         *
         * @param id
         *            id
         */
        public PersoonControleResultaat(final String id) {
            this.id = id;
        }

        /**
         * Geef de succes.
         *
         * @return succes
         */
        public boolean isSucces() {
            boolean result = true;
            if (TestStatus.EXCEPTIE == status || TestStatus.NOK == status || !TestResultaat.isSucces(gbav, brp)) {
                result = false;
            }

            return result;
        }

        /**
         * Geeft het ID terug.
         *
         * @return Het ID.
         */
        public String getId() {
            return id;
        }

        /**
         * Geeft de status van de test terug.
         *
         * @return De status van de test.
         */
        public TestStatus getStatus() {
            return status;
        }

        /**
         * Geeft de GBAV teststap terug.
         *
         * @return De GBAV teststap.
         */
        public TestStap getGbav() {
            return gbav;
        }

        /**
         * Geeft de BRP teststap terug.
         *
         * @return De BRP teststap.
         */
        public TestStap getBrp() {
            return brp;
        }

        /**
         * Zet de status van de test.
         *
         * @param status
         *            De te zetten status.
         */
        public void setStatus(final TestStatus status) {
            this.status = status;
        }

        /**
         * Zet de GBAV teststap.
         *
         * @param gbav
         *            De te zetten GBAV teststap.
         */
        public void setGbav(final TestStap gbav) {
            this.gbav = gbav;
        }

        /**
         * Zet de BRP teststap.
         *
         * @param brp
         *            De te zetten BRP teststap.
         */
        public void setBrp(final TestStap brp) {
            this.brp = brp;
        }

    }

}
