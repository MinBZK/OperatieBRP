/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.protocollering.service.algemeen.ProtocolleringService;

/**
 * De generieke implementatie van {@link Bevraging.ProtocolleerBerichtService}.
 * @param <V> type bevraging verzoek
 * @param <R> type bevraging resultaat
 */
@Bedrijfsregel(Regel.R1613)
@Bedrijfsregel(Regel.R1617)
@Bedrijfsregel(Regel.R1618)
@Bedrijfsregel(Regel.R1620)
public abstract class AbstractProtocolleerBerichtServiceImpl<V extends BevragingVerzoek, R extends BevragingResultaat>
        implements Bevraging.ProtocolleerBerichtService<V, R> {

    private ProtocolleringService protocolleringVerwerkingService;

    @Override
    @Bedrijfsregel(Regel.R1613)
    public final void protocolleer(final V verzoek, final R berichtResultaat, final AntwoordBerichtResultaat antwoordBerichtResultaat) {
        List<LeveringPersoon> personenVoorProtocollering = bepaalTeProtocollerenPersonen(berichtResultaat);
        if (personenVoorProtocollering.isEmpty()) {
            return;
        }
        final ProtocolleringOpdracht protocolleringOpdracht = new ProtocolleringOpdracht();
        protocolleringOpdracht.setGeleverdePersonen(personenVoorProtocollering);
        protocolleringOpdracht.setToegangLeveringsautorisatieId(berichtResultaat.getAutorisatiebundel().getToegangLeveringsautorisatie().getId());
        protocolleringOpdracht.setDienstId(berichtResultaat.getAutorisatiebundel().getDienst().getId());
        protocolleringOpdracht.setDatumTijdKlaarzettenLevering(antwoordBerichtResultaat.getDatumVerzending());
        protocolleringOpdracht.setDatumAanvangMaterielePeriodeResultaat(bepaalDatumAanvangMaterielePeriodeResultaat(verzoek));
        protocolleringOpdracht.setDatumEindeMaterielePeriodeResultaat(bepaalDatumEindeMaterielePeriodeResultaat(verzoek));
        protocolleringOpdracht.setDatumTijdAanvangFormelePeriodeResultaat(bepaalDatumTijdAanvangFormelePeriodeResultaat(verzoek));
        protocolleringOpdracht.setDatumTijdEindeFormelePeriodeResultaat(
                bepaalDatumTijdEindeFormelePeriodeResultaat(verzoek, antwoordBerichtResultaat.getDatumVerzending()));
        protocolleringOpdracht.setSoortDienst(verzoek.getSoortDienst());

        vulScopingParameters(verzoek, protocolleringOpdracht);

        protocolleringVerwerkingService.protocolleer(protocolleringOpdracht);
    }

    private List<LeveringPersoon> bepaalTeProtocollerenPersonen(final R berichtResultaat) {
        boolean doeProtocollering;
        doeProtocollering = berichtResultaat.getBericht() != null;
        doeProtocollering = doeProtocollering && berichtResultaat.getBericht().getBasisBerichtGegevens().getResultaat().getVerwerking()
                .equals(VerwerkingsResultaat.GESLAAGD.getNaam());
        doeProtocollering = doeProtocollering && berichtResultaat.getAutorisatiebundel() != null;
        doeProtocollering = doeProtocollering && berichtResultaat.getAutorisatiebundel().getToegangLeveringsautorisatie() != null;
        doeProtocollering = doeProtocollering
                && berichtResultaat.getAutorisatiebundel().getRol() == Rol.AFNEMER;
        doeProtocollering = doeProtocollering && berichtResultaat.getAutorisatiebundel().getLeveringsautorisatie()
                .getProtocolleringsniveau() != Protocolleringsniveau.GEHEIM;
        return doeProtocollering ? bepaalGeleverdePersonen(berichtResultaat) : Collections.emptyList();
    }

    /**
     * Vul de scoping parameters. Wordt default niet gedaan.
     * @param verzoek het verzoek
     * @param berichtGegevens de berichtgegevens
     */
    protected void vulScopingParameters(final V verzoek, final ProtocolleringOpdracht berichtGegevens) {

    }

    /**
     * Bepaal de datum aanvang materiele periode resultaat.
     * @param verzoek het verzoek
     * @return datum aanvang materiele periode resultaat
     */
    @Bedrijfsregel(Regel.R1617)
    protected abstract Integer bepaalDatumAanvangMaterielePeriodeResultaat(final V verzoek);

    /**
     * Bepaal de datum einde materiele periode resulaat.
     * @param verzoek het berichtresultaat
     * @return datum einde materiele periode resultaat
     */
    @Bedrijfsregel(Regel.R1618)
    protected abstract Integer bepaalDatumEindeMaterielePeriodeResultaat(final V verzoek);

    /**
     * Bepaal de datum/tijd aanvang formele periode resultaat.
     * @param verzoek het verzoek
     * @return de datum/tijd aanvang formele periode resultaat
     */
    @Bedrijfsregel(Regel.R1619)
    protected abstract ZonedDateTime bepaalDatumTijdAanvangFormelePeriodeResultaat(final V verzoek);

    /**
     * Bepaal de datum/tijd einde formele periode resultaat.
     * @param verzoek het berichtresultaat
     * @param datumTijdKlaarzettenBericht de datum/tijd waarop het bericht is klaargezet
     * @return de datum/tijd einde formele periode resultaat
     */
    @Bedrijfsregel(Regel.R1620)
    protected abstract ZonedDateTime bepaalDatumTijdEindeFormelePeriodeResultaat(final V verzoek,
                                                                                 final ZonedDateTime datumTijdKlaarzettenBericht);

    /**
     * Bepaal de geleverde persoon ids.
     * @param berichtResultaat het berichtresultaat
     * @return de lijst met geleverde persoon ids
     */
    private List<LeveringPersoon> bepaalGeleverdePersonen(final R berichtResultaat) {
        return berichtResultaat.getBericht().getBijgehoudenPersonen().stream()
                .map(p -> new LeveringPersoon(p.getPersoonslijst().getId(), p.getPersoonslijst().getNuNuBeeld().bepaalTijdstipLaatsteWijziging()))
                .collect(Collectors.toList());
    }

    @Inject
    public void setProtocolleringService(final ProtocolleringService protocolleringVerwerkingService) {
        this.protocolleringVerwerkingService = protocolleringVerwerkingService;
    }
}
