/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;

public final class BrpIstTestUtils {

    private static final LandOfGebied LAND_GEBIED_NEDERLAND = new LandOfGebied("6030", "Nederland");
    private static final Partij PARTIJ_AMSTERDAM = new Partij("Gemeente Amsterdam", "000363");
    private static final Gemeente GEMEENTE_DEN_HAAG = new Gemeente((short) 518, "Den Haag", "0518", new Partij("Gemeente Den Haag", "000518"));
    private static final Gemeente GEMEENTE_BREDA = new Gemeente((short) 758, "Breda", "0758", new Partij("Gemeente Breda", "000758"));
    private static final String GEBOORTE_AKTE = "geboorte akte";

    /**
     * Niet instantieerbaar.
     */
    private BrpIstTestUtils() {
    }

    public static Stapel maakStapelModel(final Persoon persoon, final Lo3CategorieEnum lo3Categorie, final Integer volgnummer) {
        return new Stapel(persoon, lo3Categorie.getCategorie(), volgnummer);
    }

    public static StapelVoorkomen maakStapelVoorkomenModel(
            final Stapel stapel,
            final Integer volgnummer,
            final AdministratieveHandeling administratieveHandeling) {
        final StapelVoorkomen result = new StapelVoorkomen(stapel, volgnummer, administratieveHandeling);
        stapel.getStapelvoorkomens().add(result);
        return result;
    }

    public static void vulStapelVoorkomenCategorieGezagsverhoudingGroepModel(
            final StapelVoorkomen stapelVoorkomen,
            final boolean ouder1Gezag,
            final boolean ouder2Gezag,
            final boolean derdeGezag,
            final boolean onderCuratele) {
        stapelVoorkomen.setIndicatieOuder1HeeftGezag(ouder1Gezag);
        stapelVoorkomen.setIndicatieOuder2HeeftGezag(ouder2Gezag);
        stapelVoorkomen.setIndicatieDerdeHeeftGezag(derdeGezag);
        stapelVoorkomen.setIndicatieOnderCuratele(onderCuratele);
    }

    public static void vulStapelVoorkomenCategorieGerelateerdenGroepModel(
            final StapelVoorkomen stapelVoorkomen,
            final String aktenummer,
            final String aNummer,
            final String bsn,
            final String voornamen,
            final String voorvoegsel,
            final String geslachtsnaam,
            final Integer geboorteDatum,
            final Predicaat predicaat,
            final AdellijkeTitel adellijkeTitel,
            final Gemeente gemeenteGeboorte,
            final LandOfGebied landGebiedGeboorte,
            final Geslachtsaanduiding geslacht) {
        stapelVoorkomen.setAktenummer(aktenummer);
        stapelVoorkomen.setAnummer(aNummer);
        stapelVoorkomen.setBsn(bsn);
        stapelVoorkomen.setVoornamen(voornamen);
        stapelVoorkomen.setGeslachtsaanduidingBijAdellijkeTitelOfPredikaat(geslacht);
        stapelVoorkomen.setPredicaat(predicaat);
        stapelVoorkomen.setAdellijkeTitel(adellijkeTitel);
        stapelVoorkomen.setVoorvoegsel(voorvoegsel);
        stapelVoorkomen.setScheidingsteken('-');
        stapelVoorkomen.setGeslachtsnaamstam(geslachtsnaam);
        stapelVoorkomen.setDatumGeboorte(geboorteDatum);
        stapelVoorkomen.setGemeenteGeboorte(gemeenteGeboorte);
        stapelVoorkomen.setLandOfGebiedGeboorte(landGebiedGeboorte);
        stapelVoorkomen.setBuitenlandsePlaatsGeboorte(null);
        stapelVoorkomen.setOmschrijvingLocatieGeboorte(null);
        stapelVoorkomen.setGeslachtsaanduiding(geslacht);

    }

    public static void vulStapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel(
            final StapelVoorkomen stapelVoorkomen,
            final Integer aanvangDatum,
            final Integer eindDatum,
            final Gemeente gemeenteAanvang,
            final LandOfGebied landAanvang,
            final String locatieOmschrijving,
            final Character redenEindeRelatieCode,
            final String omschrijvingRedenEinde,
            final SoortRelatie soortRelatieCode) {
        stapelVoorkomen.setDatumAanvang(aanvangDatum);
        stapelVoorkomen.setGemeenteAanvang(gemeenteAanvang);
        stapelVoorkomen.setLandOfGebiedAanvang(landAanvang);
        stapelVoorkomen.setBuitenlandsePlaatsAanvang(null);
        stapelVoorkomen.setOmschrijvingLocatieAanvang(locatieOmschrijving);

        stapelVoorkomen.setRedenBeeindigingRelatie(
                redenEindeRelatieCode == null ? null : new RedenBeeindigingRelatie(redenEindeRelatieCode, "Omschrijving"));

        stapelVoorkomen.setDatumEinde(eindDatum);
        stapelVoorkomen.setGemeenteEinde(null);
        stapelVoorkomen.setLandOfGebiedEinde(null);
        stapelVoorkomen.setBuitenlandsePlaatsEinde(null);
        stapelVoorkomen.setOmschrijvingLocatieEinde(null);

        stapelVoorkomen.setSoortRelatie(soortRelatieCode);

    }

