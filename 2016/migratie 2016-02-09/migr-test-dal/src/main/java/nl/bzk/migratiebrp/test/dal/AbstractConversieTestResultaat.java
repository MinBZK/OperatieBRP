/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.dal;

import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Abstract class voor de resultaten van het testen van de conversie.
 */
public abstract class AbstractConversieTestResultaat extends TestResultaat implements ConversieTestResultaat {
    @Element(name = "syntaxPrecondities", required = false)
    private TestStap syntaxPrecondities;

    @Element(name = "lo3NaarBrp", required = false)
    private TestStap lo3NaarBrp;

    @Element(name = "conversieLog", required = false)
    private TestStap conversieLog;

    @Element(name = "opslaanBrp", required = false)
    private TestStap opslaanBrp;

    @Element(name = "lezenBrp", required = false)
    private TestStap lezenBrp;

    @Element(name = "brpNaarLo3", required = false)
    private TestStap brpNaarLo3;

    @Element(name = "brpNaarLo3-va", required = false)
    private TestStap brpNaarLo3VerschilAnalyse;

    @Element(name = "kruimelpad", required = false)
    private TestStap kruimelpad;

    /**
     * Constructor.
     *
     * @param thema
     *            thema
     * @param naam
     *            naam
     */
    protected AbstractConversieTestResultaat(
        @Attribute(name = "thema", required = false) final String thema,
        @Attribute(name = "naam", required = false) final String naam)
    {
        super(thema, naam);
    }

    @Override
    public final TestStap getSyntaxPrecondities() {
        return syntaxPrecondities;
    }

    @Override
    public final void setSyntaxPrecondities(final TestStap syntaxPrecondities) {
        this.syntaxPrecondities = syntaxPrecondities;
    }

    @Override
    public final TestStap getLo3NaarBrp() {
        return lo3NaarBrp;
    }

    @Override
    public final void setLo3NaarBrp(final TestStap lo3NaarBrp) {
        this.lo3NaarBrp = lo3NaarBrp;
    }

    @Override
    public final TestStap getOpslaanBrp() {
        return opslaanBrp;
    }

    @Override
    public final void setOpslaanBrp(final TestStap opslaanBrp) {
        this.opslaanBrp = opslaanBrp;
    }

    @Override
    public final TestStap getLezenBrp() {
        return lezenBrp;
    }

    @Override
    public final void setLezenBrp(final TestStap lezenBrp) {
        this.lezenBrp = lezenBrp;
    }

    @Override
    public final TestStap getBrpNaarLo3() {
        return brpNaarLo3;
    }

    @Override
    public final void setBrpNaarLo3(final TestStap brpNaarLo3) {
        this.brpNaarLo3 = brpNaarLo3;
    }

    @Override
    public final TestStap getBrpNaarLo3VerschilAnalyse() {
        return brpNaarLo3VerschilAnalyse;
    }

    @Override
    public final void setBrpNaarLo3VerschilAnalyse(final TestStap brpNaarLo3VerschilAnalyse) {
        this.brpNaarLo3VerschilAnalyse = brpNaarLo3VerschilAnalyse;
    }

    @Override
    public final TestStap getConversieLog() {
        return conversieLog;
    }

    @Override
    public final void setConversieLog(final TestStap conversieLog) {
        this.conversieLog = conversieLog;
    }

    @Override
    public final void setKruimelpad(final TestStap kruimelpad) {
        this.kruimelpad = kruimelpad;
    }

    @Override
    public final TestStap getKruimelpad() {
        return kruimelpad;
    }
}
