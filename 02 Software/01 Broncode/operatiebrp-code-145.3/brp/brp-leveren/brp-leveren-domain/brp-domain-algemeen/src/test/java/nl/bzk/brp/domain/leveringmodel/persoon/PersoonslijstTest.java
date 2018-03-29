/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.helper.TestDatumUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class PersoonslijstTest {

    private final Actie actieGisteren = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
    private final Actie actieNu = TestVerantwoording.maakActie(2, DatumUtil.nuAlsZonedDateTime());
    private final AdministratieveHandeling admhnd = TestVerantwoording.maakAdministratieveHandeling(1);

    @Test
    public void test() {
        final Persoonslijst persoonslijst = maakPersoon();

        Assert.assertEquals(999L, persoonslijst.getId().longValue());
        Assert.assertNotNull(persoonslijst.getModelIndex());
        Assert.assertNotNull(persoonslijst.getOnderzoekIndex());
        Assert.assertTrue(persoonslijst.getVerstrekkingsbeperkingen().isEmpty());
        Assert.assertTrue(persoonslijst.getGeldendeAfnemerindicaties().isEmpty());
        Assert.assertEquals(2, persoonslijst.getAdministratieveHandelingen().size());
        Assert.assertFalse(persoonslijst.heeftIndicatieVolledigeVerstrekkingsbeperking());
        Assert.assertNull(persoonslijst.getDatumAanvangMaterielePeriodeVanAfnemerindicatie(2));
        Assert.assertNull(persoonslijst.getActueleRecord(null).orElse(null));
        Assert.assertNotNull(persoonslijst.getNuNuBeeld());
        Assert.assertFalse(persoonslijst.isActueel(null));

    }

    /**
     * Dienst verantwoording kan enkel gevuld zijn op records in groepen met dienstverantwoording.
     */
    @Test(expected = IllegalStateException.class)
    public void testDienstInhoudNietOpGroepMetActieVerantwoording() {
        final MetaObject metaObject = TestBuilders.maakLeegPersoon()
                .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                .metRecord()
                //.metDienstInhoud(new DienstVerantwoording(1, new DatumTijdAttribuut().getWaarde()))
                .eindeRecord()
                .eindeGroep()
                .build();
        new Persoonslijst(metaObject, 0L);
    }

    @Test
    public void testBepalingIndicatieVerstrekkingsbeperking() {
        Persoonslijst persoonslijstMetIndicatie = maakPersoonMetIndicatieVerstrekkingsbeperking(true, true);
        Assert.assertTrue(persoonslijstMetIndicatie.heeftIndicatieVolledigeVerstrekkingsbeperking());

        Persoonslijst persoonslijstMetIndicatieGeenActueelRecord = maakPersoonMetIndicatieVerstrekkingsbeperking(true, false);
        Assert.assertFalse(persoonslijstMetIndicatieGeenActueelRecord.heeftIndicatieVolledigeVerstrekkingsbeperking());

        Persoonslijst persoonslijstGeenIndicatie = maakPersoonMetIndicatieVerstrekkingsbeperking(false, true);
        Assert.assertFalse(persoonslijstGeenIndicatie.heeftIndicatieVolledigeVerstrekkingsbeperking());
    }

    @Test
    public void testBepalingVerstrekkingsbeperking() {
        final Persoonslijst persoonslijstMetVerstrBep = maakPersoonMetVerstrekkingsbeperking(true);
        System.out.println(persoonslijstMetVerstrBep.toStringVolledig());
        Assert.assertEquals(1, persoonslijstMetVerstrBep.getVerstrekkingsbeperkingen().size());

        final Persoonslijst persoonslijstZonderVerstrBep = maakPersoonMetIndicatieVerstrekkingsbeperking(false, true);
        Assert.assertTrue(persoonslijstZonderVerstrBep.getVerstrekkingsbeperkingen().isEmpty());
    }

    @Test
    public void testBepaalDatumAanvangMaterielePeriodeAfnemerIndicatie() {
        //@formatter:off
        final MetaObject persoonMeta = MetaObject.maakBuilder()
                .metId(999)
                .metObjectElement(Element.PERSOON)
                .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode())
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                .metRecord()
                .metActieInhoud(actieNu)
                .eindeRecord()
                .eindeGroep()
                .metObject()
                .metObjectElement(Element.PERSOON_AFNEMERINDICATIE.getId())
                .metGroep()
                .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PERSOON.getId(), 999)
                .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE.getId(), "888")
                .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE.getId(), 111)
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId())
                .metRecord()
                .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId(), LocalDate.of(1981, 1, 1).atStartOfDay(ZoneId.of
                        (DatumUtil.UTC)))
                .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DATUMAANVANGMATERIELEPERIODE.getId(), 20140101)
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .metObject()
                .metObjectElement(Element.PERSOON_AFNEMERINDICATIE.getId())
                .metGroep()
                .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PERSOON.getId(), 999)
                .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE.getId(), "888")
                .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE.getId(), 222)
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId())
                .metRecord()
                .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId(), LocalDate.of(1981, 1, 1).atStartOfDay(ZoneId.of
                        (DatumUtil.UTC)))
                .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPVERVAL.getId(), LocalDate.of(1982, 1, 1).atStartOfDay(ZoneId.of
                        (DatumUtil.UTC)))
                .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DATUMAANVANGMATERIELEPERIODE.getId(), 20140101)
                .eindeRecord()
                .eindeGroep()

                .eindeObject().build();
        //@formatter:on

        final Persoonslijst persoonslijst = new Persoonslijst(persoonMeta, 0L);
        Assert.assertEquals(new Integer(20140101), persoonslijst.getDatumAanvangMaterielePeriodeVanAfnemerindicatie(111));
        Assert.assertNull(persoonslijst.getDatumAanvangMaterielePeriodeVanAfnemerindicatie(222));
    }

    @Test
    public void testGeefActueleWaarde() {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
                .metId(999)
                .metObjectElement(Element.PERSOON)
                .metObject()
                .metObjectElement(Element.PERSOON_NATIONALITEIT.getId())
                .metGroep()
                .metGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId())
                .metRecord()
                .metId(1)
                .metAttribuut(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE.getId(), 1)
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId())
                .metRecord()
                .metActieInhoud(actieGisteren)
                .metAttribuut(Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE.getId(), 1)
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .build();
        //@formatter:on
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);

        Assert.assertEquals(1,
                (int) persoonslijst.<Integer>getActueleAttribuutWaarde(getAttribuutElement(Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE)).orElse(-1));
    }

    @Test
    public void testMaakBeeld() {
        //@formatter:off
        final MetaObject persoonAlleRecordsVervallen = MetaObject.maakBuilder()
                .metId(999)
                .metObjectElement(Element.PERSOON)
                .metObject()
                .metObjectElement(Element.PERSOON_NATIONALITEIT.getId())
                .metGroep()
                .metGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId())
                .metRecord()
                .metId(1)
                .eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId())
                .metRecord()
                .metActieInhoud(actieGisteren)
                .metActieVerval(actieGisteren)
                .metDatumAanvangGeldigheid(20010101)
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .build();
        //@formatter:on
        final Persoonslijst persoonslijstVervallen = new Persoonslijst(persoonAlleRecordsVervallen, 0L);

        Assert.assertEquals(0,
                persoonslijstVervallen.beeldVan().formeelPeilmoment(TestDatumUtil.gisteren()).getMetaObject().getGroepen().size());
        Assert.assertEquals(0, persoonslijstVervallen.getNuNuBeeld().getMetaObject().getGroepen().size());
    }

    @Test
    public void testBepaalTijdstipLaatsteWijziging() {
        //@formatter:off
        final ZonedDateTime tijdstipLaatsteWijziging = DatumUtil.nuAlsZonedDateTime();
        final MetaObject persoon = MetaObject.maakBuilder()
                .metId(999)
                .metObjectElement(Element.PERSOON)
                .metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                .metRecord()
                .metId(1)
                .metActieInhoud(TestVerantwoording.maakActie(1, TestDatumUtil.gisteren()))
                .metAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING.getId(), tijdstipLaatsteWijziging)
                .eindeRecord()
                .eindeGroep()
                .build();
        //@formatter:on
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);

        assertThat(persoonslijst.bepaalTijdstipLaatsteWijziging(), is(tijdstipLaatsteWijziging));
    }

    @Test
    public void testBepaalTijdstipLaatsteWijzigingNietActueelRecord() {
        //@formatter:off
        final ZonedDateTime tijdstipLaatsteWijziging = DatumUtil.nuAlsZonedDateTime();
        final MetaObject persoon = MetaObject.maakBuilder()
                .metId(999)
                .metObjectElement(Element.PERSOON)
                .metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                .metRecord()
                .metId(1)
                .metActieInhoud(TestVerantwoording.maakActie(1, TestDatumUtil.morgen()))
                .metActieVerval(TestVerantwoording.maakActie(2, TestDatumUtil.gisteren()))
                .metAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING.getId(), tijdstipLaatsteWijziging)
                .eindeRecord()
                .eindeGroep()
                .build();
        //@formatter:on
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);

        assertNull(persoonslijst.bepaalTijdstipLaatsteWijziging());
    }

    @Test
    public void testBepaalTijdstipLaatsteWijzigingGBASystemtiek() {
        //@formatter:off
        final ZonedDateTime tijdstipLaatsteWijziging = DatumUtil.nuAlsZonedDateTime();
        final MetaObject persoon = MetaObject.maakBuilder()
                .metId(999)
                .metObjectElement(Element.PERSOON)
                .metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                .metRecord()
                .metId(1)
                .metActieInhoud(TestVerantwoording.maakActie(1, TestDatumUtil.gisteren()))
                .metAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK.getId(), tijdstipLaatsteWijziging)
                .eindeRecord()
                .eindeGroep()
                .build();
        //@formatter:on
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);

        assertThat(persoonslijst.bepaalTijdstipLaatsteWijzigingGBASystematiek(), is(tijdstipLaatsteWijziging));
    }

    @Test
    public void testBepaalTijdstipLaatsteWijzigingGBASystemtiekNietActueelRecord() {
        //@formatter:off
        final ZonedDateTime tijdstipLaatsteWijziging = DatumUtil.nuAlsZonedDateTime();
        final MetaObject persoon = MetaObject.maakBuilder()
                .metId(999)
                .metObjectElement(Element.PERSOON)
                .metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                .metRecord()
                .metId(1)
                .metActieInhoud(TestVerantwoording.maakActie(1, DatumUtil.nuAlsZonedDateTime()))
                .metActieVerval(TestVerantwoording.maakActie(2, DatumUtil.nuAlsZonedDateTime().minusYears(1)))
                .metAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK.getId(), tijdstipLaatsteWijziging)
                .eindeRecord()
                .eindeGroep()
                .build();
        //@formatter:on
        final Persoonslijst persoonslijst = new Persoonslijst(persoon, 0L);

        assertNull(persoonslijst.bepaalTijdstipLaatsteWijzigingGBASystematiek());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaakBeeldOpHandelingBestaatNiet() {
        final Persoonslijst persoonslijst = TestBuilders.maakPersoonMetHandelingen(1);
        persoonslijst.beeldVan().administratievehandeling(455);
    }

    private Persoonslijst maakPersoon() {
        //@formatter:off
        final MetaObject persoonMeta = MetaObject.maakBuilder()
                .metId(999)
                .metObjectElement(Element.PERSOON)

                .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord().metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode()).eindeRecord()
                .eindeGroep()

                .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metId(20)
                .metGroep()
                .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                .metRecord()
                .metId(15)
                .metActieInhoud(actieNu)
                .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 2)
                .eindeRecord()
                .metRecord()
                .metId(25)
                .metActieInhoud(actieGisteren)
                .metActieVerval(actieGisteren)
                .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), 20)
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .metObject()
                .metObjectElement(Element.PERSOON_NATIONALITEIT.getId())
                .metGroep()
                .metGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId())
                .metRecord().metId(1).eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId())
                .metRecord()
                .metActieInhoud(actieGisteren)
                .metDatumAanvangGeldigheid(20010101)
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .build();
        //@formatter:on

        return new Persoonslijst(persoonMeta, 0L);
    }

    private Persoonslijst maakPersoonMetVerstrekkingsbeperking(final boolean actueel) {
        //@formatter:off
        final MetaObject persoonMeta = MetaObject.maakBuilder()
                .metId(999)
                .metObjectElement(Element.PERSOON)

                .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord().metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode()).eindeRecord()
                .eindeGroep()

                .metObject()
                .metObjectElement(Element.PERSOON_VERSTREKKINGSBEPERKING.getId())
                .metGroep()
                .metGroepElement(Element.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT.getId())
                .metRecord()
                .metActieInhoud(actieGisteren)
                .metActieVerval(actueel ? null : actieNu)
                .metAttribuut(Element.PERSOON_VERSTREKKINGSBEPERKING_PARTIJCODE.getId(), 1)
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .build();
        //@formatter:on

        return new Persoonslijst(persoonMeta, 0L);
    }

    private Persoonslijst maakPersoonMetIndicatieVerstrekkingsbeperking(final boolean heeftIndicatie, final boolean actueel) {

        //@formatter:off
        final MetaObject persoonMeta = MetaObject.maakBuilder()
                .metId(999)
                .metObjectElement(Element.PERSOON)

                .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord().metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode()).eindeRecord()
                .eindeGroep()

                .metObject()
                .metObjectElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING.getId())
                .metGroep()
                .metGroepElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_IDENTITEIT.getId())
                .metRecord().eindeRecord()
                .eindeGroep()
                .metGroep()
                .metGroepElement(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_STANDAARD.getId())
                .metRecord()
                .metActieInhoud(actieNu)
                .metActieVerval(actueel ? null : actieNu)
                .metAttribuut(Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_WAARDE.getId(), heeftIndicatie)
                .eindeRecord()
                .eindeGroep()
                .eindeObject()

                .eindeObject()
                .build();
        //@formatter:on

        return new Persoonslijst(persoonMeta, 0L);
    }
}