    public static void vulStapelVoorkomenStandaardGroepModel(
            final StapelVoorkomen stapelVoorkomen,
            final SoortDocument soortDocument,
            final Partij partij,
            final Integer rubriek8220DatumDocument,
            final String documentOmschrijving,
            final Integer rubriek8310AanduidingGegevensInOnderzoek,
            final Integer rubriek8320DatumIngangOnderzoek,
            final Integer rubriek8330DatumEindeOnderzoek,
            final Character rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde,
            final Integer rubriek8510IngangsdatumGeldigheid,
            final Integer rubriek8610DatumVanOpneming) {
        stapelVoorkomen.setSoortDocument(soortDocument);
        stapelVoorkomen.setPartij(partij);
        stapelVoorkomen.setRubriek8220DatumDocument(rubriek8220DatumDocument);
        stapelVoorkomen.setDocumentOmschrijving(documentOmschrijving);
        stapelVoorkomen.setRubriek8310AanduidingGegevensInOnderzoek(rubriek8310AanduidingGegevensInOnderzoek);
        stapelVoorkomen.setRubriek8320DatumIngangOnderzoek(rubriek8320DatumIngangOnderzoek);
        stapelVoorkomen.setRubriek8330DatumEindeOnderzoek(rubriek8330DatumEindeOnderzoek);
        stapelVoorkomen.setRubriek8410OnjuistOfStrijdigOpenbareOrde(rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde);
        stapelVoorkomen.setRubriek8510IngangsdatumGeldigheid(rubriek8510IngangsdatumGeldigheid);
        stapelVoorkomen.setRubriek8610DatumVanOpneming(rubriek8610DatumVanOpneming);
    }

    public static void vulStapelVoorkomenCategorieOudersGroepModel(
            final StapelVoorkomen stapelVoorkomen,
            final Integer datumIngangFamilierechtelijkeBetrekking) {
        stapelVoorkomen.setRubriek6210DatumIngangFamilierechtelijkeBetrekking(datumIngangFamilierechtelijkeBetrekking);
    }

