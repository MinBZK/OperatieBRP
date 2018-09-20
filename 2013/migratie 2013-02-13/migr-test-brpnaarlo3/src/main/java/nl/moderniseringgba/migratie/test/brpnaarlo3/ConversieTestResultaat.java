/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3;

import nl.moderniseringgba.migratie.test.resultaat.TestResultaat;
import nl.moderniseringgba.migratie.test.resultaat.TestStap;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Conversie test resultaat.
 */
public final class ConversieTestResultaat extends TestResultaat {

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
    protected ConversieTestResultaat(@Attribute(name = "thema", required = false) final String thema, @Attribute(
            name = "naam", required = false) final String naam) {
        super(thema, naam);
    }

    public TestStap getLezen() {
        return lezen;
    }

    public void setLezen(final TestStap lezen) {
        this.lezen = lezen;
    }

    public TestStap getConversie() {
        return conversie;
    }

    public void setConversie(final TestStap conversie) {
        this.conversie = conversie;
    }
}
