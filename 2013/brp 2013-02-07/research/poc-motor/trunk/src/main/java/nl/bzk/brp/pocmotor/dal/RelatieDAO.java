/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal;

import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Relatie;
import org.springframework.stereotype.Repository;

/**
 * Data Access Object voor het ophalen en wijzigen van {@link Relatie} instanties.
 */
@Repository
public interface RelatieDAO {

    void persisteerFamilieRechtelijkeBetrekking(final Relatie geboorteRelatie);

    void voegVaderToeAanFamilieRechtelijkeBetrekking(final Persoon vader, final Persoon kind);

}
