/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlChildList;

/**
 * De abstract class voor alle relatie elementen, zoals huwelijk, geregistreerd partnerschap en familierechtelijke
 * betrekken.
 */
public abstract class AbstractRelatieElement extends AbstractBmrGroepReferentie implements RelatieElement {
    private final RelatieGroepElement relatieGroep;
    @XmlChildList(listElementType = BetrokkenheidElement.class)
    private final List<BetrokkenheidElement> betrokkenheden;

    /**
     * Constructor voor AbstractRelatieElement.
     * @param attributen de basis attribuutgroep, mag niet null zijn
     * @param relatieGroep relatie, mag niet null zijn
     * @param betrokkenheden betrokkenheden, mag niet null zijn
     */
    public AbstractRelatieElement(
            final Map<String, String> attributen,
            final List<BetrokkenheidElement> betrokkenheden,
            final RelatieGroepElement relatieGroep) {
        super(attributen);
        this.betrokkenheden = initArrayList(betrokkenheden);
        this.relatieGroep = relatieGroep;
    }

    /************* Methodes van RelatieElement. *************/

    @Override
    public final RelatieGroepElement getRelatieGroep() {
        return relatieGroep;
    }

    @Override
    public final List<BetrokkenheidElement> getBetrokkenheden() {
        return Collections.unmodifiableList(betrokkenheden);
    }

    @Override
    public final Class<BijhoudingRelatie> getEntiteitType() {
        return BijhoudingRelatie.class;
    }

    @Override
    public final BijhoudingRelatie getRelatieEntiteit() {
        return getVerzoekBericht().getEntiteitVoorObjectSleutel(getEntiteitType(), getObjectSleutel());
    }

    /************* Methodes van BmrEntiteit. *************/

    @Override
    public boolean inObjectSleutelIndex() {
        return true;
    }

    /************* Methodes van AbstractBmrGroep. *************/

    @Override
    protected final List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        valideerBetrokkenen(meldingen);
        valideerObjecttypeHierarchie(meldingen);
        meldingen.addAll(valideerSpecifiekeInhoud());
        return meldingen;
    }

    /*  Protected methodes. *************/

    /**
     * Voert validaties uit die specifiek zijn voor specialisaties van relaties zoals huwelijk of familierechtelijke
     * betrekkingen.
     * @return de lijst met meldingen, wanneer er geen fouten zijn wordt een lege lijst geretoureerd.
     */
    protected abstract List<MeldingElement> valideerSpecifiekeInhoud();

    /************* Private methodes *************/

    @Bedrijfsregel(Regel.R1630)
    private void valideerBetrokkenen(final List<MeldingElement> meldingen) {
        final Set<String> betrokkenIndividuen = new HashSet<>(2);
        int countNietIngeschrevene = 0;
        for (BetrokkenheidElement betrokkene : getBetrokkenheden()) {
            final PersoonElement betrokkenPersoonElement = betrokkene.getPersoon();
            if (betrokkenPersoonElement == null) {
                betrokkenIndividuen.add(betrokkene.getSoort().name());
            }else if (betrokkenPersoonElement.heeftPersoonEntiteit()) {
                final Persoon persoon = betrokkenPersoonElement.getPersoonEntiteit();
                if (persoon != null && SoortPersoon.INGESCHREVENE.equals(persoon.getSoortPersoon())) {
                    betrokkenIndividuen.add("" + persoon.getId());
                } else {
                    betrokkenIndividuen.add(betrokkenPersoonElement.getObjectSleutel());
                }
            } else {
                betrokkenIndividuen.add(Integer.toString(countNietIngeschrevene));
                countNietIngeschrevene++;
            }
        }
        if (betrokkenIndividuen.size() != getBetrokkenheden().size()) {
            meldingen.add(MeldingElement.getInstance(Regel.R1630, this));
        }
    }

    @Bedrijfsregel(Regel.R1845)
    private void valideerObjecttypeHierarchie(final List<MeldingElement> meldingen) {
        if (getObjectSleutel() != null && !getBetrokkenheden().isEmpty()) {
            valideerDatAlleBetrokkenhedenVoorkomen(getRelatieEntiteit().getBetrokkenheidSet(), meldingen);
        }
    }

    @Bedrijfsregel(Regel.R1845)
    private void valideerDatAlleBetrokkenhedenVoorkomen(
            final Set<Betrokkenheid> betrokkenheidSet,
            final List<MeldingElement> meldingen) {
        for (final BetrokkenheidElement betrokkenheidElement : getBetrokkenheden()) {
            if (betrokkenheidElement.getObjectSleutel() != null) {
                boolean matchOpBetrokkenheidId = false;
                final long betrokkenheidIdUitBericht = betrokkenheidElement.getBetrokkenheidEntiteit().getId();
                matchOpBetrokkenheidId = valideerIedereBetrokkenheid(betrokkenheidSet,
                        meldingen,
                        betrokkenheidElement,
                        matchOpBetrokkenheidId,
                        betrokkenheidIdUitBericht);
                if (!matchOpBetrokkenheidId) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1845, betrokkenheidElement));
                }
            }
        }
    }

    private boolean valideerIedereBetrokkenheid(final Set<Betrokkenheid> betrokkenheidSet,
                                                final List<MeldingElement> meldingen,
                                                final BetrokkenheidElement betrokkenheidElement,
                                                final boolean matchOpBetrokkenheidId,
                                                final long betrokkenheidIdUitBericht) {
        boolean matchOpBetrokkenheid = matchOpBetrokkenheidId;
        for (final Betrokkenheid betrokkenheidEntiteit : betrokkenheidSet) {
            if (betrokkenheidEntiteit.getId() == betrokkenheidIdUitBericht) {
                matchOpBetrokkenheid = true;
                valideerDatPersonenOvereenkomen(betrokkenheidElement.getPersoon(), betrokkenheidEntiteit.getPersoon(), meldingen);
                break;
            }
        }
        return matchOpBetrokkenheid;
    }

    @Bedrijfsregel(Regel.R1845)
    private void valideerDatPersonenOvereenkomen(
            final PersoonElement persoonElement,
            final Persoon persoonEntiteit,
            final List<MeldingElement> meldingen) {
        if (persoonElement.heeftPersoonEntiteit()) {
            final long persoonIdUitBericht = persoonElement.getPersoonEntiteit().getId();
            final long verwachtePersoonId = persoonEntiteit.getId();
            if (persoonIdUitBericht != verwachtePersoonId) {
                meldingen.add(MeldingElement.getInstance(Regel.R1845, persoonElement));
            }
        }
    }
}
