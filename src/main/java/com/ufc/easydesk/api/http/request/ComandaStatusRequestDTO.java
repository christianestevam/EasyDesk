package com.ufc.easydesk.api.http.request;

import com.ufc.easydesk.model.enums.Status;
import lombok.Data;

@Data
public class ComandaStatusRequestDTO {
    private Status status;
}
