/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.conversie.zoekvraag;

import com.google.common.collect.Lists;
import java.io.PrintWriter;
import java.io.StringWriter;
import nl.bzk.brp.util.conversie.zoekvraag.xml.In0Root;
import nl.bzk.brp.util.conversie.zoekvraag.xml.InElement;
import nl.bzk.brp.util.conversie.zoekvraag.xml.Masker;
import nl.bzk.brp.util.conversie.zoekvraag.xml.ParameterItem;
import nl.bzk.brp.util.conversie.zoekvraag.xml.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 */
public class CSVWriterTest {


    CSVWriter csvWriter;
    private StringWriter stringWriter;

    @Before
    public void voorTest(){
        stringWriter = new StringWriter();
        csvWriter = new CSVWriter(new PrintWriter(stringWriter));
    }

    @Test
    public void test() {

        final In0Root root = new In0Root();
        final InElement inElement = new InElement();

        final Masker masker = new Masker();
        masker.setItemList(Lists.newArrayList());
        inElement.setMasker(masker);
        final Parameters parameters = new Parameters();
        final ParameterItem parameterItem = new ParameterItem();
        parameterItem.setRubrieknummer("020310");
        parameterItem.setZoekwaarde("20000114");
        parameters.setItemList(Lists.newArrayList(parameterItem));
        inElement.setParameters(parameters);
        root.setElementList(Lists.newArrayList(inElement));
        csvWriter.toCsv(root);

        Assert.isTrue(stringWriter.toString().contains("2000-01-14"));

    }
}
