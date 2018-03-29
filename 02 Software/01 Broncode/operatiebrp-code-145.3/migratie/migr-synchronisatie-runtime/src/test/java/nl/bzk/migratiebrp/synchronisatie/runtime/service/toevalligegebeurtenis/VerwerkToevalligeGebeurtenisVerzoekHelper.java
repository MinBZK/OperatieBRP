/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis;

import java.math.BigInteger;
import java.util.Arrays;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AdellijkeTitelPredicaatType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AkteGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.FamilierechtelijkeBetrekkingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeboorteGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeldigheidGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeslachtGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeslachtsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.IdentificatienummersGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.NaamGeslachtType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.NaamGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.OverlijdenGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.OverlijdenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieOntbindingGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieSluitingGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieSoortGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieType.Omzetting;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieType.Ontbinding;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieType.Sluiting;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortRelatieType;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisNaamGeslacht;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisOverlijden;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenisOntbinding;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenisSluiting;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;

public final class VerwerkToevalligeGebeurtenisVerzoekHelper {

    private VerwerkToevalligeGebeurtenisVerzoekHelper() {
        // Niet instantieerbaar.
    }

    public static AkteGroepType maakAkteGroep(final String akteNummer, final String registerGemeente) {
        final AkteGroepType akteGroep = new AkteGroepType();
        akteGroep.setAktenummer(akteNummer);
        akteGroep.setRegistergemeente(registerGemeente);
        return akteGroep;
    }

    public static FamilierechtelijkeBetrekkingType maakFamilierechtelijkeBetrekking() {
        final FamilierechtelijkeBetrekkingType familierechtelijkBetrekking = new FamilierechtelijkeBetrekkingType();
        return familierechtelijkBetrekking;
    }

    public static GeldigheidGroepType maakGeldigheid(final BigInteger datumIngangGeldigheid) {
        final GeldigheidGroepType geldigheid = new GeldigheidGroepType();
        geldigheid.setDatumIngang(datumIngangGeldigheid);
        return geldigheid;
    }

    public static OverlijdenType maakOverlijden(final BigInteger datum, final String land, final String plaats) {
        final OverlijdenType overlijdenType = new OverlijdenType();
        final OverlijdenGroepType overlijden = new OverlijdenGroepType();
        overlijden.setDatum(datum);
        overlijden.setLand(land);
        overlijden.setPlaats(plaats);
        overlijdenType.setOverlijden(overlijden);
        return overlijdenType;
    }

    public static PersoonType maakPersoon(
            final BigInteger geboorteDatum,
            final String geboorteLand,
            final String geboortePlaats,
            final GeslachtsaanduidingType geslacht,
            final String aNummer,
            final String bsn,
            final NaamGroepType naam) {
        final PersoonType persoon = new PersoonType();
        final GeboorteGroepType geboorteGroep = new GeboorteGroepType();
        geboorteGroep.setDatum(geboorteDatum);
        geboorteGroep.setLand(geboorteLand);
        geboorteGroep.setPlaats(geboortePlaats);
        persoon.setGeboorte(geboorteGroep);
        final GeslachtGroepType geslachtGroep = new GeslachtGroepType();
        geslachtGroep.setGeslachtsaanduiding(geslacht);
        persoon.setGeslacht(geslachtGroep);
        final IdentificatienummersGroepType identificatieNummers = new IdentificatienummersGroepType();
        identificatieNummers.setANummer(aNummer);
        identificatieNummers.setBurgerservicenummer(bsn);
        persoon.setIdentificatienummers(identificatieNummers);
        persoon.setNaam(naam);
        return persoon;
    }

    public static RelatieType maakRelatieSluitingHuwelijk(
            final SoortRelatieType soortRelatie,
            final BigInteger datum,
            final String land,
            final String plaats,
            final PersoonType persoon) {
        final RelatieType relatie = new RelatieType();
        final Sluiting sluiting = new Sluiting();
        final RelatieSluitingGroepType relatieSluitGroep = new RelatieSluitingGroepType();
        relatieSluitGroep.setDatum(datum);
        relatieSluitGroep.setLand(land);
        relatieSluitGroep.setPlaats(plaats);
        final RelatieSoortGroepType relatieSoort = new RelatieSoortGroepType();
        relatieSoort.setSoort(soortRelatie);
        sluiting.setSluiting(relatieSluitGroep);
        sluiting.setSoort(relatieSoort);
        relatie.setSluiting(sluiting);
        relatie.setPersoon(persoon);
        return relatie;
    }

