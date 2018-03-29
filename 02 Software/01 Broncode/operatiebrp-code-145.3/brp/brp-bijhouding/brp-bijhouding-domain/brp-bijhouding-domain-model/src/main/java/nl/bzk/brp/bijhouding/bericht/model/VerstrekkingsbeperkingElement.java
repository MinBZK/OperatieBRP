/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerstrekkingsbeperking;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * Het verstrekkingsbeperking element binnen een persoon element in een bijhoudingsbericht.
 */
@XmlElement("verstrekkingsbeperking")
public class VerstrekkingsbeperkingElement extends AbstractBmrObjecttype {

    private final StringElement partijCode;
    private final StringElement omschrijvingDerde;
    private final StringElement gemeenteVerordeningPartijCode;

    /**
     * Maakt een nieuw VerstrekkingsbeperkingElement object.
     * @param attributen de lijst met attributen waarin objecttype moet voorkomen
     * @param partijCode partijCode
     * @param omschrijvingDerde omschrijvingDerde
     * @param gemeenteVerordeningPartijCode gemeenteVerordeningPartijCode
     */
    public VerstrekkingsbeperkingElement(final Map<String, String> attributen, final StringElement partijCode, final StringElement omschrijvingDerde,
                                         final StringElement gemeenteVerordeningPartijCode) {
        super(attributen);
        this.partijCode = partijCode;
        this.omschrijvingDerde = omschrijvingDerde;
        this.gemeenteVerordeningPartijCode = gemeenteVerordeningPartijCode;
    }

    /**
     * Geef de waarde van partijCode.
     * @return partijCode
     */
    public StringElement getPartijCode() {
        return partijCode;
    }

    /**
     * Geef de waarde van omschrijvingDerde.
     * @return omschrijvingDerde
     */
    public StringElement getOmschrijvingDerde() {
        return omschrijvingDerde;
    }

    /**
     * Geef de waarde van gemeenteVerordeningPartijCode.
     * @return gemeenteVerordeningPartijCode
     */
    public StringElement getGemeenteVerordeningPartijCode() {
        return gemeenteVerordeningPartijCode;
    }

    /**
     * Vergelijkt dit Verstrekkingsbeperking element met de gegeven bestaande Verstrekkingsberperking entiteit.
     * @param verstrekkingsbeperkingEntiteit de bestaande verstrekkingsbeperking
     * @return true als dit element dezelfde verstrekkingsbeperking aanduidt als de bestaande verstrekkingsbeperking, anders false
     */
    public boolean isGelijkAanBestaandeVerstrekkingsbeperking(final PersoonVerstrekkingsbeperking verstrekkingsbeperkingEntiteit) {
        return isVerstrekkingsBeperkingPartijGelijk(verstrekkingsbeperkingEntiteit)
                || (isVerstrekkingsbeperkingGemeentelijkeVerordeningGelijk(verstrekkingsbeperkingEntiteit)
                && isVerstrekkingBeperkingOmschrijvingDerdeGelijk(verstrekkingsbeperkingEntiteit));
    }

    private boolean isVerstrekkingBeperkingOmschrijvingDerdeGelijk(final PersoonVerstrekkingsbeperking verstrekkingsbeperkingEntiteit) {
        return verstrekkingsbeperkingEntiteit.getOmschrijvingDerde() != null && verstrekkingsbeperkingEntiteit.getOmschrijvingDerde()
                .equals(BmrAttribuut.getWaardeOfNull(getOmschrijvingDerde()));
    }

    private boolean isVerstrekkingsbeperkingGemeentelijkeVerordeningGelijk(final PersoonVerstrekkingsbeperking verstrekkingsbeperkingEntiteit) {
        return verstrekkingsbeperkingEntiteit.getGemeenteVerordening() != null && verstrekkingsbeperkingEntiteit.getGemeenteVerordening().getCode()
                .equals(BmrAttribuut.getWaardeOfNull(getGemeenteVerordeningPartijCode()));
    }

    private boolean isVerstrekkingsBeperkingPartijGelijk(final PersoonVerstrekkingsbeperking verstrekkingsbeperkingEntiteit) {
        return verstrekkingsbeperkingEntiteit.getPartij() != null && verstrekkingsbeperkingEntiteit.getPartij().getCode()
                .equals(BmrAttribuut.getWaardeOfNull(getPartijCode()));
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        controleerOmschrijvingDerde(meldingen);
        controleerPartij(meldingen);
        contoleerGemeenteVerordeningPartij(meldingen);
        return meldingen;
    }


    @Bedrijfsregel(Regel.R2257)
    @Bedrijfsregel(Regel.R2419)
    @Bedrijfsregel(Regel.R2255)
    private void contoleerGemeenteVerordeningPartij(final List<MeldingElement> meldingen) {
        if (gemeenteVerordeningPartijCode != null && getOmschrijvingDerde() == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2419, this));
        }
        if (gemeenteVerordeningPartijCode != null) {
            final Gemeente gemeente = getDynamischeStamtabelRepository().getGemeenteByPartijcode(gemeenteVerordeningPartijCode.getWaarde());
            if (gemeente == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R2257, this));
            } else if (!DatumUtil.valtDatumBinnenPeriode(getVerzoekBericht().getAdministratieveHandeling().getPeilDatum().getWaarde(),
                    gemeente.getPartij().getDatumIngang(), gemeente.getPartij().getDatumEinde())) {
                meldingen.add(MeldingElement.getInstance(Regel.R2255, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1911)
    @Bedrijfsregel(Regel.R2256)
    @Bedrijfsregel(Regel.R2253)
    private void controleerPartij(final List<MeldingElement> meldingen) {
        if (partijCode != null) {
            final Partij partij = getDynamischeStamtabelRepository().getPartijByCode(partijCode.getWaarde());
            if (partij == null) {
                meldingen.add(MeldingElement.getInstance(Regel.R2256, this));
            } else if (!partij.isIndicatieVerstrekkingsbeperkingMogelijk()) {
                meldingen.add(MeldingElement.getInstance(Regel.R1911, this));
            } else if (!DatumUtil.valtDatumBinnenPeriode(getVerzoekBericht().getAdministratieveHandeling().getPeilDatum().getWaarde(), partij.getDatumIngang(),
                    partij.getDatumEinde())) {
                meldingen.add(MeldingElement.getInstance(Regel.R2253, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1907)
    @Bedrijfsregel(Regel.R1908)
    @Bedrijfsregel(Regel.R2418)
    private void controleerOmschrijvingDerde(final List<MeldingElement> meldingen) {
        if (partijCode == null && omschrijvingDerde == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R1907, this));
        }
        if (omschrijvingDerde != null && gemeenteVerordeningPartijCode == null) {
            meldingen.add(MeldingElement.getInstance(Regel.R1908, this));
        }
        if (omschrijvingDerde != null && getPartijCode() != null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2418, this));
        }
    }
}
