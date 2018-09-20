/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.preconditie;

import nl.moderniseringgba.migratie.test.resultaat.TestResultaat;
import nl.moderniseringgba.migratie.test.resultaat.TestStap;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Test resultaat: precondities.
 */
public final class PreconditieTestResultaat extends TestResultaat {

    @Element(name = "preconditie", required = false)
    private TestStap preconditie;

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
    protected PreconditieTestResultaat(@Attribute(name = "thema", required = false) final String thema, @Attribute(
            name = "naam", required = false) final String naam) {
        super(thema, naam);
    }

    public TestStap getPreconditie() {
        return preconditie;
    }

    public void setPreconditie(final TestStap preconditie) {
        this.preconditie = preconditie;
    }

    public TestStap getConversie() {
        return conversie;
    }

    public void setConversie(final TestStap conversie) {
        this.conversie = conversie;
    }

}
