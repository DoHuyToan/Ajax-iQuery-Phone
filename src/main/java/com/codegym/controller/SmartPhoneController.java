package com.codegym.controller;

import com.codegym.model.Smartphone;
import com.codegym.service.ISmartphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/smartphones")
public class SmartPhoneController {

    @Autowired
    private ISmartphoneService smartphoneService;

    @GetMapping
//    •	ResponseEntity đại diện cho toàn bộ phản hồi HTTP
    private ResponseEntity<Iterable<Smartphone>> allPhones(){
        return new ResponseEntity<>(smartphoneService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ModelAndView getAllSmartphonePage(){
        ModelAndView modelAndView = new ModelAndView("/phone/list");
        modelAndView.addObject("smartphoneList", smartphoneService.findAll());
        return modelAndView;
    }

//  Tạo API thực hiện sự kiện thêm mới trong SmartphoneController
    @PostMapping
//    @RequestBody: liên kết phần thân <body> yêu cầu HTTP đến với tham số
//    @RequestBody Smartphone smartphone thực hiện gán dữ liệu từ json nhận được
//    vào các trường tương ứng của smartphone
    public ResponseEntity<Smartphone> createSmartphone(@RequestBody Smartphone smartphone){
        return new ResponseEntity<>(smartphoneService.save(smartphone), HttpStatus.CREATED);
    }

//  Tạo API thực hiện sự kiện xoá trong SmartphoneController
    @DeleteMapping("/{id}")
    public ResponseEntity<Smartphone> deleteSmartphone(@PathVariable Long id){
//        Optional chỉ trả về có hoặc ko, nên phải get() để lấy đối tượng ra
        Optional<Smartphone> smartphoneOptional = smartphoneService.findById(id);
        if(!smartphoneOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        smartphoneService.remove(id);
        return new ResponseEntity<>(smartphoneOptional.get(), HttpStatus.NO_CONTENT);
    }
}
