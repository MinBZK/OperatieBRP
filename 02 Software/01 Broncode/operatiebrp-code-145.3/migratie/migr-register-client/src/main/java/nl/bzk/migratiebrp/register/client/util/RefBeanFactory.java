/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.register.client.util;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;

/**
 * Bean factory om een refertie naar een andere bean te maken obv de naam.
 * @param <T> bean type
 */
public final class RefBeanFactory<T> implements FactoryBean<T> {

    private T bean;

    /**
     * Zet de bean.
     * @param bean de te zetten bean
     */
    @Required
    public void setBean(final T bean) {
        this.bean = bean;
    }

    @Override
    public T getObject() {
        return bean;
    }

    @Override
    public Class<?> getObjectType() {
        return bean == null ? null : bean.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
