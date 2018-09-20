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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.MultiRealiteitregelID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortMultiRealiteitRegel;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Betrokkenheid;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Relatie;


/**
 * Multi-realiteit regel

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractMultiRealiteitRegel extends AbstractTabel {

   @Transient
   protected MultiRealiteitregelID id;

   @ManyToOne
   @JoinColumn(name = "GeldigVoor")
   protected Persoon geldigVoor;

   @Column(name = "Srt")
   protected SoortMultiRealiteitRegel soort;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @ManyToOne
   @JoinColumn(name = "MultiRealiteitPers")
   protected Persoon multiRealiteitPersoon;

   @ManyToOne
   @JoinColumn(name = "Relatie")
   protected Relatie relatie;

   @ManyToOne
   @JoinColumn(name = "Betr")
   protected Betrokkenheid betrokkenheid;

   @AttributeOverride(name = "waarde", column = @Column(name = "MultiRealiteitRegelStatusHis"))
   protected StatusHistorie multiRealiteitRegelStatusHis;


   @Id
   @SequenceGenerator(name = "seq_MultiRealiteitRegel", sequenceName = "Kern.seq_MultiRealiteitRegel")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_MultiRealiteitRegel")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new MultiRealiteitregelID();
      }
      id.setWaarde(value);
   }

   public Persoon getGeldigVoor() {
      return geldigVoor;
   }

   public void setGeldigVoor(final Persoon geldigVoor) {
      this.geldigVoor = geldigVoor;
   }

   public SoortMultiRealiteitRegel getSoort() {
      return soort;
   }

   public void setSoort(final SoortMultiRealiteitRegel soort) {
      this.soort = soort;
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

   public Persoon getMultiRealiteitPersoon() {
      return multiRealiteitPersoon;
   }

   public void setMultiRealiteitPersoon(final Persoon multiRealiteitPersoon) {
      this.multiRealiteitPersoon = multiRealiteitPersoon;
   }

   public Relatie getRelatie() {
      return relatie;
   }

   public void setRelatie(final Relatie relatie) {
      this.relatie = relatie;
   }

   public Betrokkenheid getBetrokkenheid() {
      return betrokkenheid;
   }

   public void setBetrokkenheid(final Betrokkenheid betrokkenheid) {
      this.betrokkenheid = betrokkenheid;
   }

   public StatusHistorie getMultiRealiteitRegelStatusHis() {
      return multiRealiteitRegelStatusHis;
   }

   public void setMultiRealiteitRegelStatusHis(final StatusHistorie multiRealiteitRegelStatusHis) {
      this.multiRealiteitRegelStatusHis = multiRealiteitRegelStatusHis;
   }



}
