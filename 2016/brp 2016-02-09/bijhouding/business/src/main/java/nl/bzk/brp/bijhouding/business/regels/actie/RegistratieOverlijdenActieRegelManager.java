/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.overlijden.acties.registratieoverlijden.BRBY0907;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.overlijden.acties.registratieoverlijden.BRBY0908;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.overlijden.acties.registratieoverlijden.BRBY0909;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.overlijden.acties.registratieoverlijden.BRBY0913;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.groep.BRAL9003;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.materielehistorie.BRBY0011;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.materielehistorie.BRBY0012;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0208;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0209;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0210;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0216;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0218;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden.BRBY0902;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden.BRBY0903;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden.BRBY0904;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden.BRBY0906;
import nl.bzk.brp.business.regels.VoorActieRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.springframework.stereotype.Component;

/**
 * Implementatie van ActieRegelManager.
 */
@Component
public class RegistratieOverlijdenActieRegelManager extends AbstractActieRegelManager {

    @Override
    public List<Class<? extends VoorActieRegel>> getVoorActieRegels(final SoortAdministratieveHandeling soortAdministratieveHandeling) {

        final List<Class<? extends VoorActieRegel>> regels = new ArrayList<>();

        regels.addAll(ALGEMENE_VOOR_ACTIE_REGELS);

        regels.addAll(Arrays.asList(BRAL0208.class,
            BRAL0209.class,
            BRAL0210.class,
            BRAL0216.class,
            BRAL0218.class,
            BRBY0913.class,
            BRBY0011.class,
            BRBY0012.class,
            BRBY0902.class,
            BRBY0903.class,
            BRBY0904.class,
            BRBY0907.class,
            BRBY0908.class,
            BRBY0909.class,
            BRBY0913.class,
            BRAL9003.class));

        switch (soortAdministratieveHandeling) {
            case OVERLIJDEN_IN_BUITENLAND:
                regels.add(BRBY0906.class);
                break;
            default:
                break;
        }

        return regels;
    }
}
