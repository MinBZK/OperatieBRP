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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractBestaansperiodeHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.ElementID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LangeNaamEnumeratiewaarde;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortElement;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Element;


/**
 * Element

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractElement extends AbstractBestaansperiodeHisTabel {

   @Transient
   protected ElementID id;

   @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
   protected LangeNaamEnumeratiewaarde naam;

   @Column(name = "Srt")
   protected SoortElement soort;

   @ManyToOne
   @JoinColumn(name = "Ouder")
   protected Element ouder;


   @Id
   @SequenceGenerator(name = "seq_Element", sequenceName = "Kern.seq_Element")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Element")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new ElementID();
      }
      id.setWaarde(value);
   }

   public LangeNaamEnumeratiewaarde getNaam() {
      return naam;
   }

   public void setNaam(final LangeNaamEnumeratiewaarde naam) {
      this.naam = naam;
   }

   public SoortElement getSoort() {
      return soort;
   }

   public void setSoort(final SoortElement soort) {
      this.soort = soort;
   }

   public Element getOuder() {
      return ouder;
   }

   public void setOuder(final Element ouder) {
      this.ouder = ouder;
   }



}
