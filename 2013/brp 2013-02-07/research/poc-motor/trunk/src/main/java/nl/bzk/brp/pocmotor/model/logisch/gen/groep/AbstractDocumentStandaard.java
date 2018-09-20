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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Aktenummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DocumentIdentificatie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DocumentOmschrijving;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Partij;


/**
 * Document.Standaard

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractDocumentStandaard extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "Ident"))
   protected DocumentIdentificatie identificatie;

   @AttributeOverride(name = "waarde", column = @Column(name = "Aktenr"))
   protected Aktenummer aktenummer;

   @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
   protected DocumentOmschrijving omschrijving;

   @ManyToOne
   @JoinColumn(name = "Partij")
   protected Partij partij;


   public DocumentIdentificatie getIdentificatie() {
      return identificatie;
   }

   public void setIdentificatie(final DocumentIdentificatie identificatie) {
      this.identificatie = identificatie;
   }

   public Aktenummer getAktenummer() {
      return aktenummer;
   }

   public void setAktenummer(final Aktenummer aktenummer) {
      this.aktenummer = aktenummer;
   }

   public DocumentOmschrijving getOmschrijving() {
      return omschrijving;
   }

   public void setOmschrijving(final DocumentOmschrijving omschrijving) {
      this.omschrijving = omschrijving;
   }

   public Partij getPartij() {
      return partij;
   }

   public void setPartij(final Partij partij) {
      this.partij = partij;
   }



}
