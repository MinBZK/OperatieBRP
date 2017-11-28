/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlChildList;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElementen;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * De AdministratieveHandelingElement uit het bijhoudingsbericht.
 */
@XmlElementen(enumType = AdministratieveHandelingElementSoort.class, methode = "getSoort")
public final class AdministratieveHandelingElement extends AbstractBmrObjecttype {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String COMMUNICATIE_ID = "COMMUNICATIE_ID:";
    private static final String DATABASE_ID = "DATABASE_ID:";
    private static final int JAAR_16 = 16;
    private final AdministratieveHandelingElementSoort soort;
    private final StringElement partijCode;
    private final StringElement toelichtingOntlening;
    @XmlChildList(listElementType = PersoonGegevensElement.class)
    private final List<PersoonGegevensElement> bezienVanuitPersonen;
    @XmlChildList(listElementType = GedeblokkeerdeMeldingElement.class)
    private final List<GedeblokkeerdeMeldingElement> gedeblokkeerdeMeldingen;
    @XmlChildList(listElementType = BronElement.class)
    private final List<BronElement> bronnen;
    @XmlChildList(listElementType = ActieElement.class)
    private final List<ActieElement> acties;

    /**
     * Maakt een AdministratieveHandelingElement object.
     * @param basisAttribuutGroep de basis attribuutgroep de basis attribuutgroep
     * @param soort soort, mag niet null zijn
     * @param partijCode partijCode, mag niet null zijn
     * @param toelichtingOntlening toelichtingOntlening toelichtingOntlening
     * @param bezienVanuitPersonen bezien vanuit personen
     * @param gedeblokkeerdeMeldingen gedeblokkeerdeMeldingen
     * @param bronnen bronnen bronnen
     * @param acties acties acties
     */
    public AdministratieveHandelingElement(
            final Map<String, String> basisAttribuutGroep,
            final AdministratieveHandelingElementSoort soort,
            final StringElement partijCode,
            final StringElement toelichtingOntlening,
            final List<PersoonGegevensElement> bezienVanuitPersonen,
            final List<GedeblokkeerdeMeldingElement> gedeblokkeerdeMeldingen,
            final List<BronElement> bronnen,
            final List<ActieElement> acties) {
        super(basisAttribuutGroep);
        ValidatieHelper.controleerOpNullWaarde(soort, "soort");
        ValidatieHelper.controleerOpNullWaarde(partijCode, "partijCode");
        this.soort = soort;
        this.partijCode = partijCode;
        this.toelichtingOntlening = toelichtingOntlening;
        this.bezienVanuitPersonen = initArrayList(bezienVanuitPersonen);
        this.gedeblokkeerdeMeldingen = initArrayList(gedeblokkeerdeMeldingen);
        this.bronnen = initArrayList(bronnen);

        this.acties = ActieElementSorter.sort(initArrayList(acties));
    }

    /**
     * Geef de waarde van soort.
     * @return soort
     */
    public AdministratieveHandelingElementSoort getSoort() {
        return soort;
    }

    /**
     * Geef de waarde van partijCode.
     * @return partijCode
     */
    public StringElement getPartijCode() {
        return partijCode;
    }

    /**
     * Geef de waarde van toelichtingOntlening.
     * @return toelichtingOntlening
     */
    public StringElement getToelichtingOntlening() {
        return toelichtingOntlening;
    }

    /**
     * Geeft de lijst van bezien vanuit personen terug.
     * @return de lijst van bezien vanuit personen
     */
    public List<PersoonGegevensElement> getBezienVanuitPersonen() {
        return bezienVanuitPersonen;
    }

    /**
     * Geef de waarde van gedeblokkeerdeMeldingen.
     * @return gedeblokkeerdeMeldingen
     */
    public List<GedeblokkeerdeMeldingElement> getGedeblokkeerdeMeldingen() {
        return Collections.unmodifiableList(gedeblokkeerdeMeldingen);
    }

    /**
     * Geef de waarde van bronnen.
     * @return bronnen
     */
    public List<BronElement> getBronnen() {
        return Collections.unmodifiableList(bronnen);
    }

    /**
     * Geef de waarde van acties.
     * @return acties
     */
    public List<ActieElement> getActies() {
        return Collections.unmodifiableList(acties);
    }

