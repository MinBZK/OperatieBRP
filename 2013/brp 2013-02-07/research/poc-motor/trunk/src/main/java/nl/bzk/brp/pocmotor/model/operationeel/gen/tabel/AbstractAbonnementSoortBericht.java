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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AbonnementSoortBerichtID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortBericht;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Abonnement;


/**
 * Abonnement \ Soort bericht

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractAbonnementSoortBericht extends AbstractTabel {

   @Transient
   protected AbonnementSoortBerichtID id;

   @ManyToOne
   @JoinColumn(name = "Abonnement")
   protected Abonnement abonnement;

   @Column(name = "SrtBer")
   protected SoortBericht soortBericht;

   @AttributeOverride(name = "waarde", column = @Column(name = "AbonnementSrtBerStatusHis"))
   protected StatusHistorie abonnementSoortBerichtStatusHis;


   @Id
   @SequenceGenerator(name = "seq_AbonnementSrtBer", sequenceName = "Lev.seq_AbonnementSrtBer")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_AbonnementSrtBer")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new AbonnementSoortBerichtID();
      }
      id.setWaarde(value);
   }

   public Abonnement getAbonnement() {
      return abonnement;
   }

   public void setAbonnement(final Abonnement abonnement) {
      this.abonnement = abonnement;
   }

   public SoortBericht getSoortBericht() {
      return soortBericht;
   }

   public void setSoortBericht(final SoortBericht soortBericht) {
      this.soortBericht = soortBericht;
   }

   public StatusHistorie getAbonnementSoortBerichtStatusHis() {
      return abonnementSoortBerichtStatusHis;
   }

   public void setAbonnementSoortBerichtStatusHis(final StatusHistorie abonnementSoortBerichtStatusHis) {
      this.abonnementSoortBerichtStatusHis = abonnementSoortBerichtStatusHis;
   }



}
