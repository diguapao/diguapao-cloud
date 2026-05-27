package com.aip.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatStreamResponse {

    private List<Choice> choices;

}