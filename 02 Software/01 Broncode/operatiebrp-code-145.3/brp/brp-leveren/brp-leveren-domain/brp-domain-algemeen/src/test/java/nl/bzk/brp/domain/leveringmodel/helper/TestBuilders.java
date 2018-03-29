/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.helper;

import com.google.common.collect.Iterables;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereAanduidingVerval;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Ignore;

/**
 * Utility voor tests
 */
@Ignore
public class TestBuilders {

    public static final MetaObject LEEG_PERSOON = maakLeegPersoon().build();
    public static final Persoonslijst PERSOON_MET_HANDELINGEN = maakPersoonMetHandelingen(1);
    public static final Persoonslijst PERSOON_MET_MATERIELE_EN_FORMELE_HISTORIE = maakPersoonFormeleEnMaterieleHistorie(1);
    public static final Persoonslijst PERSOON_MET_MATERIELE_EN_FORMELE_MUTLEV_HISTORIE = maakPersoonFormeleEnMaterieleMutlevHistorie(1);

    public static MetaObject.Builder maakLeegPersoon() {
        //@formatter:off
        final MetaObject.Builder persoon = MetaObject.maakBuilder().metId(1)
            .metObjectElement(Element.PERSOON.getId())
                        .metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                    .metRecord().metActieInhoud(TestVerantwoording.maakActie(1)).eindeRecord()
                .eindeGroep();
        //@formatter:on
        return persoon;
    }

