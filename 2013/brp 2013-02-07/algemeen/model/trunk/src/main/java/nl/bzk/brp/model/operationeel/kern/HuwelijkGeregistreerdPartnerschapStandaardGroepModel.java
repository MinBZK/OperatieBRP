/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaats;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegio;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenBeeindigingRelatie;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschapStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractHuwelijkGeregistreerdPartnerschapStandaardGroepModel;


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
@Embeddable
public class HuwelijkGeregistreerdPartnerschapStandaardGroepModel extends
        AbstractHuwelijkGeregistreerdPartnerschapStandaardGroepModel implements
        HuwelijkGeregistreerdPartnerschapStandaardGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected HuwelijkGeregistreerdPartnerschapStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param datumAanvang datumAanvang van Standaard.
     * @param gemeenteAanvang gemeenteAanvang van Standaard.
     * @param woonplaatsAanvang woonplaatsAanvang van Standaard.
     * @param buitenlandsePlaatsAanvang buitenlandsePlaatsAanvang van Standaard.
     * @param buitenlandseRegioAanvang buitenlandseRegioAanvang van Standaard.
     * @param omschrijvingLocatieAanvang omschrijvingLocatieAanvang van Standaard.
     * @param landAanvang landAanvang van Standaard.
     * @param redenEinde redenEinde van Standaard.
     * @param datumEinde datumEinde van Standaard.
     * @param gemeenteEinde gemeenteEinde van Standaard.
     * @param woonplaatsEinde woonplaatsEinde van Standaard.
     * @param buitenlandsePlaatsEinde buitenlandsePlaatsEinde van Standaard.
     * @param buitenlandseRegioEinde buitenlandseRegioEinde van Standaard.
     * @param omschrijvingLocatieEinde omschrijvingLocatieEinde van Standaard.
     * @param landEinde landEinde van Standaard.
     */
    public HuwelijkGeregistreerdPartnerschapStandaardGroepModel(final Datum datumAanvang, final Partij gemeenteAanvang,
            final Plaats woonplaatsAanvang, final BuitenlandsePlaats buitenlandsePlaatsAanvang,
            final BuitenlandseRegio buitenlandseRegioAanvang, final LocatieOmschrijving omschrijvingLocatieAanvang,
            final Land landAanvang, final RedenBeeindigingRelatie redenEinde, final Datum datumEinde,
            final Partij gemeenteEinde, final Plaats woonplaatsEinde, final BuitenlandsePlaats buitenlandsePlaatsEinde,
            final BuitenlandseRegio buitenlandseRegioEinde, final LocatieOmschrijving omschrijvingLocatieEinde,
            final Land landEinde)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(datumAanvang, gemeenteAanvang, woonplaatsAanvang, buitenlandsePlaatsAanvang, buitenlandseRegioAanvang,
                omschrijvingLocatieAanvang, landAanvang, redenEinde, datumEinde, gemeenteEinde, woonplaatsEinde,
                buitenlandsePlaatsEinde, buitenlandseRegioEinde, omschrijvingLocatieEinde, landEinde);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param huwelijkGeregistreerdPartnerschapStandaardGroep te kopieren groep.
     */
    public HuwelijkGeregistreerdPartnerschapStandaardGroepModel(
            final HuwelijkGeregistreerdPartnerschapStandaardGroep huwelijkGeregistreerdPartnerschapStandaardGroep)
    {
        super(huwelijkGeregistreerdPartnerschapStandaardGroep);
    }

}
