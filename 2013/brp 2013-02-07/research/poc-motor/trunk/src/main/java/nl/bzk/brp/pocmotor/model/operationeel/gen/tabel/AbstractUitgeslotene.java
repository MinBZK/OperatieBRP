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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AuthorisatiebesluitGegevenselementID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Bijhoudingsautorisatie;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;


/**
 * Uitgeslotene

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractUitgeslotene extends AbstractTabel {

   @Transient
   protected AuthorisatiebesluitGegevenselementID id;

   @ManyToOne
   @JoinColumn(name = "Bijhautorisatie")
   protected Bijhoudingsautorisatie bijhoudingsautorisatie;

   @ManyToOne
   @JoinColumn(name = "UitgeslotenPartij")
   protected Partij uitgeslotenPartij;

   @AttributeOverride(name = "waarde", column = @Column(name = "UitgesloteneStatusHis"))
   protected StatusHistorie uitgesloteneStatusHis;


   @Id
   @SequenceGenerator(name = "seq_Uitgeslotene", sequenceName = "AutAut.seq_Uitgeslotene")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Uitgeslotene")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new AuthorisatiebesluitGegevenselementID();
      }
      id.setWaarde(value);
   }

   public Bijhoudingsautorisatie getBijhoudingsautorisatie() {
      return bijhoudingsautorisatie;
   }

   public void setBijhoudingsautorisatie(final Bijhoudingsautorisatie bijhoudingsautorisatie) {
      this.bijhoudingsautorisatie = bijhoudingsautorisatie;
   }

   public Partij getUitgeslotenPartij() {
      return uitgeslotenPartij;
   }

   public void setUitgeslotenPartij(final Partij uitgeslotenPartij) {
      this.uitgeslotenPartij = uitgeslotenPartij;
   }

   public StatusHistorie getUitgesloteneStatusHis() {
      return uitgesloteneStatusHis;
   }

   public void setUitgesloteneStatusHis(final StatusHistorie uitgesloteneStatusHis) {
      this.uitgesloteneStatusHis = uitgesloteneStatusHis;
   }



}
