/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.actie.ActieFactory;
import nl.bzk.brp.bijhouding.business.actie.ActieUitvoerder;
import nl.bzk.brp.bijhouding.business.regels.actie.BijhoudingRegelService;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.bijhouding.business.util.Geldigheidsperiode;
import nl.bzk.brp.business.regels.NaActieRegel;
import nl.bzk.brp.business.regels.VoorActieRegel;
import nl.bzk.brp.business.regels.VoorActieRegelMetHistorischBesef;
import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.dataaccess.repository.AdministratieveHandelingRepository;
import nl.bzk.brp.hismodelattribuutaccess.HisModelAttribuutAccessAdministratie;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Deze stap heeft meerdere taken:
 * - Uitvoeren van de pre-actie-verwerking bedrijfsregels
 * - Verwerken van de acties zelf
 * - Uitvoeren van de post-actie-verwerking bedrijfsregels
 * <p/>
 * Dit is bewust in 1 stap gedaan, omdat deze taken per groepje acties met
 * dezelfde dag/deg wordt doorlopen. Dit is weer om geen checks te missen in 'tussensituaties'
 * die ontstaan door het verwerken van meerdere acties in 1 adm.hand. met verschillende dag/deg.
 */
@Component
public class BerichtVerwerkingStap {
    @Inject
    private ActieFactory                         actieFactory;
    @Inject
    private ActieRepository                      actieRepository;
    @Inject
    private AdministratieveHandelingRepository   administratieveHandelingRepository;
    @Inject
    private HisModelAttribuutAccessAdministratie attribuutAccessAdministratie;
    @Inject
    private VerantwoordingService                verantwoordingService;
    @Inject
    private MeldingFactory                       meldingFactory;
    @Inject
    private BijhoudingRegelService               bijhoudingRegelService;

    /**
     * Voert de stap uit.
     * @param bericht het bijhoudingsbericht
     * @param context de bijhoudingberichtcontext
     * @return meldinglijst
     */
    public Resultaat voerStapUit(final BijhoudingsBericht bericht, final BijhoudingBerichtContext context) {
        final Set<ResultaatMelding> resultaatMeldingen = new HashSet<>();

        // Sla als eerste de administratieve handeling op.
        final AdministratieveHandelingModel administratieveHandeling =
            this.slaAdministratieveHandelingOp(bericht);
        // Zet de administratieve handeling op de context, zodat deze later op de JMS queue gezet kan worden.
        context.setAdministratieveHandeling(administratieveHandeling);


        // Sla alle acties op
        final Map<ActieBericht, ActieModel> actieModelPerActieBericht =
            initialiseerActies(context, bericht,
                bericht.getAdministratieveHandeling().getActies(),
                administratieveHandeling);

        // Sla de documenten van de acties op
        final List<ResultaatMelding> verantwoordingMeldingen = verantwoordingService.slaActieVerantwoordingenOp(actieModelPerActieBericht,
            bericht.getAdministratieveHandeling(),
            context
        );
        resultaatMeldingen.addAll(verantwoordingMeldingen);

        final SortedMap<Geldigheidsperiode, List<ActieBericht>> actiesPerGeldigheidsperiode = verdeelActiesNaarGesorteerdeGeldigheidsperiode(
            bericht.getAdministratieveHandeling().getActies());

        //Uitvoer van de acties per geldigheidsperiode.
        for (final List<ActieBericht> acties : actiesPerGeldigheidsperiode.values()) {

            // Maak een persoonHisVolledig aan voor de niet ingeschreven personen en zet deze in de context.
            maakNietIngeschrevenenAan(acties, actieModelPerActieBericht, context);

            // Controleer de voor actie regels.
            resultaatMeldingen.addAll(controleerVoorActieRegels(acties, administratieveHandeling, context));

            // Alleen doorgaan met de verwerking als er geen verwerking verhinderende fout is opgetreden.
            if (!isVerwerkingstoppendeFoutOpgetreden(resultaatMeldingen)) {
                // Verwerk de acties.
                final List<Afleidingsregel> afleidingsRegels = verwerkActies(acties, actieModelPerActieBericht, context);

                // Verwerk de afleidingsregels.
                resultaatMeldingen.addAll(verwerkAfleidingRegels(afleidingsRegels, context));

                // Controleer na actie regels.
                resultaatMeldingen.addAll(controleerNaActieRegels(acties, administratieveHandeling, context));

            }

        }

        return new Resultaat.Builder().withMeldingen(resultaatMeldingen).build();
    }

