/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.DienstbundelGroep;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelGroepBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestElementBuilder;
import nl.bzk.brp.model.basis.FormeleHistorieImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


public class MagHistorieTonenPredikaatTest {


    @Before
    public void setUp() {
        ;
    }

    @Test
    public void testFormeelHistorischLeverenMagNiet() {

        final Set<DienstbundelGroep> dienstbundelGroepen = new HashSet<>();
        final MagHistorieTonenPredikaat predikaat = new MagHistorieTonenPredikaat(dienstbundelGroepen, false);


        final ElementAttribuut element = new ElementAttribuut(TestElementBuilder.maker().metNaam(ElementEnum.PERSOON_GEBOORTE).maak());
        final DienstbundelGroep dienstbundelGroep =
            TestDienstbundelGroepBuilder.maker().metGroep(element.getWaarde()).metIndicatieFormeleHistorie(false).
                metIndicatieMaterieleHistorie(false).metIndicatieVerantwoording(false).maak();
        dienstbundelGroepen.add(dienstbundelGroep);

        final Set<HisPersoonGeboorteModel> alleModellen = maakTestPersoon().getPersoonGeboorteHistorie().getHistorie();

        // act
        final Collection gefilterdeModellen = CollectionUtils.select(alleModellen, predikaat);

        // assert:
        assertThat(gefilterdeModellen.size(), is(1));
    }

    @Test
    public void testFormeelHistorischLeverenMagNietVanwegeActieVervalGevuld() {
        final Set<DienstbundelGroep> dienstbundelGroepen = new HashSet<>();
        final MagHistorieTonenPredikaat predikaat = new MagHistorieTonenPredikaat(dienstbundelGroepen, false);

        final ElementAttribuut element = new ElementAttribuut(TestElementBuilder.maker().metNaam(ElementEnum.PERSOON_GEBOORTE).maak());
        final DienstbundelGroep dienstbundelGroep =
            TestDienstbundelGroepBuilder.maker().metGroep(element.getWaarde()).metIndicatieFormeleHistorie(false).
                metIndicatieMaterieleHistorie(false).metIndicatieVerantwoording(false).maak();
        dienstbundelGroepen.add(dienstbundelGroep);

        final Set<HisPersoonGeboorteModel> alleModellen = maakTestPersoon().getPersoonGeboorteHistorie().getHistorie();
        for (final HisPersoonGeboorteModel hisPersoonGeboorteModel : alleModellen) {
            final FormeleHistorieImpl formeleHistorie =
                (FormeleHistorieImpl) ReflectionTestUtils.getField(hisPersoonGeboorteModel, "formeleHistorie");
            formeleHistorie.setDatumTijdVerval(null);
        }

        // act
        final Collection gefilterdeModellen = CollectionUtils.select(alleModellen, predikaat);

        // assert:
        assertThat(gefilterdeModellen.size(), is(1));
    }

    @Test
    public void testFormeelHistorischLeverenMag() {
        final Set<DienstbundelGroep> dienstbundelGroepen = new HashSet<>();
        final MagHistorieTonenPredikaat predikaat = new MagHistorieTonenPredikaat(dienstbundelGroepen, false);

        final ElementAttribuut element = new ElementAttribuut(TestElementBuilder.maker().metNaam(ElementEnum.PERSOON_GEBOORTE).maak());
        final DienstbundelGroep dienstbundelGroep =
            TestDienstbundelGroepBuilder.maker().metGroep(element.getWaarde()).metIndicatieFormeleHistorie(true).
                metIndicatieMaterieleHistorie(false).metIndicatieVerantwoording(false).maak();
        dienstbundelGroepen.add(dienstbundelGroep);

        final Set<HisPersoonGeboorteModel> alleModellen = maakTestPersoon().getPersoonGeboorteHistorie().getHistorie();

        // act
        final Collection gefilterdeModellen = CollectionUtils.select(alleModellen, predikaat);

        // assert:
        assertThat(gefilterdeModellen.size(), is(2));
    }

