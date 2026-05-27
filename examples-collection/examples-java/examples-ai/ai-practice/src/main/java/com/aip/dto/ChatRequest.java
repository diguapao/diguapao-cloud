package com.aip.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatRequest {

    private String model;

    private Boolean stream;

    private Double temperature;

    private Integer max_tokens;
    private Double top_p;
    private String do_sample;

    private List<Message> messages;

}