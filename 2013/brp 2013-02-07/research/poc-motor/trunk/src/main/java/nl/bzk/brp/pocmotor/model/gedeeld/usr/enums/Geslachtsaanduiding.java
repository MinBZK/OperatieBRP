/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.gedeeld.usr.enums;

/**
 * Geslachtsaanduiding
  */
public enum Geslachtsaanduiding {

   /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
   DUMMY(null, null, null),
   MAN("M", "Man", ""),
   VROUW("V", "Vrouw", ""),
   ONBEKEND("O", "Onbekend", "");


   private final String code;

   private final String naam;

   private final String omschrijving;



   private Geslachtsaanduiding(final String code, final String naam, final String omschrijving) {
      this.code = code;
      this.naam = naam;
      this.omschrijving = omschrijving;
   }


   public String getCode() {
      return code;
   }

   public String getNaam() {
      return naam;
   }

   public String getOmschrijving() {
      return omschrijving;
   }



}
