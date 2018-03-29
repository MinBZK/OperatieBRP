/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.bericht;

import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import org.junit.Test;

/**
 */
public class BerichtgegevensTest extends BerichtTestUtil {
    @Test
    public void test() throws Exception {

        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        leveringsautorisatie.setDienstbundelSet(Collections.singleton(dienstbundel));
        leveringsautorisatie.setId(4);
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.ATTENDERING);
        dienst.setId(1);

        //@formatter:off
        final VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(
            BasisBerichtGegevens.builder()
                .metParameters()
                    .metDienst(dienst)
                    .metSoortSynchronisatie(SoortSynchronisatie.MUTATIE_BERICHT)
                .eindeParameters()
            .build(), null, null);
        //@formatter:on

        final String output = geefOutput(writer -> BerichtWriterTemplate.write(writer, bericht.getBasisBerichtGegevens().getParameters()));

        assertGelijk("parameters.xml", output);
    }
}
