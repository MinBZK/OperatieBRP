/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AutoriseerAttributenObvAboPartijServiceImplTest {

    private static final String RECHTSGROND_OMSCHRIJVING = "rechtsgrondomschrijving";

    private final AutoriseerAttributenObvAboPartijServiceImpl regel = new AutoriseerAttributenObvAboPartijServiceImpl();

    @Test
    public void autoriseerAboHandelingen() {
        test(RECHTSGROND_OMSCHRIJVING, true);
    }

    @Test
    public void geenAutorisatie_GeenRechtsgrondomschrijvingInActiebronnen() {
        test(null, false);
    }

    private void test(final String rechtsgrondOmschrijving, final boolean isGeautoriseerd) {

        final Berichtgegevens berichtgegevens = maakLeverPersoon(rechtsgrondOmschrijving);
        berichtgegevens.getPersoonslijst().getMetaObject().accept(new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                berichtgegevens.autoriseer(record);
            }
        });
        regel.execute(berichtgegevens);

        final MetaGroep groep = berichtgegevens.getPersoonslijst().getModelIndex()
                .geefGroepenVanElement(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId())).iterator().next();
        Assert.assertEquals(2, groep.getRecords().size());
        for (final MetaRecord metaRecord : groep.getRecords()) {
            final GroepElement groepElement = metaRecord.getParentGroep().getGroepElement();
            Assert.assertEquals(isGeautoriseerd, berichtgegevens.isGeautoriseerd(metaRecord.getAttribuut(groepElement.getActieInhoudAttribuut())));
            Assert.assertEquals(isGeautoriseerd, berichtgegevens.isGeautoriseerd(metaRecord.getAttribuut(groepElement.getActieVervalAttribuut())));
            Assert.assertEquals(isGeautoriseerd,
                    berichtgegevens.isGeautoriseerd(metaRecord.getAttribuut(groepElement.getActieAanpassingGeldigheidAttribuut())));

        }
    }

    private Berichtgegevens maakLeverPersoon(final String rechtsgrondOmschrijving) {
        final Actie actieInhoud = maakActie(1, rechtsgrondOmschrijving);
        final Actie actieVerval = maakActie(2, rechtsgrondOmschrijving);
        final Actie actieaanpassingGeldigheid = maakActie(3, rechtsgrondOmschrijving);

        //@formatter:off
        final MetaObject persoonMetaObject = MetaObject.maakBuilder()
            .metId(1)
            .metObjectElement(getObjectElement(Element.PERSOON.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieInhoud)
                        .metActieVerval(actieVerval)
                        .metActieAanpassingGeldigheid(actieaanpassingGeldigheid)
                    .eindeRecord()
                    .metRecord()
                        .metId(2)
                        .metActieInhoud(actieInhoud)
                        .metActieVerval(actieVerval)
                        .metActieAanpassingGeldigheid(actieaanpassingGeldigheid)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on

        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(persoonMetaObject, 0L);
        return new Berichtgegevens(maakBerichtParameters, persoonslijst, new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT),
                null, new StatischePersoongegevens());
    }

    private Actie maakActie(int id, String rechtsgrondOmschrijving) {
        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTime();
        final MetaObject ah = TestVerantwoording
                .maakAdministratieveHandeling(id, "000001", nu, SoortAdministratieveHandeling.CORRECTIE_ADRES)
                .metObject(TestVerantwoording.maakActieBuilder(id, SoortActie.BEEINDIGING_VOORNAAM, nu, "000001", 0)
                        .metObject(TestVerantwoording.maakActiebronBuilder(id, "01", rechtsgrondOmschrijving))
                ).build();
        final AdministratieveHandeling h1 = AdministratieveHandeling.converter().converteer(ah);
        return h1.getActies().iterator().next();
    }

}
