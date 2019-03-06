package com.kdhira.jwtgen.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jwtgen/info")
public class InformationController {

    @GetMapping
    public ResponseEntity<java.util.Map<String, Object>> map() {
        java.util.Map<String, Object> map = new java.util.HashMap<String, Object>();
        map.put("name", InformationController.class.getPackage().getImplementationTitle());
        map.put("version", InformationController.class.getPackage().getImplementationVersion());
        return ResponseEntity.ok(map);
    }

}
