package com.motta.insurance_scheme_service.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceCheckDTO {

    private int id;
    private String serviceName;
    private int status;

    public ServiceCheckDTO(int id, String serviceName, int status) {
        this.id = id;
        this.serviceName = serviceName;
        this.status = status;
    }
}
