/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;

/**
 * Service voor het maken van de uiteindelijke selectieresultaatset bestanden. De verzameling
 * fragmenten worden verwerkt tot een reeks selectie XSD valide persoonberichten. Daarnaast
 * wordt een totaalbestand gegenereerd. Hierin staat het totaal aan personen en berichten
 * dat gegenereerd is.
 * <p>
 * Deze service is impliciet threadsafe omdat het deel uit maakt van een JMS messagegroup.
 * Alle writes mbt een gegeven selectietaak zijn serieel.
 */
public interface MaakSelectieResultaatTaakVerwerkerService {

    /**
     * verwerk de maak selectie resultaat taak.
     * @param maakSelectieResultaatTaak maakSelectieResultaatTaak
     */
    void verwerk(MaakSelectieResultaatTaak maakSelectieResultaatTaak);
}
