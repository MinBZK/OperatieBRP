/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder.OuderschapGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.inschrijving.InschrijvingAfleidingDoorGeboorte;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.relatie.FamilierechtelijkeBetrekkingVerwerker;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel;


/**
 * Actie uitvoerder voor registratie door geboorte.
 */
public final class RegistratieGeboorteUitvoerder extends
        AbstractActieUitvoerder<FamilierechtelijkeBetrekkingBericht, FamilierechtelijkeBetrekkingHisVolledigImpl>
{

    private static final Logger    LOGGER = nl.bzk.brp.logging.LoggerFactory.getLogger();
    private PersoonHisVolledigImpl kindHisVolledig;
    private PersoonHisVolledigImpl indicatieOuderUitWieKindIsGeboren;

    @Override
    protected void verzamelVerwerkingsregels() {
        this.voegVerwerkingsregelToe(new FamilierechtelijkeBetrekkingVerwerker(getBerichtRootObject(),
                getModelRootObject(), getActieModel()));

        // Eerst de ouders verwerken zodat de adresgevendeOuder wordt bepaald.
        verwerkOuders(getModelRootObject());
        verwerkKind(getModelRootObject());
    }

    @Override
    protected FamilierechtelijkeBetrekkingHisVolledigImpl maakNieuwRootObjectHisVolledig() {
        return new FamilierechtelijkeBetrekkingHisVolledigImpl();
    }

    /**
     * Verwerk de informatie voor het nieuwe kind.
     *
     * @param afstamming de familierechtelijke betrekking
     */
    private void verwerkKind(final FamilierechtelijkeBetrekkingHisVolledigImpl afstamming) {
        kindHisVolledig = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonBericht kindUitBericht = getBerichtRootObject().getKindBetrokkenheid().getPersoon();
        // Geef aan in de context dat er een nieuw root object (het kind) is aangemaakt.
        getContext().voegAangemaaktBijhoudingsRootObjectToe(kindUitBericht.getCommunicatieID(), kindHisVolledig);

        final KindHisVolledigImpl kindBetrokkenheid = new KindHisVolledigImpl(afstamming, kindHisVolledig);
        afstamming.getBetrokkenheden().add(kindBetrokkenheid);
        kindBetrokkenheid.getBetrokkenheidHistorie().voegToe(new HisBetrokkenheidModel(kindBetrokkenheid, getActieModel()));
        kindHisVolledig.getBetrokkenheden().add(kindBetrokkenheid);

        this.voegVerwerkingsregelsToe(PersoonGroepVerwerkersUtil.bepaalAlleVerwerkingsregels(kindUitBericht,
                kindHisVolledig, getActieModel(), indicatieOuderUitWieKindIsGeboren));
        // Log waarschuwing dat er geen BSN is meegegeven in het bericht
        if (kindUitBericht.getIdentificatienummers() == null || kindUitBericht.getIdentificatienummers().getBurgerservicenummer() == null) {
            LOGGER.warn("Geboorte van kind zonder BSN! {}",
                    (kindUitBericht.getSamengesteldeNaam() != null && kindUitBericht.getSamengesteldeNaam()
                            .getGeslachtsnaamstam() != null) ? kindUitBericht.getSamengesteldeNaam()
                            .getGeslachtsnaamstam().getWaarde() : "");
        }

    }

    /**
     * Maak de betrokkenheden aan. Onderdeel van VR01001 en VR01001a, aanmaken van betrokkenheid.
     *
     * @param afstamming de familierechtelijke betrekking
     */
    private void verwerkOuders(final FamilierechtelijkeBetrekkingHisVolledigImpl afstamming) {
        // Voeg de ouders toe als betrokkenheden.
        for (final OuderBericht ouderBetrokkenheid : getBerichtRootObject().getOuderBetrokkenheden()) {
            final PersoonHisVolledigImpl persoonHisVolledig =
                (PersoonHisVolledigImpl) getContext().zoekHisVolledigRootObject(ouderBetrokkenheid.getPersoon());

            final OuderHisVolledigImpl ouderHisVolledig = new OuderHisVolledigImpl(afstamming, persoonHisVolledig);
            ouderHisVolledig.getBetrokkenheidHistorie().voegToe(new HisBetrokkenheidModel(ouderHisVolledig, getActieModel()));
            persoonHisVolledig.getBetrokkenheden().add(ouderHisVolledig);
            afstamming.getBetrokkenheden().add(ouderHisVolledig);

            if (ouderBetrokkenheid.getOuderschap() != null
                && ouderBetrokkenheid.getOuderschap().getIndicatieOuderUitWieKindIsGeboren().getWaarde())
            {
                indicatieOuderUitWieKindIsGeboren = persoonHisVolledig;
            }

            this.voegVerwerkingsregelToe(new OuderschapGroepVerwerker(ouderBetrokkenheid, ouderHisVolledig, getActieModel()));
        }
    }

    @Override
    protected void verzamelAfleidingsregels() {
        voegAfleidingsregelToe(new InschrijvingAfleidingDoorGeboorte(kindHisVolledig, getActieModel()));
    }
}
