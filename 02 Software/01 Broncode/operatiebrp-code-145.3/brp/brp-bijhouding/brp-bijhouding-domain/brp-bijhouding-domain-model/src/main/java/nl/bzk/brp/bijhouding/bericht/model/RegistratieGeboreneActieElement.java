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
import java.util.Objects;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperking;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * De actie voor het registreren van een geborene.
 */
//
@XmlElement("registratieGeborene")
public final class RegistratieGeboreneActieElement extends AbstractEersteInschrijvingActieElement implements ActieMetOuderGegevens {

    private final FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekking;

    /**
     * Maakt een AbstractActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param familierechtelijkeBetrekking de familierechtelijke betrekking
     */
    public RegistratieGeboreneActieElement(final Map<String, String> basisAttribuutGroep,
                                           final DatumElement datumAanvangGeldigheid,
                                           final DatumElement datumEindeGeldigheid,
                                           final List<BronReferentieElement> bronReferenties,
                                           final FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekking) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties);
        this.familierechtelijkeBetrekking = familierechtelijkeBetrekking;

        familierechtelijkeBetrekking.getBetrokkenheidElementen(BetrokkenheidElementSoort.OUDER).forEach(ouder -> {
            if (ouder.getOuderschap() == null) {
                ouder.maakOuderOuwkig();
            }
        });

    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_GEBORENE;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BRPActie actie;
        if (zijnAlleHoofdPersonenVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            vulPersoonEntiteitKind(actie);

            familierechtelijkeBetrekking.maakRelatieEntiteitEnBetrokkenen(actie, getDatumAanvangGeldigheid().getWaarde());
        } else {
            actie = null;
        }
        return actie;
    }

    private void vulPersoonEntiteitKind(final BRPActie actie) {
        final PersoonElement kindElement = getFamilierechtelijkeBetrekking().getPersoonElementen().get(0);
        final BijhoudingPersoon kindEntiteit = kindElement.getPersoonEntiteit();

        // Inschrijving
        final Integer datumAanvangGeldigheid = getDatumAanvangGeldigheid().getWaarde();
        kindEntiteit.voegPersoonInschrijvingHistorieToe(datumAanvangGeldigheid, 1L, getVerzoekBericht().getTijdstipOntvangst().toTimestamp(), actie);

        // Bijhouding
        kindEntiteit.voegPersoonBijhoudingHistorieToe(kindEntiteit.getBijhoudingspartijVoorBijhoudingsplan(), Bijhoudingsaard.INGEZETENE,
                NadereBijhoudingsaard.ACTUEEL,
                datumAanvangGeldigheid, actie);

        // Geslachtsaanduiding
        kindEntiteit.voegPersoonGeslachtsaanduidingHistorieToe(kindElement.getGeslachtsaanduiding(), actie, datumAanvangGeldigheid);

        // Geboorte
        kindEntiteit.voegPersoonGeboorteHistorieToe(kindElement.getGeboorte(), actie);

        // Voornaam
        kindEntiteit.voegPersoonVoornamenHistorieToe(kindElement.getVoornamen(), actie, datumAanvangGeldigheid);

        // Geslachtsnaamcomponent
        kindEntiteit.voegPersoonGeslachtsnaamComponentHistorieToe(kindElement.getGeslachtsnaamcomponenten().get(0), actie, datumAanvangGeldigheid);

        // Samengestelde naam
        kindEntiteit.leidAf(actie, datumAanvangGeldigheid, false,
                BmrAttribuut.getWaardeOfNull(kindElement.getSamengesteldeNaam().getIndicatieNamenreeks()));

        // Naamgebruik
        kindEntiteit.leidtNaamgebruikAf(Naamgebruik.EIGEN, actie, false);

        final BijhoudingPersoon ouwkig = getOuwkig();

        // Verstrekkingsbeperking
        verwerkVerstrekkingsBeperking(actie, kindElement, kindEntiteit, ouwkig);

        // BVP
        verwerkBVP(actie, kindEntiteit, getOuders(), getVerzoekBericht());

        // Adres
        verwerkAdres(actie, kindEntiteit, datumAanvangGeldigheid, ouwkig);

    }

    private void verwerkAdres(final BRPActie actie, final BijhoudingPersoon kindEntiteit, final Integer datumAanvangGeldigheid,
                              final BijhoudingPersoon ouwkig) {
        if (ouwkig != null) {
            for (final PersoonAdres ouwkigAdres : ouwkig.getPersoonAdresSet()) {
                if (FormeleHistorieZonderVerantwoording.heeftActueelVoorkomen(ouwkigAdres.getPersoonAdresHistorieSet())) {
                    final RedenWijzigingVerblijf redenWijzigingVerblijf =
                            getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(RedenWijzigingVerblijf.AMBTSHALVE);
                    kindEntiteit.kopieerAdres(ouwkigAdres, redenWijzigingVerblijf, datumAanvangGeldigheid, actie);
                }
            }
        }

    }


    private void verwerkVerstrekkingsBeperking(final BRPActie actie, final PersoonElement kindElement, final BijhoudingPersoon kindEntiteit,
                                               final BijhoudingPersoon ouwkig) {
        if (ouwkig != null && getVerzoekBericht().getAdministratieveHandeling().getActieBySoort(SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING) == null) {
            for (PersoonVerstrekkingsbeperking verstrekkingsbeperking : ouwkig.getPersoonVerstrekkingsbeperkingSet()) {
                final Partij partij = verstrekkingsbeperking.getPartij();
                if (FormeleHistorieZonderVerantwoording.heeftActueelVoorkomen(verstrekkingsbeperking.getPersoonVerstrekkingsbeperkingHistorieSet())
                        && (partij == null || DatumUtil
                        .valtDatumBinnenPeriode(kindElement.getGeboorte().getDatum().getWaarde(), partij.getDatumIngang(), partij.getDatumEinde()))) {
                    kindEntiteit.kopieerVerstrekkingsbeperking(verstrekkingsbeperking, actie);
                }
            }
        }
    }

    @Override
    public List<BijhoudingPersoon> getHoofdPersonen() {
        return familierechtelijkeBetrekking.getHoofdPersonen();
    }

    @Override
    public List<PersoonElement> getPersoonElementen() {
        return familierechtelijkeBetrekking.getPersoonElementen().stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public DatumElement getPeilDatum() {
        return familierechtelijkeBetrekking.getPersoonElementen().get(0).getGeboorte().getDatum();
    }

    @Override
    public PersoonElement getKind() {
        return familierechtelijkeBetrekking.getBetrokkenheidElementen(BetrokkenheidElementSoort.KIND).get(0).getPersoon();
    }

    @Override
    protected List<MeldingElement> valideerImplementerendeActies() {
        List<MeldingElement> meldingen = new ArrayList<>();
        final BijhoudingPersoon ouwkig = getOuwkig();
        if (ouwkig != null) {
            final PersoonBijhoudingHistorie
                    geldigBijhoudingsHistorie =
                    MaterieleHistorie.getGeldigVoorkomenOpPeildatum(ouwkig.getPersoonBijhoudingHistorieSet(), getPeilDatum().getWaarde());
            controleerIdentificatieNummers(meldingen, geldigBijhoudingsHistorie);
            controleerIndienendeGemeente(geldigBijhoudingsHistorie, meldingen);
            controleerAdresOuders(meldingen);
        }
        return meldingen;
    }

    @Override
    PersoonElement getInTeSchrijvenPersoon() {
        return getFamilierechtelijkeBetrekking().getBetrokkenheidElementen(BetrokkenheidElementSoort.KIND).get(0).getPersoon();
    }

    @Bedrijfsregel(Regel.R2487)
    private void controleerAdresOuders(final List<MeldingElement> meldingen) {
        for (final PersoonElement ouder : getOuders()) {
            if (ouder.getPersoonEntiteit() != null && !ouder.getPersoonEntiteit().getPersoonAdresSet().isEmpty()) {
                heeftOuderNederlandsAdresEnBuitenlandRegels(meldingen, ouder);
            }
        }
    }

    private void heeftOuderNederlandsAdresEnBuitenlandRegels(final List<MeldingElement> meldingen, final PersoonElement ouder) {
        for (final PersoonAdres adres : ouder.getPersoonEntiteit().getPersoonAdresSet()) {
            final PersoonAdresHistorie geldigVoorkomen =
                    MaterieleHistorie.getGeldigVoorkomenOpPeildatum(adres.getPersoonAdresHistorieSet(), getPeilDatum().getWaarde());
            if (geldigVoorkomen != null && Objects.equals(LandOfGebied.CODE_NEDERLAND, geldigVoorkomen.getLandOfGebied().getCode())
                    && !ValidationUtils.zijnParametersAllemaalNull(geldigVoorkomen.getBuitenlandsAdresRegel1(),
                    geldigVoorkomen.getBuitenlandsAdresRegel2(),
                    geldigVoorkomen.getBuitenlandsAdresRegel3(),
                    geldigVoorkomen.getBuitenlandsAdresRegel4(),
                    geldigVoorkomen.getBuitenlandsAdresRegel5(),
                    geldigVoorkomen.getBuitenlandsAdresRegel6())) {
                meldingen.add(MeldingElement.getInstance(Regel.R2487, ouder));
            }
        }
    }

    @Bedrijfsregel(Regel.R2434)
    private void controleerIndienendeGemeente(final PersoonBijhoudingHistorie ouwkig, final List<MeldingElement> meldingen) {
        final Partij indienendePartij = getVerzoekBericht().getZendendePartij();
        final Partij bijhoudendePartijOuwkig = ouwkig != null ? ouwkig.getPartij() : null;
        if (indienendePartij.getRollen().contains(Rol.BIJHOUDINGSORGAAN_COLLEGE)
                && (bijhoudendePartijOuwkig == null || !indienendePartij.getId().equals(bijhoudendePartijOuwkig.getId()))) {
            boolean kindpartijGelijk = false;
            for (PersoonElement kind : getHoofdPersonen().get(0).getPersoonElementen()) {
                if (kind.getGeboorte() != null) {
                    final String geboorteGemeenteCode = kind.getGeboorte().getGemeenteCode().getWaarde();
                    final Gemeente gemeente = getDynamischeStamtabelRepository().getGemeenteByGemeentecode(geboorteGemeenteCode);
                    kindpartijGelijk = kindpartijGelijk || indienendePartij.getId().equals(gemeente.getPartij().getId())
                            || gemeente.isPartijVoortzettendeGemeentePartij(indienendePartij);
                }
            }
            if (!kindpartijGelijk) {
                meldingen.add(MeldingElement.getInstance(Regel.R2434, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R2408)
    @Bedrijfsregel(Regel.R2409)
    private void controleerIdentificatieNummers(final List<MeldingElement> meldingen, final PersoonBijhoudingHistorie geldigBijhoudingsHistorie) {
        if (geldigBijhoudingsHistorie != null) {
            boolean identificatieNummersAanwezig = false;
            for (PersoonElement persoonElement : getHoofdPersonen().get(0).getPersoonElementen()) {
                identificatieNummersAanwezig = identificatieNummersAanwezig || persoonElement.getIdentificatienummers() != null;
            }
            final boolean zijnPartijenGelijk = geldigBijhoudingsHistorie.getPartij().getId().equals(getVerzoekBericht().getZendendePartij().getId());
            if (zijnPartijenGelijk && !identificatieNummersAanwezig) {
                meldingen.add(MeldingElement.getInstance(Regel.R2408, this));
            } else if (!zijnPartijenGelijk && identificatieNummersAanwezig) {
                meldingen.add(MeldingElement.getInstance(Regel.R2409, this));
            }

        }
    }

    public FamilierechtelijkeBetrekkingElement getFamilierechtelijkeBetrekking() {
        return familierechtelijkeBetrekking;
    }


    @Override
    public List<PersoonElement> getOuders() {
        return familierechtelijkeBetrekking.getOuders().stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<PersoonElement> getNouwkigs() {
        final BetrokkenheidElement nouwkigBetrokkenheid = familierechtelijkeBetrekking.getOuderBetrokkenheidElement(false);
        return nouwkigBetrokkenheid != null ? Collections.singletonList(nouwkigBetrokkenheid.getPersoon()) : Collections.emptyList();
    }

    /**
     * Geeft de {@link BijhoudingPersoon} terug van de persoon waar de indicatie "ouder uit wie kind is gekomen" op true staat.
     * @return de {@link BijhoudingPersoon} behorende bij de OUWKIG
     */
    public BijhoudingPersoon getOuwkig() {

        final BetrokkenheidElement ouwkigBetrokkenheid = familierechtelijkeBetrekking.getOuderBetrokkenheidElement(true);
        if (ouwkigBetrokkenheid!=null && ouwkigBetrokkenheid.heeftPersoonEntiteit()) {
            return ouwkigBetrokkenheid.getPersoon().getPersoonEntiteit();
        }
        return null;
    }
}
