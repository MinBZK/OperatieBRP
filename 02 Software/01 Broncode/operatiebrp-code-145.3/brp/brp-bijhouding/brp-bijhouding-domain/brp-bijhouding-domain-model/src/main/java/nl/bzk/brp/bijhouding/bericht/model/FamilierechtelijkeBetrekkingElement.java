/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.algemeenbrp.util.common.DatumUtil.bepaalDatum;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonOverlijdenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlTransient;

/**
 * Een familierechtelijke betrekking.
 */
//
@XmlElement("familierechtelijkeBetrekking")
public final class FamilierechtelijkeBetrekkingElement extends AbstractRelatieElement {

    private static final char OVERLIJDEN_REDEN_EINDE_RELATIE = 'O';
    private static final int PERIODE_306 = 306;
    private static final int MINIMALE_LEEFTIJD_OUDER = 14;
    private static final int MAXIMALE_LEEFTIJD_OUDER = 50;
    @XmlTransient
    private BetrokkenheidElement bezienVanuit;
    private BijhoudingRelatie nieuweFamilieRechtelijkeBetrekking;

    /**
     * Constructor voor AbstractRelatieElement.
     * @param attributen de basis attribuutgroep, mag niet null zijn
     * @param betrokkenheden betrokkenheden, mag niet null zijn
     * @param relatieGroep relatie, mag niet null zijn
     */
    public FamilierechtelijkeBetrekkingElement(final Map<String, String> attributen,
                                               final RelatieGroepElement relatieGroep, final List<BetrokkenheidElement> betrokkenheden) {
        super(attributen, betrokkenheden, relatieGroep);
    }

    /**
     * Zet de betrokkenheid van waaruit we de familie rechtelijke betrekking benaderen. Dit kan zowel een ouder als een kind zijn. Als de relatie direct wordt
     * benaderd, dan moet deze methode niet gebruikt worden.
     * @param betrokkenheidElement de betrokkenheid waar vanuit de relatie wordt bezien.
     */
    void setBezienVanuit(final BetrokkenheidElement betrokkenheidElement) {
        this.bezienVanuit = betrokkenheidElement;
    }

