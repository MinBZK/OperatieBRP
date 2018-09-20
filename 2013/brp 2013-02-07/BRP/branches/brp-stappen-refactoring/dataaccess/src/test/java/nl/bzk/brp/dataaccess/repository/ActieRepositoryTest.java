/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Aktenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijving;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingInschrijvingDoorGeboorteBericht;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisDocumentModel;
import org.junit.Test;

public class ActieRepositoryTest extends AbstractRepositoryTestCase {
    @Inject
    private ActieRepository actieRepository;

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Test
    public void saveActieMet1Bron() {
        ActieBericht actieBericht = maakActie(maakBronBericht(1, "akteNr", "docId", 4));
        ActieBronBericht bron = null;
        for (ActieBronBericht b : actieBericht.getBronnen()) {
            bron = b;
            break;
        }

        DocumentModel docModel = new DocumentModel(bron.getDocument());
        docModel.setDocumentStatusHis(StatusHistorie.A);

        ActieModel newModel = new ActieModel(actieBericht, null);
        newModel.getBronnen().add(new ActieBronModel(bron, newModel, docModel));

        ActieModel actieModel = actieRepository.opslaanNieuwActie(newModel);

        List<ActieBronModel> bronnen =
                em.createQuery("select brn FROM ActieBronModel brn WHERE brn.actie.id=:actieId", ActieBronModel.class)
                  .setParameter("actieId", actieModel.getID())
                  .getResultList();
        Assert.assertEquals(1, bronnen.size());
        Assert.assertNotNull(bronnen.get(0));
        Assert.assertNotNull(bronnen.get(0).getDocument());
        Assert.assertEquals("Geboorteakte", bronnen.get(0).getDocument().getSoort().getNaam().getWaarde());
        Assert.assertEquals("akteNr", bronnen.get(0).getDocument().getStandaard().getAktenummer().getWaarde());

        // test of dingen goed weggeschreven zijn in de historie
        List<HisDocumentModel> hisModel = em.createQuery(
                "select his FROM HisDocumentModel his WHERE his.document.id=:docId",
                HisDocumentModel.class)
                                            .setParameter("docId", bronnen.get(0).getDocument().getID())
                                            .getResultList();
        Assert.assertEquals(1, hisModel.size());
        Assert.assertNotNull(hisModel.get(0));
        Assert.assertEquals("akteNr", hisModel.get(0).getAktenummer().getWaarde());
        Assert.assertEquals(actieModel.getID(), hisModel.get(0).getFormeleHistorie().getActieInhoud().getID());
        Assert.assertNull(hisModel.get(0).getFormeleHistorie().getActieVerval());
        Assert.assertNull(hisModel.get(0).getFormeleHistorie().getDatumTijdVerval());
        Assert.assertEquals(actieModel.getTijdstipRegistratie().getWaarde(),
                            hisModel.get(0).getFormeleHistorie().getActieInhoud().getTijdstipRegistratie().getWaarde());
        Assert.assertEquals(bron.getDocument().getStandaard().getIdentificatie().getWaarde(),
                            hisModel.get(0).getIdentificatie().getWaarde());
    }

