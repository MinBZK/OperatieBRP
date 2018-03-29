/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * De actie voor het registreren van een nationaliteit.
 */
//
@XmlElement("registratieNationaliteit")
public class RegistratieNationaliteitActieElement extends AbstractPersoonWijzigingActieElement {

    /**
     * Maakt een {@link RegistratieNationaliteitActieElement} object.
     * omdat we meerdere registratieNationaliteiten in een handeling kunnen hebben
     * voegen we de datumAanvangGeldigheid toe aan de bewuste nationaliteit.
     * Indien hier naar gerefereerd word hoeven we niet opnieuw de actie erbij te gaan zoeken
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param persoon de persoon
     */
    public RegistratieNationaliteitActieElement(final Map<String, String> basisAttribuutGroep,
                                                final DatumElement datumAanvangGeldigheid,
                                                final DatumElement datumEindeGeldigheid,
                                                final List<BronReferentieElement> bronReferenties,
                                                final PersoonGegevensElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, persoon);
        getPersoon().getNationaliteit().setDatumAanvangGeldigheidRegistratieNationaliteitActie(getDatumAanvangGeldigheid().getWaarde());
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_NATIONALITEIT;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BRPActie actie;
        final PersoonElement persoonElement = getPersoon();
        final BijhoudingPersoon persoon = persoonElement.getPersoonEntiteit();
        if (persoon.isVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            persoon.voegPersoonNationaliteitToe(persoonElement.getNationaliteit(), actie, getDatumAanvangGeldigheid().getWaarde());
        } else {
            actie = null;
        }
        return actie;
    }

    @Override
    public DatumElement getPeilDatum() {
        return getDatumAanvangGeldigheid();
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new LinkedList<>();
        final AdministratieveHandelingElement administratieveHandeling = getVerzoekBericht().getAdministratieveHandeling();
        final RegistratieGeboreneActieElement registratieGeborene =
                (RegistratieGeboreneActieElement) administratieveHandeling.getActieBySoort(SoortActie.REGISTRATIE_GEBORENE);
        final RegistratieOuderActieElement registratieOuder =
                (RegistratieOuderActieElement) administratieveHandeling.getActieBySoort(SoortActie.REGISTRATIE_OUDER);
        controleerDatumAanvangGeldigheid(meldingen, registratieGeborene, registratieOuder);
        controleerNationaliteitenPersoon(meldingen);
        controleerNationaliteitCode(meldingen);
        controleerRedenVerkrijgingGeldig(meldingen);
        controleerTeVerkrijgenNationaliteitBijOuders(meldingen, registratieGeborene, registratieOuder);
        controleerTegenStaatloos(meldingen);
        controleerNederlandseNationaliteitMetKinderen(meldingen);
        return meldingen;
    }

    @Bedrijfsregel(Regel.R2643)
    @Bedrijfsregel(Regel.R2644)
    private void controleerTegenStaatloos(final List<MeldingElement> meldingen) {
        if (getPersoon().heeftPersoonEntiteit()) {
            final PersoonIndicatieHistorie meestRecenteHistorie = getPersoon().getPersoonEntiteit().getMeestRecenteIndicatie(SoortIndicatie.STAATLOOS);
            if (meestRecenteHistorie != null) {
                if (meestRecenteHistorie.getDatumEindeGeldigheid() == null && getPeilDatum().getWaarde() <= meestRecenteHistorie.getDatumAanvangGeldigheid()) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2643, this));
                }
                if (meestRecenteHistorie.getDatumEindeGeldigheid() != null && getPeilDatum().getWaarde() < meestRecenteHistorie.getDatumEindeGeldigheid()) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2644, this));
                }
            }
        }
    }

    @Bedrijfsregel(Regel.R1695)
    private void controleerTeVerkrijgenNationaliteitBijOuders(final List<MeldingElement> meldingen,
                                                              final RegistratieGeboreneActieElement registratieGeborene,
                                                              final RegistratieOuderActieElement registratieOuder) {
        if (getBronReferenties().isEmpty()) {
            final List<PersoonElement> ouders = verzamelOuders(registratieGeborene, registratieOuder);
            boolean nationaliteitKomtVoorBijOuder = false;
            for (final PersoonElement ouder : ouders) {
                nationaliteitKomtVoorBijOuder = nationaliteitKomtVoorBijOuder || (ouder.heeftPersoonEntiteit() &&
                        ouder.getPersoonEntiteit().heeftNationaliteit(BmrAttribuut.getWaardeOfNull(getNationaliteitElement().getNationaliteitCode()),
                                getDatumAanvangGeldigheid().getWaarde()));
            }
            if (!ouders.isEmpty() && !nationaliteitKomtVoorBijOuder) {
                meldingen.add(MeldingElement.getInstance(Regel.R1695, this));
            }
        }
    }

    private List<PersoonElement> verzamelOuders(final RegistratieGeboreneActieElement registratieGeborene,
                                                final RegistratieOuderActieElement registratieOuder) {
        List<PersoonElement> ouders = new LinkedList<>();
        if (registratieGeborene != null && Objects.equals(getPeilDatum().getWaarde(), registratieGeborene.getPeilDatum().getWaarde())) {
            ouders = initArrayList(registratieGeborene.getOuders());
        }
        if (registratieOuder != null && Objects.equals(getPeilDatum().getWaarde(), registratieOuder.getPeilDatum().getWaarde())) {
            ouders = initArrayList(registratieOuder.getOuders());
            if (registratieGeborene != null) {
                ouders.addAll(initArrayList(registratieGeborene.getOuders()));
            }
        }
        return ouders;
    }

    @Bedrijfsregel(Regel.R2488)
    private void controleerDatumAanvangGeldigheid(final List<MeldingElement> meldingen, final RegistratieGeboreneActieElement registratieGeborene,
                                                  final RegistratieOuderActieElement registratieOuder) {
        final AdministratieveHandelingElement administratieveHandeling = getVerzoekBericht().getAdministratieveHandeling();
        if (administratieveHandeling.isErkenningNaGeboorteHandeling()) {
            final boolean isGelijkAanGeboorte = registratieGeborene == null
                    || Objects.equals(registratieGeborene.getDatumAanvangGeldigheid().getWaarde(), getDatumAanvangGeldigheid().getWaarde());
            final boolean isGelijkAanErkenning = registratieOuder == null
                    || Objects.equals(registratieOuder.getDatumAanvangGeldigheid().getWaarde(), getDatumAanvangGeldigheid().getWaarde());

            if (!isGelijkAanErkenning && !isGelijkAanGeboorte) {
                meldingen.add(MeldingElement.getInstance(Regel.R2488, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1692)
    @Bedrijfsregel(Regel.R2011)
    private void controleerNationaliteitenPersoon(final List<MeldingElement> meldingen) {
        final BijhoudingPersoon persoonEntiteit = getPersoon().getPersoonEntiteit();
        if (persoonEntiteit.heeftNationaliteitAl(getNationaliteitElement(), getDatumAanvangGeldigheid().getWaarde())) {
            meldingen.add(MeldingElement.getInstance(Regel.R1692, this));
        }
        if (!Objects.equals(Nationaliteit.NEDERLANDSE, getNationaliteitElement().getNationaliteitCode().getWaarde())
                && persoonEntiteit.heeftNederlandseNationaliteitOfIndicatieBehandeldAlsNederlander(getDatumAanvangGeldigheid().getWaarde())) {
            meldingen.add(MeldingElement.getInstance(Regel.R2011, this));
        }
    }

    @Bedrijfsregel(Regel.R1707)
    private void controleerNationaliteitCode(final List<MeldingElement> meldingen) {
        final Nationaliteit code =
                getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode(getNationaliteitElement().getNationaliteitCode().getWaarde());
        if (code != null && !DatumUtil.valtDatumBinnenPeriode(getPeilDatum().getWaarde(), code.getDatumAanvangGeldigheid(), code.getDatumEindeGeldigheid())) {
            meldingen.add(MeldingElement.getInstance(Regel.R1707, this));
        }
    }

    @Bedrijfsregel(Regel.R1708)
    @Bedrijfsregel(Regel.R2544)
    private void controleerRedenVerkrijgingGeldig(final List<MeldingElement> meldingen) {
        final String redenVerkrijgingsCode = BmrAttribuut.getWaardeOfNull(getNationaliteitElement().getRedenVerkrijgingCode());
        if (redenVerkrijgingsCode != null) {
            final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit =
                    getDynamischeStamtabelRepository().getRedenVerkrijgingNLNationaliteitByCode(redenVerkrijgingsCode);
            if (redenVerkrijgingNLNationaliteit != null) {
                if (!Nationaliteit.NEDERLANDSE.equals(getNationaliteitElement().getNationaliteitCode().getWaarde())) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2544, this));
                } else if (!DatumUtil.valtDatumBinnenPeriode(getPeilDatum().getWaarde(),
                        redenVerkrijgingNLNationaliteit.getDatumAanvangGeldigheid(), redenVerkrijgingNLNationaliteit.getDatumEindeGeldigheid())) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1708, this));
                }
            }
        }
    }

    @Bedrijfsregel(Regel.R2582)
    private void controleerNederlandseNationaliteitMetKinderen(final List<MeldingElement> meldingen) {
        final BijhoudingPersoon bijhoudingPersoon = getPersoon().getPersoonEntiteit();
        if (!bijhoudingPersoon.isEersteInschrijving() && getNationaliteitElement().isNederlandse() &&
                !bijhoudingPersoon.getActueleKinderen().isEmpty()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2582, this));
        }
    }

    private NationaliteitElement getNationaliteitElement() {
        return getPersoon().getNationaliteit();
    }

    /**
     * Geeft aan of de nationaliteit de Nederlandse is.
     * @return true als de nationaliteit de Nederlandse is
     */
    boolean isNederlandseNationaliteit() {
        return getPersoon().getNationaliteit().isNederlandse();
    }
}
