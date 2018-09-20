/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.validatie.constraint.Datum;


/**
 * BRP Actie.
 */
public class BRPActie {

    private List<RootObject> rootObjecten;

    private Long             id;

    private SoortActie       soort;

    private Partij           partij;

    @Datum
    private Integer          datumAanvangGeldigheid;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public SoortActie getSoort() {
        return soort;
    }

    public void setSoort(final SoortActie soort) {
        this.soort = soort;
    }

    public Partij getPartij() {
        return partij;
    }

    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    public List<RootObject> getRootObjecten() {
        return rootObjecten;
    }

    public void setRootObjecten(final List<RootObject> rootObjecten) {
        this.rootObjecten = rootObjecten;
    }

    /**
     * Voegt een RootObject toe aan de actie.
     *
     * @param rootObj Het toe te voegen rootobject.
     */
    public void voegRootObjectToe(final RootObject rootObj) {
        if (rootObjecten == null) {
            rootObjecten = new ArrayList<RootObject>();
        }
        rootObjecten.add(rootObj);
    }
}
