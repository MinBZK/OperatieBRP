/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.operationeel.gen.tabel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractMaterieleEnFormeleHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_PersoonVerblijfsrechtID;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Verblijfsrecht;


/**
 * His Persoon Verblijfsrecht

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_PersoonVerblijfsrecht extends AbstractMaterieleEnFormeleHisTabel {

   @Transient
   protected His_PersoonVerblijfsrechtID id;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @ManyToOne
   @JoinColumn(name = "Verblijfsr")
   protected Verblijfsrecht verblijfsrecht;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvVerblijfsr"))
   protected Datum datumAanvangVerblijfsrecht;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatVoorzEindeVerblijfsr"))
   protected Datum datumVoorzienEindeVerblijfsrecht;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatAanvAaneenslVerblijfsr"))
   protected Datum datumAanvangAaneensluitendVerblijfsrecht;


   @Id
   @SequenceGenerator(name = "seq_His_PersVerblijfsr", sequenceName = "Kern.seq_His_PersVerblijfsr")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_PersVerblijfsr")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_PersoonVerblijfsrechtID();
      }
      id.setWaarde(value);
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

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
