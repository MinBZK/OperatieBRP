/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * Het element dat een migratie BMR groep representeert.
 */
@XmlElement("migratie")
public class MigratieElement extends AbstractBmrGroep {
    private static final char ONBEKEND = '?';
    private static final char TECHNISCHEWIJZIGINGBAG = 'B';
    private static final char INFRASTRUCTURELEWIJZIGING = 'I';

    private static final char PERSOON = 'P';
    private final CharacterElement redenWijzigingCode;
    private final CharacterElement aangeverCode;
    private final StringElement landGebiedCode;
    private final StringElement buitenlandsAdresRegel1;
    private final StringElement buitenlandsAdresRegel2;
    private final StringElement buitenlandsAdresRegel3;
    private final StringElement buitenlandsAdresRegel4;
    private final StringElement buitenlandsAdresRegel5;
    private final StringElement buitenlandsAdresRegel6;

    /**
     * Maakt een AbstractBmrGroep object.
     * @param attributen de lijst met attributen
     * @param redenWijzigingCode reden wijziging
     * @param aangeverCode aangever
     * @param landGebiedCode land of gebied
     * @param buitenlandsAdresRegel1 regel 1
     * @param buitenlandsAdresRegel2 regel 2
     * @param buitenlandsAdresRegel3 regel 3
     * @param buitenlandsAdresRegel4 regel 4
     * @param buitenlandsAdresRegel5 regel 5
     * @param buitenlandsAdresRegel6 regel 6
     */
    //
    /*aantal parameters is gerelateerd aan xml*/
    public MigratieElement(final Map<String, String> attributen,
                           final CharacterElement redenWijzigingCode,
                           final CharacterElement aangeverCode,
                           final StringElement landGebiedCode,
                           final StringElement buitenlandsAdresRegel1,
                           final StringElement buitenlandsAdresRegel2,
                           final StringElement buitenlandsAdresRegel3,
                           final StringElement buitenlandsAdresRegel4,
                           final StringElement buitenlandsAdresRegel5,
                           final StringElement buitenlandsAdresRegel6) {
        super(attributen);
        this.redenWijzigingCode = redenWijzigingCode;
        this.aangeverCode = aangeverCode;
        this.landGebiedCode = landGebiedCode;
        this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
        this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
        this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
        this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
        this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
        this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        List<MeldingElement> meldingen = new LinkedList<>();
        controleerLandGebied(meldingen);
        controleerAdresRegelsInCombinatieMetLandGebied(meldingen);
        controleerRedenWijzigng(meldingen);
        controleerAangever(meldingen);
        controleerBuitenlandseAdresRegels(meldingen);
        controleerBuitenlandseAdresRegelsOvergeslagen(meldingen);
        controleerLengteBuitenlandseAdresRegels(meldingen);
        return meldingen;
    }

