/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.autorisatie;

import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Test resultaat: conversie autorisatie tabel lo3 naar brp.
 */
public final class ConversieTestResultaat extends TestResultaat {

    @Element(name = "initieren", required = false)
    private TestStap initieren;

    @Element(name = "precondities", required = false)
    private TestStap precondities;

    @Element(name = "lo3NaarBrp", required = false)
    private TestStap lo3NaarBrp;

    @Element(name = "conversieLog", required = false)
    private TestStap conversieLog;

    @Element(name = "opslaanBrp", required = false)
    private TestStap opslaanBrp;

    @Element(name = "lezenBrp", required = false)
    private TestStap lezenBrp;

    /**
     * Constructor.
     * 
     * @param thema
     *            thema
     * @param naam
     *            naam
     */
    protected ConversieTestResultaat(@Attribute(name = "thema", required = false) final String thema, @Attribute(name = "naam",
            required = false) final String naam)
    {
        super(thema, naam);
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.common.resultaat.TestResultaat#isSucces()
     */
    @Override
    public boolean isSucces() {
        return isSucces(initieren, precondities, lo3NaarBrp, conversieLog, opslaanBrp, lezenBrp);
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
     * Geef de waarde van precondities.
     *
     * @return precondities
     */
    public TestStap getPrecondities() {
        return precondities;
    }

    /**
     * Zet de waarde van precondities.
     *
     * @param precondities
     *            precondities
     */
    public void setPrecondities(final TestStap precondities) {
        this.precondities = precondities;
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
     * Geef de waarde van conversie log.
     *
     * @return conversie log
     */
    public TestStap getConversieLog() {
        return conversieLog;
    }

    /**
     * Zet de waarde van conversie log.
     *
     * @param conversieLog
     *            conversie log
     */
    public void setConversieLog(final TestStap conversieLog) {
        this.conversieLog = conversieLog;
    }

    /**
     * Geef de waarde van opslaan brp.
     *
     * @return opslaan brp
     */
    public TestStap getOpslaanBrp() {
        return opslaanBrp;
    }

    /**
     * Zet de waarde van opslaan brp.
     *
     * @param opslaanBrp
     *            opslaan brp
     */
    public void setOpslaanBrp(final TestStap opslaanBrp) {
        this.opslaanBrp = opslaanBrp;
    }

    /**
     * Geef de waarde van lezen brp.
     *
     * @return lezen brp
     */
    public TestStap getLezenBrp() {
        return lezenBrp;
    }

    /**
     * Zet de waarde van lezen brp.
     *
     * @param lezenBrp
     *            lezen brp
     */
    public void setLezenBrp(final TestStap lezenBrp) {
        this.lezenBrp = lezenBrp;
    }

}
