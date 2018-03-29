/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Documentatie;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Gezag;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.junit.Before;
import org.junit.Test;

public class BrpIstGezagsverhoudingConverteerderTest extends AbstractBrpIstConverteerderTest<BrpIstGezagsVerhoudingGroepInhoud> {

    private Lo3GezagsverhoudingInhoud expectedInhoud;
    private Lo3Documentatie expectedDocumentatie;
    private Lo3Historie expectedHistorie;
    private Lo3Herkomst expectedHerkomst;
    private BrpIstGezagsverhoudingConverteerder subject;

    @Before
    public void setUp() {
        expectedInhoud = lo3Gezag("1", false);
        expectedDocumentatie = maakDocumentatie(false);
        expectedHistorie = maakHistorie();
        expectedHerkomst = maakHerkomst(Lo3CategorieEnum.CATEGORIE_11);
        subject = new BrpIstGezagsverhoudingConverteerder(attribuutConverteerder);

        when(attribuutConverteerder.converteerIndicatieCurateleRegister(new BrpBoolean(true))).thenReturn(new Lo3IndicatieCurateleregister(1));
    }

    @Test
    public void testNullStapel() {
        assertNull(subject.converteerGezagsverhouding(null));
    }


    @Test
    public void testOuder1HeeftGezag() {
        final List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen = new ArrayList<>();
        final BrpIstGezagsVerhoudingGroepInhoud.Builder builder = maakGezagsverhoudingGroepInhoud(true);
        builder.indicatieOuder1HeeftGezag(new BrpBoolean(true, null));
        groepen.add(maakGroep(builder.build()));
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> lo3Stapel = subject.converteerGezagsverhouding(new BrpStapel<>(groepen));

        controleerStapel(lo3Stapel, expectedInhoud, expectedDocumentatie, null, expectedHistorie, expectedHerkomst);
    }

    @Test
    public void testOuder2HeeftGezag() {
        final List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen = new ArrayList<>();
        final BrpIstGezagsVerhoudingGroepInhoud.Builder builder = maakGezagsverhoudingGroepInhoud(true);
        builder.indicatieOuder2HeeftGezag(new BrpBoolean(true, null));
        final BrpIstGezagsVerhoudingGroepInhoud inhoud = builder.build();
        groepen.add(maakGroep(inhoud));
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> lo3Stapel = subject.converteerGezagsverhouding(new BrpStapel<>(groepen));

        final Lo3GezagsverhoudingInhoud expectedInhoudParam = lo3Gezag("2", false);
        controleerStapel(lo3Stapel, expectedInhoudParam, expectedDocumentatie, null, expectedHistorie, expectedHerkomst);
    }

    @Test
    public void testDerdeHeeftGezag() {
        final List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen = new ArrayList<>();
        final BrpIstGezagsVerhoudingGroepInhoud.Builder builder = maakGezagsverhoudingGroepInhoud(true);
        builder.indicatieDerdeHeeftGezag(new BrpBoolean(true, null));
        final BrpIstGezagsVerhoudingGroepInhoud inhoud = builder.build();
        groepen.add(maakGroep(inhoud));
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> lo3Stapel = subject.converteerGezagsverhouding(new BrpStapel<>(groepen));

        final Lo3GezagsverhoudingInhoud expectedInhoudParam = lo3Gezag("D", false);
        controleerStapel(lo3Stapel, expectedInhoudParam, expectedDocumentatie, null, expectedHistorie, expectedHerkomst);
    }

    @Test
    public void testOuder1DerdeHeeftGezag() {
        final List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen = new ArrayList<>();
        final BrpIstGezagsVerhoudingGroepInhoud.Builder builder = maakGezagsverhoudingGroepInhoud(true);
        builder.indicatieOuder1HeeftGezag(new BrpBoolean(true, null));
        builder.indicatieDerdeHeeftGezag(new BrpBoolean(true, null));
        final BrpIstGezagsVerhoudingGroepInhoud inhoud = builder.build();
        groepen.add(maakGroep(inhoud));
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> lo3Stapel = subject.converteerGezagsverhouding(new BrpStapel<>(groepen));

        final Lo3GezagsverhoudingInhoud expectedInhoudParam = lo3Gezag("1D", false);
        controleerStapel(lo3Stapel, expectedInhoudParam, expectedDocumentatie, null, expectedHistorie, expectedHerkomst);
    }

