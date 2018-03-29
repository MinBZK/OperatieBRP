/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class RelatieGroepElementTest extends AbstractElementTest {

    private static final int DATUM_GEMEENTE_BEGIN = 20110120;
    private static final int DATUM_GEMEENTE_EINDE = 20110110;
    private static final int PEIL_DATUM = 20110115;
    private static final int TOEKOMST = 20990101;
    private static final int PEIL_DATUM_ONGELDIG = 20110132;
    private static final int VANDAAG = DatumUtil.vandaag();
    private static final StringElement GEMEENTE_BKND = new StringElement("0001");
    private static final StringElement GEMEENTE_ONBKND = new StringElement("0002");
    private static final StringElement GEMEENTE_BEGIN = new StringElement("0003");
    private static final StringElement GEMEENTE_EIND = new StringElement("0004");
    private static final String GEMEENTECODE_BEKEND = GEMEENTE_BKND.getWaarde();
    private static final String GEMEENTECODE_ONBEKEND = GEMEENTE_ONBKND.getWaarde();
    private static final String GEMEENTECODE_BEGIN = GEMEENTE_BEGIN.getWaarde();
    private static final String GEMEENTECODE_EIND = GEMEENTE_EIND.getWaarde();
    private static final String AMSTERDAM = "Amsterdam";
    private static final String GRONINGEN = "Groningen";
    private static final String ONBEKEND = "onbekend";
    private static final Map<String, String> ATTRIBUTEN = new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_test").build();
    private static final CharacterElement REDEN_BEKEND = new CharacterElement('N');
    private static final CharacterElement REDEN_ONBEKEND = new CharacterElement('X');
    private static final StringElement SPANJE = new StringElement("6037");
    private static final StringElement ONBEKEND_LAND = new StringElement("1010");
    private static final StringElement ADEN = new StringElement("9041");
    private static final StringElement LANDGEBIEDCODE_NL = new StringElement("6030");
    private static final StringElement LANDGEBIEDCODE_NIET_NL = new StringElement("0001");
    private static final StringElement LANDGEBIEDCODE_ONBEKEND = new StringElement("0000");
    private static final StringElement LANDGEBIEDCODE_INTERNATIONAAL = new StringElement("9999");
    private static final LandOfGebied NEDERLAND = new LandOfGebied("6030", "Nederland");
    private static final LandOfGebied DUITSLAND = new LandOfGebied("6029", "Duitsland");
    private static final LandOfGebied ONGELDIG_LAND = new LandOfGebied("0000", "Onbekend");
    private static final String BUITENLANDSEPLAATS = "buitenlandseplaats";

    private ElementBuilder builder;

    @Before
    public void setup() {
        builder = new ElementBuilder();
        final Gemeente gemeente = new Gemeente(Short.parseShort("1"), "gemeente", "0001", new Partij("gemeente", "000001"));
        final Gemeente gemeente_begin = new Gemeente(Short.parseShort("1"), "gemeente", "0001", new Partij("gemeente", "000001"));
        gemeente_begin.setDatumAanvangGeldigheid(DATUM_GEMEENTE_BEGIN);
        final Gemeente gemeente_eind = new Gemeente(Short.parseShort("1"), "gemeente", "0001", new Partij("gemeente", "000001"));
        gemeente_eind.setDatumEindeGeldigheid(DATUM_GEMEENTE_EINDE);

        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(GEMEENTECODE_BEKEND)).thenReturn(gemeente);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(GEMEENTECODE_BEGIN)).thenReturn(gemeente_begin);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(GEMEENTECODE_EIND)).thenReturn(gemeente_eind);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(GEMEENTECODE_ONBEKEND)).thenReturn(null);

        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(LANDGEBIEDCODE_NL.getWaarde())).thenReturn(NEDERLAND);
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(LANDGEBIEDCODE_NIET_NL.getWaarde())).thenReturn(
                new LandOfGebied("0001", "Niet Nederland"));
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(LANDGEBIEDCODE_ONBEKEND.getWaarde())).thenReturn(ONGELDIG_LAND);
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(DUITSLAND.getCode())).thenReturn(DUITSLAND);
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(LANDGEBIEDCODE_INTERNATIONAAL.getWaarde())).thenReturn(
                new LandOfGebied("9999", "Internationaal gebied"));
        final Plaats amsterdam = new Plaats(AMSTERDAM);
        final Plaats groningen = new Plaats(GRONINGEN);
        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(AMSTERDAM)).thenReturn(amsterdam);
        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(GRONINGEN)).thenReturn(groningen);

        final RedenBeeindigingRelatie redenBeeindigingRelatie = new RedenBeeindigingRelatie('N', "Nietigverklaring");
        when(getDynamischeStamtabelRepository().getRedenBeeindigingRelatieByCode(REDEN_BEKEND.getWaarde())).thenReturn(redenBeeindigingRelatie);

        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(SPANJE.getWaarde())).thenReturn(new LandOfGebied("6037", "Spanje"));
        final LandOfGebied aden = new LandOfGebied("9041", "Aden");
        aden.setDatumEindeGeldigheid(19910601);
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(ADEN.getWaarde())).thenReturn(aden);
    }

    @Test
    public void bestaandeLandGebied() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement(ONBEKEND),
                        new StringElement(ONBEKEND),
                        null,
                        SPANJE);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(0, relatie.valideerInhoud().size());
    }

    @Test
    public void bestaandeLandGebied_2165_niet() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        new StringElement(BUITENLANDSEPLAATS),
                        null,
                        null,
                        SPANJE,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(0, relatie.valideerInhoud().size());
    }

    @Test
    public void bestaandeLandGebied_2165() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        new StringElement(BUITENLANDSEPLAATS),
                        null,
                        null,
                        ONBEKEND_LAND,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(Regel.R2165, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void OnbekendLandGebiedR2180() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement(ONBEKEND),
                        new StringElement(ONBEKEND),
                        null,
                        new StringElement(ONBEKEND));
        relatie.setVerzoekBericht(getBericht());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(Regel.R2180, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void OnbekendLandGebiedR1881() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement(ONBEKEND),
                        new StringElement(ONBEKEND),
                        null,
                        ADEN);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(Regel.R1881, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void LandGebiedNederlandR1877() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement(LandOfGebied.CODE_NEDERLAND));
        relatie.setVerzoekBericht(getBericht());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(Regel.R1877, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void LandGebiedAanvang_R1648() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        SPANJE,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(Regel.R1648, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void bestaandeGemeenteR2150() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(PEIL_DATUM),
                        GEMEENTE_BKND,
                        new StringElement(AMSTERDAM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(0, relatie.valideerInhoud().size());
    }

    @Test
    public void nietBestaandeGemeenteR2150() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(PEIL_DATUM),
                        GEMEENTE_ONBKND,
                        new StringElement(AMSTERDAM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(Regel.R2150, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void gemeenteBestaatNognietR1870() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(PEIL_DATUM),
                        GEMEENTE_BEGIN,
                        new StringElement(AMSTERDAM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(Regel.R1870, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void gemeenteBestaatNietMeerR1870() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(PEIL_DATUM),
                        GEMEENTE_EIND,
                        new StringElement(AMSTERDAM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(Regel.R1870, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void datumAanvangToekomstR1872() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(TOEKOMST),
                        GEMEENTE_BKND,
                        new StringElement(AMSTERDAM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(Regel.R1872, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void datumAanvangNuR1872() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(VANDAAG),
                        GEMEENTE_BKND,
                        new StringElement(AMSTERDAM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(0, relatie.valideerInhoud().size());
    }

    @Test
    public void datumAanvangVerledenR1872() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(PEIL_DATUM),
                        GEMEENTE_BKND,
                        new StringElement(AMSTERDAM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(0, relatie.valideerInhoud().size());
    }

    @Test
    public void datumAanvangGeldigR1859() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(PEIL_DATUM),
                        GEMEENTE_BKND,
                        new StringElement(AMSTERDAM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(0, relatie.valideerInhoud().size());
    }

    @Test
    public void datumAanvangOngeldigR1859() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(2016_00_01),
                        GEMEENTE_BKND,
                        new StringElement(AMSTERDAM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(Regel.R1859, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void datumEindeOngeldigR1860() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        GEMEENTE_BKND,
                        new StringElement(AMSTERDAM),
                        null,
                        new DatumElement(2016_01_00),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        final List<MeldingElement> meldingElements = relatie.valideerInhoud();
        assertEquals(1, meldingElements.size());
        assertEquals(Regel.R1860, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void datumEindeGeldigR1860() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        GEMEENTE_BKND,
                        new StringElement(AMSTERDAM),
                        null,
                        new DatumElement(VANDAAG),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        final List<MeldingElement> meldingElements = relatie.valideerInhoud();
        assertEquals(0, meldingElements.size());
    }

    @Test
    public void datumAanvangOngeldigR1859Prevalidatie() {
        final BijhoudingVerzoekBericht bericht = getBericht();
        when(bericht.isPrevalidatie()).thenReturn(true);
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(PEIL_DATUM_ONGELDIG),
                        GEMEENTE_BKND,
                        new StringElement(AMSTERDAM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(bericht);
        assertEquals(0, relatie.valideerInhoud().size());
    }

    @Test
    public void plaatsOngeldigR2151() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(PEIL_DATUM),
                        GEMEENTE_BKND,
                        new StringElement(ONBEKEND),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(1, relatie.valideerInhoud().size());
        relatie.setVerzoekBericht(getBericht());
        assertEquals(Regel.R2151, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void datumEindeToekomstR1875() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        GEMEENTE_BKND,
                        new StringElement(AMSTERDAM),
                        null,
                        new DatumElement(TOEKOMST),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(Regel.R1875, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void datumEindeNuR1875() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        GEMEENTE_BKND,
                        new StringElement(AMSTERDAM),
                        null,
                        new DatumElement(VANDAAG),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(0, relatie.valideerInhoud().size());
    }

    @Test
    public void datumEindeVerledenR1875() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        GEMEENTE_BKND,
                        new StringElement(AMSTERDAM),
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(0, relatie.valideerInhoud().size());
    }

    @Test
    public void gemeenteEindeBestaatNognietR1880() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        GEMEENTE_BEGIN,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(Regel.R1880, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void gemeenteEindeBestaatNietMeerR1880() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        GEMEENTE_EIND,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(Regel.R1880, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void bestaandeGemeenteEindeR2179() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        GEMEENTE_BKND,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(0, relatie.valideerInhoud().size());
    }

    @Test
    public void nietBestaandeGemeenteEindeR2179() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        GEMEENTE_ONBKND,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(1, relatie.valideerInhoud().size());
        assertEquals(Regel.R2179, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void plaatsEindeLijstLeegR2178() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        GEMEENTE_BKND,
                        new StringElement(ONBEKEND),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(1, relatie.valideerInhoud().size());
        relatie.setVerzoekBericht(getBericht());
        assertEquals(Regel.R2178, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void plaatsEindeEindDatumLeegR2025() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        null,
                        GEMEENTE_BKND,
                        new StringElement(GRONINGEN),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(0, relatie.valideerInhoud().size());
    }

    @Test
    public void redenEeindeOnbekendR1853() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(ATTRIBUTEN, null, null, null, REDEN_ONBEKEND, null, null, null, null, null, null, null, null, null, null, null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(1, relatie.valideerInhoud().size());
        relatie.setVerzoekBericht(getBericht());
        assertEquals(Regel.R1853, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void redenEeindeBekendR1853() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(ATTRIBUTEN, null, null, null, REDEN_BEKEND, null, null, null, null, null, null, null, null, null, null, null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(0, relatie.valideerInhoud().size());
    }

    @Test
    @Bedrijfsregel(Regel.R2039)
    public void plaatsAanvangOngeldigR2039() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        new StringElement(GRONINGEN),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(1, relatie.valideerInhoud().size());
        relatie.setVerzoekBericht(getBericht());
        assertEquals(Regel.R2039, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void plaatsEindeOngeldigR2040() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement(GRONINGEN),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(1, relatie.valideerInhoud().size());
        relatie.setVerzoekBericht(getBericht());
        assertEquals(Regel.R2040, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    // Gooit ook altijd R1877 ivm landGebiedEindeCode mag geen NL zijn. Als deze gevuld is is het altijd buitenland.
    // Deze code komt niet voor in de XSD
    // bij NL.
    public void buitenlandsePlaatsRegioEindeOngeldigIcmNLR2029() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement(ONBEKEND),
                        null,
                        null,
                        LANDGEBIEDCODE_NL);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(2, relatie.valideerInhoud().size());
        relatie.setVerzoekBericht(getBericht());
        assertEquals(Regel.R1877, relatie.valideerInhoud().get(0).getRegel());
        assertEquals(Regel.R2029, relatie.valideerInhoud().get(1).getRegel());
    }

    @Test
    public void buitenlandsePlaatsRegioEindeOngeldigIcmIntR2029() {
        buitenlandsePlaatsRegioEindeOngeldigR2029(LANDGEBIEDCODE_INTERNATIONAAL);
    }

    @Test
    public void buitenlandsePlaatsRegioEindeOngeldigIcmOnbekendR2029() {
        buitenlandsePlaatsRegioEindeOngeldigR2029(LANDGEBIEDCODE_ONBEKEND);
    }

    private void buitenlandsePlaatsRegioEindeOngeldigR2029(final StringElement landOfGebiedEindeCode) {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement(ONBEKEND),
                        null,
                        null,
                        landOfGebiedEindeCode);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(1, relatie.valideerInhoud().size());
        relatie.setVerzoekBericht(getBericht());
        assertEquals(Regel.R2029, relatie.valideerInhoud().get(0).getRegel());
        assertTrue(relatie.isEindeLandOfGebiedOnbekendOfInternationaal());
    }

    @Test
    public void buitenlandsePlaatsRegioEindeOngeldigIcmBuitenlandR2029() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement(ONBEKEND),
                        new StringElement(ONBEKEND),
                        null,
                        LANDGEBIEDCODE_NIET_NL);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(0, relatie.valideerInhoud().size());
    }

    @Test
    public void buitenlandseRegioEindeOngeldigIcmBuitenlandR2020() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement(""),
                        null,
                        SPANJE);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(1, relatie.valideerInhoud().size());
        relatie.setVerzoekBericht(getBericht());
        assertEquals(Regel.R2020, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void buitenlandsePlaatsEindeOngeldigIcmBuitenlandR2020() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement(""),
                        null,
                        null,
                        SPANJE);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(1, relatie.valideerInhoud().size());
        relatie.setVerzoekBericht(getBericht());
        assertEquals(Regel.R2020, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void controleerOmschrijvingLocatieAanvangLandGebiedOnbekendR2048() {
        controleerOmschrijvingLocatieAanvangLandGebiedR2048(LANDGEBIEDCODE_ONBEKEND);
    }

    @Test
    public void controleerOmschrijvingLocatieAanvangLandGebiedIntR2048() {
        controleerOmschrijvingLocatieAanvangLandGebiedR2048(LANDGEBIEDCODE_INTERNATIONAAL);
    }

    private void controleerOmschrijvingLocatieAanvangLandGebiedR2048(final StringElement landOfGebiedAanvangCode) {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        landOfGebiedAanvangCode,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(1, relatie.valideerInhoud().size());
        relatie.setVerzoekBericht(getBericht());
        assertEquals(Regel.R2048, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void controleerOmschrijvingLocatieAanvangLandGebiedCorrectR2048() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        null,
                        null,
                        new StringElement(ONBEKEND),
                        LANDGEBIEDCODE_INTERNATIONAAL,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(0, relatie.valideerInhoud().size());
    }

    @Test
    public void controleerBuitenlandsePlaatsAanvangBuitenlandseRegioAanvangR2028() {
        // Internationaal
        testR2028(true, false, LANDGEBIEDCODE_INTERNATIONAAL, true);
        testR2028(false, true, LANDGEBIEDCODE_INTERNATIONAAL, true);
        testR2028(false, false, LANDGEBIEDCODE_INTERNATIONAAL, false);
        // NL
        testR2028(false, true, LANDGEBIEDCODE_NL, true);
        testR2028(false, true, LANDGEBIEDCODE_NL, true);
        testR2028(false, false, LANDGEBIEDCODE_NL, false);
        // Onbekend
        testR2028(false, true, LANDGEBIEDCODE_ONBEKEND, true);
        testR2028(false, true, LANDGEBIEDCODE_ONBEKEND, true);
        testR2028(false, false, LANDGEBIEDCODE_ONBEKEND, false);
    }

    private void testR2028(boolean buitenlandseRegio, boolean buitenlandsePlaats, final StringElement landGebied, final boolean geeftRegel) {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        buitenlandsePlaats ? new StringElement("plaats") : null,
                        buitenlandseRegio ? new StringElement("regio") : null,
                        new StringElement("omsLocatie"),
                        landGebied,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        final List<MeldingElement> meldingen = relatie.valideerInhoud();
        if (geeftRegel) {
            if (LANDGEBIEDCODE_NL.equals(landGebied)) {
                assertEquals(2, meldingen.size());
                assertEquals(Regel.R2028, meldingen.get(1).getRegel());
            } else {
                assertEquals(1, meldingen.size());
                assertEquals(Regel.R2028, meldingen.get(0).getRegel());
            }
        } else {
            if (LANDGEBIEDCODE_NL.equals(landGebied)) {
                assertEquals(1, meldingen.size());
                assertEquals(Regel.R1873, meldingen.get(0).getRegel());
            } else {
                assertEquals(0, meldingen.size());
            }
        }
    }

    @Test
    public void controleerAanvangLandGebiedNederlandHGPBuitenlandR1873() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        null,
                        null,
                        null,
                        null,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        LANDGEBIEDCODE_NL,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBerichtPrevalidatie());
        assertEquals(1, relatie.valideerInhoud().size());
        relatie.setVerzoekBericht(getBericht());
        assertEquals(Regel.R1873, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    public void controleerAanvangLandGebiedOngeldig1871() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(PEIL_DATUM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        ADEN,
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        assertEquals(Regel.R1871, relatie.valideerInhoud().get(0).getRegel());
    }

    @Test
    @Bedrijfsregel(Regel.R1872)
    public void testDatumAanvangInToekomst() {
        //setup
        final int morgen = DatumUtil.vandaag() + 1;
        final RelatieGroepElement
                relatieGroepElement =
                builder.maakRelatieGroepElement("CI_relatie_1", new ElementBuilder.RelatieGroepParameters().datumAanvang(morgen));
        final List<MeldingElement> meldingen = new ArrayList<>();
        //execute
        relatieGroepElement.controleerDatumAanvangNietInToekomst(meldingen);
        //valideer
        controleerRegels(meldingen, Regel.R1872);
    }

    @Test
    public void testDatumAanvangNietInToekomst() {
        //setup
        final RelatieGroepElement
                relatieGroepElement =
                builder.maakRelatieGroepElement("CI_relatie_1", new ElementBuilder.RelatieGroepParameters().datumAanvang(DatumUtil.vandaag()));
        final List<MeldingElement> meldingen = new ArrayList<>();
        //execute
        relatieGroepElement.controleerDatumAanvangNietInToekomst(meldingen);
        //valideer
        controleerRegels(meldingen);
    }

    @Test
    @Bedrijfsregel(Regel.R2030)
    public void testAanvangRelatieOngelijkNlR2030MetNlGemeente() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(VANDAAG),
                        GEMEENTE_BKND,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement("regio in buitenland"),
                        null,
                        new StringElement(DUITSLAND.getCode()),
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        final List<MeldingElement> meldingen = relatie.valideerInhoud();
        controleerRegels(meldingen, Regel.R2030);
    }

    @Test
    @Bedrijfsregel(Regel.R2039)
    @Bedrijfsregel(Regel.R2030)
    public void testAanvangRelatieOngelijkNlR2030MetNlPlaats() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(VANDAAG),
                        null,
                        new StringElement(AMSTERDAM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement("regio in buitenland"),
                        null,
                        new StringElement(DUITSLAND.getCode()),
                        null,
                        null,
                        null,
                        null);
        relatie.setVerzoekBericht(getBericht());
        final List<MeldingElement> meldingen = relatie.valideerInhoud();
        controleerRegels(meldingen, Regel.R2030, Regel.R2039);
    }

    @Test
    @Bedrijfsregel(Regel.R2031)
    public void testEindeRelatieOngelijkNlR2031MetNlGemeente() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(VANDAAG),
                        null,
                        null,
                        null,
                        null,
                        GEMEENTE_BKND,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement("regio in buitenland"),
                        null,
                        new StringElement(DUITSLAND.getCode()));
        relatie.setVerzoekBericht(getBericht());
        final List<MeldingElement> meldingen = relatie.valideerInhoud();
        controleerRegels(meldingen, Regel.R2031);
    }

    @Test
    @Bedrijfsregel(Regel.R2040)
    @Bedrijfsregel(Regel.R2031)
    public void testEindeRelatieOngelijkNlR2031MetNlPlaats() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        ATTRIBUTEN,
                        new DatumElement(VANDAAG),
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement(AMSTERDAM),
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement("regio in buitenland"),
                        null,
                        new StringElement(DUITSLAND.getCode()));
        relatie.setVerzoekBericht(getBericht());
        final List<MeldingElement> meldingen = relatie.valideerInhoud();
        controleerRegels(meldingen, Regel.R2031, Regel.R2040);
    }
}
