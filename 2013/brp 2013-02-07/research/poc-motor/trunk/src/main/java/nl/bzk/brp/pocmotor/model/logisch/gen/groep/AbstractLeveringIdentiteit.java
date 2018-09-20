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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractGroep;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatumTijd;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.LeveringID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortLevering;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Abonnement;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Authenticatiemiddel;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Bericht;


/**
 * Levering.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractLeveringIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected LeveringID iD;

   @Column(name = "Srt")
   protected SoortLevering soort;

   @ManyToOne
   @JoinColumn(name = "Authenticatiemiddel")
   protected Authenticatiemiddel authenticatiemiddel;

   @ManyToOne
   @JoinColumn(name = "Abonnement")
   protected Abonnement abonnement;

   @AttributeOverride(name = "waarde", column = @Column(name = "TsBesch"))
   protected DatumTijd datumTijdBeschouwing;

   @AttributeOverride(name = "waarde", column = @Column(name = "TsKlaarzettenLev"))
   protected DatumTijd datumTijdKlaarzettenLevering;

   @ManyToOne
   @JoinColumn(name = "GebaseerdOp")
   protected Bericht gebaseerdOp;


   public LeveringID getID() {
      return iD;
   }

   public void setID(final LeveringID iD) {
      this.iD = iD;
   }

   public SoortLevering getSoort() {
      return soort;
   }

   public void setSoort(final SoortLevering soort) {
      this.soort = soort;
   }

   public Authenticatiemiddel getAuthenticatiemiddel() {
      return authenticatiemiddel;
   }

   public void setAuthenticatiemiddel(final Authenticatiemiddel authenticatiemiddel) {
      this.authenticatiemiddel = authenticatiemiddel;
   }

   public Abonnement getAbonnement() {
      return abonnement;
   }

   public void setAbonnement(final Abonnement abonnement) {
      this.abonnement = abonnement;
   }

   public DatumTijd getDatumTijdBeschouwing() {
      return datumTijdBeschouwing;
   }

   public void setDatumTijdBeschouwing(final DatumTijd datumTijdBeschouwing) {
      this.datumTijdBeschouwing = datumTijdBeschouwing;
   }

   public DatumTijd getDatumTijdKlaarzettenLevering() {
      return datumTijdKlaarzettenLevering;
   }

   public void setDatumTijdKlaarzettenLevering(final DatumTijd datumTijdKlaarzettenLevering) {
      this.datumTijdKlaarzettenLevering = datumTijdKlaarzettenLevering;
   }

   public Bericht getGebaseerdOp() {
      return gebaseerdOp;
   }

   public void setGebaseerdOp(final Bericht gebaseerdOp) {
      this.gebaseerdOp = gebaseerdOp;
   }



}
