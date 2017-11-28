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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Het {@link VerblijfsrechtElement} voor een bijhoudings bericht.
 */
@XmlElement("verblijfsrecht")
public final class VerblijfsrechtElement extends AbstractBmrGroep {

    private final StringElement aanduidingCode;
    private final DatumElement datumAanvang;
    private final DatumElement datumMededeling;
    private final DatumElement datumVoorzienEinde;

    /**
     * Maakt een AbstractBmrGroep object.
     * @param attributen de lijst met attributen
     * @param aanduidingCode aanduidingcode
     * @param datumAanvang datum aanvang
     * @param datumMededeling datum mededeling
     * @param datumVoorzienEinde datum voorzien einde
     */
    public VerblijfsrechtElement(final Map<String, String> attributen, final StringElement aanduidingCode, final DatumElement datumAanvang,
                                 final DatumElement datumMededeling, final DatumElement datumVoorzienEinde) {
        super(attributen);
        ValidatieHelper.controleerOpNullWaarde(aanduidingCode, "aanduidingCode");
        ValidatieHelper.controleerOpNullWaarde(datumAanvang, "datumAanvang");
        ValidatieHelper.controleerOpNullWaarde(datumMededeling, "datumMededeling");
        this.aanduidingCode = aanduidingCode;
        this.datumAanvang = datumAanvang;
        this.datumMededeling = datumMededeling;
        this.datumVoorzienEinde = datumVoorzienEinde;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new LinkedList<>();
        controleerAanduidingCode(meldingen);
        controleerDatumVoorzienEinde(meldingen);
        controleerDatumMededelingNietInToekomst(meldingen);
        controleerDatumAanvang(meldingen);
        return meldingen;
    }


    @Bedrijfsregel(Regel.R2330)
    @Bedrijfsregel(Regel.R2350)
    private void controleerDatumMededelingNietInToekomst(final List<MeldingElement> meldingen) {
        if (!datumMededeling.isVolledigBekendeDatum()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2350, this));
        } else if (datumMededeling.getWaarde() > DatumUtil.vandaag()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2330, this));
        }
    }

    @Bedrijfsregel(Regel.R2332)
    @Bedrijfsregel(Regel.R2349)
    private void controleerDatumAanvang(final List<MeldingElement> meldingen) {
        if (!datumAanvang.isVolledigBekendeDatum()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2349, this));
        } else if (datumAanvang.getWaarde() > DatumUtil.vandaag()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2332, this));
        }
    }

    @Bedrijfsregel(Regel.R1900)
    @Bedrijfsregel(Regel.R2750)
    private void controleerAanduidingCode(final List<MeldingElement> meldingen) {
        final Verblijfsrecht verblijfsrecht = getDynamischeStamtabelRepository().getVerblijfsrechtByCode(aanduidingCode.getWaarde());
        if (verblijfsrecht == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R1900, this));
        } else if (!DatumUtil.valtDatumBinnenPeriode(datumAanvang.getWaarde(), verblijfsrecht.getDatumAanvangGeldigheid(),
                verblijfsrecht.getDatumEindeGeldigheid())) {
            meldingen.add(MeldingElement.getInstance(Regel.R2750, this));
        }
    }

    @Bedrijfsregel(Regel.R2379)
    @Bedrijfsregel(Regel.R2331)
    @Bedrijfsregel(Regel.R2351)
    private void controleerDatumVoorzienEinde(final List<MeldingElement> meldingen) {
        if (ValidatieHelper.vergelijkStringMetShort(aanduidingCode.getWaarde(), Verblijfsrecht.GEEN_VERBLIJFSTITEL)
                && BmrAttribuut.getWaardeOfNull(datumVoorzienEinde) != null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2379, this));
        }
        if (datumVoorzienEinde != null) {
            if (DatumUtil.vergelijkOnbekendeDatumsGroterOfGelijkAan(datumAanvang.getWaarde(), datumVoorzienEinde.getWaarde())) {
                meldingen.add(MeldingElement.getInstance(Regel.R2331, this));
            }
            if (!datumVoorzienEinde.isVolledigBekendeDatum()) {
                meldingen.add(MeldingElement.getInstance(Regel.R2351, this));
            }
        }
    }

    /**
     * Geeft de aanduiding code.
     * @return aanduiding code
     */
    public StringElement getAanduidingCode() {
        return aanduidingCode;
    }

    /**
     * Geeft de datum aanvang.
     * @return de datum aanvang
     */
    public DatumElement getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Geeft de dataum mededeling.
     * @return datum mededeling
     */
    public DatumElement getDatumMededeling() {
        return datumMededeling;
    }

    /**
     * Geeft de datum voorzien einde.
     * @return datum voorzien einde
     */
    public DatumElement getDatumVoorzienEinde() {
        return datumVoorzienEinde;
    }
}
