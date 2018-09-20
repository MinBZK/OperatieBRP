/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.zoeken;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerBrpNaarLo3Service;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.PersoonRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.BrpPersoonslijstMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

@Ignore("Cashed op een verify error. PowerMock dingetje; test herschrijven")
@RunWith(PowerMockRunner.class)
@PrepareForTest({BrpPersoonslijstMapper.class, BrpPersoonslijst.class })
public class ZoekPersoonFilterImplTest {

    private PersoonRepository persoonRepository;
    private BrpPersoonslijstMapper brpPersoonslijstMapper;
    private ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service;
    private ZoekPersoonFilterImpl subject;

    @Before
    public void setup() {
        brpPersoonslijstMapper = PowerMockito.mock(BrpPersoonslijstMapper.class);
        persoonRepository = Mockito.mock(PersoonRepository.class);
        converteerBrpNaarLo3Service = Mockito.mock(ConverteerBrpNaarLo3Service.class);

        subject = new ZoekPersoonFilterImpl();
        ReflectionTestUtils.setField(subject, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(subject, "brpPersoonslijstMapper", brpPersoonslijstMapper);
        ReflectionTestUtils.setField(subject, "converteerBrpNaarLo3Service", converteerBrpNaarLo3Service);
    }

    @Test
    public void debug() {
        final Lo3CategorieWaarde filter01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        filter01.addElement(Lo3ElementEnum.ELEMENT_0210, "April");
        filter01.addElement(Lo3ElementEnum.ELEMENT_0230, "");
        final List<Lo3CategorieWaarde> filter = Arrays.asList(filter01);
        final String filterTekst = Lo3Inhoud.formatInhoud(filter);
        System.out.println("Filter: " + filterTekst);
    }

    @Test
    public void testFilterGeen() throws BerichtSyntaxException {
        final Lo3CategorieWaarde filter01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        filter01.addElement(Lo3ElementEnum.ELEMENT_0210, "Jaap");
        filter01.addElement(Lo3ElementEnum.ELEMENT_0230, "");
        filter01.addElement(Lo3ElementEnum.ELEMENT_0240, "Smorensen");
        final List<Lo3CategorieWaarde> filter = Arrays.asList(filter01);
        final String filterTekst = Lo3Inhoud.formatInhoud(filter);
        System.out.println("Filter: " + filterTekst);

        final Persoon persoon1 = setupPersoon("Jaap", "van der", "Smorensen");
        final Persoon persoon2 = setupPersoon("Jozef", null, "Smorensen");
        final Persoon persoon3 = setupPersoon("Jaap", "van der", "Proper");

        final List<Persoon> personen = Arrays.asList(persoon1, persoon2, persoon3);

        final List<GevondenPersoon> result = subject.filter(personen, filterTekst);
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void testFilterEen() throws BerichtSyntaxException {
        final Lo3CategorieWaarde filter01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        filter01.addElement(Lo3ElementEnum.ELEMENT_0210, "Jozef");
        filter01.addElement(Lo3ElementEnum.ELEMENT_0230, "");
        filter01.addElement(Lo3ElementEnum.ELEMENT_0240, "Smorensen");
        final List<Lo3CategorieWaarde> filter = Arrays.asList(filter01);
        final String filterTekst = Lo3Inhoud.formatInhoud(filter);
        System.out.println("Filter: " + filterTekst);

        final Persoon persoon1 = setupPersoon("Jaap", "van der", "Smorensen");
        final Persoon persoon2 = setupPersoon("Jozef", null, "Smorensen");
        final Persoon persoon3 = setupPersoon("Jaap", "van der", "Proper");

        final List<Persoon> personen = Arrays.asList(persoon1, persoon2, persoon3);

        final List<GevondenPersoon> result = subject.filter(personen, filterTekst);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void testFilterMeerdere() throws BerichtSyntaxException {
        final Lo3CategorieWaarde filter01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        filter01.addElement(Lo3ElementEnum.ELEMENT_0210, "Jaap");
        final List<Lo3CategorieWaarde> filter = Arrays.asList(filter01);
        final String filterTekst = Lo3Inhoud.formatInhoud(filter);
        System.out.println("Filter: " + filterTekst);

        final Persoon persoon1 = setupPersoon("Jaap", "van der", "Smorensen");
        final Persoon persoon2 = setupPersoon("Jozef", null, "Smorensen");
        final Persoon persoon3 = setupPersoon("Jaap", "van der", "Proper");
        final Persoon persoon4 = setupPersoon("Jaap", "van der", "Erperson");

        final List<Persoon> personen = Arrays.asList(persoon1, persoon2, persoon3, persoon4);

        final List<GevondenPersoon> result = subject.filter(personen, filterTekst);
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
    }

    private Persoon setupPersoon(final String voornamen, final String voorvoegsel, final String achternaam) {
        final Persoon persoon = Mockito.mock(Persoon.class);
        final BrpPersoonslijst brpPersoonslijst = Mockito.mock(BrpPersoonslijst.class);

        Mockito.when(persoonRepository.findOnderzoekenVoorPersoon(Matchers.same(persoon))).thenReturn(new ArrayList<Onderzoek>());
        Mockito.when(brpPersoonslijstMapper.mapNaarMigratie(Matchers.same(persoon), Matchers.any(BrpOnderzoekMapper.class))).thenReturn(brpPersoonslijst);

        final Lo3CategorieWaarde cat01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0210, voornamen);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0230, voorvoegsel);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0240, achternaam);
        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(cat01);
        final Lo3Persoonslijst lo3Persoonslijst = new Lo3PersoonslijstParser().parse(categorieen);

        Mockito.when(converteerBrpNaarLo3Service.converteerBrpPersoonslijst(Matchers.same(brpPersoonslijst))).thenReturn(lo3Persoonslijst);
        return persoon;
    }

