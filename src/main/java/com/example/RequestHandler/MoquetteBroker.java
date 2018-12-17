package com.example.RequestHandler;

public class MoquetteBroker {
    private String host;
    private Integer port;

    public MoquetteBroker(String host, Integer port, Integer score) {
        this.host = host;
        this.port = port;
    }

    public MoquetteBroker(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoquetteBroker that = (MoquetteBroker) o;

        if (!host.equals(that.host)) return false;
        return port.equals(that.port);
    }

    @Override
    public int hashCode() {
        int result = host.hashCode();
        result = 31 * result + port.hashCode();
        return result;
    }
}
