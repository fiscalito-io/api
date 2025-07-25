
package io.fiscalito.api.domain.arca.wsfev1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FECAEARequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FECAEARequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FeCabReq" type="{http://ar.gov.afip.dif.FEV1/}FECAEACabRequest" minOccurs="0"/>
 *         &lt;element name="FeDetReq" type="{http://ar.gov.afip.dif.FEV1/}ArrayOfFECAEADetRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FECAEARequest", propOrder = {
    "feCabReq",
    "feDetReq"
})
public class FECAEARequest {

    @XmlElement(name = "FeCabReq")
    protected FECAEACabRequest feCabReq;
    @XmlElement(name = "FeDetReq")
    protected ArrayOfFECAEADetRequest feDetReq;

    /**
     * Gets the value of the feCabReq property.
     * 
     * @return
     *     possible object is
     *     {@link FECAEACabRequest }
     *     
     */
    public FECAEACabRequest getFeCabReq() {
        return feCabReq;
    }

    /**
     * Sets the value of the feCabReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link FECAEACabRequest }
     *     
     */
    public void setFeCabReq(FECAEACabRequest value) {
        this.feCabReq = value;
    }

    /**
     * Gets the value of the feDetReq property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFECAEADetRequest }
     *     
     */
    public ArrayOfFECAEADetRequest getFeDetReq() {
        return feDetReq;
    }

    /**
     * Sets the value of the feDetReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFECAEADetRequest }
     *     
     */
    public void setFeDetReq(ArrayOfFECAEADetRequest value) {
        this.feDetReq = value;
    }

}