    private boolean isVerwerkingstoppendeFoutOpgetreden(final Set<ResultaatMelding> resultaatMeldingen) {
        for (ResultaatMelding resultaatMelding : resultaatMeldingen) {
            if (resultaatMelding.isVerwerkingStoppend()) {
                return true;
            }
        }

        return false;
    }

    /**
     * package private ivm unittest.
     *
     * @param acties    acties
     * @return actiesPerGeldigheidsperiode
     */
    SortedMap<Geldigheidsperiode, List<ActieBericht>> verdeelActiesNaarGesorteerdeGeldigheidsperiode(final List<? extends ActieBericht> acties) {
        final SortedMap<Geldigheidsperiode, List<ActieBericht>> actiesPerGeldigheidsperiode = new TreeMap<>();

        for (final ActieBericht actie : acties) {
            final Geldigheidsperiode geldigheidsperiode = new Geldigheidsperiode(actie);

            List<ActieBericht> geldigheidsperiodeActies = actiesPerGeldigheidsperiode.get(geldigheidsperiode);
            if (geldigheidsperiodeActies == null) {
                geldigheidsperiodeActies = new ArrayList<>();
                actiesPerGeldigheidsperiode.put(geldigheidsperiode, geldigheidsperiodeActies);
            }

            geldigheidsperiodeActies.add(actie);
        }

        return actiesPerGeldigheidsperiode;
    }

    /**
     * Sla de administratieve handeling op.
     *
     * @param bericht het bericht
     * @return de opgeslagen administratieve handeling
     */
    private AdministratieveHandelingModel slaAdministratieveHandelingOp(
        final Bericht bericht)
    {
        final AdministratieveHandelingBericht administratieveHandelingBericht = getAdministratieveHandelingBericht(bericht);
        final AdministratieveHandelingModel administratieveHandelingModel =
                administratieveHandelingRepository.opslaanNieuwAdministratieveHandeling(
                        administratieveHandelingBericht);

        // Zet de technische sleutel van het model op de administratieve handeling in het bericht,
        // omdat we deze terug moeten communiceren in het antwoord bericht.
        administratieveHandelingBericht.setObjectSleutel(administratieveHandelingModel.getObjectSleutel());
        return administratieveHandelingModel;
    }

    private AdministratieveHandelingBericht getAdministratieveHandelingBericht(final Bericht bericht) {
        final BerichtStandaardGroepBericht berichtStandaardGroep = (BerichtStandaardGroepBericht) bericht.getStandaard();
        return berichtStandaardGroep.getAdministratieveHandeling();
    }

    /**
     * Sla de actie op.
     *
     * @param actie        het actie bericht
     * @param admHandeling de (al opgeslagen) administratieve handeling
     * @return de opgeslagen actie
     */
    private ActieModel slaActieOp(final ActieBericht actie, final AdministratieveHandelingModel admHandeling) {
        final ActieModel actieModel = actieRepository.opslaanNieuwActie(new ActieModel(actie, admHandeling));
        // Zet de terug referentie van de bi-directionele associatie omdat deze later nodig is.
        admHandeling.getActies().add(actieModel);
        return actieModel;
    }

