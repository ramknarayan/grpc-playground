package org.example;

import io.grpc.*;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import org.example.sec06.BankService;
import org.example.sec12.interceptors.GzipResponseInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.Consumer;

public class GrpcServer {
    /*private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws Exception{
        var server = ServerBuilder.forPort(8080)
                .addService(new BankService())
                .build();
        server.start();
        server.awaitTermination();
    }*/
    public static final Logger log = LoggerFactory.getLogger(GrpcServer.class);

    private final Server server;

    public GrpcServer(Server server) {
        this.server = server;
    }

    public static GrpcServer create(BindableService... services){
        return create(6060,services);
    }


    public static GrpcServer create(int port, BindableService... services){
        return create(port,builder ->{
           Arrays.asList(services).forEach(builder::addService);
        });
        /*var builder = ServerBuilder.forPort(port);
        consumer.accept((NettyServerBuilder) builder);
        return new GrpcServer(builder.build());*/
    }
    public static GrpcServer create(int port, Consumer<ServerBuilder> consumer){
        var builder = ServerBuilder.forPort(port);
        consumer.accept(builder);
        return new GrpcServer(builder.build());
    }



    public GrpcServer start(){
        var services = server.getServices()
                .stream()
                .map(ServerServiceDefinition::getServiceDescriptor)
                .map(ServiceDescriptor::getName)
                .toList();
        try{
            server.start();
            log.info("Server started. Listening on port{}. Services : {}",server.getPort(),services);
            return this;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void await(){
        try{
            server.awaitTermination();
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }
    public void stop(){
        server.shutdownNow();
        log.info("Server stopped");
    }
}

