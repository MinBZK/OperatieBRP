/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractDelegatePersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonMigratieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerblijfsrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperking;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperkingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Deze decorator class voegt bijhoudingsfunctionaliteit toe aan de persoon entiteit.
 */
public class BijhoudingPersoon extends AbstractDelegatePersoon implements BijhoudingEntiteit {
    private static final long serialVersionUID = 1;

    private final transient Map<VoorkomenSleutel, FormeleHistorie> idOpKopieHistorieMap = new HashMap<>();

    private final Timestamp tijdstipVoorlaatsteWijziging;
    private BijhoudingSituatie bijhoudingSituatie;
    private Partij bijhoudingspartijVoorBijhoudingsplan;
    private final boolean isEersteInschrijving;
    private final transient List<PersoonElement> persoonElementen = new ArrayList<>();
    private transient NationaliteitHelper nationaliteitHelper;
    private boolean isResultaatVanOntrelateren = false;

    /**
     * Maakt een nieuwe BijhoudingPersoon aan voor nieuw personen met {@link SoortPersoon#INGESCHREVENE}.
     */
    public BijhoudingPersoon() {
        super(false, new Persoon(SoortPersoon.INGESCHREVENE));
        isEersteInschrijving = true;
        tijdstipVoorlaatsteWijziging = null;
    }

    /**
     * Maakt een nieuwe BijhoudingPersoon object.
     * @param delegate de persoon die moet worden uigebreid met bijhoudingsfunctionaliteit
     */
    public BijhoudingPersoon(final Persoon delegate) {
        super(false, delegate);
        isEersteInschrijving = false;
        tijdstipVoorlaatsteWijziging = bepaalDatumTijdVoorlaatsteWijziging(delegate);
    }

    private NationaliteitHelper getNationaliteitHelper() {
        if (nationaliteitHelper == null) {
            nationaliteitHelper = new NationaliteitHelper(this);
        }
        return nationaliteitHelper;
    }

    /**
     * Maakt een nieuwe BijhoudingPersoon object.
     * @param delegate de persoon die moet worden uigebreid met bijhoudingsfunctionaliteit
     * @return een persoon met bijhoudingsfunctionaliteit
     */
    public static BijhoudingPersoon decorate(final Persoon delegate) {
        if (delegate == null) {
            return null;
        }
        return new BijhoudingPersoon(delegate);
    }

    /**
     * De indicatie dat deze persoon is ontstaan als gevolg van het ontrelateer proces.
     * @return true als dat zo is, anders false
     */
    public final boolean isResultaatVanOntrelateren() {
        return isResultaatVanOntrelateren;
    }

    /**
     * Zet hiermee de indicatie dat deze persoon is ontstaan als gevolg van het ontrelateer proces.
     */
    public final void setIsResultaatVanOntrelateren() {
        this.isResultaatVanOntrelateren = true;
    }

    /**
     * Registreert een {@link PersoonElement} die bij deze {@link BijhoudingPersoon} hoort. Bij nieuwe personen kunnen de gegevens over meerdere acties bekend
     * worden gemaakt. Voor het controleren van regels en het maken van het bijhoudingsplan, is het noodzakelijk dat deze gegevens op 1 plek te benaderen zijn.
     * @param persoonElement een {@link PersoonElement}
     */
    void registreerPersoonElement(final PersoonElement persoonElement) {
        this.persoonElementen.add(persoonElement);
    }

