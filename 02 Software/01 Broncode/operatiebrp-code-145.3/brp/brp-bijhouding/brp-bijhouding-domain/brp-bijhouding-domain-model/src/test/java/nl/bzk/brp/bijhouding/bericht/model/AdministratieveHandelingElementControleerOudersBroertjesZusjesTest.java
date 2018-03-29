/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel.R1732;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import org.junit.Before;
import org.junit.Test;


public class AdministratieveHandelingElementControleerOudersBroertjesZusjesTest extends AbstractAdministratieveHandelingElementTest {

    private ElementBuilder builder;
    private BijhoudingPersoon mama, mamaMetTitel, mamaDuits, papaDuits_metTitel;
    private BijhoudingPersoon papa, papa2, papaDuits, papa_Adelijke_titel, papa_predicaat,papaOverleden;
    private BijhoudingPersoon broertje, broertje2, broertje3,broertje3b, broertje4, broertje5, broertje6, broertjeDuits,broertje_bsn_anr1,broertje_bsn_anr2;

    @Before
    public void setup() throws OngeldigeObjectSleutelException {
        builder = new ElementBuilder();
        //mama
        mama = maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 1, true, "Stam", null, null, null, null, null, null, null, null);
        mamaMetTitel = maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 10, true, "Stam", null, null, null, null, null, null, null, null);
        mamaDuits = maakPersoon(SoortPersoon.INGESCHREVENE, DUITS, 11, true, "Stam", null, null, null, null, null, null, null, null);
        //papa
        papa = maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 2, true, "Stam", null, null, null, null, null, null, null, null);
        //papa
        papaOverleden = maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 25, true, "Stam", null, null, null, null, null, 20091201, null, null);
        //papa2
        papa2 = maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 20, true, "Stam2", null, null, Geslachtsaanduiding.MAN, null, null, null, null, null);
        //papaDuits
        papaDuits = maakPersoon(SoortPersoon.INGESCHREVENE, DUITS, 24, true, "Stam2", null, null, Geslachtsaanduiding.MAN, null, null, null, null, null);
        //papaDuits
        papaDuits_metTitel = maakPersoon(SoortPersoon.INGESCHREVENE, DUITS, 23, true, "Stam2", null, AdellijkeTitel.B, Geslachtsaanduiding.MAN, null, null,
                null, null, null);
        //anderKind
        broertje2 = maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 3, true, "Stam2", null, null, null, null, null, null, "0012345673", "012345673");
        //anderKind
        broertje = maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 4, true, "Stam", null, null, null, null, null, null, "0012345674", "012345674");
        //anderKind
        broertje3 = maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 5, true, "Stam", null, null, null, "Karel Appel", 20100101, null, "0012345675", "012345675");
        broertje3b = maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 5, true, "Stam", null, null, null, "Karel Appel", 20100102, null, "0012345675", "012345675");
        //anderKind
        broertje4 = maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 6, true, "Stam", null, null, null, "Karel Appel", 20100102, null, "0012345676", "012345676");
        //anderKind
        broertje5 = maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 7, true, "Stam", null, null, null, "Karel", 20100101, null, "0012345677", "012345677");
        //anderKind
        broertje6 = maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 9, true, "Stam", null, null, null, null, 20100101,null, "0012345679", "012345679");
        //anderKind
        broertje_bsn_anr1 = maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 100, true, "Stam", null, null, null, "Peter", 20100101,null, "0012345670", "022345670");
        broertje_bsn_anr2 = maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 101, true, "Stam", null, null, null, "Piet", 20080101,null, "0012345671", "022345671");

        //anderKind
        broertjeDuits = maakPersoon(SoortPersoon.INGESCHREVENE, DUITS, 8, true, "Stam", null, null, null, "Karel", 20100101,null, "0012345678", "012345678");
        //baron
        papa_Adelijke_titel =
                maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 21, true, "Stam", null, AdellijkeTitel.H, Geslachtsaanduiding.MAN, null, null,
                null, null, null);
        //predicaat
        papa_predicaat = maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 22, true, "Stam", Predicaat.J, null, Geslachtsaanduiding.MAN, null, null, null,
                null, null);

        setupBericht();
        when(getDynamischeStamtabelRepository().getPartijByCode("000001")).thenReturn(Z_PARTIJ);
    }

    @Test
    public void testGeboorteZelfdeOudersMetBroertjeMetZelfdeNaam() {
        voegKindToeAanOuder(broertje, papa, mama);
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null, null,
                        null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testGeboorteVerwantschapOuders() {
        voegKindToeAanOuder(broertje, papa, mama);
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "4", null, null,
                        null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(Regel.R1733, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2523)
    @Test
    public void testGeboorteAnderKindZelfdeAnr() {
        voegKindToeAanOuder(broertje_bsn_anr1, papa, mama);
        voegKindToeAanOuder(broertje_bsn_anr2, papa, mama);
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null, null,
                        null, null, "55", "0012345670", null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2523, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2523)
    @Test
    public void testGeboorteAnderKindZelfdeBsn() {
        voegKindToeAanOuder(broertje_bsn_anr1, papa, mama);
        voegKindToeAanOuder(broertje_bsn_anr2, papa, mama);
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null, null,
                        null, null, "022345670", "56", null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2523, meldingen.get(0).getRegel());
    }


    @Bedrijfsregel(R1732)
    @Test
    public void testGeboorteBroertjeZelfdeNaamZelfdeVoornaamEnGeboorteDatum() {
        voegKindToeAanOuder(broertje3, papa, mama);

        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null, null,
                        null, null, null, "151515", null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R1732, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(R1732)
    @Test
    public void testGeboorteMetDuitseNationaliteitBroertjeZelfdeNaamZelfdeVoornaamEnGeboorteDatum() {
        voegKindToeAanOuder(broertje3, papaDuits, mamaDuits);

        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0036", "Karel appel", "Stam", "11", "24", null, null,
                        null, null, "15151515", null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R1732, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(R1732)
    @Test
    public void testGeboorteBroertjeZelfdeNaamZelfdeVoornaamEnAndereGeboorteDatum() {
        voegKindToeAanOuder(broertje3b, papa, mama);

        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null, null,
                        null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(R1732)
    @Test
    public void testGeboorteBroertjeZelfdeVoornaamEnAndereGeboorteDatum() {
        voegKindToeAanOuder(broertje4, papa, mama);

        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null, null,
                        null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(R1732)
    @Test
    public void testGeboorteBroertjeZonderVoornaam() {
        voegKindToeAanOuder(broertje6, papaDuits, mamaDuits);

        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0036", null, "Stam", "11", "24", null, null,
                        null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R1732, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(R1732)
    @Test
    public void testGeboorteBroertjeAndereVoornaamEnZelfdeGeboorteDatum() {
        voegKindToeAanOuder(broertje5, papa, mama);

        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null, null,
                        null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R1731)
    @Test
    public void testGeboortePapaPseudoMetTitel() {
        voegKindToeAanOuder(broertje, papa, mama);

        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final ElementBuilder.PersoonParameters papaParameters = new ElementBuilder.PersoonParameters();
        final ElementBuilder.NaamParameters naamParameters = new ElementBuilder.NaamParameters();
        naamParameters.adellijkeTitelCode("B");
        naamParameters.indicatieNamenreeks(false);
        naamParameters.geslachtsnaamstam("Stam");
        final SamengesteldeNaamElement samengestel = builder.maakSamengesteldeNaamElement("samen", naamParameters);
        papaParameters.samengesteldeNaam(samengestel);
        final GeslachtsaanduidingElement geslachtsaanduidingElement = builder.maakGeslachtsaanduidingElement("geslacht", "M");
        papaParameters.geslachtsaanduiding(geslachtsaanduidingElement);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "10", null, null, null,
                        null, papaParameters, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1731, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1731)
    @Test
    public void testGeboorteMamaPseudoMetTitel() {
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final ElementBuilder.PersoonParameters papaParameters = new ElementBuilder.PersoonParameters();
        final ElementBuilder.NaamParameters naamParameters = new ElementBuilder.NaamParameters();
        naamParameters.adellijkeTitelCode("B");
        naamParameters.indicatieNamenreeks(false);
        naamParameters.geslachtsnaamstam("Stam");
        final SamengesteldeNaamElement samengestel = builder.maakSamengesteldeNaamElement("samen", naamParameters);
        papaParameters.samengesteldeNaam(samengestel);
        final GeslachtsaanduidingElement geslachtsaanduidingElement = builder.maakGeslachtsaanduidingElement("geslacht", "V");
        papaParameters.geslachtsaanduiding(geslachtsaanduidingElement);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "10", null, null, null,
                        null, papaParameters, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R1731)
    @Test
    public void testGeboortePseudoMetTitelZonderGeslachtsAanduiding() {
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final ElementBuilder.PersoonParameters papaParameters = new ElementBuilder.PersoonParameters();
        final ElementBuilder.NaamParameters naamParameters = new ElementBuilder.NaamParameters();
        naamParameters.adellijkeTitelCode("B");
        naamParameters.indicatieNamenreeks(false);
        naamParameters.geslachtsnaamstam("Stam");
        final SamengesteldeNaamElement samengestel = builder.maakSamengesteldeNaamElement("samen", naamParameters);
        papaParameters.samengesteldeNaam(samengestel);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "10", null, null, null,
                        null, papaParameters, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R1731)
    @Test
    public void testGeboortemamaMetTitel() {
        voegKindToeAanOuder(broertje, papa, mama);

        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "10", "2", null, null,
                        null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2543)
    @Test
    public void testGeboortekindMetPredicaatPapaZonder() {
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null, "J",
                        null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2543, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1731)
    @Test
    public void testGeboortekindMetTitel() {
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "21", "B", null,
                        null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R2543)
    @Test
    public void testGeboortekindMetTitelEnPapaZonder() {
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", "B", null,
                        null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2543, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1731)
    @Test
    public void testGeboortepapaMetAdelijkeTitel() {
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "21", null,
                        null, null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1731, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1731)
    @Test
    public void testGeboorteNationaliteitOngelijkNederlandPapaMetAdelijkeTitel() {
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0036", "Karel appel", "Stam2", "11", "23", null,
                        null, null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1731, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1731)
    @Test
    public void testGeboortepapaMetPredicaat() {
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "22", null,
                        null, null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1731, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1727)
    @Test
    public void testGeboortezelfdeOudersMetBroertjeMetAndereNaam() {
        voegKindToeAanOuder(broertje2, papa, mama);

        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null, null,
                        null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1727, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1727)
    @Test
    public void testGeboorteZelfdeMamaAnderePapaMetBroertjeMetAndereNaam() {
        voegKindToeAanOuder(broertje2, papa2, mama);

        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null, null,
                        null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0, meldingen.size());
    }


    public void voegKindToeAanOuder(final BijhoudingPersoon kind, final BijhoudingPersoon... ouders) {
        for (BijhoudingPersoon ouder : ouders) {
            final Relatie ouderKind = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
            final RelatieHistorie relatieHistorie = new RelatieHistorie(ouderKind);
            ouderKind.getRelatieHistorieSet().add(relatieHistorie);

            final Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, ouderKind);
            ouderBetrokkenheid.setPersoon(ouder);
            ouderBetrokkenheid.addBetrokkenheidHistorie(new BetrokkenheidHistorie(ouderBetrokkenheid));
            ouder.getBetrokkenheidSet().add(ouderBetrokkenheid);

            final Betrokkenheid kindBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, ouderKind);
            kindBetrokkenheid.setPersoon(kind);
            kindBetrokkenheid.addBetrokkenheidHistorie(new BetrokkenheidHistorie(kindBetrokkenheid));
            kind.getBetrokkenheidSet().add(kindBetrokkenheid);

            ouderKind.addBetrokkenheid(ouderBetrokkenheid);
            ouderKind.addBetrokkenheid(kindBetrokkenheid);
        }
    }
}
