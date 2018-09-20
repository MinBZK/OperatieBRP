/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.web.taglibs;

import junit.framework.Assert;
import nl.bzk.brp.model.data.kern.Pers;
import org.junit.Test;


public class TagFunctiesTest {

    @Test
    public void testFormatteerDatum() {
        Assert.assertEquals("01-01-1999", TagFuncties.formatteerDatum("19990101"));
    }

    @Test
    public void testFormatteerDatumOnbekend() {
        Assert.assertEquals("00-00-0000", TagFuncties.formatteerDatum("0"));
    }

    @Test
    public void testFormatteerDatumNull() {
        Assert.assertNull(TagFuncties.formatteerDatum(null));
    }

    @Test
    public void testFormatteerDatumLeeg() {
        Assert.assertNull(TagFuncties.formatteerDatum(""));
    }

    @Test
    public void shouldFormatteerNaam() {
        Pers persoon = new Pers();
        persoon.setVoornamen("Mike Sierra Foxtrot");
        persoon.setGeslnaam("Zulu");
        persoon.setVoorvoegsel("von");
        Assert.assertEquals("Mike Sierra Foxtrot von Zulu",TagFuncties.formatteerNaam(persoon));
    }

    @Test
    public void shouldFormatteerNaamZonderVoorvoegsel() {
        Pers persoon = new Pers();
        persoon.setVoornamen("Mike Sierra Foxtrot");
        persoon.setGeslnaam("Zulu");
        persoon.setScheidingsteken(" ");
        Assert.assertEquals("Mike Sierra Foxtrot Zulu",TagFuncties.formatteerNaam(persoon));
    }

    @Test
    public void shouldFormatteerNaamMetVreemdScheidingsteken() {
        Pers persoon = new Pers();
        persoon.setVoornamen("Mike Sierra Foxtrot");
        persoon.setGeslnaam("Zulu");
        persoon.setScheidingsteken(";");
        Assert.assertEquals("Mike Sierra Foxtrot;Zulu",TagFuncties.formatteerNaam(persoon));
    }


}
