/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.persoon;

import java.util.Arrays;
import javax.inject.Inject;
import nl.bzk.brp.blobifier.exceptie.NietUniekeBsnExceptie;
import nl.bzk.brp.blobifier.exceptie.PersoonCacheNietAanwezigExceptie;
import nl.bzk.brp.blobifier.exceptie.PersoonNietAanwezigExceptie;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.business.regels.logging.RegelLoggingUtil;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;


/**
 * Deze stap haalt de PersoonHisVolledig objecten op voor de betrokken personen en plaatst deze op de context.
 */
public class HaalBetrokkenPersoonHisVolledigOpStap
        extends
        AbstractBerichtVerwerkingStap<GeefSynchronisatiePersoonBericht, SynchronisatieBerichtContext, SynchronisatieResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private BlobifierService    blobifierService;

    /**
     * Voert de stap uit.
     *
     * @brp.bedrijfsregel VR00054
     *
     * @param onderwerp het onderwerp
     * @param context de context
     * @param resultaat het resultaat
     * @return true als stap succesvol is doorlopen, anders false.
     */
    @Regels({ Regel.VR00054, Regel.BRLV0008, Regel.BRLV0009 })
    @Override
    public final boolean voerStapUit(final GeefSynchronisatiePersoonBericht onderwerp,
            final SynchronisatieBerichtContext context, final SynchronisatieResultaat resultaat)
    {
        boolean stapResultaat = DOORGAAN;
        final BurgerservicenummerAttribuut bsn = onderwerp.getZoekcriteriaPersoon().getBurgerservicenummer();

        try {
            final PersoonHisVolledig persoon = blobifierService.leesBlob(bsn);
            context.setPersoonHisVolledig(persoon);
            resultaat.getTeArchiverenPersonenIngaandBericht().add(persoon.getID());

            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0009,
                    RegelLoggingUtil.geefLogmeldingSucces(Regel.BRLV0009));
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0008,
                    RegelLoggingUtil.geefLogmeldingSucces(Regel.BRLV0008));
        } catch (final PersoonNietAanwezigExceptie e) {
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRLV0008));
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0008,
                    RegelLoggingUtil.geefLogmeldingFout(Regel.BRLV0008, Arrays.asList(bsn.getWaarde()), null));
            stapResultaat = STOPPEN;
        } catch (final PersoonCacheNietAanwezigExceptie e) {
            // TODO TEAMBRP-2902 Specifieke regel gebruiken zodra deze beschikbaar is
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001));
            stapResultaat = STOPPEN;
        } catch (final NietUniekeBsnExceptie e) {
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRLV0009));
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0009,
                    RegelLoggingUtil.geefLogmeldingFout(Regel.BRLV0009, Arrays.asList(bsn.getWaarde()), null));
            stapResultaat = STOPPEN;
        }
        return stapResultaat;
    }
}
