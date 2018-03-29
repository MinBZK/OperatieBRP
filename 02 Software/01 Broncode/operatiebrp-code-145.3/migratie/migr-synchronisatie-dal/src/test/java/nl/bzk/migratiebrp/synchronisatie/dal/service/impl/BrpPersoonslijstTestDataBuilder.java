/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentiteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;

/**
 * Naamgeving ontleent aan @link{http://c2.com/cgi/wiki?TestDataBuilder}
 *
 * <h3>Default gebruik:</h3>
 *
 * <pre>
 * new BrpPersoonslijstTestDataBuilder().addDefaultTestStapels().build();
 * </pre>
 *
 * <h3>Extra stapel toevoegen:</h3>
 *
 * <pre>
 * new BrpPersoonslijstTestDataBuilder().addDefaultTestStapels()
 *                                      .addGroepMetHistorieA(new BrpNationaliteitInhoud(new BrpNationaliteitCode(&quot;1&quot;), null, null))
 *                                      .build();
 * </pre>
 */
public class BrpPersoonslijstTestDataBuilder {

    static final BrpString DEFAULT_ADMINISTRATIENUMMER = new BrpString("1234567890", null);
    private static final BrpString DEFAULT_BURGERSERVICENUMMER = new BrpString("987654321", null);
    private static final BrpGeslachtsaanduidingCode DEFAULT_GESLACHT = BrpGeslachtsaanduidingCode.MAN;
    private static final String LAND_OF_GEBIED_6030 = "6030";
    private static long actieIdTeller;

    private final List<BrpStapel<?>> brpStapels = new ArrayList<>();
    private final List<BrpRelatie> brpRelaties = new ArrayList<>();
    private final List<BrpStapel<BrpIstHuwelijkOfGpGroepInhoud>> istHuwelijkOfGpStapels = new ArrayList<>();
    private final List<BrpStapel<BrpIstRelatieGroepInhoud>> istKindStapels = new ArrayList<>();
    private BrpStapel<BrpIstRelatieGroepInhoud> istOuder1Stapel;
    private BrpStapel<BrpIstRelatieGroepInhoud> istOuder2Stapel;
    private BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsverhoudingStapel;

    BrpPersoonslijstTestDataBuilder() {
    }

    BrpPersoonslijstTestDataBuilder addDefaultTestStapels() {
        addGroepMetHistorieA(new BrpIdentificatienummersInhoud(DEFAULT_ADMINISTRATIENUMMER, DEFAULT_BURGERSERVICENUMMER));
        addGroepMetHistorieA(new BrpBijhoudingInhoud(new BrpPartijCode("003401"), BrpBijhoudingsaardCode.INGEZETENE, BrpNadereBijhoudingsaardCode.ACTUEEL));
        addGroepMetHistorieA(
                new BrpAdresInhoud(
                        BrpSoortAdresCode.W,
                        new BrpRedenWijzigingVerblijfCode('P'),
                        null,
                        new BrpDatum(20110101, null),
                        null, null, new BrpGemeenteCode("0626", null),
                        null, null, null, null, null, null, null,
                        null, null, null, null, null, null,
                        null, null, null, new BrpLandOfGebiedCode(LandOfGebied.CODE_NEDERLAND, null), null));
        addGroepMetHistorieB(new BrpGeslachtsaanduidingInhoud(DEFAULT_GESLACHT));
        addGroepMetHistorieC(new BrpInschrijvingInhoud(new BrpDatum(20110101, null), new BrpLong(0L, null), BrpDatumTijd.fromDatum(20130101, null)));
        addGroepMetHistorieD(
                new BrpSamengesteldeNaamInhoud(
                        null,
                        new BrpString("Noam", null),
                        new BrpString("D", null),
                        new BrpCharacter('-', null),
                        null,
                        new BrpString("Chomsky", null),
                        new BrpBoolean(false, null),
                        new BrpBoolean(true, null)));
        return this;
    }

    public BrpPersoonslijst build() {
        return new BrpPersoonslijstBuilderVisitor(
                new BrpPersoonslijstBuilder().relaties(brpRelaties)
                        .istHuwelijkOfGpStapels(istHuwelijkOfGpStapels)
                        .istKindStapels(istKindStapels)
                        .istOuder1Stapel(istOuder1Stapel)
                        .istOuder2Stapel(istOuder2Stapel)
                        .istGezagsverhoudingStapel(istGezagsverhoudingStapel)).addStapels(brpStapels).build();
    }

