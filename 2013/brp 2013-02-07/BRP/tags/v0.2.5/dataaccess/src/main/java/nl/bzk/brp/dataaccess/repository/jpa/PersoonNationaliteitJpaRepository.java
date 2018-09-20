/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie;
import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie.ReferentieVeld;
import nl.bzk.brp.dataaccess.repository.PersoonNationaliteitRepository;
import nl.bzk.brp.dataaccess.repository.historie.GroepHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Nationaliteitcode;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonNationaliteitStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonNationaliteitHisModel;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonNationaliteitModel;
import org.springframework.stereotype.Repository;

/**
 *
 *
 */
@Repository
public class PersoonNationaliteitJpaRepository implements PersoonNationaliteitRepository {

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Inject
    private GroepHistorieRepository<PersoonNationaliteitModel, AbstractPersoonNationaliteitStandaardGroep,
            PersoonNationaliteitHisModel> persoonNationaliteitStandaardHistorieRepository;


    /**
     * .
     *
     * @param persoon .
     * @param nationaliteit .
     * @param actie .
     * @param datumAanvangGeldigheid .
     * @return .
     */
    @Override
    public PersoonModel voegNationaliteit(final PersoonModel persoon,
        final PersoonNationaliteitModel nationaliteit, final ActieModel actie,
        final Datum datumAanvangGeldigheid)
    {
        // We gaan vanuit dat de nationaliteit al gevuld is, bahalve de persoon de primary key.
        // nationaliteit bevat alleen een 'nationaliteit' + historie.
        // Let op dat je de huidige dus niet kan/mag wijzigen.
        Nationaliteitcode nieuweCode = nationaliteit.getNationaliteit().getNationaliteitcode();
        for (PersoonNationaliteitModel bestaande : persoon.getNationaliteiten()) {
            if (bestaande.getNationaliteit().getNationaliteitcode().equals(nieuweCode)) {
                throw new ObjectReedsBestaandExceptie(ReferentieVeld.LANDCODE, nieuweCode.getWaarde(), null);
            }
        }

        // Kopieer nu alle gegevens naar de daadwerkelijke nieuwe nationalieit, schrijf het weg
        // update de historie, haal de persoon nu opnieuw op en geef deze terug..
        PersoonNationaliteitModel nieuwNationaliteit = new PersoonNationaliteitModel(nationaliteit, persoon);
        persoon.getNationaliteiten().add(nieuwNationaliteit);
        em.merge(persoon);
        // zoek welke is toegevoegd:
        // of we kunnen de nieuwNationaliteit persisteren dan toevoegen aan onze persoon, dan persoon  mergen
        // of we voegen toe aan de persoon en de persoon mergen dan uitzoeken welke de nieuwe was.
        // vervolgens gaan we de historie records aanmaken.
        for (PersoonNationaliteitModel bestaande : persoon.getNationaliteiten()) {
            if (bestaande.getNationaliteit().getNationaliteitcode().equals(nieuweCode)) {
                nieuwNationaliteit = bestaande;
                break;
            }
        }
        persoonNationaliteitStandaardHistorieRepository.persisteerHistorie(nieuwNationaliteit,
            actie, datumAanvangGeldigheid, null);
        return em.createQuery("Select persoon from PersoonModel persoon where id = :id", PersoonModel.class)
                 .setParameter("id", persoon.getId())
                 .getSingleResult();
    }

}
