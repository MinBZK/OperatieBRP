/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Test;


/** Unit test voor de {@link BedrijfsRegelManagerImpl} klasse. */
public class BedrijfsRegelManagerImplTest {

    private static final List<? extends ActieBedrijfsRegel>          ACTIEREGELS1;
    private static final List<? extends ActieBedrijfsRegel>          ACTIEREGELS2;
    private static final List<? extends BerichtBedrijfsRegel>        BERICHTREGELS1;
    private static final List<? extends BerichtContextBedrijfsRegel> BERICHTCONTEXTREGELS1;

    static {
        ACTIEREGELS1 = Arrays.asList(new ActieBedrijfsRegel() {

            @Override
            public String getCode() {
                return "code1";
            }

            @Override
            public List<Melding> executeer(final RootObject huidigeSituatie, final RootObject nieuweSituatie,
                    final Actie actie)
            {
                return null;
            }
        });
        ACTIEREGELS2 = Arrays.asList(new ActieBedrijfsRegel() {

            @Override
            public String getCode() {
                return "code2";
            }

            @Override
            public List<Melding> executeer(final RootObject huidigeSituatie, final RootObject nieuweSituatie,
                    final Actie actie)
            {
                return null;
            }
        });
        BERICHTREGELS1 = Arrays.asList(new BerichtBedrijfsRegel() {

            @Override
            public String getCode() {
                return "code3";
            }

            @Override
            public List<Melding> executeer(final AbstractBijhoudingsBericht bericht) {
                return null;
            }
        });
        BERICHTCONTEXTREGELS1 = Arrays.asList(new BerichtContextBedrijfsRegel() {

            @Override
            public String getCode() {
                return "code3";
            }

            @Override
            public List<Melding> executeer(final BerichtContext bericht) {
                return null;
            }
        });

    }

    @Test
    public void testConstructorMetNulls() {
        BedrijfsRegelManagerImpl bedrijfsRegelManager;

        // Test Nulls voor beide maps
        bedrijfsRegelManager = new BedrijfsRegelManagerImpl(null, null, null);
        Assert.assertNull(bedrijfsRegelManager.getUitTeVoerenActieBedrijfsRegels(SoortActie.REGISTRATIE_GEBOORTE));
        Assert.assertNull(bedrijfsRegelManager.getUitTeVoerenBerichtBedrijfsRegels(AbstractBijhoudingsBericht.class));
        Assert.assertNull(bedrijfsRegelManager.getUitTeVoerenBerichtContextBedrijfsRegels(AbstractBijhoudingsBericht.class));
    }

    @Test
    public void testConstructorMetVulling() {
        BedrijfsRegelManagerImpl bedrijfsRegelManager;

        bedrijfsRegelManager =
            new BedrijfsRegelManagerImpl(bouwActieRegelsMap(), bouwBerichtRegelsMap(), bouwContextRegelsMap());

        Assert.assertNotNull(bedrijfsRegelManager.getUitTeVoerenActieBedrijfsRegels(SoortActie.REGISTRATIE_GEBOORTE));
        Assert.assertSame(ACTIEREGELS1,
                bedrijfsRegelManager.getUitTeVoerenActieBedrijfsRegels(SoortActie.REGISTRATIE_GEBOORTE));
        Assert.assertNotSame(ACTIEREGELS2,
                bedrijfsRegelManager.getUitTeVoerenActieBedrijfsRegels(SoortActie.REGISTRATIE_GEBOORTE));
        Assert.assertNull(bedrijfsRegelManager.getUitTeVoerenActieBedrijfsRegels(SoortActie.DUMMY));

        Assert.assertNotNull(bedrijfsRegelManager.getUitTeVoerenBerichtBedrijfsRegels(VerhuizingBericht.class));
        Assert.assertNull(bedrijfsRegelManager.getUitTeVoerenBerichtBedrijfsRegels(AbstractBijhoudingsBericht.class));

        Assert.assertNotNull(bedrijfsRegelManager.getUitTeVoerenBerichtContextBedrijfsRegels(VerhuizingBericht.class));
        Assert.assertNull(bedrijfsRegelManager.getUitTeVoerenBerichtContextBedrijfsRegels(AbstractBijhoudingsBericht.class));
    }

    /**
     * Bouwt en instantieert een map tussen soorten acties en bijbehorende lijst van actie bedrijfs regels.
     *
     * @return een map voor soort actie op bedrijfsregels.
     */
    private Map<SoortActie, List<? extends ActieBedrijfsRegel>> bouwActieRegelsMap() {
        Map<SoortActie, List<? extends ActieBedrijfsRegel>> actieRegelMap =
            new HashMap<SoortActie, List<? extends ActieBedrijfsRegel>>();

        actieRegelMap.put(SoortActie.REGISTRATIE_GEBOORTE, ACTIEREGELS1);
        actieRegelMap.put(SoortActie.CORRECTIE_ADRES, ACTIEREGELS2);
        return actieRegelMap;
    }

    /**
     * Bouwt en instantieert een map tussen bericht classes en bijbehorende lijst van bericht bedrijfs regels.
     *
     * @return een map voor bericht classes op bedrijfsregels.
     */
    private Map<Class<? extends AbstractBijhoudingsBericht>, List<? extends BerichtBedrijfsRegel>> bouwBerichtRegelsMap() {
        Map<Class<? extends AbstractBijhoudingsBericht>, List<? extends BerichtBedrijfsRegel>> berichtRegelMap =
            new HashMap<Class<? extends AbstractBijhoudingsBericht>, List<? extends BerichtBedrijfsRegel>>();
        berichtRegelMap.put(VerhuizingBericht.class, BERICHTREGELS1);
        return berichtRegelMap;
    }

    /**
     * Bouwt en instantieert een map tussen bericht classes en bijbehorende lijst van bericht bedrijfs regels.
     *
     * @return een map voor bericht classes op bedrijfsregels.
     */
    private Map<Class<? extends AbstractBijhoudingsBericht>, List<? extends BerichtContextBedrijfsRegel>> bouwContextRegelsMap() {
        Map<Class<? extends AbstractBijhoudingsBericht>, List<? extends BerichtContextBedrijfsRegel>> berichtRegelMap =
            new HashMap<Class<? extends AbstractBijhoudingsBericht>, List<? extends BerichtContextBedrijfsRegel>>();
        berichtRegelMap.put(VerhuizingBericht.class, BERICHTCONTEXTREGELS1);
        return berichtRegelMap;
    }

}
