/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

public final class BrpStapelHelper {

    private BrpStapelHelper() {
        throw new AssertionError("Niet instantieerbaar");
    }

    public static BrpHistorie his(final Integer aanvangGeldigheid, final Integer eindeGeldigheid, final Long registratie, final Long verval) {
        final BrpDatum datumAanvangGeldigheid = aanvangGeldigheid == null ? null : new BrpDatum(aanvangGeldigheid, null);
        final BrpDatum datumEindeGeldigheid = eindeGeldigheid == null ? null : new BrpDatum(eindeGeldigheid, null);
        final BrpDatumTijd datumTijdRegistratie = BrpDatumTijd.fromDatumTijd(registratie, null);
        final BrpDatumTijd datumTijdVerval = verval == null ? null : BrpDatumTijd.fromDatumTijd(verval, null);

        return new BrpHistorie(datumAanvangGeldigheid, datumEindeGeldigheid, datumTijdRegistratie, datumTijdVerval, null);
    }

    public static BrpHistorie his(final Integer aanvangGeldigheid, final Integer eindeGeldigheid, final Integer registratie, final Integer verval) {

        return BrpStapelHelper.his(aanvangGeldigheid, eindeGeldigheid, registratie * 1000000L, verval == null ? null : verval * 1000000L);
    }

    public static BrpHistorie his(final Integer aanvangGeldigheid, final Long registratie) {
        return BrpStapelHelper.his(aanvangGeldigheid, null, registratie, null);
    }

    public static BrpHistorie his(final Integer aanvangGeldigheid) {
        return BrpStapelHelper.his(aanvangGeldigheid, null, aanvangGeldigheid + 1, null);
    }

    public static BrpDocumentInhoud document(
            final String soortDocumentCode,
            final String aktenummer,
            final String omschrijving,
            final String partijCode) {
        final BrpSoortDocumentCode soortDocument = soortDocumentCode == null ? null : new BrpSoortDocumentCode(soortDocumentCode);
        final BrpPartijCode partij = partijCode == null ? null : new BrpPartijCode(partijCode);

        return new BrpDocumentInhoud(
                soortDocument,
                BrpString.wrap(aktenummer, null),
                BrpString.wrap(omschrijving, null),
                partij);
    }

    public static BrpDocumentInhoud doc(final String omschrijving, final String partijCode) {
        return BrpStapelHelper.document("Document", null, omschrijving, partijCode);
    }

    private static BrpDocumentInhoud akt(final String aktenummer, final String partijCode) {
        return BrpStapelHelper.document("Akte", aktenummer, null, partijCode);
    }

    public static BrpActie act(
            final Long id,
            final String soortActieCode,
            final String partijCode,
            final Long datumTijdRegistratie,
            final Lo3Herkomst lo3Herkomst,
            final BrpDocumentInhoud... documenten) {

        final BrpSoortActieCode soortActie = new BrpSoortActieCode(soortActieCode);
        final BrpPartijCode partij = partijCode == null ? null : new BrpPartijCode(partijCode);
        final BrpDatumTijd registratie = datumTijdRegistratie == null ? null : BrpDatumTijd.fromDatumTijd(datumTijdRegistratie, null);

        final List<BrpActieBron> documentStapels = new ArrayList<>();
        for (final BrpDocumentInhoud documentInhoud : documenten) {
            documentStapels.add(
                    new BrpActieBron(
                            new BrpStapel<>(
                                    Collections.singletonList(
                                            new BrpGroep<>(documentInhoud, new BrpHistorie(null, null, registratie, null, null), null, null, null))),
                            null));
        }

        return new BrpActie(id, soortActie, partij, registratie, null, documentStapels, 0, lo3Herkomst);
    }

    public static BrpActie act(
            final Long id,
            final String soortActieCode,
            final String partijCode,
            final Long datumTijdRegistratie,
            final String aktenummer) {
        return BrpStapelHelper.act(id, soortActieCode, partijCode, datumTijdRegistratie, null, BrpStapelHelper.akt(aktenummer, partijCode));
    }