    @Override
    public BijhoudingRelatie maakRelatieEntiteitEnBetrokkenen(final BRPActie actie, final int datumAanvangGeldigheid) {
        nieuweFamilieRechtelijkeBetrekking = BijhoudingRelatie.decorate(new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
        nieuweFamilieRechtelijkeBetrekking.voegVoorkomenMetAanvangRelatieHistorieEnBetrokkenenToe(this, actie, datumAanvangGeldigheid);
        return nieuweFamilieRechtelijkeBetrekking;
    }

    @Override
    public List<BijhoudingPersoon> getHoofdPersonen() {
        return getBetrokkenheden().stream().filter(betrokkenheid -> betrokkenheid.getSoort() == BetrokkenheidElementSoort.KIND)
                .map(betrokkenheidElement -> betrokkenheidElement.getPersoon().getPersoonEntiteit()).collect(Collectors.toList());
    }

    @Override
    public List<PersoonElement> getPersoonElementen() {
        return getBetrokkenheden().stream().map(BetrokkenheidElement::getPersoon).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public SoortRelatie getSoortRelatie() {
        return SoortRelatie.FAMILIERECHTELIJKE_BETREKKING;
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        final BetrokkenheidElement kindBetrokkenheid = getKindBetrokkenheid();
        if (kindBetrokkenheid != null) {
            final Integer geboorteDatumKind;
            if (kindBetrokkenheid.getPersoon() == null) {
                final BijhoudingPersoon kindBijhoudingPersoon = BijhoudingPersoon.decorate(kindBetrokkenheid.getBetrokkenheidEntiteit().getPersoon());
                geboorteDatumKind = kindBijhoudingPersoon.getActueleDatumGeboorte();
            } else {
                geboorteDatumKind = kindBetrokkenheid.getPersoon().getGeboorte().getDatum().getWaarde();
            }

            if (geboorteDatumKind != null) {
                controleerGeboorteDatumKindEnOuders(meldingen, geboorteDatumKind);
                if (bezienVanuit == null) {
                    controleerKandidaatNouwkig(meldingen, geboorteDatumKind);
                    controleerOuwkig(meldingen, geboorteDatumKind);
                    controleerOverledenOuwkig(meldingen, geboorteDatumKind);
                }
            }
        }
        return meldingen;
    }

    @Bedrijfsregel(Regel.R2455)
    private void controleerOuwkig(final List<MeldingElement> meldingen, final int geboorteDatumKind) {
        if (getBetrokkenheidElementen(BetrokkenheidElementSoort.OUDER).stream()
                .filter(betrokkenheidElement -> betrokkenheidElement.getOuderschap().getIndicatieOuderUitWieKindIsGeboren().getWaarde()).count() != 1) {
            meldingen.add(MeldingElement.getInstance(Regel.R2455, this));
        }
        final BetrokkenheidElement ouderBetrokkenheidElement = getOuderBetrokkenheidElement(true);
        if (ouderBetrokkenheidElement != null && ouderBetrokkenheidElement.getPersoon() != null) {
            controleerOuwkigAdresEnBijhouding(meldingen, geboorteDatumKind, ouderBetrokkenheidElement.getPersoon());
            controleerGeboortedatumAndereKinderen(meldingen, geboorteDatumKind, ouderBetrokkenheidElement.getPersoon());
            controleerLeeftijd(geboorteDatumKind, ouderBetrokkenheidElement.getPersoon(), meldingen);
        }
    }

    @Bedrijfsregel(Regel.R1745)
    private void controleerLeeftijd(final int geboorteDatumKind, final PersoonElement ouwkig, final List<MeldingElement> meldingen) {
        if (ouwkig.heeftPersoonEntiteit()) {
            final PersoonGeboorteHistorie geboorteHistorie =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(ouwkig.getPersoonEntiteit().getPersoonGeboorteHistorieSet());
            final Long jarenTussen = DatumUtil.bepaalJarenTussenDatumsSoepel(geboorteHistorie.getDatumGeboorte(), geboorteDatumKind);
            if (jarenTussen < MINIMALE_LEEFTIJD_OUDER || jarenTussen > MAXIMALE_LEEFTIJD_OUDER) {
                meldingen.add(MeldingElement.getInstance(Regel.R1745, this));
            }
        }
    }

    private void controleerOuwkigAdresEnBijhouding(final List<MeldingElement> meldingen, final int geboorteDatumKind, final PersoonElement ouwkig) {
        boolean heeftNederlandsAdres = false;
        PersoonBijhoudingHistorie geldigBijhoudingsHistorie = null;
        if (ouwkig.heeftPersoonEntiteit()) {
            geldigBijhoudingsHistorie =
                    MaterieleHistorie.getGeldigVoorkomenOpPeildatum(ouwkig.getPersoonEntiteit().getPersoonBijhoudingHistorieSet(), geboorteDatumKind);
            heeftNederlandsAdres =
                    controleerAdresOuwkigEnBepaalOfErNederlandsAdresIs(meldingen, geboorteDatumKind, ouwkig.getPersoonEntiteit().getPersoonAdresSet(), ouwkig);
        }
        controleerBijhoudingOuwkig(meldingen, ouwkig, geldigBijhoudingsHistorie, heeftNederlandsAdres);
    }

    private boolean controleerAdresOuwkigEnBepaalOfErNederlandsAdresIs(final List<MeldingElement> meldingen, final int geboorteDatumKind,
                                                                       final Set<PersoonAdres> addresSet, final PersoonElement persoonElement) {
        boolean bevatNederlandsAdres = false;
        for (final PersoonAdres adres : addresSet) {
            final PersoonAdresHistorie
                    geldigAdres = MaterieleHistorie.getGeldigVoorkomenOpPeildatum(adres.getPersoonAdresHistorieSet(), geboorteDatumKind);
            if (geldigAdres != null) {
                bevatNederlandsAdres = bevatNederlandsAdres || isNederlandsAdres(geldigAdres);
                controleerAdresOuwkig(meldingen, persoonElement, geldigAdres);

            }
        }
        return bevatNederlandsAdres;
    }

    private boolean isNederlandsAdres(final PersoonAdresHistorie geldigAdres) {
        return geldigAdres != null && geldigAdres.getLandOfGebied() != null && Objects
                .equals(geldigAdres.getLandOfGebied().getCode(), LandOfGebied.CODE_NEDERLAND);
    }


    @Bedrijfsregel(Regel.R1743)
    private void controleerBijhoudingOuwkig(final List<MeldingElement> meldingen, final PersoonElement persoonElement,
                                            final PersoonBijhoudingHistorie geldigBijhoudingsHistorie, final Boolean heeftNederlandsAdres) {
        final boolean isIngezetene = geldigBijhoudingsHistorie != null
                && Bijhoudingsaard.INGEZETENE.equals(geldigBijhoudingsHistorie.getBijhoudingsaard());
        if (!persoonElement.heeftPersoonEntiteit() || !heeftNederlandsAdres || !isIngezetene) {
            meldingen.add(MeldingElement.getInstance(Regel.R1743, persoonElement));
        }
    }

    @Bedrijfsregel(Regel.R1753)
    @Bedrijfsregel(Regel.R1725)
    private void controleerAdresOuwkig(final List<MeldingElement> meldingen, final PersoonElement persoonElement, final PersoonAdresHistorie geldigAdres) {
        if (geldigAdres != null && geldigAdres.getDatumEindeGeldigheid() != null) {
            meldingen.add(MeldingElement.getInstance(Regel.R1753, persoonElement));
        }
        if (geldigAdres != null && SoortAdres.BRIEFADRES.equals(geldigAdres.getSoortAdres())) {
            meldingen.add(MeldingElement.getInstance(Regel.R1725, persoonElement));
        }
    }


    @Bedrijfsregel(Regel.R1746)
    /* binnen 306 dagen, maar periode bepaling is inclusief-exclusief: vandaar -1 */
    private void controleerGeboortedatumAndereKinderen(final List<MeldingElement> meldingen, final int geboorteDatumKind, final PersoonElement ouwkig) {
        if (ouwkig.heeftPersoonEntiteit()) {
            for (Betrokkenheid kind : ouwkig.getPersoonEntiteit().getActueleKinderen()) {
                final PersoonGeboorteHistorie geboorteHistorie =
                        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(kind.getPersoon().getPersoonGeboorteHistorieSet());
                if (geboorteHistorie != null && geboorteHistorie.getDatumGeboorte() != geboorteDatumKind
                        && isOnvoldoendeRuimteTussenGeboorteDatums(geboorteHistorie.getDatumGeboorte(), geboorteDatumKind)) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1746, this));
                    break;
                }
            }
        }
    }

    private boolean isOnvoldoendeRuimteTussenGeboorteDatums(final int geboorteDatumBestaandKind, final int geboorteDatumNieuwKind) {
        final LocalDate localDateNieuwKind = DatumUtil.vanIntegerNaarLocalDate(geboorteDatumNieuwKind);
        final LocalDate localDateBestaandKind = DatumUtil.vanIntegerNaarLocalDate(geboorteDatumBestaandKind);
        long aantalDagen = ChronoUnit.DAYS.between(localDateBestaandKind, localDateNieuwKind);
        aantalDagen = aantalDagen < 0 ? aantalDagen * -1 : aantalDagen;
        return aantalDagen < PERIODE_306;
    }

    @Bedrijfsregel(Regel.R1721)
    private void controleerKandidaatNouwkig(final List<MeldingElement> meldingen, final int geboorteDatumKind) {
        BetrokkenheidElement betrokkenheidElementOuwkig = getOuderBetrokkenheidElement(true);
        BetrokkenheidElement betrokkenheidElementNouwkig = getOuderBetrokkenheidElement(false);

        if (betrokkenheidElementOuwkig != null && betrokkenheidElementNouwkig == null && betrokkenheidElementOuwkig.getPersoon() != null) {
            final Set<Betrokkenheid> nietVervallenPartners = betrokkenheidElementOuwkig.getPersoon().getPersoonEntiteit().getActuelePartners();

            for (Betrokkenheid partner : nietVervallenPartners) {
                final RelatieHistorie actueleRelatieHistorie = partner.getRelatie().getActueleRelatieHistorie();
                final Integer startPeriodeNouwkig = bepaalDatum(geboorteDatumKind, PERIODE_306);
                if (actueleRelatieHistorie.getRedenBeeindigingRelatie() == null || (
                        OVERLIJDEN_REDEN_EINDE_RELATIE == actueleRelatieHistorie.getRedenBeeindigingRelatie().getCode()
                                && DatumUtil.valtDatumBinnenPeriodeStreng(actueleRelatieHistorie.getDatumEinde(), startPeriodeNouwkig, geboorteDatumKind))) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1721, this));
                }
            }
        }
    }

    @Bedrijfsregel(Regel.R1468)
    private void controleerGeboorteDatumKindEnOuders(final List<MeldingElement> meldingen, final int geboorteDatumKind) {
        for (final BetrokkenheidElement ouderBetrokkenheid : getBetrokkenheidElementen(BetrokkenheidElementSoort.OUDER)) {
            final PersoonElement ouderPersoonElement = ouderBetrokkenheid.getPersoon();

            final Integer geboorteDatumOuder;
            if (ouderPersoonElement != null) {
                if (ouderPersoonElement.heeftPersoonEntiteit()) {
                    final PersoonGeboorteHistorie persoonGeboorteHistorie = FormeleHistorieZonderVerantwoording
                            .getActueelHistorieVoorkomen(ouderPersoonElement.getPersoonEntiteit().getPersoonGeboorteHistorieSet());
                    geboorteDatumOuder = persoonGeboorteHistorie.getDatumGeboorte();
                } else {
                    geboorteDatumOuder = ouderPersoonElement.getGeboorte().getDatum().getWaarde();
                }

                if (geboorteDatumKind <= geboorteDatumOuder) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1468, this));
                    break;
                }
            }
        }
    }

    @Bedrijfsregel(Regel.R2480)
    private void controleerOverledenOuwkig(final List<MeldingElement> meldingen, final int geboorteDatumKind) {
        BetrokkenheidElement ouwkigElement = getOuderBetrokkenheidElement(true);

        if (ouwkigElement != null && ouwkigElement.getPersoon() != null && ouwkigElement.getPersoon().heeftPersoonEntiteit()) {
            final BijhoudingPersoon ouwkig = ouwkigElement.getPersoon().getPersoonEntiteit();
            if (NadereBijhoudingsaard.OVERLEDEN.equals(ouwkig.getNadereBijhoudingsaard())) {
                final PersoonOverlijdenHistorie overlijdenHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(
                        ouwkig.getPersoonOverlijdenHistorieSet());
                if (overlijdenHistorie != null && overlijdenHistorie.getDatumOverlijden() < geboorteDatumKind) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2480, this));
                }
            }
        }
    }


    public List<PersoonElement> getOuders() {
        return getBetrokkenheden().stream().filter(betrokkenheid -> betrokkenheid.getSoort() == BetrokkenheidElementSoort.OUDER)
                .map(BetrokkenheidElement::getPersoon).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Geeft een lijst van {@link BetrokkenheidElement} terug, die als soort de meegegeven {@link BetrokkenheidElementSoort} hebben.
     * @param soort {@link BetrokkenheidElementSoort} die de betrokkenheidElementen moeten hebben.
     * @return lijst van BetrokkenheidElementen.
     */
    List<BetrokkenheidElement> getBetrokkenheidElementen(final BetrokkenheidElementSoort soort) {
        return getBetrokkenheden().stream().filter(element -> element.getSoort() == soort).collect(Collectors.toList());
    }

    /**
     * Geeft de {@link BijhoudingPersoon} terug van de ouder. Of dit de ouwkig of de nouwkig is, hangt van de meegegeven parameter af.
     * @param isOuwkig true als de ouwkig gezocht wordt.
     * @return de {@link BijhoudingPersoon} behorende bij de OUWKIG
     */
    BetrokkenheidElement getOuderBetrokkenheidElement(final boolean isOuwkig) {
        return getBetrokkenheidElementen(BetrokkenheidElementSoort.OUDER).stream()
                .filter(betrokkenheidElement -> isOuwkig == betrokkenheidElement.getOuderschap().getIndicatieOuderUitWieKindIsGeboren().getWaarde()).findFirst()
                .orElse(null);
    }

    private BetrokkenheidElement getKindBetrokkenheid() {
        final BetrokkenheidElement result;
        if (bezienVanuit != null && bezienVanuit.getSoort() == BetrokkenheidElementSoort.KIND) {
            result = bezienVanuit;
        } else {
            final List<BetrokkenheidElement> kinderen = getBetrokkenheidElementen(BetrokkenheidElementSoort.KIND);
            result = kinderen.isEmpty() ? null : kinderen.get(0);
        }
        return result;
    }

    @Override
    public FamilierechtelijkeBetrekkingElement getReferentie() {
        return (FamilierechtelijkeBetrekkingElement) getGroep();
    }

    @Override
    public boolean verwijstNaarBestaandEnJuisteType() {
        return getReferentieId() == null || getGroep() instanceof FamilierechtelijkeBetrekkingElement;
    }

    /**
     * geeft de bijhoudings relatie terug.
     * @return nieuweFamilieRechtelijkeBetrekking
     */
    BijhoudingRelatie getBijhoudingRelatie() {
        final BijhoudingRelatie result;
        if (getReferentieId() != null) {
            result = getReferentie().getBijhoudingRelatie();
        } else if (getObjectSleutel() != null) {
            result = getRelatieEntiteit();
        } else if (nieuweFamilieRechtelijkeBetrekking != null) {
            result = nieuweFamilieRechtelijkeBetrekking;
        } else {
            result = null;
        }
        return result;
    }
}
