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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractFormeleHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_AbonnementID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Populatiecriterium;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Abonnement;


/**
 * His Abonnement

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_Abonnement extends AbstractFormeleHisTabel {

   @Transient
   protected His_AbonnementID id;

   @ManyToOne
   @JoinColumn(name = "Abonnement")
   protected Abonnement abonnement;

   @AttributeOverride(name = "waarde", column = @Column(name = "Populatiecriterium"))
   protected Populatiecriterium populatiecriterium;


   @Id
   @SequenceGenerator(name = "seq_His_Abonnement", sequenceName = "Lev.seq_His_Abonnement")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_Abonnement")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_AbonnementID();
      }
      id.setWaarde(value);
   }

   public Abonnement getAbonnement() {
      return abonnement;
   }

   public void setAbonnement(final Abonnement abonnement) {
      this.abonnement = abonnement;
   }

   public Populatiecriterium getPopulatiecriterium() {
      return populatiecriterium;
   }

   public void setPopulatiecriterium(final Populatiecriterium populatiecriterium) {
      this.populatiecriterium = populatiecriterium;
   }



}
