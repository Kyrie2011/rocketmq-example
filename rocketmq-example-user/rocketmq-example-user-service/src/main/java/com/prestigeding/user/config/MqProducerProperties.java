package com.prestigeding.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "apache.rocketmq.producer")
public class MqProducerProperties {

    private String producerGroup;
    private String namesrvAddr;
    private String transactionProducerGroup;

    public String getProducerGroup() {
        return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getTransactionProducerGroup() {
        return transactionProducerGroup;
    }

    public void setTransactionProducerGroup(String transactionProducerGroup) {
        this.transactionProducerGroup = transactionProducerGroup;
    }
}
