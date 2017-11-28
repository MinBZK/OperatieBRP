/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.algemeen;

import java.time.ZonedDateTime;
import java.util.UUID;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * De generieke implementatie van Bevraging. Deze klasse bepaalt de flow van alle Bevraging services.
 * @param <V> het specifieke {@link BevragingVerzoek}
 * @param <R> het specifieke {@link BevragingResultaat}
 */
public class GeneriekeBevragingVerzoekVerwerker<V extends BevragingVerzoek, R extends BevragingResultaat> implements BevragingVerzoekVerwerker<V> {

    private Bevraging.MaakBerichtService<V, R> maakBerichtService;
    private Bevraging.ArchiveerBerichtService<V, R> archiveerBerichtService;
    private Bevraging.ProtocolleerBerichtService<V, R> protocolleerBerichtService;

    /**
     * Verwerk het bevraging verzoek.
     * @param bevragingVerzoek het bevragingsverzoek
     * @param bevragingCallback bevragingCallback
     */
    @Override
    @Transactional(transactionManager = "masterTransactionManager")
    public final void verwerk(final V bevragingVerzoek, final BevragingCallback<String> bevragingCallback) {
        //berichtresultaat nooit null
        final R berichtResultaat = maakBerichtService.voerStappenUit(bevragingVerzoek);
        //antwoord is nooit null, of hij klapt eruit met een runtimeexceptie
        bevragingCallback.verwerkResultaat(bevragingVerzoek, berichtResultaat);
        final String referentieNummer;
        final ZonedDateTime datumVerzending;
        if (berichtResultaat.getBericht() == null) {
            referentieNummer = UUID.randomUUID().toString();
            datumVerzending = DatumUtil.nuAlsZonedDateTime();
        } else {
            referentieNummer = berichtResultaat.getBericht().getBasisBerichtGegevens().getStuurgegevens().getReferentienummer();
            datumVerzending = berichtResultaat.getBericht().getBasisBerichtGegevens().getStuurgegevens().getTijdstipVerzending();
        }
        final AntwoordBerichtResultaat antwoordBerichtResultaat = new AntwoordBerichtResultaat(bevragingCallback.getResultaat(),
                datumVerzending, referentieNummer, berichtResultaat.getMeldingen());
        archiveerBerichtService.archiveer(bevragingVerzoek, berichtResultaat, antwoordBerichtResultaat);
        protocolleerBerichtService.protocolleer(bevragingVerzoek, berichtResultaat, antwoordBerichtResultaat);
    }

    @Inject
    public void setMaakBerichtService(final Bevraging.MaakBerichtService<V, R> maakBerichtService) {
        this.maakBerichtService = maakBerichtService;
    }

    @Inject
    public void setArchiveerBerichtService(final Bevraging.ArchiveerBerichtService<V, R> archiveerBerichtService) {
        this.archiveerBerichtService = archiveerBerichtService;
    }

    @Inject
    public void setProtocolleerBerichtService(final Bevraging.ProtocolleerBerichtService<V, R> protocolleerBerichtService) {
        this.protocolleerBerichtService = protocolleerBerichtService;
    }
}
