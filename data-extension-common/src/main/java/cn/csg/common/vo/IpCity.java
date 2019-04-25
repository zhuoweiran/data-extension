
package cn.csg.common.vo;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * ip city
 * <p>
 * all the ip address
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "area",
    "ip",
    "latitude",
    "longitude"
})
public class IpCity {

    /**
     * id key
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("id key")
    private String id;
    /**
     * 区域
     * 
     */
    @JsonProperty("area")
    @JsonPropertyDescription("\u533a\u57df")
    private String area;
    /**
     * ip address
     * 
     */
    @JsonProperty("ip")
    @JsonPropertyDescription("ip address")
    private String ip;
    /**
     * 经度
     * 
     */
    @JsonProperty("latitude")
    @JsonPropertyDescription("\u7ecf\u5ea6")
    private Double latitude;
    /**
     * 纬度
     * 
     */
    @JsonProperty("longitude")
    @JsonPropertyDescription("\u7eac\u5ea6")
    private Double longitude;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * id key
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * id key
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public IpCity withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 区域
     * 
     */
    @JsonProperty("area")
    public String getArea() {
        return area;
    }

    /**
     * 区域
     * 
     */
    @JsonProperty("area")
    public void setArea(String area) {
        this.area = area;
    }

    public IpCity withArea(String area) {
        this.area = area;
        return this;
    }

    /**
     * ip address
     * 
     */
    @JsonProperty("ip")
    public String getIp() {
        return ip;
    }

    /**
     * ip address
     * 
     */
    @JsonProperty("ip")
    public void setIp(String ip) {
        this.ip = ip;
    }

    public IpCity withIp(String ip) {
        this.ip = ip;
        return this;
    }

    /**
     * 经度
     * 
     */
    @JsonProperty("latitude")
    public Double getLatitude() {
        return latitude;
    }

    /**
     * 经度
     * 
     */
    @JsonProperty("latitude")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public IpCity withLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    /**
     * 纬度
     * 
     */
    @JsonProperty("longitude")
    public Double getLongitude() {
        return longitude;
    }

    /**
     * 纬度
     * 
     */
    @JsonProperty("longitude")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public IpCity withLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public IpCity withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(area).append(ip).append(latitude).append(longitude).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IpCity) == false) {
            return false;
        }
        IpCity rhs = ((IpCity) other);
        return new EqualsBuilder().append(id, rhs.id).append(area, rhs.area).append(ip, rhs.ip).append(latitude, rhs.latitude).append(longitude, rhs.longitude).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