    private <T extends BrpGroepInhoud> BrpStapel<T> createStapel(final BrpGroep<T> groep) {
        return new BrpStapel<>(Collections.singletonList(groep));
    }

    /**
     * maakt een nieuwe stapel aan met de meegegeven inhoud, met default historiepatroon A.
     * @param inhoud Een specifiek BrpGroepInhoud object.
     * @return Deze TestDataBuilder
     */
    <T extends BrpGroepInhoud> BrpPersoonslijstTestDataBuilder addGroepMetHistorieA(final T inhoud) {
        brpStapels.add(createStapel(createGroepMetHistorieA(inhoud)));
        return this;
    }

    /**
     * maakt een nieuwe stapel aan met de meegegeven inhoud, met default historiepatroon B.
     * @param inhoud Een specifiek BrpGroepInhoud object.
     * @return Deze TestDataBuilder
     */
    private <T extends BrpGroepInhoud> BrpPersoonslijstTestDataBuilder addGroepMetHistorieB(final T inhoud) {
        brpStapels.add(createStapel(createGroepMetHistorieB(inhoud)));
        return this;
    }


    /**
     * maakt een nieuwe stapel aan met de meegegeven inhoud, met default historiepatroon C.
     * @param inhoud Een specifiek BrpGroepInhoud object.
     * @return Deze TestDataBuilder
     */
    private <T extends BrpGroepInhoud> BrpPersoonslijstTestDataBuilder addGroepMetHistorieC(final T inhoud) {
        brpStapels.add(createStapel(createGroepMetHistorieC(inhoud)));
        return this;
    }

    /**
     * maakt een nieuwe stapel aan met de meegegeven inhoud, met default historiepatroon D.
     * @param inhoud Een specifiek BrpGroepInhoud object.
     * @return Deze TestDataBuilder
     */
    private <T extends BrpGroepInhoud> BrpPersoonslijstTestDataBuilder addGroepMetHistorieD(final T inhoud) {
        brpStapels.add(createStapel(createGroepMetHistorieD(inhoud)));
        return this;
    }

