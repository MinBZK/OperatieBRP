/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoudTestUtil;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.junit.Test;

public class BrpPersoonslijstSorterTest {
    private BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

    @Test
    public void sorteer() throws Exception {
        builder.adresStapel(BrpAdresInhoudTest.createStapel());
        builder.buitenlandsPersoonsnummerStapels(createBuitenlandsPersoonNummerStapels());
        builder.geslachtsnaamcomponentStapels(createGeslachtsNaamComponentStapels());
        builder.voornaamStapels(createVoornaamStapels());
        builder.nationaliteitStapels(createNationaliteitStapels());
        builder.reisdocumentStapels(createReisDocumentStapels());
        builder.relaties(createRElatieStapels());
        builder.verificatieStapels(createVerificatieStapels());
        final BrpPersoonslijst pl = builder.build();
        BrpPersoonslijstSorter.sorteer(pl);
        assertResultaatBuitensPersoonNrStapels(pl);
        assertGeslachtsNaamComponentStapels(pl);
        assertVoornaamStapels(pl);
        assertNationaliteitStapels(pl);
        assertRelatieStapels(pl);

    }

    private List<BrpStapel<BrpReisdocumentInhoud>> createReisDocumentStapels() {
        List<BrpStapel<BrpReisdocumentInhoud>> stapels = new LinkedList<>();

        stapels.add(createReisDocumentStapel("EK", "2234", 20120102, 20120102, "M", 20300102, null, null,1));
        stapels.add(createReisDocumentStapel("EK", "2234", 20120102, 20120102, "M", 20300101, null, null,1));
        stapels.add(createReisDocumentStapel("EK", "2234", 20120102, 20120102, "B", 20300101, null, null,1));
        stapels.add(createReisDocumentStapel("EK", "2234", 20120102, 20120101, "B", 20300101, null, null,1));
        stapels.add(createReisDocumentStapel("EK", "2234", 20120101, 20120101, "B", 20300101, null, null,1));
        stapels.add(createReisDocumentStapel("EK", "1234", 20120101, 20120101, "B", 20300101, null, null,1));
        stapels.add(createReisDocumentStapel("AK", "1234", 20120101, 20120101, "B", 20300101, null, null,1));


        stapels.add(createReisDocumentStapel("EK", "1234", 20120101, 20120101, "B", 20300101, null, 'V',2));
        stapels.add(createReisDocumentStapel("EK", "1234", 20120101, 20120101, "B", 20300101, null, null,2));
        stapels.add(createReisDocumentStapel("EK", "1234", 20120101, 20120101, "B", 20300101, 20170101, 'V',2));
        return stapels;
    }

    private BrpStapel<BrpReisdocumentInhoud> createReisDocumentStapel(final String soort, final String nummer, final Integer ingang,
                                                                      final Integer uitgifte, final String autoriteit, final Integer eindeDoc,
                                                                      final Integer inhVermis,
                                                                      final Character aandVermis,
                                                                      final int herkomstVoorkomen) {
        final List<BrpGroep<BrpReisdocumentInhoud>> groepen = new LinkedList<>();
        groepen.add(createReisDocument(soort, nummer, ingang, uitgifte, autoriteit, eindeDoc, inhVermis, aandVermis, herkomstVoorkomen));
        return new BrpStapel<>(groepen);
    }

