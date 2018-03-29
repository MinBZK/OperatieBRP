/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * Een persoon element waarbij de gegevens (niet zijn de relatiegegevens) worden gewijzigd.
 */
@XmlElement("persoon")
public final class PersoonGegevensElement extends AbstractPersoonElement {
    private static final String OBJECT_TYPE = "Persoon";
    private static final int MAXIMALE_TOTALE_LENGTE_VOORNAMEN = 200;

    /**
     * Maakt een PersoonElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param afgeleidAdministratief afgeleid administratief
     * @param identificatienummers identificatienummers
     * @param samengesteldeNaam samengesteldeNaam
     * @param geboorte geboorte
     * @param geslachtsaanduiding geslachtsaanduiding
     * @param bijhouding bijhouding
     * @param verblijfsrecht verblijfsrecht
     * @param voornamen voornamen
     * @param geslachtsnaamcomponenten geslachtsnaamcomponenten
     * @param naamgebruik naamgebruik
     * @param adressen adres elementen
     * @param indicaties indicaties
     * @param verstrekkingsbeperkingen verstrekkingsbeperkingen
     * @param migratie MigratieElement
     * @param nationaliteiten nationaliteiten
     * @param onderzoeken onderzoeken
     */
    public PersoonGegevensElement(
            final Map<String, String> basisAttribuutGroep,
            final AfgeleidAdministratiefElement afgeleidAdministratief,
            final IdentificatienummersElement identificatienummers,
            final SamengesteldeNaamElement samengesteldeNaam,
            final GeboorteElement geboorte,
            final GeslachtsaanduidingElement geslachtsaanduiding,
            final BijhoudingElement bijhouding,
            final VerblijfsrechtElement verblijfsrecht,
            final List<VoornaamElement> voornamen,
            final List<GeslachtsnaamcomponentElement> geslachtsnaamcomponenten,
            final NaamgebruikElement naamgebruik,
            final List<AdresElement> adressen,
            final List<IndicatieElement> indicaties,
            final List<VerstrekkingsbeperkingElement> verstrekkingsbeperkingen,
            final MigratieElement migratie,
            final List<NationaliteitElement> nationaliteiten,
            final List<OnderzoekElement> onderzoeken) {
        super(basisAttribuutGroep, afgeleidAdministratief, identificatienummers, samengesteldeNaam, geboorte, geslachtsaanduiding, bijhouding, verblijfsrecht,
                voornamen, geslachtsnaamcomponenten, naamgebruik, adressen, indicaties, verstrekkingsbeperkingen, migratie, nationaliteiten, onderzoeken);

        if (getObjectSleutel() == null && getReferentieId() == null && !getGeslachtsnaamcomponenten().isEmpty()) {
            setNieuwIngeschrevenBijhoudingPersoon(new BijhoudingPersoon());
        }
    }

