/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;


/**
 * Modellering van een {@link nl.bzk.brp.model.operationeel.kern.PersoonModel} met alle historische gegevens
 * die er van iemand zijn.
 */
public class PersoonVolledig extends AbstractVersie {

    @JsonProperty
    private int id;

    @JsonProperty
    private SoortPersoon soort;

    @JsonProperty
    private List<HisPersoonGeslachtsaanduidingModel> geslachtsaanduiding;

    @JsonProperty
    private List<HisPersoonIdentificatienummersModel> identificatienummers;

    @JsonProperty
    private List<HisPersoonGeslachtsnaamcomponentModel> geslachtsnaamcomponent;

    @JsonProperty
    private List<HisPersoonGeboorteModel> geboorte;

    @JsonProperty
    private List<HisPersoonOverlijdenModel> overlijden;

    @JsonProperty
    private List<HisPersoonAdresModel> adressen;

    /**
     * Constructor met id.
     * @param id de id van dit object
     */
    public PersoonVolledig(final int id) {
        this.setVersie(HUIDIGE_VERSIE);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public SoortPersoon getSoort() {
        return soort;
    }

    public void setSoort(final SoortPersoon soort) {
        this.soort = soort;
    }

    public List<HisPersoonGeslachtsaanduidingModel> getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    public void setGeslachtsaanduiding(final List<HisPersoonGeslachtsaanduidingModel> geslachtsaanduiding) {
        this.geslachtsaanduiding = geslachtsaanduiding;
    }

    public List<HisPersoonIdentificatienummersModel> getIdentificatienummers() {
        return identificatienummers;
    }

    public void setIdentificatienummers(final List<HisPersoonIdentificatienummersModel> identificatienummers) {
        this.identificatienummers = identificatienummers;
    }

    /**
     * Getter die {@link #geslachtsnaamcomponent} instantieert als deze <code>null</code> is.
     *
     * @return de lijst geslachtsnaamcomponent, of een lege lijst
     */
    public List<HisPersoonGeslachtsnaamcomponentModel> getGeslachtsnaamcomponent() {
        if (this.geslachtsnaamcomponent == null) {
            this.geslachtsnaamcomponent = new ArrayList<HisPersoonGeslachtsnaamcomponentModel>();
        }
        return geslachtsnaamcomponent;
    }

    public void setGeslachtsnaamcomponent(final List<HisPersoonGeslachtsnaamcomponentModel> geslachtsnaamcomponent) {
        this.geslachtsnaamcomponent = geslachtsnaamcomponent;
    }

    public List<HisPersoonGeboorteModel> getGeboorte() {
        return geboorte;
    }

    public void setGeboorte(final List<HisPersoonGeboorteModel> geboorte) {
        this.geboorte = geboorte;
    }

    public List<HisPersoonOverlijdenModel> getOverlijden() {
        return overlijden;
    }

    public void setOverlijden(final List<HisPersoonOverlijdenModel> overlijden) {
        this.overlijden = overlijden;
    }

    /**
     * Getter die {@link #adressen} instantieert als deze <code>null</code> is.
     *
     * @return de lijst adressen, of een lege lijst
     */
    public List<HisPersoonAdresModel> getAdressen() {
        if (this.adressen == null) {
            this.adressen = new ArrayList<HisPersoonAdresModel>();
        }
        return adressen;
    }

    public void setAdressen(final List<HisPersoonAdresModel> adressen) {
        this.adressen = adressen;
    }
}
