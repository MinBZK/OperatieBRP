/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.brp.service.dalapi.LeveringsautorisatieRepository;
import org.junit.Test;

/**
 *
 */
@Data(resources = {"classpath:/data/testdata-autaut.xml"})
public class LeveringsautorisatieRepositoryImplTest extends AbstractDataAccessTest {

    @Inject
    private LeveringsautorisatieRepository repository;

    @Test
    public void haalAlleToegangLeveringsautorisatiesOpZonderAssociaties() {
        final List<ToegangLeveringsAutorisatie> resultaat = repository.haalAlleToegangLeveringsautorisatiesOpZonderAssociaties();

        assertThat(resultaat, notNullValue());
        assertThat(resultaat.size(), is(1));
        assertThat(resultaat.get(0).getLeveringsautorisatie().getId(), is(1));
    }

    @Test
    public void haalAlleLeveringsautorisatiesOpZonderAssociaties() {
        final List<Leveringsautorisatie> resultaat = repository.haalAlleLeveringsautorisatiesOpZonderAssocaties();

        assertThat(resultaat, notNullValue());
        assertThat(resultaat.size(), is(1));
        assertThat(resultaat.get(0).getNaam(), is("lev1"));
    }

    @Test
    public void haalAlleDienstbundelsOpZonderAssociaties() {
        final List<Dienstbundel> resultaat = repository.haalAlleDienstbundelsOpZonderAssocaties();

        assertThat(resultaat, notNullValue());
        assertThat(resultaat.size(), is(1));
        assertThat(resultaat.get(0).getLeveringsautorisatie().getId(), is(1));
    }

    @Test
    public void haalAlleDienstenOpZonderAssociaties() {
        final List<Dienst> resultaat = repository.haalAlleDienstenOpZonderAssocaties();

        assertThat(resultaat, notNullValue());
        assertThat(resultaat.size(), is(4));
        resultaat.forEach(d -> assertThat(d.getDienstbundel().getId(), is(1)));
    }

    @Test
    public void haalAlleDienstundelGroepenOpZonderAssociaties() {
        final List<DienstbundelGroep> resultaat = repository.haalAlleDienstbundelGroepenOpZonderAssocaties();

        assertThat(resultaat, notNullValue());
        assertThat(resultaat.size(), is(1));
        resultaat.forEach(d -> assertThat(d.getDienstbundel().getId(), is(1)));
    }

    @Test
    public void haalAlleDienstBundelGroepAttributenOpZonderAssociaties() {
        final List<DienstbundelGroepAttribuut> resultaat = repository.haalAlleDienstbundelGroepAttributenOpZonderAssocaties();

        assertThat(resultaat, notNullValue());
        assertThat(resultaat.size(), is(1));
        resultaat.forEach(d -> assertThat(d.getDienstbundelGroep().getId(), is(1)));
    }
}
