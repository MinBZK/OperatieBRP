/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap.BRBY0403;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieeindehuwelijkpartnerschap.BRBY0443;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieeindehuwelijkpartnerschap.BRBY0445;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieeindehuwelijkpartnerschap.BRBY0455;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratiehuwelijkpartnerschap.BRBY0437;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0208;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0209;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0210;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0216;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0218;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRAL2104;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRAL2112;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRAL2113;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRBY0446;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRBY0451;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRBY0452;
import nl.bzk.brp.business.regels.VoorActieRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.springframework.stereotype.Component;

/**
 * Implementatie van ActieRegelManager.
 */
@Component
public class RegistratieEindeHuwelijkGeregistreerdPartnerschapActieRegelManager extends AbstractActieRegelManager {

    @Override
    public List<Class<? extends VoorActieRegel>> getVoorActieRegels(final SoortAdministratieveHandeling soortAdministratieveHandeling) {

        final List<Class<? extends VoorActieRegel>> regels = new ArrayList<>();

        regels.addAll(ALGEMENE_VOOR_ACTIE_REGELS);

        regels.addAll(Arrays.asList(BRAL0208.class, BRAL0209.class, BRAL0210.class, BRAL0216.class, BRAL0218.class, BRAL2104.class,
            BRAL2113.class, BRAL2112.class, BRBY0437.class, BRBY0443.class, BRBY0455.class, BRBY0451.class, BRBY0452.class,
            BRBY0446.class, BRBY0445.class));

        switch (soortAdministratieveHandeling) {
            case OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK:
                regels.add(BRBY0403.class);
                break;
            default:
                break;
        }

        return regels;
    }
}
