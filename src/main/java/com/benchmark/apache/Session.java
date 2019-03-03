package com.benchmark.apache;

import lombok.Data;

@Data
public class Session {

    private Double completeRequest;
    private Double failedRequest;
    private Double requestPerSecond;
    private Double takenTime;
    private Double timePerRequest;

}
