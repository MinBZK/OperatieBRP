/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.dataaccess.repository.ToegangLeveringsautorisatieRepository;
import nl.bzk.brp.dataaccess.repository.archivering.BerichtPersoonRepository;
import nl.bzk.brp.dataaccess.repository.archivering.BerichtRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.BerichtdataAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.RichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingswijzeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.logisch.ber.BerichtResultaatGroep;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.ber.BerichtParametersGroepModel;
import nl.bzk.brp.model.operationeel.ber.BerichtPersoonModel;
import nl.bzk.brp.model.operationeel.ber.BerichtResultaatGroepModel;
import nl.bzk.brp.model.operationeel.ber.BerichtStandaardGroepModel;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Standaard implementatie van de {@link ArchiveringService} die archiveert naar de database.
 *
 * @brp.bedrijfsregel VR00053
 */
@Regels(Regel.VR00053)
@Service
public class ArchiveringServiceImpl implements ArchiveringService {

    private static final String WORDT_NADER_BEPAALD = "<Wordt nader bepaald>";

    @Inject
    private BerichtRepository berichtRepository;

    @Inject
    private BerichtPersoonRepository berichtPersoonRepository;

    @Override
    @Transactional(value = "archiveringTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public final BerichtenIds archiveer(final String ingaandBerichtdata) {
        final BerichtModel ingaandBerichtModel = archiveerIngaandBericht(ingaandBerichtdata);
        final BerichtModel uitgaandBerichtModel = archiveerUitgaandBericht(ingaandBerichtModel, null);

        return new BerichtenIds(ingaandBerichtModel.getID(), uitgaandBerichtModel.getID());
    }

    @Override
    @Transactional(value = "archiveringTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public final BerichtModel archiveer(final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens) {
        final BerichtModel uitgaandBerichtModel =
            new BerichtModel(new SoortBerichtAttribuut(SoortBericht.LVG_SYN_VERWERK_PERSOON), new RichtingAttribuut(Richting.UITGAAND));
        final BerichtStandaardGroepModel standaardGroepModel =
            new BerichtStandaardGroepModel(synchronisatieBerichtGegevens.getAdministratieveHandelingId(),
                new BerichtdataAttribuut(WORDT_NADER_BEPAALD),
                null);
        uitgaandBerichtModel.setStandaard(standaardGroepModel);

        final SoortSynchronisatieAttribuut soortSynchronisatie = synchronisatieBerichtGegevens.getSoortSynchronisatie();
        final Integer dienstId = synchronisatieBerichtGegevens.getDienstId();

        uitgaandBerichtModel.setParameters(
            new BerichtParametersGroepModel(null, null, null, soortSynchronisatie, synchronisatieBerichtGegevens.getToegangLeveringsautorisatieId(), dienstId, null,
                null,
                null, null));

        uitgaandBerichtModel.setStuurgegevens(synchronisatieBerichtGegevens.getStuurgegevens());

        final BerichtModel opgeslagenArchiveringsbericht = berichtRepository.save(uitgaandBerichtModel);

        final Set<Integer> teArchiverenPersonen = new HashSet<>(synchronisatieBerichtGegevens.getGeleverdePersoonsIds());
        archiveerPersonen(opgeslagenArchiveringsbericht, teArchiverenPersonen);

        return opgeslagenArchiveringsbericht;
    }

