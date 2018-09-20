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

import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
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
    public Boolean getBehandeldAlsNederlander() {
        return getIndicatie(SoortIndicatie.BEHANDELD_ALS_NEDERLANDER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getBelemmeringVerstrekkingReisdocument() {
        return getIndicatie(SoortIndicatie.BELEMMERING_VERSTREKKING_REISDOCUMENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getBezitBuitenlandsReisdocument() {
        return getIndicatie(SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getDerdeHeeftGezag() {
        return getIndicatie(SoortIndicatie.DERDE_HEEFT_GEZAG);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getGepriviligeerde() {
        return getIndicatie(SoortIndicatie.GEPRIVILEGIEERDE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getOnderCuratele() {
        return getIndicatie(SoortIndicatie.ONDER_CURATELE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getVastgesteldNietNederlander() {
        return getIndicatie(SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getVerstrekkingsBeperking() {
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

    private void setIndicatie(final SoortIndicatie soortIndicatie, final Boolean waarde) {
        if (indicaties == null) {
            indicaties = new TreeMap<SoortIndicatie, PersoonIndicatie>();
        }
        PersoonIndicatie huidigeIndicatie = indicaties.get(soortIndicatie);
        if (huidigeIndicatie == null) {
            huidigeIndicatie = new PersistentDomeinObjectFactory().createPersoonIndicatie();
            huidigeIndicatie.setPersoon(this);
            huidigeIndicatie.setSoort(soortIndicatie);
            addPersoonIndicatie(huidigeIndicatie);
            indicaties.put(soortIndicatie, huidigeIndicatie);
        }
        huidigeIndicatie.setWaarde(waarde);
    }

    @Override
    public void setBehandeldAlsNederlander(final Boolean waarde) {
        setIndicatie(SoortIndicatie.BEHANDELD_ALS_NEDERLANDER, waarde);
    }

    @Override
    public void setBelemmeringVerstrekkingReisdocument(final Boolean waarde) {
        setIndicatie(SoortIndicatie.BELEMMERING_VERSTREKKING_REISDOCUMENT, waarde);
    }

    @Override
    public void setBezitBuitenlandsReisdocument(final Boolean waarde) {
        setIndicatie(SoortIndicatie.BEZIT_BUITENLANDS_REISDOCUMENT, waarde);
    }

    @Override
    public void setDerdeHeeftGezag(final Boolean waarde) {
        setIndicatie(SoortIndicatie.DERDE_HEEFT_GEZAG, waarde);
    }

    @Override
    public void setGepriviligeerde(final Boolean waarde) {
        setIndicatie(SoortIndicatie.GEPRIVILEGIEERDE, waarde);
    }

    @Override
    public void setOnderCuratele(final Boolean waarde) {
        setIndicatie(SoortIndicatie.ONDER_CURATELE, waarde);
    }

    @Override
    public void setVastgesteldNietNederlander(final Boolean waarde) {
        setIndicatie(SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER, waarde);
    }

    @Override
    public void setVerstrekkingsBeperking(final Boolean waarde) {
        setIndicatie(SoortIndicatie.VERSTREKKINGSBEPERKING, waarde);
    }
}
