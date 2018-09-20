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
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Verblijfsrecht;


/**
 * Persoon.Verblijfsrecht

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonVerblijfsrecht extends AbstractGroep {

   @ManyToOne
   @JoinColumn(name = "Verblijfsr")
   protected Verblijfsrecht verblijfsrecht;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvVerblijfsr"))
   protected Datum datumAanvangVerblijfsrecht;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatVoorzEindeVerblijfsr"))
   protected Datum datumVoorzienEindeVerblijfsrecht;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvAaneenslVerblijfsr"))
   protected Datum datumAanvangAaneensluitendVerblijfsrecht;


   public Verblijfsrecht getVerblijfsrecht() {
      return verblijfsrecht;
   }

   public void setVerblijfsrecht(final Verblijfsrecht verblijfsrecht) {
      this.verblijfsrecht = verblijfsrecht;
   }

   public Datum getDatumAanvangVerblijfsrecht() {
      return datumAanvangVerblijfsrecht;
   }

   public void setDatumAanvangVerblijfsrecht(final Datum datumAanvangVerblijfsrecht) {
      this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
   }

   public Datum getDatumVoorzienEindeVerblijfsrecht() {
      return datumVoorzienEindeVerblijfsrecht;
   }

   public void setDatumVoorzienEindeVerblijfsrecht(final Datum datumVoorzienEindeVerblijfsrecht) {
      this.datumVoorzienEindeVerblijfsrecht = datumVoorzienEindeVerblijfsrecht;
   }

   public Datum getDatumAanvangAaneensluitendVerblijfsrecht() {
      return datumAanvangAaneensluitendVerblijfsrecht;
   }

   public void setDatumAanvangAaneensluitendVerblijfsrecht(final Datum datumAanvangAaneensluitendVerblijfsrecht) {
      this.datumAanvangAaneensluitendVerblijfsrecht = datumAanvangAaneensluitendVerblijfsrecht;
   }



}
