/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.model;

import nl.bzk.brp.gba.domain.bevraging.Adresvraag;
import nl.bzk.brp.gba.domain.bevraging.Basisvraag;
import nl.bzk.migratiebrp.conversie.vragen.ConversieLo3NaarBrpVragen;
import nl.bzk.migratiebrp.conversie.vragen.mapper.VerzoekBerichtMapper;

/**
 * Mapt instanties van Vraag op instanties van Adresvraag of Persoonsvraag.
 * @param <T> geeft aan of er naar een Adresvraag of Persoonsvraag gemapped wordt
 */
public final class WebserviceVraagMapper<T extends Basisvraag> {

    private final Class<T> clazz;
    private final VerzoekBerichtMapper verzoekBerichtMapper;

    /**
     * Default constructor.
     * @param clazz Class van type T om de vraag mee te instantieren
     * @param conversieLo3NaarBrpVragen conversieService voor het converteren van de zoekrubrieken naar de BRP varianten
     */
    public WebserviceVraagMapper(final Class<T> clazz, final ConversieLo3NaarBrpVragen conversieLo3NaarBrpVragen) {
        this.clazz = clazz;
        this.verzoekBerichtMapper = new VerzoekBerichtMapper(conversieLo3NaarBrpVragen);
    }

    /**
     * Mapt instanties van Basisvraag op instanties van ZoekPersoonGbaVerzoek.
     * @param vraag vraag uit de webservice
     * @param partijCode de code van de versturende partij
     * @return naar persoonsvraag geconverteerde vraag
     */
    public T mapVraag(final AbstractWebserviceVraagBericht vraag, final String partijCode) {

        T geconverteerdeVraag;
        try {
            geconverteerdeVraag = verzoekBerichtMapper.mapNaarBrpVraag(
                    clazz.newInstance(),
                    partijCode,
                    vraag.getSoortDienst(),
                    vraag.getGevraagdeRubrieken(),
                    vraag.getZoekCriteria()
            );
        } catch (IllegalAccessException | InstantiationException e) {
            throw new IllegalStateException(e);
        }

        if (geconverteerdeVraag instanceof Adresvraag && ((AdhocWebserviceVraagBericht) vraag).isAdresvraag()) {
            Adresvraag.SoortIdentificatie ident;
            if (((AdhocWebserviceVraagBericht) vraag).isPersoonIdentificatie()) {
                ident = Adresvraag.SoortIdentificatie.PERSOON;
            } else {
                ident = Adresvraag.SoortIdentificatie.ADRES;
            }
            ((Adresvraag) geconverteerdeVraag).setSoortIdentificatie(ident);
        }

        geconverteerdeVraag.setSlimZoekenStandaard();
        if (vraag instanceof AdhocWebserviceVraagBericht && ((AdhocWebserviceVraagBericht) vraag).isZoekenInHistorie()) {
            geconverteerdeVraag.setZoekenInHistorie();
        }

        return geconverteerdeVraag;
    }
}