    @Test
    public void testVoldoetAanFilterOk() throws ReflectiveOperationException {
        final Lo3CategorieWaarde cat01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        cat01.addElement(Lo3ElementEnum.ELEMENT_0210, "Jaap");
        cat01.addElement(Lo3ElementEnum.ELEMENT_0240, "Smorensen");
        final Lo3CategorieWaarde cat51a = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 1);
        cat51a.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        final Lo3CategorieWaarde cat51b = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 2);
        cat51b.addElement(Lo3ElementEnum.ELEMENT_0110, "1231231232");
        final Lo3CategorieWaarde cat02 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_02, 0, 0);
        cat02.addElement(Lo3ElementEnum.ELEMENT_0110, "5664565555");
        cat02.addElement(Lo3ElementEnum.ELEMENT_0210, "Josephine");
        cat02.addElement(Lo3ElementEnum.ELEMENT_0240, "Porper");

        final Lo3CategorieWaarde fil01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        fil01.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        fil01.addElement(Lo3ElementEnum.ELEMENT_0230, "");
        fil01.addElement(Lo3ElementEnum.ELEMENT_0240, "Smorensen");
        final Lo3CategorieWaarde fil51 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_51, 0, 1);
        fil51.addElement(Lo3ElementEnum.ELEMENT_0110, "1231231232");

        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(cat01, cat51a, cat51b, cat02);
        final List<Lo3CategorieWaarde> filter = Arrays.asList(fil01, fil51);

        Assert.assertTrue(callVoldoetAanFilter(categorieen, filter));
    }

    private boolean callVoldoetAanFilter(final List<Lo3CategorieWaarde> categorieen, final List<Lo3CategorieWaarde> filter)
        throws ReflectiveOperationException
    {
        final Method method = subject.getClass().getDeclaredMethod("voldoetAanFilter", new Class<?>[] {List.class, List.class });
        method.setAccessible(true);

        return (Boolean) method.invoke(subject, categorieen, filter);
    }

    @Test
    public void testVoldoetAanFilterNokWaarde() throws ReflectiveOperationException {
        final Lo3CategorieWaarde cat01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        cat01.addElement(Lo3ElementEnum.ELEMENT_0210, "Jaap");
        cat01.addElement(Lo3ElementEnum.ELEMENT_0240, "Smorensen");

        final Lo3CategorieWaarde fil01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        fil01.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        fil01.addElement(Lo3ElementEnum.ELEMENT_0210, "Jozef");
        fil01.addElement(Lo3ElementEnum.ELEMENT_0240, "Smorensen");

        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(cat01);
        final List<Lo3CategorieWaarde> filter = Arrays.asList(fil01);

        Assert.assertFalse(callVoldoetAanFilter(categorieen, filter));
    }

    @Test
    public void testVoldoetAanFilterNokLeeg() throws ReflectiveOperationException {
        final Lo3CategorieWaarde cat01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        cat01.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        cat01.addElement(Lo3ElementEnum.ELEMENT_0210, "Jaap");
        cat01.addElement(Lo3ElementEnum.ELEMENT_0240, "Smorensen");

        final Lo3CategorieWaarde fil01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        fil01.addElement(Lo3ElementEnum.ELEMENT_0110, "1234567890");
        fil01.addElement(Lo3ElementEnum.ELEMENT_0210, "");
        fil01.addElement(Lo3ElementEnum.ELEMENT_0240, "Smorensen");

        final List<Lo3CategorieWaarde> categorieen = Arrays.asList(cat01);
        final List<Lo3CategorieWaarde> filter = Arrays.asList(fil01);

        Assert.assertFalse(callVoldoetAanFilter(categorieen, filter));
    }

}
