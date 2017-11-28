/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.brp.levering.lo3.mapper.NationaliteitMapper;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import org.springframework.stereotype.Component;

/**
 * Verwerk onderzoeken op de a-laag (identiteit groep) van nationaliteit.
 */
@Component
public class NationaliteitIdentiteitOnderzoekVerwerker extends BasisIdentiteitOnderzoekVerwerker<Lo3NationaliteitInhoud> {

    /**
     * Default constructor.
     */
    public NationaliteitIdentiteitOnderzoekVerwerker() {
        super(NationaliteitMapper.IDENTITEIT_GROEP_ELEMENT);
    }

}
