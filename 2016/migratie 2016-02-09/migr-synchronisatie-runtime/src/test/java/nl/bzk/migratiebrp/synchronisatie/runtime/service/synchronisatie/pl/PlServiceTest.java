/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.jms.JMSException;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerBrpNaarLo3Service;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlServiceTest {

    @Mock
    private BrpDalService brpDalService;
    @Mock
    private ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service;
    // @Mock(name = "gbaBijhoudingen")
    // private Destination gbaBijhoudingQueue;
    // @Mock(name = "gbaBijhoudingenJmsTemplate")
    // private JmsTemplate jmsTemplate;
    // @Mock
    // private Session jmsSession;
    @Mock
    private BrpNotificator notificator;

    @InjectMocks
    private PlServiceImpl subject;

    @Test
    public void zoekPersoonslijstOpActueelAnummer() {
        final BrpPersoonslijst brpPersoonslijst = new BrpPersoonslijstBuilder().build();

        Mockito.when(brpDalService.zoekPersoonOpAnummer(1L)).thenReturn(brpPersoonslijst);

        final List<BrpPersoonslijst> result = subject.zoekPersoonslijstenOpActueelAnummer(1L);
        Assert.assertFalse(result.isEmpty());
        Assert.assertSame(brpPersoonslijst, result.get(0));

        Mockito.verify(brpDalService).zoekPersoonOpAnummer(1L);
        Mockito.verifyNoMoreInteractions(brpDalService, converteerBrpNaarLo3Service);
    }

    @Test
    public void zoekNietFoutievePersoonslijstOpActueelAnummer() {
        final BrpPersoonslijst brpPersoonslijst = new BrpPersoonslijstBuilder().build();

        Mockito.when(brpDalService.zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(1L)).thenReturn(brpPersoonslijst);

        final BrpPersoonslijst result = subject.zoekNietFoutievePersoonslijstOpActueelAnummer(1L);
        Assert.assertSame(brpPersoonslijst, result);

        Mockito.verify(brpDalService).zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(1L);
        Mockito.verifyNoMoreInteractions(brpDalService, converteerBrpNaarLo3Service);
    }

    @Test
    public void zoekPersoonslijstenOpHistorischAnummer() {
        final List<BrpPersoonslijst> brpPersoonslijst = new ArrayList<>();

        Mockito.when(brpDalService.zoekPersonenOpHistorischAnummer(1L)).thenReturn(brpPersoonslijst);

        final List<BrpPersoonslijst> result = subject.zoekPersoonslijstenOpHistorischAnummer(1L);
        Assert.assertSame(brpPersoonslijst, result);

        Mockito.verify(brpDalService).zoekPersonenOpHistorischAnummer(1L);
        Mockito.verifyNoMoreInteractions(brpDalService, converteerBrpNaarLo3Service);
    }

    @Test
    public void persisteerPersoonslijstNieuw() throws JMSException {
        final BrpPersoonslijst brpPersoonslijst = new BrpPersoonslijstBuilder().build();
        final Lo3Bericht loggingBericht = new Lo3Bericht("ref", Lo3BerichtenBron.SYNCHRONISATIE, new Timestamp(System.currentTimeMillis()), "data", true);

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setAdministratieveHandeling(new AdministratieveHandeling(new Partij("Test", 1), SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL));
        persoon.getAdministratieveHandeling().setId(3456L);
        persoon.setId(19);
        final Set<AdministratieveHandeling> nieuweAdministratieveHandelingen = new HashSet<>();
        nieuweAdministratieveHandelingen.add(persoon.getAdministratieveHandeling());
        Mockito.when(brpDalService.persisteerPersoonslijst(brpPersoonslijst, loggingBericht)).thenReturn(
            new PersoonslijstPersisteerResultaat(persoon, nieuweAdministratieveHandelingen));

        subject.persisteerPersoonslijst(brpPersoonslijst, loggingBericht);

        Mockito.verify(brpDalService).persisteerPersoonslijst(brpPersoonslijst, loggingBericht);
        Mockito.verifyNoMoreInteractions(brpDalService, converteerBrpNaarLo3Service);

        Mockito.verify(notificator).stuurGbaBijhoudingBerichten(Matchers.anySetOf(AdministratieveHandeling.class), Matchers.eq(19));
        Mockito.verifyNoMoreInteractions(notificator);
        //
        // final ArgumentCaptor<MessageCreator> messageCreator = ArgumentCaptor.forClass(MessageCreator.class);
        // Mockito.verify(jmsTemplate).send(same(gbaBijhoudingQueue), messageCreator.capture());
        // Mockito.verifyNoMoreInteractions(jmsTemplate);
        //
        // final String expectedMessage = "{\"administratieveHandelingId\":3456,\"bijgehoudenPersoonIds\":[19]}";
        // messageCreator.getValue().createMessage(jmsSession);
        // Mockito.verify(jmsSession).createTextMessage(expectedMessage);
        // Mockito.verifyNoMoreInteractions(jmsSession);
    }

    @Test
    public void persisteerPersoonslijstVervangen() throws JMSException {
        final BrpPersoonslijst brpPersoonslijst = new BrpPersoonslijstBuilder().build();
        final Lo3Bericht loggingBericht = new Lo3Bericht("ref", Lo3BerichtenBron.SYNCHRONISATIE, new Timestamp(System.currentTimeMillis()), "data", true);

        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setAdministratieveHandeling(new AdministratieveHandeling(new Partij("Test", 1), SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL));
        persoon.getAdministratieveHandeling().setId(3457L);
        persoon.setId(20);
        final Set<AdministratieveHandeling> nieuweAdministratieveHandelingen = new HashSet<>();
        nieuweAdministratieveHandelingen.add(persoon.getAdministratieveHandeling());
        Mockito.when(brpDalService.persisteerPersoonslijst(brpPersoonslijst, 1L, false, loggingBericht)).thenReturn(
            new PersoonslijstPersisteerResultaat(persoon, nieuweAdministratieveHandelingen));

        subject.persisteerPersoonslijst(brpPersoonslijst, 1L, false, loggingBericht);

        Mockito.verify(brpDalService).persisteerPersoonslijst(brpPersoonslijst, 1L, false, loggingBericht);
        Mockito.verifyNoMoreInteractions(brpDalService, converteerBrpNaarLo3Service);

        Mockito.verify(notificator).stuurGbaBijhoudingBerichten(Matchers.anySetOf(AdministratieveHandeling.class), Matchers.eq(20));
        Mockito.verifyNoMoreInteractions(notificator);
        //
        // final ArgumentCaptor<MessageCreator> messageCreator = ArgumentCaptor.forClass(MessageCreator.class);
        // Mockito.verify(jmsTemplate).send(same(gbaBijhoudingQueue), messageCreator.capture());
        // Mockito.verifyNoMoreInteractions(jmsTemplate);
        //
        // final String expectedMessage = "{\"administratieveHandelingId\":3457,\"bijgehoudenPersoonIds\":[20]}";
        // messageCreator.getValue().createMessage(jmsSession);
        // Mockito.verify(jmsSession).createTextMessage(expectedMessage);
        // Mockito.verifyNoMoreInteractions(jmsSession);
    }

    @Test
    public void converteerKandidaat() {
        final BrpPersoonslijst brpPersoonslijst = new BrpPersoonslijstBuilder().build();
        final Lo3PersoonslijstBuilder lo3Builder = new Lo3PersoonslijstBuilder();
        lo3Builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Persoon(1L, "Piet", "Weersman", 19770101, "0599", "0001", "M"),
            Lo3CategorieEnum.PERSOON)));
        final Lo3Persoonslijst lo3Persoonslijst = lo3Builder.build();

        Mockito.when(converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst)).thenReturn(lo3Persoonslijst);

        final String result = subject.converteerKandidaat(brpPersoonslijst);
        Assert.assertEquals(
            "0015601151011001000000000010210004Piet0240008Weersman03100081977010103200040599033000400010410001M6110001E8110004051881200071-X0001851000820120101861000820120102",
            result);

        Mockito.verify(converteerBrpNaarLo3Service).converteerBrpPersoonslijst(brpPersoonslijst);
        Mockito.verifyNoMoreInteractions(brpDalService, converteerBrpNaarLo3Service);
    }

    @Test
    public void converteerKandidaten() {
        final BrpPersoonslijst brpPersoonslijst = new BrpPersoonslijstBuilder().build();
        final Lo3PersoonslijstBuilder lo3Builder = new Lo3PersoonslijstBuilder();
        lo3Builder.persoonStapel(Lo3StapelHelper.lo3Stapel(Lo3StapelHelper.lo3Cat(
            Lo3StapelHelper.lo3Persoon(1L, "Piet", "Weersman", 19770101, "0599", "0001", "M"),
            Lo3CategorieEnum.PERSOON)));
        final Lo3Persoonslijst lo3Persoonslijst = lo3Builder.build();

        Mockito.when(converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst)).thenReturn(lo3Persoonslijst);

        final String[] result = subject.converteerKandidaten(Arrays.asList(brpPersoonslijst));
        Assert.assertEquals(
            "0015601151011001000000000010210004Piet0240008Weersman03100081977010103200040599033000400010410001M6110001E8110004051881200071-X0001851000820120101861000820120102",
            result[0]);

        Mockito.verify(converteerBrpNaarLo3Service).converteerBrpPersoonslijst(brpPersoonslijst);
        Mockito.verifyNoMoreInteractions(brpDalService, converteerBrpNaarLo3Service);
    }
}
