/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * De actie voor het registreren van een nationaliteit.
 */
@XmlElement("beeindigingNationaliteit")
public class BeeindigingNationaliteitActieElement extends AbstractBeeindigingActieElement {

    /**
     * Maakt een {@link BeeindigingNationaliteitActieElement}.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param bronReferenties bron referenties
     * @param persoon de persoon
     * @param datumEindeGeldigheid datum einde geldigheid
     */
    public BeeindigingNationaliteitActieElement(final Map<String, String> basisAttribuutGroep,
                                                final DatumElement datumAanvangGeldigheid,
                                                final DatumElement datumEindeGeldigheid,
                                                final List<BronReferentieElement> bronReferenties,
                                                final PersoonGegevensElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, persoon);
        getPersoon().getNationaliteit().setDatumEindeGeldigheidRegistratieNationaliteitActie(datumEindeGeldigheid.getWaarde());
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.BEEINDIGING_NATIONALITEIT;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BRPActie actie;
        final PersoonElement persoonElement = getPersoon();
        final BijhoudingPersoon persoonEntiteit = persoonElement.getPersoonEntiteit();
        if (persoonEntiteit.isVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            persoonEntiteit.beeindigNationaliteit(persoonElement.getNationaliteit(), actie,
                    persoonElement.getNationaliteit().getRedenVerliesCode(), getDatumEindeGeldigheid());
        } else {
            actie = null;
        }
        return actie;
    }

    @Override
    public DatumElement getPeilDatum() {
        return getDatumEindeGeldigheid();
    }

    @Override
    protected List<MeldingElement> valideerActieInhoud() {
        final List<MeldingElement> meldingen = new LinkedList<>();
        controleerRedenVerliesBijNationaliteit(meldingen);
        controleerDatumEindeGeldigheidGelijkAanDatumErkenning(meldingen);
        controleerDatumAanvangNationaliteitGelijkAanGeboorteDatum(meldingen);
        controleerRedenVerliesCode(meldingen);
        controleerAanwezigheidVanTeVerliezenNationaliteit(meldingen);
        controleerWordtNationaliteitMeerdereKerenBeeindigd(meldingen);
        return meldingen;
    }

    @Bedrijfsregel(Regel.R2799)
    private void controleerWordtNationaliteitMeerdereKerenBeeindigd(final List<MeldingElement> meldingen) {
        if (getPersoon().getPersoonEntiteit().wordtNationaliteitMeerDanEensBeeindigd(getPersoonElement().getNationaliteit())) {
            meldingen.add(MeldingElement.getInstance(Regel.R2799, this));
        }
    }

    @Bedrijfsregel(Regel.R1845)
    private void controleerAanwezigheidVanTeVerliezenNationaliteit(final List<MeldingElement> meldingen) {
        if (!getPersoon().getPersoonEntiteit().isEersteInschrijving()
                && getPersoon().getNationaliteit().heeftObjectSleutel()
                && getPersoon().getNationaliteit().getEntiteit() == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R1845, this));
        }
    }

    @Bedrijfsregel(Regel.R2522)
    private void controleerDatumAanvangNationaliteitGelijkAanGeboorteDatum(final List<MeldingElement> meldingen) {
        final RegistratieGeboreneActieElement registratieGeborene =
                (RegistratieGeboreneActieElement) getVerzoekBericht().getAdministratieveHandeling().getActieBySoort(SoortActie.REGISTRATIE_GEBORENE);
        if (registratieGeborene != null) {
            final DatumElement geboorteDatum =
                    registratieGeborene.getFamilierechtelijkeBetrekking().getBetrokkenheidElementen(BetrokkenheidElementSoort.KIND).get(0).getPersoon()
                            .getGeboorte().getDatum();
            if (!Objects.equals(geboorteDatum.getWaarde(), getPersoon().getNationaliteit().getDatumAanvangGeldigheidRegistratieNationaliteitActie())) {
                meldingen.add(MeldingElement.getInstance(Regel.R2522, this));
            }
        }

    }

    @Bedrijfsregel(Regel.R2451)
    private void controleerDatumEindeGeldigheidGelijkAanDatumErkenning(final List<MeldingElement> meldingen) {
        final ActieElement registratieOuder = getVerzoekBericht().getAdministratieveHandeling().getActieBySoort(SoortActie.REGISTRATIE_OUDER);
        if (registratieOuder != null && !Objects.equals(getDatumEindeGeldigheid().getWaarde(), registratieOuder.getDatumAanvangGeldigheid().getWaarde())) {
            meldingen.add(MeldingElement.getInstance(Regel.R2451, this));
        }
    }

    @Bedrijfsregel(Regel.R1689)
    @Bedrijfsregel(Regel.R2545)
    private void controleerRedenVerliesBijNationaliteit(final List<MeldingElement> meldingen) {
        final StringElement redenVerliesCode = getPersoon().getNationaliteit().getRedenVerliesCode();
        if (isNederlandseNationaliteit()) {
            if (redenVerliesCode == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R1689, getPersoon().getNationaliteit()));
            }
        } else if (redenVerliesCode != null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2545, getPersoon().getNationaliteit()));
        }
    }

    /**
     * Geeft aan of de nationaliteit de Nederlandse is.
     * @return true als de nationaliteit de Nederlandse is
     */
    boolean isNederlandseNationaliteit() {
        return getPersoon().getNationaliteit().isNederlandse();
    }

    @Bedrijfsregel(Regel.R1706)
    private void controleerRedenVerliesCode(final List<MeldingElement> meldingen) {
        final NationaliteitElement nationaliteitElement = getPersoon().getNationaliteit();
        if (nationaliteitElement != null && nationaliteitElement.getRedenVerliesCode() != null) {
            final RedenVerliesNLNationaliteit redenVerliesNLNationaliteit = getDynamischeStamtabelRepository().
                    getRedenVerliesNLNationaliteitByCode(nationaliteitElement.getRedenVerliesCode().getWaarde());
            if (redenVerliesNLNationaliteit != null && !DatumUtil.valtDatumBinnenPeriode(getPeilDatum().getWaarde(),
                    redenVerliesNLNationaliteit.getDatumAanvangGeldigheid(), redenVerliesNLNationaliteit.getDatumEindeGeldigheid())) {
                meldingen.add(MeldingElement.getInstance(Regel.R1706, nationaliteitElement));
            }
        }
    }

    @Override
    protected Integer getDatumAanvangGeldigheiVanTeBeeindingenRij() {
        final Integer datumAanvangGeldigheid;

        final NationaliteitElement nationaliteitElement = getPersoon().getNationaliteit();
        final BijhoudingPersoonNationaliteit nationaliteit = getPersoon().getNationaliteit().getEntiteit();
        if (nationaliteit == null) {
            return nationaliteitElement.getDatumAanvangGeldigheidRegistratieNationaliteitActie();
        } else {
            final PersoonNationaliteitHistorie actueelHistorieVoorkomen =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nationaliteit.getPersoonNationaliteitHistorieSet());
            datumAanvangGeldigheid = actueelHistorieVoorkomen == null ? null : actueelHistorieVoorkomen.getDatumAanvangGeldigheid();
        }
        return datumAanvangGeldigheid;
    }

    @Override
    Set getTeBeeindigenHistorie() {
        if (getPersoon().getNationaliteit().getEntiteit() != null) {
            return getPersoon().getNationaliteit().getEntiteit().getPersoonNationaliteitHistorieSet();
        }
        return Collections.emptySet();
    }
}
