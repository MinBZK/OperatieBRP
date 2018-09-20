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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_PersoonVoornaamID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Voornaam;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonVoornaam;


/**
 * His Persoon \ Voornaam

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_PersoonVoornaam extends AbstractMaterieleEnFormeleHisTabel {

   @Transient
   protected His_PersoonVoornaamID id;

   @ManyToOne
   @JoinColumn(name = "PersVoornaam")
   protected PersoonVoornaam persoonVoornaam;

   @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
   protected Voornaam naam;


   @Id
   @SequenceGenerator(name = "seq_His_PersVoornaam", sequenceName = "Kern.seq_His_PersVoornaam")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_PersVoornaam")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_PersoonVoornaamID();
      }
      id.setWaarde(value);
   }

   public PersoonVoornaam getPersoonVoornaam() {
      return persoonVoornaam;
   }

   public void setPersoonVoornaam(final PersoonVoornaam persoonVoornaam) {
      this.persoonVoornaam = persoonVoornaam;
   }

   public Voornaam getNaam() {
      return naam;
   }

   public void setNaam(final Voornaam naam) {
      this.naam = naam;
   }



}