    public static BrpActie act(final Integer id, final Integer datumTijdRegistratie, final Lo3Herkomst lo3Herkomst) {
        return BrpStapelHelper.act(
                id.longValue(),
                "Conversie GBA",
                "051801",
                datumTijdRegistratie * 1000000L,
                lo3Herkomst,
                BrpStapelHelper.akt("9-X" + String.format("%04d", id), "051801"));
    }

    public static BrpActie act(final Integer id, final Integer datumTijdRegistratie) {
        return BrpStapelHelper.act(id, datumTijdRegistratie, (Lo3Herkomst) null);
    }

    public static BrpActie act(final Integer id, final Integer datumTijdRegistratie, final BrpDocumentInhoud document) {
        return BrpStapelHelper.act(id.longValue(), "Conversie GBA", "051801", datumTijdRegistratie * 1000000L, null, document);
    }

    @SafeVarargs
    public static <T extends BrpGroepInhoud> BrpStapel<T> stapel(final BrpGroep<T>... groepen) {
        return new BrpStapel<>(Arrays.asList(groepen));
    }

    public static <T extends BrpGroepInhoud> BrpGroep<T> groep(
            final T inhoud,
            final BrpHistorie historie,
            final BrpActie actieInhoud,
            final BrpActie actieVerval,
            final BrpActie actieGeldigheid) {
        return new BrpGroep<>(inhoud, historie, actieInhoud, actieVerval, actieGeldigheid);
    }

    public static <T extends BrpGroepInhoud> BrpGroep<T> groep(
            final T inhoud,
            final BrpHistorie historie,
            final BrpActie actieInhoud,
            final BrpActie actieVerval) {
        return BrpStapelHelper.groep(inhoud, historie, actieInhoud, actieVerval, null);
    }

    public static <T extends BrpGroepInhoud> BrpGroep<T> groep(final T inhoud, final BrpHistorie historie, final BrpActie actieInhoud) {
        return BrpStapelHelper.groep(inhoud, historie, actieInhoud, null, null);
    }

    public static BrpDatum datum(final Integer datum) {
        return datum == null ? null : new BrpDatum(datum, null);
    }

    public static BrpGemeenteCode gemeente(final String gemeente) {
        return gemeente == null ? null : new BrpGemeenteCode(gemeente);
    }

    public static BrpLandOfGebiedCode land(final String land) {
        return land == null ? null : new BrpLandOfGebiedCode(land);
    }

    public static BrpDerdeHeeftGezagIndicatieInhoud derde(final Boolean derdeHeeftGezag) {
        return new BrpDerdeHeeftGezagIndicatieInhoud(new BrpBoolean(derdeHeeftGezag), null, null);
    }

    public static BrpInschrijvingInhoud inschrijving(final Integer datumInschrijving, final int versienummer, final long datumtijdstempel) {
        return new BrpInschrijvingInhoud(
                new BrpDatum(datumInschrijving, null),
                BrpLong.wrap((long) versienummer, null),
                BrpDatumTijd.fromDatumTijd(datumtijdstempel, null));
    }

    public static BrpNummerverwijzingInhoud nummerverwijzing(
            final String vorigeAdministratienummer,
            final String volgendeAdministratienummer,
            final String vorigeBurgerservicenummer,
            final String volgendeBurgerservicenummer) {
        return new BrpNummerverwijzingInhoud(
                BrpString.wrap(vorigeAdministratienummer, null),
                BrpString.wrap(volgendeAdministratienummer, null),
                BrpString.wrap(vorigeBurgerservicenummer, null),
                BrpString.wrap(volgendeBurgerservicenummer, null));
    }

