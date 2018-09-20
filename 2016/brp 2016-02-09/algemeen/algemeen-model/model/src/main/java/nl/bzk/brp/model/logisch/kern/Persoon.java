/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import nl.bzk.brp.model.RootObject;


/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 * <p/>
 * Buiten de BRP wordt ook wel de term 'Natuurlijk persoon' gebruikt. In de BRP worden zowel personen waarvan de bijhouding valt onder afdeling I
 * ('Ingezetenen') van de Wet BRP, als personen waarvoor de bijhouding onder afdeling II ('Niet ingezetenen') van de Wet BRP valt, ingeschreven.
 * <p/>
 * 1. Binnen BRP gebruiken we het begrip Persoon, waar elders gebruikelijk is voor dit objecttype de naam "Natuurlijk persoon" te gebruiken. Binnen de
 * context van BRP hebben we het bij het hanteren van de term Persoon echter nooit over niet-natuurlijke personen zoals rechtspersonen. Het gebruik van de
 * term Persoon is verder dermate gebruikelijk binnen de context van BRP, dat ervoor gekozen is deze naam te blijven hanteren. We spreken dus over Persoon
 * en niet over "Natuurlijk persoon". 2. Voor "alternatieve realiteit" personen, en voor niet-ingeschrevenen, gebruiken we in het logisch & operationeel
 * model (maar dus NIET in de gegevensset) het construct 'persoon'. Om die reden zijn veel groepen niet verplicht, die wellicht wel verplicht zouden zijn
 * in geval van (alleen) ingeschrevenen. RvdP 27 juni 2011
 */
public interface Persoon extends PersoonBasis, RootObject {

    /**
     * Checkt of de persoon de Nederlandse nationaliteit heeft.
     *
     * @return true indien persoon Nederlandse nationaliteit heeft.
     */
    boolean heeftNederlandseNationaliteit();

    /**
     * Controleert of een persoon een Ingezetene is.
     *
     * @return true als persoon een Ingezetene is
     */
    boolean isIngezetene();

    /**
     * Controleert of de persoon gelijk is aan de persoon die wordt meegegeven.
     *
     * @param persoon de persoon waarmee wordt vergeleken
     * @return true als de personen gelijk zijn.
     */
    boolean isPersoonGelijkAan(Persoon persoon);
}
