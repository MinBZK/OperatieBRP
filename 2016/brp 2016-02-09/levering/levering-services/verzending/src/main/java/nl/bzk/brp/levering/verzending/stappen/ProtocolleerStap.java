/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.stappen;

import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.jms.JMSException;
import nl.bzk.brp.levering.verzending.context.BerichtContext;
import nl.bzk.brp.levering.verzending.excepties.ProtocolleerExceptie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.internbericht.ProtocolleringOpdracht;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.lev.LeveringModel;
import nl.bzk.brp.model.operationeel.lev.LeveringPersoonModel;
import nl.bzk.brp.protocollering.publicatie.ProtocolleringPublicatieMisluktExceptie;
import nl.bzk.brp.protocollering.publicatie.ProtocolleringPublicatieService;
import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Component;

/**
 * Deze stap verzorgt de protocollering van de berichten die verzonden worden aan afnemers.
 *
 * @brp.bedrijfsregel VR00042
 * @brp.bedrijfsregel VR00043
 * @brp.bedrijfsregel R1995
 */
@Component
@Regels({ Regel.VR00042, Regel.VR00043, Regel.R1995 })
public class ProtocolleerStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private ProtocolleringPublicatieService protocolleringPublicatieService;

    /**
     * Verwerkt de protocollering.
     *
     * @param berichtContext bericht context
     * @throws Exception mogelijke exception
     */
    @Profiled(tag = "ProtocolleerStap", logFailuresSeparately = true, level = "DEBUG")
    public void process(final BerichtContext berichtContext) throws Exception {
        LOGGER.debug("Procotolleer verzending");
        final long start = System.currentTimeMillis();
        final SynchronisatieBerichtGegevens metadata = berichtContext.getSynchronisatieBerichtGegevens();
        final Set<Integer> geleverdePersoonIdSet = new HashSet<>(metadata.getGeleverdePersoonsIds());
        zetMDCMDCVeld(berichtContext);

        try {
            final BerichtModel archiefBericht = berichtContext.getBerichtArchiefModel();
            if (erMoetGeprotocolleerdWorden(berichtContext)) {
                protocolleerLevering(metadata, geleverdePersoonIdSet);
                berichtContext.getVerwerkContext().addProtocolleerTijd(System.currentTimeMillis() - start);
                LOGGER.debug("Protocolleer de verzending van bericht {}", archiefBericht.getID());
            } else {
                LOGGER.info("Het bericht met id {} zal niet geprotocolleerd worden.", archiefBericht.getID());
            }
        } finally {
            verwijderMDCVelden();
        }
    }

    /**
     * Controleert of er geprotocolleerd moet worden. Wanneer het protcolleringsniveau van de leveringsautorisatie op de waarde 'geheim' staat, dan wordt
     * er niet geprotocolleerd.
     *
     * @param berichtContext de bericht context
     * @return true als er geprotocolleerd moet worden, anders false.
     */
    private boolean erMoetGeprotocolleerdWorden(final BerichtContext berichtContext) throws JMSException {
        return !Protocolleringsniveau.GEHEIM.equals(berichtContext.getProtocolleringNiveau());
    }

    /**
     * Doe de protocollering.
     *
     * @param metadata            De berichtgegevens/metadata.
     * @param geleverdePersoonIds lijst met IDs van geleverde personen
     */
    private void protocolleerLevering(final SynchronisatieBerichtGegevens metadata,
        final Set<Integer> geleverdePersoonIds)
    {
        final Integer toegangAboId = metadata.getToegangLeveringsautorisatieId();
        final Integer dienstId = metadata.getDienstId();
        final Long administratieveHandelingId = metadata.getAdministratieveHandelingId();
        final SoortSynchronisatieAttribuut soortSynchronisatie = metadata.getSoortSynchronisatie();

        final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriodeResultaatDeelsOnbekend =
            metadata.getDatumAanvangMaterielePeriodeResultaat();
        DatumAttribuut datumAanvangMaterielePeriodeResultaat = null;
        if (datumAanvangMaterielePeriodeResultaatDeelsOnbekend != null) {
            datumAanvangMaterielePeriodeResultaat =
                new DatumAttribuut(datumAanvangMaterielePeriodeResultaatDeelsOnbekend);
        }

        final DatumEvtDeelsOnbekendAttribuut datumEindeMaterielePeriodeResultaatDeelsOnbekend =
            metadata.getDatumEindeMaterielePeriodeResultaat();
        DatumAttribuut datumEindeMaterielePeriodeResultaat = null;
        if (datumEindeMaterielePeriodeResultaatDeelsOnbekend != null) {
            datumEindeMaterielePeriodeResultaat =
                new DatumAttribuut(datumEindeMaterielePeriodeResultaatDeelsOnbekend);
        }

        final LeveringModel levering = new LeveringModel(toegangAboId, dienstId,
            metadata.getStuurgegevens().getDatumTijdVerzending(),
            null,
            datumAanvangMaterielePeriodeResultaat,
            datumEindeMaterielePeriodeResultaat,
            metadata.getDatumTijdAanvangFormelePeriodeResultaat(),
            metadata.getDatumTijdEindeFormelePeriodeResultaat(),
            administratieveHandelingId, soortSynchronisatie);
        final Set<LeveringPersoonModel> leveringPersonen = new HashSet<>(geleverdePersoonIds.size());

        for (final Integer persoonId : geleverdePersoonIds) {
            final LeveringPersoonModel leveringPersoon = new LeveringPersoonModel(null, persoonId);
            leveringPersonen.add(leveringPersoon);
        }

        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht(levering, leveringPersonen);

        if (metadata.getSoortDienst() != null) {
            protocolleringOpdracht.setSoortDienst(metadata.getSoortDienst());
        }

        try {
            protocolleringPublicatieService.publiceerProtocolleringGegevens(protocolleringOpdracht);
        } catch (final ProtocolleringPublicatieMisluktExceptie e) {
            throw new ProtocolleerExceptie("Protocolleringopdracht is niet gepubliceerd", e);
        }
    }

    /**
     * Zet MDC logging waarden in MDC velden.
     *
     * @param berichtContext berichtContext
     */
    private void zetMDCMDCVeld(final BerichtContext berichtContext) throws JMSException
    {
        MDC.put(MDCVeld.MDC_LEVERINGAUTORISATIEID, berichtContext.getLeveringsautorisatieId());
        MDC.put(MDCVeld.MDC_PARTIJ_CODE, berichtContext.getOntvangendePartijCode());
        MDC.put(MDCVeld.MDC_ADMINISTRATIEVE_HANDELING, String.valueOf(berichtContext.getSynchronisatieBerichtGegevens().getAdministratieveHandelingId()));
    }

    /**
     * Verwijder de waarden van de eerder gezette MDC velden.
     */
    private void verwijderMDCVelden() {
        MDC.remove(MDCVeld.MDC_LEVERINGAUTORISATIEID);
        MDC.remove(MDCVeld.MDC_PARTIJ_CODE);
        MDC.remove(MDCVeld.MDC_ADMINISTRATIEVE_HANDELING);
    }
}
