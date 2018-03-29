/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Maakt bijhouding verzoek berichten ter ondersteuning van JUnit testen.
 */
public final class VerzoekBerichtBuilder {

    /**
     * Maakt een bericht met daarin in een voltrekking huwelijk in NL, registratie geslachtsnaam en registratie naamgebruik.
     * Het huwelijk is tussen een ingeschreven en pseudo persoon.
     * @return een succesvol voltrekking huwelijk in NL bericht
     */
    public static BijhoudingVerzoekBericht maakSuccesHuwelijkInNederlandBericht() {
        return maakSuccesHuwelijkInNederlandBericht(false, "212121", false);
    }

    /**
     * Maakt een bericht met daarin in een voltrekking huwelijk in NL, registratie geslachtsnaam en registratie naamgebruik.
     * Het huwelijk is tussen een ingeschreven en pseudo persoon. Dit bericht bevat een fout in de datum aanvang van registratie geslachtsnaam, deze wijkt af
     * van de peildatum van de hoofdactie.
     * @return een succesvol voltrekking huwelijk in NL bericht
     */
    public static BijhoudingVerzoekBericht maakSuccesHuwelijkInNederlandBerichtMetFoutInAanvangGeldheid() {
        return maakSuccesHuwelijkInNederlandBericht(false, "212121", true);
    }

    /**
     * Maakt een bericht met daarin in een voltrekking huwelijk in NL, registratie geslachtsnaam en registratie naamgebruik.
     * Het huwelijk is tussen twee ingeschreven personen
     * @return een succesvol voltrekking huwelijk in NL bericht
     */
    public static BijhoudingVerzoekBericht maakSuccesHuwelijkInNederlandBerichtTweeIngeschrevenen() {
        return maakSuccesHuwelijkInNederlandBericht(true, "212121", false);
    }

    /**
     * Maakt een bericht met daarin in een voltrekking huwelijk in NL, registratie geslachtsnaam en registratie naamgebruik.
     * Het huwelijk is tussen twee ingeschreven personen. Voor de test is het mogelijk om een afwijkende objectsleutel mee te geven voor
     * de persoon waarna gerefereerd wordt in registratie naamgebruik.
     * @param objectSleutelPersoonNaamgebruik object sleutel voor registratie naamgebruik
     * @return een succesvol voltrekking huwelijk in NL bericht met gewijzigde objectsleutel voor naamgebruikt
     */
    public static BijhoudingVerzoekBericht maakSuccesHuwelijkInNederlandBerichtTweeIngeschrevenen(final String objectSleutelPersoonNaamgebruik) {
        return maakSuccesHuwelijkInNederlandBericht(true, objectSleutelPersoonNaamgebruik, false);
    }

    /**
     * Maakt een bericht met daarin in een ontbinding huwelijk in NL met een wijziging van de samengestelde naam van de partner.
     * @return een succesvol einde huwelijk in NL bericht
     */
    public static BijhoudingVerzoekBericht maakSuccesEindeHuwelijkInNederlandBericht() {
        final ElementBuilder builder = new ElementBuilder();
        final SamengesteldeNaamElement
                samengesteldeNaamElement =
                builder.maakSamengesteldeNaamElement("CI_samengesteldenaam_1",
                        new ElementBuilder.NaamParameters().indicatieNamenreeks(false).geslachtsnaamstam("Jansen"));
        final PersoonGegevensElement
                persoonElement =
                builder.maakPersoonGegevensElement("CI_persoon_1", "212121", null, new ElementBuilder.PersoonParameters().samengesteldeNaam(samengesteldeNaamElement));
        final BetrokkenheidElement
                betrokkenheidElement =
                builder.maakBetrokkenheidElement("CI_betrokkenheid_1", "121212", BetrokkenheidElementSoort.PARTNER, persoonElement, null);
        final List<BetrokkenheidElement> betrokkenheden = new ArrayList<>();
        betrokkenheden.add(betrokkenheidElement);
        final HuwelijkElement
                huwelijk =
                builder.maakHuwelijkElement("CI_huwelijk", "1", builder.maakRelatieGroepElement("CI_huwelijk_groep_relatie",
                        new ElementBuilder.RelatieGroepParameters().datumEinde(20160321).gemeenteEindeCode("0530").woonplaatsnaamEinde("Hellevoetsluis")
                                .redenEindeCode('1')),
                        betrokkenheden);
        final ActieElement
                registratieEindeHuwelijk =
                builder.maakRegistratieEindeHuwelijkActieElement("CI_actie_reg_huw_gp", 20160321,
                        Collections.singletonList(builder.maakBronReferentieElement("CI_actie_bron", "CI_ah_bron_1")), huwelijk);
        final List<ActieElement> acties = new ArrayList<>();
        acties.add(registratieEindeHuwelijk);

        final AdministratieveHandelingElement
                administratieveHandeling =
                builder.maakAdministratieveHandelingElement("CI_adm_hand",
                        new ElementBuilder.AdministratieveHandelingParameters().soort(AdministratieveHandelingElementSoort.ONTBINDING_HUWELIJK_IN_NEDERLAND)
                                .partijCode("053001").toelichtingOntlening("Test toelichting op de ontlening")
                                .bronnen(maakBronnen(builder)).acties(acties));

        final BijhoudingVerzoekBericht
                result =
                new BijhoudingVerzoekBerichtImpl(Collections.emptyMap(), BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP, maakStuurgegevensElement(builder),
                        maakParametersElement(builder),
                        administratieveHandeling);
        builder.initialiseerVerzoekBericht(result);
        return result;
    }

