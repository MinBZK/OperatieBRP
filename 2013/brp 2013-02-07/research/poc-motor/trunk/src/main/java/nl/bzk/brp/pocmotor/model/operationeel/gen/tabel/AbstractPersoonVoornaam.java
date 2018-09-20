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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PersoonVoornaamID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Volgnummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Voornaam;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;


/**
 * Persoon \ Voornaam

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonVoornaam extends AbstractTabel {

   @Transient
   protected PersoonVoornaamID id;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @AttributeOverride(name = "waarde", column = @Column(name = "Volgnr"))
   protected Volgnummer volgnummer;

   @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
   protected Voornaam naam;

   @AttributeOverride(name = "waarde", column = @Column(name = "PersVoornaamStatusHis"))
   protected StatusHistorie persoonVoornaamStatusHis;


   @Id
   @SequenceGenerator(name = "seq_PersVoornaam", sequenceName = "Kern.seq_PersVoornaam")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_PersVoornaam")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new PersoonVoornaamID();
      }
      id.setWaarde(value);
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

   public Volgnummer getVolgnummer() {
      return volgnummer;
   }

   public void setVolgnummer(final Volgnummer volgnummer) {
      this.volgnummer = volgnummer;
   }

   public Voornaam getNaam() {
      return naam;
   }

   public void setNaam(final Voornaam naam) {
      this.naam = naam;
   }

   public StatusHistorie getPersoonVoornaamStatusHis() {
      return persoonVoornaamStatusHis;
   }

   public void setPersoonVoornaamStatusHis(final StatusHistorie persoonVoornaamStatusHis) {
      this.persoonVoornaamStatusHis = persoonVoornaamStatusHis;
   }



}