    /**
     * Controleert alle bij de opgegeven acties behorende voor-actie-verwerking regels.
     *  @param acties                           de lijst van acties waarvoor de regels gecontroleerd dienen te worden.
     * @param administratieveHandeling         de administratieve handeling waartoe de acties behoren.
     * @param context                          de BijhoudingBerichtContext waarbinnen de acties worden verwerkt.
     */
    private Set<ResultaatMelding> controleerVoorActieRegels(final List<ActieBericht> acties,
        final AdministratieveHandelingModel administratieveHandeling, final BijhoudingBerichtContext context)
    {
        final Set<ResultaatMelding> meldingen = new HashSet<>();

        for (final ActieBericht actie : acties) {
            final Map<String, PersoonView> bestaandeBetrokkenen = getBestaandeBetrokkenPersonen(context);
            final BerichtRootObject berichtRootObject = actie.getRootObject();

            this.attribuutAccessAdministratie.activeer();

            // Verzamel alle regels die voor de uitvoer van dit groepje acties gedraaid moeten worden
            // en voer deze allemaal uit.
            final List<? extends VoorActieRegel> voorActieRegels =
                    bijhoudingRegelService.getVoorActieRegels(administratieveHandeling.getSoort().getWaarde(), actie.getSoort().getWaarde());
            for (final VoorActieRegel<RootObject, BerichtRootObject> regel : voorActieRegels) {
                this.attribuutAccessAdministratie.setHuidigeRegel(regel.getRegel());
                final RootObject rootObject = bepaalRootObject(context, berichtRootObject, regel);
                final List<? extends BerichtIdentificeerbaar> overtredendeObjecten = regel.voerRegelUit(rootObject, berichtRootObject, actie,
                    bestaandeBetrokkenen);
                for (BerichtIdentificeerbaar overtreder : overtredendeObjecten) {
                    meldingen.add(meldingFactory.maakResultaatMelding(regel.getRegel(), overtreder, null));
                }
            }
            this.attribuutAccessAdministratie.deactiveer();
        }
        return meldingen;
    }

    private RootObject bepaalRootObject(final BijhoudingBerichtContext context, final BerichtRootObject berichtRootObject, final VoorActieRegel regel) {
        final RootObject rootObject;
        if (regel instanceof VoorActieRegelMetHistorischBesef) {
            // In het geval van historisch besef geven we een his volledig root object mee.
            rootObject = getHisVolledigRootObjectUitContext(context, berichtRootObject);
        } else {
            // Zonder historisch besef maken we een tijd-slice van het root object.
            rootObject = getRootObjectSliceUitContext(context, berichtRootObject);
        }
        return rootObject;
    }

    /**
     * Retourneert een {@link Map} waarin identificerende sleutels zoals gebruikt in het bericht worden gemapped naar
     * de personen uit het systeem. Neemt alleen de al bestaande personen in de database.
     *
     * @param context de context waarin de berichtverwerking plaatsvindt.
     * @return een map met technische sleutels gemapped naar personen.
     */
    private Map<String, PersoonView> getBestaandeBetrokkenPersonen(final BijhoudingBerichtContext context) {
        final Map<String, PersoonView> betrokkenen = new HashMap<>();
        for (final Map.Entry<String, HisVolledigRootObject> entry
                : context.getBestaandeBijhoudingsRootObjecten().entrySet())
        {
            if (entry.getValue() instanceof PersoonHisVolledig) {
                betrokkenen.put(entry.getKey(), new PersoonView((PersoonHisVolledig) entry.getValue()));
            }
        }

        return betrokkenen;
    }

    private List<Afleidingsregel> verwerkActies(final List<ActieBericht> acties,
            final Map<ActieBericht, ActieModel> actieModelPerActieBericht, final BijhoudingBerichtContext context)
    {
        final List<Afleidingsregel> afleidingsRegels = new ArrayList<>();
        for (final ActieBericht actieBericht : acties) {
            afleidingsRegels.addAll(voerActieUit(actieBericht, actieModelPerActieBericht.get(actieBericht), context));
        }
        return afleidingsRegels;
    }

    /**
     * Voer de meegegeven actie uit.
     *
     * @param actieBericht    de actie uit het bericht
     * @param actieModel de opgeslagen actie
     * @param context         de context
     * @return de afleidingsregels voor deze actie
     */
    private List<Afleidingsregel> voerActieUit(final ActieBericht actieBericht,
            final ActieModel actieModel, final BijhoudingBerichtContext context)
    {
        final List<Afleidingsregel> afleidingsRegels = new ArrayList<>();
        final ActieUitvoerder<ActieBericht> uitvoerder = this.maakActieUitvoerderAan(actieBericht, actieModel, context);

        afleidingsRegels.addAll(uitvoerder.voerActieUit());
        return afleidingsRegels;
    }

