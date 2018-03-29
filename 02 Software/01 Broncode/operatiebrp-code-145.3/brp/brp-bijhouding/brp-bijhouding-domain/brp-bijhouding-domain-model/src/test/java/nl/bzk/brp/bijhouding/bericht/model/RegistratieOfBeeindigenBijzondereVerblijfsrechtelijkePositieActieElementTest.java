/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unittest voor {@link RegistratieBijzondereVerblijfsrechtelijkePositieActieElement}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegistratieOfBeeindigenBijzondereVerblijfsrechtelijkePositieActieElementTest extends AbstractElementTest {

    @Mock
    private BijhoudingVerzoekBericht bericht;

    private ElementBuilder elementBuilder;

    private Map<String, String> attr;
    private AdministratieveHandeling administratieveHandeling;
    private Partij partij;

    private BijhoudingPersoon bijhoudingPersoon;
    private RegistratieBijzondereVerblijfsrechtelijkePositieActieElement registratieActie;
    private BeeindigingBijzondereVerblijfsrechtelijkePositieActieElement beeindigingActie;

    @Before
    public void setup() {
        bijhoudingPersoon = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));
        bijhoudingPersoon.getPersoonIndicatieSet().add(new PersoonIndicatie(bijhoudingPersoon, SoortIndicatie.ONDER_CURATELE));

        initElementBuilder();

        when(bericht.getEntiteitVoorObjectSleutel(any(), any())).thenReturn(bijhoudingPersoon);
        when(bericht.getDatumOntvangst()).thenReturn(new DatumElement(DatumUtil.vandaag()));

        partij = new Partij("partij", "000001");
        partij.addPartijRol(new PartijRol(partij, Rol.BIJHOUDINGSORGAAN_COLLEGE));
        when(bericht.getZendendePartij()).thenReturn(partij);

        final PersoonBijhoudingHistorie
                historie = new PersoonBijhoudingHistorie(bijhoudingPersoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        bijhoudingPersoon.addPersoonBijhoudingHistorie(historie);
    }

    private void initElementBuilder() {
        elementBuilder = new ElementBuilder();
        attr = new LinkedHashMap<>();
        attr.put(OBJECTTYPE_ATTRIBUUT.toString(), "objecttype");
        attr.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "ci_test");
    }

    private void initAdministratieveHandelingElement(final List<ActieElement> acties) {
        initElementBuilder();
        administratieveHandeling =
                new AdministratieveHandeling(new Partij("test partij", "000000"), SoortAdministratieveHandeling.VERHUIZING_INTERGEMEENTELIJK, new Timestamp(
                        System.currentTimeMillis()));

        final AdministratieveHandelingElement
                administratieveHandelingElement =
                elementBuilder.maakAdministratieveHandelingElement("com_ah", new ElementBuilder.AdministratieveHandelingParameters().acties(acties)
                        .soort(AdministratieveHandelingElementSoort.WIJZIGING_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE).partijCode("5100")
                        .bronnen(Collections.emptyList()));

        administratieveHandelingElement.setVerzoekBericht(bericht);
        when(bericht.getAdministratieveHandeling()).thenReturn(administratieveHandelingElement);
    }

    @Test
    public void testVerwerkIndicatieVoorRegistratie() {
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        assertNull(bijhoudingPersoon.getPersoonIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE));

        registratieActie = maakRegistratieActieBijzondereVerblijfsrechtelijkePositie();
        initAdministratieveHandelingElement(Collections.singletonList(registratieActie));

        registratieActie.verwerk(bericht, administratieveHandeling);
        assertEquals(1, bijhoudingPersoon.getPersoonIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE).getPersoonIndicatieHistorieSet().size());
    }

    @Test
    public void testVerwerkingMetBestaandeIndicatie() {
        voegBVPIndicatieToeAanPersoon(false);
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);

        final PersoonIndicatie persoonIndicatie = bijhoudingPersoon.getPersoonIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        assertNotNull(persoonIndicatie);
        assertEquals(1, persoonIndicatie.getPersoonIndicatieHistorieSet().size());
        assertTrue(persoonIndicatie.getPersoonIndicatieHistorieSet().iterator().next().isActueel());

        final RegistratieBijzondereVerblijfsrechtelijkePositieActieElement registratieActie = maakRegistratieActieBijzondereVerblijfsrechtelijkePositie();
        initAdministratieveHandelingElement(Collections.singletonList(registratieActie));

        registratieActie.verwerk(bericht, administratieveHandeling);
        final PersoonIndicatie indicatie = bijhoudingPersoon.getPersoonIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        final long countVerval = indicatie.getPersoonIndicatieHistorieSet().stream().filter(historie -> historie.getActieVerval() != null).count();
        assertEquals(1, countVerval);
        assertEquals(2, indicatie.getPersoonIndicatieHistorieSet().size());
    }

    @Test
    public void testVerwerkIndicatieVoorBeeindiging() {
        voegBVPIndicatieToeAanPersoon(false);

        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        final PersoonIndicatie persoonIndicatie = bijhoudingPersoon.getPersoonIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        assertNotNull(persoonIndicatie);
        assertEquals(1, persoonIndicatie.getPersoonIndicatieHistorieSet().size());
        assertTrue(persoonIndicatie.getPersoonIndicatieHistorieSet().iterator().next().isActueel());

        beeindigingActie = maakBeeindigingActieBijzondereVerblijfsrechtelijkePositie();
        initAdministratieveHandelingElement(Collections.singletonList(beeindigingActie));
        beeindigingActie.verwerk(bericht, administratieveHandeling);

        final PersoonIndicatie indicatieNaBijhouding = bijhoudingPersoon.getPersoonIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        final long
                countVerval =
                indicatieNaBijhouding.getPersoonIndicatieHistorieSet().stream().filter(historieIndicatie -> historieIndicatie.getActieVerval() != null).count();
        assertEquals(1, countVerval);
        assertEquals(1, indicatieNaBijhouding.getPersoonIndicatieHistorieSet().size());
    }

    @Test
    public void testVerwerkActieNull() {
        registratieActie = maakRegistratieActieBijzondereVerblijfsrechtelijkePositie();
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.OPGESCHORT);
        final BRPActie brpActie = registratieActie.verwerk(bericht, administratieveHandeling);
        assertNull(brpActie);
        initAdministratieveHandelingElement(Collections.singletonList(registratieActie));
        beeindigingActie = maakBeeindigingActieBijzondereVerblijfsrechtelijkePositie();
        assertNull(beeindigingActie.verwerk(bericht, administratieveHandeling));
    }


    @Test
    public void testValideerSpecifiekOK() {
        final List<ActieElement> acties = new ArrayList<>();
        registratieActie = maakRegistratieActieBijzondereVerblijfsrechtelijkePositie();

        acties.add(registratieActie);
        initAdministratieveHandelingElement(acties);
        assertEquals(0, registratieActie.valideerSpecifiekeInhoud().size());
    }

    @Test
    public void testR1431FinseNationaliteit() {
        final Nationaliteit nationaliteit = new Nationaliteit("Finse",  "0056");
        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(bijhoudingPersoon, nationaliteit);
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(persoonNationaliteit);
        historie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        persoonNationaliteit.addPersoonNationaliteitHistorie(historie);
        bijhoudingPersoon.addPersoonNationaliteit(persoonNationaliteit);

        registratieActie = maakRegistratieActieBijzondereVerblijfsrechtelijkePositie();
        final List<MeldingElement> meldingen = registratieActie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testR1431VervallenNederlandseNationaliteit() {
        final Nationaliteit nationaliteit = new Nationaliteit("Nederlandse",  "0001");
        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(bijhoudingPersoon, nationaliteit);
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(persoonNationaliteit);
        historie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        historie.setDatumTijdVerval(Timestamp.from(Instant.now()));
        persoonNationaliteit.addPersoonNationaliteitHistorie(historie);
        bijhoudingPersoon.addPersoonNationaliteit(persoonNationaliteit);

        registratieActie = maakRegistratieActieBijzondereVerblijfsrechtelijkePositie();
        final List<MeldingElement> meldingen = registratieActie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testR1431NietMeerGeldigNederlandseNationaliteit() {
        final Nationaliteit nationaliteit = new Nationaliteit("Nederlandse",  "0001");
        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(bijhoudingPersoon, nationaliteit);
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(persoonNationaliteit);
        historie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        historie.setDatumAanvangGeldigheid(20060101);
        historie.setDatumEindeGeldigheid(20160101);
        persoonNationaliteit.addPersoonNationaliteitHistorie(historie);
        bijhoudingPersoon.addPersoonNationaliteit(persoonNationaliteit);

        registratieActie = maakRegistratieActieBijzondereVerblijfsrechtelijkePositie();
        final List<MeldingElement> meldingen = registratieActie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testR1431NederlandseNationaliteit() {
        final Nationaliteit nationaliteit = new Nationaliteit("Nederlandse",  "0001");
        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(bijhoudingPersoon, nationaliteit);
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(persoonNationaliteit);
        historie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        persoonNationaliteit.addPersoonNationaliteitHistorie(historie);
        bijhoudingPersoon.addPersoonNationaliteit(persoonNationaliteit);

        registratieActie = maakRegistratieActieBijzondereVerblijfsrechtelijkePositie();
        final List<MeldingElement> meldingen = registratieActie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1431, meldingen.get(0).getRegel());
    }

    @Test
    public void testR1431NederlandseNationaliteitNietMetBeeindigenBVP() {
        final Nationaliteit nationaliteit = new Nationaliteit("Nederlandse",  "0001");
        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(bijhoudingPersoon, nationaliteit);
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie(persoonNationaliteit);
        historie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        persoonNationaliteit.addPersoonNationaliteitHistorie(historie);
        bijhoudingPersoon.addPersoonNationaliteit(persoonNationaliteit);

        voegBVPIndicatieToeAanPersoon(false);
        beeindigingActie = maakBeeindigingActieBijzondereVerblijfsrechtelijkePositie();
        final List<MeldingElement> meldingen = beeindigingActie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testR2501GeenVervallenRijen() {
        voegBVPIndicatieToeAanPersoon(false);
        beeindigingActie = maakBeeindigingActieBijzondereVerblijfsrechtelijkePositie();
        final List<MeldingElement> meldingen = beeindigingActie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testR2501VervallenRijen() {
        voegBVPIndicatieToeAanPersoon(true);
        beeindigingActie = maakBeeindigingActieBijzondereVerblijfsrechtelijkePositie();
        final List<MeldingElement> meldingen = beeindigingActie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2501, meldingen.get(0).getRegel());
    }

    @Test
    public void testR2501GeenIndicatie() {
        beeindigingActie = maakBeeindigingActieBijzondereVerblijfsrechtelijkePositie();
        final List<MeldingElement> meldingen = beeindigingActie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2501, meldingen.get(0).getRegel());
    }

    @Test
    public void testR2501RegistratieGeenMelding() {
        registratieActie = maakRegistratieActieBijzondereVerblijfsrechtelijkePositie();
        final List<MeldingElement> meldingen = registratieActie.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    private void voegBVPIndicatieToeAanPersoon(final boolean isVervallen) {
        final PersoonIndicatie indicatie = new PersoonIndicatie(bijhoudingPersoon, SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        final PersoonIndicatieHistorie historie = new PersoonIndicatieHistorie(indicatie, true);
        final Instant nu = Instant.now();
        final Instant gisteren = nu.minus(1, ChronoUnit.DAYS);
        historie.setDatumTijdRegistratie(Timestamp.from(gisteren));
        if (isVervallen) {
            historie.setDatumTijdVerval(Timestamp.from(nu));
        }
        indicatie.addPersoonIndicatieHistorie(historie);
        bijhoudingPersoon.getPersoonIndicatieSet().add(indicatie);
    }

    private RegistratieBijzondereVerblijfsrechtelijkePositieActieElement maakRegistratieActieBijzondereVerblijfsrechtelijkePositie() {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.indicaties(maakIndicatie(true));

        PersoonGegevensElement persoon = elementBuilder.maakPersoonGegevensElement("ci_persoon", "object", null, persoonParameters);
        RegistratieBijzondereVerblijfsrechtelijkePositieActieElement
                actieBVP =
                new RegistratieBijzondereVerblijfsrechtelijkePositieActieElement(attr, null, null, Collections.emptyList(), persoon);
        persoon.setVerzoekBericht(bericht);
        actieBVP.setVerzoekBericht(bericht);
        return actieBVP;
    }

    private BeeindigingBijzondereVerblijfsrechtelijkePositieActieElement maakBeeindigingActieBijzondereVerblijfsrechtelijkePositie() {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.indicaties(maakIndicatie(null));

        PersoonGegevensElement persoon = elementBuilder.maakPersoonGegevensElement("ci_persoon2", "object", null, persoonParameters);
        BeeindigingBijzondereVerblijfsrechtelijkePositieActieElement
                actieBVP =
                new BeeindigingBijzondereVerblijfsrechtelijkePositieActieElement(attr, null, null, Collections.emptyList(), persoon);
        persoon.setVerzoekBericht(bericht);
        actieBVP.setVerzoekBericht(bericht);
        return actieBVP;
    }

    private List<IndicatieElement> maakIndicatie(final Boolean indicatie) {
        final String commId = indicatie != null ? "comm_id" : "comm_id2";
        return Collections.singletonList(elementBuilder
                .maakBijzondereVerblijfsrechtelijkePositieIndicatieElement(commId, new ElementBuilder.IndicatieElementParameters().heeftIndicatie(indicatie)));
    }
}
