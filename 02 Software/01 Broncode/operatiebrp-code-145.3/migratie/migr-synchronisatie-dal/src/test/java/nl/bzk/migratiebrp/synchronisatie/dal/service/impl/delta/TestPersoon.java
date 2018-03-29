/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import java.sql.Timestamp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3AanduidingOuder;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AanduidingOuder;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;

/**
 * Test persoon die {@link Persoon} extend en extra functionaliteit bevat voor de vershillende unit-tests.
 */
public class TestPersoon extends Persoon {

    private static final String OUDER1_CATEGORIE = "02";
    private static final String OUDER2_CATEGORIE = "03";
    private static final String HUWELIJK_OF_GP_CATEGORIE = "05";
    private static final String GEZAGSVERHOUDING_CATEGORIE = "11";
    private static final Timestamp TIJDSTIP = Timestamp.valueOf("1990-01-01 00:00:00.0");

    private final SoortAdministratieveHandeling soortAdministratieveHandeling;

    private Betrokkenheid ikBetrokkenheidOuders;
    private Betrokkenheid ouder1;
    private Betrokkenheid ouder2;
    private Relatie actueleHuwelijkOfGp;

    /**
     * Constructor voor een standaard BRP Persoon met alleen de, volgens het BMR, verplichte groepen met standaard
     * waarde voor de verplichte velden.
     * @param soortPersoon Geeft aan wat voor soort persoon dit is.
     */
    public TestPersoon(final SoortPersoon soortPersoon) {
        this(soortPersoon, TIJDSTIP, "Vermeer", 1L, SoortAdministratieveHandeling.GBA_INITIELE_VULLING);
    }

    /**
     * Constructor voor een standaard BRP Persoon met allen de, volgens BMR, verplichte groepen waarbij een aantal
     * velden instelbaar zijn.
     * @param soortPersoon Geeft aan wat voor soort persoon dit is.
     * @param datumTijdLaatsteWijziging de datum/tijd van de laatste wijziging
     * @param geslachtsnaamstam de geslachtsnaam van deze persoon
     * @param versienummer versienummer van deze persoonslijst
     */
    public TestPersoon(
            final SoortPersoon soortPersoon,
            final Timestamp datumTijdLaatsteWijziging,
            final String geslachtsnaamstam,
            final Long versienummer,
            final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        this.soortAdministratieveHandeling = soortAdministratieveHandeling;
        setSoortPersoon(soortPersoon);
        voegSamengesteldeNaamToe(geslachtsnaamstam);
        voegAfgeleidAdministratiefToe(datumTijdLaatsteWijziging);

        setAdministratieveHandeling(maakAdministratieveHandeling(soortAdministratieveHandeling, datumTijdLaatsteWijziging));
    }

    private void voegSamengesteldeNaamToe(final String geslachtsnaamstam) {
        final PersoonSamengesteldeNaamHistorie historie = new PersoonSamengesteldeNaamHistorie(this, geslachtsnaamstam, false, false);
        addPersoonSamengesteldeNaamHistorie(historie);
    }

    /**
     * Voegt een nieuw afgeleid administratief historie toe aan de persoon.
     * @param datumTijdLaatsteWijziging de datum/tijd van de laatste wijziging.
     */
    public void voegAfgeleidAdministratiefToe(final Timestamp datumTijdLaatsteWijziging) {

        final PersoonAfgeleidAdministratiefHistorie historie =
                new PersoonAfgeleidAdministratiefHistorie(
                        (short) 0,
                        this,
                        maakAdministratieveHandeling(SoortAdministratieveHandeling.GBA_INITIELE_VULLING, datumTijdLaatsteWijziging),
                        datumTijdLaatsteWijziging);
        addPersoonAfgeleidAdministratiefHistorie(historie);
    }

    private AdministratieveHandeling maakAdministratieveHandeling(
            final SoortAdministratieveHandeling soortAdministratieveHandeling,
            final Timestamp datumTijdLaatsteWijziging) {
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(maakPartij(), soortAdministratieveHandeling, datumTijdLaatsteWijziging);
        return administratieveHandeling;
    }

    /**
     * Maakt een partij voor de gemeente 's-Gravenhage (0518).
     * @return een partij voor de gemeente 's-Gravenhage(0518)
     */
    private Partij maakPartij() {
        return new Partij("Gemeente 's-Gravenhage", "051801");
    }

    private Gemeente maakGemeente(final Partij partij) {
        return new Gemeente((short) 1, "'s-Gravenhage", "0518", partij);
    }

    private LandOfGebied maakLandOfGebied() {
        return new LandOfGebied("0001", "één");
    }

