/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens.ber;

import nl.bzk.brp.beheer.webapp.repository.stamgegevens.EnumReadonlyRepository;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import org.springframework.stereotype.Repository;

/**
 * SoortMelding repository.
 */
@Repository
public class SoortMeldingRepository extends EnumReadonlyRepository<SoortMelding> {

    /**
     * Constructor.
     */
    public SoortMeldingRepository() {
        super(SoortMelding.class);
    }
}
