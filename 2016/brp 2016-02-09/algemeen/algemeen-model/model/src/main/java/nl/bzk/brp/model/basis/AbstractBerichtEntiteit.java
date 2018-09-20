/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Basis klasse voor alle bericht entiteiten (objecttypes die in een bericht zitten, niet zijnde een super type en/of een koppeling tussen andere
 * objecttypes). Deze klasse biedt de standaard implementatie voor de methodes van de {@link BerichtEntiteit} interface.
 */
public abstract class AbstractBerichtEntiteit extends AbstractBerichtIdentificeerbaar implements BerichtEntiteit,
    BerichtIdentificeerbaar, MetaIdentificeerbaar
{

    private String                 objecttype;
    private String                 referentieID;
    private String                 objectSleutel;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getObjecttype() {
        return objecttype;
    }

    /**
     * Setter voor het zetten van het entiteitstype.
     *
     * @param objecttype het type van deze entiteit/object.
     */
    public void setObjecttype(final String objecttype) {
        this.objecttype = objecttype;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getReferentieID() {
        return referentieID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setReferentieID(final String referentieID) {
        this.referentieID = referentieID;
    }

    /**
     * Geeft de technische sleutel terug als String. Onderliggend type is Sleutelwaardetekst. Dit gaat wellicht in de toekomst nog veranderen.
     *
     * @return de object sleutel als String
     */
    @Override
    public String getObjectSleutel() {
        return objectSleutel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setObjectSleutel(final String objectSleutel) {
        this.objectSleutel = objectSleutel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCommunicatieIdVoorElement(final Integer databaseObjectId) {
        String communicatieId = null;
        if (databaseObjectId != null) {
            if (this.getMetaId().equals(databaseObjectId)) {
                // Gezocht object is huidige entiteit
                communicatieId = this.getCommunicatieID();
            } else {
                // Gezocht object is mogelijk een attribuut van deze entiteit.
                for (final BerichtEntiteitGroep groep : getBerichtEntiteitGroepen()) {
                    if (groep != null
                        && (groep.getMetaId().equals(databaseObjectId) || groep
                        .bevatElementMetMetaId(databaseObjectId)))
                    {
                        if (groep.getCommunicatieID() != null) {
                            communicatieId = groep.getCommunicatieID();
                        } else {
                            communicatieId = this.getCommunicatieID();
                        }
                        break;
                    }
                }

                if (communicatieId == null) {
                    // Gezocht object is mogelijk een onderdeel van een van de (sub)entiteiten van deze entiteit
                    for (final BerichtEntiteit entiteit : getBerichtEntiteiten()) {
                        communicatieId = entiteit.getCommunicatieIdVoorElement(databaseObjectId);
                        if (communicatieId != null) {
                            break;
                        }
                    }
                }
            }
        }
        return communicatieId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean bevatElementMetMetaId(final Integer metaId) {
        return groepBevatMetaId(metaId) || entiteitBevatMetaId(metaId);
    }

    private boolean entiteitBevatMetaId(final Integer metaId) {
        boolean bevatMetaId = false;
        for (final BerichtEntiteit entiteit : getBerichtEntiteiten()) {
            bevatMetaId = entiteit.getMetaId().equals(metaId) || entiteit.bevatElementMetMetaId(metaId);
            if (bevatMetaId) {
                break;
            }
        }
        return bevatMetaId;
    }

    private boolean groepBevatMetaId(final Integer metaId) {
        boolean bevatMetaId = false;
        for (final BerichtEntiteitGroep groep : getBerichtEntiteitGroepen()) {
            bevatMetaId = groep.getMetaId().equals(metaId) || groep.bevatElementMetMetaId(metaId);
            if (bevatMetaId) {
                break;
            }
        }
        return bevatMetaId;
    }

}
