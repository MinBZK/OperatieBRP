/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.gedeeld.usr.enums;

/**
 * Reden opschorting
  */
public enum RedenOpschorting {

   /** Dummy value. Echte values beginnen in de database bij 1 ipv 0 */
   DUMMY(null, null),
   OVERLIJDEN("O", "Overlijden"),
   MINISTERIEEL_BESLUIT("M", "Ministerieel besluit"),
   FOUT("F", "Fout"),
   ONBEKEND("?", "Onbekend");


   private final String code;

   private final String naam;



   private RedenOpschorting(final String code, final String naam) {
      this.code = code;
      this.naam = naam;
   }


   public String getCode() {
      return code;
   }

   public String getNaam() {
      return naam;
   }



}
