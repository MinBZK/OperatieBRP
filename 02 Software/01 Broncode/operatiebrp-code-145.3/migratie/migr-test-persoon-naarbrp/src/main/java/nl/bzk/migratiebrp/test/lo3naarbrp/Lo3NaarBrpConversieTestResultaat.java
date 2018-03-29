/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.lo3naarbrp;

import nl.bzk.algemeenbrp.util.xml.annotation.Attribute;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.dal.AbstractConversieTestResultaat;

/**
 * Test resultaat: conversie lo3 naar brp.
 */
public final class Lo3NaarBrpConversieTestResultaat extends AbstractConversieTestResultaat {

    @Element(name = "rondverteer", required = false)
    private TestStap rondverteer;

    @Element(name = "rondverteer-va", required = false)
    private TestStap rondverteerVerschilAnalyse;

    /**
     * Constructor.
     * @param thema thema
     * @param naam naam
     */
    protected Lo3NaarBrpConversieTestResultaat(
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
        return isSucces(
                getSyntaxPrecondities(),
                getLo3NaarBrp(),
                rondverteer,
                rondverteerVerschilAnalyse,
                getConversieLog(),
                getOpslaanBrp(),
                getLezenBrp(),
                getBrpNaarLo3(),
                getBrpNaarLo3VerschilAnalyse());
    }

    /**
     * Geef de waarde van rondverteer.
     * @return rondverteer
     */
    public TestStap getRondverteer() {
        return rondverteer;
    }

    /**
     * Zet de waarde van rondverteer.
     * @param rondverteer rondverteer
     */
    public void setRondverteer(final TestStap rondverteer) {
        this.rondverteer = rondverteer;
    }

    /**
     * Geef de waarde van rondverteer verschil analyse.
     * @return rondverteer verschil analyse
     */
    public TestStap getRondverteerVerschilAnalyse() {
        return rondverteerVerschilAnalyse;
    }

    /**
     * Zet de waarde van rondverteer verschil analyse.
     * @param rondverteerVerschilAnalyse rondverteer verschil analyse
     */
    public void setRondverteerVerschilAnalyse(final TestStap rondverteerVerschilAnalyse) {
        this.rondverteerVerschilAnalyse = rondverteerVerschilAnalyse;
    }
}
