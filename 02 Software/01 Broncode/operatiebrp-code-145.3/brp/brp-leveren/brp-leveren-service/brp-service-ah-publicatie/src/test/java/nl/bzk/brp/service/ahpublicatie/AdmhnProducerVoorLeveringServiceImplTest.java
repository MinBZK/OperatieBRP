/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.ahpublicatie;

import static org.mockito.Mockito.never;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusLeveringAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.service.dalapi.AdministratieveHandelingRepository;
import nl.bzk.brp.service.dalapi.TeLeverenHandelingDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * AdmhnProducerVoorLeveringServiceImpl.
 */
@RunWith(MockitoJUnitRunner.class)
public class AdmhnProducerVoorLeveringServiceImplTest {

    @Mock
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    @Mock
    private AdmhndPublicatieVoorLeveringService admhndPublicatieVoorLeveringService;

    @InjectMocks
    private AdmhndProducerVoorLeveringServiceImpl admhnProducerVoorLeveringService;

    @Captor
    private ArgumentCaptor<Set<Long>> repositoryCapture;
    @Captor
    private ArgumentCaptor<Long> markeerAlsVerwerktCapture;
    @Captor
    private ArgumentCaptor<Set<String>> jmsMessagesCapture;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(admhnProducerVoorLeveringService, "maxHandelingenInLevering", 10);
    }

    @Test
    public void testPublicatieHappyFlow() {

        final TeLeverenHandelingDTO handeling1 = maakHandeling(1L, 1L, null, new Date());
        final TeLeverenHandelingDTO handeling2 = maakHandeling(2L, 2L, null, new Date());

        final List<TeLeverenHandelingDTO> handelingenVoorPublicatie = Lists.newArrayList(handeling1, handeling2);

        Mockito.when(administratieveHandelingRepository.geefHandelingenVoorAdmhndPublicatie()).thenReturn(handelingenVoorPublicatie);

        //test
        admhnProducerVoorLeveringService.produceerHandelingenVoorLevering();
        //capture
        Mockito.verify(administratieveHandelingRepository).zetHandelingenStatusInLevering(repositoryCapture.capture());
        Mockito.verify(admhndPublicatieVoorLeveringService).publiceerHandelingen(jmsMessagesCapture.capture());
        //assert
        Assert.assertEquals(2, repositoryCapture.getValue().size());
        Assert.assertEquals(2, jmsMessagesCapture.getValue().size());
    }

    @Test
    public void testPublicatieFilterHandelingenInLevering() {

        final TeLeverenHandelingDTO handeling1 = maakHandeling(1L, 1L, StatusLeveringAdministratieveHandeling.IN_LEVERING, new Date());
        final TeLeverenHandelingDTO handeling2 = maakHandeling(2L, 2L, StatusLeveringAdministratieveHandeling.IN_LEVERING, new Date());

        final List<TeLeverenHandelingDTO> handelingenVoorPublicatie = Lists.newArrayList(handeling1, handeling2);

        Mockito.when(administratieveHandelingRepository.geefHandelingenVoorAdmhndPublicatie()).thenReturn(handelingenVoorPublicatie);

        //test
        admhnProducerVoorLeveringService.produceerHandelingenVoorLevering();
        //verify
        Mockito.verify(administratieveHandelingRepository, never()).zetHandelingenStatusInLevering(Mockito.anySetOf(Long.class));
        Mockito.verifyZeroInteractions(admhndPublicatieVoorLeveringService);
    }

    @Test
    public void testPublicatieFilterPersoonAlInLevering() {

        final TeLeverenHandelingDTO handeling1 = maakHandeling(1L, 1L, null, new Date());
        final TeLeverenHandelingDTO handeling2 = maakHandeling(1L, 2L, null, new Date());
        final TeLeverenHandelingDTO handeling3 = maakHandeling(2L, 2L, StatusLeveringAdministratieveHandeling.IN_LEVERING, new Date());

        final List<TeLeverenHandelingDTO> handelingenVoorPublicatie = Lists.newArrayList(handeling1, handeling2, handeling3);

        Mockito.when(administratieveHandelingRepository.geefHandelingenVoorAdmhndPublicatie()).thenReturn(handelingenVoorPublicatie);

        //test
        admhnProducerVoorLeveringService.produceerHandelingenVoorLevering();
        //verify
        Mockito.verify(administratieveHandelingRepository, never()).zetHandelingenStatusInLevering(Mockito.anySetOf(Long.class));
        Mockito.verifyZeroInteractions(admhndPublicatieVoorLeveringService);
    }

    @Test
    public void testPublicatieFilterPersoonAlInLeveringEn1Niet() {

        final TeLeverenHandelingDTO handeling1 = maakHandeling(1L, 1L, null, new Date());
        final TeLeverenHandelingDTO handeling2 = maakHandeling(1L, 2L, null, new Date());
        final TeLeverenHandelingDTO handeling3 = maakHandeling(2L, 2L, StatusLeveringAdministratieveHandeling.IN_LEVERING, new Date());
        final TeLeverenHandelingDTO handeling4 = maakHandeling(3L, 3L, null, new Date());

        final List<TeLeverenHandelingDTO> handelingenVoorPublicatie = Lists.newArrayList(handeling1, handeling2, handeling3, handeling4);

        Mockito.when(administratieveHandelingRepository.geefHandelingenVoorAdmhndPublicatie()).thenReturn(handelingenVoorPublicatie);

        //test
        admhnProducerVoorLeveringService.produceerHandelingenVoorLevering();
        //capture
        Mockito.verify(administratieveHandelingRepository).zetHandelingenStatusInLevering(repositoryCapture.capture());
        Mockito.verify(admhndPublicatieVoorLeveringService).publiceerHandelingen(jmsMessagesCapture.capture());
        //assert
        Assert.assertEquals(1, repositoryCapture.getValue().size());
        Assert.assertEquals(1, jmsMessagesCapture.getValue().size());
    }

    @Test
    public void testPublicatieFilterPersoonAlInLeveringEn1NietMaarBovenMaxInLevering() {

        ReflectionTestUtils.setField(admhnProducerVoorLeveringService, "maxHandelingenInLevering", 0);

        final TeLeverenHandelingDTO handeling1 = maakHandeling(1L, 1L, null, new Date());
        final TeLeverenHandelingDTO handeling2 = maakHandeling(1L, 2L, null, new Date());
        final TeLeverenHandelingDTO handeling3 = maakHandeling(2L, 2L, StatusLeveringAdministratieveHandeling.IN_LEVERING, new Date());
        final TeLeverenHandelingDTO handeling4 = maakHandeling(3L, 3L, null, new Date());

        final List<TeLeverenHandelingDTO> handelingenVoorPublicatie = Lists.newArrayList(handeling1, handeling2, handeling3, handeling4);

        Mockito.when(administratieveHandelingRepository.geefHandelingenVoorAdmhndPublicatie()).thenReturn(handelingenVoorPublicatie);

        //test
        admhnProducerVoorLeveringService.produceerHandelingenVoorLevering();
        //verify
        Mockito.verify(administratieveHandelingRepository, never()).zetHandelingenStatusInLevering(Mockito.anySetOf(Long.class));
        Mockito.verifyZeroInteractions(admhndPublicatieVoorLeveringService);
    }

    @Test
    public void testPublicatieFilterZelfdePersonenInHandelingenNietInLevering() {

        final TeLeverenHandelingDTO handeling1 = maakHandeling(1L, 1L, null, new Date());
        final TeLeverenHandelingDTO handeling2 = maakHandeling(1L, 2L, null, new Date());
        final TeLeverenHandelingDTO handeling3 = maakHandeling(2L, 2L, null, new Date());
        final TeLeverenHandelingDTO handeling4 = maakHandeling(3L, 3L, null, new Date());

        final List<TeLeverenHandelingDTO> handelingenVoorPublicatie = Lists.newArrayList(handeling1, handeling2, handeling3, handeling4);

        Mockito.when(administratieveHandelingRepository.geefHandelingenVoorAdmhndPublicatie()).thenReturn(handelingenVoorPublicatie);

        //test
        admhnProducerVoorLeveringService.produceerHandelingenVoorLevering();
        //capture
        Mockito.verify(administratieveHandelingRepository).zetHandelingenStatusInLevering(repositoryCapture.capture());
        Mockito.verify(admhndPublicatieVoorLeveringService).publiceerHandelingen(jmsMessagesCapture.capture());
        //assert
        Assert.assertEquals(2, repositoryCapture.getValue().size());
        Assert.assertEquals(2, jmsMessagesCapture.getValue().size());
    }


    private TeLeverenHandelingDTO maakHandeling(final Long admhndId, final Long bijgehoudenPersoon, final StatusLeveringAdministratieveHandeling status,
                                                final Date tsReg) {
        final TeLeverenHandelingDTO handeling1 = new TeLeverenHandelingDTO();
        handeling1.setAdmhndId(admhndId);
        handeling1.setStatus(status);
        if (bijgehoudenPersoon != null) {
            handeling1.setBijgehoudenPersoon(bijgehoudenPersoon);
        }
        handeling1.setTijdstipRegistratie(ZonedDateTime.ofInstant(tsReg.toInstant(), DatumUtil.BRP_ZONE_ID));
        return handeling1;
    }
}
