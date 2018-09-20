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
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;


/**
 * 1. De vorm van historie is conform bij geboorte: ondanks dat er een materieel tijdsaspect is (datum overlijden) is er g��n sprake van een 'materieel
 * historiepatroon'. Immers, het is absurd om te stellen dat iemand vorig jaar overleden is in plaats X en dit jaar in plaats Y ;-0 RvdP 9 jan 2012.
 */
@Embeddable
public class PersoonOverlijdenGroepModel extends AbstractPersoonOverlijdenGroepModel implements PersoonOverlijdenGroep {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonOverlijdenGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumOverlijden               datumOverlijden van Overlijden.
     * @param gemeenteOverlijden            gemeenteOverlijden van Overlijden.
     * @param woonplaatsOverlijden          woonplaatsOverlijden van Overlijden.
     * @param buitenlandsePlaatsOverlijden  buitenlandsePlaatsOverlijden van Overlijden.
     * @param buitenlandseRegioOverlijden   buitenlandseRegioOverlijden van Overlijden.
     * @param omschrijvingLocatieOverlijden omschrijvingLocatieOverlijden van Overlijden.
     * @param landgebiedOverlijden          landgebiedOverlijden van Overlijden.
     */
    public PersoonOverlijdenGroepModel(final DatumEvtDeelsOnbekendAttribuut datumOverlijden,
        final GemeenteAttribuut gemeenteOverlijden, final NaamEnumeratiewaardeAttribuut woonplaatsOverlijden,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsOverlijden,
        final BuitenlandseRegioAttribuut buitenlandseRegioOverlijden,
        final LocatieomschrijvingAttribuut omschrijvingLocatieOverlijden,
        final LandGebiedAttribuut landgebiedOverlijden)
    {
        super(datumOverlijden, gemeenteOverlijden, woonplaatsOverlijden, buitenlandsePlaatsOverlijden,
            buitenlandseRegioOverlijden, omschrijvingLocatieOverlijden, landgebiedOverlijden);
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
