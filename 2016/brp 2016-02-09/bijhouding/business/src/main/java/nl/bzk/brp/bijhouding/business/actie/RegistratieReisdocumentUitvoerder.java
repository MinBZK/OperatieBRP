/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument.ReisdocumentGroepVerwerker;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonReisdocumentHisVolledigImpl;
import org.apache.commons.lang.StringUtils;


/**
 * Actie uitvoerder voor registratie reisdocument.
 */
public final class RegistratieReisdocumentUitvoerder extends AbstractActieUitvoerder<PersoonBericht, PersoonHisVolledigImpl> {

    @Override
    protected void verzamelVerwerkingsregels() {
        if (getBerichtRootObject().getReisdocumenten() != null) {
            for (final PersoonReisdocumentBericht persoonReisdocumentBericht : getBerichtRootObject()
                    .getReisdocumenten())
            {
                voegVerwerkingsregelToe(
                        new ReisdocumentGroepVerwerker(persoonReisdocumentBericht,
                                bepaalPersoonReisdocumentHisVolledigImpl(persoonReisdocumentBericht),
                                getActieModel()));
            }
        }
    }

    /**
     * Maak een nieuwe PersoonReisdocumentHisVolledigImpl of neem een bestaande over.
     * Dit naar aanleiding van of er een technische sleutel in het bericht aanwezig is of niet.
     *
     * @param persoonReisdocumentBericht residocument uit het bericht.
     * @return PersoonReisdocumentHisVolledigImpl
     */
    private PersoonReisdocumentHisVolledigImpl bepaalPersoonReisdocumentHisVolledigImpl(
        final PersoonReisdocumentBericht persoonReisdocumentBericht)
    {
        final PersoonHisVolledigImpl persoonHisVolledig = getModelRootObject();

        PersoonReisdocumentHisVolledigImpl persoonReisdocumentHisVolledig = null;

        if (StringUtils.isNotBlank(persoonReisdocumentBericht.getObjectSleutel())) {
            final int objectSleutel = Integer.parseInt(persoonReisdocumentBericht.getObjectSleutel());
            // Zoek het bestaande reisdocument met de betreffende sleutel.
            for (final PersoonReisdocumentHisVolledigImpl reisdocument : persoonHisVolledig.getReisdocumenten()) {
                if (reisdocument.getID().equals(objectSleutel)) {
                    persoonReisdocumentHisVolledig = reisdocument;
                    break;
                }
            }
            // Als er geen geschikte persoon reisdocument his volledig gevonden is, klopt er iets niet.
            if (persoonReisdocumentHisVolledig == null) {
                throw new IllegalStateException("Ongeldige status: geen reisdocument gevonden "
                        + "met technische sleutel: '" + persoonReisdocumentBericht.getObjectSleutel() + "'");
            }
        } else {
            persoonReisdocumentHisVolledig = new PersoonReisdocumentHisVolledigImpl(
                    getModelRootObject(), persoonReisdocumentBericht.getSoort());
            persoonHisVolledig.getReisdocumenten().add(persoonReisdocumentHisVolledig);
        }

        return persoonReisdocumentHisVolledig;
    }

}

