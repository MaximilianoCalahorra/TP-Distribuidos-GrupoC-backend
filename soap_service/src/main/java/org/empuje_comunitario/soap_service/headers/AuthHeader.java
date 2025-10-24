package org.empuje_comunitario.soap_service.headers;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Auth", namespace = "auth.headers")
public class AuthHeader {

    private String grupo;
    private String clave;

    @XmlElement(name = "Grupo", namespace = "auth.headers")
    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @XmlElement(name = "Clave", namespace = "auth.headers")
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}