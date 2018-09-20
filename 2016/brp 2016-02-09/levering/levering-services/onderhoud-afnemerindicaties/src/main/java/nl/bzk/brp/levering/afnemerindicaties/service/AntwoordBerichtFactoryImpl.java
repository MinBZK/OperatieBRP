/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBijgehoudenPersoonBericht;
import nl.bzk.brp.model.bericht.ber.AntwoordBericht;
import nl.bzk.brp.model.bericht.ber.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingPlaatsingAfnemerindicatieBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerwijderingAfnemerindicatieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieAntwoordBericht;
import nl.bzk.brp.webservice.business.service.AbstractAntwoordBerichtFactory;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;

import org.springframework.stereotype.Component;

import java.util.Collections;


/**
 * De implementatie van de AntwoordBerichtFactory, deze wordt door de webservice gebruikt voor .
 */
@Component
public class AntwoordBerichtFactoryImpl extends AbstractAntwoordBerichtFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    protected final BerichtResultaatGroepBericht maakInitieelBerichtResultaatGroepBericht(
        final Bericht ingaandBericht,
        final BerichtVerwerkingsResultaat resultaat)
    {
        return new BerichtResultaatGroepBericht();
    }

    @Override
    protected final void vulAntwoordBerichtAan(final BerichtVerwerkingsResultaat berichtVerwerkingsResultaat,
        final Bericht bericht,
        final AntwoordBericht antwoordBericht)
    {
        final BerichtStandaardGroepBericht berichtStandaardGroep = (BerichtStandaardGroepBericht) bericht.getStandaard();
        final AdministratieveHandelingBericht inkomendAdmH = berichtStandaardGroep.getAdministratieveHandeling();
        final BerichtStandaardGroepBericht standaardGroepBericht = new BerichtStandaardGroepBericht();

        final SoortAdministratieveHandeling soortAdministratieveHandeling = inkomendAdmH.getSoort().getWaarde();
        switch (soortAdministratieveHandeling) {
            case PLAATSING_AFNEMERINDICATIE:
                standaardGroepBericht.setAdministratieveHandeling(new HandelingPlaatsingAfnemerindicatieBericht());
                break;
            case VERWIJDERING_AFNEMERINDICATIE:
                standaardGroepBericht.setAdministratieveHandeling(new HandelingVerwijderingAfnemerindicatieBericht());
                break;
            default:
                final String foutmelding = "Mapping van ingaande en uitgaande bericht soorten is niet compleet."
                    + " Geen administratieve handeling bericht gevonden voor: '"
                    + soortAdministratieveHandeling + "'.";
                LOGGER.error(foutmelding);
                throw new IllegalStateException(foutmelding);
        }
        antwoordBericht.setStandaard(standaardGroepBericht);
        antwoordBericht.getAdministratieveHandeling().setPartij(inkomendAdmH.getPartij());
        antwoordBericht.getAdministratieveHandeling().setPartijCode(inkomendAdmH.getPartijCode());
        antwoordBericht.getAdministratieveHandeling().setToelichtingOntlening(inkomendAdmH.getToelichtingOntlening());
        final OnderhoudAfnemerindicatiesResultaat onderhoudAfnemerindicatiesResultaat = (OnderhoudAfnemerindicatiesResultaat) berichtVerwerkingsResultaat;
        if (onderhoudAfnemerindicatiesResultaat.getTijdstipRegistratie() != null) {
            antwoordBericht.getAdministratieveHandeling().setTijdstipRegistratie(
                new DatumTijdAttribuut(onderhoudAfnemerindicatiesResultaat.getTijdstipRegistratie()));
        }
        if (antwoordBericht.getResultaat() != null && antwoordBericht.getResultaat().getVerwerking() != null
                && antwoordBericht.getResultaat().getVerwerking().getWaarde() == Verwerkingsresultaat.GESLAAGD) {
            final AdministratieveHandelingBijgehoudenPersoonBericht administratieveHandelingBijgehoudenPersoonBericht = new AdministratieveHandelingBijgehoudenPersoonBericht();
            administratieveHandelingBijgehoudenPersoonBericht.setPersoon((PersoonBericht) berichtStandaardGroep.getAdministratieveHandeling().getHoofdActie().getRootObject());
            antwoordBericht.getAdministratieveHandeling().setBijgehoudenPersonen(Collections.singletonList(
                    administratieveHandelingBijgehoudenPersoonBericht
            ));
        }
    }

    /**
     * Bepaalt op basis van het ingaande bericht het antwoord bericht dat geretourneerd moet worden.
     *
     * @param ingaandBericht Het ingaande bericht.
     * @return Het antwoord bericht behorende bij het ingaande bericht.
     */
    @Override
    protected final AntwoordBericht maakInitieelAntwoordBerichtVoorInkomendBericht(final Bericht ingaandBericht) {
        final AntwoordBericht antwoordBericht;
        final SoortBericht soortBericht = ingaandBericht.getSoort().getWaarde();
        switch (soortBericht) {
            case LVG_SYN_REGISTREER_AFNEMERINDICATIE:
                antwoordBericht = new RegistreerAfnemerindicatieAntwoordBericht();
                break;
            default:
                final String foutmelding =
                    "Mapping van ingaande en uitgaande bericht soorten is niet compleet voor soort bericht: "
                        + soortBericht;
                LOGGER.error(foutmelding);
                throw new IllegalStateException(foutmelding);
        }
        return antwoordBericht;
    }

}
