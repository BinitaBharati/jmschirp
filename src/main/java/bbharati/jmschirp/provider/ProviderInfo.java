package bbharati.jmschirp.provider;

/**
 * Created with IntelliJ IDEA.
 * User: binita.bharati@gmail.com
 * Date: 28/06/13
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 *
 */

public class ProviderInfo
{
    String connectionName;
    String host;
    String port;
    String adminUser;
    String adminPasswd;
    String vendorType;
    String vendorVersion;

    //EMS specific
    String jndiName;
    String jndiUser;
    String jndiPasswd;

    //ActiveMQ specific
    String jmxPort;
    String jmxUser;
    String jmxPassword;

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(String adminUser) {
        this.adminUser = adminUser;
    }

    public String getAdminPasswd() {
        return adminPasswd;
    }

    public void setAdminPasswd(String adminPasswd) {
        this.adminPasswd = adminPasswd;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public String getVendorVersion() {
        return vendorVersion;
    }

    public void setVendorVersion(String vendorVersion) {
        this.vendorVersion = vendorVersion;
    }

    public String getJndiName() {
        return jndiName;
    }

    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }

    public String getJndiUser() {
        return jndiUser;
    }

    public void setJndiUser(String jndiUser) {
        this.jndiUser = jndiUser;
    }

    public String getJndiPasswd() {
        return jndiPasswd;
    }

    public void setJndiPasswd(String jndiPasswd) {
        this.jndiPasswd = jndiPasswd;
    }

    public String getJmxPort() {
        return jmxPort;
    }

    public void setJmxPort(String jmxPort) {
        this.jmxPort = jmxPort;
    }

    public String getJmxUser() {
        return jmxUser;
    }

    public void setJmxUser(String jmxUser) {
        this.jmxUser = jmxUser;
    }

    public String getJmxPassword() {
        return jmxPassword;
    }

    public void setJmxPassword(String jmxPassword) {
        this.jmxPassword = jmxPassword;
    }
}
