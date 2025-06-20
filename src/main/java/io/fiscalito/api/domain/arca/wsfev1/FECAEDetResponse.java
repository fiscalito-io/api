package io.fiscalito.api.domain.arca.wsfev1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * <p>Java class for FECAEDetResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="FECAEDetResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ar.gov.afip.dif.FEV1/}FEDetResponse">
 *       &lt;sequence>
 *         &lt;element name="CAE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CAEFchVto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImpTotal" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ImpNeto" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ImpIVA" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ImpOpEx" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ImpTrib" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ImpTotConc" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FECAEDetResponse", propOrder = {
        "cae",
        "caeFchVto",
        "impTotal",
        "impNeto",
        "impIVA",
        "impOpEx",
        "impTrib",
        "impTotConc"
})
public class FECAEDetResponse extends FEDetResponse {

    @XmlElement(name = "CAE")
    protected String cae;

    @XmlElement(name = "CAEFchVto")
    protected String caeFchVto;

    @XmlElement(name = "ImpTotal")
    protected double impTotal;

    @XmlElement(name = "ImpNeto")
    protected double impNeto;

    @XmlElement(name = "ImpIVA")
    protected double impIVA;

    @XmlElement(name = "ImpOpEx")
    protected double impOpEx;

    @XmlElement(name = "ImpTrib")
    protected double impTrib;

    @XmlElement(name = "ImpTotConc")
    protected double impTotConc;

    public String getCAE() {
        return cae;
    }

    public void setCAE(String cae) {
        this.cae = cae;
    }

    public String getCAEFchVto() {
        return caeFchVto;
    }

    public void setCAEFchVto(String caeFchVto) {
        this.caeFchVto = caeFchVto;
    }

    public double getImpTotal() {
        return impTotal;
    }

    public void setImpTotal(double impTotal) {
        this.impTotal = impTotal;
    }

    public double getImpNeto() {
        return impNeto;
    }

    public void setImpNeto(double impNeto) {
        this.impNeto = impNeto;
    }

    public double getImpIVA() {
        return impIVA;
    }

    public void setImpIVA(double impIVA) {
        this.impIVA = impIVA;
    }

    public double getImpOpEx() {
        return impOpEx;
    }

    public void setImpOpEx(double impOpEx) {
        this.impOpEx = impOpEx;
    }

    public double getImpTrib() {
        return impTrib;
    }

    public void setImpTrib(double impTrib) {
        this.impTrib = impTrib;
    }

    public double getImpTotConc() {
        return impTotConc;
    }

    public void setImpTotConc(double impTotConc) {
        this.impTotConc = impTotConc;
    }
}
