/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.levering.lo3.conversie.AbstractConversieIntegratieTest;

public abstract class AbstractMutatieConverteerderIntegratieTest extends AbstractConversieIntegratieTest {

    protected Actie actie;

    protected AdministratieveHandeling basisAdministratieveHandeling;

    protected AtomicInteger idTeller = new AtomicInteger(10000);

    public AbstractMutatieConverteerderIntegratieTest() {
        maakBasisAdministratieveHandeling();
    }

    protected void maakBasisAdministratieveHandeling() {
        final MetaObject ah = TestVerantwoording
                .maakAdministratieveHandeling(-21L, "000034", ZonedDateTime.of(1920, 1, 3, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID),
                        SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL)
                .metObject(TestVerantwoording
                        .maakActieBuilder(-21L, SoortActie.BEEINDIGING_VOORNAAM, ZonedDateTime.of(1920, 1, 2, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID), "000001", null)
                ).build();
        basisAdministratieveHandeling = AdministratieveHandeling.converter().converteer(ah);
        actie = basisAdministratieveHandeling.getActies().iterator().next();
    }

    AdministratieveHandeling getBijhoudingsAdministratieveHandeling() {
        return getBijhoudingsAdministratieveHandeling(1940, 1, 2);
    }

    AdministratieveHandeling getBijhoudingsAdministratieveHandeling(final int jaar, final int maand, final int dag) {
        final ZonedDateTime tsReg = ZonedDateTime.of(jaar, maand, dag, 0, 0, 0, 0,
                DatumUtil.BRP_ZONE_ID);
        final MetaObject ah = TestVerantwoording
                .maakAdministratieveHandeling(idTeller.getAndIncrement(), "000034", tsReg, SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL)
                .metObject(TestVerantwoording.maakActieBuilder(idTeller.getAndIncrement(), SoortActie.CONVERSIE_GBA, tsReg, "000001", 0)
                ).build();
        return AdministratieveHandeling.converter().converteer(ah);
    }


    protected MetaObject maakBasisPersoon(final int persoonId) {
        return maakBasisPersoonBuilder(persoonId).build();
    }

    MetaObject.Builder maakBasisPersoonBuilder(final int persoonId) {
        MetaObject.Builder basispersoon = MetaObject.maakBuilder();

        basispersoon.metObjectElement(ElementHelper.getObjectElement(Element.PERSOON.getId())).metId(persoonId);
        voegGeboorteToe(basispersoon, actie, 19200101, "0518", "6030");
        voegIdentificatieNummersToe(basispersoon, actie, 19200101, "1234567890", "123456789");
        voegSamengesteldeNaamToe(basispersoon, actie, 19200101, "Voornaam1 Voornaam2", "de", " ", "geslachtsnaam");
        voegAfgeleidAdministratiefToe(basispersoon);
        voegInschrijvingToe(basispersoon);
        voegIndentiteitToe(basispersoon, "I");
        voegBijhoudingToe(basispersoon);
        voegNaamgebruikToe(basispersoon);
        voegGeslachtsaanduidingToe(basispersoon, actie, 19200101, "M");
        basispersoon.eindeObject();

        MetaObject.Builder basisadres =
                basispersoon.metObject().metObjectElement(ElementHelper.getObjectElement(Element.PERSOON_ADRES.getId())).metId(34544);
        voegAdresIdentiteitToe(basisadres);
        voegAdresStandaardToe(basisadres);
        basispersoon.eindeObject();
        return basispersoon;
    }

