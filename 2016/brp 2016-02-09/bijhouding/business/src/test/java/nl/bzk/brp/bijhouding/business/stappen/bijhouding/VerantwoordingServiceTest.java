/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.dataaccess.repository.VerantwoordingRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNaamgebruikBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingGeboorteInNederlandBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.DocumentHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VerantwoordingServiceTest {

    @InjectMocks
    private VerantwoordingService verantwoordingService;

    @Mock
    private VerantwoordingRepository verantwoordingRepository;

    private final BijhoudingBerichtContext berichtContext = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, null, null);

    @Test
    public void testSlaActieVerantwoordingenOp2VerschillendeDocumentenIn2Acties() {
        final DocumentBericht document1 = maakDocument("1");
        final ActieBericht actieBericht1 = new ActieRegistratieNaamgebruikBericht();
        final ActieBronBericht actieBronBericht1 = new ActieBronBericht();
        actieBronBericht1.setDocument(document1);
        actieBericht1.setBronnen(Arrays.asList(actieBronBericht1));

        final DocumentBericht document2 = maakDocument("2");
        final ActieBericht actieBericht2 = new ActieRegistratieGeboorteBericht();
        final ActieBronBericht actieBronBericht2 = new ActieBronBericht();
        actieBronBericht2.setDocument(document2);
        actieBericht2.setBronnen(Arrays.asList(actieBronBericht2));

        final ActieModel actieModel1 = maakActieModel();
        final ActieModel actieModel2 = maakActieModel();

        final Map<ActieBericht, ActieModel> mapPersistenteActies = new HashMap<>();
        mapPersistenteActies.put(actieBericht1, actieModel1);
        mapPersistenteActies.put(actieBericht2, actieModel2);

        final AdministratieveHandelingBericht administratieveHandelingBericht =
            new HandelingGeboorteInNederlandBericht();
        administratieveHandelingBericht.setActies(Arrays.asList(actieBericht1, actieBericht2));

        final DocumentModel documentModel1 = new DocumentModel(StatischeObjecttypeBuilder.bouwSoortDocument("iets"));

        when(verantwoordingRepository.slaDocumentOp(any(DocumentHisVolledigImpl.class))).thenReturn(documentModel1);

        verantwoordingService.slaActieVerantwoordingenOp(mapPersistenteActies,
            administratieveHandelingBericht,
            berichtContext
        );

        verify(verantwoordingRepository, times(2)).slaDocumentOp(
            any(DocumentHisVolledigImpl.class));
        verify(verantwoordingRepository, never()).haalDocumentOp(Mockito.anyLong());
        verify(verantwoordingRepository, times(2)).slaActieBronOp(any(ActieModel.class), any(DocumentModel.class));

        assertEquals(2, berichtContext.getBijgehoudenDocumenten().size());
        assertEquals(documentModel1, berichtContext.getBijgehoudenDocument("1"));
        assertEquals(documentModel1, berichtContext.getBijgehoudenDocument("2"));
    }

    @Test
    public void testSlaActieVerantwoordingenOp1DocumentIn2Acties() {
        final DocumentBericht document1 = maakDocument("1");
        final ActieBericht actieBericht1 = new ActieRegistratieNaamgebruikBericht();
        final ActieBronBericht actieBronBericht1 = new ActieBronBericht();
        actieBronBericht1.setDocument(document1);
        actieBericht1.setBronnen(Arrays.asList(actieBronBericht1));

        final ActieBericht actieBericht2 = new ActieRegistratieGeboorteBericht();
        final ActieBronBericht actieBronBericht2 = new ActieBronBericht();
        actieBronBericht2.setDocument(document1);
        actieBericht2.setBronnen(Arrays.asList(actieBronBericht2));

        final ActieModel actieModel1 = maakActieModel();
        final ActieModel actieModel2 = maakActieModel();

        final Map<ActieBericht, ActieModel> mapPersistenteActies = new HashMap<>();
        mapPersistenteActies.put(actieBericht1, actieModel1);
        mapPersistenteActies.put(actieBericht2, actieModel2);


        final AdministratieveHandelingBericht administratieveHandelingBericht =
                new HandelingGeboorteInNederlandBericht();
        administratieveHandelingBericht.setActies(Arrays.asList(actieBericht1, actieBericht2));

        final DocumentModel documentModel1 = new DocumentModel(StatischeObjecttypeBuilder.bouwSoortDocument("iets"));

        when(verantwoordingRepository.slaDocumentOp(any(DocumentHisVolledigImpl.class))).thenReturn(documentModel1);

        verantwoordingService.slaActieVerantwoordingenOp(mapPersistenteActies,
                administratieveHandelingBericht,
                berichtContext
        );

        verify(verantwoordingRepository, times(1)).slaDocumentOp(
                any(DocumentHisVolledigImpl.class));
        verify(verantwoordingRepository, never()).haalDocumentOp(Mockito.anyLong());
        verify(verantwoordingRepository, times(2)).slaActieBronOp(any(ActieModel.class), any(DocumentModel.class));

        assertEquals(1, berichtContext.getBijgehoudenDocumenten().size());
        assertEquals(documentModel1, berichtContext.getBijgehoudenDocument("1"));
    }

    @Test
    public void testSlaActieVerantwoordingenOp1DocumentIn2ActiesDocumentHeeftTechnischeSleutel() {
        final DocumentBericht document1 = maakDocument("1");
        document1.setObjectSleutel("101");
        final DocumentModel documentModel1 = new DocumentModel(StatischeObjecttypeBuilder.bouwSoortDocument("iets"));
        when(verantwoordingRepository.haalDocumentOp(101L)).thenReturn(documentModel1);
        final ActieBericht actieBericht1 = new ActieRegistratieNaamgebruikBericht();
        final ActieBronBericht actieBronBericht1 = new ActieBronBericht();
        actieBronBericht1.setDocument(document1);
        actieBericht1.setBronnen(Arrays.asList(actieBronBericht1));

        final ActieBericht actieBericht2 = new ActieRegistratieGeboorteBericht();
        final ActieBronBericht actieBronBericht2 = new ActieBronBericht();
        actieBronBericht2.setDocument(document1);
        actieBericht2.setBronnen(Arrays.asList(actieBronBericht2));

        final ActieModel actieModel1 = maakActieModel();
        final ActieModel actieModel2 = maakActieModel();

        final Map<ActieBericht, ActieModel> mapPersistenteActies = new HashMap<>();
        mapPersistenteActies.put(actieBericht1, actieModel1);
        mapPersistenteActies.put(actieBericht2, actieModel2);


        final AdministratieveHandelingBericht administratieveHandelingBericht =
                new HandelingGeboorteInNederlandBericht();
        administratieveHandelingBericht.setActies(Arrays.asList(actieBericht1, actieBericht2));


        when(verantwoordingRepository.slaDocumentOp(any(DocumentHisVolledigImpl.class))).thenReturn(documentModel1);

        verantwoordingService.slaActieVerantwoordingenOp(mapPersistenteActies,
                administratieveHandelingBericht,
                berichtContext
        );

        verify(verantwoordingRepository, never()).slaDocumentOp(
                any(DocumentHisVolledigImpl.class));
        verify(verantwoordingRepository, times(2)).haalDocumentOp(Mockito.anyLong());
        verify(verantwoordingRepository, times(2)).slaActieBronOp(any(ActieModel.class), any(DocumentModel.class));

        assertEquals(0, berichtContext.getBijgehoudenDocumenten().size());
    }

    @Test
    public void testSlaActieVerantwoordingenOp1DocumentIn1ActieDocumentHeeftTechnischeSleutelMaarZitNietInDeDatabase() {
        final DocumentBericht document1 = maakDocument("1");
        document1.setObjectSleutel("101");
        final DocumentModel documentModel1 = new DocumentModel(StatischeObjecttypeBuilder.bouwSoortDocument("iets"));
        when(verantwoordingRepository.haalDocumentOp(101L)).thenReturn(null);
        final ActieBericht actieBericht1 = new ActieRegistratieNaamgebruikBericht();
        final ActieBronBericht actieBronBericht1 = new ActieBronBericht();
        actieBronBericht1.setDocument(document1);
        actieBericht1.setBronnen(Arrays.asList(actieBronBericht1));

        final ActieModel actieModel1 = maakActieModel();

        final Map<ActieBericht, ActieModel> mapPersistenteActies = new HashMap<>();
        mapPersistenteActies.put(actieBericht1, actieModel1);

        final AdministratieveHandelingBericht administratieveHandelingBericht =
                new HandelingGeboorteInNederlandBericht();
        administratieveHandelingBericht.setActies(Arrays.asList(actieBericht1));


        when(verantwoordingRepository.slaDocumentOp(any(DocumentHisVolledigImpl.class))).thenReturn(documentModel1);
        List<ResultaatMelding> meldingen = verantwoordingService.slaActieVerantwoordingenOp(mapPersistenteActies,
            administratieveHandelingBericht,
            berichtContext
        );

        verify(verantwoordingRepository, never()).slaDocumentOp(
                any(DocumentHisVolledigImpl.class));
        verify(verantwoordingRepository, times(1)).haalDocumentOp(Mockito.anyLong());
        verify(verantwoordingRepository, never()).slaActieBronOp(any(ActieModel.class), any(DocumentModel.class));

        assertEquals(0, berichtContext.getBijgehoudenDocumenten().size());

        assertEquals(Regel.BRBY0014, meldingen.iterator().next().getRegel());
    }

    @Test
    public void testSlaActieVerantwoordingenOpActieHeeftGeenBronnen() {
        final ActieBericht actieBericht1 = new ActieRegistratieNaamgebruikBericht();
        actieBericht1.setBronnen(null);

        final ActieModel actieModel1 = maakActieModel();

        final Map<ActieBericht, ActieModel> mapPersistenteActies = new HashMap<>();
        mapPersistenteActies.put(actieBericht1, actieModel1);


        final AdministratieveHandelingBericht administratieveHandelingBericht =
                new HandelingGeboorteInNederlandBericht();
        administratieveHandelingBericht.setActies(Arrays.asList(actieBericht1));

        final BijhoudingResultaat resultaat =
                new BijhoudingResultaat(new ArrayList<Melding>());
        verantwoordingService.slaActieVerantwoordingenOp(mapPersistenteActies,
                administratieveHandelingBericht,
                berichtContext
        );

        verify(verantwoordingRepository, never()).slaDocumentOp(
                any(DocumentHisVolledigImpl.class));
        verify(verantwoordingRepository, never()).haalDocumentOp(Mockito.anyLong());
        verify(verantwoordingRepository, never()).slaActieBronOp(any(ActieModel.class), any(DocumentModel.class));

        assertEquals(0, berichtContext.getBijgehoudenDocumenten().size());

        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    private ActieModel maakActieModel() {
        final ActieModel actieModel = new ActieModel(
                null, null, null, null, null, DatumTijdAttribuut.nu(), null
        );
        return actieModel;
    }

    private DocumentBericht maakDocument(final String commId) {
        final DocumentBericht documentBericht = new DocumentBericht();
        documentBericht.setSoort(StatischeObjecttypeBuilder.bouwSoortDocument("Akte"));
        documentBericht.setCommunicatieID(commId);
        documentBericht.setStandaard(new DocumentStandaardGroepBericht());
        return documentBericht;
    }
}
