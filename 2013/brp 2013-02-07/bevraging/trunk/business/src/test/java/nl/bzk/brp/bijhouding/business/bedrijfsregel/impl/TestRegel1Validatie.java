/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.bedrijfsregel.impl;

import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bijhouding.business.bedrijfsregel.AbstractValidatie;
import nl.bzk.brp.bijhouding.business.bedrijfsregel.ValidatieResultaat;
import nl.bzk.brp.bijhouding.business.service.impl.TestBerichtX;


public class TestRegel1Validatie extends AbstractValidatie<TestBerichtX> {

    @Override
    public ValidatieResultaat voerUit() {
        return null;
    }

    @Override
    public BerichtVerwerkingsFoutCode getFoutCode() {
        return null;
    }

}
