package com.example.finance.factory;

import com.example.finance.model.dto.TransferFunds;
import com.example.finance.utils.TestConstants;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransferFundsMockFactory {

    public TransferFunds createTransferFundsDto() {
        return new TransferFunds(
                TestConstants.USER_UUID,
                TestConstants.FROM_CATEGORY_NAME,
                TestConstants.TO_CATEGORY_NAME,
                TestConstants.TRANSFER_AMOUNT,
                TestConstants.TRANSFER_FROM_BUDGET_ID,
                TestConstants.TRANSFER_TO_BUDGET_ID
        );
    }
}
