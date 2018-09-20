/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.logisch.kern.RelatieStandaardGroep;

/**
 * Gegevens over de aanvang en einde van een Relatie
 *
 * 1. Niet van toepassing op de familierechtelijke betrekking. Zie ook overkoepelend memo over Relatie & Betrokkenheid.
 * Het lijkt erop dat de attributen waarmee de 'plaats' (woonplaats, gemeente, land etc etc) wordt aangeduid, alleen van
 * belang is voor huwelijk en geregistreerd partnerschap. Opnemen van de velden voor andere relaties is alleen reden
 * voor verwarring. We kiezen er daarom voor om 'plaats' velden alleen te vullen voor huwelijk en geregistreerd
 * partnerschap. 2. Vorm van historie: alleen formeel. Motivatie: alle (materi�le) tijdsaspecten zijn uitgemodelleerd
 * (met datum aanvang en datum einde), waardoor dus geen (extra) materi�le historie van toepassing is. Verder 'herleeft'
 * een Huwelijk niet, en wordt het ene Huwelijk niet door een ander Huwelijk be�indigd. Met andere woorden: twee
 * personen die eerst met elkaar Huwen, vervolgens scheiden, en vervolgens weer Huwen, hebben TWEE (verschillende)
 * exemplaren van Relatie: het eerste Huwelijk, en het tweede. Door deze zienswijze (die volgt uit de definitie van
 * Relatie) is er DUS geen sprake van materi�le historie, en volstaat dus de formele historie.
 *
 *
 *
 */
@Embeddable
public class RelatieStandaardGroepModel extends AbstractRelatieStandaardGroepModel implements RelatieStandaardGroep {

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected RelatieStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param datumAanvang datumAanvang van Standaard.
     * @param gemeenteAanvang gemeenteAanvang van Standaard.
     * @param woonplaatsnaamAanvang woonplaatsnaamAanvang van Standaard.
     * @param buitenlandsePlaatsAanvang buitenlandsePlaatsAanvang van Standaard.
     * @param buitenlandseRegioAanvang buitenlandseRegioAanvang van Standaard.
     * @param omschrijvingLocatieAanvang omschrijvingLocatieAanvang van Standaard.
     * @param landGebiedAanvang landGebiedAanvang van Standaard.
     * @param redenEinde redenEinde van Standaard.
     * @param datumEinde datumEinde van Standaard.
     * @param gemeenteEinde gemeenteEinde van Standaard.
     * @param woonplaatsnaamEinde woonplaatsnaamEinde van Standaard.
     * @param buitenlandsePlaatsEinde buitenlandsePlaatsEinde van Standaard.
     * @param buitenlandseRegioEinde buitenlandseRegioEinde van Standaard.
     * @param omschrijvingLocatieEinde omschrijvingLocatieEinde van Standaard.
     * @param landGebiedEinde landGebiedEinde van Standaard.
     */
    public RelatieStandaardGroepModel(
        final DatumEvtDeelsOnbekendAttribuut datumAanvang,
        final GemeenteAttribuut gemeenteAanvang,
        final NaamEnumeratiewaardeAttribuut woonplaatsnaamAanvang,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsAanvang,
        final BuitenlandseRegioAttribuut buitenlandseRegioAanvang,
        final LocatieomschrijvingAttribuut omschrijvingLocatieAanvang,
        final LandGebiedAttribuut landGebiedAanvang,
        final RedenEindeRelatieAttribuut redenEinde,
        final DatumEvtDeelsOnbekendAttribuut datumEinde,
        final GemeenteAttribuut gemeenteEinde,
        final NaamEnumeratiewaardeAttribuut woonplaatsnaamEinde,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsEinde,
        final BuitenlandseRegioAttribuut buitenlandseRegioEinde,
        final LocatieomschrijvingAttribuut omschrijvingLocatieEinde,
        final LandGebiedAttribuut landGebiedEinde)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(datumAanvang,
              gemeenteAanvang,
              woonplaatsnaamAanvang,
              buitenlandsePlaatsAanvang,
              buitenlandseRegioAanvang,
              omschrijvingLocatieAanvang,
              landGebiedAanvang,
              redenEinde,
              datumEinde,
              gemeenteEinde,
              woonplaatsnaamEinde,
              buitenlandsePlaatsEinde,
              buitenlandseRegioEinde,
              omschrijvingLocatieEinde,
              landGebiedEinde);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param relatieStandaardGroep te kopieren groep.
     */
    public RelatieStandaardGroepModel(final RelatieStandaardGroep relatieStandaardGroep) {
        super(relatieStandaardGroep);
    }

}
