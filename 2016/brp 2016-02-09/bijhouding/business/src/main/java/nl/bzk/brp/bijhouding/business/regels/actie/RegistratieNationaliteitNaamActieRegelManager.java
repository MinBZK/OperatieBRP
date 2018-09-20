/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit.BRBY0151;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit.BRBY0153;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit.BRBY0157;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit.BRBY0158;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit.BRBY0162;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit.BRBY0141;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit.BRBY0163;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit.BRBY0164;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit.BRBY0173;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit.BRBY0176;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit.BRBY0178;
import nl.bzk.brp.business.regels.VoorActieRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.springframework.stereotype.Component;

/**
 * Implementatie van ActieRegelManager.
 */
@Component
public class RegistratieNationaliteitNaamActieRegelManager extends AbstractActieRegelManager {

    @Override
    public List<Class<? extends VoorActieRegel>> getVoorActieRegels(final SoortAdministratieveHandeling soortAdministratieveHandeling) {

        final List<Class<? extends VoorActieRegel>> regels = new ArrayList<>();

        regels.addAll(ALGEMENE_VOOR_ACTIE_REGELS);

        regels.addAll(Arrays.asList(BRBY0141.class, BRBY0151.class, BRBY0153.class, BRBY0157.class,
            BRBY0158.class, BRBY0162.class, BRBY0173.class, BRBY0176.class, BRBY0178.class));

        switch (soortAdministratieveHandeling) {
            case VERKRIJGING_NEDERLANDSE_NATIONALITEIT:
                regels.add(BRBY0163.class);
                break;
            case VERKRIJGING_VREEMDE_NATIONALITEIT:
                regels.add(BRBY0164.class);
                break;
            default:
                break;
        }
        return regels;
    }
}
