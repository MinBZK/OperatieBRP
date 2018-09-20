/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.uitsluitingkiesrecht.BRBY0131;
import nl.bzk.brp.business.regels.VoorActieRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.springframework.stereotype.Component;

/**
 * Implementatie van ActieRegelManager.
 */
@Component
public class RegistratieUitsluitingKiesrechtActieRegelManager extends AbstractActieRegelManager {

    @Override
    public List<Class<? extends VoorActieRegel>> getVoorActieRegels(final SoortAdministratieveHandeling soortAdministratieveHandeling) {

        final List<Class<? extends VoorActieRegel>> regels = new ArrayList<>();

        regels.addAll(ALGEMENE_VOOR_ACTIE_REGELS);

        regels.addAll(Arrays.asList(BRBY0131.class));

        return regels;
    }
}
