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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.BijhoudingssituatieID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.CategorieSoortActie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.CategorieSoortDocument;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortActie;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Bijhoudingsautorisatie;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.SoortDocument;


/**
 * Bijhoudingssituatie

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBijhoudingssituatie extends AbstractTabel {

   @Transient
   protected BijhoudingssituatieID id;

   @ManyToOne
   @JoinColumn(name = "Bijhautorisatie")
   protected Bijhoudingsautorisatie bijhoudingsautorisatie;

   @Column(name = "CategorieSrtActie")
   protected CategorieSoortActie categorieSoortActie;

   @Column(name = "SrtActie")
   protected SoortActie soortActie;

   @Column(name = "CategorieSrtDoc")
   protected CategorieSoortDocument categorieSoortDocument;

   @ManyToOne
   @JoinColumn(name = "SrtDoc")
   protected SoortDocument soortDocument;

   @AttributeOverride(name = "waarde", column = @Column(name = "BijhsituatieStatusHis"))
   protected StatusHistorie bijhoudingssituatieStatusHis;


   @Id
   @SequenceGenerator(name = "seq_Bijhsituatie", sequenceName = "AutAut.seq_Bijhsituatie")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Bijhsituatie")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new BijhoudingssituatieID();
      }
      id.setWaarde(value);
   }

   public Bijhoudingsautorisatie getBijhoudingsautorisatie() {
      return bijhoudingsautorisatie;
   }

   public void setBijhoudingsautorisatie(final Bijhoudingsautorisatie bijhoudingsautorisatie) {
      this.bijhoudingsautorisatie = bijhoudingsautorisatie;
   }

   public CategorieSoortActie getCategorieSoortActie() {
      return categorieSoortActie;
   }

   public void setCategorieSoortActie(final CategorieSoortActie categorieSoortActie) {
      this.categorieSoortActie = categorieSoortActie;
   }

   public SoortActie getSoortActie() {
      return soortActie;
   }

   public void setSoortActie(final SoortActie soortActie) {
      this.soortActie = soortActie;
   }

   public CategorieSoortDocument getCategorieSoortDocument() {
      return categorieSoortDocument;
   }

   public void setCategorieSoortDocument(final CategorieSoortDocument categorieSoortDocument) {
      this.categorieSoortDocument = categorieSoortDocument;
   }

   public SoortDocument getSoortDocument() {
      return soortDocument;
   }

   public void setSoortDocument(final SoortDocument soortDocument) {
      this.soortDocument = soortDocument;
   }

   public StatusHistorie getBijhoudingssituatieStatusHis() {
      return bijhoudingssituatieStatusHis;
   }

   public void setBijhoudingssituatieStatusHis(final StatusHistorie bijhoudingssituatieStatusHis) {
      this.bijhoudingssituatieStatusHis = bijhoudingssituatieStatusHis;
   }



}