    /**
     * Voegt een ouder toe aan de persoonlijst. Afhankelijk van de parameter isOuder1 wordt dit gedaan voor ouder 1
     * (cat02) of ouder 2 (cat03).
     * @param isOuder1 true als het ouder 1 betreft, false als het ouder 2 betreft
     */
    public void voegOuderToe(final boolean isOuder1) {
        controleerOfOuderBestaat(isOuder1);

        final Relatie relatie = getOuderRelatie();

        final String categorie;
        final AanduidingOuder aanduidingOuder;
        if (isOuder1) {
            categorie = OUDER1_CATEGORIE;
            aanduidingOuder = AanduidingOuder.OUDER_1;
        } else {
            categorie = OUDER2_CATEGORIE;
            aanduidingOuder = AanduidingOuder.OUDER_2;
        }

        final Stapel istOuder1Stapel = new Stapel(this, categorie, 0);
        addStapel(istOuder1Stapel);
        relatie.addStapel(istOuder1Stapel);

        voegIstStapelVoorkomenToe(istOuder1Stapel, true, 0);

        final Persoon ouder1Gerelateerde =
                new TestPersoon(
                        SoortPersoon.PSEUDO_PERSOON,
                        Timestamp.valueOf("1990-01-01 00:00:00.0"),
                        "Kleinjan",
                        1L,
                        soortAdministratieveHandeling);
        final Betrokkenheid gerelateerdeBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        final BetrokkenheidOuderHistorie ouderHistorie = new BetrokkenheidOuderHistorie(gerelateerdeBetrokkenheid);

        gerelateerdeBetrokkenheid.addBetrokkenheidOuderHistorie(ouderHistorie);
        ouder1Gerelateerde.addBetrokkenheid(gerelateerdeBetrokkenheid);
        relatie.addBetrokkenheid(gerelateerdeBetrokkenheid);

        gerelateerdeBetrokkenheid.setAanduidingOuder(new Lo3AanduidingOuder(aanduidingOuder, gerelateerdeBetrokkenheid));

        if (isOuder1) {
            ouder1 = gerelateerdeBetrokkenheid;
        } else {
            ouder2 = gerelateerdeBetrokkenheid;
        }
    }

    private void controleerOfOuderBestaat(final boolean isOuder1) {
        if (isOuder1 && ouder1 != null || !isOuder1 && ouder2 != null) {
            throw new IllegalStateException("Er bestaat al een ouder1 voor deze persoonslijst.");
        }
    }

    /**
     * Voegt een puntouder toe aan de persoonlijst. Afhankelijk van de parameter isOuder1 wordt dit gedaan voor ouder 1
     * (cat02) of ouder 2 (cat03).
     * @param isOuder1 true als het ouder 1 betreft, false als het ouder 2 betreft
     */
    public void voegPuntouderToe(final boolean isOuder1) {
        controleerOfOuderBestaat(isOuder1);

        final Relatie relatie = getOuderRelatie();

        final String categorie;
        final AanduidingOuder aanduidingOuder;
        if (isOuder1) {
            categorie = OUDER1_CATEGORIE;
            aanduidingOuder = AanduidingOuder.OUDER_1;
        } else {
            categorie = OUDER2_CATEGORIE;
            aanduidingOuder = AanduidingOuder.OUDER_2;
        }

        final Stapel istOuder1Stapel = new Stapel(this, categorie, 0);
        addStapel(istOuder1Stapel);
        relatie.addStapel(istOuder1Stapel);

        voegIstStapelVoorkomenToe(istOuder1Stapel, true, 0);

        final Betrokkenheid gerelateerdeBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        final BetrokkenheidOuderHistorie ouderHistorie = new BetrokkenheidOuderHistorie(gerelateerdeBetrokkenheid);

        gerelateerdeBetrokkenheid.addBetrokkenheidOuderHistorie(ouderHistorie);
        relatie.addBetrokkenheid(gerelateerdeBetrokkenheid);

        gerelateerdeBetrokkenheid.setAanduidingOuder(new Lo3AanduidingOuder(aanduidingOuder, gerelateerdeBetrokkenheid));

        if (isOuder1) {
            ouder1 = gerelateerdeBetrokkenheid;
        } else {
            ouder2 = gerelateerdeBetrokkenheid;
        }
    }

    /**
     * Voegt een "juridisch geen ouder" toe aan de persoonlijst. Afhankelijk van de parameter isOuder1 wordt dit gedaan
     * voor ouder 1 (cat02) of ouder 2 (cat03).
     * @param isOuder1 true als het ouder 1 betreft, false als het ouder 2 betreft
     */
    public void voegJuridischGeenOuderToe(final boolean isOuder1) {
        controleerOfOuderBestaat(isOuder1);

        final Relatie relatie = getOuderRelatie();

        final String categorie = isOuder1 ? OUDER1_CATEGORIE : OUDER2_CATEGORIE;

        final Stapel istOuder1Stapel = new Stapel(this, categorie, 0);
        addStapel(istOuder1Stapel);
        relatie.addStapel(istOuder1Stapel);

        voegIstStapelVoorkomenToe(istOuder1Stapel, true, 0);
    }