    @Test
    public void testMaterieelHistorischLeverenMagNiet() {
        final Set<DienstbundelGroep> dienstbundelGroepen = new HashSet<>();
        final MagHistorieTonenPredikaat predikaat = new MagHistorieTonenPredikaat(dienstbundelGroepen, false);

        final ElementAttribuut element = new ElementAttribuut(TestElementBuilder.maker().metNaam(ElementEnum.PERSOON_BIJHOUDING).maak());
        final DienstbundelGroep dienstbundelGroep =
            TestDienstbundelGroepBuilder.maker().metGroep(element.getWaarde()).
                metIndicatieFormeleHistorie(false).metIndicatieMaterieleHistorie(false).metIndicatieVerantwoording(false).maak();
        dienstbundelGroepen.add(dienstbundelGroep);

        final Set<HisPersoonBijhoudingModel> alleModellen = maakTestPersoon().getPersoonBijhoudingHistorie().getHistorie();

        // act
        final Collection gefilterdeModellen = CollectionUtils.select(alleModellen, predikaat);

        // assert
        assertThat(gefilterdeModellen.size(), is(1));
    }

    @Test
    public void testMaterieelHistorischLeverenMagAlleenMaterieel() {
        final Set<DienstbundelGroep> dienstbundelGroepen = new HashSet<>();
        final MagHistorieTonenPredikaat predikaat = new MagHistorieTonenPredikaat(dienstbundelGroepen, false);

        final ElementAttribuut element = new ElementAttribuut(TestElementBuilder.maker().metNaam(ElementEnum.PERSOON_BIJHOUDING).maak());
        final DienstbundelGroep dienstbundelGroep =
            TestDienstbundelGroepBuilder.maker().metGroep(element.getWaarde()).metIndicatieFormeleHistorie(false).
                metIndicatieMaterieleHistorie(true).metIndicatieVerantwoording(false).maak();
        dienstbundelGroepen.add(dienstbundelGroep);

        final Set<HisPersoonBijhoudingModel> alleModellen = maakTestPersoon().getPersoonBijhoudingHistorie().getHistorie();

        // act
        final Collection<HisPersoonBijhoudingModel> gefilterdeModellen = CollectionUtils.select(alleModellen, predikaat);

        // assert
        assertThat(gefilterdeModellen.size(), is(2));
        for (final HisPersoonBijhoudingModel hisPersoonBijhoudingModel : gefilterdeModellen) {
            assertThat(hisPersoonBijhoudingModel.getMaterieleHistorie().getDatumTijdVerval(), is(nullValue()));
        }
    }

    @Test
    public void testMaterieelHistorischLeverenMagAlleenFormeel() {
        final Set<DienstbundelGroep> dienstbundelGroepen = new HashSet<>();
        final MagHistorieTonenPredikaat predikaat = new MagHistorieTonenPredikaat(dienstbundelGroepen, false);

        final ElementAttribuut element = new ElementAttribuut(TestElementBuilder.maker().metNaam(ElementEnum.PERSOON_BIJHOUDING).maak());
        final DienstbundelGroep dienstbundelGroep =
            TestDienstbundelGroepBuilder.maker().metGroep(element.getWaarde()).metIndicatieFormeleHistorie(true).
                metIndicatieMaterieleHistorie(false).metIndicatieVerantwoording(false).maak();
        dienstbundelGroepen.add(dienstbundelGroep);

        final Set<HisPersoonBijhoudingModel> alleModellen = maakTestPersoon().getPersoonBijhoudingHistorie().getHistorie();

        // act
        final Collection<HisPersoonBijhoudingModel> gefilterdeModellen = CollectionUtils.select(alleModellen, predikaat);

        // assert
        assertThat(gefilterdeModellen.size(), is(2));
        for (final HisPersoonBijhoudingModel hisPersoonBijhoudingModel : gefilterdeModellen) {
            assertThat(hisPersoonBijhoudingModel.getMaterieleHistorie().getDatumEindeGeldigheid(), is(nullValue()));
        }
    }

    @Test
    public void testMaterieelHistorischLeverenMagMaterieelEnFormeel() {
        final Set<DienstbundelGroep> dienstbundelGroepen = new HashSet<>();
        final MagHistorieTonenPredikaat predikaat = new MagHistorieTonenPredikaat(dienstbundelGroepen, false);


        final ElementAttribuut element = new ElementAttribuut(TestElementBuilder.maker().metNaam(ElementEnum.PERSOON_BIJHOUDING).maak());
        final DienstbundelGroep dienstbundelGroep =
            TestDienstbundelGroepBuilder.maker().metGroep(element.getWaarde()).metIndicatieFormeleHistorie(true).
                metIndicatieMaterieleHistorie(true).metIndicatieVerantwoording(false).maak();
        dienstbundelGroepen.add(dienstbundelGroep);

        final Set<HisPersoonBijhoudingModel> alleModellen = maakTestPersoon().getPersoonBijhoudingHistorie().getHistorie();

        // act
        final Collection gefilterdeModellen = CollectionUtils.select(alleModellen, predikaat);

        // assert
        assertThat(gefilterdeModellen.size(), is(3));
    }

