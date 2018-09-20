/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import java.util.Calendar;
import java.util.Collection;

import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;


public class TestBerichtY implements BerichtVerzoek<BerichtAntwoord> {

    @Override
    public SoortBericht getSoortBericht() {
        return null;
    }

    @Override
    public Calendar getBeschouwing() {
        return null;
    }

    @Override
    public Collection<String> getReadBsnLocks() {
        return null;
    }

    @Override
    public Collection<String> getWriteBsnLocks() {
        return null;
    }

    @Override
    public Class<BerichtAntwoord> getAntwoordClass() {
        return null;
    }

}
