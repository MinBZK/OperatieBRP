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
import nl.bzk.brp.model.attribuuttype.Aktenummer;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.DocumentIdentificatie;
import nl.bzk.brp.model.attribuuttype.DocumentOmschrijving;
import nl.bzk.brp.model.groep.operationeel.historisch.DocumentStandaardHisModel;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BronBericht;
import nl.bzk.brp.model.objecttype.bericht.DocumentBericht;
import nl.bzk.brp.model.objecttype.bericht.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.BronModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortDocument;
import org.junit.Test;

public class ActieRepositoryTest extends AbstractRepositoryTestCase {
    @Inject
    private ActieRepository actieRepository;

//    @Inject
//    private BronRepository bronRepository;
//
//    @Inject
//    private DocumentRepository documentRepository;
//    @Inject
//    private ReferentieDataRepository referentieDataRepository;

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager   em;

    @Test
    public void saveActieMet1Bron() {
        ActieBericht actieBericht = maakActie(maakBronBericht(1, "akteNr", "docId", 4));
        BronBericht bron = null;
        for (BronBericht b : actieBericht.getBronnen()) {
            bron = b;
            break;
        }

        ActieModel newModel = new ActieModel(actieBericht);
        ActieModel actieModel = actieRepository.opslaanNieuwActie(newModel);

//        documentRepository.save(bron.getDocument());
//        bronRepository.save(bron);
        List<BronModel> bronnen = em.createQuery("select brn FROM BronModel brn WHERE brn.actie.id=:actieId", BronModel.class)
                .setParameter("actieId", actieModel.getId())
                .getResultList();
        Assert.assertEquals(1, bronnen.size());
        Assert.assertNotNull(bronnen.get(0));
        Assert.assertNotNull(bronnen.get(0).getDocument());
        Assert.assertEquals("Geboorte Akte", bronnen.get(0).getDocument().getSoort().getNaam().getWaarde());
        Assert.assertEquals("akteNr", bronnen.get(0).getDocument().getStandaard().getAktenummer().getWaarde());

        // test of dingen goed weggeschreven zijn in de historie
        List<DocumentStandaardHisModel> hisModel = em.createQuery(
                "select his FROM DocumentStandaardHisModel his WHERE his.document.id=:docId",
                        DocumentStandaardHisModel.class)
                .setParameter("docId", bronnen.get(0).getDocument().getId())
                .getResultList();
        Assert.assertEquals(1, hisModel.size());
        Assert.assertNotNull(hisModel.get(0));
        Assert.assertEquals("akteNr", hisModel.get(0).getAktenummer().getWaarde());
        Assert.assertEquals(actieModel.getId(), hisModel.get(0).getActieInhoud().getId());
        Assert.assertNull(hisModel.get(0).getActieVerval());
        Assert.assertNull(hisModel.get(0).getDatumTijdVerval());
        Assert.assertEquals(actieModel.getTijdstipRegistratie().getWaarde(),
                    hisModel.get(0).getDatumTijdRegistratie().getWaarde());
        Assert.assertEquals(bron.getDocument().getStandaard().getIdentificatie().getWaarde(),
                    hisModel.get(0).getIdentificatie().getWaarde());
    }

    @Test
    public void saveActieMetMeerdereBron() {
        ActieBericht actieBericht = maakActie(
            maakBronBericht(1, "akteNr", "docId", 4),
            maakBronBericht(1, "akteN0", "docIddddd", 1),
            maakBronBericht(2, "akteN1", "docIdeeeeee", 2)
        );

        ActieModel newModel = new ActieModel(actieBericht);
        Assert.assertEquals(3, newModel.getBronnen().size());
        ActieModel actieModel = actieRepository.opslaanNieuwActie(newModel);
        em.flush();

        List<BronModel> bronnenModel = em.createQuery("select brn FROM BronModel brn WHERE brn.actie.id=:actieId order by id", BronModel.class)
                .setParameter("actieId", actieModel.getId())
                .getResultList();
        Assert.assertEquals(3, bronnenModel.size());
        Assert.assertEquals("Geboorte Akte", bronnenModel.get(0).getDocument().getSoort().getNaam().getWaarde());
        Assert.assertEquals("Geboorte Akte", bronnenModel.get(1).getDocument().getSoort().getNaam().getWaarde());
        Assert.assertEquals("Trouw Akte", bronnenModel.get(2).getDocument().getSoort().getNaam().getWaarde());

        Assert.assertEquals("docId", bronnenModel.get(0).getDocument().getStandaard().getIdentificatie().getWaarde());
        Assert.assertEquals("docIddddd", bronnenModel.get(1).getDocument().getStandaard().getIdentificatie().getWaarde());
        Assert.assertEquals("docIdeeeeee", bronnenModel.get(2).getDocument().getStandaard().getIdentificatie().getWaarde());

        // test of dingen goed weggeschreven zijn in de historie
        List<DocumentStandaardHisModel> hisModel = em.createQuery(
                "select his FROM DocumentStandaardHisModel his WHERE his.document.id in :docIds order by his.document.id",
                        DocumentStandaardHisModel.class)
                .setParameter("docIds", Arrays.asList(
                        bronnenModel.get(0).getDocument().getId(),
                        bronnenModel.get(1).getDocument().getId(),
                        bronnenModel.get(2).getDocument().getId()
            ))
            .getResultList();
        Assert.assertEquals(3, hisModel.size());
    }

    private ActieBericht maakActie(final BronBericht... bronnen) {
        ActieBericht actieBericht = new ActieBericht();
        actieBericht.setTijdstipRegistratie(new DatumTijd(new Timestamp(System.currentTimeMillis() - 1)));
        actieBericht.setSoort(SoortActie.REGISTRATIE_NATIONALITEIT);
        actieBericht.setPartij(em.find(Partij.class, (short) 4));
        if (bronnen != null) {
            List<BronBericht> listBronnen = new ArrayList<BronBericht>();
            for (BronBericht  b: bronnen) {
                if (b != null) {
                    listBronnen.add(b);
                }
            }
            actieBericht.setBronnen(listBronnen);
        }
        return actieBericht;
    }

    private BronBericht maakBronBericht(final int soortDoc, final String akteNr, final String docId, final int partijId) {
        BronBericht bron = new BronBericht();
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
