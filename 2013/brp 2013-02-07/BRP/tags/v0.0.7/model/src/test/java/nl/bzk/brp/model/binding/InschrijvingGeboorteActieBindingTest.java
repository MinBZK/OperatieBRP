/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.io.IOException;

import nl.bzk.brp.model.binding.impl.InschrijvingGeboorteActie;
import nl.bzk.brp.model.logisch.Persoon;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test klasse voor het testen van de binding van 'InschrijvingGeboorteActie'. Hiervoor is een aparte Test class
 * die een wrapper vormt voor de abstracte JiBX mapping.
 */
public class InschrijvingGeboorteActieBindingTest extends AbstractBindingTest<InschrijvingGeboorteActie> {

    @Test
    public void test() throws JiBXException, IOException {
        String xml = leesBestand("inschrijving-geboorte.xml");
        InschrijvingGeboorteActie actie = unmarshalObject(xml);

        Assert.assertNotNull(actie);
        Assert.assertEquals(Integer.valueOf(20111120), actie.getDatumAanvangGeldigheid());
        Assert.assertFalse(actie.getRootObjecten().isEmpty());

        Persoon kind = (Persoon) actie.getRootObjecten().get(0);
        Assert.assertEquals("103962438", kind.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertEquals(Integer.valueOf(20111120), kind.getPersoonGeboorte().getDatumGeboorte());
        Assert.assertEquals(2, kind.getPersoonVoornamen().size());
    }

    @Override
    protected Class<InschrijvingGeboorteActie> getBindingClass() {
        return InschrijvingGeboorteActie.class;
    }
}
