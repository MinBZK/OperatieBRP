/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.robuustheid;

import java.io.IOException;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

/**
 * LoadTestUtils.
 */
public class LoadTestUtils {


    public static String[] maakBerichten(final int aantalBerichtFormaten) throws IOException {
        String[] bodies = new String[aantalBerichtFormaten];
        final String body = IOUtils.toString(new ClassPathResource("robuustheid/body.txt").getInputStream());
        for (int i = 0; i < aantalBerichtFormaten; i++) {
            bodies[i] = body;
            for (int j = 0; j < i; j++) {
                bodies[i] = bodies[i] + body;
            }
        }
        return bodies;

    }

    public static ToegangLeveringsAutorisatie maakToegangLeveringsautorisatie(String partijCode) {
        //final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final PartijRol geautoriseerde = new PartijRol(new Partij("Partij" + partijCode, partijCode), Rol.AFNEMER);
        return new ToegangLeveringsAutorisatie(geautoriseerde, leveringsautorisatie);

    }
}
