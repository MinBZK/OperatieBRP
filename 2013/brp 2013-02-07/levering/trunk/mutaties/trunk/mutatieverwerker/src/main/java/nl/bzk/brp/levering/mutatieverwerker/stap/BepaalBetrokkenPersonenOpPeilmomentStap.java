/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.stap;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.levering.mutatieverwerker.excepties.ConverteerExceptie;
import nl.bzk.brp.levering.mutatieverwerker.model.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatieverwerker.service.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.levering.mutatieverwerker.stap.util.PersoonConverteerder;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Persoon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Deze stap converteert de betrokken PersonenVolledig naar Betrokken Personen en plaatst deze op de context.
 */
public class BepaalBetrokkenPersonenOpPeilmomentStap extends AbstractAdministratieveHandelingVerwerkingStap {

    /** De Constante LOGGER. */
    private static final Logger             LOGGER
        = LoggerFactory.getLogger(BepaalBetrokkenPersonenOpPeilmomentStap.class);

    private final PersoonConverteerder persoonConverteerder = new PersoonConverteerder();

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.business.stappen.Stap#voerStapUit(nl.bzk.brp.model.basis.ObjectType,
     * nl.bzk.brp.business.stappen.StappenContext, nl.bzk.brp.business.stappen.StappenResultaat)
     */
    @Override
    public boolean voerStapUit(final AdministratieveHandelingMutatie onderwerp, final AdministratieveHandelingVerwerkingContext context,
            final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        try {
            final List<PersoonHisVolledig> betrokkenPersonenVolledig = haalBetrokkenPersonenVolledigUitContext(context);

            List<Persoon> betrokkenPersonen =
                bepaalBetrokkenPersonenOpPeilmoment(betrokkenPersonenVolledig, context);

            zetBetrokkenPersonenOpContext(context, betrokkenPersonen);

            return DOORGAAN;
        } catch (Exception exceptie) {
            LOGGER.error(
                    "Het ophalen van de betrokken personen voor de administratieve handeling met id "
                        + onderwerp.getAdministratieveHandelingId() + " is mislukt.", exceptie);
            resultaat.setVerwerkingIsSuccesvol(false);
            return STOPPEN;
        }
    }

    /**
     * Haal de betrokken personen volledig uit de stappen context.
     *
     * @param context de context
     * @return de list
     */
    private List<PersoonHisVolledig> haalBetrokkenPersonenVolledigUitContext(final AdministratieveHandelingVerwerkingContext context) {
        return ((AdministratieveHandelingVerwerkingContext) context).getBetrokkenPersonenVolledig();
    }

    /**
     * Bepaal betrokken personen op peilmoment.
     *
     * @param betrokkenPersonenVolledig de betrokken personen volledig
     * @param context de context
     * @return de lijst met betrokken personen
     */
    private List<Persoon> bepaalBetrokkenPersonenOpPeilmoment(
            final List<PersoonHisVolledig> betrokkenPersonenVolledig, final AdministratieveHandelingVerwerkingContext context)
    {
        DatumTijd peilmoment = getPeilmoment(context);

        List<Persoon> betrokkenPersonen = new ArrayList<Persoon>();
        for (PersoonHisVolledig persoonVolledig : betrokkenPersonenVolledig) {
            Persoon persoon =
                persoonConverteerder.bepaalBetrokkenPersoonOpPeilmoment(persoonVolledig, peilmoment);

            if (persoon == null) {
                throw new ConverteerExceptie("Kon persoon model niet bepalen voor persoon met id "
                        + persoonVolledig.getID() + " op peildatum " + peilmoment.getWaarde());
            }

            betrokkenPersonen.add(persoon);
        }

        return betrokkenPersonen;
    }

    /**
     * Haalt een peilmoment op uit de stappen context via de Administratieve Handeling.
     *
     * @param context de context
     * @return peilmoment
     */
    private DatumTijd getPeilmoment(final StappenContext context) {
        return ((AdministratieveHandelingVerwerkingContext) context).getHuidigeAdministratieveHandeling()
                .getTijdstipOntlening();
    }

    /**
     * Zet de betrokken personen op de stappen context.
     *
     * @param context de context
     * @param betrokkenPersonen de betrokken personen volledig
     */
    private void
    zetBetrokkenPersonenOpContext(final StappenContext context, final List<Persoon> betrokkenPersonen)
    {
        final AdministratieveHandelingVerwerkingContext administratieveHandelingVerwerkingContext =
            (AdministratieveHandelingVerwerkingContext) context;
        administratieveHandelingVerwerkingContext.setBetrokkenPersonen(betrokkenPersonen);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.business.stappen.Stap#voerNabewerkingStapUit(nl.bzk.brp.model.basis.ObjectType,
     * nl.bzk.brp.business.stappen.StappenContext, nl.bzk.brp.business.stappen.StappenResultaat)
     */
    @Override
    public void voerNabewerkingStapUit(final AdministratieveHandelingMutatie onderwerp, final AdministratieveHandelingVerwerkingContext context,
            final AdministratieveHandelingVerwerkingResultaat resultaat)
    {

    }

}
