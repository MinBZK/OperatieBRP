/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutel;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelServiceImpl;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import org.junit.Before;
import org.junit.Test;

public class AbstractAdministratieveHandelingElementTest extends AbstractElementTest {

    static final Nationaliteit NEDERLANDS = new Nationaliteit("NL", "0001");
    static final Nationaliteit DUITS = new Nationaliteit("dld", "0036");
    private static final Nationaliteit BELG = new Nationaliteit("BE", "0052");
    ElementBuilder builder = new ElementBuilder();
    BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
    static final Partij Z_PARTIJ = new Partij("partij", "000001");

    @Before
    public void setup() throws OngeldigeObjectSleutelException {
        when(getDynamischeStamtabelRepository().getPartijByCode("000001")).thenReturn(Z_PARTIJ);
        setupPersonen();
        setupBericht();
    }

    public void setupBericht() {
        Z_PARTIJ.addPartijRol(new PartijRol(Z_PARTIJ, Rol.BIJHOUDINGSORGAAN_COLLEGE));
        when(bericht.getZendendePartij()).thenReturn(Z_PARTIJ);

        final ElementBuilder.StuurgegevensParameters
                stuurParams =
                new ElementBuilder.StuurgegevensParameters().zendendePartij(Z_PARTIJ.getCode()).zendendeSysteem("brp")
                        .referentienummer("88409eeb-1aa5-43fc-8614-43055123a165").tijdstipVerzending("2016-03-21T09:32:03.234+02:00");
        final StuurgegevensElement stuurgegevens = builder.maakStuurgegevensElement("stuur_comm", stuurParams);
        when(bericht.getStuurgegevens()).thenReturn(stuurgegevens);
    }

    @Test
    public void test() {
        assertTrue(true);
        //we need a test
    }

