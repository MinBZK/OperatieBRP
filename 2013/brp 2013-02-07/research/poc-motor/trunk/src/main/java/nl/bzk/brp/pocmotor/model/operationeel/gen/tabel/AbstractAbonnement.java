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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AbonnementID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Populatiecriterium;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortAbonnement;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Doelbinding;


/**
 * Abonnement

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractAbonnement extends AbstractTabel {

   @Transient
   protected AbonnementID id;

   @ManyToOne
   @JoinColumn(name = "Doelbinding")
   protected Doelbinding doelbinding;

   @Column(name = "SrtAbonnement")
   protected SoortAbonnement soortAbonnement;

   @AttributeOverride(name = "waarde", column = @Column(name = "Populatiecriterium"))
   protected Populatiecriterium populatiecriterium;

   @AttributeOverride(name = "waarde", column = @Column(name = "AbonnementStatusHis"))
   protected StatusHistorie abonnementStatusHis;


   @Id
   @SequenceGenerator(name = "seq_Abonnement", sequenceName = "Lev.seq_Abonnement")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Abonnement")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new AbonnementID();
      }
      id.setWaarde(value);
   }

   public Doelbinding getDoelbinding() {
      return doelbinding;
   }

   public void setDoelbinding(final Doelbinding doelbinding) {
      this.doelbinding = doelbinding;
   }

   public SoortAbonnement getSoortAbonnement() {
      return soortAbonnement;
   }

   public void setSoortAbonnement(final SoortAbonnement soortAbonnement) {
      this.soortAbonnement = soortAbonnement;
   }

   public Populatiecriterium getPopulatiecriterium() {
      return populatiecriterium;
   }

   public void setPopulatiecriterium(final Populatiecriterium populatiecriterium) {
      this.populatiecriterium = populatiecriterium;
   }

   public StatusHistorie getAbonnementStatusHis() {
      return abonnementStatusHis;
   }

   public void setAbonnementStatusHis(final StatusHistorie abonnementStatusHis) {
      this.abonnementStatusHis = abonnementStatusHis;
   }



}
