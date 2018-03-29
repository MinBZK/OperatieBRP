/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class voor met maken van elementen uit het bijhouding berichten model.
 */
public final class ElementBuilder {

    private static final String ACTIE = "Actie";

    private final Map<String, BmrGroep> groepMap;
    private final List<Element> elementen;
    private final List<BmrGroepReferentie> referenties;
    private final ObjectSleutelIndex objectSleutelIndex;

    public ElementBuilder() {
        groepMap = new HashMap<>();
        elementen = new ArrayList<>();
        referenties = new ArrayList<>();
        objectSleutelIndex = new ObjectSleutelIndexImpl();
    }

    public StuurgegevensElement maakStuurgegevensElement(final String communicatieId, final StuurgegevensParameters parameters) {
        return registeerElement(
                new StuurgegevensElement(attr(communicatieId), parameters.zendendePartij, parameters.zendendeSysteem, parameters.ontvangendePartij,
                        parameters.referentienummer, parameters.crossReferentienummer, parameters.tijdstipVerzending));
    }

    public ParametersElement maakParametersElement(final String communicatieId, final String verwerkingswijze) {
        return registeerElement(new ParametersElement(attr(communicatieId), se(verwerkingswijze)));
    }

    public AdministratieveHandelingElement maakAdministratieveHandelingElement(final String communicatieId,
                                                                               final AdministratieveHandelingParameters parameters) {
        return registeerElement(
                new AdministratieveHandelingElement(attr(communicatieId, null, "AdministratieveHandeling", null, null), parameters.soort, parameters.partijCode,
                        parameters.toelichtingOntlening, parameters.bezienVanuitPersonen, parameters.gedeblokkeerdeMeldingen, parameters.bronnen,
                        parameters.acties));
    }

    public GedeblokkeerdeMeldingElement maakGedeblokkeerdeMeldingElement(final String communicatieId, final String referentieId, final String regelCode,
                                                                         final String melding) {
        return registeerElement(
                new GedeblokkeerdeMeldingElement(attr(communicatieId, referentieId, "GedeblokkeerdeMelding", null, null), se(regelCode), se(melding)));
    }

    public BronElement maakBronElement(final String communicatieId, final DocumentElement document) {
        return registeerElement(new BronElement(attr(communicatieId, null, "AdministratieveHandelingBron", null, null), document, null));
    }

    public BronElement maakBronElement(final String communicatieId, final DocumentElement document, final String rechtsgrondCode) {
        return registeerElement(
                new BronElement(attr(communicatieId, null, "AdministratieveHandelingBron", null, null), document, new StringElement(rechtsgrondCode)));
    }

    public DocumentElement maakDocumentElement(final String communicatieId, final String soortNaam, final String aktenummer, final String omschrijving,
                                               final String partijCode) {
        return registeerElement(
                new DocumentElement(attr(communicatieId, null, "Document", null, null), se(soortNaam), se(aktenummer), se(omschrijving), se(partijCode)));
    }

    public BeeindigingNationaliteitActieElement maakBeeindigNationaliteitActieElement(final String communicatieId,
                                                                                      final List<BronReferentieElement> bronnen,
                                                                                      final PersoonGegevensElement persoon,
                                                                                      final Integer datumEindeGeldigheid) {
        return registeerElement(
                new BeeindigingNationaliteitActieElement(attr(communicatieId, null, ACTIE, null, null), null, de(datumEindeGeldigheid),
                        new ArrayList<>(bronnen),
                        persoon));

    }