    public void setupPersonen() throws OngeldigeObjectSleutelException {
        //persoon 1 DUITS
        maakPersoon(SoortPersoon.INGESCHREVENE, DUITS, 1, false, "Stam", null, null, null, null, null, null, null, null);
        //persoon 2 Nederlands
        maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 2, true, "Stam", null, null, null, null, null, null, null, null);
        //persoon 3 DUITS
        maakPersoon(SoortPersoon.INGESCHREVENE, BELG, 3, false, "Stam", null, null, null, null, null, null, null, null);

    }

    public BijhoudingPersoon maakPersoon(final SoortPersoon soortPersoon, final Nationaliteit nationaliteit, final long objectSleutel,
                                         final boolean voegBijhoudingHistorieToe, final String stam,
                                         final Predicaat predicaat, final AdellijkeTitel adelijkeTitel,
                                         final Geslachtsaanduiding geslachtsAanduiding, final String voornamen, final Integer geboortedatum,
                                         final Integer datumOverleden, final String aNr, final String bsn) throws OngeldigeObjectSleutelException {
        final Persoon delegate = new Persoon(soortPersoon);
        BijhoudingPersoon persoon = new BijhoudingPersoon(delegate);
        final PersoonNationaliteit nat = new PersoonNationaliteit(persoon, nationaliteit);
        final PersoonNationaliteitHistorie natHist = new PersoonNationaliteitHistorie(nat);
        natHist.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        nat.getPersoonNationaliteitHistorieSet().add(natHist);
        persoon.getPersoonNationaliteitSet().add(nat);
        if (geboortedatum != null) {
            final PersoonGeboorteHistorie
                    persoonGeboorteHistorie =
                    new PersoonGeboorteHistorie(persoon, geboortedatum, new LandOfGebied(LandOfGebied.CODE_NEDERLAND, "Nederland"));
            persoon.addPersoonGeboorteHistorie(persoonGeboorteHistorie);
        }

        if (voegBijhoudingHistorieToe || datumOverleden != null) {
            final PersoonBijhoudingHistorie bijHis;
            if (datumOverleden == null) {
                bijHis =
                        new PersoonBijhoudingHistorie(persoon, Z_PARTIJ, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
            } else {
                bijHis =
                        new PersoonBijhoudingHistorie(persoon, Z_PARTIJ, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.OVERLEDEN);
                bijHis.setDatumAanvangGeldigheid(datumOverleden);
            }
            persoon.getPersoonBijhoudingHistorieSet().add(bijHis);
        }
        persoon.setId(objectSleutel);
        if (geslachtsAanduiding != null) {
            final PersoonGeslachtsaanduidingHistorie persoonGeslachtsaanduidingHistorie = new PersoonGeslachtsaanduidingHistorie(persoon, geslachtsAanduiding);
            persoon.addPersoonGeslachtsaanduidingHistorie(persoonGeslachtsaanduidingHistorie);
        }
        final PersoonIDHistorie idHistory = new PersoonIDHistorie(persoon);
        idHistory.setAdministratienummer(aNr);
        idHistory.setBurgerservicenummer(bsn);
        persoon.addPersoonIDHistorie(idHistory);
        final PersoonGeslachtsnaamcomponent geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent(persoon, 1);
        PersoonGeslachtsnaamcomponentHistorie persoonGeslachtsnaamcomponentHistorie = new PersoonGeslachtsnaamcomponentHistorie(geslachtsnaamcomponent, stam);
        persoonGeslachtsnaamcomponentHistorie.setPredicaat(predicaat);
        persoonGeslachtsnaamcomponentHistorie.setAdellijkeTitel(adelijkeTitel);
        geslachtsnaamcomponent.addPersoonGeslachtsnaamcomponentHistorie(persoonGeslachtsnaamcomponentHistorie);
        persoon.addPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent);
        final PersoonSamengesteldeNaamHistorie psnHistorie = new PersoonSamengesteldeNaamHistorie(persoon, stam, false, false);
        psnHistorie.setVoornamen(voornamen);
        psnHistorie.setPredicaat(predicaat);
        psnHistorie.setAdellijkeTitel(adelijkeTitel);
        persoon.getPersoonSamengesteldeNaamHistorieSet().add(psnHistorie);

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, Long.toString(objectSleutel))).thenReturn(persoon);

        //OBJECTSLEUTELS BEHORENDE BIJ PERSONEN
        final ObjectSleutel sleutel = new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(objectSleutel, objectSleutel);
        when(getObjectSleutelService().maakPersoonObjectSleutel(Long.toString(objectSleutel))).thenReturn(sleutel);
        return persoon;
    }

    public AdministratieveHandelingElement createAdministratieveHandelingAanvangHuwelijk(final List<BetrokkenheidElement> betrokkenen) {
        final ElementBuilder.AdministratieveHandelingParameters ahPara = new ElementBuilder.AdministratieveHandelingParameters();
        ahPara.soort(AdministratieveHandelingElementSoort.VOLTREKKING_HUWELIJK_IN_NEDERLAND);
        final List<ActieElement> acties = new LinkedList<>();
        final ElementBuilder.RelatieGroepParameters relPara = new ElementBuilder.RelatieGroepParameters();
        relPara.datumAanvang(20100101);
        relPara.gemeenteAanvangCode("2");
        relPara.woonplaatsnaamAanvang("Leiden");
        final RelatieGroepElement relatieGroep = builder.maakRelatieGroepElement("rel_groep", relPara);

        final HuwelijkElement huwelijk = builder.maakHuwelijkElement("relatie", relatieGroep, betrokkenen);
        huwelijk.setVerzoekBericht(bericht);
        acties.add(builder.maakRegistratieAanvangHuwelijkActieElement("aan_huwelijk", 20100101, Collections.emptyList(), huwelijk));
        ahPara.acties(acties);
        ahPara.partijCode(Z_PARTIJ.getCode());
        final AdministratieveHandelingElement ah = builder.maakAdministratieveHandelingElement("ah_id", ahPara);
        ah.setVerzoekBericht(bericht);
        return ah;
    }

    public BetrokkenheidElement createPartner(final ElementBuilder.PersoonParameters pParams, final String commmunicatieId,
                                              final String objectSleutel) {
        final PersoonGegevensElement persoon1;
        if (pParams == null) {
            persoon1 = builder.maakPersoonGegevensElement(commmunicatieId, objectSleutel);
        } else {
            persoon1 = builder.maakPersoonGegevensElement(commmunicatieId, objectSleutel, null, pParams);
        }
        persoon1.setVerzoekBericht(bericht);
        if (persoon1.getPersoonEntiteit() != null) {
            persoon1.getPersoonEntiteit().registreerPersoonElement(persoon1);
        }
        return builder.maakBetrokkenheidElement("p_" + commmunicatieId, BetrokkenheidElementSoort.PARTNER, persoon1, null);
    }

    public AdministratieveHandelingElement createAdministratieveHandelingRegistratieGeborene(final Integer geboorteDatum,
                                                                                             final ElementBuilder.NaamParameters kindNaamParameters,
                                                                                             final String nationaliteitCodeKind,
                                                                                             final String voornaam,
                                                                                             final String kindStamNaam,
                                                                                             final String objectsleutelOuder1,
                                                                                             final String objectsleutelOuder2,
                                                                                             final String adelijkeTitel, final String predicaat,
                                                                                             final ElementBuilder.PersoonParameters ouder1Parameters,
                                                                                             final ElementBuilder.PersoonParameters ouder2Parameters,
                                                                                             final String bsn, final String anummer,
                                                                                             final Integer dagActies) {
        final ElementBuilder.AdministratieveHandelingParameters ahPara = new ElementBuilder.AdministratieveHandelingParameters();
        ahPara.soort(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);
        final List<ActieElement> acties = new LinkedList<>();
        //actie geborene
        final ElementBuilder.RelatieGroepParameters rPara = new ElementBuilder.RelatieGroepParameters();
        rPara.datumAanvang(20100101);
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
            betrokkenen.add(builder.maakBetrokkenheidElement("ouder1", BetrokkenheidElementSoort.OUDER, ouder1, null));
        } else {
            final PersoonGegevensElement ouder1 = builder.maakPersoonGegevensElement("ouder1_persoon", null, null, ouder1Parameters);
            ouder1.setVerzoekBericht(bericht);
            betrokkenen.add(builder.maakBetrokkenheidElement("ouder1", BetrokkenheidElementSoort.OUDER, ouder1, null));
        }

        //ouder
        if (objectsleutelOuder2 != null) {
            final PersoonGegevensElement ouder2 = builder.maakPersoonGegevensElement("ouder2_persoon", objectsleutelOuder2);
            ouder2.setVerzoekBericht(bericht);
            betrokkenen.add(builder.maakBetrokkenheidElement("ouder2", BetrokkenheidElementSoort.OUDER, ouder2, null));
        } else {
            final PersoonGegevensElement ouder2 = builder.maakPersoonGegevensElement("ouder2_persoon", null, null, ouder2Parameters);
            ouder2.setVerzoekBericht(bericht);
            betrokkenen.add(builder.maakBetrokkenheidElement("ouder2", BetrokkenheidElementSoort.OUDER, ouder2, null));
        }
        for (BetrokkenheidElement ouder : betrokkenen.stream().filter(betr -> Objects.equals(betr.getSoort(), BetrokkenheidElementSoort.OUDER))
                .collect(Collectors.toList())) {
            if (ouder.getPersoon().getPersoonEntiteit() != null) {
                ouder.getPersoon().getPersoonEntiteit().registreerPersoonElement(ouder.getPersoon());
            }
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

        acties.add(builder.maakRegistratieGeboreneActieElement("geb_actie", dagActies == null ? 20100101 : dagActies, frbElement,
                Collections.singletonList(bronReferentieElement)));

        //actie nationaliteit
        if (nationaliteitCodeKind != null) {
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
                    builder.maakRegistratieNationaliteitActieElement("nat_actie", dagActies == null ? 20100101 : dagActies, Collections.emptyList(), kind_nat);
            nat_actie.setVerzoekBericht(bericht);
            acties.add(nat_actie);
        }

        ahPara.acties(acties);
        ahPara.partijCode(Z_PARTIJ.getCode());
        final AdministratieveHandelingElement ah = builder.maakAdministratieveHandelingElement("ah_id", ahPara);
        ah.setVerzoekBericht(bericht);
        builder.initialiseerVerzoekBericht(bericht);
        return ah;
    }

}
