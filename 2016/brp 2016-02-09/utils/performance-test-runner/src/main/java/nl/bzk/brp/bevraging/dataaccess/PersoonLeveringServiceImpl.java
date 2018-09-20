/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.dataaccess;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.google.common.base.Optional;
import nl.bzk.brp.dataaccess.repository.PersoonCacheRepository;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.dataaccess.repository.jpa.PersoonHisVolledigJpaRepository;
import nl.bzk.brp.expressietaal.expressies.BRPExpressies;
import nl.bzk.brp.expressietaal.expressies.Expressie;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.ApplicatienaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.OrganisatienaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardetekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.hisvolledig.predikaat.MaterieleHistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AbstractLeveringBericht;
import nl.bzk.brp.model.levering.AdministratieveHandelingKennisgeving;
import nl.bzk.brp.model.levering.KennisgevingBericht;
import nl.bzk.brp.model.levering.VerwerkingsSoort;
import nl.bzk.brp.model.operationeel.ber.BerichtStuurgegevensGroepModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigSerializer;
import nl.bzk.brp.util.DatumTijdUtil;
import nl.bzk.brp.util.DatumUtil;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service voor het leveren van personen.
 */
@Service
public class PersoonLeveringServiceImpl implements PersoonLeveringService {
    private final Logger LOGGER = LoggerFactory.getLogger(PersoonLeveringServiceImpl.class);

    @Inject
    private PersoonCacheRepository persoonCacheRepository;

    @Inject
    private PersoonHisVolledigRepository persoonHisVolledigRepository;

    @Inject
    private BestandRepository bestandRepository;

    @Inject
    private PersoonHisVolledigSerializer persoonSerializer;

    @Inject
    private String populatieBeperking;

    private Expressie expressie;

    @PostConstruct
    protected void postConstruct() {
        LOGGER.info("populatiebeperking {}", populatieBeperking );
        this.expressie = BRPExpressies.parse(populatieBeperking).getExpressie();
    }

    @Override
    public Optional<Boolean> leverPersoon(final Integer id) {

        LOGGER.debug("probeer persoon {} te leveren", id);
        StopWatch stopWatch = new Slf4JStopWatch();

        PersoonHisVolledig persoon = persoonHisVolledigRepository.leesGeserializeerdModel(id);
        stopWatch.lap("1-deserializeer persoon");

        Optional<Boolean> geleverd = this.leverPersoon(persoon);

        if (geleverd.isPresent() && geleverd.get()) {
            stopWatch.lap("2-bepaal populatie");
            String bericht = this.maakKennisGeving(persoon);
            stopWatch.lap("3-xml binding");

            this.schrijfKennisgeving(persoon.getID(), bericht);
            stopWatch.stop("4-schrijven kennisgeving");
        } else {
            stopWatch.stop("2-bepaal populatie");
        }

        return geleverd;
    }

    @Override
    public Optional<Boolean> leverPersoon(final byte[] data) {
        StopWatch stopWatch = new Slf4JStopWatch();

        PersoonHisVolledig persoon = this.maakPersoon(data);
        stopWatch.lap("1-deserializeer persoon");

        Optional<Boolean> geleverd = this.leverPersoon(persoon);

        if (geleverd.isPresent() && geleverd.get()) {
            stopWatch.lap("2-bepaal populatie");
            String bericht = this.maakKennisGeving(persoon);
            stopWatch.lap("3-xml binding");

            this.schrijfKennisgeving(persoon.getID(), bericht);
            stopWatch.stop("4-schrijven kennisgeving");
        } else {
            stopWatch.stop("2-bepaal populatie");
        }

        return geleverd;
    }

    /**
     * Leest de blob van een persoon.
     * @param id de ID van de persoon
     * @return de byte[] van de blob
     */
    private byte[] leesPersoonData(final Integer id) {

        PersoonCacheModel cacheModel = persoonCacheRepository.haalPersoonCacheOp(id);

        return cacheModel.getStandaard().getPersoonHistorieVolledigGegevens().getWaarde();
    }

    /**
     * Converteert een persoon-blob naar Java model.
     * @param data de blob
     * @return een {@link PersoonHisVolledig} instantie
     */
    private PersoonHisVolledig maakPersoon(final byte[] data) {
        PersoonHisVolledigImpl persoon = persoonSerializer.deserializeer(data);

        persoonHisVolledigRepository.verrijkMetBetrokkenPersonenProxies(persoon);

        return persoon;
    }

    /**
     * Doet de werkelijke levering van de persoon
     * @param persoon te leveren persoon
     * @return Optional met het resultaat, of {@link com.google.common.base.Optional#absent()}
     * als het is fout gegaan
     */
    private Optional<Boolean> leverPersoon(final PersoonHisVolledig persoon) {
        PersoonView persoonView = new PersoonView(persoon);

        LOGGER.debug("evalueer persoon [{}] tegen expressie [{}]", persoonView.getID(), expressie.getStringWaarde());


        Expressie resultaat = BRPExpressies.evalueer(expressie, persoonView);

        if (!resultaat.isFout()) {
            if (resultaat.getBooleanWaarde()) {
                // lever persoon
                LOGGER.debug("Persoon {} wordt geleverd: {}", persoon.getID(), resultaat.getBooleanWaarde());
            }

            return Optional.of(resultaat.getBooleanWaarde());
        } else {
            return Optional.absent();
        }
    }

    /**
     * Maakt een kennisgeving bericht.
     * @param persoonHisVolledig de persoon waarvoor de kennisgeving geldt
     * @return het bericht als string
     */
    private String maakKennisGeving(final PersoonHisVolledig persoonHisVolledig) {
        AdministratieveHandelingModel administratieveHandeling =
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON), StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM,
                                                  new OntleningstoelichtingAttribuut("Levering"),
                                                  DatumTijdUtil.nu());

        AdministratieveHandelingKennisgeving handelingKennisgeving =
                new AdministratieveHandelingKennisgeving(administratieveHandeling);
        handelingKennisgeving.setVerwerkingsSoort(VerwerkingsSoort.I);

        handelingKennisgeving.setBijgehoudenPersonen(Arrays.asList(new PersoonHisVolledigView(persoonHisVolledig,
                                                                                              MaterieleHistorieVanafPredikaat
                                                                                                      .geldigOpEnNa(
                                                                                                              DatumUtil
                                                                                                                      .vandaag()))));

        BerichtStuurgegevensGroepModel stuurgegevens = new BerichtStuurgegevensGroepModel(
                new OrganisatienaamAttribuut("Ministerie van Binnenlandse zaken"),
                new ApplicatienaamAttribuut("BRP"),
                new SleutelwaardetekstAttribuut(UUID.randomUUID().toString()), null
        );

        KennisgevingBericht kennisgevingBericht = new KennisgevingBericht(handelingKennisgeving);
        kennisgevingBericht.setStuurgegevens(stuurgegevens);

        try {
            IBindingFactory bfact = BindingDirectory.getFactory(AbstractLeveringBericht.class);

            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(2);
            StringWriter stringWriter = new StringWriter();
            mctx.setOutput(stringWriter);
            mctx.marshalDocument(kennisgevingBericht);

            return stringWriter.toString();
        } catch (Exception e) {
            LOGGER.error("Fout binding naar XML", e);
        }

        return "";
    }

    /**
     * Schrijft de kennisgeving weg.
     * @param persoonID id als basis voor de bestandsnaam
     * @param bericht het bericht
     */
    private void schrijfKennisgeving(final Integer persoonID, final String bericht) {
        bestandRepository.schrijfRegels(persoonID.toString(), Arrays.asList(bericht));
    }
}