    public RegistratieAanvangHuwelijkActieElement maakRegistratieAanvangHuwelijkActieElement(final String communicatieId,
                                                                                             final Integer datumAanvangGeldigheid,
                                                                                             final List<BronReferentieElement> bronnen,
                                                                                             final HuwelijkElement huwelijk) {
        return registeerElement(
                new RegistratieAanvangHuwelijkActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null,
                        new ArrayList<>(bronnen),
                        huwelijk));
    }

    public RegistratieEindeHuwelijkActieElement maakRegistratieEindeHuwelijkActieElement(final String communicatieId,
                                                                                         final Integer datumAanvangGeldigheid,
                                                                                         final List<BronReferentieElement> bronnen,
                                                                                         final HuwelijkElement huwelijk) {
        return registeerElement(
                new RegistratieEindeHuwelijkActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null,
                        new ArrayList<>(bronnen),
                        huwelijk));
    }

    public RegistratieGeslachtsnaamVoornaamActieElement maakRegistratieGeslachtsnaamVoornaamActieElement(final String communicatieId,
                                                                                                         final Integer datumAanvangGeldigheid,
                                                                                                         final List<BronReferentieElement> bronnen,
                                                                                                         final PersoonGegevensElement persoon) {
        return registeerElement(
                new RegistratieGeslachtsnaamVoornaamActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null,
                        new ArrayList<>(bronnen), persoon));
    }


    public RegistratieAanvangGeregistreerdPartnerschapActieElement maakRegistratieAanvangGeregistreerdPartnerschapActieElement(
            final String communicatieId,
            final Integer datumAanvangGeldigheid,
            final List<BronReferentieElement> bronnen,
            final GeregistreerdPartnerschapElement geregistreerdPartnerschap) {
        return registeerElement(
                new RegistratieAanvangGeregistreerdPartnerschapActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null,
                        new ArrayList<>(bronnen),
                        geregistreerdPartnerschap));
    }

    public RegistratieGeslachtsnaamActieElement maakRegistratieGeslachtsnaamActieElement(final String communicatieId,
                                                                                         final Integer datumAanvangGeldigheid,
                                                                                         final List<BronReferentieElement> bronnen,
                                                                                         final PersoonGegevensElement persoon) {
        return registeerElement(
                new RegistratieGeslachtsnaamActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null,
                        new ArrayList<>(bronnen),
                        persoon));
    }

    public RegistratieNationaliteitActieElement maakRegistratieNationaliteitActieElement(final String communicatieId,
                                                                                         final Integer datumAanvangGeldigheid,
                                                                                         final List<BronReferentieElement> bronnen,
                                                                                         final PersoonGegevensElement persoon) {
        return registeerElement(
                new RegistratieNationaliteitActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null,
                        new ArrayList<>(bronnen),
                        persoon));
    }

    public RegistratieStaatloosActieElement maakRegistratieStaatloosActieElement(final String communicatieId,
                                                                                 final Integer datumAanvangGeldigheid,
                                                                                 final List<BronReferentieElement> bronnen,
                                                                                 final PersoonGegevensElement persoon) {
        return registeerElement(
                new RegistratieStaatloosActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null, new ArrayList<>(bronnen),
                        persoon));
    }


    public RegistratieNaamgebruikActieElement maakRegistratieNaamgebruikActieElement(final String communicatieId, final Integer datumAanvangGeldigheid,
                                                                                     final List<BronReferentieElement> bronnen,
                                                                                     final PersoonGegevensElement persoon) {
        return registeerElement(
                new RegistratieNaamgebruikActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null,
                        new ArrayList<>(bronnen),
                        persoon));
    }

    public RegistratieAdresActieElement maakRegistratieAdresActieElement(final String communicatieId, final Integer datumAanvangGeldigheid,
                                                                         final PersoonGegevensElement persoonElement) {
        return registeerElement(
                new RegistratieAdresActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null, Collections.emptyList(),
                        persoonElement));
    }

    public RegistratieVerblijfsrechtActieElement maakRegistratieVerblijfsrechtActieElement(final String communicatieId, final Integer datumAanvangGeldigheid,
                                                                                           final PersoonGegevensElement persoonElement) {
        return registeerElement(
                new RegistratieVerblijfsrechtActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null,
                        Collections.emptyList(),
                        persoonElement));
    }

    public RegistratieVerstrekkingsbeperkingActieElement maakRegistratieVerstrekkingsbeperkingActieElement(final String communicatieId,
                                                                                                           final Integer datumAanvangGeldigheid,
                                                                                                           final PersoonGegevensElement persoonElement) {
        return registeerElement(
                new RegistratieVerstrekkingsbeperkingActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null,
                        Collections.emptyList(),
                        persoonElement));
    }

    public RegistratieMigratieActieElement maakRegistratieMigratieActieElement(final String communicatieId, final Integer datumAanvangGeldigheid,
                                                                               final PersoonGegevensElement persoonElement) {
        return registeerElement(
                new RegistratieMigratieActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null, Collections.emptyList(),
                        persoonElement));
    }


    public RegistratieNationaliteitActieElement maakRegistratieNationaliteitActieElement(final String communicatieId, final Integer datumAanvangGeldigheid,
                                                                                         final PersoonGegevensElement persoonElement) {
        return registeerElement(
                new RegistratieNationaliteitActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null,
                        Collections.emptyList(),
                        persoonElement));
    }

    public RegistratieOuderActieElement maakRegistratieOuderActieElement(final String communicatieId, final Integer datumAanvangGeldigheid,
                                                                         final PersoonRelatieElement persoonRelatieElement) {
        return registeerElement(
                new RegistratieOuderActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null, Collections.emptyList(),
                        persoonRelatieElement));
    }

    public RegistratieGeboreneActieElement maakRegistratieGeboreneActieElement(final String communicatieId, final Integer datumAanvangGeldigheid,
                                                                               final FamilierechtelijkeBetrekkingElement familieRechtelijkeBetrekking) {
        return maakRegistratieGeboreneActieElement(communicatieId, datumAanvangGeldigheid, familieRechtelijkeBetrekking, Collections.emptyList());
    }

    public RegistratieGeboreneActieElement maakRegistratieGeboreneActieElement(final String communicatieId, final Integer datumAanvangGeldigheid,
                                                                               final FamilierechtelijkeBetrekkingElement familieRechtelijkeBetrekking,
                                                                               List<BronReferentieElement> bronnen) {
        return registeerElement(
                new RegistratieGeboreneActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null, bronnen,
                        familieRechtelijkeBetrekking));
    }

    public RegistratieIdentificatienummersActieElement maakRegistratieIdentificatienummersActieElement(final String commId,
                                                                                                       final Integer datumAanvangGeldigheid,
                                                                                                       final PersoonGegevensElement persoonElement) {
        return registeerElement(
                new RegistratieIdentificatienummersActieElement(attr(commId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null,
                        Collections.emptyList(),
                        persoonElement));
    }

    public CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerde maakCorrectieRegistratieIdentificatienummersGerelateerdeActieElement(
            final String communicatieId, final Integer datumAanvang, final Integer datumEinde,
            final PersoonRelatieElement persoon) {
        final DatumElement einde = datumEinde == null ? null : new DatumElement(datumEinde);
        return registeerElement(
                new CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerde(attr(communicatieId, null, ACTIE, null, null),
                        new DatumElement(datumAanvang),
                        einde, null, persoon));
    }

    public CorrectieVervalIdentificatienummersGerelateerde maakCorrectieVervalIdentificatienummersGerelateerdeActieElement(
            final String communicatieId, final PersoonRelatieElement persoon, final Character aanduidingVerval) {
        return registeerElement(
                new CorrectieVervalIdentificatienummersGerelateerde(attr(communicatieId, null, ACTIE, null, null), null,
                        null, null, new CharacterElement(aanduidingVerval), persoon));
    }

    public CorrectieRegistratieGeslachtsaanduidingRegistratieGegevensGerelateerde maakCorrectieRegistratieGeslachtsaanduidingGerelateerdeActieElement(
            final String communicatieId, final Integer datumAanvang, final Integer datumEinde,
            final PersoonRelatieElement persoon) {
        final DatumElement einde = datumEinde == null ? null : new DatumElement(datumEinde);
        return registeerElement(
                new CorrectieRegistratieGeslachtsaanduidingRegistratieGegevensGerelateerde(attr(communicatieId, null, ACTIE, null, null),
                        new DatumElement(datumAanvang),
                        einde, null, persoon));
    }

    public CorrectieVervalGeslachtsaanduidingGerelateerde maakCorrectieVervalGeslachtsaanduidingGerelateerdeActieElement(
            final String communicatieId, final PersoonRelatieElement persoon, final Character aanduidingVerval) {
        return registeerElement(
                new CorrectieVervalGeslachtsaanduidingGerelateerde(attr(communicatieId, null, ACTIE, null, null), null,
                        null, null, new CharacterElement(aanduidingVerval), persoon));
    }

    public CorrectieRegistratieGeboorteRegistratieGegevensGerelateerde maakCorrectieRegistratieGeboorteGerelateerdeActieElement(
            final String communicatieId, final Integer datumAanvang, final Integer datumEinde,
            final PersoonRelatieElement persoon) {
        final DatumElement einde = datumEinde == null ? null : new DatumElement(datumEinde);
        return registeerElement(
                new CorrectieRegistratieGeboorteRegistratieGegevensGerelateerde(attr(communicatieId, null, ACTIE, null, null), new DatumElement(datumAanvang),
                        einde, null, persoon));
    }

    public CorrectieVervalGeboorteGerelateerde maakCorrectieVervalGeboorteGerelateerdeActieElement(
            final String communicatieId, final PersoonRelatieElement persoon, final Character aanduidingVerval) {
        return registeerElement(
                new CorrectieVervalGeboorteGerelateerde(attr(communicatieId, null, ACTIE, null, null), null,
                        null, null, new CharacterElement(aanduidingVerval), persoon));
    }

    public CorrectieRegistratieSamengesteldeNaamRegistratieGegevensGerelateerde maakCorrectieRegistratieSamengesteldeNaamGerelateerdeActieElement(
            final String communicatieId, final Integer datumAanvang, final Integer datumEinde,
            final PersoonRelatieElement persoon) {
        final DatumElement einde = datumEinde == null ? null : new DatumElement(datumEinde);
        return registeerElement(
                new CorrectieRegistratieSamengesteldeNaamRegistratieGegevensGerelateerde(attr(communicatieId, null, ACTIE, null, null),
                        new DatumElement(datumAanvang),
                        einde, null, persoon));
    }

    public CorrectieVervalSamengesteldeNaamGerelateerde maakCorrectieVervalSamengesteldeNaamGerelateerdeActieElement(
            final String communicatieId, final PersoonRelatieElement persoon, final Character aanduidingVerval) {
        return registeerElement(
                new CorrectieVervalSamengesteldeNaamGerelateerde(attr(communicatieId, null, ACTIE, null, null), null,
                        null, null, new CharacterElement(aanduidingVerval), persoon));
    }


    public CorrectieRegistratieRelatieActieElement maakCorrectieRegistratieRelatieActieElement(final String communicatieId,
                                                                                               final RelatieElement relatieElement) {
        return registeerElement(new CorrectieRegistratieRelatieActieElement(attr(communicatieId, null, ACTIE, null, null), null, null, null, relatieElement));
    }

    public CorrectieVervalRelatieActieElement maakCorrectieVervalRelatieActieElement(final String communicatieId, final RelatieElement relatieElement,
                                                                                     final String voorkomenSleutel, final Character nadereAanduidingVerval) {
        final CharacterElement aanduidingVerval = nadereAanduidingVerval == null ? null : new CharacterElement(nadereAanduidingVerval);
        return registeerElement(
                new CorrectieVervalRelatieActieElement(attr(communicatieId, null, ACTIE, null, voorkomenSleutel), null, null, null, aanduidingVerval,
                        relatieElement));
    }

    public RegistratieGeslachtsaanduidingGerelateerdeActieElement maakRegistratieGeslachtsaanduidingGerelateerdeActieElement(final String communicatieId,
                                                                                                                             final Integer
                                                                                                                                     datumAanvangGeldigheid,
                                                                                                                             final PersoonRelatieElement
                                                                                                                                     persoonRelatieElement) {
        return registeerElement(
                new RegistratieGeslachtsaanduidingGerelateerdeActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null,
                        null,
                        persoonRelatieElement));
    }

    public RegistratieGeboorteGerelateerdeActieElement maakRegistratieGeboorteGerelateerdeActieElement(final String communicatieId, final Integer
            datumAanvangGeldigheid, final PersoonRelatieElement persoonRelatieElement) {
        return registeerElement(
                new RegistratieGeboorteGerelateerdeActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null, null,
                        persoonRelatieElement));
    }

    public RegistratieIdentificatienummersGerelateerdeActieElement maakRegistratieIdentificatienummersGerelateerdeActieElement(final String communicatieId,
                                                                                                                               final Integer
                                                                                                                                       datumAanvangGeldigheid,
                                                                                                                               final PersoonRelatieElement
                                                                                                                                       persoonRelatieElement) {
        return registeerElement(
                new RegistratieIdentificatienummersGerelateerdeActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null,
                        null,
                        persoonRelatieElement));
    }

    public RegistratieSamengesteldeNaamGerelateerdeActieElement maakRegistratieSamengesteldeNaamGerelateerdeActieElement(final String communicatieId,
                                                                                                                         final Integer
                                                                                                                                 datumAanvangGeldigheid,
                                                                                                                         final PersoonRelatieElement
                                                                                                                                 persoonRelatieElement) {
        return registeerElement(
                new RegistratieSamengesteldeNaamGerelateerdeActieElement(attr(communicatieId, null, ACTIE, null, null), de(datumAanvangGeldigheid), null, null,
                        persoonRelatieElement));
    }

    public FamilierechtelijkeBetrekkingElement maakFamilierechtelijkeBetrekkingElement(final String communicatieId, final RelatieGroepElement relatie,
                                                                                       final List<BetrokkenheidElement> betrokkenheden) {
        return maakFamilierechtelijkeBetrekkingElement(communicatieId, null, null, relatie, betrokkenheden);
    }

    public FamilierechtelijkeBetrekkingElement maakFamilierechtelijkeBetrekkingElement(final String communicatieId, final String objectSleutel,
                                                                                       final String referentieId,
                                                                                       final RelatieGroepElement relatie,
                                                                                       final List<BetrokkenheidElement> betrokkenheden) {
        return registeerElement(new FamilierechtelijkeBetrekkingElement(attr(communicatieId, referentieId, "relatie", objectSleutel, null),
                relatie, betrokkenheden));
    }


    public BetrokkenheidElement maakBetrokkenheidElement(final String communicatieId, final BetrokkenheidElementSoort soort,
                                                         final PersoonGegevensElement persoon, final OuderschapElement ouderschap) {
        return maakBetrokkenheidElement(communicatieId, null, soort, persoon, ouderschap);
    }

    public BetrokkenheidElement maakBetrokkenheidElement(final String communicatieId, final String objectSleutel, final BetrokkenheidElementSoort soort,
                                                         final PersoonGegevensElement persoon, final OuderschapElement ouderschap) {
        return registeerElement(new BetrokkenheidElement(attr(communicatieId, null, "Betrokkenheid", objectSleutel, null), soort, persoon, ouderschap, null,
                null));
    }

    public BetrokkenheidElement maakBetrokkenheidElement(final String communicatieId, final String objectSleutel, final String referentieId,
                                                         final BetrokkenheidElementSoort soort, final FamilierechtelijkeBetrekkingElement famBetrekking) {
        return registeerElement(
                new BetrokkenheidElement(attr(communicatieId, referentieId, "Betrokkenheid", objectSleutel, null), soort, null, null, famBetrekking, null));
    }

    public BetrokkenheidElement maakBetrokkenheidElement(final String communicatieId, final String objectSleutel, final String referentieId,
                                                         final BetrokkenheidElementSoort soort, final HuwelijkElement huwelijk) {
        return registeerElement(
                new BetrokkenheidElement(attr(communicatieId, referentieId, "Betrokkenheid", objectSleutel, null), soort, null, null, null, huwelijk));
    }

    public BetrokkenheidElement maakBetrokkenheidElement(final String communicatieId, final String objectSleutel, final String referentieId,
                                                         final BetrokkenheidElementSoort soort,
                                                         final GeregistreerdPartnerschapElement geregistreerdPartnerschap) {
        return registeerElement(
                new BetrokkenheidElement(attr(communicatieId, referentieId, "Betrokkenheid", objectSleutel, null), soort, null, null, null,
                        geregistreerdPartnerschap));
    }

    public OuderschapElement maakOuderschapElement(final String communicatieId, final boolean indicatie) {
        return registeerElement(new OuderschapElement(attr(communicatieId, null, "ouderschap", null, null), be(indicatie)));
    }

    public HuwelijkElement maakHuwelijkElement(final String communicatieId, final RelatieGroepElement relatieGroep,
                                               final List<BetrokkenheidElement> betrokkenheden) {
        return maakHuwelijkElement(communicatieId, null, relatieGroep, betrokkenheden);
    }

    public HuwelijkElement maakHuwelijkElement(final String communicatieId, final String objectSleutel, final RelatieGroepElement relatieGroep,
                                               final List<BetrokkenheidElement> betrokkenheden) {
        return registeerElement(new HuwelijkElement(attr(communicatieId, null, "Relatie", objectSleutel, null), relatieGroep, new ArrayList<>(betrokkenheden)));
    }

    public GeregistreerdPartnerschapElement maakGeregistreerdPartnerschapElement(final String communicatieId, RelatieGroepElement relatieGroep,
                                                                                 List<BetrokkenheidElement> betrokkenheden) {
        return registeerElement(
                new GeregistreerdPartnerschapElement(attr(communicatieId, null, "Relatie", null, null), relatieGroep, new ArrayList<>(betrokkenheden)));
    }

    public RelatieGroepElement maakRelatieGroepElement(final String communicatieId, final RelatieGroepParameters parameters) {
        return maakRelatieGroepElement(communicatieId, null, parameters);
    }

    public RelatieGroepElement maakRelatieGroepElement(final String communicatieId, final String voorkomenSleutel, final RelatieGroepParameters parameters) {
        final RelatieGroepElement
                relatieGroepElement =
                new RelatieGroepElement(attr(communicatieId, null, null, null, voorkomenSleutel), parameters.datumAanvang, parameters.gemeenteAanvangCode,
                        parameters.woonplaatsnaamAanvang,
                        parameters.redenEindeCode, parameters.datumEinde, parameters.gemeenteEindeCode, parameters.woonplaatsnaamEinde,
                        parameters.buitenlandsePlaatsAanvang, parameters.buitenlandseRegioAanvang, parameters.omschrijvingLocatieAanvang,
                        parameters.landGebiedAanvangCode, parameters.buitenlandsePlaatsEinde, parameters.buitenlandseRegioEinde,
                        parameters.omschrijvingLocatieEinde,
                        parameters.landGebiedEindeCode);
        relatieGroepElement.setVerzoekBericht(parameters.verzoekBericht);
        return registeerElement(
                relatieGroepElement);
    }

    public PersoonGegevensElement maakPersoonGegevensElement(final String communicatieId, final String objectsleutel) {
        return maakPersoonGegevensElement(communicatieId, objectsleutel, null, new PersoonParameters());
    }

    public PersoonRelatieElement maakPersoonRelatieElement(final String communicatieId, final String referentie,
                                                           final String objectSleutel, final List<BetrokkenheidElement> betrokkenheden) {
        return registeerElement(
                new PersoonRelatieElement(attr(communicatieId, referentie, "Persoon", objectSleutel, null), null, null, null, null, null, null, null, null,
                        null, null,
                        null, null, null, null, null, null, betrokkenheden)
        );
    }


    public PersoonGegevensElement maakPersoonGegevensElement(final String communicatieId, final String objectsleutel, final String referentieId,
                                                             final PersoonParameters parameters) {
        return registeerElement(
                new PersoonGegevensElement(attr(communicatieId, referentieId, "Persoon", objectsleutel, null), parameters.afgeleidAdministratief,
                        parameters.identificatienummers,
                        parameters.samengesteldeNaam, parameters.geboorte, parameters.geslachtsaanduiding, parameters.bijhouding,
                        parameters.verblijfsrecht, parameters.voornamen, parameters.geslachtsnaamcomponenten,
                        parameters.naamgebruik,
                        parameters.adres == null ? null : Collections.singletonList(parameters.adres),
                        parameters.indicaties,
                        parameters.verstrekkingsbeperking,
                        parameters.migratie,
                        parameters.nationaliteiten,
                        parameters.onderzoeken));
    }

    public PersoonGegevensElement maakPersoonGegevensElement(final Map<String, String> attributen, final PersoonParameters parameters) {
        return registeerElement(
                new PersoonGegevensElement(attributen, parameters.afgeleidAdministratief,
                        parameters.identificatienummers,
                        parameters.samengesteldeNaam, parameters.geboorte, parameters.geslachtsaanduiding, parameters.bijhouding, parameters.verblijfsrecht,
                        parameters.voornamen, parameters.geslachtsnaamcomponenten,
                        parameters.naamgebruik,
                        parameters.adres == null ? null : Collections.singletonList(parameters.adres),
                        parameters.indicaties,
                        parameters.verstrekkingsbeperking,
                        parameters.migratie,
                        parameters.nationaliteiten,
                        parameters.onderzoeken));
    }


    public BronReferentieElement maakBronReferentieElement(final String communicatieId, final String referentieId) {
        return registeerElement(new BronReferentieElement(attr(communicatieId, referentieId, "ActieBron", null, null)));
    }

    public NaamgebruikElement maakNaamgebruikElement(final String communicatieId, final String code,
                                                     final Boolean indicatieAfgeleid, final String predicaatCode, final String voornamen,
                                                     final String adellijkeTitelCode, final String voorvoegsel, final Character scheidingsteken,
                                                     final String geslachtsnaamstam) {
        return registeerElement(new NaamgebruikElement(attr(communicatieId), se(code), be(indicatieAfgeleid),
                se(predicaatCode), se(voornamen), se(adellijkeTitelCode), se(voorvoegsel),
                ce(scheidingsteken), se(geslachtsnaamstam)));
    }

    public GeslachtsnaamcomponentElement maakGeslachtsnaamcomponentElement(final String communicatieId, final String predicaatCode,
                                                                           final String adellijkeTitelCode, final String voorvoegsel,
                                                                           final Character scheidingsteken, final String stam) {
        return registeerElement(
                new GeslachtsnaamcomponentElement(attr(communicatieId, null, "PersoonGeslachtsnaamcomponent", null, null), se(predicaatCode),
                        se(adellijkeTitelCode),
                        se(voorvoegsel), ce(scheidingsteken), se(stam)));
    }

    public IdentificatienummersElement maakIdentificatienummersElement(final String communicatieId, final String burgerservicenummer,
                                                                       final String administratienummer) {
        return registeerElement(new IdentificatienummersElement(attr(communicatieId), se(burgerservicenummer), se(administratienummer)));
    }

    public IdentificatienummersElement maakIdentificatienummersElementVoorVerval(final String communicatieId, final String voorkomenSleutel) {
        return registeerElement(new IdentificatienummersElement(attr(communicatieId, null, null, null, voorkomenSleutel), null, null));
    }

    public SamengesteldeNaamElement maakSamengesteldeNaamElement(final String communicatieId, final NaamParameters parameters) {
        return registeerElement(
                new SamengesteldeNaamElement(attr(communicatieId), parameters.indicatieNamenreeks, parameters.predicaatCode, parameters.voornamen,
                        parameters.adellijkeTitelCode, parameters.voorvoegsel, parameters.scheidingsteken, parameters.geslachtsnaamstam));
    }

    public SamengesteldeNaamElement maakSamengesteldeNaamElementVoorVerval(final String communicatieId, final String voorkomenSleutel) {
        return registeerElement(
                new SamengesteldeNaamElement(attr(communicatieId, null, null, null, voorkomenSleutel), null, null, null, null, null, null, null));
    }

    public GeboorteElement maakGeboorteElement(final String communicatieId, final GeboorteParameters parameters) {
        return registeerElement(
                new GeboorteElement(attr(communicatieId), parameters.datum, parameters.gemeenteCode, parameters.woonplaatsnaam, parameters.buitenlandsePlaats,
                        parameters.buitenlandseRegio, parameters.omschrijvingLocatie, parameters.landGebiedCode));
    }

    public GeboorteElement maakGeboorteElementVoorVerval(final String communicatieId, final String voorkomenSleutel) {
        return registeerElement(
                new GeboorteElement(attr(communicatieId, null, null, null, voorkomenSleutel), null, null, null, null, null, null, null));
    }

    public GeslachtsaanduidingElement maakGeslachtsaanduidingElement(final String communicatieId, final String code) {
        return registeerElement(new GeslachtsaanduidingElement(attr(communicatieId), se(code)));
    }

    public GeslachtsaanduidingElement maakGeslachtsaanduidingElementVoorVerval(final String communicatieId, final String voorkomenSleutel) {
        return registeerElement(new GeslachtsaanduidingElement(attr(communicatieId, null, null, null, voorkomenSleutel), null));
    }

    public VoornaamElement maakVoornaamElement(final String communicatieId, final int volgnummer, final String voornaam) {
        return registeerElement(new VoornaamElement(attr(communicatieId), new IntegerElement(volgnummer), new StringElement(voornaam)));
    }


    public MigratieElement maakMigratie(final String communicatieId, final MigratieParameters params) {
        return registeerElement(new MigratieElement(attr(communicatieId), params.redenWijzigingCode, params.aangeverCode, params.landOfGebiedCode,
                params.adresRegel1, params.adresRegel2, params.adresRegel3, params.adresRegel4, params.adresRegel5, params.adresRegel6));
    }

    public AdresElement maakAdres(final String communicatieId, final AdresElementParameters params) {

        return registeerElement(new AdresElement(attr(communicatieId), params.soortCode, params.redenWijzigingCode, params.aangeverAdreshoudingCode,
                params.datumAanvangAdreshouding, params.identificatiecodeAdresseerbaarObject,
                params.identificatiecodeNummeraanduiding, params.gemeenteCode, params.naamOpenbareRuimte, params.afgekorteNaamOpenbareRuimte, params.huisnummer,
                params.huisletter,
                params.huisnummertoevoeging, params.postcode, params.woonplaatsnaam, params.locatieTenOpzichteVanAdres, params.locatieomschrijving));
    }

    public VerstrekkingsbeperkingElement maakVerstrekkingsbeperkingElement(final String communicatieId, final VerstrekkingsbeperkingParameters parameters) {
        return registeerElement(
                new VerstrekkingsbeperkingElement(attr(communicatieId, null, "PersoonVerstrekkingsbeperking", null, null),
                        parameters.partijCode, parameters.omschrijvingDerde, parameters.gemeenteVerordeningPartijCode));
    }

    public VerblijfsrechtElement maakVerblijfsrechtElement(final String communicatieId, final VerblijfsrechtParameters parameters) {
        return registeerElement(
                new VerblijfsrechtElement(attr(communicatieId, null, "verblijfsrecht", null, null), parameters.aanduidingCode, parameters.datumAanvang,
                        parameters.datumMededeling, parameters.datumVoorzienEinde));
    }

    public VolledigeVerstrekkingsbeperkingIndicatieElement maakVolledigeVerstrekkingsbeperkingIndicatieElement(final String communicatieId,
                                                                                                               final IndicatieElementParameters parameters) {
        return registeerElement(
                new VolledigeVerstrekkingsbeperkingIndicatieElement(attr(communicatieId, null, "PersoonIndicatie", null, null), parameters.heeftIndicatie));
    }

    public BijzondereVerblijfsrechtelijkePositieIndicatieElement maakBijzondereVerblijfsrechtelijkePositieIndicatieElement(final String communicatieId,
                                                                                                                           final IndicatieElementParameters
                                                                                                                                   parameters) {
        return registeerElement(
                new BijzondereVerblijfsrechtelijkePositieIndicatieElement(attr(communicatieId, null, "PersoonIndicatie", null, null),
                        parameters.heeftIndicatie));
    }

    public StaatloosIndicatieElement maakStaatloosIndicatieElement(final String communicatieId, final Boolean indicatieWaarde) {
        IndicatieElementParameters parameters = new IndicatieElementParameters();
        parameters.heeftIndicatie(indicatieWaarde);
        return registeerElement(new StaatloosIndicatieElement(attr(communicatieId, null, "PersoonIndicatie", null, null), parameters.heeftIndicatie));
    }

    public StaatloosIndicatieElement maakStaatloosIndicatieElement(final String communicatieId, final IndicatieElementParameters parameters) {
        return registeerElement(new StaatloosIndicatieElement(attr(communicatieId, null, "PersoonIndicatie", null, null), parameters.heeftIndicatie));
    }

    public NationaliteitElement maakNationaliteitElementVerlies(final String communicatieId, final String objectSleutel, final String referentieId,
                                                                final String redenVerliesCode) {
        return registeerElement(
                new NationaliteitElement(attr(communicatieId, referentieId, "PersoonNationaliteit", objectSleutel, null), null, null, se(redenVerliesCode)));
    }

    public NationaliteitElement maakNationaliteitElement(final String communicatieId, final String nationaliteitCode, final String redenVerkrijgingCode) {
        return registeerElement(
                new NationaliteitElement(attr(communicatieId, null, "PersoonNationaliteit", null, null), se(nationaliteitCode), se(redenVerkrijgingCode),
                        null));
    }

    public OnderzoekElement maakOnderzoekElement(final String communicatieId, final String objectSleutel, final OnderzoekGroepElement onderzoekGroep,
                                                 List<GegevenInOnderzoekElement> gegevensInOnderzoek) {
        return new OnderzoekElement(attr(communicatieId, null, "Onderzoek", objectSleutel, null), onderzoekGroep, gegevensInOnderzoek);
    }

    public void initialiseerVerzoekBericht(final BijhoudingVerzoekBericht result) {
        result.setObjectSleutelIndex(objectSleutelIndex);
        for (final BmrGroepReferentie groepReferentie : referenties) {
            groepReferentie.initialiseer(groepMap);
        }
        result.setReferenties(referenties);
        for (final Element element : elementen) {
            element.setVerzoekBericht(result);
            result.registreerPostConstructAanroep(element);
        }
    }

    private <T extends Element> T registeerElement(final T element) {
        elementen.add(element);
        if ((element instanceof BmrGroep) && ((BmrGroep) element).getCommunicatieId() != null) {
            final BmrGroep groep = (BmrGroep) element;
            if (groepMap.containsKey(groep.getCommunicatieId())) {
                throw new IllegalArgumentException(
                        String.format("Er komende meerdere elementen in het bericht voor met communicatieId: %s", groep.getCommunicatieId()));
            }
            groepMap.put(groep.getCommunicatieId(), groep);
            if (groep instanceof BmrGroepReferentie) {
                referenties.add((BmrGroepReferentie) groep);
            }
        }
        objectSleutelIndex.voegToe(element);
        return element;
    }

    private static StringElement se(final String string) {
        return StringElement.getInstance(string);
    }

    private static BooleanElement be(final Boolean bool) {
        if (bool == null) {
            return null;
        } else {
            return new BooleanElement(bool);
        }
    }

    private static CharacterElement ce(final Character character) {
        if (character == null) {
            return null;
        } else {
            return new CharacterElement(character);
        }
    }

    private static DatumElement de(final Integer datum) {
        if (datum == null) {
            return null;
        } else {
            return new DatumElement(datum);
        }
    }

    private static Map<String, String> attr(final String communicatieId) {
        return attr(communicatieId, null, null, null, null);
    }

    private static Map<String, String> attr(final String communicatieId, final String referentieId,
                                            final String objecttype,
                                            final String objectSleutel, final String voorkomenSleutel) {
        return new AbstractBmrGroep.AttributenBuilder().communicatieId(communicatieId).referentieId(referentieId).objecttype(objecttype)
                .objectSleutel(objectSleutel).voorkomenSleutel(voorkomenSleutel).build();
    }


    public final static class PersoonParameters {
        private AfgeleidAdministratiefElement afgeleidAdministratief;
        private IdentificatienummersElement identificatienummers;
        private SamengesteldeNaamElement samengesteldeNaam;
        private GeboorteElement geboorte;
        private GeslachtsaanduidingElement geslachtsaanduiding;
        private BijhoudingElement bijhouding;
        private VerblijfsrechtElement verblijfsrecht;
        private List<VoornaamElement> voornamen;
        private List<GeslachtsnaamcomponentElement> geslachtsnaamcomponenten;
        private NaamgebruikElement naamgebruik;
        private AdresElement adres;
        private List<VerstrekkingsbeperkingElement> verstrekkingsbeperking;
        private List<IndicatieElement> indicaties;
        public MigratieElement migratie;
        private List<NationaliteitElement> nationaliteiten;
        private List<OnderzoekElement> onderzoeken;

        public PersoonParameters afgeleidAdministratief(final AfgeleidAdministratiefElement param) {
            afgeleidAdministratief = param;
            return this;
        }

        public PersoonParameters identificatienummers(final IdentificatienummersElement param) {
            identificatienummers = param;
            return this;
        }

        public PersoonParameters samengesteldeNaam(final SamengesteldeNaamElement param) {
            samengesteldeNaam = param;
            return this;
        }

        public PersoonParameters geboorte(final GeboorteElement param) {
            geboorte = param;
            return this;
        }

        public PersoonParameters geslachtsaanduiding(final GeslachtsaanduidingElement param) {
            geslachtsaanduiding = param;
            return this;
        }

        public PersoonParameters bijhouding(final BijhoudingElement param) {
            bijhouding = param;
            return this;
        }

        public PersoonParameters verblijfsrecht(final VerblijfsrechtElement param) {
            verblijfsrecht = param;
            return this;
        }

        public PersoonParameters voornamen(final List<VoornaamElement> param) {
            voornamen = new ArrayList<>(param);
            return this;
        }

        public PersoonParameters geslachtsnaamcomponenten(final List<GeslachtsnaamcomponentElement> param) {
            geslachtsnaamcomponenten = new ArrayList<>(param);
            return this;
        }

        public PersoonParameters naamgebruik(final NaamgebruikElement param) {
            naamgebruik = param;
            return this;
        }

        public PersoonParameters adres(final AdresElement param) {
            adres = param;
            return this;
        }

        public PersoonParameters verstrekkingsbeperking(final List<VerstrekkingsbeperkingElement> param) {
            verstrekkingsbeperking = new ArrayList<>(param);
            return this;
        }

        public PersoonParameters indicaties(final List<IndicatieElement> param) {
            indicaties = new ArrayList<>(param);
            return this;
        }

        public PersoonParameters nationaliteiten(final List<NationaliteitElement> param) {
            nationaliteiten = new ArrayList<>(param);
            return this;
        }

        public PersoonParameters onderzoeken(final List<OnderzoekElement> param) {
            onderzoeken = new ArrayList<>(param);
            return this;
        }
    }

    public final static class RelatieGroepParameters {
        private DatumElement datumAanvang;
        private StringElement gemeenteAanvangCode;
        private StringElement woonplaatsnaamAanvang;
        private CharacterElement redenEindeCode;
        private DatumElement datumEinde;
        private StringElement gemeenteEindeCode;
        private StringElement woonplaatsnaamEinde;
        private StringElement buitenlandsePlaatsAanvang;
        private StringElement buitenlandseRegioAanvang;
        private StringElement omschrijvingLocatieAanvang;
        private StringElement landGebiedAanvangCode;
        private StringElement buitenlandsePlaatsEinde;
        private StringElement buitenlandseRegioEinde;
        private StringElement omschrijvingLocatieEinde;
        private StringElement landGebiedEindeCode;
        private BijhoudingVerzoekBericht verzoekBericht;

        public RelatieGroepParameters verzoekBericht(final BijhoudingVerzoekBericht bericht) {
            verzoekBericht = bericht;
            return this;
        }

        public RelatieGroepParameters datumAanvang(final Integer param) {
            datumAanvang = de(param);
            return this;
        }

        public RelatieGroepParameters gemeenteAanvangCode(final String param) {
            gemeenteAanvangCode = se(param);
            return this;
        }

        public RelatieGroepParameters woonplaatsnaamAanvang(final String param) {
            woonplaatsnaamAanvang = se(param);
            return this;
        }

        public RelatieGroepParameters redenEindeCode(final Character param) {
            redenEindeCode = ce(param);
            return this;
        }

        public RelatieGroepParameters datumEinde(final Integer param) {
            datumEinde = de(param);
            return this;
        }

        public RelatieGroepParameters gemeenteEindeCode(final String param) {
            gemeenteEindeCode = se(param);
            return this;
        }

        public RelatieGroepParameters woonplaatsnaamEinde(final String param) {
            woonplaatsnaamEinde = se(param);
            return this;
        }

        public RelatieGroepParameters buitenlandsePlaatsAanvang(final String param) {
            buitenlandsePlaatsAanvang = se(param);
            return this;
        }

        public RelatieGroepParameters buitenlandseRegioAanvang(final String param) {
            buitenlandseRegioAanvang = se(param);
            return this;
        }

        public RelatieGroepParameters omschrijvingLocatieAanvang(final String param) {
            omschrijvingLocatieAanvang = se(param);
            return this;
        }

        public RelatieGroepParameters landGebiedAanvangCode(final String param) {
            landGebiedAanvangCode = se(param);
            return this;
        }

        public RelatieGroepParameters buitenlandsePlaatsEinde(final String param) {
            buitenlandsePlaatsEinde = se(param);
            return this;
        }

        public RelatieGroepParameters buitenlandseRegioEinde(final String param) {
            buitenlandseRegioEinde = se(param);
            return this;
        }

        public RelatieGroepParameters omschrijvingLocatieEinde(final String param) {
            omschrijvingLocatieEinde = se(param);
            return this;
        }

        public RelatieGroepParameters landGebiedEindeCode(final String param) {
            landGebiedEindeCode = se(param);
            return this;
        }
    }

    public final static class AdministratieveHandelingParameters {
        private AdministratieveHandelingElementSoort soort;
        private StringElement partijCode;
        private StringElement toelichtingOntlening;
        private List<PersoonGegevensElement> bezienVanuitPersonen;
        private List<GedeblokkeerdeMeldingElement> gedeblokkeerdeMeldingen;
        private List<BronElement> bronnen;
        private List<ActieElement> acties;

        public AdministratieveHandelingParameters soort(final AdministratieveHandelingElementSoort param) {
            soort = param;
            return this;
        }

        public AdministratieveHandelingParameters partijCode(final String param) {
            partijCode = se(param);
            return this;
        }

        public AdministratieveHandelingParameters toelichtingOntlening(final String param) {
            toelichtingOntlening = se(param);
            return this;
        }

        public AdministratieveHandelingParameters bezienVanuitPersonen(final List<PersoonGegevensElement> param) {
            bezienVanuitPersonen = new ArrayList<>(param);
            return this;
        }

        public AdministratieveHandelingParameters gedeblokkeerdeMeldingen(final List<GedeblokkeerdeMeldingElement> param) {
            gedeblokkeerdeMeldingen = new ArrayList<>(param);
            return this;
        }

        public AdministratieveHandelingParameters bronnen(final List<BronElement> param) {
            bronnen = new ArrayList<>(param);
            return this;
        }

        public AdministratieveHandelingParameters acties(final List<ActieElement> param) {
            acties = new ArrayList<>(param);
            return this;
        }
    }

    public final static class StuurgegevensParameters {
        private StringElement zendendePartij;
        private StringElement zendendeSysteem;
        private StringElement ontvangendePartij;
        private StringElement referentienummer;
        private StringElement crossReferentienummer;
        private DatumTijdElement tijdstipVerzending;

        public StuurgegevensParameters zendendePartij(final String param) {
            zendendePartij = se(param);
            return this;
        }

        public StuurgegevensParameters zendendeSysteem(final String param) {
            zendendeSysteem = se(param);
            return this;
        }

        public StuurgegevensParameters ontvangendePartij(final String param) {
            ontvangendePartij = se(param);
            return this;
        }

        public StuurgegevensParameters referentienummer(final String param) {
            referentienummer = se(param);
            return this;
        }

        public StuurgegevensParameters crossReferentienummer(final String param) {
            crossReferentienummer = se(param);
            return this;
        }

        public StuurgegevensParameters tijdstipVerzending(final String param) {
            if (param == null) {
                tijdstipVerzending = null;
            } else {
                try {
                    tijdstipVerzending = DatumTijdElement.parseWaarde(param);
                } catch (OngeldigeWaardeException e) {
                    throw new IllegalArgumentException("Kan geen DatumTijdElement maken.", e);
                }
            }
            return this;
        }
    }

    public final static class NaamParameters {

        private StringElement predicaatCode;
        private StringElement adellijkeTitelCode;
        private StringElement voorvoegsel;
        private CharacterElement scheidingsteken;
        private BooleanElement indicatieNamenreeks;
        private StringElement voornamen;
        private StringElement geslachtsnaamstam;

        public NaamParameters predicaatCode(final String param) {
            predicaatCode = se(param);
            return this;
        }

        public NaamParameters adellijkeTitelCode(final String param) {
            adellijkeTitelCode = se(param);
            return this;
        }

        public NaamParameters voorvoegsel(final String param) {
            voorvoegsel = se(param);
            return this;
        }

        public NaamParameters scheidingsteken(final Character param) {
            scheidingsteken = ce(param);
            return this;
        }

        public NaamParameters indicatieNamenreeks(final Boolean param) {
            indicatieNamenreeks = be(param);
            return this;
        }

        public NaamParameters voornamen(final String param) {
            voornamen = se(param);
            return this;
        }

        public NaamParameters geslachtsnaamstam(final String param) {
            geslachtsnaamstam = se(param);
            return this;
        }
    }

    public final static class GeboorteParameters {

        private DatumElement datum;
        private StringElement gemeenteCode;
        private StringElement woonplaatsnaam;
        private StringElement buitenlandsePlaats;
        private StringElement buitenlandseRegio;
        private StringElement omschrijvingLocatie;
        private StringElement landGebiedCode;

        public GeboorteParameters datum(final Integer param) {
            datum = de(param);
            return this;
        }

        public GeboorteParameters gemeenteCode(final String param) {
            gemeenteCode = se(param);
            return this;
        }

        public GeboorteParameters woonplaatsnaam(final String param) {
            woonplaatsnaam = se(param);
            return this;
        }

        public GeboorteParameters buitenlandsePlaats(final String param) {
            buitenlandsePlaats = se(param);
            return this;
        }

        public GeboorteParameters buitenlandseRegio(final String param) {
            buitenlandseRegio = se(param);
            return this;
        }

        public GeboorteParameters omschrijvingLocatie(final String param) {
            omschrijvingLocatie = se(param);
            return this;
        }

        public GeboorteParameters landGebiedCode(final String param) {
            landGebiedCode = se(param);
            return this;
        }
    }

    public final static class VerblijfsrechtParameters {
        private StringElement aanduidingCode;
        private DatumElement datumAanvang;
        private DatumElement datumMededeling;
        private DatumElement datumVoorzienEinde;

        public VerblijfsrechtParameters aanduidingCode(final String param) {
            aanduidingCode = se(param);
            return this;
        }

        public VerblijfsrechtParameters datumAanvang(final Integer param) {
            datumAanvang = de(param);
            return this;
        }

        public VerblijfsrechtParameters datumMededeling(final Integer param) {
            datumMededeling = de(param);
            return this;
        }

        public VerblijfsrechtParameters datumVoorzienEinde(final Integer param) {
            datumVoorzienEinde = de(param);
            return this;
        }
    }

    public final static class VerstrekkingsbeperkingParameters {
        private StringElement partijCode;
        private StringElement omschrijvingDerde;
        private StringElement gemeenteVerordeningPartijCode;


        public VerstrekkingsbeperkingParameters partijCode(final String param) {
            partijCode = se(param);
            return this;
        }

        public VerstrekkingsbeperkingParameters omschrijvingDerde(final String param) {
            omschrijvingDerde = se(param);
            return this;
        }

        public VerstrekkingsbeperkingParameters gemeenteVerordeningPartijCodes(final String param) {
            gemeenteVerordeningPartijCode = se(param);
            return this;
        }


    }

    public static class MigratieParameters {

        final Map<String, String> attributen;
        CharacterElement redenWijzigingCode;
        CharacterElement aangeverCode;
        StringElement landOfGebiedCode;
        StringElement adresRegel1;
        StringElement adresRegel2;
        StringElement adresRegel3;

        public void setRedenWijzigingCode(final CharacterElement redenWijzigingCode) {
            this.redenWijzigingCode = redenWijzigingCode;
        }

        public void setAangeverCode(final CharacterElement aangeverCode) {
            this.aangeverCode = aangeverCode;
        }

        public void setLandOfGebiedCode(final StringElement landOfGebiedCode) {
            this.landOfGebiedCode = landOfGebiedCode;
        }

        public void setAdresRegel1(final StringElement adresRegel1) {
            this.adresRegel1 = adresRegel1;
        }

        public void setAdresRegel2(final StringElement adresRegel2) {
            this.adresRegel2 = adresRegel2;
        }

        public void setAdresRegel3(final StringElement adresRegel3) {
            this.adresRegel3 = adresRegel3;
        }

        public void setAdresRegel4(final StringElement adresRegel4) {
            this.adresRegel4 = adresRegel4;
        }

        public void setAdresRegel5(final StringElement adresRegel5) {
            this.adresRegel5 = adresRegel5;
        }

        public void setAdresRegel6(final StringElement adresRegel6) {
            this.adresRegel6 = adresRegel6;
        }

        StringElement adresRegel4;
        StringElement adresRegel5;
        StringElement adresRegel6;

        public MigratieParameters(Character redenWijziging, Character aangeverCode, String landOfGebied) {
            AbstractBmrGroep.AttributenBuilder builder = new AbstractBmrGroep.AttributenBuilder();
            attributen = builder.build();
            if (redenWijziging != null) {
                this.redenWijzigingCode = new CharacterElement(redenWijziging);
            }
            if (aangeverCode != null) {
                this.aangeverCode = new CharacterElement(aangeverCode);
            }

            if (landOfGebied != null) {
                this.landOfGebiedCode = new StringElement(landOfGebied);
            }
        }

    }

    public static class AdresElementParameters {
        final Map<String, String> attributen;
        StringElement soortCode;
        CharacterElement redenWijzigingCode;
        CharacterElement aangeverAdreshoudingCode;
        DatumElement datumAanvangAdreshouding;
        StringElement identificatiecodeAdresseerbaarObject;
        StringElement identificatiecodeNummeraanduiding;
        StringElement gemeenteCode;
        StringElement naamOpenbareRuimte;
        StringElement afgekorteNaamOpenbareRuimte;
        StringElement huisnummer;
        CharacterElement huisletter;
        StringElement huisnummertoevoeging;
        StringElement postcode;
        StringElement woonplaatsnaam;
        StringElement locatieTenOpzichteVanAdres;
        StringElement locatieomschrijving;

        public AdresElementParameters(String soortCode, Character redenWijzigingCode, Integer datumAanvangAdreshouding, String gemeenteCode) {
            AbstractBmrGroep.AttributenBuilder builder = new AbstractBmrGroep.AttributenBuilder();
            attributen = builder.build();
            if (soortCode != null) {
                this.soortCode = new StringElement(soortCode);
            }

            if (redenWijzigingCode != null) {
                this.redenWijzigingCode = new CharacterElement(redenWijzigingCode);
            }
            if (gemeenteCode != null) {
                this.gemeenteCode = new StringElement(gemeenteCode);
            }
            if (datumAanvangAdreshouding != null) {
                this.datumAanvangAdreshouding = new DatumElement(datumAanvangAdreshouding);
            }
        }

        public void setSoortCode(final StringElement soortCode) {
            this.soortCode = soortCode;
        }

        public void setRedenWijzigingCode(final CharacterElement redenWijzigingCode) {
            this.redenWijzigingCode = redenWijzigingCode;
        }

        public void setAangeverAdreshoudingCode(final CharacterElement aangeverAdreshoudingCode) {
            this.aangeverAdreshoudingCode = aangeverAdreshoudingCode;
        }

        public void setDatumAanvangAdreshouding(final DatumElement datumAanvangAdreshouding) {
            this.datumAanvangAdreshouding = datumAanvangAdreshouding;
        }

        public void setIdentificatiecodeAdresseerbaarObject(final StringElement identificatiecodeAdresseerbaarObject) {
            this.identificatiecodeAdresseerbaarObject = identificatiecodeAdresseerbaarObject;
        }

        public void setIdentificatiecodeNummeraanduiding(final StringElement identificatiecodeNummeraanduiding) {
            this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
        }

        public void setGemeenteCode(final StringElement gemeenteCode) {
            this.gemeenteCode = gemeenteCode;
        }

        public void setNaamOpenbareRuimte(final StringElement naamOpenbareRuimte) {
            this.naamOpenbareRuimte = naamOpenbareRuimte;
        }

        public void setAfgekorteNaamOpenbareRuimte(final StringElement afgekorteNaamOpenbareRuimte) {
            this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
        }

        public void setHuisnummer(final StringElement huisnummer) {
            this.huisnummer = huisnummer;
        }

        public void setHuisletter(final CharacterElement huisletter) {
            this.huisletter = huisletter;
        }

        public void setHuisnummertoevoeging(final StringElement huisnummertoevoeging) {
            this.huisnummertoevoeging = huisnummertoevoeging;
        }

        public void setPostcode(final StringElement postcode) {
            this.postcode = postcode;
        }

        public void setWoonplaatsnaam(final StringElement woonplaatsnaam) {
            this.woonplaatsnaam = woonplaatsnaam;
        }

        public void setLocatieTenOpzichteVanAdres(final StringElement locatieTenOpzichteVanAdres) {
            this.locatieTenOpzichteVanAdres = locatieTenOpzichteVanAdres;
        }

        public void setLocatieomschrijving(final StringElement locatieomschrijving) {
            this.locatieomschrijving = locatieomschrijving;
        }
    }

    public static class IndicatieElementParameters {
        private BooleanElement heeftIndicatie;

        public IndicatieElementParameters heeftIndicatie(final Boolean param) {
            heeftIndicatie = be(param);
            return this;
        }
    }

}
