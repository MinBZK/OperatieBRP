/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import junit.framework.Assert;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.groep.bericht.PersoonIndicatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonIndicatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonIndicatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortIndicatie;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Before;
import org.junit.Test;


/** Unit test voor de JiBX marshalling van een {@link PersoonIndicatieMapper} class. */
public class PersoonIndicatieMarshallerTest {

    private IMarshallingContext    marshallingContext;
    private PersoonIndicatieMapper mapper;
    private Writer                 outputWriter;

    @Test
    public void testMarshallingIndicatie() throws JiBXException {
        mapper.marshal(new PersoonIndicatieModel(bouwIndicatie(SoortIndicatie.ONDER_CURATELE)), marshallingContext);
        Assert.assertEquals("<onderCuratele><waarde>J</waarde></onderCuratele>", outputWriter.toString());
    }

    @Test(expected = JiBXException.class)
    public void testMarshallingIndicatieMetVerkeerdObject() throws JiBXException {
        mapper.marshal(new Object(), marshallingContext);
    }

    /**
     * Bouw een {@link nl.bzk.brp.model.objecttype.operationeel.PersoonIndicatieModel} instantie op met de opgegeven soort en een waarde van <code>true/code>.
     *
     * @param soortIndicatie de soort van de indicatie
     * @return een indicatie instantie van de opgegeven soort.
     */
    private PersoonIndicatieBericht bouwIndicatie(final SoortIndicatie soortIndicatie) {
        PersoonIndicatieStandaardGroepBericht gegevens = new PersoonIndicatieStandaardGroepBericht();
        gegevens.setSoort(soortIndicatie);
        gegevens.setWaarde(Ja.Ja);

        PersoonIndicatieBericht indicatie = new PersoonIndicatieBericht();
        indicatie.setGegevens(gegevens);

        return indicatie;
    }

    @Before
    public void initClass() throws JiBXException, IOException {
        IBindingFactory bfact = BindingDirectory.getFactory("binding_objecttypen_model", PersoonIndicatieModel.class);
        marshallingContext = bfact.createMarshallingContext();
        outputWriter = new StringWriter();
        marshallingContext.setOutput(outputWriter);
        mapper = new PersoonIndicatieMapper();
        marshallingContext.getXmlWriter().openNamespaces(new int[] { 0, 1, 2, 3 },
                new String[] { "", "", "xsi", "brp" });
    }
}