    @Override
    public final void werkIngaandBerichtInfoBij(final Long ingaandBerichtId,
        final Long administratieveHandelingId,
        final BerichtStuurgegevensGroepBericht stuurgegevensIngaand,
                                                final BerichtParametersGroepBericht parametersIngaand,
                                                final SoortBericht soortBericht,
                                                final Set<Integer> teArchiverenPersonen)
    {
        final BerichtModel ingaandBericht = berichtRepository.findOne(ingaandBerichtId);
        ingaandBericht.getStandaard().setAdministratieveHandelingId(administratieveHandelingId);

        // Let op: bericht heeft al stuurgegevens. Zie archiveer() functie.
        // Let op: DatumTijd ontvangst is dus al ingevuld.
        final Short partijId;
        if (stuurgegevensIngaand.getZendendePartij() != null) {
            partijId = stuurgegevensIngaand.getZendendePartij().getWaarde().getID();
        } else {
            partijId = null;
        }

        final BerichtStuurgegevensGroepModel stuurgegevensGroepGearchiveerdBericht = ingaandBericht.getStuurgegevens();
        stuurgegevensGroepGearchiveerdBericht.setZendendePartijId(partijId);
        stuurgegevensGroepGearchiveerdBericht.setZendendeSysteem(stuurgegevensIngaand.getZendendeSysteem());
        stuurgegevensGroepGearchiveerdBericht.setDatumTijdVerzending(stuurgegevensIngaand.getDatumTijdVerzending());
        stuurgegevensGroepGearchiveerdBericht.setReferentienummer(stuurgegevensIngaand.getReferentienummer());

        if (null != parametersIngaand) {
            final VerwerkingswijzeAttribuut verwerkingswijzeAttribuut = parametersIngaand.getVerwerkingswijze();

            //FIXME doen we hier null-checks omdat het mogelijk is...of omdat de test deze velden niet vult.
            Integer laId = null;
            if (parametersIngaand.getLeveringsautorisatieID() != null) {
                laId = Integer.parseInt(parametersIngaand.getLeveringsautorisatieID());
            }
            Integer dienstId = null;
            if (parametersIngaand.getDienstID() != null) {
                dienstId = Integer.parseInt(parametersIngaand.getDienstID());
            }

            ingaandBericht.setParameters(
                    new BerichtParametersGroepModel(verwerkingswijzeAttribuut, null,null, null,
                            laId, dienstId,
                            parametersIngaand.getPeilmomentMaterieelSelectie(),
                            parametersIngaand.getPeilmomentMaterieelResultaat(),
                            parametersIngaand.getPeilmomentFormeelResultaat(),
                            parametersIngaand.getStamgegeven()));
        }

        if (null != soortBericht) {
            ingaandBericht.setSoort(new SoortBerichtAttribuut(soortBericht));
        }

        berichtRepository.save(ingaandBericht);
        // Archiveer de personen.
        archiveerPersonen(ingaandBericht, teArchiverenPersonen);
    }

    @Regels({Regel.VR00050, Regel.R1268 })
    @Override
    public final void werkUitgaandBerichtInfoBij(
            final Long uitgaandBerichtId,
            final Long administratieveHandelingId,
            final BerichtStuurgegevensGroepBericht stuurgegevensGroepAntwoordBericht,
            final BerichtResultaatGroep berichtResultaatGroep,
            final SoortBerichtAttribuut soortBericht,
            final Leveringsautorisatie leveringsautorisatie,
            final VerwerkingswijzeAttribuut verwerkingswijze,
            final Set<Integer> teArchiverenPersonen)
    {
        // Zet hier een timestamp op de uitgaande en opgeslagen stuurgegevens:
        final DatumTijdAttribuut datumTijdVerzending = new DatumTijdAttribuut(Calendar.getInstance().getTime());
        stuurgegevensGroepAntwoordBericht.setDatumTijdVerzending(datumTijdVerzending);

        final BerichtModel uitgaandBericht = berichtRepository.findOne(uitgaandBerichtId);
        uitgaandBericht.getStandaard().setAdministratieveHandelingId(administratieveHandelingId);
        // Uitgaand bericht heeft nog geen stuurgegevens, zie archiveer() functie.
        // Voor vulling zie AbstractAntwoordBerichtFactory.maakStuurgegevensVoorAntwoordBericht().
        final PartijAttribuut ontvangendePartij = stuurgegevensGroepAntwoordBericht.getOntvangendePartij();
        Short ontvangendePartijId = null;
        if (ontvangendePartij != null) {
            ontvangendePartijId = ontvangendePartij.getWaarde().getID();
        }
        final PartijAttribuut zendendePartij = stuurgegevensGroepAntwoordBericht.getZendendePartij();
        final Short zendendePartijId;
        if (zendendePartij != null) {
            zendendePartijId = zendendePartij.getWaarde().getID();
        } else {
            zendendePartijId = null;
        }
        final BerichtStuurgegevensGroepModel stuurgegevensGroep =
                new BerichtStuurgegevensGroepModel(
                        zendendePartijId,
                        stuurgegevensGroepAntwoordBericht.getZendendeSysteem(),
                        ontvangendePartijId,
                        stuurgegevensGroepAntwoordBericht.getOntvangendeSysteem(),
                        stuurgegevensGroepAntwoordBericht.getReferentienummer(),
                        stuurgegevensGroepAntwoordBericht.getCrossReferentienummer(),
                        datumTijdVerzending,
                        null);
        uitgaandBericht.setStuurgegevens(stuurgegevensGroep);
        uitgaandBericht.setResultaat(new BerichtResultaatGroepModel(berichtResultaatGroep));
        uitgaandBericht.setSoort(soortBericht);

        final Integer laId;
        if (leveringsautorisatie != null) {
            laId = leveringsautorisatie.getID();
        } else {
            laId = null;
        }

        if (laId != null || verwerkingswijze != null) {
            uitgaandBericht.setParameters(new BerichtParametersGroepModel(verwerkingswijze, null, null, null, laId, null, null, null, null, null));
        }

        berichtRepository.save(uitgaandBericht);
        // Archiveer de personen.
        archiveerPersonen(uitgaandBericht, teArchiverenPersonen);
    }

