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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.GegevenInOnderzoekID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Sleutelwaarde;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.DatabaseObject;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Onderzoek;


/**
 * Gegeven in onderzoek

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractGegevenInOnderzoek extends AbstractTabel {

   @Transient
   protected GegevenInOnderzoekID id;

   @ManyToOne
   @JoinColumn(name = "Onderzoek")
   protected Onderzoek onderzoek;

   @ManyToOne
   @JoinColumn(name = "SrtGegeven")
   protected DatabaseObject soortGegeven;

   @AttributeOverride(name = "waarde", column = @Column(name = "Ident"))
   protected Sleutelwaarde identificatie;


   @Id
   @SequenceGenerator(name = "seq_GegevenInOnderzoek", sequenceName = "Kern.seq_GegevenInOnderzoek")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_GegevenInOnderzoek")
   @Access(AccessType.PROPERTY)
   public Long getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Long value) {
      if (id == null) {
          id = new GegevenInOnderzoekID();
      }
      id.setWaarde(value);
   }

   public Onderzoek getOnderzoek() {
      return onderzoek;
   }

   public void setOnderzoek(final Onderzoek onderzoek) {
      this.onderzoek = onderzoek;
   }

   public DatabaseObject getSoortGegeven() {
      return soortGegeven;
   }

   public void setSoortGegeven(final DatabaseObject soortGegeven) {
      this.soortGegeven = soortGegeven;
   }

   public Sleutelwaarde getIdentificatie() {
      return identificatie;
   }

   public void setIdentificatie(final Sleutelwaarde identificatie) {
      this.identificatie = identificatie;
   }



}
