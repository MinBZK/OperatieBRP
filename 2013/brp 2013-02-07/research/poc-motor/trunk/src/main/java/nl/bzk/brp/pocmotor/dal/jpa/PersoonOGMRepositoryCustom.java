/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal.jpa;

import java.util.Set;

import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonNationaliteit;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonVoornaam;

public interface PersoonOGMRepositoryCustom {

    void persisteerGerelateerdeHisEntiteitenVoorNieuwePersoon(final Persoon persoon,
                                                              final Set<PersoonVoornaam> voornamen,
                                                              final Set<PersoonNationaliteit> nationaliteiten,
                                                              final Set<PersoonGeslachtsnaamcomponent> geslachtsnaamcomponenten);
}
