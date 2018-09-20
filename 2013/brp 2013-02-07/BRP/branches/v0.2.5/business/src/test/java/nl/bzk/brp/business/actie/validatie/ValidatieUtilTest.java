/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.AttribuutType;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Test;
import org.mockito.Mockito;

public class ValidatieUtilTest {

    @Test
    public void testControleerVerplichtVeldInGroepAttribuuttype() {
        List<Melding> meldingen = new ArrayList<Melding>();
        ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, Mockito.mock(AbstractGroepBericht.class), new Burgerservicenummer(null), "veld");
        ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, Mockito.mock(AbstractGroepBericht.class), (AttribuutType) null, "veld");
        ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, Mockito.mock(AbstractGroepBericht.class), new Burgerservicenummer(""), "veld");
        Assert.assertTrue(meldingen.size() == 3);
    }

    @Test
    public void testControleerVerplichtVeldInGroepStatischObjectType() {
        List<Melding> meldingen = new ArrayList<Melding>();
        ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, Mockito.mock(AbstractGroepBericht.class), (AbstractStatischObjectType) null, "veld");
        ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, Mockito.mock(AbstractGroepBericht.class), new Land(), "veld");
        Assert.assertTrue(meldingen.size() == 1);
    }

    @Test
    public void testControleerVerplichtVeldInGroepEnum() {
        List<Melding> meldingen = new ArrayList<Melding>();
        ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, Mockito.mock(AbstractGroepBericht.class), (Enum) null, "veld");
        ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, Mockito.mock(AbstractGroepBericht.class), Geslachtsaanduiding.MAN, "veld");
        Assert.assertTrue(meldingen.size() == 1);
    }

    @Test
    public void testControlleerVerplichteCollectieInObjectType() {
        List<Melding> meldingen = new ArrayList<Melding>();
        ValidatieUtil.controlleerVerplichteCollectieInObjectType(meldingen, Mockito.mock(AbstractObjectTypeBericht.class), null, "veld");
        ValidatieUtil.controlleerVerplichteCollectieInObjectType(meldingen, Mockito.mock(AbstractObjectTypeBericht.class), new ArrayList(), "veld");
        Assert.assertTrue(meldingen.size() == 2);
    }

    @Test
    public void testControlleerVerplichteVeldInObjectTypeAttribuutType() {
        List<Melding> meldingen = new ArrayList<Melding>();
        ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen, Mockito.mock(AbstractObjectTypeBericht.class), (AttribuutType) null, "veld");
        ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen, Mockito.mock(AbstractObjectTypeBericht.class), new Burgerservicenummer(null), "veld");
        ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen, Mockito.mock(AbstractObjectTypeBericht.class), new Burgerservicenummer(""), "veld");
        Assert.assertTrue(meldingen.size() == 3);
    }

    @Test
    public void testControlleerVerplichteVeldInObjectTypeStatischObjectType() {
        List<Melding> meldingen = new ArrayList<Melding>();
        ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen, Mockito.mock(AbstractObjectTypeBericht.class), (AbstractStatischObjectType) null, "veld");
        ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen, Mockito.mock(AbstractObjectTypeBericht.class), new Land(), "veld");
        Assert.assertTrue(meldingen.size() == 1);
    }

    @Test
    public void testControleerVerplichteGroepInObjectType() {
        List<Melding> meldingen = new ArrayList<Melding>();
        ValidatieUtil.controleerVerplichteGroepInObjectType(meldingen, Mockito.mock(AbstractObjectTypeBericht.class), null, "veld");
        Assert.assertTrue(meldingen.size() == 1);
    }
}
