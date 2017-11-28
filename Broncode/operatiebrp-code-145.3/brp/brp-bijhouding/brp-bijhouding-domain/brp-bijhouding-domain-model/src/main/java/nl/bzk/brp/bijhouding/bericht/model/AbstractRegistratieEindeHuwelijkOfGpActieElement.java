/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlTransient;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * De base class voor het registreren van een einde van een huwelijk of geregistreerd partnerschap.
 */
public abstract class AbstractRegistratieEindeHuwelijkOfGpActieElement extends AbstractActieElement {

    private static final List<Character> REDEN_BEEINDIGING_HGP_NEDERLAND = new ArrayList<>();
    private static final List<Character> REDEN_BEEINDIGING_HGP_BUITENLAND = new ArrayList<>();
    private static final List<Character> REDEN_BEEINDIGING_OMZETTING = new ArrayList<>();
    private static final List<Character> REDEN_BEEINDIGING_NIETIGVERKLARING = new ArrayList<>();

    static {

        REDEN_BEEINDIGING_HGP_NEDERLAND.add('A');
        REDEN_BEEINDIGING_HGP_NEDERLAND.add('O');
        REDEN_BEEINDIGING_HGP_NEDERLAND.add('R');
        REDEN_BEEINDIGING_HGP_NEDERLAND.add('S');
        REDEN_BEEINDIGING_HGP_BUITENLAND.addAll(REDEN_BEEINDIGING_HGP_NEDERLAND);
        REDEN_BEEINDIGING_HGP_BUITENLAND.add('?');
        REDEN_BEEINDIGING_HGP_BUITENLAND.add('V');
        REDEN_BEEINDIGING_OMZETTING.add('Z');
        REDEN_BEEINDIGING_NIETIGVERKLARING.add('N');
    }

    @XmlTransient
    private final HuwelijkOfGpElement huwelijkOfGp;

