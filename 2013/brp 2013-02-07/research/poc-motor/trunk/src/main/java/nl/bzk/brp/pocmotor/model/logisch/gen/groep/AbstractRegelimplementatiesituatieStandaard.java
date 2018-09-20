/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.gen.groep;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.RedenOpschorting;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Regeleffect;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.Verantwoordelijke;


/**
 * Regelimplementatiesituatie.Standaard

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractRegelimplementatiesituatieStandaard extends AbstractGroep {

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
