
package io.fiscalito.api.domain.arca.wsfev1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Auth" type="{http://ar.gov.afip.dif.FEV1/}FEAuthRequest" minOccurs="0"/>
 *         &lt;element name="Periodo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Orden" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "auth",
    "periodo",
    "orden"
})
@XmlRootElement(name = "FECAEASolicitar")
public class FECAEASolicitar {

    @XmlElement(name = "Auth")
    protected FEAuthRequest auth;
    @XmlElement(name = "Periodo")
    protected int periodo;
    @XmlElement(name = "Orden")
    protected short orden;

    /**
     * Gets the value of the auth property.
     * 
     * @return
     *     possible object is
     *     {@link FEAuthRequest }
     *     
     */
    public FEAuthRequest getAuth() {
        return auth;
    }

    /**
     * Sets the value of the auth property.
     * 
     * @param value
     *     allowed object is
     *     {@link FEAuthRequest }
     *     
     */
    public void setAuth(FEAuthRequest value) {
        this.auth = value;
    }

    /**
     * Gets the value of the periodo property.
     * 
     */
    public int getPeriodo() {
        return periodo;
    }

    /**
     * Sets the value of the periodo property.
     * 
     */
    public void setPeriodo(int value) {
        this.periodo = value;
    }

    /**
     * Gets the value of the orden property.
     * 
     */
    public short getOrden() {
        return orden;
    }

    /**
     * Sets the value of the orden property.
     * 
     */
    public void setOrden(short value) {
        this.orden = value;
    }

}
