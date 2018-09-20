/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.usr.objecttype;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.RootObject;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.logisch.gen.objecttype.AbstractBRPActie;

/**
 * BRP Actie
  */
@Entity
@Table(schema = "Kern", name = "Actie")
@Access(AccessType.FIELD)
public class BRPActie extends AbstractBRPActie {

    @Transient
    private boolean          isCorrectGestructureerd = false;
    @Transient
    private List<RootObject> rootObjecten;
    @Transient
    private Datum            datumAanvangGeldigheid;


    public List<? extends RootObject> getRootObjecten() {
        return rootObjecten;
    }

    public void setRootObjecten(List<RootObject> rootObjecten) {
        this.rootObjecten = rootObjecten;
    }

    public void voegRootObjectToe(RootObject rootObj) {
        if (rootObj != null) {
            if (rootObjecten == null) {
                rootObjecten = new ArrayList<RootObject>();
            }
            rootObjecten.add(rootObj);
        }
    }

    public boolean heeftBetrekking() {
        return !(rootObjecten == null || rootObjecten.isEmpty());
    }

    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public void setDatumAanvangGeldigheid(final Datum datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    public RootObject getNieuweSituatie() {
        if (!isCorrectGestructureerd) {
            herstructureerRootObjecten();
        }

        return rootObjecten.get(0);
    }

    /**
     * Herstructureert de root objecten in de actie (indien meer dan een) tot een lijst van slechts een enkel root
     * object.
     */
    private void herstructureerRootObjecten() {
        if (!isCorrectGestructureerd && rootObjecten.size() > 1) {
            for (RootObject ro : rootObjecten) {
                // herstructureer ??    Lijkt bericht afhankelijk ?
            }
        }
    }
}
