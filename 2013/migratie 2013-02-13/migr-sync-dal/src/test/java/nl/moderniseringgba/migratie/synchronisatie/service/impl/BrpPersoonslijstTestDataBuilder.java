/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerdragCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;

/**
 * Naamgeving ontleent aan {@link http://c2.com/cgi/wiki?TestDataBuilder}
 * 
 * <h3>
 * Default gebruik:</h3>
 * 
 * <pre>
 * new BrpPersoonslijstTestDataBuilder().addDefaultTestStapels().build();
 * </pre>
 * 
 * <h3>
 * Extra stapel toevoegen:</h3>
 * 
 * <pre>
 * new BrpPersoonslijstTestDataBuilder().addDefaultTestStapels()
 *         .addGroepMetHistorieA(new BrpNationaliteitInhoud(new BrpNationaliteitCode(&quot;1&quot;), null, null)).build();
 * </pre>
 */
public class BrpPersoonslijstTestDataBuilder {

    public static final long DEFAULT_ADMINISTRATIENUMMER = 123456789L;
    public static final long DEFAULT_BURGERSERVICENUMMER = 987654321L;
    public static final BrpGeslachtsaanduidingCode DEFAULT_GESLACHT = BrpGeslachtsaanduidingCode.MAN;

    private static long actieIdTeller;

    private final List<BrpStapel<?>> brpStapels = new ArrayList<BrpStapel<?>>();
    private final List<BrpRelatie> brpRelaties = new ArrayList<BrpRelatie>();

    public BrpPersoonslijstTestDataBuilder() {
    }

    public BrpPersoonslijstTestDataBuilder addDefaultTestStapels() {
        addGroepMetHistorieA(new BrpIdentificatienummersInhoud(DEFAULT_ADMINISTRATIENUMMER,
                DEFAULT_BURGERSERVICENUMMER));
        addGroepMetHistorieB(new BrpGeslachtsaanduidingInhoud(DEFAULT_GESLACHT));
        addGroepMetHistorieC(new BrpInschrijvingInhoud(null, null, new BrpDatum(20110101), 0));
        addGroepMetHistorieD(new BrpSamengesteldeNaamInhoud(null, "Noam", "D", '-', null, "Chomsky", false, true));
        return this;
    }

    public BrpPersoonslijst build() throws InputValidationException {
        return new BrpPersoonslijstBuilderVisitor(new BrpPersoonslijstBuilder().relaties(brpRelaties)).addStapels(
                brpStapels).build();
    }

    private <T extends BrpGroepInhoud> BrpStapel<T> createStapel(final BrpGroep<T> groep) {
        return new BrpStapel<T>(Collections.singletonList(groep));
    }

    /**
     * maakt een nieuwe stapel aan met de meegegeven inhoud, met default historiepatroon A.
     * 
     * @param inhoud
     *            Een specifiek BrpGroepInhoud object.
     * @return Deze TestDataBuilder
     */
    public <T extends BrpGroepInhoud> BrpPersoonslijstTestDataBuilder addGroepMetHistorieA(final T inhoud) {
        brpStapels.add(createStapel(createGroepMetHistorieA(inhoud)));
        return this;
    }

    /**
     * maakt een nieuwe stapel aan met de meegegeven inhoud, met default historiepatroon B.
     * 
     * @param inhoud
     *            Een specifiek BrpGroepInhoud object.
     * @return Deze TestDataBuilder
     */
    public <T extends BrpGroepInhoud> BrpPersoonslijstTestDataBuilder addGroepMetHistorieB(final T inhoud) {
        brpStapels.add(createStapel(createGroepMetHistorieB(inhoud)));
        return this;
    }

    /**
     * maakt een nieuwe stapel aan met de meegegeven inhoud, met default historiepatroon C.
     * 
     * @param inhoud
     *            Een specifiek BrpGroepInhoud object.
     * @return Deze TestDataBuilder
     */
    public <T extends BrpGroepInhoud> BrpPersoonslijstTestDataBuilder addGroepMetHistorieC(final T inhoud) {
        brpStapels.add(createStapel(createGroepMetHistorieC(inhoud)));
        return this;
    }

