/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;


import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;

import com.google.common.collect.Iterables;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VerwijderMigratieGroepServiceImplTest {

    private VerwijderMigratieGroepServiceImpl service = new VerwijderMigratieGroepServiceImpl();


    private ZonedDateTime tijdstipRegistratie;

    @Before
    public void voorTest() {
        tijdstipRegistratie = DatumUtil.nuAlsZonedDateTime();
        BrpNu.set(tijdstipRegistratie);
    }

    @Test
    public void testEmigratie() {

        final GroepElement migratieGroepElement = ElementHelper.getGroepElement(Element.PERSOON_MIGRATIE);
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
            .metGroep()
                .metGroepElement(getGroepElement(migratieGroepElement.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(TestVerantwoording.maakActie(1, tijdstipRegistratie))
                    .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_SOORTCODE), SoortMigratie.EMIGRATIE.getCode())
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        maakBerichtParameters.setMigratieGroepEnkelOpnemenBijEmigratie(true);

        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), null, new StatischePersoongegevens());

        final MetaGroep migratieGroep = Iterables.getOnlyElement(persoonslijst.getModelIndex()
                .geefGroepenVanElement(migratieGroepElement));
        berichtgegevens.autoriseer(migratieGroep);

        service.execute(berichtgegevens);
        Assert.assertTrue(berichtgegevens.isGeautoriseerd(migratieGroep));

        maakBerichtParameters.setMigratieGroepEnkelOpnemenBijEmigratie(false);
        service.execute(berichtgegevens);
        Assert.assertTrue(berichtgegevens.isGeautoriseerd(migratieGroep));
    }

    @Test
    public void testImmigratie() {

        final GroepElement migratieGroepElement = ElementHelper.getGroepElement(Element.PERSOON_MIGRATIE);
        //@formatter:off
        final MetaObject persoon = TestBuilders.maakLeegPersoon()
            .metGroep()
                .metGroepElement(getGroepElement(migratieGroepElement.getId()))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(TestVerantwoording.maakActie(1, tijdstipRegistratie))
                    .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_SOORTCODE), SoortMigratie.IMMIGRATIE.getCode())
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);
        maakBerichtParameters.setMigratieGroepEnkelOpnemenBijEmigratie(true);

        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, persoonslijst,
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), null, new StatischePersoongegevens());

        final MetaGroep migratieGroep = Iterables.getOnlyElement(persoonslijst.getModelIndex()
                .geefGroepenVanElement(migratieGroepElement));

        maakBerichtParameters.setMigratieGroepEnkelOpnemenBijEmigratie(false);
        berichtgegevens.autoriseer(migratieGroep);
        service.execute(berichtgegevens);
        Assert.assertTrue(berichtgegevens.isGeautoriseerd(migratieGroep));

        maakBerichtParameters.setMigratieGroepEnkelOpnemenBijEmigratie(true);
        berichtgegevens.autoriseer(migratieGroep);
        service.execute(berichtgegevens);
        Assert.assertFalse(berichtgegevens.isGeautoriseerd(migratieGroep));
    }
}
