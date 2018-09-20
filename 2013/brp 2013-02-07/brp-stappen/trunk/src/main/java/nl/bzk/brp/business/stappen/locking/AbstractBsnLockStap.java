/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.locking;

import javax.inject.Inject;

import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.business.stappen.AbstractStappenResultaat;
import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Vergrendelbaar;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;


/** BSN Locker service stap, gebruikt de bsnLocker service om een logisch lock op BSN's te verkwijgen en te releasen. */
public abstract class AbstractBsnLockStap<O extends Vergrendelbaar, C extends StappenContext, R extends AbstractStappenResultaat> extends AbstractStap<O, C ,R> {

    //TODO: melding is nog bericht specifiek!
    private static final String FOUTMELDING = "De burgerservicenummers in deze stap geinitieerd door een handeling met ID %d worden gelijktijdig door"
                                                + " een ander bericht geraadpleegd of gemuteerd";
    @Inject
    private BsnLocker           bsnLocker;


    @Deprecated
    public boolean voerVerwerkingsStapUitVoorBericht(final O onderwerp, final C context,
        final R resultaat)
    {
         return voerStapUit(onderwerp, context, resultaat);
    }

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
    public boolean voerStapUit(final O onderwerp, final C context, final R resultaat) {
        boolean status;
        Long referentieId = null;
        try {
            referentieId = context.getReferentieId();
            bsnLocker.getLocks(onderwerp.getReadBsnLocks(), onderwerp.getWriteBsnLocks(), referentieId);
            status = AbstractStap.DOORGAAN;
        } catch (BsnLockerExceptie e) {
            resultaat.voegMeldingToe(creeerMelding(referentieId));
            status = AbstractStap.STOPPEN;
        }
        return status;
    }

    /**
     * Creeert een melding voor de meegegeven BsnLockerExceptie.
     *
     * @param referentieNummer Het ID van het ingaande bericht, dat getoond wordt in de melding om foutdiagnose te
     *            vergemakkelijken.
     *
     * @return Een melding met een omschrijving van de exceptie.
     */
    private Melding creeerMelding(final Long referentieNummer) {
        Melding melding = new Melding(SoortMelding.FOUT, MeldingCode.ALG0001, null, null);
        melding.setOmschrijving(String.format(FOUTMELDING, referentieNummer));
        return melding;
    }


    @Deprecated
    public void naVerwerkingsStapVoorBericht(final O bericht, final C context,
        final R resultaat)
    {
         voerNabewerkingStapUit(bericht, context, resultaat);
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
    public void voerNabewerkingStapUit(final O onderwerp, final C context, final R resultaat) {
        bsnLocker.unLock();
    }
}
