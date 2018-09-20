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
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonOverlijdenGroepModel;


/**
 * 1. De vorm van historie is conform bij geboorte: ondanks dat er een materieel tijdsaspect is (datum overlijden) is er
 * g��n sprake van een 'materieel historiepatroon'. Immers, het is absurd om te stellen dat iemand vorig jaar overleden
 * is in plaats X en dit jaar in plaats Y ;-0
 * RvdP 9 jan 2012.
 *
 *
 *
 */
@Embeddable
public class PersoonOverlijdenGroepModel extends AbstractPersoonOverlijdenGroepModel implements PersoonOverlijdenGroep {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PersoonOverlijdenGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumOverlijden datumOverlijden van Overlijden.
     * @param gemeenteOverlijden gemeenteOverlijden van Overlijden.
     * @param woonplaatsOverlijden woonplaatsOverlijden van Overlijden.
     * @param buitenlandsePlaatsOverlijden buitenlandsePlaatsOverlijden van Overlijden.
     * @param buitenlandseRegioOverlijden buitenlandseRegioOverlijden van Overlijden.
     * @param omschrijvingLocatieOverlijden omschrijvingLocatieOverlijden van Overlijden.
     * @param landOverlijden landOverlijden van Overlijden.
     */
    public PersoonOverlijdenGroepModel(final Datum datumOverlijden, final Partij gemeenteOverlijden,
            final Plaats woonplaatsOverlijden, final BuitenlandsePlaats buitenlandsePlaatsOverlijden,
            final BuitenlandseRegio buitenlandseRegioOverlijden,
            final LocatieOmschrijving omschrijvingLocatieOverlijden, final Land landOverlijden)
    {
        super(datumOverlijden, gemeenteOverlijden, woonplaatsOverlijden, buitenlandsePlaatsOverlijden,
                buitenlandseRegioOverlijden, omschrijvingLocatieOverlijden, landOverlijden);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonOverlijdenGroep te kopieren groep.
     */
    public PersoonOverlijdenGroepModel(final PersoonOverlijdenGroep persoonOverlijdenGroep) {
        super(persoonOverlijdenGroep);
    }

}