    public static Persoonslijst maakPersoonMetIdentificatieNrs(final String bsn, final String anr) {
        //@formatter:off
        MetaObject persoon = TestBuilders.maakLeegPersoon()
                .metGroep()
                    .metGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS)
                        .metRecord()
                            .metId(1)
                            .metAttribuut(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId(), anr)
                            .metAttribuut(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(), bsn)
                        .eindeRecord()
                .eindeGroep()
                .eindeObject().build();
        //@formatter:on
        return new Persoonslijst(persoon, 1L);
    }

    public static MetaObject.Builder maakLeegPersoon(final int id) {
        return maakLeegPersoon().metId(id);
    }

    public static MetaObject.Builder maakIngeschrevenPersoon() {
        //@formatter:off
        final MetaObject.Builder persoon = MetaObject.maakBuilder().metId(1)
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord()
                    .metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode())
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                    .metRecord().metActieInhoud(TestVerantwoording.maakActie(1)).eindeRecord()
                .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_VERSIE.getId())
                    .metRecord().metAttribuut(Element.PERSOON_VERSIE_LOCK.getId(), 123L).eindeRecord()
                .eindeGroep()
            .metObjectElement(Element.PERSOON.getId());
        //@formatter:on
        return persoon;
    }

    public static MetaObject.Builder maakPseudoPersoon() {
        //@formatter:off
        final MetaObject.Builder persoon = MetaObject.maakBuilder()
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord()
                    .metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.PSEUDO_PERSOON.getCode())
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                    .metRecord().metActieInhoud(TestVerantwoording.maakActie(1)).eindeRecord()
                .eindeGroep()
            .metObjectElement(Element.PERSOON);
        //@formatter:on
        return persoon;
    }

    public static MetaObject.Builder maakAfnemerindicatie(final Integer leveringsAutorisatieId, final String afnemerCode) {
        final ZonedDateTime zonedDateTime = ZonedDateTime.of(1998, 3, 4, 5, 30, 25, 0, DatumUtil.BRP_ZONE_ID);
        //@formatter:off
        final MetaObject.Builder builder = MetaObject.maakBuilder()
            .metId(1L)
            .metObjectElement(Element.PERSOON_AFNEMERINDICATIE)
            .metGroep()
                .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_IDENTITEIT.getId())
                .metRecord()
                    .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE.getId(), afnemerCode)
                    .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE.getId(), leveringsAutorisatieId)
                    .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PERSOON.getId(), 1L)
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId())
                .metRecord()
                    .metId(2L)
                    .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTINHOUD.getId(), 1)
                    .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId(), zonedDateTime)
                .eindeRecord()
            .eindeGroep()
        ;
        //@formatter:on
        return builder;
    }

    public static MetaObject.Builder maakVerlopenAfnemerIndicaties(final int leveringsAutorisatieId, final String afnemerCode) {
        //@formatter:off
        final MetaObject.Builder builder = MetaObject.maakBuilder()
            .metId(1L)
            .metObjectElement(Element.PERSOON_AFNEMERINDICATIE)
            .metGroep()
                .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_IDENTITEIT.getId())
                .metRecord()
                    .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PARTIJCODE.getId(), afnemerCode)
                    .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE.getId(), leveringsAutorisatieId)
                    .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_PERSOON.getId(), 1L)
                .eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_AFNEMERINDICATIE_STANDAARD.getId())
                .metRecord()
                    .metId(2L)
                    .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DIENSTINHOUD.getId(), 1)
                    .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE.getId(), TestDatumUtil.gisteren())
                    .metAttribuut(Element.PERSOON_AFNEMERINDICATIE_DATUMEINDEVOLGEN.getId(), 19001010)
                .eindeRecord()
            .eindeGroep()
        ;
        //@formatter:on
        return builder;
    }


    public static Persoonslijst maakPersoonMetHandelingen(int persId) {


        final ZonedDateTime time = DatumUtil.nuAlsZonedDateTime();
        final AdministratieveHandeling ah1 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(1, "000123", time, SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());

        final AdministratieveHandeling ah2 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(2, "000123", time.minusYears(1), SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(2, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());

        final AdministratieveHandeling ah3 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(3, "000123", time.minusYears(2), SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(3, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());


        //@formatter:off
        final MetaObject persoonMeta = MetaObject.maakBuilder()
            .metId(persId)
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord().metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode()).eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId())
                .metRecord()
                    .metId(1)
                    .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                    .metAttribuut(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(), "111")
                .eindeRecord()
                .metRecord()
                    .metId(2)
                    .metActieInhoud(Iterables.getOnlyElement(ah2.getActies()))
                    .metActieVerval(Iterables.getOnlyElement(ah1.getActies()))
                    .metAttribuut(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(), "222")
                .eindeRecord()
                .metRecord()
                    .metId(3)
                    .metActieInhoud(Iterables.getOnlyElement(ah3.getActies()))
                    .metActieVerval(Iterables.getOnlyElement(ah2.getActies()))
                    .metAttribuut(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(), "333")
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        return new Persoonslijst(persoonMeta, 0L);
    }

    public static Persoonslijst maakPersoonMetHandelingenEnAfnemerindicatie(int persId) {


        final ZonedDateTime nu = DatumUtil.nuAlsZonedDateTime();
        final AdministratieveHandeling ah1 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(1, "000123", nu, SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.BEEINDIGING_VOORNAAM, nu, "000123", 0)).build());

        //@formatter:off
        final MetaObject persoonMeta = MetaObject.maakBuilder()
            .metId(persId)
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord().metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode()).eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId())
                .metRecord()
                    .metId(1)
                    .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                    .metAttribuut(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId(), "111")
                .eindeRecord()
            .eindeGroep()
            .metObject(TestBuilders.maakAfnemerindicatie(1, "000001"))
        .build();
        //@formatter:on

        return new Persoonslijst(persoonMeta, 0L);
    }

    public static Persoonslijst maakBasisPersoon(final long persId, final Actie actie) {
        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON).eindeObject()
            .metId(persId)
            .metGroep()
                .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                .metRecord()
                    .metActieInhoud(actie)
                .eindeRecord()
            .eindeGroep()
        .build();
        //@formatter:on

        return new Persoonslijst(persoon, 0L);
    }

    public static Persoonslijst maakPersoonFormeleEnMaterieleMutlevHistorie(int persId) {


        final ZonedDateTime time = DatumUtil.nuAlsZonedDateTime();
        final AdministratieveHandeling ah1 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(3, "000123", time, SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());

        final AdministratieveHandeling ah2 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(2, "000123", time.minusYears(1), SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(2, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());

        final AdministratieveHandeling ah3 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(1, "000123", time.minusYears(2), SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(3, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());


        //@formatter:off
        final MetaObject persoonMeta = MetaObject.maakBuilder()
            .metId(persId)
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord().metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode()).eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                .metRecord()
                    .metId(1)
                    .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                    .metAttribuut(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId(), 111)
                .eindeRecord()
                .metRecord()
                    .metId(2)
                    .metActieInhoud(Iterables.getOnlyElement(ah2.getActies()))
                    .metActieVerval(Iterables.getOnlyElement(ah2.getActies()))
                    .metActieVervalTbvLeveringMutaties(Iterables.getOnlyElement(ah1.getActies()))
                    .metAttribuut(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId(), 222)
                .eindeRecord()
                .metRecord()
                    .metId(3)
                    .metActieInhoud(Iterables.getOnlyElement(ah3.getActies()))
                    .metActieVerval(Iterables.getOnlyElement(ah3.getActies()))
                    .metActieVervalTbvLeveringMutaties(Iterables.getOnlyElement(ah2.getActies()))
                    .metAttribuut(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId(), 333)
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId())
                    .metRecord()
                        .metId(0)
                    .eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metId(5)
                        .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah1.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(), "Soest")
                    .eindeRecord()
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(Iterables.getOnlyElement(ah2.getActies()))
                        .metActieAanpassingGeldigheid(Iterables.getOnlyElement(ah1.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah2.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metDatumEindeGeldigheid(DatumUtil.vanDatumNaarInteger(ah1.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(), "Rijswijk")
                    .eindeRecord()
                    .metRecord()
                        .metId(3)
                        .metActieInhoud(Iterables.getOnlyElement(ah2.getActies()))
                        .metActieVerval(Iterables.getOnlyElement(ah2.getActies()))
                        .metActieVervalTbvLeveringMutaties(Iterables.getOnlyElement(ah1.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah2.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(), "Rijswijk")
                    .eindeRecord()
                    .metRecord()
                        .metId(2)
                        .metActieInhoud(Iterables.getOnlyElement(ah3.getActies()))
                        .metActieAanpassingGeldigheid(Iterables.getOnlyElement(ah2.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah3.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metDatumEindeGeldigheid(DatumUtil.vanDatumNaarInteger(ah2.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(), "Purmerend")
                    .eindeRecord()
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(Iterables.getOnlyElement(ah3.getActies()))
                        .metActieVerval(Iterables.getOnlyElement(ah3.getActies()))
                        .metActieVervalTbvLeveringMutaties(Iterables.getOnlyElement(ah2.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah3.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(), "Purmerend")
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on

        return new Persoonslijst(persoonMeta, 0L);
    }

    public static Persoonslijst maakPersoonFormeleEnMaterieleHistorie(int persId) {


        final ZonedDateTime time = DatumUtil.nuAlsZonedDateTime();
        final AdministratieveHandeling ah1 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(3, "000123", time, SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());

        final AdministratieveHandeling ah2 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(2, "000123", time.minusYears(1), SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(2, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());

        final AdministratieveHandeling ah3 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(1, "000123", time.minusYears(2), SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(3, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());


        //@formatter:off
        final MetaObject persoonMeta = MetaObject.maakBuilder()
            .metId(persId)
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord().metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode()).eindeRecord()
            .eindeGroep()
            .metGroep()
                .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                .metRecord()
                    .metId(1)
                    .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                    .metAttribuut(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId(), 111)
                .eindeRecord()
                .metRecord()
                    .metId(2)
                    .metActieInhoud(Iterables.getOnlyElement(ah2.getActies()))
                    .metActieVerval(Iterables.getOnlyElement(ah1.getActies()))
                    .metAttribuut(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId(), 222)
                .eindeRecord()
                .metRecord()
                    .metId(3)
                    .metActieInhoud(Iterables.getOnlyElement(ah3.getActies()))
                    .metActieVerval(Iterables.getOnlyElement(ah2.getActies()))
                    .metAttribuut(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId(), 333)
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId())
                    .metRecord()
                        .metId(0)
                    .eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metId(5)
                        .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah1.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(), "Soest")
                    .eindeRecord()
                    .metRecord()
                        .metId(4)
                        .metActieInhoud(Iterables.getOnlyElement(ah2.getActies()))
                        .metActieAanpassingGeldigheid(Iterables.getOnlyElement(ah1.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah2.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metDatumEindeGeldigheid(DatumUtil.vanDatumNaarInteger(ah1.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(), "Rijswijk")
                    .eindeRecord()
                    .metRecord()
                        .metId(3)
                        .metActieInhoud(Iterables.getOnlyElement(ah2.getActies()))
                        .metActieVerval(Iterables.getOnlyElement(ah1.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah2.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(), "Rijswijk")
                    .eindeRecord()
                    .metRecord()
                        .metId(2)
                        .metActieAanpassingGeldigheid(Iterables.getOnlyElement(ah2.getActies()))
                        .metActieInhoud(Iterables.getOnlyElement(ah3.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah3.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metDatumEindeGeldigheid(DatumUtil.vanDatumNaarInteger(ah2.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(), "Purmerend")
                    .eindeRecord()
                    .metRecord()
                        .metId(1)
                        .metActieVerval(Iterables.getOnlyElement(ah2.getActies()))
                        .metActieInhoud(Iterables.getOnlyElement(ah3.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah3.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId(), "Purmerend")
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on

        return new Persoonslijst(persoonMeta, 0L);
    }


    public static Persoonslijst maakPersoonMet2Nationaliteiten(int persId) {


        final ZonedDateTime time = DatumUtil.nuAlsZonedDateTime();
        final AdministratieveHandeling ah1 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(1, "000123", time.minusYears(2), SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());

        final AdministratieveHandeling ah2 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(2, "000123", time.minusYears(1), SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(2, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());

        final AdministratieveHandeling ah3 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(3, "000123", time.minusYears(0), SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(3, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());


        //@formatter:off
        final MetaObject persoonMeta = MetaObject.maakBuilder()
            .metId(persId)
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(Element.PERSOON_IDENTITEIT.getId())
                .metRecord().metAttribuut(Element.PERSOON_SOORTCODE.getId(), SoortPersoon.INGESCHREVENE.getCode()).eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(Element.PERSOON_NATIONALITEIT.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId())
                    .metRecord()
                        .metId(0)
                    .eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId())
                    .metRecord()
                        .metId(2)
                        .metActieAanpassingGeldigheid(Iterables.getOnlyElement(ah2.getActies()))
                        .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah3.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metDatumEindeGeldigheid(DatumUtil.vanDatumNaarInteger(ah2.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE.getId(), 31)
                        .metAttribuut(Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE.getId(), 20)
                        .metAttribuut(Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE.getId(), 60)
                    .eindeRecord()
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                        .metActieVerval(Iterables.getOnlyElement(ah2.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah3.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE.getId(), 31)
                        .metAttribuut(Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE.getId(), 20)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
            .metObject()
                .metObjectElement(Element.PERSOON_NATIONALITEIT.getId())
                .metId(2)
                .metGroep()
                    .metGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId())
                    .metRecord()
                        .metId(0)
                    .eindeRecord()
                .eindeGroep()
                .metGroep()
                    .metGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId())
                    .metRecord()
                        .metId(4)
                        .metActieAanpassingGeldigheid(Iterables.getOnlyElement(ah2.getActies()))
                        .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah3.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metDatumEindeGeldigheid(DatumUtil.vanDatumNaarInteger(ah2.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE.getId(), 31)
                        .metAttribuut(Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE.getId(), 20)
                        .metAttribuut(Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE.getId(), 60)
                    .eindeRecord()
                    .metRecord()
                        .metId(3)
                        .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                        .metActieVerval(Iterables.getOnlyElement(ah2.getActies()))
                        .metDatumAanvangGeldigheid(DatumUtil.vanDatumNaarInteger(ah3.getTijdstipRegistratie().minusDays(10).toLocalDate()))
                        .metAttribuut(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE.getId(), 32)
                        .metAttribuut(Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE.getId(), 20)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on

        return new Persoonslijst(persoonMeta, 0L);
    }

    public static Persoonslijst maakPersoonMetNadereAanduidingVerval(int persId, NadereAanduidingVerval nadereAanduidingVerval) {


        final ZonedDateTime time = DatumUtil.nuAlsZonedDateTime();
        final AdministratieveHandeling ah1 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(1, "000123", time.minusYears(2), SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(1, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());

        final AdministratieveHandeling ah2 = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(2, "000123", time.minusYears(1), SoortAdministratieveHandeling.AANVANG_ONDERZOEK)
                .metObject(TestVerantwoording.maakActieBuilder(2, SoortActie.BEEINDIGING_VOORNAAM, time, "000123", 0)).build());

        //@formatter:off
        final MetaObject persoonMeta = MetaObject.maakBuilder()
            .metId(persId)
            .metObjectElement(Element.PERSOON)
            .metGroep()
                .metGroepElement(Element.PERSOON_GEBOORTE.getId())
                .metRecord()
                    .metId(2)
                    .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                    .metAttribuut(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId(), 12)
                .eindeRecord()
                .metRecord()
                    .metId(1)
                    .metActieInhoud(Iterables.getOnlyElement(ah1.getActies()))
                    .metActieVerval(Iterables.getOnlyElement(ah2.getActies()))
                    .metNadereAanduidingVerval(nadereAanduidingVerval.getCode())
                    .metAttribuut(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId(), 14)
                .eindeRecord()
            .eindeGroep()
        .eindeObject()
        .build();
        //@formatter:on

        return new Persoonslijst(persoonMeta, 0L);
    }

}
