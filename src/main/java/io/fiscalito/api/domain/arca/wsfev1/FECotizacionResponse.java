
package io.fiscalito.api.domain.arca.wsfev1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FECotizacionResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FECotizacionResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResultGet" type="{http://ar.gov.afip.dif.FEV1/}Cotizacion" minOccurs="0"/>
 *         &lt;element name="Errors" type="{http://ar.gov.afip.dif.FEV1/}ArrayOfErr" minOccurs="0"/>
 *         &lt;element name="Events" type="{http://ar.gov.afip.dif.FEV1/}ArrayOfEvt" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FECotizacionResponse", propOrder = {
    "resultGet",
    "errors",
    "events"
})
public class FECotizacionResponse {

    @XmlElement(name = "ResultGet")
    protected Cotizacion resultGet;
    @XmlElement(name = "Errors")
    protected ArrayOfErr errors;
    @XmlElement(name = "Events")
    protected ArrayOfEvt events;

    /**
     * Gets the value of the resultGet property.
     * 
     * @return
     *     possible object is
     *     {@link Cotizacion }
     *     
     */
    public Cotizacion getResultGet() {
        return resultGet;
    }

    /**
     * Sets the value of the resultGet property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cotizacion }
     *     
     */
    public void setResultGet(Cotizacion value) {
        this.resultGet = value;
    }

    /**
     * Gets the value of the errors property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfErr }
     *     
     */
    public ArrayOfErr getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfErr }
     *     
     */
    public void setErrors(ArrayOfErr value) {
        this.errors = value;
    }

    /**
     * Gets the value of the events property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEvt }
     *     
     */
    public ArrayOfEvt getEvents() {
        return events;
    }

    /**
     * Sets the value of the events property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEvt }
     *     
     */
    public void setEvents(ArrayOfEvt value) {
        this.events = value;
    }

}
