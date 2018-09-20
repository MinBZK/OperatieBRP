/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.resultaat;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Resultaat van een testgeval of een onderdeel daarvan t.b.v. rapportage van het resultaat. In het rapport
 * correspondeert dit met een enkele regel.
 */
@Root(name = "resultaat")
public abstract class TestResultaat {

    private final String thema;
    private final String naam;

    @Element(name = "foutmelding", required = false)
    private Foutmelding foutmelding;

    @Attribute(name = "bron", required = false)
    private String bron;

    /**
     * Constructor.
     * 
     * @param thema
     *            thema
     * @param naam
     *            naam
     */
    protected TestResultaat(@Attribute(name = "thema", required = false) final String thema, @Attribute(
            name = "naam", required = false) final String naam) {
        super();
        this.thema = thema;
        this.naam = naam;

    }

    @Attribute(name = "thema", required = false)
    public final String getThema() {
        return thema;
    }

    @Attribute(name = "naam", required = false)
    public final String getNaam() {
        return naam;
    }

    public final String getBron() {
        return bron;
    }

    public final void setBron(final String bron) {
        this.bron = bron;
    }

    public final Foutmelding getFoutmelding() {
        return foutmelding;
    }

    public final void setFoutmelding(final Foutmelding foutmelding) {
        this.foutmelding = foutmelding;
    }

}