    public static RelatieType maakRelatieOntbindingHuwelijk(
            final SoortRelatieType soortRelatie,
            final BigInteger datum,
            final String land,
            final String plaats,
            final BigInteger datumOntbinding,
            final String landOntbinding,
            final String plaatsOntbinding) {
        final RelatieType relatie = new RelatieType();
        final Sluiting sluiting = new Sluiting();
        final RelatieSluitingGroepType relatieSluitGroep = new RelatieSluitingGroepType();
        relatieSluitGroep.setDatum(datum);
        relatieSluitGroep.setLand(land);
        relatieSluitGroep.setPlaats(plaats);
        final RelatieSoortGroepType relatieSoort = new RelatieSoortGroepType();
        relatieSoort.setSoort(soortRelatie);
        sluiting.setSluiting(relatieSluitGroep);
        sluiting.setSoort(relatieSoort);
        relatie.setSluiting(sluiting);
        final Ontbinding ontbinding = new Ontbinding();
        final RelatieOntbindingGroepType ontbindingType = new RelatieOntbindingGroepType();
        ontbindingType.setDatum(datumOntbinding);
        ontbindingType.setLand(landOntbinding);
        ontbindingType.setPlaats(plaatsOntbinding);
        ontbindingType.setReden("S");
        ontbinding.setOntbinding(ontbindingType);
        relatie.setOntbinding(ontbinding);
        return relatie;
    }

    public static RelatieType maakRelatieOmzettingHuwelijk(
            final SoortRelatieType soortRelatie,
            final BigInteger datum,
            final String land,
            final String plaats,
            final BigInteger datumOmzetting,
            final SoortRelatieType nieuwSoortRelatie) {
        final RelatieType relatie = new RelatieType();
        final Sluiting sluiting = new Sluiting();
        final RelatieSluitingGroepType relatieSluitGroepOmzetting = new RelatieSluitingGroepType();
        relatieSluitGroepOmzetting.setDatum(datum);
        relatieSluitGroepOmzetting.setLand(land);
        relatieSluitGroepOmzetting.setPlaats(plaats);
        final RelatieSluitingGroepType relatieSluitGroep = new RelatieSluitingGroepType();
        relatieSluitGroep.setDatum(datumOmzetting);
        relatieSluitGroep.setLand(land);
        relatieSluitGroep.setPlaats(plaats);
        final RelatieSoortGroepType relatieSoortOmzetting = new RelatieSoortGroepType();
        relatieSoortOmzetting.setSoort(nieuwSoortRelatie);
        sluiting.setSluiting(relatieSluitGroep);
        sluiting.setSoort(relatieSoortOmzetting);
        final RelatieSoortGroepType relatieSoort = new RelatieSoortGroepType();
        relatieSoort.setSoort(soortRelatie);
        final Omzetting omzetting = new Omzetting();
        omzetting.setSluiting(relatieSluitGroep);
        relatie.setOmzetting(omzetting);
        return relatie;
    }

    public static void voegRelatieOntbindingHuwelijkToe(
            final RelatieType relatie,
            final String reden,
            final BigInteger datum,
            final String land,
            final String plaats) {
        final RelatieOntbindingGroepType ontbindingGroepType = new RelatieOntbindingGroepType();
        final Ontbinding ontbinding = new Ontbinding();
        ontbindingGroepType.setDatum(datum);
        ontbindingGroepType.setPlaats(plaats);
        ontbindingGroepType.setLand(land);
        ontbindingGroepType.setReden(reden);
        ontbinding.setOntbinding(ontbindingGroepType);
        relatie.setOntbinding(ontbinding);
    }

    public static void voegRelatieOmzettingHuwelijkToe(
            final RelatieType relatie,
            final SoortRelatie soortRelatie,
            final BigInteger datum,
            final String land,
            final String plaats) {
        final RelatieSluitingGroepType sluitingGroepType = new RelatieSluitingGroepType();
        final Omzetting omzetting = new Omzetting();
        sluitingGroepType.setDatum(datum);
        sluitingGroepType.setPlaats(plaats);
        sluitingGroepType.setLand(land);
        omzetting.setSluiting(sluitingGroepType);
        relatie.setOmzetting(omzetting);
    }

