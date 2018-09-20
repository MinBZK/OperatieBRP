/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaats;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegio;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenBeeindigingRelatie;
import nl.bzk.brp.model.basis.Groep;


/**
 * Gegevens over de aanvang en einde van een Relatie
 *
 * 1. Niet van toepassing op de familierechtelijke betrekking. Zie ook overkoepelend memo over Relatie & Betrokkenheid.
 * Het lijkt erop dat de attributen waarmee de 'plaats' (woonplaats, gemeente, land etc etc) wordt aangeduid, alleen van
 * belang is voor huwelijk en geregistreerd partnerschap. Opnemen van de velden voor andere relaties is alleen reden
 * voor verwarring. We kiezen er daarom voor om 'plaats' velden alleen te vullen voor huwelijk en geregistreerd
 * partnerschap.
 * 2. Vorm van historie: alleen formeel. Motivatie: alle (materi�le) tijdsaspecten zijn uitgemodelleerd (met datum
 * aanvang en datum einde), waardoor dus geen (extra) materi�le historie van toepassing is. Verder 'herleeft' een
 * Huwelijk niet, en wordt het ene Huwelijk niet door een ander Huwelijk be�indigd. Met andere woorden: twee personen
 * die eerst met elkaar Huwen, vervolgens scheiden, en vervolgens weer Huwen, hebben TWEE (verschillende) exemplaren van
 * Relatie: het eerste Huwelijk, en het tweede.
 * Door deze zienswijze (die volgt uit de definitie van Relatie) is er DUS geen sprake van materi�le historie, en
 * volstaat dus de formele historie.
 * RvdP 17 jan 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:52 CET 2013.
 */
public interface HuwelijkGeregistreerdPartnerschapStandaardGroepBasis extends Groep {

    /**
     * Retourneert Datum aanvang van Standaard.
     *
     * @return Datum aanvang.
     */
    Datum getDatumAanvang();

    /**
     * Retourneert Gemeente aanvang van Standaard.
     *
     * @return Gemeente aanvang.
     */
    Partij getGemeenteAanvang();

    /**
     * Retourneert Woonplaats aanvang van Standaard.
     *
     * @return Woonplaats aanvang.
     */
    Plaats getWoonplaatsAanvang();

    /**
     * Retourneert Buitenlandse plaats aanvang van Standaard.
     *
     * @return Buitenlandse plaats aanvang.
     */
    BuitenlandsePlaats getBuitenlandsePlaatsAanvang();

    /**
     * Retourneert Buitenlandse regio aanvang van Standaard.
     *
     * @return Buitenlandse regio aanvang.
     */
    BuitenlandseRegio getBuitenlandseRegioAanvang();

    /**
     * Retourneert Omschrijving locatie aanvang van Standaard.
     *
     * @return Omschrijving locatie aanvang.
     */
    LocatieOmschrijving getOmschrijvingLocatieAanvang();

    /**
     * Retourneert Land aanvang van Standaard.
     *
     * @return Land aanvang.
     */
    Land getLandAanvang();

    /**
     * Retourneert Reden einde van Standaard.
     *
     * @return Reden einde.
     */
    RedenBeeindigingRelatie getRedenEinde();

    /**
     * Retourneert Datum einde van Standaard.
     *
     * @return Datum einde.
     */
    Datum getDatumEinde();

    /**
     * Retourneert Gemeente einde van Standaard.
     *
     * @return Gemeente einde.
     */
    Partij getGemeenteEinde();

    /**
     * Retourneert Woonplaats einde van Standaard.
     *
     * @return Woonplaats einde.
     */
    Plaats getWoonplaatsEinde();

    /**
     * Retourneert Buitenlandse plaats einde van Standaard.
     *
     * @return Buitenlandse plaats einde.
     */
    BuitenlandsePlaats getBuitenlandsePlaatsEinde();

    /**
     * Retourneert Buitenlandse regio einde van Standaard.
     *
     * @return Buitenlandse regio einde.
     */
    BuitenlandseRegio getBuitenlandseRegioEinde();

    /**
     * Retourneert Omschrijving locatie einde van Standaard.
     *
     * @return Omschrijving locatie einde.
     */
    LocatieOmschrijving getOmschrijvingLocatieEinde();

    /**
     * Retourneert Land einde van Standaard.
     *
     * @return Land einde.
     */
    Land getLandEinde();

}
