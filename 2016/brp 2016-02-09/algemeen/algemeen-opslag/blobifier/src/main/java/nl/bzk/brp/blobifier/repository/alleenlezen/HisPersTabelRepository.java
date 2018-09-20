/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.alleenlezen;

import nl.bzk.brp.blobifier.exceptie.NietUniekeAnummerExceptie;
import nl.bzk.brp.blobifier.exceptie.NietUniekeBsnExceptie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;


/**
 * Repository voor de {@link nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig} class.
 */
public interface HisPersTabelRepository {

    /**
     * Leest een {@link nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig} direct uit de database. Deze persoon kan gebruikt worden voor een in-memory
     * blob.
     *
     * @param id technische sleutel van persoonHisVolledig
     * @return een instantie van PersoonHisVolledig
     */
    PersoonHisVolledigImpl leesGenormalizeerdModelVoorInMemoryBlob(final Integer id);


    /**
     * Leest een {@link nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig} direct uit de database. Deze persoon kan gebruikt worden voor het maken van
     * een nieuwe blob.
     *
     * @param id technische sleutel van persoonHisVolledig
     * @return een instantie van PersoonHisVolledig
     */
    PersoonHisVolledigImpl leesGenormalizeerdModelVoorNieuweBlob(final Integer id);

    /**
     * Zoek het technisch (database) id van een persoon aan de hand van een BSN. Een 'null' return value kan betekenen dat het BSN niet bekend is in de
     * database, maar ook dat er meerdere personen bekend zijn met dit BSN.
     *
     * @param bsn het bsn
     * @return het technisch id of null
     * @throws NietUniekeBsnExceptie niet unieke BSN exceptie
     */
    Integer zoekIdBijBSN(BurgerservicenummerAttribuut bsn) throws NietUniekeBsnExceptie;

    /**
     * Zoek het technisch (database) id van een persoon aan de hand van een BSN. Een 'null' return value kan betekenen dat het BSN niet bekend is in de
     * database, maar ook dat er meerdere personen bekend zijn met dit BSN.
     *
     * @param bsn het bsn
     * @return het technisch id of null
     * @throws NietUniekeBsnExceptie niet unieke BSN exceptie
     */
    Integer zoekIdBijBSNVoorActievePersoon(BurgerservicenummerAttribuut bsn) throws NietUniekeBsnExceptie;


    /**
     * Zoek het technisch (database) id van een persoon aan de hand van een A-nummer. Een 'null' return value kan betekenen dat het A-nummer niet bekend is
     * in de database, maar ook dat er meerdere personen bekend zijn met dit A-nummer.
     *
     * @param anr het a-nummer
     * @return het technisch id of null
     * @throws NietUniekeAnummerExceptie niet unieke anummer exceptie
     */
    Integer zoekIdBijAnummer(AdministratienummerAttribuut anr) throws NietUniekeAnummerExceptie;

    /**
     * Verifieert of een persoon bestaat.
     *
     * @param technischId id van de persoon die we testen
     * @return {@code true} als de persoon bestaat, anders {@code false}
     */
    boolean bestaatPersoonMetId(final Integer technischId);
}