    private static BijhoudingVerzoekBericht     maakSuccesHuwelijkInNederlandBericht(final boolean isPartnerIngeschreven,
                                                                                 final String objectSleutelPersoonNaamgebruik,
                                                                                 boolean heeftAndereDatumAanvangRegistratieGeslachtsnaam) {
        final ElementBuilder builder = new ElementBuilder();

        final List<GedeblokkeerdeMeldingElement> gedeblokkeerdeMeldingen = new ArrayList<>();
        gedeblokkeerdeMeldingen.add(builder.maakGedeblokkeerdeMeldingElement("CI_gedeblokkeerde_melding_1", "CI_ref_naar_vorig_bericht", "R1651", null));

        final List<ActieElement> acties = new ArrayList<>();
        final List<BetrokkenheidElement> betrokkenheden = new ArrayList<>();
        betrokkenheden.add(builder
                .maakBetrokkenheidElement("CI_betrokkenheid_1", BetrokkenheidElementSoort.PARTNER, builder.maakPersoonGegevensElement("CI_persoon_1", "212121"), null));

        if (isPartnerIngeschreven) {
            betrokkenheden.add(builder
                    .maakBetrokkenheidElement("CI_betrokkenheid_2", BetrokkenheidElementSoort.PARTNER, builder.maakPersoonGegevensElement("CI_persoon_2", "313131"),
                            null));
        } else {
            final IdentificatienummersElement
                    identificatienummers =
                    builder.maakIdentificatienummersElement("CI_identificatienummers_p2", "309072025", "4279162913");
            final SamengesteldeNaamElement
                    samengesteldenaam =
                    builder.maakSamengesteldeNaamElement("CI_samengesteldeNaam_p2",
                            new ElementBuilder.NaamParameters().indicatieNamenreeks(Boolean.FALSE).voornamen("Willy ").voorvoegsel("dos").scheidingsteken(' ')
                                    .geslachtsnaamstam("Santos da Victoria"));
            final GeboorteElement
                    geboorte =
                    builder.maakGeboorteElement("CI_geboorte_p2",
                            new ElementBuilder.GeboorteParameters().datum(19850919).buitenlandsePlaats("Maceio").landGebiedCode("6008"));
            final GeslachtsaanduidingElement geslachtsaanduiding = builder.maakGeslachtsaanduidingElement("CI_geslachtsaanduiding_p2", "M");
            betrokkenheden
                    .add(builder
                            .maakBetrokkenheidElement("CI_betrokkenheid_2", BetrokkenheidElementSoort.PARTNER, builder.maakPersoonGegevensElement("CI_persoon_2", null,
                                    null, new ElementBuilder.PersoonParameters().identificatienummers(identificatienummers)
                                            .samengesteldeNaam(samengesteldenaam)
                                            .geboorte(geboorte)
                                            .geslachtsaanduiding(geslachtsaanduiding)), null));
        }
        final HuwelijkElement
                huwelijk =
                builder.maakHuwelijkElement("CI_huwelijk", builder.maakRelatieGroepElement("CI_huwelijk_groep_relatie",
                        new ElementBuilder.RelatieGroepParameters().datumAanvang(20160321).gemeenteAanvangCode("1530").woonplaatsnaamAanvang("Hellevoetsluis")),
                        betrokkenheden);
        final ActieElement
                registratieAanvangHuwelijk =
                builder.maakRegistratieAanvangHuwelijkActieElement("CI_actie_reg_huw_gp", 20160321,
                        Collections.singletonList(builder.maakBronReferentieElement("CI_actie_bron", "CI_ah_bron_1")), huwelijk);
        final GeslachtsnaamcomponentElement
                nieuweGeslachtsnaam =
                builder.maakGeslachtsnaamcomponentElement("CI_persoon_3_gc_1", null, "B", "van", ' ', "Bakkersma");
        final PersoonGegevensElement
                persoonGeslachtsnaamWijziging =
                builder.maakPersoonGegevensElement("CI_persoon_3", "212121",
                        null, new ElementBuilder.PersoonParameters().geslachtsnaamcomponenten(Collections.singletonList(nieuweGeslachtsnaam)));
        final ActieElement
                registratieGeslachtsnaam =
                builder.maakRegistratieGeslachtsnaamActieElement("CI_actie_reg_geslachtsnaam",
                        heeftAndereDatumAanvangRegistratieGeslachtsnaam ? huwelijk.getRelatieGroep().getDatumAanvang().getWaarde() + 1
                                : huwelijk.getRelatieGroep().getDatumAanvang().getWaarde(),
                        Collections.singletonList(builder.maakBronReferentieElement("CI_actie_bron_1", "CI_ah_bron_1")), persoonGeslachtsnaamWijziging);
        final PersoonGegevensElement
                persoonNaamgebruikWijziging =
                builder.maakPersoonGegevensElement("CI_persoon_4", objectSleutelPersoonNaamgebruik, null, new ElementBuilder.PersoonParameters()
                        .naamgebruik(builder.maakNaamgebruikElement("CI_persoon_4_naamgebruik", "E", Boolean.FALSE, "H", "Cornelis Jan", null, "s", '\'',
                                "Jansen")));

        final ActieElement
                registratieNaamgebruik =
                builder.maakRegistratieNaamgebruikActieElement("CI_actie_reg_naamgebruik", null,
                        Collections.singletonList(builder.maakBronReferentieElement("CI_actie_bron_2", "CI_ah_bron_2")), persoonNaamgebruikWijziging);
        acties.add(registratieAanvangHuwelijk);
        acties.add(registratieGeslachtsnaam);
        acties.add(registratieNaamgebruik);
        final AdministratieveHandelingElement
                administratieveHandeling =
                builder.maakAdministratieveHandelingElement("CI_adm_hand",
                        new ElementBuilder.AdministratieveHandelingParameters().soort(AdministratieveHandelingElementSoort.VOLTREKKING_HUWELIJK_IN_NEDERLAND)
                                .partijCode("053001").toelichtingOntlening("Test toelichting op de ontlening").gedeblokkeerdeMeldingen(gedeblokkeerdeMeldingen)
                                .bronnen(maakBronnen(builder)).acties(acties));

        final BijhoudingVerzoekBericht
                result =
                new BijhoudingVerzoekBerichtImpl(Collections.emptyMap(), BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP, maakStuurgegevensElement(builder),
                        maakParametersElement(builder),
                        administratieveHandeling);
        builder.initialiseerVerzoekBericht(result);
        return result;
    }

    private static StuurgegevensElement maakStuurgegevensElement(final ElementBuilder builder) {
        return builder.maakStuurgegevensElement("CI_stuurgegevens",
                new ElementBuilder.StuurgegevensParameters().zendendePartij("053001").zendendeSysteem("BZM Leverancier A")
                        .referentienummer("88409eeb-1aa5-43fc-8614-43055123a165").tijdstipVerzending("2016-03-21T09:32:03.234Z"));
    }

    private static ParametersElement maakParametersElement(final ElementBuilder builder) {
        return builder.maakParametersElement("CI_parameters", "Bijhouding");
    }

    private static List<BronElement> maakBronnen(final ElementBuilder builder) {
        final List<BronElement> results = new ArrayList<>();
        final DocumentElement document1 = builder.maakDocumentElement("CI_document_1", "Huwelijksakte", "3 abde3", null, "053001");
        results.add(builder.maakBronElement("CI_ah_bron_1", document1));
        final DocumentElement document2 = builder.maakDocumentElement("CI_document_2", "Verklaring of kennisgeving naamgebruik", null, null, "053001");
        results.add(builder.maakBronElement("CI_ah_bron_2", document2));
        return results;
    }
}
