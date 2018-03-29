/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import org.junit.Before;
import org.junit.Test;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractMaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;

public class TransformatieDw021Test extends AbstractTransformatieTest {

    private TransformatieDw021 transformer;

    @Before
    public void setup() {
        transformer = new TransformatieDw021();
    }

    @Test
    public void testAcceptZonderVerliesNLNationaliteitZonder901Variant() {
        assertTrue(transformer.accept(maakVoornaamActualisering()));
    }

    @Test
    public void testAcceptMetVerliesNLNationaliteitZonder901Variant() {
        assertTrue(transformer.accept(maakNationaliteitActualisering()));
    }

    @Test
    public void testAcceptIndicatieMetRedenBeeindigingNationaliteit() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonIndicatie persoonIndicatie = new PersoonIndicatie(persoon, SoortIndicatie.BEHANDELD_ALS_NEDERLANDER);
        final PersoonIndicatieHistorie indicatieHistorie = new PersoonIndicatieHistorie(persoonIndicatie, true);

        final List<Verschil> verschillen = new ArrayList<>();
        verschillen.add(
                new Verschil(
                        new EntiteitSleutel(PersoonIndicatieHistorie.class, PersoonIndicatieHistorie.DATUM_EINDE_GELDIGHEID),
                        null,
                        null,
                        indicatieHistorie,
                        indicatieHistorie));
        verschillen.add(
                new Verschil(
                        new EntiteitSleutel(PersoonIndicatieHistorie.class, PersoonIndicatieHistorie.ACTIE_AANPASSING_GELDIGHEID),
                        null,
                        null,
                        indicatieHistorie,
                        indicatieHistorie));
        verschillen.add(
                new Verschil(
                        new EntiteitSleutel(PersoonIndicatieHistorie.class, PersoonIndicatieHistorie.MIGRATIE_REDEN_BEEINDIGING_NATIONALITEIT),
                        null,
                        null,
                        VerschilType.ELEMENT_NIEUW,
                        indicatieHistorie,
                        indicatieHistorie));

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(indicatieHistorie);
        verschilGroep.addVerschillen(verschillen);
        assertTrue(transformer.accept(verschilGroep));
    }

    @Test
    public void testAcceptMet901Variant() {
        assertTrue(transformer.accept(maakBijhoudingActualisering()));
    }

    @Test
    public void testExecuteZonder901Variant() {
        testExecute(false);
    }

    @Test
    public void testExcecuteMet901Variant() {
        testExecute(true);
    }

    @Test
    public void testGetCode() {
        assertEquals(DeltaWijziging.DW_021, transformer.getDeltaWijziging());
    }

    private void testExecute(final boolean is901Variant) {
        final VerschilGroep actualisering;
        if (is901Variant) {
            actualisering = maakBijhoudingActualisering();
        } else {
            actualisering = maakVoornaamActualisering();
        }

        final BRPActie actieVervalTbvLeveringMuts = maakActieVerval("07", 0, 0);
        final VerschilGroep aangepasteActualisering = transformer.execute(actualisering, actieVervalTbvLeveringMuts, context);

        assertNotNull(aangepasteActualisering);
        final ArrayList<Verschil> nieuweVerschillen = new ArrayList<>(aangepasteActualisering.getVerschillen());
        nieuweVerschillen.removeAll(actualisering.getVerschillen());
        assertEquals(6, nieuweVerschillen.size());

        final Verschil actieVervalVerschil = zoekActieVervalVerschil(nieuweVerschillen);
        final Verschil tsVervalVerschil = zoekTsVervalVerschil(nieuweVerschillen);
        final Verschil indicatieRijTbvLeveringVerschil = zoekIndicatieTbvLeveringVerschil(nieuweVerschillen);
        final Verschil actieTbvLeveringMutatiesVerschil = zoekActieTbvLeveringMutatiesVerschil(nieuweVerschillen);
        final Verschil nieuweRijVerschil;
        if (is901Variant) {
            nieuweRijVerschil = zoekNieuweRijVerschil(nieuweVerschillen, "persoonBijhoudingHistorieSet");
        } else {
            nieuweRijVerschil = zoekNieuweRijVerschil(nieuweVerschillen, "persoonVoornaamHistorieSet");
        }
        final Verschil nieuweRijActieInhoudVerschil = zoekNieuweRijAanpassingVerschil(nieuweVerschillen, "actieInhoud");

        assertNotNull(actieVervalVerschil);
        assertNotNull(tsVervalVerschil);
        assertNotNull(indicatieRijTbvLeveringVerschil);
        assertNotNull(actieTbvLeveringMutatiesVerschil);
        assertNotNull(nieuweRijVerschil);
        assertNotNull(nieuweRijActieInhoudVerschil);
    }

    private VerschilGroep maakVoornaamActualisering() {
        final EntiteitSleutel datumEindeGeldigheidSleutel =
                maakPersoonVoornaamHistorieEntiteitSleutel("datumEindeGeldigheid", 19900101, maakTimestamp("1990-01-02 02"));
        final EntiteitSleutel actieAanpassingGeldigheidSleutel =
                maakPersoonVoornaamHistorieEntiteitSleutel("actieAanpassingGeldigheid", 19900101, maakTimestamp("1990-01-02 02"));

        final int nieuweDatumEindeGeldigheid = 20000101;
        final BRPActie nieuweActieAanpassingGeldigheid = maakActieVerval("01", 0, 0);

        final PersoonVoornaam persoonVoornaamOud = new PersoonVoornaam(persoonOud, 1);
        persoonVoornaamOud.setNaam("Jan");
        persoonOud.addPersoonVoornaam(persoonVoornaamOud);
        final PersoonVoornaam persoonVoornaamNieuw = new PersoonVoornaam(persoonNieuw, 1);
        persoonVoornaamNieuw.setNaam("Jan");
        persoonNieuw.addPersoonVoornaam(persoonVoornaamNieuw);

        final PersoonVoornaamHistorie oudeRij = new PersoonVoornaamHistorie(persoonVoornaamOud, "Jan");
        oudeRij.setActieInhoud(nieuweActieAanpassingGeldigheid);
        oudeRij.setDatumTijdRegistratie(nieuweActieAanpassingGeldigheid.getDatumTijdRegistratie());
        persoonVoornaamOud.addPersoonVoornaamHistorie(oudeRij);
        final PersoonVoornaamHistorie nieuweRij = new PersoonVoornaamHistorie(persoonVoornaamNieuw, "Jan");
        nieuweRij.setActieInhoud(nieuweActieAanpassingGeldigheid);
        nieuweRij.setDatumTijdRegistratie(nieuweActieAanpassingGeldigheid.getDatumTijdRegistratie());
        persoonVoornaamNieuw.addPersoonVoornaamHistorie(nieuweRij);

        final Verschil datumEindeGeldigheidVerschil =
                new Verschil(datumEindeGeldigheidSleutel, null, nieuweDatumEindeGeldigheid, VerschilType.ELEMENT_NIEUW, oudeRij, nieuweRij);
        final Verschil actieAanpassingGeldigheidVerschil =
                new Verschil(actieAanpassingGeldigheidSleutel, null, nieuweActieAanpassingGeldigheid, VerschilType.ELEMENT_NIEUW, oudeRij, nieuweRij);
        final List<Verschil> verschillen = new ArrayList<>();
        verschillen.add(datumEindeGeldigheidVerschil);
        verschillen.add(actieAanpassingGeldigheidVerschil);

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(oudeRij);
        verschilGroep.addVerschillen(verschillen);
        return verschilGroep;
    }

    private VerschilGroep maakNationaliteitActualisering() {
        final int nieuweDatumEindeGeldigheid = 20000101;
        final BRPActie nieuweActieAanpassingGeldigheid = maakActieVerval("01", 0, 0);

        final Nationaliteit nationaliteit = new Nationaliteit("Nederlands", "0001");
        final PersoonNationaliteit persoonNationaliteit = new PersoonNationaliteit(persoonOud, nationaliteit);
        final PersoonNationaliteitHistorie oudeRij = new PersoonNationaliteitHistorie(persoonNationaliteit);
        final PersoonNationaliteitHistorie nieuweRij = new PersoonNationaliteitHistorie(persoonNationaliteit);

        final EntiteitSleutel datumEindeGeldigheidSleutel =
                maakPersoonVoornaamHistorieEntiteitSleutel("datumEindeGeldigheid", 19900101, maakTimestamp("1990-01-02 02"));
        final EntiteitSleutel actieAanpassingGeldigheidSleutel =
                maakPersoonVoornaamHistorieEntiteitSleutel("actieAanpassingGeldigheid", 19900101,
                        maakTimestamp("1990-01-02 02"));

        final Verschil datumEindeGeldigheidVerschil =
                new Verschil(datumEindeGeldigheidSleutel, null, nieuweDatumEindeGeldigheid, VerschilType.ELEMENT_NIEUW, oudeRij, nieuweRij);
        final Verschil actieAanpassingGeldigheidVerschil =
                new Verschil(actieAanpassingGeldigheidSleutel, null, nieuweActieAanpassingGeldigheid, VerschilType.ELEMENT_NIEUW, oudeRij,
                        nieuweRij);
        final List<Verschil> verschillen = new ArrayList<>();
        verschillen.add(datumEindeGeldigheidVerschil);
        verschillen.add(actieAanpassingGeldigheidVerschil);
        final EntiteitSleutel redenVerliesNLNationaliteitSleutel =
                maakPersoonVoornaamHistorieEntiteitSleutel("redenVerliesNLNationaliteit", 19900101,
                        maakTimestamp("1990-01-02 02"));
        final Verschil
                redenVerliesNLNationaliteitVerschil =
                new Verschil(redenVerliesNLNationaliteitSleutel, null, new RedenVerliesNLNationaliteit("060", "R"),
                        VerschilType.ELEMENT_NIEUW, oudeRij, nieuweRij);
        verschillen.add(redenVerliesNLNationaliteitVerschil);

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(oudeRij);
        verschilGroep.addVerschillen(verschillen);
        return verschilGroep;
    }

    private VerschilGroep maakBijhoudingActualisering() {
        final AdministratieveHandeling administratieveHandeling = getAdministratieveHandeling();
        final Partij partij = administratieveHandeling.getPartij();
        final BRPActie actie = new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, partij, new Timestamp(System.currentTimeMillis()));
        actie.setLo3Voorkomen(
                new Lo3Voorkomen(
                        new Lo3Bericht("referentie", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(System.currentTimeMillis()), "testdata", true),
                        "07"));
        final PersoonBijhoudingHistorie bijhoudingHistorieOud =
                new PersoonBijhoudingHistorie(persoonOud, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        // set actie met lo3 voorkomen zodat herkomst juist wordt gedetecteerd voor 901 bepaling
        bijhoudingHistorieOud.setActieInhoud(actie);
        persoonOud.addPersoonBijhoudingHistorie(bijhoudingHistorieOud);
        final PersoonBijhoudingHistorie bijhoudingHistorieNieuw =
                new PersoonBijhoudingHistorie(persoonNieuw, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        // set actie met lo3 voorkomen zodat herkomst juist wordt gedetecteerd voor 901 bepaling
        bijhoudingHistorieNieuw.setActieInhoud(actie);
        persoonNieuw.addPersoonBijhoudingHistorie(bijhoudingHistorieNieuw);
        final Verschil tsRegVerschil = maakPersoonBijhoudingTsRegHistorieVerschil(bijhoudingHistorieOud, bijhoudingHistorieNieuw);
        final Verschil degVerschil = maakPersoonBijhoudingDegHistorieVerschil(bijhoudingHistorieOud, bijhoudingHistorieNieuw);
        final Verschil actieInhoudVerschil = maakPersoonBijhoudingAiHistorieVerschil(bijhoudingHistorieOud, bijhoudingHistorieNieuw, actie);
        final Verschil actieAanpassingGeldigheidVerschil = maakPersoonBijhoudingAagHistorieVerschil(bijhoudingHistorieOud, bijhoudingHistorieNieuw, actie);

        final VerschilGroep verschilGroep = VerschilGroep.maakVerschilGroepMetHistorie(bijhoudingHistorieOud);
        verschilGroep.addVerschillen(Arrays.asList(tsRegVerschil, degVerschil, actieInhoudVerschil, actieAanpassingGeldigheidVerschil));
        return verschilGroep;
    }

    private EntiteitSleutel maakPersoonVoornaamHistorieEntiteitSleutel(final String veld, final Integer datumAanvangGeldigheid, final Timestamp tsReg) {
        final EntiteitSleutel eigenaar = new EntiteitSleutel(Persoon.class, "persoonVoornaamSet");

        final EntiteitSleutel persoonVoornaam = new EntiteitSleutel(PersoonVoornaam.class, "persoonVoornaamHistorieSet", eigenaar);
        persoonVoornaam.addSleuteldeel("volgnr", 1);

        final EntiteitSleutel result = new EntiteitSleutel(PersoonVoornaamHistorie.class, veld, persoonVoornaam);
        result.addSleuteldeel("dataanvgel", datumAanvangGeldigheid);
        result.addSleuteldeel("tsreg", tsReg);
        return result;
    }

    private Verschil maakPersoonBijhoudingTsRegHistorieVerschil(
            final PersoonBijhoudingHistorie bijhoudingHistorieOud,
            final PersoonBijhoudingHistorie bijhoudingHistorieNieuw) {
        final EntiteitSleutel bijhoudingTsRegHistorieSleutel =
                maakPersoonBijhoudingHistorieEntiteitSleutel(AbstractMaterieleHistorie.DATUM_TIJD_REGISTRATIE);
        final Timestamp tsReg1 = new Timestamp(System.currentTimeMillis());
        final Timestamp tsReg2 = new Timestamp(tsReg1.getTime());
        return new Verschil(bijhoudingTsRegHistorieSleutel, tsReg1, tsReg2, bijhoudingHistorieOud, bijhoudingHistorieNieuw);
    }

    private Verschil maakPersoonBijhoudingDegHistorieVerschil(
            final PersoonBijhoudingHistorie bijhoudingHistorieOud,
            final PersoonBijhoudingHistorie bijhoudingHistorieNieuw) {
        final EntiteitSleutel bijhoudingTsRegHistorieSleutel =
                maakPersoonBijhoudingHistorieEntiteitSleutel(AbstractMaterieleHistorie.DATUM_EINDE_GELDIGHEID);
        final Integer deg = 20150101;
        return new Verschil(bijhoudingTsRegHistorieSleutel, null, deg, bijhoudingHistorieOud, bijhoudingHistorieNieuw);
    }

    private Verschil maakPersoonBijhoudingAiHistorieVerschil(
            final PersoonBijhoudingHistorie bijhoudingHistorieOud,
            final PersoonBijhoudingHistorie bijhoudingHistorieNieuw,
            final BRPActie actie) {
        final EntiteitSleutel bijhoudingTsRegHistorieSleutel = maakPersoonBijhoudingHistorieEntiteitSleutel(AbstractMaterieleHistorie.ACTIE_INHOUD);
        return new Verschil(bijhoudingTsRegHistorieSleutel, actie, actie, bijhoudingHistorieOud, bijhoudingHistorieNieuw);
    }

    private Verschil maakPersoonBijhoudingAagHistorieVerschil(
            final PersoonBijhoudingHistorie bijhoudingHistorieOud,
            final PersoonBijhoudingHistorie bijhoudingHistorieNieuw,
            final BRPActie actie) {
        final EntiteitSleutel bijhoudingTsRegHistorieSleutel =
                maakPersoonBijhoudingHistorieEntiteitSleutel(AbstractMaterieleHistorie.ACTIE_AANPASSING_GELDIGHEID);
        return new Verschil(bijhoudingTsRegHistorieSleutel, null, actie, bijhoudingHistorieOud, bijhoudingHistorieNieuw);
    }

    private EntiteitSleutel maakPersoonBijhoudingHistorieEntiteitSleutel(final String veld) {
        final EntiteitSleutel eigenaar = new EntiteitSleutel(Persoon.class, "persoonBijhoudingHistorieSet");
        return new EntiteitSleutel(PersoonBijhoudingHistorie.class, veld, eigenaar);
    }
}
