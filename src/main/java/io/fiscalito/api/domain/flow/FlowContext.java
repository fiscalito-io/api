package io.fiscalito.api.domain.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Locale;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlowContext implements Serializable {
    private String phoneNumber;
    private FlowStateEnum currentState;
    private FlowStateEnum previousState;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Instant lastUpdated;
    private String flowType;
    private String data; // JSON string to store any additional data needed by the flow
    private Boolean waitingForResponse; // Indicates if the flow is waiting for a response from the user
    private String lang;

    @JsonIgnore
    public Locale getLocale() {
        if (lang == null || lang.isEmpty()) {
            return Locale.getDefault(); // Fallback to default locale if lang is not set
        }
        String[] parts = lang.split("_");
        if (parts.length == 1) {
            return Locale.of(parts[0]); // Language only
        } else if (parts.length == 2) {
            return Locale.of(parts[0], parts[1]); // Language and country
        } else {
            return Locale.of(parts[0], parts[1], parts[2]); // Language, country, and variant
        }
    }
}