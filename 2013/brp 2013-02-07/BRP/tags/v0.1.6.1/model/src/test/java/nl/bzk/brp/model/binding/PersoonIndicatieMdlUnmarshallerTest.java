/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.io.Reader;
import java.io.StringReader;

import junit.framework.Assert;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonIndicatieMdl;
import nl.bzk.brp.model.objecttype.statisch.SoortIndicatie;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Before;
import org.junit.Test;

/** Unit test voor de JiBX unmarshalling van een {@link PersoonIndicatieMapper} class. */
public class PersoonIndicatieMdlUnmarshallerTest {

    private IUnmarshallingContext  unMarshallingContext;
    private PersoonIndicatieMapper mapper;

    @Test
    public void testUnmarshallingIndicatie() throws JiBXException {
        String xml = "<onderCuratele><waarde>J</waarde></onderCuratele>";
        Reader reader = new StringReader(xml);
        unMarshallingContext.setDocument(reader);

        PersoonIndicatieMdl indicatie = new PersoonIndicatieMdl();
        mapper.unmarshal(indicatie, unMarshallingContext);

        Assert.assertEquals(SoortIndicatie.ONDER_CURATELE, indicatie.getSoort());
        Assert.assertEquals(JaNee.Ja, indicatie.getWaarde());
    }

    @Test
    public void testUnmarshallingIndicatieMetNegatieveWaarde() throws JiBXException {
        String xml = "<verstrekkingsbeperking><waarde>N</waarde></verstrekkingsbeperking>";
        Reader reader = new StringReader(xml);
        unMarshallingContext.setDocument(reader);

        PersoonIndicatieMdl indicatie = new PersoonIndicatieMdl();
        mapper.unmarshal(indicatie, unMarshallingContext);

        Assert.assertEquals(SoortIndicatie.VERSTREKKINGSBEPERKING, indicatie.getSoort());
        Assert.assertEquals(JaNee.Nee, indicatie.getWaarde());
    }

    @Test(expected = JiBXException.class)
    public void testUnmarshallingIndicatieMetOnbekendeIndicatie() throws JiBXException {
        String xml = "<testOnbekend><waarde>J</waarde></testOnbekend>";
        Reader reader = new StringReader(xml);
        unMarshallingContext.setDocument(reader);

        PersoonIndicatieMdl indicatie = new PersoonIndicatieMdl();
        mapper.unmarshal(indicatie, unMarshallingContext);
    }

    @Before
    public void initClass() throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory("binding_objecttypen_mdl", PersoonIndicatieMdl.class);
        unMarshallingContext = bfact.createUnmarshallingContext();
        mapper = new PersoonIndicatieMapper();
    }
}
