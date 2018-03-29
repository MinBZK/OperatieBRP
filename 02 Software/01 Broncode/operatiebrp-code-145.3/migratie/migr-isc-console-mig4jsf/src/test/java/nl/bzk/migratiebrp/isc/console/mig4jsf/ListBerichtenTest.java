/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import nl.bzk.migratiebrp.isc.console.mig4jsf.dto.Bericht;
import nl.bzk.migratiebrp.isc.console.mig4jsf.filter.BerichtenFilter;
import nl.bzk.migratiebrp.isc.console.mig4jsf.filter.Filter;
import nl.bzk.migratiebrp.isc.console.mig4jsf.filter.FilterEnum;
import nl.bzk.migratiebrp.isc.console.mig4jsf.pager.PagerBean;
import org.jbpm.jsf.JbpmActionListener;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class ListBerichtenTest extends AbstractTagTest {

    @Test
    public void test() throws Exception {
        final PagerBean pager = new PagerBean(1, 25);
        final Map<FilterEnum, String> waarden = new EnumMap<>(FilterEnum.class);
        waarden.put(FilterEnum.KANAAL, "VOISC");
        waarden.put(FilterEnum.RICHTING, "I");
        waarden.put(FilterEnum.BRON, "0518");
        waarden.put(FilterEnum.DOEL, "0519");
        waarden.put(FilterEnum.TYPE, "NAAM");
        waarden.put(FilterEnum.BERICHT_ID, "MSG-ID");
        waarden.put(FilterEnum.PROCESS_INSTANCE_ID, "4321");
        waarden.put(FilterEnum.CORRELATIE_ID, "CORR-ID");
        final Filter filter = new BerichtenFilter(waarden);

        addTagAttribute("pager", pager);
        addTagAttribute("filter", filter);
        addTagAttribute("target", null);

        setupDatabase("/sql/hsqldb4postgres.sql", "/sql/mig-drop.sql", "/sql/jbpm-drop.sql", "/sql/jbpm-create.sql", "/sql/mig-create.sql",
                "/nl/bzk/migratiebrp/isc/console/mig4jsf/insert-berichten.sql");

        // Execute
        final JbpmActionListener subject = initializeSubject(ListBerichtenHandler.class);
        Assert.assertEquals("listBerichten", subject.getName());
        subject.handleAction(jbpmJsfContext, actionEvent);

        // Verify
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.anyString());
        Mockito.verify(jbpmJsfContext, Mockito.never()).setError(Matchers.anyString(), Matchers.<Throwable>anyObject());

        @SuppressWarnings("unchecked")
        final Collection<Bericht> bericht = (Collection<Bericht>) getExpressionValues().get("target");
        Assert.assertNotNull(bericht);
        Assert.assertEquals(1, bericht.size());
    }
}