    @Test
    public void testOuder2DerdeHeeftGezag() {
        final List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen = new ArrayList<>();
        final BrpIstGezagsVerhoudingGroepInhoud.Builder builder = maakGezagsverhoudingGroepInhoud(true);
        builder.indicatieOuder2HeeftGezag(new BrpBoolean(true, null));
        builder.indicatieDerdeHeeftGezag(new BrpBoolean(true, null));
        final BrpIstGezagsVerhoudingGroepInhoud inhoud = builder.build();
        groepen.add(maakGroep(inhoud));
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> lo3Stapel = subject.converteerGezagsverhouding(new BrpStapel<>(groepen));

        final Lo3GezagsverhoudingInhoud expectedInhoudParam = lo3Gezag("2D", false);
        controleerStapel(lo3Stapel, expectedInhoudParam, expectedDocumentatie, null, expectedHistorie, expectedHerkomst);
    }

    @Test
    public void testOnderCuratele() {
        final List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen = new ArrayList<>();
        final BrpIstGezagsVerhoudingGroepInhoud.Builder builder = maakGezagsverhoudingGroepInhoud(true);
        builder.indicatieOnderCuratele(new BrpBoolean(true, null));
        final BrpIstGezagsVerhoudingGroepInhoud inhoud = builder.build();
        groepen.add(maakGroep(inhoud));
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> lo3Stapel = subject.converteerGezagsverhouding(new BrpStapel<>(groepen));

        final Lo3GezagsverhoudingInhoud expectedInhoudParam = lo3Gezag(null, true);
        controleerStapel(lo3Stapel, expectedInhoudParam, expectedDocumentatie, null, expectedHistorie, expectedHerkomst);
    }

    private BrpIstGezagsVerhoudingGroepInhoud.Builder maakGezagsverhoudingGroepInhoud(final boolean heeftDocumentatie) {
        final BrpIstStandaardGroepInhoud.Builder standaardGegevensBuilder = new BrpIstStandaardGroepInhoud.Builder(Lo3CategorieEnum.CATEGORIE_11, 0, 0);

        if (heeftDocumentatie) {
            standaardGegevensBuilder.documentOmschrijving(new BrpString(DOCUMENT_OMSCHRIJVING));
            standaardGegevensBuilder.soortDocument(BrpSoortDocumentCode.HISTORIE_CONVERSIE);
            standaardGegevensBuilder.partij(new BrpPartijCode(BRP_PARTIJ_CODE));
            standaardGegevensBuilder.rubriek8220DatumDocument(new BrpInteger(DATUM_DOCUMENT));
            standaardGegevensBuilder.aktenummer(null);
        }
        standaardGegevensBuilder.rubriek8510IngangsdatumGeldigheid(new BrpInteger(DATUM_GELDIGHEID));
        standaardGegevensBuilder.rubriek8610DatumVanOpneming(new BrpInteger(DATUM_OPNEMING));
        return new BrpIstGezagsVerhoudingGroepInhoud.Builder(standaardGegevensBuilder.build());
    }

    @Test
    public void testGeenDocumentatie() {
        final List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen = new ArrayList<>();
        final BrpIstGezagsVerhoudingGroepInhoud.Builder builder = maakGezagsverhoudingGroepInhoud(false);
        builder.indicatieOuder1HeeftGezag(new BrpBoolean(true, null));

        final BrpIstGezagsVerhoudingGroepInhoud inhoud = builder.build();
        groepen.add(maakGroep(inhoud));
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> lo3Stapel = subject.converteerGezagsverhouding(new BrpStapel<>(groepen));

        final Lo3Documentatie expectedDocument = lo3Documentatie(0L, null, null, null, null, null);
        controleerStapel(lo3Stapel, expectedInhoud, expectedDocument, null, expectedHistorie, expectedHerkomst);
    }
}