    private BrpGroep<BrpReisdocumentInhoud> createReisDocument(final String soort, final String nummer, final Integer ingang,
                                                               final Integer uitgifte, final String autoriteit, final Integer eindeDoc, final Integer inhVermis,
                                                               final Character aandVermis, int herkomstVoorkomen) {
        final BrpReisdocumentInhoud
                doc = new BrpReisdocumentInhoud(
                soort == null ? null : new BrpSoortNederlandsReisdocumentCode(soort),
                nummer == null ? null : new BrpString(nummer),
                ingang == null ? null : new BrpDatum(ingang, null),
                uitgifte == null ? null : new BrpDatum(uitgifte, null),
                autoriteit == null ? null : new BrpReisdocumentAutoriteitVanAfgifteCode(autoriteit),
                eindeDoc == null ? null : new BrpDatum(eindeDoc, null),
                inhVermis == null ? null : new BrpDatum(inhVermis, null),
                aandVermis == null ? null : new BrpAanduidingInhoudingOfVermissingReisdocumentCode(aandVermis));
        final Lo3Herkomst herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 1, herkomstVoorkomen);
        BrpActie actieInhoud = new BrpActie(1L, BrpSoortActieCode.CONVERSIE_GBA, new BrpPartijCode("000123"), null, null, null, 1, herkomst);
        return new BrpGroep<>(doc, BrpHistorieTest.createdefaultInhoud(), actieInhoud, null, null);
    }

    private List<BrpStapel<BrpVerificatieInhoud>> createVerificatieStapels() {
        List<BrpStapel<BrpVerificatieInhoud>> result = new LinkedList<>();
        result.add(BrpVerificatieInhoudTestUtil.createStapel());
        result.add(BrpVerificatieInhoudTestUtil.createStapel());
        return result;
    }

    private void assertRelatieStapels(final BrpPersoonslijst pl) {
        final List<BrpRelatie> relaties = pl.getRelaties();
        assertEquals(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, relaties.get(0).getSoortRelatieCode());
        assertEquals(BrpSoortBetrokkenheidCode.KIND, relaties.get(0).getRolCode());
        assertEquals(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, relaties.get(1).getSoortRelatieCode());
        assertEquals(BrpSoortBetrokkenheidCode.KIND, relaties.get(1).getRolCode());
        assertEquals(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, relaties.get(2).getSoortRelatieCode());
        assertEquals(BrpSoortBetrokkenheidCode.KIND, relaties.get(2).getRolCode());
        assertEquals(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, relaties.get(3).getSoortRelatieCode());
        assertEquals(BrpSoortBetrokkenheidCode.OUDER, relaties.get(3).getRolCode());
        assertEquals(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, relaties.get(4).getSoortRelatieCode());
        assertEquals(BrpSoortBetrokkenheidCode.OUDER, relaties.get(4).getRolCode());
        assertEquals(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, relaties.get(5).getSoortRelatieCode());
        assertEquals(BrpSoortBetrokkenheidCode.PARTNER, relaties.get(5).getRolCode());
        assertEquals(BrpSoortRelatieCode.HUWELIJK, relaties.get(6).getSoortRelatieCode());
        assertEquals(BrpSoortBetrokkenheidCode.PARTNER, relaties.get(6).getRolCode());
        assertEquals(BrpSoortRelatieCode.HUWELIJK, relaties.get(7).getSoortRelatieCode());
        assertEquals(BrpSoortBetrokkenheidCode.PARTNER, relaties.get(7).getRolCode());

    }

    private List<BrpRelatie> createRElatieStapels() {
        List<BrpRelatie> relaties = new LinkedList<>();
        relaties.add(BrpRelatieTest.createKindRelatie());
        relaties.add(BrpRelatieTest.createHuwelijkRelatie());
        relaties.add(BrpRelatieTest.createPartnerschapRelatie());
        relaties.add(BrpRelatieTest.createOuderRelatieZonderOuders());
        relaties.add(BrpRelatieTest.createKindRelatie());
        relaties.add(BrpRelatieTest.createHuwelijkRelatie());
        relaties.add(BrpRelatieTest.createOuderRelatieZonderOuders());
        relaties.add(BrpRelatieTest.createKindRelatie());
        return relaties;
    }

    private void assertNationaliteitStapels(final BrpPersoonslijst pl) {
        final List<BrpStapel<BrpNationaliteitInhoud>> list = pl.getNationaliteitStapels();
        assertEquals("1234", list.get(0).get(0).getInhoud().getNationaliteitCode().getWaarde());
        assertEquals("2234", list.get(1).get(0).getInhoud().getNationaliteitCode().getWaarde());
        assertEquals("2234", list.get(2).get(0).getInhoud().getNationaliteitCode().getWaarde());
        assertEquals("3234", list.get(3).get(0).getInhoud().getNationaliteitCode().getWaarde());
        assertEquals("4234", list.get(4).get(0).getInhoud().getNationaliteitCode().getWaarde());

    }

    private List<BrpStapel<BrpNationaliteitInhoud>> createNationaliteitStapels() {
        List<BrpStapel<BrpNationaliteitInhoud>> stapels = new LinkedList<>();
        stapels.add(createNationaliteitStapel("1234"));
        stapels.add(createNationaliteitStapel("4234"));
        stapels.add(createNationaliteitStapel("2234"));
        stapels.add(createNationaliteitStapel("3234"));
        stapels.add(createNationaliteitStapel("2234"));
        return stapels;

    }

    private BrpStapel<BrpNationaliteitInhoud> createNationaliteitStapel(final String code) {
        final List<BrpGroep<BrpNationaliteitInhoud>> groepen = new LinkedList<>();
        groepen.add(new BrpGroep<>(BrpNationaliteitInhoudTest.createInhoud(code), BrpHistorieTest.createdefaultInhoud(), null, null, null));
        return (BrpStapel<BrpNationaliteitInhoud>) new BrpStapel(groepen);
    }

    private void assertVoornaamStapels(final BrpPersoonslijst pl) {
        final List<BrpStapel<BrpVoornaamInhoud>> stapels = pl.getVoornaamStapels();
        assertEquals(1, stapels.get(0).get(0).getInhoud().getVolgnummer().getWaarde().intValue());
        assertEquals(2, stapels.get(1).get(0).getInhoud().getVolgnummer().getWaarde().intValue());
        assertEquals(2, stapels.get(2).get(0).getInhoud().getVolgnummer().getWaarde().intValue());
        assertEquals(3, stapels.get(3).get(0).getInhoud().getVolgnummer().getWaarde().intValue());
        assertEquals(4, stapels.get(4).get(0).getInhoud().getVolgnummer().getWaarde().intValue());

    }

    private List<BrpStapel<BrpVoornaamInhoud>> createVoornaamStapels() {
        List<BrpStapel<BrpVoornaamInhoud>> stapels = new LinkedList<>();
        stapels.add(createVoornaamStapel("Max", 4));
        stapels.add(createVoornaamStapel("Bert", 1));
        stapels.add(createVoornaamStapel("Alex", 3));
        stapels.add(createVoornaamStapel("Cornelus", 2));
        stapels.add(createVoornaamStapel("Willem", 2));
        return stapels;
    }

    private BrpStapel<BrpVoornaamInhoud> createVoornaamStapel(final String voornaam, final Integer volgnummer) {
        final List<BrpGroep<BrpVoornaamInhoud>> groepen = new LinkedList<>();
        final BrpGroep<BrpVoornaamInhoud>
                groep =
                new BrpGroep<>(new BrpVoornaamInhoud(new BrpString(voornaam), new BrpInteger(volgnummer)), BrpHistorieTest.createdefaultInhoud(), null, null,
                        null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);

    }

    private void assertGeslachtsNaamComponentStapels(final BrpPersoonslijst pl) {
        final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> stapels = pl.getGeslachtsnaamcomponentStapels();
        assertEquals(1, stapels.get(0).get(0).getInhoud().getVolgnummer().getWaarde().intValue());
        assertEquals(1, stapels.get(1).get(0).getInhoud().getVolgnummer().getWaarde().intValue());
        assertEquals(2, stapels.get(2).get(0).getInhoud().getVolgnummer().getWaarde().intValue());
        assertEquals(3, stapels.get(3).get(0).getInhoud().getVolgnummer().getWaarde().intValue());
    }

    private List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> createGeslachtsNaamComponentStapels() {
        List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> stapels = new LinkedList<>();
        stapels.add(createBrpgeslachtsNaamComponentStapel(3));
        stapels.add(createBrpgeslachtsNaamComponentStapel(1));
        stapels.add(createBrpgeslachtsNaamComponentStapel(1));
        stapels.add(createBrpgeslachtsNaamComponentStapel(2));
        return stapels;
    }

    private BrpStapel<BrpGeslachtsnaamcomponentInhoud> createBrpgeslachtsNaamComponentStapel(final int volgnummer) {
        final List<BrpGroep<BrpGeslachtsnaamcomponentInhoud>> groepen = new LinkedList<>();
        final BrpGroep<BrpGeslachtsnaamcomponentInhoud>
                groep =
                new BrpGroep<>(BrpGeslachtsnaamcomponentInhoudTest.createInhoud(volgnummer), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    private void assertResultaatBuitensPersoonNrStapels(final BrpPersoonslijst pl) {
        final List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> stapels = pl.getBuitenlandsPersoonsnummerStapels();
        assertEquals("0000", stapels.get(0).get(0).getInhoud().getAutoriteitVanAfgifte().getWaarde());
        assertEquals("2", stapels.get(0).get(0).getInhoud().getNummer().getWaarde());
        assertEquals("0000", stapels.get(1).get(0).getInhoud().getAutoriteitVanAfgifte().getWaarde());
        assertEquals("3", stapels.get(1).get(0).getInhoud().getNummer().getWaarde());
        assertEquals("0000", stapels.get(2).get(0).getInhoud().getAutoriteitVanAfgifte().getWaarde());
        assertEquals("3", stapels.get(2).get(0).getInhoud().getNummer().getWaarde());
        assertEquals("0030", stapels.get(3).get(0).getInhoud().getAutoriteitVanAfgifte().getWaarde());
        assertEquals("2", stapels.get(3).get(0).getInhoud().getNummer().getWaarde());
        assertEquals("0031", stapels.get(4).get(0).getInhoud().getAutoriteitVanAfgifte().getWaarde());
        assertEquals("1", stapels.get(4).get(0).getInhoud().getNummer().getWaarde());
        assertEquals("0032", stapels.get(5).get(0).getInhoud().getAutoriteitVanAfgifte().getWaarde());
        assertEquals("4", stapels.get(5).get(0).getInhoud().getNummer().getWaarde());
    }

    private List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> createBuitenlandsPersoonNummerStapels() {
        List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> stapels = new LinkedList<>();
        stapels.add(createBuitenlandsPNRStapel("1", "0031"));
        stapels.add(createBuitenlandsPNRStapel("3", "0000"));
        stapels.add(createBuitenlandsPNRStapel("2", "0000"));
        stapels.add(createBuitenlandsPNRStapel("3", "0000"));
        stapels.add(createBuitenlandsPNRStapel("2", "0030"));
        stapels.add(createBuitenlandsPNRStapel("4", "0032"));
        return stapels;
    }

    private BrpStapel<BrpBuitenlandsPersoonsnummerInhoud> createBuitenlandsPNRStapel(final String nummer, final String autoriteit) {
        final List<BrpGroep<BrpBuitenlandsPersoonsnummerInhoud>> groepen = new LinkedList<>();
        final BrpGroep<BrpBuitenlandsPersoonsnummerInhoud> groep = new BrpGroep<>(BrpBuitenlandsPersoonsnummerInhoudTest.createInhoud(nummer, autoriteit),
                BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }


}
