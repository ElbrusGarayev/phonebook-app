package com.gara.ibasestep.model.response;

import com.gara.ibasestep.enums.OperationStatus;
import com.gara.ibasestep.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOperationResponse {

    private int userId;
    private OperationType operationType;
    private OperationStatus operationStatus;

}