    public static NaamGroepType maakNaam(
            final AdellijkeTitelPredicaatType adellijkeTitelPredicaat,
            final String geslachtsnaam,
            final String voornamen,
            final String voorvoegsels) {
        final NaamGroepType naamGroep = new NaamGroepType();
        naamGroep.setAdellijkeTitelPredicaat(adellijkeTitelPredicaat);
        naamGroep.setGeslachtsnaam(geslachtsnaam);
        naamGroep.setVoornamen(voornamen);
        naamGroep.setVoorvoegsel(voorvoegsels);
        return naamGroep;
    }

    public static NaamGeslachtType maakNaamGeslacht(final GeslachtsaanduidingType geslacht, final NaamGroepType naam) {
        final NaamGeslachtType naamGeslacht = new NaamGeslachtType();

        if (geslacht != null) {
            final GeslachtGroepType geslachtGroep = new GeslachtGroepType();
            geslachtGroep.setGeslachtsaanduiding(geslacht);
            naamGeslacht.setGeslacht(geslachtGroep);
        }

        if (naam != null) {
            naamGeslacht.setNaam(naam);
        }
        return naamGeslacht;
    }

    public static BrpRelatie maakRelatie(
            final Long id,
            final BrpSoortRelatieCode soortRelatieCode,
            final BrpDatum datumAanvang,
            final BrpGemeenteCode gemeenteCodeAanvang,
            final BrpLandOfGebiedCode landOfGebiedCodeAanvang,
            final BrpString buitenlandsePlaatsAanvang,
            final BrpDatum datumEinde,
            final BrpGemeenteCode gemeenteCodeEinde,
            final BrpLandOfGebiedCode landOfGebiedCodeEinde,
            final BrpString buitenlandsePlaatsEinde,
            final BrpRedenEindeRelatieCode redenEindeRelatieCode,
            final BrpStapel<BrpGeboorteInhoud> geboorte,
            final BrpStapel<BrpGeslachtsaanduidingInhoud> geslacht,
            final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaam,
            final boolean betrokkenhedenGelijk,
            final boolean vervallenRelatie) {
        final BrpRelatieInhoud inhoud =
                new BrpRelatieInhoud(
                        datumAanvang,
                        gemeenteCodeAanvang,
                        null,
                        buitenlandsePlaatsAanvang,
                        null,
                        landOfGebiedCodeAanvang,
                        null,
                        redenEindeRelatieCode,
                        datumEinde,
                        gemeenteCodeEinde,
                        null,
                        buitenlandsePlaatsEinde,
                        null,
                        landOfGebiedCodeEinde,
                        null);
        final BrpHistorie historie;
        if (vervallenRelatie) {
            historie = BrpStapelHelper.his(19581512, 20160101, 19581512, 20160101);
        } else {
            historie = BrpStapelHelper.his(20110101);
        }
        final BrpGroep<BrpRelatieInhoud> relatieGroep = new BrpGroep<>(inhoud, historie, BrpStapelHelper.act(0, 20110101), null, null);
        final BrpStapel<BrpRelatieInhoud> relatieStapel = new BrpStapel<>(Arrays.asList(relatieGroep));
        final BrpBetrokkenheid betrokkenheid =
                new BrpBetrokkenheid(BrpSoortBetrokkenheidCode.PARTNER, null, geslacht, geboorte, null, samengesteldeNaam, null, null);
        final BrpBetrokkenheid ikBetrokkenheid;
        if (betrokkenhedenGelijk) {
            ikBetrokkenheid = betrokkenheid;

        } else {
            ikBetrokkenheid = new BrpBetrokkenheid(BrpSoortBetrokkenheidCode.PARTNER, null, null, null, null, null, null, null);
        }
        final BrpRelatie relatie =
                new BrpRelatie(
                        id,
                        soortRelatieCode,
                        BrpSoortBetrokkenheidCode.PARTNER,
                        ikBetrokkenheid,
                        Arrays.asList(betrokkenheid),
                        relatieStapel,
                        null,
                        null,
                        null,
                        null,
                        null);

        return relatie;

    }

