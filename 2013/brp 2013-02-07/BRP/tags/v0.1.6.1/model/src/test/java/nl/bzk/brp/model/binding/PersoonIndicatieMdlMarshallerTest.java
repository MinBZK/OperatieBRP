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
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonIndicatieMdl;
import nl.bzk.brp.model.objecttype.statisch.SoortIndicatie;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Before;
import org.junit.Test;

/** Unit test voor de JiBX marshalling van een {@link PersoonIndicatieMapper} class. */
public class PersoonIndicatieMdlMarshallerTest {

    private IMarshallingContext    marshallingContext;
    private PersoonIndicatieMapper mapper;
    private Writer                 outputWriter;

    @Test
    public void testMarshallingIndicatie() throws JiBXException {
        this.mapper.marshal(bouwIndicatie(SoortIndicatie.ONDER_CURATELE), this.marshallingContext);
        Assert.assertEquals("<onderCuratele><waarde>J</waarde></onderCuratele>",
                this.outputWriter.toString());
    }

    @Test
    public void testMarshallingIndicatieMetNegatieveWaarde() throws JiBXException {
        PersoonIndicatieMdl persoonIndicatie = bouwIndicatie(SoortIndicatie.VERSTREKKINGSBEPERKING);
        persoonIndicatie.setWaarde(JaNee.Nee);

        this.mapper.marshal(persoonIndicatie, this.marshallingContext);
        Assert.assertEquals("<verstrekkingsbeperking><waarde>N</waarde></verstrekkingsbeperking>",
                this.outputWriter.toString());
    }

    @Test(expected = JiBXException.class)
    public void testMarshallingIndicatieMetVerkeerdObject() throws JiBXException {
        this.mapper.marshal(new Object(), this.marshallingContext);
    }

    /**
     * Bouw een {@link PersoonIndicatieMdl} instantie op met de opgegeven soort en een waarde van <code>true/code>.
     *
     * @param soortIndicatie de soort van de indicatie
     * @return een indicatie instantie van de opgegeven soort.
     */
    private PersoonIndicatieMdl bouwIndicatie(final SoortIndicatie soortIndicatie) {
        PersoonIndicatieMdl indicatie = new PersoonIndicatieMdl();
        indicatie.setWaarde(JaNee.Ja);
        indicatie.setSoort(soortIndicatie);
        return indicatie;
    }

    @Before
    public void initClass() throws JiBXException, IOException {
        IBindingFactory bfact = BindingDirectory.getFactory("binding_objecttypen_mdl", PersoonIndicatieMdl.class);
        this.marshallingContext = bfact.createMarshallingContext();
        this.outputWriter = new StringWriter();
        this.marshallingContext.setOutput(this.outputWriter);
        this.mapper = new PersoonIndicatieMapper();
        this.marshallingContext.getXmlWriter().openNamespaces(new int[] { 0, 1, 2, 3 },
            new String[] { "", "", "xsi", "brp" });
    }
}
