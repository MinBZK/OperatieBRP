/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import org.junit.BeforeClass;

public abstract class AbstractActieMetGeboreneTest extends AbstractElementTest {

    static final String KIND_PERSOON_COMM_ID = "kind_persoon";
    protected ElementBuilder builder = new ElementBuilder();
    protected AdministratieveHandelingElement ah;
    protected RegistratieGeboreneActieElement actie;
    static final String BORGER_GEM_CODE = "1681";
    static final String BORGER_GEM_CODE2 = "1682";
    static final String BORGER_GEM_CODE3 = "1683";
    static final Partij PARTIJ_1 = new Partij("Borger", "001681");
    static final Gemeente GEMEENTE_BORGERODOORN = new Gemeente(Short.parseShort(BORGER_GEM_CODE), "Borger", BORGER_GEM_CODE, PARTIJ_1);
    static final Partij PARTIJ_2 = new Partij("Borger2", "001682");
    static final Partij PARTIJ_3_NIET_ROL_COLLEGE = new Partij("Borger3", "001683");
    static final Gemeente GEMEENTE_BORGERODOORN2 = new Gemeente(Short.parseShort(BORGER_GEM_CODE2), "Borger2", BORGER_GEM_CODE2, PARTIJ_2);
    static final Gemeente GEMEENTE_BORGERODOORN3 = new Gemeente(Short.parseShort(BORGER_GEM_CODE3), "Borger3", BORGER_GEM_CODE3, PARTIJ_3_NIET_ROL_COLLEGE);
    static final String BORGER = "Borger";
    static final String BORGER2 = "Borger2";
    static final String BORGER3 = "Borger2";

    @BeforeClass
    public static void setupBefore() {
        PARTIJ_1.setId((short) 1);
        PARTIJ_2.setId((short) 2);
        PARTIJ_2.addPartijRol(new PartijRol(PARTIJ_2, Rol.BIJHOUDINGSORGAAN_COLLEGE));
        PARTIJ_3_NIET_ROL_COLLEGE.setId((short) 5);
        GEMEENTE_BORGERODOORN.setId((short) 3);
        GEMEENTE_BORGERODOORN2.setId((short) 4);
        GEMEENTE_BORGERODOORN3.setId((short) 6);
    }

    RegistratieGeboreneActieElement maakActieRegistratieGeborene(final Integer dag, final BijhoudingPersoon ouderPersoon,
                                                                 final boolean voegRegistratieVerstrekkingbeperkingToe,
                                                                 final boolean voegIdentificatieNummersToe,
                                                                 final String geboorteGemeenteCode,
                                                                 final RegistratieNationaliteitActieElement regnatActieElement) {
        final List<BetrokkenheidElement> betrokkenheden = new LinkedList<>();
        final PersoonGegevensElement kindPersoon = createKindPersoon(voegIdentificatieNummersToe, geboorteGemeenteCode, dag);
        betrokkenheden.add(builder.maakBetrokkenheidElement("kind", BetrokkenheidElementSoort.KIND, kindPersoon, null));
        kindPersoon.getPersoonEntiteit().registreerPersoonElement(kindPersoon);
        betrokkenheden.add(builder.maakBetrokkenheidElement("ouder", BetrokkenheidElementSoort.OUDER, createOuderPersoon(ouderPersoon),
                builder.maakOuderschapElement("ouderschap", true)));

        final FamilierechtelijkeBetrekkingElement frBetrekking = builder.maakFamilierechtelijkeBetrekkingElement("geb_fam", null, betrokkenheden);

        final HashMap<String, String> attr = new HashMap<>();
        attr.put(BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT.toString(), "brondocument");
        final BronElement bronElement = builder.maakBronElement("ci_test_2",
                new DocumentElement(attr, new StringElement("33"), new StringElement("3"), null, new StringElement("123456")));
        final HashMap<String, BmrGroep> bronParams = new HashMap<>();
        bronParams.put("ci_test_2", bronElement);
        final BronReferentieElement bronReferentieElement = builder.maakBronReferentieElement("com_bron", "ci_test_2");
        bronReferentieElement.initialiseer(bronParams);

        actie = builder.maakRegistratieGeboreneActieElement("actie", dag, frBetrekking, Collections.singletonList(bronReferentieElement));
        actie.setVerzoekBericht(getBericht());
        setupAdministratieveHandeling(voegRegistratieVerstrekkingbeperkingToe, regnatActieElement);
        when(getBericht().getAdministratieveHandeling()).thenReturn(ah);
        return actie;
    }