    /**
     * Voegt een huwelijk toe aan de persoonslijst.
     */
    public void voegHuwelijkToe(final Integer datumAanvang, final Integer datumEinde) {
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        relatie.setDatumAanvang(datumAanvang);
        relatie.setDatumEinde(datumEinde);

        final RelatieHistorie historie = new RelatieHistorie(relatie);
        historie.setDatumAanvang(datumAanvang);
        historie.setDatumEinde(datumEinde);

        final Betrokkenheid ikBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        relatie.addBetrokkenheid(ikBetrokkenheid);

        final Betrokkenheid gerelateerdeBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        relatie.addBetrokkenheid(gerelateerdeBetrokkenheid);

        final Persoon gerelateerdePersoon =
                new TestPersoon(SoortPersoon.PSEUDO_PERSOON, Timestamp.valueOf("2005-03-30 00:00:00.0"), "Harvey", 1L, soortAdministratieveHandeling);
        gerelateerdePersoon.addBetrokkenheid(gerelateerdeBetrokkenheid);

        final Stapel istHuwelijkStapel = new Stapel(this, HUWELIJK_OF_GP_CATEGORIE, 0);
        addStapel(istHuwelijkStapel);
        relatie.addStapel(istHuwelijkStapel);

        voegIstStapelVoorkomenToe(istHuwelijkStapel, false, 0);

        addBetrokkenheid(ikBetrokkenheid);
        actueleHuwelijkOfGp = relatie;
    }

    public void voegGezagsverhoudingToe() {
        final Stapel istGezagsverhoudingStapel = new Stapel(this, GEZAGSVERHOUDING_CATEGORIE, 0);
        addStapel(istGezagsverhoudingStapel);
        getOuderRelatie().addStapel(istGezagsverhoudingStapel);

        final StapelVoorkomen voorkomen =
                new StapelVoorkomen(istGezagsverhoudingStapel, 0, maakAdministratieveHandeling(soortAdministratieveHandeling, TIJDSTIP));
        voorkomen.setIndicatieDerdeHeeftGezag(true);
        voorkomen.setIndicatieOnderCuratele(true);
        voorkomen.setIndicatieOuder1HeeftGezag(true);
        voorkomen.setIndicatieOuder2HeeftGezag(true);
        istGezagsverhoudingStapel.addStapelVoorkomen(voorkomen);
    }

    private Relatie getOuderRelatie() {
        final Relatie relatie;
        if (ikBetrokkenheidOuders == null) {
            relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
            ikBetrokkenheidOuders = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);
            final BetrokkenheidHistorie ikBetrokkenheidHistorie = new BetrokkenheidHistorie(ikBetrokkenheidOuders);
            ikBetrokkenheidOuders.addBetrokkenheidHistorie(ikBetrokkenheidHistorie);
            relatie.addBetrokkenheid(ikBetrokkenheidOuders);

            addBetrokkenheid(ikBetrokkenheidOuders);
        } else {
            relatie = ikBetrokkenheidOuders.getRelatie();
        }
        return relatie;
    }

    private void voegIstStapelVoorkomenToe(final Stapel stapel, final boolean isMan, final int volgnr) {
        voegIstStapelVoorkomenToe(stapel, isMan, volgnr, false);
    }

    private void voegIstStapelVoorkomenToe(final Stapel stapel, final boolean isMan, final int volgnr, final boolean isOnjuist) {
        final Partij partij = maakPartij();
        final Gemeente gemeente = maakGemeente(partij);
        final LandOfGebied landOfGebied = maakLandOfGebied();

        final StapelVoorkomen voorkomen = new StapelVoorkomen(stapel, volgnr, maakAdministratieveHandeling(soortAdministratieveHandeling, TIJDSTIP));

        if (isMan) {
            voorkomen.setAnummer("1234567890");
            voorkomen.setBsn("987654321");
            voorkomen.setAktenummer("A1 234");
            voorkomen.setDatumGeboorte(20150101);
            voorkomen.setGemeenteGeboorte(gemeente);
            voorkomen.setGeslachtsaanduiding(Geslachtsaanduiding.MAN);
            voorkomen.setGeslachtsnaamstam("Puk");
            voorkomen.setLandOfGebiedGeboorte(landOfGebied);
            voorkomen.setPartij(partij);
            voorkomen.setRubriek8510IngangsdatumGeldigheid(20120101);
            voorkomen.setRubriek8610DatumVanOpneming(20120102);
            voorkomen.setVoornamen("Pietje");
        } else {
            voorkomen.setAnummer("1236547890");
            voorkomen.setBsn("789654123");
            voorkomen.setAktenummer("A1 789");
            voorkomen.setDatumGeboorte(20120101);
            voorkomen.setGemeenteGeboorte(gemeente);
            voorkomen.setGeslachtsaanduiding(Geslachtsaanduiding.VROUW);
            voorkomen.setGeslachtsnaamstam("Puk");
            voorkomen.setLandOfGebiedGeboorte(landOfGebied);
            voorkomen.setPartij(partij);
            voorkomen.setRubriek8510IngangsdatumGeldigheid(20120101);
            voorkomen.setRubriek8610DatumVanOpneming(20120102);
            voorkomen.setVoornamen("Plien");
        }

        if (isOnjuist) {
            voorkomen.setRubriek8410OnjuistOfStrijdigOpenbareOrde('O');
        }
        stapel.addStapelVoorkomen(voorkomen);
    }
}
