/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.resultaat;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementList;
import nl.bzk.algemeenbrp.util.xml.annotation.Root;

/**
 * Top level element van de datastructuur die de rapportage van een (regressie)test-run representeert. Dit is een
 * immutable class.
 */
@Root(name = "testrapport")
public final class TestRapportage {

    private final String tijdstip;

    private final List<TestResultaat> resultaten = new ArrayList<>();

    /**
     * Maakt een nieuw, compleet gevuld, testrapport aan.
     * @param tijdstip Het tijdstip waarop de test heeft plaatsgevonden
     */
    public TestRapportage(final String tijdstip) {
        this.tijdstip = tijdstip;
    }

    /**
     * Geeft de resultaten terug.
     * @return De lijst met testgeval-resultaat objecten
     */
    @ElementList(name = "resultaten", entry = "resultaat", inline = true, type = TestResultaat.class, required = false)
    public List<TestResultaat> getResultaten() {
        return resultaten;
    }

    /**
     * Voeg resultaten toe.
     * @param deResultaten resultaten
     */
    public void addResultaat(final List<TestResultaat> deResultaten) {
        resultaten.addAll(deResultaten);

    }

    /**
     * Voeg resultaat toe.
     * @param resultaat resultaat
     */
    public void addResultaat(final TestResultaat resultaat) {
        resultaten.add(resultaat);

    }

    /**
     * Geef de waarde van tijdstip.
     * @return tijdstip
     */
    @Element(name = "tijdstip")
    public String getTijdstip() {
        return tijdstip;
    }

}
