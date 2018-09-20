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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_RegelimplementatiesituatieID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.RedenOpschorting;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Regeleffect;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Verantwoordelijke;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Regelimplementatiesituatie;


/**
 * His Regelimplementatiesituatie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_Regelimplementatiesituatie extends AbstractFormeleHisTabel {

   @Transient
   protected His_RegelimplementatiesituatieID id;

   @ManyToOne
   @JoinColumn(name = "Regelimplementatiesituatie")
   protected Regelimplementatiesituatie regelimplementatiesituatie;

   @Column(name = "Bijhverantwoordelijkheid")
   protected Verantwoordelijke bijhoudingsverantwoordelijkheid;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndOpgeschort"))
   protected JaNee indicatieOpgeschort;

   @Column(name = "RdnOpschorting")
   protected RedenOpschorting redenOpschorting;

   @Column(name = "Effect")
   protected Regeleffect effect;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndActief"))
   protected JaNee indicatieActief;


   @Id
   @SequenceGenerator(name = "seq_His_Regelimplementatiesituat", sequenceName = "BRM.seq_His_Regelimplementatiesituat")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_Regelimplementatiesituat")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_RegelimplementatiesituatieID();
      }
      id.setWaarde(value);
   }

   public Regelimplementatiesituatie getRegelimplementatiesituatie() {
      return regelimplementatiesituatie;
   }

   public void setRegelimplementatiesituatie(final Regelimplementatiesituatie regelimplementatiesituatie) {
      this.regelimplementatiesituatie = regelimplementatiesituatie;
   }

   public Verantwoordelijke getBijhoudingsverantwoordelijkheid() {
      return bijhoudingsverantwoordelijkheid;
   }

   public void setBijhoudingsverantwoordelijkheid(final Verantwoordelijke bijhoudingsverantwoordelijkheid) {
      this.bijhoudingsverantwoordelijkheid = bijhoudingsverantwoordelijkheid;
   }

   public JaNee getIndicatieOpgeschort() {
      return indicatieOpgeschort;
   }

   public void setIndicatieOpgeschort(final JaNee indicatieOpgeschort) {
      this.indicatieOpgeschort = indicatieOpgeschort;
   }

   public RedenOpschorting getRedenOpschorting() {
      return redenOpschorting;
   }

   public void setRedenOpschorting(final RedenOpschorting redenOpschorting) {
      this.redenOpschorting = redenOpschorting;
   }

   public Regeleffect getEffect() {
      return effect;
   }

   public void setEffect(final Regeleffect effect) {
      this.effect = effect;
   }

   public JaNee getIndicatieActief() {
      return indicatieActief;
   }

   public void setIndicatieActief(final JaNee indicatieActief) {
      this.indicatieActief = indicatieActief;
   }



}
