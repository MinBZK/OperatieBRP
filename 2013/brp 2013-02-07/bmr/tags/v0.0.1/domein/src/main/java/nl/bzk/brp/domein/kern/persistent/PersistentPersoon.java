/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Gegenereerd uit de BRP Metadata Repository.
 * Gegenereerd op : Thu Dec 15 08:32:18 CET 2011
 * Model : BRP/Kern (0.07)
 * Generator : DomeinModelGenerator
 */
package nl.bzk.brp.domein.kern.persistent;

import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.bzk.brp.domein.kern.Persoon;
import nl.bzk.brp.domein.kern.PersoonIndicatie;
import nl.bzk.brp.domein.kern.SoortIndicatie;
import nl.bzk.brp.domein.kern.persistent.basis.AbstractPersistentPersoon;


@Entity
@Table(name = "Pers", schema = "Kern")
public class PersistentPersoon extends AbstractPersistentPersoon implements Persoon {

    @Transient
    private Map<SoortIndicatie, PersoonIndicatie> indicaties;

    public PersistentPersoon() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean behandeldAlsNederlander() {
        return getIndicatie(SoortIndicatie.BEHANDELD_ALS_NEDERLANDER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object belemmeringVerstrekkingReisdocument() {
        return getIndicatie(SoortIndicatie.BELEMMERING_VERSTREKKING_REISDOCUMENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object bezitBuitenlandsReisdocument() {
        return getIndicatie(SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean derdeHeeftGezag() {
        return getIndicatie(SoortIndicatie.DERDE_HEEFT_GEZAG);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean gepriviligeerde() {
        return getIndicatie(SoortIndicatie.GEPRIVILEGIEERDE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onderCuratele() {
        return getIndicatie(SoortIndicatie.ONDER_CURATELE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean vastgesteldNietNederlander() {
        return getIndicatie(SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean verstrekkingsBeperking() {
        return getIndicatie(SoortIndicatie.VERSTREKKINGSBEPERKING);
    }

    /**
     * @param soortIndicatie TODO
     * @return
     */
    private Boolean getIndicatie(final SoortIndicatie soortIndicatie) {
        if (indicaties == null) {
            indicaties = new TreeMap<SoortIndicatie, PersoonIndicatie>();
            for (PersoonIndicatie indicatie : getPersoonIndicatieen()) {
                indicaties.put(indicatie.getSoort(), indicatie);
            }
        }
        PersoonIndicatie indicatie = indicaties.get(soortIndicatie);
        if (indicatie != null) {
            return indicatie.getWaarde();
        }
        return null;
    }
}
