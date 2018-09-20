/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifte;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentRedenOntbreken;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentSoort;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

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
    private final BrpReisdocumentSoort soort;
    @Element(name = "nummer", required = false)
    private final String nummer;
    @Element(name = "datumIngangDocument", required = false)
    private final BrpDatum datumIngangDocument;
    @Element(name = "datumUitgifte", required = false)
    private final BrpDatum datumUitgifte;
    @Element(name = "autoriteitVanAfgifte", required = false)
    private final BrpReisdocumentAutoriteitVanAfgifte autoriteitVanAfgifte;
    @Element(name = "datumVoorzieneEindeGeldigheid", required = false)
    private final BrpDatum datumVoorzieneEindeGeldigheid;
    @Element(name = "datumInhoudingVermissing", required = false)
    private final BrpDatum datumInhoudingVermissing;
    @Element(name = "redenOntbreken", required = false)
    private final BrpReisdocumentRedenOntbreken redenOntbreken;
    @Element(name = "lengteHouder", required = false)
    private final Integer lengteHouder;

    /**
     * Maakt een BrpReisdocumentInhoud object.
     * 
     * @param soort
     *            soort reisdocument, mag niet null zijn
     * @param nummer
     *            nummer van het reisdocument, mag niet null zijn
     * @param datumUitgifte
     *            datum van uitgifte, mag niet null zijn
     * @param autoriteitVanAfgifte
     *            autoriteit van afgifte, mag niet null zijn
     * @param datumVoorzieneEindeGeldigheid
     *            voorziene einde geldigheid, mag niet null zijn
     * @param datumInhoudingVermissing
     *            datum inhouding of vermissing, mag null zijn
     * @param redenOntbreken
     *            reden dat het reisdocument is vervallen, mag null zijn
     * @param lengteHouder
     *            lengte van de persoon in hele centimeters, mag null zijn
     * 
     * @throws NullPointerException
     *             als een van de verplichte velden null is
     */
    // CHECKSTYLE:OFF - Meer dan 7 parameters is in constructors van immutable model klassen getolereerd.
    public BrpReisdocumentInhoud(
            @Element(name = "soort", required = false) final BrpReisdocumentSoort soort,
            @Element(name = "nummer", required = false) final String nummer,
            @Element(name = "datumIngangDocument", required = false) final BrpDatum datumIngangDocument,
            @Element(name = "datumUitgifte", required = false) final BrpDatum datumUitgifte,
            @Element(name = "autoriteitVanAfgifte", required = false) final BrpReisdocumentAutoriteitVanAfgifte autoriteitVanAfgifte,
            @Element(name = "datumVoorzieneEindeGeldigheid", required = false) final BrpDatum datumVoorzieneEindeGeldigheid,
            @Element(name = "datumInhoudingVermissing", required = false) final BrpDatum datumInhoudingVermissing,
            @Element(name = "redenOntbreken", required = false) final BrpReisdocumentRedenOntbreken redenOntbreken,
            @Element(name = "lengteHouder", required = false) final Integer lengteHouder) {
        // CHECKSTYLE:ON
        checkNotNull(soort, nummer, datumIngangDocument, datumUitgifte, autoriteitVanAfgifte,
                datumVoorzieneEindeGeldigheid);
        this.soort = soort;
        this.nummer = nummer;
        this.datumIngangDocument = datumIngangDocument;
        this.datumUitgifte = datumUitgifte;
        this.autoriteitVanAfgifte = autoriteitVanAfgifte;
        this.datumVoorzieneEindeGeldigheid = datumVoorzieneEindeGeldigheid;
        this.datumInhoudingVermissing = datumInhoudingVermissing;
        this.redenOntbreken = redenOntbreken;
        this.lengteHouder = lengteHouder;
    }

    /**
     * Controleer de verplichte velden op null waarden.
     * 
     * @throws NullPointerException
     *             als een van de velden null is
     */
    private void checkNotNull(
            final BrpReisdocumentSoort soort,
            final String nummer,
            final BrpDatum datumIngangDocument,
            final BrpDatum datumUitgifte,
            final BrpReisdocumentAutoriteitVanAfgifte autoriteitVanAfgifte,
            final BrpDatum datumVoorzieneEindeGeldigheid) {
        final String message =
                "Een van de verplichte velden is null: [soort=%s, nummer=%s, datumIngangDocument=%s, "
                        + "datumUitgifte=%s, autoriteitVanAfgifte=%s, datumVoorzieneEindeGeldigheid=%s]";
        ValidationUtils.controleerOpNullWaarden(message, soort, nummer, datumIngangDocument, datumUitgifte,
                autoriteitVanAfgifte, datumVoorzieneEindeGeldigheid);
    }

    @Override
    public boolean isLeeg() {
        // groep kan niet leeg zijn, de constructor voorkomt dat.
        return false;
    }

    /**
     * @return the soort
     */
    public BrpReisdocumentSoort getSoort() {
        return soort;
    }

    /**
     * @return the nummer
     */
    public String getNummer() {
        return nummer;
    }

    /**
     * @return the datumIngangDocument
     */
    public BrpDatum getDatumIngangDocument() {
        return datumIngangDocument;
    }

    /**
     * @return the datumUitgifte
     */
    public BrpDatum getDatumUitgifte() {
        return datumUitgifte;
    }

    /**
     * @return the autoriteitVanAfgifte
     */
    public BrpReisdocumentAutoriteitVanAfgifte getAutoriteitVanAfgifte() {
        return autoriteitVanAfgifte;
    }

    /**
     * @return the datumVoorzieneEindeGeldigheid
     */
    public BrpDatum getDatumVoorzieneEindeGeldigheid() {
        return datumVoorzieneEindeGeldigheid;
    }

    /**
     * @return the datumInhoudingVermissing
     */
    public BrpDatum getDatumInhoudingVermissing() {
        return datumInhoudingVermissing;
    }

    /**
     * @return the redenOntbreken
     */
    public BrpReisdocumentRedenOntbreken getRedenOntbreken() {
        return redenOntbreken;
    }

    /**
     * @return the lengteHouder
     */
    public Integer getLengteHouder() {
        return lengteHouder;
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
        return new EqualsBuilder().append(soort, castOther.soort).append(nummer, castOther.nummer)
                .append(datumIngangDocument, castOther.datumIngangDocument)
                .append(datumUitgifte, castOther.datumUitgifte)
                .append(autoriteitVanAfgifte, castOther.autoriteitVanAfgifte)
                .append(datumVoorzieneEindeGeldigheid, castOther.datumVoorzieneEindeGeldigheid)
                .append(datumInhoudingVermissing, castOther.datumInhoudingVermissing)
                .append(redenOntbreken, castOther.redenOntbreken).append(lengteHouder, castOther.lengteHouder)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soort).append(nummer).append(datumIngangDocument).append(datumUitgifte)
                .append(autoriteitVanAfgifte).append(datumVoorzieneEindeGeldigheid).append(datumInhoudingVermissing)
                .append(redenOntbreken).append(lengteHouder).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("soort", soort).append("nummer", nummer).append("datumIngangDocument", datumIngangDocument)
                .append("datumUitgifte", datumUitgifte).append("autoriteitVanAfgifte", autoriteitVanAfgifte)
                .append("datumVoorzieneEindeGeldigheid", datumVoorzieneEindeGeldigheid)
                .append("datumInhoudingVermissing", datumInhoudingVermissing)
                .append("redenOntbreken", redenOntbreken).append("lengteHouder", lengteHouder).toString();
    }

}
