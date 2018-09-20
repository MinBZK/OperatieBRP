/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.stap;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.levering.mutatieverwerker.excepties.DataNietAanwezigExceptie;
import nl.bzk.brp.levering.mutatieverwerker.model.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatieverwerker.service.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.mutatieverwerker.service.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Deze stap logt een aantal gegevens van de Administratieve Handeling.
 */
public class HaalBetrokkenPersonenVolledigOpStap extends AbstractAdministratieveHandelingVerwerkingStap {

    /**
     * De Constante LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HaalBetrokkenPersonenVolledigOpStap.class);

    /**
     * De persoon volledig repository.
     */
    @Inject
    private PersoonHisVolledigRepository persoonHisVolledigRepository;

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
            final List<Integer> betrokkenPersonenIds = haalBetrokkenPersonenIdsUitContext(context);

            final List<PersoonHisVolledig> betrokkenPersonenVolledig =
                    haalBetrokkenPersonenVolledig(betrokkenPersonenIds);

            zetBetrokkenPersonenVolledigOpContext(context, betrokkenPersonenVolledig);

            return DOORGAAN;
        } catch (Exception exceptie) {
            LOGGER.error(
                    "Het ophalen van de betrokken personen volledig voor de administratieve handeling met id "
                            + onderwerp.getAdministratieveHandelingId() + " is mislukt.", exceptie);
            resultaat.setVerwerkingIsSuccesvol(false);
            return STOPPEN;
        }
    }

    /**
     * Zet de betrokken personen op de stappen context.
     *
     * @param context                   de context
     * @param betrokkenPersonenVolledig de betrokken personen volledig
     */
    private void zetBetrokkenPersonenVolledigOpContext(final AdministratieveHandelingVerwerkingContext context,
                                                       final List<PersoonHisVolledig> betrokkenPersonenVolledig)
    {
        final AdministratieveHandelingVerwerkingContext administratieveHandelingVerwerkingContext =
                (AdministratieveHandelingVerwerkingContext) context;
        administratieveHandelingVerwerkingContext.setBetrokkenPersonenVolledig(betrokkenPersonenVolledig);
    }

    /**
     * Haal de betrokken personen volledig uit de database.
     *
     * @param betrokkenPersonenIds de betrokken personen ids
     * @return de lijst met betrokken personen
     */
    private List<PersoonHisVolledig> haalBetrokkenPersonenVolledig(final List<Integer> betrokkenPersonenIds) {
        final List<PersoonHisVolledig> betrokkenPersonenVolledig =
                persoonHisVolledigRepository.haalPersonenOp(betrokkenPersonenIds);

        if (betrokkenPersonenVolledig == null
                || betrokkenPersonenIds.size() != betrokkenPersonenVolledig.size())
        {
            throw new DataNietAanwezigExceptie("Niet alle persoon volledigs zijn gevonden. Betrokken personen ids: "
                    + betrokkenPersonenIds.toString());
        }

        return betrokkenPersonenVolledig;
    }

    /**
     * Haal de betrokken personen ids uit de stappen context.
     *
     * @param context de context
     * @return de list
     */
    private List<Integer> haalBetrokkenPersonenIdsUitContext(final AdministratieveHandelingVerwerkingContext context) {
        return ((AdministratieveHandelingVerwerkingContext) context).getBetrokkenPersonenIds();
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
