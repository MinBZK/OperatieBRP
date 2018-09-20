/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Gegevens over de aanvang en einde van een Relatie
 *
 * 1. Niet van toepassing op de familierechtelijke betrekking. Zie ook overkoepelend memo over Relatie & Betrokkenheid.
 * Het lijkt erop dat de attributen waarmee de 'plaats' (woonplaats, gemeente, land etc etc) wordt aangeduid, alleen van
 * belang is voor huwelijk en geregistreerd partnerschap. Opnemen van de velden voor andere relaties is alleen reden
 * voor verwarring. We kiezen er daarom voor om 'plaats' velden alleen te vullen voor huwelijk en geregistreerd
 * partnerschap. 2. Vorm van historie: alleen formeel. Motivatie: alle (materiële) tijdsaspecten zijn uitgemodelleerd
 * (met datum aanvang en datum einde), waardoor dus geen (extra) materiële historie van toepassing is. Verder 'herleeft'
 * een Huwelijk niet, en wordt het ene Huwelijk niet door een ander Huwelijk beëindigd. Met andere woorden: twee
 * personen die eerst met elkaar Huwen, vervolgens scheiden, en vervolgens weer Huwen, hebben TWEE (verschillende)
 * exemplaren van Relatie: het eerste Huwelijk, en het tweede. Door deze zienswijze (die volgt uit de definitie van
 * Relatie) is er DUS geen sprake van materiële historie, en volstaat dus de formele historie.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface RelatieStandaardGroepBasis extends Groep {

    /**
     * Retourneert Datum aanvang van Standaard.
     *
     * @return Datum aanvang.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumAanvang();

    /**
     * Retourneert Gemeente aanvang van Standaard.
     *
     * @return Gemeente aanvang.
     */
    GemeenteAttribuut getGemeenteAanvang();

    /**
     * Retourneert Woonplaatsnaam aanvang van Standaard.
     *
     * @return Woonplaatsnaam aanvang.
     */
    NaamEnumeratiewaardeAttribuut getWoonplaatsnaamAanvang();

    /**
     * Retourneert Buitenlandse plaats aanvang van Standaard.
     *
     * @return Buitenlandse plaats aanvang.
     */
    BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsAanvang();

    /**
     * Retourneert Buitenlandse regio aanvang van Standaard.
     *
     * @return Buitenlandse regio aanvang.
     */
    BuitenlandseRegioAttribuut getBuitenlandseRegioAanvang();

    /**
     * Retourneert Omschrijving locatie aanvang van Standaard.
     *
     * @return Omschrijving locatie aanvang.
     */
    LocatieomschrijvingAttribuut getOmschrijvingLocatieAanvang();

    /**
     * Retourneert Land/gebied aanvang van Standaard.
     *
     * @return Land/gebied aanvang.
     */
    LandGebiedAttribuut getLandGebiedAanvang();

    /**
     * Retourneert Reden einde van Standaard.
     *
     * @return Reden einde.
     */
    RedenEindeRelatieAttribuut getRedenEinde();

    /**
     * Retourneert Datum einde van Standaard.
     *
     * @return Datum einde.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumEinde();

    /**
     * Retourneert Gemeente einde van Standaard.
     *
     * @return Gemeente einde.
     */
    GemeenteAttribuut getGemeenteEinde();

    /**
     * Retourneert Woonplaatsnaam einde van Standaard.
     *
     * @return Woonplaatsnaam einde.
     */
    NaamEnumeratiewaardeAttribuut getWoonplaatsnaamEinde();

    /**
     * Retourneert Buitenlandse plaats einde van Standaard.
     *
     * @return Buitenlandse plaats einde.
     */
    BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsEinde();

    /**
     * Retourneert Buitenlandse regio einde van Standaard.
     *
     * @return Buitenlandse regio einde.
     */
    BuitenlandseRegioAttribuut getBuitenlandseRegioEinde();

    /**
     * Retourneert Omschrijving locatie einde van Standaard.
     *
     * @return Omschrijving locatie einde.
     */
    LocatieomschrijvingAttribuut getOmschrijvingLocatieEinde();

    /**
     * Retourneert Land/gebied einde van Standaard.
     *
     * @return Land/gebied einde.
     */
    LandGebiedAttribuut getLandGebiedEinde();

}
