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
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Sector;


/**
 * Partij.Standaard

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPartijStandaard extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "DatAanv"))
   protected Datum datumAanvang;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
   protected Datum datumEinde;

   @ManyToOne
   @JoinColumn(name = "Sector")
   protected Sector sector;


   public Datum getDatumAanvang() {
      return datumAanvang;
   }

   public void setDatumAanvang(final Datum datumAanvang) {
      this.datumAanvang = datumAanvang;
   }

   public Datum getDatumEinde() {
      return datumEinde;
   }

   public void setDatumEinde(final Datum datumEinde) {
      this.datumEinde = datumEinde;
   }

   public Sector getSector() {
      return sector;
   }

   public void setSector(final Sector sector) {
      this.sector = sector;
   }



}
