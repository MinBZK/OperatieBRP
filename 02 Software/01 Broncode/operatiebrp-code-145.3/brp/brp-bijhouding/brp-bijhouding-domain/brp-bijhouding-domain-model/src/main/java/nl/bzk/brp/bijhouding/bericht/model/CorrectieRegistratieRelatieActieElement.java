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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlTransient;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * De actie voor het registreren van een nieuw relatie voorkomen (als onderdeel van een correctie) in de bijhouding.
 */
@XmlElement("correctieregistratieRelatie")
public final class CorrectieRegistratieRelatieActieElement extends AbstractCorrectieRegistratieActieElement<RelatieHistorie>
        implements CorrectieRelatieActieElement {

    @XmlTransient
    private static final CharacterElement REDEN_NIETIGVERKLARING = new CharacterElement('N');

    private final RelatieElement relatie;

    /**
     * Maakt een CorrectieVervalRelatieActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param relatie relatie objecttype
     */
    public CorrectieRegistratieRelatieActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronReferenties,
            final RelatieElement relatie) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties);
        ValidatieHelper.controleerOpNullWaarde(relatie, "relatie");
        this.relatie = relatie;
    }

    /**
     * Geef de waarde van relatie.
     * @return relatie
     */
    public RelatieElement getRelatie() {
        return relatie;
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.CORRECTIEREGISTRATIE_RELATIE;
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();

        controleerDatumAanvangNietInToekomst(meldingen);
        controleerEindeRelatie(meldingen);
        controleerNietigVerklaring(meldingen);

        controleerGemeenteAanvangGevuldBijLandNederland(meldingen);
        controleerGemeenteEindeGevuld(meldingen);
        return meldingen;
    }

    @Bedrijfsregel(Regel.R2044)
    private void controleerGemeenteAanvangGevuldBijLandNederland(final List<MeldingElement> meldingen) {
        final RelatieGroepElement relatieGroep = relatie.getRelatieGroep();
        if (relatieGroep.getGemeenteAanvangCode() == null && relatieGroep.isAanvangLandOfGebiedNederland()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2044, this));
        }
    }

    @Bedrijfsregel(Regel.R2045)
    private void controleerGemeenteEindeGevuld(final List<MeldingElement> meldingen) {
        final RelatieGroepElement relatieGroep = relatie.getRelatieGroep();
        if (relatieGroep.getLandGebiedEindeCode() != null && relatieGroep.getGemeenteEindeCode() == null && relatieGroep.isEindeLandOfGebiedNederland()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2045, this));
        }
    }

    private void controleerDatumAanvangNietInToekomst(final List<MeldingElement> meldingen) {
        if (relatie instanceof AbstractHuwelijkOfGpElement && getVerzoekBericht().isPrevalidatie()) {
            /*
             * Als het geen prevalidatie is dan wordt deze regel al gecontroleerd door RelatieGroepElement, bij correctie van HGP
             * moet deze regel echter OOK bij de prevalidatie afgaan.
             */
            relatie.getRelatieGroep().controleerDatumAanvangNietInToekomst(meldingen);
        }
    }

    @Bedrijfsregel(Regel.R1878)
    @Bedrijfsregel(Regel.R2478)
    private void controleerEindeRelatie(final List<MeldingElement> meldingen) {
        if (getRelatie().getRelatieGroep().getDatumEinde() != null) {
            if (FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getRelatieEntiteit().getRelatieHistorieSet()).getDatumEinde() == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R1878, getRelatie().getRelatieGroep()));
            }
            if (getRelatie().getRelatieGroep().getRedenEindeCode() == null || getRelatie().getRelatieGroep().getLandGebiedEindeCode() == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R2478, getRelatie().getRelatieGroep()));
            }
        } else if (!alleBeeindigingGegevensLeeg(getRelatie().getRelatieGroep())) {
            meldingen.add(MeldingElement.getInstance(Regel.R2479, getRelatie().getRelatieGroep()));
        }
    }

    private boolean alleBeeindigingGegevensLeeg(RelatieGroepElement relatieGroep) {
        return ValidationUtils.zijnParametersAllemaalNull(relatieGroep.getRedenEindeCode(), relatieGroep.getGemeenteEindeCode(),
                relatieGroep.getWoonplaatsnaamEinde(), relatieGroep.getLandGebiedEindeCode(), relatieGroep.getBuitenlandsePlaatsEinde(),
                relatieGroep.getBuitenlandseRegioEinde(), relatieGroep.getOmschrijvingLocatieEinde());
    }

    @Bedrijfsregel(Regel.R2477)
    private void controleerNietigVerklaring(final List<MeldingElement> meldingen) {
        if (REDEN_NIETIGVERKLARING.equals(getRelatie().getRelatieGroep().getRedenEindeCode()) && (
                (getRelatie().getRelatieGroep().getDatumAanvang() == null && getRelatie().getRelatieGroep().getDatumEinde() == null) || !Objects
                        .equals(getRelatie().getRelatieGroep().getDatumAanvang(), getRelatie().getRelatieGroep().getDatumEinde()))) {
            meldingen.add(MeldingElement.getInstance(Regel.R2477, getRelatie().getRelatieGroep()));
        }
    }

    @Override
    public List<BijhoudingPersoon> getHoofdPersonen() {
        return relatie.getRelatieEntiteit().getHoofdPersonen(getVerzoekBericht());
    }

    @Override
    public DatumElement getPeilDatum() {
        return getVerzoekBericht().getDatumOntvangst();
    }

    @Override
    public List<PersoonElement> getPersoonElementen() {
        return relatie.getPersoonElementen();
    }

    @Override
    public BijhoudingRelatie getRelatieEntiteit() {
        return relatie.getRelatieEntiteit();
    }

    @Override
    protected Set<RelatieHistorie> bepaalSetVoorNieuwVoorkomen() {
        return relatie.getRelatieEntiteit().getRelatieHistorieSet();
    }

    @Override
    protected RelatieHistorie maakNieuwVoorkomen() {
        return relatie.getRelatieEntiteit().maakNieuweRelatieHistorieVoorCorrectie(relatie.getRelatieGroep());
    }
}