    public static BrpPersoonskaartInhoud persoonskaart(final String gemeentePartijCode, final Boolean pkVolledig) {
        final BrpPartijCode gemeentePKCode = gemeentePartijCode == null ? null : new BrpPartijCode(gemeentePartijCode);
        final BrpBoolean indicatiePKVolledigGeconverteerd = Boolean.TRUE.equals(pkVolledig) ? new BrpBoolean(true) : null;

        return new BrpPersoonskaartInhoud(gemeentePKCode, indicatiePKVolledigGeconverteerd);
    }

    public static BrpVerstrekkingsbeperkingIndicatieInhoud verstrekkingsbeperking(final boolean verstrekkingsbeperking) {
        return new BrpVerstrekkingsbeperkingIndicatieInhoud(new BrpBoolean(verstrekkingsbeperking), null, null);
    }

    public static BrpPersoonAfgeleidAdministratiefInhoud afgeleid() {
        return new BrpPersoonAfgeleidAdministratiefInhoud();
    }

    public static BrpVerificatieInhoud verificatie(final int datum, final String omschrijving, final String partijCode) {
        return new BrpVerificatieInhoud(new BrpPartijCode(partijCode), new BrpString(omschrijving), new BrpDatum(datum, null));
    }

    public static BrpDeelnameEuVerkiezingenInhoud deelnameEuVerkiezingen(
            final Boolean deelnameEuVerkiezingen,
            final Integer aanleidingAanpassingDeelnameEuVerkiezingen,
            final Integer voorzienEindeUitsluitingEuVerkiezingen) {
        final BrpBoolean indicatieDeelnameEuVerkiezingen = Boolean.TRUE.equals(deelnameEuVerkiezingen) ? new BrpBoolean(Boolean.TRUE) : null;
        final BrpDatum datumAanleidingAanpassingDeelnameEuVerkiezingen =
                aanleidingAanpassingDeelnameEuVerkiezingen == null ? null : new BrpDatum(aanleidingAanpassingDeelnameEuVerkiezingen, null);
        final BrpDatum datumVoorzienEindeUitsluitingEuVerkiezingen =
                voorzienEindeUitsluitingEuVerkiezingen == null ? null : new BrpDatum(voorzienEindeUitsluitingEuVerkiezingen, null);

        return new BrpDeelnameEuVerkiezingenInhoud(
                indicatieDeelnameEuVerkiezingen,
                datumAanleidingAanpassingDeelnameEuVerkiezingen,
                datumVoorzienEindeUitsluitingEuVerkiezingen);
    }

    public static BrpUitsluitingKiesrechtInhoud uitsluiting(
            final Boolean indicatieUitsluitingKiesrecht,
            final Integer datumVoorzienEindeUitsluitingKiesrecht) {
        final BrpBoolean uitsluitingKiesrecht = Boolean.TRUE.equals(indicatieUitsluitingKiesrecht) ? new BrpBoolean(Boolean.TRUE) : null;
        final BrpDatum voorzienEindeUitsluitingKiesrecht =
                datumVoorzienEindeUitsluitingKiesrecht == null ? null : new BrpDatum(datumVoorzienEindeUitsluitingKiesrecht, null);

        return new BrpUitsluitingKiesrechtInhoud(uitsluitingKiesrecht, voorzienEindeUitsluitingKiesrecht);
    }

    public static BrpNationaliteitInhoud nationaliteit(final String natCode, final String verkrijgCode, final String verliesCode) {
        return new BrpNationaliteitInhoud(
                natCode == null ? null : new BrpNationaliteitCode(natCode),
                verkrijgCode == null ? null : new BrpRedenVerkrijgingNederlandschapCode(verkrijgCode),
                verliesCode == null ? null : new BrpRedenVerliesNederlandschapCode(verliesCode),
                null,
                null,
                null,
                null);
    }

    public static BrpBehandeldAlsNederlanderIndicatieInhoud behandeldAlsNederlander(final Boolean indicatie) {
        return new BrpBehandeldAlsNederlanderIndicatieInhoud(new BrpBoolean(indicatie), null, null);
    }

