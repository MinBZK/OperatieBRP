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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.NaamEnumeratiewaarde;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.NationaliteitID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Nationaliteitcode;


/**
 * Nationaliteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractNationaliteit extends AbstractBestaansperiodeHisTabel {

   @Transient
   protected NationaliteitID id;

   @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
   protected NaamEnumeratiewaarde naam;

   @AttributeOverride(name = "waarde", column = @Column(name = "Nationcode"))
   protected Nationaliteitcode nationaliteitcode;


   @Id
   @SequenceGenerator(name = "seq_Nation", sequenceName = "Kern.seq_Nation")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Nation")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new NationaliteitID();
      }
      id.setWaarde(value);
   }

   public NaamEnumeratiewaarde getNaam() {
      return naam;
   }

   public void setNaam(final NaamEnumeratiewaarde naam) {
      this.naam = naam;
   }

   public Nationaliteitcode getNationaliteitcode() {
      return nationaliteitcode;
   }

   public void setNationaliteitcode(final Nationaliteitcode nationaliteitcode) {
      this.nationaliteitcode = nationaliteitcode;
   }



}