    private static Timestamp bepaalDatumTijdLaatsteWijzigingGba(final Persoon persoon) {
        final PersoonAfgeleidAdministratiefHistorie actueleAfgeleidAdministratief =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonAfgeleidAdministratiefHistorieSet());
        final Timestamp tijdstipLaatsteWijzigingGbaSystematiek;
        if (actueleAfgeleidAdministratief != null && actueleAfgeleidAdministratief.getDatumTijdLaatsteWijzigingGba() != null) {
            tijdstipLaatsteWijzigingGbaSystematiek = actueleAfgeleidAdministratief.getDatumTijdLaatsteWijzigingGba();
        } else {
            tijdstipLaatsteWijzigingGbaSystematiek = null;
        }
        return tijdstipLaatsteWijzigingGbaSystematiek;
    }

    /**
     * Geeft een unmodifiable lijst terug van de {@link PersoonElement} objecten die bij deze {@link BijhoudingPersoon} zijn geregistreerd.
     * @return de lijst van geregistreerde {@link PersoonElement} objecten
     */
    public List<PersoonElement> getPersoonElementen() {
        return Collections.unmodifiableList(persoonElementen);
    }

    /**
     * De {@link BijhoudingSituatie} voor deze persoon.
     * @return de {@link BijhoudingSituatie} voor deze persoon of null als deze nog niet is bepaald
     */
    public BijhoudingSituatie getBijhoudingSituatie() {
        return bijhoudingSituatie;
    }

    /**
     * Zet de {@link BijhoudingSituatie} voor deze persoon.
     * @param bijhoudingSituatie de {@link BijhoudingSituatie} voor deze persoon
     */
    public void setBijhoudingSituatie(final BijhoudingSituatie bijhoudingSituatie) {
        this.bijhoudingSituatie = bijhoudingSituatie;
    }

    /**
     * Geeft de waarde van {@link PersoonAfgeleidAdministratiefHistorie#getDatumTijdLaatsteWijziging()} voordat er wijzigingen op deze persoon zijn
     * aangebracht.
     * @return tijdstipVoorlaatsteWijziging
     */
    public final Timestamp getTijdstipVoorlaatsteWijziging() {
        return Entiteit.timestamp(tijdstipVoorlaatsteWijziging);
    }

    /**
     * Geef de bijhoudingspartij voor personen in het bijhoudingsplan die bepaald zijn a.d.h.v. de administratieve handeling.
     * @return de bijhoudingspartij voor het bijhoudingsplan
     */
    public Partij getBijhoudingspartijVoorBijhoudingsplan() {
        return bijhoudingspartijVoorBijhoudingsplan;
    }

    /**
     * Zet de bijhoudingspartij voor personen in het bijhoudingsplan die bepaald zijn a.d.h.v. de administratieve handeling.
     * @param bijhoudingspartijVoorBijhoudingsplan de bijhoudingspartij voor het bijhoudingsplan
     */
    public void setBijhoudingspartijVoorBijhoudingsplan(final Partij bijhoudingspartijVoorBijhoudingsplan) {
        this.bijhoudingspartijVoorBijhoudingsplan = bijhoudingspartijVoorBijhoudingsplan;
    }

    /**
     * Geeft aan of de opgegeven partij de huidige(actueel en geldige) bijhoudingspartij is
     * @param partij de partij die mogelijk de bijhoudingspartij is van de persoon
     * @return true als het de bijhoudingspartij is
     */
    public boolean isPartijBijhoudingspartij(final Partij partij) {
        final Persoon persoon = getDelegates().get(0);
        final PersoonBijhoudingHistorie
                actueleBijhoudingHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonBijhoudingHistorieSet());
        return actueleBijhoudingHistorie.getPartij().isInhoudelijkGelijkAan(partij);
    }

    /**
     * Maakt een PersoonID historie entiteit o.b.v. de indentificatienummers uit het bijhoudingsbericht en de gegeven persoon en verantwoordingsgegevens.
     * @param identificatienummersElement de identificatienummersElement
     * @param actie de actie
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     */
    public final void voegPersoonIDHistorieToe(
            final IdentificatienummersElement identificatienummersElement,
            final BRPActie actie,
            final int datumAanvangGeldigheid) {
        for (final Persoon delegate : getDelegates()) {
            final PersoonIDHistorie idHistorie = new PersoonIDHistorie(delegate);
            if (identificatienummersElement.getAdministratienummer() != null) {
                idHistorie.setAdministratienummer(identificatienummersElement.getAdministratienummer().getWaarde());
            }
            if (identificatienummersElement.getBurgerservicenummer() != null) {
                idHistorie.setBurgerservicenummer(identificatienummersElement.getBurgerservicenummer().getWaarde());
            }
            BijhoudingEntiteit.voegMaterieleHistorieToe(idHistorie, actie, datumAanvangGeldigheid, delegate.getPersoonIDHistorieSet());
        }
    }

    /**
     * Maakt een nieuw {@link PersoonSamengesteldeNaamHistorie} voorkomen o.b.v. het bijhoudingsbericht en de gegeven persoon en verantwoordingsgegevens.
     * @param samengesteldeNaamElement de samengesteldeNaamElement
     * @param actie de actie
     * @param datumAanvangGeldigheid de datun aanvang geldigheid
     */
    @Bedrijfsregel(Regel.R1808)
    public final void voegPersoonSamengesteldeNaamHistorieToe(
            final SamengesteldeNaamElement samengesteldeNaamElement,
            final BRPActie actie,
            final int datumAanvangGeldigheid) {
        for (final Persoon delegate : getDelegates()) {
            final PersoonSamengesteldeNaamHistorie result =
                    new PersoonSamengesteldeNaamHistorie(
                            delegate,
                            samengesteldeNaamElement.getGeslachtsnaamstam().getWaarde(),
                            isPersoonIngeschrevene(),
                            samengesteldeNaamElement.getIndicatieNamenreeks().getWaarde());
            if (samengesteldeNaamElement.getPredicaatCode() != null) {
                result.setPredicaat(Predicaat.parseCode(samengesteldeNaamElement.getPredicaatCode().getWaarde()));
            }
            if (samengesteldeNaamElement.getAdellijkeTitelCode() != null) {
                result.setAdellijkeTitel(AdellijkeTitel.parseCode(samengesteldeNaamElement.getAdellijkeTitelCode().getWaarde()));
            }
            result.setVoornamen(BmrAttribuut.getWaardeOfNull(samengesteldeNaamElement.getVoornamen()));
            result.setVoorvoegsel(BmrAttribuut.getWaardeOfNull(samengesteldeNaamElement.getVoorvoegsel()));
            result.setScheidingsteken(BmrAttribuut.getWaardeOfNull(samengesteldeNaamElement.getScheidingsteken()));
            BijhoudingEntiteit.voegMaterieleHistorieToe(result, actie, datumAanvangGeldigheid, delegate.getPersoonSamengesteldeNaamHistorieSet());
        }
    }

    /**
     * Maakt een nieuw {@link PersoonGeboorteHistorie} voorkomen o.b.v. het bijhoudingsbericht en de gegeven persoon en verantwoordingsgegevens.
     * @param geboorteElement de geboorteElement
     * @param actie de actie
     */
    public final void voegPersoonGeboorteHistorieToe(final GeboorteElement geboorteElement, final BRPActie actie) {
        final String landOfGebiedCodeElement = BmrAttribuut.getWaardeOfNull(geboorteElement.getLandGebiedCode());
        final String landOfGebiedCode = landOfGebiedCodeElement == null ? LandOfGebied.CODE_NEDERLAND : landOfGebiedCodeElement;
        for (final Persoon delegate : getDelegates()) {
            final PersoonGeboorteHistorie result =
                    new PersoonGeboorteHistorie(
                            delegate,
                            geboorteElement.getDatum().getWaarde(),
                            geboorteElement.getDynamischeStamtabelRepository().getLandOfGebiedByCode(landOfGebiedCode));

            if (geboorteElement.getGemeenteCode() != null) {
                result.setGemeente(
                        geboorteElement.getDynamischeStamtabelRepository().getGemeenteByGemeentecode(geboorteElement.getGemeenteCode().getWaarde()));
            }
            result.setWoonplaatsnaamGeboorte(BmrAttribuut.getWaardeOfNull(geboorteElement.getWoonplaatsnaam()));
            result.setBuitenlandsePlaatsGeboorte(BmrAttribuut.getWaardeOfNull(geboorteElement.getBuitenlandsePlaats()));
            result.setBuitenlandseRegioGeboorte(BmrAttribuut.getWaardeOfNull(geboorteElement.getBuitenlandseRegio()));
            result.setOmschrijvingGeboortelocatie(BmrAttribuut.getWaardeOfNull(geboorteElement.getOmschrijvingLocatie()));
            BijhoudingEntiteit.voegFormeleHistorieToe(result, actie, delegate.getPersoonGeboorteHistorieSet());
        }
    }

    /**
     * Maakt een nieuw {@link PersoonGeslachtsaanduidingHistorie} voorkomen o.b.v. het bijhoudingsbericht en de gegeven persoon en verantwoordingsgegevens.
     * @param geslachtsaanduidingElement de geslachtsaanduidingElement
     * @param actie de actie
     * @param datumAanvangGeldigheid de datun aanvang geldigheid
     */
    public final void voegPersoonGeslachtsaanduidingHistorieToe(
            final GeslachtsaanduidingElement geslachtsaanduidingElement,
            final BRPActie actie,
            final int datumAanvangGeldigheid) {
        for (final Persoon delegate : getDelegates()) {
            final PersoonGeslachtsaanduidingHistorie result =
                    new PersoonGeslachtsaanduidingHistorie(delegate, Geslachtsaanduiding.parseCode(geslachtsaanduidingElement.getCode().getWaarde()));
            BijhoudingEntiteit.voegMaterieleHistorieToe(result, actie, datumAanvangGeldigheid, delegate.getPersoonGeslachtsaanduidingHistorieSet());
        }
    }

    /**
     * Maakt een nieuw {@link PersoonBijhoudingHistorie} voorkomen o.b.v. het bijhoudingsbericht en verantwoordingsgegevens.
     * @param gemeente Prtij die bijhoding doet
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param actie de actie
     */
    public void voegPersoonBijhoudingHistorieToe(final Gemeente gemeente, final int datumAanvangGeldigheid, final BRPActie actie) {
        final PersoonBijhoudingHistorie actueelVoorkomen = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonBijhoudingHistorieSet());
        voegPersoonBijhoudingHistorieToe(gemeente.getPartij(), actueelVoorkomen.getBijhoudingsaard(), actueelVoorkomen.getNadereBijhoudingsaard(),
                datumAanvangGeldigheid, actie);
    }

    void voegPersoonBijhoudingHistorieToe(final Partij partij, final Bijhoudingsaard bijhoudingsaard, final NadereBijhoudingsaard nadereBijhoudingsaard,
                                          final int datumAanvangGeldigheid, final BRPActie actie) {
        for (final Persoon delegate : getDelegates()) {
            final PersoonBijhoudingHistorie
                    nieuwVoorkomen =
                    new PersoonBijhoudingHistorie(delegate, partij, bijhoudingsaard, nadereBijhoudingsaard);
            BijhoudingEntiteit.voegMaterieleHistorieToe(nieuwVoorkomen, actie, datumAanvangGeldigheid,
                    delegate.getPersoonBijhoudingHistorieSet());
        }
    }

    /**
     * Voegt de {@link PersoonInschrijvingHistorie} toe aan de {@link BijhoudingPersoon}.
     * @param datumInschrijving de datum van inschrijving
     * @param versienummer het versienummer van der persoonslijst
     * @param datumtijdstempel de datum/tijd stempel
     * @param actie de actie waar deze historie mee gemaakt is
     */
    public void voegPersoonInschrijvingHistorieToe(final int datumInschrijving, final long versienummer, final Timestamp datumtijdstempel,
                                                   final BRPActie actie) {
        for (final Persoon delegate : getDelegates()) {
            final PersoonInschrijvingHistorie historie = new PersoonInschrijvingHistorie(delegate, datumInschrijving, versienummer, datumtijdstempel);
            BijhoudingEntiteit.voegFormeleHistorieToe(historie, actie, delegate.getPersoonInschrijvingHistorieSet());
        }
    }

    /**
     * Maakt een nieuw {@link PersoonMigratieHistorie} voorkomen o.b.v. het {@link MigratieElement}
     * @param migratieElement het migratie element
     * @param datumAanvangGeldigheid de datum aanvang geldigheid
     * @param actie de actie waaraan het voorkomen gekoppeld wordt.
     */
    void voegPersoonMigratieHistorieToe(final MigratieElement migratieElement, final Integer datumAanvangGeldigheid, final BRPActie actie) {
        final DynamischeStamtabelRepository dynamischeStamtabelRepository = migratieElement.getDynamischeStamtabelRepository();
        final RedenWijzigingVerblijf
                redenWijziging =
                dynamischeStamtabelRepository.getRedenWijzigingVerblijf(migratieElement.getRedenWijzigingCode().getWaarde());

        Aangever aangeverCode = null;
        if (migratieElement.getAangeverCode() != null) {
            aangeverCode = dynamischeStamtabelRepository.getAangeverByCode(migratieElement.getAangeverCode().getWaarde());
        }
        LandOfGebied landOfGebied = null;
        if (migratieElement.getLandGebiedCode() != null) {
            landOfGebied = dynamischeStamtabelRepository.getLandOfGebiedByCode(migratieElement.getLandGebiedCode().getWaarde());
        }

        for (final Persoon delegate : getDelegates()) {
            final PersoonMigratieHistorie historie = new PersoonMigratieHistorie(delegate, SoortMigratie.EMIGRATIE);
            historie.setAangeverMigratie(aangeverCode);
            historie.setLandOfGebied(landOfGebied);
            historie.setBuitenlandsAdresRegel1(BmrAttribuut.getWaardeOfNull(migratieElement.getBuitenlandsAdresRegel1()));
            historie.setBuitenlandsAdresRegel2(BmrAttribuut.getWaardeOfNull(migratieElement.getBuitenlandsAdresRegel2()));
            historie.setBuitenlandsAdresRegel3(BmrAttribuut.getWaardeOfNull(migratieElement.getBuitenlandsAdresRegel3()));
            historie.setBuitenlandsAdresRegel4(BmrAttribuut.getWaardeOfNull(migratieElement.getBuitenlandsAdresRegel4()));
            historie.setBuitenlandsAdresRegel5(BmrAttribuut.getWaardeOfNull(migratieElement.getBuitenlandsAdresRegel5()));
            historie.setBuitenlandsAdresRegel6(BmrAttribuut.getWaardeOfNull(migratieElement.getBuitenlandsAdresRegel6()));
            historie.setRedenWijzigingMigratie(redenWijziging);

            BijhoudingEntiteit.voegMaterieleHistorieToe(historie, actie, datumAanvangGeldigheid, delegate.getPersoonMigratieHistorieSet());
        }
    }

    void beeindigAdres(final Integer datumAanvangGeldigheid, final BRPActie actie) {
        for (final Persoon delegate : getDelegates()) {
            final PersoonAdres adres = delegate.getPersoonAdresSet().iterator().next();
            MaterieleHistorie.beeindigActueelVoorkomen(adres.getPersoonAdresHistorieSet(), actie, actie.getDatumTijdRegistratie(), datumAanvangGeldigheid);
        }
    }

    void laatPersoonDeelnameEuVerkiezingenVervallen(final BRPActie actie) {
        for (final Persoon delegate : getDelegates()) {
            FormeleHistorie.laatActueelVoorkomenVervallen(delegate.getPersoonDeelnameEuVerkiezingenHistorieSet(), actie);
        }
    }

    /**
     * Maakt een nieuw {@link PersoonVerblijfsrechtHistorie} voorkomen o.b.v. het {@link VerblijfsrechtElement}
     * @param verblijfsrechtElement het verblijfsrechtElement
     * @param actie de actie waaraan het voorkomen gekoppeld wordt.
     */
    public final void voegPersoonVerblijfsrechtHistorieToe(final VerblijfsrechtElement verblijfsrechtElement, final BRPActie actie) {
        final Verblijfsrecht
                verblijfsrecht =
                verblijfsrechtElement.getDynamischeStamtabelRepository()
                        .getVerblijfsrechtByCode(verblijfsrechtElement.getAanduidingCode().getWaarde());
        for (final Persoon delegate : getDelegates()) {
            final PersoonVerblijfsrechtHistorie
                    result = new PersoonVerblijfsrechtHistorie(delegate, verblijfsrecht, verblijfsrechtElement.getDatumAanvang().getWaarde(),
                    verblijfsrechtElement.getDatumMededeling().getWaarde());
            result.setDatumMededelingVerblijfsrecht(verblijfsrechtElement.getDatumMededeling().getWaarde());
            result.setDatumVoorzienEindeVerblijfsrecht(BmrAttribuut.getWaardeOfNull(verblijfsrechtElement.getDatumVoorzienEinde()));
            BijhoudingEntiteit.voegFormeleHistorieToe(result, actie, delegate.getPersoonVerblijfsrechtHistorieSet());
        }
    }

    /**
     * Werkt voor de persoon de groep afgeleid administratief bij alleen als deze persoon ingeschreven is.
     * @param actie de actie die gebruik wordt om het nieuwe afgeleid administratief voorkomen te maken
     */
    @Bedrijfsregel(Regel.R1391)
    @Bedrijfsregel(Regel.R1392)
    public final void werkGroepAfgeleidAdministratiefBij(final BRPActie actie) {
        for (final Persoon delegate : getDelegates()) {
            if (SoortPersoon.INGESCHREVENE.equals(delegate.getSoortPersoon())) {
                final PersoonAfgeleidAdministratiefHistorie afgeleidAdministratiefHistorie =
                        new PersoonAfgeleidAdministratiefHistorie(
                                (short) 1,
                                delegate,
                                actie.getAdministratieveHandeling(),
                                actie.getAdministratieveHandeling().getDatumTijdRegistratie());
                afgeleidAdministratiefHistorie.setDatumTijdLaatsteWijzigingGba(bepaalDatumTijdLaatsteWijzigingGba(delegate));
                BijhoudingEntiteit.voegFormeleHistorieToe(afgeleidAdministratiefHistorie, actie, delegate.getPersoonAfgeleidAdministratiefHistorieSet());
            }
        }
    }

    /**
     * Wijzigt de naamgebruik van de persoon.
     * @param actie de actie waarmee het naamgebruik van de persoon wordt gewijzigd
     * @param naamgebruikElement het naamgebruik element
     * @param alsActieMeegegeven true als de naamgebruik als actie is meegegeven
     */
    @Bedrijfsregel(Regel.R1683)
    public final void wijzigPersoonNaamgebruikHistorie(
            final BRPActie actie,
            final NaamgebruikElement naamgebruikElement,
            final boolean alsActieMeegegeven) {
        final Naamgebruik naamgebruik = Naamgebruik.parseCode(naamgebruikElement.getCode().getWaarde());
        if (naamgebruikElement.getIndicatieAfgeleid().getWaarde()) {
            leidtNaamgebruikAf(naamgebruik, actie, alsActieMeegegeven);
        } else {
            for (final Persoon persoon : getDelegates()) {
                final PersoonNaamgebruikHistorie historie =
                        new PersoonNaamgebruikHistorie(persoon, naamgebruikElement.getGeslachtsnaamstam().getWaarde(), false, naamgebruik);
                historie.setAdellijkeTitel(naamgebruikElement.getAdellijkeTitel());
                historie.setPredicaat(naamgebruikElement.getPredicaat());
                historie.setScheidingstekenNaamgebruik(BmrAttribuut.getWaardeOfNull(naamgebruikElement.getScheidingsteken()));
                historie.setVoornamenNaamgebruik(BmrAttribuut.getWaardeOfNull(naamgebruikElement.getVoornamen()));
                historie.setVoorvoegselNaamgebruik(BmrAttribuut.getWaardeOfNull(naamgebruikElement.getVoorvoegsel()));
                BijhoudingEntiteit.voegFormeleHistorieToe(historie, actie, persoon.getPersoonNaamgebruikHistorieSet());
            }
        }
    }

    /**
     * Wijzigt een PersoonGeslachtsnaamcomponent van de persoon entiteit o.b.v. de geslachtsnaamcomponent uit het bijhoudingsbericht.
     * @param geslachtsnaamcomponentElement de geslachtsnaamcomponentElement
     * @param actie de actie
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     */
    public final void wijzigPersoonGeslachtsnaamcomponentEntiteit(
            final GeslachtsnaamcomponentElement geslachtsnaamcomponentElement,
            final BRPActie actie,
            final int datumAanvangGeldigheid) {
        for (Persoon persoon : getDelegates()) {
            final PersoonGeslachtsnaamcomponent geslachtsnaamcomponent;
            if (!persoon.getPersoonGeslachtsnaamcomponentSet().isEmpty()) {
                geslachtsnaamcomponent = persoon.getPersoonGeslachtsnaamcomponentSet().iterator().next();
            } else {
                geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent(persoon, 1);
                persoon.addPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent);
            }
            final PersoonGeslachtsnaamcomponentHistorie historie =
                    new PersoonGeslachtsnaamcomponentHistorie(geslachtsnaamcomponent, geslachtsnaamcomponentElement.getStam().getWaarde());
            historie.setPredicaat(Predicaat.parseCode(BmrAttribuut.getWaardeOfNull(geslachtsnaamcomponentElement.getPredicaatCode())));
            historie.setAdellijkeTitel(AdellijkeTitel.parseCode(BmrAttribuut.getWaardeOfNull(geslachtsnaamcomponentElement.getAdellijkeTitelCode())));
            historie.setVoorvoegsel(BmrAttribuut.getWaardeOfNull(geslachtsnaamcomponentElement.getVoorvoegsel()));
            historie.setScheidingsteken(BmrAttribuut.getWaardeOfNull(geslachtsnaamcomponentElement.getScheidingsteken()));
            BijhoudingEntiteit.voegMaterieleHistorieToe(
                    historie,
                    actie,
                    datumAanvangGeldigheid,
                    geslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorieSet());
        }
    }

    /**
     * Wijzigt een {@link PersoonSamengesteldeNaamHistorie} van de persoon entiteit o.b.v. de geslachtsnaamcomponent uit het bijhoudingsbericht. Deze methode
     * wordt gebruikt om Pseudo personen aan te passen obv een bericht voor ingeschreven personen. Dit is handig wanneer er ontrelateerd wordt.
     * @param geslachtsnaamcomponentElement de geslachtsnaamcomponentElement
     * @param actie de actie
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     */
    public final void wijzigPersoonSamengesteldeNaamEntiteit(
            final GeslachtsnaamcomponentElement geslachtsnaamcomponentElement,
            final BRPActie actie,
            final int datumAanvangGeldigheid) {
        for (Persoon persoon : getDelegates()) {
            final PersoonSamengesteldeNaamHistorie
                    actueleSamengesteldeNaam =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonSamengesteldeNaamHistorieSet());
            final PersoonSamengesteldeNaamHistorie
                    nieuweSamengesteldeNaam =
                    new PersoonSamengesteldeNaamHistorie(persoon, geslachtsnaamcomponentElement.getStam().getWaarde(), false, false);
            nieuweSamengesteldeNaam.setPredicaat(Predicaat.parseCode(BmrAttribuut.getWaardeOfNull(geslachtsnaamcomponentElement.getPredicaatCode())));
            nieuweSamengesteldeNaam
                    .setAdellijkeTitel(AdellijkeTitel.parseCode(BmrAttribuut.getWaardeOfNull(geslachtsnaamcomponentElement.getAdellijkeTitelCode())));
            nieuweSamengesteldeNaam.setVoorvoegsel(BmrAttribuut.getWaardeOfNull(geslachtsnaamcomponentElement.getVoorvoegsel()));
            nieuweSamengesteldeNaam.setScheidingsteken(BmrAttribuut.getWaardeOfNull(geslachtsnaamcomponentElement.getScheidingsteken()));
            if (actueleSamengesteldeNaam != null) {
                nieuweSamengesteldeNaam.setVoornamen(actueleSamengesteldeNaam.getVoornamen());
            }
            BijhoudingEntiteit.voegMaterieleHistorieToe(
                    nieuweSamengesteldeNaam,
                    actie,
                    datumAanvangGeldigheid,
                    persoon.getPersoonSamengesteldeNaamHistorieSet());
        }
    }

    /**
     * Wijzigt een PersoonAdres van de persoon entiteit o.b.v. de adres uit het bijhoudingsbericht.
     * @param adresElement AdresElement
     * @param actie actie
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     */
    public final void wijzigPersoonAdresEntiteit(final AdresElement adresElement,
                                                 final BRPActie actie,
                                                 final int datumAanvangGeldigheid) {
        for (final Persoon persoon : getDelegates()) {
            final PersoonAdres huidigAdresEntiteit = persoon.getPersoonAdresSet().iterator().next();
            BijhoudingEntiteit
                    .voegMaterieleHistorieToe(getAdresGegevensOpBasisVanSoortHandeling(huidigAdresEntiteit, adresElement, actie), actie, datumAanvangGeldigheid,
                            huidigAdresEntiteit.getPersoonAdresHistorieSet());
        }
    }


    /**
     * Deze methode wordt gebruikt om o.b.v. de id's van een oud voorkomen het nieuwe (kopie) voorkomen te achterhalen. Dit wordt gebruikt om m.b.v.
     * voorkomensleutels die naar oude voorkomens verwijzen toch het nieuwe voorkomen te vinden die nog geen ID heeft.
     * @param idOrigineleHistorie de id van de {@link RelatieHistorie} die is gekopieerd
     * @param kopieHistorie de kopie
     */
    public <T extends FormeleHistorie> void registreerKopieHistorie(final long idOrigineleHistorie, final T kopieHistorie) {
        idOpKopieHistorieMap.put(new VoorkomenSleutel(idOrigineleHistorie, kopieHistorie.getClass()), kopieHistorie);
    }

    /**
     * De methode zoekt voor voorkomensleutel uit het bijhoudingsbericht naar een bijbehorende historie voorkomen voor deze entiteit. Deze methode geeft als
     * resultaat het kopie van een voorkomen als het voorkomen waar de sleutel naar verwijst inmiddels is gekopieerd (bijv. tijdens ontrelateren).
     * @param voorkomenSleutel de voorkomen sleutel
     * @param typeHistorie het type historie
     * @return het bijbehorende voorkomen
     */
    public <T extends FormeleHistorie> T zoekRelatieHistorieVoorVoorkomenSleutel(final String voorkomenSleutel, final Class<T> typeHistorie) {
        T result = null;
        try {
            final Long historieId = Long.valueOf(voorkomenSleutel);
            final VoorkomenSleutel sleutel = new VoorkomenSleutel(Long.valueOf(voorkomenSleutel), typeHistorie);
            if (idOpKopieHistorieMap.containsKey(sleutel)) {
                result = (T) idOpKopieHistorieMap.get(sleutel);
            } else {
                result = zoekInHistorieSet(historieId, typeHistorie);
            }
        } catch (NumberFormatException nfe) {
            result = null;
        }
        return result;
    }

    private <T extends FormeleHistorie> T zoekInHistorieSet(final Long voorkomenId, final Class<T> typeHistorie) {
        final Set<FormeleHistorie> voorkomens = new LinkedHashSet<>();
        if (PersoonIDHistorie.class.equals(typeHistorie)) {
            voorkomens.addAll(getPersoonIDHistorieSet());
        } else if (PersoonGeboorteHistorie.class.equals(typeHistorie)) {
            voorkomens.addAll(getPersoonGeboorteHistorieSet());
        } else if (PersoonSamengesteldeNaamHistorie.class.equals(typeHistorie)) {
            voorkomens.addAll(getPersoonSamengesteldeNaamHistorieSet());
        } else if (PersoonGeslachtsaanduidingHistorie.class.equals(typeHistorie)) {
            voorkomens.addAll(getPersoonGeslachtsaanduidingHistorieSet());
        } else {
            throw new IllegalArgumentException("Niet ondersteund type voorkomen voor zoeken obv voorkomensleutel.");
        }
        for (final FormeleHistorie voorkomen : voorkomens) {
            if (voorkomenId.equals(voorkomen.getId())) {
                return (T) voorkomen;
            }
        }
        return null;
    }

    private PersoonAdresHistorie getAdresGegevensOpBasisVanSoortHandeling(final PersoonAdres persoonAdres, final AdresElement adresElement,
                                                                          final BRPActie actie) {
        final HandelingSpecifiekeAdresGegevens handelingSpecifiekeAdresGegevens;
        switch (actie.getAdministratieveHandeling().getSoort()) {
            case WIJZIGING_ADRES_INFRASTRUCTUREEL:
                handelingSpecifiekeAdresGegevens = maakAdresGegevensInfrastructureelOpBasisVanHuidigAdres(adresElement);
                break;
            case WIJZIGING_GEMEENTE_INFRASTRUCTUREEL:
                handelingSpecifiekeAdresGegevens = maakAdresGegevensInfrastructureelOpBasisVanHuidigAdresBehalveGemeente(adresElement);
                break;
            case VERHUIZING_BINNENGEMEENTELIJK:
                handelingSpecifiekeAdresGegevens = maakAdresGegevensOpBasisVanBerichtBehalveGemeenteEnAdreshouding(adresElement);
                break;
            case VERHUIZING_INTERGEMEENTELIJK:
            case GBA_VERHUIZING_INTERGEMEENTELIJK_GBA_NAAR_BRP:
                handelingSpecifiekeAdresGegevens = maakAdresGegevensOpBasisVanBericht(adresElement);
                break;
            default:
                throw new UnsupportedOperationException(
                        String.format("De handeling (%s) wordt niet ondersteund.", actie.getAdministratieveHandeling().getSoort()));
        }
        return handelingSpecifiekeAdresGegevens
                .build(persoonAdres, adresElement.getDynamischeStamtabelRepository().getLandOfGebiedByCode(LandOfGebied.CODE_NEDERLAND));
    }

    private HandelingSpecifiekeAdresGegevens maakAdresGegevensInfrastructureelOpBasisVanHuidigAdres(final AdresElement adresElement) {
        return new HandelingSpecifiekeAdresGegevens(adresElement)
                .gemeente(getActuelePersoonAdresHistorie().getGemeente())
                .redenwijziging(adresElement.getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(RedenWijzigingVerblijf.INFRASTRUCTURELE_WIJZIGING))
                .datumAanvangAdreshouding(getActuelePersoonAdresHistorie().getDatumAanvangAdreshouding())
                .soortAdres(getActuelePersoonAdresHistorie().getSoortAdres())
                .indicatiePersoonAangetroffenOpAdres(getActuelePersoonAdresHistorie().getIndicatiePersoonAangetroffenOpAdres());
    }

    private HandelingSpecifiekeAdresGegevens maakAdresGegevensInfrastructureelOpBasisVanHuidigAdresBehalveGemeente(final AdresElement adresElement) {
        return new HandelingSpecifiekeAdresGegevens(adresElement)
                .gemeente(adresElement.getDynamischeStamtabelRepository().getGemeenteByGemeentecode(adresElement.getGemeenteCode().getWaarde()))
                .redenwijziging(adresElement.getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(RedenWijzigingVerblijf.INFRASTRUCTURELE_WIJZIGING))
                .datumAanvangAdreshouding(getActuelePersoonAdresHistorie().getDatumAanvangAdreshouding())
                .soortAdres(getActuelePersoonAdresHistorie().getSoortAdres())
                .indicatiePersoonAangetroffenOpAdres(getActuelePersoonAdresHistorie().getIndicatiePersoonAangetroffenOpAdres());
    }

    private HandelingSpecifiekeAdresGegevens maakAdresGegevensOpBasisVanBerichtBehalveGemeenteEnAdreshouding(final AdresElement adresElement) {
        return new HandelingSpecifiekeAdresGegevens(adresElement)
                .gemeente(getActuelePersoonAdresHistorie().getGemeente())
                .redenwijziging(adresElement.getDynamischeStamtabelRepository()
                        .getRedenWijzigingVerblijf(BmrAttribuut.getWaardeOfNull(adresElement.getRedenWijzigingCode())))
                .datumAanvangAdreshouding(BmrAttribuut.getWaardeOfNull(adresElement.getDatumAanvangAdreshouding()))
                .soortAdres(SoortAdres.parseCode(BmrAttribuut.getWaardeOfNull(adresElement.getSoortCode())));
    }

    private HandelingSpecifiekeAdresGegevens maakAdresGegevensOpBasisVanBericht(final AdresElement adresElement) {
        return new HandelingSpecifiekeAdresGegevens(adresElement)
                .gemeente(
                        adresElement.getDynamischeStamtabelRepository().getGemeenteByGemeentecode(BmrAttribuut.getWaardeOfNull(adresElement.getGemeenteCode())))
                .redenwijziging(adresElement.getDynamischeStamtabelRepository()
                        .getRedenWijzigingVerblijf(BmrAttribuut.getWaardeOfNull(adresElement.getRedenWijzigingCode())))
                .datumAanvangAdreshouding(BmrAttribuut.getWaardeOfNull(adresElement.getDatumAanvangAdreshouding()))
                .soortAdres(SoortAdres.parseCode(BmrAttribuut.getWaardeOfNull(adresElement.getSoortCode())));
    }

    public PersoonAdresHistorie getActuelePersoonAdresHistorie() {
        final Set<PersoonAdresHistorie> persoonAdresHistorieSet = getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet();
        return FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoonAdresHistorieSet);
    }

    /**
     * Wijzigt een PersoonVerstrekkingsbeperking van de persoon entiteit o.b.v. de verstrekkingsbeperkingElement uit het bijhoudingsbericht.
     * @param verstrekkingsbeperkingElement het VerstrekkingsbeperkingElement.
     * @param actie de actie.
     */
    public final void wijzigPersoonVerstrekkingsbeperkingEntiteit(final VerstrekkingsbeperkingElement verstrekkingsbeperkingElement, final BRPActie actie) {
        final DynamischeStamtabelRepository repository = verstrekkingsbeperkingElement.getDynamischeStamtabelRepository();
        for (final Persoon persoon : getDelegates()) {
            PersoonVerstrekkingsbeperking persoonVerstrekkingsbeperking = null;

            for (final PersoonVerstrekkingsbeperking verstrekkingsbeperkingEntiteit : persoon.getPersoonVerstrekkingsbeperkingSet()) {
                if (verstrekkingsbeperkingElement.isGelijkAanBestaandeVerstrekkingsbeperking(verstrekkingsbeperkingEntiteit)) {
                    persoonVerstrekkingsbeperking = verstrekkingsbeperkingEntiteit;
                    break;
                }
            }
            if (persoonVerstrekkingsbeperking == null) {
                persoonVerstrekkingsbeperking = new PersoonVerstrekkingsbeperking(persoon);
                persoon.addPersoonVerstrekkingsbeperking(persoonVerstrekkingsbeperking);
                if (verstrekkingsbeperkingElement.getGemeenteVerordeningPartijCode() != null) {
                    persoonVerstrekkingsbeperking
                            .setGemeenteVerordening(repository.getPartijByCode(verstrekkingsbeperkingElement.getGemeenteVerordeningPartijCode().getWaarde()));
                }
                if (verstrekkingsbeperkingElement.getPartijCode() != null) {
                    persoonVerstrekkingsbeperking.setPartij(repository.getPartijByCode(verstrekkingsbeperkingElement.getPartijCode().getWaarde()));
                }
                persoonVerstrekkingsbeperking.setOmschrijvingDerde(BmrAttribuut.getWaardeOfNull(verstrekkingsbeperkingElement.getOmschrijvingDerde()));
            }
            BijhoudingEntiteit.voegFormeleHistorieToe(new PersoonVerstrekkingsbeperkingHistorie(persoonVerstrekkingsbeperking), actie,
                    persoonVerstrekkingsbeperking.getPersoonVerstrekkingsbeperkingHistorieSet());
        }
    }

    /**
     * Maakt een kopie van de meegegeven {@link PersoonVerstrekkingsbeperking}.
     * @param origineel het origineel
     * @param actie de actie die aan de nieuew historie gekoppeld moet worden
     */
    public void kopieerVerstrekkingsbeperking(final PersoonVerstrekkingsbeperking origineel, final BRPActie actie) {
        for (final Persoon deletage : getDelegates()) {
            final PersoonVerstrekkingsbeperking kopie = new PersoonVerstrekkingsbeperking(deletage);
            deletage.addPersoonVerstrekkingsbeperking(kopie);
            kopie.setGemeenteVerordening(origineel.getGemeenteVerordening());
            kopie.setPartij(origineel.getPartij());
            kopie.setOmschrijvingDerde(origineel.getOmschrijvingDerde());
            BijhoudingEntiteit
                    .voegFormeleHistorieToe(new PersoonVerstrekkingsbeperkingHistorie(kopie), actie, kopie.getPersoonVerstrekkingsbeperkingHistorieSet());
        }
    }

    /**
     * Maakt een kopie van een {@link PersoonIndicatie}. Ook de actuele historie wordt gekopieerd.
     * @param origineel het origineel
     * @param actie de actie die aan de nieuwe historie gekoppeld moet worden
     */
    public void kopieerIndicatie(final PersoonIndicatie origineel, final BRPActie actie) {
        for (final Persoon delegate : getDelegates()) {
            final PersoonIndicatie kopie = new PersoonIndicatie(delegate, origineel.getSoortIndicatie());
            delegate.addPersoonIndicatie(kopie);
            final PersoonIndicatieHistorie origineleHistorie =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(origineel.getPersoonIndicatieHistorieSet());
            BijhoudingEntiteit.voegPersoonIndicatieHistorieToe(
                    new PersoonIndicatieHistorie(kopie, origineleHistorie.getWaarde()), actie,
                    origineleHistorie.getDatumAanvangGeldigheid(), kopie.getPersoonIndicatieHistorieSet());
        }
    }

    /**
     * Maakt een kopie van het meegegeven {@link PersoonAdres} inclusief historie.
     * @param origineel het origineel
     * @param redenWijzigingVerblijf de reden wijziging verblijf die in de historie wordt aangepast
     * @param datumAanvangGeldigheid de datum aanvang geldigheid
     * @param actie de actie die aan de nieuwe historie gekoppeld moet worden
     */
    public void kopieerAdres(final PersoonAdres origineel, final RedenWijzigingVerblijf redenWijzigingVerblijf, final int datumAanvangGeldigheid,
                             final BRPActie actie) {
        for (final Persoon delegate : getDelegates()) {
            final PersoonAdres kopie = new PersoonAdres(delegate);
            delegate.addPersoonAdres(kopie);

            final PersoonAdresHistorie origineelHistorie =
                    MaterieleHistorie.getGeldigVoorkomenOpPeildatum(origineel.getPersoonAdresHistorieSet(), datumAanvangGeldigheid);
            if (origineelHistorie != null) {
                final PersoonAdresHistorie kopieHistorie = origineelHistorie.kopieer();
                kopieHistorie.setRedenWijziging(redenWijzigingVerblijf);
                kopieHistorie.setAangeverAdreshouding(null);
                kopieHistorie.setIndicatiePersoonAangetroffenOpAdres(null);
                kopieHistorie.setDatumAanvangAdreshouding(datumAanvangGeldigheid);
                // Indien er na datum een verhuizing heeft plaatsgevonden betreft het kopie een niet actuele
                // die we actueel moeten maken.
                kopieHistorie.setActieAanpassingGeldigheid(null);
                kopieHistorie.setDatumEindeGeldigheid(null);
                // Let op, de verwijzing naar de A-laag wordt ook gekopieerd. Goed zetten dus
                kopieHistorie.setPersoonAdres(kopie);
                BijhoudingEntiteit.voegMaterieleHistorieToe(kopieHistorie, actie, datumAanvangGeldigheid, kopie.getPersoonAdresHistorieSet());
            }
        }
    }

    /**
     * Laat alle actuele verstrekkingsbeperkingen vervallen.
     * @param actie waarmee de actuele beperking vervalt
     */
    final void beeindigAllePersoonVerstrekkingsbeperkingEntiteiten(final BRPActie actie) {
        for (final Persoon persoon : getDelegates()) {
            for (final PersoonVerstrekkingsbeperking verstrekkingsbeperking : persoon.getPersoonVerstrekkingsbeperkingSet()) {
                BijhoudingEntiteit.laatActueelVoorkomenVervallen(verstrekkingsbeperking.getPersoonVerstrekkingsbeperkingHistorieSet(), actie);
            }
        }
    }

    /**
     * Laat alle persoon indicaties van het gegeven {@link SoortIndicatie} vervallen.
     * @param soortIndicatie het soort indicatie
     * @param actie die verantwoording voor het verval
     */
    final void laatPersoonIndicatieVervallen(final SoortIndicatie soortIndicatie, final BRPActie actie) {
        for (final Persoon persoon : getDelegates()) {
            for (PersoonIndicatie indicatie : persoon.getPersoonIndicatieSet()) {
                if (soortIndicatie.equals(indicatie.getSoortIndicatie())) {
                    BijhoudingEntiteit.laatActueelVoorkomenVervallen(indicatie.getPersoonIndicatieHistorieSet(), actie);
                }
            }
        }
    }

    /**
     * Wijzigt een PersoonIndicatie van de persoon entiteit o.b.v. de indicatieElement uit het bijhoudingsbericht.
     * @param indicatieElement de indicatieElement.
     * @param actie de verantwoodelijke actie.
     * @param soortIndicatie indicatie van de indicatie.
     * @param datumAanvangGeldigheid datum van aanvang voor de matriele historie.
     */
    final void wijzigPersoonIndicatieEntiteit(final IndicatieElement indicatieElement, final BRPActie actie, final SoortIndicatie soortIndicatie,
                                              final int datumAanvangGeldigheid) {
        for (final Persoon persoon : getDelegates()) {
            PersoonIndicatie persoonIndicatie = getPersoonIndicatie(soortIndicatie);
            if (persoonIndicatie == null) {
                persoonIndicatie = new PersoonIndicatie(persoon, soortIndicatie);
                persoon.addPersoonIndicatie(persoonIndicatie);
            }
            BijhoudingEntiteit
                    .voegPersoonIndicatieHistorieToe(
                            new PersoonIndicatieHistorie(persoonIndicatie, BmrAttribuut.getWaardeOfNull(indicatieElement.getHeeftIndicatie())), actie,
                            datumAanvangGeldigheid, persoonIndicatie.getPersoonIndicatieHistorieSet());
        }
    }

    /**
     * Bepaalt of een persoon overleden is op de gegeven datum.
     * @param datum de datum
     * @return true als deze persoon overleden is voor of op de gegeven datum, anders false
     */

    final boolean isPersoonOverledenOpDatum(final Integer datum) {
        final PersoonBijhoudingHistorie geldigVoorkomenOpPeildatum = MaterieleHistorie.getGeldigVoorkomenOpPeildatum(getPersoonBijhoudingHistorieSet(), datum);
        return geldigVoorkomenOpPeildatum != null && geldigVoorkomenOpPeildatum.getNadereBijhoudingsaard() == NadereBijhoudingsaard.OVERLEDEN;
    }

    /**
     * Geeft de lijst van ingeschreven gerelateerden terug van de persoon.
     * @return de lijst van ingeschreven gerelateerde personen.
     */
    public final List<BijhoudingPersoon> verzamelGerelateerden() {
        final List<BijhoudingPersoon> alleGerelateerdePersonen = new ArrayList<>();
        for (final Relatie relatie : getRelaties()) {
            final Set<Betrokkenheid> betrokkenheden = relatie.getBetrokkenheidSet();
            for (final Betrokkenheid betrokkenheid : betrokkenheden) {
                final Persoon gerelateerdePersoon = betrokkenheid.getPersoon();
                if (gerelateerdePersoon != null
                        && gerelateerdePersoon.getSoortPersoon() == SoortPersoon.INGESCHREVENE
                        && gerelateerdePersoon != getDelegates().iterator().next()) {
                    alleGerelateerdePersonen.add(BijhoudingPersoon.decorate(gerelateerdePersoon));
                }
            }
        }
        return alleGerelateerdePersonen;
    }

    /**
     * Bepaalt of deze bijhouding persoon verwerkbaar is.
     * @return true als deze persoon verwerkbaar is, anders false
     * @throws NullPointerException als {@link #getBijhoudingSituatie()} null oplevert
     * @see BijhoudingSituatie#isVerwerkbaar()
     */
    public final boolean isVerwerkbaar() {
        return getBijhoudingSituatie().isVerwerkbaar();
    }

    /**
     * Methode voor het kopieeren van bepaalde groepen van een {@link Persoon}. Datum aanvang wordt alleen gebruikt indien de datum aanvang groter is dan de
     * datum aanvang geldigheid van de groep.
     * @param datumAanvang datum aanvang van de relatie of ouderschap.
     * @param isKind boolean of het een kopie van een kind is.
     * @return pseudo Persoon kopie van de orginele.
     */
    public final BijhoudingPersoon kopieer(final Integer datumAanvang, final boolean isKind) {
        return kopieer(datumAanvang, isKind, Collections.emptySet(), null);
    }

    /**
     * Methode voor het kopieeren van bepaalde groepen van een {@link Persoon}. Datum aanvang wordt alleen gebruikt indien de datum aanvang groter is dan de
     * datum aanvang geldigheid van de groep.
     * @param datumAanvang datum aanvang van de relatie of ouderschap.
     * @param isKind boolean of het een kopie van een kind is.
     * @param onderzoeken de set van onderzoeken die mogelijk ook moeten gelden voor gekopieerde gegevens
     * @param actieVoorOnderzoek de verantwoording voor de kopie gegevens die in onderzoek komen
     * @return pseudo Persoon kopie van de orginele.
     */
    public final BijhoudingPersoon kopieer(final Integer datumAanvang, final boolean isKind, final Set<Onderzoek> onderzoeken,
                                           final BRPActie actieVoorOnderzoek) {
        final Persoon pseudoPersoon = new Persoon(SoortPersoon.PSEUDO_PERSOON);
        final BijhoudingPersoon result = BijhoudingPersoon.decorate(pseudoPersoon);
        Onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(onderzoeken, getDelegates().iterator().next(), pseudoPersoon, actieVoorOnderzoek);
        kopieerIDHistorie(datumAanvang, pseudoPersoon, onderzoeken, actieVoorOnderzoek).registreerKopieHistorie(result);
        kopieerSamengesteldeNaamHistorie(datumAanvang, pseudoPersoon, onderzoeken, actieVoorOnderzoek).registreerKopieHistorie(result);
        kopieerGeboorteHistorie(pseudoPersoon, onderzoeken, actieVoorOnderzoek).registreerKopieHistorie(result);
        if (!isKind) {
            kopieerGeslachtsaanduidingHistorie(datumAanvang, pseudoPersoon, onderzoeken, actieVoorOnderzoek).registreerKopieHistorie(result);
        }
        return result;
    }

    /**
     * Geeft de lijst met HGP relaties waarvoor geldt dat de relatie actueel is op het moment van de gegeven peildatum.
     * @param peildatum datum waarop relatie actueel moet zijn
     * @return de lijst met actuele HGP relaties
     */
    public final List<Relatie> getActueleHgpRelaties(final int peildatum) {
        final List<Relatie> result = new LinkedList<>();
        for (Betrokkenheid partner : getActuelePartners()) {
            RelatieHistorie actueleRelatieHistorie = partner.getRelatie().getActueleRelatieHistorie();
            if (DatumUtil.valtDatumBinnenPeriodeStreng(peildatum, actueleRelatieHistorie.getDatumAanvang(), actueleRelatieHistorie.getDatumEinde())) {
                result.add(partner.getRelatie());
            }
        }
        return result;
    }

    /**
     * Geeft de eerste {@link PersoonIndicatie} terug waarvan het soort overeenkomt met de gegeven soort.
     * @param soortIndicatie soort indicatie
     * @return de persoon indicatie van deze persoon van het gegeven soort
     */
    PersoonIndicatie getPersoonIndicatie(final SoortIndicatie soortIndicatie) {
        for (final PersoonIndicatie indicatie : getPersoonIndicatieSet()) {
            if (indicatie.getSoortIndicatie().equals(soortIndicatie)) {
                return indicatie;
            }
        }
        return null;
    }

    private VoorkomenPaar kopieerGeslachtsaanduidingHistorie(final Integer datumAanvang, final Persoon pseudoPersoon,
                                                             final Set<Onderzoek> onderzoeken, BRPActie actieVoorOnderzoek) {
        final PersoonGeslachtsaanduidingHistorie actueelPersoonGeslachtsaanduidingVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonGeslachtsaanduidingHistorieSet());
        if (actueelPersoonGeslachtsaanduidingVoorkomen != null) {
            final PersoonGeslachtsaanduidingHistorie pseudoPersoonGeslachtsaanduidingHistorie = actueelPersoonGeslachtsaanduidingVoorkomen.kopieer();
            if (pseudoPersoonGeslachtsaanduidingHistorie.getDatumAanvangGeldigheid() == null
                    || pseudoPersoonGeslachtsaanduidingHistorie.getDatumAanvangGeldigheid() < datumAanvang) {
                pseudoPersoonGeslachtsaanduidingHistorie.setDatumAanvangGeldigheid(datumAanvang);
            }
            pseudoPersoon.addPersoonGeslachtsaanduidingHistorie(pseudoPersoonGeslachtsaanduidingHistorie);
            Onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(onderzoeken, actueelPersoonGeslachtsaanduidingVoorkomen,
                    pseudoPersoonGeslachtsaanduidingHistorie, actieVoorOnderzoek);
            return new VoorkomenPaar(actueelPersoonGeslachtsaanduidingVoorkomen, pseudoPersoonGeslachtsaanduidingHistorie);
        } else {
            return VoorkomenPaar.NULL;
        }
    }

    private VoorkomenPaar kopieerGeboorteHistorie(final Persoon pseudoPersoon, final Set<Onderzoek> onderzoeken,
                                                  final BRPActie actieVoorOnderzoek) {
        final PersoonGeboorteHistorie actueelPersoonGeboorteVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonGeboorteHistorieSet());
        if (actueelPersoonGeboorteVoorkomen != null) {
            final PersoonGeboorteHistorie pseudoPersoonGeboorteHistorie = actueelPersoonGeboorteVoorkomen.kopieer();
            pseudoPersoon.addPersoonGeboorteHistorie(pseudoPersoonGeboorteHistorie);
            Onderzoek
                    .kopieerGegevenInOnderzoekVoorNieuwGegeven(onderzoeken, actueelPersoonGeboorteVoorkomen, pseudoPersoonGeboorteHistorie, actieVoorOnderzoek);
            return new VoorkomenPaar(actueelPersoonGeboorteVoorkomen, pseudoPersoonGeboorteHistorie);
        } else {
            return VoorkomenPaar.NULL;
        }
    }

    private VoorkomenPaar kopieerSamengesteldeNaamHistorie(final Integer datumAanvang, final Persoon pseudoPersoon,
                                                           final Set<Onderzoek> onderzoeken, final BRPActie actieVoorOnderzoek) {
        final PersoonSamengesteldeNaamHistorie actueelPersoonSamengesteldeNaamVoorkomen =
                getActuelePersoonSamengesteldeNaamHistorie();
        if (actueelPersoonSamengesteldeNaamVoorkomen != null) {
            final PersoonSamengesteldeNaamHistorie pseudoPersoonSamengesteldeNaamHistorie = actueelPersoonSamengesteldeNaamVoorkomen.kopieer();
            pseudoPersoonSamengesteldeNaamHistorie.setIndicatieAfgeleid(false);
            if (pseudoPersoonSamengesteldeNaamHistorie.getDatumAanvangGeldigheid() == null
                    || pseudoPersoonSamengesteldeNaamHistorie.getDatumAanvangGeldigheid() < datumAanvang) {
                pseudoPersoonSamengesteldeNaamHistorie.setDatumAanvangGeldigheid(datumAanvang);
            }
            pseudoPersoon.addPersoonSamengesteldeNaamHistorie(pseudoPersoonSamengesteldeNaamHistorie);
            Onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(onderzoeken, actueelPersoonSamengesteldeNaamVoorkomen, pseudoPersoonSamengesteldeNaamHistorie,
                    actieVoorOnderzoek);
            return new VoorkomenPaar(actueelPersoonSamengesteldeNaamVoorkomen, pseudoPersoonSamengesteldeNaamHistorie);
        } else {
            return VoorkomenPaar.NULL;
        }
    }

    private VoorkomenPaar kopieerIDHistorie(final Integer datumAanvang, final Persoon pseudoPersoon,
                                            final Set<Onderzoek> onderzoeken, final BRPActie actieVoorOnderzoek) {
        final PersoonIDHistorie actueelPersoonIdVoorkomen = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonIDHistorieSet());
        if (actueelPersoonIdVoorkomen != null) {
            final PersoonIDHistorie pseudoPersoonIDHistorie = actueelPersoonIdVoorkomen.kopieer();
            if (pseudoPersoonIDHistorie.getDatumAanvangGeldigheid() == null || pseudoPersoonIDHistorie.getDatumAanvangGeldigheid() < datumAanvang) {
                pseudoPersoonIDHistorie.setDatumAanvangGeldigheid(datumAanvang);
            }
            pseudoPersoon.addPersoonIDHistorie(pseudoPersoonIDHistorie);
            Onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(onderzoeken, actueelPersoonIdVoorkomen, pseudoPersoonIDHistorie, actieVoorOnderzoek);
            return new VoorkomenPaar(actueelPersoonIdVoorkomen, pseudoPersoonIDHistorie);
        } else {
            return VoorkomenPaar.NULL;
        }
    }

    private Timestamp bepaalDatumTijdVoorlaatsteWijziging(final Persoon persoon) {
        final PersoonAfgeleidAdministratiefHistorie actueelHistorieVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonAfgeleidAdministratiefHistorieSet());
        if (actueelHistorieVoorkomen == null) {
            return null;
        } else {
            return actueelHistorieVoorkomen.getDatumTijdLaatsteWijziging();
        }
    }

    /**
     * Controleert of de persoon op peildatum de Nederlandse nationaliteit heeft of indicatie behandelt als nederlander.
     * @param peilDatum datum waarop gecontroleerd moet worden.
     * @return true indien persoon Nederlandse nationaliteit heeft op de peildatum.
     */
    boolean heeftNederlandseNationaliteitOfIndicatieBehandeldAlsNederlander(final Integer peilDatum) {
        final boolean heeftNederlandseNationaliteit = getNationaliteitHelper().heeftNederlandseNationaliteit(peilDatum);
        final boolean eersteInschrijvingEnNietBeeindigeNLnationaliteit = isEersteInschrijving && heeftNederlandseNationaliteit;
        return eersteInschrijvingEnNietBeeindigeNLnationaliteit
                || (!isEersteInschrijving && (heeftNederlandseNationaliteit || heeftBehandeldAlsNederlanderIndicatieOpDatum(peilDatum)));
    }

    /**
     * Controleert of de nationaliteit bij de persoon meerdere keren in het bericht wordt geregistreerd zonder beeindiging of reeds in de db actueel voorkomt.
     * indien de dag verschilt wordt alleen true terug gegeven bij de laatste
     * @param nationaliteitElement de nieuwe nationaliteit
     * @param peildatum de peildatum
     * @return true als de persoon al de nationaliteit heeft op de datum aanvang geldigheid
     */
    boolean heeftNationaliteitAl(final NationaliteitElement nationaliteitElement, final Integer peildatum) {
        return getNationaliteitHelper().heeftNationaliteitAl(BmrAttribuut.getWaardeOfNull(nationaliteitElement.getNationaliteitCode()), peildatum);
    }


    /**
     * Geeft aan of een persoon de meegegeven nationaliteit(code) heeft op de peildatum.
     * @param nationaliteitCode de code van de nationaliteit
     * @param peildatum peildatumdatum
     * @return true indien de nationaliteit bij de persoon bestaat op de peildatum
     */
    boolean heeftNationaliteit(final String nationaliteitCode, final int peildatum) {
        return getNationaliteitHelper().heeftNationaliteit(nationaliteitCode, peildatum);
    }

    /**
     * Geeft aan of de persoon een ingeschreven persoon is en niet de nadere bijhoudingsaard FOUT of GEWIST heeft.
     * @return true als aan de voorwaarde wordt voldaan
     */
    boolean isIngeschreveneEnNietOpgeschortMetRedenFoutOfGewist() {
        final PersoonBijhoudingHistorie
                actueleBijhouding =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonBijhoudingHistorieSet());
        return isEersteInschrijving || (getSoortPersoon() == SoortPersoon.INGESCHREVENE
                && actueleBijhouding.getNadereBijhoudingsaard() != NadereBijhoudingsaard.FOUT
                && actueleBijhouding.getNadereBijhoudingsaard() != NadereBijhoudingsaard.GEWIST);
    }

    /**
     * Geeft aan of deze persoon een eerste inschrijving is.
     * @return true als het een eerste inschrijving is
     */
    public boolean isEersteInschrijving() {
        return isEersteInschrijving;
    }

    public String getBsnOrNull() {
        final PersoonIDHistorie actueleHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonIDHistorieSet());
        return actueleHistorie == null ? null : actueleHistorie.getBurgerservicenummer();
    }

    public String getActueelBurgerservicenummer() {
        final String result;
        if (isEersteInschrijving()) {
            final List<IdentificatienummersElement>
                    idNummerElementen =
                    persoonElementen.stream().filter(element -> element.getIdentificatienummers() != null).map(PersoonElement::getIdentificatienummers)
                            .collect(Collectors.toList());
            if (!idNummerElementen.isEmpty()) {
                result = idNummerElementen.get(idNummerElementen.size() - 1).getBurgerservicenummer().getWaarde();
            } else {
                result = null;
            }
        } else {
            final PersoonIDHistorie idHistorie =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonIDHistorieSet());
            result = idHistorie == null ? null : idHistorie.getBurgerservicenummer();
        }
        return result;
    }

    /**
     * Voegt voornamen toe aan de persoon.
     * @param voornaamElementen de lijst van voornaam elementen uit het bericht
     * @param actie de verantwoodelijke actie.
     * @param datumAanvangGeldigheid datum van aanvang voor de matriele historie.
     */
    public void voegPersoonVoornamenHistorieToe(final List<VoornaamElement> voornaamElementen, final BRPActie actie, final Integer datumAanvangGeldigheid) {
        for (final VoornaamElement voornaamElement : voornaamElementen) {
            for (final Persoon delegate : getDelegates()) {
                final Integer volgnummer = voornaamElement.getVolgnummer().getWaarde();
                PersoonVoornaam voornaam = delegate.getPersoonVoornaam(volgnummer);
                if (voornaam == null) {
                    voornaam = new PersoonVoornaam(delegate, volgnummer);
                    delegate.addPersoonVoornaam(voornaam);
                }

                final PersoonVoornaamHistorie historie = new PersoonVoornaamHistorie(voornaam, voornaamElement.getNaam().getWaarde());
                BijhoudingEntiteit.voegMaterieleHistorieToe(historie, actie, datumAanvangGeldigheid, voornaam.getPersoonVoornaamHistorieSet());

            }
        }
    }

    /**
     * Voegt voornamen toe aan de persoon.
     * @param geslachtsnaamcomponentElement de lijst van voornaam elementen uit het bericht
     * @param actie de verantwoodelijke actie.
     * @param datumAanvangGeldigheid datum van aanvang voor de matriele historie.
     */
    public void voegPersoonGeslachtsnaamComponentHistorieToe(final GeslachtsnaamcomponentElement geslachtsnaamcomponentElement, final BRPActie actie,
                                                             final Integer datumAanvangGeldigheid) {
        for (final Persoon delegate : getDelegates()) {
            final PersoonGeslachtsnaamcomponent geslachtsnaamcomponent;
            if (delegate.getPersoonGeslachtsnaamcomponentSet().isEmpty()) {
                geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent(delegate, 1);
            } else {
                geslachtsnaamcomponent = delegate.getPersoonGeslachtsnaamcomponentSet().iterator().next();
            }

            final PersoonGeslachtsnaamcomponentHistorie historie =
                    new PersoonGeslachtsnaamcomponentHistorie(geslachtsnaamcomponent, geslachtsnaamcomponentElement.getStam().getWaarde());
            historie.setAdellijkeTitel(geslachtsnaamcomponentElement.getAdellijkeTitel());
            historie.setPredicaat(geslachtsnaamcomponentElement.getPredicaat());
            historie.setScheidingsteken(BmrAttribuut.getWaardeOfNull(geslachtsnaamcomponentElement.getScheidingsteken()));
            historie.setVoorvoegsel(BmrAttribuut.getWaardeOfNull(geslachtsnaamcomponentElement.getVoorvoegsel()));

            BijhoudingEntiteit
                    .voegMaterieleHistorieToe(historie, actie, datumAanvangGeldigheid, geslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorieSet());
            if (delegate.getPersoonGeslachtsnaamcomponentSet().isEmpty()) {
                delegate.addPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent);
            }
        }
    }

    /**
     * Geeft aan of de persoon een {@link Bijhoudingsaard#INGEZETENE} is op de meegegeven peildatum.
     * @param peildatum de peildatum waarop de persoon een ingezetene is.
     * @return true als de persoon een geldig (historie)voorkomen heeft op de peildatum
     */
    public boolean isIngezeteneOpPeildatum(final Integer peildatum) {
        if (getDelegates().get(0).getPersoonBijhoudingHistorieSet().isEmpty()) {
            return false;
        }

        final PersoonBijhoudingHistorie
                geldigVoorkomenOpPeildatum =
                MaterieleHistorie.getGeldigVoorkomenOpPeildatum(getDelegates().get(0).getPersoonBijhoudingHistorieSet(), peildatum);
        return geldigVoorkomenOpPeildatum != null && geldigVoorkomenOpPeildatum.getBijhoudingsaard() == Bijhoudingsaard.INGEZETENE;
    }

    /**
     * Beeindigd  Nationaliteit van een persoon
     * @param nationaliteitElement element met code te beindigen nationaliteit
     * @param actie brpActie
     * @param redenVerliesCode code reden verlies nederlandse nationaliteit
     * @param datumEindeGeldigheid per welke datum
     */
    public void beeindigNationaliteit(final NationaliteitElement nationaliteitElement, final BRPActie actie, final StringElement redenVerliesCode,
                                      final DatumElement datumEindeGeldigheid) {
        for (final Persoon persoon : getDelegates()) {
            final Set<PersoonNationaliteit> persoonNationaliteitSet = persoon.getPersoonNationaliteitSet();
            for (PersoonNationaliteit nationaliteit : persoonNationaliteitSet) {
                beeindigNationaliteit(nationaliteitElement, actie, redenVerliesCode, datumEindeGeldigheid, nationaliteit);
            }
        }
    }

    private void beeindigNationaliteit(final NationaliteitElement nationaliteitElement, final BRPActie actie, final StringElement redenVerliesCode,
                                       final DatumElement datumEindeGeldigheid, final PersoonNationaliteit nationaliteit) {
        final DynamischeStamtabelRepository dynamischeStamtabelRepository = nationaliteitElement.getDynamischeStamtabelRepository();
        if (isAangewezenNationaliteit(nationaliteit, nationaliteitElement)) {
            final PersoonNationaliteitHistorie beeindigActueelVoorkomen = MaterieleHistorie
                    .beeindigActueelVoorkomen(nationaliteit.getPersoonNationaliteitHistorieSet(), actie, actie.getDatumTijdRegistratie(),
                            datumEindeGeldigheid.getWaarde());
            if (beeindigActueelVoorkomen != null && BmrAttribuut.getWaardeOfNull(redenVerliesCode) != null) {
                final RedenVerliesNLNationaliteit redenVerliesNLNationaliteitByCode =
                        dynamischeStamtabelRepository.getRedenVerliesNLNationaliteitByCode(redenVerliesCode.getWaarde());
                beeindigActueelVoorkomen.setRedenVerliesNLNationaliteit(redenVerliesNLNationaliteitByCode);
            }
        }
    }

    private boolean isAangewezenNationaliteit(final PersoonNationaliteit nationaliteit, final NationaliteitElement element) {
        if (element.heeftReferentie()) {
            return nationaliteit.getNationaliteit().getCode().equals(element.getNationaliteitCode().getWaarde());
        } else {
            return Objects.equals(nationaliteit.getId(), Long.valueOf(element.getObjectSleutel()));
        }
    }


    /**
     * Voegt een nieuwe nationaliteit toe aan de persoon.
     * @param nationaliteitElement het {@link NationaliteitElement} met daarin de gegevens van de nieuwe nationaliteit
     * @param actie de actie waarmee deze nationaliteit wordt vastgelegd
     * @param datumAanvangGeldigheid de datum vanaf deze nationaliteit moet gelden
     */
    public void voegPersoonNationaliteitToe(final NationaliteitElement nationaliteitElement, final BRPActie actie, final int datumAanvangGeldigheid) {
        final DynamischeStamtabelRepository dynamischeStamtabelRepository = nationaliteitElement.getDynamischeStamtabelRepository();
        for (final Persoon persoon : getDelegates()) {
            final Nationaliteit nationalteit =
                    dynamischeStamtabelRepository.getNationaliteitByNationaliteitcode(nationaliteitElement.getNationaliteitCode().getWaarde());
            final List<PersoonNationaliteit>
                    bestaandeNationaliteit =
                    persoon.getPersoonNationaliteitSet().stream()
                            .filter(persoonNationaliteit -> Objects.equals(nationalteit, persoonNationaliteit.getNationaliteit())).collect(Collectors.toList());

            final PersoonNationaliteit persoonNationaliteit;
            if (bestaandeNationaliteit.isEmpty()) {
                persoonNationaliteit = new PersoonNationaliteit(persoon, nationalteit);
            } else {
                persoonNationaliteit = bestaandeNationaliteit.get(0);
            }
            persoon.addPersoonNationaliteit(persoonNationaliteit);

            final PersoonNationaliteitHistorie persoonNationaliteitHistorie = new PersoonNationaliteitHistorie(persoonNationaliteit);

            final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit;
            final String redenVerkrijgingCode = BmrAttribuut.getWaardeOfNull(nationaliteitElement.getRedenVerkrijgingCode());
            if (redenVerkrijgingCode != null) {
                redenVerkrijgingNLNationaliteit = dynamischeStamtabelRepository.getRedenVerkrijgingNLNationaliteitByCode(redenVerkrijgingCode);
            } else {
                redenVerkrijgingNLNationaliteit = null;
            }
            persoonNationaliteitHistorie.setRedenVerkrijgingNLNationaliteit(redenVerkrijgingNLNationaliteit);

            if (nationalteit.isNederlandse()) {
                // alle overige nationaliteiten laten vervallen + bijhouding beeindigd? vullen
                laatAlleNationaliteitenVervallen(persoon, actie);
                // Verblijfsrecht laten vervallen
                FormeleHistorie.laatActueelVoorkomenVervallen(persoon.getPersoonVerblijfsrechtHistorieSet(), actie);

                // BVP vervallen
                final PersoonIndicatie bvpIndicatie = getPersoonIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
                if (bvpIndicatie != null) {
                    FormeleHistorie.laatActueelVoorkomenVervallen(bvpIndicatie.getPersoonIndicatieHistorieSet(), actie);
                }
            }

            // Controleer overlap met Staatloos.
            controleerOverlapStaatloos(datumAanvangGeldigheid, actie);

            BijhoudingEntiteit.voegMaterieleHistorieToe(persoonNationaliteitHistorie, actie, datumAanvangGeldigheid,
                    persoonNationaliteit.getPersoonNationaliteitHistorieSet());
        }
    }

    /**
     * gaat in de bijbehorende persoonElementen op zoek naar identificatieNummerElemeten en geeft deze terug.
     * @return List<IdentificatienummersElement>
     */
    public List<IdentificatienummersElement> getIndentificatieNummersVanElementen() {
        return getPersoonElementen().stream().filter(persoonElement -> persoonElement.getIdentificatienummers() != null)
                .map(PersoonElement::getIdentificatienummers).collect(Collectors.toList());
    }

    private void controleerOverlapStaatloos(final int datumAanvangGeldigheid, final BRPActie actie) {
        final PersoonIndicatie staatloosIndicatie = getPersoonIndicatie(SoortIndicatie.STAATLOOS);
        if (staatloosIndicatie != null) {
            final Set<PersoonIndicatieHistorie> indicatieHistorieSet = staatloosIndicatie.getPersoonIndicatieHistorieSet();
            final PersoonIndicatieHistorie actueleHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(indicatieHistorieSet);
            if (actueleHistorie != null) {
                // Controleren of de overlap volledig is of gedeeltelijk
                if (actueleHistorie.getDatumAanvangGeldigheid().equals(datumAanvangGeldigheid)) {
                    // Volledige overlap, geheel laten vervallen
                    FormeleHistorie.laatActueelVoorkomenVervallen(indicatieHistorieSet, actie);
                } else {
                    MaterieleHistorie.beeindigActueelVoorkomen(indicatieHistorieSet, actie, actie.getDatumTijdRegistratie(), datumAanvangGeldigheid);
                }
            }
        }
    }

    private void laatAlleNationaliteitenVervallen(final Persoon persoon, final BRPActie actie) {
        for (final PersoonNationaliteit nationaliteit : persoon.getPersoonNationaliteitSet()) {
            for (final PersoonNationaliteitHistorie historie : nationaliteit.getPersoonNationaliteitHistorieSet()) {
                if (!historie.isVervallen()) {
                    historie.setDatumTijdVerval(actie.getDatumTijdRegistratie());
                    historie.setActieVerval(actie);
                    historie.setIndicatieBijhoudingBeeindigd(true);
                }
            }
        }
    }

    /**
     * Geeft aan of de persoon meerderjarig is. Zie regel {@link Regel#R1282} voor de omschrijving,
     * @param peildatum de peildatum waarop gekeken moet worden
     * @return true als de persoon meerderjarig is.
     */
    @Bedrijfsregel(Regel.R1282)
    public boolean isMeerderjarig(final int peildatum) {
        final boolean isMeerderjarig;
        // Een persoon is meerdarig als deze ouder is dan 18 jaar op de peildatum of een huwelijk heeft (gehad).
        final int leeftijdGrensMeerderjarig = 18;
        if (isEersteInschrijving) {
            // Persoon staat nog niet in de database. Controleer of de geboortedatum is meegegeven in het bericht. Een huwelijk of GP hoeven we hier niet te
            // controleren.
            final PersoonElement persoonElement = persoonElementen.stream().filter(element -> element.getGeboorte() != null).findFirst().orElse(null);
            if (persoonElement != null) {
                final Integer geboorteDatumWaarde = persoonElement.getGeboorte().getDatum().getWaarde();
                isMeerderjarig = DatumUtil.bepaalJarenTussenDatumsSoepel(geboorteDatumWaarde, peildatum) >= leeftijdGrensMeerderjarig;
            } else {
                isMeerderjarig = false;
            }
        } else {
            // Persoon staat al in de database. Eerst leeftijd controleren, daarna evt een bestaand huwelijk/GP
            final Persoon persoon = getDelegates().get(0);
            final PersoonGeboorteHistorie
                    geboorteHistorie =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonGeboorteHistorieSet());
            final long aantalRelaties =
                    persoon.getRelaties(SoortBetrokkenheid.PARTNER).stream().filter(relatie ->
                            FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatie.getRelatieHistorieSet()).getDatumAanvang() <= peildatum)
                            .count();
            isMeerderjarig =
                    DatumUtil.bepaalJarenTussenDatumsSoepel(geboorteHistorie.getDatumGeboorte(), peildatum) >= leeftijdGrensMeerderjarig || aantalRelaties > 0;
        }

        return isMeerderjarig;
    }

    /**
     * Controleert of een {@link BijhoudingPersoon} een indicatie van soort ONDER_CURATELE heeft op peildatum
     * @param peildatum de peildatum waarop gecontroleerd wordt
     * @return true indien op peildatum een indicatie curatele is, false in andere gevallen.
     */
    boolean heeftCurateleIndicatieOpDatum(final int peildatum) {
        boolean result = false;
        for (final PersoonIndicatie indicatie : this.getPersoonIndicatieSet()) {
            if (SoortIndicatie.ONDER_CURATELE.equals(indicatie.getSoortIndicatie())) {
                result = isIndicatieActueelOpPeildatum(peildatum, indicatie);
            }
        }
        return result;
    }

    private boolean heeftBehandeldAlsNederlanderIndicatieOpDatum(final int peildatum) {
        boolean result = false;
        for (final PersoonIndicatie indicatie : this.getPersoonIndicatieSet()) {
            if (SoortIndicatie.BEHANDELD_ALS_NEDERLANDER.equals(indicatie.getSoortIndicatie())) {
                result = isIndicatieActueelOpPeildatum(peildatum, indicatie);
            }
        }
        return result;
    }

    private boolean isIndicatieActueelOpPeildatum(final int peilDatum, final PersoonIndicatie indicatie) {
        boolean result = false;
        for (final PersoonIndicatieHistorie persoonIndicatieHistorie : indicatie.getPersoonIndicatieHistorieSet()) {
            if (DatumUtil.valtDatumBinnenPeriode(peilDatum, persoonIndicatieHistorie.getDatumAanvangGeldigheid(),
                    persoonIndicatieHistorie.getDatumEindeGeldigheid())) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Voegt een ouder toe aan een kind. indien het kind nog geen betrokkenheid van het type kind heeft wordt deze aangemaakt met daaraan een relatie van het
     * soort familierechtelijke betrekking.
     * @param persoonEntiteit entiteit ouder
     * @param actie BrpActie
     * @param datumAanvangGeldigheid aanvang geldigheid.
     */

    public void voegOuderToe(final BijhoudingPersoon persoonEntiteit, final BRPActie actie, final Integer datumAanvangGeldigheid) {
        Betrokkenheid kindBetrokkenheid;
        for (Persoon delegate : getDelegates()) {
            if (delegate.getBetrokkenheidSet(SoortBetrokkenheid.KIND).isEmpty()) {
                final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
                RelatieHistorie relatieHistorie = new RelatieHistorie(relatie);
                relatieHistorie.setActieInhoud(actie);
                relatieHistorie.setDatumAanvang(datumAanvangGeldigheid);
                relatieHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
                kindBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);
                delegate.getBetrokkenheidSet().add(kindBetrokkenheid);
            } else {
                kindBetrokkenheid = delegate.getBetrokkenheidSet(SoortBetrokkenheid.KIND).iterator().next();
            }

            final Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, kindBetrokkenheid.getRelatie());
            final BetrokkenheidHistorie betrokkenheidHistorie = new BetrokkenheidHistorie(ouderBetrokkenheid);
            betrokkenheidHistorie.setActieInhoud(actie);
            betrokkenheidHistorie.setDatumTijdRegistratie(actie.getDatumTijdRegistratie());
            ouderBetrokkenheid.getBetrokkenheidHistorieSet().add(betrokkenheidHistorie);
            kindBetrokkenheid.getRelatie().addBetrokkenheid(ouderBetrokkenheid);
            ouderBetrokkenheid.setPersoon(persoonEntiteit.getDelegates().get(0));
        }
    }

    /**
     * bepaalt of de megegeven datum na de DAg van de actuele bijhouding is
     */
    boolean isDatumNaActueleBijhouding(final DatumElement datum) {
        boolean result = true;
        for (Persoon persoon : getDelegates()) {
            result = result && persoon.getPersoonBijhoudingHistorieSet().stream()
                    .filter(historie -> historie.getDatumAanvangGeldigheid() >= datum.getWaarde()).collect(Collectors.toList()).isEmpty();
        }
        return result;
    }

    /**
     * geeft de actuele bijhoudingsPartij terug.
     * @return Partij of null als er geen bijhoudingsPartij is
     */
    Partij getActueleBijhoudingspartij() {
        final PersoonBijhoudingHistorie
                actueelHistorieVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonBijhoudingHistorieSet());
        if (actueelHistorieVoorkomen != null) {
            return actueelHistorieVoorkomen.getPartij();
        }
        return null;
    }

    Partij getActuelePartijGeboorteGemeente() {
        final PersoonGeboorteHistorie
                actueelHistorieVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonGeboorteHistorieSet());
        if (actueelHistorieVoorkomen != null) {
            Gemeente gemeente = actueelHistorieVoorkomen.getGemeente();
            if (gemeente != null) {
                while (gemeente.getVoortzettendeGemeente() != null) {
                    gemeente = gemeente.getVoortzettendeGemeente();
                }
                return gemeente.getPartij();
            }
        }
        return null;
    }

    /**
     * geeft het aantal actuele nationaliteiten terug. Bij indicatieStaatloos wordt 1 terug gegeven.
     * @return aantal nationaliteiten of 1 bij indicatie staatloos;
     */
    long aantalNationaliteitenOfIndicatieStaatloos() {
        final PersoonIndicatieHistorie
                actueleIndicatieStaatLoos = getPersoonIndicatie(SoortIndicatie.STAATLOOS) != null ?
                MaterieleHistorie
                        .getGeldigVoorkomenOpPeildatum(getPersoonIndicatie(SoortIndicatie.STAATLOOS).getPersoonIndicatieHistorieSet(), DatumUtil.vandaag())
                : null;
        long aantalDbNationaliteiten = getPersoonNationaliteitSet().stream()
                .filter(nat -> FormeleHistorieZonderVerantwoording.heeftActueelVoorkomen(nat.getPersoonNationaliteitHistorieSet())).count();
        int indicatieStaatloos = actueleIndicatieStaatLoos != null && actueleIndicatieStaatLoos.getWaarde() ? 1 : 0;
        return aantalDbNationaliteiten + indicatieStaatloos;
    }

    /**
     * Geeft de actuele geboortedatum van de persoon terug uit de persoonelementen, of anders uit de database.
     * @return de actuele geboortedatum of null
     */
    Integer getActueleDatumGeboorte() {
        for (final PersoonElement persoonElement : getPersoonElementen()) {
            if (persoonElement.getGeboorte() != null) {
                return persoonElement.getGeboorte().getDatum().getWaarde();
            }
        }
        return getGeboorteDatumUitDataBase();
    }

    private Integer getGeboorteDatumUitDataBase() {
        final PersoonGeboorteHistorie actueelHistorieVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getDelegates().get(0).getPersoonGeboorteHistorieSet());
        return actueelHistorieVoorkomen != null ? actueelHistorieVoorkomen.getDatumGeboorte() : null;
    }

    /**
     * geeft de actuele persoonIndicatieHistorie terug voor moment Nu.
     * @param soortindicatie Soortindicatie
     * @return PersoonIndicatieHistorie
     */
    PersoonIndicatieHistorie getActueleIndicatie(final SoortIndicatie soortindicatie) {
        final PersoonIndicatie indicatie = getPersoonIndicatie(soortindicatie);
        if (indicatie != null) {
            return MaterieleHistorie.getGeldigVoorkomenOpPeildatum(indicatie.getPersoonIndicatieHistorieSet(), DatumUtil.vandaag());
        }
        return null;

    }

    /**
     * Geeft het meest recente voorkomen terug van de opgegeven indicatie.
     * @param soortIndicatie {@link SoortIndicatie}
     * @return het meest recente voorkomen van de opgegeven indicatie
     */
    PersoonIndicatieHistorie getMeestRecenteIndicatie(final SoortIndicatie soortIndicatie) {
        final PersoonIndicatie indicatie = getPersoonIndicatie(soortIndicatie);
        if (indicatie != null) {
            final Set<PersoonIndicatieHistorie> historie =
                    FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(indicatie.getPersoonIndicatieHistorieSet());
            return historie.stream().sorted(Comparator.comparingInt(PersoonIndicatieHistorie::getDatumAanvangGeldigheid).reversed())
                    .findFirst().orElse(null);
        }
        return null;
    }

    /**
     * Geeft de actuele indicatie namenreeks terug of null als er geen actuele samengestelde naam is.
     * @return de actuele indicatie namenreeks
     */
    boolean getActueleIndicatieNamenreeks() {
        return getActuelePersoonSamengesteldeNaamHistorie()
                .getIndicatieNamenreeks();
    }

    /**
     * geeft de actuelePersoonSamenGesteldeNaamHistorie terug.
     */
    PersoonSamengesteldeNaamHistorie getActuelePersoonSamengesteldeNaamHistorie() {
        return FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonSamengesteldeNaamHistorieSet());
    }

    /**
     * Geeft aan of de persoon op de peildatum nog een geldige nationaliteit of een indicatie staatloos heeft.
     * @param peilDatum datum waarop nationaliteit actueel moet zijn.
     * @return true als er een actuele nationaliteit niet beeindigd wordt of als er een actuele indicatieStaatloos aanwezig is
     */
    boolean heeftNogActueleNationaliteitenOfIndicatieStaatLoos(int peilDatum) {
        return !isEersteInschrijving() && (getActueleIndicatie(SoortIndicatie.STAATLOOS) != null || getNationaliteitHelper()
                .heeftGeldigeNationaliteit(peilDatum));
    }


    /**
     * is nederlandseNationaliteitNaVerwerkingNogActueel
     * @param peildatum peildatum
     */
    boolean heeftNederlandseNationaliteit(final int peildatum) {
        return getNationaliteitHelper().heeftNederlandseNationaliteit(peildatum);
    }


    /**
     * Geeft aan of het meegeven nationaliteit element er voor zorgt dat een nationaliteit meer dan 1x wordt beeindigd.
     * @param nationaliteitElement het nationaliteit element
     * @return true als de nationaliteit vaker dan 1x wordt beeindigd.
     */
    boolean wordtNationaliteitMeerDanEensBeeindigd(final NationaliteitElement nationaliteitElement) {
        return getNationaliteitHelper().isNationaliteitMeerdereKerenBeeindigd(nationaliteitElement);
    }

    /**
     * Geeft aan of er een aanpassing wordt doorgevoerd waardoor het reisdocument vervalt. Persoon.Datum geboorte Persoon.Voornamen Persoon.Geslachtsnaamstam
     * Persoon.Geslachtsaanduiding Persoon.Burgerservicenummer Verlies van de Nederlandse nationaliteit
     * @param peildatum datum aanvang geldigheid.
     * @return true indien geboortedatum in bericht gevuld is
     */
    boolean isErEenAanpassingWaardoorReisdocumentVervalt(final int peildatum) {
        final boolean heeftNederlandseNationaliteit = heeftNederlandseNationaliteit(peildatum);
        boolean erIsEenWijziging = false;
        if (isEersteInschrijving() || !heeftNietVermistOfIngehoudenReisDocument()) {
            return false;
        }

        if (heeftNederlandseNationaliteit) {
            for (PersoonElement persoonElement : getPersoonElementen()) {
                if (zijnErWijzigingenInPersonalia(persoonElement, peildatum)
                        || isBSNNummersGewijzigd(persoonElement, peildatum)
                        || isGeslachtsAanduidingAangepast(persoonElement, peildatum)) {
                    erIsEenWijziging = true;
                    break;
                }
            }
        } else {
            if (getNationaliteitHelper().verliestNederlandseNationaliteit(peildatum)) {
                erIsEenWijziging = true;
            }
        }

        return erIsEenWijziging;
    }

    private boolean isGeslachtsAanduidingAangepast(final PersoonElement persoonElement, final int peildatum) {
        if (persoonElement.getGeslachtsaanduiding() != null && Geslachtsaanduiding.bestaatCode(persoonElement.getGeslachtsaanduiding().getCode().getWaarde())) {
            final PersoonGeslachtsaanduidingHistorie actueleGeslachtsAanduiding = getActueleGeslachtsAanduiding(peildatum);
            return actueleGeslachtsAanduiding == null
                    || !Objects
                    .equals(actueleGeslachtsAanduiding.getGeslachtsaanduiding().getCode(), persoonElement.getGeslachtsaanduiding().getCode().getWaarde());
        }

        return false;
    }

    private boolean isBSNNummersGewijzigd(final PersoonElement persoonElement, int peildatum) {
        if (persoonElement.getIdentificatienummers() != null) {
            final PersoonIDHistorie
                    geldigVoorkomenOpPeildatum =
                    MaterieleHistorie.getGeldigVoorkomenOpPeildatum(getDelegates().get(0).getPersoonIDHistorieSet(), peildatum);
            return geldigVoorkomenOpPeildatum == null || !Objects.equals(geldigVoorkomenOpPeildatum.getBurgerservicenummer(),
                    BmrAttribuut.getWaardeOfNull(persoonElement.getIdentificatienummers().getBurgerservicenummer()));

        }
        return false;
    }

    private boolean zijnErWijzigingenInPersonalia(final PersoonElement persoonElement, final int peildatum) {
        return zijnVoornamenAangepast(persoonElement, peildatum)
                || zijnGeslachtsnaamGegevensAangepast(persoonElement, peildatum);
    }

    private boolean zijnGeslachtsnaamGegevensAangepast(final PersoonElement persoonElement, final int peildatum) {
        if (!persoonElement.getGeslachtsnaamcomponenten().isEmpty()) {
            final StringElement stam = persoonElement.getGeslachtsnaamcomponenten().get(0).getStam();
            final PersoonGeslachtsnaamcomponentHistorie actueleGeslachtsnaamComponent = getActueleGeslachtsnaamComponent(peildatum);
            final String actueleStam = actueleGeslachtsnaamComponent != null ? actueleGeslachtsnaamComponent.getStam() : null;
            return !Objects.equals(stam.getWaarde(), actueleStam);
        }
        return false;
    }

    /**
     * vind actuele geslachtsAanduiding op peildatum terug, of null als deze er niet is.
     * @param peildatum peildatum
     * @return actuele {@link PersoonGeslachtsaanduidingHistorie}
     */
    PersoonGeslachtsaanduidingHistorie getActueleGeslachtsAanduiding(final int peildatum) {
        return MaterieleHistorie
                .getGeldigVoorkomenOpPeildatum(getDelegates().get(0).getPersoonGeslachtsaanduidingHistorieSet(), peildatum);
    }


    /**
     * \ vind actuele geslachtsnaamcomponent op peildatum terug, of null als deze er niet is.
     * @param peildatum peildatum
     * @return actuele {@link PersoonGeslachtsnaamcomponent}
     */
    PersoonGeslachtsnaamcomponentHistorie getActueleGeslachtsnaamComponent(final int peildatum) {
        final Iterator<PersoonGeslachtsnaamcomponent>
                persoonGeslachtsnaamcomponentIterator =
                getDelegates().get(0).getPersoonGeslachtsnaamcomponentSet().iterator();
        if (persoonGeslachtsnaamcomponentIterator.hasNext()) {
            return MaterieleHistorie
                    .getGeldigVoorkomenOpPeildatum(persoonGeslachtsnaamcomponentIterator.next().getPersoonGeslachtsnaamcomponentHistorieSet(), peildatum);
        }
        return null;

    }

    private boolean zijnVoornamenAangepast(final PersoonElement persoonElement, final int peildatum) {
        if (!persoonElement.getVoornamen().isEmpty()) {
            final List<PersoonVoornaamHistorie> actueleVoornamen = getActueleVoornamen(peildatum);
            if (persoonElement.getVoornamen().size() != actueleVoornamen.size()) {
                return true;
            }
            Map<Integer, String> voornamenMap = new HashMap<>();
            for (PersoonVoornaamHistorie voornaamHistorie : actueleVoornamen) {
                voornamenMap.put(voornaamHistorie.getPersoonVoornaam().getVolgnummer(), voornaamHistorie.getNaam());
            }
            for (VoornaamElement voornaamElement : persoonElement.getVoornamen()) {
                if (!voornamenMap.get(voornaamElement.getVolgnummer().getWaarde()).equals(voornaamElement.getNaam().getWaarde())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean heeftNietVermistOfIngehoudenReisDocument() {
        if (!getDelegates().get(0).getPersoonReisdocumentSet().isEmpty()) {
            for (PersoonReisdocument doc : getDelegates().get(0).getPersoonReisdocumentSet()) {
                if (doc.isActueelEnGeldig() && doc.getAanduidingInhoudingOfVermissingReisdocument() == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Geeft aan of de samengestelde naam is afgeleid.
     * @return true als de samengestelde naam is afgeleid of als er geen actuele gegevens kunnen worden bepaald.
     */
    boolean isSamengesteldenaamAfgeleid() {
        // op dit moment (30-6-17) is het niet mogelijk om indicatie afgeleid van de samengestelde naam historie in het bericht aan te passen.
        PersoonSamengesteldeNaamHistorie actueelHistorieVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonSamengesteldeNaamHistorieSet());
        return actueelHistorieVoorkomen == null || actueelHistorieVoorkomen.getIndicatieAfgeleid();
    }

    /**
     * Geeft aan of het naamgebruik is afgeleid.
     * @return true als het naamgebruik is afgeleid of als er geen actuele gegevens kunnen worden bepaald.
     */
    boolean isNaamgebruikAfgeleid() {
        final PersoonNaamgebruikHistorie actueelHistorieVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonNaamgebruikHistorieSet());
        return actueelHistorieVoorkomen == null || actueelHistorieVoorkomen.getIndicatieNaamgebruikAfgeleid();
    }

    /**
     * Geeft het actueel naamgebruik terug.
     * @return Naamgebruik
     */
    Naamgebruik getActueelNaamGebruik() {
        final PersoonNaamgebruikHistorie actueelHistorieVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getPersoonNaamgebruikHistorieSet());
        return actueelHistorieVoorkomen.getNaamgebruik();
    }

    /**
     * geeft een lijst met actuele Voornamen op peildatum terug.
     * @param peildatum datum
     * @return List<PersoonVoornaamHistorie>
     */
    public List<PersoonVoornaamHistorie> getActueleVoornamen(final int peildatum) {
        return getDelegates().get(0).getPersoonVoornaamSet().stream()
                .filter(voornaam -> MaterieleHistorie.getGeldigVoorkomenOpPeildatum(voornaam.getPersoonVoornaamHistorieSet(), peildatum) != null)
                .map(voornaam -> MaterieleHistorie.getGeldigVoorkomenOpPeildatum(voornaam.getPersoonVoornaamHistorieSet(), peildatum))
                .collect(Collectors.toList());
    }

    /**
     * Maakt een nieuwe {@link PersoonIDHistorie} voor correcties.
     * @param element het element met de nieuwe identificatienummers
     */
    PersoonIDHistorie maakNieuweIDHistorieVoorCorrectie(final IdentificatienummersElement element) {
        final PersoonIDHistorie historie = new PersoonIDHistorie(getDelegates().get(0));
        historie.setAdministratienummer(BmrAttribuut.getWaardeOfNull(element.getAdministratienummer()));
        historie.setBurgerservicenummer(BmrAttribuut.getWaardeOfNull(element.getBurgerservicenummer()));
        return historie;
    }

    /**
     * Maakt een nieuwe {@link PersoonGeboorteHistorie} voor correcties.
     * @param element het element met de nieuwe geboorte gegevens
     */
    PersoonGeboorteHistorie maakNieuweGeboorteHistorieVoorCorrectie(final GeboorteElement element) {
        final LandOfGebied landOfGebied = element.getDynamischeStamtabelRepository().getLandOfGebiedByCode(element.getLandGebiedCode().getWaarde());
        final PersoonGeboorteHistorie historie = new PersoonGeboorteHistorie(getDelegates().get(0), element.getDatum().getWaarde(), landOfGebied);
        historie.setGemeente(
                element.getDynamischeStamtabelRepository().getGemeenteByGemeentecode(BmrAttribuut.getWaardeOfNull(element.getGemeenteCode())));
        historie.setWoonplaatsnaamGeboorte(BmrAttribuut.getWaardeOfNull(element.getWoonplaatsnaam()));
        historie.setBuitenlandsePlaatsGeboorte(BmrAttribuut.getWaardeOfNull(element.getBuitenlandsePlaats()));
        historie.setBuitenlandseRegioGeboorte(BmrAttribuut.getWaardeOfNull(element.getBuitenlandseRegio()));
        historie.setOmschrijvingGeboortelocatie(BmrAttribuut.getWaardeOfNull(element.getOmschrijvingLocatie()));
        return historie;
    }

    /**
     * Maakt een nieuwe {@link PersoonGeslachtsaanduidingHistorie} voor correcties.
     * @param element het element met de nieuwe geslachtsaanduiding
     */
    PersoonGeslachtsaanduidingHistorie maakNieuweGeslachtsaanduidingHistorieVoorCorrectie(final GeslachtsaanduidingElement element) {
        return new PersoonGeslachtsaanduidingHistorie(getDelegates().get(0), Geslachtsaanduiding.parseCode(element.getCode().getWaarde()));
    }

    /**
     * Maakt een nieuwe {@link PersoonSamengesteldeNaamHistorie} voor correcties.
     * @param samengesteldeNaamElement het element met de nieuwe samengestelde naam
     * @return De nieuw aangemaakte PersoonSamengesteldeNaamHistorie.
     */
    PersoonSamengesteldeNaamHistorie maakNieuweSamengesteldeNaamHistorieVoorCorrectie(final SamengesteldeNaamElement samengesteldeNaamElement) {
        final PersoonSamengesteldeNaamHistorie historie = new PersoonSamengesteldeNaamHistorie(getDelegates().get(0),
                samengesteldeNaamElement.getGeslachtsnaamstam().getWaarde(), false, samengesteldeNaamElement.getIndicatieNamenreeks().getWaarde());
        historie.setAdellijkeTitel(samengesteldeNaamElement.getAdellijkeTitel());
        historie.setPredicaat(samengesteldeNaamElement.getPredicaat());
        historie.setScheidingsteken(BmrAttribuut.getWaardeOfNull(samengesteldeNaamElement.getScheidingsteken()));
        historie.setVoornamen(BmrAttribuut.getWaardeOfNull(samengesteldeNaamElement.getVoornamen()));
        historie.setVoorvoegsel(BmrAttribuut.getWaardeOfNull(samengesteldeNaamElement.getVoorvoegsel()));
        return historie;
    }

    @Override
    public Set<PersoonIDHistorie> getPersoonIDHistorieSet() {
        final BijhoudingPersoonGroepSet<PersoonIDHistorie> historiePerDelegate = new BijhoudingPersoonGroepSet<>();
        for (final Persoon delegate : getDelegates()) {
            historiePerDelegate.addPersoonGroepSet(delegate, delegate.getPersoonIDHistorieSet());
        }

        return historiePerDelegate;
    }

    @Override
    public Set<PersoonSamengesteldeNaamHistorie> getPersoonSamengesteldeNaamHistorieSet() {
        final BijhoudingPersoonGroepSet<PersoonSamengesteldeNaamHistorie> historiePerDelegate = new BijhoudingPersoonGroepSet<>();
        for (final Persoon delegate : getDelegates()) {
            historiePerDelegate.addPersoonGroepSet(delegate, delegate.getPersoonSamengesteldeNaamHistorieSet());
        }
        return historiePerDelegate;
    }

    @Override
    public Set<PersoonGeboorteHistorie> getPersoonGeboorteHistorieSet() {
        final BijhoudingPersoonGroepSet<PersoonGeboorteHistorie> historiePerDelegate = new BijhoudingPersoonGroepSet<>();
        for (final Persoon delegate : getDelegates()) {
            historiePerDelegate.addPersoonGroepSet(delegate, delegate.getPersoonGeboorteHistorieSet());
        }

        return historiePerDelegate;
    }

    @Override
    public Set<PersoonGeslachtsaanduidingHistorie> getPersoonGeslachtsaanduidingHistorieSet() {
        final BijhoudingPersoonGroepSet<PersoonGeslachtsaanduidingHistorie> historiePerDelegate = new BijhoudingPersoonGroepSet<>();
        for (final Persoon delegate : getDelegates()) {
            historiePerDelegate.addPersoonGroepSet(delegate, delegate.getPersoonGeslachtsaanduidingHistorieSet());
        }

        return historiePerDelegate;
    }

    /**
     * Geeft aan of er bij de persoon een onderzoek is die een status heeft ongelijk aan {@link StatusOnderzoek#AFGESLOTEN}.
     * @return true als er 1 of meerdere onderzoeken zijn die niet de status {@link StatusOnderzoek#AFGESLOTEN} heeft
     */
    boolean heeftLopendOnderzoek() {
        return getOnderzoeken().stream().anyMatch(onderzoek -> !onderzoek.getStatusOnderzoek().equals(StatusOnderzoek.AFGESLOTEN));
    }

    private static final class VoorkomenSleutel {
        private final Long id;
        private final Class<? extends FormeleHistorie> type;

        private VoorkomenSleutel(final Long id, final Class<? extends FormeleHistorie> type) {
            this.id = id;
            this.type = type;
        }

        @Override
        public boolean equals(final Object anderObject) {
            if (this == anderObject) {
                return true;
            }
            if (anderObject == null || getClass() != anderObject.getClass()) {
                return false;
            }
            final VoorkomenSleutel andereSleutel = (VoorkomenSleutel) anderObject;
            return new EqualsBuilder()
                    .append(id, andereSleutel.id)
                    .append(type, andereSleutel.type)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(id)
                    .append(type)
                    .toHashCode();
        }
    }

    private static final class VoorkomenPaar {
        private static final VoorkomenPaar NULL = new VoorkomenPaar();

        private final FormeleHistorie oud;
        private final FormeleHistorie nieuw;
        private boolean isNull = false;

        private VoorkomenPaar() {
            this(null, null);
            isNull = true;
        }

        private VoorkomenPaar(final FormeleHistorie oud, final FormeleHistorie nieuw) {
            this.oud = oud;
            this.nieuw = nieuw;
        }

        private void registreerKopieHistorie(final BijhoudingPersoon persoon) {
            if (isNull) {
                return;
            }
            persoon.registreerKopieHistorie(oud.getId().longValue(), nieuw);
        }
    }
}
