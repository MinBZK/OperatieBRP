/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis;

import java.math.BigInteger;

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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;

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
        final NaamGroepType naam)
    {
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
        final PersoonType persoon)
    {
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
        final String plaatsOntbinding)
    {
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
        final SoortRelatieType nieuwSoortRelatie)
    {
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
        final String plaats)
    {
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
        final String plaats)
    {
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
        final String voorvoegsels)
    {
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

    public static Relatie maakRelatie(final Integer id, final SoortRelatie soortRelatie) {
        final Relatie relatie = new Relatie(soortRelatie);
        relatie.setId(id);
        return relatie;
    }
}
