
package io.fiscalito.api.domain.arca.wsfev1;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfPtoVenta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPtoVenta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PtoVenta" type="{http://ar.gov.afip.dif.FEV1/}PtoVenta" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPtoVenta", propOrder = {
    "ptoVenta"
})
public class ArrayOfPtoVenta {

    @XmlElement(name = "PtoVenta", nillable = true)
    protected List<PtoVenta> ptoVenta;

    /**
     * Gets the value of the ptoVenta property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ptoVenta property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPtoVenta().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PtoVenta }
     * 
     * 
     */
    public List<PtoVenta> getPtoVenta() {
        if (ptoVenta == null) {
            ptoVenta = new ArrayList<PtoVenta>();
        }
        return this.ptoVenta;
    }

}
