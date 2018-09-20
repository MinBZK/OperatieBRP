/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import junit.framework.Assert;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Applicatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Organisatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Sleutelwaardetekst;
import nl.bzk.brp.model.binding.AbstractBindingUitTest;
import org.jibx.runtime.JiBXException;
import org.junit.Test;


public class BerichtStuurgegevensGroepBerichtBindingTest extends AbstractBindingUitTest<BerichtStuurgegevensGroepBericht>{

    @Test
    public void testBinding() throws JiBXException {
        BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
        stuurgegevens.setOrganisatie(new Organisatienaam("Ministerie BZK"));
        stuurgegevens.setApplicatie(new Applicatienaam("BRP"));
        stuurgegevens.setReferentienummer(new Sleutelwaardetekst("BRPBVR2012081300293765"));
        stuurgegevens.setCrossReferentienummer(new Sleutelwaardetekst("vrgB201208130001256"));
        stuurgegevens.setFunctie("VraagDetailsPersoon");

        Assert.assertEquals(maakExpectedStuurgegevens(), marshalObject(stuurgegevens));
    }

    private String maakExpectedStuurgegevens() {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<brp:StuurgegevensDu02_VraagTEST xmlns:brp=\"http://www.bprbzk.nl/BRP/0001\" xmlns:stuf=\"http://www.kinggemeenten.nl/StUF/StUF0302\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                    "<stuf:berichtcode>Du02</stuf:berichtcode>" +
                    "<stuf:zender>" +
                        "<stuf:organisatie>Ministerie BZK</stuf:organisatie>" +
                        "<stuf:applicatie>BRP</stuf:applicatie>" +
                    "</stuf:zender>" +
                    "<stuf:referentienummer>BRPBVR2012081300293765</stuf:referentienummer>" +
                    "<stuf:crossRefnummer>vrgB201208130001256</stuf:crossRefnummer>" +
                    "<stuf:functie>VraagDetailsPersoon</stuf:functie>" +
                "</brp:StuurgegevensDu02_VraagTEST>";

        return xml;
    }

    @Override
    protected Class<BerichtStuurgegevensGroepBericht> getBindingClass() {
        return BerichtStuurgegevensGroepBericht.class;
    }

}
