/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.resultaat;

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
    protected TestResultaat(
        @Attribute(name = "thema", required = false) final String thema,
        @Attribute(name = "naam", required = false) final String naam)
    {
        super();
        this.thema = thema;
        this.naam = naam;
    }

    /**
     * Geef de waarde van thema.
     *
     * @return thema
     */
    @Attribute(name = "thema", required = false)
    public final String getThema() {
        return thema;
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    @Attribute(name = "naam", required = false)
    public final String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van bron.
     *
     * @return bron
     */
    public final String getBron() {
        return bron;
    }

    /**
     * Zet de waarde van bron.
     *
     * @param bron
     *            bron
     */
    public final void setBron(final String bron) {
        this.bron = bron;
    }

    /**
     * Geef de waarde van foutmelding.
     *
     * @return foutmelding
     */
    public final Foutmelding getFoutmelding() {
        return foutmelding;
    }

    /**
     * Zet de waarde van foutmelding.
     *
     * @param foutmelding
     *            foutmelding
     */
    public final void setFoutmelding(final Foutmelding foutmelding) {
        this.foutmelding = foutmelding;
    }

    /**
     * Geef de succes.
     *
     * @return was the test succesfull?
     */
    public abstract boolean isSucces();

    /**
     * Controleer of alle stappen ok zijn.
     * 
     * @param stappen
     *            stappen
     * @return true, als alle stappen ok zijn, anders false
     */
    public static final boolean isSucces(final TestStap... stappen) {
        int aantalStappen = 0;
        int aantalOk = 0;
        for (final TestStap stap : stappen) {
            if (stap != null) {
                aantalStappen++;
                final TestStatus statusStap = stap.getStatus();
                if (TestStatus.OK == statusStap || TestStatus.GEEN_VERWACHTING == statusStap) {
                    aantalOk++;
                }
            }
        }
        return aantalStappen == aantalOk;
    }

}