    private void voegAdresIdentiteitToe(final MetaObject.Builder moBuilder) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .eindeRecord()
                .eindeGroep();
    }

    private void voegAdresStandaardToe(final MetaObject.Builder moBuilder) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metDatumAanvangGeldigheid(19200101)
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE.getId()), "I")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING.getId()), 19200101)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), "0518")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_GEMEENTEDEEL.getId()), "deel vd gemeente")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISLETTER.getId()), "A")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId()), 10)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING.getId()), "B")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES.getId()), false)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId()), "6030")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId()), "naam")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_POSTCODE.getId()), "2245HJ")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_REDENWIJZIGINGCODE.getId()), "P")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_SOORTCODE.getId()), "W")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId()), "Rotterdam")
                .eindeRecord()
                .eindeGroep();
    }

    private void voegGeboorteToe(
            final MetaObject.Builder moBuilder,
            final Actie geboorteActie,
            final int datum,
            final String gemeenteCode,
            final String landgebied) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(geboorteActie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM.getId()), datum)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_GEMEENTECODE.getId()), gemeenteCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()), landgebied)
                .eindeRecord()
                .eindeGroep();
    }

    void voegGerelateerdeOuderGeboorteToe(
            final MetaObject.Builder moBuilder,
            final Actie geboorteActie,
            final int datum,
            final String gemeenteCode,
            final String landgebied) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(geboorteActie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_DATUM.getId()), datum)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_GEMEENTECODE.getId()), gemeenteCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()), landgebied)
                .eindeRecord()
                .eindeGroep();
    }

    void voegGerelateerdeKindGeboorteToe(
            final MetaObject.Builder moBuilder,
            final Actie geboorteActie,
            final int datum,
            final String gemeenteCode,
            final String landgebied) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(geboorteActie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_DATUM.getId()), datum)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_GEMEENTECODE.getId()), gemeenteCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()), landgebied)
                .eindeRecord()
                .eindeGroep();
    }

    protected void voegGerelateerdeHuwelijkspartnerGeboorteToe(
            final MetaObject.Builder moBuilder,
            final Actie geboorteActie,
            final int datum,
            final String gemeenteCode,
            final String landgebied) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(geboorteActie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_DATUM.getId()), datum)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_GEMEENTECODE.getId()), gemeenteCode)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()), landgebied)
                .eindeRecord()
                .eindeGroep();
    }

    protected void voegGerelateerdeGeregistreerdePartnerGeboorteToe(
            final MetaObject.Builder moBuilder,
            final Actie geboorteActie,
            final int datum,
            final String gemeenteCode,
            final String landgebied) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(geboorteActie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_DATUM.getId()), datum)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_GEMEENTECODE.getId()),
                        gemeenteCode)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()),
                        landgebied)
                .eindeRecord()
                .eindeGroep();
    }

    void voegIdentificatieNummersToe(
            final MetaObject.Builder moBuilder,
            final Actie actieIdentificatienummers,
            final int datum,
            final String anr,
            final String bsn) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actieIdentificatienummers)
                .metDatumAanvangGeldigheid(datum)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()), anr)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()), bsn)
                .eindeRecord()
                .eindeGroep();
    }

    void voegGerelateerdeOuderIdentificatieNummersToe(
            final MetaObject.Builder moBuilder,
            final Actie actieIdentificatienummers,
            final int datum,
            final String anr,
            final String bsn) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actieIdentificatienummers)
                .metDatumAanvangGeldigheid(datum)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()), anr)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()), bsn)
                .eindeRecord()
                .eindeGroep();
    }

    void voegGerelateerdeKindIdentificatieNummersToe(
            final MetaObject.Builder moBuilder,
            final Actie actieIdentificatienummers,
            final int datum,
            final String anr,
            final String bsn) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actieIdentificatienummers)
                .metDatumAanvangGeldigheid(datum)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()), anr)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()), bsn)
                .eindeRecord()
                .eindeGroep();
    }

    protected void voegGerelateerdeHuwelijkspartnerIdentificatieNummersToe(
            final MetaObject.Builder moBuilder,
            final Actie actieIdentificatienummers,
            final int datum,
            final String anr,
            final String bsn) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actieIdentificatienummers)
                .metDatumAanvangGeldigheid(datum)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()),
                        anr)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()),
                        bsn)
                .eindeRecord()
                .eindeGroep();
    }

    protected void voegGerelateerdeGeregistreerdePartnerIdentificatieNummersToe(
            final MetaObject.Builder moBuilder,
            final Actie actieIdentificatienummers,
            final int datum,
            final String anr,
            final String bsn) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actieIdentificatienummers)
                .metDatumAanvangGeldigheid(datum)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getId()),
                        anr)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER.getId()),
                        bsn)
                .eindeRecord()
                .eindeGroep();
    }

    private void voegSamengesteldeNaamToe(
            final MetaObject.Builder moBuilder,
            final Actie actieSamengesteldeNaam,
            final int datum,
            final String voornaam,
            final String voorvoegsel,
            final String scheidingsteken,
            final String geslachtsnaamstam) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_SAMENGESTELDENAAM.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actieSamengesteldeNaam)
                .metDatumAanvangGeldigheid(datum)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()), geslachtsnaamstam)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getId()), scheidingsteken)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getId()), voornaam)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getId()), voorvoegsel)
                .eindeRecord()
                .eindeGroep();
    }

    void voegGerelateerdeOuderSamengesteldeNaamToe(
            final MetaObject.Builder moBuilder,
            final Actie actieSamengesteldeNaam,
            final int datum,
            final String voornaam,
            final String voorvoegsel,
            final String scheidingsteken,
            final String geslachtsnaamstam) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actieSamengesteldeNaam)
                .metDatumAanvangGeldigheid(datum)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()),
                        geslachtsnaamstam)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getId()),
                        scheidingsteken)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getId()), voornaam)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getId()), voorvoegsel)
                .eindeRecord()
                .eindeGroep();
    }

    void voegGerelateerdeKindSamengesteldeNaamToe(
            final MetaObject.Builder moBuilder,
            final Actie actieSamengesteldeNaam,
            final int datum,
            final String voornaam,
            final String voorvoegsel,
            final String scheidingsteken,
            final String geslachtsnaamstam) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actieSamengesteldeNaam)
                .metDatumAanvangGeldigheid(datum)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()),
                        geslachtsnaamstam)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getId()),
                        scheidingsteken)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getId()), voornaam)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getId()), voorvoegsel)
                .eindeRecord()
                .eindeGroep();
    }

    protected void voegGerelateerdeHuwelijkspartnerSamengesteldeNaamToe(
            final MetaObject.Builder moBuilder,
            final Actie actieSamengesteldeNaam,
            final int datum,
            final String voornaam,
            final String voorvoegsel,
            final String scheidingsteken,
            final String geslachtsnaamstam) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actieSamengesteldeNaam)
                .metDatumAanvangGeldigheid(datum)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()),
                        geslachtsnaamstam)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getId()),
                        scheidingsteken)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getId()),
                        voornaam)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getId()),
                        voorvoegsel)
                .eindeRecord()
                .eindeGroep();
    }

    protected void voegGerelateerdeGeregistreerdePartnerSamengesteldeNaamToe(
            final MetaObject.Builder moBuilder,
            final Actie actieSamengesteldeNaam,
            final int datum,
            final String voornaam,
            final String voorvoegsel,
            final String scheidingsteken,
            final String geslachtsnaamstam) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actieSamengesteldeNaam)
                .metDatumAanvangGeldigheid(datum)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()),
                        geslachtsnaamstam)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getId()),
                        scheidingsteken)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getId()),
                        voornaam)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getId()),
                        voorvoegsel)
                .eindeRecord()
                .eindeGroep();
    }

    private void voegAfgeleidAdministratiefToe(final MetaObject.Builder moBuilder) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING.getId()),
                        actie.getTijdstipRegistratie())
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK.getId()),
                        actie.getTijdstipRegistratie())
                .eindeRecord()
                .eindeGroep();
    }

    private void voegInschrijvingToe(final MetaObject.Builder moBuilder) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_INSCHRIJVING.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_DATUM.getId()), 19200101)
                .metAttribuut(
                        ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL.getId()),
                        ZonedDateTime.of(1920, 1, 1, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID))
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_VERSIENUMMER.getId()), 1)
                .eindeRecord()
                .eindeGroep();
    }

    private void voegIndentiteitToe(final MetaObject.Builder moBuilder, final String identiteit) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_SOORTCODE.getId()), identiteit)
                .eindeRecord()
                .eindeGroep();
    }

    void voegGerelateerdeOuderIndentiteitToe(final MetaObject.Builder moBuilder, final String identiteit) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_SOORTCODE.getId()), identiteit)
                .eindeRecord()
                .eindeGroep();
    }

    private void voegBijhoudingToe(final MetaObject.Builder moBuilder) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metDatumAanvangGeldigheid(19200101)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId()), "051801")
                .eindeRecord()
                .eindeGroep();
    }

    private void voegNaamgebruikToe(final MetaObject.Builder moBuilder) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_NAAMGEBRUIK.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actie)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_CODE.getId()), "E")
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_INDICATIEAFGELEID.getId()), true)
                .eindeRecord()
                .eindeGroep();
    }

    private void voegGeslachtsaanduidingToe(final MetaObject.Builder moBuilder, final Actie actieNaamgeslacht, final int datum, final String code) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.PERSOON_GESLACHTSAANDUIDING.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actieNaamgeslacht)
                .metDatumAanvangGeldigheid(datum)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.PERSOON_GESLACHTSAANDUIDING_CODE.getId()), code)
                .eindeRecord()
                .eindeGroep();
    }

    void voegGerelateerdeOuderGeslachtsaanduidingToe(
            final MetaObject.Builder moBuilder,
            final Actie actieNaamgeslacht,
            final int datum,
            final String code) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actieNaamgeslacht)
                .metDatumAanvangGeldigheid(datum)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_CODE.getId()), code)
                .eindeRecord()
                .eindeGroep();
    }

    protected void voegGerelateerdeHuwelijkspartnerGeslachtsaanduidingToe(
            final MetaObject.Builder moBuilder,
            final Actie actieNaamgeslacht,
            final int datum,
            final String code) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actieNaamgeslacht)
                .metDatumAanvangGeldigheid(datum)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_CODE.getId()), code)
                .eindeRecord()
                .eindeGroep();
    }

    protected void voegGerelateerdeGeregistreerdePartnerGeslachtsaanduidingToe(
            final MetaObject.Builder moBuilder,
            final Actie actieNaamgeslacht,
            final int datum,
            final String code) {
        moBuilder.metGroep()
                .metGroepElement(ElementHelper.getGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING.getId()))
                .metRecord()
                .metId(idTeller.getAndIncrement())
                .metActieInhoud(actieNaamgeslacht)
                .metDatumAanvangGeldigheid(datum)
                .metAttribuut(ElementHelper.getAttribuutElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_CODE.getId()), code)
                .eindeRecord()
                .eindeGroep();
    }
}
