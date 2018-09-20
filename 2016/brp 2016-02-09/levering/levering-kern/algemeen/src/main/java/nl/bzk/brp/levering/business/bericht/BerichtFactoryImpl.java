/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bericht;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.levering.business.bepalers.SoortSynchronisatieBepaler;
import nl.bzk.brp.levering.business.toegang.populatie.PersoonViewFactory;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicatiesAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.ber.MeldingBericht;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.springframework.stereotype.Component;


/**
 * Handelt diensten af met bericht.
 *
 * @brp.bedrijfsregel R1994
 */
@Regels(Regel.R1994)
@Component
public class BerichtFactoryImpl implements BerichtFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final int PARTIJ_CODE_BRP = 199903;

    private static final String PERSOON_HEEFT_MELDING = "Persoon met id [{}] heeft melding: {} - {}";

    @Inject
    private PartijService partijService;

    @Inject
    private PersoonViewFactory persoonViewFactory;

    @Inject
    private SoortSynchronisatieBepaler soortSynchronisatieBepaler;

    /**
     * Maakt een vul- en mutatiebericht die eventueel gevuld gaan worden. Dit hangt af van de persoon, de leveringsautorisatie en de populatie.
     *
     * @param persoonHisVolledigViews       de persoon his volledig views
     * @param leveringAutorisatie           de leveringsautorisatie
     * @param populatieMap                  de populatie map
     * @param administratieveHandelingModel het administratieve handeling model
     * @return lijst met synchronisatieberichten
     * @brp.bedrijfsregel VR00056
     * @brp.bedrijfsregel VR00057
     * @brp.bedrijfsregel VR00126
     */
    @Regels({ Regel.VR00056, Regel.VR00057, Regel.VR00126 })
    @Override
    public final List<SynchronisatieBericht> maakBerichten(final List<PersoonHisVolledigView> persoonHisVolledigViews,
        final Leveringinformatie leveringAutorisatie, final Map<Integer, Populatie> populatieMap,
        final AdministratieveHandelingModel administratieveHandelingModel)
    {
        final List<SynchronisatieBericht> leveringBerichten = new ArrayList<>();
        final SoortDienst soortDienst = leveringAutorisatie.getSoortDienst();

        final MutatieBericht mutatieBericht = new MutatieBericht(new AdministratieveHandelingSynchronisatie(administratieveHandelingModel));
        // De verwerkingssoort is altijd T, omdat de administratieve handeling altijd is toegevoegd in de leveren mutaties context.
        mutatieBericht.getAdministratieveHandeling().setVerwerkingssoort(Verwerkingssoort.TOEVOEGING);
        final VolledigBericht volledigBericht = new VolledigBericht(new AdministratieveHandelingSynchronisatie(administratieveHandelingModel));

        for (final PersoonHisVolledigView persoonHisVolledigView : persoonHisVolledigViews) {
            final Populatie populatie = populatieMap.get(persoonHisVolledigView.getID());
            final SoortSynchronisatie soortSynchronisatie =
                soortSynchronisatieBepaler.bepaalSoortSynchronisatie(populatie, soortDienst, administratieveHandelingModel.getSoort().getWaarde());

            final int viewIndex;

            final SynchronisatieBericht synchronisatieBericht;
            switch (soortSynchronisatie) {
                case VOLLEDIGBERICHT:
                    synchronisatieBericht = volledigBericht;
                    break;
                case MUTATIEBERICHT:
                    synchronisatieBericht = mutatieBericht;
                    break;
                default:
                    LOGGER.error(FunctioneleMelding.LEVERING_ONGELDIGE_SOORT_SYNCHRONISATIE,
                        "De soort synchronisatie wordt niet ondersteund.");
                    continue;
            }

            viewIndex = synchronisatieBericht.addPersoon(persoonHisVolledigView);
            persoonHisVolledigView.setCommunicatieID(Integer.toString(viewIndex));

            // evt. een melding toevoegen.
            voegEventueleMeldingenToe(synchronisatieBericht, soortDienst, populatie, persoonHisVolledigView);
            voegEventueleMeldingVerstrekkingsbeperkingToe(synchronisatieBericht, persoonHisVolledigView.getPersoon(),
                administratieveHandelingModel.getTijdstipRegistratie(),
                persoonHisVolledigView.getCommunicatieID());
        }

        // Gemaakte berichten toevoegen, !Belangrijk, volgorde is niet willekeurig
        if (volledigBericht.heeftPersonen()) {
            leveringBerichten.add(volledigBericht);
        }
        if (mutatieBericht.heeftPersonen()) {
            leveringBerichten.add(mutatieBericht);
        }

        return leveringBerichten;
    }

    @Override
    public final VolledigBericht maakVolledigBericht(final PersoonHisVolledig persoon,
        final Leveringinformatie leveringAutorisatie,
        final AdministratieveHandelingModel administratieveHandeling, final DatumAttribuut materieelVanaf)
    {
        final VolledigBericht volledigBericht =
            new VolledigBericht(new AdministratieveHandelingSynchronisatie(administratieveHandeling));
        final PersoonHisVolledigView view =
            persoonViewFactory.maakMaterieleHistorieView(persoon, leveringAutorisatie, administratieveHandeling, materieelVanaf);

        final int viewIndex = volledigBericht.addPersoon(view);
        view.setCommunicatieID(Integer.toString(viewIndex));

        // evt. een melding toevoegen
        voegEventueleMeldingVerstrekkingsbeperkingToe(volledigBericht, persoon, administratieveHandeling.getTijdstipRegistratie(),
            view.getCommunicatieID());
        return volledigBericht;
    }

    /**
     * Voegt eventuele meldingen toe afhankelijk van de populatie.
     *
     * @param synchronisatieBericht het synchronisatiebericht
     * @param soortDienst           de categorie dienst
     * @param populatie             populatie waartoe persoon behoort
     * @param view                  view op de persoon
     */
    @Regels({ Regel.BRLV0027, Regel.BRLV0028, Regel.VR00071 })
    private void voegEventueleMeldingenToe(final SynchronisatieBericht synchronisatieBericht,
        final SoortDienst soortDienst, final Populatie populatie, final PersoonHisVolledigView view)
    {
        if (SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE == soortDienst
            && (Populatie.VERLAAT == populatie || Populatie.BUITEN == populatie))
        {
            synchronisatieBericht.addMelding(maakMelding(view.getCommunicatieID(), Regel.BRLV0027));
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0027, PERSOON_HEEFT_MELDING, view.getID(),
                Regel.BRLV0027, Regel.BRLV0027.getOmschrijving());
        } else if (Populatie.VERLAAT == populatie) {
            synchronisatieBericht.addMelding(maakMelding(view.getCommunicatieID(), Regel.BRLV0028));
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0028, PERSOON_HEEFT_MELDING, view.getID(),
                Regel.BRLV0028, Regel.BRLV0028.getOmschrijving());
        }
    }

    /**
     * Voegt indien nodig een melding toe dat de betreffende persoon een verstrekkingsbeperking heeft.
     *
     * @param synchronisatieBericht het synchronisatiebericht
     * @param persoon               de betreffende persoon
     * @param peilmoment            peilmoment waarop we kijken naar de persoon
     * @param communicatieId        communicatie ID van de persoon in het bericht
     */
    @Regels(Regel.BRLV0032)
    private void voegEventueleMeldingVerstrekkingsbeperkingToe(
        final SynchronisatieBericht synchronisatieBericht, final PersoonHisVolledig persoon,
        final DatumTijdAttribuut peilmoment, final String communicatieId)
    {
        final PersoonView view = new PersoonView(persoon, peilmoment);

        if (view.heeftVerstrekkingsbeperking()) {
            synchronisatieBericht.addMelding(maakMelding(communicatieId, Regel.BRLV0032));
        }
    }

    /**
     * Maakt een melding op basis van een communicatie id en een regel.
     *
     * @param communicatieId communicatie id
     * @param regel          regel waarvan melding wordt gegeven.
     * @return melding bericht
     */
    private MeldingBericht maakMelding(final String communicatieId, final Regel regel) {
        final MeldingBericht melding = new MeldingBericht();

        melding.setReferentieID(communicatieId);
        melding.setSoort(new SoortMeldingAttribuut(SoortMelding.WAARSCHUWING));
        melding.setRegel(new RegelAttribuut(regel));
        melding.setMelding(new MeldingtekstAttribuut(regel.getOmschrijving()));

        return melding;
    }

    @Override
    public final BerichtStuurgegevensGroepBericht maakStuurgegevens(final Partij ontvangendePartij) {
        return maakStuurgegevens(ontvangendePartij, null);
    }

    /**
     * Maakt de stuurgegevens voor een bericht.
     *
     * @param ontvangendePartij de ontvangende partij
     * @param crossReferentie   de cross referentie
     * @return het bericht stuurgegevens groep bericht
     * @brp.bedrijfsregel VR00051
     */
    @Regels(Regel.VR00051)
    @Override
    public final BerichtStuurgegevensGroepBericht maakStuurgegevens(final Partij ontvangendePartij,
        final String crossReferentie)
    {
        final PartijAttribuut zendendePartij = haalPartijBrpOp();
        final SysteemNaamAttribuut zendendeSysteem = new SysteemNaamAttribuut("BRP");
        final PartijAttribuut ontvangendePartijAttribuut = new PartijAttribuut(ontvangendePartij);
        final SysteemNaamAttribuut ontvangendeSysteem = new SysteemNaamAttribuut("Leveringsysteem");
        final ReferentienummerAttribuut referentienummer = new ReferentienummerAttribuut(UUID.randomUUID().toString());
        final DatumTijdAttribuut datumTijdVerzending = new DatumTijdAttribuut(new Date());
        ReferentienummerAttribuut crossReferentienummer = null;

        if (crossReferentie != null) {
            crossReferentienummer = new ReferentienummerAttribuut(crossReferentie);
        }

        final BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
        stuurgegevens.setZendendePartij(zendendePartij);
        stuurgegevens.setZendendeSysteem(zendendeSysteem);
        stuurgegevens.setOntvangendePartij(ontvangendePartijAttribuut);
        stuurgegevens.setOntvangendeSysteem(ontvangendeSysteem);
        stuurgegevens.setReferentienummer(referentienummer);
        stuurgegevens.setDatumTijdVerzending(datumTijdVerzending);
        stuurgegevens.setDatumTijdOntvangst(null);
        stuurgegevens.setCrossReferentienummer(crossReferentienummer);

        return stuurgegevens;
    }

    @Override
    public final BerichtParametersGroepBericht maakParameters(final Leveringinformatie leveringAutorisatie,
        final SoortSynchronisatie soortSynchronisatie)
    {
        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setDienstID(leveringAutorisatie.getDienst().getID().toString());
        parameters.setLeveringsautorisatieID(leveringAutorisatie.getToegangLeveringsautorisatie().getLeveringsautorisatie().getID().toString());
        parameters.setSoortSynchronisatie(new SoortSynchronisatieAttribuut(soortSynchronisatie));

        final EffectAfnemerindicaties effectAfnemerindicaties =
            leveringAutorisatie.getDienst().getEffectAfnemerindicaties();
        if (effectAfnemerindicaties != null) {
            parameters.setEffectAfnemerindicatie(new EffectAfnemerindicatiesAttribuut(effectAfnemerindicaties));
        }

        return parameters;
    }

    /**
     * Haalt de partij voor de BRP op. Hoewel dit uit de repository komt zou dit door de second-level cache opgevangen moeten worden.
     *
     * @return De partij.
     */
    private PartijAttribuut haalPartijBrpOp() {
        return new PartijAttribuut(partijService.vindPartijOpCode(PARTIJ_CODE_BRP));
    }
}
