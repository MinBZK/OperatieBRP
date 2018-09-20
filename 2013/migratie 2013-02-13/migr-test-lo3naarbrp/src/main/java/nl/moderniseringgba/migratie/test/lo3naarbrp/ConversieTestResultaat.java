/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.lo3naarbrp;

import nl.moderniseringgba.migratie.test.resultaat.TestResultaat;
import nl.moderniseringgba.migratie.test.resultaat.TestStap;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Test resultaat: conversie lo3 naar brp.
 */
public final class ConversieTestResultaat extends TestResultaat {

    @Element(name = "lo3NaarBrp", required = false)
    private TestStap lo3NaarBrp;

    @Element(name = "rondverteer", required = false)
    private TestStap rondverteer;

    @Element(name = "conversieLog", required = false)
    private TestStap conversieLog;

    @Element(name = "opslaanBrp", required = false)
    private TestStap opslaanBrp;

    @Element(name = "lezenBrp", required = false)
    private TestStap lezenBrp;

    @Element(name = "brpNaarLo3", required = false)
    private TestStap brpNaarLo3;

    /**
     * Constructor.
     * 
     * @param thema
     *            thema
     * @param naam
     *            naam
     */
    protected ConversieTestResultaat(@Attribute(name = "thema", required = false) final String thema, @Attribute(
            name = "naam", required = false) final String naam) {
        super(thema, naam);
    }

    public TestStap getLo3NaarBrp() {
        return lo3NaarBrp;
    }

    public void setLo3NaarBrp(final TestStap lo3NaarBrp) {
        this.lo3NaarBrp = lo3NaarBrp;
    }

    public TestStap getRondverteer() {
        return rondverteer;
    }

    public void setRondverteer(final TestStap rondverteer) {
        this.rondverteer = rondverteer;
    }

    public TestStap getOpslaanBrp() {
        return opslaanBrp;
    }

    public void setOpslaanBrp(final TestStap opslaanBrp) {
        this.opslaanBrp = opslaanBrp;
    }

    public TestStap getLezenBrp() {
        return lezenBrp;
    }

    public void setLezenBrp(final TestStap lezenBrp) {
        this.lezenBrp = lezenBrp;
    }

    public TestStap getBrpNaarLo3() {
        return brpNaarLo3;
    }

    public void setBrpNaarLo3(final TestStap brpNaarLo3) {
        this.brpNaarLo3 = brpNaarLo3;
    }

    public TestStap getConversieLog() {
        return conversieLog;
    }

    public void setConversieLog(final TestStap conversieLog) {
        this.conversieLog = conversieLog;
    }
}
