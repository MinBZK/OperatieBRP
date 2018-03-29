/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.cache.LeveringsAutorisatieCache;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class AutoriseerAttributenObvAttribuutAutorisatieServiceImplTest {

    private Actie actieInhoud = TestVerantwoording.maakActie(1, DatumUtil.nuAlsZonedDateTime());

    @Mock
    private LeveringsAutorisatieCache leveringsAutorisatieCache;

    @InjectMocks
    private AutoriseerAttributenObvAttribuutAutorisatieServiceImpl autoriseerAangewezenAttribuutService;

    @Before
    public void voorTest() {
        final List<AttribuutElement> lijst = Lists.newArrayList(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE));
        Mockito.when(leveringsAutorisatieCache.geefGeldigeElementen(Mockito.any(), Mockito.any())).thenReturn(lijst);
        BrpNu.set();
    }

    @Test
    public final void attribuutGeautoriseerd() {
        final AttribuutElement element = getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE);
        final Dienst dienst = maakDienst(element);
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(dienst, Sets.newHashSet());
        berichtgegevens.setKandidaatRecords(Sets.newHashSet(
                berichtgegevens.getPersoonslijst().getModelIndex().geefAttributen(element).iterator().next().getParentRecord()
        ));
        autoriseerAangewezenAttribuutService.execute(berichtgegevens);
        final Collection<MetaAttribuut> attributen = berichtgegevens.getPersoonslijst().getModelIndex().
                geefAttributen(element);
        Assert.assertTrue(berichtgegevens.isGeautoriseerd(attributen.iterator().next()));
    }

    @Test
    public final void attribuutNietGeautoriseerdWantRecordWordtNietGeleverd() {
        final AttribuutElement element = getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE);
        final Dienst dienst = maakDienst(element);
        final Berichtgegevens berichtgegevens = maakBerichtgegevens(dienst, Sets.newHashSet());
        berichtgegevens.setKandidaatRecords(Sets.newHashSet());
        autoriseerAangewezenAttribuutService.execute(berichtgegevens);
        final Collection<MetaAttribuut> attributen = berichtgegevens.getPersoonslijst().getModelIndex().
                geefAttributen(element);
        Assert.assertFalse(berichtgegevens.isGeautoriseerd(attributen.iterator().next()));
    }

    private Berichtgegevens maakBerichtgegevens(Dienst dienst, final Set<AttribuutElement> scopeElementen) {
        return maakBerichtgegevens(dienst, scopeElementen, Rol.AFNEMER);
    }

    private Berichtgegevens maakBerichtgegevens(Dienst dienst, final Set<AttribuutElement> scopeElementen, final Rol rol) {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final Persoonslijst persoonslijst = new Persoonslijst(maakDummyPersoonObject(), 0L);
        final Autorisatiebundel autorisatiebundel = TestAutorisaties.bundelMetRol(rol, dienst);
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        maakBerichtParameters.setGevraagdeElementen(scopeElementen);
        return new Berichtgegevens(maakBerichtParameters, persoonslijst, new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT),
                autorisatiebundel, new StatischePersoongegevens());
    }

    private Dienst maakDienst(final AttribuutElement element) {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setId(1);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        leveringsautorisatie.setDienstbundelSet(Collections.singleton(dienstbundel));
        dienstbundel.setDienstSet(Collections.singleton(dienst));
        final DienstbundelGroep dienstbundelGroep = new DienstbundelGroep(dienstbundel, Element.parseId(element.getGroepId()), false, false,
                false);
        dienstbundel.setDienstbundelGroepSet(Collections.singleton(dienstbundelGroep));
        final Set<DienstbundelGroepAttribuut> dienstbundelGroepAttributen = new HashSet<>();
        dienstbundelGroep.setDienstbundelGroepAttribuutSet(dienstbundelGroepAttributen);

        dienstbundelGroepAttributen.add(new DienstbundelGroepAttribuut(dienstbundelGroep, Element.parseId(element.getId())));

        return dienst;
    }

    public MetaObject maakDummyPersoonObject() {
        //@formatter:off
        return TestBuilders.maakLeegPersoon(1)
             .metGroep()
                .metGroepElement(getGroepElement(Element.PERSOON_BIJHOUDING))
                .metRecord()
                    .metId(1)
                    .metActieInhoud(actieInhoud)
                    .metAttribuut(getAttribuutElement(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId()), NadereBijhoudingsaard.ACTUEEL
                        .getCode())
                .eindeRecord()
            .eindeGroep()
            .metObject()
                .metObjectElement(getObjectElement(Element.PERSOON_ADRES.getId()))
                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                    .metRecord()
                        .metId(1)
                        .metActieInhoud(actieInhoud)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId()), 123)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId()), "abc")
                        .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGGELDIGHEID.getId()), 20100203)
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on
    }
}
