/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;

/**
 * De base class voor huwelijk of geregeistreerd partnerschap uit het bijhoudingsbericht.
 */
public abstract class AbstractHuwelijkOfGpElement extends AbstractRelatieElement implements HuwelijkOfGpElement {

    private static final int MINIMALE_LEEFTIJD = 18;

    /**
     * Constructor voor AbstractHuwelijkOfGpElement.
     * @param attributen de basis attribuutgroep, mag niet null zijn
     * @param relatieGroep relatie, mag niet null zijn
     * @param betrokkenheden betrokkenheden, mag niet null zijn
     */
    public AbstractHuwelijkOfGpElement(
            final Map<String, String> attributen,
            final List<BetrokkenheidElement> betrokkenheden,
            final RelatieGroepElement relatieGroep) {
        super(attributen, betrokkenheden, relatieGroep);
    }

    /************* Methodes van HuwelijkOfGpElement. *************/

    @Override
    public final void werkPseudoPersoonInRelatieEntiteitBij(final BRPActie actie, final BijhoudingVerzoekBericht bericht,
                                                            final DatumElement datumAanvangGeldigheid) {
        if (!getBetrokkenheden().isEmpty()) {
            final BetrokkenheidElement betrokkenheidElement = getBetrokkenheden().get(0);
            final PersoonElement betrokkenPersoonElement = betrokkenheidElement.getPersoon();
            final long betrokkenheidId = betrokkenheidElement.getBetrokkenheidEntiteit().getId();
            final long betrokkenPersoonId = betrokkenPersoonElement.getPersoonEntiteit().getId();
            final BijhoudingPersoon betrokkenPersoon = getRelatieEntiteit().getPseudoPersoonVoorIds(betrokkenheidId, betrokkenPersoonId);
            if (betrokkenPersoon != null) {
                betrokkenPersoon
                        .voegPersoonSamengesteldeNaamHistorieToe(betrokkenPersoonElement.getSamengesteldeNaam(), actie, datumAanvangGeldigheid.getWaarde());
            }
        }
    }

    /************* Methodes van RelatieElement. *************/

    @Override
    public final BijhoudingRelatie maakRelatieEntiteitEnBetrokkenen(final BRPActie actie, final int datumAanvangGeldigheid) {
        final BijhoudingRelatie result = BijhoudingRelatie.decorate(new Relatie(getSoortRelatie()));
        result.voegVoorkomenMetAanvangRelatieHistorieEnBetrokkenenToe(this, actie, datumAanvangGeldigheid);
        return result;
    }

    @Override
    public final List<BijhoudingPersoon> getHoofdPersonen() {
        final List<BijhoudingPersoon> hoofdPersonen = new ArrayList<>();
        for (final BijhoudingPersoon bijhoudingPersoon : getPersonen()) {
            if (bijhoudingPersoon.isPersoonIngeschrevene()) {
                hoofdPersonen.add(bijhoudingPersoon);
            }
        }
        return hoofdPersonen;
    }

    @Override
    public final List<PersoonElement> getPersoonElementen() {
        final List<PersoonElement> results = new ArrayList<>();
        for (final BetrokkenheidElement betrokkenheid : getBetrokkenheden()) {
            if(betrokkenheid.getPersoon()!=null) {
                results.add(betrokkenheid.getPersoon());
            }
        }
        return results;
    }

    /************* Methodes van AbstractRelatieElement. *************/

