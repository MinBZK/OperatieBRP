/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber.basis;

import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;
import nl.bzk.brp.model.logisch.kern.Persoon;


/**
 * De bijhouding van gegevens over een persoon door middel van een administratieve handeling.
 *
 * Bijhoudingen gebeuren doordat een administratieve handeling wordt verwerkt dat tot wijzigingen leid van
 * persoonsgegevens. Daar waar een administratieve handeling leidt tot een aanpassing van het veld datumtijdstip laatste
 * wijziging van die persoon, is er sprake van een "Administratieve handeling\Bijgehouden persoon". Meer informatie is
 * te vinden bij de beschrijving van de verwerkingsregel voor datumtijd laatste wijziging. Kort gezegd komt het neer op:
 * - 0e graads: dit zijn wijzigingen in het desbetreffende record van de tabel Kern.Pers zelf.
 * - 1e graads: dit zijn wijzigingen in een record van een tabel die verwijst naar �het desbetreffende record van de
 * tabel Kern.Pers�.
 * - 2e graads: dit zijn wijzigingen in een record van een tabel�
 * o � die verwijst naar �een record van een tabel die verwijst naar het desbetreffende record van de tabel Kern.Pers�
 * o � of waarnaar wordt verwezen door �een record van een tabel die verwijst naar het desbetreffende record van de
 * tabel Kern.Pers�.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:52 CET 2013.
 */
public interface AdministratieveHandelingBijgehoudenPersoonBasis extends ObjectType {

    /**
     * Retourneert Administratieve handeling van Administratieve handeling \ Bijgehouden persoon.
     *
     * @return Administratieve handeling.
     */
    AdministratieveHandeling getAdministratieveHandeling();

    /**
     * Retourneert Persoon van Administratieve handeling \ Bijgehouden persoon.
     *
     * @return Persoon.
     */
    Persoon getPersoon();

}
