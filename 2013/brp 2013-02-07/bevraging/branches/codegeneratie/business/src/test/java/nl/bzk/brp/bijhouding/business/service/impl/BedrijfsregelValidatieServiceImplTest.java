/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bijhouding.business.bedrijfsregel.AbstractValidatie;
import nl.bzk.brp.bijhouding.business.bedrijfsregel.ValidatieResultaat;
import nl.bzk.brp.bijhouding.business.bedrijfsregel.impl.TestRegel1Validatie;
import nl.bzk.brp.bijhouding.business.service.BedrijfsregelValidatieService;
import nl.bzk.brp.bijhouding.business.service.exception.OnbekendeValidatieExceptie;
import nl.bzk.brp.bijhouding.business.service.exception.OngeldigValidatieResultaatExceptie;
import nl.bzk.brp.domein.ber.SoortBericht;
import nl.bzk.brp.domein.brm.Regel;
import nl.bzk.brp.domein.brm.Regeleffect;
import nl.bzk.brp.domein.brm.Regelimplementatie;
import nl.bzk.brp.domein.brm.Regelimplementatiesituatie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;


public class BedrijfsregelValidatieServiceImplTest {

    private static final Regel                  REGEL_1 = BedrijfsregelTestUtils.creeerRegel("TestRegel1");

    private final BedrijfsregelValidatieService service = new BedrijfsregelValidatieServiceImpl();

    @Mock
    private ApplicationContext                  applicationContext;

    @Mock
    private BerichtVerzoek<BerichtAntwoord>     verzoek;

    @Mock
    private Regelimplementatiesituatie          gedrag;

    @Test
    public void testCreeerValidatie() throws Exception {
        TestBerichtX berichtX = new TestBerichtX();
        TestRegel1Validatie validatieX = new TestRegel1Validatie();
        Mockito.when(applicationContext.getBean(REGEL_1.getCode(), AbstractValidatie.class)).thenReturn(validatieX);

        // test
        AbstractValidatie<?> validatie = service.creeerValidatie(REGEL_1, berichtX);

        Assert.assertSame(validatieX, validatie);
        Assert.assertSame(berichtX, validatie.getVerzoek());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCreeerValidatieOnbekendeRegel() {
        TestBerichtX berichtX = new TestBerichtX();
        Mockito.when(applicationContext.getBean(REGEL_1.getCode(), AbstractValidatie.class)).thenThrow(
                NoSuchBeanDefinitionException.class);

        // test
        try {
            service.creeerValidatie(REGEL_1, berichtX);
            Assert.fail("exeptie verwacht");
        } catch (OnbekendeValidatieExceptie e) {
        }
    }

    @Test
    public void testRoepValidatieAanMetResultaatConform() {
        @SuppressWarnings("unchecked")
        AbstractValidatie<BerichtVerzoek<BerichtAntwoord>> validatie = Mockito.mock(AbstractValidatie.class);

        Mockito.when(validatie.getVerzoek()).thenReturn(verzoek);
        Mockito.when(validatie.getFoutCode()).thenReturn(BerichtVerwerkingsFoutCode.PARTIJ_ONBEKEND);
        Mockito.when(validatie.voerUit()).thenReturn(ValidatieResultaat.CONFORM);

        // test
        BerichtVerwerkingsFout fout = service.roepValidatieAan(validatie, gedrag);

        Assert.assertSame(null, fout);
    }

    @Test
    public void testRoepValidatieAanMetResultaatAfwijkend() {
        @SuppressWarnings("unchecked")
        AbstractValidatie<BerichtVerzoek<BerichtAntwoord>> validatie = Mockito.mock(AbstractValidatie.class);

        Mockito.when(validatie.getVerzoek()).thenReturn(verzoek);
        Mockito.when(validatie.getFoutCode()).thenReturn(BerichtVerwerkingsFoutCode.PARTIJ_ONBEKEND);
        Mockito.when(validatie.voerUit()).thenReturn(ValidatieResultaat.AFWIJKEND);

        // test
        BerichtVerwerkingsFout fout = service.roepValidatieAan(validatie, gedrag);

        Assert.assertSame(BerichtVerwerkingsFoutCode.PARTIJ_ONBEKEND.getCode(), fout.getCode());
        Assert.assertSame(BerichtVerwerkingsFoutZwaarte.INFO, fout.getZwaarte());
    }

    @Test
    public void testRoepValidatieAanMetResultaatOngeldig() {
        AbstractValidatie<?> validatie = Mockito.mock(AbstractValidatie.class);

        Mockito.when(validatie.getFoutCode()).thenReturn(BerichtVerwerkingsFoutCode.PARTIJ_ONBEKEND);
        Mockito.when(validatie.voerUit()).thenReturn(null);

        // test
        try {
            service.roepValidatieAan(validatie, gedrag);
            Assert.fail("exceptie verwacht");
        } catch (OngeldigValidatieResultaatExceptie e) {
        }
    }

    @Test
    public void testRoepValidatieAanVoorOnbekendeValidatie() {

        // test
        BerichtVerwerkingsFout fout = service.roepValidatieAan(null, gedrag);

        Assert.assertSame(BerichtVerwerkingsFoutZwaarte.SYSTEEM, fout.getZwaarte());
        Assert.assertEquals(
                "Het is niet mogelijk om bedrijfsregel TestRegel1 te valideren bij een OpvragenPersoonVraag bericht",
                fout.getMelding());
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Regelimplementatie implementatie = Mockito.mock(Regelimplementatie.class);
        ReflectionTestUtils.setField(service, "applicationContext", applicationContext);

        Mockito.when(verzoek.getSoortBericht()).thenReturn(SoortBericht.OPVRAGENPERSOONVRAAG);
        Mockito.when(gedrag.getEffect()).thenReturn(Regeleffect.INFORMEREN);
        Mockito.when(gedrag.getRegelimplementatie()).thenReturn(implementatie);
        Mockito.when(implementatie.getRegel()).thenReturn(REGEL_1);
        Mockito.when(implementatie.getSoortBericht()).thenReturn(SoortBericht.OPVRAGENPERSOONVRAAG);
    }

}
