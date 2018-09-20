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
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractBestaansperiodeHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.ISO31661Alpha2;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LandID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Landcode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.NaamEnumeratiewaarde;


/**
 * Land

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractLand extends AbstractBestaansperiodeHisTabel {

   @Transient
   protected LandID id;

   @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
   protected NaamEnumeratiewaarde naam;

   @AttributeOverride(name = "waarde", column = @Column(name = "ISO31661Alpha2"))
   protected ISO31661Alpha2 iSO31661Alpha2;

   @AttributeOverride(name = "waarde", column = @Column(name = "Landcode"))
   protected Landcode landcode;


   @Id
   @SequenceGenerator(name = "seq_Land", sequenceName = "Kern.seq_Land")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Land")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new LandID();
      }
      id.setWaarde(value);
   }

   public NaamEnumeratiewaarde getNaam() {
      return naam;
   }

   public void setNaam(final NaamEnumeratiewaarde naam) {
      this.naam = naam;
   }

   public ISO31661Alpha2 getISO31661Alpha2() {
      return iSO31661Alpha2;
   }

   public void setISO31661Alpha2(final ISO31661Alpha2 iSO31661Alpha2) {
      this.iSO31661Alpha2 = iSO31661Alpha2;
   }

   public Landcode getLandcode() {
      return landcode;
   }

   public void setLandcode(final Landcode landcode) {
      this.landcode = landcode;
   }



}
