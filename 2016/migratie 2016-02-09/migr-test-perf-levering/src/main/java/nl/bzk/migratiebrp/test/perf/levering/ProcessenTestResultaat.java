/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.levering;

import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import org.simpleframework.xml.Element;

/**
 * Test resultaat: processen.
 */
public final class ProcessenTestResultaat extends TestResultaat {

    @Element(name = "duration", required = false)
    private Long duration;

    @Element(name = "aantal", required = false)
    private Long aantal;

    @Element(name = "resultaat", required = false)
    private TestStap resultaat;

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

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.common.resultaat.TestResultaat#isSucces()
     */
    @Override
    public boolean isSucces() {
        return isSucces(resultaat);
    }

    /**
     * Geef de waarde van duration.
     *
     * @return duration
     */
    public Long getDuration() {
        return duration;
    }

    /**
     * Zet de waarde van duration.
     *
     * @param duration
     *            duration
     */
    public void setDuration(final Long duration) {
        this.duration = duration;
    }

    /**
     * Geef de waarde van aantal.
     *
     * @return aantal
     */
    public Long getAantal() {
        return aantal;
    }

    /**
     * Zet de waarde van aantal.
     *
     * @param aantal
     *            aantal
     */
    public void setAantal(final Long aantal) {
        this.aantal = aantal;
    }

    /**
     * Geef de waarde van resultaat.
     *
     * @return resultaat
     */
    public TestStap getResultaat() {
        return resultaat;
    }

    /**
     * Zet de waarde van resultaat.
     *
     * @param resultaat
     *            resultaat
     */
    public void setResultaat(final TestStap resultaat) {
        this.resultaat = resultaat;
    }

}