    @Bedrijfsregel(Regel.R2363)
    @Bedrijfsregel(Regel.R2366)
    @Bedrijfsregel(Regel.R2427)
    private void controleerAangever(final List<MeldingElement> meldingen) {
        if (redenWijzigingCode != null && PERSOON == redenWijzigingCode.getWaarde() && aangeverCode == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2363, this));
        }
        if (aangeverCode != null) {
            final Aangever aangever = getDynamischeStamtabelRepository().getAangeverByCode(aangeverCode.getWaarde());
            if (aangever == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R2366, this));
            } else if (redenWijzigingCode == null || PERSOON != redenWijzigingCode.getWaarde()) {
                meldingen.add(MeldingElement.getInstance(Regel.R2427, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R2361)
    @Bedrijfsregel(Regel.R2362)
    private void controleerRedenWijzigng(final List<MeldingElement> meldingen) {
        if (redenWijzigingCode != null) {
            final RedenWijzigingVerblijf redenWijzigingVerblijf = getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(redenWijzigingCode.getWaarde());
            if (redenWijzigingVerblijf == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R2361, this));
            }
            if (ONBEKEND == redenWijzigingCode.getWaarde()
                    || INFRASTRUCTURELEWIJZIGING == redenWijzigingCode.getWaarde()
                    || TECHNISCHEWIJZIGINGBAG == redenWijzigingCode.getWaarde()) {
                meldingen.add(MeldingElement.getInstance(Regel.R2362, this));
            }

        }
    }

    @Bedrijfsregel(Regel.R1669)
    private void controleerAdresRegelsInCombinatieMetLandGebied(final List<MeldingElement> meldingen) {
        if (landGebiedCode != null && LandOfGebied.CODE_ONBEKEND.equals(landGebiedCode.getWaarde())
                && isEenElementgevuld(buitenlandsAdresRegel1, buitenlandsAdresRegel2, buitenlandsAdresRegel3, buitenlandsAdresRegel4, buitenlandsAdresRegel5,
                buitenlandsAdresRegel6)) {
            meldingen.add(MeldingElement.getInstance(Regel.R1669, this));
        }
    }

    private boolean isEenElementgevuld(StringElement... elementen) {
        for (StringElement elem : elementen) {
            if (BmrAttribuut.getWaardeOfNull(elem) != null) {
                return true;
            }
        }
        return false;
    }

    @Bedrijfsregel(Regel.R1664)
    @Bedrijfsregel(Regel.R1666)
    private void controleerLandGebied(final List<MeldingElement> meldingen) {
        if (landGebiedCode != null) {
            if (LandOfGebied.CODE_NEDERLAND.equals(landGebiedCode.getWaarde())) {
                meldingen.add(MeldingElement.getInstance(Regel.R1666, this));
            } else if (getDynamischeStamtabelRepository().getLandOfGebiedByCode(landGebiedCode.getWaarde()) == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R1664, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R2393)
    private void controleerBuitenlandseAdresRegels(final List<MeldingElement> meldingen) {
        if (isEenElementgevuld(buitenlandsAdresRegel4, buitenlandsAdresRegel5, buitenlandsAdresRegel6)) {
            meldingen.add(MeldingElement.getInstance(Regel.R2393, this));
        }
    }

    @Bedrijfsregel(Regel.R2394)
    private void controleerBuitenlandseAdresRegelsOvergeslagen(final List<MeldingElement> meldingen) {
        boolean adresRegelsOnGeldig = BmrAttribuut.getWaardeOfNull(getBuitenlandsAdresRegel6()) != null &&
                BmrAttribuut.getWaardeOfNull(getBuitenlandsAdresRegel5()) == null;
        adresRegelsOnGeldig |= BmrAttribuut.getWaardeOfNull(getBuitenlandsAdresRegel5()) != null &&
                BmrAttribuut.getWaardeOfNull(getBuitenlandsAdresRegel4()) == null;
        adresRegelsOnGeldig |= BmrAttribuut.getWaardeOfNull(getBuitenlandsAdresRegel4()) != null &&
                BmrAttribuut.getWaardeOfNull(getBuitenlandsAdresRegel3()) == null;
        adresRegelsOnGeldig |= BmrAttribuut.getWaardeOfNull(getBuitenlandsAdresRegel3()) != null &&
                BmrAttribuut.getWaardeOfNull(getBuitenlandsAdresRegel2()) == null;
        adresRegelsOnGeldig |= BmrAttribuut.getWaardeOfNull(getBuitenlandsAdresRegel2()) != null &&
                BmrAttribuut.getWaardeOfNull(getBuitenlandsAdresRegel1()) == null;
        if (adresRegelsOnGeldig) {
            meldingen.add(MeldingElement.getInstance(Regel.R2394, this));
        }
    }

    @Bedrijfsregel(Regel.R1377)
    private void controleerLengteBuitenlandseAdresRegels(final List<MeldingElement> meldingen) {
        if (isAdresRegelTeLang(buitenlandsAdresRegel1, buitenlandsAdresRegel2, buitenlandsAdresRegel3)) {
            meldingen.add(MeldingElement.getInstance(Regel.R1377, this));
        }
    }

    private boolean isAdresRegelTeLang(final StringElement... adresRegelElementen) {
        final int maxAantalKarakters = 35;
        for (final StringElement adresRegelElement : adresRegelElementen) {
            final String adresRegel = BmrAttribuut.getWaardeOfNull(adresRegelElement);
            if (adresRegel != null && adresRegel.length() > maxAantalKarakters) {
                return true;
            }
        }
        return false;
    }

    /**
     * geeft redenWijzigingCode terug.
     * @return StringElement
     */
    public CharacterElement getRedenWijzigingCode() {
        return redenWijzigingCode;
    }

    /**
     * geeft aanleverCode terug.
     * @return StringElement
     */
    public CharacterElement getAangeverCode() {
        return aangeverCode;
    }

    /**
     * Geeft land of gebied terug.
     * @return land of gebied
     */
    public StringElement getLandGebiedCode() {
        return landGebiedCode;
    }

    /**
     * geeft buitenland adres regel 1 terug.
     * @return StringElement
     */
    public StringElement getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    /**
     * geeft buitenland adres regel 2 terug.
     * @return StringElement
     */
    public StringElement getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    /**
     * geeft buitenland adres regel 3 terug.
     * @return StringElement
     */
    public StringElement getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    /**
     * geeft buitenland adres regel 4 terug.
     * @return StringElement
     */
    public StringElement getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    /**
     * geeft buitenland adres regel 5 terug.
     * @return StringElement
     */
    public StringElement getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    /**
     * geeft buitenland adres regel 6 terug.
     * @return StringElement
     */
    public StringElement getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

}
