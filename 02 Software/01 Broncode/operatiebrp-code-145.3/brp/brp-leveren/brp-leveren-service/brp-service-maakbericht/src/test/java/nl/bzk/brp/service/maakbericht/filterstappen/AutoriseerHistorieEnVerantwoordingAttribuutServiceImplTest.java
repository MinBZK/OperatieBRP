/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.cache.GeldigeAttributenElementenCache;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class AutoriseerHistorieEnVerantwoordingAttribuutServiceImplTest {

    @Mock
    private GeldigeAttributenElementenCache geldigeAttributenElementenCache;

    @InjectMocks
    private AutoriseerHistorieEnVerantwoordingAttribuutServiceImpl voorkomenFilterService;

    @Before
    public void voorTest() {
        Mockito.when(geldigeAttributenElementenCache.geldigVoorGroepAutorisatie(Mockito.any())).thenReturn(true);
    }

    @Test
    public void testGroepAfnemerindicatieMetAlleVlaggen() {

        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_AFNEMERINDICATIE_STANDAARD;
        groepDefinitie.indicatieFormeleHistorie = true;
        groepDefinitie.indicatieMaterieleHistorie = true;
        groepDefinitie.indicatieVerantwoording = true;

        final Berichtgegevens berichtgegevens = maakBerichtgegevens(groepDefinitie);
        voorkomenFilterService.execute(berichtgegevens);

        final GroepElement groepElement = getGroepElement(groepDefinitie.element.getId());
        final MetaGroep metaGroepMetAlleVlaggen = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefGroepenVanElement(groepElement).iterator().next();
        Assert.assertFalse(metaGroepMetAlleVlaggen.getRecords().isEmpty());
        for (final MetaRecord metaRecord : metaGroepMetAlleVlaggen.getRecords()) {

            //formeel
            Assert.assertTrue(isTijdstipRegistratieGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertTrue(isTijdstipVervalGeautoriseerd(metaRecord, berichtgegevens));

        }
    }

    @Test
    public void testGroepMetAlleVlaggen() {

        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_ADRES_STANDAARD;
        groepDefinitie.indicatieFormeleHistorie = true;
        groepDefinitie.indicatieMaterieleHistorie = true;
        groepDefinitie.indicatieVerantwoording = true;

        final Berichtgegevens berichtgegevens = maakBerichtgegevens(groepDefinitie);
        voorkomenFilterService.execute(berichtgegevens);

        final GroepElement groepElement = getGroepElement(groepDefinitie.element.getId());
        final MetaGroep metaGroepMetAlleVlaggen = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefGroepenVanElement(groepElement).iterator().next();
        Assert.assertFalse(metaGroepMetAlleVlaggen.getRecords().isEmpty());
        for (final MetaRecord metaRecord : metaGroepMetAlleVlaggen.getRecords()) {

            //formeel
            Assert.assertTrue(isTijdstipRegistratieGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertTrue(isTijdstipVervalGeautoriseerd(metaRecord, berichtgegevens));

            //materieel
            Assert.assertFalse(isDatumAanvangGeldigheidGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertTrue(isDatumEindeGeldigheidGeautoriseerd(metaRecord, berichtgegevens));

            //verantwoording
            Assert.assertTrue(isActieInhoudGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertTrue(isActieVervalGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertTrue(isActieAanpassingGeldingheidGeautoriseerd(metaRecord, berichtgegevens));
        }
    }

    @Test
    public void testGroepMetAlleVlaggenVerzoekparameterVerantwoordingGeen() {

        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_ADRES_STANDAARD;
        groepDefinitie.indicatieFormeleHistorie = true;
        groepDefinitie.indicatieMaterieleHistorie = true;
        groepDefinitie.indicatieVerantwoording = true;

        final Berichtgegevens berichtgegevens = maakBerichtgegevens(groepDefinitie);
        berichtgegevens.getParameters().setVerantwoordingLeveren(false);
        voorkomenFilterService.execute(berichtgegevens);

        final GroepElement groepElement = getGroepElement(groepDefinitie.element.getId());
        final MetaGroep metaGroepMetAlleVlaggen = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefGroepenVanElement(groepElement).iterator().next();
        Assert.assertFalse(metaGroepMetAlleVlaggen.getRecords().isEmpty());
        for (final MetaRecord metaRecord : metaGroepMetAlleVlaggen.getRecords()) {

            //formeel
            Assert.assertTrue(isTijdstipRegistratieGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertTrue(isTijdstipVervalGeautoriseerd(metaRecord, berichtgegevens));

            //materieel
            Assert.assertFalse(isDatumAanvangGeldigheidGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertTrue(isDatumEindeGeldigheidGeautoriseerd(metaRecord, berichtgegevens));

            //verantwoording
            Assert.assertFalse(isActieInhoudGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertFalse(isActieVervalGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertFalse(isActieAanpassingGeldingheidGeautoriseerd(metaRecord, berichtgegevens));
        }
    }


    @Test
    public void testGroepMetFormeleVlag() {

        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_ADRES_STANDAARD;
        groepDefinitie.indicatieFormeleHistorie = true;
        groepDefinitie.indicatieMaterieleHistorie = false;
        groepDefinitie.indicatieVerantwoording = false;

        final Berichtgegevens berichtgegevens = maakBerichtgegevens(groepDefinitie);
        voorkomenFilterService.execute(berichtgegevens);

        final GroepElement groepElement = getGroepElement(groepDefinitie.element.getId());
        final MetaGroep metaGroepMetAlleVlaggen = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefGroepenVanElement(groepElement).iterator().next();
        Assert.assertFalse(metaGroepMetAlleVlaggen.getRecords().isEmpty());
        for (final MetaRecord metaRecord : metaGroepMetAlleVlaggen.getRecords()) {

            //formeel
            Assert.assertTrue(isTijdstipRegistratieGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertTrue(isTijdstipVervalGeautoriseerd(metaRecord, berichtgegevens));

            //materieel
            Assert.assertFalse(isDatumAanvangGeldigheidGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertFalse(isDatumEindeGeldigheidGeautoriseerd(metaRecord, berichtgegevens));

            //verantwoording
            Assert.assertFalse(isActieInhoudGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertFalse(isActieVervalGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertFalse(isActieAanpassingGeldingheidGeautoriseerd(metaRecord, berichtgegevens));
        }
    }

    @Test
    public void testGroepMetFormeleVlagMaarAlleGroepElementNietVerstrekken() {
        Mockito.when(geldigeAttributenElementenCache.geldigVoorGroepAutorisatie(Mockito.any())).thenReturn(false);

        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_ADRES_STANDAARD;
        groepDefinitie.indicatieFormeleHistorie = true;
        groepDefinitie.indicatieMaterieleHistorie = false;
        groepDefinitie.indicatieVerantwoording = false;

        final Berichtgegevens berichtgegevens = maakBerichtgegevens(groepDefinitie);
        voorkomenFilterService.execute(berichtgegevens);

        final GroepElement groepElement = getGroepElement(groepDefinitie.element.getId());
        final MetaGroep metaGroepMetAlleVlaggen = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefGroepenVanElement(groepElement).iterator().next();
        Assert.assertFalse(metaGroepMetAlleVlaggen.getRecords().isEmpty());
        for (final MetaRecord metaRecord : metaGroepMetAlleVlaggen.getRecords()) {

            //formeel
            Assert.assertFalse(isTijdstipRegistratieGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertFalse(isTijdstipVervalGeautoriseerd(metaRecord, berichtgegevens));
        }
    }


    @Test
    public void testGroepAfnemerindicatieMetFormeleVlag() {

        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_AFNEMERINDICATIE_STANDAARD;
        groepDefinitie.indicatieFormeleHistorie = true;
        groepDefinitie.indicatieMaterieleHistorie = false;
        groepDefinitie.indicatieVerantwoording = false;

        final Berichtgegevens berichtgegevens = maakBerichtgegevens(groepDefinitie);
        voorkomenFilterService.execute(berichtgegevens);

        final GroepElement groepElement = getGroepElement(groepDefinitie.element.getId());
        final MetaGroep metaGroepMetAlleVlaggen = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefGroepenVanElement(groepElement).iterator().next();
        Assert.assertFalse(metaGroepMetAlleVlaggen.getRecords().isEmpty());
        for (final MetaRecord metaRecord : metaGroepMetAlleVlaggen.getRecords()) {

            //formeel
            Assert.assertTrue(isTijdstipRegistratieGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertTrue(isTijdstipVervalGeautoriseerd(metaRecord, berichtgegevens));
        }
    }


    @Test
    public void testGroepMetMaterieleVlag() {

        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_ADRES_STANDAARD;
        groepDefinitie.indicatieFormeleHistorie = false;
        groepDefinitie.indicatieMaterieleHistorie = true;
        groepDefinitie.indicatieVerantwoording = false;

        final Berichtgegevens berichtgegevens = maakBerichtgegevens(groepDefinitie);
        voorkomenFilterService.execute(berichtgegevens);

        final GroepElement groepElement = getGroepElement(groepDefinitie.element.getId());
        final MetaGroep metaGroepMetAlleVlaggen = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefGroepenVanElement(groepElement).iterator().next();
        Assert.assertFalse(metaGroepMetAlleVlaggen.getRecords().isEmpty());
        for (final MetaRecord metaRecord : metaGroepMetAlleVlaggen.getRecords()) {

            //formeel
            Assert.assertFalse(isTijdstipRegistratieGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertFalse(isTijdstipVervalGeautoriseerd(metaRecord, berichtgegevens));

            //materieel
            Assert.assertFalse(isDatumAanvangGeldigheidGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertTrue(isDatumEindeGeldigheidGeautoriseerd(metaRecord, berichtgegevens));

            //verantwoording
            Assert.assertFalse(isActieInhoudGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertFalse(isActieVervalGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertFalse(isActieAanpassingGeldingheidGeautoriseerd(metaRecord, berichtgegevens));
        }
    }

    @Test
    public void testGroepMetVerantwoordingVlag() {
        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_ADRES_STANDAARD;
        groepDefinitie.indicatieFormeleHistorie = false;
        groepDefinitie.indicatieMaterieleHistorie = false;
        groepDefinitie.indicatieVerantwoording = true;

        final Berichtgegevens berichtgegevens = maakBerichtgegevens(groepDefinitie);
        voorkomenFilterService.execute(berichtgegevens);

        final GroepElement groepElement = getGroepElement(groepDefinitie.element.getId());
        final MetaGroep metaGroepMetAlleVlaggen = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefGroepenVanElement(groepElement).iterator().next();
        Assert.assertFalse(metaGroepMetAlleVlaggen.getRecords().isEmpty());
        for (final MetaRecord metaRecord : metaGroepMetAlleVlaggen.getRecords()) {

            //formeel
            Assert.assertFalse(isTijdstipRegistratieGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertFalse(isTijdstipVervalGeautoriseerd(metaRecord, berichtgegevens));

            //materieel
            Assert.assertFalse(isDatumAanvangGeldigheidGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertFalse(isDatumEindeGeldigheidGeautoriseerd(metaRecord, berichtgegevens));

            //verantwoording
            Assert.assertTrue(isActieInhoudGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertTrue(isActieVervalGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertTrue(isActieAanpassingGeldingheidGeautoriseerd(metaRecord, berichtgegevens));
        }
    }

    @Test
    public void testGroepZonderVlaggen() {

        final TestAutorisaties.GroepDefinitie groepDefinitie = new TestAutorisaties.GroepDefinitie();
        groepDefinitie.element = Element.PERSOON_VOORNAAM_STANDAARD;

        final Berichtgegevens berichtgegevens = maakBerichtgegevens(groepDefinitie);
        voorkomenFilterService.execute(berichtgegevens);

        final GroepElement groepElement = getGroepElement(groepDefinitie.element.getId());
        final MetaGroep metaGroepZonderVlaggen = berichtgegevens.getPersoonslijst()
                .getModelIndex().geefGroepenVanElement(groepElement).iterator().next();
        Assert.assertFalse(metaGroepZonderVlaggen.getRecords().isEmpty());
        for (final MetaRecord metaRecord : metaGroepZonderVlaggen.getRecords()) {

            //formeel
            Assert.assertFalse(isTijdstipRegistratieGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertFalse(isTijdstipVervalGeautoriseerd(metaRecord, berichtgegevens));

            //materieel
            Assert.assertFalse(isDatumAanvangGeldigheidGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertFalse(isDatumEindeGeldigheidGeautoriseerd(metaRecord, berichtgegevens));

            //verantwoording
            Assert.assertFalse(isActieInhoudGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertFalse(isActieVervalGeautoriseerd(metaRecord, berichtgegevens));
            Assert.assertFalse(isActieAanpassingGeldingheidGeautoriseerd(metaRecord, berichtgegevens));
        }
    }

    private Berichtgegevens maakBerichtgegevens(TestAutorisaties.GroepDefinitie groepDefinitie) {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        final Dienst dienst = TestAutorisaties.maakDienst(leveringsautorisatie, null, SoortDienst.ATTENDERING, groepDefinitie);
        final MetaObject object = maakDummyPersoonObject();
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(null, dienst);
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));

        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, new Persoonslijst(object, 0L),
                new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT), autorisatiebundel, new StatischePersoongegevens());
        berichtgegevens.setKandidaatRecords(Sets.newHashSet());

        object.accept(new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaRecord record) {
                berichtgegevens.getKandidaatRecords().add(record);
            }
        });
        return berichtgegevens;
    }


    public MetaObject maakDummyPersoonObject() {
        //@formatter:off
        final Integer vandaag = DatumUtil.vandaag();
        return TestBuilders.maakLeegPersoon()
            .metObject()
                .metObjectElement(getObjectElement(Element.PERSOON_ADRES.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(TestVerantwoording.maakActie(1))
                        .metActieVerval(TestVerantwoording.maakActie(2))
                        .metActieAanpassingGeldigheid(TestVerantwoording.maakActie(2))
                        .metDatumAanvangGeldigheid(vandaag)
                        .metDatumEindeGeldigheid(vandaag)
                    .eindeRecord()
                    .metRecord()
                        .metId(2)
                        .metActieInhoud(TestVerantwoording.maakActie(1))
                        .metActieVerval(TestVerantwoording.maakActie(2))
                        .metActieAanpassingGeldigheid(TestVerantwoording.maakActie(2))
                        .metDatumAanvangGeldigheid(vandaag)
                        .metDatumEindeGeldigheid(vandaag)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.PERSOON_VOORNAAM.getId())
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_VOORNAAM_STANDAARD.getId()))
                    .metRecord()
                        .metId(3)
                        .metActieInhoud(TestVerantwoording.maakActie(1))
                        .metActieVerval(TestVerantwoording.maakActie(2))
                        .metActieAanpassingGeldigheid(TestVerantwoording.maakActie(2))
                        .metDatumAanvangGeldigheid(vandaag)
                        .metDatumEindeGeldigheid(vandaag)
                    .eindeRecord()
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(TestVerantwoording.maakActie(1))
                        .metActieVerval(TestVerantwoording.maakActie(2))
                        .metActieAanpassingGeldigheid(TestVerantwoording.maakActie(2))
                        .metDatumAanvangGeldigheid(vandaag)
                        .metDatumEindeGeldigheid(vandaag)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
             .metObject()
                .metObjectElement(Element.PERSOON_AFNEMERINDICATIE.getId())
                .metId(1)
                .metGroep()
                    .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_IDENTITEIT.getId())
                        .metRecord()
                            .metId(2)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE.getId(), 1)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE.getId(), "000003")
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PERSOON.getId(), 223)
                        .eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId())
                        .metRecord()
                            .metId(2)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DATUMAANVANGMATERIELEPERIODE.getId(), 10102001)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTINHOUD.getId(), 3)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId(), DatumUtil.nuAlsZonedDateTime())
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTVERVAL.getId(), 3)
                            .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPVERVAL.getId(), DatumUtil.nuAlsZonedDateTime())
                        .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on
    }


    private boolean isTijdstipRegistratieGeautoriseerd(final MetaRecord record, final Berichtgegevens berichtgegevens) {
        return isGeautoriseerd(AttribuutElement.DATUM_TIJD_REGISTRATIE, record, berichtgegevens);
    }

    private boolean isTijdstipVervalGeautoriseerd(final MetaRecord record, final Berichtgegevens berichtgegevens) {
        return isGeautoriseerd(AttribuutElement.DATUM_TIJD_VERVAL, record, berichtgegevens);
    }

    private boolean isDatumAanvangGeldigheidGeautoriseerd(final MetaRecord record, final Berichtgegevens berichtgegevens) {
        return isGeautoriseerd(AttribuutElement.DATUM_AANVANG_GELDIGHEID, record, berichtgegevens);
    }

    private boolean isDatumEindeGeldigheidGeautoriseerd(final MetaRecord record, final Berichtgegevens berichtgegevens) {
        return isGeautoriseerd(AttribuutElement.DATUM_EINDE_GELDIGHEID, record, berichtgegevens);
    }

    private boolean isActieAanpassingGeldingheidGeautoriseerd(final MetaRecord record, final Berichtgegevens berichtgegevens) {
        return isGeautoriseerd(AttribuutElement.BRP_ACTIE_AANPASSING_GELDIGHEID, record, berichtgegevens);
    }

    private boolean isActieVervalGeautoriseerd(final MetaRecord record, final Berichtgegevens berichtgegevens) {
        return isGeautoriseerd(AttribuutElement.BRP_ACTIE_VERVAL, record, berichtgegevens);
    }

    private boolean isActieInhoudGeautoriseerd(final MetaRecord record, final Berichtgegevens berichtgegevens) {
        return isGeautoriseerd(AttribuutElement.BRP_ACTIE_INHOUD, record, berichtgegevens);
    }

    private boolean isGeautoriseerd(final String elementNaam, final MetaRecord metaRecord, final Berichtgegevens berichtgegevens) {
        final MetaAttribuut metaAttribuut = metaRecord
                .getAttribuut((metaRecord.getParentGroep().getGroepElement().getAttribuutMetElementNaam(elementNaam)));
        return metaAttribuut != null && berichtgegevens.isGeautoriseerd(metaAttribuut);
    }
}