    public static BrpToevalligeGebeurtenisPersoon maakPersoon(
            final String administratieNummer,
            final String burgerservicenummer,
            final BrpPredicaatCode predikaatCode,
            final String voornamen,
            final String voorvoegselGeslachtsnaam,
            final Character scheidingsteken,
            final BrpAdellijkeTitelCode adellijkeTitelCode,
            final String geslachtsnaam,
            final Integer geboortedatum,
            final String geboorteGemeenteCode,
            final String geboorteLandCode,
            final String buitenlandseGeboorteplaats,
            final String locatieOmschrijvingGeboorte,
            final BrpGeslachtsaanduidingCode geslachtsaanduiding) {
        return new BrpToevalligeGebeurtenisPersoon(
                administratieNummer == null ? null : new BrpString(administratieNummer),
                burgerservicenummer == null ? null : new BrpString(burgerservicenummer),
                predikaatCode,
                new BrpString(voornamen),
                voorvoegselGeslachtsnaam == null ? null : new BrpString(voorvoegselGeslachtsnaam),
                scheidingsteken == null ? null : new BrpCharacter(scheidingsteken),
                adellijkeTitelCode,
                new BrpString(geslachtsnaam),
                new BrpDatum(geboortedatum, null),
                geboorteGemeenteCode == null ? null : new BrpGemeenteCode(geboorteGemeenteCode),
                buitenlandseGeboorteplaats == null ? null : new BrpString(buitenlandseGeboorteplaats),
                new BrpLandOfGebiedCode(geboorteLandCode),
                locatieOmschrijvingGeboorte == null ? null : new BrpString(locatieOmschrijvingGeboorte),
                geslachtsaanduiding);
    }

    public static BrpToevalligeGebeurtenisNaamGeslacht maakNaamGeslacht(
            final BrpPredicaatCode predikaatCode,
            final String voornamen,
            final String voorvoegselGeslachtsnaam,
            final Character scheidingsteken,
            final BrpAdellijkeTitelCode adellijkeTitelCode,
            final String geslachtsnaam,
            final BrpGeslachtsaanduidingCode geslachtsaanduiding) {
        return new BrpToevalligeGebeurtenisNaamGeslacht(
                predikaatCode,
                new BrpString(voornamen),
                voorvoegselGeslachtsnaam == null ? null : new BrpString(voorvoegselGeslachtsnaam),
                scheidingsteken == null ? null : new BrpCharacter(scheidingsteken),
                adellijkeTitelCode,
                new BrpString(geslachtsnaam),
                geslachtsaanduiding);
    }

    public static BrpToevalligeGebeurtenisOverlijden maakOverlijden(
            final Integer datum,
            final String gemeente,
            final String buitenlandsePlaats,
            final String land) {
        return new BrpToevalligeGebeurtenisOverlijden(
                new BrpDatum(datum, null),
                gemeente == null ? null : new BrpGemeenteCode(gemeente),
                buitenlandsePlaats == null ? null : new BrpString(buitenlandsePlaats),
                new BrpLandOfGebiedCode(land),
                null);
    }

    public static BrpToevalligeGebeurtenisVerbintenisSluiting maakSluiting(
            final BrpSoortRelatieCode soortVerbintenis,
            final Integer datum,
            final String gemeente,
            final String buitenlandsePlaats,
            final String land) {
        return new BrpToevalligeGebeurtenisVerbintenisSluiting(
                soortVerbintenis,
                new BrpDatum(datum, null),
                gemeente == null ? null : new BrpGemeenteCode(gemeente),
                buitenlandsePlaats == null ? null : new BrpString(buitenlandsePlaats),
                new BrpLandOfGebiedCode(land),
                null);
    }

    public static BrpToevalligeGebeurtenisVerbintenisOntbinding maakOntbinding(
            final BrpRedenEindeRelatieCode redenEindeVerbintenis,
            final Integer datum,
            final String gemeente,
            final String buitenlandsePlaats,
            final String land) {
        return new BrpToevalligeGebeurtenisVerbintenisOntbinding(
                new BrpDatum(datum, null),
                gemeente == null ? null : new BrpGemeenteCode(gemeente),
                buitenlandsePlaats == null ? null : new BrpString(buitenlandsePlaats),
                new BrpLandOfGebiedCode(land),
                null,
                redenEindeVerbintenis);
    }
}
