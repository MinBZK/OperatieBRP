/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.sql.Timestamp;
import java.util.ArrayList;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Dienst;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.DienstHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

public class BrpDienstMapperTest {

    private final BrpDienstMapper subject = new BrpDienstMapper();

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    @Ignore("BMR44")
    @Test
    public void test() {
        final Dienst entiteit = Mockito.mock(Dienst.class);
        final DienstHistorie historie = new DienstHistorie(entiteit, new Timestamp(System.currentTimeMillis()), 19900101);
        historie.setDatumEinde(19920101);

        final BrpDienstInhoud inhoud = subject.mapInhoud(historie, brpOnderzoekMapper);
        Assert.assertNotNull(inhoud);
        Assert.assertEquals(new BrpDatum(19900101, null), inhoud.getDatumIngang());
        Assert.assertEquals(new BrpDatum(19920101, null), inhoud.getDatumEinde());
    }
}
