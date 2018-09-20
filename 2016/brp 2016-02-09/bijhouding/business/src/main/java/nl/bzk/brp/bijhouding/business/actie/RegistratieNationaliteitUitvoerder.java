/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit.NationaliteitVerwerker;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;


/**
 * Actie uitvoerder voor registratie nationaliteit.
 */
public class RegistratieNationaliteitUitvoerder extends AbstractActieUitvoerder<PersoonBericht, PersoonHisVolledig> {

    @Override
    protected void verzamelVerwerkingsregels() {
        if (getBerichtRootObject().getNationaliteiten() != null) {
            for (final PersoonNationaliteitBericht persoonNationaliteitBericht : getBerichtRootObject()
                    .getNationaliteiten())
            {
                voegVerwerkingsregelToe(
                        new NationaliteitVerwerker(persoonNationaliteitBericht,
                                bepaalPersoonNationaliteitHisVolledigImpl(persoonNationaliteitBericht),
                                getActieModel()));
            }
        }
    }

    /**
     * Maak een nieuwe PersoonNationaliteitHisVolledig of neem een bestaande over.
     *
     * @param persoonNationaliteitBericht nationaliteit uit het bericht.
     * @return PersoonNationaliteitHisVolledig
     */
    private PersoonNationaliteitHisVolledigImpl bepaalPersoonNationaliteitHisVolledigImpl(
            final PersoonNationaliteitBericht persoonNationaliteitBericht)
    {
        final PersoonHisVolledigImpl persoonHisVolledig = (PersoonHisVolledigImpl) getModelRootObject();

        PersoonNationaliteitHisVolledigImpl persoonNationaliteitHisVolledig = null;

        // Zoek of er al een persoon nationaliteit bestaat voor deze nationaliteit (code).
        for (final PersoonNationaliteitHisVolledigImpl nation : persoonHisVolledig.getNationaliteiten()) {
            if (nation.getNationaliteit().getWaarde().getCode().equals(
                    persoonNationaliteitBericht.getNationaliteit().getWaarde().getCode()))
            {
                persoonNationaliteitHisVolledig = nation;
                break;
            }
        }

        // Als er geen bestaande persoon nationaliteit gevonden is, maak een nieuwe aan.
        if (persoonNationaliteitHisVolledig == null) {
            persoonNationaliteitHisVolledig = new PersoonNationaliteitHisVolledigImpl(persoonHisVolledig,
                    persoonNationaliteitBericht.getNationaliteit());

            persoonHisVolledig.getNationaliteiten().add(persoonNationaliteitHisVolledig);
        }

        return persoonNationaliteitHisVolledig;
    }
}

