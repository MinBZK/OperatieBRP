/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel.basis;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.PersoonAdresStandaardGroepBasis;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonAdresStandaardGroepModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonAdresBasis;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.StatusHistorie;


/**
 * Implementatie voor objecttype Persoon adres.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonAdresModel extends AbstractDynamischObjectType implements PersoonAdresBasis {

    @Id
    @SequenceGenerator(name = "PERSOONADRES", sequenceName = "Kern.seq_PersAdres")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONADRES")
    private Integer                         id;

    @ManyToOne
    @JoinColumn(name = "Pers")
    @NotNull
    private PersoonModel                    persoon;

    @Embedded
    @NotNull
    private PersoonAdresStandaardGroepModel gegevens;

    @Column(name = "PersAdresStatusHis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie                  statusHistorie;

    /** historie van de stndaard groep, wordt met de hand gevuld. */
    @Transient
    private List<PersoonAdresHisModel> historie;


    /**
     * copy constructor.
     *
     * @param adres het adres
     * @param pers de persoon
     */
    protected AbstractPersoonAdresModel(final PersoonAdresBasis adres, final PersoonModel pers) {
        super(adres);
        initLegeStatusHistorie();
        if (adres.getGegevens() != null) {
            gegevens = new PersoonAdresStandaardGroepModel(adres.getGegevens());
            statusHistorie = StatusHistorie.A;
        }
        historie = null;
        persoon = pers;
    }

    /**
     * default cons.
     */
    protected AbstractPersoonAdresModel() {
        initLegeStatusHistorie();
        historie = null;
    }

    /**
     * initieer alle sttaushistories op waarde X.
     */
    private void initLegeStatusHistorie() {
        statusHistorie = StatusHistorie.X;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public PersoonModel getPersoon() {
        return persoon;
    }

    @Override
    public PersoonAdresStandaardGroepModel getGegevens() {
        return gegevens;
    }

    /**
     * Vervangt de adres gegevens met de gegevens uit de opgegeven adres object.
     *
     * @param nieuwAdres het adres waarvan de gegevens moeten worden overgenomen.
     */
    protected void vervang(final AbstractPersoonAdresModel nieuwAdres) {
        vervangGroepen(nieuwAdres.getGegevens());
        historie = null;
    }

    /**
     * Vervang de groepen binnen dit ObjectType met een of meerdere groepen.
     * @param groepen een lijst van compatibele groepen.
     */
    protected void vervangGroepen(final AbstractGroep ... groepen) {
        if (groepen != null) {
            for (AbstractGroep groep : groepen) {
                if (groep instanceof PersoonAdresStandaardGroepModel) {
                    gegevens = new PersoonAdresStandaardGroepModel((PersoonAdresStandaardGroepBasis) groep);
                } else {
                    // adres heeft alleen maar 1 groep. Dus eigenlijk hoeven we geen list mee te geven.
                    throw new IllegalArgumentException("Groep van type " + groep.getClass().getName() + " wordt hier niet ondersteund");
                }
            }
        }
    }

    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }

    public List<PersoonAdresHisModel> getHistorie() {
        return historie;
    }

    public void setHistorie(final List<PersoonAdresHisModel> historie) {
        this.historie = historie;
    }
}
