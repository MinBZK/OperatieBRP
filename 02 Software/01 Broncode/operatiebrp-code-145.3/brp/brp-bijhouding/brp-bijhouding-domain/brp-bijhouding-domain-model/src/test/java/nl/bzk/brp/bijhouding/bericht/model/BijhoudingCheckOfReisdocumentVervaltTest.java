/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Testen voor {@link BijhoudingPersoon}.
 */
public class BijhoudingCheckOfReisdocumentVervaltTest extends AbstractNaamTest {
    public static final Timestamp DATUM_TIJD_REGISTRATIE = new Timestamp(DatumUtil.nu().getTime());
    ElementBuilder builder;
    Set<PersoonNationaliteit> nationaliteiten;
    Persoon persoonEntity;
    BijhoudingPersoon persoon;
    Map<Integer, String> voornamenBericht;
    Set<PersoonVoornaam> voornamenDatabase;

    @Before
    public void setup() {
        builder = new ElementBuilder();
        nationaliteiten = new HashSet<>();
        persoonEntity = mock(Persoon.class);
        persoon = new BijhoudingPersoon();
        voornamenDatabase = new HashSet<>();
        voornamenBericht = new HashMap<>();
        when(persoonEntity.getPersoonNationaliteitSet()).thenReturn(nationaliteiten);
    }

    @Test
    public void testVervallenDocumentNieuwPersoon() {
        assertFalse(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentGeenActueelReisdocument() {
        final BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.emptySet());
        assertFalse(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
        final Set<PersoonReisdocument> reisdocumentenSet = new HashSet<>();
        final PersoonReisdocument vermistReisDocument = mock(PersoonReisdocument.class);
        when(vermistReisDocument.getAanduidingInhoudingOfVermissingReisdocument()).thenReturn(new AanduidingInhoudingOfVermissingReisdocument('A', "vermist"));
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(vermistReisDocument));
        assertFalse(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentNationaliteit() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));
        assertFalse(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, 20160101);
        voegNationaliteitToe("0002", 20160102, null);
        persoon = new BijhoudingPersoon(persoonEntity);
        assertFalse(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
        assertTrue(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20160101));

    }

