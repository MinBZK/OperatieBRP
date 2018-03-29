/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service;

import java.sql.Timestamp;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LeveringsaantekeningPersoon;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.Protocollering;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringAntwoord;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringBericht;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.synchronisatie.dal.service.LeveringsaantekeningService;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;

/**
 * Verwerkt het ProtocolleringBericht en retourneerd een ProtocolleringAntwoordBericht.
 */
final class ProtocolleringService implements SynchronisatieBerichtService<ProtocolleringBericht, ProtocolleringAntwoordBericht> {

    private final LeveringsaantekeningService leveringsaantekeningService;

    /**
     * Instantieert een ProtocolleringService.
     * @param leveringsaantekeningService service voor opslaan en ophalen van leveringsaantekeningen
     */
    @Inject
    ProtocolleringService(final LeveringsaantekeningService leveringsaantekeningService) {
        this.leveringsaantekeningService = leveringsaantekeningService;
    }

    @Override
    public Class<ProtocolleringBericht> getVerzoekType() {
        return ProtocolleringBericht.class;
    }

    @Override
    public ProtocolleringAntwoordBericht verwerkBericht(final ProtocolleringBericht protocolleringBericht)
            throws OngeldigePersoonslijstException, BerichtSyntaxException {
        final ProtocolleringAntwoordBericht antwoord = new ProtocolleringAntwoordBericht();
        antwoord.setMessageId(MessageId.generateSyncMessageId());
        antwoord.setCorrelationId(protocolleringBericht.getMessageId());

        for (final Protocollering protocollering : protocolleringBericht.getProtocollering()) {
            antwoord.addProtocolleringAntwoord(verwerkProtocollering(protocollering));
        }

        return antwoord;
    }

    @Override
    public String getServiceNaam() {
        return this.getClass().getSimpleName();
    }

    private ProtocolleringAntwoord verwerkProtocollering(final Protocollering protocollering) {
        final ProtocolleringAntwoord antwoord = new ProtocolleringAntwoord(protocollering.getActiviteitId());

        if (protocollering.getToegangLeveringsautorisatieCount() > 1) {
            antwoord.setFoutmelding("Er zijn meerdere Toegang leveringsautorisaties gevonden");
        } else if (protocollering.getToegangLeveringsautorisatieId() == null) {
            antwoord.setFoutmelding("Toegang leveringsautorisatie kan niet gevonden worden");
        } else if (protocollering.getDienstId() == null) {
            antwoord.setFoutmelding("Dienst kan niet gevonden worden");
        } else if ("F".equals(protocollering.getNadereBijhoudingsaardCode())) {
            antwoord.setFoutmelding("Persoon is opgeschort met reden 'F'");
        } else if ("W".equals(protocollering.getNadereBijhoudingsaardCode())) {
            antwoord.setFoutmelding("Persoon is opgeschort met reden 'W'");
        } else if (protocollering.getPersoonId() == null) {
            antwoord.setFoutmelding("Persoon kan niet gevonden worden");
        }

        if (antwoord.getFoutmelding() == null) {
            antwoord.setStatus(StatusType.OK);
        } else {
            antwoord.setStatus(StatusType.FOUT);
        }

        if (StatusType.OK.equals(antwoord.getStatus())) {
            leveringsaantekeningService.persisteerLeveringsaantekening(converteer(protocollering));
        }

        return antwoord;
    }

    private Leveringsaantekening converteer(final Protocollering protocollering) {
        final Leveringsaantekening leveringsaantekening =
                new Leveringsaantekening(protocollering.getToegangLeveringsautorisatieId(), protocollering.getDienstId(),
                        Timestamp.valueOf(protocollering.getLaatsteActieTijdstip()), Timestamp.valueOf(protocollering.getStartTijdstip()));
        leveringsaantekening.setDatumTijdAanvangFormelePeriodeResultaat(Timestamp.valueOf(protocollering.getStartTijdstip()));
        leveringsaantekening.setDatumTijdEindeFormelePeriodeResultaat(Timestamp.valueOf(protocollering.getStartTijdstip()));

        final LeveringsaantekeningPersoon leveringsaantekeningPersoon = new LeveringsaantekeningPersoon(leveringsaantekening,
                Long.valueOf(protocollering.getPersoonId()), Timestamp.valueOf(protocollering.getLaatsteActieTijdstip()),
                Timestamp.valueOf(leveringsaantekening.getDatumTijdKlaarzettenLevering().toLocalDateTime()));
        leveringsaantekening.addLeveringsaantekeningPersoon(leveringsaantekeningPersoon);

        return leveringsaantekening;
    }
}
