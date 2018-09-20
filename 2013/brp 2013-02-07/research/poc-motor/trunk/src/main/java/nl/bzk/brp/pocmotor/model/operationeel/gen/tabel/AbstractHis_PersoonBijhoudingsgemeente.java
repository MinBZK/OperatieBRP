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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractMaterieleEnFormeleHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.His_PersoonBijhoudingsgemeenteID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.JaNee;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Partij;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;


/**
 * His Persoon Bijhoudingsgemeente

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractHis_PersoonBijhoudingsgemeente extends AbstractMaterieleEnFormeleHisTabel {

   @Transient
   protected His_PersoonBijhoudingsgemeenteID id;

   @ManyToOne
   @JoinColumn(name = "Pers")
   protected Persoon persoon;

   @ManyToOne
   @JoinColumn(name = "Bijhgem")
   protected Partij bijhoudingsgemeente;

   @AttributeOverride(name = "waarde", column = @Column(name = "DatInschrInGem"))
   protected Datum datumInschrijvingInGemeente;

   @AttributeOverride(name = "waarde", column = @Column(name = "IndOnverwDocAanw"))
   protected JaNee indicatieOnverwerktDocumentAanwezig;


   @Id
   @SequenceGenerator(name = "seq_His_PersBijhgem", sequenceName = "Kern.seq_His_PersBijhgem")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_His_PersBijhgem")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new His_PersoonBijhoudingsgemeenteID();
      }
      id.setWaarde(value);
   }

   public Persoon getPersoon() {
      return persoon;
   }

   public void setPersoon(final Persoon persoon) {
      this.persoon = persoon;
   }

   public Partij getBijhoudingsgemeente() {
      return bijhoudingsgemeente;
   }

   public void setBijhoudingsgemeente(final Partij bijhoudingsgemeente) {
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
