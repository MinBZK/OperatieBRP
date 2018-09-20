/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.GeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test voor het testen van de bedrijfsregel BRBY0430.
 */
public class BRBY0430Test {

    private static final LandGebiedCodeAttribuut GELDIGE_LAND_CODE = new LandGebiedCodeAttribuut((short) 34);
    private static final LandGebiedCodeAttribuut GELDIGE_LAND_CODE_ZONDER_DATUM = new LandGebiedCodeAttribuut((short) 17);

    private BRBY0430 bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY0430();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0430, bedrijfsregel.getRegel());
    }

    @Test
    public void testZonderNieuweSituatieGeenBerichtEntiteiten() {
        List<BerichtEntiteit> berichtEntiteiten;

        berichtEntiteiten = bedrijfsregel.voerRegelUit(null, new HuwelijkBericht(), null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
        berichtEntiteiten = bedrijfsregel.voerRegelUit(null, new GeregistreerdPartnerschapBericht(), null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testZonderLandAanvang() {
        final HuwelijkGeregistreerdPartnerschapBericht relatie = bouwHuwelijksrelatie(null, new DatumEvtDeelsOnbekendAttribuut(20120603));
        Assert.assertEquals(0, bedrijfsregel.voerRegelUit(null, relatie, null, null).size());
    }

    @Test
    public void testZonderDatumAanvangWaaropHuidigeDatumWordtGenomen() {
        List<BerichtEntiteit> berichtEntiteiten;

        // Test met nog steeds geldige land
        berichtEntiteiten = bedrijfsregel.voerRegelUit(null, bouwHuwelijksrelatie(
                bouwLand(GELDIGE_LAND_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), null), null), null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());

        // Test met niet meer geldige land
        final HuwelijkGeregistreerdPartnerschapBericht relatie = bouwHuwelijksrelatie(
                bouwLand(GELDIGE_LAND_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), new DatumEvtDeelsOnbekendAttribuut(20120728)), null);
        berichtEntiteiten = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(relatie, berichtEntiteiten.get(0));
    }

    @Test
    public void testAnderLandOfOnbekendLand() {
        List<BerichtEntiteit> berichtEntiteiten;

        final HuwelijkGeregistreerdPartnerschapBericht relatie =
                bouwHuwelijksrelatie(bouwLand(GELDIGE_LAND_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), null), new DatumEvtDeelsOnbekendAttribuut(20120101));

        // Test met Nederland
        berichtEntiteiten = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(relatie, berichtEntiteiten.get(0));

        // Test zonder land
        relatie.getStandaard().setLandGebiedAanvang(null);
        berichtEntiteiten = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());

        // Test met ander land
        final LandGebied anderLand = new LandGebied(new LandGebiedCodeAttribuut((short) 666), null, null, null, null);
        relatie.getStandaard().setLandGebiedAanvang(new LandGebiedAttribuut(anderLand));
        berichtEntiteiten = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testGeldigheidGemeenteVoorReedsOpgehaaldeGemeenteMetDatumAanvangEnDatumEinde() {
        List<BerichtEntiteit> berichtEntiteiten;

        final LandGebied land = bouwLand(GELDIGE_LAND_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), new DatumEvtDeelsOnbekendAttribuut(20120728));

        // Test met datum voor aanvang geldigheid land
        HuwelijkGeregistreerdPartnerschapBericht relatie = bouwHuwelijksrelatie(land, new DatumEvtDeelsOnbekendAttribuut(20120101));
        berichtEntiteiten = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(relatie, berichtEntiteiten.get(0));

        // Test met datum 1 dag voor aanvang geldigheid land
        relatie = bouwHuwelijksrelatie(land, new DatumEvtDeelsOnbekendAttribuut(20120324));
        berichtEntiteiten = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(relatie, berichtEntiteiten.get(0));

        // Test met datum gelijk aan aanvang geldigheid land
        relatie = bouwHuwelijksrelatie(land, new DatumEvtDeelsOnbekendAttribuut(20120325));
        berichtEntiteiten = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());

        // Test met datum tussen aanvang geldigheid land en einde geledigheid land
        relatie = bouwHuwelijksrelatie(land, new DatumEvtDeelsOnbekendAttribuut(20120603));
        berichtEntiteiten = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());

        // Test met datum 1 dag voor einde geldigheid land
        relatie = bouwHuwelijksrelatie(land, new DatumEvtDeelsOnbekendAttribuut(20120727));
        berichtEntiteiten = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());

        // Test met datum gelijk aan einde geldigheid land
        relatie = bouwHuwelijksrelatie(land, new DatumEvtDeelsOnbekendAttribuut(20120728));
        berichtEntiteiten =
                bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(relatie, berichtEntiteiten.get(0));

        // Test met datum 1 dag na einde geldigheid land
        relatie = bouwHuwelijksrelatie(land, new DatumEvtDeelsOnbekendAttribuut(20120729));
        berichtEntiteiten =
                bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(relatie, berichtEntiteiten.get(0));
    }

    @Test
    public void testGeldigheidGemeenteVoorReedsOpgehaaldeGemeenteMetDatumAanvangMaarZonderDatumEinde() {
        List<BerichtEntiteit> berichtEntiteiten;

        final LandGebied land = bouwLand(GELDIGE_LAND_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), null);

        // Test met datum voor aanvang geldigheid land
        HuwelijkGeregistreerdPartnerschapBericht relatie = bouwHuwelijksrelatie(land, new DatumEvtDeelsOnbekendAttribuut(20120101));
        berichtEntiteiten = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(relatie, berichtEntiteiten.get(0));

        // Test met datum 1 dag voor aanvang geldigheid land
        relatie = bouwHuwelijksrelatie(land, new DatumEvtDeelsOnbekendAttribuut(20120324));
        berichtEntiteiten =
                bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(relatie, berichtEntiteiten.get(0));

        // Test met datum gelijk aan aanvang geldigheid land
        berichtEntiteiten =
                bedrijfsregel.voerRegelUit(null, bouwHuwelijksrelatie(land, new DatumEvtDeelsOnbekendAttribuut(20120325)), null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());

        // Test met datum na aanvang geldigheid
        berichtEntiteiten =
                bedrijfsregel.voerRegelUit(null, bouwHuwelijksrelatie(land, new DatumEvtDeelsOnbekendAttribuut(20120603)), null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());

        // Test met datum in de toekomst
        berichtEntiteiten =
                bedrijfsregel.voerRegelUit(null, bouwHuwelijksrelatie(land, new DatumEvtDeelsOnbekendAttribuut(20170101)), null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testOngeldigheidBijOntbrekenDatumAanvangGemeente() {
        List<BerichtEntiteit> berichtEntiteiten;

        final LandGebied land = bouwLand(GELDIGE_LAND_CODE_ZONDER_DATUM, null, null);
        berichtEntiteiten =
                bedrijfsregel.voerRegelUit(null, bouwHuwelijksrelatie(land, new DatumEvtDeelsOnbekendAttribuut(20120101)), null, null);
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    /**
     * Instantieert een nieuwe {@link nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied} met de opgegeven code, datum
     * van aanvang en datum van einde geldigheid.
     *
     * @param code         de land code.
     * @param datumAanvang de datum van aanvang van geldigheid van de land.
     * @param datumEinde   de datum van einde van geldigheid van de land.
     * @return een nieuwe land met opgegeven waardes.
     */
    private LandGebied bouwLand(final LandGebiedCodeAttribuut code, final DatumEvtDeelsOnbekendAttribuut datumAanvang, final DatumEvtDeelsOnbekendAttribuut datumEinde) {
        return new LandGebied(code, null, null, datumAanvang, datumEinde);
    }

    /**
     * Instantieert een nieuwe {@link nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht} van het
     * type huwelijk die aanvangt in de opgegeven land en met de opgegeven datum van aanvang.
     *
     * @param land         de land waar de relatie is/wordt geregistreerd.
     * @param datumAanvang de datum van aanvang van het huwelijk.
     * @return een geinstantieerde relatie met de opgegeven waardes.
     */
    private HuwelijkGeregistreerdPartnerschapBericht bouwHuwelijksrelatie(final LandGebied land, final DatumEvtDeelsOnbekendAttribuut datumAanvang) {
        final HuwelijkGeregistreerdPartnerschapBericht relatie = new HuwelijkBericht();
        relatie.setStandaard(new RelatieStandaardGroepBericht());
        relatie.getStandaard().setDatumAanvang(datumAanvang);
        if (land != null) {
            relatie.getStandaard().setLandGebiedAanvang(new LandGebiedAttribuut(land));
        }
        return relatie;
    }
}
