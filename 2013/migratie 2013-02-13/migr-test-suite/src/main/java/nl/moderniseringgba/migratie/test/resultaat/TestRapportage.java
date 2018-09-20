/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.resultaat;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Top level element van de datastructuur die de rapportage van een (regressie)test-run representeert. Dit is een
 * immutable class.
 */
@Root(name = "testrapport")
public final class TestRapportage {

    private final String tijdstip;

    private final List<TestResultaat> resultaten = new ArrayList<TestResultaat>();

    /**
     * Maakt een nieuw, compleet gevuld, testrapport aan.
     * 
     * @param tijdstip
     *            Het tijdstip waarop de test heeft plaatsgevonden
     */
    public TestRapportage(final String tijdstip) {
        this.tijdstip = tijdstip;
    }

    /**
     * Geeft de resultaten terug.
     * 
     * @return De lijst met testgeval-resultaat objecten
     */
    @ElementList(name = "resultaten", inline = true, required = false)
    public List<TestResultaat> getResultaten() {
        return resultaten;
    }

    /**
     * Voeg resultaten toe.
     * 
     * @param resultaten
     *            resultaten
     */
    public void addResultaat(final List<TestResultaat> resultaten) {
        this.resultaten.addAll(resultaten);

    }

    /**
     * Voeg resultaat toe.
     * 
     * @param resultaat
     *            resultaat
     */
    public void addResultaat(final TestResultaat resultaat) {
        this.resultaten.add(resultaat);

    }

    @Element(name = "tijdstip")
    public String getTijdstip() {
        return tijdstip;
    }

}
