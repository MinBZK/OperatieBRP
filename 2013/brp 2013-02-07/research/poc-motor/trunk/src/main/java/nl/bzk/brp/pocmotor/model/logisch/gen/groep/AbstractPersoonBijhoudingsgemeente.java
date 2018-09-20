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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Gemeente;


/**
 * Persoon.Bijhoudingsgemeente

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonBijhoudingsgemeente extends AbstractGroep {

   @ManyToOne
   @JoinColumn(name = "Bijhgem")
   protected Gemeente bijhoudingsgemeente;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatInschrInGem"))
   protected Datum datumInschrijvingInGemeente;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndOnverwDocAanw"))
   protected JaNee indicatieOnverwerktDocumentAanwezig;


   public Gemeente getBijhoudingsgemeente() {
      return bijhoudingsgemeente;
   }

   public void setBijhoudingsgemeente(final Gemeente bijhoudingsgemeente) {
      this.bijhoudingsgemeente = bijhoudingsgemeente;
   }

   public Datum getDatumInschrijvingInGemeente() {
      return datumInschrijvingInGemeente;
   }

   public void setDatumInschrijvingInGemeente(final Datum datumInschrijvingInGemeente) {
      this.datumInschrijvingInGemeente = datumInschrijvingInGemeente;
   }

   public JaNee getIndicatieOnverwerktDocumentAanwezig() {
      return indicatieOnverwerktDocumentAanwezig;
   }

   public void setIndicatieOnverwerktDocumentAanwezig(final JaNee indicatieOnverwerktDocumentAanwezig) {
      this.indicatieOnverwerktDocumentAanwezig = indicatieOnverwerktDocumentAanwezig;
   }



}
