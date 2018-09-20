/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.service;

import java.util.List;
import nl.bzk.brp.blobifier.exceptie.NietUniekeAnummerExceptie;
import nl.bzk.brp.blobifier.exceptie.NietUniekeBsnExceptie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;

/**
 * De service die het serialiseren en deserialiseren van een persoon mogelijk maakt.
 */
public interface BlobifierService {

    /**
     * Maak een 'afdruk' van de persoon en al zijn historie en sla deze op als blob in de database.
     *
     * @param persoonHisVolledig                 Het persoonHisVolledig object die geserialiseerd moet worden.
     * @param blobifyNietIngeschrevenBetrokkenen Indien actief dan wordt er ook voor de niet ingeschreven betrokkenen van de persoon een 'afdruk'
     *                                           opgeslagen.
     */
    void blobify(PersoonHisVolledigImpl persoonHisVolledig, boolean blobifyNietIngeschrevenBetrokkenen);

    /**
     * Haal een persoon inclusief alle historie op uit de database en maak een 'afdruk' en sla die op in de persoon cache.
     *
     * @param technischId                        De technisch id waarvan de persoon 'afdruk' moet worden gemaakt.
     * @param blobifyNietIngeschrevenBetrokkenen Indien actief dan wordt er ook voor de niet ingeschreven betrokkenen van de persoon een 'afdruk'
     *                                           opgeslagen.
     */
    void blobify(Integer technischId, boolean blobifyNietIngeschrevenBetrokkenen);

    /**
     * Haal een persoon inclusief alle historie op uit de database en maak een 'afdruk' en sla die op in de persoon cache.
     *
     * @param burgerservicenummer                De BSN waarvan de persoon 'afdruk' moet worden gemaakt.
     * @param blobifyNietIngeschrevenBetrokkenen Indien actief dan wordt er ook voor de niet ingeschreven betrokkenen van de persoon een 'afdruk'
     *                                           opgeslagen.
     * @throws NietUniekeBsnExceptie niet unieke bsn exceptie
     */
    void blobify(BurgerservicenummerAttribuut burgerservicenummer, boolean blobifyNietIngeschrevenBetrokkenen) throws NietUniekeBsnExceptie;

    /**
     * Haal een persoon inclusief alle historie op uit de database en maak een 'afdruk' en sla die op in de persoon cache.
     *
     * @param administratienummer                Het A-nummer waarvan de persoon 'afdruk' moet worden gemaakt.
     * @param blobifyNietIngeschrevenBetrokkenen Indien actief dan wordt er ook voor de niet ingeschreven betrokkenen van de persoon een 'afdruk'
     *                                           opgeslagen.
     * @throws NietUniekeAnummerExceptie niet unieke anummer exceptie
     */
    void blobify(AdministratienummerAttribuut administratienummer, boolean blobifyNietIngeschrevenBetrokkenen) throws NietUniekeAnummerExceptie;

    /**
     * Lees de 'afdruk' van een persoon in.
     *
     * @param technischId Het technisch id van de persoon waarvan de blob uit de persooncache moet worden opgehaald en gedeserialiseerd.
     * @return volledig Persoon
     */
    PersoonHisVolledigImpl leesBlob(Integer technischId);

    /**
     * Lees de 'afdruk' van een persoon in.
     *
     * @param burgerservicenummer   Het BSN waarvan de blob uit de persooncache moet worden opgehaald en gedeserialiseerd.
     * @return volledig Persoon
     * @throws NietUniekeBsnExceptie niet unieke bsn exceptie
     */
    PersoonHisVolledigImpl leesBlob(BurgerservicenummerAttribuut burgerservicenummer) throws NietUniekeBsnExceptie;

    /**
     * Lees de 'afdruk' van een actieve persoon in.
     *
     * @param burgerservicenummer   Het BSN waarvan de blob uit de persooncache moet worden opgehaald en gedeserialiseerd.
     * @return volledig Persoon
     * @throws NietUniekeBsnExceptie niet unieke bsn exceptie
     */
    PersoonHisVolledigImpl leesBlobActievePersoon(BurgerservicenummerAttribuut burgerservicenummer) throws
        NietUniekeBsnExceptie;

    /**
     * Lees de 'afdruk' van een persoon in.
     *
     * @param administratienummer Het A-nummer waarvan de blob uit de persooncache moet worden opgehaald en gedeserialiseerd.
     * @return volledig Persoon
     * @throws NietUniekeAnummerExceptie niet unieke anummer exceptie
     */
    PersoonHisVolledigImpl leesBlob(AdministratienummerAttribuut administratienummer) throws NietUniekeAnummerExceptie;

    /**
     * Lees de 'afdruk' van een aantal personen in.
     *
     * @param technischeIds De technisch ids van de personen waarvan de blob uit de persooncache moet worden opgehaald en gedeserialiseerd.
     * @return Een lijst van persoon his volledigs.
     */
    List<PersoonHisVolledigImpl> leesBlobs(final List<Integer> technischeIds);

}