    public static Set<Stapel> maakSimpeleStapelAlleCategorien() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);

        final Set<Stapel> istStapel = new HashSet<>();
        final Stapel stapelCategorie2 = BrpIstTestUtils.maakStapelModel(persoon, Lo3CategorieEnum.CATEGORIE_02, 0);
        final Stapel stapelCategorie3 = BrpIstTestUtils.maakStapelModel(persoon, Lo3CategorieEnum.CATEGORIE_03, 0);
        final Stapel stapelCategorie5 = BrpIstTestUtils.maakStapelModel(persoon, Lo3CategorieEnum.CATEGORIE_05, 0);
        final Stapel stapelCategorie9 = BrpIstTestUtils.maakStapelModel(persoon, Lo3CategorieEnum.CATEGORIE_09, 0);
        final Stapel stapelCategorie11 = BrpIstTestUtils.maakStapelModel(persoon, Lo3CategorieEnum.CATEGORIE_11, 0);

        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(
                        new Partij("Amsterdam", "000517"),
                        SoortAdministratieveHandeling.GBA_INITIELE_VULLING,
                        new Timestamp(System.currentTimeMillis()));

        final StapelVoorkomen stapelVoorkomenCategorie2 = BrpIstTestUtils.maakStapelVoorkomenModel(stapelCategorie2, 0, administratieveHandeling);
        final StapelVoorkomen stapelVoorkomenCategorie3 = BrpIstTestUtils.maakStapelVoorkomenModel(stapelCategorie3, 0, administratieveHandeling);
        final StapelVoorkomen stapelVoorkomenCategorie5 = BrpIstTestUtils.maakStapelVoorkomenModel(stapelCategorie5, 0, administratieveHandeling);
        final StapelVoorkomen stapelVoorkomenCategorie9 = BrpIstTestUtils.maakStapelVoorkomenModel(stapelCategorie9, 0, administratieveHandeling);
        final StapelVoorkomen stapelVoorkomenCategorie11 = BrpIstTestUtils.maakStapelVoorkomenModel(stapelCategorie11, 0, administratieveHandeling);

        BrpIstTestUtils.vulStapelVoorkomenStandaardGroepModel(
                stapelVoorkomenCategorie2,
                new SoortDocument("Geboorte akte", "Geboorte akte"),
                PARTIJ_AMSTERDAM,
                20100101,
                GEBOORTE_AKTE,
                null,
                null,
                null,
                null,
                20100101,
                20100101);
        BrpIstTestUtils.vulStapelVoorkomenStandaardGroepModel(
                stapelVoorkomenCategorie3,
                new SoortDocument("Geboorte akte", "Geboorte akte"),
                PARTIJ_AMSTERDAM,
                20100101,
                GEBOORTE_AKTE,
                null,
                null,
                null,
                null,
                20100101,
                20100101);
        BrpIstTestUtils.vulStapelVoorkomenStandaardGroepModel(
                stapelVoorkomenCategorie5,
                new SoortDocument("Geboorte akte", "Geboorte akte"),
                PARTIJ_AMSTERDAM,
                20100101,
                GEBOORTE_AKTE,
                null,
                null,
                null,
                null,
                20100101,
                20100101);
        BrpIstTestUtils.vulStapelVoorkomenStandaardGroepModel(
                stapelVoorkomenCategorie9,
                new SoortDocument("Geboorte akte", "Geboorte akte"),
                PARTIJ_AMSTERDAM,
                20100101,
                GEBOORTE_AKTE,
                null,
                null,
                null,
                null,
                20100101,
                20100101);
        BrpIstTestUtils.vulStapelVoorkomenStandaardGroepModel(
                stapelVoorkomenCategorie11,
                new SoortDocument("Geboorte akte", "Geboorte akte"),
                PARTIJ_AMSTERDAM,
                20100101,
                GEBOORTE_AKTE,
                null,
                null,
                null,
                null,
                20100101,
                20100101);

        BrpIstTestUtils.vulStapelVoorkomenCategorieGezagsverhoudingGroepModel(stapelVoorkomenCategorie2, true, true, false, false);
        BrpIstTestUtils.vulStapelVoorkomenCategorieGezagsverhoudingGroepModel(stapelVoorkomenCategorie3, true, true, false, false);
        BrpIstTestUtils.vulStapelVoorkomenCategorieGezagsverhoudingGroepModel(stapelVoorkomenCategorie11, true, true, false, false);

        BrpIstTestUtils.vulStapelVoorkomenCategorieOudersGroepModel(stapelVoorkomenCategorie2, 20100101);
        BrpIstTestUtils.vulStapelVoorkomenCategorieOudersGroepModel(stapelVoorkomenCategorie3, 20100101);
        // BrpIstTestUtils.vulStapelVoorkomenCategorieOudersGroepModel(stapelVoorkomenCategorie5, 20100101);
        // BrpIstTestUtils.vulStapelVoorkomenCategorieOudersGroepModel(stapelVoorkomenCategorie9, 20100101);

        BrpIstTestUtils.vulStapelVoorkomenCategorieGerelateerdenGroepModel(
                stapelVoorkomenCategorie2,
                "a3523432",
                "2563463632",
                "556735635",
                "Hendrik Jan",
                "van",
                "Heuvel tot Veld",
                19680405,
                Predicaat.J,
                AdellijkeTitel.B,
                GEMEENTE_BREDA,
                LAND_GEBIED_NEDERLAND,
                Geslachtsaanduiding.MAN);
        BrpIstTestUtils.vulStapelVoorkomenCategorieGerelateerdenGroepModel(
                stapelVoorkomenCategorie3,
                "a3523432",
                "2563463632",
                "556735635",
                "Hendrik Jan",
                "van",
                "Heuvel tot Veld",
                19680405,
                Predicaat.J,
                AdellijkeTitel.B,
                GEMEENTE_BREDA,
                LAND_GEBIED_NEDERLAND,
                Geslachtsaanduiding.MAN);
        BrpIstTestUtils.vulStapelVoorkomenCategorieGerelateerdenGroepModel(
                stapelVoorkomenCategorie5,
                "a3523432",
                "2563463632",
                "556735635",
                "Hendrik Jan",
                "van",
                "Heuvel tot Veld",
                19680405,
                Predicaat.J,
                AdellijkeTitel.B,
                GEMEENTE_BREDA,
                LAND_GEBIED_NEDERLAND,
                Geslachtsaanduiding.MAN);
        BrpIstTestUtils.vulStapelVoorkomenCategorieGerelateerdenGroepModel(
                stapelVoorkomenCategorie9,
                "a3523432",
                "2563463632",
                "556735635",
                "Hendrik Jan",
                "van",
                "Heuvel tot Veld",
                19680405,
                Predicaat.J,
                AdellijkeTitel.B,
                GEMEENTE_BREDA,
                LAND_GEBIED_NEDERLAND,
                Geslachtsaanduiding.MAN);
        // BrpIstTestUtils.vulStapelVoorkomenCategorieGerelateerdenGroepModel(stapelVoorkomenCategorie11,
        // "a3523432",
        // "2563463632",
        // 556735635,
        // "Hendrik Jan",
        // "van",
        // "Heuvel tot Veld",
        // 19680405,
        // Predicaat.J,
        // AdellijkeTitel.B,
        // new Gemeente((short)758, "Breda", (short)758, new Partij("Gemeente Breda", 758)),
        // new LandOfGebied((short)6030, "Nederland"),
        // Geslachtsaanduiding.MAN);

        BrpIstTestUtils.vulStapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel(
                stapelVoorkomenCategorie5,
                20120604,
                null,
                GEMEENTE_DEN_HAAG,
                LAND_GEBIED_NEDERLAND,
                null,
                null,
                null,
                SoortRelatie.HUWELIJK);

        istStapel.add(stapelCategorie2);
        istStapel.add(stapelCategorie3);
        istStapel.add(stapelCategorie5);
        istStapel.add(stapelCategorie9);
        istStapel.add(stapelCategorie11);

        return istStapel;
    }
}
