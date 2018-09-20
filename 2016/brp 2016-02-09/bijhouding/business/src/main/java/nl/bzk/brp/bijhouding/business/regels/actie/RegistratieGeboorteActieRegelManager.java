/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0105M;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0106;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0107;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0126;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0129;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0134;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0187;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRBY0033;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRBY0036;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRBY0103;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRBY0168;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRBY0169;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRBY0170;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRBY0177;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRPUC00112;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte.BRPUC00120;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geboorte.BRBY01032;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geboorte.BRBY01037;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geboorte.BRBY0166;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identiteit.BRAL0205;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0208;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0209;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0210;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0216;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie.BRAL0218;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam.BRAL0505;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.staatloos.BRAL0219;
import nl.bzk.brp.business.regels.NaActieRegel;
import nl.bzk.brp.business.regels.VoorActieRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.springframework.stereotype.Component;

/**
 * Implementatie van ActieRegelManager.
 */
@Component
public class RegistratieGeboorteActieRegelManager extends AbstractActieRegelManager {

    @Override
    public List<Class<? extends VoorActieRegel>> getVoorActieRegels(final SoortAdministratieveHandeling soortAdministratieveHandeling) {

        final List<Class<? extends VoorActieRegel>> regels = new ArrayList<>();

        regels.addAll(ALGEMENE_VOOR_ACTIE_REGELS);

        regels.addAll(Arrays.asList(BRAL0208.class,
            BRAL0209.class, BRAL0210.class, BRAL0216.class, BRAL0218.class, BRAL0205.class, BRBY0169.class, BRPUC00112.class,
            BRPUC00120.class, BRBY0134.class, BRBY0033.class, BRBY0187.class, BRBY0036.class, BRBY0129.class, BRBY01032.class,
            BRBY01037.class, BRBY0166.class, BRBY0177.class));

        switch (soortAdministratieveHandeling) {
            case GEBOORTE_IN_NEDERLAND:
            case GEBOORTE_IN_NEDERLAND_MET_ERKENNING:
                regels.addAll(Arrays.asList(BRBY0168.class, BRBY0170.class, BRBY0103.class));
                break;
            default:
                break;
        }
        return regels;
    }

    @Override
    public List<Class<? extends NaActieRegel>> getNaActieRegels(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        final List<Class<? extends NaActieRegel>> regels = new ArrayList<>();

        regels.addAll(ALGEMENE_NA_ACTIE_REGELS);

        switch (soortAdministratieveHandeling) {
            case GEBOORTE_IN_NEDERLAND:
            case GEBOORTE_IN_NEDERLAND_MET_ERKENNING:
                regels.addAll(Arrays.asList(
                    BRBY0126.class, BRAL0505.class, BRBY0105M.class, BRBY0106.class, BRBY0107.class, BRAL0219.class));
                break;
            case TOEVOEGING_GEBOORTEAKTE:
            case VERBETERING_GEBOORTEAKTE:
                regels.add(BRAL0505.class);
                break;
            default:
                break;
        }
        return regels;
    }
}
