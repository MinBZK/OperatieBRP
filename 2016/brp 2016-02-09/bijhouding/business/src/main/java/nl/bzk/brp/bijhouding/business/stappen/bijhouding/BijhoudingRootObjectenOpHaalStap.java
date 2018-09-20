/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.WaardeMetMeldingen;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.dataaccess.repository.PersoonOnderzoekSpringDataRepository;
import nl.bzk.brp.dataaccess.repository.RelatieHisVolledigRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.webservice.business.service.ObjectSleutelService;
import nl.bzk.brp.webservice.business.service.OngeldigeObjectSleutelExceptie;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Deze stap haalt van alle personen of relaties waarnaar gerefereerd wordt in het bericht middels een technische
 * sleutel de persoonslijst op.
 * Deze stap controleert dus ook bedrijfsregel BRBY0014.
 *
 * @brp.bedrijfsregel BRBY0014
 */
@Component
public class BijhoudingRootObjectenOpHaalStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String IDENTIFICERENDE_SLEUTEL_OBJECT_SLEUTEL_PREFIX = "objectSleutel=";
    private static final String BERICHT_VAN_SOORT_HEEFT_EEN_ONGELDIGE_SLEUTEL_GEBRUIKT_FOUTMELDING =
            "Bericht van soort {} heeft een ongeldige sleutel gebruikt, foutmelding: {}";
    private static final String PERSOON = "persoon";
    private static final String RELATIE = "relatie";
    private static final String REGEL_OMSCHRIJVING_VARIABELE = "%s";

    @Inject
    private ObjectSleutelService objectSleutelService;

    @Inject
    private PersoonHisVolledigRepository persoonHisVolledigRepository;

    @Inject
    private RelatieHisVolledigRepository relatieHisVolledigRepository;

    @Inject
    private PersoonOnderzoekSpringDataRepository persoonOnderzoekSpringDataRepository;

    /**
     * Voert de stap uit.
     * @param bericht het bijhoudingsbericht
     * @param context de bijhoudingberichtcontext
     * @return het resultaat
     */
    public Resultaat voerStapUit(final BijhoudingsBericht bericht, final BijhoudingBerichtContext context) {
        Resultaat resultaat = Resultaat.LEEG;
        if (bericht.getAdministratieveHandeling() != null) {
            for (final ActieBericht actieBericht : bericht.getAdministratieveHandeling().getActies()) {
                final RootObject rootObject = actieBericht.getRootObject();
                if (rootObject instanceof RelatieBericht) {
                    resultaat = resultaat.voegToe(verrijkContextMetBijhoudingRootObjectRelatie(bericht, context, (RelatieBericht) rootObject));
                } else if (rootObject instanceof PersoonBericht) {
                    resultaat = resultaat.voegToe(verrijkContextMetBijhoudingRootObjectPersoon(bericht, context, (PersoonBericht) rootObject));
                }
            }
        }
        return resultaat;
    }

    /**
     * Haalt PersoonHisVolledig rootobject op waarnaar verwezen wordt in het bericht via de technische sleutel. Indien
     * objecten niet gevonden kunnen worden op basis van de opgegeven referentie ids, dan zal er een melding aan het
     * resultaat worden toegevoegd.
     *
     * @param bericht        Het bericht.
     * @param context        de bericht context waaraan het object moet worden toegevoegd.
     * @param persoonBericht Persoon uit het bericht wat de technische sleutel bevat.
     * @return het resultaat
     * @brp.bedrijfsregel VR00054
     */
    @Regels(Regel.VR00054)
    private Resultaat verrijkContextMetBijhoudingRootObjectPersoon(final BijhoudingsBericht bericht,
                                                              final BijhoudingBerichtContext context,
                                                              final PersoonBericht persoonBericht)
    {
        Resultaat resultaat = Resultaat.LEEG;

        SoortPersoon soortPersoonInDb = null;
        if (StringUtils.isNotBlank(persoonBericht.getObjectSleutel())
                && context.getBestaandeBijhoudingsRootObjectMetIdentificerendeSleutel(persoonBericht.getObjectSleutel()) == null)
        {
            final Resultaat.Builder resultaatBuilder = new Resultaat.Builder();
            try {

                final String objectSleutelString = persoonBericht.getObjectSleutel();
                final Integer persoonId =
                        objectSleutelService.bepaalPersoonId(objectSleutelString,
                                context.getPartij().getWaarde().getCode().getWaarde());
                persoonBericht.setIdentificerendeSleutel(IDENTIFICERENDE_SLEUTEL_OBJECT_SLEUTEL_PREFIX + objectSleutelString);
                persoonBericht.setObjectSleutelDatabaseID(persoonId);
                final WaardeMetMeldingen<PersoonHisVolledig> waardeMetMeldingen = plaatsPersoonMetDatabaseIdInContext(context, persoonBericht, persoonId);
                resultaatBuilder.withMeldingen(waardeMetMeldingen.getMeldingen());
                final PersoonHisVolledig persoonHisVolledig = waardeMetMeldingen.getWaarde();
                if (persoonHisVolledig != null) {
                    soortPersoonInDb = persoonHisVolledig.getSoort().getWaarde();

                    // Voeg persoon Id toe aan het resultaat voor archivering
                    if (persoonHisVolledig.getID() != null) {
                        resultaatBuilder.withTeArchiverenPersoonIdsIngaandBericht(Collections.singletonList(persoonHisVolledig.getID()));
                    }
                }
            } catch (final OngeldigeObjectSleutelExceptie objectSleutelExceptie) {
                // objectSleutel verificatie wordt in de BijhoudingObjectSleutelverificatieStap uitgevoerd.
                //TODO: Moet dit niet een functionele log melding zijn? Of een aparte categorie voor authenticatie/autorisatie achtige meldingen
                if (bericht.getSoort().heeftWaarde()) {
                    LOGGER.warn(BERICHT_VAN_SOORT_HEEFT_EEN_ONGELDIGE_SLEUTEL_GEBRUIKT_FOUTMELDING, bericht.getSoort().getWaarde(),
                            objectSleutelExceptie);
                } else {
                    LOGGER.warn(BERICHT_VAN_SOORT_HEEFT_EEN_ONGELDIGE_SLEUTEL_GEBRUIKT_FOUTMELDING, "onbekend",
                            objectSleutelExceptie);
                }
            }

            // Check voor betrokkenheden en personen en haal deze ook op.
            resultaat = resultaatBuilder.build().voegToe(verrijkContextMetBetrokkenhedenVanPersoon(bericht, context, persoonBericht));
        }

        // Bepalen van soort persoon. Het kan zijn dat de soort al eerder (verrijking) gezet is, dan hoeven we niet
        // (opnieuw) te bepalen.
        if (persoonBericht.getSoort() == null) {
            persoonBericht.setSoort(new SoortPersoonAttribuut(bepaalSoortPersoon(context, persoonBericht.getReferentieID(), soortPersoonInDb)));
        }

        return resultaat;
    }

    /**
     * Onder een root object persoon kunnen betrokkenheden zitten, bijvoorbeeld bij de actie Registratie gezag.
     * Deze betrokkenheden kunnen weer personen bevatten met objectsleutels die we moeten ophalen. Maar de betrokkenheid
     * kan ook weer een relatie bevatten met betrokkenheden en personen. Kortom wordt hier verder verrijkt.
     *
     * @param bericht        Het bericht.
     * @param context        de bericht context waaraan het object moet worden toegevoegd.
     * @param persoonBericht Persoon uit het bericht wat de technische sleutel bevat.
     * @return het resultaat
     */
    private Resultaat verrijkContextMetBetrokkenhedenVanPersoon(final BijhoudingsBericht bericht,
                                                           final BijhoudingBerichtContext context,
                                                           final PersoonBericht persoonBericht)
    {
        Resultaat resultaat = Resultaat.LEEG;
        // In sommige berichten (bijv. registratie gezag) kan een persoon nog betrokkenheden hebben met personen
        // met objectSleutels:
        if (persoonBericht.getBetrokkenheden() != null) {
            for (final BetrokkenheidBericht betrokkenheidOnderRootObjectPersoon : persoonBericht.getBetrokkenheden()) {
                if (betrokkenheidOnderRootObjectPersoon.getPersoon() != null) {
                    resultaat = resultaat.voegToe(verrijkContextMetBijhoudingRootObjectPersoon(bericht, context, betrokkenheidOnderRootObjectPersoon
                        .getPersoon()));
                }

                // Er kan ook nog een relatie in zitten met daarin weer betrokkenheden en personen.
                if (betrokkenheidOnderRootObjectPersoon.getRelatie() != null
                        && betrokkenheidOnderRootObjectPersoon.getRelatie().getBetrokkenheden() != null)
                {
                    for (final BetrokkenheidBericht betrokkenheidOnderRelatie : betrokkenheidOnderRootObjectPersoon.getRelatie().getBetrokkenheden()) {
                        if (betrokkenheidOnderRelatie.getPersoon() != null) {
                            resultaat = resultaat.voegToe(
                                verrijkContextMetBijhoudingRootObjectPersoon(bericht, context, betrokkenheidOnderRelatie.getPersoon()));
                        }
                    }
                }
            }
        }
        return resultaat;
    }

    /**
     * Bepaalt aan de hand van de technische sleutel of referentie id de soort van de persoon in het bericht.
     *
     * @param context          de bericht context
     * @param referentieId     referentie id van persoon in persoon bericht
     * @param soortPersoonInDb de soort persoon zoals aanwezig in de database (of null indien niet gevonden)
     * @return soort persoon
     */
    private SoortPersoon bepaalSoortPersoon(final BijhoudingBerichtContext context,
                                            final String referentieId, final SoortPersoon soortPersoonInDb)
    {
        SoortPersoon soortPersoon = null;

        if (StringUtils.isNotBlank(referentieId)) {
            // Als de persoon een referentie id heeft, neem dan de soort persoon over uit de
            // (inmiddels verrijkte) soort persoon van de betreffende communicatie id.
            final List<BerichtIdentificeerbaar> gevondenObjecten =
                    context.getIdentificeerbareObjecten().get(referentieId);
            // Check, check, dubbelcheck.
            if (gevondenObjecten != null && gevondenObjecten.size() == 1
                    && gevondenObjecten.get(0) instanceof PersoonBericht)
            {
                soortPersoon = ((PersoonBericht) gevondenObjecten.get(0)).getSoort().getWaarde();
            }
        } else if (soortPersoonInDb != null) {
            // Als er een soort persoon in de database is gevonden, neem dan de soort persoon hiervan over.
            soortPersoon = soortPersoonInDb;
        } else {
            // Als er geen persoon in de database is gevonden, zijn er nog 2 opties:
            // Het is een nieuw (geboren) persoon of het is een niet ingeschrevene.
            // Voor nieuw (geboren) personen wordt op dit moment de soort gezet in de verrijking stap,
            // (zie BerichtVerrijkingsStap.verrijkActieGegevens())
            // dus als we hier zijn aangekomen, dan moeten we wel met een niet ingeschrevene te maken hebben.
            soortPersoon = SoortPersoon.NIET_INGESCHREVENE;
        }

        return soortPersoon;
    }

    /**
     * Plaats de personen in de context.
     *
     * @param context        de context
     * @param persoonBericht de persoon
     * @param databaseId     database id
     * @return een wrapper object dat een waarde en de lijst met resultaatmeldingen bevat
     */
    private WaardeMetMeldingen<PersoonHisVolledig> plaatsPersoonMetDatabaseIdInContext(final BijhoudingBerichtContext context,
                                                                   final PersoonBericht persoonBericht,
                                                                   final Integer databaseId)
    {
        final Set<ResultaatMelding> meldingen = new HashSet<>();

        PersoonHisVolledig persoonHisVolledig = null;
        if (databaseId != null) {
            persoonHisVolledig = persoonHisVolledigRepository.leesGenormalizeerdModel(databaseId);
            if (persoonHisVolledig != null) {
                context.voegBestaandBijhoudingsRootObjectToe(persoonBericht.getIdentificerendeSleutel(),
                        persoonHisVolledig);
                LOGGER.debug("Gevonden bestaand Persoon (his volledig) toegevoegd aan context.");

                // Haal tevens alle onderzoeken voor deze persoon op en voeg die toe in de context.
                final List<HisOnderzoekModel> onderzoeken =
                        this.persoonOnderzoekSpringDataRepository.vindOnderzoekenVoorPersoon(persoonHisVolledig.getID(),
                                new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
                context.voegOnderzoekenToeVoorPersoon(persoonHisVolledig.getID(), onderzoeken);
            } else {
                LOGGER.info("Geen persoon gevonden voor identificerende sleutel '{}'.", persoonBericht.getIdentificerendeSleutel());
                meldingen.add(maakMeldingVoorNietGevondenRootobject(persoonBericht, PERSOON));
            }
        } else {
            LOGGER.warn("Geen database id kunnen bepalen voor persoon op basis van identificerende sleutel '{}'.",
                    persoonBericht.getIdentificerendeSleutel());
            meldingen.add(maakMeldingVoorNietGevondenRootobject(persoonBericht, PERSOON));
        }
        return new WaardeMetMeldingen<>(persoonHisVolledig, meldingen);
    }

    /**
     * Haalt RelatieHisVolledig rootobject op indien daarnaar verwezen wordt met een technisch sleutel in het bericht.
     * Anders worden voor alle betrokkenheden de Persoon rootobjecten opgehaald indien daarnaar verwezen wordt met een
     * technische sleutel. Indien objecten niet gevonden kunnen worden op basis van de opgegeven referentie ids, dan
     * zal er een melding aan de context worden toegevoegd.
     *
     * @param bericht        Het bericht.
     * @param context        de context waaraan rootobjecten moeten worden toegevoegd.
     * @param relatieBericht relatie uit het bericht.
     * @return het resultaat
     * @brp.bedrijfsregel VR00054
     */
    @Regels(Regel.VR00054)
    private Resultaat verrijkContextMetBijhoudingRootObjectRelatie(final BijhoudingsBericht bericht,
                                                              final BijhoudingBerichtContext context,
                                                              final RelatieBericht relatieBericht)
    {
        Resultaat resultaat = Resultaat.LEEG;

        if (StringUtils.isNotBlank(relatieBericht.getObjectSleutel())) {
            //Haal de relatie op en verrijk de context ermee.
            final String objectSleutelRelatie = relatieBericht.getObjectSleutel();
            Integer databaseIdRelatie = null;
            if (StringUtils.isNumeric(objectSleutelRelatie)) {
                databaseIdRelatie = Integer.parseInt(objectSleutelRelatie);
            }

            final Resultaat.Builder resultaatBuilder = new Resultaat.Builder();
            if (databaseIdRelatie != null) {
                final RelatieHisVolledig relatieHisVolledig =
                        relatieHisVolledigRepository.leesGenormalizeerdModel(databaseIdRelatie);
                if (relatieHisVolledig != null) {
                    resultaatBuilder.withMeldingen(verrijkContextVoorRelatieHisVolledig(context, relatieBericht, objectSleutelRelatie,
                        relatieHisVolledig));
                } else {
                    LOGGER.info("Geen relatie gevonden voor technische sleutel '{}'.", objectSleutelRelatie);
                    resultaatBuilder.withMeldingen(Collections.singleton(maakMeldingVoorNietGevondenRootobject(relatieBericht, RELATIE)));
                }
            } else {
                LOGGER.warn("Geen database id kunnen bepalen voor relatie op basis van technische sleutel '{}'.",
                        objectSleutelRelatie);
                resultaatBuilder.withMeldingen(Collections.singleton(maakMeldingVoorNietGevondenRootobject(relatieBericht, RELATIE)));
            }
            resultaat = resultaatBuilder.build();
        }

        //Haal de Personen op die betrokken zijn bij de relatie, voor zover die bekend zijn in de BRP.
        for (final BetrokkenheidBericht betrokkenheidBericht : relatieBericht.getBetrokkenheden()) {
            if (betrokkenheidBericht.getPersoon() != null) {
                resultaat = resultaat.voegToe(verrijkContextMetBijhoudingRootObjectPersoon(bericht, context, betrokkenheidBericht.getPersoon()));

                // Voeg persoon Id toe aan het resultaat voor archivering
                if (betrokkenheidBericht.getPersoon().getObjectSleutelDatabaseID() != null) {
                    final Resultaat.Builder resultaatBuilder = new Resultaat.Builder();
                    resultaat = resultaatBuilder
                        .withTeArchiverenPersoonIdsIngaandBericht(Collections.singletonList(betrokkenheidBericht.getPersoon().getObjectSleutelDatabaseID()))
                        .build().voegToe(resultaat);
                }
            }
        }
        return resultaat;
    }

    /**
     * voeg aan de context toe indien de relatieHisVolledig-type gelijk is aan het relatieBericht-type
     *  @param context de bijhoudingberichtcontext
     * @param relatieBericht het relatiebericht
     * @param objectSleutelRelatie de objectsleutelrelatie
     * @param relatieHisVolledig de relatiehisvolledig
     */
    private Set<ResultaatMelding> verrijkContextVoorRelatieHisVolledig(final BijhoudingBerichtContext context, final RelatieBericht relatieBericht,
                                                      final String objectSleutelRelatie, final RelatieHisVolledig relatieHisVolledig)
    {
        final Set<ResultaatMelding> meldingen = new HashSet<>();
        if (relatieHisVolledig.getSoort().getWaarde() == relatieBericht.getSoort().getWaarde()) {
            context.voegBestaandBijhoudingsRootObjectToe(objectSleutelRelatie, relatieHisVolledig);
        } else {
            LOGGER.info("Foutieve soort relatie gevonden voor technische sleutel '{}'. "
                            + "Verwacht '{}', gevonden '{}'.", objectSleutelRelatie,
                    relatieBericht.getSoort().getWaarde(), relatieHisVolledig.getSoort().getWaarde()
            );
            meldingen.add(maakMeldingVoorNietGevondenRootobject(relatieBericht, RELATIE));
        }
        return meldingen;
    }

    /**
     * Voeg een melding toe aan resultaat dat aangeeft dat een rootobject niet in het systeem voorkomt.
     * @param berichtRootObject het niet gevonden rootobject
     * @param attribuutNaam     attribuut naam in het bericht
     * @return de resultaatmelding
     */
    private ResultaatMelding maakMeldingVoorNietGevondenRootobject(final BerichtRootObject berichtRootObject,
                                                          final String attribuutNaam)
    {
        return ResultaatMelding.builder()
            .withSoort(SoortMelding.FOUT)
            .withRegel(Regel.BRBY0014)
            .withMeldingTekst(Regel.BRBY0014.getOmschrijving().replace(REGEL_OMSCHRIJVING_VARIABELE, berichtRootObject.getObjectSleutel()))
            .withAttribuutNaam(attribuutNaam)
            .withReferentieID(berichtRootObject.getCommunicatieID())
            .build();
    }
}
