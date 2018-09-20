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

import nl.bzk.brp.pocmotor.model.basis.impl.AbstractBestaansperiodeHisTabel;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatabaseObjectID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.IdentifierLang;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.NaamEnumeratiewaarde;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortDatabaseObject;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.DatabaseObject;


/**
 * Database object

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractDatabaseObject extends AbstractBestaansperiodeHisTabel {

   @Transient
   protected DatabaseObjectID id;

   @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
   protected NaamEnumeratiewaarde naam;

   @Column(name = "Srt")
   protected SoortDatabaseObject soort;

   @ManyToOne
   @JoinColumn(name = "Ouder")
   protected DatabaseObject ouder;

   @AttributeOverride(name = "waarde", column = @Column(name = "JavaIdentifier"))
   protected IdentifierLang javaIdentifier;


   @Id
   @SequenceGenerator(name = "seq_DbObject", sequenceName = "Kern.seq_DbObject")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_DbObject")
   @Access(AccessType.PROPERTY)
   public Integer getId() {
      if (id != null) {
         return id.getWaarde();
      }
      return null;
   }

   public void setId(final Integer value) {
      if (id == null) {
          id = new DatabaseObjectID();
      }
      id.setWaarde(value);
   }

   public NaamEnumeratiewaarde getNaam() {
      return naam;
   }

   public void setNaam(final NaamEnumeratiewaarde naam) {
      this.naam = naam;
   }

   public SoortDatabaseObject getSoort() {
      return soort;
   }

   public void setSoort(final SoortDatabaseObject soort) {
      this.soort = soort;
   }

   public DatabaseObject getOuder() {
      return ouder;
   }

   public void setOuder(final DatabaseObject ouder) {
      this.ouder = ouder;
   }

   public IdentifierLang getJavaIdentifier() {
      return javaIdentifier;
   }

   public void setJavaIdentifier(final IdentifierLang javaIdentifier) {
      this.javaIdentifier = javaIdentifier;
   }



}
