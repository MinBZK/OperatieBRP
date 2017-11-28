/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Abstracte class voor de gezamelijke controle van wijzigen gerelateerde gegevens.
 */
public abstract class AbstractWijzigenGerelateerdeGegevensActieElement extends AbstractActieElement {
    private PersoonRelatieElement persoonRelatieElement;


    /**
     * Maakt een {@link AbstractWijzigenGerelateerdeGegevensActieElement} object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param persoonRelatieElement de persoon
     */
    public AbstractWijzigenGerelateerdeGegevensActieElement(final Map<String, String> basisAttribuutGroep,
                                                            final DatumElement datumAanvangGeldigheid,
                                                            final DatumElement datumEindeGeldigheid,
                                                            final List<BronReferentieElement> bronReferenties,
                                                            final PersoonRelatieElement persoonRelatieElement) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties);
        ValidatieHelper.controleerOpNullWaarde(persoonRelatieElement, "persoonRelatieElement");
        this.persoonRelatieElement = persoonRelatieElement;
    }

    /**
     * Geeft de persoon terug.
     * @return de persoon
     */
    public PersoonRelatieElement getPersoonRelatieElement() {
        return persoonRelatieElement;
    }

    @Override
    public final List<BijhoudingPersoon> getHoofdPersonen() {
        return Collections.singletonList(persoonRelatieElement.getPersoonEntiteit());
    }

    @Override
    public final List<PersoonElement> getPersoonElementen() {
        return Collections.singletonList(persoonRelatieElement);
    }

    @Override
    public final DatumElement getPeilDatum() {
        // Zolang WIT nog geen besluit heeft genomen met de Peildatum, heb ik (JPvR, in overleg FM) dit gelijk getrokken met correcties.
        return new DatumElement(DatumUtil.vandaag());
    }

    @Override
    @Bedrijfsregel(Regel.R2653)
    @Bedrijfsregel(Regel.R2654)
    protected final List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();

        final BetrokkenheidElement ikBetrokkenheidElement = persoonRelatieElement.getBetrokkenheden().get(0);
        final RelatieHistorie actueleRelatieHistorie;
        final BetrokkenheidOuderHistorie actueleOuderHistorie;

        final BijhoudingBetrokkenheid ikBetrokkenheidEntiteit = ikBetrokkenheidElement.getBetrokkenheidEntiteit();
        final boolean isFamilierechtelijkeBetrekking = SoortBetrokkenheid.PARTNER != ikBetrokkenheidEntiteit.getSoortBetrokkenheid();

        if (!isFamilierechtelijkeBetrekking) {
            actueleRelatieHistorie = ikBetrokkenheidElement.getRelatieElement().getRelatieEntiteit().getActueleRelatieHistorie();
            actueleOuderHistorie = null;
        } else {
            final FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekking = ikBetrokkenheidElement.getFamilierechtelijkeBetrekking();
            actueleRelatieHistorie = familierechtelijkeBetrekking.getRelatieEntiteit().getActueleRelatieHistorie();
            actueleOuderHistorie = getOuderHistorie(ikBetrokkenheidEntiteit, familierechtelijkeBetrekking);
        }

        if (actueleRelatieHistorie == null || (isFamilierechtelijkeBetrekking && actueleOuderHistorie == null)) {
            meldingen.add(MeldingElement.getInstance(Regel.R2654, this));
        } else if (getDatumAanvangGeldigheid() != null) {
            final Integer datumAanvangGeldigheid = getDatumAanvangGeldigheid().getWaarde();
            if ((isFamilierechtelijkeBetrekking && datumAanvangGeldigheid < actueleOuderHistorie.getDatumAanvangGeldigheid()) || (
                    !isFamilierechtelijkeBetrekking && datumAanvangGeldigheid < actueleRelatieHistorie.getDatumAanvang())) {
                meldingen.add(MeldingElement.getInstance(Regel.R2653, this));
            }
        }

        return meldingen;
    }

    private BetrokkenheidOuderHistorie getOuderHistorie(final BijhoudingBetrokkenheid ikBetrokkenheidEntiteit,
                                                        final FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekking) {
        final BetrokkenheidOuderHistorie actueleOuderHistorie;
        if (SoortBetrokkenheid.OUDER == ikBetrokkenheidEntiteit.getSoortBetrokkenheid()) {
            actueleOuderHistorie =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(ikBetrokkenheidEntiteit.getBetrokkenheidOuderHistorieSet());
        } else {
            final BijhoudingBetrokkenheid partnerBetrokkenheidEntiteit = familierechtelijkeBetrekking.getBetrokkenheden().get(0).getBetrokkenheidEntiteit();
            actueleOuderHistorie =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(partnerBetrokkenheidEntiteit.getBetrokkenheidOuderHistorieSet());
        }
        return actueleOuderHistorie;
    }

    @Override
    public final boolean heeftInvloedOpGerelateerden() {
        return true;
    }

    BijhoudingPersoon bepaalPartner(final Long ikPersoonId, final BijhoudingRelatie relatie) {
        for (final Betrokkenheid betrokkenheid : relatie.getBetrokkenheidSet()) {
            if (!ikPersoonId.equals(betrokkenheid.getPersoon().getId())) {
                return BijhoudingPersoon.decorate(betrokkenheid.getPersoon());
            }
        }
        throw new IllegalStateException("Er kon geen partner persoon gevonden worden");
    }
}
