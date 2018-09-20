/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import org.junit.Assert;
import org.junit.Test;

public class PersoonOnderzoekSpringDataRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonOnderzoekSpringDataRepository persoonOnderzoekSpringDataRepository;

    @Test
    public void testVindGegevensInOnderzoekVoorPersoon() {
        final List<HisOnderzoekModel> onderzoeken =
                persoonOnderzoekSpringDataRepository.vindOnderzoekenVoorPersoon(1, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        Assert.assertEquals(1, onderzoeken.size());
        final Set<? extends GegevenInOnderzoekHisVolledig> gegevensInOnderzoek = onderzoeken.get(0).getOnderzoek().getGegevensInOnderzoek();
        Assert.assertEquals(2, gegevensInOnderzoek.size());
    }

    @Test
    public void testVindGegevensInOnderzoekAfgerondVoorPersoon() {
        final List<HisOnderzoekModel> onderzoeken =
                persoonOnderzoekSpringDataRepository.vindOnderzoekenVoorPersoon(2, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        Assert.assertEquals(0, onderzoeken.size());
    }

    @Test
    public void testVindGegevensInOnderzoekVervallenVoorPersoon() {
        final List<HisOnderzoekModel> onderzoeken =
                persoonOnderzoekSpringDataRepository.vindOnderzoekenVoorPersoon(3, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        Assert.assertEquals(0, onderzoeken.size());
    }

    @Test
    public void testVindGeenGegevensInOnderzoekVoorPersoon() {
        final List<HisOnderzoekModel> onderzoeken =
                persoonOnderzoekSpringDataRepository.vindOnderzoekenVoorPersoon(4, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        Assert.assertEquals(0, onderzoeken.size());
    }
}
