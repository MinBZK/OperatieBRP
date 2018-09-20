/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3;

import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Conversie test resultaat.
 */
public final class BrpNaarLo3ConversieTestResultaat extends TestResultaat {

    @Element(name = "lezen", required = false)
    private TestStap lezen;

    @Element(name = "conversie", required = false)
    private TestStap conversie;

    /**
     * Constructor.
     * 
     * @param thema
     *            thema
     * @param naam
     *            naam
     */
    protected BrpNaarLo3ConversieTestResultaat(@Attribute(name = "thema", required = false) final String thema, @Attribute(name = "naam",
            required = false) final String naam)
    {
        super(thema, naam);
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.test.common.resultaat.TestResultaat#isSucces()
     */
    @Override
    public boolean isSucces() {
        return isSucces(lezen, conversie);
    }

    /**
     * Geef de waarde van lezen.
     *
     * @return lezen
     */
    public TestStap getLezen() {
        return lezen;
    }

    /**
     * Zet de waarde van lezen.
     *
     * @param lezen
     *            lezen
     */
    public void setLezen(final TestStap lezen) {
        this.lezen = lezen;
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
}
