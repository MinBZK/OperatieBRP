/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.preconditie;

import nl.bzk.algemeenbrp.util.xml.annotation.Attribute;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;

/**
 * Test resultaat: precondities.
 */
public final class PreconditieTestResultaat extends TestResultaat {

    @Element(name = "syntaxPrecondities", required = false)
    private TestStap syntaxPrecondities;

    @Element(name = "lo3NaarBrp", required = false)
    private TestStap lo3NaarBrp;

    @Element(name = "conversieLog", required = false)
    private TestStap conversieLog;

    /**
     * Constructor.
     * @param thema thema
     * @param naam naam
     */
    protected PreconditieTestResultaat(
            @Attribute(name = "thema", required = false) final String thema,
            @Attribute(name = "naam", required = false) final String naam) {
        super(thema, naam);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.test.common.resultaat.TestResultaat#isSucces()
     */
    @Override
    public boolean isSucces() {
        return isSucces(syntaxPrecondities, lo3NaarBrp, conversieLog);
    }

    /**
     * Geef de waarde van syntax precondities.
     * @return syntax precondities
     */
    public TestStap getSyntaxPrecondities() {
        return syntaxPrecondities;
    }

    /**
     * Zet de waarde van syntax precondities.
     * @param syntaxPrecondities syntax precondities
     */
    public void setSyntaxPrecondities(final TestStap syntaxPrecondities) {
        this.syntaxPrecondities = syntaxPrecondities;
    }

    /**
     * Geef de waarde van lo3 naar brp.
     * @return lo3 naar brp
     */
    public TestStap getLo3NaarBrp() {
        return lo3NaarBrp;
    }

    /**
     * Zet de waarde van lo3 naar brp.
     * @param lo3NaarBrp lo3 naar brp
     */
    public void setLo3NaarBrp(final TestStap lo3NaarBrp) {
        this.lo3NaarBrp = lo3NaarBrp;
    }

    /**
     * Geef de waarde van conversie log.
     * @return conversie log
     */
    public TestStap getConversieLog() {
        return conversieLog;
    }

    /**
     * Zet de waarde van conversie log.
     * @param conversieLog conversie log
     */
    public void setConversieLog(final TestStap conversieLog) {
        this.conversieLog = conversieLog;
    }
}