    /**
     * Maak een actie uitvoerder aan. Zet daarbij tevens alle verwachte velden op de juiste waarde.
     *
     * @param actieBericht    het actie bericht
     * @param actieModel de opgeslagen actie
     * @param context         de context
     * @return de actie uitvoerder
     */
    // Generiek gebruik van parameterized types, dus verwachte warnings.
    private ActieUitvoerder<ActieBericht> maakActieUitvoerderAan(final ActieBericht actieBericht,
            final ActieModel actieModel, final BijhoudingBerichtContext context)
    {
        final ActieUitvoerder<ActieBericht> uitvoerder = actieFactory.getActieUitvoerder(
            actieModel.getAdministratieveHandeling().getSoort().getWaarde(),
            actieModel.getSoort().getWaarde());
        uitvoerder.setActieBericht(actieBericht);
        uitvoerder.setActieModel(actieModel);
        uitvoerder.setContext(context);
        return uitvoerder;
    }

    /**
     * Controleert alle bij de opgegeven acties behorende post-actie-verwerking regels, waarbij eventuele meldingen
     * worden toegevoegd aan de lijst van opgetreden meldingen in het {@link nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat}.
     *
     * @param acties                           de lijst van acties waarvoor de regels gecontroleerd dienen te worden.
     * @param administratieveHandeling         de administratieve handeling waartoe de acties behoren.
     * @param context                          de BijhoudingBerichtContext waarbinnen de acties worden verwerkt.
     * @return de lijst met meldingen
     */
    private Set<ResultaatMelding> controleerNaActieRegels(final List<ActieBericht> acties,
        final AdministratieveHandelingModel administratieveHandeling, final BijhoudingBerichtContext context)
    {
        final Set<ResultaatMelding> meldingen = new HashSet<>();

        for (final ActieBericht actie : acties) {
            final BerichtRootObject berichtRootObject = actie.getRootObject();

            this.attribuutAccessAdministratie.activeer();

            // Verzamel alle regels die na de uitvoer van dit groepje acties gedraaid moeten worden
            // en voer deze allemaal uit.
            final List<? extends NaActieRegel> naActieRegels =
                    bijhoudingRegelService.getNaActieRegels(
                        administratieveHandeling.getSoort().getWaarde(), actie.getSoort().getWaarde());
            for (final NaActieRegel<RootObject, BerichtRootObject> regel : naActieRegels) {
                this.attribuutAccessAdministratie.setHuidigeRegel(regel.getRegel());
                final RootObject rootObject = getRootObjectSliceUitContext(context, berichtRootObject);
                final List<BerichtEntiteit> overtredendeObjecten = regel.voerRegelUit(rootObject, berichtRootObject);
                for (BerichtEntiteit overtreder : overtredendeObjecten) {
                    meldingen.add(meldingFactory.maakResultaatMelding(regel.getRegel(), overtreder, null));
                }
            }
            this.attribuutAccessAdministratie.deactiveer();
        }

        return meldingen;
    }

    private List<ResultaatMelding> verwerkAfleidingRegels(final List<Afleidingsregel> afleidingsRegels,
        final BijhoudingBerichtContext context)
    {
        final List<AfleidingResultaat> afleidingResultaten = bepaalAfleidingsResultaten(afleidingsRegels);
        voegExtraBijgehoudenPersonenToeAanContext(context,
            bepaalExtraBijgehoudenPersonen(afleidingsRegels));

        voegExtraAangemaaktePersonenToeAanContext(context,
            bepaalExtraAangemaaktePersonen(afleidingsRegels));

        final List<ResultaatMelding> meldingen = new ArrayList<>();
        meldingen.addAll(bepaalMeldingen(afleidingResultaten));

        final List<Afleidingsregel> vervolgAfleidingsregels = bepaalVervolgAfleidingsRegels(afleidingResultaten);
        if (!vervolgAfleidingsregels.isEmpty()) {
            // recursief vervolgafleidingsregels verwerken
            final List<ResultaatMelding> vervolgMeldingen = verwerkAfleidingRegels(vervolgAfleidingsregels, context);
            meldingen.addAll(vervolgMeldingen);
        }
        return meldingen;
    }

