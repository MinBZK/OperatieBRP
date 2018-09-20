/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP Groep Reisdocument.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpReisdocumentInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "soort", required = false)
    private final BrpSoortNederlandsReisdocumentCode soort;
    @Element(name = "nummer", required = false)
    private final BrpString nummer;
    @Element(name = "datumIngangDocument", required = false)
    private final BrpDatum datumIngangDocument;
    @Element(name = "datumUitgifte", required = false)
    private final BrpDatum datumUitgifte;
    @Element(name = "autoriteitVanAfgifte", required = false)
    private final BrpReisdocumentAutoriteitVanAfgifteCode autoriteitVanAfgifte;
    @Element(name = "datumEindeDocument", required = false)
    private final BrpDatum datumEindeDocument;
    @Element(name = "datumInhoudingOfVermissing", required = false)
    private final BrpDatum datumInhoudingOfVermissing;
    @Element(name = "aanduidingInhoudingOfVermissing", required = false)
    private final BrpAanduidingInhoudingOfVermissingReisdocumentCode aanduidingInhoudingOfVermissing;

    /**
     * Maakt een BrpReisdocumentInhoud object.
     *
     * @param soort
     *            soort reisdocument, mag niet null zijn
     * @param nummer
     *            nummer van het reisdocument, mag niet null zijn
     * @param datumIngangDocument
     *            datum ingang document, mag niet null zijn
     * @param datumUitgifte
     *            datum van uitgifte, mag niet null zijn
     * @param autoriteitVanAfgifte
     *            autoriteit van afgifte, mag niet null zijn
     * @param datumEindeDocument
     *            voorziene einde geldigheid, mag niet null zijn
     * @param datumInhoudingOfVermissing
     *            datum inhouding of vermissing, mag null zijn
     * @param aanduidingInhoudingOfVermissing
     *            reden dat het reisdocument is vervallen, mag null zijn
     * @throws NullPointerException
     *             als een van de verplichte velden null is
     */
    public BrpReisdocumentInhoud(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = "soort", required = false) final BrpSoortNederlandsReisdocumentCode soort,
        @Element(name = "nummer", required = false) final BrpString nummer,
        @Element(name = "datumIngangDocument", required = false) final BrpDatum datumIngangDocument,
        @Element(name = "datumUitgifte", required = false) final BrpDatum datumUitgifte,
        @Element(name = "autoriteitVanAfgifte", required = false) final BrpReisdocumentAutoriteitVanAfgifteCode autoriteitVanAfgifte,
        @Element(name = "datumEindeDocument", required = false) final BrpDatum datumEindeDocument,
        @Element(name = "datumInhoudingOfVermissing", required = false) final BrpDatum datumInhoudingOfVermissing,
        @Element(name = "aanduidingInhoudingOfVermissing",
                required = false) final BrpAanduidingInhoudingOfVermissingReisdocumentCode aanduidingInhoudingOfVermissing)
    {
        checkNotNull(soort, nummer, datumIngangDocument, datumUitgifte, autoriteitVanAfgifte, datumEindeDocument);
        this.soort = soort;
        this.nummer = nummer;
        this.datumIngangDocument = datumIngangDocument;
        this.datumUitgifte = datumUitgifte;
        this.autoriteitVanAfgifte = autoriteitVanAfgifte;
        this.datumEindeDocument = datumEindeDocument;
        this.datumInhoudingOfVermissing = datumInhoudingOfVermissing;
        this.aanduidingInhoudingOfVermissing = aanduidingInhoudingOfVermissing;
    }

    /**
     * Controleer de verplichte velden op null waarden.
     *
     * @throws NullPointerException
     *             als een van de velden null is
     */
    private void checkNotNull(
        final BrpSoortNederlandsReisdocumentCode soortParam,
        final BrpString nummerParam,
        final BrpDatum datumIngangDocumentParam,
        final BrpDatum datumUitgifteParam,
        final BrpReisdocumentAutoriteitVanAfgifteCode autoriteitVanAfgifteParam,
        final BrpDatum datumVoorzieneEindeGeldigheidParam)
    {
        final String message =
                "Een van de verplichte velden is null: [soort=%s, nummer=%s, datumIngangDocument=%s, "
                               + "datumUitgifte=%s, autoriteitVanAfgifte=%s, datumEindeDocument=%s]";
        Validatie.controleerOpNullWaarden(
            message,
            soortParam,
            nummerParam,
            datumIngangDocumentParam,
            datumUitgifteParam,
            autoriteitVanAfgifteParam,
            datumVoorzieneEindeGeldigheidParam);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        // groep kan niet leeg zijn, de constructor voorkomt dat.
        return false;
    }

    /**
     * Geef de waarde van soort.
     *
     * @return the soort
     */
    public BrpSoortNederlandsReisdocumentCode getSoort() {
        return soort;
    }

    /**
     * Geef de waarde van nummer.
     *
     * @return the nummer
     */
    public BrpString getNummer() {
        return nummer;
    }

    /**
     * Geef de waarde van datum ingang document.
     *
     * @return the datumIngangDocument
     */
    public BrpDatum getDatumIngangDocument() {
        return datumIngangDocument;
    }

    /**
     * Geef de waarde van datum uitgifte.
     *
     * @return the datumUitgifte
     */
    public BrpDatum getDatumUitgifte() {
        return datumUitgifte;
    }

    /**
     * Geef de waarde van autoriteit van afgifte.
     *
     * @return the autoriteitVanAfgifte
     */
    public BrpReisdocumentAutoriteitVanAfgifteCode getAutoriteitVanAfgifte() {
        return autoriteitVanAfgifte;
    }

    /**
     * Geef de waarde van datum einde document.
     *
     * @return the datumEindeDocument
     */
    public BrpDatum getDatumEindeDocument() {
        return datumEindeDocument;
    }

    /**
     * Geef de waarde van datum inhouding of vermissing.
     *
     * @return the datumInhoudingOfVermissing
     */
    public BrpDatum getDatumInhoudingOfVermissing() {
        return datumInhoudingOfVermissing;
    }

    /**
     * Geef de waarde van aanduiding inhouding of vermissing.
     *
     * @return the aanduidingInhoudingOfVermissing
     */
    public BrpAanduidingInhoudingOfVermissingReisdocumentCode getAanduidingInhoudingOfVermissing() {
        return aanduidingInhoudingOfVermissing;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpReisdocumentInhoud)) {
            return false;
        }
        final BrpReisdocumentInhoud castOther = (BrpReisdocumentInhoud) other;
        return new EqualsBuilder().append(soort, castOther.soort)
                                  .append(nummer, castOther.nummer)
                                  .append(datumIngangDocument, castOther.datumIngangDocument)
                                  .append(datumUitgifte, castOther.datumUitgifte)
                                  .append(autoriteitVanAfgifte, castOther.autoriteitVanAfgifte)
                                  .append(datumEindeDocument, castOther.datumEindeDocument)
                                  .append(datumInhoudingOfVermissing, castOther.datumInhoudingOfVermissing)
                                  .append(aanduidingInhoudingOfVermissing, castOther.aanduidingInhoudingOfVermissing)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soort)
                                    .append(nummer)
                                    .append(datumIngangDocument)
                                    .append(datumUitgifte)
                                    .append(autoriteitVanAfgifte)
                                    .append(datumEindeDocument)
                                    .append(datumInhoudingOfVermissing)
                                    .append(aanduidingInhoudingOfVermissing)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("soort", soort)
                                                                          .append("nummer", nummer)
                                                                          .append("datumIngangDocument", datumIngangDocument)
                                                                          .append("datumUitgifte", datumUitgifte)
                                                                          .append("autoriteitVanAfgifte", autoriteitVanAfgifte)
                                                                          .append("datumEindeDocument", datumEindeDocument)
                                                                          .append("datumInhoudingOfVermissing", datumInhoudingOfVermissing)
                                                                          .append("aanduidingInhoudingOfVermissing", aanduidingInhoudingOfVermissing)
                                                                          .toString();
    }

}
