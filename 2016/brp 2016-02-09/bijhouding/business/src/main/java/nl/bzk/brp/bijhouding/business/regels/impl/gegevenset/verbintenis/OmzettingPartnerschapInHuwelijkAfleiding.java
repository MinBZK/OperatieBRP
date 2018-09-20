/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkGeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;

/**
 * Implementatie voor de afleidingsregel voor het afleiden van een huwelijk uit de omzetting van een partnerschap in
 * een huwelijk.
 *
 * @brp.bedrijfsregel VR02002c
 */
public class OmzettingPartnerschapInHuwelijkAfleiding
    extends AbstractAfleidingsregel<HuwelijkGeregistreerdPartnerschapHisVolledigImpl>
{

    /**
     * Forwarding constructor.
     *
     * @param relatie de relatie
     * @param actie actie
     */
    public OmzettingPartnerschapInHuwelijkAfleiding(final HuwelijkGeregistreerdPartnerschapHisVolledigImpl relatie,
        final ActieModel actie)
    {
        super(relatie, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR02002c;
    }

    @Override
    public AfleidingResultaat leidAf() {
        final HisRelatieModel partnerschap = getModel().getRelatieHistorie().getActueleRecord();

        final HuwelijkHisVolledigImpl huwelijk = new HuwelijkHisVolledigImpl();

        final HisRelatieModel nieuwHuwelijk =
            new HisRelatieModel(
                huwelijk,
                partnerschap.getDatumEinde(),
                partnerschap.getGemeenteEinde(),
                partnerschap.getWoonplaatsnaamEinde(),
                null, null, null,
                partnerschap.getLandGebiedEinde(),
                null, null, null, null, null, null, null, null, getActie());

        huwelijk.getRelatieHistorie().voegToe(nieuwHuwelijk);

        // Stop de juiste betrokkenheden en betrokkenen in de relatie.
        for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : partnerschap.getRelatie().getBetrokkenheden()) {
            final PersoonHisVolledigImpl partner;
            if (betrokkenheidHisVolledig.getPersoon().getSoort().getWaarde() == SoortPersoon.NIET_INGESCHREVENE) {
                partner = cloneNietIngeschrevene(betrokkenheidHisVolledig.getPersoon());
                this.voegExtraAangemaakteNietIngeschreveneToe(partner);
            } else {
                partner = (PersoonHisVolledigImpl) betrokkenheidHisVolledig.getPersoon();
            }

            final PartnerHisVolledigImpl partnerBetrokkenheid = new PartnerHisVolledigImpl(huwelijk, partner);
            huwelijk.getBetrokkenheden().add(partnerBetrokkenheid);
            partner.getBetrokkenheden().add(partnerBetrokkenheid);
        }

        return GEEN_VERDERE_AFLEIDINGEN;
    }

    /**
     * Maakt een kopie van de niet ingeschrevene, daar de niet ingeschrevene niet in twee relaties mag zitten.
     *
     * @param nietIngeschrevene de niet ingeschrevene persoon die gekopieerd dient te worden.
     * @return een nieuwe niet ingeschrevene
     */
    private PersoonHisVolledigImpl cloneNietIngeschrevene(final PersoonHisVolledig nietIngeschrevene) {
        final PersoonHisVolledigImpl persoon =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));

        if (nietIngeschrevene.getPersoonGeboorteHistorie().getActueleRecord() != null) {
            final HisPersoonGeboorteModel geboorte = nietIngeschrevene.getPersoonGeboorteHistorie().getActueleRecord();
            persoon.getPersoonGeboorteHistorie().voegToe(
                new HisPersoonGeboorteModel(persoon, geboorte, getActie()));
        }

        if (nietIngeschrevene.getPersoonSamengesteldeNaamHistorie().getActueleRecord() != null) {
            final HisPersoonSamengesteldeNaamModel samengesteldeNaam =
                nietIngeschrevene.getPersoonSamengesteldeNaamHistorie().getActueleRecord();
            persoon.getPersoonSamengesteldeNaamHistorie().voegToe(
                new HisPersoonSamengesteldeNaamModel(persoon, samengesteldeNaam, getActie(), getActie()));
        }

        if (nietIngeschrevene.getPersoonGeslachtsaanduidingHistorie().getActueleRecord() != null) {
            final HisPersoonGeslachtsaanduidingModel geslachtsaanduiding =
                nietIngeschrevene.getPersoonGeslachtsaanduidingHistorie().getActueleRecord();
            persoon.getPersoonGeslachtsaanduidingHistorie().voegToe(
                new HisPersoonGeslachtsaanduidingModel(persoon, geslachtsaanduiding, getActie(), getActie()));
        }

        return persoon;
    }
}