    /**
     * maakt een nieuwe stapel aan met de meegegeven inhoud, met default historiepatroon E en actie en documenten.
     * @param inhoud Een specifiek BrpGroepInhoud object.
     * @return Deze TestDataBuilder
     */
    public <T extends BrpGroepInhoud> BrpPersoonslijstTestDataBuilder addGroepMetHistorieE(final T inhoud) {
        brpStapels.add(createStapel(createGroepMetHistorieE(inhoud)));
        return this;
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> createGroepMetHistorieA(final T inhoud) {
        return createGroepMetHistorieEnActies(inhoud, createBrpHistorie(20110101, null, 20110103, null));
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> createGroepMetHistorieB(final T inhoud) {
        return createGroepMetHistorieEnActies(inhoud, createBrpHistorie(20120101, 20120102, 20120103, null));
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> createGroepMetHistorieC(final T inhoud) {
        return createGroepMetHistorieEnActies(inhoud, createBrpHistorie(20130101, 20130102, 20130103, 20130104));
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> createGroepMetHistorieD(final T inhoud) {
        return createGroepMetHistorieEnActies(inhoud, createBrpHistorie(20140101, 20140102, 20140103, 20140104));
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> createGroepMetHistorieE(final T inhoud) {
        return createGroepMetHistorieEnActies(inhoud, createBrpHistorie(20140101, 20140102, 20140103, 20140104));
    }

    private BrpHistorie createBrpHistorie(final Integer datumAanvang, final Integer datumEinde, final Integer registratieTijd, final Integer vervalTijd) {
        return new BrpHistorie(
                new BrpDatum(datumAanvang, null),
                datumEinde == null ? null : new BrpDatum(datumEinde, null),
                BrpDatumTijd.fromDatum(registratieTijd, null),
                vervalTijd == null ? null : BrpDatumTijd.fromDatum(vervalTijd, null),
                null);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> createGroepMetHistorieEnActies(final T inhoud, final BrpHistorie brpHistorie) {
        final List<BrpActieBron> documentStapels = new ArrayList<>();
        final BrpDocumentInhoud brpDocumentInhoud1His1 =
                new BrpDocumentInhoud(
                        BrpSoortDocumentCode.HISTORIE_CONVERSIE,
                        null,
                        new BrpString("D 1", null),
                        new BrpPartijCode("003401"));
        final BrpDocumentInhoud brpDocumentInhoud1His2 =
                new BrpDocumentInhoud(
                        BrpSoortDocumentCode.HISTORIE_CONVERSIE,
                        null,
                        new BrpString("D 1.1", null),
                        new BrpPartijCode("003401"));
        final List<BrpGroep<BrpDocumentInhoud>> brpDocumentInhoud1List = new ArrayList<>();
        brpDocumentInhoud1List.add(
                new BrpGroep<>(
                        brpDocumentInhoud1His1,
                        new BrpHistorie(
                                brpHistorie.getDatumAanvangGeldigheid(),
                                null,
                                BrpDatumTijd.fromDatum(20120203, null),
                                BrpDatumTijd.fromDatum(20120303, null),
                                null),
                        null,
                        null,
                        null));
        brpDocumentInhoud1List.add(
                new BrpGroep<>(
                        brpDocumentInhoud1His2,
                        new BrpHistorie(brpHistorie.getDatumAanvangGeldigheid(), null, BrpDatumTijd.fromDatum(20120303, null), null, null),
                        null,
                        null,
                        null));
        documentStapels.add(new BrpActieBron(new BrpStapel<>(brpDocumentInhoud1List), null));

        final BrpSoortDocumentCode akte = new BrpSoortDocumentCode("Geboorteakte");
        final BrpDocumentInhoud brpDocumentInhoud2His1 =
                new BrpDocumentInhoud(akte, new BrpString("1 A", null), null, new BrpPartijCode("003401"));
        final BrpDocumentInhoud brpDocumentInhoud2His2 =
                new BrpDocumentInhoud(akte, new BrpString("1 A.1", null), null, new BrpPartijCode("003401"));
        final List<BrpGroep<BrpDocumentInhoud>> brpDocumentInhoud2List = new ArrayList<>();
        brpDocumentInhoud2List.add(
                new BrpGroep<>(
                        brpDocumentInhoud2His1,
                        new BrpHistorie(
                                brpHistorie.getDatumAanvangGeldigheid(),
                                null,
                                BrpDatumTijd.fromDatum(20120203, null),
                                BrpDatumTijd.fromDatum(20120303, null),
                                null),
                        null,
                        null,
                        null));
        brpDocumentInhoud2List.add(
                new BrpGroep<>(
                        brpDocumentInhoud2His2,
                        new BrpHistorie(brpHistorie.getDatumAanvangGeldigheid(), null, BrpDatumTijd.fromDatum(20120303, null), null, null),
                        null,
                        null,
                        null));
        documentStapels.add(new BrpActieBron(new BrpStapel<>(brpDocumentInhoud2List), null));

        final Lo3Herkomst dummyLo3Herkomst1 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        final Lo3Herkomst dummyLo3Herkomst2 = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0);
        final BrpActie actieInhoud =
                new BrpActie(
                        actieIdTeller++,
                        BrpSoortActieCode.CONVERSIE_GBA,
                        new BrpPartijCode("003401"),
                        brpHistorie.getDatumTijdRegistratie(),
                        null,
                        documentStapels,
                        0,
                        dummyLo3Herkomst1);
        BrpActie actieVerval = null;
        if (brpHistorie.getDatumTijdVerval() != null) {
            actieVerval =
                    new BrpActie(
                            actieIdTeller++,
                            BrpSoortActieCode.CONVERSIE_GBA,
                            new BrpPartijCode("003401"),
                            brpHistorie.getDatumTijdRegistratie(),
                            null,
                            documentStapels,
                            0,
                            dummyLo3Herkomst1);
        }
        final BrpActie actieGeldigheid =
                new BrpActie(
                        actieIdTeller++,
                        BrpSoortActieCode.CONVERSIE_GBA,
                        new BrpPartijCode("003401"),
                        brpHistorie.getDatumTijdRegistratie(),
                        null,
                        documentStapels,
                        0,
                        dummyLo3Herkomst2);

        return new BrpGroep<>(inhoud, brpHistorie, actieInhoud, actieVerval, actieGeldigheid);
    }

    public BrpPersoonslijstTestDataBuilder addKindRelatie() {
        final BrpSoortBetrokkenheidCode rol = BrpSoortBetrokkenheidCode.KIND;
        final BrpString administratienummer = new BrpString("999654321", null);
        final BrpString bsn = new BrpString("123456999", null);
        final BrpGeslachtsaanduidingCode geslachtsaanduidingCode = BrpGeslachtsaanduidingCode.MAN;
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel =
                createStapel(createGroepMetHistorieA(new BrpIdentificatienummersInhoud(administratienummer, bsn)));
        final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel =
                createStapel(createGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(geslachtsaanduidingCode)));
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel =
                createStapel(
                        createGroepMetHistorieA(new BrpGeboorteInhoud(
                                new BrpDatum(19800101, null),
                                new BrpGemeenteCode("1900"),
                                null,
                                null,
                                null,
                                new BrpLandOfGebiedCode(LAND_OF_GEBIED_6030),
                                null)));
        final BrpStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel =
                createStapel(createGroepMetHistorieA(new BrpOuderlijkGezagInhoud(new BrpBoolean(true, null))));
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel =
                createStapel(
                        createGroepMetHistorieA(new BrpSamengesteldeNaamInhoud(
                                null,
                                new BrpString("Piet", null),
                                new BrpString("van", null),
                                new BrpCharacter(' ', null),
                                null,
                                new BrpString("Klaassen", null),
                                new BrpBoolean(false, null),
                                new BrpBoolean(false, null))));
        final BrpStapel<BrpOuderInhoud> ouderStapel = createStapel(createGroepMetHistorieA(new BrpOuderInhoud(null)));

        final BrpBetrokkenheid kindBetrokkenheid =
                new BrpBetrokkenheid(
                        rol,
                        identificatienummersStapel,
                        geslachtsaanduidingStapel,
                        geboorteStapel,
                        ouderlijkGezagStapel,
                        samengesteldeNaamStapel,
                        ouderStapel,
                        null);

        final BrpStapel<BrpRelatieInhoud> relatieStapel =
                createStapel(
                        createGroepMetHistorieA(new BrpRelatieInhoud(
                                new BrpDatum(19800101, null),
                                new BrpGemeenteCode("1900"),
                                null,
                                null,
                                null,
                                new BrpLandOfGebiedCode(LAND_OF_GEBIED_6030),
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null)));

        final BrpIstStandaardGroepInhoud istStandaardGroepInhoud =
                new BrpIstStandaardGroepInhoud.Builder(Lo3CategorieEnum.CATEGORIE_09, istKindStapels.size(), 0).build();
        final BrpGroep<BrpIstRelatieGroepInhoud> istKindGroep =
                createGroepMetHistorieA(
                        new BrpIstRelatieGroepInhoud.Builder(istStandaardGroepInhoud).anummer(administratienummer)
                                .bsn(new BrpString("123456999", null))
                                .geslachtsaanduidingCode(geslachtsaanduidingCode)
                                .build());

        final BrpStapel<BrpIstRelatieGroepInhoud> istKindStapel = createStapel(istKindGroep);

        final BrpRelatie.Builder relatieBuilder =
                new BrpRelatie.Builder(
                        null,
                        BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                        BrpSoortBetrokkenheidCode.OUDER,
                        new LinkedHashMap<>());
        relatieBuilder.betrokkenheden(Collections.singletonList(kindBetrokkenheid));
        relatieBuilder.relatieStapel(relatieStapel);
        relatieBuilder.istKindStapel(istKindStapel);
        brpRelaties.add(relatieBuilder.build());
        istKindStapels.add(istKindStapel);
        return this;
    }

    public BrpPersoonslijstTestDataBuilder addHuwelijkRelatie(
            final BrpString aNummerPartner,
            final BrpString bsnPartner,
            final BrpGeslachtsaanduidingCode geslachtPartner,
            final BrpGeboorteInhoud geboortePartner,
            final BrpSamengesteldeNaamInhoud naamPartner,
            final BrpDatum trouwDatum,
            final BrpGemeenteCode trouwGemeente,
            final boolean laatRelatieVervallen) {
        final BrpSoortBetrokkenheidCode rol = BrpSoortBetrokkenheidCode.PARTNER;
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel =
                createStapel(createGroepMetHistorieA(new BrpIdentificatienummersInhoud(aNummerPartner, bsnPartner)));
        final BrpStapel<BrpIdentiteitInhoud> identiteitStapel = createStapel(createGroepMetHistorieA(BrpIdentiteitInhoud.IDENTITEIT));
        final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel =
                createStapel(createGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(geslachtPartner)));
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel = createStapel(createGroepMetHistorieA(geboortePartner));
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel = createStapel(createGroepMetHistorieA(naamPartner));

        final BrpBetrokkenheid partnerBetrokkenheid =
                new BrpBetrokkenheid(
                        rol,
                        identificatienummersStapel,
                        geslachtsaanduidingStapel,
                        geboorteStapel,
                        null,
                        samengesteldeNaamStapel,
                        null,
                        identiteitStapel);

        final BrpGroep<BrpRelatieInhoud> relatieGroep;
        if (laatRelatieVervallen) {
            relatieGroep =
                    createGroepMetHistorieC(
                            new BrpRelatieInhoud(
                                    trouwDatum,
                                    trouwGemeente,
                                    null,
                                    null,
                                    null,
                                    new BrpLandOfGebiedCode(LAND_OF_GEBIED_6030),
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null));
        } else {
            relatieGroep =
                    createGroepMetHistorieA(
                            new BrpRelatieInhoud(
                                    trouwDatum,
                                    trouwGemeente,
                                    null,
                                    null,
                                    null,
                                    new BrpLandOfGebiedCode(LAND_OF_GEBIED_6030),
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null));
        }

        final BrpStapel<BrpRelatieInhoud> relatieStapel = createStapel(relatieGroep);

        final BrpIstStandaardGroepInhoud istStandaardGroepInhoud =
                new BrpIstStandaardGroepInhoud.Builder(Lo3CategorieEnum.CATEGORIE_05, istHuwelijkOfGpStapels.size(), 0).build();
        final BrpIstRelatieGroepInhoud istRelatieGroepInhoud =
                new BrpIstRelatieGroepInhoud.Builder(istStandaardGroepInhoud).anummer(aNummerPartner)
                        .bsn(bsnPartner)
                        .geslachtsaanduidingCode(geslachtPartner)
                        .build();
        final BrpGroep<BrpIstHuwelijkOfGpGroepInhoud> istHuwelijkOfGpGroep =
                createGroepMetHistorieA(
                        new BrpIstHuwelijkOfGpGroepInhoud.Builder(istStandaardGroepInhoud, istRelatieGroepInhoud)
                                .datumAanvang(
                                        new BrpInteger(trouwDatum.getWaarde()))
                                .gemeenteCodeAanvang(trouwGemeente)
                                .soortRelatieCode(
                                        BrpSoortRelatieCode.HUWELIJK)
                                .build());
        final BrpStapel<BrpIstHuwelijkOfGpGroepInhoud> istHuwelijkOfGpStapel = createStapel(istHuwelijkOfGpGroep);

        final BrpRelatie.Builder relatieBuilder =
                new BrpRelatie.Builder(null, BrpSoortRelatieCode.HUWELIJK, BrpSoortBetrokkenheidCode.PARTNER, new LinkedHashMap<>());
        relatieBuilder.betrokkenheden(Collections.singletonList(partnerBetrokkenheid));
        relatieBuilder.relatieStapel(relatieStapel);
        relatieBuilder.istHuwelijkOfGpStapel(istHuwelijkOfGpStapel);

        brpRelaties.add(relatieBuilder.build());
        istHuwelijkOfGpStapels.add(istHuwelijkOfGpStapel);
        return this;
    }

    public BrpPersoonslijstTestDataBuilder addRelatieMetKind(
            final BrpString aNummerKind,
            final BrpString bsnKind,
            final BrpGeslachtsaanduidingCode geslachtKind,
            final BrpGeboorteInhoud geboorteKind,
            final BrpSamengesteldeNaamInhoud naamKind) {
        final BrpSoortBetrokkenheidCode rol = BrpSoortBetrokkenheidCode.KIND;
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel =
                createStapel(createGroepMetHistorieA(new BrpIdentificatienummersInhoud(aNummerKind, bsnKind)));
        final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel =
                createStapel(createGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(geslachtKind)));
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel = createStapel(createGroepMetHistorieA(geboorteKind));
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel = createStapel(createGroepMetHistorieA(naamKind));

        final BrpBetrokkenheid kindBetrokkenheid =
                new BrpBetrokkenheid(
                        rol,
                        identificatienummersStapel,
                        geslachtsaanduidingStapel,
                        geboorteStapel,
                        null,
                        samengesteldeNaamStapel,
                        null,
                        null);

        final BrpIstStandaardGroepInhoud istStandaardGroepInhoud =
                new BrpIstStandaardGroepInhoud.Builder(Lo3CategorieEnum.CATEGORIE_09, istKindStapels.size(), 0).build();
        final BrpGroep<BrpIstRelatieGroepInhoud> istKindGroep =
                createGroepMetHistorieA(
                        new BrpIstRelatieGroepInhoud.Builder(istStandaardGroepInhoud).anummer(aNummerKind)
                                .bsn(bsnKind)
                                .geslachtsaanduidingCode(geslachtKind)
                                .build());

        final BrpStapel<BrpIstRelatieGroepInhoud> istKindStapel = createStapel(istKindGroep);

        final BrpRelatie.Builder relatieBuilder =
                new BrpRelatie.Builder(
                        null,
                        BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                        BrpSoortBetrokkenheidCode.OUDER,
                        new LinkedHashMap<>());
        relatieBuilder.betrokkenheden(Collections.singletonList(kindBetrokkenheid));
        relatieBuilder.istKindStapel(istKindStapel);

        brpRelaties.add(relatieBuilder.build());
        istKindStapels.add(istKindStapel);
        return this;
    }

    public BrpPersoonslijstTestDataBuilder addRelatieMetOuders(
            final BrpString aNummerOuder1,
            final BrpString bsnOuder1,
            final BrpGeslachtsaanduidingCode geslachtOuder1,
            final BrpGeboorteInhoud geboorteOuder1,
            final BrpSamengesteldeNaamInhoud naamOuder1,
            final BrpString aNummerOuder2,
            final BrpString bsnOuder2,
            final BrpGeslachtsaanduidingCode geslachtOuder2,
            final BrpGeboorteInhoud geboorteOuder2,
            final BrpSamengesteldeNaamInhoud naamOuder2,
            final boolean ouder1HeeftOuderlijkGezag) {
        final BrpSoortBetrokkenheidCode rol = BrpSoortBetrokkenheidCode.OUDER;

        final List<BrpBetrokkenheid> betrokkenheidList = new ArrayList<>();
        // Ouder 1
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel1 =
                createStapel(createGroepMetHistorieA(new BrpIdentificatienummersInhoud(aNummerOuder1, bsnOuder1)));
        final BrpStapel<BrpIdentiteitInhoud> identiteitStapel = createStapel(createGroepMetHistorieA(BrpIdentiteitInhoud.IDENTITEIT));
        final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel1 =
                createStapel(createGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(geslachtOuder1)));
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel1 = createStapel(createGroepMetHistorieA(geboorteOuder1));
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel1 = createStapel(createGroepMetHistorieA(naamOuder1));

        final BrpIstStandaardGroepInhoud istStandaardGroepInhoud = new BrpIstStandaardGroepInhoud.Builder(Lo3CategorieEnum.CATEGORIE_02, 0, 0).build();
        final BrpGroep<BrpIstRelatieGroepInhoud> istOuder1Groep =
                createGroepMetHistorieA(
                        new BrpIstRelatieGroepInhoud.Builder(istStandaardGroepInhoud).anummer(aNummerOuder1)
                                .bsn(bsnOuder1)
                                .geslachtsaanduidingCode(geslachtOuder1)
                                .build());

        istOuder1Stapel = createStapel(istOuder1Groep);

        BrpStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel = null;
        if (ouder1HeeftOuderlijkGezag) {
            ouderlijkGezagStapel = createStapel(createGroepMetHistorieA(new BrpOuderlijkGezagInhoud(new BrpBoolean(true, null))));

            final BrpIstStandaardGroepInhoud istStandaardGezagGroepInhoud =
                    new BrpIstStandaardGroepInhoud.Builder(Lo3CategorieEnum.CATEGORIE_11, 0, 0).build();
            final BrpIstGezagsVerhoudingGroepInhoud istGezagsVerhoudingGroep =
                    new BrpIstGezagsVerhoudingGroepInhoud.Builder(istStandaardGezagGroepInhoud).indicatieOuder1HeeftGezag(new BrpBoolean(true, null))
                            .build();
            istGezagsverhoudingStapel = createStapel(createGroepMetHistorieA(istGezagsVerhoudingGroep));
        }

        betrokkenheidList.add(new BrpBetrokkenheid(
                rol,
                identificatienummersStapel1,
                geslachtsaanduidingStapel1,
                geboorteStapel1,
                ouderlijkGezagStapel,
                samengesteldeNaamStapel1,
                null,
                identiteitStapel));

        if (aNummerOuder2 != null) {
            // Ouder 2
            final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel2 =
                    createStapel(createGroepMetHistorieA(new BrpIdentificatienummersInhoud(aNummerOuder2, bsnOuder2)));
            final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel2 =
                    createStapel(createGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(geslachtOuder2)));
            final BrpStapel<BrpGeboorteInhoud> geboorteStapel2 = createStapel(createGroepMetHistorieA(geboorteOuder2));
            final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel2 = createStapel(createGroepMetHistorieA(naamOuder2));

            betrokkenheidList.add(new BrpBetrokkenheid(
                    rol,
                    identificatienummersStapel2,
                    geslachtsaanduidingStapel2,
                    geboorteStapel2,
                    null,
                    samengesteldeNaamStapel2,
                    null,
                    identiteitStapel));

            final BrpIstStandaardGroepInhoud istStandaardOuder2GroepInhoud =
                    new BrpIstStandaardGroepInhoud.Builder(Lo3CategorieEnum.CATEGORIE_03, 0, 0).build();
            final BrpGroep<BrpIstRelatieGroepInhoud> istOuder2Groep =
                    createGroepMetHistorieA(
                            new BrpIstRelatieGroepInhoud.Builder(istStandaardOuder2GroepInhoud).anummer(aNummerOuder2)
                                    .bsn(bsnOuder2)
                                    .geslachtsaanduidingCode(geslachtOuder2)
                                    .build());

            istOuder2Stapel = createStapel(istOuder2Groep);
        }

        final BrpRelatie.Builder relatieBuilder =
                new BrpRelatie.Builder(
                        null,
                        BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                        BrpSoortBetrokkenheidCode.KIND,
                        new LinkedHashMap<>());
        relatieBuilder.betrokkenheden(betrokkenheidList);
        relatieBuilder.istOuder1Stapel(istOuder1Stapel);
        relatieBuilder.istOuder2Stapel(istOuder2Stapel);
        relatieBuilder.istGezagsverhoudingStapel(istGezagsverhoudingStapel);
        brpRelaties.add(relatieBuilder.build());
        return this;
    }