    @Test
    public void testGeenGroepen() {
        final Set<DienstbundelGroep> dienstbundelGroepen = new HashSet<>();
        final MagHistorieTonenPredikaat predikaat = new MagHistorieTonenPredikaat(dienstbundelGroepen, true);


        final Set<HisPersoonGeboorteModel> alleModellen = maakTestPersoon().getPersoonGeboorteHistorie().getHistorie();

        // act
        final Collection gefilterdeModellen = CollectionUtils.select(alleModellen, predikaat);

        // assert:
        assertThat(gefilterdeModellen.size(), is(2));
    }

    @Test
    public void testGeenGroepenMutatieLevering() {
        final Set<DienstbundelGroep> dienstbundelGroepen = new HashSet<>();
        final MagHistorieTonenPredikaat predikaat = new MagHistorieTonenPredikaat(dienstbundelGroepen, false);

        final Set<HisPersoonGeboorteModel> alleModellen = maakTestPersoon().getPersoonGeboorteHistorie().getHistorie();

        // act
        final Collection gefilterdeModellen = CollectionUtils.select(alleModellen, predikaat);

        // assert:
        assertThat(gefilterdeModellen.size(), is(1));
    }

    @Test
    public void testNietElementIdentificeerbaar() {

        final Set<DienstbundelGroep> dienstbundelGroepen = new HashSet<>();
        final MagHistorieTonenPredikaat predikaat = new MagHistorieTonenPredikaat(dienstbundelGroepen, false);

        final Set<SoortRelatieAttribuut> alleModellen = new HashSet<>();
        alleModellen.add(new SoortRelatieAttribuut(SoortRelatie.DUMMY));

        // act
        final Collection gefilterdeModellen = CollectionUtils.select(alleModellen, predikaat);

        // assert:
        assertThat(gefilterdeModellen.size(), is(0));
    }

    @Test
    public void testGeenHistorieMaarWelFormeelIvmMutatieLevering() {
        final Set<DienstbundelGroep> dienstbundelGroepen = new HashSet<>();
        final MagHistorieTonenPredikaat predikaat = new MagHistorieTonenPredikaat(dienstbundelGroepen, false);

        final ElementAttribuut element = new ElementAttribuut(TestElementBuilder.maker().metNaam(ElementEnum.PERSOON_BIJHOUDING).maak());
        final DienstbundelGroep dienstbundelGroep =
            TestDienstbundelGroepBuilder.maker().metGroep(element.getWaarde()).metIndicatieFormeleHistorie(true).
                metIndicatieMaterieleHistorie(false).metIndicatieVerantwoording(false).maak();
        dienstbundelGroepen.add(dienstbundelGroep);

        final Set<HisPersoonBijhoudingModel> alleModellen = maakTestPersoon().getPersoonBijhoudingHistorie().getHistorie();

        // act
        final Collection<HisPersoonBijhoudingModel> gefilterdeModellen = CollectionUtils.select(alleModellen, predikaat);

        // assert
        assertThat(gefilterdeModellen.size(), is(2));
        for (final HisPersoonBijhoudingModel hisPersoonBijhoudingModel : gefilterdeModellen) {
            assertThat(hisPersoonBijhoudingModel.getMaterieleHistorie().getDatumEindeGeldigheid(), is(nullValue()));
        }
    }

    private PersoonHisVolledig maakTestPersoon() {
        return new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
            .nieuwBijhoudingRecord(20130101, null, 20130101).bijhoudingspartij(3037).eindeRecord()
            .nieuwBijhoudingRecord(20130101, 20130801, 20130801).bijhoudingspartij(3037).eindeRecord()
            .nieuwBijhoudingRecord(20130801, null, 20130801).bijhoudingspartij(1000).eindeRecord()
            .nieuwGeboorteRecord(19780309).datumGeboorte(19780309)
            .gemeenteGeboorte(StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde())
            .landGebiedGeboorte(StatischeObjecttypeBuilder.LAND_NEDERLAND.getWaarde()).eindeRecord()
            .nieuwGeboorteRecord(20100101).datumGeboorte(19780308)
            .gemeenteGeboorte(StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde())
            .landGebiedGeboorte(StatischeObjecttypeBuilder.LAND_NEDERLAND.getWaarde()).eindeRecord().build();
    }
}
