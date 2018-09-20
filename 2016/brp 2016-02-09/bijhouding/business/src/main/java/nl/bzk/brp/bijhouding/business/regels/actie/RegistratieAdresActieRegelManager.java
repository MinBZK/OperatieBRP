/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.migratie.acties.adres.BRBY0502;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.groep.BRAL9003;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres.BRBY05111;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres.groep3.BRBY0175;
import nl.bzk.brp.business.regels.VoorActieRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.springframework.stereotype.Component;

/**
 * Implementatie van ActieRegelManager.
 */
@Component
public class RegistratieAdresActieRegelManager extends AbstractActieRegelManager {

    @Override
    public List<Class<? extends VoorActieRegel>> getVoorActieRegels(final SoortAdministratieveHandeling soortAdministratieveHandeling) {

        final List<Class<? extends VoorActieRegel>> regels = new ArrayList<>();
        regels.addAll(ALGEMENE_VOOR_ACTIE_REGELS);

        regels.add(BRBY05111.class);

        switch (soortAdministratieveHandeling) {
            case VERHUIZING_BINNENGEMEENTELIJK:
            case VERHUIZING_INTERGEMEENTELIJK:
                regels.addAll(Arrays.asList(BRBY0175.class, BRBY0502.class, BRAL9003.class));
                break;
            default:
                break;
        }
        return regels;
    }
}
