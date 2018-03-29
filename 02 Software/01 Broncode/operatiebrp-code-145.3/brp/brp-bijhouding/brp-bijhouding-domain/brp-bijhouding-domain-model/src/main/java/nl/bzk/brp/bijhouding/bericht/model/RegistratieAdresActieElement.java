/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperking;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperkingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * De actie voor het registreren van een adres.
 */
//
@XmlElement("registratieAdres")
public final class RegistratieAdresActieElement extends AbstractPersoonWijzigingActieElement {

    private static final int LEEFTIJD_16 = 16;

    /**
     * Maakt een RegistratieAdresActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronnen bron referenties
     * @param persoon de persoon
     */
    public RegistratieAdresActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronnen,
            final PersoonGegevensElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronnen, persoon);
        ValidatieHelper.controleerOpNullWaarde(persoon, "persoon");
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        controleerAanvangGeldigheidTegenBijhouding(meldingen);
        valideerGemeenteCode(meldingen);
        valideerLeeftijdEnCuratele(meldingen);
        valideerRelatieHgp(meldingen);
        valideerDatumAanvangGeldigheidGelijkAanDatumAanvangAdreshouding(meldingen);
        valideerDatumAanvangAdreshoudingMetActueelAdres(meldingen);
        valideerActueleOuderBetrokkenheid(meldingen);
        valideerActueleKindBetrokkenheid(meldingen);
        valideerPartijCodeAHMetAdresGemeente(meldingen);
        valideerDatumAanvangGeldigheidMetActueelAdres(meldingen);
        valideerVerstrekkingsBeperking(meldingen);
        return meldingen;
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_ADRES;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BijhoudingPersoon persoonEntiteit = getPersoon().getPersoonEntiteit();
        if (persoonEntiteit.isVerwerkbaar()) {
            final PersoonAdresHistorie
                    huidigAdresHistorie =
                    FormeleHistorieZonderVerantwoording
                            .getActueelHistorieVoorkomen(persoonEntiteit.getPersoonAdresSet().iterator().next().getPersoonAdresHistorieSet());
            final BRPActie actie = maakActieEntiteit(administratieveHandeling);
            final AdresElement nieuwAdres = getPersoon().getAdres();
            persoonEntiteit.wijzigPersoonAdresEntiteit(nieuwAdres, actie, getDatumAanvangGeldigheid().getWaarde());
            if (AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK.equals(getVerzoekBericht().getAdministratieveHandeling().getSoort())) {

                voegBijhoudingsHistorieToe(nieuwAdres.getGemeenteCode().getWaarde(), persoonEntiteit, actie,
                        nieuwAdres.getDatumAanvangAdreshouding().getWaarde());
            } else if (AdministratieveHandelingElementSoort.WIJZIGING_GEMEENTE_INFRASTRUCTUREEL.equals(getVerzoekBericht()
                    .getAdministratieveHandeling().getSoort())) {
                voegBijhoudingsHistorieToe(nieuwAdres.getGemeenteCode().getWaarde(), persoonEntiteit, actie, huidigAdresHistorie.getDatumAanvangAdreshouding());

            }
            return actie;
        } else {
            return null;
        }
    }

    private void voegBijhoudingsHistorieToe(final String gemeenteCode, final BijhoudingPersoon persoonEntiteit, final BRPActie actie,
                                            final Integer datumAanvangAdreshouding) {
        final Gemeente
                nieuweGemeente =
                getDynamischeStamtabelRepository().getGemeenteByGemeentecode(gemeenteCode);
        persoonEntiteit.voegPersoonBijhoudingHistorieToe(nieuweGemeente, datumAanvangAdreshouding, actie);
    }

    @Override
    public DatumElement getPeilDatum() {
        if (AdministratieveHandelingElementSoort.WIJZIGING_ADRES_INFRASTRUCTUREEL.equals(getVerzoekBericht().getAdministratieveHandeling().getSoort())
                || AdministratieveHandelingElementSoort.WIJZIGING_GEMEENTE_INFRASTRUCTUREEL.equals(getVerzoekBericht().getAdministratieveHandeling()
                .getSoort())) {
            return getDatumAanvangGeldigheid();
        }
        return getAdres().getDatumAanvangAdreshouding();
    }

    /**
     * Geef het adres dat hoort bij deze actie.
     * @return het adres element van deze actie
     */
    public AdresElement getAdres() {
        return getPersoon().getAdres();
    }

    /**
     * Valideert of gemeneentecode in het bericht ongelijk is aan gemeentecode behorend bij de entiteit.
     * @param meldingen Lijst van meldingen waar R1384 evt aan toegevoegd wordt.
     */
    @Bedrijfsregel(Regel.R1384)
    private void valideerGemeenteCode(final List<MeldingElement> meldingen) {
        final BijhoudingPersoon bijhoudingPersoon = getPersoon().getPersoonEntiteit();
        if (getPersoon().getAdres() != null && AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK
                .equals(getVerzoekBericht().getAdministratieveHandeling().getSoort())) {

            final String gemeenteCodeElement = getPersoon().getAdres().getGemeenteCode().getWaarde();
            final PersoonAdresHistorie actueelAdres = bijhoudingPersoon.getActuelePersoonAdresHistorie();
            if (actueelAdres != null && actueelAdres.getGemeente() != null
                    && actueelAdres.getGemeente().getCode().equals(gemeenteCodeElement)) {
                meldingen.add(MeldingElement.getInstance(Regel.R1384, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1928)
    private void valideerRelatieHgp(final List<MeldingElement> meldingen) {
        final BijhoudingPersoon persoonEntiteit = getPersoon().getPersoonEntiteit();
        final Aangever aangever = getAangever();
        if (persoonEntiteit != null && aangever != null && Aangever.PARTNER == aangever.getCode()) {
            final List<Relatie> relaties = persoonEntiteit.getActueleHgpRelaties(DatumUtil.vandaag());
            if (relaties.isEmpty()) {
                meldingen.add(MeldingElement.getInstance(Regel.R1928, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1929)
    private void valideerActueleOuderBetrokkenheid(final List<MeldingElement> meldingen) {
        final BijhoudingPersoon verhuizendPersoon = getPersoon().getPersoonEntiteit();
        final Aangever aangever = getAangever();
        if (verhuizendPersoon != null && aangever != null && Aangever.MEERDERJARIG_INWONEND_KIND == aangever.getCode()) {
            final Set<Betrokkenheid> actueleKindBetrokkenheden = verhuizendPersoon.getActueleKinderen();
            if (!hasMeerderjarigeKinderen(actueleKindBetrokkenheden)) {
                meldingen.add(MeldingElement.getInstance(Regel.R1929, this));
            }
        }
    }

    private boolean hasMeerderjarigeKinderen(final Set<Betrokkenheid> actueleKindBetrokkenheden) {
        boolean hasMeerderjarigeKinderen = false;
        for (final Betrokkenheid kindBetrokkenheid : actueleKindBetrokkenheden) {
            if (!isPersoonMinderJarig(kindBetrokkenheid.getPersoon())) {
                hasMeerderjarigeKinderen = true;
            }
        }
        return hasMeerderjarigeKinderen;
    }

    @Bedrijfsregel(Regel.R1930)
    private void valideerActueleKindBetrokkenheid(final List<MeldingElement> meldingen) {
        final BijhoudingPersoon verhuizendPersoon = getPersoon().getPersoonEntiteit();
        final Aangever aangever = getAangever();
        if (verhuizendPersoon != null && aangever != null && Aangever.OUDER == aangever.getCode()) {
            boolean isMinderjarig = isPersoonMinderJarig(verhuizendPersoon);
            if (isMinderjarig || !hasActueelOuderschap(verhuizendPersoon)) {
                meldingen.add(MeldingElement.getInstance(Regel.R1930, this));
            }

        }
    }


    private Aangever getAangever() {
        if (getPersoon().getAdres() != null && getPersoon().getAdres().getAangeverAdreshoudingCode() != null) {
            return getDynamischeStamtabelRepository().getAangeverByCode(BmrAttribuut.getWaardeOfNull(getPersoon().getAdres().getAangeverAdreshoudingCode()));
        } else {
            return null;
        }
    }

    @Bedrijfsregel(Regel.R1927)
    @Bedrijfsregel(Regel.R1931)
    private void valideerLeeftijdEnCuratele(final List<MeldingElement> meldingen) {
        final BijhoudingPersoon bijhoudingPersoon = getPersoon().getPersoonEntiteit();
        final Aangever aangever = getAangever();
        if (bijhoudingPersoon != null && aangever != null) {
            if (Aangever.INGESCHREVENE == aangever.getCode()) {
                int leeftijd = bijhoudingPersoon.bepaalLeeftijd(getVerzoekBericht().getDatumOntvangst().getWaarde());
                if (leeftijd < LEEFTIJD_16 || hasCurateleIndicatie(bijhoudingPersoon)) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1927, getPersoon().getAdres()));
                }
            } else if (Aangever.GEZAGHOUDER == aangever.getCode() && !isPersoonMinderJarig(bijhoudingPersoon) && !hasCurateleIndicatie(bijhoudingPersoon)) {
                meldingen.add(MeldingElement.getInstance(Regel.R1931, getPersoon().getAdres()));
            }
        }
    }

    @Bedrijfsregel(Regel.R2344)
    private void valideerVerstrekkingsBeperking(final List<MeldingElement> meldingen) {
        final BijhoudingPersoon bijhoudingPersoon = getPersoon().getPersoonEntiteit();
        final Gemeente nieuweGemeente = getAdres().getDynamischeStamtabelRepository().
                getGemeenteByGemeentecode(BmrAttribuut.getWaardeOfNull(getAdres().getGemeenteCode()));
        if (!bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet().isEmpty()) {
            for (PersoonVerstrekkingsbeperking verstrekkingsbeperking : bijhoudingPersoon.getPersoonVerstrekkingsbeperkingSet()) {
                PersoonVerstrekkingsbeperkingHistorie verstrekkingsbeperkingHistorie = FormeleHistorieZonderVerantwoording.
                        getActueelHistorieVoorkomen(verstrekkingsbeperking.getPersoonVerstrekkingsbeperkingHistorieSet());
                if (verstrekkingsbeperkingHistorie.getPersoonVerstrekkingsbeperking().getGemeenteVerordening() != null &&
                        !verstrekkingsbeperkingHistorie.getPersoonVerstrekkingsbeperking().getGemeenteVerordening().getCode()
                                .equals(nieuweGemeente.getPartij().getCode())) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2344, this));
                }
            }
        }

    }

    private boolean hasCurateleIndicatie(BijhoudingPersoon persoonEntiteit) {
        boolean result = false;
        for (final PersoonIndicatie indicatie : persoonEntiteit.getPersoonIndicatieSet()) {
            if (SoortIndicatie.ONDER_CURATELE.equals(indicatie.getSoortIndicatie())) {
                result = true;
            }
        }
        return result;
    }

    @Bedrijfsregel(Regel.R2500)
    private void valideerDatumAanvangGeldigheidGelijkAanDatumAanvangAdreshouding(final List<MeldingElement> meldingen) {
        if (!AdministratieveHandelingElementSoort.WIJZIGING_ADRES_INFRASTRUCTUREEL.equals(getVerzoekBericht().getAdministratieveHandeling().getSoort()) &&
                !AdministratieveHandelingElementSoort.WIJZIGING_GEMEENTE_INFRASTRUCTUREEL.equals(getVerzoekBericht().getAdministratieveHandeling().getSoort())
                && !Objects.equals(getDatumAanvangGeldigheid(), getPersoon().getAdres().getDatumAanvangAdreshouding())) {
            meldingen.add(MeldingElement.getInstance(Regel.R2500, this));
        }
    }

    @Bedrijfsregel(Regel.R2325)
    private void valideerPartijCodeAHMetAdresGemeente(final List<MeldingElement> meldingen) {
        if (getPersoon().getAdres() != null && AdministratieveHandelingElementSoort.VERHUIZING_INTERGEMEENTELIJK
                .equals(getVerzoekBericht().getAdministratieveHandeling().getSoort())) {
            final String partijCodeAH = getVerzoekBericht().getAdministratieveHandeling().getPartijCode().getWaarde();
            final Gemeente gemeente = getDynamischeStamtabelRepository().getGemeenteByGemeentecode(getAdres().getGemeenteCode().getWaarde());
            if (gemeente != null && !gemeente.getPartij().getCode().equals(partijCodeAH)) {
                meldingen.add(MeldingElement.getInstance(Regel.R2325, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R2322)
    private void valideerDatumAanvangAdreshoudingMetActueelAdres(final List<MeldingElement> meldingen) {
        final AdresElement adres = getPersoon().getAdres();
        final PersoonAdresHistorie actueelHistorieVoorkomen = getPersoon().getPersoonEntiteit().getActuelePersoonAdresHistorie();

        if (adres != null && actueelHistorieVoorkomen != null) {
            final DatumElement datumAanvangAdreshouding = adres.getDatumAanvangAdreshouding();
            final boolean geldigeDatums = DatumUtil.isGeldigeKalenderdatum(actueelHistorieVoorkomen.getDatumAanvangAdreshouding()) &&
                    datumAanvangAdreshouding != null && datumAanvangAdreshouding.isGeldigeKalenderDatum();

            if (geldigeDatums && actueelHistorieVoorkomen.getDatumAanvangAdreshouding() > datumAanvangAdreshouding.getWaarde()) {
                meldingen.add(MeldingElement.getInstance(Regel.R2322, this));
            }

        }

    }


    @Bedrijfsregel(Regel.R2358)
    private void valideerDatumAanvangGeldigheidMetActueelAdres(final List<MeldingElement> meldingen) {
        final AdresElement adres = getPersoon().getAdres();
        final PersoonAdresHistorie actueelHistorieVoorkomen = getPersoon().getPersoonEntiteit().getActuelePersoonAdresHistorie();
        if (adres != null && actueelHistorieVoorkomen != null) {
            final DatumElement datumAanvangGeldigheid = getDatumAanvangGeldigheid();
            boolean geldigeDatums = DatumUtil.isGeldigeKalenderdatum(actueelHistorieVoorkomen.getDatumAanvangGeldigheid()) &&
                    datumAanvangGeldigheid != null && datumAanvangGeldigheid.isGeldigeKalenderDatum();

            if (geldigeDatums && actueelHistorieVoorkomen.getDatumAanvangGeldigheid() >= datumAanvangGeldigheid.getWaarde()) {
                meldingen.add(MeldingElement.getInstance(Regel.R2358, this));
            }
        }

    }

}