    private List<Afleidingsregel> bepaalVervolgAfleidingsRegels(final List<AfleidingResultaat> afleidingResultaten) {
        final List<Afleidingsregel> vervolgAfleidingsRegels = new ArrayList<>();
        for (AfleidingResultaat afleidingResultaat : afleidingResultaten) {
            vervolgAfleidingsRegels.addAll(afleidingResultaat.getVervolgAfleidingen());
        }
        return vervolgAfleidingsRegels;
    }

    private List<ResultaatMelding> bepaalMeldingen(final List<AfleidingResultaat> afleidingResultaten) {
        final List<ResultaatMelding> afleidingMeldingen = new ArrayList<>();
        for (AfleidingResultaat afleidingResultaat : afleidingResultaten) {
            afleidingMeldingen.addAll(afleidingResultaat.getMeldingen());
        }
        return afleidingMeldingen;
    }

    private List<AfleidingResultaat> bepaalAfleidingsResultaten(final List<Afleidingsregel> huidigeAfleidingsRegels) {
        final List<AfleidingResultaat> afleidingResultaten = new ArrayList<>();
        for (final Afleidingsregel afleidingsRegel : huidigeAfleidingsRegels) {
            final AfleidingResultaat afleidingResultaat = afleidingsRegel.leidAf();
            afleidingResultaten.add(afleidingResultaat);
        }
        return afleidingResultaten;
    }

    private void voegExtraBijgehoudenPersonenToeAanContext(final BijhoudingBerichtContext context,
        final List<PersoonHisVolledigImpl> personen)
    {
        for (PersoonHisVolledigImpl persoon : personen) {
            context.voegNietInBerichtMaarWelBijgehoudenPersoonToe(persoon);
        }
    }

    private void voegExtraAangemaaktePersonenToeAanContext(final BijhoudingBerichtContext context,
        final List<PersoonHisVolledigImpl> personen)
    {
        for (PersoonHisVolledigImpl persoon : personen) {
            context.voegNietInBerichtMaarWelAangemaakteNietIngeschreveneToe(persoon);
        }
    }

    private List<PersoonHisVolledigImpl> bepaalExtraBijgehoudenPersonen(final List<Afleidingsregel> huidigeAfleidingsRegels) {
        final List<PersoonHisVolledigImpl> personen = new ArrayList<>();
        for (Afleidingsregel afleidingsregel : huidigeAfleidingsRegels) {
            for (PersoonHisVolledigImpl persoonHisVolledig : afleidingsregel.getExtraBijgehoudenPersonen()) {
                personen.add(persoonHisVolledig);
            }
        }
        return personen;
    }

    private List<PersoonHisVolledigImpl> bepaalExtraAangemaaktePersonen(final List<Afleidingsregel> huidigeAfleidingsRegels) {
        final List<PersoonHisVolledigImpl> personen = new ArrayList<>();
        for (Afleidingsregel afleidingsregel : huidigeAfleidingsRegels) {
            for (PersoonHisVolledigImpl persoonHisVolledig : afleidingsregel.getExtraAangemaakteNietIngeschrevenen()) {
                personen.add(persoonHisVolledig);
            }
        }
        return personen;
    }

    /**
     * Haalt de Model versie van het RootObject van de opgegeven actie uit de context en retourneert deze.
     * Het gaat hier om een slice in de tijd van het his volledig root object.
     *
     * @param context           de context met daarin alle root objecten in Model versie.
     * @param berichtRootObject bericht variant van het root object waarvoor de Model variant gezocht wordt.
     * @return de model variant van het bericht root object.
     */
    private RootObject getRootObjectSliceUitContext(
            final BijhoudingBerichtContext context, final BerichtRootObject berichtRootObject)
    {
        final HisVolledigRootObject hisVolledigResultaat = getHisVolledigRootObjectUitContext(context, berichtRootObject);

        // Het is mogelijk dat er nu nog steeds geen root object is gevonden, namelijk als
        // dit een actie is die een nieuw root object aanmaakt en de actie verwerker nog niet
        // heeft gerund (oftewel, we zijn bij voor actie regel checks). Dan is het veilig om
        // een null terug te geven, want de regels die daarop werken zullen niks (kunnen) doen
        // met een huidige situatie root object.
        RootObject rootObject = null;
        if (hisVolledigResultaat != null) {
            // Slice op 'nu', zodat de regels een momentopname krijgen om hun checks op te doen.
            rootObject = bouwViewVariantVanHisVolledig(hisVolledigResultaat);
        }
        return rootObject;
    }

