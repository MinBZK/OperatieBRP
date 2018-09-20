/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.algemeen;

import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test voor het testen van de bedrijfsregel BRBY0429. */
public class BRBY0429Test {

    private static final Gemeentecode GELDIGE_GEMEENTE_CODE        = new Gemeentecode((short) 34);
    private static final Gemeentecode ONGELDIGE_GEMEENTE_CODE      = new Gemeentecode((short) 2);
    private static final Gemeentecode NIET_BESTAANDE_GEMEENTE_CODE = new Gemeentecode((short) 2000);

    private BRBY0429 bedrijfsregel;

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(referentieDataRepository.vindGemeenteOpCode(GELDIGE_GEMEENTE_CODE))
               .thenReturn(bouwGemeente(GELDIGE_GEMEENTE_CODE, new Datum(20120325), null));
        Mockito.when(referentieDataRepository.vindGemeenteOpCode(ONGELDIGE_GEMEENTE_CODE))
               .thenReturn(bouwGemeente(ONGELDIGE_GEMEENTE_CODE, new Datum(20120325), new Datum(20120728)));
        Mockito.when(referentieDataRepository.vindGemeenteOpCode(NIET_BESTAANDE_GEMEENTE_CODE))
               .thenThrow(new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE,
                   null, null));

        bedrijfsregel = new BRBY0429();

        ReflectionTestUtils.setField(bedrijfsregel, "referentieDataRepository", referentieDataRepository);
    }

    @Test
    public void testZonderNieuweSituatieGeenMeldingen() {
        List<Melding> meldingen;

        meldingen = bedrijfsregel.executeer(null, null, null, null);
        Assert.assertEquals(0, meldingen.size());
        meldingen = bedrijfsregel.executeer(null, new RelatieBericht(), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testZonderGemeenteAanvang() {
        Relatie relatie = bouwHuwelijksrelatie(null, new Datum(20120603));
        Assert.assertEquals(0, bedrijfsregel.executeer(null, relatie, null, null).size());
    }

    @Test
    public void testZonderDatumAanvangWaaropHuidigeDatumWordtGenomen() {
        List<Melding> meldingen;

        // Test met nog steeds geldige gemeente
        meldingen = bedrijfsregel.executeer(null, bouwHuwelijksrelatie(
            bouwGemeente(GELDIGE_GEMEENTE_CODE, new Datum(20120325), null), null), null, null);
        Assert.assertEquals(0, meldingen.size());

        // Test met niet meer geldige gemeente
        meldingen = bedrijfsregel.executeer(null, bouwHuwelijksrelatie(
            bouwGemeente(GELDIGE_GEMEENTE_CODE, new Datum(20120325), new Datum(20120728)), null), null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0429, meldingen.get(0).getCode());
    }

    @Test
    public void testAnderLandOfOnbekendLand() {
        List<Melding> meldingen;

        Relatie relatie = bouwHuwelijksrelatie(bouwGemeente(GELDIGE_GEMEENTE_CODE, new Datum(20120325), null), new Datum(20120101));

        // Test met Nederland
        meldingen = bedrijfsregel.executeer(null, relatie, null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0429, meldingen.get(0).getCode());

        // Test zonder land
        ((RelatieBericht) relatie).getGegevens().setLandAanvang(null);
        meldingen = bedrijfsregel.executeer(null, relatie, null, null);
        Assert.assertEquals(0, meldingen.size());

        // Test met ander land
        Land anderLand = new Land();
        anderLand.setCode(new Landcode((short) 666));
        ((RelatieBericht) relatie).getGegevens().setLandAanvang(anderLand);
        meldingen = bedrijfsregel.executeer(null, relatie, null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeldigheidGemeenteVoorReedsOpgehaaldeGemeenteMetDatumAanvangEnDatumEinde() {
        List<Melding> meldingen;

        Partij gemeente = bouwGemeente(GELDIGE_GEMEENTE_CODE, new Datum(20120325), new Datum(20120728));

        // Test met datum voor aanvang geldigheid gemeente
        meldingen = bedrijfsregel.executeer(null, bouwHuwelijksrelatie(gemeente, new Datum(20120101)), null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0429, meldingen.get(0).getCode());

        // Test met datum 1 dag voor aanvang geldigheid gemeente
        meldingen = bedrijfsregel.executeer(null, bouwHuwelijksrelatie(gemeente, new Datum(20120324)), null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0429, meldingen.get(0).getCode());

        // Test met datum gelijk aan aanvang geldigheid gemeente
        meldingen = bedrijfsregel.executeer(null, bouwHuwelijksrelatie(gemeente, new Datum(20120325)), null, null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum tussen aanvang geldigheid gemeente en einde geledigheid gemeente
        meldingen = bedrijfsregel.executeer(null, bouwHuwelijksrelatie(gemeente, new Datum(20120603)), null, null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum 1 dag voor einde geldigheid gemeente
        meldingen = bedrijfsregel.executeer(null, bouwHuwelijksrelatie(gemeente, new Datum(20120727)), null, null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum gelijk aan einde geldigheid gemeente
        meldingen = bedrijfsregel.executeer(null, bouwHuwelijksrelatie(gemeente, new Datum(20120728)), null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0429, meldingen.get(0).getCode());

        // Test met datum 1 dag na einde geldigheid gemeente
        meldingen = bedrijfsregel.executeer(null, bouwHuwelijksrelatie(gemeente, new Datum(20120729)), null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0429, meldingen.get(0).getCode());
    }

    @Test
    public void testGeldigheidGemeenteVoorReedsOpgehaaldeGemeenteMetDatumAanvangMaarZonderDatumEinde() {
        List<Melding> meldingen;

        Partij gemeente = bouwGemeente(GELDIGE_GEMEENTE_CODE, new Datum(20120325), null);

        // Test met datum voor aanvang geldigheid gemeente
        meldingen = bedrijfsregel.executeer(null, bouwHuwelijksrelatie(gemeente, new Datum(20120101)), null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0429, meldingen.get(0).getCode());

        // Test met datum 1 dag voor aanvang geldigheid gemeente
        meldingen = bedrijfsregel.executeer(null, bouwHuwelijksrelatie(gemeente, new Datum(20120324)), null, null);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(MeldingCode.BRBY0429, meldingen.get(0).getCode());

        // Test met datum gelijk aan aanvang geldigheid gemeente
        meldingen = bedrijfsregel.executeer(null, bouwHuwelijksrelatie(gemeente, new Datum(20120325)), null, null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum na aanvang geldigheid
        meldingen = bedrijfsregel.executeer(null, bouwHuwelijksrelatie(gemeente, new Datum(20120603)), null, null);
        Assert.assertEquals(0, meldingen.size());

        // Test met datum in de toekomst
        meldingen = bedrijfsregel.executeer(null, bouwHuwelijksrelatie(gemeente, new Datum(20170101)), null, null);
        Assert.assertEquals(0, meldingen.size());
    }

    /**
     * Instantieert een nieuwe {@link Partij} met de opgegeven code, datum van aanvang en datum van einde geldigheid.
     *
     * @param gemeentecode de gemeente code.
     * @param datumAanvang de datum van aanvang van geldigheid van de gemeente.
     * @param datumEinde de datum van einde van geldigheid van de gemeente.
     * @return een nieuwe gemeente met opgegeven waardes.
     */
    private Partij bouwGemeente(final Gemeentecode gemeentecode, final Datum datumAanvang, final Datum datumEinde) {
        Partij gemeente = new Partij();
        gemeente.setGemeentecode(gemeentecode);
        gemeente.setDatumAanvang(datumAanvang);
        gemeente.setDatumEinde(datumEinde);
        return gemeente;
    }

    /**
     * Instantieert een nieuwe {@link Relatie} van het type huwelijk die aanvangt in de opgegeven gemeente en met de
     * opgegeven datum van aanvang.
     *
     * @param gemeente de gemeente waar de relatie is/wordt geregistreerd.
     * @param datumAanvang de datum van aanvang van het huwelijk.
     * @return een geinstantieerde relatie met de opgegeven waardes.
     */
    private Relatie bouwHuwelijksrelatie(final Partij gemeente, final Datum datumAanvang) {
        Land nederland = new Land();
        nederland.setCode(BrpConstanten.NL_LAND_CODE);

        RelatieBericht relatie = new RelatieBericht();
        relatie.setSoort(SoortRelatie.HUWELIJK);
        relatie.setGegevens(new RelatieStandaardGroepBericht());
        relatie.getGegevens().setGemeenteAanvang(gemeente);
        relatie.getGegevens().setDatumAanvang(datumAanvang);
        relatie.getGegevens().setLandAanvang(nederland);
        return relatie;
    }
}
