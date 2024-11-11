package com.vhProject.service;


import com.vhProject.constant.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    private ResponseHandler(){}
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put(AppConstant.MESSAGE, message);
        map.put(AppConstant.STATUS, status.value());
        map.put(AppConstant.DATA, responseObj);
        return ResponseEntity.ok(map);
    }
}
