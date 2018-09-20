/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.io.Reader;
import java.io.StringReader;

import junit.framework.Assert;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.groep.bericht.PersoonIndicatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonIndicatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonIndicatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortIndicatie;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Before;
import org.junit.Test;

/** Unit test voor de JiBX unmarshalling van een {@link PersoonIndicatieMapper} class. */
public class PersoonIndicatieUnmarshallerTest {

    private IUnmarshallingContext  unMarshallingContext;
    private PersoonIndicatieMapper mapper;

    @Test
    public void testUnmarshallingIndicatie() throws JiBXException {
        String xml = "<onderCuratele><waarde>J</waarde></onderCuratele>";
        Reader reader = new StringReader(xml);
        unMarshallingContext.setDocument(reader);

        PersoonIndicatieBericht indicatieWeb = new PersoonIndicatieBericht();
        PersoonIndicatieStandaardGroepBericht pidWeb = new PersoonIndicatieStandaardGroepBericht();
        indicatieWeb.setGegevens(pidWeb);
        PersoonIndicatieModel indicatie = new PersoonIndicatieModel(indicatieWeb);
        mapper.unmarshal(indicatie, unMarshallingContext);

        Assert.assertEquals(SoortIndicatie.ONDER_CURATELE, indicatie.getGegevens().getSoort());
        Assert.assertEquals(Ja.Ja, indicatie.getGegevens().getWaarde());
    }

    @Test(expected = JiBXException.class)
    public void testUnmarshallingIndicatieMetOnbekendeIndicatie() throws JiBXException {
        String xml = "<testOnbekend><waarde>J</waarde></testOnbekend>";
        Reader reader = new StringReader(xml);
        unMarshallingContext.setDocument(reader);

        PersoonIndicatieBericht indicatieWeb = new PersoonIndicatieBericht();
        PersoonIndicatieStandaardGroepBericht pidWeb = new PersoonIndicatieStandaardGroepBericht();
        indicatieWeb.setGegevens(pidWeb);
        PersoonIndicatieModel indicatie = new PersoonIndicatieModel(indicatieWeb);
        mapper.unmarshal(indicatie, unMarshallingContext);
    }

    @Before
    public void initClass() throws JiBXException {
        IBindingFactory bfact = BindingDirectory.getFactory("binding_objecttypen_model", PersoonIndicatieModel.class);
        unMarshallingContext = bfact.createUnmarshallingContext();
        mapper = new PersoonIndicatieMapper();
    }
}
