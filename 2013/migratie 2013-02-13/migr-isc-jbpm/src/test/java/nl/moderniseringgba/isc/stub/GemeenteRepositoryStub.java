/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.stub;

import java.util.List;

import nl.moderniseringgba.isc.migratie.domein.entities.Gemeente;
import nl.moderniseringgba.isc.migratie.repository.GemeenteRepository;

public class GemeenteRepositoryStub implements GemeenteRepository {

    @Override
    public Gemeente findGemeente(final int gemeenteCode) {
        return null;
    }

    @Override
    public List<Gemeente> getBrpActiveGemeente() {
        return null;
    }

}
