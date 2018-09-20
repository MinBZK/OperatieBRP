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
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonGeboorteGroepModel;


/**
 * Geboortegevens over een Persoon.
 *
 * Geboortegegevens zijn belangrijke identificerende gegevens. De geboortelocatie is zodanig gespecificeerd dat nagenoeg
 * alle situaties verwerkt kunnen worden.
 * Verreweg de meeste gevallen passen in de structuur 'land - regio - buitenlandse plaats' of indien het een geboorte is
 * in het Europese deel van Nederland in de structuur 'land - gemeente - woonplaats'. In uitzonderingssituaties zijn
 * deze structuren niet toereikend. In dat geval wordt 'omschrijving geboortelocatie' gebruikt. In voorkomende gevallen
 * kan hier een verwijzing naar bijvoorbeeld geografische co�rdinaten staan.
 *
 * 1. Het is denkbaar om 'Geboorte' als een levensgebeurtenis te beschouwen, c.q. een objecttype te construeren die
 * klinkt als 'Gebeurtenis'. Hiervan zouden dan een aantal relevante attributen onderkend kunnen worden, zoals een punt
 * in de tijddimensie (bijv. datum of datum) en in de ruimte (middels woonplaats, gemeente, land, etc etc). Hier is niet
 * voor gekozen.
 *
 * Het binnen de scope van de BRP brengen van Levensgebeurtenis heeft echter grote impact, en introduceert vragen waar
 * nu nog geen antwoord op bekend is ('is adoptie een levensgebeurtenis?'). De feiten die we bij bijvoorbeeld de
 * Geboorte, Overlijden, sluiten en ontbinding Huwelijk willen weten is in essentie de aanuiding van ruimte (plaats,
 * gemeente, ...) en tijd. Behalve deze logische identificatie van ruimte en tijd is er echter 'niets' dat we hierover
 * willen weten. Anders gesteld: een dergelijke entiteit heeft g��n bestaansrecht. We onderkennen daarom niet een apart
 * objecttype hiervoor, en modelleren de relevante attributen (datum, gemeente, woonplaats, ..., land) uit daar waar het
 * zich voordoet.
 *
 * Consequenties:
 * Door gebeurtenis direct 'specifiek' uit te modelleren (en dus bijvoorbeeld gewoon een groepje 'geboortegegevens' op
 * te nemen bij de Persoon) wordt het model eenvoudiger. Het nadeel is dat dezelfde soort gegevens (aanduiding van een
 * punt in tijd en tuimte) herhaaldelijk gespecificeerd moet worden, maar dat is een overzienbaar resultaat.
 * 2. Geboorte kent g��n (aparte) materi�le historie: het refereert naar ��n moment in de (materi�le) tijd, op de datum
 * geboorte; er is verder geen (materi�le) 'geldigheidsperiode' voor bijv. datum geboorte: dit jaar, vorig jaar en
 * volgend jaar ben je nog steeds in 1969 geboren ;-0
 * RvdP 6-1-2012
 *
 *
 *
 */
@Embeddable
public class PersoonGeboorteGroepModel extends AbstractPersoonGeboorteGroepModel implements PersoonGeboorteGroep {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PersoonGeboorteGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumGeboorte datumGeboorte van Geboorte.
     * @param gemeenteGeboorte gemeenteGeboorte van Geboorte.
     * @param woonplaatsGeboorte woonplaatsGeboorte van Geboorte.
     * @param buitenlandsePlaatsGeboorte buitenlandsePlaatsGeboorte van Geboorte.
     * @param buitenlandseRegioGeboorte buitenlandseRegioGeboorte van Geboorte.
     * @param omschrijvingLocatieGeboorte omschrijvingLocatieGeboorte van Geboorte.
     * @param landGeboorte landGeboorte van Geboorte.
     */
    public PersoonGeboorteGroepModel(final Datum datumGeboorte, final Partij gemeenteGeboorte,
            final Plaats woonplaatsGeboorte, final BuitenlandsePlaats buitenlandsePlaatsGeboorte,
            final BuitenlandseRegio buitenlandseRegioGeboorte, final LocatieOmschrijving omschrijvingLocatieGeboorte,
            final Land landGeboorte)
    {
        super(datumGeboorte, gemeenteGeboorte, woonplaatsGeboorte, buitenlandsePlaatsGeboorte,
                buitenlandseRegioGeboorte, omschrijvingLocatieGeboorte, landGeboorte);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonGeboorteGroep te kopieren groep.
     */
    public PersoonGeboorteGroepModel(final PersoonGeboorteGroep persoonGeboorteGroep) {
        super(persoonGeboorteGroep);
    }

}
