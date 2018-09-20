/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.special;

import java.util.Date;

import javax.inject.Inject;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Applicatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Berichtdata;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Organisatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Sleutelwaardetekst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import org.junit.Assert;
import org.junit.Test;


public class BerichtRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private BerichtRepository repository;

    @Test
    public void testFindOneIngaand() {
        BerichtModel bericht = repository.findOne(2001L);
        Assert.assertNotNull(bericht);
        Assert.assertEquals(2001L, bericht.getID().longValue());
        Assert.assertEquals(Richting.INGAAND, bericht.getRichting());
        Assert.assertEquals("in", bericht.getData().getWaarde());
        Assert.assertEquals(1302519660000L, bericht.getDatumTijdOntvangst().getWaarde().getTime());
        Assert.assertNull(bericht.getDatumTijdVerzenden());
        Assert.assertNull(bericht.getAntwoordOp());
        Assert.assertEquals(Verwerkingsresultaat.VERWERKING_GESLAAGD, bericht.getResultaat().getVerwerking());
        Assert.assertEquals(Bijhoudingsresultaat.VERWERKT, bericht.getResultaat().getBijhouding());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, bericht.getResultaat().getHoogsteMeldingsniveau());
        Assert.assertEquals(SoortBericht.H_G_P_REGISTREER_HUWELIJK_B, bericht.getSoort());
        Assert.assertEquals(new Organisatienaam("Org"), bericht.getStuurgegevens().getOrganisatie());
        Assert.assertEquals(new Applicatienaam("App"), bericht.getStuurgegevens().getApplicatie());
        Assert.assertEquals(new Sleutelwaardetekst("1234567890"), bericht.getStuurgegevens().getReferentienummer());
        Assert.assertEquals(new Sleutelwaardetekst("1234567890"),
                            bericht.getStuurgegevens().getCrossReferentienummer());
    }

    @Test
    public void testFindOneUitgaand() {
        BerichtModel bericht = repository.findOne(2002L);
        Assert.assertNotNull(bericht);
        Assert.assertEquals(2002L, bericht.getID().longValue());
        Assert.assertEquals(Richting.UITGAAND, bericht.getRichting());
        Assert.assertEquals("uit", bericht.getData().getWaarde());
        Assert.assertNull(bericht.getDatumTijdOntvangst());
        Assert.assertEquals(1302519720000L, bericht.getDatumTijdVerzenden().getWaarde().getTime());
        Assert.assertNotNull(bericht.getAntwoordOp());
    }

    @Test
    public void testSave() {
        //BerichtModel bericht = new BerichtModel(Richting.INGAAND, new Berichtdata("test"));
        BerichtModel bericht = new BerichtModel(
                SoortBericht.A_F_S_REGISTREER_GEBOORTE_B_R,
                                                null,
                                                new Berichtdata("test"), new DatumTijd(new Date()),
                                                new DatumTijd(new Date()), null, Richting.INGAAND);

        repository.save(bericht);
        Assert.assertNotNull(bericht.getID());
    }

}
