/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;

/**
 * Beeindig een huwelijk of geregistreerd partnerschap naar aanleiding van het overlijden van
 * een van beide partners.
 *
 * @brp.bedrijfsregel VR02002a
 */
public class BeeindigHuwelijkGeregistreerdPartnerschapDoorOverlijden
        extends AbstractAfleidingsregel<HuwelijkGeregistreerdPartnerschapHisVolledig>
{

    private final HisPersoonOverlijdenModel overlijden;

    /**
     * Forwarding constructor.
     *
     * @param hgpModel het model
     * @param actie de actie
     * @param overlijden de overlijden groep
     */
    public BeeindigHuwelijkGeregistreerdPartnerschapDoorOverlijden(
            final HuwelijkGeregistreerdPartnerschapHisVolledig hgpModel,
            final ActieModel actie, final HisPersoonOverlijdenModel overlijden)
    {
        super(hgpModel, actie);
        this.overlijden = overlijden;
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR02002a;
    }

    @Override
    public AfleidingResultaat leidAf() {
        final HisRelatieModel hgp = getModel().getRelatieHistorie().getActueleRecord();

        voegPartnerToeAlsExtraBijgehoudenPersoon();

        final RedenEindeRelatie redenBeeindigingRelatie = this.getReferentieDataRepository().
                vindRedenEindeRelatieOpCode(RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_OVERLIJDEN_CODE);
        final HisRelatieModel eindeHGP = new HisRelatieModel(
                getModel(),
                hgp.getDatumAanvang(), hgp.getGemeenteAanvang(), hgp.getWoonplaatsnaamAanvang(),
                hgp.getBuitenlandsePlaatsAanvang(), hgp.getBuitenlandseRegioAanvang(),
                hgp.getOmschrijvingLocatieAanvang(), hgp.getLandGebiedAanvang(),
                new RedenEindeRelatieAttribuut(redenBeeindigingRelatie),
                overlijden.getDatumOverlijden(),
                overlijden.getGemeenteOverlijden(), overlijden.getWoonplaatsnaamOverlijden(),
                overlijden.getBuitenlandsePlaatsOverlijden(), overlijden.getBuitenlandseRegioOverlijden(),
                overlijden.getOmschrijvingLocatieOverlijden(), overlijden.getLandGebiedOverlijden(), getActie());

        getModel().getRelatieHistorie().voegToe(eindeHGP);

        return GEEN_VERDERE_AFLEIDINGEN;
    }

    /**
     * Zoek de partner op uit het hgp en voeg die toe als extra bijgehouden persoon.
     */
    private void voegPartnerToeAlsExtraBijgehoudenPersoon() {
        for (final BetrokkenheidHisVolledig partnerBetrokkenheid : getModel().getBetrokkenheden()) {
            // Als de persoon bij de partner betrokkenheid niet de overleden persoon is,
            // dan wordt die partner persoon toegevoegd als extra bijgehouden persoon.
            if (overlijden.getPersoon().getID() == null
                || !overlijden.getPersoon().getID().equals(partnerBetrokkenheid.getPersoon().getID()))
            {
                this.voegExtraBijgehoudenPersoonToe((PersoonHisVolledigImpl) partnerBetrokkenheid.getPersoon());
            }
        }
    }

}
