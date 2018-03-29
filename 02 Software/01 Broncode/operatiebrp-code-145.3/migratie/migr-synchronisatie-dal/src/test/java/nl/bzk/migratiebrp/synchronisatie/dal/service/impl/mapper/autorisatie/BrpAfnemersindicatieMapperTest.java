/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.ArrayList;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;
import nl.bzk.algemeenbrp.test.dal.TimestampUtil;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class BrpAfnemersindicatieMapperTest extends AbstractEntityNaarBrpMapperTestBasis {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    private final BrpAfnemersindicatieMapper subject = new BrpAfnemersindicatieMapper();

    @Test
    public void test() {
        final PersoonAfnemerindicatie input = maak();
        final BrpStapel<BrpAfnemersindicatieInhoud> stapel = subject.map(input.getPersoonAfnemerindicatieHistorieSet(), brpOnderzoekMapper);
        Assert.assertNotNull(stapel);
        Assert.assertEquals(1, stapel.size());
        final BrpAfnemersindicatieInhoud inhoud = stapel.get(0).getInhoud();
        Assert.assertNotNull(inhoud);
        Assert.assertEquals(new BrpDatum(19000101, null), inhoud.getDatumAanvangMaterielePeriode());
        Assert.assertEquals(new BrpDatum(19900102, null), inhoud.getDatumEindeVolgen());
    }

    public static PersoonAfnemerindicatie maak() {
        final Partij testPartij = new Partij("naam", "000000");
        final Leveringsautorisatie testLeveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, false);
        final PersoonAfnemerindicatie entiteit = new PersoonAfnemerindicatie(Mockito.mock(Persoon.class), testPartij, testLeveringsautorisatie);

        final PersoonAfnemerindicatieHistorie historie = new PersoonAfnemerindicatieHistorie(entiteit);
        historie.setDatumAanvangMaterielePeriode(19000101);
        historie.setDatumEindeVolgen(19900102);
        historie.setDatumTijdRegistratie(TimestampUtil.maakTimestamp(1990, 0, 2, 0, 0, 0));
        entiteit.getPersoonAfnemerindicatieHistorieSet().add(historie);

        return entiteit;
    }

}