    @Test
    public void testVervallenDocumentWijzigenPersonaliaNietGewijzigd() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));
        assertFalse(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        assertFalse(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenPersonaliaGeboorteGemeenteGewijzigd() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));
        registreerGeboorteBijBijhoudingspersoon(persoon, 20100101, "123456", 20100101, "1255");
        assertFalse(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }


    @Test
    public void testVervallenDocumentWijzigenPersonaliaVoornamenNietGewijzigd() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));
        voegVoornaamAanBerichtToe(1, "Willem");
        voegVoornaamAanEntiteitToe(1, "Willem", 20010101, null);
        registreerVoornamenBijBijhoudingspersoon(persoon);
        assertFalse(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenPersonaliaVoornamenGewijzigdNieuweErbij() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));
        voegVoornaamAanBerichtToe(1, "Willem");
        voegVoornaamAanEntiteitToe(1, "Willem", 20010101, null);
        voegVoornaamAanBerichtToe(2, "Willem");
        registreerVoornamenBijBijhoudingspersoon(persoon);
        assertTrue(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenPersonaliaVoornamenGewijzigdInhoud() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));
        voegVoornaamAanBerichtToe(1, "Willem");
        voegVoornaamAanEntiteitToe(1, "Willem", 20010101, null);
        voegVoornaamAanBerichtToe(2, "Willem");
        voegVoornaamAanEntiteitToe(2, "Piet", 20010101, null);
        registreerVoornamenBijBijhoudingspersoon(persoon);
        assertTrue(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenPersonaliaVoornamenGewijzigdDbVervallen() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));
        voegVoornaamAanBerichtToe(1, "Willem");
        voegVoornaamAanEntiteitToe(1, "Willem", 20010101, null);
        voegVoornaamAanBerichtToe(2, "Willem");
        voegVoornaamAanEntiteitToe(2, "Willem", 20010101, 20160101);
        registreerVoornamenBijBijhoudingspersoon(persoon);
        assertTrue(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenPersonaliaGeslachtsnaamStamRegistratieNieuw() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));
        registreerGeslachtsnaamComponentBijBijhoudingspersoon(persoon, "de", "Wit", null, null, 20010101, null);
        assertTrue(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenPersonaliaGeslachtsnaamStamDBBeeindigd() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));
        registreerGeslachtsnaamComponentBijBijhoudingspersoon(persoon, "de", "Wit", "de", "wit", 20010101, 20161231);
        assertTrue(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenPersonaliaGeslachtsnaamStamActueelMaarZelfde() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));
        registreerGeslachtsnaamComponentBijBijhoudingspersoon(persoon, "de", "Wit", "de", "Wit", 20010101, null);
        assertFalse(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenPersonaliaGeslachtsnaamStamAlleenVoorvoegselAangepast() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));
        registreerGeslachtsnaamComponentBijBijhoudingspersoon(persoon, "de", "Wit", "het", "Wit", 20010101, null);
        assertFalse(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenPersonaliaGeslachtsnaamStamHoofdletterGevoelig() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));
        registreerGeslachtsnaamComponentBijBijhoudingspersoon(persoon, "de", "Wit", "de", "wit", 20010101, null);
        assertTrue(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenPersonaliaIdentificatieNummersNietVeranderd() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));

        registreerIdentificatieNummersBijBijhoudingspersoon(persoon, "123456789", "9876543210", "123456789", "9876543210", 20010101, null);
        assertFalse(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenPersonaliaIdentificatieNummersDBNietActueel() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));

        registreerIdentificatieNummersBijBijhoudingspersoon(persoon, "123456789", "9876543210", "123456789", "9876543210", 20010101, 20161231);
        assertTrue(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenPersonaliaIdentificatieNummersAnrVeranderd() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));

        registreerIdentificatieNummersBijBijhoudingspersoon(persoon, "123456789", "9876543210", "123456789", "9999993210", 20010101, null);
        assertFalse(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenPersonaliaIdentificatieNummersBSNGewijzigd() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));

        registreerIdentificatieNummersBijBijhoudingspersoon(persoon, "123456789", "9876543210", "123456790", "9876543210", 20010101, null);
        assertTrue(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenPersonaliaIdentificatieNummersNietInDB() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));

        registreerIdentificatieNummersBijBijhoudingspersoon(persoon, "123456789", "9876543210", null, null, null, null);
        assertTrue(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenGeslachtsAanduidingNietAangepast() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));

        registreerAanduidingGeslachtBijBijhoudingspersoon(persoon, "M","M",  20010101, null);
        assertFalse(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenGeslachtsAanduidingInDBVerlopen() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));

        registreerAanduidingGeslachtBijBijhoudingspersoon(persoon, "M","M",  20010101, 20161231);
        assertTrue(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }

    @Test
    public void testVervallenDocumentWijzigenGeslachtsAanduidingBestaandeAangepast() {
        BijhoudingPersoon persoon = new BijhoudingPersoon(persoonEntity);
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final PersoonReisdocument reisdocument = mock(PersoonReisdocument.class);
        when(reisdocument.isActueelEnGeldig()).thenReturn(true);
        when(persoonEntity.getPersoonReisdocumentSet()).thenReturn(Collections.singleton(reisdocument));

        registreerAanduidingGeslachtBijBijhoudingspersoon(persoon, "V","M",  20010101, null);
        assertTrue(persoon.isErEenAanpassingWaardoorReisdocumentVervalt(20170101));
    }


    private void registreerAanduidingGeslachtBijBijhoudingspersoon(final BijhoudingPersoon bijhoudingPersoon, final String geslachtsAanduidingCode,
                                                                   final String geslachtsAanduidingCodeDB, final Integer dag, final Integer deg) {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.geslachtsaanduiding(builder.maakGeslachtsaanduidingElement("gAand", geslachtsAanduidingCode));
        final PersoonElement persoonElement = builder.maakPersoonGegevensElement("geslaan_pers", null, null, persoonParameters);
        if (dag != null) {
            final Geslachtsaanduiding aanduiding = Geslachtsaanduiding.parseCode(geslachtsAanduidingCodeDB);
            final PersoonGeslachtsaanduidingHistorie historie = new PersoonGeslachtsaanduidingHistorie(persoonEntity, aanduiding);
            historie.setDatumTijdRegistratie(DATUM_TIJD_REGISTRATIE);
            historie.setDatumAanvangGeldigheid(dag);
            historie.setDatumEindeGeldigheid(deg);
            when(persoonEntity.getPersoonGeslachtsaanduidingHistorieSet()).thenReturn(Collections.singleton(historie));
        }
        bijhoudingPersoon.registreerPersoonElement(persoonElement);
    }


    private void registreerIdentificatieNummersBijBijhoudingspersoon(final BijhoudingPersoon bijhoudingPersoon, final String bsn, final String anr,
                                                                     final String bsnDB, final String anrDB, final Integer dag,
                                                                     final Integer deg) {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        persoonParameters.identificatienummers(builder.maakIdentificatienummersElement("ident", bsn, anr));
        final PersoonElement identificatieNummers = builder.maakPersoonGegevensElement("gesl_pers", null, null, persoonParameters);
        if (dag != null) {
            final PersoonIDHistorie idHistorie = new PersoonIDHistorie(persoonEntity);
            idHistorie.setAdministratienummer(anrDB);
            idHistorie.setBurgerservicenummer(bsnDB);
            idHistorie.setDatumTijdRegistratie(DATUM_TIJD_REGISTRATIE);
            idHistorie.setDatumAanvangGeldigheid(dag);
            idHistorie.setDatumEindeGeldigheid(deg);
            when(persoonEntity.getPersoonIDHistorieSet()).thenReturn(Collections.singleton(idHistorie));
        }
        bijhoudingPersoon.registreerPersoonElement(identificatieNummers);
    }


    private void registreerGeslachtsnaamComponentBijBijhoudingspersoon(final BijhoudingPersoon bijhoudingPersoon, final String voorvoegsel, final String stam,
                                                                       final String voorvoegselDB, final String stamDB, final int dag, final Integer deg) {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        final GeslachtsnaamcomponentElement geslachtsnaamComponent = builder.maakGeslachtsnaamcomponentElement("gesl", null, null, voorvoegsel, ' ', stam);
        persoonParameters.geslachtsnaamcomponenten(Collections.singletonList(geslachtsnaamComponent));
        final PersoonElement voornaam = builder.maakPersoonGegevensElement("gesl_pers", null, null, persoonParameters);
        if (stamDB != null) {
            PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent(persoonEntity, 1);
            final PersoonGeslachtsnaamcomponentHistorie
                    geslachtsnaamComponentHistorie =
                    new PersoonGeslachtsnaamcomponentHistorie(persoonGeslachtsnaamcomponent, stamDB);
            geslachtsnaamComponentHistorie.setDatumAanvangGeldigheid(dag);
            if (deg != null) {
                geslachtsnaamComponentHistorie.setDatumEindeGeldigheid(deg);
            }
            geslachtsnaamComponentHistorie.setDatumTijdRegistratie(DATUM_TIJD_REGISTRATIE);
            geslachtsnaamComponentHistorie.setVoorvoegsel(voorvoegselDB);
            persoonGeslachtsnaamcomponent.addPersoonGeslachtsnaamcomponentHistorie(geslachtsnaamComponentHistorie);
            when(persoonEntity.getPersoonGeslachtsnaamcomponentSet()).thenReturn(Collections.singleton(persoonGeslachtsnaamcomponent));
        }
        bijhoudingPersoon.registreerPersoonElement(voornaam);
    }


    private void registreerVoornamenBijBijhoudingspersoon(final BijhoudingPersoon bijhoudingPersoon) {
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        final ElementBuilder.GeboorteParameters geboorteParameters = new ElementBuilder.GeboorteParameters();
        final List<VoornaamElement> voornamen = new LinkedList<>();
        for (Map.Entry<Integer, String> entry : voornamenBericht.entrySet()) {
            voornamen.add(builder.maakVoornaamElement("vn" + entry.getKey(), entry.getKey(), entry.getValue()));
        }
        persoonParameters.voornamen(voornamen);
        final PersoonElement voornaam = builder.maakPersoonGegevensElement("kindvn", null, null, persoonParameters);
        bijhoudingPersoon.registreerPersoonElement(voornaam);

        when(persoonEntity.getPersoonVoornaamSet()).thenReturn(voornamenDatabase);


    }

    private void voegVoornaamAanEntiteitToe(final int volgnummer, final String naam, final Integer dag, final Integer deg) {
        final PersoonVoornaam pVoornaam = new PersoonVoornaam(persoon, volgnummer);
        final PersoonVoornaamHistorie pVoornaamHistorie = new PersoonVoornaamHistorie(pVoornaam, naam);
        pVoornaamHistorie.setDatumAanvangGeldigheid(dag);
        if (deg != null) {
            pVoornaamHistorie.setDatumEindeGeldigheid(deg);
        }
        pVoornaam.addPersoonVoornaamHistorie(pVoornaamHistorie);
        voornamenDatabase.add(pVoornaam);
    }

    private void voegVoornaamAanBerichtToe(final int volgnummer, final String naam) {
        voornamenBericht.put(volgnummer, naam);
    }

    public void registreerGeboorteBijBijhoudingspersoon(final BijhoudingPersoon bijhoudingpersoon, final int geboortedatum, final String gemeenteCode,
                                                        final Integer geboortedatumDB, final String gemeenteCodeDB) {
        voegNationaliteitToe(Nationaliteit.NEDERLANDSE, 20010101, null);
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        final ElementBuilder.GeboorteParameters geboorteParameters = new ElementBuilder.GeboorteParameters();
        geboorteParameters.gemeenteCode(gemeenteCode);
        geboorteParameters.datum(geboortedatum);
        persoonParameters.geboorte(builder.maakGeboorteElement("geb", geboorteParameters));
        final PersoonElement geboorte = builder.maakPersoonGegevensElement("kind", null, null, persoonParameters);
        bijhoudingpersoon.registreerPersoonElement(geboorte);
        final PersoonGeboorteHistorie geboorteHistorie = new PersoonGeboorteHistorie(persoonEntity, geboortedatumDB, new LandOfGebied("0031", "NL"));
        geboorteHistorie.setGemeente(new Gemeente((short) 1, "gem", gemeenteCodeDB, Z_PARTIJ));
        geboorteHistorie.setDatumTijdRegistratie(DATUM_TIJD_REGISTRATIE);
        when(persoonEntity.getPersoonGeboorteHistorieSet()).thenReturn(Collections.singleton(geboorteHistorie));
    }


    public void voegNationaliteitToe(final String nationaliteitCode, final int dag, final Integer deg) {
        PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoonEntity, new Nationaliteit(nationaliteitCode, nationaliteitCode));
        final PersoonNationaliteitHistorie persoonNationaliteitHistorie = new PersoonNationaliteitHistorie(nationaliteit);
        persoonNationaliteitHistorie.setDatumTijdRegistratie(DATUM_TIJD_REGISTRATIE);
        persoonNationaliteitHistorie.setDatumAanvangGeldigheid(dag);
        if (deg != null) {
            persoonNationaliteitHistorie.setDatumEindeGeldigheid(deg);
        }
        nationaliteit.addPersoonNationaliteitHistorie(persoonNationaliteitHistorie);
        nationaliteiten.add(nationaliteit);
    }


}
