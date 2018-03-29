/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestDatumUtil;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.AutorisatieAlles;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;

/**
 * VerwijderPreRelatieGegevensServiceImplTest.
 */
public class VerwijderPreRelatieGegevensServiceImplTest {

    private final VerwijderPreRelatieGegevensServiceImpl verwijderPreRelatieGegevensService = new VerwijderPreRelatieGegevensServiceImpl();

    @Test
    public void testVerwijderVoorAfnemer() {
        final Berichtgegevens berichtgegevens = getBerichtgegevens(Rol.AFNEMER);

        verwijderPreRelatieGegevensService.execute(berichtgegevens);

        for (MetaRecord prerelatieRecord : berichtgegevens.getStatischePersoongegevens().getPreRelatieRecords()) {
            Assert.assertFalse(berichtgegevens.isGeautoriseerd(prerelatieRecord));
        }
    }

    @Test
    public void testNietVerwijderVoorBijhouder() {

        final Berichtgegevens berichtgegevens = getBerichtgegevens(Rol.BIJHOUDINGSORGAAN_MINISTER);

        verwijderPreRelatieGegevensService.execute(berichtgegevens);

        for (MetaRecord prerelatieRecord : berichtgegevens.getStatischePersoongegevens().getPreRelatieRecords()) {
            Assert.assertTrue(berichtgegevens.isGeautoriseerd(prerelatieRecord));
        }

    }

    private Berichtgegevens getBerichtgegevens(final Rol rol) {
        final StatischePersoongegevens statischePersoongegevens = new StatischePersoongegevens();
        final MaakBerichtParameters maakBerichtparameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(maakPersoon(), 0L);
        final MaakBerichtPersoonInformatie maakBerichtPersoonInformatie = new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON), SoortDienst.SYNCHRONISATIE_PERSOON);
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(TestAutorisaties.maak(rol, dienst), dienst);
        final Berichtgegevens
                berichtgegevens =
                new Berichtgegevens(maakBerichtparameters, persoonslijst, maakBerichtPersoonInformatie, autorisatiebundel, statischePersoongegevens);
        final Set<MetaRecord> prerelatieRecords = new HashSet<>();
        new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                prerelatieRecords.add(record);
            }
        }.visit(berichtgegevens.getPersoonslijst().getMetaObject());
        berichtgegevens.getStatischePersoongegevens().setPreRelatieRecords(prerelatieRecords);
        new AutorisatieAlles(berichtgegevens).doVisit(persoonslijst.getMetaObject());
        return berichtgegevens;
    }

    private MetaObject maakPersoon() {
        final Actie actieInhoud = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metId(1)
            .metObjectElement(Element.PERSOON)
            .metObject()
                .metObjectElement(getObjectElement(Element.PERSOON_PARTNER))
                .metId(2)
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_PARTNER_IDENTITEIT))
                    .metRecord().metId(1).metActieInhoud(actieInhoud).eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on
        return persoon;

    }
}