    /**
     * Haalt de volledige historie van het RootObject van de opgegeven actie uit de context en retourneert deze.
     *
     * @param context           de context met daarin alle root objecten in Model versie.
     * @param berichtRootObject bericht variant van het root object waarvoor de Model variant gezocht wordt.
     * @return de model variant van het bericht root object.
     */
    private HisVolledigRootObject getHisVolledigRootObjectUitContext(
            final BijhoudingBerichtContext context, final BerichtRootObject berichtRootObject)
    {
        return context.zoekHisVolledigRootObject(berichtRootObject);
    }

    /**
     * Bouwt een 'view' met als peilmoment 'nu' op voor de opgegeven HisVolledig instantie van een Root Object.
     *
     * @param hisVolledigRootObject de HisVolledig instantie waarvoor de view moet worden gebouwd.
     * @return een 'view' op de opgegeven HisVolledig instantie.
     */
    private RootObject bouwViewVariantVanHisVolledig(final HisVolledigRootObject hisVolledigRootObject) {
        final RootObject rootObject;
        if (hisVolledigRootObject instanceof PersoonHisVolledig) {
            rootObject = new PersoonView((PersoonHisVolledig) hisVolledigRootObject);
        } else if (hisVolledigRootObject instanceof RelatieHisVolledig) {
            final RelatieHisVolledig relatieHisVolledig = (RelatieHisVolledig) hisVolledigRootObject;
            switch (relatieHisVolledig.getSoort().getWaarde()) {
                case HUWELIJK:
                case GEREGISTREERD_PARTNERSCHAP:
                    rootObject = new HuwelijkGeregistreerdPartnerschapView(
                            (HuwelijkGeregistreerdPartnerschapHisVolledig) relatieHisVolledig,
                            DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());
                    break;
                case FAMILIERECHTELIJKE_BETREKKING:
                    rootObject = new FamilierechtelijkeBetrekkingView(
                            (FamilierechtelijkeBetrekkingHisVolledig) relatieHisVolledig,
                            DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());
                    break;
                default:
                    throw new IllegalArgumentException(
                            String.format("Onbekende relatie soort voor HisVolledigRootobject: %s",
                                    relatieHisVolledig.getSoort())
                    );
            }
        } else {
            throw new IllegalArgumentException(String.format("Onbekend RootObject type: %s",
                    hisVolledigRootObject.getClass().getName()));
        }
        return rootObject;
    }

    /**
     * Maakt voor de niet-ingeschreven personen een PersoonHisVolledig
     * en zet deze in de bericht context.
     *
     * @param actieBerichten   de acties met de personen
     * @param actieModelPerActieBericht persistente acties
     * @param context          bericht context
     */
    private void maakNietIngeschrevenenAan(final List<ActieBericht> actieBerichten,
                                           final Map<ActieBericht, ActieModel> actieModelPerActieBericht,
                                           final BijhoudingBerichtContext context)
    {

        for (final ActieBericht actieBericht : actieBerichten) {
            final RootObject rootObject = actieBericht.getRootObject();
            if (rootObject instanceof RelatieBericht) {
                final RelatieBericht relatieBericht = (RelatieBericht) rootObject;
                for (final BetrokkenheidBericht betrokkene : relatieBericht.getBetrokkenheden()) {
                    final PersoonBericht persoonBericht = betrokkene.getPersoon();
                    maakNietIngeschreveneAan(actieModelPerActieBericht, context, actieBericht, persoonBericht);
                }
            } else if (rootObject instanceof PersoonBericht) {
                final PersoonBericht persoonBericht = (PersoonBericht) rootObject;
                maakNietIngeschreveneAan(actieModelPerActieBericht, context, actieBericht, persoonBericht);
            }
        }
    }