    @Bedrijfsregel(Regel.R2577)
    @Override
    protected List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        controleerActiesToegestaan(meldingen);
        if (!acties.isEmpty()) {
            controleerPartijAdministratieveHandeling(meldingen);
            controleerPersonenInActies(meldingen);
            controleerDatumAanvangActieMetPeilDatum(meldingen);
            controleerPersoonIngezetene(meldingen);
            controleerBzvuPersonenMetBetrokkenPersonenInActie(meldingen);
            controleerNationaliteitOpPeilDatum(meldingen);
            controleerToelichtingBijGedeblokeerdeMeldingen(meldingen);
            controleerPartijMetZendendePartij(meldingen);
            controleerFeitDatums(meldingen);
            controleerPartijAdminHandelingIsBijhoudendePartij(meldingen);
            controleerNationaliteitKind(meldingen);
            controleerNationaliteitBijAndereGegevens(meldingen);
            controleerNederlandseNationaliteitKindBijNederlandseOuders(meldingen);
            controleerGeslachtsNaamComponentBijNederlandsKind(meldingen);
            controleerAenBsnBijBroertjesEnZusjes(meldingen);
            controleerAnummerEnBsnPersonenInBericht(meldingen);
            controleerOuders(meldingen);
            controleerLeeftijdOuders(meldingen);
            controleerRelatieBijCorrectieRelatieActies(meldingen);
            controleerNouwkigCuratele(meldingen);
            controleerOfReisdocumentOngeldigWordt(meldingen);
            controleerOnderzoekenBijHoofdpersonen(meldingen);
            controleerMaterieleTijdlijnBijCorrecties(meldingen);
        } else {
            meldingen.add(MeldingElement.getInstance(Regel.R2577, this));
        }
        return meldingen;
    }

    private void controleerMaterieleTijdlijnBijCorrecties(final List<MeldingElement> meldingen) {
        if (!getSoort().isCorrectie()) {
            return;
        }
        for (final BijhoudingPersoon persoon : getHoofdActie().getHoofdPersonen()) {
            MaterieleTijdslijnHelper.controleerTijdslijn(persoon, this, meldingen);
        }
    }

    @Bedrijfsregel(Regel.R2626)
    private void controleerOnderzoekenBijHoofdpersonen(final List<MeldingElement> meldingen) {
        if (!isOnderzoekHandeling() || AdministratieveHandelingElementSoort.AANVANG_ONDERZOEK.equals(getSoort())) {
            for (final BijhoudingPersoon persoon : getHoofdActie().getHoofdPersonen()) {
                if (persoon.heeftLopendOnderzoek()) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2626, persoon.getPersoonElementen().get(0)));
                }
            }
        }
    }

    @Bedrijfsregel(Regel.R2536)
    private void controleerPersoonIngezetene(final List<MeldingElement> meldingen) {
        final ActieElement actieElement = getHoofdActie();
        final List<BijhoudingPersoon> hoofdPersonen = actieElement.getHoofdPersonen();
        int aantalIngezetene = 0;
        if (!hoofdPersonen.isEmpty() && getVerzoekBericht().getZendendePartij().getRollen().contains(Rol.BIJHOUDINGSORGAAN_COLLEGE)) {
            for (final BijhoudingPersoon hoofdPersoon : hoofdPersonen) {
                if (isPersoonIngezetene(hoofdPersoon)) {
                    aantalIngezetene++;
                }
            }
            if (aantalIngezetene == 0) {
                meldingen.add(MeldingElement.getInstance(Regel.R2536, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1790)
    private void controleerOfReisdocumentOngeldigWordt(final List<MeldingElement> meldingen) {
        for (BijhoudingPersoon persoon : getHoofdActie().getHoofdPersonen()) {
            if (persoon.isErEenAanpassingWaardoorReisdocumentVervalt(getPeilDatum().getWaarde())) {
                meldingen.add(MeldingElement.getInstance(Regel.R1790, persoon.getPersoonElementen().get(0)));
            }
        }
    }

    private void controleerOuders(final List<MeldingElement> meldingen) {
        if (getHoofdActie() instanceof ActieMetOuderGegevens) {
            PersoonElement persoonElementKind = ((ActieMetOuderGegevens) getHoofdActie()).getKind();
            final Set<BijhoudingPersoon> ouders = getOuders(persoonElementKind, getPeilDatum().getWaarde()).keySet();
            final Iterator<BijhoudingPersoon> ouderIterator = ouders.iterator();
            while (ouderIterator.hasNext()) {
                final BijhoudingPersoon ouder = ouderIterator.next();
                ouderIterator.remove();
                controleerVerwandschapIngeschrevenePersonen(meldingen, ouders, ouder);
            }
        }
    }

    @Bedrijfsregel(Regel.R1736)
    private void controleerLeeftijdOuders(final List<MeldingElement> meldingen) {
        if (isErkenningHandeling()) {
            for (PersoonElement ouder : getNouwkigs()) {
                if (ouder.heeftPersoonEntiteit() && ouder.getPersoonEntiteit().bepaalLeeftijd(getPeilDatum().getWaarde()) < JAAR_16) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1736, ouder));
                }
            }
        }
    }

    @Bedrijfsregel(Regel.R1733)
    private void controleerVerwandschapIngeschrevenePersonen(final List<MeldingElement> meldingen, final Set<BijhoudingPersoon> ouders,
                                                             final BijhoudingPersoon ouder) {
        if (SoortPersoon.INGESCHREVENE.equals(ouder.getSoortPersoon())) {
            for (BijhoudingPersoon andereOuder : ouders) {
                if (SoortPersoon.INGESCHREVENE.equals(andereOuder.getSoortPersoon()) && Persoon.bestaatVerwantschap(ouder, andereOuder)) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1733, this));
                }
            }
        }
    }

    @Bedrijfsregel(Regel.R2502)
    private void controleerAnummerEnBsnPersonenInBericht(final List<MeldingElement> meldingen) {
        final List<PersoonElement> personen = verzamelUniekePersoonElementenOverAlleActies();

        final PersoonElement persoonElementMetDuplicaat = zijnErDuplicateAnummersOfBsns(personen);
        if (persoonElementMetDuplicaat != null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2502, persoonElementMetDuplicaat));
        }
    }

    private List<PersoonElement> verzamelUniekePersoonElementenOverAlleActies() {
        final List<PersoonElement> personen = new ArrayList<>();
        final List<Long> persoonIDs = new ArrayList<>();
        for (ActieElement actie : getActies()) {
            for (PersoonElement persoon : actie.getPersoonElementen()) {
                Long persoonId = null;
                if (persoon.heeftPersoonEntiteit()) {
                    persoonId = persoon.getPersoonEntiteit().isEersteInschrijving() ? -1 : persoon.getPersoonEntiteit().getId();
                }
                if (persoonId == null || !persoonIDs.contains(persoonId)) {
                    personen.add(persoon);
                    persoonIDs.add(persoonId);
                }
            }
        }
        return personen;
    }

    private PersoonElement zijnErDuplicateAnummersOfBsns(final List<PersoonElement> personen) {
        final List<String> bsns = new ArrayList<>();
        final List<String> anummers = new ArrayList<>();
        final List<Long> persoonIds = new ArrayList<>();
        for (final PersoonElement persoonElement : personen) {
            if (persoonElement.heeftPersoonEntiteit() && persoonIds.contains(persoonElement.getPersoonEntiteit().getId())) {
                continue;
            }
            if (isDuplicaatAnrOfBsn(bsns, anummers, persoonIds, persoonElement)) {
                return persoonElement;
            }
        }
        return null;
    }

    private boolean isDuplicaatAnrOfBsn(final List<String> bsns, final List<String> anummers, final List<Long> persoonIds,
                                        final PersoonElement persoonElement) {
        boolean duplicaatGevonden = false;
        if (persoonElement.heeftPersoonEntiteit() && !persoonElement.getPersoonEntiteit().isEersteInschrijving()) {
            persoonIds.add(persoonElement.getPersoonEntiteit().getId());
            duplicaatGevonden = heeftPersoonIdentificatieNummerDatAlVoorKomt(persoonElement.getPersoonEntiteit(), anummers, bsns);
        } else if (persoonElement.heeftPersoonEntiteit()) {
            for (IdentificatienummersElement identificatie : persoonElement.getPersoonEntiteit().getIndentificatieNummersVanElementen()) {
                duplicaatGevonden = waardeZitInLijstVoegAndersToe(identificatie.getAdministratienummer(), anummers) || waardeZitInLijstVoegAndersToe(
                        identificatie.getBurgerservicenummer(), bsns);
            }
        } else {
            duplicaatGevonden = heeftPersoonElementIdentificatieDatAlVoorkomt(Collections.singletonList(persoonElement), anummers, bsns);
        }
        return duplicaatGevonden;
    }

    private boolean waardeZitInLijstVoegAndersToe(final StringElement element, final List<String> lijst) {
        if (BmrAttribuut.getWaardeOfNull(element) != null) {
            if (lijst.contains(element.getWaarde())) {
                return true;
            }
            lijst.add(element.getWaarde());
        }
        return false;
    }

    @Bedrijfsregel(Regel.R1815)
    private void controleerNationaliteitKind(final List<MeldingElement> meldingen) {
        if (isEersteInschrijving() || isRegistratieOudersNaGeboorteHandeling()) {
            final List<NationaliteitElement> nationaliteitElements = getLijstMetGeregistreerdeMaarNietBeeindigdeNationaliteiten();
            final BijhoudingPersoon kind = getHoofdActie().getHoofdPersonen().get(0);
            boolean isRegistratieStaatloosAanwezig = getActieBySoort(SoortActie.REGISTRATIE_STAATLOOS) != null;
            if (!isRegistratieStaatloosAanwezig && nationaliteitElements.isEmpty() && !kind
                    .heeftNogActueleNationaliteitenOfIndicatieStaatLoos(getHoofdActie().getPeilDatum().getWaarde())) {
                meldingen.add(MeldingElement.getInstance(Regel.R1815, this));
            }
        }
    }


    private List<NationaliteitElement> getLijstMetGeregistreerdeMaarNietBeeindigdeNationaliteiten() {
        final List<NationaliteitElement> nationaliteitenBeeindigingBericht =
                getActiesBySoort(SoortActie.BEEINDIGING_NATIONALITEIT).stream()
                        .filter(nat -> nat.getPersoonElementen().get(0).getNationaliteit().heeftReferentie())
                        .map(nat -> nat.getPersoonElementen().get(0).getNationaliteit().getReferentie()).collect(Collectors.toList());

        return getActiesBySoort(SoortActie.REGISTRATIE_NATIONALITEIT).stream()
                .filter(nat -> !nationaliteitenBeeindigingBericht.contains(nat.getPersoonElementen().get(0).getNationaliteit()))
                .map(nat -> nat.getPersoonElementen().get(0).getNationaliteit())
                .collect(Collectors.toList());
    }

    @Bedrijfsregel(Regel.R2523)
    private void controleerAenBsnBijBroertjesEnZusjes(final List<MeldingElement> meldingen) {
        final ActieElement hoofdActie = getHoofdActie();
        if (hoofdActie instanceof ActieMetOuderGegevens) {
            PersoonElement persoonElementKind = ((ActieMetOuderGegevens) hoofdActie).getKind();
            final Set<BijhoudingPersoon> ouders = getOuders(null, getPeilDatum().getWaarde()).keySet();
            if (!ouders.isEmpty()) {
                final List<Persoon> alleAndereKinderen = new ArrayList<>(verzamelKinderenPerOuder(ouders, persoonElementKind.getPersoonEntiteit()));
                if (zijnErDubbeleAnummersOfBsn(alleAndereKinderen)) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2523, this));
                }
            }
        }
    }

    private boolean zijnErDubbeleAnummersOfBsn(final List<Persoon> personen) {
        final List<String> anummers = new LinkedList<>();
        final List<String> bsn = new LinkedList<>();
        final List<Long> kindIds = new LinkedList<>();
        for (final Persoon persoon : personen) {
            if (persoon != null && !kindIds.contains(persoon.getId()) && !persoon.getPersoonIDHistorieSet().isEmpty()) {
                kindIds.add(persoon.getId());
                if (heeftPersoonIdentificatieNummerDatAlVoorKomt(persoon, anummers, bsn)) {
                    return true;
                }
            }
        }
        final List<PersoonElement> kindPersoonElementen = getHoofdActie().getHoofdPersonen().get(0).getPersoonElementen();
        return heeftPersoonElementIdentificatieDatAlVoorkomt(kindPersoonElementen, anummers, bsn);
    }

    private boolean heeftPersoonElementIdentificatieDatAlVoorkomt(final List<PersoonElement> persoonElementen, final List<String> anummers,
                                                                  final List<String> bsn) {
        for (PersoonElement persoon : persoonElementen) {
            final IdentificatienummersElement persoonIds = persoon.getIdentificatienummers();
            if (persoonIds != null) {
                String persoonANr = persoonIds.getAdministratienummer() != null ? persoonIds.getAdministratienummer().getWaarde() : "onbekendAnr";
                String persoonBSN = persoonIds.getBurgerservicenummer() != null ? persoonIds.getBurgerservicenummer().getWaarde() : "onbekendBsn";
                if (anummers.contains(persoonANr) || bsn.contains(persoonBSN)) {
                    return true;
                }
                anummers.add(persoonANr);
                bsn.add(persoonBSN);
            }
        }
        return false;
    }

    private boolean heeftPersoonIdentificatieNummerDatAlVoorKomt(final Persoon persoon, final List<String> anummers, final List<String> bsn) {
        final PersoonIDHistorie geldigVoorkomen =
                MaterieleHistorie.getGeldigVoorkomenOpPeildatum(persoon.getPersoonIDHistorieSet(), getPeilDatum().getWaarde());
        if (geldigVoorkomen != null) {
            if (geldigVoorkomen.getAdministratienummer() != null && anummers.contains(geldigVoorkomen.getAdministratienummer())) {
                return true;
            }
            if (geldigVoorkomen.getBurgerservicenummer() != null && bsn.contains(geldigVoorkomen.getBurgerservicenummer())) {
                return true;
            }
            anummers.add(geldigVoorkomen.getAdministratienummer());
            bsn.add(geldigVoorkomen.getBurgerservicenummer());
        }
        return false;
    }

    private void controleerGeslachtsNaamComponentBijNederlandsKind(final List<MeldingElement> meldingen) {
        final ActieElement hoofdActie = getHoofdActie();
        if (hoofdActie instanceof ActieMetOuderGegevens) {
            PersoonElement persoonElementKind = ((ActieMetOuderGegevens) hoofdActie).getKind();

            final ActieElement registratieNaamGeslachtActie = getActieBySoort(SoortActie.REGISTRATIE_GESLACHTSNAAMVOORNAAM);
            if (registratieNaamGeslachtActie != null) {
                persoonElementKind = registratieNaamGeslachtActie.getPersoonElement();
            }

            final boolean kindNationaliteitNederlands = persoonElementKind.getPersoonEntiteit().heeftNederlandseNationaliteit(getPeilDatum().getWaarde());
            final Set<BijhoudingPersoon> ouders = getOuders(persoonElementKind, hoofdActie.getPeilDatum().getWaarde()).keySet();

            vergelijkGeslachtsNaamMetOuders(meldingen, persoonElementKind, ouders, kindNationaliteitNederlands);
            vergelijkNamenMetBroertjesEnZusjes(meldingen, persoonElementKind, ouders, kindNationaliteitNederlands);
        }
    }

    @Bedrijfsregel(Regel.R1727)
    @Bedrijfsregel(Regel.R1732)
    private void vergelijkNamenMetBroertjesEnZusjes(final List<MeldingElement> meldingen, final PersoonElement persoonElementKind,
                                                    final Set<BijhoudingPersoon> ouders, final boolean kindNationaliteitNederlands) {
        final List<Persoon> alleAndereKinderen = verzamelKinderenPerOuder(ouders, persoonElementKind.getPersoonEntiteit());
        if (kindNationaliteitNederlands && hebbenKinderenZelfdeOudersEnAndereNaam(persoonElementKind, ouders, alleAndereKinderen)) {
            meldingen.add(MeldingElement.getInstance(Regel.R1727, persoonElementKind));
        }
        for (Persoon anderKind : alleAndereKinderen) {
            final PersoonGeboorteHistorie persoonGeboorteHistorie =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(anderKind.getPersoonGeboorteHistorieSet());
            if (persoonGeboorteHistorie != null
                    && Objects.equals(persoonElementKind.getPersoonEntiteit().getActueleDatumGeboorte(), persoonGeboorteHistorie.getDatumGeboorte())
                    && hebbenKinderenZelfdeGeslachtsNaam(persoonElementKind, anderKind)
                    && hebbenKinderenZelfdeVoornaam(persoonElementKind, anderKind)) {
                meldingen.add(MeldingElement.getInstance(Regel.R1732, persoonElementKind));
                break;
            }
        }
    }

    private boolean hebbenKinderenZelfdeVoornaam(final PersoonElement persoonElementKind, final Persoon anderKind) {
        final PersoonSamengesteldeNaamHistorie geldigVoorkomenOpPeildatum =
                MaterieleHistorie.getGeldigVoorkomenOpPeildatum(anderKind.getPersoonSamengesteldeNaamHistorieSet(), getPeilDatum().getWaarde());
        final List<VoornaamElement> voornamenKind = persoonElementKind.getVoornamen().stream().sorted(VoornaamElement.COMPARATOR).collect(Collectors.toList());
        final String[] voornamenAnderKind =
                geldigVoorkomenOpPeildatum == null || geldigVoorkomenOpPeildatum.getVoornamen() == null ? new String[0]
                        : geldigVoorkomenOpPeildatum.getVoornamen().split(" ");
        if (voornamenKind.size() == voornamenAnderKind.length) {
            for (int i = 0; i < voornamenKind.size(); i++) {
                if (!voornamenKind.get(i).getNaam().getWaarde().equals(voornamenAnderKind[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hebbenKinderenZelfdeOudersEnAndereNaam(final PersoonElement persoonElementKind,
                                                           final Set<BijhoudingPersoon> ouders, final List<Persoon> alleAndereKinderen) {
        for (final Persoon anderKind : alleAndereKinderen) {
            final Set<Betrokkenheid> actueleOuders = anderKind.getActueleOuders();
            if (actueleOuders.size() == ouders.size() && zijnZelfdeOuders(ouders, actueleOuders)
                    && !hebbenKinderenZelfdeGeslachtsNaam(persoonElementKind, anderKind)) {
                return true;
            }
        }
        return false;
    }

    private boolean zijnZelfdeOuders(final Set<BijhoudingPersoon> ouders, final Set<Betrokkenheid> andereOuders) {
        final Set<Betrokkenheid> oudersOmMeeTeVergelijken = new LinkedHashSet<>(andereOuders);
        for (BijhoudingPersoon ouder : ouders) {
            verwijderZelfdeOuder(oudersOmMeeTeVergelijken, ouder);
        }
        return oudersOmMeeTeVergelijken.isEmpty();
    }

    private void verwijderZelfdeOuder(final Set<Betrokkenheid> oudersOmMeeTeVergelijken, final BijhoudingPersoon eigenOuder) {
        final Iterator<Betrokkenheid> iterator = oudersOmMeeTeVergelijken.iterator();
        while (iterator.hasNext()) {
            final Persoon ouderAnderKind = iterator.next().getPersoon();
            if (SoortPersoon.INGESCHREVENE.equals(eigenOuder.getSoortPersoon())
                    && SoortPersoon.INGESCHREVENE.equals(ouderAnderKind.getSoortPersoon())
                    && eigenOuder.getId().equals(ouderAnderKind.getId())) {
                iterator.remove();
            }
        }
    }

    private List<Persoon> verzamelKinderenPerOuder(final Set<BijhoudingPersoon> ouders,
                                                   final BijhoudingPersoon kind) {
        List<Persoon> alleKinderen = new LinkedList<>();
        for (BijhoudingPersoon ouder : ouders) {
            for (Betrokkenheid betrokkenheid : ouder.getActueleKinderen()) {
                if (!Objects.equals(betrokkenheid.getPersoon(), kind.getDelegates().get(0))) {
                    alleKinderen.add(betrokkenheid.getPersoon());
                }
            }
        }
        return alleKinderen;
    }

    private boolean hebbenKinderenZelfdeGeslachtsNaam(final PersoonElement persoonElementKind, final Persoon anderKind) {
        final BijhoudingGeslachtsNaamComponent geslachtsnaamAnderKind =
                new BijhoudingGeslachtsNaamComponent(
                        MaterieleHistorie.getGeldigVoorkomenOpPeildatum(anderKind.getPersoonSamengesteldeNaamHistorieSet(), getPeilDatum().getWaarde()));

        BijhoudingGeslachtsNaamComponent kind =
                new BijhoudingGeslachtsNaamComponent(persoonElementKind.getPersoonEntiteit(), getPeilDatum().getWaarde());
        return geslachtsnaamAnderKind != null
                && kind.heeftZelfdeGeslachtsNaam(geslachtsnaamAnderKind);
    }


    @Bedrijfsregel(Regel.R1726)
    private void vergelijkGeslachtsNaamMetOuders(final List<MeldingElement> meldingen, final PersoonElement persoonElementKind,
                                                 final Set<BijhoudingPersoon> ouders,
                                                 final boolean kindNationaliteitNederlands) {
        BijhoudingGeslachtsNaamComponent
                geslachtsNaamComponentKind =
                new BijhoudingGeslachtsNaamComponent(persoonElementKind.getPersoonEntiteit(), getPeilDatum().getWaarde());

        boolean gelijkMetOuder = false;
        boolean mannelijkeOuderHeeftZelfdeNaamEnAdelijkeTitelOfPredicaat = false;

        for (BijhoudingPersoon ouder : ouders) {
            BijhoudingGeslachtsNaamComponent geslachtsNaamComponentOuder = new BijhoudingGeslachtsNaamComponent(ouder, getPeilDatum().getWaarde());
            final boolean zelfdeGeslachtsNaam = geslachtsNaamComponentOuder.heeftZelfdeGeslachtsNaam(geslachtsNaamComponentKind);
            if (geslachtsNaamComponentOuder.isMan() && zelfdeGeslachtsNaam) {
                mannelijkeOuderHeeftZelfdeNaamEnAdelijkeTitelOfPredicaat =
                        mannelijkeOuderHeeftZelfdeNaamEnAdelijkeTitelOfPredicaat || geslachtsNaamComponentOuder.heeftPredicaatOfAdelijkeTitel();
            }
            gelijkMetOuder = gelijkMetOuder || zelfdeGeslachtsNaam;
        }

        if (kindNationaliteitNederlands && !gelijkMetOuder) {
            meldingen.add(MeldingElement.getInstance(Regel.R1726, persoonElementKind));
        }
        controleerTitelEnPredicaat(meldingen, persoonElementKind, mannelijkeOuderHeeftZelfdeNaamEnAdelijkeTitelOfPredicaat,
                geslachtsNaamComponentKind.heeftPredicaatOfAdelijkeTitel());
    }

    @Bedrijfsregel(Regel.R1731)
    @Bedrijfsregel(Regel.R2543)
    private void controleerTitelEnPredicaat(final List<MeldingElement> meldingen, final PersoonElement kind,
                                            final boolean mannelijkeOuderHeeftAdelijkeTitelOfPredicaat, final boolean kindHeeftAdelijkeTitelOfPredicaat) {
        if (mannelijkeOuderHeeftAdelijkeTitelOfPredicaat && !kindHeeftAdelijkeTitelOfPredicaat) {
            meldingen.add(MeldingElement.getInstance(Regel.R1731, kind));
        } else if (!mannelijkeOuderHeeftAdelijkeTitelOfPredicaat && kindHeeftAdelijkeTitelOfPredicaat) {
            meldingen.add(MeldingElement.getInstance(Regel.R2543, kind));
        }
    }

    @Bedrijfsregel(Regel.R1696)
    private void controleerNationaliteitBijAndereGegevens(final List<MeldingElement> meldingen) {
        for (final List<PersoonElement> persoonElementenPerPersoon : groepeerPersoonElementen().values()) {
            SamengesteldeNaamElement samengesteldeNaamElement = null;
            NationaliteitElement nationaliteitElement = null;
            boolean heeftVoornamenInBericht = false;

            for (final PersoonElement persoonElement : persoonElementenPerPersoon) {
                samengesteldeNaamElement = persoonElement.getSamengesteldeNaam() != null ? persoonElement.getSamengesteldeNaam() : samengesteldeNaamElement;
                heeftVoornamenInBericht = heeftVoornamenInBericht || (!persoonElement.getVoornamen().isEmpty());
                nationaliteitElement = persoonElement.heeftNationaliteiten() ? persoonElement.getNationaliteit() : nationaliteitElement;
            }
            controleerNationaliteitEnNamenreeksBeideInBericht(meldingen, samengesteldeNaamElement, persoonElementenPerPersoon.get(0));
            controleerVoornaam(nationaliteitElement, heeftVoornamenInBericht, persoonElementenPerPersoon.get(0), meldingen);
        }
    }

    private Map<String, List<PersoonElement>> groepeerPersoonElementen() {
        final Map<String, List<PersoonElement>> results = new LinkedHashMap<>();
        for (final ActieElement actie : getActies()) {
            verzamelPersoonElementen(results, actie);
        }
        return results;
    }

    private void verzamelPersoonElementen(final Map<String, List<PersoonElement>> persoonElementen, final ActieElement actie) {
        for (PersoonElement persoonElement : actie.getPersoonElementen()) {
            if (persoonElement.getReferentieId() != null) {
                voegPersoonElementToeAanLijst(persoonElementen, COMMUNICATIE_ID + persoonElement.getReferentieId(), persoonElement);
            } else if (persoonElement.getPersoonEntiteit() != null
                    && persoonElement.getPersoonEntiteit().isEersteInschrijving()) {
                final String communicatieId = persoonElement.getCommunicatieId();
                voegPersoonElementToeAanLijst(persoonElementen, COMMUNICATIE_ID + communicatieId, persoonElement);
            } else if (persoonElement.getObjectSleutel() != null) {
                voegPersoonElementToeAanLijst(persoonElementen, DATABASE_ID + persoonElement.getPersoonEntiteit().getId(), persoonElement);
            }
        }
    }

    private void voegPersoonElementToeAanLijst(final Map<String, List<PersoonElement>> persoonElementen, final String communicatieId,
                                               final PersoonElement persoonElement) {
        if (!persoonElementen.containsKey(communicatieId)) {
            persoonElementen.put(communicatieId, new LinkedList<>());
        }
        persoonElementen.get(communicatieId).add(persoonElement);
    }

    private void controleerNationaliteitEnNamenreeksBeideInBericht(final List<MeldingElement> meldingen,
                                                                   final SamengesteldeNaamElement samengesteldeNaamElement,
                                                                   final PersoonElement persoonElement) {

        final boolean heeftIndicatieNamenreeks;
        if (samengesteldeNaamElement == null) {
            final PersoonSamengesteldeNaamHistorie geldigVoorkomenOpPeildatum =
                    MaterieleHistorie.getGeldigVoorkomenOpPeildatum(persoonElement.getPersoonEntiteit().getPersoonSamengesteldeNaamHistorieSet(),
                            getPeilDatum().getWaarde());
            heeftIndicatieNamenreeks = geldigVoorkomenOpPeildatum != null && geldigVoorkomenOpPeildatum.getIndicatieNamenreeks();
        } else {
            heeftIndicatieNamenreeks = samengesteldeNaamElement.getIndicatieNamenreeks().getWaarde();
        }

        if (persoonElement.getPersoonEntiteit().heeftNederlandseNationaliteit(getPeilDatum().getWaarde()) && heeftIndicatieNamenreeks) {
            meldingen.add(MeldingElement.getInstance(Regel.R1696, this));
        }
    }

    @Bedrijfsregel(Regel.R1697)
    private void controleerVoornaam(final NationaliteitElement nationaliteitElement, final boolean heeftVoornamenInBericht,
                                    final PersoonElement persoonElement, final List<MeldingElement> meldingen) {
        if (nationaliteitElement != null || heeftVoornamenInBericht) {
            boolean isNederlandse =
                    persoonElement.heeftPersoonEntiteit() && persoonElement.getPersoonEntiteit().heeftNederlandseNationaliteit(getPeilDatum().getWaarde());
            if (isNederlandse && !heeftVoornamenInBericht && !bepaalVoornamenOpBasisVanHistorie(persoonElement)) {
                meldingen.add(MeldingElement.getInstance(Regel.R1697, this));
            }
        }
    }

    private boolean bepaalVoornamenOpBasisVanHistorie(final PersoonElement persoonElement) {
        for (final PersoonVoornaam persoonVoornaam : persoonElement.getPersoonEntiteit().getPersoonVoornaamSet()) {
            if (MaterieleHistorie.getGeldigVoorkomenOpPeildatum(persoonVoornaam.getPersoonVoornaamHistorieSet(), getPeilDatum().getWaarde()) != null) {
                return true;
            }
        }
        return false;
    }

    @Bedrijfsregel(Regel.R2352)
    @Bedrijfsregel(Regel.R2526)
    @Bedrijfsregel(Regel.R2652)
    private void controleerActiesToegestaan(final List<MeldingElement> meldingen) {
        if (getSoort() == AdministratieveHandelingElementSoort.WIJZIGING_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE && getActies().size() != 1) {
            meldingen.add(MeldingElement.getInstance(Regel.R2352, this));
        }
        if (!getActiesBySoort(SoortActie.REGISTRATIE_NATIONALITEIT).isEmpty()
                && !getActiesBySoort(SoortActie.REGISTRATIE_STAATLOOS).isEmpty()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2652, this));
        }
        final List<ActieElement> actiecorrectieRegistratieGeboorteGerelateerde = getActiesBySoort(SoortActie.CORRECTIEREGISTRATIE_GEBOORTE_GERELATEERDE);
        final List<ActieElement> actiecorrectieVervalGeboorteGerelateerde = getActiesBySoort(SoortActie.CORRECTIEVERVAL_GEBOORTE_GERELATEERDE);
        if (actiecorrectieRegistratieGeboorteGerelateerde.size() > 1
                || actiecorrectieRegistratieGeboorteGerelateerde.size() != actiecorrectieVervalGeboorteGerelateerde.size()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2526, this));
        }

    }

    @Bedrijfsregel(Regel.R1610)
    private void controleerPartijAdminHandelingIsBijhoudendePartij(final List<MeldingElement> meldingen) {
        final Partij partijAdministratieveHandeling = getDynamischeStamtabelRepository().getPartijByCode(getPartijCode().getWaarde());
        if (partijAdministratieveHandeling != null && getSoort().isControleerZendendePartijIsBijhoudendePartij()) {
            for (final BijhoudingPersoon persoon : getHoofdActie().getHoofdPersonen()) {
                if (partijAdministratieveHandeling.getRollen().contains(Rol.BIJHOUDINGSORGAAN_COLLEGE) && !persoon
                        .isPartijBijhoudingspartij(partijAdministratieveHandeling)) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1610, this));
                }
            }
        }
    }

    @Bedrijfsregel(Regel.R2489)
    private void controleerPartijAdministratieveHandeling(final List<MeldingElement> meldingen) {
        final Partij partijAdministratieveHandeling = getDynamischeStamtabelRepository().getPartijByCode(getPartijCode().getWaarde());
        if (partijAdministratieveHandeling != null && partijAdministratieveHandeling.getRollen().contains(Rol.BIJHOUDINGSORGAAN_COLLEGE) &&
                isRegistratieOudersNaGeboorteHandeling() &&
                !getHoofdActie().getHoofdPersonen().isEmpty()) {
            BijhoudingPersoon persoon = getHoofdActie().getHoofdPersonen().get(0);
            final Partij bijhoudingsPartij = persoon.getActueleBijhoudingspartij();
            final Partij partijGemeenteGeboorte = persoon.getActuelePartijGeboorteGemeente();
            String administratievehandelingPartij = getPartijCode().getWaarde();
            if (!administratievehandelingPartij.equals(bijhoudingsPartij != null ? bijhoudingsPartij.getCode() : null)
                    && !administratievehandelingPartij.equals(partijGemeenteGeboorte != null ? partijGemeenteGeboorte.getCode() : null)) {
                meldingen.add(MeldingElement.getInstance(Regel.R2489, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R2493)
    private void controleerFeitDatums(final List<MeldingElement> meldingen) {
        if (getSoort().isCorrectie()) {
            return;
        }
        final List<BijhoudingPersoon> hoofdPersonen = getHoofdActie().getHoofdPersonen();
        boolean foutGevonden = false;
        for (final ActieElement actie : getActies()) {
            for (final BijhoudingPersoon persoon : hoofdPersonen) {
                final PersoonFeitdatumHelper.Feitdatum
                        feitDatumDieKomtNaOfGelijkMetDitActieElement =
                        new PersoonFeitdatumHelper(persoon).getFeitDatumDieKomtNaOfGelijkMetDitActieElement(actie);
                if (feitDatumDieKomtNaOfGelijkMetDitActieElement != null) {
                    LOGGER.debug("Regel R2493: Er bestaat een feitdatum na deze actie: " + feitDatumDieKomtNaOfGelijkMetDitActieElement.getNaam());
                    foutGevonden = true;
                }
            }
        }
        if (foutGevonden) {
            meldingen.add(MeldingElement.getInstance(Regel.R2493, this));
        }
    }

    @Bedrijfsregel(Regel.R2348)
    private void controleerPartijMetZendendePartij(List<MeldingElement> meldingen) {
        if (!partijCode.getWaarde().equals(getVerzoekBericht().getStuurgegevens().getZendendePartij().getWaarde())) {
            meldingen.add(MeldingElement.getInstance(Regel.R2348, this));
        }
    }

    @Bedrijfsregel(Regel.R2413)
    private void controleerToelichtingBijGedeblokeerdeMeldingen(final List<MeldingElement> meldingen) {
        if (!gedeblokkeerdeMeldingen.isEmpty() && toelichtingOntlening == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2413, this));
        }
    }

    @Bedrijfsregel(Regel.R2214)
    private void controleerBzvuPersonenMetBetrokkenPersonenInActie(final List<MeldingElement> meldingen) {
        if (!bezienVanuitPersonen.isEmpty()) {
            final Map<Long, PersoonElement> bezienVanuitPersonenIds = mapNaarTechnischId();
            final Set<BijhoudingPersoon> alleBetrokkenPersonen = new HashSet<>();
            for (final ActieElement actieElement : getActies()) {
                controleerHoofdPersonen(alleBetrokkenPersonen, actieElement, actieElement.getHoofdPersonen());
            }
            final List<Long> persoonIds = alleBetrokkenPersonen.stream().map(Persoon::getId).collect(Collectors.toList());
            for (final Map.Entry<Long, PersoonElement> bezienVanuitPersoon : bezienVanuitPersonenIds.entrySet()) {
                if (!persoonIds.contains(bezienVanuitPersoon.getKey())) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2214, bezienVanuitPersoon.getValue()));
                }
            }
        }
    }

    private void controleerHoofdPersonen(final Set<BijhoudingPersoon> alleBetrokkenPersonen,
                                         final ActieElement actieElement,
                                         final List<BijhoudingPersoon> hoofdPersonenVoorActie) {
        if (hoofdPersonenVoorActie != null) {
            alleBetrokkenPersonen.addAll(hoofdPersonenVoorActie);
            if (actieElement.heeftInvloedOpGerelateerden()) {
                for (final BijhoudingPersoon hoofdPersoon : hoofdPersonenVoorActie) {
                    alleBetrokkenPersonen.addAll(hoofdPersoon.verzamelGerelateerden());
                }
            }
        }
    }

    private Map<Long, PersoonElement> mapNaarTechnischId() {
        final Map<Long, PersoonElement> resultaat = new HashMap<>();
        for (final PersoonElement bezienVanuitPersoon : bezienVanuitPersonen) {
            resultaat.put(bezienVanuitPersoon.getPersoonEntiteit().getId(), bezienVanuitPersoon);
        }
        return resultaat;
    }

    @Bedrijfsregel(Regel.R1836)
    @Bedrijfsregel(Regel.R2521)
    private void controleerPersonenInActies(final List<MeldingElement> meldingen) {
        final Set<Long> persoonIdsHoofdActie = bepaalHoofdPersoonIds(getHoofdActie().getHoofdPersonen());
        Long persoonIdGerelateerde = null;
        if (isHandelingCorrectieGerelateerdeGegevens()) {
            persoonIdGerelateerde =
                    ((PersoonRelatieElement) getHoofdActie().getPersoonElementen().get(0)).getBetrokkenheden().get(0).getRelatieElement().getBetrokkenheden()
                            .get(0).getPersoon().getPersoonEntiteit().getId();
        }
        persoonIdsHoofdActie.remove(null);
        for (final ActieElement nevenActie : getNevenActies()) {
            final Set<Long> persoonIdsNevenActies = bepaalHoofdPersoonIds(nevenActie.getHoofdPersonen());
            persoonIdsNevenActies.remove(null);
            if (!persoonIdsHoofdActie.containsAll(persoonIdsNevenActies)) {
                meldingen.add(MeldingElement.getInstance(Regel.R1836, nevenActie));
            }
            if (isHandelingCorrectieGerelateerdeGegevens() && !Objects.equals(persoonIdGerelateerde,
                    ((PersoonRelatieElement) nevenActie.getPersoonElementen().get(0)).getBetrokkenheden().get(0).getRelatieElement().getBetrokkenheden()
                            .get(0).getPersoon().getPersoonEntiteit().getId())) {
                meldingen.add(MeldingElement.getInstance(Regel.R2521, nevenActie));
            }
        }
    }

    private Set<Long> bepaalHoofdPersoonIds(final List<BijhoudingPersoon> hoofdPersonen) {
        final Set<Long> result = new HashSet<>();
        if (hoofdPersonen != null) {
            result.addAll(hoofdPersonen.stream().map(persoon -> persoon != null && persoon.getId() != null ? persoon.getId() : null)
                    .collect(Collectors.toList()));
        }
        return result;
    }

    @Bedrijfsregel(Regel.R1882)
    @Bedrijfsregel(Regel.R1883)
    @Bedrijfsregel(Regel.R1250)
    @Bedrijfsregel(Regel.R2784)
    void controleerDatumAanvangActieMetPeilDatum(final List<MeldingElement> meldingen) {

        for (final ActieElement actieElement : getActies()) {
            final DatumElement datumAanvangGeldigheid = actieElement.getDatumAanvangGeldigheid();
            DatumElement andereDag = null;
            Regel regel = null;

            if (isGeboorteHandeling() && !isUitzonderingOpR1250ErkenningNaGeboorte(actieElement)) {
                andereDag = getHoofdActie().getPeilDatum();
                regel = Regel.R1250;
            } else if (getHoofdActie() instanceof AbstractRegistratieEindeHuwelijkOfGpActieElement) {
                andereDag = getHoofdActie().getPeilDatum();
                regel = Regel.R1883;
            } else if (getHoofdActie() instanceof AbstractRegistratieAanvangHuwelijkOfGpActieElement) {
                andereDag = getHoofdActie().getPeilDatum();
                regel = Regel.R1882;
            } else if (isWijzigingPartnerGegevens(actieElement)) {
                andereDag = getHoofdActie().getDatumAanvangGeldigheid();
                regel = Regel.R2784;
            }

            if (regel != null && datumAanvangGeldigheid != null && !datumAanvangGeldigheid.equals(andereDag)) {
                meldingen.add(MeldingElement.getInstance(regel, actieElement));
            }
        }
    }

    private boolean isWijzigingPartnerGegevens(final ActieElement actieElement) {
        return (getSoort().equals(AdministratieveHandelingElementSoort.WIJZIGING_PARTNERGEGEVENS_GEREGISTREERD_PARTNERSCHAP) || getSoort()
                .equals(AdministratieveHandelingElementSoort.WIJZIGING_PARTNERGEGEVENS_HUWELIJK)) && !Objects
                .equals(actieElement.getSoortActie(), SoortActie.REGISTRATIE_GEBOORTE_GERELATEERDE);
    }

    private boolean isUitzonderingOpR1250ErkenningNaGeboorte(final ActieElement actieElement) {
        return isErkenningNaGeboorteHandeling()
                && (actieElement instanceof RegistratieNationaliteitActieElement ||
                actieElement instanceof RegistratieOuderActieElement || actieElement instanceof RegistratieGeslachtsnaamVoornaamActieElement);
    }

    @Bedrijfsregel(Regel.R2486)
    private void controleerRelatieBijCorrectieRelatieActies(final List<MeldingElement> meldingen) {
        BijhoudingRelatie relatieEntiteit = null;
        for (final CorrectieRelatieActieElement correctieRelatieActieElement : getCorrectieRelatieActies()) {
            if (relatieEntiteit != null && !relatieEntiteit.getId().equals(correctieRelatieActieElement.getRelatieEntiteit().getId())) {
                meldingen.add(MeldingElement.getInstance(Regel.R2486, this));
                break;
            } else if (relatieEntiteit == null) {
                relatieEntiteit = correctieRelatieActieElement.getRelatieEntiteit();
            }
        }
    }

    /**
     * Geeft de eerste actie terug horende bij het {@link SoortActie}. Null als deze {@link AdministratieveHandelingElement} niet deze actie bevat.
     * @param soortActie de {@link SoortActie}
     * @return de actie die deze {@link SoortActie} heeft, anders null
     */
    public ActieElement getActieBySoort(final SoortActie soortActie) {
        final List<ActieElement> actiesBySoort = getActiesBySoort(soortActie);
        if (actiesBySoort.isEmpty()) {
            return null;
        }
        return actiesBySoort.get(0);
    }

    /**
     * Geeft alle acties terug horende bij het {@link SoortActie}. Een {@link Collections#emptyList()} als deze {@link AdministratieveHandelingElement} niet
     * deze actie bevat.
     * @param soortActie de {@link SoortActie}
     * @return de actie die deze {@link SoortActie} heeft, anders een lege lijst.
     */
    List<ActieElement> getActiesBySoort(final SoortActie soortActie) {
        return acties.stream().filter(actie -> actie.getSoortActie() == soortActie).collect(Collectors.toList());
    }

    /**
     * @return Geeft de hoofdactie terug
     */
    public ActieElement getHoofdActie() {
        return acties.get(0);
    }

    private List<ActieElement> getNevenActies() {
        if (acties.size() > 1) {
            return acties.subList(1, acties.size());
        } else {
            return new ArrayList<>();
        }
    }

    private List<CorrectieRelatieActieElement> getCorrectieRelatieActies() {
        final List<CorrectieRelatieActieElement> results = new ArrayList<>();
        for (final ActieElement actie : acties) {
            if (actie instanceof CorrectieRelatieActieElement) {
                results.add((CorrectieRelatieActieElement) actie);
            }
        }
        return results;
    }

    private boolean isPersoonIngezetene(final BijhoudingPersoon persoon) {
        final PersoonBijhoudingHistorie geldigVoorkomenOpPeildatum = MaterieleHistorie
                .getGeldigVoorkomenOpPeildatum(persoon.getPersoonBijhoudingHistorieSet(), getHoofdActie().getPeilDatum().getWaarde());
        return persoon.isEersteInschrijving() || (geldigVoorkomenOpPeildatum != null && geldigVoorkomenOpPeildatum.getBijhoudingsaard()
                .equals(Bijhoudingsaard.INGEZETENE));
    }

    @Bedrijfsregel(Regel.R1573)
    @Bedrijfsregel(Regel.R2174)
    private void controleerNationaliteitOpPeilDatum(final List<MeldingElement> meldingen) {
        final ActieElement hoofdActie = getHoofdActie();
        if (hoofdActie instanceof AbstractRegistratieAanvangHuwelijkOfGpActieElement
                || hoofdActie instanceof AbstractRegistratieEindeHuwelijkOfGpActieElement) {
            for (final ActieElement actie : getNevenActies()) {
                controleerNevenactieNationaliteitOpPeilDatum(meldingen, actie);
            }
        }
    }

    private void controleerNevenactieNationaliteitOpPeilDatum(List<MeldingElement> meldingen, ActieElement actie) {
        if (SoortActie.REGISTRATIE_GESLACHTSNAAM.equals(actie.getSoortActie()) && isHoofdPersoonNederlanderOpPeilDatum(actie)) {
            switch (getSoort()) {
                case VOLTREKKING_HUWELIJK_IN_NEDERLAND:
                case AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND:
                    meldingen.add(MeldingElement.getInstance(Regel.R1573, actie));
                    break;
                case ONTBINDING_HUWELIJK_IN_NEDERLAND:
                case BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND:
                case NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND:
                case NIETIGVERKLARING_HUWELIJK_IN_NEDERLAND:
                case OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK:
                    meldingen.add(MeldingElement.getInstance(Regel.R2174, actie));
                    break;
                default:
                    break;
            }
        }
    }

    private Boolean isHoofdPersoonNederlanderOpPeilDatum(final ActieElement actie) {
        final Persoon hoofdPersoon = actie.getHoofdPersonen().get(0);
        for (PersoonNationaliteit persoonNationaliteit : hoofdPersoon.getPersoonNationaliteitSet()) {
            if (persoonNationaliteit.getNationaliteit().isNederlandse()
                    && controleerNationaliteitHistorieOpPeildatum(persoonNationaliteit)) {
                return true;

            }
        }
        return false;
    }

    private boolean controleerNationaliteitHistorieOpPeildatum(PersoonNationaliteit persoonNationaliteit) {
        for (PersoonNationaliteitHistorie his : persoonNationaliteit.getPersoonNationaliteitHistorieSet()) {
            if (DatumUtil.valtDatumBinnenPeriodeStreng(getPeilDatum().getWaarde(), his.getDatumAanvangGeldigheid(), his.getDatumEindeGeldigheid())) {
                return true;
            }
        }
        return false;
    }


    @Bedrijfsregel(Regel.R1691)
    private void controleerNederlandseNationaliteitKindBijNederlandseOuders(final List<MeldingElement> meldingen) {
        if (getHoofdActie() instanceof ActieMetOuderGegevens
                && !AdministratieveHandelingElementSoort.ERKENNING.equals(getSoort())
                && !getHoofdActie().getHoofdPersonen().get(0)
                .heeftNederlandseNationaliteit(getPeilDatum().getWaarde())) {
            PersoonElement persoonElementKind = ((ActieMetOuderGegevens) getHoofdActie()).getKind();
            final Map<BijhoudingPersoon, Integer> oudersMetNederlandseNationaliteit = getOudersMetNederlandseNationaliteit(persoonElementKind);
            for (final Map.Entry<BijhoudingPersoon, Integer> ouder : oudersMetNederlandseNationaliteit.entrySet()) {
                if (getPeilDatum().getWaarde().equals(ouder.getValue()) && !getHoofdActie().getHoofdPersonen().get(0).isMeerderjarig(ouder.getValue())) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1691, this));
                    break;
                }
            }
        }
    }

    private Map<BijhoudingPersoon, Integer> getOudersMetNederlandseNationaliteit(PersoonElement persoonElementKind) {
        final Map<BijhoudingPersoon, Integer> oudersMetNederlandseNationaliteit = new HashMap<>();
        for (Map.Entry<BijhoudingPersoon, Integer> entry : getOuders(persoonElementKind, getPeilDatum().getWaarde()).entrySet()) {
            final BijhoudingPersoon persoon = entry.getKey();
            final Integer peildatumActie = entry.getValue();
            if (persoon.heeftNederlandseNationaliteit(peildatumActie)) {
                oudersMetNederlandseNationaliteit.put(persoon, peildatumActie);
            }
        }
        return oudersMetNederlandseNationaliteit;
    }

    @Bedrijfsregel(Regel.R1729)
    private void controleerNouwkigCuratele(final List<MeldingElement> meldingen) {
        if (isErkenningHandeling()) {
            for (final PersoonElement persoonElement : getNouwkigs()) {
                if (persoonElement.heeftPersoonEntiteit() && persoonElement.getPersoonEntiteit().heeftCurateleIndicatieOpDatum(
                        BmrAttribuut.getWaardeOfNull(getPeilDatum()))) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1729, persoonElement));
                }
            }
        }
    }

    /**
     * Geeft voor alle acties met ouder gegevens van deze handeling de {@link PersoonElement} objecten terug van de ouders met de daarbij horende DAG. daarnaast
     * wordt indien het kind geen eerste inschreiving betreft ook de reeds geregistreerde ouders toegevoegd
     * @param peildatum peildatum voor het bepalen van reeds geregistreerde ouders
     * @param persoonElementKind kind
     * @return map van ouders met bijbehorende DAG (bij reeds geregistreerde ouders wordt de peildatum van de actie toegevoegd.
     */
    public Map<BijhoudingPersoon, Integer> getOuders(final PersoonElement persoonElementKind, final int peildatum) {
        Map<BijhoudingPersoon, Integer> list = new HashMap<>();
        for (final ActieElement actie : getActies()) {
            list.putAll(verzamelOudersUitActie(actie));
        }
        if (persoonElementKind != null && !persoonElementKind.getPersoonEntiteit().isEersteInschrijving()) {
            for (Persoon ouder : persoonElementKind.getPersoonEntiteit().getOuders(peildatum)) {
                list.put(new BijhoudingPersoon(ouder), peildatum);
            }
        }
        return list;
    }

    private Map<BijhoudingPersoon, Integer> verzamelOudersUitActie(final ActieElement actie) {
        final Map<BijhoudingPersoon, Integer> ouderLijst = new HashMap<>();
        if (actie instanceof ActieMetOuderGegevens) {
            List<PersoonElement> ouders = ((ActieMetOuderGegevens) actie).getOuders();
            for (PersoonElement persoonElement : ouders) {
                final Integer dag = actie.getDatumAanvangGeldigheid().getWaarde();
                if (persoonElement.heeftPersoonEntiteit()) {
                    ouderLijst.put(persoonElement.getPersoonEntiteit(), dag);
                } else {
                    final BijhoudingPersoon ouder = ((PersoonGegevensElement) persoonElement).maakPseudoPersoonEntiteit(null, dag);
                    ouderLijst.put(ouder, dag);
                }
            }
        }
        return ouderLijst;
    }

    private List<PersoonElement> getNouwkigs() {
        return getActies().stream().filter(actie -> actie instanceof ActieMetOuderGegevens).map(actie -> ((ActieMetOuderGegevens) actie).getNouwkigs())
                .flatMap(Collection::stream).collect(Collectors.toList());
    }

    /**
     * Geeft de peildatum terug van de hoofdactie.
     * @return de peildatum van de hoofdactie
     */
    public DatumElement getPeilDatum() {
        return getHoofdActie().getPeilDatum();
    }

    /**
     * Geeft aan of deze {@link AdministratieveHandelingElement} een geboorte handeling betreft.
     * @return true als het een geboorte handeling is
     */
    public boolean isGeboorteHandeling() {
        switch (soort) {
            case GEBOORTE_IN_NEDERLAND:
            case GEBOORTE_IN_NEDERLAND_MET_ERKENNING_OP_GEBOORTEDATUM:
            case GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM:
            case GBA_GEBOORTE_IN_NEDERLAND:
            case GBA_GEBOORTE_IN_NEDERLAND_MET_ERKENNING_OP_GEBOORTEDATUM:
            case GBA_GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM:
                return true;
            default:
                return false;
        }
    }

    /**
     * Geeft aan of deze {@link AdministratieveHandelingElement} een geboorte handeling betreft.
     * @return true als het een geboorte handeling is
     */
    boolean isErkenningNaGeboorteHandeling() {
        switch (soort) {
            case GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM:
            case GBA_GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM:
                return true;
            default:
                return false;
        }
    }


    /**
     * Geeft aan of deze {@link AdministratieveHandelingElement} een geboorte handeling betreft.
     * @return true als het een geboorte handeling is
     */
    boolean isErkenningHandeling() {
        switch (soort) {
            case ERKENNING:
            case GEBOORTE_IN_NEDERLAND_MET_ERKENNING_OP_GEBOORTEDATUM:
            case GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM:
            case GBA_GEBOORTE_IN_NEDERLAND_MET_ERKENNING_OP_GEBOORTEDATUM:
            case GBA_GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM:
                return true;
            default:
                return false;
        }
    }


    /**
     * geeft true als erkenning of adoptie is
     */
    boolean isRegistratieOudersNaGeboorteHandeling() {
        switch (soort) {
            case ERKENNING:
            case ADOPTIE:
                return true;
            default:
                return false;
        }

    }


    /**
     * Geeft aan of deze {@link AdministratieveHandelingElement} een eerste inschrijving betreft.
     * @return true als het een eerste inschrijving is
     */
    public boolean isEersteInschrijving() {
        switch (soort) {
            case GEBOORTE_IN_NEDERLAND:
            case GEBOORTE_IN_NEDERLAND_MET_ERKENNING_OP_GEBOORTEDATUM:
            case GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM:
            case GBA_GEBOORTE_IN_NEDERLAND:
            case GBA_GEBOORTE_IN_NEDERLAND_MET_ERKENNING_OP_GEBOORTEDATUM:
            case GBA_GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM:
            case VESTIGING_NIET_INGESCHREVENE:
                return true;
            default:
                return false;
        }
    }

    /**
     * Geeft aan of deze handeling een onderzoek handeling betreft.
     * @return true als dit een onderzoek handeling is, anders false
     */
    boolean isOnderzoekHandeling() {
        switch (soort) {
            case AANVANG_ONDERZOEK:
            case WIJZIGING_ONDERZOEK:
            case REGISTRATIE_NIET_AANGETROFFEN_OP_ADRES:
            case BEEINDIGING_ONDERZOEK:
                return true;
            default:
                return false;
        }
    }

    /**
     * Geeft aan of deze handeling een correctie op de gegevens van een gerelateerde betreft.
     * @return true als dit een onderzoek handeling is, anders false
     */
    boolean isHandelingCorrectieGerelateerdeGegevens() {
        return soort == AdministratieveHandelingElementSoort.CORRECTIE_PARTNERGEGEVENS_GEREGISTREERD_PARTNERSCHAP
                || soort == AdministratieveHandelingElementSoort.CORRECTIE_PARTNERGEGEVENS_HUWELIJK;
    }

    /**
     * Geeft aan of de administratieve handeling een a-symmetrische handeling is.
     * @return true als het een a-symmetrische handeling is.
     */
    public boolean isAsymmetrisch() {
        switch (soort) {
            case WIJZIGING_PARTNERGEGEVENS_GEREGISTREERD_PARTNERSCHAP:
            case WIJZIGING_PARTNERGEGEVENS_HUWELIJK:
            case CORRECTIE_PARTNERGEGEVENS_HUWELIJK:
            case CORRECTIE_PARTNERGEGEVENS_GEREGISTREERD_PARTNERSCHAP:
                return true;
            default:
                return false;
        }
    }
}
