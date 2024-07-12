package org.example.sec010.validator;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import org.example.models.sec10.ErrorMessage;
import org.example.models.sec10.ValidationCode;

import java.util.Optional;

public class RequestValidator {

    private static final  Metadata.Key<ErrorMessage> ERROR_KEY1 = ProtoUtils.keyForProto(ErrorMessage.newBuilder().setValidationCode(ValidationCode.INVALID_ACCOUNT).build());
    private static final  Metadata.Key<ErrorMessage> ERROR_KEY2 = ProtoUtils.keyForProto(ErrorMessage.newBuilder().setValidationCode(ValidationCode.INVALID_AMOUNT).build());
    private static final  Metadata.Key<ErrorMessage> ERROR_KEY3 = ProtoUtils.keyForProto(ErrorMessage.newBuilder().setValidationCode(ValidationCode.INSUFFICIENT_BALANCE).build());
    public static Optional<StatusRuntimeException> validateAccount(int accountNumber){
        if(accountNumber>0 && accountNumber <11){
            return Optional.empty();
        }
        var metadata = toMetadata(ValidationCode.INVALID_ACCOUNT,ERROR_KEY1);
        return Optional.of(Status.INVALID_ARGUMENT.asRuntimeException(metadata));
    }

    public static Optional<StatusRuntimeException> isAmountDivisibleBy10(int amount){
        if(amount>0 && amount % 10 ==0){
            return Optional.empty();
        }
        var metadata = toMetadata(ValidationCode.INVALID_AMOUNT,ERROR_KEY2);
        return Optional.of(Status.INVALID_ARGUMENT.asRuntimeException(metadata));
    }

    public static Optional<StatusRuntimeException> hasSufficientBalance(int amount,int balance){
        if(amount<=balance){
            return Optional.empty();
        }
        var metadata = toMetadata(ValidationCode.INSUFFICIENT_BALANCE,ERROR_KEY3);
        return Optional.of(Status.FAILED_PRECONDITION.asRuntimeException(metadata));
    }

    private static Metadata toMetadata(ValidationCode code, Metadata.Key key){
        var metadata = new Metadata();

        var errorMessage = ErrorMessage.newBuilder()
                .setValidationCode(code)
                .build();
        metadata.put(key,errorMessage);
        var stringKey = Metadata.Key.of("desc",Metadata.ASCII_STRING_MARSHALLER);
        metadata.put(stringKey, code.toString());
        return metadata;
    }
}
