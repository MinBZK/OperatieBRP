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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.RegeleffectID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.RedenOpschorting;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Regeleffect;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Verantwoordelijke;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Regelimplementatie;


/**
 * Regelimplementatiesituatie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractRegelimplementatiesituatie extends AbstractTabel {

   @Transient
   protected RegeleffectID id;

   @ManyToOne
   @JoinColumn(name = "Regelimplementatie")
   protected Regelimplementatie regelimplementatie;

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

   @AttributeOverride(name = "waarde", column = @Column(name = "RegelimplementatiesituatieSt"))
   protected StatusHistorie regelimplementatiesituatieStatusHis;


   @Id
   @SequenceGenerator(name = "seq_Regelimplementatiesituatie", sequenceName = "BRM.seq_Regelimplementatiesituatie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Regelimplementatiesituatie")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new RegeleffectID();
      }
      id.setWaarde(value);
   }

   public Regelimplementatie getRegelimplementatie() {
      return regelimplementatie;
   }

   public void setRegelimplementatie(final Regelimplementatie regelimplementatie) {
      this.regelimplementatie = regelimplementatie;
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

   public StatusHistorie getRegelimplementatiesituatieStatusHis() {
      return regelimplementatiesituatieStatusHis;
   }

   public void setRegelimplementatiesituatieStatusHis(final StatusHistorie regelimplementatiesituatieStatusHis) {
      this.regelimplementatiesituatieStatusHis = regelimplementatiesituatieStatusHis;
   }



}
