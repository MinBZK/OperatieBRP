/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.huwelijkpartnerschap;

import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.GeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Before;
import org.junit.Test;

/** Unit test voor het testen van de bedrijfsregel BRAL2101. */
public class BRAL2101Test {

    private BRAL2101 bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRAL2101();
    }

    @Test
    public void testMetLegeRelatie() {
        // Test met null voor relatie
        Assert.assertEquals(0, bedrijfsregel.executeer(null, null, null).size());

        // Test met null voor soort relatie
        // TO uitzetten deze test, kan eigenlijk niet meer, omdat het PERSE een HGP bericht moet zijn.
        HuwelijkGeregistreerdPartnerschapBericht relatie = bouwRelatie(null, new Datum(20120325));
        Assert.assertEquals(0, bedrijfsregel.executeer(null, relatie, null).size());
    }

    @Test
    public void testVerplichtheidGemeenteVoorHuwelijkEnLand() {
        HuwelijkGeregistreerdPartnerschapBericht relatie;
        List<Melding> meldingen;
        // Test met land = nederland en gemeente is ingevuld.
        relatie = bouwRelatie(SoortRelatie.HUWELIJK, new Datum(20120325));
        Assert.assertEquals(0, bedrijfsregel.executeer(null, relatie, null).size());

        // Test met land = nederland en gemeente is NIET ingevuld.
        relatie = bouwRelatie(SoortRelatie.HUWELIJK, new Datum(20120325));
        relatie.getStandaard().setGemeenteAanvang(null);
        meldingen = bedrijfsregel.executeer(null, relatie, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRAL2101, meldingen.get(0).getCode());

        // Test met land = null en gemeente is ingevuld.
        relatie = bouwRelatie(SoortRelatie.HUWELIJK, new Datum(20120325));
        relatie.getStandaard().setLandAanvang(null);
        Assert.assertEquals(0, bedrijfsregel.executeer(null, relatie, null).size());

        // Test met land = null en gemeente is NIET ingevuld.
        relatie = bouwRelatie(SoortRelatie.HUWELIJK, new Datum(20120325));
        relatie.getStandaard().setLandAanvang(null);
        relatie.getStandaard().setGemeenteAanvang(null);
        Assert.assertEquals(0, bedrijfsregel.executeer(null, relatie, null).size());

        Land anderLand = new Land(new Landcode((short) 666), null, null, null, null);

        // Test met land = ander land en gemeente is ingevuld.
        relatie = bouwRelatie(SoortRelatie.HUWELIJK, new Datum(20120325));
        relatie.getStandaard().setLandAanvang(anderLand);
        Assert.assertEquals(0, bedrijfsregel.executeer(null, relatie, null).size());

        // Test met land = ander land en gemeente is NIET ingevuld.
        relatie = bouwRelatie(SoortRelatie.HUWELIJK, new Datum(20120325));
        relatie.getStandaard().setLandAanvang(anderLand);
        relatie.getStandaard().setGemeenteAanvang(null);
        Assert.assertEquals(0, bedrijfsregel.executeer(null, relatie, null).size());
    }

    @Test
    public void testVerplichtheidGemeenteAfhankelijkVanSoort() {
        HuwelijkGeregistreerdPartnerschapBericht relatie;
        List<Melding> meldingen;

        // Test Huwelijk met land = nederland en gemeente is NIET ingevuld.
        relatie = bouwRelatie(SoortRelatie.HUWELIJK, new Datum(20120325));

        relatie.getStandaard().setGemeenteAanvang(null);
        meldingen = bedrijfsregel.executeer(null, relatie, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRAL2101, meldingen.get(0).getCode());

        // Test Geregistreerd partnerschap met land = nederland en gemeente is NIET ingevuld.
        relatie = bouwRelatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP, new Datum(20120325));
        relatie.getStandaard().setGemeenteAanvang(null);
        meldingen = bedrijfsregel.executeer(null, relatie, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRAL2101, meldingen.get(0).getCode());

        // Test Familie rechtelijke betrekking met land = nederland en gemeente is NIET ingevuld.
        // Kanniet meer. de interface vraagt expliciet om een H&GP bericht.
//        relatie = bouwRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, new Datum(20120325));
//        relatie.getStandaard().setGemeenteAanvang(null);
//        meldingen = bedrijfsregel.executeer(null, relatie, null);
//        Assert.assertEquals(0, meldingen.size());
    }

    /**
     * Instantieert een standaard relatie van de opgegeven soort en met opgegeven datum aanvang. De gemeente en het
     * land van aanvang worden standaard reeds gezet.
     *
     * @param soort de soort van de relatie.
     * @param datumAanvang de datum van aanvang van de relatie.
     * @return een relatie instantie.
     */
    private HuwelijkGeregistreerdPartnerschapBericht bouwRelatie(final SoortRelatie soort, final Datum datumAanvang) {
        if (null == soort) {
            return null;
        }
        Land nederland = new Land(BrpConstanten.NL_LAND_CODE, null, null, null, null);
        Partij gemeente = new Partij(new NaamEnumeratiewaarde("test"), SoortPartij.GEMEENTE,
                                     new GemeenteCode((short) 34), new Datum(0), null, null, null, null, null, null);

        HuwelijkGeregistreerdPartnerschapBericht bericht = null;
        switch (soort) {
            case HUWELIJK:
                bericht = new HuwelijkBericht(); break;
            case GEREGISTREERD_PARTNERSCHAP:
                bericht = new GeregistreerdPartnerschapBericht(); break;
            default:
                throw new IllegalArgumentException("Onbekende soort " + soort);
        }
        bericht.setStandaard(new HuwelijkGeregistreerdPartnerschapStandaardGroepBericht());
        bericht.getStandaard().setLandAanvang(nederland);
        bericht.getStandaard().setGemeenteAanvang(gemeente);
        bericht.getStandaard().setDatumAanvang(datumAanvang);
        return bericht;
    }
}