    /**
     * maakt een nieuwe stapel aan met de meegegeven inhoud, met default historiepatroon D.
     * 
     * @param inhoud
     *            Een specifiek BrpGroepInhoud object.
     * @return Deze TestDataBuilder
     */
    public <T extends BrpGroepInhoud> BrpPersoonslijstTestDataBuilder addGroepMetHistorieD(final T inhoud) {
        brpStapels.add(createStapel(createGroepMetHistorieD(inhoud)));
        return this;
    }

    /**
     * maakt een nieuwe stapel aan met de meegegeven inhoud, met default historiepatroon E en actie en documenten.
     * 
     * @param inhoud
     *            Een specifiek BrpGroepInhoud object.
     * @return Deze TestDataBuilder
     */
    public <T extends BrpGroepInhoud> BrpPersoonslijstTestDataBuilder addGroepMetHistorieE(final T inhoud) {
        brpStapels.add(createStapel(createGroepMetHistorieE(inhoud)));
        return this;
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> createGroepMetHistorieA(final T inhoud) {
        return createGroepMetHistorie(inhoud, createBrpHistorie(20110101, 20110102, 20110103, null), null, null, null);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> createGroepMetHistorieB(final T inhoud) {
        return createGroepMetHistorie(inhoud, createBrpHistorie(20120101, 20120102, 20120103, null), null, null, null);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> createGroepMetHistorieC(final T inhoud) {
        return createGroepMetHistorie(inhoud, createBrpHistorie(20130101, 20130102, 20130103, 20130104), null, null,
                null);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> createGroepMetHistorieD(final T inhoud) {
        return createGroepMetHistorie(inhoud, createBrpHistorie(20140101, 20140102, 20140103, 20140104), null, null,
                null);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> createGroepMetHistorieE(final T inhoud) {
        return createGroepMetHistorieEnActies(inhoud, createBrpHistorie(20140101, 20140102, 20140103, 20140104));
    }

    private BrpHistorie createBrpHistorie(
            final Integer datumAanvang,
            final Integer datumEinde,
            final Integer registratieTijd,
            final Integer vervalTijd) {
        return new BrpHistorie(new BrpDatum(datumAanvang), new BrpDatum(datumEinde),
                BrpDatumTijd.fromDatum(registratieTijd), vervalTijd == null ? null
                        : BrpDatumTijd.fromDatum(vervalTijd));
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> createGroepMetHistorie(
            final T inhoud,
            final BrpHistorie brpHistorie,
            final BrpActie actieInhoud,
            final BrpActie actieVerval,
            final BrpActie actieGeldigheid) {
        return new BrpGroep<T>(inhoud, brpHistorie, actieInhoud, actieVerval, actieGeldigheid);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> createGroepMetHistorieEnActies(
            final T inhoud,
            final BrpHistorie brpHistorie) {
        final List<BrpStapel<BrpDocumentInhoud>> documentStapels = new ArrayList<BrpStapel<BrpDocumentInhoud>>();
        final BrpDocumentInhoud brpDocumentInhoud1His1 =
                new BrpDocumentInhoud(BrpSoortDocumentCode.DOCUMENT, "ID001", null, "D 1", new BrpPartijCode(
                        "Almere", null));
        final BrpDocumentInhoud brpDocumentInhoud1His2 =
                new BrpDocumentInhoud(BrpSoortDocumentCode.DOCUMENT, "ID001", null, "D 1.1", new BrpPartijCode(
                        "Almere", null));
        final List<BrpGroep<BrpDocumentInhoud>> brpDocumentInhoud1List = new ArrayList<BrpGroep<BrpDocumentInhoud>>();
        brpDocumentInhoud1List.add(new BrpGroep<BrpDocumentInhoud>(brpDocumentInhoud1His1, new BrpHistorie(
                brpHistorie.getDatumAanvangGeldigheid(), null, BrpDatumTijd.fromDatum(20120203), BrpDatumTijd
                        .fromDatum(20120303)), null, null, null));
        brpDocumentInhoud1List.add(new BrpGroep<BrpDocumentInhoud>(brpDocumentInhoud1His2, new BrpHistorie(
                brpHistorie.getDatumAanvangGeldigheid(), null, BrpDatumTijd.fromDatum(20120303), null), null, null,
                null));
        documentStapels.add(new BrpStapel<BrpDocumentInhoud>(brpDocumentInhoud1List));

        final BrpDocumentInhoud brpDocumentInhoud2His1 =
                new BrpDocumentInhoud(BrpSoortDocumentCode.AKTE, "ID002", "A 1", null, new BrpPartijCode("Almere",
                        null));
        final BrpDocumentInhoud brpDocumentInhoud2His2 =
                new BrpDocumentInhoud(BrpSoortDocumentCode.AKTE, "ID002", "A 1.1", null, new BrpPartijCode("Almere",
                        null));
        final List<BrpGroep<BrpDocumentInhoud>> brpDocumentInhoud2List = new ArrayList<BrpGroep<BrpDocumentInhoud>>();
        brpDocumentInhoud2List.add(new BrpGroep<BrpDocumentInhoud>(brpDocumentInhoud2His1, new BrpHistorie(
                brpHistorie.getDatumAanvangGeldigheid(), null, BrpDatumTijd.fromDatum(20120203), BrpDatumTijd
                        .fromDatum(20120303)), null, null, null));
        brpDocumentInhoud2List.add(new BrpGroep<BrpDocumentInhoud>(brpDocumentInhoud2His2, new BrpHistorie(
                brpHistorie.getDatumAanvangGeldigheid(), null, BrpDatumTijd.fromDatum(20120303), null), null, null,
                null));
        documentStapels.add(new BrpStapel<BrpDocumentInhoud>(brpDocumentInhoud2List));

        final Lo3Herkomst dummyLo3Herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        final BrpActie actieInhoud =
                new BrpActie(actieIdTeller++, BrpSoortActieCode.CONVERSIE_GBA, new BrpPartijCode("Almere", null),
                        new BrpVerdragCode("V1"), BrpDatumTijd.fromDatum(brpHistorie.getDatumAanvangGeldigheid()
                                .getDatum()), brpHistorie.getDatumTijdRegistratie(), documentStapels, 0,
                        dummyLo3Herkomst);
        BrpActie actieVerval = null;
        if (brpHistorie.getDatumTijdVerval() != null) {
            actieVerval =
                    new BrpActie(actieIdTeller++, BrpSoortActieCode.CONVERSIE_GBA, new BrpPartijCode("Almere", null),
                            new BrpVerdragCode("V1"), brpHistorie.getDatumTijdVerval(),
                            brpHistorie.getDatumTijdRegistratie(), documentStapels, 0, dummyLo3Herkomst);
        }
        final BrpActie actieGeldigheid =
                new BrpActie(actieIdTeller++, BrpSoortActieCode.CONVERSIE_GBA, new BrpPartijCode("Almere", null),
                        new BrpVerdragCode("V2"), BrpDatumTijd.fromDatum(brpHistorie.getDatumAanvangGeldigheid()
                                .getDatum()), brpHistorie.getDatumTijdRegistratie(), documentStapels, 0,
                        dummyLo3Herkomst);

        return new BrpGroep<T>(inhoud, brpHistorie, actieInhoud, actieVerval, actieGeldigheid);
    }

    public BrpPersoonslijstTestDataBuilder addKindRelatie() {
        final BrpSoortBetrokkenheidCode rol = BrpSoortBetrokkenheidCode.KIND;
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel =
                createStapel(createGroepMetHistorieA(new BrpIdentificatienummersInhoud(999654321L, 123456999L)));
        final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel =
                createStapel(createGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN)));
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel =
                createStapel(createGroepMetHistorieA(new BrpGeboorteInhoud(new BrpDatum(19800101),
                        new BrpGemeenteCode(new BigDecimal("1900")), null, null, null, new BrpLandCode(
                                Integer.valueOf("6030")), null)));
        final BrpStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel =
                createStapel(createGroepMetHistorieA(new BrpOuderlijkGezagInhoud(true)));
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel =
                createStapel(createGroepMetHistorieA(new BrpSamengesteldeNaamInhoud(null, "Piet", "van", ' ', null,
                        "Klaassen", false, false)));
        final BrpStapel<BrpOuderInhoud> ouderStapel =
                createStapel(createGroepMetHistorieA(new BrpOuderInhoud(true, new BrpDatum(19800102))));

        final BrpBetrokkenheid kindBetrokkenheid =
                new BrpBetrokkenheid(rol, identificatienummersStapel, geslachtsaanduidingStapel, geboorteStapel,
                        ouderlijkGezagStapel, samengesteldeNaamStapel, ouderStapel);

        final BrpStapel<BrpRelatieInhoud> relatieStapel =
                createStapel(createGroepMetHistorieA(new BrpRelatieInhoud(new BrpDatum(19800101),
                        new BrpGemeenteCode(new BigDecimal("1900")), null, null, null, new BrpLandCode(
                                Integer.valueOf("6030")), null, null, null, null, null, null, null, null, null)));
        brpRelaties.add(new BrpRelatie(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                BrpSoortBetrokkenheidCode.OUDER, Collections.singletonList(kindBetrokkenheid), relatieStapel));
        return this;
    }

    public BrpPersoonslijstTestDataBuilder addHuwelijkRelatie(
            final Long aNummerPartner,
            final Long bsnPartner,
            final BrpGeslachtsaanduidingCode geslachtPartner,
            final BrpGeboorteInhoud geboortePartner,
            final BrpSamengesteldeNaamInhoud naamPartner,
            final BrpDatum trouwDatum,
            final BrpGemeenteCode trouwGemeente,
            final boolean laatRelatieVervallen) {
        final BrpSoortBetrokkenheidCode rol = BrpSoortBetrokkenheidCode.PARTNER;
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel =
                createStapel(createGroepMetHistorieA(new BrpIdentificatienummersInhoud(aNummerPartner, bsnPartner)));
        final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel =
                createStapel(createGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(geslachtPartner)));
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel = createStapel(createGroepMetHistorieA(geboortePartner));
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel =
                createStapel(createGroepMetHistorieA(naamPartner));

        final BrpBetrokkenheid partnerBetrokkenheid =
                new BrpBetrokkenheid(rol, identificatienummersStapel, geslachtsaanduidingStapel, geboorteStapel,
                        null, samengesteldeNaamStapel, null);

        BrpGroep<BrpRelatieInhoud> relatieGroep;
        if (laatRelatieVervallen) {
            relatieGroep =
                    createGroepMetHistorieC(new BrpRelatieInhoud(trouwDatum, trouwGemeente, null, null, null,
                            new BrpLandCode(Integer.valueOf("6030")), null, null, null, null, null, null, null, null,
                            null));
        } else {
            relatieGroep =
                    createGroepMetHistorieA(new BrpRelatieInhoud(trouwDatum, trouwGemeente, null, null, null,
                            new BrpLandCode(Integer.valueOf("6030")), null, null, null, null, null, null, null, null,
                            null));
        }

        final BrpStapel<BrpRelatieInhoud> relatieStapel = createStapel(relatieGroep);
        brpRelaties.add(new BrpRelatie(BrpSoortRelatieCode.HUWELIJK, BrpSoortBetrokkenheidCode.PARTNER, Collections
                .singletonList(partnerBetrokkenheid), relatieStapel));
        return this;
    }

    public BrpPersoonslijstTestDataBuilder addRelatieMetKind(
            final Long aNummerKind,
            final Long bsnKind,
            final BrpGeslachtsaanduidingCode geslachtKind,
            final BrpGeboorteInhoud geboorteKind,
            final BrpSamengesteldeNaamInhoud naamKind,
            final boolean laatRelatieVervallen) {
        final BrpSoortBetrokkenheidCode rol = BrpSoortBetrokkenheidCode.KIND;
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel =
                createStapel(createGroepMetHistorieA(new BrpIdentificatienummersInhoud(aNummerKind, bsnKind)));
        final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel =
                createStapel(createGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(geslachtKind)));
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel = createStapel(createGroepMetHistorieA(geboorteKind));
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel =
                createStapel(createGroepMetHistorieA(naamKind));

        final BrpBetrokkenheid kindBetrokkenheid =
                new BrpBetrokkenheid(rol, identificatienummersStapel, geslachtsaanduidingStapel, geboorteStapel,
                        null, samengesteldeNaamStapel, null);

        brpRelaties.add(new BrpRelatie(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                BrpSoortBetrokkenheidCode.OUDER, Collections.singletonList(kindBetrokkenheid), null));
        return this;
    }

    public BrpPersoonslijstTestDataBuilder addRelatieMetOuders(
            final Long aNummerOuder1,
            final Long bsnOuder1,
            final BrpGeslachtsaanduidingCode geslachtOuder1,
            final BrpGeboorteInhoud geboorteOuder1,
            final BrpSamengesteldeNaamInhoud naamOuder1,
            final Long aNummerOuder2,
            final Long bsnOuder2,
            final BrpGeslachtsaanduidingCode geslachtOuder2,
            final BrpGeboorteInhoud geboorteOuder2,
            final BrpSamengesteldeNaamInhoud naamOuder2,
            final boolean laatRelatieVervallen,
            final boolean ouder1HeeftOuderlijkGezag) {
        final BrpSoortBetrokkenheidCode rol = BrpSoortBetrokkenheidCode.OUDER;

        final List<BrpBetrokkenheid> betrokkenheidList = new ArrayList<BrpBetrokkenheid>();
        // Ouder 1
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel1 =
                createStapel(createGroepMetHistorieA(new BrpIdentificatienummersInhoud(aNummerOuder1, bsnOuder1)));
        final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel1 =
                createStapel(createGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(geslachtOuder1)));
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel1 = createStapel(createGroepMetHistorieA(geboorteOuder1));
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel1 =
                createStapel(createGroepMetHistorieA(naamOuder1));

        BrpStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel = null;
        if (ouder1HeeftOuderlijkGezag) {
            ouderlijkGezagStapel = createStapel(createGroepMetHistorieA(new BrpOuderlijkGezagInhoud(true)));
        }

        betrokkenheidList.add(new BrpBetrokkenheid(rol, identificatienummersStapel1, geslachtsaanduidingStapel1,
                geboorteStapel1, ouderlijkGezagStapel, samengesteldeNaamStapel1, null));

        if (aNummerOuder2 != null) {
            // Ouder 2
            final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel2 =
                    createStapel(createGroepMetHistorieA(new BrpIdentificatienummersInhoud(aNummerOuder2, bsnOuder2)));
            final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel2 =
                    createStapel(createGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(geslachtOuder2)));
            final BrpStapel<BrpGeboorteInhoud> geboorteStapel2 =
                    createStapel(createGroepMetHistorieA(geboorteOuder2));
            final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel2 =
                    createStapel(createGroepMetHistorieA(naamOuder2));

            betrokkenheidList.add(new BrpBetrokkenheid(rol, identificatienummersStapel2, geslachtsaanduidingStapel2,
                    geboorteStapel2, null, samengesteldeNaamStapel2, null));
        }

        brpRelaties.add(new BrpRelatie(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                BrpSoortBetrokkenheidCode.KIND, betrokkenheidList, null));
        return this;
    }

    public BrpPersoonslijstTestDataBuilder addRelatieMetPuntOuder(
            final boolean laatRelatieVervallen,
            final boolean ouder1HeeftOuderlijkGezag) {
        final BrpSoortBetrokkenheidCode rol = BrpSoortBetrokkenheidCode.OUDER;

        final List<BrpBetrokkenheid> betrokkenheidList = new ArrayList<BrpBetrokkenheid>();

        BrpStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel = null;
        if (ouder1HeeftOuderlijkGezag) {
            ouderlijkGezagStapel = createStapel(createGroepMetHistorieA(new BrpOuderlijkGezagInhoud(true)));
        }

        betrokkenheidList.add(new BrpBetrokkenheid(rol, null, null, null, ouderlijkGezagStapel, null, null));

        brpRelaties.add(new BrpRelatie(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                BrpSoortBetrokkenheidCode.KIND, betrokkenheidList, null));
        return this;
    }
}
