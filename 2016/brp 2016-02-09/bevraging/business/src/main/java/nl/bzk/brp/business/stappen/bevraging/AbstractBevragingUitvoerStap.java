/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.Stap;
import nl.bzk.brp.business.util.BevragingUtil;
import nl.bzk.brp.dataaccess.exceptie.NietUniekeAnummerExceptie;
import nl.bzk.brp.dataaccess.exceptie.NietUniekeBsnExceptie;
import nl.bzk.brp.dataaccess.exceptie.PersoonNietGevondenExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardetekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.HistorievormAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.BevragingHistoriePredikaat;
import nl.bzk.brp.model.hisvolledig.predikaat.HuidigeSituatiePredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.PersoonHisVolledigViewUtil;
import nl.bzk.brp.webservice.business.service.ObjectSleutelPartijInvalideExceptie;
import nl.bzk.brp.webservice.business.service.ObjectSleutelService;
import nl.bzk.brp.webservice.business.service.ObjectSleutelVerlopenExceptie;
import nl.bzk.brp.webservice.business.service.OngeldigeObjectSleutelExceptie;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;


/**
 * Uitvoer stap die het opvragen van een persoon afhandelt. De persoon wordt via de DAL laag opgehaald.
 */
public abstract class AbstractBevragingUitvoerStap extends
    AbstractBerichtVerwerkingStap<BevragingsBericht, BevragingBerichtContextBasis, BevragingResultaat>
{
    @Inject
    private BlobifierService                          blobifierService;
    @Inject
    private ObjectSleutelService                      objectSleutelService;
    @Inject
    private PersoonRepository                         persoonRepository;

    /**
     * Methode om persoon details op te halen.
     *
     * @param bericht   het VraagDetailsPersoonBericht.
     * @param context   de bericht context.
     * @param resultaat een set met gevonden personen.
     * @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false).
     * @brp.bedrijfsregel VR00054
     * @brp.bedrijfsregel VR00055
     */
    @Regels({ Regel.VR00054, Regel.VR00055 })
    protected boolean geefDetailPersoon(final BevragingsBericht bericht,
        final BevragingBerichtContextBasis context, final BevragingResultaat resultaat)
    {
        PersoonHisVolledigImpl persoon = null;
        final BurgerservicenummerAttribuut bsn = bericht.getZoekcriteriaPersoon().getBurgerservicenummer();
        final AdministratienummerAttribuut anr = bericht.getZoekcriteriaPersoon().getAdministratienummer();
        final SleutelwaardetekstAttribuut objectSleutel = bericht.getZoekcriteriaPersoon().getObjectSleutel();

        try {
            if (bsn != null) {
                persoon = haalPersoonOpMetBurgerservicenummer(bsn);
            } else if (anr != null) {
                persoon = haalPersoonOpMetAdministratienummer(anr);
            } else if (objectSleutel != null) {
                persoon = haalPersoonOpMetObjectSleutel(objectSleutel, context.getPartij());
            }
            // Er zou ook een niet ingeschreven persoon gevonden kunnen worden. Dit moeten we afvangen, zie BRBV0005.
            if (persoon != null && persoon.getSoort().getWaarde() == SoortPersoon.NIET_INGESCHREVENE) {
                // Geeft geen specifieke melding, maar zet persoon op null
                // en gooit een exceptie, waardoor BRBV0006 afgaat.
                persoon = null;
                throw new PersoonNietGevondenExceptie("Niet ingeschrevenen kunnen niet bevraagd worden.");
            }
        } catch (final PersoonNietGevondenExceptie e) {
            resultaat.voegMeldingToe(new Melding(
                SoortMelding.FOUT,
                Regel.BRBV0006,
                Regel.BRBV0006.getOmschrijving(),
                bericht.getZoekcriteriaPersoon(),
                null));
        } catch (NietUniekeBsnExceptie | NietUniekeAnummerExceptie e) {
            resultaat.voegMeldingToe(new Melding(
                SoortMelding.FOUT,
                Regel.BRBV0007,
                Regel.BRBV0007.getOmschrijving(),
                bericht.getZoekcriteriaPersoon(),
                null));
        } catch (final ObjectSleutelVerlopenExceptie e) {
            voegMeldingToeVoorObjectSleutelExceptie(resultaat, bericht, Regel.BRAL0014);
        } catch (final ObjectSleutelPartijInvalideExceptie e) {
            voegMeldingToeVoorObjectSleutelExceptie(resultaat, bericht, Regel.BRAL0015);
        } catch (final OngeldigeObjectSleutelExceptie e) {
            voegMeldingToeVoorObjectSleutelExceptie(resultaat, bericht, Regel.BRAL0016);
        }

        if (persoon != null) {
            DatumTijdAttribuut peilmomentFormeel = null;
            DatumAttribuut peilmomentMaterieel = null;
            HistorievormAttribuut historievorm = null;
            if (bericht.getParameters() != null) {
                peilmomentFormeel = bericht.getParameters().getPeilmomentFormeelResultaat();
                peilmomentMaterieel = bericht.getParameters().getPeilmomentMaterieelResultaat();
                historievorm = bericht.getParameters().getHistorievorm();
            }
            // In geval van geen waarde in het bericht aanwezig, gebruik de default waardes.
            if (peilmomentMaterieel == null) {
                peilmomentMaterieel = BevragingUtil.getDefaultMaterieelPeilmomentVoorBevraging();
            }
            if (peilmomentFormeel == null) {
                peilmomentFormeel = BevragingUtil.getDefaultFormeelPeilmomentVoorBevraging();
            }
            if (historievorm == null) {
                historievorm = BevragingUtil.getDefaultHistorieVormVoorBevraging();
            }
            final PersoonHisVolledigView persoonHisVolledigView = getPersoonHisVolledigView(persoon, peilmomentFormeel, peilmomentMaterieel,
                historievorm, context);
            bewerkBetrokkenhedenInViews(Collections.singletonList(persoonHisVolledigView));
            bepaalEnZetObjectSleutels(Collections.singletonList(persoonHisVolledigView), context);
            resultaat.voegGevondenPersoonToe(persoonHisVolledigView);
            // Voeg persoon toe aan de context, voor de naVerwerkingRegels.
            context.setPersoonHisVolledigImpl(persoon);

            // Voeg persoon Id toe aan het resultaat voor archivering
            resultaat.getTeArchiverenPersonenIngaandBericht().add(persoonHisVolledigView.getID());
            resultaat.getTeArchiverenPersonenUitgaandBericht().add(persoonHisVolledigView.getID());
        }

        return Stap.DOORGAAN;
    }

    /**
     * Bouwt een persoonhisvolledig view met mag groep tonen predikaat.
     *
     * @param persoon de persoon
     * @param peilmomentFormeel de formele peildatum
     * @param peilmomentMaterieel de materiele peildatum
     * @param historievorm de historievorm
     * @param context de berichtcontext
     * @return een PersoonHisVolledigView
     */
    protected PersoonHisVolledigView getPersoonHisVolledigView(final PersoonHisVolledigImpl persoon, final DatumTijdAttribuut peilmomentFormeel,
        final DatumAttribuut peilmomentMaterieel, final HistorievormAttribuut historievorm, final BevragingBerichtContextBasis context)
    {
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(persoon,
            new BevragingHistoriePredikaat(peilmomentMaterieel, peilmomentFormeel, historievorm.getWaarde()));
        PersoonHisVolledigViewUtil.initialiseerView(persoonHisVolledigView);
        return persoonHisVolledigView;
    }

    /**
     * Maakt object sleutels aan en zet ze op de bevraagde personen en (zichtbare) betrokkenheden.
     *
     * @param persoonHisVolledigViews de bevraagde personen.
     * @param context                 de bericht context.
     * @brp.bedrijfsregel BRBV0004
     */
    protected void bepaalEnZetObjectSleutels(final List<PersoonHisVolledigView> persoonHisVolledigViews,
        final BevragingBerichtContextBasis context)
    {
        final Integer partijCode = context.getPartij().getWaarde().getCode().getWaarde();
        for (final PersoonHisVolledigView persoonHisVolledigView : persoonHisVolledigViews) {
            final String objectSleutel =
                objectSleutelService.genereerObjectSleutelString(persoonHisVolledigView.getID(), partijCode);
            persoonHisVolledigView.setObjectSleutel(objectSleutel);
            for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : persoonHisVolledigView.getBetrokkenheden()) {
                final RelatieHisVolledig relatie = betrokkenheidHisVolledigView.getRelatie();
                if (((RelatieHisVolledigView) relatie).isZichtbaar()) {
                    for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : relatie.getBetrokkenheden()) {
                        if (((BetrokkenheidHisVolledigView) betrokkenheidHisVolledig).isZichtbaar()) {
                            final String objectSleutelBetrokkenheid =
                                objectSleutelService.genereerObjectSleutelString(
                                    betrokkenheidHisVolledig.getPersoon().getID(), partijCode);
                            ((PersoonHisVolledigView) betrokkenheidHisVolledig.getPersoon()).setObjectSleutel(
                                objectSleutelBetrokkenheid);
                        }
                    }
                }
            }
        }
    }

    /**
     * Zorg ervoor dat de persoon zelf niet getoond wordt als betrokkenheid in Huwelijk- en GeregistreerdPartnerschappen. Dit doen we door het zichtbaar
     * vlaggetje op false te zetten. Voor familierechtelijke betrekkingen hoeven we geen vlaggetjes te zetten, omdat in de output sowieso alleen maar de
     * 'andere' betrokkenheden (ouders vs kind) komen te staan.
     *
     * @param persoonHisVolledigViews de te bewerken view.
     */
    protected void bewerkBetrokkenhedenInViews(final List<PersoonHisVolledigView> persoonHisVolledigViews) {
        for (final PersoonHisVolledigView persoonHisVolledigView : persoonHisVolledigViews) {
            for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : persoonHisVolledigView.getPartnerBetrokkenheden()) {
                final RelatieHisVolledig relatie = betrokkenheidHisVolledigView.getRelatie();
                for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : relatie.getBetrokkenheden()) {
                    if (betrokkenheidHisVolledig.getPersoon().getID().equals(persoonHisVolledigView.getID())) {
                        ((BetrokkenheidHisVolledigView) betrokkenheidHisVolledig).setZichtbaar(false);
                    }
                }
            }
        }
    }

    /**
     * Haal een persoon his volledig op aan de hand van een technisch id. Let op: tevens worden alle betrokkenen ingeladen om lazy loading problemen te
     * voorkomen.
     *
     * @param technischId het technisch id van de persoon
     * @return de persoon his volledig
     */
    protected PersoonHisVolledigImpl haalPersoonOpMetTechnischId(final int technischId) {
        final PersoonHisVolledigImpl persoon = blobifierService.leesBlob(technischId);
        return laadBetrokkenhedenVanPersoon(persoon);
    }

    /**
     * Haal een persoon his volledig op aan de hand van een technisch id. Let op: tevens worden alle betrokkenen ingeladen om lazy loading te voorkomen.
     *
     * @param bsn het bsn van de persoon
     * @return de persoon his volledig of null indien niet gevonden
     */
    protected PersoonHisVolledigImpl haalPersoonOpMetBurgerservicenummer(final BurgerservicenummerAttribuut bsn) {
        final Integer technischId = persoonRepository.zoekIdBijBSN(bsn);
        if (technischId == null) {
            throw new PersoonNietGevondenExceptie();
        }
        return haalPersoonOpMetTechnischId(technischId);
    }

    /**
     * Haal een persoon his volledig op aan de hand van een A-nummer. Let op: tevens worden alle betrokkenen ingeladen om lazy loading te voorkomen.
     *
     * @param anr het a-nummer
     * @return de persoon his volledig of null indien niet gevonden
     */
    protected PersoonHisVolledigImpl haalPersoonOpMetAdministratienummer(final AdministratienummerAttribuut anr) {
        final Integer technischId = persoonRepository.zoekIdBijAnummer(anr);
        if (technischId == null) {
            throw new PersoonNietGevondenExceptie();
        }
        return haalPersoonOpMetTechnischId(technischId);
    }

    /**
     * Haal een persoon his volledig op aan de hand van een objectsleutel. Let op: tevens worden alle betrokkenen ingeladen om lazy loading te voorkomen.
     *
     * @param objectsleutel de objectsleutel
     * @param partijCode    de partijCode van de bevragende partij
     * @return de persoon his volledig of null indien niet gevonden
     * @throws OngeldigeObjectSleutelExceptie indien de sleutel in het geheel niet te gebruiken is, door bijvoorbeeld een fout tijdens het decrypten of
     *                                        deserialiseren.
     */
    protected PersoonHisVolledigImpl haalPersoonOpMetObjectSleutel(final SleutelwaardetekstAttribuut objectsleutel, final PartijAttribuut partijCode)
        throws OngeldigeObjectSleutelExceptie
    {
        final Integer persoonId = objectSleutelService.bepaalPersoonId(
            objectsleutel.getWaarde(), partijCode.getWaarde().getCode().getWaarde());
        return haalPersoonOpMetTechnischId(persoonId);
    }

    /**
     * Haal alle persoon his volledig's op die de meegegeven id's hebben. Let op: tevens worden van elke persoon alle betrokkenen ingeladen om lazy loading
     * te voorkomen.
     *
     * @param ids de id's van personen
     * @return een lijst met persoon his volledigs
     */
    protected List<PersoonHisVolledig> haalPersonenOpMetId(final Iterable<Integer> ids) {
        final List<PersoonHisVolledig> personen = new ArrayList<>();
        for (final Integer id : ids) {
            final PersoonHisVolledigImpl persoon = blobifierService.leesBlob(id);
            personen.add(laadBetrokkenhedenVanPersoon(persoon));
        }
        return personen;
    }

    /**
     * Haal alle betrokkenen op uit de database dmv het 'aanraken' van de soort. Dit triggert de proxy/hibernate tot het ophalen van de betrokken persoon.
     * Dit voorkomt lazy loading bij het jibx binden.
     *
     * @param persoon de persoon (mag null zijn)
     * @return de persoon met ingeladen betrokkenen of null indien input null
     */
    protected PersoonHisVolledigImpl laadBetrokkenhedenVanPersoon(final PersoonHisVolledigImpl persoon) {
        if (persoon != null) {
            for (final BetrokkenheidHisVolledig betrokkenheid : persoon.getBetrokkenheden()) {
                for (final BetrokkenheidHisVolledig indirecteBetrokkenheid : betrokkenheid.getRelatie().getBetrokkenheden()) {
                    indirecteBetrokkenheid.getPersoon().getSoort();
                }
            }
        }
        return persoon;
    }

    /**
     * Filtert een his volledig persoon, zodat alleen de huidige informatie overblijft.
     *
     * @param persoonHisVolledig de persoon his volledig
     * @return een view met alleen huidige data
     */
    protected PersoonHisVolledigView maakHuidigePersoon(final PersoonHisVolledig persoonHisVolledig) {
        final PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(persoonHisVolledig, new HuidigeSituatiePredikaat());
        PersoonHisVolledigViewUtil.initialiseerView(persoonHisVolledigView);
        return persoonHisVolledigView;
    }

    /**
     * Voeg een melding toe aan resultaat voor een opgetreden object sleutel exceptie.
     *
     * @param resultaat        resultaat van de bevraging
     * @param bevragingBericht bericht waarvan de object sleutel van invalide is
     * @param regel            de regel behorende bij de opgetreden exceptie
     */
    private void voegMeldingToeVoorObjectSleutelExceptie(final BevragingResultaat resultaat,
        final BevragingsBericht bevragingBericht, final Regel regel)
    {
        final Melding melding = new Melding(SoortMelding.FOUT, regel, regel.getOmschrijving().replace("%s",
            bevragingBericht.getZoekcriteriaPersoon().getObjectSleutel().getWaarde()));
        melding.setReferentieID(bevragingBericht.getZoekcriteriaPersoon().getCommunicatieID());
        resultaat.voegMeldingToe(melding);
    }
}