    @Override
    @Transactional(value = "archiveringTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public final void werkDataBij(final Long uitgaandBerichtId, final String data) {
        final BerichtModel uitgaandBerichtModel = berichtRepository.findOne(uitgaandBerichtId);
        if (uitgaandBerichtModel == null) {
            throw new IllegalStateException(String.format("uitgaand bericht %s ontbreekt in database", uitgaandBerichtId));
        }
        final BerichtStandaardGroepModel standaard = uitgaandBerichtModel.getStandaard();
        standaard.setData(new BerichtdataAttribuut(data));
        berichtRepository.save(uitgaandBerichtModel);
    }

    /**
     * Archiveert een ingaand bericht.
     *
     * @param ingaandBerichtdata ingaand berichtdata
     * @return het bericht model
     */
    private BerichtModel archiveerIngaandBericht(final String ingaandBerichtdata) {
        final BerichtModel ingaandBerichtModel = new BerichtModel();
        ingaandBerichtModel.setRichting(new RichtingAttribuut(Richting.INGAAND));
        ingaandBerichtModel.setStandaard(new BerichtStandaardGroepModel(null, new BerichtdataAttribuut(ingaandBerichtdata), null));

        // Vul de ontvangst datumtijd in:
        ingaandBerichtModel.setStuurgegevens(new BerichtStuurgegevensGroepModel(
                null, null, null, null, null, null, null, new DatumTijdAttribuut(Calendar.getInstance().getTime())));

        berichtRepository.save(ingaandBerichtModel);
        return ingaandBerichtModel;

    }

    /**
     * Archiveert een uitgaand bericht.
     *
     * @param ingaandBerichtModel ingaand bericht model
     * @param administratieveHandelingId de administratieve handeling id
     * @return het bericht model
     */
    private BerichtModel archiveerUitgaandBericht(final BerichtModel ingaandBerichtModel,
                                                  final Long administratieveHandelingId)
    {
        final BerichtModel uitgaandBerichtModel = new BerichtModel();
        uitgaandBerichtModel.setRichting(new RichtingAttribuut(Richting.UITGAAND));
        uitgaandBerichtModel.setStandaard(new BerichtStandaardGroepModel(administratieveHandelingId, new BerichtdataAttribuut(WORDT_NADER_BEPAALD),
                                                                         ingaandBerichtModel));

        berichtRepository.save(uitgaandBerichtModel);
        return uitgaandBerichtModel;
    }

    /**
     * Archiveer de personen bij een bericht.
     *
     * @param bericht het bericht
     * @param teArchiverenPersonen de te archiveren personen bij het bericht.
     */
    @Regels({ Regel.VR00054, Regel.VR00055 })
    private void archiveerPersonen(final BerichtModel bericht, final Set<Integer> teArchiverenPersonen) {
        if (teArchiverenPersonen != null && !teArchiverenPersonen.isEmpty()) {
            final List<BerichtPersoonModel> archiveringPersonen = new ArrayList<>();
            for (final Integer persoonId : teArchiverenPersonen) {
                archiveringPersonen.add(new BerichtPersoonModel(bericht, persoonId));
            }

            // Sla op:
            berichtPersoonRepository.save(archiveringPersonen);
        }
    }
}
