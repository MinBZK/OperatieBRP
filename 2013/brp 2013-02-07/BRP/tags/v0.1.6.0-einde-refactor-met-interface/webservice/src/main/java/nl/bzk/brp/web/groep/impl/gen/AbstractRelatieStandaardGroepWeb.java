/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.groep.impl.gen;

import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.groep.interfaces.gen.RelatieStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.Land;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.model.objecttype.statisch.Plaats;
import nl.bzk.brp.model.objecttype.statisch.RedenBeeindigingRelatie;
import nl.bzk.brp.web.AbstractGroepWeb;

/**
 *
 */
public abstract class AbstractRelatieStandaardGroepWeb extends AbstractGroepWeb
    implements RelatieStandaardGroepBasis
{

    private Datum datumAanvang;
    private Datum  datumEinde;
    private Land landAanvang;
    private Land landEinde;
    private Partij gemeenteAanvang;
    private Partij gemeenteEinde;
    private RedenBeeindigingRelatie redenBeeindigingRelatie;
    private Omschrijving omschrijvingLocatieAanvang;
    private Omschrijving omschrijvingLocatieEinde;
    private Plaats woonPlaatsAanvang;
    private Plaats woonPlaatsEinde;
    private BuitenlandsePlaats buitenlandsePlaatsAanvang;
    private BuitenlandsePlaats buitenlandsePlaatsEinde;
    private BuitenlandseRegio buitenlandseRegioAanvang;
    private BuitenlandseRegio buitenlandseRegioEinde;

    @Override
    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    @Override
    public Datum getDatumEinde() {
        return datumEinde;
    }

    @Override
    public Land getLandAanvang() {
        return landAanvang;
    }

    @Override
    public Land getLandEinde() {
        return landEinde;
    }

    @Override
    public Partij getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    @Override
    public Partij getGemeenteEinde() {
        return gemeenteEinde;
    }

    @Override
    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    @Override
    public Omschrijving getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    @Override
    public Omschrijving getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    @Override
    public Plaats getWoonPlaatsAanvang() {
        return woonPlaatsAanvang;
    }

    @Override
    public Plaats getWoonPlaatsEinde() {
        return woonPlaatsEinde;
    }

    @Override
    public BuitenlandsePlaats getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    @Override
    public BuitenlandsePlaats getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    @Override
    public BuitenlandseRegio getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    @Override
    public BuitenlandseRegio getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }
}
