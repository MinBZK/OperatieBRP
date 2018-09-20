/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.archivering;

import javax.inject.Inject;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.BerichtdataAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.RichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.ber.BerichtStandaardGroepModel;
import org.junit.Assert;
import org.junit.Test;


public class BerichtRepositoryTest extends AbstractRepositoryTestCase {

    public static final String WAARDE_REFERENTIENUMMER = "1234567890";
    @Inject
    private BerichtRepository repository;

    @Test
    public void testFindOneIngaand() {
        final BerichtModel bericht = repository.findOne(2001L);
        Assert.assertNotNull(bericht);
        Assert.assertEquals(2001L, bericht.getID().longValue());
        Assert.assertEquals(Richting.INGAAND, bericht.getRichting().getWaarde());
        Assert.assertEquals("in", bericht.getStandaard().getData().getWaarde());
        Assert.assertEquals(1302519660000L, bericht.getStuurgegevens().getDatumTijdOntvangst().getWaarde().getTime());
        Assert.assertNull(bericht.getStuurgegevens().getDatumTijdVerzending());
        Assert.assertNull(bericht.getStandaard().getAntwoordOp());
        Assert.assertEquals(Verwerkingsresultaat.GESLAAGD,
                bericht.getResultaat().getVerwerking().getWaarde());
        Assert.assertEquals(Bijhoudingsresultaat.VERWERKT, bericht.getResultaat().getBijhouding().getWaarde());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, bericht.getResultaat().getHoogsteMeldingsniveau().getWaarde());
        Assert.assertEquals(SoortBericht.BHG_HGP_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP, bericht.getSoort().getWaarde());
        Assert.assertEquals(Short.valueOf("8"), bericht.getStuurgegevens().getZendendePartijId());
        Assert.assertEquals(new SysteemNaamAttribuut("BRP"), bericht.getStuurgegevens().getZendendeSysteem());
        Assert.assertEquals(new ReferentienummerAttribuut(WAARDE_REFERENTIENUMMER), bericht.getStuurgegevens().getReferentienummer());
        Assert.assertEquals(new ReferentienummerAttribuut(WAARDE_REFERENTIENUMMER), bericht.getStuurgegevens().getCrossReferentienummer());
    }

    @Test
    public void testFindOneUitgaand() {
        final BerichtModel bericht = repository.findOne(2002L);
        Assert.assertNotNull(bericht);
        Assert.assertEquals(2002L, bericht.getID().longValue());
        Assert.assertEquals(Richting.UITGAAND, bericht.getRichting().getWaarde());
        Assert.assertEquals("uit", bericht.getStandaard().getData().getWaarde());
        Assert.assertNull(bericht.getStuurgegevens().getDatumTijdOntvangst());
        Assert.assertEquals(1302519720000L, bericht.getStuurgegevens().getDatumTijdVerzending().getWaarde().getTime());
        Assert.assertNotNull(bericht.getStandaard().getAntwoordOp());
    }

    @Test
    public void testSave() {
        final BerichtStandaardGroepModel standaardGroepModel = new BerichtStandaardGroepModel(null, new BerichtdataAttribuut("test"), null);

        final BerichtModel bericht = new BerichtModel(new SoortBerichtAttribuut(SoortBericht.BHG_AFS_REGISTREER_GEBOORTE_R), new RichtingAttribuut(Richting.INGAAND));
        bericht.setStandaard(standaardGroepModel);

        repository.save(bericht);
        Assert.assertNotNull(bericht.getID());
    }

}
