/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AktenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.bericht.kern.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.DocumentHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisDocumentModel;
import org.junit.Assert;
import org.junit.Test;

public class VerantwoordingRepositoryTest extends AbstractRepositoryTestCase {

    private static final String AKTENR = "aktenr";
    private static final String DOCID = "docid";

    @Inject
    private VerantwoordingRepository verantwoordingRepository;

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Test
    public void testSlaDocumentOp() {
        final SoortDocument soortDocument = em.find(SoortDocument.class, (short) 1);
        final DocumentStandaardGroepBericht standaard = new DocumentStandaardGroepBericht();
        standaard.setAktenummer(new AktenummerAttribuut(AKTENR));
        standaard.setIdentificatie(new DocumentIdentificatieAttribuut(DOCID));
        standaard.setPartij(new PartijAttribuut(em.find(Partij.class, (short) 1)));
        standaard.setDatumTijdRegistratie(DatumTijdAttribuut.nu());

        final DocumentHisVolledigImpl documentHisVolledig = new DocumentHisVolledigImpl(new SoortDocumentAttribuut(soortDocument));
        documentHisVolledig.getDocumentHistorie().voegToe(new HisDocumentModel(documentHisVolledig, standaard, em.find(ActieModel.class, 101L)));
        final DocumentModel documentModel = verantwoordingRepository.slaDocumentOp(documentHisVolledig);
        Assert.assertNotNull(documentModel);
    }

    @Test
    public void testSlaActieBronOp() {
        final SoortDocument soortDocument = em.find(SoortDocument.class, (short) 1);
        final DocumentStandaardGroepBericht standaard = new DocumentStandaardGroepBericht();
        standaard.setAktenummer(new AktenummerAttribuut(AKTENR));
        standaard.setIdentificatie(new DocumentIdentificatieAttribuut(DOCID));
        standaard.setPartij(new PartijAttribuut(em.find(Partij.class, (short) 1)));
        standaard.setDatumTijdRegistratie(DatumTijdAttribuut.nu());

        final DocumentHisVolledigImpl documentHisVolledig = new DocumentHisVolledigImpl(new SoortDocumentAttribuut(soortDocument));
        documentHisVolledig.getDocumentHistorie().voegToe(new HisDocumentModel(documentHisVolledig, standaard, em.find(ActieModel.class, 101L)));
        final DocumentModel documentModel = verantwoordingRepository.slaDocumentOp(documentHisVolledig);

        final ActieModel actieModel = em.find(ActieModel.class, 101L);
        verantwoordingRepository.slaActieBronOp(actieModel, documentModel);

        //Controlleer dat de actie bronnen heeft. Dit moet door de functie slaActieBronOp() worden geregeld.
        Assert.assertFalse(actieModel.getBronnen().isEmpty());
        Assert.assertEquals(101L, actieModel.getBronnen().iterator().next().getActie().getID().longValue());
    }

    @Test
    public void testHaalDocumentOp() {
        final DocumentModel documentModel = verantwoordingRepository.haalDocumentOp(101L);
        Assert.assertNotNull(documentModel);
    }
}
