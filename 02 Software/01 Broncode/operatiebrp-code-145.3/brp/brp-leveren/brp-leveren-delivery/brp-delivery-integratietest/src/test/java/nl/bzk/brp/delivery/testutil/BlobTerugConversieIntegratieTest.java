/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.testutil;

import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.test.dal.data.DBUnitLoaderTestExecutionListener;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.algemeenbrp.test.dal.data.DataSourceProvider;
import nl.bzk.brp.domain.leveringmodel.ModelAfdruk;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.blob.PersoonData;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstFactory;
import nl.bzk.brp.service.dalapi.AfnemerindicatieRepository;
import nl.bzk.brp.service.dalapi.PersoonRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = {"/config/integratie-test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional(transactionManager = "transactionManager")
@TestExecutionListeners(DBUnitLoaderTestExecutionListener.class)
@Data(resources = {"classpath:/data/persdata.xml"})
public class BlobTerugConversieIntegratieTest extends AbstractTransactionalJUnit4SpringContextTests implements
        DataSourceProvider {
    private DataSource dataSource;

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private AfnemerindicatieRepository afnemerindicatieRepository;

    @Test
    public void testTerugConversieUitBlob() throws IOException, BlobException {
        final Persoon persoon = persoonRepository.haalPersoonOp(1);
        final List<PersoonAfnemerindicatie> persoonAfnemerindicaties = afnemerindicatieRepository.haalAfnemerindicatiesOp(1);
        final PersoonData persoonData = new PersoonData(Blobber.maakBlob(persoon), Blobber.maakBlob(persoonAfnemerindicaties), 0L);
        final Persoonslijst persoonslijst = PersoonslijstFactory.maak(persoonData);
        final String afdruk = removeLineEndings(ModelAfdruk.maakAfdruk(persoonslijst.getMetaObject()));
        final String bewaardeAfdruk = removeLineEndings(
                IOUtils.toString(BlobTerugConversieIntegratieTest.class.getResourceAsStream("/data/terugconversie.txt")));
        Assert.assertEquals(bewaardeAfdruk, afdruk);
    }

    private String removeLineEndings(String value) {
        return value.replace("\n", "").replace("\r", "");
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    @Inject
    @Named("blobberDalDataSource")
    public final void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);
        this.dataSource = dataSource;
    }
}