    /**
     * Maakt een AbstractRegistratieHuwelijkOfGpActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param huwelijkOfGp huwelijkOfGp
     */
    public AbstractRegistratieEindeHuwelijkOfGpActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronReferenties,
            final AbstractHuwelijkOfGpElement huwelijkOfGp) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties);
        ValidatieHelper.controleerOpNullWaarde(huwelijkOfGp, "huwelijkOfGp");
        this.huwelijkOfGp = huwelijkOfGp;
    }

    @Override
    protected final List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        controleerOfDatumEindeIsOpOfNaDatumAanvang(meldingen);
        controleerGemeenteAanvang(meldingen);
        valideerGemeenteEindeMetRegisterGemeente(meldingen);
        controleerOfRelatieAlNietBeeindigdIs(meldingen);
        controleerLandGebiedOngelijkNederland(meldingen);
        valideerRedenEinde(meldingen);
        for (final BetrokkenheidElement betrokkenheid : huwelijkOfGp.getBetrokkenheden()) {
            controleerGroepenNietAanwezig(meldingen, betrokkenheid.getPersoon());
        }
        return meldingen;
    }

    @Bedrijfsregel(Regel.R2254)
    private void controleerGroepenNietAanwezig(final List<MeldingElement> meldingen, final PersoonElement persoonElement) {
        if (persoonElement.heeftPersoonEntiteit()
                && !SoortPersoon.PSEUDO_PERSOON.equals(persoonElement.getPersoonEntiteit().getSoortPersoon())
                && !ValidationUtils.zijnParametersAllemaalNull(persoonElement.getSamengesteldeNaam())) {
            meldingen.add(MeldingElement.getInstance(Regel.R2254, persoonElement));
        }
    }

    @Bedrijfsregel(Regel.R1874)
    private void controleerOfRelatieAlNietBeeindigdIs(final List<MeldingElement> meldingen) {
        final Relatie relatieEntiteit = huwelijkOfGp.getRelatieEntiteit();
        final RelatieHistorie
                actueelHistorieVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet());
        if (actueelHistorieVoorkomen == null || actueelHistorieVoorkomen.getDatumEinde() != null) {
            meldingen.add(MeldingElement.getInstance(Regel.R1874, huwelijkOfGp.getRelatieGroep()));
        }
    }

    @Bedrijfsregel(Regel.R1864)
    private void controleerOfDatumEindeIsOpOfNaDatumAanvang(final List<MeldingElement> meldingen) {
        // bij een nietigverklaring wordt datum einde afgeleid van datum aanvang en heeft deze controle dus geen zin
        if (!isNietigVerklaring()) {
            final Relatie relatieEntiteit = huwelijkOfGp.getRelatieEntiteit();
            final DatumElement datumEinde = huwelijkOfGp.getRelatieGroep().getDatumEinde();
            if (relatieEntiteit.getDatumAanvang() != null
                    && !DatumUtil.valtDatumBinnenPeriode(datumEinde.getWaarde(), relatieEntiteit.getDatumAanvang(), null)) {
                meldingen.add(MeldingElement.getInstance(Regel.R1864, huwelijkOfGp.getRelatieGroep()));
            }
        }
    }

    @Bedrijfsregel(Regel.R1863)
    private void controleerGemeenteAanvang(final List<MeldingElement> meldingen) {
        final Relatie relatieEntiteit = huwelijkOfGp.getRelatieEntiteit();
        final Gemeente gemeenteAanvang = relatieEntiteit.getGemeenteAanvang();
        final List<Character> redenUitsluiting = new ArrayList<>(Arrays.asList('O', 'R', 'Z'));
        final RelatieGroepElement relatieGroepElement = huwelijkOfGp.getRelatieGroep();
        final String gemeenteEindeCode = BmrAttribuut.getWaardeOfNull(relatieGroepElement.getGemeenteEindeCode());
        final boolean gemeenteAanvangEnEindeGevuld = gemeenteEindeCode != null && gemeenteAanvang != null;
        if (gemeenteAanvangEnEindeGevuld && !relatieGroepElement.isRedenBeeindigingGeldig(redenUitsluiting) && !gemeenteAanvang.getCode()
                .equals(gemeenteEindeCode)
                && !isVoortzettendeGemeenteAanvangGelijkAanGemeenteEinde(gemeenteAanvang, gemeenteEindeCode)) {
            meldingen.add(MeldingElement.getInstance(Regel.R1863, getHuwelijkOfGp().getRelatieGroep()));
        }
    }

    private static boolean isVoortzettendeGemeenteAanvangGelijkAanGemeenteEinde(final Gemeente gemeenteAanvang, final String gemeenteEindeCode) {
        return gemeenteAanvang.getVoortzettendeGemeente() != null && (gemeenteAanvang.getVoortzettendeGemeente().getCode().equals(gemeenteEindeCode)
                || isVoortzettendeGemeenteAanvangGelijkAanGemeenteEinde(gemeenteAanvang.getVoortzettendeGemeente(), gemeenteEindeCode));
    }

    @Bedrijfsregel(Regel.R2031)
    @Bedrijfsregel(Regel.R2049)
    private void controleerLandGebiedOngelijkNederland(final List<MeldingElement> meldingen) {
        final RelatieGroepElement relatie = huwelijkOfGp.getRelatieGroep();
        if (BmrAttribuut.getWaardeOfNull(relatie.getLandGebiedEindeCode()) != null) {
            if (relatie.isEindeLandOfGebiedOnbekendOfInternationaal()) {
                if (BmrAttribuut.getWaardeOfNull(relatie.getOmschrijvingLocatieEinde()) == null) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2049, getHuwelijkOfGp().getRelatieGroep()));
                }
            } else {
                if (BmrAttribuut.getWaardeOfNull(relatie.getOmschrijvingLocatieEinde()) != null) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2049, getHuwelijkOfGp().getRelatieGroep()));
                }
            }
        }
    }

    @Bedrijfsregel(Regel.R2131)
    private void valideerGemeenteEindeMetRegisterGemeente(final List<MeldingElement> meldingen) {
        final AdministratieveHandelingElementSoort administratieveHandelingSoort = getVerzoekBericht().getAdministratieveHandeling().getSoort();
        final RelatieGroepElement relatieGroep = huwelijkOfGp.getRelatieGroep();
        if (AdministratieveHandelingElementSoort.GBA_OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK.equals(administratieveHandelingSoort)
                || AdministratieveHandelingElementSoort.OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK.equals(administratieveHandelingSoort)) {
            final StringElement gemeenteEindeCode = relatieGroep.getGemeenteEindeCode();
            if (gemeenteEindeCode != null) {
                controleerBronReferentie(meldingen, getDynamischeStamtabelRepository().getGemeenteByGemeentecode(gemeenteEindeCode.getWaarde()));
            }
        }
    }

    private void controleerBronReferentie(final List<MeldingElement> meldingen, final Gemeente gemeenteEinde) {
        for (final BronReferentieElement bronReferentie : getBronReferenties()) {
            if (gemeenteEinde != null && !bronReferentie.getReferentie().getDocument().getPartijCode().getWaarde()
                    .equals(gemeenteEinde.getPartij().getCode())) {
                meldingen.add(MeldingElement.getInstance(Regel.R2131, bronReferentie));
            }
        }
    }

    @Bedrijfsregel(Regel.R1876)
    private void valideerRedenEinde(final List<MeldingElement> meldingen) {
        final RelatieGroepElement relatieGroep = huwelijkOfGp.getRelatieGroep();
        if (relatieGroep.getRedenEindeCode() != null
                && !relatieGroep.isRedenBeeindigingGeldig(geefVanSoortAdministratieveHandelingDeMogelijkeRedenBeeindiging())) {
            meldingen.add(MeldingElement.getInstance(Regel.R1876, relatieGroep));
        }
    }

    private List<Character> geefVanSoortAdministratieveHandelingDeMogelijkeRedenBeeindiging() {
        final List<Character> results;
        if (isEindeNederland()) {
            results = REDEN_BEEINDIGING_HGP_NEDERLAND;
        } else if (isEindeBuitenland()) {
            results = REDEN_BEEINDIGING_HGP_BUITENLAND;
        } else if (isOmzetting()) {
            results = REDEN_BEEINDIGING_OMZETTING;
        } else if (isNietigVerklaring()) {
            results = REDEN_BEEINDIGING_NIETIGVERKLARING;
        } else {
            results = Collections.emptyList();
        }
        return results;
    }

    @Override
    public final BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BRPActie actie;
        if (zijnAlleHoofdPersonenVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            // einde relatie
            final BijhoudingRelatie relatieEntiteit = huwelijkOfGp.getRelatieEntiteit();
            relatieEntiteit.voegVoorkomenMetEindeRelatieToe(huwelijkOfGp, actie, getPeilDatum());
            // wijziging geslachtsnaam pseudo persoon
            huwelijkOfGp.werkPseudoPersoonInRelatieEntiteitBij(actie, bericht, getDatumAanvangGeldigheid());
            // igv omzetting: maak nieuw huwelijk op basis van gp
            if (isOmzetting()) {
                maakNieuwHuwelijkOpBasisVanGp(relatieEntiteit, actie);
            }
        } else {
            actie = null;
        }
        return actie;
    }

    private void maakNieuwHuwelijkOpBasisVanGp(final BijhoudingRelatie oudeRelatie, final BRPActie actie) {
        final RelatieHistorie relatieHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(oudeRelatie.getRelatieHistorieSet());

        final DatumElement datumAanvangElement = relatieHistorie.getDatumEinde() == null ? null : new DatumElement(relatieHistorie.getDatumEinde());
        final StringElement
                gemeenteAanvangCodeElement =
                relatieHistorie.getGemeenteEinde() == null ? null : new StringElement(String.valueOf(relatieHistorie.getGemeenteEinde().getCode()));
        final StringElement
                woonplaatsnaamAanvangElement =
                relatieHistorie.getWoonplaatsnaamEinde() == null ? null : new StringElement(relatieHistorie.getWoonplaatsnaamEinde());
        final StringElement
                landGebiedAanvangCodeElement =
                relatieHistorie.getLandOfGebiedEinde() == null ? null : new StringElement(String.valueOf(relatieHistorie.getLandOfGebiedEinde().getCode()));

        final RelatieGroepElement
                relatieGroep =
                new RelatieGroepElement(new AttributenBuilder().build(), datumAanvangElement,
                        gemeenteAanvangCodeElement,
                        woonplaatsnaamAanvangElement,
                        null, null, null, null, null, null, null, landGebiedAanvangCodeElement, null,
                        null,
                        null, null);
        final List<BetrokkenheidElement> betrokkenheden = new ArrayList<>();
        for (final BijhoudingPersoon persoonEntiteit : oudeRelatie.getPersonen(getVerzoekBericht())) {
            final BetrokkenheidElement
                    betrokkenheidElement =
                    BetrokkenheidElement
                            .getInstance(BetrokkenheidElementSoort.PARTNER, PersoonGegevensElement.getInstance(persoonEntiteit, getVerzoekBericht()), null);
            betrokkenheidElement.setVerzoekBericht(getVerzoekBericht());
            betrokkenheden.add(betrokkenheidElement);
        }
        final HuwelijkElement nieuwHuwelijkElement = new HuwelijkElement(new AttributenBuilder().objecttype("Relatie").build(), relatieGroep, betrokkenheden);
        nieuwHuwelijkElement.setVerzoekBericht(getVerzoekBericht());

        nieuwHuwelijkElement.maakRelatieEntiteitEnBetrokkenen(actie, getDatumAanvangGeldigheid().getWaarde());
    }

    @Override
    public final List<BijhoudingPersoon> getHoofdPersonen() {
        return huwelijkOfGp.getRelatieEntiteit().getHoofdPersonen(getVerzoekBericht());
    }

    @Override
    public final List<PersoonElement> getPersoonElementen() {
        return huwelijkOfGp.getPersoonElementen();
    }

    @Override
    public final DatumElement getPeilDatum() {
        if (isNietigVerklaring()) {
            final RelatieHistorie
                    actueleRelatieHistorie =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(huwelijkOfGp.getRelatieEntiteit().getRelatieHistorieSet());
            return new DatumElement(actueleRelatieHistorie.getDatumAanvang());
        } else {
            return huwelijkOfGp.getRelatieGroep().getDatumEinde();
        }
    }

    /**
     * Geeft de waarde van huwelijkOfGp.
     * @return huwelijkOfGp
     */
    public final HuwelijkOfGpElement getHuwelijkOfGp() {
        return huwelijkOfGp;
    }

    private boolean isNietigVerklaring() {
        switch (getVerzoekBericht().getAdministratieveHandeling().getSoort()) {
            case NIETIGVERKLARING_HUWELIJK_IN_NEDERLAND:
            case NIETIGVERKLARING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND:
                return true;
            default:
                return false;
        }
    }

    private boolean isOmzetting() {
        switch (getVerzoekBericht().getAdministratieveHandeling().getSoort()) {
            case OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK:
            case GBA_OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK:
                return true;
            default:
                return false;
        }
    }

    private boolean isEindeBuitenland() {
        switch (getVerzoekBericht().getAdministratieveHandeling().getSoort()) {
            case BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND:
            case ONTBINDING_HUWELIJK_IN_BUITENLAND:
                return true;
            default:
                return false;
        }
    }

    private boolean isEindeNederland() {
        switch (getVerzoekBericht().getAdministratieveHandeling().getSoort()) {
            case BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND:
            case GBA_BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND:
            case ONTBINDING_HUWELIJK_IN_NEDERLAND:
            case GBA_ONTBINDING_HUWELIJK_IN_NEDERLAND:
                return true;
            default:
                return false;
        }
    }
}
