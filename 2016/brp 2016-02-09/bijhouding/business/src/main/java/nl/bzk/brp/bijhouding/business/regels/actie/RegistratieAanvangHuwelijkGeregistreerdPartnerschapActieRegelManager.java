/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap.BRBY0401;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap.BRBY0402;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap.BRBY0403;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap.BRBY0409;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap.BRBY0417;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap.BRBY0454;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratiehuwelijkpartnerschap.BRBY0437;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identiteit.BRAL0205;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0208;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0209;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0210;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0216;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0218;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.relatie.BRAL0202;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.relatie.BRAL2111;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRAL2104;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRAL2110;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRBY0429;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRBY0430;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRBY0438;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BRBY0442;
import nl.bzk.brp.business.regels.VoorActieRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.springframework.stereotype.Component;

/**
 * Implementatie van ActieRegelManager.
 */
@Component
public class RegistratieAanvangHuwelijkGeregistreerdPartnerschapActieRegelManager extends AbstractActieRegelManager {

    @Override
    public List<Class<? extends VoorActieRegel>> getVoorActieRegels(final SoortAdministratieveHandeling soortAdministratieveHandeling) {

        final List<Class<? extends VoorActieRegel>> regels = new ArrayList<>();

        regels.addAll(ALGEMENE_VOOR_ACTIE_REGELS);

        regels.addAll(Arrays.asList(BRAL0202.class, BRAL0205.class, BRAL0208.class, BRAL0209.class, BRAL0210.class, BRAL0216.class, BRAL0218.class,
            BRAL2104.class, BRAL2110.class, BRAL2111.class, BRBY0401.class, BRBY0402.class, BRBY0403.class, BRBY0409.class,
            BRBY0417.class, BRBY0429.class, BRBY0430.class, BRBY0437.class, BRBY0438.class, BRBY0442.class, BRBY0454.class));

        return regels;
    }
}
