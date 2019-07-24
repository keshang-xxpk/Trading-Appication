package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.*;
import java.util.HashMap;
import java.util.Map;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "country",
        "dob",
        "email",
        "firstName",
        "id",
        "lastName"
})
public class Trader {

        @JsonProperty("country")
        private String country;
        @JsonProperty("dob")
        private String dob;
        @JsonProperty("email")
        private String email;
        @JsonProperty("firstName")
        private String firstName;
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("lastName")
        private String lastName;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("country")
        public String getCountry() {
            return country;
        }

        @JsonProperty("country")
        public void setCountry(String country) {
            this.country = country;
        }

        @JsonProperty("dob")
        public String getDob() {
            return dob;
        }

        @JsonProperty("dob")
        public void setDob(String dob) {
            this.dob = dob;
        }

        @JsonProperty("email")
        public String getEmail() {
            return email;
        }

        @JsonProperty("email")
        public void setEmail(String email) {
            this.email = email;
        }

        @JsonProperty("firstName")
        public String getFirstName() {
            return firstName;
        }

        @JsonProperty("firstName")
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        @JsonProperty("id")
        public Integer getId() {
            return id;
        }

        @JsonProperty("id")
        public void setId(Integer id) {
            this.id = id;
        }

        @JsonProperty("lastName")
        public String getLastName() {
            return lastName;
        }

        @JsonProperty("lastName")
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }


}