    /**
     * Maakt een nieuw {@link Persoon} object o.b.v. het bijhoudingsbericht en de gegeven persoon en
     * verantwoordingsgegevens van het soort {@link SoortPersoon#PSEUDO_PERSOON}.
     * @param actie de actie
     * @param datumAanvangGeldigheid de datun aanvang geldigheid
     * @return een nieuw persoon
     */
    public BijhoudingPersoon maakPseudoPersoonEntiteit(final BRPActie actie, final int datumAanvangGeldigheid) {
        final BijhoudingPersoon result = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.PSEUDO_PERSOON));
        if (actie == null) {
            result.registreerPersoonElement(this);
            return result;
        }
        if (getIdentificatienummers() != null) {
            result.voegPersoonIDHistorieToe(getIdentificatienummers(), actie, datumAanvangGeldigheid);
        }
        if (getSamengesteldeNaam() != null) {
            result.voegPersoonSamengesteldeNaamHistorieToe(getSamengesteldeNaam(), actie, datumAanvangGeldigheid);
        }
        if (getGeboorte() != null) {
            result.voegPersoonGeboorteHistorieToe(getGeboorte(), actie);
        }
        if (getGeslachtsaanduiding() != null) {
            result.voegPersoonGeslachtsaanduidingHistorieToe(getGeslachtsaanduiding(), actie, datumAanvangGeldigheid);
        }
        return result;
    }

    /**
     * Controleer of dit persoon element een wijziging betreft van gegevens die van invloed kunnen zijn op de gerelateerde gegevens van een kopie
     * pseudo persoon van deze persoon. Deze controle wordt gebruikt of een bijhouding mogelijk verschil kan veroorzaken tussen de gegevens van deze persoon en
     * eventuele kopie pseudo personen van deze persoon.
     * @return true als dit waar is, anders false
     */
    public boolean isWijzigingVanInvloedOpGerelateerdePseudoPersoon() {
        if (!bevatWijzigingVanGerelateerdeGegevens()) {
            return false;
        }
        final BijhoudingPersoon persoonEntiteit = getPersoonEntiteit();
        if (persoonEntiteit != null && persoonEntiteit.isPersoonIngeschrevene()) {
            final List<Persoon>
                    pseudoPersonen =
                    getPersoonRepository().zoekGerelateerdePseudoPersonen(persoonEntiteit.getAdministratienummer(), persoonEntiteit.getBurgerservicenummer());
            for (final Persoon pseudoPersoon : pseudoPersonen) {
                final boolean isKind = !pseudoPersoon.getActueleBetrokkenheidSet(SoortBetrokkenheid.KIND).isEmpty();
                if (bevatWijzigingVanGerelateerdeGegevens(isKind)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean bevatWijzigingVanGerelateerdeGegevens() {
        return bevatWijzigingVanGerelateerdeGegevens(false);
    }

    private boolean bevatWijzigingVanGerelateerdeGegevens(final boolean isGerelateerdeKind) {
        final List<Object> gerelateerdeGegevens =
                new ArrayList<>(Arrays.asList(getIdentificatienummers(), getGeboorte(), getSamengesteldeNaam()));
        if (!isGerelateerdeKind) {
            gerelateerdeGegevens.add(getGeslachtsaanduiding());
        }
        return !ValidationUtils.zijnParametersAllemaalNull(gerelateerdeGegevens.toArray()) || !getVoornamen().isEmpty() || !getGeslachtsnaamcomponenten()
                .isEmpty();
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();

        final Persoon persoonEntiteit = getPersoonEntiteit();

        if (persoonEntiteit != null && persoonEntiteit.isPersoonIngeschrevene()) {
            controleerNaamWijzigingEnBestaanVanKinderen(meldingen, persoonEntiteit);
            controleerGewijzigdeSamengesteldeNaamEnNamenreeksIsJa(meldingen, persoonEntiteit);
            controleerPersoonNietGeblokkeerd(meldingen, persoonEntiteit);
            controleerPersoonNadereBijhoudingsAard(meldingen, persoonEntiteit);
            controleerVerstrekkingsbeperking(meldingen);
            controleerWijzigingVanInvloedOpGerelateerdeGegevens(meldingen);
            controleerOnderzoeken(meldingen);
            controleerNaamwijzigingDoorgewerktNaarAfgeleideNaamGegevens(meldingen);
            controleerNaamswijzigingDoorgewerktNaarPartner(meldingen);
        } else {
            controleerSamengesteldeNaamGewijzigdPseudoPartnerDoorgewerktNaarEigenPersoon(meldingen);
            controleerVerplichteGroepenAanwezigVoorPseudoPersoon(meldingen);
            controleerNamenreeksEnVoorvoegselGeslachtsnaamOngeldig(meldingen);
        }
        controleerNamenReeksEnVoornamen(meldingen, persoonEntiteit);
        controleerVoornamen(meldingen);
        return meldingen;
    }

    @Bedrijfsregel(Regel.R1809)
    private void controleerNamenReeksEnVoornamen(final List<MeldingElement> meldingen, final Persoon persoonEntiteit) {
        if (getVerzoekBericht().getAdministratieveHandeling().getSoort().isCorrectie()) {
            return;
        }

        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = persoonEntiteit == null ? null
                : FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoonEntiteit.getPersoonSamengesteldeNaamHistorieSet());

        final boolean heeftIndicatieNamenreeks = samengesteldeNaamHistorie != null && samengesteldeNaamHistorie.getIndicatieNamenreeks();

        final SamengesteldeNaamElement samengesteldeNaam = getSamengesteldeNaam();
        if (samengesteldeNaam != null) {
            final boolean krijgtVoornamen = !getVoornamen().isEmpty() || getSamengesteldeNaam().getVoornamen() != null;
            final Boolean krijgtIndicatie = samengesteldeNaam.getIndicatieNamenreeks().getWaarde();

            if (krijgtIndicatie && krijgtVoornamen) {
                meldingen.add(MeldingElement.getInstance(Regel.R1809, this));
            }
        } else if (!getVoornamen().isEmpty() && heeftIndicatieNamenreeks) {
            meldingen.add(MeldingElement.getInstance(Regel.R1809, this));
        }
    }

    @Bedrijfsregel(Regel.R1807)
    @Bedrijfsregel(Regel.R1919)
    @Bedrijfsregel(Regel.R1920)
    private void controleerVoornamen(final List<MeldingElement> meldingen) {
        if (!getVoornamen().isEmpty()) {
            final Iterator<VoornaamElement> iterator = getVoornamen().iterator();
            VoornaamElement voornaamElement = iterator.next();
            String voornaam = voornaamElement.getNaam().getWaarde();
            int volgnummer = voornaamElement.getVolgnummer().getWaarde();
            boolean voornaamBevatSpaties = voornaam.indexOf(' ') > -1;
            int gewenstVolgnummer = 1;
            boolean isCorrectVolgnummer = gewenstVolgnummer == volgnummer;
            StringBuilder voornamenAchterElkaar = new StringBuilder(voornaam);
            while (iterator.hasNext()) {
                gewenstVolgnummer++;
                voornaamElement = iterator.next();
                voornaam = voornaamElement.getNaam().getWaarde();
                voornaamBevatSpaties = voornaamBevatSpaties || voornaam.indexOf(' ') > -1;
                voornamenAchterElkaar.append(" ");
                voornamenAchterElkaar.append(voornaam);
                isCorrectVolgnummer = isCorrectVolgnummer && gewenstVolgnummer == voornaamElement.getVolgnummer().getWaarde();
            }
            if (voornamenAchterElkaar.length() > MAXIMALE_TOTALE_LENGTE_VOORNAMEN) {
                meldingen.add(MeldingElement.getInstance(Regel.R1807, this));
            }
            if (voornaamBevatSpaties) {
                meldingen.add(MeldingElement.getInstance(Regel.R1919, this));
            }
            if (!isCorrectVolgnummer) {
                meldingen.add(MeldingElement.getInstance(Regel.R1920, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1909)
    private void controleerVerstrekkingsbeperking(final List<MeldingElement> meldingen) {
        final Predicate<IndicatieElement> predicate = indicatieElement -> indicatieElement instanceof VolledigeVerstrekkingsbeperkingIndicatieElement;
        if (!getVerstrekkingsbeperkingen().isEmpty() && getIndicaties().stream()
                .anyMatch(predicate)) {
            meldingen.add(MeldingElement.getInstance(Regel.R1909, this));
        }
    }

    @Bedrijfsregel(Regel.R2437)
    private void controleerWijzigingVanInvloedOpGerelateerdeGegevens(List<MeldingElement> meldingen) {
        if (isWijzigingVanInvloedOpGerelateerdePseudoPersoon()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2437, this));
        }
    }

    private void controleerOnderzoeken(final List<MeldingElement> meldingen) {
        for (final OnderzoekElement onderzoekElement : getOnderzoeken()) {
            for (final GegevenInOnderzoekElement gegevenInOnderzoekElement : onderzoekElement.getGegevensInOnderzoek()) {
                gegevenInOnderzoekElement.controleerAangewezenObject(meldingen, getPersoonEntiteit());
                gegevenInOnderzoekElement.controleerAangewezenVoorkomen(meldingen, getPersoonEntiteit());
            }
        }
    }

    @Bedrijfsregel(Regel.R1580)
    private void controleerPersoonNadereBijhoudingsAard(final List<MeldingElement> meldingen, final Persoon persoon) {
        final PersoonBijhoudingHistorie bijhoudingHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonBijhoudingHistorieSet());
        if (bijhoudingHistorie != null &&
                (NadereBijhoudingsaard.FOUT.equals(bijhoudingHistorie.getNadereBijhoudingsaard())
                        || NadereBijhoudingsaard.GEWIST.equals(bijhoudingHistorie.getNadereBijhoudingsaard()))) {
            meldingen.add(MeldingElement.getInstance(Regel.R1580, this));
        }
    }

    @Bedrijfsregel(Regel.R2132)
    private void controleerGewijzigdeSamengesteldeNaamEnNamenreeksIsJa(final List<MeldingElement> meldingen, final Persoon persoon) {
        final PersoonSamengesteldeNaamHistorie actueelHistorieVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonSamengesteldeNaamHistorieSet());
        if (!getGeslachtsnaamcomponenten().isEmpty()
                && actueelHistorieVoorkomen != null
                && actueelHistorieVoorkomen.getIndicatieAfgeleid()
                && actueelHistorieVoorkomen.getIndicatieNamenreeks()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2132, this));
        }
    }

    @Bedrijfsregel(Regel.R2173)
    private void controleerNamenreeksEnVoorvoegselGeslachtsnaamOngeldig(final List<MeldingElement> meldingen) {
        boolean isSamengesteldeNaamOk = getSamengesteldeNaam() != null && getSamengesteldeNaam().getIndicatieNamenreeks().getWaarde();
        if (isSamengesteldeNaamOk && !getGeslachtsnaamcomponenten().isEmpty() && getGeslachtsnaamcomponenten().get(0).getVoorvoegsel() != null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2173, this));
        }
    }

    @Bedrijfsregel(Regel.R1625)
    private void controleerVerplichteGroepenAanwezigVoorPseudoPersoon(final List<MeldingElement> meldingen) {
        final boolean isGeenBestaandOfReferentiePersoon = !heeftObjectSleutel() && !heeftReferentie();
        if (isGeenBestaandOfReferentiePersoon && (getGeslachtsaanduiding() == null || getGeboorte() == null || getSamengesteldeNaam() == null)) {
            meldingen.add(MeldingElement.getInstance(Regel.R1625, this));
        }
    }

    @Bedrijfsregel(Regel.R1572)
    private void controleerNaamWijzigingEnBestaanVanKinderen(final List<MeldingElement> meldingen, final Persoon persoon) {
        if (!getGeslachtsnaamcomponenten().isEmpty() && persoon != null && !persoon.getRelaties(SoortBetrokkenheid.OUDER).isEmpty()) {
            meldingen.add(MeldingElement.getInstance(Regel.R1572, this));
        }
    }

    @Bedrijfsregel(Regel.R2098)
    private void controleerPersoonNietGeblokkeerd(final List<MeldingElement> meldingen, final Persoon persoon) {
        if (!getVerzoekBericht().isPrevalidatie() && getPersoonRepository().isPersoonGeblokkeerd(persoon)) {
            meldingen.add(MeldingElement.getInstance(Regel.R2098, this));
        }
    }

    @Bedrijfsregel(Regel.R2736)
    @Bedrijfsregel(Regel.R2737)
    private void controleerNaamwijzigingDoorgewerktNaarAfgeleideNaamGegevens(final List<MeldingElement> meldingen) {
        final BijhoudingPersoon persoon = getPersoonEntiteit();
        final boolean heeftNaamWijziging = !getGeslachtsnaamcomponenten().isEmpty() || !getVoornamen().isEmpty();
        if (heeftNaamWijziging) {
            if (!persoon.isSamengesteldenaamAfgeleid() ) {
                meldingen.add(MeldingElement.getInstance(Regel.R2736, this));
            } else if (!persoon.isNaamgebruikAfgeleid() && !Naamgebruik.PARTNER.equals(persoon.getActueelNaamGebruik())) {
                meldingen.add(MeldingElement.getInstance(Regel.R2737, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R2738)
    private void controleerNaamswijzigingDoorgewerktNaarPartner(final List<MeldingElement> meldingen) {
        final BijhoudingPersoon persoon = getPersoonEntiteit();
        final Persoon partner = persoon.getActuelePartner();
        if (partner != null && !getGeslachtsnaamcomponenten().isEmpty() && !persoon.isSamengesteldenaamAfgeleid() && !Naamgebruik.EIGEN
                .equals(partner.getNaamgebruik())) {
            meldingen.add(MeldingElement.getInstance(Regel.R2738, this));
        }
    }

    @Bedrijfsregel(Regel.R2739)
    private void controleerSamengesteldeNaamGewijzigdPseudoPartnerDoorgewerktNaarEigenPersoon(final List<MeldingElement> meldingen) {
        if (heeftPersoonEntiteit() && getSamengesteldeNaam() != null) {
            final BijhoudingPersoon hoofdPersoon = getVerzoekBericht().getAdministratieveHandeling().getHoofdActie().getHoofdPersonen().get(0);
            if (!hoofdPersoon.isNaamgebruikAfgeleid() && !Naamgebruik.EIGEN.equals(hoofdPersoon.getActueelNaamGebruik())) {
                meldingen.add(MeldingElement.getInstance(Regel.R2739, this));
            }
        }
    }

    /**
     * Maakt een nieuw {@link PersoonElement}.
     * @param identificatienummersElement identificatienummers element
     * @return een nieuw {@link PersoonElement}
     */
    public static PersoonGegevensElement getInstance(final IdentificatienummersElement identificatienummersElement) {
        final Map<String, String> attributen = new AttributenBuilder().objecttype("Persoon").build();
        return new PersoonGegevensElement(attributen, null, identificatienummersElement, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null
        );
    }

    /**
     * Maakt een PersoonElement o.b.v. een bestaande persoon entiteit. Als de gegeven persoon entiteit een ingeschreven persoon is dan moet deze al voorkomen in
     * de objectsleutelindex en moet een id hebben. Bij het maken van het persoon element wordt een unieke objectsleutel gegenereerd die wordt toegevoegd aan de
     * index zodat bij verdere verwerking de gegeven persoon entiteit zal worden gevonden bij dit persoon element.
     * @param persoonEntiteit de persoon entiteit
     * @param verzoekBericht het bericht waar de nieuwe persoon element deel vanuit moet gaan maken
     * @return het nieuwe persoon element
     */
    public static PersoonGegevensElement getInstance(final BijhoudingPersoon persoonEntiteit, final BijhoudingVerzoekBericht verzoekBericht) {
        final Map<String, String> attributen;
        final IdentificatienummersElement identificatienummersElement;
        final SamengesteldeNaamElement samengesteldeNaamElement;
        final GeboorteElement geboorteElement;
        final GeslachtsaanduidingElement geslachtsaanduidingElement;
        if (persoonEntiteit.isPersoonIngeschrevene()) {
            identificatienummersElement = null;
            samengesteldeNaamElement = null;
            geboorteElement = null;
            geslachtsaanduidingElement = null;
            final String objectSleutel = UUID.randomUUID().toString();
            verzoekBericht.voegObjectSleutelToe(objectSleutel, BijhoudingPersoon.class, persoonEntiteit.getId());
            attributen = new AttributenBuilder().objecttype(OBJECT_TYPE).objectSleutel(objectSleutel).build();
        } else {
            attributen = new AttributenBuilder().objecttype(OBJECT_TYPE).build();
            identificatienummersElement =
                    IdentificatienummersElement
                            .getInstance(FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoonEntiteit.getPersoonIDHistorieSet()),
                                    verzoekBericht);
            samengesteldeNaamElement =
                    SamengesteldeNaamElement.getInstance(
                            FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoonEntiteit.getPersoonSamengesteldeNaamHistorieSet()),
                            verzoekBericht);
            geboorteElement =
                    GeboorteElement.getInstance(
                            FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoonEntiteit.getPersoonGeboorteHistorieSet()), verzoekBericht);
            geslachtsaanduidingElement =
                    GeslachtsaanduidingElement.getInstance(
                            FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoonEntiteit.getPersoonGeslachtsaanduidingHistorieSet()),
                            verzoekBericht);
        }
        final PersoonGegevensElement
                result =
                new PersoonGegevensElement(attributen, null, identificatienummersElement,
                        samengesteldeNaamElement, geboorteElement, geslachtsaanduidingElement, null, null, null, null, null, null, null, null, null, null, null
                );
        result.setVerzoekBericht(verzoekBericht);
        return result;
    }

    @Override
    public boolean bevatGerelateerdeGegevens() {
        return !ValidationUtils.zijnParametersAllemaalNull(
                getIdentificatienummers(),
                getGeslachtsaanduiding(),
                getGeboorte(),
                getSamengesteldeNaam());
    }

}