    public static BrpVastgesteldNietNederlanderIndicatieInhoud vastgesteldNietNederlander(final Boolean indicatie) {
        return new BrpVastgesteldNietNederlanderIndicatieInhoud(new BrpBoolean(indicatie), null, null);
    }

    public static BrpGeboorteInhoud geboorte(final int datum, final String gemeente) {
        return new BrpGeboorteInhoud(
                new BrpDatum(datum, null),
                gemeente == null ? null : new BrpGemeenteCode(gemeente),
                null,
                null,
                null,
                new BrpLandOfGebiedCode("6030"),
                null);
    }

    public static BrpVoornaamInhoud voornaam(final String voornaam, final int volgnummer) {
        return new BrpVoornaamInhoud(new BrpString(voornaam), new BrpInteger(volgnummer));
    }

    public static BrpSamengesteldeNaamInhoud samengesteldnaam(final String voornamen, final String geslachtsnaam) {
        return new BrpSamengesteldeNaamInhoud(
                null,
                new BrpString(voornamen),
                null,
                null,
                null,
                new BrpString(geslachtsnaam),
                new BrpBoolean(false),
                new BrpBoolean(true));
    }

    public static BrpSamengesteldeNaamInhoud samengesteldnaam(
            final String voornamen,
            final String geslachtsnaam,
            final String predikaat,
            final String adellijkeTitel) {
        return new BrpSamengesteldeNaamInhoud(
                predikaat == null ? null : new BrpPredicaatCode(predikaat),
                new BrpString(voornamen),
                null,
                null,
                adellijkeTitel == null ? null : new BrpAdellijkeTitelCode(adellijkeTitel),
                new BrpString(geslachtsnaam),
                new BrpBoolean(false),
                new BrpBoolean(true));
    }

    public static BrpGeslachtsaanduidingInhoud geslacht(final String geslacht) {
        return new BrpGeslachtsaanduidingInhoud(new BrpGeslachtsaanduidingCode(geslacht));
    }

    public static BrpAdresInhoud adres(
            final String soortAdresCode,
            final Character redenWijzigingVerblijfCode,
            final Character aangeverCode,
            final Integer datumAanvangAdreshouding,
            final String gemeenteCode,
            final String naamOpenbareRuimte,
            final String afgekorteNaamOpenbareRuimte,
            final Integer huisnummer,
            final String postcode,
            final String woonplaatsnaam) {
        return BrpStapelHelper.adres(
                soortAdresCode,
                redenWijzigingVerblijfCode,
                aangeverCode,
                datumAanvangAdreshouding,
                null,
                null,
                gemeenteCode,
                naamOpenbareRuimte,
                afgekorteNaamOpenbareRuimte,
                null,
                huisnummer,
                null,
                null,
                postcode,
                woonplaatsnaam,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                BrpLandOfGebiedCode.NEDERLAND.getWaarde(),
                null);
    }

    public static BrpAdresInhoud adres(
            final String soortAdresCode,
            final Character redenWijzigingVerblijfCode,
            final Character aangeverCode,
            final Integer datumAanvangAdreshouding,
            final String identificatiecodeAdresseerbaarObject,
            final String identificatiecodeNummeraanduiding,
            final String gemeenteCode,
            final String naamOpenbareRuimte,
            final String afgekorteNaamOpenbareRuimte,
            final String gemeentedeel,
            final Integer huisnummer,
            final Character huisletter,
            final String huisnummertoevoeging,
            final String postcode,
            final String woonplaatsnaam,
            final String locatieTovAdres,
            final String locatieOmschrijving) {
        return BrpStapelHelper.adres(
                soortAdresCode,
                redenWijzigingVerblijfCode,
                aangeverCode,
                datumAanvangAdreshouding,
                identificatiecodeAdresseerbaarObject,
                identificatiecodeNummeraanduiding,
                gemeenteCode,
                naamOpenbareRuimte,
                afgekorteNaamOpenbareRuimte,
                gemeentedeel,
                huisnummer,
                huisletter,
                huisnummertoevoeging,
                postcode,
                woonplaatsnaam,
                locatieTovAdres,
                locatieOmschrijving,
                null,
                null,
                null,
                null,
                null,
                null,
                BrpLandOfGebiedCode.NEDERLAND.getWaarde(),
                null);
    }

