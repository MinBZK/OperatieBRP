/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.web.groep.impl.gen;

import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonGeboorteGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.Land;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.model.objecttype.statisch.Plaats;
import nl.bzk.brp.model.web.AbstractGroepWeb;

/**
 * .
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeboorteGroepWeb extends AbstractGroepWeb
    implements PersoonGeboorteGroepBasis
{
    private Datum datumGeboorte;
    private Partij gemeenteGeboorte;
    private Plaats woonplaatsGeboorte;
    private BuitenlandsePlaats buitenlandseGeboortePlaats;
    private BuitenlandseRegio buitenlandseRegioGeboorte;
    private Land landGeboorte;
    private Omschrijving omschrijvingGeboorteLocatie;




    @Override
    public Datum getDatumGeboorte() {
        return datumGeboorte;
    }

    @Override
    public Partij getGemeenteGeboorte() {
        return gemeenteGeboorte;
    }

    @Override
    public Plaats getWoonplaatsGeboorte() {
        return woonplaatsGeboorte;
    }

    @Override
    public BuitenlandsePlaats getBuitenlandseGeboortePlaats() {
        return buitenlandseGeboortePlaats;
    }

    @Override
    public BuitenlandseRegio getBuitenlandseRegioGeboorte() {
        return buitenlandseRegioGeboorte;
    }

    @Override
    public Land getLandGeboorte() {
        return landGeboorte;
    }

    @Override
    public Omschrijving getOmschrijvingGeboorteLocatie() {
        return omschrijvingGeboorteLocatie;
    }

}
