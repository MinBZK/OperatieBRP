/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.huwelijkpartnerschap;

import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
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
        Relatie relatie = bouwRelatie(null, new Datum(20120325));
        Assert.assertEquals(0, bedrijfsregel.executeer(null, relatie, null).size());
    }

    @Test
    public void testVerplichtheidGemeenteVoorHuwelijkEnLand() {
        Relatie relatie;
        List<Melding> meldingen;
        // Test met land = nederland en gemeente is ingevuld.
        relatie = bouwRelatie(SoortRelatie.HUWELIJK, new Datum(20120325));
        Assert.assertEquals(0, bedrijfsregel.executeer(null, relatie, null).size());

        // Test met land = nederland en gemeente is NIET ingevuld.
        relatie = bouwRelatie(SoortRelatie.HUWELIJK, new Datum(20120325));
        ((RelatieBericht) relatie).getGegevens().setGemeenteAanvang(null);
        meldingen = bedrijfsregel.executeer(null, relatie, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRAL2101, meldingen.get(0).getCode());

        // Test met land = null en gemeente is ingevuld.
        relatie = bouwRelatie(SoortRelatie.HUWELIJK, new Datum(20120325));
        ((RelatieBericht) relatie).getGegevens().setLandAanvang(null);
        Assert.assertEquals(0, bedrijfsregel.executeer(null, relatie, null).size());

        // Test met land = null en gemeente is NIET ingevuld.
        relatie = bouwRelatie(SoortRelatie.HUWELIJK, new Datum(20120325));
        ((RelatieBericht) relatie).getGegevens().setLandAanvang(null);
        ((RelatieBericht) relatie).getGegevens().setGemeenteAanvang(null);
        Assert.assertEquals(0, bedrijfsregel.executeer(null, relatie, null).size());

        Land anderLand = new Land();
        anderLand.setCode(new Landcode((short) 666));

        // Test met land = ander land en gemeente is ingevuld.
        relatie = bouwRelatie(SoortRelatie.HUWELIJK, new Datum(20120325));
        ((RelatieBericht) relatie).getGegevens().setLandAanvang(anderLand);
        Assert.assertEquals(0, bedrijfsregel.executeer(null, relatie, null).size());

        // Test met land = ander land en gemeente is NIET ingevuld.
        relatie = bouwRelatie(SoortRelatie.HUWELIJK, new Datum(20120325));
        ((RelatieBericht) relatie).getGegevens().setLandAanvang(anderLand);
        ((RelatieBericht) relatie).getGegevens().setGemeenteAanvang(null);
        Assert.assertEquals(0, bedrijfsregel.executeer(null, relatie, null).size());
    }

    @Test
    public void testVerplichtheidGemeenteAfhankelijkVanSoort() {
        Relatie relatie;
        List<Melding> meldingen;

        // Test Huwelijk met land = nederland en gemeente is NIET ingevuld.
        relatie = bouwRelatie(SoortRelatie.HUWELIJK, new Datum(20120325));
        ((RelatieBericht) relatie).getGegevens().setGemeenteAanvang(null);
        meldingen = bedrijfsregel.executeer(null, relatie, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRAL2101, meldingen.get(0).getCode());

        // Test Geregistreerd partnerschap met land = nederland en gemeente is NIET ingevuld.
        relatie = bouwRelatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP, new Datum(20120325));
        ((RelatieBericht) relatie).getGegevens().setGemeenteAanvang(null);
        meldingen = bedrijfsregel.executeer(null, relatie, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRAL2101, meldingen.get(0).getCode());

        // Test Familie rechtelijke betrekking met land = nederland en gemeente is NIET ingevuld.
        relatie = bouwRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, new Datum(20120325));
        ((RelatieBericht) relatie).getGegevens().setGemeenteAanvang(null);
        meldingen = bedrijfsregel.executeer(null, relatie, null);
        Assert.assertEquals(0, meldingen.size());
    }

        /**
        * Instantieert een standaard relatie van de opgegeven soort en met opgegeven datum aanvang. De gemeente en het
        * land van aanvang worden standaard reeds gezet.
        *
        * @param soort de soort van de relatie.
        * @param datumAanvang de datum van aanvang van de relatie.
        * @return een relatie instantie.
        */
    private Relatie bouwRelatie(final SoortRelatie soort, final Datum datumAanvang) {
        Land nederland = new Land();
        nederland.setCode(BrpConstanten.NL_LAND_CODE);

        Partij gemeente = new Partij();
        gemeente.setGemeentecode(new Gemeentecode((short) 34));
        gemeente.setNaam(new Naam("test"));
        gemeente.setDatumAanvang(new Datum(0));

        RelatieBericht relatie = new RelatieBericht();
        relatie.setSoort(soort);
        relatie.setGegevens(new RelatieStandaardGroepBericht());
        relatie.getGegevens().setLandAanvang(nederland);
        relatie.getGegevens().setGemeenteAanvang(gemeente);
        relatie.getGegevens().setDatumAanvang(datumAanvang);
        return relatie;
    }
}
