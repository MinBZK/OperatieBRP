/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import java.util.Collections;
import javax.inject.Inject;
import nl.bzk.brp.blobifier.exceptie.NietUniekeBsnExceptie;
import nl.bzk.brp.blobifier.exceptie.PersoonCacheNietAanwezigExceptie;
import nl.bzk.brp.blobifier.exceptie.PersoonNietAanwezigExceptie;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.business.regels.logging.RegelLoggingUtil;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.logging.FunctioneleMelding;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;


/**
 * Deze stap haalt de PersoonHisVolledig objecten op voor de betrokken personen en plaatst deze op de context.
 */
public class HaalBetrokkenPersoonHisVolledigOpStap
        extends
        AbstractBerichtVerwerkingStap<RegistreerAfnemerindicatieBericht, OnderhoudAfnemerindicatiesBerichtContext, OnderhoudAfnemerindicatiesResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private BlobifierService    blobifierService;

    /**
     * Voer stap uit.
     *
     * @brp.bedrijfsregel VR00054
     * @brp.bedrijfsregel BRLV0008
     * @brp.bedrijfsregel BRLV0009
     *
     * @param onderwerp het onderwerp
     * @param context de context
     * @param afnemerindicatiesResultaat het afnemerindicaties resultaat
     * @return true als stap succesvol is, anders false
     */
    @Regels({ Regel.VR00054, Regel.BRLV0008, Regel.BRLV0009 })
    @Override
    public final boolean voerStapUit(final RegistreerAfnemerindicatieBericht onderwerp,
            final OnderhoudAfnemerindicatiesBerichtContext context,
            final OnderhoudAfnemerindicatiesResultaat afnemerindicatiesResultaat)
    {
        Boolean resultaat = DOORGAAN;
        final BurgerservicenummerAttribuut bsn =
            ((PersoonBericht) onderwerp.getAdministratieveHandeling().getHoofdActie().getRootObject())
                    .getIdentificatienummers().getBurgerservicenummer();

        try {
            final PersoonHisVolledig persoon = blobifierService.leesBlobActievePersoon(bsn);
            context.setPersoonHisVolledig(persoon);
            afnemerindicatiesResultaat.getTeArchiverenPersonenIngaandBericht().add(persoon.getID());

            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0009,
                    RegelLoggingUtil.geefLogmeldingSucces(Regel.BRLV0009));
        } catch (final PersoonNietAanwezigExceptie e) {
            afnemerindicatiesResultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRLV0008));
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0008,
                    RegelLoggingUtil.geefLogmeldingFout(Regel.BRLV0008, Collections.singletonList(bsn.getWaarde()), null));
            resultaat = STOPPEN;
        } catch (final PersoonCacheNietAanwezigExceptie e) {
            // TODO TEAMBRP-2902 Specifieke regel gebruiken zodra deze beschikbaar is
            afnemerindicatiesResultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001));
            resultaat = STOPPEN;
        } catch (final NietUniekeBsnExceptie e) {
            afnemerindicatiesResultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRLV0009));
            LOGGER.info(FunctioneleMelding.LEVERING_BEDRIJFSREGEL_BRLV0009,
                    RegelLoggingUtil.geefLogmeldingFout(Regel.BRLV0009, Collections.singletonList(bsn.getWaarde()), null));
            resultaat = STOPPEN;
        }

        return resultaat;
    }
}
