/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestGemeenteBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test voor het testen van de bedrijfsregel BRBY0429.
 */
public class BRBY0429Test {

    private static final GemeenteCodeAttribuut GELDIGE_GEMEENTE_CODE = new GemeenteCodeAttribuut((short) 34);
    private static final GemeenteCodeAttribuut GELDIGE_GEMEENTE_CODE_ZONDER_DATUM = new GemeenteCodeAttribuut((short) 17);

    private BRBY0429 bedrijfsregel;

    @Before
    public void init() {
        bedrijfsregel = new BRBY0429();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0429, bedrijfsregel.getRegel());
    }

    @Test
    public void testZonderNieuweSituatieGeenMeldingen() {
        final List<BerichtEntiteit> meldingen;

        meldingen = bedrijfsregel.voerRegelUit(null, bouwHuwelijksrelatie(null, null), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testZonderGemeenteAanvang() {
        final HuwelijkGeregistreerdPartnerschapBericht relatie = bouwHuwelijksrelatie(null, new DatumEvtDeelsOnbekendAttribuut(20120603));
        Assert.assertEquals(0, bedrijfsregel.voerRegelUit(null, relatie, null, null).size());
    }

    @Test
    public void testZonderDatumAanvangWaaropHuidigeDatumWordtGenomen() {
        List<BerichtEntiteit> meldingen;

        // Test met nog steeds geldige gemeente
        meldingen = bedrijfsregel.voerRegelUit(null, bouwHuwelijksrelatie(
                bouwGemeente(GELDIGE_GEMEENTE_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), null), null), null, null);
        Assert.assertEquals(0, meldingen.size());

        // Test met niet meer geldige gemeente
        final HuwelijkGeregistreerdPartnerschapBericht relatie = bouwHuwelijksrelatie(
                bouwGemeente(GELDIGE_GEMEENTE_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), new DatumEvtDeelsOnbekendAttribuut(20120728)), null);
        meldingen = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(relatie, meldingen.get(0));
    }

    @Test
    public void testAnderLandOfOnbekendLand() {
        List<BerichtEntiteit> meldingen;

        final HuwelijkGeregistreerdPartnerschapBericht relatie =
                bouwHuwelijksrelatie(bouwGemeente(GELDIGE_GEMEENTE_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), null),
                        new DatumEvtDeelsOnbekendAttribuut(20120101));

        // Test met Nederland
        meldingen = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(relatie, meldingen.get(0));

        // Test zonder land
        relatie.getStandaard().setLandGebiedAanvang(null);
        meldingen = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(0, meldingen.size());

        // Test met ander land
        final LandGebied anderLand = new LandGebied(new LandGebiedCodeAttribuut((short) 666), null, null, null, null);
        relatie.getStandaard().setLandGebiedAanvang(new LandGebiedAttribuut(anderLand));
        meldingen = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeldigheidGemeenteVoorReedsOpgehaaldeGemeenteMetDatumAanvangEnDatumEinde() {
        List<BerichtEntiteit> meldingen;

        final Gemeente gemeente = bouwGemeente(GELDIGE_GEMEENTE_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), new DatumEvtDeelsOnbekendAttribuut(20120728));

        // Test met datum voor aanvang geldigheid gemeente
        HuwelijkGeregistreerdPartnerschapBericht relatie = bouwHuwelijksrelatie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120101));
        meldingen = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(relatie, meldingen.get(0));

        // Test met datum 1 dag voor aanvang geldigheid gemeente
        relatie = bouwHuwelijksrelatie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120324));
        meldingen = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(relatie, meldingen.get(0));

        // Test met datum gelijk aan aanvang geldigheid gemeente
        meldingen = bedrijfsregel.voerRegelUit(null, bouwHuwelijksrelatie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120325)), null, null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum tussen aanvang geldigheid gemeente en einde geledigheid gemeente
        meldingen = bedrijfsregel.voerRegelUit(null, bouwHuwelijksrelatie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120603)), null, null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum 1 dag voor einde geldigheid gemeente
        meldingen = bedrijfsregel.voerRegelUit(null, bouwHuwelijksrelatie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120727)), null, null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum gelijk aan einde geldigheid gemeente
        relatie = bouwHuwelijksrelatie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120728));
        meldingen = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(relatie, meldingen.get(0));

        // Test met datum 1 dag na einde geldigheid gemeente
        relatie = bouwHuwelijksrelatie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120729));
        meldingen = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(relatie, meldingen.get(0));
    }

    @Test
    public void testGeldigheidGemeenteVoorReedsOpgehaaldeGemeenteMetDatumAanvangMaarZonderDatumEinde() {
        List<BerichtEntiteit> meldingen;

        final Gemeente gemeente = bouwGemeente(GELDIGE_GEMEENTE_CODE, new DatumEvtDeelsOnbekendAttribuut(20120325), null);

        // Test met datum voor aanvang geldigheid gemeente
        HuwelijkGeregistreerdPartnerschapBericht relatie = bouwHuwelijksrelatie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120101));
        meldingen = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(relatie, meldingen.get(0));

        // Test met datum 1 dag voor aanvang geldigheid gemeente
        relatie = bouwHuwelijksrelatie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120324));
        meldingen = bedrijfsregel.voerRegelUit(null, relatie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(relatie, meldingen.get(0));

        // Test met datum gelijk aan aanvang geldigheid gemeente
        meldingen = bedrijfsregel.voerRegelUit(null, bouwHuwelijksrelatie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120325)), null, null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum na aanvang geldigheid
        meldingen = bedrijfsregel.voerRegelUit(null, bouwHuwelijksrelatie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20120603)), null, null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum in de toekomst
        meldingen = bedrijfsregel.voerRegelUit(null, bouwHuwelijksrelatie(gemeente, new DatumEvtDeelsOnbekendAttribuut(20170101)), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testOngeldigheidBijOntbrekenDatumAanvangGemeente() {
        final Gemeente gemeente = bouwGemeente(GELDIGE_GEMEENTE_CODE_ZONDER_DATUM, null, null);
        final List<BerichtEntiteit> meldingen = bedrijfsregel.voerRegelUit(null, bouwHuwelijksrelatie(gemeente,
            new DatumEvtDeelsOnbekendAttribuut(20120101)), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    /**
     * Instantieert een nieuwe {@link nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente} met de opgegeven code, datum van aanvang en datum van einde geldigheid.
     *
     * @param gemeentecode de gemeente code.
     * @param datumAanvang de datum van aanvang van geldigheid van de gemeente.
     * @param datumEinde   de datum van einde van geldigheid van de gemeente.
     * @return een nieuwe gemeente met opgegeven waardes.
     */
    private Gemeente bouwGemeente(final GemeenteCodeAttribuut gemeentecode,
                                  final DatumEvtDeelsOnbekendAttribuut datumAanvang,
                                  final DatumEvtDeelsOnbekendAttribuut datumEinde)
    {
        return TestGemeenteBuilder.maker().metCode(gemeentecode).metDatumAanvang(datumAanvang)
            .metDatumEinde(datumEinde).maak();
    }

    /**
     * Instantieert een nieuwe {@link nl.bzk.brp.model.logisch.kern.Relatie} van het type huwelijk die aanvangt in de opgegeven gemeente en met de
     * opgegeven datum van aanvang.
     *
     * @param gemeente     de gemeente waar de relatie is/wordt geregistreerd.
     * @param datumAanvang de datum van aanvang van het huwelijk.
     * @return een geinstantieerde relatie met de opgegeven waardes.
     */
    private HuwelijkGeregistreerdPartnerschapBericht bouwHuwelijksrelatie(final Gemeente gemeente,
                                                                          final DatumEvtDeelsOnbekendAttribuut datumAanvang)
    {
        final LandGebied nederland = new LandGebied(LandGebiedCodeAttribuut.NEDERLAND, null, null, null, null);

        final HuwelijkGeregistreerdPartnerschapBericht relatie = new HuwelijkBericht();
        relatie.setStandaard(new RelatieStandaardGroepBericht());
        if (gemeente != null) {
            relatie.getStandaard().setGemeenteAanvang(new GemeenteAttribuut(gemeente));
        }
        relatie.getStandaard().setDatumAanvang(datumAanvang);
        relatie.getStandaard().setLandGebiedAanvang(new LandGebiedAttribuut(nederland));
        return relatie;
    }
}