    @Test
    public void saveActieMetMeerdereBron() {
        ActieBronBericht bronb1 = maakBronBericht(1, "akteNr", "docId", 4);
        ActieBronBericht bronb2 = maakBronBericht(1, "akteN0", "docIddddd", 1);
        ActieBronBericht bronb3 = maakBronBericht(1, "akteN1", "docIdeeeeee", 2);

        DocumentModel docModel1 = new DocumentModel(bronb1.getDocument());
        docModel1.setDocumentStatusHis(StatusHistorie.A);
        DocumentModel docModel2 = new DocumentModel(bronb2.getDocument());
        docModel2.setDocumentStatusHis(StatusHistorie.A);
        DocumentModel docModel3 = new DocumentModel(bronb3.getDocument());
        docModel3.setDocumentStatusHis(StatusHistorie.A);

        ActieBericht actieBericht = maakActie(bronb1, bronb2, bronb3);

        ActieModel newModel = new ActieModel(actieBericht, null);
        newModel.getBronnen().add(new ActieBronModel(bronb1, newModel, docModel1));
        newModel.getBronnen().add(new ActieBronModel(bronb2, newModel, docModel2));
        newModel.getBronnen().add(new ActieBronModel(bronb3, newModel, docModel3));

        Assert.assertEquals(3, newModel.getBronnen().size());
        ActieModel actieModel = actieRepository.opslaanNieuwActie(newModel);
        em.flush();

        List<ActieBronModel> bronnenModel = em.createQuery("select brn FROM ActieBronModel brn " +
            "WHERE brn.actie.id=:actieId order by brn.document.standaard.identificatie.waarde", ActieBronModel.class)
                                              .setParameter("actieId", actieModel.getID()).getResultList();
        Assert.assertEquals(3, bronnenModel.size());
        Assert.assertEquals("Geboorteakte", bronnenModel.get(0).getDocument().getSoort().getNaam().getWaarde());
        Assert.assertEquals("Geboorteakte", bronnenModel.get(1).getDocument().getSoort().getNaam().getWaarde());
        Assert.assertEquals("Geboorteakte", bronnenModel.get(2).getDocument().getSoort().getNaam().getWaarde());

        Assert.assertEquals("docId", bronnenModel.get(0).getDocument().getStandaard().getIdentificatie().getWaarde());
        Assert.assertEquals("docIddddd",
                            bronnenModel.get(1).getDocument().getStandaard().getIdentificatie().getWaarde());
        Assert.assertEquals("docIdeeeeee",
                            bronnenModel.get(2).getDocument().getStandaard().getIdentificatie().getWaarde());

        // test of dingen goed weggeschreven zijn in de historie
        List<HisDocumentModel> hisModel = em.createQuery(
                "select his FROM HisDocumentModel his WHERE his.document.id in :docIds order by his.document.id",
                HisDocumentModel.class)
                                            .setParameter("docIds", Arrays.asList(
                                                    bronnenModel.get(0).getDocument().getID(),
                                                    bronnenModel.get(1).getDocument().getID(),
                                                    bronnenModel.get(2).getDocument().getID()))
                                            .getResultList();
        Assert.assertEquals(3, hisModel.size());
    }

    private ActieBericht maakActie(final ActieBronBericht... bronnen) {
        ActieBericht actieBericht = new ActieRegistratieNationaliteitBericht();
        actieBericht.setTijdstipRegistratie(new DatumTijd(new Timestamp(System.currentTimeMillis() - 1)));
        actieBericht.setPartij(em.find(Partij.class, (short) 4));
        if (bronnen != null) {
            List<ActieBronBericht> listBronnen = new ArrayList<ActieBronBericht>();
            for (ActieBronBericht b : bronnen) {
                if (b != null) {
                    listBronnen.add(b);
                }
            }
            actieBericht.setBronnen(listBronnen);
        }
        return actieBericht;
    }

    private AdministratieveHandelingBericht maakAdministratieveHandeling() {
        AdministratieveHandelingBericht adminh = new HandelingInschrijvingDoorGeboorteBericht();
        adminh.setPartij(em.find(Partij.class, (short) 4));
        adminh.setTijdstipOntlening(new DatumTijd(new Timestamp(System.currentTimeMillis() - 1)));

        return adminh;
    }

    private ActieBronBericht maakBronBericht(final int soortDoc, final String akteNr, final String docId,
                                             final int partijId)
    {
        ActieBronBericht bron = new ActieBronBericht();
        bron.setDocument(new DocumentBericht());
        bron.getDocument().setSoort(em.find(SoortDocument.class, (short) soortDoc));
        bron.getDocument().setStandaard(new DocumentStandaardGroepBericht());
        bron.getDocument().getStandaard().setAktenummer(new Aktenummer(akteNr));
        bron.getDocument().getStandaard().setIdentificatie(new DocumentIdentificatie(docId));
        bron.getDocument().getStandaard().setOmschrijving(new DocumentOmschrijving("omsch"));
        bron.getDocument().getStandaard().setPartij(em.find(Partij.class, (short) partijId));
        return bron;
    }

}