    @Override
    protected final List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        valideerIndicatiesMinimaleLeeftijdEnAndereRelatiesVanBetrokkenen(meldingen);
        valideerRegistratieBetrokkenGemeenteVoorHuwelijkGp(meldingen);
        return meldingen;
    }

    /************* Private methodes *************/

    private List<BijhoudingPersoon> getPersonen() {
        final List<BijhoudingPersoon> personen = new ArrayList<>();
        for (final PersoonElement persoonElement : getPersoonElementen()) {
            if (persoonElement.heeftPersoonEntiteit()) {
                personen.add(persoonElement.getPersoonEntiteit());
            }
        }
        return personen;
    }

    @Bedrijfsregel(Regel.R1861)
    private void valideerRegistratieBetrokkenGemeenteVoorHuwelijkGp(final List<MeldingElement> meldingen) {
        final String administratieveHandelingPartijCode = getVerzoekBericht().getAdministratieveHandeling().getPartijCode().getWaarde();
        final StringElement gemeenteCode = bepaalGemeenteCode();
        if (gemeenteCode != null && !zijnGemeentesGelijk(administratieveHandelingPartijCode, gemeenteCode.getWaarde())) {
            final List<Persoon> personen = new LinkedList<>();
            if (getRelatieEntiteit() != null) {
                // Beeindigen van een HGP
                personen.addAll(
                        getRelatieEntiteit().getActueleBetrokkenheidSet(SoortBetrokkenheid.PARTNER).stream().map(Betrokkenheid::getPersoon)
                                .collect(Collectors.toList()));
            } else {
                // Aangaan van een HGP
                personen.addAll(getBetrokkenheden().stream()
                        .filter(betrokkenheidElement -> betrokkenheidElement.getPersoon().heeftPersoonEntiteit())
                        .map(betrokkenheidElement -> betrokkenheidElement.getPersoon().getPersoonEntiteit()).collect(Collectors.toList()));
            }

            boolean partijGelijkAanFeitPartij = false;
            final Iterator<Persoon> persoonIterator = personen.iterator();
            while (!partijGelijkAanFeitPartij && persoonIterator.hasNext()) {
                final Persoon persoon = persoonIterator.next();
                final PersoonBijhoudingHistorie bijhoudingHistorie =
                        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonBijhoudingHistorieSet());
                partijGelijkAanFeitPartij =
                        bijhoudingHistorie != null && administratieveHandelingPartijCode.equals(bijhoudingHistorie.getPartij().getCode());
            }

            if (!partijGelijkAanFeitPartij) {
                meldingen.add(MeldingElement.getInstance(Regel.R1861, this));
            }
        }
    }


    private StringElement bepaalGemeenteCode() {
        StringElement gemeenteCode = null;
        if (getRelatieGroep() != null) {
            if (getRelatieGroep().getGemeenteAanvangCode() != null) {
                gemeenteCode = getRelatieGroep().getGemeenteAanvangCode();
            } else if (getRelatieGroep().getGemeenteEindeCode() != null) {
                gemeenteCode = getRelatieGroep().getGemeenteEindeCode();
            }
        }
        return gemeenteCode;
    }

    private boolean zijnGemeentesGelijk(final String administratieveHandelingPartij, final String gemeenteCode) {
        final Gemeente administratieveHandelingGemeente = getDynamischeStamtabelRepository().getGemeenteByPartijcode(administratieveHandelingPartij);
        final Gemeente gemeente = getDynamischeStamtabelRepository().getGemeenteByGemeentecode(gemeenteCode);
        return gemeente != null && administratieveHandelingGemeente != null
                && (gemeenteCode.equals(administratieveHandelingGemeente.getCode())
                || gemeente.isPartijVoortzettendeGemeentePartij(administratieveHandelingGemeente.getPartij()));
    }

    private void valideerIndicatiesMinimaleLeeftijdEnAndereRelatiesVanBetrokkenen(final List<MeldingElement> meldingen) {
        for (BetrokkenheidElement betrokkene : getBetrokkenheden()) {
            if (betrokkene.getPersoon().heeftPersoonEntiteit()) {
                final BijhoudingPersoon persoon = betrokkene.getPersoon().getPersoonEntiteit();
                controleerMinimaleLeeftijd(meldingen, betrokkene, persoon);
                if (persoon != null && SoortPersoon.INGESCHREVENE.equals(persoon.getSoortPersoon())) {
                    controleerIndicaties(meldingen, betrokkene, persoon);
                    controleerAndereRelaties(meldingen, betrokkene, persoon);
                }
            }
        }
    }

    @Bedrijfsregel(Regel.R1869)
    private void controleerAndereRelaties(final List<MeldingElement> meldingen, final BetrokkenheidElement betrokkene, final BijhoudingPersoon persoon) {
        final DatumElement datumAanvang = getRelatieGroep() == null ? null : getRelatieGroep().getDatumAanvang();
        if (datumAanvang != null && !persoon.getActueleHgpRelaties(datumAanvang.getWaarde()).isEmpty()) {
            meldingen.add(MeldingElement.getInstance(Regel.R1869, betrokkene));
        }
    }

    private void controleerIndicaties(final List<MeldingElement> meldingen, final BetrokkenheidElement betrokkene, final Persoon persoonEntiteit) {
        if (persoonEntiteit != null) {
            for (PersoonIndicatie indicatie : persoonEntiteit.getPersoonIndicatieSet()) {
                controleerOnderCuratele(meldingen, betrokkene, indicatie);
            }
        }
    }

    @Bedrijfsregel(Regel.R1867)
    private void controleerOnderCuratele(final List<MeldingElement> meldingen, final BetrokkenheidElement betrokkene, final PersoonIndicatie indicatie) {
        if (SoortIndicatie.ONDER_CURATELE.equals(indicatie.getSoortIndicatie())) {
            final Set<PersoonIndicatieHistorie> historieSet = indicatie.getPersoonIndicatieHistorieSet();
            for (PersoonIndicatieHistorie indicatieHistorie : historieSet) {
                if (getRelatieGroep().getDatumAanvang() != null
                        && DatumUtil.valtDatumBinnenPeriodeStreng(
                        getRelatieGroep().getDatumAanvang().getWaarde(),
                        indicatieHistorie.getDatumAanvangGeldigheid(),
                        indicatieHistorie.getDatumEindeGeldigheid())) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1867, betrokkene));
                    break;
                }
            }
        }
    }

    @Bedrijfsregel(Regel.R1865)
    private void controleerMinimaleLeeftijd(
            final List<MeldingElement> meldingen,
            final BetrokkenheidElement betrokkene,
            final Persoon persoonEntiteit) {
        Integer geboortedatum = null;
        if (persoonEntiteit != null) {
            geboortedatum = persoonEntiteit.getDatumGeboorte();
        } else if (betrokkene.getPersoon().getGeboorte() != null) {
            geboortedatum = betrokkene.getPersoon().getGeboorte().getDatum().getWaarde();
        }
        final DatumElement datumAanvang = getRelatieGroep() == null ? null : getRelatieGroep().getDatumAanvang();
        if (geboortedatum != null
                && datumAanvang != null
                && DatumUtil.bepaalJarenTussenDatumsSoepel(geboortedatum, datumAanvang.getWaarde()) < MINIMALE_LEEFTIJD) {
            meldingen.add(MeldingElement.getInstance(Regel.R1865, betrokkene));
        }
    }

    @Override
    public BmrGroep getReferentie() {
        return null;
    }

    @Override
    public boolean verwijstNaarBestaandEnJuisteType() {
        return getReferentieId() == null || getGroep() instanceof AbstractHuwelijkOfGpElement;
    }

}
