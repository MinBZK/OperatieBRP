/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service.impl;

import static nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut.JA;
import static nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut.NEE;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.DienstbundelGroep;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelGroepBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestElementBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@Deprecated
@Ignore //FIXME verplaats dit naar model
@RunWith(MockitoJUnitRunner.class)
public class DienstBundelTest {

//    @InjectMocks
//    private final AbonnementGroepService abonnementGroepService = new AbonnementGroepServiceImpl();

    @Test
    public final void groepZichtbaarIndienOnderliggendAttribuutZichtbaar() {
        final Dienst dienst = getDienst(ElementEnum.PERSOON_OVERLIJDEN);

        final Set<ElementEnum> enums = dienst.getDienstbundel().geefGeautoriseerdeGroepen();
        assertTrue(enums.size() == 1);
        assertTrue(enums.contains(ElementEnum.PERSOON_OVERLIJDEN));
    }

    @Test
    public final void identiteitGroepOokZichtbaarIndientStandaardGroepZichtbaar() {
        final Dienst dienst = getDienst(ElementEnum.HUWELIJK_STANDAARD);
        final Set<ElementEnum> enums = dienst.getDienstbundel().geefGeautoriseerdeGroepen();
        assertTrue(enums.size() == 2);
        assertTrue(enums.contains(ElementEnum.HUWELIJK_STANDAARD));
        assertTrue(enums.contains(ElementEnum.HUWELIJK_IDENTITEIT));
    }

    @Test
    public final void standaardGroepOokZichtbaarIndienIdentiteitGroepZichtbaar() {
        final Dienst dienst = getDienst(ElementEnum.HUWELIJK_IDENTITEIT);
        final Set<ElementEnum> enums = dienst.getDienstbundel().geefGeautoriseerdeGroepen();
        assertTrue(enums.size() == 2);
        assertTrue(enums.contains(ElementEnum.HUWELIJK_STANDAARD));
        assertTrue(enums.contains(ElementEnum.HUWELIJK_IDENTITEIT));
    }

    @Test
    public final void groepNietZichtbaarIndienGeenGroepVlaggen() {
        final Dienst dienst = getDienstMetGroep(false, false, false);
        final Set<ElementEnum> enums = dienst.getDienstbundel().geefGeautoriseerdeGroepen();
        assertTrue(enums.isEmpty());
    }

    @Test
    public final void groepZichtbaarDoorFormeleVlag() {
        final Dienst dienst = getDienstMetGroep(true, false, false);
        final Set<ElementEnum> enums = dienst.getDienstbundel().geefGeautoriseerdeGroepen();
        assertTrue(enums.size() == 1);
        assertTrue(enums.contains(ElementEnum.PERSOON_OVERLIJDEN));
    }

    @Test
    public final void groepZichtbaarDoorMaterieleVlag() {
        final Dienst dienst = getDienstMetGroep(false, true, false);
        final Set<ElementEnum> enums = dienst.getDienstbundel().geefGeautoriseerdeGroepen();
        assertTrue(enums.size() == 1);
        assertTrue(enums.contains(ElementEnum.PERSOON_OVERLIJDEN));
    }


    @Test
    public final void groepZichtbaarDoorVerantwoordingVlag() {
        final Dienst dienst = getDienstMetGroep(false, false, true);
        final Set<ElementEnum> enums = dienst.getDienstbundel().geefGeautoriseerdeGroepen();
        assertTrue(enums.size() == 1);
        assertTrue(enums.contains(ElementEnum.PERSOON_OVERLIJDEN));
    }

    @Test
    public void geeftGeautoriseerdeGerelateerdeObjectTypen() {
        //FIXME
//        final Abonnement abonnement = TestAbonnementBuilder.maker().maak();
//        final Set<AbonnementAttribuutModel> abonnementAttributen = new HashSet<>();
//        abonnementAttributen.add(new AbonnementAttribuutModel(new AbonnementAttribuut(abonnement), new ElementAttribuut(
//            TestElementBuilder.maker()
//                .metNaam(ElementEnum.GERELATEERDEERKENNER_PERSOON_GEBOORTE_GEMEENTECODE)
//                .metElementGroep(
//                    TestElementBuilder.maker()
//                        .metNaam(ElementEnum.GERELATEERDEERKENNER_PERSOON_GEBOORTE)
//                        .metElementObjectType(TestElementBuilder.maker()
//                            .metNaam(ElementEnum.GERELATEERDEERKENNER_PERSOON)
//                            .maak())
//                        .maak())
//                .maak()
//        )));
//        abonnement.setAbonnementAttributen(abonnementAttributen);
//
//        final Set<ElementEnum> enums = abonnementGroepService.geefGeautoriseerdeGerelateerdeObjectTypen(abonnement);
//
//        assertTrue(enums.size() == 1);
//        assertTrue(enums.contains(ElementEnum.GERELATEERDEERKENNER_PERSOON));
    }

    /**
     * maak een abonnement met daarin een attribuut dat behoort tot de gegeven groep
     */
    private Dienst getDienst(final ElementEnum groepNaam) {

        final Element element = TestElementBuilder.maker()
            .metElementGroep(TestElementBuilder.maker().metNaam(groepNaam).maak())
            .maak();
        final DienstbundelGroep dienstbundelGroep = TestDienstbundelGroepBuilder.maker().metGroep(element).maak();

        final Dienstbundel dienstbundel = TestDienstbundelBuilder.maker().metGroepen(dienstbundelGroep).maak();

        final Dienst dienst = TestDienstBuilder.maker().metDienstbundel(dienstbundel).maak();

        return dienst;
    }

    private Dienst getDienstMetGroep(final boolean jaNeeFormeleHistorie, final boolean jaNeeMaterieleHistorie,
        final boolean jaNeeVerantwoording)
    {
        final DienstbundelGroep dienstbundelGroep = TestDienstbundelGroepBuilder.maker().
                metIndicatieMaterieleHistorie(jaNeeMaterieleHistorie).
                metIndicatieFormeleHistorie(jaNeeFormeleHistorie).
                metIndicatieVerantwoording(jaNeeVerantwoording).maak();
        final Dienstbundel dienstbundel = TestDienstbundelBuilder.maker().metGroepen(dienstbundelGroep).maak();
        return TestDienstBuilder.maker().metDienstbundel(dienstbundel).maak();
    }
}
