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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.ActieID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatumTijd;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortActie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Partij;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Verdrag;


/**
 * BRP Actie.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBRPActieIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected ActieID iD;

   @Column(name = "Srt")
   protected SoortActie soort;

   @ManyToOne
   @JoinColumn(name = "Partij")
   protected Partij partij;

   @ManyToOne
   @JoinColumn(name = "Verdrag")
   protected Verdrag verdrag;

   @AttributeOverride(name = "waarde", column = @Column(name = "TsOntlening"))
   protected DatumTijd datumTijdOntlening;

   @AttributeOverride(name = "waarde", column = @Column(name = "TsReg"))
   protected DatumTijd datumTijdRegistratie;

   @AttributeOverride(name = "waarde", column = @Column(name = "GegevensAangepast"))
   protected OmschrijvingEnumeratiewaarde gegevensAangepast;


   public ActieID getID() {
      return iD;
   }

   public void setID(final ActieID iD) {
      this.iD = iD;
   }

   public SoortActie getSoort() {
      return soort;
   }

   public void setSoort(final SoortActie soort) {
      this.soort = soort;
   }

   public Partij getPartij() {
      return partij;
   }

   public void setPartij(final Partij partij) {
      this.partij = partij;
   }

   public Verdrag getVerdrag() {
      return verdrag;
   }

   public void setVerdrag(final Verdrag verdrag) {
      this.verdrag = verdrag;
   }

   public DatumTijd getDatumTijdOntlening() {
      return datumTijdOntlening;
   }

   public void setDatumTijdOntlening(final DatumTijd datumTijdOntlening) {
      this.datumTijdOntlening = datumTijdOntlening;
   }

   public DatumTijd getDatumTijdRegistratie() {
      return datumTijdRegistratie;
   }

   public void setDatumTijdRegistratie(final DatumTijd datumTijdRegistratie) {
      this.datumTijdRegistratie = datumTijdRegistratie;
   }

   public OmschrijvingEnumeratiewaarde getGegevensAangepast() {
      return gegevensAangepast;
   }

   public void setGegevensAangepast(final OmschrijvingEnumeratiewaarde gegevensAangepast) {
      this.gegevensAangepast = gegevensAangepast;
   }



}
