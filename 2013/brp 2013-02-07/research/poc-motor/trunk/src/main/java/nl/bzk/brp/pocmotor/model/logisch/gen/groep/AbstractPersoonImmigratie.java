/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.groep;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Land;


/**
 * Persoon.Immigratie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonImmigratie extends AbstractGroep {

   @ManyToOne
   @JoinColumn(name = "LandVanwaarGevestigd")
   protected Land landVanwaarGevestigd;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatVestigingInNederland"))
   protected Datum datumVestigingInNederland;


   public Land getLandVanwaarGevestigd() {
      return landVanwaarGevestigd;
   }

   public void setLandVanwaarGevestigd(final Land landVanwaarGevestigd) {
      this.landVanwaarGevestigd = landVanwaarGevestigd;
   }

   public Datum getDatumVestigingInNederland() {
      return datumVestigingInNederland;
   }

   public void setDatumVestigingInNederland(final Datum datumVestigingInNederland) {
      this.datumVestigingInNederland = datumVestigingInNederland;
   }



}
