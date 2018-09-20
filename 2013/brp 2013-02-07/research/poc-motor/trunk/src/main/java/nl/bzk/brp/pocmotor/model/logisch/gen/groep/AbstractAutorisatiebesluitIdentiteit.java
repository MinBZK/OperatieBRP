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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.AutorisatiebesluitID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.TekstUitBesluit;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortAutorisatiebesluit;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Autorisatiebesluit;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Partij;


/**
 * Autorisatiebesluit.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractAutorisatiebesluitIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected AutorisatiebesluitID iD;

   @Column(name = "Srt")
   protected SoortAutorisatiebesluit soort;

   @AttributeOverride(name = "waarde", column = @Column(name = "Besluittekst"))
   protected TekstUitBesluit besluittekst;

   @ManyToOne
   @JoinColumn(name = "Autoriseerder")
   protected Partij autoriseerder;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndModelBesluit"))
   protected JaNee indicatieModelBesluit;

   @ManyToOne
   @JoinColumn(name = "GebaseerdOp")
   protected Autorisatiebesluit gebaseerdOp;


   public AutorisatiebesluitID getID() {
      return iD;
   }

   public void setID(final AutorisatiebesluitID iD) {
      this.iD = iD;
   }

   public SoortAutorisatiebesluit getSoort() {
      return soort;
   }

   public void setSoort(final SoortAutorisatiebesluit soort) {
      this.soort = soort;
   }

   public TekstUitBesluit getBesluittekst() {
      return besluittekst;
   }

   public void setBesluittekst(final TekstUitBesluit besluittekst) {
      this.besluittekst = besluittekst;
   }

   public Partij getAutoriseerder() {
      return autoriseerder;
   }

   public void setAutoriseerder(final Partij autoriseerder) {
      this.autoriseerder = autoriseerder;
   }

   public JaNee getIndicatieModelBesluit() {
      return indicatieModelBesluit;
   }

   public void setIndicatieModelBesluit(final JaNee indicatieModelBesluit) {
      this.indicatieModelBesluit = indicatieModelBesluit;
   }

   public Autorisatiebesluit getGebaseerdOp() {
      return gebaseerdOp;
   }

   public void setGebaseerdOp(final Autorisatiebesluit gebaseerdOp) {
      this.gebaseerdOp = gebaseerdOp;
   }



}