    private void maakNietIngeschreveneAan(final Map<ActieBericht, ActieModel> actieModelPerActieBericht,
                                          final BijhoudingBerichtContext context, final ActieBericht actieBericht,
                                          final PersoonBericht persoonBericht)
    {
        // Alleen aanmaken als het geen refererend object is en het een niet ingeschrevene betreft.
        if (StringUtils.isBlank(persoonBericht.getReferentieID())
                && persoonBericht.getSoort().getWaarde() == SoortPersoon.NIET_INGESCHREVENE)
        {
            context.voegAangemaaktBijhoudingsRootObjectToe(persoonBericht.getCommunicatieID(),
                    maakPersoonHisVolledigVoorNietIngeschrevene(
                        persoonBericht, actieModelPerActieBericht.get(actieBericht))
            );
        }
    }

    /**
     * Deze methode kopieert de groepen uit het persoonBericht naar PersoonHisVolledig.
     *
     * @param persoonBericht  het persoon het uit bericht
     * @param persistentActie de persistenteActie
     * @return PersoonHisVolledig
     */
    private PersoonHisVolledig maakPersoonHisVolledigVoorNietIngeschrevene(final PersoonBericht persoonBericht,
                                                                           final ActieModel persistentActie)
    {
        final PersoonHisVolledig persoonHisVolledig = new PersoonHisVolledigImpl(persoonBericht.getSoort());

        if (persoonBericht.getIdentificatienummers() != null) {
            final HisPersoonIdentificatienummersModel identificatienummmers =
                    new HisPersoonIdentificatienummersModel(persoonHisVolledig,
                            persoonBericht.getIdentificatienummers(),
                            persoonBericht.getIdentificatienummers(),
                            persistentActie);
            persoonHisVolledig.getPersoonIdentificatienummersHistorie().voegToe(identificatienummmers);
        }

        if (persoonBericht.getGeboorte() != null) {
            final HisPersoonGeboorteModel geboorte = new HisPersoonGeboorteModel(persoonHisVolledig,
                    persoonBericht.getGeboorte(),
                    persistentActie);
            persoonHisVolledig.getPersoonGeboorteHistorie().voegToe(geboorte);
        }

        if (persoonBericht.getGeslachtsaanduiding() != null) {
            final HisPersoonGeslachtsaanduidingModel geslachtsaanduiding =
                    new HisPersoonGeslachtsaanduidingModel(persoonHisVolledig,
                            persoonBericht.getGeslachtsaanduiding(),
                            persoonBericht.getGeslachtsaanduiding(),
                            persistentActie);
            persoonHisVolledig.getPersoonGeslachtsaanduidingHistorie().voegToe(geslachtsaanduiding);
        }

        if (persoonBericht.getSamengesteldeNaam() != null) {
            persoonBericht.getSamengesteldeNaam().setIndicatieAfgeleid(JaNeeAttribuut.NEE);

            final HisPersoonSamengesteldeNaamModel samengesteldeNaam =
                    new HisPersoonSamengesteldeNaamModel(persoonHisVolledig, persoonBericht.getSamengesteldeNaam(),
                            persoonBericht.getSamengesteldeNaam(), persistentActie);
            persoonHisVolledig.getPersoonSamengesteldeNaamHistorie().voegToe(samengesteldeNaam);
        }

        return persoonHisVolledig;
    }

    /**
     * Slaat de acties op en stopt het in een map.
     *
     * @param context                  bericht context
     * @param bericht                  bericht
     * @param acties                   acties
     * @param administratieveHandeling administratieveHandeling
     * @return map met de persistente  acties
     */
    private Map<ActieBericht, ActieModel> initialiseerActies(final BijhoudingBerichtContext context,
            final BerichtBericht bericht, final List<ActieBericht> acties,
            final AdministratieveHandelingModel administratieveHandeling)
    {
        final Map<ActieBericht, ActieModel> actiePerBericht = new HashMap<>();

        for (final ActieBericht actieBericht : acties) {
            final ActieModel actieModel = this.slaActieOp(actieBericht, administratieveHandeling);
            actiePerBericht.put(actieBericht, actieModel);
            // Sla de actie mapping ook op in de context, voor latere verwerking.
            context.voegActieMappingToe(actieBericht, actieModel);
            // Zet ook de link van de actie bericht naar administratieve handeling bericht, voor gebruik in de regels.
            actieBericht.setAdministratieveHandeling(bericht.getAdministratieveHandeling());
        }

        return actiePerBericht;
    }
}
