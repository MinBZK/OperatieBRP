/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument.BRBY0037;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument.BRBY0040;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument.BRBY0042;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument.BRBY0044;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument.BRBY0045;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument.BRBY1026;
import nl.bzk.brp.business.regels.VoorActieRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.springframework.stereotype.Component;

/**
 * Implementatie van ActieRegelManager.
 */
@Component
public class RegistratieReisdocumentActieRegelManager extends AbstractActieRegelManager {

    @Override
    public List<Class<? extends VoorActieRegel>> getVoorActieRegels(final SoortAdministratieveHandeling soortAdministratieveHandeling) {

        final List<Class<? extends VoorActieRegel>> regels = new ArrayList<>();

        regels.addAll(ALGEMENE_VOOR_ACTIE_REGELS);

        regels.addAll(Arrays.asList(BRBY1026.class, BRBY0037.class, BRBY0045.class));

        switch (soortAdministratieveHandeling) {
            case VERKRIJGING_REISDOCUMENT:
                regels.addAll(Arrays.asList(BRBY0040.class, BRBY0042.class));
                break;
            case ONTTREKKING_REISDOCUMENT:
                regels.addAll(Arrays.asList(BRBY0044.class));
                break;
            default:
                break;
        }

        return regels;
    }
}
