/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.dto.antwoord;

import java.util.List;
import java.util.Collection;

import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.domein.kern.Persoon;

public class BijhoudingAntwoord implements BerichtAntwoord {

    @Override
    public List<BerichtVerwerkingsFout> getFouten() {
        return null;
    }

    @Override
    public void voegFoutToe(final BerichtVerwerkingsFout fout) {

    }

    @Override
    public Collection<Persoon> getPersonen() {
        return null;
    }

    @Override
    public Long getLeveringId() {
        return null;
    }

    @Override
    public void setLeveringId(final Long leveringId) {

    }

    @Override
    public void wisContent() {

    }
}
