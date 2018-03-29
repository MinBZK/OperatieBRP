/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link MaterieleTijdslijnHelper}.
 */
@RunWith(MockitoJUnitRunner.class)
public class MaterieleTijdslijnHelperTest extends AbstractElementTest{

    private static final String PARTNER_OBJ_SLEUTEL = "partnerObjSleutel";
    private static final String IK_OBJ_SLEUTEL = "ikObjSleutel";
    public static final String PARTIJ_CODE = "053001";
    private ElementBuilder builder;
    private BijhoudingVerzoekBericht bericht;
    private BijhoudingPersoon persoon;
    private BijhoudingPersoon partner;

    @Before
    public void setUp() {
        this.builder = new ElementBuilder();
        bericht = mock(BijhoudingVerzoekBericht.class);
        persoon = mock(BijhoudingPersoon.class);
        partner = mock(BijhoudingPersoon.class);
        final Betrokkenheid betrokkenheid = mock(Betrokkenheid.class);
        final Betrokkenheid betrokkenheid2 = mock(Betrokkenheid.class);

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, IK_OBJ_SLEUTEL)).thenReturn(persoon);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PARTNER_OBJ_SLEUTEL)).thenReturn(partner);
        when(persoon.getActuelePartners()).thenReturn(Collections.singleton(betrokkenheid));
        when(betrokkenheid.getPersoon()).thenReturn(partner);
        when(betrokkenheid2.getPersoon()).thenReturn(new Persoon(SoortPersoon.INGESCHREVENE));
    }

    @Test(expected = AssertionError.class)
    public void testPrivateConstructor() throws Throwable {
        try {
            Constructor<MaterieleTijdslijnHelper> c = MaterieleTijdslijnHelper.class.getDeclaredConstructor();
            c.setAccessible(true);
            final MaterieleTijdslijnHelper u = c.newInstance();
        } catch (Exception e) {
            throw e.getCause();
        }
    }

    @Test
    public void controleerTijdslijn_GeenCorrectieHandeling() throws Exception {
        final List<MeldingElement> meldingen = new ArrayList<>();
        MaterieleTijdslijnHelper.controleerTijdslijn(null,
                maakAdministratieveHandelingElement(AdministratieveHandelingElementSoort.VOLTREKKING_HUWELIJK_IN_NEDERLAND, Collections.emptyList()),
                meldingen);
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testViaAdministratieHandeling() throws Exception {
        final long historieId = 1L;
        final PersoonIDHistorie historie = new PersoonIDHistorie(partner);
        historie.setId(historieId);
        historie.setDatumAanvangGeldigheid(2015_01_01);

        when(partner.getPersoonIDHistorieSet()).thenReturn(Collections.singleton(historie));
        when(partner.maakNieuweIDHistorieVoorCorrectie(any(IdentificatienummersElement.class))).thenReturn(new PersoonIDHistorie(partner));
        when(partner.zoekRelatieHistorieVoorVoorkomenSleutel("" + historieId, PersoonIDHistorie.class)).thenReturn(historie);

        // Mock steps voor AH validatie.
        final Partij partij = mock(Partij.class);
        when (getDynamischeStamtabelRepository().getPartijByCode(PARTIJ_CODE)).thenReturn(partij);
        when(bericht.getZendendePartij()).thenReturn(partij);
        when(bericht.getDatumOntvangst()).thenReturn(new DatumElement(DatumUtil.vandaag()));
        when(bericht.getStuurgegevens()).thenReturn(getStuurgegevensElement());
        when(partij.getRollen()).thenReturn(Collections.singleton(Rol.BIJHOUDINGSORGAAN_COLLEGE));
        final PersoonBijhoudingHistorie bijhoudingHistorie = new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        when(persoon.getPersoonBijhoudingHistorieSet()).thenReturn(Collections.singleton(bijhoudingHistorie));

        final ElementBuilder.PersoonParameters vervalPartnerParams = new ElementBuilder.PersoonParameters();
        vervalPartnerParams.identificatienummers(builder.maakIdentificatienummersElementVoorVerval("vervalIdRij", "" + historieId));
        final ElementBuilder.PersoonParameters registratiePartnerParams = new ElementBuilder.PersoonParameters();
        registratiePartnerParams.identificatienummers(builder.maakIdentificatienummersElement("registratieIdRij", "123456789", "1234567890"));

        final AdministratieveHandelingElement administratieveHandelingElement = maakAHElementCorrectiePartnerGegevensHuwelijk(
                maakCorrectieActiesVoorIdHistorie(vervalPartnerParams, registratiePartnerParams));
        administratieveHandelingElement.setVerzoekBericht(bericht);
        assertEquals(0, administratieveHandelingElement.valideerInhoud().size());
    }

    @Test
    public void controleerTijdslijn_HistorieMetGaten_TijdlijnOK() throws Exception {
        final List<MeldingElement> meldingen = new ArrayList<>();

        final long historieId = 1L;
        final PersoonIDHistorie historie = new PersoonIDHistorie(partner);
        historie.setId(historieId);
        historie.setDatumAanvangGeldigheid(2015_01_01);

        when(partner.getPersoonIDHistorieSet()).thenReturn(Collections.singleton(historie));
        when(partner.maakNieuweIDHistorieVoorCorrectie(any(IdentificatienummersElement.class))).thenReturn(new PersoonIDHistorie(partner));
        when(partner.zoekRelatieHistorieVoorVoorkomenSleutel("" + historieId, PersoonIDHistorie.class)).thenReturn(historie);

        final ElementBuilder.PersoonParameters vervalPartnerParams = new ElementBuilder.PersoonParameters();
        vervalPartnerParams.identificatienummers(builder.maakIdentificatienummersElementVoorVerval("vervalIdRij", "" + historieId));
        final ElementBuilder.PersoonParameters registratiePartnerParams = new ElementBuilder.PersoonParameters();
        registratiePartnerParams.identificatienummers(builder.maakIdentificatienummersElement("registratieIdRij", "123456789", "1234567890"));

        MaterieleTijdslijnHelper
                .controleerTijdslijn(persoon, maakAHElementCorrectiePartnerGegevensHuwelijk(
                        maakCorrectieActiesVoorIdHistorie(vervalPartnerParams, registratiePartnerParams)), meldingen);
        assertEquals(0, meldingen.size());
    }

    @Test
    public void controleerTijdslijn_HistorieMetGaten_Gat() throws Exception {
        final List<MeldingElement> meldingen = new ArrayList<>();

        final long historieId = 1L;
        final PersoonIDHistorie historie = new PersoonIDHistorie(partner);
        historie.setId(historieId);
        historie.setDatumAanvangGeldigheid(2014_01_01);

        final PersoonIDHistorie historie2 = new PersoonIDHistorie(partner);
        historie2.setDatumAanvangGeldigheid(2012_01_01);
        historie2.setDatumEindeGeldigheid(2014_01_01);

        when(partner.getPersoonIDHistorieSet()).thenReturn(new HashSet<>(Arrays.asList(historie, historie2)));
        when(partner.maakNieuweIDHistorieVoorCorrectie(any(IdentificatienummersElement.class))).thenReturn(new PersoonIDHistorie(partner));
        when(partner.zoekRelatieHistorieVoorVoorkomenSleutel("" + historieId, PersoonIDHistorie.class)).thenReturn(historie);

        final ElementBuilder.PersoonParameters vervalPartnerParams = new ElementBuilder.PersoonParameters();
        vervalPartnerParams.identificatienummers(builder.maakIdentificatienummersElementVoorVerval("vervalIdRij", "" + historieId));
        final ElementBuilder.PersoonParameters registratiePartnerParams = new ElementBuilder.PersoonParameters();
        registratiePartnerParams.identificatienummers(builder.maakIdentificatienummersElement("registratieIdRij", "123456789", "1234567890"));

        MaterieleTijdslijnHelper
                .controleerTijdslijn(persoon, maakAHElementCorrectiePartnerGegevensHuwelijk(
                        maakCorrectieActiesVoorIdHistorie(vervalPartnerParams, registratiePartnerParams)),
                        meldingen);
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2675)
    @Test
    public void controleerTijdslijn_HistorieMetGaten_Overlap() throws Exception {
        final List<MeldingElement> meldingen = new ArrayList<>();

        final long historieId = 1L;
        final PersoonIDHistorie historie = new PersoonIDHistorie(partner);
        historie.setId(historieId);
        historie.setDatumAanvangGeldigheid(2017_01_01);

        final PersoonIDHistorie historie2 = new PersoonIDHistorie(partner);
        historie2.setDatumAanvangGeldigheid(2015_01_01);
        historie2.setDatumEindeGeldigheid(2017_01_01);

        when(partner.getPersoonIDHistorieSet()).thenReturn(new HashSet<>(Arrays.asList(historie, historie2)));
        when(partner.maakNieuweIDHistorieVoorCorrectie(any(IdentificatienummersElement.class))).thenReturn(new PersoonIDHistorie(partner));
        when(partner.zoekRelatieHistorieVoorVoorkomenSleutel("" + historieId, PersoonIDHistorie.class)).thenReturn(historie);

        final ElementBuilder.PersoonParameters vervalPartnerParams = new ElementBuilder.PersoonParameters();
        vervalPartnerParams.identificatienummers(builder.maakIdentificatienummersElementVoorVerval("vervalIdRij", "" + historieId));
        final ElementBuilder.PersoonParameters registratiePartnerParams = new ElementBuilder.PersoonParameters();
        registratiePartnerParams.identificatienummers(builder.maakIdentificatienummersElement("registratieIdRij", "123456789", "1234567890"));

        MaterieleTijdslijnHelper
                .controleerTijdslijn(persoon, maakAHElementCorrectiePartnerGegevensHuwelijk(
                        maakCorrectieActiesVoorIdHistorie(vervalPartnerParams, registratiePartnerParams)), meldingen);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2675, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2675)
    @Test
    public void controleerTijdslijn_HistorieMetGaten_Overlap_GeenDatumEinde() throws Exception {
        final List<MeldingElement> meldingen = new ArrayList<>();

        final PersoonIDHistorie historie = new PersoonIDHistorie(partner);
        historie.setId(2L);
        historie.setDatumAanvangGeldigheid(2015_01_01);

        final PersoonIDHistorie historie2 = new PersoonIDHistorie(partner);
        historie2.setDatumAanvangGeldigheid(2016_01_01);

        when(partner.getPersoonIDHistorieSet()).thenReturn(Collections.singleton(historie));
        when(partner.maakNieuweIDHistorieVoorCorrectie(any(IdentificatienummersElement.class))).thenReturn(historie2);

        final ElementBuilder.PersoonParameters vervalPartnerParams = new ElementBuilder.PersoonParameters();
        vervalPartnerParams.identificatienummers(builder.maakIdentificatienummersElementVoorVerval("vervalIdRij", "1"));
        final ElementBuilder.PersoonParameters registratiePartnerParams = new ElementBuilder.PersoonParameters();
        registratiePartnerParams.identificatienummers(builder.maakIdentificatienummersElement("registratieIdRij", "123456789", "1234567890"));

        MaterieleTijdslijnHelper
                .controleerTijdslijn(persoon, maakAHElementCorrectiePartnerGegevensHuwelijk(
                        maakCorrectieActiesVoorIdHistorie(vervalPartnerParams, registratiePartnerParams)),
                        meldingen);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2675, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerTijdslijn_HistorieMetGaten_DatumAanvangDeelsOnbekend() throws Exception {
        final List<MeldingElement> meldingen = new ArrayList<>();

        final long historieId = 1L;
        final PersoonIDHistorie historie = new PersoonIDHistorie(partner);
        historie.setId(historieId);
        historie.setDatumAanvangGeldigheid(2015_01_00);

        when(partner.getPersoonIDHistorieSet()).thenReturn(Collections.singleton(historie));
        when(partner.maakNieuweIDHistorieVoorCorrectie(any(IdentificatienummersElement.class))).thenReturn(new PersoonIDHistorie(partner));

        final ElementBuilder.PersoonParameters registratiePartnerParams = new ElementBuilder.PersoonParameters();
        registratiePartnerParams.identificatienummers(builder.maakIdentificatienummersElement("registratieIdRij", "123456789", "1234567890"));

        MaterieleTijdslijnHelper
                .controleerTijdslijn(persoon, maakAHElementCorrectiePartnerGegevensHuwelijk(maakCorrectieActiesVoorIdHistorie(null, registratiePartnerParams)),
                        meldingen);
        assertEquals(0, meldingen.size());
    }

    @Test
    public void controleerTijdslijn_HistorieZonderGaten_TijdlijnOK() throws Exception {
        final List<MeldingElement> meldingen = new ArrayList<>();

        final long historieId = 1L;
        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN);
        historie.setId(historieId);
        historie.setDatumAanvangGeldigheid(2015_01_01);

        when(partner.getPersoonGeslachtsaanduidingHistorieSet()).thenReturn(Collections.singleton(historie));
        when(partner.maakNieuweGeslachtsaanduidingHistorieVoorCorrectie(any(GeslachtsaanduidingElement.class)))
                .thenReturn(new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN));
        when(partner.zoekRelatieHistorieVoorVoorkomenSleutel("" + historieId, PersoonGeslachtsaanduidingHistorie.class)).thenReturn(historie);

        final ElementBuilder.PersoonParameters vervalPartnerParams = new ElementBuilder.PersoonParameters();
        vervalPartnerParams.geslachtsaanduiding(builder.maakGeslachtsaanduidingElementVoorVerval("vervalIdRij", "" + historieId));
        final ElementBuilder.PersoonParameters registratiePartnerParams = new ElementBuilder.PersoonParameters();
        registratiePartnerParams.geslachtsaanduiding(builder.maakGeslachtsaanduidingElement("registratieIdRij", "M"));

        MaterieleTijdslijnHelper
                .controleerTijdslijn(persoon, maakAHElementCorrectiePartnerGegevensHuwelijk(
                        maakCorrectieActiesVoorGeslachtsaanduidingHistorie(vervalPartnerParams, registratiePartnerParams)), meldingen);
        assertEquals(0, meldingen.size());
    }

    @Test
    public void controleerTijdslijn_HistorieZonderGaten_GeenGat() throws Exception {
        final List<MeldingElement> meldingen = new ArrayList<>();

        final long historieId = 1L;
        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN);
        historie.setId(historieId);
        historie.setDatumAanvangGeldigheid(2014_01_01);

        final PersoonGeslachtsaanduidingHistorie historie2 = new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN);
        historie2.setDatumAanvangGeldigheid(2012_01_01);
        historie2.setDatumEindeGeldigheid(2016_01_01);

        when(partner.getPersoonGeslachtsaanduidingHistorieSet()).thenReturn(new HashSet<>(Arrays.asList(historie, historie2)));
        when(partner.maakNieuweGeslachtsaanduidingHistorieVoorCorrectie(any(GeslachtsaanduidingElement.class)))
                .thenReturn(new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN));
        when(partner.zoekRelatieHistorieVoorVoorkomenSleutel("" + historieId, PersoonGeslachtsaanduidingHistorie.class)).thenReturn(historie);

        final ElementBuilder.PersoonParameters vervalPartnerParams = new ElementBuilder.PersoonParameters();
        vervalPartnerParams.geslachtsaanduiding(builder.maakGeslachtsaanduidingElementVoorVerval("vervalIdRij", "" + historieId));
        final ElementBuilder.PersoonParameters registratiePartnerParams = new ElementBuilder.PersoonParameters();
        registratiePartnerParams.geslachtsaanduiding(builder.maakGeslachtsaanduidingElement("registratieIdRij", "M"));

        MaterieleTijdslijnHelper
                .controleerTijdslijn(persoon, maakAHElementCorrectiePartnerGegevensHuwelijk(
                        maakCorrectieActiesVoorGeslachtsaanduidingHistorie(vervalPartnerParams, registratiePartnerParams)), meldingen);
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2530)
    @Test
    public void controleerTijdslijn_HistorieZonderGaten_Gat() throws Exception {
        final List<MeldingElement> meldingen = new ArrayList<>();

        final long historieId = 1L;
        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN);
        historie.setId(historieId);
        historie.setDatumAanvangGeldigheid(2014_01_01);

        final PersoonGeslachtsaanduidingHistorie historie2 = new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN);
        historie2.setDatumAanvangGeldigheid(2012_01_01);
        historie2.setDatumEindeGeldigheid(2014_01_01);

        when(partner.getPersoonGeslachtsaanduidingHistorieSet()).thenReturn(new HashSet<>(Arrays.asList(historie, historie2)));
        when(partner.maakNieuweGeslachtsaanduidingHistorieVoorCorrectie(any(GeslachtsaanduidingElement.class)))
                .thenReturn(new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN));
        when(partner.zoekRelatieHistorieVoorVoorkomenSleutel("" + historieId, PersoonGeslachtsaanduidingHistorie.class)).thenReturn(historie);

        final ElementBuilder.PersoonParameters vervalPartnerParams = new ElementBuilder.PersoonParameters();
        vervalPartnerParams.geslachtsaanduiding(builder.maakGeslachtsaanduidingElementVoorVerval("vervalIdRij", "" + historieId));
        final ElementBuilder.PersoonParameters registratiePartnerParams = new ElementBuilder.PersoonParameters();
        registratiePartnerParams.geslachtsaanduiding(builder.maakGeslachtsaanduidingElement("registratieIdRij", "M"));

        MaterieleTijdslijnHelper
                .controleerTijdslijn(persoon, maakAHElementCorrectiePartnerGegevensHuwelijk(
                        maakCorrectieActiesVoorGeslachtsaanduidingHistorie(vervalPartnerParams, registratiePartnerParams)), meldingen);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2530, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2530)
    @Test
    public void controleerTijdslijn_HistorieZonderGaten_Overlap() throws Exception {
        final List<MeldingElement> meldingen = new ArrayList<>();

        final long historieId = 1L;
        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN);
        historie.setId(historieId);
        historie.setDatumAanvangGeldigheid(2017_01_01);

        final PersoonGeslachtsaanduidingHistorie historie2 = new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN);
        historie2.setDatumAanvangGeldigheid(2015_01_01);
        historie2.setDatumEindeGeldigheid(2017_01_01);

        when(partner.getPersoonGeslachtsaanduidingHistorieSet()).thenReturn(new HashSet<>(Arrays.asList(historie, historie2)));
        when(partner.maakNieuweGeslachtsaanduidingHistorieVoorCorrectie(any(GeslachtsaanduidingElement.class)))
                .thenReturn(new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN));
        when(partner.zoekRelatieHistorieVoorVoorkomenSleutel("" + historieId, PersoonGeslachtsaanduidingHistorie.class)).thenReturn(historie);

        final ElementBuilder.PersoonParameters vervalPartnerParams = new ElementBuilder.PersoonParameters();
        vervalPartnerParams.geslachtsaanduiding(builder.maakGeslachtsaanduidingElementVoorVerval("vervalIdRij", "" + historieId));
        final ElementBuilder.PersoonParameters registratiePartnerParams = new ElementBuilder.PersoonParameters();
        registratiePartnerParams.geslachtsaanduiding(builder.maakGeslachtsaanduidingElement("registratieIdRij", "M"));

        MaterieleTijdslijnHelper
                .controleerTijdslijn(persoon, maakAHElementCorrectiePartnerGegevensHuwelijk(
                        maakCorrectieActiesVoorGeslachtsaanduidingHistorie(vervalPartnerParams, registratiePartnerParams)), meldingen);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2530, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2530)
    @Test
    public void controleerTijdslijn_HistorieZonderGaten_Overlap_GeenDatumEinde() throws Exception {
        final List<MeldingElement> meldingen = new ArrayList<>();

        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN);
        historie.setId(2L);
        historie.setDatumAanvangGeldigheid(2015_01_01);

        when(partner.getPersoonGeslachtsaanduidingHistorieSet()).thenReturn(Collections.singleton(historie));
        when(partner.maakNieuweGeslachtsaanduidingHistorieVoorCorrectie(any(GeslachtsaanduidingElement.class)))
                .thenReturn(new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN));

        final ElementBuilder.PersoonParameters vervalPartnerParams = new ElementBuilder.PersoonParameters();
        vervalPartnerParams.geslachtsaanduiding(builder.maakGeslachtsaanduidingElementVoorVerval("vervalIdRij", "1"));
        final ElementBuilder.PersoonParameters registratiePartnerParams = new ElementBuilder.PersoonParameters();
        registratiePartnerParams.geslachtsaanduiding(builder.maakGeslachtsaanduidingElement("registratieIdRij", "M"));

        MaterieleTijdslijnHelper
                .controleerTijdslijn(persoon, maakAHElementCorrectiePartnerGegevensHuwelijk(
                        maakCorrectieActiesVoorGeslachtsaanduidingHistorie(vervalPartnerParams, registratiePartnerParams)), meldingen);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2530, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2691)
    @Test
    public void controleerTijdslijn_HistorieZonderGaten_GeenGeldigeRijOver() throws Exception {
        final List<MeldingElement> meldingen = new ArrayList<>();

        final long historieId = 1L;
        final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN);
        historie.setId(historieId);
        historie.setDatumAanvangGeldigheid(2014_01_01);

        when(partner.getPersoonGeslachtsaanduidingHistorieSet()).thenReturn(new HashSet<>(Collections.singletonList(historie)));
        when(partner.maakNieuweGeslachtsaanduidingHistorieVoorCorrectie(any(GeslachtsaanduidingElement.class)))
                .thenReturn(new PersoonGeslachtsaanduidingHistorie(partner, Geslachtsaanduiding.MAN));
        when(partner.zoekRelatieHistorieVoorVoorkomenSleutel("" + historieId, PersoonGeslachtsaanduidingHistorie.class)).thenReturn(historie);

        final ElementBuilder.PersoonParameters vervalPartnerParams = new ElementBuilder.PersoonParameters();
        vervalPartnerParams.geslachtsaanduiding(builder.maakGeslachtsaanduidingElementVoorVerval("vervalIdRij", "" + historieId));
        final ElementBuilder.PersoonParameters registratiePartnerParams = new ElementBuilder.PersoonParameters();
        registratiePartnerParams.geslachtsaanduiding(builder.maakGeslachtsaanduidingElement("registratieIdRij", "M"));

        MaterieleTijdslijnHelper
                .controleerTijdslijn(persoon, maakAHElementCorrectiePartnerGegevensHuwelijk(
                        maakCorrectieActiesVoorGeslachtsaanduidingHistorie(vervalPartnerParams, registratiePartnerParams, 2017_01_01)), meldingen);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2691, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerTijdslijn_HistorieZonderGaten_TijdlijnOK_SamengesteldeNaamHistorie() throws Exception {
        final List<MeldingElement> meldingen = new ArrayList<>();

        final long historieId = 1L;
        final PersoonSamengesteldeNaamHistorie historie = new PersoonSamengesteldeNaamHistorie(partner, "Stam", false, false);
        historie.setId(historieId);
        historie.setDatumAanvangGeldigheid(2015_01_01);

        when(partner.getPersoonSamengesteldeNaamHistorieSet()).thenReturn(Collections.singleton(historie));
        when(partner.maakNieuweSamengesteldeNaamHistorieVoorCorrectie(any(SamengesteldeNaamElement.class)))
                .thenReturn(new PersoonSamengesteldeNaamHistorie(partner, "Stam", false, false));
        when(partner.zoekRelatieHistorieVoorVoorkomenSleutel("" + historieId, PersoonSamengesteldeNaamHistorie.class)).thenReturn(historie);

        final ElementBuilder.PersoonParameters vervalPartnerParams = new ElementBuilder.PersoonParameters();
        vervalPartnerParams.samengesteldeNaam(builder.maakSamengesteldeNaamElementVoorVerval("vervalIdRij", "" + historieId));
        final ElementBuilder.PersoonParameters registratiePartnerParams = new ElementBuilder.PersoonParameters();
        final ElementBuilder.NaamParameters naamParams = new ElementBuilder.NaamParameters();
        naamParams.geslachtsnaamstam("Stam");
        naamParams.indicatieNamenreeks(false);
        registratiePartnerParams.samengesteldeNaam(builder.maakSamengesteldeNaamElement("registratieIdRij", naamParams));

        MaterieleTijdslijnHelper
                .controleerTijdslijn(persoon, maakAHElementCorrectiePartnerGegevensHuwelijk(
                        maakCorrectieActiesVoorSamengesteldeNaamHistorie(vervalPartnerParams, registratiePartnerParams)), meldingen);
        assertEquals(0, meldingen.size());
    }

    private List<ActieElement> maakCorrectieActiesVoorIdHistorie(final ElementBuilder.PersoonParameters vervalPartnerParams,
                                                                 final ElementBuilder.PersoonParameters registratiePartnerParams) {
        final List<ActieElement> acties = new ArrayList<>();
        if (vervalPartnerParams != null) {
            final CorrectieVervalIdentificatienummersGerelateerde actie =
                    builder.maakCorrectieVervalIdentificatienummersGerelateerdeActieElement("idVerval", maakPersoonRelatieElement(vervalPartnerParams, 1), 'S');
            actie.setVerzoekBericht(bericht);
            acties.add(actie);
        }
        if (registratiePartnerParams != null) {
            final CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerde actie =
                    builder.maakCorrectieRegistratieIdentificatienummersGerelateerdeActieElement("idRegistratie", 2016_01_01, null,
                            maakPersoonRelatieElement(registratiePartnerParams, 2));
            actie.setVerzoekBericht(bericht);
            acties.add(actie);
        }

        return acties;
    }

    private List<ActieElement> maakCorrectieActiesVoorGeslachtsaanduidingHistorie(final ElementBuilder.PersoonParameters vervalPartnerParams,
                                                                                  final ElementBuilder.PersoonParameters registratiePartnerParams) {
        return maakCorrectieActiesVoorGeslachtsaanduidingHistorie(vervalPartnerParams, registratiePartnerParams, null);
    }

    private List<ActieElement> maakCorrectieActiesVoorGeslachtsaanduidingHistorie(final ElementBuilder.PersoonParameters vervalPartnerParams,
                                                                                  final ElementBuilder.PersoonParameters registratiePartnerParams,
                                                                                  final Integer datumEindeGeldigheid) {
        final List<ActieElement> acties = new ArrayList<>();
        if (vervalPartnerParams != null) {
            final CorrectieVervalGeslachtsaanduidingGerelateerde actie =
                    builder.maakCorrectieVervalGeslachtsaanduidingGerelateerdeActieElement("idVerval", maakPersoonRelatieElement(vervalPartnerParams, 1), 'S');
            actie.setVerzoekBericht(bericht);
            acties.add(actie);
        }
        if (registratiePartnerParams != null) {
            final CorrectieRegistratieGeslachtsaanduidingRegistratieGegevensGerelateerde actie =
                    builder.maakCorrectieRegistratieGeslachtsaanduidingGerelateerdeActieElement("idRegistratie", 2016_01_01, datumEindeGeldigheid,
                            maakPersoonRelatieElement(registratiePartnerParams, 2));
            actie.setVerzoekBericht(bericht);
            acties.add(actie);
        }

        return acties;
    }

    private List<ActieElement> maakCorrectieActiesVoorSamengesteldeNaamHistorie(final ElementBuilder.PersoonParameters vervalPartnerParams,
                                                                                final ElementBuilder.PersoonParameters registratiePartnerParams) {
        final List<ActieElement> acties = new ArrayList<>();
        if (vervalPartnerParams != null) {
            final CorrectieVervalSamengesteldeNaamGerelateerde actie =
                    builder.maakCorrectieVervalSamengesteldeNaamGerelateerdeActieElement("idVerval", maakPersoonRelatieElement(vervalPartnerParams, 1), 'S');
            actie.setVerzoekBericht(bericht);
            acties.add(actie);
        }
        if (registratiePartnerParams != null) {
            final CorrectieRegistratieSamengesteldeNaamRegistratieGegevensGerelateerde actie =
                    builder.maakCorrectieRegistratieSamengesteldeNaamGerelateerdeActieElement("idRegistratie", 2016_01_01, null,
                            maakPersoonRelatieElement(registratiePartnerParams, 2));
            actie.setVerzoekBericht(bericht);
            acties.add(actie);
        }

        return acties;
    }

    private AdministratieveHandelingElement maakAHElementCorrectiePartnerGegevensHuwelijk(final List<ActieElement> acties) {
        return maakAdministratieveHandelingElement(AdministratieveHandelingElementSoort.CORRECTIE_PARTNERGEGEVENS_HUWELIJK, acties);
    }

    private AdministratieveHandelingElement maakAdministratieveHandelingElement(final AdministratieveHandelingElementSoort soort,
                                                                                final List<ActieElement> acties) {
        final ElementBuilder.AdministratieveHandelingParameters params = new ElementBuilder.AdministratieveHandelingParameters();
        params.acties(acties);
        params.soort(soort);
        params.partijCode(PARTIJ_CODE);
        return builder.maakAdministratieveHandelingElement("ah", params);
    }

    private PersoonRelatieElement maakPersoonRelatieElement(final ElementBuilder.PersoonParameters partnerParams, final int volgnr) {
        final PersoonGegevensElement partner = builder.maakPersoonGegevensElement("partner_" + volgnr, PARTNER_OBJ_SLEUTEL, null, partnerParams);
        final BetrokkenheidElement partnerBetrokkenheid = builder.maakBetrokkenheidElement("partnerBetr_" + volgnr, "partnerBetrObjSleutel",
                BetrokkenheidElementSoort.PARTNER, partner, null);
        final GeregistreerdPartnerschapElement gpElement =
                builder.maakGeregistreerdPartnerschapElement("gpElement_" + volgnr, null, Collections.singletonList(partnerBetrokkenheid));
        final BetrokkenheidElement ikBetrokkenheid =
                builder.maakBetrokkenheidElement("ikBetr_" + volgnr, "betrObjSleutel", null, BetrokkenheidElementSoort.OUDER, gpElement);
        final PersoonRelatieElement
                ikPersoon =
                builder.maakPersoonRelatieElement("persoon_" + volgnr, null, IK_OBJ_SLEUTEL, Collections.singletonList(ikBetrokkenheid));
        partner.setVerzoekBericht(bericht);
        ikPersoon.setVerzoekBericht(bericht);
        return ikPersoon;
    }
}