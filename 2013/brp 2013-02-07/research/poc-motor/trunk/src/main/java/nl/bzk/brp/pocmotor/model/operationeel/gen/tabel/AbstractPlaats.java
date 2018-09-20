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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PlaatsID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Woonplaatscode;


/**
 * Plaats

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPlaats extends AbstractBestaansperiodeHisTabel {

   @Transient
   protected PlaatsID id;

   @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
   protected NaamEnumeratiewaarde naam;

   @AttributeOverride(name = "waarde", column = @Column(name = "Wplcode"))
   protected Woonplaatscode woonplaatscode;


   @Id
   @SequenceGenerator(name = "seq_Plaats", sequenceName = "Kern.seq_Plaats")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Plaats")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new PlaatsID();
      }
      id.setWaarde(value);
   }

   public NaamEnumeratiewaarde getNaam() {
      return naam;
   }

   public void setNaam(final NaamEnumeratiewaarde naam) {
      this.naam = naam;
   }

   public Woonplaatscode getWoonplaatscode() {
      return woonplaatscode;
   }

   public void setWoonplaatscode(final Woonplaatscode woonplaatscode) {
      this.woonplaatscode = woonplaatscode;
   }



}
