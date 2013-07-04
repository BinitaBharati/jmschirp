package bbharati.jmschirp.provider;

class JMSProviderDetails
{
    def jmsConnectionName;
    def jmsProviderUrl;
    def jmsUserName;
    def jmsPasswd;
    def jmsVendorType;
    def jmsVendorVersion;
    def connectionFactoryJndiName;
    def useJndi

    public def getJmsConnectionName() {
        return jmsConnectionName
    }

    public void setJmsConnectionName(def jmsConnectionName) {
        this.jmsConnectionName = jmsConnectionName
    }

    public def getJmsProviderUrl() {
        return jmsProviderUrl
    }

    public void setJmsProviderUrl(def jmsProviderUrl) {
        this.jmsProviderUrl = jmsProviderUrl
    }

    public def getJmsUserName() {
        return jmsUserName
    }

    public void setJmsUserName(def jmsUserName) {
        this.jmsUserName = jmsUserName
    }

    public def getJmsPasswd() {
        return jmsPasswd
    }

    public void setJmsPasswd(def jmsPasswd) {
        this.jmsPasswd = jmsPasswd
    }

    public def getJmsVendorType() {
        return jmsVendorType
    }

    public void setJmsVendorType(def jmsVendorType) {
        this.jmsVendorType = jmsVendorType
    }

    public def getJmsVendorVersion() {
        return jmsVendorVersion
    }

    public void setJmsVendorVersion(def jmsVendorVersion) {
        this.jmsVendorVersion = jmsVendorVersion
    }

    public def getConnectionFactoryJndiName() {
        return connectionFactoryJndiName
    }

    public void setConnectionFactoryJndiName(def connectionFactoryJndiName) {
        this.connectionFactoryJndiName = connectionFactoryJndiName
    }

    public def getUseJndi() {
        return useJndi
    }

    public def setUseJndi(def useJndi) {
        this.useJndi = useJndi
    }
/*JMSProviderDetails( jmsProviderUrl, jmsUserName, jmsPasswd)
    {
        this.jmsProviderUrl = jmsProviderUrl;
        this.jmsUserName = jmsUserName;
        this.jmsPasswd = jmsPasswd;
    }

    JMSProviderDetails( jmsProviderUrl, jmsUserName, jmsPasswd,
     jmsVendorName,  jmsVendorVersion)
    {
        this.jmsProviderUrl = jmsProviderUrl;
        this.jmsUserName = jmsUserName;
        this.jmsPasswd = jmsPasswd;
        this.jmsVendorName = jmsVendorName;
        this.jmsVendorVersion = jmsVendorVersion;
    } */


}
