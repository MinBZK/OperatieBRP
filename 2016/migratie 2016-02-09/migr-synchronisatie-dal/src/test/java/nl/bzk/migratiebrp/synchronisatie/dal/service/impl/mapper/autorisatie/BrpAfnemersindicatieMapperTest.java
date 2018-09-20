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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.PersoonAfnemerindicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class BrpAfnemersindicatieMapperTest extends AbstractEntityNaarBrpMapperTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    private final BrpAfnemersindicatieMapper subject = new BrpAfnemersindicatieMapper();

    @Test
    @Ignore("BMR44")
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
//        final Partij testPartij = new Partij("naam", 0);
//        final Abonnement testAbonnement = new Abonnement("test");
//        final ToegangAbonnement toegangAbonnement = new ToegangAbonnement(testPartij, testAbonnement);
//        final Afleverwijze afleverwijze = new Afleverwijze(toegangAbonnement, Kanaal.BRP);
//        testAbonnement.setToegangAbonnementen(Collections.singleton(toegangAbonnement));
//        toegangAbonnement.setAfleverwijzeSet(Collections.singleton(afleverwijze));
//        final PersoonAfnemerindicatie entiteit = new PersoonAfnemerindicatie(testPartij, Mockito.mock(Persoon.class), testAbonnement);
//
//        final PersoonAfnemerindicatieHistorie historie =
//                new PersoonAfnemerindicatieHistorie(entiteit, new Dienst(testAbonnement, CatalogusOptie.OPTIE_1));
//        historie.setDatumAanvangMaterielePeriode(19000101);
//        historie.setDatumEindeVolgen(19900102);
//        vulFormeleHistorie(historie);
//
//        entiteit.getPersoonAfnemerindicatieHistorieSet().add(historie);
//
//        return entiteit;

        //TODO BRM44
        return null;
    }

}
