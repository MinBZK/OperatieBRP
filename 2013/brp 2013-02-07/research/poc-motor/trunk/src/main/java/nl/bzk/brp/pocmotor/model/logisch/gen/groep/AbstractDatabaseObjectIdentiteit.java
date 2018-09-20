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
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatabaseObjectID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.IdentifierLang;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.NaamEnumeratiewaarde;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortDatabaseObject;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.DatabaseObject;


/**
 * Database object.Identiteit

 * Generated Abstract Class
  */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractDatabaseObjectIdentiteit extends AbstractGroep {

   @AttributeOverride(name = "waarde", column = @Column(name = "id", insertable = false, updatable = false))
   protected DatabaseObjectID iD;

   @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
   protected NaamEnumeratiewaarde naam;

   @Column(name = "Srt")
   protected SoortDatabaseObject soort;

   @ManyToOne
   @JoinColumn(name = "Ouder")
   protected DatabaseObject ouder;

   @AttributeOverride(name = "waarde", column = @Column(name = "JavaIdentifier"))
   protected IdentifierLang javaIdentifier;


   public DatabaseObjectID getID() {
      return iD;
   }

   public void setID(final DatabaseObjectID iD) {
      this.iD = iD;
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