    BrpPersoonslijstTestDataBuilder addRelatieMetPuntOuder(final boolean ouder1HeeftOuderlijkGezag) {
        final BrpSoortBetrokkenheidCode rol = BrpSoortBetrokkenheidCode.OUDER;

        final List<BrpBetrokkenheid> betrokkenheidList = new ArrayList<>();

        BrpStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel = null;
        if (ouder1HeeftOuderlijkGezag) {
            ouderlijkGezagStapel = createStapel(createGroepMetHistorieA(new BrpOuderlijkGezagInhoud(new BrpBoolean(true, null))));

            final BrpIstStandaardGroepInhoud istStandaardGezagGroepInhoud =
                    new BrpIstStandaardGroepInhoud.Builder(Lo3CategorieEnum.CATEGORIE_11, 0, 0).build();
            final BrpIstGezagsVerhoudingGroepInhoud istGezagsVerhoudingGroep =
                    new BrpIstGezagsVerhoudingGroepInhoud.Builder(istStandaardGezagGroepInhoud).indicatieOuder1HeeftGezag(new BrpBoolean(true, null))
                            .build();
            istGezagsverhoudingStapel = createStapel(createGroepMetHistorieA(istGezagsVerhoudingGroep));
        }
        betrokkenheidList.add(new BrpBetrokkenheid(rol, null, null, null, ouderlijkGezagStapel, null, null, null));

        final BrpRelatie.Builder relatieBuilder =
                new BrpRelatie.Builder(
                        null,
                        BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                        BrpSoortBetrokkenheidCode.KIND,
                        new LinkedHashMap<>());
        relatieBuilder.betrokkenheden(betrokkenheidList);
        relatieBuilder.istGezagsverhoudingStapel(istGezagsverhoudingStapel);
        brpRelaties.add(relatieBuilder.build());
        return this;
    }
}
