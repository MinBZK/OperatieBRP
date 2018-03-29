/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import com.google.common.base.Predicates;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class KandidaatRecordBepalingTest {

    @InjectMocks
    private KandidaatRecordBepaling kandidaatRecordBepaling;

    @Mock
    private MetaRecordFilterFactory metaRecordFilterFactory;

    @Test
    public void test() {

        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final MaakBerichtPersoonInformatie maakBerichtPersoon = new MaakBerichtPersoonInformatie(
                SoortSynchronisatie.VOLLEDIG_BERICHT);
        maakBerichtPersoon.setDatumAanvangmaterielePeriode(1);
        final Berichtgegevens
                berichtgegevens =
                new Berichtgegevens(maakBerichtParameters, new Persoonslijst(maakPersoon(), 0L), maakBerichtPersoon, null, new StatischePersoongegevens());

        Mockito.when(metaRecordFilterFactory.maakRecordfilters(Mockito.any()))
                .thenReturn(Predicates.alwaysFalse());
        kandidaatRecordBepaling.execute(berichtgegevens);
        Assert.assertTrue(berichtgegevens.getKandidaatRecords().isEmpty());

        Mockito.when(metaRecordFilterFactory.maakRecordfilters(Mockito.any()))
                .thenReturn(Predicates.alwaysTrue());
        kandidaatRecordBepaling.execute(berichtgegevens);
        Assert.assertFalse(berichtgegevens.getKandidaatRecords().isEmpty());
        Assert.assertTrue(berichtgegevens.getKandidaatRecords().size() == 3);

    }

    private MetaObject maakPersoon() {

        //@formatter:off
        final MetaObject metaObject = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metId(1)
            .metGroep()
            .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                .metRecord().metId(1).metActieInhoud(TestVerantwoording.maakActie(1)).eindeRecord()
                .metRecord().metId(2).metActieInhoud(TestVerantwoording.maakActie(2)).eindeRecord()
                .metRecord().metId(3).metActieInhoud(TestVerantwoording.maakActie(3)).eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on
        return metaObject;
    }

}
