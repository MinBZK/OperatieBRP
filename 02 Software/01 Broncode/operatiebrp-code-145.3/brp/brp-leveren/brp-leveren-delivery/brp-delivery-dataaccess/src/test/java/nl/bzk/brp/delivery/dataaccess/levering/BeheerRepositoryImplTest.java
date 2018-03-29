/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import java.sql.Timestamp;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortVrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBerichtVrijBericht;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.brp.service.dalapi.BeheerRepository;
import org.junit.Test;
import org.springframework.util.Assert;

@Data(resources = {
        "classpath:/data/testdata-beh.xml"})
public class BeheerRepositoryImplTest extends AbstractDataAccessTest {

    @Inject
    private BeheerRepository beheerRepository;

    @Test
    public void ophalenSoortVrijBericht() throws Exception {
        final SoortVrijBericht soortVrijBericht = beheerRepository.haalSoortVrijBerichtOp("dummy");
        Assert.notNull(soortVrijBericht);

        final SoortVrijBericht soortVrijBerichtNietBestaand = beheerRepository.haalSoortVrijBerichtOp("nietbestaand");
        Assert.isNull(soortVrijBerichtNietBestaand);
    }

    @Test
    public void opslaanNieuwVrijBericht() throws Exception {
        final SoortVrijBericht soortVrijBericht = beheerRepository.haalSoortVrijBerichtOp("dummy");

        final VrijBericht vrijBericht = new VrijBericht(
                SoortBerichtVrijBericht.VERWERK_VRIJ_BERICHT,
                soortVrijBericht,
                new Timestamp(System.currentTimeMillis()),
                "data",
                false);
        beheerRepository.opslaanNieuwVrijBericht(vrijBericht);
    }

}