    public static BrpAdresInhoud adres(
            final String soortAdresCode,
            final Character redenWijzigingVerblijfCode,
            final Character aangeverCode,
            final Integer datumAanvangAdreshouding,
            final String identificatiecodeAdresseerbaarObject,
            final String identificatiecodeNummeraanduiding,
            final String gemeenteCode,
            final String naamOpenbareRuimte,
            final String afgekorteNaamOpenbareRuimte,
            final String gemeentedeel,
            final Integer huisnummer,
            final Character huisletter,
            final String huisnummertoevoeging,
            final String postcode,
            final String woonplaatsnaam,
            final String locatieTovAdres,
            final String locatieOmschrijving,
            final String buitenlandsAdresRegel1,
            final String buitenlandsAdresRegel2,
            final String buitenlandsAdresRegel3,
            final String buitenlandsAdresRegel4,
            final String buitenlandsAdresRegel5,
            final String buitenlandsAdresRegel6,
            final String landOfGebiedCode,
            final Boolean indicatiePersoonAangetroffenOpAdres) {
        return new BrpAdresInhoud(
                soortAdresCode == null ? null : new BrpSoortAdresCode(soortAdresCode),
                redenWijzigingVerblijfCode == null ? null : new BrpRedenWijzigingVerblijfCode(redenWijzigingVerblijfCode),
                aangeverCode == null ? null : new BrpAangeverCode(aangeverCode),
                BrpStapelHelper.datum(datumAanvangAdreshouding),
                BrpString.wrap(identificatiecodeAdresseerbaarObject, null),
                BrpString.wrap(identificatiecodeNummeraanduiding, null),
                BrpStapelHelper.gemeente(gemeenteCode),
                BrpString.wrap(naamOpenbareRuimte, null),
                BrpString.wrap(afgekorteNaamOpenbareRuimte, null),
                BrpString.wrap(gemeentedeel, null),
                BrpInteger.wrap(huisnummer, null),
                BrpCharacter.wrap(huisletter, null),
                BrpString.wrap(huisnummertoevoeging, null),
                BrpString.wrap(postcode, null),
                BrpString.wrap(woonplaatsnaam, null),
                locatieTovAdres == null ? null : new BrpAanduidingBijHuisnummerCode(locatieTovAdres),
                BrpString.wrap(locatieOmschrijving, null),
                BrpString.wrap(buitenlandsAdresRegel1, null),
                BrpString.wrap(buitenlandsAdresRegel2, null),
                BrpString.wrap(buitenlandsAdresRegel3, null),
                BrpString.wrap(buitenlandsAdresRegel4, null),
                BrpString.wrap(buitenlandsAdresRegel5, null),
                BrpString.wrap(buitenlandsAdresRegel6, null),
                landOfGebiedCode == null ? null : new BrpLandOfGebiedCode(landOfGebiedCode),
                BrpBoolean.wrap(indicatiePersoonAangetroffenOpAdres, null));
    }

    public static BrpBijhoudingInhoud bijhouding(
            final String bijhoudingspartijCode,
            final BrpBijhoudingsaardCode bijhoudingsaardCode,
            final BrpNadereBijhoudingsaardCode nadereBijhoudingsaardCode) {
        return new BrpBijhoudingInhoud(
                new BrpPartijCode(bijhoudingspartijCode),
                bijhoudingsaardCode,
                nadereBijhoudingsaardCode);
    }

    public static BrpIdentificatienummersInhoud identificatie(final String administratienummer, final String burgerservicenummer) {
        return new BrpIdentificatienummersInhoud(BrpString.wrap(administratienummer, null), BrpString.wrap(burgerservicenummer, null));
    }

}
