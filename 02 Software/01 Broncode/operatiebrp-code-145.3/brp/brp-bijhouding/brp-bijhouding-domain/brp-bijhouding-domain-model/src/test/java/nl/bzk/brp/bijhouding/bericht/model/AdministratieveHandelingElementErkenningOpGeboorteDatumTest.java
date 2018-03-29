/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel.R1691;
import static nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel.R1729;
import static nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel.R1736;
import static nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel.R2652;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import org.junit.Test;

public class AdministratieveHandelingElementErkenningOpGeboorteDatumTest extends AbstractAdministratieveHandelingElementTest {

    @Test
    public void testOk() throws OngeldigeObjectSleutelException {
        maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 2, true, "Stam", null, null, null, null, 19900101, null, null, null);
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeboreneMetErkenningOpGeboorteDatum(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null,
                        null,
                        null, null, false);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0, meldingen.size());
    }



    @Test
    @Bedrijfsregel(R2652)
    public void testRegistratieStaatloosEnNationaliteit() throws OngeldigeObjectSleutelException {
        maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 2, true, "Stam", null, null, null, null, 19900101, null, null, null);
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");
        final AdministratieveHandelingElement                ah =
                createAdministratieveHandelingRegistratieGeboreneMetErkenningOpGeboorteDatum(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null,
                        null, null, null, true);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R2652, meldingen.get(0).getRegel());
    }

    /**
     * door de ouders wordt 1691 getriggerd, maar geen R2652, daar gaat het om.
     */
    @Test
    @Bedrijfsregel(R2652)
    public void testRegistratieStaatloos() throws OngeldigeObjectSleutelException {
        maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 2, true, "Stam", null, null, null, null, 19900101, null, null, null);
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");
        final AdministratieveHandelingElement                ah =
                createAdministratieveHandelingRegistratieGeboreneMetErkenningOpGeboorteDatum(20100101, sPara, null, "Karel appel", "Stam", "1", "2", null,
                        null, null, null, true);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R1691, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(R1736)
    @Test
    public void testR1736_16_jaar_en_1_dag() throws OngeldigeObjectSleutelException {
        maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 2, true, "Stam", null, null, null, null, 19900102, null, null, null);
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");
        final AdministratieveHandelingElement                ah =
                createAdministratieveHandelingRegistratieGeboreneMetErkenningOpGeboorteDatum(20060103, sPara, "0001", "Karel appel", "Stam", "1", "2", null,
                        null,
                        null, null, false);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(R1736)
    @Test
    public void testR1736_16_jaar() throws OngeldigeObjectSleutelException {
        maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 2, true, "Stam", null, null, null, null, 19900102, null, null, null);
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");
        final AdministratieveHandelingElement                ah =
                createAdministratieveHandelingRegistratieGeboreneMetErkenningOpGeboorteDatum(20060102, sPara, "0001", "Karel appel", "Stam", "1", "2", null,
                        null,
                        null, null, false);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(R1736)
    @Test
    public void testR1736_15_jaar_laatste_dag() throws OngeldigeObjectSleutelException {
        maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 2, true, "Stam", null, null, null, null, 19900102, null, null, null);
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeboreneMetErkenningOpGeboorteDatum(20060101, sPara, "0001", "Karel appel", "Stam", "1", "2", null,
                        null,
                        null, null, false);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R1736, meldingen.get(0).getRegel());
    }

    @Test
    @Bedrijfsregel(R1729)
    public void testR1729MetCuratele() throws OngeldigeObjectSleutelException {
        final BijhoudingPersoon
                persoon =
                maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 2, true, "Stam", null, null, null, null, 19900101, null, null, null);
        final PersoonIndicatie persoonIndicatie = new PersoonIndicatie(persoon, SoortIndicatie.ONDER_CURATELE);
        final PersoonIndicatieHistorie persoonIndicatieHistorie = new PersoonIndicatieHistorie(persoonIndicatie, true);
        persoonIndicatie.getPersoonIndicatieHistorieSet().add(persoonIndicatieHistorie);
        persoon.getPersoonIndicatieSet().add(persoonIndicatie);
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");
        final AdministratieveHandelingElement ah =
                createAdministratieveHandelingRegistratieGeboreneMetErkenningOpGeboorteDatum(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null,
                        null,

                        null, null, false);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(R1729, meldingen.get(0).getRegel());
    }

    public AdministratieveHandelingElement createAdministratieveHandelingRegistratieGeboreneMetErkenningOpGeboorteDatum(final Integer geboorteDatum,
                                                                                                                        final ElementBuilder.NaamParameters
                                                                                                                                kindNaamParameters,
                                                                                                                        final String nationaliteitCodeKind,
                                                                                                                        final String voornaam,
                                                                                                                        final String kindStamNaam,
                                                                                                                        final String objectsleutelOuder1,
                                                                                                                        final String objectsleutelOuder2,
                                                                                                                        final String adelijkeTitel,
                                                                                                                        final String predicaat,
                                                                                                                        final String bsn,
                                                                                                                        final String anummer,
                                                                                                                        final boolean voeActieStaatloosToe) {
        final ElementBuilder.AdministratieveHandelingParameters ahPara = new ElementBuilder.AdministratieveHandelingParameters();
        ahPara.soort(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND_MET_ERKENNING_OP_GEBOORTEDATUM);
        final List<ActieElement> acties = new LinkedList<>();
        //actie geborene
        final ElementBuilder.RelatieGroepParameters rPara = new ElementBuilder.RelatieGroepParameters();
        rPara.datumAanvang(geboorteDatum);
        final RelatieGroepElement relatie = builder.maakRelatieGroepElement("rel", rPara);
        final List<BetrokkenheidElement> betrokkenen = new LinkedList<>();
        //kind
        final ElementBuilder.PersoonParameters pParams = new ElementBuilder.PersoonParameters();
        final ElementBuilder.GeboorteParameters gPara = new ElementBuilder.GeboorteParameters();
        //geboorte
        gPara.gemeenteCode("1").woonplaatsnaam("Amsterdam").datum(geboorteDatum);
        pParams.samengesteldeNaam(builder.maakSamengesteldeNaamElement("baby_sn", kindNaamParameters));
        if (voornaam != null) {
            final List<VoornaamElement> voornamen = new LinkedList<>();
            voornamen.add(new ElementBuilder().maakVoornaamElement("baby_vn", 1, voornaam));
            pParams.voornamen(voornamen);
        }
        final List<GeslachtsnaamcomponentElement> gnComps = new LinkedList<>();
        gnComps.add(builder.maakGeslachtsnaamcomponentElement("gc", predicaat, adelijkeTitel, null, null, kindStamNaam));
        pParams.geslachtsnaamcomponenten(gnComps);
        pParams.geboorte(builder.maakGeboorteElement("baby_geb", gPara));
        //identificatieNummers
        if (bsn != null || anummer != null) {
            pParams.identificatienummers(builder.maakIdentificatienummersElement("com_id", bsn, anummer));
        }
        final PersoonGegevensElement baby = builder.maakPersoonGegevensElement("baby_persoon", null, null, pParams);
        baby.getPersoonEntiteit().registreerPersoonElement(baby);
        baby.setVerzoekBericht(bericht);
        betrokkenen.add(builder.maakBetrokkenheidElement("baby", BetrokkenheidElementSoort.KIND, baby, null));

        //ouder
        if (objectsleutelOuder1 != null) {
            final PersoonGegevensElement ouder1 = builder.maakPersoonGegevensElement("ouder1_persoon", objectsleutelOuder1);
            ouder1.setVerzoekBericht(bericht);
            final OuderschapElement ouderschap = builder.maakOuderschapElement("os_1", true);
            betrokkenen.add(builder.maakBetrokkenheidElement("ouder1", BetrokkenheidElementSoort.OUDER, ouder1, ouderschap));
        }

        //ouder
        if (objectsleutelOuder2 != null) {
            final PersoonGegevensElement ouder2 = builder.maakPersoonGegevensElement("ouder2_persoon", objectsleutelOuder2);
            ouder2.setVerzoekBericht(bericht);
            final OuderschapElement ouderschap = builder.maakOuderschapElement("os_2", false);
            betrokkenen.add(builder.maakBetrokkenheidElement("ouder2", BetrokkenheidElementSoort.OUDER, ouder2, ouderschap));
        }

        final FamilierechtelijkeBetrekkingElement frbElement = builder.maakFamilierechtelijkeBetrekkingElement("fbr", relatie, betrokkenen);

        final HashMap<String, String> attributen = new HashMap<>();
        attributen.put(BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT.toString(), "objectTypeBronElement");
        final BronElement
                bronElement =
                builder.maakBronElement("ci_test_2",
                        new DocumentElement(attributen, new StringElement("33"), new StringElement("3"), null, new StringElement("")));
        HashMap<String, BmrGroep> bronParams = new HashMap<>();
        bronParams.put("ci_test_2", bronElement);
        BronReferentieElement bronReferentieElement = builder.maakBronReferentieElement("com_bron", "ci_test_2");
        bronReferentieElement.initialiseer(bronParams);

        acties.add(builder.maakRegistratieGeboreneActieElement("geb_actie", geboorteDatum, frbElement,
                Collections.singletonList(bronReferentieElement)));

        //actie nationaliteit
        if (nationaliteitCodeKind != null) {
            maakActieRegistratieNationaliteit(geboorteDatum, nationaliteitCodeKind, acties, baby);
        }
        //actie staatloos
        if (voeActieStaatloosToe) {
            maakActieRegistratieStaatLoos(geboorteDatum, acties, baby);
        }

        ahPara.acties(acties);
        ahPara.partijCode(Z_PARTIJ.getCode());
        final AdministratieveHandelingElement ah = builder.maakAdministratieveHandelingElement("ah_id", ahPara);
        ah.setVerzoekBericht(bericht);
        return ah;
    }

    public void maakActieRegistratieStaatLoos(final Integer geboorteDatum, final List<ActieElement> acties, final PersoonGegevensElement baby) {
        final ElementBuilder.IndicatieElementParameters indiElePara = new ElementBuilder.IndicatieElementParameters();
        indiElePara.heeftIndicatie(true);
        final ElementBuilder.PersoonParameters staatloosPers = new ElementBuilder.PersoonParameters();
        final List<IndicatieElement> indicaties = new LinkedList<>();
        indicaties.add(builder.maakStaatloosIndicatieElement("ac_stl", indiElePara));
        staatloosPers.indicaties(indicaties);
        final PersoonGegevensElement kindStaatloos = builder.maakPersoonGegevensElement("pers_staatloos", null, "baby_persoon", staatloosPers);
        final Map<String, BmrGroep> commMap = new LinkedHashMap<>();
        commMap.put("baby_persoon", baby);
        kindStaatloos.initialiseer(commMap);
        baby.getPersoonEntiteit().registreerPersoonElement(kindStaatloos);
        acties.add(builder.maakRegistratieStaatloosActieElement("actie_stl", geboorteDatum, Collections.emptyList(), kindStaatloos));
    }

    public void maakActieRegistratieNationaliteit(final Integer geboorteDatum, final String nationaliteitCodeKind, final List<ActieElement> acties,
                                                  final PersoonGegevensElement baby) {
        final ElementBuilder.PersoonParameters nPara = new ElementBuilder.PersoonParameters();
        final List<NationaliteitElement> nats = new LinkedList<>();
        nats.add(builder.maakNationaliteitElement("nate", nationaliteitCodeKind, "18"));
        nPara.nationaliteiten(nats);
        final PersoonGegevensElement kind_nat = builder.maakPersoonGegevensElement("kind_nat", null, "baby_persoon", nPara);

        final Map<String, BmrGroep> commMap = new LinkedHashMap<>();
        commMap.put("baby_persoon", baby);
        kind_nat.initialiseer(commMap);
        baby.getPersoonEntiteit().registreerPersoonElement(kind_nat);
        final RegistratieNationaliteitActieElement
                nat_actie =
                builder.maakRegistratieNationaliteitActieElement("nat_actie", geboorteDatum, Collections.emptyList(), kind_nat);
        nat_actie.setVerzoekBericht(bericht);
        acties.add(nat_actie);
    }
}
