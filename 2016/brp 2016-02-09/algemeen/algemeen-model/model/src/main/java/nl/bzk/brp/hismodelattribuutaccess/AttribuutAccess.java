/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.hismodelattribuutaccess;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * DTO klasse voor het opslaan van de informatie van 1 attribuut access.
 */
public final class AttribuutAccess {

    private static final int INITIEEL_ONEVEN_NUMMER          = 13;
    private static final int VERMENIGVULDIGING_ONEVEN_NUMMER = 31;

    private final int                            groepDbObjectId;
    private final Long                           voorkomenId;
    private final int                            attribuutDbObjectId;
    private final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;

    /**
     * Constructor met alle velden.
     *
     * @param groepDbObjectId        het groep db object id
     * @param voorkomenId            het voorkomen id (kan null zijn)
     * @param attribuutDbObjectId    het attribuut db object id
     * @param datumAanvangGeldigheid de datum aanvang geldigheid van het voorkomen (kan null zijn)
     */
    public AttribuutAccess(final int groepDbObjectId, final Long voorkomenId, final int attribuutDbObjectId,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid)
    {
        this.groepDbObjectId = groepDbObjectId;
        this.voorkomenId = voorkomenId;
        this.attribuutDbObjectId = attribuutDbObjectId;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Retourneert de DBObjectId van de groep waartoe het attribuut behoort.
     *
     * @return de DBObjectId van de groep waartoe het attribuut behoort.
     */
    public int getGroepDbObjectId() {
        return groepDbObjectId;
    }

    /**
     * Retourneert het voorkomen id (database id uit His tabel) van de groep waartoe het attribuut behoort.
     *
     * @return het voorkomen id (database id uit His tabel) van de groep waartoe het attribuut behoort.
     */
    public Long getVoorkomenId() {
        return voorkomenId;
    }

    /**
     * Retourneert de DBObjectId van het attribuut.
     *
     * @return de DBObjectId van het attribuut.
     */
    public int getAttribuutDbObjectId() {
        return attribuutDbObjectId;
    }

    /**
     * Retourneert de datum van aanvang geldigheid van de groep instantie waartoe het attribuut behoort.
     *
     * @return de datum van aanvang geldigheid van de groep instantie waartoe het attribuut behoort.
     */
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(INITIEEL_ONEVEN_NUMMER, VERMENIGVULDIGING_ONEVEN_NUMMER)
            .append(this.groepDbObjectId).append(this.voorkomenId).append(this.attribuutDbObjectId)
            .append(this.datumAanvangGeldigheid).toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        final boolean equals;
        if (obj == null) {
            equals = false;
        } else if (obj == this) {
            equals = true;
        } else if (obj instanceof AttribuutAccess) {
            final AttribuutAccess that = (AttribuutAccess) obj;
            equals =
                new EqualsBuilder().append(this.groepDbObjectId, that.groepDbObjectId)
                    .append(this.voorkomenId, that.voorkomenId)
                    .append(this.attribuutDbObjectId, that.attribuutDbObjectId)
                    .append(this.datumAanvangGeldigheid, that.datumAanvangGeldigheid).isEquals();
        } else {
            equals = false;
        }
        return equals;
    }

}
