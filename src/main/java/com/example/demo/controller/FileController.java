package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dulred
 * @description
 * @github https://github.com/dulred
 */
@Slf4j
@RestController
public class FileController {

    @GetMapping("/exportFile1")
    public void  exportFile1(){
        System.out.println("你是猪11111");
    }


    @GetMapping("/exportFile2")
    public void exportFile2( ){

        System.out.println("你是猪22");

    }


}
