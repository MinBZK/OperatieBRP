/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request;


import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBronBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.GedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** Unit test klasse voor de {@link BRBY9906} regel. */
public class BRBY9906Test {

    private BRBY9906 brby9906;

    private RegistreerGeboorteBericht bericht;
    private CommunicatieIdMap           communicatieIds;

    @Before
    public void setup() {
        communicatieIds = new CommunicatieIdMap();
        bericht = new RegistreerGeboorteBericht();
        bericht.setCommunicatieIdMap(communicatieIds);

        brby9906 = new BRBY9906();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY9906, brby9906.getRegel());
    }

    @Test
    public void testGelijkeEntiteitInReferentie() throws InstantiationException, IllegalAccessException {
        voegBerichtToe(PersoonBericht.class, "p1", null);
        voegBerichtToe(PersoonBericht.class, "p2", "p1");

        final List<BerichtIdentificeerbaar> berichtenDieDeRegelOvertreden = brby9906.voerRegelUit(bericht);

        Assert.assertEquals(0, berichtenDieDeRegelOvertreden.size());
    }

    @Test
    public void testNietGelijkeEntiteitInReferentie() throws InstantiationException, IllegalAccessException {
        voegBerichtToe(PersoonBericht.class, "p1", null);
        voegBerichtToe(PersoonAdresBericht.class, "a1", "p1");

        final List<BerichtIdentificeerbaar> berichtenDieDeRegelOvertreden = brby9906.voerRegelUit(bericht);

        Assert.assertEquals(1, berichtenDieDeRegelOvertreden.size());
        Assert.assertEquals("a1", berichtenDieDeRegelOvertreden.get(0).getCommunicatieID());
    }

    @Test
    public void testVerwezenObjectBestaatNiet() throws InstantiationException, IllegalAccessException {
        voegBerichtToe(PersoonBericht.class, "p1", "bestaatniet");

        final List<BerichtIdentificeerbaar> berichtenDieDeRegelOvertreden = brby9906.voerRegelUit(bericht);

        Assert.assertEquals(0, berichtenDieDeRegelOvertreden.size());
    }

    @Test
    public void testVerwijzingVanUitMeldingen() throws InstantiationException, IllegalAccessException {
        voegBerichtToe(PersoonBericht.class, "p1", null);
        voegBerichtToe(GedeblokkeerdeMeldingBericht.class, "d1", "p1");

        final List<BerichtIdentificeerbaar> berichtenDieDeRegelOvertreden = brby9906.voerRegelUit(bericht);

        Assert.assertEquals(0, berichtenDieDeRegelOvertreden.size());
    }

    @Test
    public void testBronnen() throws InstantiationException, IllegalAccessException {
        voegBerichtToe(AdministratieveHandelingBronBericht.class, "com1", "ref1");
        voegBerichtToe(ActieBronBericht.class, "com2", "com1");

        final List<BerichtIdentificeerbaar> berichtenDieDeRegelOvertreden = brby9906.voerRegelUit(bericht);

        Assert.assertEquals(0, berichtenDieDeRegelOvertreden.size());
    }

    @Test
    public void testNietBestaandeCommunicatieIdMap() throws InstantiationException, IllegalAccessException {
        bericht.setCommunicatieIdMap(null);

        // Test met niet gelijke entiteiten
        voegBerichtToe(PersoonBericht.class, "p1", null);
        voegBerichtToe(PersoonAdresBericht.class, "a1", "p1");

        final List<BerichtIdentificeerbaar> berichtenDieDeRegelOvertreden = brby9906.voerRegelUit(bericht);

        Assert.assertEquals(0, berichtenDieDeRegelOvertreden.size());
    }

    /**
     * Voegt een instantie van de opgegeven klasse toe aan de map van identificeerbare objecten, waarbij tevens
     * voor de instantie de communicatie en referentie id zijn opgegeven.
     *
     * @param clazz de klasse van de instantie die aangemaakt dient te worden.
     * @param communicatieId de communicatie id van de aan te maken bericht entiteit.
     * @param referentieId de referentie id van de aan te maken bericht entiteit.
     */
    private void voegBerichtToe(final Class<? extends BerichtEntiteit> clazz, final String communicatieId,
        final String referentieId) throws IllegalAccessException, InstantiationException
    {
        final BerichtEntiteit berichtEntiteit = clazz.newInstance();
        berichtEntiteit.setCommunicatieID(communicatieId);
        berichtEntiteit.setReferentieID(referentieId);

        communicatieIds.put(berichtEntiteit);
    }
}
