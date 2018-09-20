/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import nl.bzk.brp.model.logisch.kern.basis.HuwelijkGeregistreerdPartnerschapStandaardGroepBasis;


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
 */
public interface HuwelijkGeregistreerdPartnerschapStandaardGroep extends
        HuwelijkGeregistreerdPartnerschapStandaardGroepBasis
{

}
