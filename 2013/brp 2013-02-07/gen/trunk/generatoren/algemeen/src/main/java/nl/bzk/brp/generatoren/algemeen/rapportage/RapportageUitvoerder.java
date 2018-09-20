/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.rapportage;

import java.io.File;
import java.util.List;

import nl.bprbzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bprbzk.brp.generatoren.rapportage.Klasse;

/**
 * Interface voor een rapportage uitvoerder. De interface definieert functies om een rapportage bestand te
 * initialiseren/in te lezen, bij te werken en vervolgens weg te schrijven.
 */
public interface RapportageUitvoerder {

    /**
     * Initialiseert het rapportage bestand. Het rapportage bestand wordt hierbij ingelezen (ge-parsed).
     *
     * @param generatieXmlRapportageBestand rapportage bestand.
     */
    void initialiseerRapportageBestand(final File generatieXmlRapportageBestand);

    /**
     * Werkt het rapportage bestand bij met gegenereerde java klassen.
     *
     * @param generatorNaam de naam van de generator die de klassen heeft gegenereerd.
     * @param klassen de gegenereerde klassen.
     */
    void rapporteerGegenereerdeKlassen(final GeneratorNaam generatorNaam, final List<Klasse> klassen);

    /**
     * Schrijft de rapportage weg (nadat de generatoren de rapportage hebben bijgewerkt).
     *
     * @param generatieXmlRapportageBestand rapportage bestand.
     */
    void schrijfRapportageWeg(final File generatieXmlRapportageBestand);

}
