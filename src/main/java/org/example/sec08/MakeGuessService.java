/*
package org.example.sec08;

import io.grpc.stub.StreamObserver;
import org.example.models.sec07.Output;
import org.example.models.sec07.RequestSize;
import org.example.models.sec08.GuessNumberGrpc;

import org.example.models.sec08.GuessRequest;
import org.example.models.sec08.GuessResponse;
import org.example.sec07.FlowControlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class MakeGuessService extends GuessNumberGrpc.GuessNumberImplBase {


    @Override
    public StreamObserver<GuessRequest> makeGuess(StreamObserver<GuessResponse> responseObserver) {

    }
    private static class GuessRequestHandler implements StreamObserver<GuessRequest> {
        private final StreamObserver<GuessResponse> responseObserver;
        private final int secret;
        private int attempt;

        private GuessRequestHandler(StreamObserver<GuessResponse> responseObserver) {
            this.responseObserver = responseObserver;
            this.attempt = 0;
            this.secret = ThreadLocalRandom.current().nextInt(1, 101);
        }

        @Override
        public void onNext(GuessRequest guessRequest) {

        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onCompleted() {

        }
    }
}
*/
