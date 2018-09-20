/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto;

import nl.bzk.brp.model.basis.AbstractIdentificerendeGroep;
import org.apache.commons.lang.StringUtils;

/**
 * Abstracte class voor alle {@link BRPBericht} subclasses. Deze abstract class biedt bericht generieke methodes en
 * velden.
 */
public abstract class AbstractBRPBericht extends AbstractIdentificerendeGroep implements BRPBericht {

    private BerichtStuurgegevens berichtStuurgegevens;

    /**
     * Retourneert de stuurgegevens van het bericht.
     *
     * @return de stuurgegevens van het bericht.
     */
    public BerichtStuurgegevens getBerichtStuurgegevens() {
        return berichtStuurgegevens;
    }

    /**
     * Zet de stuurgegevens van het bericht.
     *
     * @param berichtStuurgegevens de stuurgegevens van het bericht.
     */
    public void setBerichtStuurgegevens(final BerichtStuurgegevens berichtStuurgegevens) {
        this.berichtStuurgegevens = berichtStuurgegevens;
    }

    /**
     * {@inheritDoc}
     * Standaard is dit de organisatie uit de stuurgegevens.
     */
    @Override
    public Integer getPartijId() {
        Integer partijId = null;
        if (getBerichtStuurgegevens() != null && StringUtils.isNotBlank(getBerichtStuurgegevens().getOrganisatie())
            && StringUtils.isNumeric(getBerichtStuurgegevens().getOrganisatie()))
        {
            partijId = Integer.valueOf(getBerichtStuurgegevens().getOrganisatie());
        }
        return partijId;
    }


}
