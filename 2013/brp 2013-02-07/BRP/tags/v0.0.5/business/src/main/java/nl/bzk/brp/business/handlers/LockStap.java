/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers;

import javax.inject.Inject;

import nl.bzk.brp.binding.BRPBericht;
import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.binding.SoortMelding;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.service.BsnLocker;


/** BSN Locker service stap, gebruikt de bsnLocker service om een logisch lock op BSN's te verkwijgen en te releasen. */
public class LockStap extends AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat> {

    private static final String FOUTMELDING = "De burgerservicenummers in bericht met ID %d worden gelijktijdig door"
                                                + " een ander bericht geraadpleegd of gemuteerd";
    @Inject
    private BsnLocker           bsnLocker;

    /**
     * {@inheritDoc}
     * <p/>
     * <p>
     * Plaatst locks op de BSNs die in het bericht bevraagd/gemuteerd worden. Het resultaat geeft aan of de locking
     * succesvol was en dus de volgende stap kan worden genomen, of dat de stap faalde en dus de verdere verwerking
     * gestopt dient te worden.
     * </p>
     * <p>
     * <b>Let op:</b> Deze stap plaatst de locks en voegt de betreffende connectie toe aan de ThreadLocal om verder in
     * het systeem nog gebruikt te worden en om in de navwerwerking van deze stap netjes deze connectie te kunnen
     * sluiten. In de naverwerking dient de connectie dan ook gesloten te worden en van de ThreadLocal verwijderd te
     * worden.
     * </p>
     */
    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final BRPBericht bericht, final BerichtContext context,
        final BerichtResultaat resultaat)
    {
        boolean status;
        try {
            // TODO (Eric Jan Malotaux, 2012-04-19): Als het bericht ID bekend is, meegeven als extra argument aan
            // onderstaande method, voor fout rapportage.
            bsnLocker.getLocks(bericht.getReadBsnLocks(), bericht.getWriteBsnLocks(), null);
            status = AbstractBerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING;
        } catch (BsnLockerExceptie e) {
            resultaat.voegMeldingToe(creeerMelding(e, context.getIngaandBerichtId()));
            status = AbstractBerichtVerwerkingsStap.STOP_VERWERKING;
        }
        return status;
    }

    /**
     * Creeert een melding voor de meegegeven BsnLockerExceptie.
     *
     * @param e De Exceptie waarvoor een melding wordt gemaakt.
     * @param berichtId TODO
     * @return Een melding met een omschrijving van de exceptie.
     */
    private Melding creeerMelding(final BsnLockerExceptie e, final Long berichtId) {
        Melding melding = new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001);
        melding.setOmschrijving(String.format(FOUTMELDING, berichtId));
        return melding;
    }

    /**
     * {@inheritDoc}
     * <p>
     * De naverwerking van deze stap dient er voor te zorgen dat de connectie (en de locks) die tijdens de verwerking
     * van de stap zijn aangemaakt, netjes worden opgeruimd. Hiervoor roept het de {@link BsnLocker#unLock()} methode
     * aan, daar deze correct de connectie uit de ThreadLocal haalt en netjes afsluit (inclusief commit of rollback in
     * het geval van fouten).
     * </p>
     * <p>
     * Deze methode wordt na uitvoering van deze stap (en alle volgende stappen in het proces) uitgevoerd.
     * </p>
     */
    @Override
    public void naVerwerkingsStapVoorBericht(final BRPBericht bericht, final BerichtContext context,
        final BerichtResultaat resultaat)
    {
        bsnLocker.unLock();
    }

}
