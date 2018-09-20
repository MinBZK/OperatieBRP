/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.HuwelijkGeregistreerdPartnerschapVerwerker;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.GeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkGeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel;

/**
 * Actie uitvoerder voor huwelijk en partnerschap.
 */
public class RegistratieHuwelijkGeregistreerdPartnerschapUitvoerder extends AbstractActieUitvoerder<
        HuwelijkGeregistreerdPartnerschapBericht, HuwelijkGeregistreerdPartnerschapHisVolledigImpl>
{

    @Override
    protected void verzamelVerwerkingsregels() {
        this.voegVerwerkingsregelToe(new HuwelijkGeregistreerdPartnerschapVerwerker(getBerichtRootObject(), getModelRootObject(), getActieModel()));
        // Voeg verwerkers voor partner groepen toe (bijv. geslachtsnaam, samengestelde naam).
        for (final BetrokkenheidBericht betrokkenheid : getBerichtRootObject().getBetrokkenheden()) {
            final PersoonBericht persoonBericht = betrokkenheid.getPersoon();
            final PersoonHisVolledigImpl persoonModel = (PersoonHisVolledigImpl) getContext().zoekHisVolledigRootObject(persoonBericht);
            this.voegVerwerkingsregelsToe(
                PersoonGroepVerwerkersUtil.bepaalAlleVerwerkingsregels(betrokkenheid.getPersoon(), persoonModel, getActieModel()));
        }
    }

    @Override
    protected HuwelijkGeregistreerdPartnerschapHisVolledigImpl maakNieuwRootObjectHisVolledig() {
        final HuwelijkGeregistreerdPartnerschapHisVolledigImpl hgpHisVolledig;
        if (isVoltrekkingHuwelijk()) {
            hgpHisVolledig = new HuwelijkHisVolledigImpl();
        } else if (isRegistratiePartnerschap()) {
            hgpHisVolledig = new GeregistreerdPartnerschapHisVolledigImpl();
        } else {
            throw new IllegalStateException("Onverwachte soort administratieve handeling: " + getSoortAdmHand());
        }

        // Stop de juiste betrokkenheden en betrokkenen in de relatie.
        for (final Betrokkenheid betrokkenheidBericht : getBerichtRootObject().getBetrokkenheden()) {
            final PersoonHisVolledigImpl partner = zoekOfMaakPartner(betrokkenheidBericht);

            final PartnerHisVolledigImpl partnerBetrokkenheid = new PartnerHisVolledigImpl(hgpHisVolledig, partner);
            partnerBetrokkenheid.getBetrokkenheidHistorie().voegToe(new HisBetrokkenheidModel(partnerBetrokkenheid, getActieModel()));
            hgpHisVolledig.getBetrokkenheden().add(partnerBetrokkenheid);
            partner.getBetrokkenheden().add(partnerBetrokkenheid);
        }

        return hgpHisVolledig;
    }

    private PersoonHisVolledigImpl zoekOfMaakPartner(final Betrokkenheid betrokkenheidBericht) {
        final PersoonBericht persoonBericht = (PersoonBericht) betrokkenheidBericht.getPersoon();
        final PersoonHisVolledigImpl partner = (PersoonHisVolledigImpl)
                getContext().zoekHisVolledigRootObject(persoonBericht);
        if (moetDummyPartnerMakenTbvPocBijhouding(partner)) {
            return maakDummyPartnerTbvPocBijhoudingEnZetOpContext((BetrokkenheidBericht) betrokkenheidBericht);
        } else {
            return partner;
        }
    }

    private boolean moetDummyPartnerMakenTbvPocBijhouding(final PersoonHisVolledigImpl partner) {
        return partner == null && situatieIsPocBijhouding();
    }

    private boolean situatieIsPocBijhouding() {
        return isSoortAdmHand(SoortAdministratieveHandeling.G_B_A_SLUITING_HUWELIJK_GEREGISTREERD_PARTNERSCHAP);
    }

    private PersoonHisVolledigImpl maakDummyPartnerTbvPocBijhoudingEnZetOpContext(final BetrokkenheidBericht betrokkenheidBericht) {
        // We maken hier een nieuwe persoon.
        // TODO tijdens POC-Relateren (of US die daaruit volgt) moeten we huwelijk netjes ontrelateren
        final PersoonHisVolledigImpl partner = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
        getContext().voegAangemaaktBijhoudingsRootObjectToe(betrokkenheidBericht.getPersoon().getCommunicatieID(),
            partner);
        return partner;
    }

    /**
     * Controleert of de soort administratieve handeling een voltrekking huwelijk is.
     *
     * @return true als soort administratieve handeling een voltrekking huwelijk is
     */
    private boolean isVoltrekkingHuwelijk() {
        // TODO Tbv TEAMBRP-3820 GBA toegevoegd. Tzt nettere oplossing nodig
        return this.isSoortAdmHand(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_BUITENLAND) || situatieIsPocBijhouding();
    }

    /**
     * Controleert of de soort administratieve handeling een registratie partnerschap is.
     *
     * @return true als soort administratieve handeling een registratie partnerschap is
     */
    private boolean isRegistratiePartnerschap() {
        return this.isSoortAdmHand(SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND,
                SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND);
    }
}
