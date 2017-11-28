/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECT_SLEUTEL_ATTRIBUUT;

/**
 * Een document uit het bijhoudingsbericht.
 */
@XmlElement("document")
public final class DocumentElement extends AbstractBmrObjecttype {

    private final StringElement soortNaam;
    private final StringElement aktenummer;
    private final StringElement omschrijving;
    private final StringElement partijCode;

    /**
     * Maakt een DocumentElement object.
     *
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param soortNaam           soortNaam
     * @param aktenummer          aktenummer
     * @param omschrijving        omschrijving
     * @param partijCode          partijCode
     */
    public DocumentElement(
            final Map<String, String> basisAttribuutGroep,
            final StringElement soortNaam,
            final StringElement aktenummer,
            final StringElement omschrijving,
            final StringElement partijCode) {
        super(basisAttribuutGroep);
        ValidatieHelper.controleerOpNullWaarde(soortNaam, "soortNaam");
        ValidatieHelper.controleerOpNullWaarde(partijCode, "partijCode");
        this.soortNaam = soortNaam;
        this.aktenummer = aktenummer;
        this.omschrijving = omschrijving;
        this.partijCode = partijCode;
    }

    /**
     * Geef de waarde van soortNaam.
     *
     * @return soortNaam
     */
    public StringElement getSoortNaam() {
        return soortNaam;
    }

    /**
     * Geef de waarde van aktenummer.
     *
     * @return aktenummer
     */
    public StringElement getAktenummer() {
        return aktenummer;
    }

    /**
     * Geef de waarde van omschrijving.
     *
     * @return omschrijving
     */
    public StringElement getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geef de waarde van partijCode.
     *
     * @return partijCode
     */
    public StringElement getPartijCode() {
        return partijCode;
    }

    /**
     * Geef de waarde van objectSleutel.
     *
     * @return objectSleutel
     */
    public String getObjectSleutel() {
        return getAttributen().get(OBJECT_SLEUTEL_ATTRIBUUT.toString());
    }

    /**
     * Geeft de {@link SoortDocument} terug van dit document.
     *
     * @return de {@link SoortDocument} of null als het soort document niet gevonden kan worden
     */
    public SoortDocument getSoortDocument() {
        return getDynamischeStamtabelRepository().getSoortDocumentByNaam(soortNaam.getWaarde());
    }

    /**
     * Maakt een Document entiteit o.b.v. de document gegevens uit het bijhoudingsbericht.
     *
     * @return een nieuw document entiteit
     */
    Document maakDocumentEntiteit() {
        final Document document = new Document(getSoortDocument(), getDynamischeStamtabelRepository().getPartijByCode(getPartijCode().getWaarde()));
        document.setAktenummer(BmrAttribuut.getWaardeOfNull(getAktenummer()));
        document.setOmschrijving(BmrAttribuut.getWaardeOfNull(getOmschrijving()));

        return document;
    }

    @Bedrijfsregel(Regel.R1848)
    @Bedrijfsregel(Regel.R1849)
    @Bedrijfsregel(Regel.R1850)
    @Bedrijfsregel(Regel.R1605)
    @Bedrijfsregel(Regel.R2235)
    @Override
    protected List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        final SoortDocument soortDocument = getSoortDocument();

        if (soortDocument != null) {
            if (!voldoetAanRegel1848(soortDocument)) {
                meldingen.add(MeldingElement.getInstance(Regel.R1848, this));
            }

            if (!voldoetAanRegel1849(soortDocument)) {
                meldingen.add(MeldingElement.getInstance(Regel.R1849, this));
            }

            if (!voldoetAanRegel1850(soortDocument)) {
                meldingen.add(MeldingElement.getInstance(Regel.R1850, this));
            }
            if (!voldoetAanRegel2235()) {
                meldingen.add(MeldingElement.getInstance(Regel.R2235, this));
            }
        } else {
            meldingen.add(MeldingElement.getInstance(Regel.R1605, this));
        }
        return meldingen;
    }

    private boolean voldoetAanRegel1849(final SoortDocument soortDocument) {
        return (soortDocument.getRegistersoort() == null) == (aktenummer == null);
    }

    private boolean voldoetAanRegel1848(final SoortDocument soortDocument) {
        return aktenummer == null
                || soortDocument.getRegistersoort() == null
                || aktenummer.getWaarde().startsWith(String.valueOf(soortDocument.getRegistersoort()));
    }

    private boolean voldoetAanRegel1850(final SoortDocument soortDocument) {
        return soortDocument.getRegistersoort() == null || omschrijving == null;
    }

    private boolean voldoetAanRegel2235() {
        final Partij partij = getDynamischeStamtabelRepository().getPartijByCode(getPartijCode().getWaarde());
        return partij != null;
    }
}
