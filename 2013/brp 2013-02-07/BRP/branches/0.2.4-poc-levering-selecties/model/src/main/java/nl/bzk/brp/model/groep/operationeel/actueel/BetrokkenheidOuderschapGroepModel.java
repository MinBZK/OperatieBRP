/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.actueel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.groep.logisch.BetrokkenheidOuderschapGroep;
import nl.bzk.brp.model.groep.logisch.basis.BetrokkenheidOuderschapGroepBasis;
import nl.bzk.brp.model.groep.operationeel.actueel.basis.AbstractBetrokkenheidOuderschapActGroepModel;
import org.hibernate.annotations.Formula;


/**
 *
 */
@Embeddable
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class BetrokkenheidOuderschapGroepModel extends AbstractBetrokkenheidOuderschapActGroepModel implements
    BetrokkenheidOuderschapGroep
{
    /**
     * datumAanvangOuderschap is de datumaanvang Geldigheid uit het C-Laag (historie).
     * Let op: AbstractBetrokkenheidOuderschapGroep kent ook een datumAanvang, en deze is transient en left over
     * omdat in de interface deze bestaat omdat het bericht nodig heeft/had.
     */
    @Formula("(select hbo.dataanvgel from kern.his_betrouderschap hbo "
        + "where hbo.betr = id and hbo.dateindegel is null and hbo.tsverval is null)")
    @JsonProperty
    private Integer datumAanvangOuderschap;
    @Formula("(select hbo.dateindegel from kern.his_betrouderschap hbo "
        + "where hbo.betr = id and hbo.dateindegel is null and hbo.tsverval is null)")
    @JsonProperty
    private Integer datumEindeGeldigheid;

    /** Default constructor tbv hibernate. */
    @SuppressWarnings("unused")
    private BetrokkenheidOuderschapGroepModel() {
        super();
    }

    /**
     * .
     *
     * @param betrokkenheidOuderschapGroepBasis BetrokkenheidOuderschapGroepBasis
     */
    public BetrokkenheidOuderschapGroepModel(
        final BetrokkenheidOuderschapGroepBasis betrokkenheidOuderschapGroepBasis)
    {
        super(betrokkenheidOuderschapGroepBasis);
    }

    /**
     * Geef aan wanneer de ouderschap begint.
     *
     * @return de datum
     */
    public Integer getDatumAanvangOuderschap() {
        return datumAanvangOuderschap;
    }

    protected void setDatumAanvangOuderschap(final Integer datumAanvangOuderschap) {
        this.datumAanvangOuderschap = datumAanvangOuderschap;
    }

    /**
     * Geef aan wanneer de ouderschap eindigt.
     *
     * @return de datum
     */
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    protected void setDatumEindeGeldigheid(final Integer datumEindeOuderschap) {
        datumEindeGeldigheid = datumEindeOuderschap;
    }
}
