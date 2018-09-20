package nl.bzk.brp.funqmachine.configuratie

/**
 * Specifieke abstractie voor de configuratie van een database.
 */
public class DatabaseConfig {

    private driverClassName
    private username
    private password
    private url

    /**
     * Maakt / zoekt de configiratie voor een specifieke database.
     *
     * @param database Database waarvoor de configuratie wordt gemaakt
     */
    DatabaseConfig(String driverClassName,
                   String username,
                   String password,
                   String url) {
        this.driverClassName = driverClassName
        this.username = username
        this.password = password
        this.url = url
    }

    String getDriverClassName() {
        return driverClassName
    }

    String getUsername() {
        return username
    }

    String getPassword() {
        return password
    }

    String getUrl() {
        return url
    }


    @Override
    public String toString() {
        return "DatabaseConfig{" +
            "driverClassName=" + driverClassName +
            ", username=" + username +
            ", password=" + password +
            ", url=" + url +
            '}';
    }
}
