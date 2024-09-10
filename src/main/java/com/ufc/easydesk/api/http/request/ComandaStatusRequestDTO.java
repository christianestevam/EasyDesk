package com.ufc.easydesk.api.http.request;

import com.ufc.easydesk.domain.enums.Status;
import lombok.Data;

@Data
public class ComandaStatusRequestDTO {
    private Status status;
}