    private PersoonGegevensElement createOuderPersoon(final BijhoudingPersoon persoon) {
        final PersoonGegevensElement persoonElement;
        final String ouder_persoon = "ouder_persoon";
        if (persoon == null) {
            final ElementBuilder.PersoonParameters persoonParams = new ElementBuilder.PersoonParameters();
            final ElementBuilder.NaamParameters naamParams = new ElementBuilder.NaamParameters();
            naamParams.geslachtsnaamstam("Stam");
            naamParams.indicatieNamenreeks(false);
            persoonParams.samengesteldeNaam(builder.maakSamengesteldeNaamElement("samengesteldeNaam", naamParams));
            persoonElement = builder.maakPersoonGegevensElement(ouder_persoon, null, null, persoonParams);
        } else {
            final String objectsleutel = "1234";
            persoonElement = builder.maakPersoonGegevensElement(ouder_persoon, objectsleutel);
            when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectsleutel)).thenReturn(persoon);
        }
        persoonElement.setVerzoekBericht(getBericht());
        return persoonElement;
    }

    private PersoonGegevensElement createKindPersoon(final boolean voegIdentificatieNummersToe, final String gemeenteCodeGeboorte,
                                                     final Integer geboorteDatum) {
        final ElementBuilder.PersoonParameters kindParameters = new ElementBuilder.PersoonParameters();
        kindParameters.samengesteldeNaam(createKindSamenGesteldeNaam());
        kindParameters.geboorte(createGeboorte(gemeenteCodeGeboorte, geboorteDatum));
        kindParameters.geslachtsaanduiding(builder.maakGeslachtsaanduidingElement("kind_GA", "M"));
        kindParameters.voornamen(createVoornaamElementen());
        kindParameters.geslachtsnaamcomponenten(createGeslachtsNaamLijst());
        if (voegIdentificatieNummersToe) {
            kindParameters.identificatienummers(builder.maakIdentificatienummersElement("comm_id", "1234", "4321"));
        }
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement(KIND_PERSOON_COMM_ID, null, null, kindParameters);
        persoonElement.setVerzoekBericht(getBericht());
        return persoonElement;
    }

    private List<GeslachtsnaamcomponentElement> createGeslachtsNaamLijst() {
        List<GeslachtsnaamcomponentElement> stamLijst = new LinkedList<>();
        stamLijst.add(builder.maakGeslachtsnaamcomponentElement("kind_stam", null, null, null, null, "Stam"));
        return stamLijst;
    }

    private List<VoornaamElement> createVoornaamElementen() {
        List<VoornaamElement> voornamen = new LinkedList<>();
        voornamen.add(builder.maakVoornaamElement("kind_v1", 1, "Jan"));
        voornamen.add(builder.maakVoornaamElement("kind_v2", 2, "Willem"));
        voornamen.add(builder.maakVoornaamElement("kind_v3", 3, "Karel"));
        return voornamen;
    }

    private GeboorteElement createGeboorte(final String gemeenteCode, final Integer geboorteDatum) {
        final ElementBuilder.GeboorteParameters params = new ElementBuilder.GeboorteParameters();
        params.datum(geboorteDatum);
        params.gemeenteCode(gemeenteCode);
        params.woonplaatsnaam(BORGER);
        return builder.maakGeboorteElement("kind_G", params);
    }

    private SamengesteldeNaamElement createKindSamenGesteldeNaam() {
        final ElementBuilder.NaamParameters params = new ElementBuilder.NaamParameters();
        params.indicatieNamenreeks(false);
        return builder.maakSamengesteldeNaamElement("kind_SN", params);
    }

    private void setupAdministratieveHandeling(final boolean voegRegistratieVerstrekkingbeperkingToe,
                                               final RegistratieNationaliteitActieElement regNatActieElement) {
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.soort(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);
        ahParams.partijCode("1");
        final List<ActieElement> acties = new LinkedList<>();
        acties.add(actie);

        if (voegRegistratieVerstrekkingbeperkingToe) {
            final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
            persoonParameters.verstrekkingsbeperking(Collections.singletonList(builder.maakVerstrekkingsbeperkingElement("verstrekberp", new ElementBuilder
                    .VerstrekkingsbeperkingParameters().partijCode("50301"))));
            final RegistratieVerstrekkingsbeperkingActieElement
                    verstrekkingsbeperkingActieElement =
                    builder.maakRegistratieVerstrekkingsbeperkingActieElement("verstrekBepActie", null,
                            builder.maakPersoonGegevensElement("persoon", null, KIND_PERSOON_COMM_ID, persoonParameters));
            acties.add(verstrekkingsbeperkingActieElement);
        }
        if (regNatActieElement != null) {
            acties.add(regNatActieElement);
        }
        ahParams.acties(acties);
        ah = builder.maakAdministratieveHandelingElement("ah", ahParams);
    }
}
